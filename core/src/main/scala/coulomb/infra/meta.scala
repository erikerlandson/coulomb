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

package coulomb.infra

import coulomb.rational.Rational
import coulomb.*
import coulomb.define.*

object meta:
    import scala.unchecked
    import scala.quoted.*
    import scala.language.implicitConversions

    given ctx_RationalToExpr: ToExpr[Rational] with
        def apply(r: Rational)(using Quotes): Expr[Rational] = r match
            // Rational(1) is a useful special case to have predefined
            // could we get clever with some kind of expression caching/sharing here?
            case v if (v == 1) => '{ Rational.const1 }
            case _             => '{ Rational(${ Expr(r.n) }, ${ Expr(r.d) }) }

    sealed class SigMode
    object SigMode:
        /**
         * Canonical mode yields signatures fully expand down to base units. Its
         * primary purpose is to compute coefficients of unit conversion, or
         * determine whether two unit types are convertable.
         */
        case object Canonical extends SigMode

        /**
         * Simplify mode is for constructing operator output types. It does not
         * expand derived units, and respects type aliases.
         */
        case object Simplify extends SigMode

        /**
         * Constant mode is for extracting constant coefficents from derived
         * unit definitions. It is used by the physical-constants library.
         */
        case object Constant extends SigMode

    object rationalTE:
        def unapply(using Quotes)(
            tr: quotes.reflect.TypeRepr
        ): Option[Rational] =
            import quotes.reflect.*
            tr match
                case AppliedType(op, List(rationalTE(n), rationalTE(d)))
                    if (op =:= TypeRepr.of[/]) =>
                    Some(n / d)
                case AppliedType(op, List(rationalTE(n), rationalTE(d)))
                    if (op =:= TypeRepr.of[*]) =>
                    Some(n * d)
                case AppliedType(op, List(rationalTE(b), bigintTE(e)))
                    if (op =:= TypeRepr.of[^]) && e.isValidInt =>
                    Some(b.pow(e.toInt))
                case bigintTE(v)                     => Some(Rational(v, 1))
                case ConstantType(DoubleConstant(v)) => Some(Rational(v))
                case ConstantType(FloatConstant(v))  => Some(Rational(v))
                case _                               => None

        def apply(using Quotes)(v: Rational): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            if (v.d == 1) then bigintTE(v.n)
            else TypeRepr.of[/].appliedTo(List(bigintTE(v.n), bigintTE(v.d)))

    object bigintTE:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[BigInt] =
            import quotes.reflect.*
            tr match
                case ConstantType(IntConstant(v))    => Some(BigInt(v))
                case ConstantType(LongConstant(v))   => Some(BigInt(v))
                case ConstantType(StringConstant(v)) =>
                    scala.util.Try { BigInt(v) } match
                        case scala.util.Success(b) => Some(b)
                        case _ => None
                case _                               => None

        def apply(using Quotes)(v: BigInt): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            v match
                case _ if v.isValidInt  => ConstantType(IntConstant(v.toInt))
                case _ if v.isValidLong => ConstantType(LongConstant(v.toLong))
                case _                  => ConstantType(StringConstant(v.toString()))

    def coefficient[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Rational] =
        import quotes.reflect.*
        // this call will fail if no coefficient exists, which means that
        // U1 and U2 are not convertable
        val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
        Expr(c)

    def coef(using
        Quotes
    )(u1: quotes.reflect.TypeRepr, u2: quotes.reflect.TypeRepr): Rational =
        import quotes.reflect.*
        // the fundamental algorithmic unit analysis criterion:
        // http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/
        given sigmode: SigMode = SigMode.Canonical
        val (rcoef, rsig) = cansig(TypeRepr.of[/].appliedTo(List(u1, u2)))
        if (rsig == Nil) then rcoef
        else
            report.error(
                s"unit type ${typestr(u1)} not convertable to ${typestr(u2)}"
            )
            Rational.const0

    def offset(using
        Quotes
    )(u: quotes.reflect.TypeRepr, b: quotes.reflect.TypeRepr): Rational =
        import quotes.reflect.*
        // given sigmode: SigMode = SigMode.Simplify
        u match
            case deltaunit(offset, d) if convertible(d, b) => offset
            case _ if convertible(u, b)                    => Rational.const0
            case _ =>
                report.error(s"bad DeltaUnit in offset: ${typestr(u)}")
                Rational.const0

    def convertible(using
        Quotes
    )(u1: quotes.reflect.TypeRepr, u2: quotes.reflect.TypeRepr): Boolean =
        import quotes.reflect.*
        given sigmode: SigMode = SigMode.Canonical
        val (_, rsig) = cansig(TypeRepr.of[/].appliedTo(List(u1, u2)))
        rsig == Nil

    // returns tuple: (expr-for-coef, type-of-Res)
    def cansig(using qq: Quotes, mode: SigMode)(
        uu: quotes.reflect.TypeRepr
    ): (Rational, List[(quotes.reflect.TypeRepr, Rational)]) =
        import quotes.reflect.*
        val u = mode match
            // in simplification mode we respect type aliases
            case SigMode.Simplify => uu
            case _                => uu.dealias
        // if this encounters a unit type pattern it cannot expand to a canonical signature,
        // at any level, it raises a compile-time error such that the context parameter search fails.
        u match
            // identify embedded coefficients (includes '1' aka unitless)
            case unitconst(c) =>
                mode match
                    case SigMode.Simplify =>
                        // in simplify mode we preserve constants in the signature
                        if (c == 1) (Rational.const1, Nil)
                        else (Rational.const1, (u, Rational.const1) :: Nil)
                    case _ => (c, Nil)
            // traverse down the operator types first, since that can be done without
            // any attempts to look up context variables for BaseUnit and DerivedUnit,
            // which only happen at the leaves of expressions
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[*]) =>
                val (lcoef, lsig) = cansig(lu)
                val (rcoef, rsig) = cansig(ru)
                val usig = unifyOp(lsig, rsig, _ + _)
                (lcoef * rcoef, usig)
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[/]) =>
                val (lcoef, lsig) = cansig(lu)
                val (rcoef, rsig) = cansig(ru)
                val usig = unifyOp(lsig, rsig, _ - _)
                (lcoef / rcoef, usig)
            case AppliedType(op, List(b, p)) if (op =:= TypeRepr.of[^]) =>
                val (bcoef, bsig) = cansig(b)
                val e = p match
                    case rationalTE(r) => r
                    case _ =>
                        report.error("improper unit exponent")
                        Rational.const0
                if (e == 0) (Rational.const1, Nil)
                else if (e == 1) (bcoef, bsig)
                else if (e.n.isValidInt && e.d.isValidInt)
                    val ucoef =
                        if (e.d == 1) bcoef.pow(e.n.toInt)
                        else bcoef.pow(e.n.toInt).root(e.d.toInt)
                    val usig = unifyPow(e, bsig)
                    (ucoef, usig)
                else
                    report.error(s"bad exponent in cansig: ${typestr(u)}")
                    (Rational.const0, Nil)
            case baseunit() => (Rational.const1, (u, Rational.const1) :: Nil)
            case derivedunit(ucoef, usig) =>
                mode match
                    case SigMode.Canonical => (ucoef, usig)
                    case _                 => (Rational.const1, (u, Rational.const1) :: Nil)
            case _ =>
                // treat any other type as if it were a BaseUnit
                (Rational.const1, (u, Rational.const1) :: Nil)

    def sortsig(using Quotes)(
        sig: List[(quotes.reflect.TypeRepr, Rational)]
    ): (
        List[(quotes.reflect.TypeRepr, Rational)],
        List[(quotes.reflect.TypeRepr, Rational)]
    ) =
        sig match
            case Nil => (Nil, Nil)
            case (u, e) :: tail =>
                val (nsig, dsig) = sortsig(tail)
                if (e > 0) ((u, e) :: nsig, dsig) else (nsig, (u, -e) :: dsig)

    def simplify(using Quotes)(
        u: quotes.reflect.TypeRepr
    ): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        given sigmode: SigMode = SigMode.Simplify
        simplifysig(cansig(u)._2)

    def simplifysig(using Quotes)(
        sig: List[(quotes.reflect.TypeRepr, Rational)]
    ): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        val (un, ud) = sortsig(sig)
        (uProd(un), uProd(ud)) match
            case (unitconst1(), unitconst1()) => TypeRepr.of[1]
            case (n, unitconst1())            => n
            case (unitconst1(), d) =>
                TypeRepr.of[/].appliedTo(List(TypeRepr.of[1], d))
            case (n, d) => TypeRepr.of[/].appliedTo(List(n, d))

    def uProd(using Quotes)(
        sig: List[(quotes.reflect.TypeRepr, Rational)]
    ): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        sig match
            case Nil           => TypeRepr.of[1]
            case (u, e) :: Nil => uTerm(u, e)
            case (u1, e1) :: (u2, e2) :: Nil =>
                TypeRepr.of[*].appliedTo(List(uTerm(u1, e1), uTerm(u2, e2)))
            case (u, e) :: tail =>
                TypeRepr.of[*].appliedTo(List(uTerm(u, e), uProd(tail)))

    def uTerm(using
        Quotes
    )(u: quotes.reflect.TypeRepr, p: Rational): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        if (p == 1) u else TypeRepr.of[^].appliedTo(List(u, rationalTE(p)))

    object unitconst1:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u match
                case rationalTE(v) if (v == 1) => true
                case _                         => false

    object unitconst:
        def unapply(using Quotes)(
            u: quotes.reflect.TypeRepr
        ): Option[Rational] =
            u match
                case rationalTE(v) => Some(v)
                case _             => None

    object baseunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u match
                case baseunitTR(_) => true
                case _             => false

    object derivedunit:
        def unapply(using qq: Quotes, mode: SigMode)(
            u: quotes.reflect.TypeRepr
        ): Option[(Rational, List[(quotes.reflect.TypeRepr, Rational)])] =
            import quotes.reflect.*
            u match
                case derivedunitTR(dtr) =>
                    mode match
                        case SigMode.Simplify =>
                            // don't expand the signature definition in simplify mode
                            Some((Rational.const1, (u, Rational.const1) :: Nil))
                        case _ =>
                            val AppliedType(_, List(_, d, _, _)) =
                                dtr: @unchecked
                            Some(cansig(d))
                case _ => None

    object baseunitTR:
        def unapply(using Quotes)(
            u: quotes.reflect.TypeRepr
        ): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            Implicits.search(
                TypeRepr
                    .of[BaseUnit]
                    .appliedTo(List(u, TypeBounds.empty, TypeBounds.empty))
            ) match
                case iss: ImplicitSearchSuccess =>
                    Some(
                        iss.tree.tpe.baseType(
                            TypeRepr.of[BaseUnit].typeSymbol
                        )
                    )
                case _ => None

    object derivedunitTR:
        def unapply(using Quotes)(
            u: quotes.reflect.TypeRepr
        ): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            Implicits.search(
                TypeRepr
                    .of[DerivedUnit]
                    .appliedTo(
                        List(
                            u,
                            TypeBounds.empty,
                            TypeBounds.empty,
                            TypeBounds.empty
                        )
                    )
            ) match
                case iss: ImplicitSearchSuccess =>
                    Some(
                        iss.tree.tpe.baseType(
                            TypeRepr.of[DerivedUnit].typeSymbol
                        )
                    )
                case _ => None

    object deltaunit:
        def unapply(using Quotes)(
            u: quotes.reflect.TypeRepr
        ): Option[(Rational, quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            Implicits.search(
                TypeRepr
                    .of[DeltaUnit]
                    .appliedTo(
                        List(
                            u,
                            TypeBounds.empty,
                            TypeBounds.empty,
                            TypeBounds.empty,
                            TypeBounds.empty
                        )
                    )
            ) match
                case iss: ImplicitSearchSuccess =>
                    val AppliedType(_, List(_, d, o, _, _)) =
                        iss.tree.tpe.baseType(
                            TypeRepr.of[DeltaUnit].typeSymbol
                        ): @unchecked
                    val rationalTE(offset) = o: @unchecked
                    Some((offset, d))
                case _ => None

    def unifyOp(using Quotes)(
        sig1: List[(quotes.reflect.TypeRepr, Rational)],
        sig2: List[(quotes.reflect.TypeRepr, Rational)],
        op: (Rational, Rational) => Rational
    ): List[(quotes.reflect.TypeRepr, Rational)] =
        sig2 match
            case Nil            => sig1
            case (u, e) :: tail => unifyOp(insertTerm(u, e, sig1, op), tail, op)

    def insertTerm(using Quotes)(
        u: quotes.reflect.TypeRepr,
        e: Rational,
        sig: List[(quotes.reflect.TypeRepr, Rational)],
        op: (Rational, Rational) => Rational
    ): List[(quotes.reflect.TypeRepr, Rational)] =
        sig match
            case Nil => (u, op(Rational.const0, e)) :: Nil
            case (u0, e0) :: tail if (u =:= u0) =>
                val ei = op(e0, e)
                if (ei == Rational.const0) tail else (u, ei) :: tail
            case (u0, e0) :: tail => (u0, e0) :: insertTerm(u, e, tail, op)

    def unifyPow(using Quotes)(
        e: Rational,
        sig: List[(quotes.reflect.TypeRepr, Rational)]
    ): List[(quotes.reflect.TypeRepr, Rational)] =
        sig match
            case _ if (e == Rational.const0) => Nil
            case Nil                         => Nil
            case (u, e0) :: tail             => (u, e0 * e) :: unifyPow(e, tail)

    def typeReprList(using Quotes)(
        tlist: quotes.reflect.TypeRepr
    ): List[quotes.reflect.TypeRepr] =
        import quotes.reflect.*
        tlist match
            case tnil if (tnil =:= TypeRepr.of[EmptyTuple]) => Nil
            case AppliedType(t, List(head, tail)) if (t =:= TypeRepr.of[*:]) =>
                head :: typeReprList(tail)
            case _ =>
                report.errorAndAbort(
                    s"typeReprList: bad type list ${tlist.show}"
                )
                null.asInstanceOf[Nothing]

    def typestr(using Quotes)(t: quotes.reflect.TypeRepr): String =
        // The policy goal here is that type aliases are never expanded.
        typestring(t, false)

    def typestring(using
        Quotes
    )(t: quotes.reflect.TypeRepr, dealias: Boolean): String =
        import quotes.reflect.*
        def work(trp: TypeRepr): String =
            val tr = if (dealias) trp.dealias else trp
            tr match
                case typealias(_) => tr.typeSymbol.name
                case ConstantType(IntConstant(v)) => s"$v"
                case ConstantType(DoubleConstant(v)) => s"$v"
                case ConstantType(StringConstant(v)) => s"\"$v\""
                case unitconst(v) => s"$v"
                case AppliedType(op, List(lhs, rhs)) if op =:= TypeRepr.of[*] =>
                    s"(${work(lhs)} * ${work(rhs)})"
                case AppliedType(op, List(lhs, rhs)) if op =:= TypeRepr.of[/] =>
                    s"(${work(lhs)} / ${work(rhs)})"
                case AppliedType(op, List(lhs, rhs)) if op =:= TypeRepr.of[^] =>
                    s"(${work(lhs)} ^ ${work(rhs)})"
                case AppliedType(tc, ta) =>
                    val tcn = tc.typeSymbol.name
                    val as = ta.map(work)
                    if (as.length == 0) tcn
                    else
                        tcn + "[" + as.mkString(",") + "]"
                case t => t.typeSymbol.name
        work(t)

    object typealias:
        def unapply(using Quotes)(
            t: quotes.reflect.TypeRepr
        ): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            val d = t.dealias
            // "=:=" doesn't work here, it will test 'true' even if dealiasing happened
            if (d.typeSymbol.name == t.typeSymbol.name) None else Some(d)
