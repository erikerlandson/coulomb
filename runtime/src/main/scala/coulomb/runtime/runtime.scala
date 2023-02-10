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
