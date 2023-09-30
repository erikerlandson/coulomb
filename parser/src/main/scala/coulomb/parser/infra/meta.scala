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

package coulomb.parser.infra

import scala.util.{Try, Success, Failure}

import coulomb.RuntimeUnit
import coulomb.rational.Rational

object meta:
    import scala.quoted.*
    import scala.util.{Try, Success, Failure}
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.infra.meta.{*, given}
    import coulomb.infra.runtime.meta.{*, given}
    import coulomb.conversion.runtimes.mapping.meta.moduleUnits

    import coulomb.parser.standard.RuntimeUnitDslParser

    def ofUTL[UTL](using Quotes, Type[UTL]): Expr[RuntimeUnitDslParser] =
        import quotes.reflect.*
        val (un, pn) = collect(typeReprList(TypeRepr.of[UTL]))
        // remove any unit names that are empty strings
        val pn1 = pn.filter(_.length > 0)
        val un1 = un.filter { case (k, _) =>
            k.length > 0
        }
        '{
            new RuntimeUnitDslParser:
                val unames = ${ Expr(un1) }
                val pnames = ${ Expr(pn1) }
        }

    private def collect(using Quotes)(
        tl: List[quotes.reflect.TypeRepr]
    ): (Map[String, String], Set[String]) =
        import quotes.reflect.*
        tl match
            case Nil => (Map.empty[String, String], Set.empty[String])
            case head :: tail =>
                val (un, pn) = collect(tail)
                head match
                    case ConstantType(StringConstant(mname)) =>
                        val (mu, mp) = collect(moduleUnits(mname))
                        (un ++ mu, pn ++ mp)
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
                        val pnr =
                            if (convertible(head, TypeRepr.of[1])) pn + name
                            else pn
                        (unr, pnr)
                    case _ =>
                        report.errorAndAbort(s"collect: bad type ${head.show}")
                        null.asInstanceOf[Nothing]
