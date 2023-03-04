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

package coulomb.conversion.runtimes.mapping

import scala.collection.immutable.HashMap

import coulomb.*
import coulomb.rational.Rational

sealed abstract class MappingCoefficientRuntime extends CoefficientRuntime:
    // can protected members change and preserve binary compatibility?
    protected def base: Set[RuntimeUnit.UnitType]
    protected def derived: Map[RuntimeUnit.UnitType, RuntimeUnit]

    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational] =
        canonical(uf / ut).flatMap { qcan =>
            val Canonical(coef, sig) = qcan
            if (sig.isEmpty) Right(coef)
            else Left(s"non-convertible units: $uf, $ut")
        }

    def canonical(u: RuntimeUnit): Either[String, Canonical] =
        u match
            case RuntimeUnit.Mul(l, r) => canonical(l) * canonical(r)
            case RuntimeUnit.Div(n, d) => canonical(n) / canonical(d)
            case RuntimeUnit.Pow(b, e) => canonical(b).pow(e)
            case RuntimeUnit.UnitConst(c) =>
                Right(Canonical(c, Canonical.one.sig))
            case u: RuntimeUnit.UnitType if (base.contains(u)) =>
                Right(Canonical(Rational.const1, HashMap(u -> Rational.const1)))
            case u: RuntimeUnit.UnitType if (derived.contains(u)) =>
                canonical(derived(u))
            case _ => Left(s"canonical: unrecognized unit $u")

extension (lhs: Either[String, Canonical])
    def *(rhs: Either[String, Canonical]): Either[String, Canonical] =
        for { l <- lhs; r <- rhs } yield l * r
    def /(rhs: Either[String, Canonical]): Either[String, Canonical] =
        for { l <- lhs; r <- rhs } yield l / r
    def pow(e: Rational): Either[String, Canonical] =
        lhs.map { l => l.pow(e) }

object MappingCoefficientRuntime:
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
    import coulomb.infra.runtime.meta.{*, given}

    import coulomb.syntax.typelist.{TNil, &:}

    def ofUTL[UTL](using Quotes, Type[UTL]): Expr[MappingCoefficientRuntime] =
        import quotes.reflect.*
        val (bu, du) = utlClosure(typeReprList(TypeRepr.of[UTL]))
        '{
            new MappingCoefficientRuntime:
                protected val base = ${ Expr(bu) }
                protected val derived = ${ Expr(du) }
        }

    def utlClosure(using Quotes)(
        utl: List[quotes.reflect.TypeRepr]
    ): (Set[RuntimeUnit.UnitType], Map[RuntimeUnit.UnitType, RuntimeUnit]) =
        import quotes.reflect.*
        utl match
            case Nil => emptyClosure
            case head :: tail =>
                val (tbu, tdu) = utlClosure(tail)
                val (hbu, hdu) = utClosure(head)
                (hbu ++ tbu, hdu ++ tdu)

    def utClosure(using Quotes)(
        tr: quotes.reflect.TypeRepr
    ): (Set[RuntimeUnit.UnitType], Map[RuntimeUnit.UnitType, RuntimeUnit]) =
        import quotes.reflect.*
        tr match
            case ConstantType(StringConstant(mname)) =>
                utlClosure(moduleUnits(mname))
            case AppliedType(op, List(lu, ru))
                if (op =:= TypeRepr.of[coulomb.`*`]) =>
                val (lbu, ldu) = utClosure(lu)
                val (rbu, rdu) = utClosure(ru)
                (lbu ++ rbu, ldu ++ rdu)
            case AppliedType(op, List(lu, ru))
                if (op =:= TypeRepr.of[coulomb.`/`]) =>
                val (lbu, ldu) = utClosure(lu)
                val (rbu, rdu) = utClosure(ru)
                (lbu ++ rbu, ldu ++ rdu)
            case AppliedType(op, List(b, _))
                if (op =:= TypeRepr.of[coulomb.`^`]) =>
                utClosure(b)
            case rationalTE(v) =>
                emptyClosure
            case ut =>
                ut match
                    case baseunit() =>
                        (Set(typeReprUT(ut)), emptyMap)
                    case derivedunitTR(dtr) =>
                        val AppliedType(_, List(_, d, _, _)) = dtr: @unchecked
                        val (dbu, ddu) = utClosure(d)
                        (
                            dbu,
                            ddu + (typeReprUT(ut) -> typeReprRTU(d))
                        )
                    case _ =>
                        report.errorAndAbort(
                            s"closureUT: bad unit type ${ut.show}"
                        )
                        null.asInstanceOf[Nothing]

    def moduleUnits(using Quotes)(
        mname: String
    ): List[quotes.reflect.TypeRepr] =
        import quotes.reflect.*
        val msym = fqFieldSymbol(mname)
        if (!msym.flags.is(Flags.Module))
            report.errorAndAbort(s"$mname is not a module")
        val usyms = msym.typeMembers.filter(isUnitSym)
        usyms.map(_.typeRef)

    def isUnitSym(using Quotes)(sym: quotes.reflect.Symbol): Boolean =
        sym.typeRef match
            case baseunit()       => true
            case derivedunitTR(_) => true
            case _                => false

    private val emptyMap =
        Map.empty[RuntimeUnit.UnitType, RuntimeUnit]

    private val emptyClosure =
        (
            Set.empty[RuntimeUnit.UnitType],
            emptyMap
        )
