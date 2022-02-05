package coulomb.ops.standard

import coulomb.*
import coulomb.ops.*
import coulomb.define.*

transparent inline given addStandard1U[VL, VR, U]: Add[VL, U, VR, U] =
    ${ meta.addStandard1U[VL, VR, U] }

transparent inline given addStandard2U[VL, UL, VR, UR]: Add[VL, UL, VR, UR] =
    ${ meta.addStandard2U[VL, UL, VR, UR] }

transparent inline given showStandard[U]: Show[U] =
    ${ meta.showStandard[U] }

transparent inline given showFullStandard[U]: ShowFull[U] =
    ${ meta.showFullStandard[U] }

object meta:
    import scala.quoted.*
    import coulomb.infra.meta.*

    def showStandard[U](using Quotes, Type[U]): Expr[Show[U]] =
        import quotes.reflect.*
        val usexpr = showrender(TypeRepr.of[U], (nu: Expr[NamedUnit]) => '{ ${nu}.abbv })
        '{ new Show[U] { val value = $usexpr } }

    def showFullStandard[U](using Quotes, Type[U]): Expr[ShowFull[U]] =
        import quotes.reflect.*
        val usexpr = showrender(TypeRepr.of[U], (nu: Expr[NamedUnit]) => '{ ${nu}.name })
        '{ new ShowFull[U] { val value = $usexpr } }

    def showrender(using Quotes)(u: quotes.reflect.TypeRepr, render: Expr[NamedUnit] => Expr[String]): Expr[String] =
        import quotes.reflect.*
        u match
            case flatmul(t) => termstr(t, render)
            case AppliedType(op, List(lu, unitless())) if (op =:= TypeRepr.of[/]) =>
                showrender(lu, render)
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[/]) =>
                val (ls, rs) = (paren(lu, render), paren(ru, render))
                '{ ${ls} + "/" + ${rs} }
            case AppliedType(op, List(b, e)) if (op =:= TypeRepr.of[^]) =>
                val (bs, es) = (paren(b, render), powstr(e))
                '{ ${bs} + "^" + ${es} }
            case unitconst(v) =>
                if (v.d == 1) Expr(v.n.toString) else '{ "(" + ${Expr(v.n.toString)} + "/" + ${Expr(v.d.toString)} + ")" }
            case namedunit(nu) => render(nu)
            case _ if (!strictunitexprs) => typestr(u)
            case _ =>
                report.error(s"unrecognized unit pattern $u")
                '{ "" }

    def termstr(using Quotes)(terms: List[quotes.reflect.TypeRepr], render: Expr[NamedUnit] => Expr[String]): Expr[String] =
        import quotes.reflect.*
        // values for terms constructed from 'flatmul' should have >= 2 terms to start
        // so should terms should never be empty in this recursion
        terms match
            case term +: Nil => paren(term, render)
            case term +: tail =>
                val h = paren(term, render)
                val t = termstr(tail, render)
                // tail should never be empty (see above)
                term match
                    case namedPU(_) => '{ ${h} + ${t} }
                    case _ => '{ ${h} + " " + ${t} }
            case _ =>
                report.error(s"unrecognized termstr pattern $terms")
                '{ "" }

    object flatmul:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[List[quotes.reflect.TypeRepr]] =
            import quotes.reflect.*
            u match
                case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[*]) =>
                    val lflat = lu match
                        case flatmul(lf) => lf
                        case _ => List(lu)
                    val rflat = ru match
                        case flatmul(rf) => rf
                        case _ => List(ru)
                    Some(lflat ++ rflat)
                case _ => None

    def powstr(using Quotes)(p: quotes.reflect.TypeRepr): Expr[String] =
        import quotes.reflect.*
        p match
            case intlt(n) if (n >= 0) => Expr(n.toString)
            case intlt(n) if (n < 0) => '{ "(" + ${Expr(n.toString)} + ")" }
            case ratlt(n, d) => '{ "(" + ${Expr(n.toString)} + "/" + ${Expr(d.toString)} + ")" }
            case _ => '{ "!!!" }

    def paren(using Quotes)(u: quotes.reflect.TypeRepr, render: Expr[NamedUnit] => Expr[String]): Expr[String] =
        val str = showrender(u, render)
        if (atomic(u)) str else '{ "(" + ${str} + ")" }

    def atomic(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
        import quotes.reflect.*
        u match
            case unitconst(_) => true
            case namedunit(_) => true
            case AppliedType(op, List(namedunit(_), _)) if (op =:= TypeRepr.of[^]) => true
            case AppliedType(op, List(flatmul(namedPU(_) +: namedunit(_) +: Nil), _)) if (op =:= TypeRepr.of[^]) => true
            case flatmul(namedPU(_) +: namedunit(_) +: Nil) => true
            case _ => false

    object namedunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[Expr[NamedUnit]] =
            import quotes.reflect.*
            u match
                case namedBU(nu) => Some(nu)
                case namedDU(nu) => Some(nu)
                case _ => None

    object namedPU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[Expr[NamedUnit]] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeRepr.of[1]))) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.asExpr.asInstanceOf[Expr[NamedUnit]])
                case _ => None

    object namedBU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[Expr[NamedUnit]] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[BaseUnit].appliedTo(u)) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.asExpr.asInstanceOf[Expr[NamedUnit]])
                case _ => None

    object namedDU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[Expr[NamedUnit]] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.asExpr.asInstanceOf[Expr[NamedUnit]])
                case _ => None

    def addStandard1U[VL :Type, VR :Type, U :Type](using Quotes): Expr[Add[VL, U, VR, U]] =
        import quotes.reflect.*
        // units are the same, so no coefficient is necessary
        (TypeRepr.of[VL], TypeRepr.of[VR]) match
            case (typeDouble(), typeDouble()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Double, vr: Double): Double = vl + vr
            }
            case _ =>
                report.error(s"addition not defined for these types")
                '{ new Add[VL, U, VR, U] { type VO = Int; type UO = Nothing; def apply(vl: VL, vr: VR): VO = 0 } }

    def addStandard2U[VL :Type, UL :Type, VR :Type, UR :Type](using Quotes): Expr[Add[VL, UL, VR, UR]] =
        import quotes.reflect.*
        // units are not identical: get coefficient (or fail)
        val cf = coef(TypeRepr.of[UR], TypeRepr.of[UL]) // get coefficient from right to left
        (TypeRepr.of[VL], TypeRepr.of[VR]) match
            case (typeDouble(), typeDouble()) => '{
                new Add[VL, UL, VR, UR]:
                    type VO = Double
                    type UO = UL
                    val c = ${cf}.toDouble
                    def apply(vl: Double, vr: Double): Double = vl + (c * vr)
            }
            case _ =>
                report.error(s"addition not defined for these types")
                '{ new Add[VL, UL, VR, UR] { type VO = Int; type UO = Nothing; def apply(vl: VL, vr: VR): VO = 0 } }
