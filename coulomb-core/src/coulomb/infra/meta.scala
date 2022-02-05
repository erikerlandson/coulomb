package coulomb.infra

import coulomb.rational.Rational
import coulomb.*
import coulomb.define.*
import coulomb.ops.*

import coulomb.Coefficient

final type SNil
final type %:[Head, Tail]

object meta:
    import scala.quoted.*
    import scala.language.implicitConversions

    // Coefficient[U1, U2]
    def coefficient[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Coefficient[U1, U2]] =
        import quotes.reflect.*
        val rcoef = coef(TypeRepr.of[U1], TypeRepr.of[U2])
        '{ new Coefficient[U1, U2] { val value = $rcoef } }

    def coef(using Quotes)(u1: quotes.reflect.TypeRepr, u2: quotes.reflect.TypeRepr): Expr[Rational] =
        import quotes.reflect.*
        if (u1 =:= u2) then
            // confirm that the type has a defined canonical signature, or fail
            val _ = cansig(u1)
            // the coefficient between two identical unit expression types is always exactly 1
            '{ Rational.const1 }
        // the fundamental algorithmic unit analysis criterion:
        // http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/
        val (rcoef, rsig) = cansig(TypeRepr.of[/].appliedTo(List(u1, u2)))
        if (rsig =:= TypeRepr.of[SNil]) then rcoef else
            report.error(s"units are not convertable: ($u1) ($u2)")
            '{ Rational.const0 }

    // returns tuple: (expr-for-coef, type-of-Res)
    def cansig(using Quotes)(u: quotes.reflect.TypeRepr, top: Boolean = false):
            (Expr[Rational], quotes.reflect.TypeRepr) =
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
                val ucoef = if (coefIs1(lcoef)) rcoef else if (coefIs1(rcoef)) lcoef else '{ $lcoef * $rcoef }
                val usig = unifyOp(lsig, rsig, _ + _)
                (ucoef, usig)
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[/]) =>
                val (lcoef, lsig) = cansig(lu)
                val (rcoef, rsig) = cansig(ru)
                val ucoef = if (coefIs1(rcoef)) lcoef else '{ $lcoef / $rcoef }
                val usig = unifyOp(lsig, rsig, _ - _)
                (ucoef, usig)
            case AppliedType(op, List(b, p)) if (op =:= TypeRepr.of[^]) =>
                val (bcoef, bsig) = cansig(b)
                val ratexp(e) = p
                if (e == 0) ('{ Rational.const1 }, signil())
                else if (e == 1) (bcoef, bsig)
                else
                    val ucoef = if (coefIs1(bcoef)) bcoef
                                else if (e.d == 1) '{ ${bcoef}.pow(${Expr(e.n.toInt)}) }
                                else '{ ${bcoef}.pow(${Expr(e.n.toInt)}).root(${Expr(e.d.toInt)}) }
                    val usig = unifyPow(p, bsig)
                    (ucoef, usig)
            case unitless() => ('{ Rational.const1 }, signil())
            case unitconst(c) => ('{ Rational(${Expr(c.n)}, ${Expr(c.d)}) }, signil())
            case baseunit() => ('{ Rational.const1 }, sigcons(u, Rational.const1, signil()))
            case derivedunit1(ucoef, usig) => (ucoef, usig)
            case derivedunit(ucoef, usig) => (ucoef, usig)
            case _ if (!strictunitexprs) =>
                // we consider any other type for "promotion" to base-unit only if
                // it does not match the strict unit expression forms above, and
                // if the strict unit expression policy has not been enabled
                ('{ Rational.const1 }, sigcons(u, Rational.const1, signil()))
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
                val ratexp(e) = p
                if (e == 0) signil()
                else if (e == 1) stdsig(b)
                else unifyPow(p, stdsig(b))
            case unitless() => signil()
            case unitconst(c) => sigcons(ratexp(c), Rational.const1, signil())
            case baseunit() => sigcons(u, Rational.const1, signil())
            case derivedunit1(_, _) => sigcons(u, Rational.const1, signil())
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

    def mulRU[UL, UR](using Quotes, Type[UL], Type[UR]): Expr[MulRU[UL, UR]] =
        import quotes.reflect.*
        val t = unifyOp(stdsig(TypeRepr.of[UL]), stdsig(TypeRepr.of[UR]), _ + _)
        val (un, ud) = sortsig(t)
        finRU(un, ud).asType match
            case '[ru] => '{ new MulRU[UL, UR] { type RU = ru } }

    def divRU[UL, UR](using Quotes, Type[UL], Type[UR]): Expr[DivRU[UL, UR]] =
        import quotes.reflect.*
        val t = unifyOp(stdsig(TypeRepr.of[UL]), stdsig(TypeRepr.of[UR]), _ - _)
        val (un, ud) = sortsig(t)
        finRU(un, ud).asType match
            case '[ru] => '{ new DivRU[UL, UR] { type RU = ru } }

    def powRU[U, P](using Quotes, Type[U], Type[P]): Expr[PowRU[U, P]] =
        import quotes.reflect.*
        val t = unifyPow(TypeRepr.of[P], stdsig(TypeRepr.of[U]))
        val (un, ud) = sortsig(t)
        finRU(un, ud).asType match
            case '[ru] => '{ new PowRU[U, P] { type RU = ru } }

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
        if (p == 1) u else TypeRepr.of[^].appliedTo(List(u, ratexp(p)))

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
                case ratlt(n, d) => Some(Rational(n, d))
                case intlt(n) => Some(Rational(n, 1))
                case dbllt(v) => Some(Rational(v))
                case _ => None

    object baseunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[BaseUnit].appliedTo(u)) match
                case iss: ImplicitSearchSuccess => true
                case _ => false

    object derivedunit1:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[(Expr[Rational], quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit1].appliedTo(List(u, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess =>
                    val AppliedType(_, List(_, d)) = iss.tree.tpe.baseType(TypeRepr.of[DerivedUnit1].typeSymbol)
                    val (dcoef, dsig) = cansig(d)
                    // in the DerivedUnit1 special case, coef=1 by definition, so we can propagate dcoef directly
                    Some((dcoef, dsig))
                case _ => None

    object derivedunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[(Expr[Rational], quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess =>
                    val AppliedType(_, List(_, d)) = iss.tree.tpe.baseType(TypeRepr.of[DerivedUnit].typeSymbol)
                    val (dcoef, dsig) = cansig(d)
                    val du = iss.tree.asExpr.asInstanceOf[Expr[DerivedUnit[_, _]]]
                    val ucoef = if (coefIs1(dcoef)) '{ ${du}.coef } else '{ $dcoef * ${du}.coef }
                    Some((ucoef, dsig))
                case _ => None

    // evaluating these at compilation time is not working, so the best I currently
    // know how to do is structural test for Rational.const1
    def coefIs1(using Quotes)(expr: Expr[Rational]): Boolean =
        expr.matches('{ Rational.const1 })

    def csErr(using Quotes): (Expr[Rational], quotes.reflect.TypeRepr) =
        ('{ Rational.const0 }, quotes.reflect.TypeRepr.of[Nothing])

    // keep this for reference
    def summonString[T](using Quotes, Type[T]): String =
        import quotes.reflect.*
        Expr.summon[T] match
            case None => "None"
            case Some(e) => s"${e.show}   ${e.asTerm.show(using Printer.TreeStructure)}"

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
        val ratexp(e) = power
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
            sctc().appliedTo(List(t2tc().appliedTo(List(u, ratexp(e))), tail))

        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[(quotes.reflect.TypeRepr, Rational, quotes.reflect.TypeRepr)] =
            import quotes.reflect.*
            tr match
                case AppliedType(sctc(), List(AppliedType(t2tc(), List(u, ratexp(e))), tail)) =>
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

    object ratexp:
        def apply(using Quotes)(e: Rational): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            if (e.d == 1) then
                ConstantType(IntConstant(e.n.toInt))
            else
                TypeRepr.of[/].appliedTo(List(ConstantType(IntConstant(e.n.toInt)), ConstantType(IntConstant(e.d.toInt))))

        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[Rational] =
            import quotes.reflect.*
            tr match
                case ratlt(n, d) => Some(Rational(n, d))
                case intlt(n) => Some(Rational(n, 1))
                case _ => None

    object ratlt:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[(Int, Int)] =
             import quotes.reflect.*
             tr match
                case AppliedType(tc, List(ConstantType(IntConstant(n)), ConstantType(IntConstant(d)))) if (tc =:= TypeRepr.of[/]) =>
                    Some((n, d))
                case _ => None

    object intlt:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[Int] =
             import quotes.reflect.*
             tr match
                case ConstantType(IntConstant(i)) => Some(i)
                case _ => None

    object dbllt:
        def unapply(using Quotes)(tr: quotes.reflect.TypeRepr): Option[Double] =
             import quotes.reflect.*
             tr match
                case ConstantType(DoubleConstant(v)) => Some(v)
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

    def typestr(using Quotes)(t: quotes.reflect.TypeRepr): Expr[String] =
        import quotes.reflect.*
        def work(tr: TypeRepr): String = tr match
            case AppliedType(tc, ta) =>
                val tcn = tc.typeSymbol.name
                val as = ta.map(work)
                if (as.length == 0) tcn else
                    tcn + "[" + as.mkString(",") + "]"
            case t => t.typeSymbol.name
        val ts = work(t.dealias)
        Expr(ts)
