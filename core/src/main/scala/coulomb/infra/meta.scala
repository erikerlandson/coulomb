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

import java.lang.{ClassLoader, Package}

def allDefinedPackages(loader: ClassLoader): Vector[Package] =
    if (loader == null)
        Vector.empty[Package]
    else
        allDefinedPackages(loader.getParent()) ++ loader.getDefinedPackages()
     
def allClassPathURLs(loader: ClassLoader): Vector[java.net.URL] =
    loader match
        case null => Vector.empty[java.net.URL]
        case u: java.net.URLClassLoader => allClassPathURLs(loader.getParent) ++ u.getURLs()
        case _ => allClassPathURLs(loader.getParent)

inline def test(inline s: String): String = ${ meta.test('s) }

object meta:
    import scala.quoted.*
    import scala.language.implicitConversions

    def test(using Quotes)(s: Expr[String]): Expr[String] =
        import quotes.reflect.*
        //val sym = Symbol.requiredPackage(s.valueOrAbort)
        val root = defn.RootPackage
        val sym = root.declarations.find { s => s.toString() == "object coulomb" }
        val defs = sym.get.declarations.map { s => (s.toString(), s.fullName, s.isPackageDef, s.isClassDef) }
        //val sym2 = root.declarations.map { s => (s.toString(), s.fullName, s.isPackageDef, s.isClassDef) }
        //Expr(s"${sym.fullName}: ${sym.exists} ${sym.isDefinedInCurrentRun} ${sym.maybeOwner.fullName}")
        //Expr(defs.mkString("\n"))
        val s2 = defn.RootPackage.declaredField(s.valueOrAbort)
        Expr(s2.toString())

    def test2(using Quotes)(s: String): Expr[String] = Expr("goo")
      /*
        import quotes.reflect.*
        val s2 = defn.RootPackage.declaredField(s)
        Expr(s2.toString())
*/

    given ctx_RationalToExpr: ToExpr[Rational] with
        def apply(r: Rational)(using Quotes): Expr[Rational] = r match
            // Rational(1) is a useful special case to have predefined
            // could we get clever with some kind of expression caching/sharing here?
            case v if (v == 1) => '{ Rational.const1 }
            case _ => '{ Rational(${Expr(r.n)}, ${Expr(r.d)}) }

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
        // the fundamental algorithmic unit analysis criterion:
        // http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/
        val (rcoef, rsig) = cansig(TypeRepr.of[/].appliedTo(List(u1, u2)))
        if (rsig == Nil) then rcoef else
            report.error(s"unit type ${typestr(u1)} not convertable to ${typestr(u2)}")
            Rational.const0

    def offset(using Quotes)(u: quotes.reflect.TypeRepr, b: quotes.reflect.TypeRepr): Rational =
        import quotes.reflect.*
        u match
            case deltaunit(offset, db) =>
                if (matchingdelta(db, b)) offset else
                    report.error(s"bad DeltaUnit in offset: ${typestr(u)}")
                    Rational.const0
            case baseunit() if convertible(u, b) => Rational.const0
            case derivedunit(_, _) if convertible(u, b) => Rational.const0
            case _ => { report.error(s"unknown unit expression in offset: ${typestr(u)}"); Rational.const0 }

    def matchingdelta(using Quotes)(db: quotes.reflect.TypeRepr, b: quotes.reflect.TypeRepr): Boolean =
        import quotes.reflect.*
        // units of db and b should cancel, and leave only a constant behind
        simplify(TypeRepr.of[/].appliedTo(List(db, b))) match
            case rationalTE(_) => true
            case _ => false

    def convertible(using Quotes)(u1: quotes.reflect.TypeRepr, u2: quotes.reflect.TypeRepr): Boolean =
        import quotes.reflect.*
        val (_, rsig) = cansig(TypeRepr.of[/].appliedTo(List(u1, u2)))
        rsig == Nil

    // returns tuple: (expr-for-coef, type-of-Res)
    def cansig(using Quotes)(u: quotes.reflect.TypeRepr):
            (Rational, List[(quotes.reflect.TypeRepr, Rational)]) =
        import quotes.reflect.*
        // if this encounters a unit type pattern it cannot expand to a canonical signature,
        // at any level, it raises a compile-time error such that the context parameter search fails.
        u match
            // identify embedded coefficients (includes '1' aka unitless)
            case unitconst(c) => (c, Nil)
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
                if (e == 0) (Rational.const1, Nil)
                else if (e == 1) (bcoef, bsig)
                else if (e.n.isValidInt && e.d.isValidInt)
                    val ucoef = if (e.d == 1) bcoef.pow(e.n.toInt)
                                else bcoef.pow(e.n.toInt).root(e.d.toInt)
                    val usig = unifyPow(e, bsig)
                    (ucoef, usig)
                else
                    report.error(s"bad exponent in cansig: ${typestr(u)}")
                    (Rational.const0, Nil)
            case baseunit() => (Rational.const1, (u, Rational.const1) :: Nil)
            case derivedunit(ucoef, usig) => (ucoef, usig)
            case _ if (!strictunitexprs) =>
                // we consider any other type for "promotion" to base-unit only if
                // it does not match the strict unit expression forms above, and
                // if the strict unit expression policy has not been enabled
                (Rational.const1, (u, Rational.const1) :: Nil)
            case _ =>
                report.error(s"unknown unit expression in cansig: ${typestr(u)}")
                (Rational.const0, Nil)

    def stdsig(using Quotes)(u: quotes.reflect.TypeRepr): List[(quotes.reflect.TypeRepr, Rational)] =
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
                if (e == 0) Nil
                else if (e == 1) stdsig(b)
                else unifyPow(e, stdsig(b))
            case unitconst(c) =>
                if (c == 1) Nil else (rationalTE(c), Rational.const1) :: Nil
            case baseunit() => (u, Rational.const1) :: Nil
            case derivedunit(_, _) => (u, Rational.const1) :: Nil
            case _ if (!strictunitexprs) =>
                // we consider any other type for "promotion" to base-unit only if
                // it does not match the strict unit expression forms above, and
                // if the strict unit expression policy has not been enabled
                (u, Rational.const1) :: Nil
            case _ =>
                report.error(s"unknown unit expression in stdsig: ${typestr(u)}")
                Nil

    def sortsig(using Quotes)(sig: List[(quotes.reflect.TypeRepr, Rational)]):
            (List[(quotes.reflect.TypeRepr, Rational)], List[(quotes.reflect.TypeRepr, Rational)]) =
        sig match
            case Nil => (Nil, Nil)
            case (u, e) :: tail =>
                val (nsig, dsig) = sortsig(tail)
                if (e > 0) ((u, e) :: nsig, dsig) else (nsig, (u, -e) :: dsig)

    def simplify(using Quotes)(u: quotes.reflect.TypeRepr): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        val (un, ud) = sortsig(stdsig(u))
        (uProd(un), uProd(ud)) match
            case (unitconst1(), unitconst1()) => TypeRepr.of[1]
            case (n, unitconst1()) => n
            case (unitconst1(), d) => TypeRepr.of[/].appliedTo(List(TypeRepr.of[1], d))
            case (n, d) => TypeRepr.of[/].appliedTo(List(n, d))

    def uProd(using Quotes)(sig: List[(quotes.reflect.TypeRepr, Rational)]): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        sig match
            case Nil => TypeRepr.of[1]
            case (u, e) :: Nil => uTerm(u, e)
            case (u1, e1) :: (u2, e2) :: Nil =>
                TypeRepr.of[*].appliedTo(List(uTerm(u1, e1), uTerm(u2, e2)))
            case (u, e) :: tail =>
                TypeRepr.of[*].appliedTo(List(uTerm(u, e), uProd(tail)))

    def uTerm(using Quotes)(u: quotes.reflect.TypeRepr, p: Rational): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        if (p == 1) u else TypeRepr.of[^].appliedTo(List(u, rationalTE(p)))

    def strictunitexprs(using Quotes): Boolean =
        import quotes.reflect.*
        Implicits.search(TypeRepr.of[coulomb.policy.StrictUnitExpressions]) match
            case _: ImplicitSearchSuccess => true
            case _ => false

    object unitconst1:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            u match
                case rationalTE(v) if (v == 1) => true
                case _ => false

    object unitconst:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[Rational] =
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
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr):
                Option[(Rational, List[(quotes.reflect.TypeRepr, Rational)])] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess =>
                    val AppliedType(_, List(_, d, _, _)) = iss.tree.tpe.baseType(TypeRepr.of[DerivedUnit].typeSymbol)
                    Some(cansig(d))
                case _ => None

    object deltaunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[(Rational, quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DeltaUnit].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess =>
                    val AppliedType(_, List(_, b, o, _, _)) = iss.tree.tpe.baseType(TypeRepr.of[DeltaUnit].typeSymbol)
                    val rationalTE(offset) = o
                    Some((offset, b))
                case _ => None

    def unifyOp(using Quotes)(
        sig1: List[(quotes.reflect.TypeRepr, Rational)],
        sig2: List[(quotes.reflect.TypeRepr, Rational)],
        op: (Rational, Rational) => Rational
            ): List[(quotes.reflect.TypeRepr, Rational)] =
        sig2 match
            case Nil => sig1
            case (u, e) :: tail => unifyOp(insertTerm(u, e, sig1, op), tail, op)

    def insertTerm(using Quotes)(
        u: quotes.reflect.TypeRepr, e: Rational,
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
            case Nil => Nil
            case (u, e0) :: tail => (u, e0 * e) :: unifyPow(e, tail)

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
