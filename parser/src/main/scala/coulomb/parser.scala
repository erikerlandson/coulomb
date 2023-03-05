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
    def parse(expr: String): Either[String, RuntimeUnit]
    def render(u: RuntimeUnit): String

object standard:
    sealed abstract class RuntimeUnitExprParser extends RuntimeUnitParser:
        protected def unames: Map[String, String]
        protected def pnames: Set[String]
        def parse(expr: String): Either[String, RuntimeUnit] =
            Left("no")
        def render(u: RuntimeUnit): String =
            "no"

    object RuntimeUnitExprParser:
        inline def of[UTL]: RuntimeUnitExprParser = ${ meta.ofUTL[UTL] }

object infra:
    import _root_.cats.parse.*

    def named(unames: Map[String, String], pnames: Set[String]): Parser[RuntimeUnit] =
        // unames is never empty by construction
        val unit = strset(unames.keySet).map { name =>
            // map will always succeed because only names in unames can parse
            RuntimeUnit.UnitType(unames(name))
        }
        if (pnames.isEmpty)
            // if there are no prefix units, just parse units
            unit
        else
            val prefix = strset(pnames).map { name =>
                // prefixes are also defined in unames
                RuntimeUnit.UnitType(unames(name))
            }
            val pfu = (prefix ~ unit).map { case (lhs, rhs) =>
                // <prefix><unit> => prefix * unit
                RuntimeUnit.Mul(lhs, rhs)
            }
            // parse either <prefix><unit> or <unit>
            // test pfu first
            pfu | unit

    def strset(ss: Set[String]): Parser[String] =
        strsetvoid(ss).string

    // assumes ss is not empty and all members are length > 0
    // this is guaranteed by construction at compile time
    private def strsetvoid(ss: Set[String]): Parser[Unit] =
        // construct a parser "branch" for each starting character
        val hp = ss.map(_.head).toList.map { h =>
            // set of string tails starting with char h
            val tails = ss.filter(_.head == h).map(_.drop(1)).filter(_.length > 0)
            if (tails.isEmpty)
                // no remaining string tails, just parse char h
                Parser.char(h)
            else
                // parse h followed by parser for tails
                (Parser.char(h) ~ strsetvoid(tails)).void
        }
        // final parser is just "or" of branches: hp(0) | hp(1) | hp(2) ...
        Parser.oneOf(hp)

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
        // remove any unit names that are empty strings
        val pn1 = pn.filter(_.length > 0)
        val un1 = un.filter { case (k, _) =>
            k.length > 0
        }
        if (un1.isEmpty)
            // unit map must be non-empty
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
                        // base units are never prefix units because prefix units are
                        // derived from '1' (unitless)
                        (un + (name -> head.typeSymbol.fullName), pn)
                    case derivedunitTR(tr) =>
                        val AppliedType(_, List(_, _, n, _)) = tr: @unchecked
                        val ConstantType(StringConstant(name)) = n: @unchecked
                        // always add to unit types
                        val unr = un + (name -> head.typeSymbol.fullName)
                        // if it is derived from unitless, also add it to prefix unit set
                        val pnr = if (convertible(head, TypeRepr.of[1])) pn + name else pn
                        (unr, pnr)
                    case _ =>
                        report.errorAndAbort(s"collect: bad type ${head.show}")
                        null.asInstanceOf[Nothing]
