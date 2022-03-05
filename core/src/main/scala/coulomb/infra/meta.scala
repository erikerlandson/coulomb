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
import coulomb.ops.*

final type SNil
final type %:[Head, Tail]

object meta:
    import scala.quoted.*
    import scala.language.implicitConversions

    given ctx_RationalToExpr: ToExpr[Rational] with
        def apply(r: Rational)(using Quotes): Expr[Rational] = r match
            // Rational(1) is a useful special case to have predefined
            // could we get clever with some kind of expression caching/sharing here?
            case v if (v == 1) => '{ Rational.const1 }
            case _ => '{ Rational(${Expr(r.n)}, ${Expr(r.d)}) }

    def teToRational[E](using Quotes, Type[E]): Expr[Rational] =
        import quotes.reflect.*
        val rationalTE(v) = TypeRepr.of[E]
        Expr(v)

    def teToBigInt[E](using Quotes, Type[E]): Expr[BigInt] =
        import quotes.reflect.*
        val bigintTE(v) = TypeRepr.of[E]
        Expr(v)

    def teToDouble[E](using Quotes, Type[E]): Expr[Double] =
        import quotes.reflect.*
        val rationalTE(v) = TypeRepr.of[E]
        Expr(v.toDouble)

    def teToNonNegInt[E](using Quotes, Type[E]): Expr[coulomb.rational.typeexpr.NonNegInt[E]] =
        import quotes.reflect.*
        val rationalTE(v) = TypeRepr.of[E]
        if ((v.d == 1) && (v.n >= 0) && (v.n.isValidInt)) then
            '{ new coulomb.rational.typeexpr.NonNegInt[E] { val value = ${Expr(v.n.toInt)} } }
        else
            report.error(s"type expr ${typestr(TypeRepr.of[E])} is not a non-negative Int")
            '{ new coulomb.rational.typeexpr.NonNegInt[E] { val value = 0 } }

    object rationalTE:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[Rational] =
            import quotes.reflect.*
            tr match
                case AppliedType(op, List(rationalTE(n), rationalTE(d))) if (op =:= TypeRepr.of[/]) =>
                    Some(n / d)
                case AppliedType(op, List(rationalTE(n), rationalTE(d))) if (op =:= TypeRepr.of[*]) =>
                    Some(n * d)
                case AppliedType(op, List(rationalTE(b), bigintTE(e))) if (op =:= TypeRepr.of[^]) && e.isValidInt =>
                    Some(b.pow(e.toInt))
                case bigintTE(v) => Some(Rational(v, 1))
                case ConstantType(DoubleConstant(v)) => Some(Rational(v))
                case ConstantType(FloatConstant(v)) => Some(Rational(v))
                case _ => None

        def apply(using Quotes)(v: Rational): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            if (v.d == 1) then bigintTE(v.n)
            else TypeRepr.of[/].appliedTo(List(bigintTE(v.n), bigintTE(v.d)))
 
    object bigintTE:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[BigInt] =
            import quotes.reflect.*
            tr match
                case ConstantType(IntConstant(v)) => Some(BigInt(v))
                case ConstantType(LongConstant(v)) => Some(BigInt(v))
                case ConstantType(StringConstant(v)) => Some(BigInt(v))
                case _ => None

        def apply(using Quotes)(v: BigInt): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            v match
                case _ if v.isValidInt => ConstantType(IntConstant(v.toInt))
                case _ if v.isValidLong => ConstantType(LongConstant(v.toLong))
                case _ => ConstantType(StringConstant(v.toString()))

    def coefficient[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Rational] =
        import quotes.reflect.*
        // this call will fail if no coefficient exists, which means that
        // U1 and U2 are not convertable
        val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
        Expr(c)

    def coef(using Quotes)(u1: quotes.reflect.TypeRepr, u2: quotes.reflect.TypeRepr): Rational =
        import quotes.reflect.*
        if (u1 =:= u2) then
            // confirm that the type has a defined canonical signature, or fail
            val _ = cansig(u1)
            // the coefficient between two identical unit expression types is always exactly 1
            Rational.const1
        // the fundamental algorithmic unit analysis criterion:
        // http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/
        val (rcoef, rsig) = cansig(TypeRepr.of[/].appliedTo(List(u1, u2)))
        if (rsig =:= TypeRepr.of[SNil]) then rcoef else
            report.error(s"unit type ${typestr(u1)} not convertable to ${typestr(u2)}")
            Rational.const0

    // returns tuple: (expr-for-coef, type-of-Res)
    def cansig(using Quotes)(u: quotes.reflect.TypeRepr):
            (Rational, quotes.reflect.TypeRepr) =
        import quotes.reflect.*
        // if this encounters a unit type pattern it cannot expand to a canonical signature,
        // at any level, it raises a compile-time error such that the context parameter search fails.
        u match
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
                val rationalTE(e) = p
                if (e == 0) (Rational.const1, signil())
                else if (e == 1) (bcoef, bsig)
                else if (e.n.isValidInt && e.d.isValidInt)
                    val ucoef = if (e.d == 1) bcoef.pow(e.n.toInt)
                                else bcoef.pow(e.n.toInt).root(e.d.toInt)
                    val usig = unifyPow(p, bsig)
                    (ucoef, usig)
                else
                    { report.error(s"bad exponent in cansig: $u"); csErr }
            case unitless() => (Rational.const1, signil())
            case unitconst(c) => (c, signil())
            case baseunit() => (Rational.const1, sigcons(u, Rational.const1, signil()))
            case derivedunit(ucoef, usig) => (ucoef, usig)
            case _ if (!strictunitexprs) =>
                // we consider any other type for "promotion" to base-unit only if
                // it does not match the strict unit expression forms above, and
                // if the strict unit expression policy has not been enabled
                (Rational.const1, sigcons(u, Rational.const1, signil()))
            case _ => { report.error(s"unknown unit expression in cansig: $u"); csErr }

    def stdsig(using Quotes)(u: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        // if this encounters a unit type pattern it cannot expand,
        // at any level, it raises a compile-time error such that the context parameter search fails.
        u match
            // traverse down the operator types first, since that can be done without
            // any attempts to look up context variables for BaseUnit and DerivedUnit,
            // which only happen at the leaves of expressions
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[*]) =>
                unifyOp(stdsig(lu), stdsig(ru), _ + _)
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[/]) =>
                unifyOp(stdsig(lu), stdsig(ru), _ - _)
            case AppliedType(op, List(b, p)) if (op =:= TypeRepr.of[^]) =>
                val rationalTE(e) = p
                if (e == 0) signil()
                else if (e == 1) stdsig(b)
                else unifyPow(p, stdsig(b))
            case unitless() => signil()
            case unitconst(c) => sigcons(rationalTE(c), Rational.const1, signil())
            case baseunit() => sigcons(u, Rational.const1, signil())
            case derivedunit(_, _) => sigcons(u, Rational.const1, signil())
            case _ if (!strictunitexprs) =>
                // we consider any other type for "promotion" to base-unit only if
                // it does not match the strict unit expression forms above, and
                // if the strict unit expression policy has not been enabled
                sigcons(u, Rational.const1, signil())
            case _ => { report.error(s"unknown unit expression in stdsig: $u"); signil() }

    def sortsig(using Quotes)(sig: quotes.reflect.TypeRepr):
            (quotes.reflect.TypeRepr, quotes.reflect.TypeRepr) =
        import quotes.reflect.*
        sig match
            case signil() => (signil(), signil())
            case sigcons(u, p, tail) =>
                val (nsig, dsig) = sortsig(tail)
                if (p > 0) (sigcons(u, p, nsig), dsig) else (nsig, sigcons(u, -p, dsig))
            case _ => { report.error(s"unknown unit expression in stdsig: $sig"); (signil(), signil()) }

    def simplifiedUnit[U](using Quotes, Type[U]): Expr[SimplifiedUnit[U]] =
        import quotes.reflect.*
        val usig = stdsig(TypeRepr.of[U])
        val (un, ud) = sortsig(usig)
        finRU(un, ud).asType match
            case '[uo] => '{ new SimplifiedUnit[U] { type UO = uo } }

    def finRU(using Quotes)(un: quotes.reflect.TypeRepr, ud: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        (uProd(un), uProd(ud)) match
            case (unitless(), unitless()) => TypeRepr.of[1]
            case (n, unitless()) => n
            case (unitless(), d) => TypeRepr.of[/].appliedTo(List(TypeRepr.of[1], d))
            case (n, d) => TypeRepr.of[/].appliedTo(List(n, d))

    def uProd(using Quotes)(u: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        u match
            case signil() => TypeRepr.of[1]
            case sigcons(u, p, signil()) => uTerm(u, p)
            case sigcons(u1, p1, sigcons(u2, p2, signil())) =>
                TypeRepr.of[*].appliedTo(List(uTerm(u1, p1), uTerm(u2, p2)))
            case sigcons(u, p, tail) =>
                TypeRepr.of[*].appliedTo(List(uTerm(u, p), uProd(tail)))
            case _ => { report.error(s"unknown unit expression in uProd: $u"); TypeRepr.of[Nothing] }

    def uTerm(using Quotes)(u: quotes.reflect.TypeRepr, p: Rational): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        if (p == 1) u else TypeRepr.of[^].appliedTo(List(u, rationalTE(p)))

    def strictunitexprs(using Quotes): Boolean =
        import quotes.reflect.*
        Implicits.search(TypeRepr.of[coulomb.policy.StrictUnitExpressions]) match
                case _: ImplicitSearchSuccess => true
                case _ => false

    object unitless:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u =:= quotes.reflect.TypeRepr.of[1]

    object unitconst:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[Rational] =
            import quotes.reflect.*
            u match
                case rationalTE(v) => Some(v)
                case _ => None

    object baseunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[BaseUnit].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess => true
                case _ => false

    object derivedunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[(Rational, quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess =>
                    val AppliedType(_, List(_, d, c, _, _)) = iss.tree.tpe.baseType(TypeRepr.of[DerivedUnit].typeSymbol)
                    val rationalTE(coef) = c
                    val (dcoef, dsig) = cansig(d)
                    Some((coef * dcoef, dsig))
                case _ => None

    def csErr(using Quotes): (Rational, quotes.reflect.TypeRepr) =
        (Rational.const0, quotes.reflect.TypeRepr.of[Nothing])

    // keep this for reference
    /*
    def summonString[T](using Quotes, Type[T]): String =
        import quotes.reflect.*
        Expr.summon[T] match
            case None => "None"
            case Some(e) => s"${e.show}   ${e.asTerm.show(using Printer.TreeStructure)}"
i   */

    def unifyOp(using Quotes)(
            sig1: quotes.reflect.TypeRepr, sig2: quotes.reflect.TypeRepr,
            op: (Rational, Rational) => Rational): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        sig2 match
            case signil() => sig1
            case sigcons(u, e, tail) => unifyOp(insertTerm(u, e, sig1, op), tail, op)
            case _ => { report.error(s"Unsupported type ${sig2.show}"); TypeRepr.of[Nothing] }

    def insertTerm(using Quotes)(
            u: quotes.reflect.TypeRepr, e: Rational,
            sig: quotes.reflect.TypeRepr,
            op: (Rational, Rational) => Rational): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        sig match
            case signil() => sigcons(u, op(Rational(0), e), signil())
            case sigcons(u0, e0, tail) if (u =:= u0) => 
                val ei = op(e0, e)
                if (ei == 0) tail else sigcons(u, ei, tail)
            case sigcons(u0, e0, tail) => sigcons(u0, e0, insertTerm(u, e, tail, op))
            case _ => { report.error(s"Unsupported type ${sig.show}"); TypeRepr.of[Nothing] }

    def unifyPow(using Quotes)(power: quotes.reflect.TypeRepr, sig: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
        val rationalTE(e) = power
        if (e == 0) signil() else unifyPowTerm(e, sig)

    def unifyPowTerm(using Quotes)(e: Rational, sig: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        sig match
            case signil() => signil()
            case sigcons(u, e0, tail) => sigcons(u, e0 * e, unifyPowTerm(e, tail))
            case _ => { report.error(s"Unsupported type ${sig.show}"); TypeRepr.of[Nothing] }

    object signil:
        def apply(using Quotes)(): quotes.reflect.TypeRepr = quotes.reflect.TypeRepr.of[SNil]

        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Boolean =
            tr =:= quotes.reflect.TypeRepr.of[SNil]

    object sigcons:
        def apply(using Quotes)(u: quotes.reflect.TypeRepr, e: Rational, tail: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
            sctc().appliedTo(List(t2tc().appliedTo(List(u, rationalTE(e))), tail))

        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[(quotes.reflect.TypeRepr, Rational, quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            tr match
                case AppliedType(sctc(), List(AppliedType(t2tc(), List(u, rationalTE(e))), tail)) =>
                    Some((u, e, tail))
                case _ => None

    object sctc:
        def apply(using Quotes)(): quotes.reflect.TypeRepr = quotes.reflect.TypeRepr.of[%:]

        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Boolean =
            tr =:= quotes.reflect.TypeRepr.of[%:]

    object t2tc:
        def apply(using Quotes)(): quotes.reflect.TypeRepr = quotes.reflect.TypeRepr.of[Tuple2]

        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Boolean =
            tr =:= quotes.reflect.TypeRepr.of[Tuple2]

    object strlt:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[String] =
             import quotes.reflect.*
             tr match
                case ConstantType(StringConstant(v)) => Some(v)
                case _ => None

    object typeDouble:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u =:= quotes.reflect.TypeRepr.of[Double]

    object typeFloat:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u =:= quotes.reflect.TypeRepr.of[Float]

    object typeInt:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u =:= quotes.reflect.TypeRepr.of[Int]

    object typeLong:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u =:= quotes.reflect.TypeRepr.of[Long]

    object typeString:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u =:= quotes.reflect.TypeRepr.of[String]

    def typestr(using Quotes)(t: quotes.reflect.TypeRepr): String =
        import quotes.reflect.*
        def work(tr: TypeRepr): String = tr match
            case AppliedType(op, List(lhs, rhs)) if op =:= TypeRepr.of[*] =>
                s"(${work(lhs)} * ${work(rhs)})"
            case AppliedType(op, List(lhs, rhs)) if op =:= TypeRepr.of[/] =>
                s"(${work(lhs)} / ${work(rhs)})"
            case AppliedType(op, List(lhs, rhs)) if op =:= TypeRepr.of[^] =>
                s"(${work(lhs)} ^ ${work(rhs)})"
            case AppliedType(tc, ta) =>
                val tcn = tc.typeSymbol.name
                val as = ta.map(work)
                if (as.length == 0) tcn else
                    tcn + "[" + as.mkString(",") + "]"
            case t => t.typeSymbol.name
        val ts = work(t.dealias)
        ts
