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

object show:
    import scala.quoted.*
    import coulomb.*
    import coulomb.define.*
    import coulomb.infra.meta.*

    def show[U](using Quotes, Type[U]): Expr[String] =
        import quotes.reflect.*
        val render = (tr: TypeRepr) => {
            val AppliedType(_, List(_, a)) = tr
            val ConstantType(StringConstant(abbv)) = a
            abbv
        }
        val str = showrender(TypeRepr.of[U], render)
        Expr(str)

    def showFull[U](using Quotes, Type[U]): Expr[String] =
        import quotes.reflect.*
        val render = (tr: TypeRepr) => {
            val AppliedType(_, List(n, _)) = tr
            val ConstantType(StringConstant(name)) = n
            name
        }
        val str = showrender(TypeRepr.of[U], render)
        Expr(str)

    def showrender(using Quotes)(u: quotes.reflect.TypeRepr, render: quotes.reflect.TypeRepr => String): String =
        import quotes.reflect.*
        u match
            // named unit defs take highest priority
            case namedunit(nu) => render(nu)
            // The policy goal here is that type aliases are never expanded
            // (unless covered by some namedunit(_) above).  However,
            // scala's implicit resolution logic undermines this somewhat because it
            // pre-dealiases type aliases, so the "outer" type is always dealiases
            // by the time my metaprogramming sees it.
            case typealias(_) => typestr(u)
            case unitconst(v) =>
                if (v.d == 1) v.n.toString else s"${v.n.toString}/${v.d.toString}"
            case flatmul(t) => termstr(t, render)
            case AppliedType(op, List(lu, unitconst1())) if (op =:= TypeRepr.of[/]) =>
                showrender(lu, render)
            case AppliedType(op, List(lu, ru)) if (op =:= TypeRepr.of[/]) =>
                val (ls, rs) = (paren(lu, render), paren(ru, render))
                s"${ls}/${rs}"
            case AppliedType(op, List(b, e)) if (op =:= TypeRepr.of[^]) =>
                val (bs, es) = (paren(b, render), powstr(e))
                s"${bs}^${es}"
            case _ if (!strictunitexprs) => typestr(u)
            case _ =>
                report.error(s"unrecognized unit pattern $u")
                ""

    def termstr(using Quotes)(terms: List[quotes.reflect.TypeRepr], render: quotes.reflect.TypeRepr => String): String =
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
                    case namedPU(_) => s"${h}${t}"
                    case _ => s"${h} ${t}"
            case _ =>
                report.error(s"unrecognized termstr pattern $terms")
                ""

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

    def powstr(using Quotes)(p: quotes.reflect.TypeRepr): String =
        import quotes.reflect.*
        p match
            case bigintTE(v) if (v >= 0) => v.toString
            case bigintTE(v) if (v < 0) => s"(${v.toString})"
            case rationalTE(v) => s"(${v.n.toString}/${v.d.toString})"
            case _ => "!!!"

    def paren(using Quotes)(u: quotes.reflect.TypeRepr, render: quotes.reflect.TypeRepr => String): String =
        val str = showrender(u, render)
        if (atomic(u)) str else s"(${str})"

    def atomic(using Quotes)(u: quotes.reflect.TypeRepr): Boolean =
        import quotes.reflect.*
        u match
            case namedunit(_) => true
            case unitconst(_) => true
            case AppliedType(op, List(namedunit(_), _)) if (op =:= TypeRepr.of[^]) => true
            case AppliedType(op, List(flatmul(namedPU(_) +: namedunit(_) +: Nil), _)) if (op =:= TypeRepr.of[^]) => true
            case flatmul(namedPU(_) +: namedunit(_) +: Nil) => true
            case AppliedType(_, _) => false
            case _ => true

    object namedunit:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            u match
                case namedSU(nu) => Some(nu)
                case namedBU(nu) => Some(nu)
                case namedDU(nu) => Some(nu)
                case _ => None

    object namedSU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[ShowUnitAlias].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.tpe.baseType(TypeRepr.of[NamedUnit].typeSymbol))
                case _ => None

    object namedPU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeRepr.of[1], TypeBounds.empty, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.tpe.baseType(TypeRepr.of[NamedUnit].typeSymbol))
                case _ => None

    object namedBU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[BaseUnit].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.tpe.baseType(TypeRepr.of[NamedUnit].typeSymbol))
                case _ => None

    object namedDU:
        def unapply(using Quotes)(u: quotes.reflect.TypeRepr): Option[quotes.reflect.TypeRepr] =
            import quotes.reflect.*
            Implicits.search(TypeRepr.of[DerivedUnit].appliedTo(List(u, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty, TypeBounds.empty))) match
                case iss: ImplicitSearchSuccess => Some(iss.tree.tpe.baseType(TypeRepr.of[NamedUnit].typeSymbol))
                case _ => None
