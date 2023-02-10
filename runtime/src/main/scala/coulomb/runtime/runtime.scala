/*
 * Copyright 2022 Erik Erlandson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package coulomb.runtime

import scala.quoted.*
import scala.util.{Try, Success, Failure}

import scala.collection.immutable.HashMap

import coulomb.{infra => _, *}
import coulomb.syntax.*
import coulomb.rational.Rational
import coulomb.conversion.*

sealed abstract class RuntimeUnit:
    def *(rhs: RuntimeUnit): RuntimeUnit.Mul = RuntimeUnit.Mul(this, rhs)
    def /(den: RuntimeUnit): RuntimeUnit.Div = RuntimeUnit.Div(this, den)
    def ^(e: Rational): RuntimeUnit.Pow = RuntimeUnit.Pow(this, e)
    override def toString: String =
        def paren(s: String, tl: Boolean): String =
            if (tl) s else s"($s)"
        def work(u: RuntimeUnit, tl: Boolean = false): String =
            u match
                case RuntimeUnit.UnitConst(value) =>
                    s"$value"
                case RuntimeUnit.UnitType(path) =>
                    path.split('.').last
                case RuntimeUnit.Mul(l, r) =>
                    paren(s"${work(l)}*${work(r)}", tl)
                case RuntimeUnit.Div(n, d) =>
                    paren(s"${work(n)}/${work(d)}", tl)
                case RuntimeUnit.Pow(b, e) =>
                    paren(s"${work(b)}^$e", tl)
        work(this, tl = true)

object RuntimeUnit:
    case class UnitConst(value: Rational) extends RuntimeUnit
    case class UnitType(path: String) extends RuntimeUnit
    case class Mul(lhs: RuntimeUnit, rhs: RuntimeUnit) extends RuntimeUnit
    case class Div(num: RuntimeUnit, den: RuntimeUnit) extends RuntimeUnit
    case class Pow(b: RuntimeUnit, e: Rational) extends RuntimeUnit
    inline def of[U]: RuntimeUnit = ${ infra.meta.unitRTU[U] }

case class Canonical(coef: Rational, sig: Map[RuntimeUnit.UnitType, Rational]):
    def *(that: Canonical): Canonical =
        val Canonical(rcoef, rsig) = that
        val s = Canonical
            .merge(sig, rsig)(_ + _)
            .filter { case (_, e) => e != Rational.const0 }
        Canonical(coef * rcoef, s)

    def /(that: Canonical): Canonical =
        val Canonical(rcoef, rsig) = that
        val rneg = rsig.map { case (u, e) => (u, -e) }
        val s = Canonical
            .merge(sig, rneg)(_ + _)
            .filter { case (_, e) => e != Rational.const0 }
        Canonical(coef / rcoef, s)

    def pow(e: Rational): Canonical =
        if (e == Rational.const0) Canonical.one
        else if (e == Rational.const1) this
        else
            val s = sig.map { case (u, ue) => (u, ue * e) }
            Canonical(coef.pow(e), s)

object Canonical:
    def merge[K, V](m1: Map[K, V], m2: Map[K, V])(f: (V, V) => V): Map[K, V] =
        val ki = m1.keySet & m2.keySet
        val r1 = m1.filter { case (k, _) => !ki.contains(k) }
        val r2 = m2.filter { case (k, _) => !ki.contains(k) }
        val ri = ki.map { k => (k, f(m1(k), m2(k))) }
        r1.concat(r2).concat(ri)

    private val s1 = HashMap.empty[RuntimeUnit.UnitType, Rational]
    private val r1 = Rational.const1

    val one: Canonical = Canonical(r1, s1)

    def apply(u: RuntimeUnit): Canonical =
        u match
            case RuntimeUnit.Mul(l, r)    => Canonical(l) * Canonical(r)
            case RuntimeUnit.Div(n, d)    => Canonical(n) / Canonical(d)
            case RuntimeUnit.Pow(b, e)    => Canonical(b).pow(e)
            case RuntimeUnit.UnitConst(c) => Canonical(c, s1)
            case u: RuntimeUnit.UnitType  => Canonical(r1, HashMap(u -> r1))

case class RuntimeQuantity[V](value: V, unit: RuntimeUnit)

object RuntimeQuantity:
    inline def apply[V, U](q: Quantity[V, U]): RuntimeQuantity[V] =
        RuntimeQuantity(q.value, RuntimeUnit.of[U])

extension [VL](ql: RuntimeQuantity[VL])
    inline def toQuantity[VR, UR](using
        crt: CoefficientRuntime,
        vi: ValueConversion[VL, Rational],
        vo: ValueConversion[Rational, VR]
    ): Either[String, Quantity[VR, UR]] =
        crt.coefficientRational[UR](ql.unit).map { coef =>
            vo(coef * vi(ql.value)).withUnit[UR]
        }

package syntax {
    extension [V](v: V)
        inline def withRuntimeUnit(u: RuntimeUnit): RuntimeQuantity[V] =
            RuntimeQuantity(v, u)
}

def runtimeCoefficient[V](uf: RuntimeUnit, ut: RuntimeUnit)(using
    crt: CoefficientRuntime,
    vc: ValueConversion[Rational, V]
): Either[String, V] =
    crt.coefficient[V](uf, ut)

abstract class CoefficientRuntime:
    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational]

    final inline def coefficientRational[UT](
        uf: RuntimeUnit
    ): Either[String, Rational] =
        infra.meta.crExpr[UT](this, uf)

    final def coefficient[V](uf: RuntimeUnit, ut: RuntimeUnit)(using
        vc: ValueConversion[Rational, V]
    ): Either[String, V] =
        this.coefficientRational(uf, ut).map(vc)

package conversion {
    abstract class RuntimeUnitConversion[V] extends (V => V)

    object RuntimeUnitConversion:
        def apply[V](uf: RuntimeUnit, ut: RuntimeUnit)(using
            crt: CoefficientRuntime,
            vi: ValueConversion[V, Rational],
            vo: ValueConversion[Rational, V]
        ): Either[String, RuntimeUnitConversion[V]] =
            crt.coefficientRational(uf, ut).map { coef =>
                new RuntimeUnitConversion[V] {
                    def apply(v: V): V = vo(coef * vi(v))
                }
            }
}

package conversion.runtimes {
    import scala.quoted.staging
    import coulomb.runtime.infra.meta

    // a CoefficientRuntime that leverages a staging compiler to do runtime magic
    // it will be possible to define other flavors of CoefficientRuntime that
    // do not require staging compiler and so can work with JS and Native
    class StagingCoefficientRuntime(using
        scmp: staging.Compiler
    ) extends CoefficientRuntime:
        def coefficientRational(
            uf: RuntimeUnit,
            ut: RuntimeUnit
        ): Either[String, Rational] =
            meta.coefStaging(uf, ut)(using scmp)

    object StagingCoefficientRuntime:
        def apply()(using
            scmp: staging.Compiler
        ): StagingCoefficientRuntime =
            new StagingCoefficientRuntime(using scmp)
}

package conversion.runtimes {
    class MappingCoefficientRuntime(
        baseUnits: Set[RuntimeUnit.UnitType],
        derivedUnits: Map[RuntimeUnit.UnitType, RuntimeUnit]
    ) extends CoefficientRuntime:
        def coefficientRational(
            uf: RuntimeUnit,
            ut: RuntimeUnit
        ): Either[String, Rational] =
            Left("No.")
}
