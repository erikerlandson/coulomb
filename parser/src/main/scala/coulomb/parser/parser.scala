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

import scala.quoted.*

import coulomb.*
import coulomb.syntax.*
import coulomb.units.si.*
import coulomb.conversion.*

object test:
    val stub = 0

    // fully qualified type name
    inline def fqtn[T]: String = ${ meta.fqtn[T] }

    inline def m[T]: Map[String, String] = ${ meta.m[T] }

    // this "almost" works but it won't bind 'Quotes' if you try to invoke it
    def q(v: Double, u: String)(using
        Quotes,
        staging.Compiler
    ): Quantity[Double, Meter] = staging.run {
        import quotes.reflect.*

        // this eventually builds arbitrary unit expr via parsing
        val t = u match
            case "meter"  => TypeRepr.of[Meter]
            case "second" => TypeRepr.of[Second]
            case _        => TypeRepr.of[1]

        val ttt = t.asType match
            case '[t] => Expr.summon[UnitConversion[Double, t, Meter]]
        val cnv = ttt match
            case Some(x) => x
            case _       => null

        val f = t.asType match
            case '[t] =>
                '{ (v: Double) =>
                    v.withUnit[t]
                        .toUnit[Meter](using
                            ${
                                cnv.asTerm
                                    .asExprOf[UnitConversion[Double, t, Meter]]
                            }
                        )
                }

        '{ (${ f })(${ Expr(v) }) }
    }

    object meta:
        import scala.language.implicitConversions

        def fqtn[T](using Quotes, Type[T]): Expr[String] =
            import quotes.reflect.*
            val tr = TypeRepr.of[T]
            Expr(tr.dealias.typeSymbol.fullName)

        def m[T](using Quotes, Type[T]): Expr[Map[String, String]] =
            val mm = Map("a" -> "b")
            Expr(mm)
