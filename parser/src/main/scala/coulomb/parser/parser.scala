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
    // fully qualified type name
    inline def fqtn[T]: String = ${ meta.fqtn[T] }

    inline def m[T]: Map[String, String] = ${ meta.m[T] }

    // this compiles and "runs" but run-time fails trying to find
    // coulomb implicits - so the staging compiler
    // currently doesn't work for finding imported implicits
    // unsure if this is due to bad class loader or something else
    def q(v: Double, u: String)(using
        staging.Compiler
    ): Quantity[Double, Meter] = staging.run {
        // inside this scope Quotes is defined

        println(s"get f...")
        val f = meta.qqq(u)

        println(s"apply f...")
        '{ (${ f })(${ Expr(v) }) }
    }

    // invoke qqq without staging compiler
    // this works because it is bypassing the staging compiler
    inline def qq(u: String): (Double => Quantity[Double, Meter]) =
        ${ meta.qq('u) }

    inline def z(inline b: Int): Int = ${ meta.z('b) }

    object meta:
        import scala.unchecked
        import scala.language.implicitConversions

        def z(b: Expr[Int])(using Quotes): Expr[Int] =
            import quotes.reflect.*
            println(s"${b.asTerm}")
            b

        def qq(u: Expr[String])(using
            Quotes
        ): Expr[Double => Quantity[Double, Meter]] =
            qqq(u.valueOrAbort)

        def qqq(u: String)(using
            Quotes
        ): Expr[Double => Quantity[Double, Meter]] =
            import quotes.reflect.*

            // this eventually builds arbitrary unit expr via parsing
            val t = u match
                case "meter"  => TypeRepr.of[Meter]
                case "second" => TypeRepr.of[Second]
                case _        => TypeRepr.of[1]

            println(s"t= $t")

            val root = defn.RootPackage
            println(s"root= $root")
            println(s"root.DF= ${root.declaredFields}")
            val clmb = root.declaredFields.filter(_.name == "coulomb").head
            println(s"clmb= $clmb")
            val units = clmb.declaredFields.filter(_.name == "units").head
            val si = units.declaredFields.filter(_.name == "si").head
            println(s"si= $si")
            println(s"si.tree= ${si.tree}")
            println(s"si.types= ${si.typeMembers}")
            val meter = si.typeMembers.filter(_.name == "Meter").head
            println(s"meter= $meter")
            println(s"typeRef= ${meter.typeRef}")

            val tt = meter.typeRef
            val iseq = (tt =:= t)
            println(s"iseq= $iseq")

            val f = t.asType match
                case '[t] =>
                    '{
                        // hard-coding imports works
                        // so question is how to allow library users to inject these from above
                        import coulomb.policy.standard.given
                        (v: Double) => v.withUnit[t].toUnit[Meter]
                    }
            f

        def fqtn[T](using Quotes, Type[T]): Expr[String] =
            import quotes.reflect.*
            val tr = TypeRepr.of[T]
            Expr(tr.dealias.typeSymbol.fullName)

        def m[T](using Quotes, Type[T]): Expr[Map[String, String]] =
            val mm = Map("a" -> "b")
            Expr(mm)
