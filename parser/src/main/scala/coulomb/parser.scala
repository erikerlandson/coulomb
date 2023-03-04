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

package coulomb.parser

import coulomb.RuntimeUnit

abstract class RuntimeUnitParser:
    def parse(expr: String): RuntimeUnit
    def render(u: RuntimeUnit): String

object standard:
    sealed abstract class RuntimeUnitExprParser extends RuntimeUnitParser:
        protected def unames: Map[String, String]
        protected def pnames: Set[String]
        def parse(expr: String): RuntimeUnit =
            RuntimeUnit.UnitType("no")
        def render(u: RuntimeUnit): String =
            "no"

    object RuntimeUnitExprParser:
        inline def of[UTL]: RuntimeUnitExprParser = ${ meta.ofUTL[UTL] }

object infra:
    import _root_.cats.parse.*
    def strset(ss: Set[String]): Parser[String] =
        // assumes ss is not empty and all members are length > 0
        // this is guaranteed by construction at compile time
        val head = ss.map(_.head)
        val po = head.map { c =>
            val tail = ss.filter(_.head == c).map(_.drop(1)).filter(_.length > 0)
            if (tail.isEmpty)
                Parser.string(Parser.char(c))
            else
                Parser.string(Parser.char(c) ~ strset(tail))
        }
        val p = po.drop(1).fold(po.head) { case (p, q) =>
            Parser.string(p | q)
        }
        p

object meta:
    import scala.quoted.*
    import scala.util.{Try, Success, Failure}
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.infra.meta.{*, given}
    import coulomb.infra.runtime.meta.{*, given}
    import coulomb.conversion.runtimes.mapping.meta.{*, given}

    import coulomb.parser.standard.RuntimeUnitExprParser

    import coulomb.syntax.typelist.{TNil, &:}

    def ofUTL[UTL](using Quotes, Type[UTL]): Expr[RuntimeUnitExprParser] =
        import quotes.reflect.*
        val (un, pn) = collect(typeReprList(TypeRepr.of[UTL]))
        val pn1 = pn.filter(_.length > 0)
        val un1 = un.filter { case (k, _) =>
            k.length > 0
        }
        if (un1.isEmpty)
            report.errorAndAbort(s"ofUTL: no defined unit names")
        '{
            new RuntimeUnitExprParser:
                protected val unames = ${ Expr(un1) }
                protected val pnames = ${ Expr(pn1) }
        }

    private def collect(using Quotes)(tl: List[quotes.reflect.TypeRepr]): (Map[String, String], Set[String]) =
        import quotes.reflect.*
        tl match
            case Nil => (Map.empty[String, String], Set.empty[String])
            case head :: tail =>
                val (un, pn) = collect(tail)
                head match
                    case ConstantType(StringConstant(mname)) =>
                        collect(moduleUnits(mname))
                    case baseunitTR(tr) =>
                        val AppliedType(_, List(_, n, _)) = tr: @unchecked
                        val ConstantType(StringConstant(name)) = n: @unchecked
                        (un + (name -> head.typeSymbol.fullName), pn)
                    case derivedunitTR(tr) =>
                        val AppliedType(_, List(_, _, n, _)) = tr: @unchecked
                        val ConstantType(StringConstant(name)) = n: @unchecked
                        val unr = un + (name -> head.typeSymbol.fullName)
                        val pnr = if (convertible(head, TypeRepr.of[1])) pn + name else pn
                        (unr, pnr)
                    case _ =>
                        report.errorAndAbort(s"collect: bad type ${head.show}")
                        null.asInstanceOf[Nothing]
