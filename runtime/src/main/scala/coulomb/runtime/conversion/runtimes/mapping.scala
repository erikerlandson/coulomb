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

package coulomb.runtime.conversion.runtimes.mapping

import scala.collection.immutable.HashMap

import coulomb.runtime.*
import coulomb.rational.Rational

class MappingCoefficientRuntime(
    baseUnits: Set[RuntimeUnit.UnitType],
    derivedUnits: Map[RuntimeUnit.UnitType, RuntimeUnit]
) extends CoefficientRuntime:
    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational] =
        canonical(ut / uf).flatMap { qcan =>
            val Canonical(coef, sig) = qcan
            if (sig.isEmpty) Right(coef) else Left("No.")
        }

    def canonical(u: RuntimeUnit): Either[String, Canonical] =
        u match
            case RuntimeUnit.Mul(l, r) => canonical(l) * canonical(r)
            case RuntimeUnit.Div(n, d) => canonical(n) / canonical(d)
            case RuntimeUnit.Pow(b, e) => canonical(b).pow(e)
            case RuntimeUnit.UnitConst(c) =>
                Right(Canonical(c, Canonical.one.sig))
            case u: RuntimeUnit.UnitType if (baseUnits.contains(u)) =>
                Right(Canonical(Rational.const1, HashMap(u -> Rational.const1)))
            case u: RuntimeUnit.UnitType if (derivedUnits.contains(u)) =>
                canonical(derivedUnits(u))
            case _ => Left(s"canonical: unrecognized unit $u")

extension (lhs: Either[String, Canonical])
    def *(rhs: Either[String, Canonical]): Either[String, Canonical] =
        for { l <- lhs; r <- rhs } yield l * r
    def /(rhs: Either[String, Canonical]): Either[String, Canonical] =
        for { l <- lhs; r <- rhs } yield l / r
    def pow(e: Rational): Either[String, Canonical] =
        lhs.map { l => l.pow(e) }

object MappingCoefficientRuntime:
    final type &:[H, T]
    final type TNil

    inline def of[UTL]: MappingCoefficientRuntime = ${ meta.ofUTL[UTL] }

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

    val one: Canonical = Canonical(
        Rational.const1,
        HashMap.empty[RuntimeUnit.UnitType, Rational]
    )

object meta:
    import scala.quoted.*
    import scala.util.{Try, Success, Failure}
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.infra.meta.{*, given}
    import coulomb.runtime.infra.meta.{*, given}

    def ofUTL[UTL](using Quotes, Type[UTL]): Expr[MappingCoefficientRuntime] =
        val bu = Set.empty[RuntimeUnit.UnitType]
        val du = Map.empty[RuntimeUnit.UnitType, RuntimeUnit]
        '{ new MappingCoefficientRuntime(${ Expr(bu) }, ${ Expr(du) }) }
