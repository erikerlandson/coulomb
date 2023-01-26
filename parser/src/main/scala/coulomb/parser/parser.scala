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

import coulomb.rational.Rational

object ast:
    sealed abstract class UnitAST
    object UnitAST:
        case class Unit(name: String) extends UnitAST
        case class Mul(lhs: UnitAST, rhs: UnitAST) extends UnitAST
        case class Div(num: UnitAST, den: UnitAST) extends UnitAST
        case class Pow(b: UnitAST, e: Rational) extends UnitAST

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

    object meta:
        import scala.unchecked
        import scala.language.implicitConversions

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
                case "meter"  => fqTypeRepr("coulomb.units.si.Meter")
                case "second" => fqTypeRepr("coulomb.units.si.Second")
                case _        => TypeRepr.of[Unit]

            // val ttt = fqTypeRepr("coulomb.units.si.Meter")
            // assert(ttt =:= TypeRepr.of[coulomb.units.si.Meter])

            val s = fqFieldSymbol("coulomb.units.info.ctx_unit_Bit")
            println(s"s= ${symbolValueType(s).show}")
            println(s"s= ${symbolValueType(s).isSingleton}")
            println(s"s= ${s.flags.show}")
            val ss = fqFieldSymbol("coulomb.units.info")
            println(s"ss= ${symbolValueType(ss).show}")
            println(s"ss= ${symbolValueType(ss).isSingleton}")
            println(s"ss= ${ss.flags.show}")

            val f = t.asType match
                case '[t] =>
                    '{
                        // hard-coding imports works
                        // so question is how to allow library users to inject these from above
                        import coulomb.policy.standard.given
                        (v: Double) => v.withUnit[t].toUnit[Meter]
                    }
            f

        def symbolValueType(using Quotes)(
            sym: quotes.reflect.Symbol
        ): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            val TermRef(tr, _) = sym.termRef: @unchecked
            tr.memberType(sym)

        def fqTypeRepr(using Quotes)(
            path: Seq[String]
        ): quotes.reflect.TypeRepr =
            import quotes.reflect.*
            if (path.isEmpty)
                report.errorAndAbort("fqTypeRepr: empty path")
                TypeRepr.of[Unit]
            else
                val q = fqFieldSymbol(path.dropRight(1))
                val qt = q.typeMembers.filter(_.name == path.last)
                if (qt.length == 1) qt.head.typeRef
                else
                    report.errorAndAbort(
                        s"""fqTypeRepr: bad path ${path.mkString(".")}"""
                    )
                    TypeRepr.of[Unit]

        def fqTypeRepr(using Quotes)(path: String): quotes.reflect.TypeRepr =
            fqTypeRepr(path.split('.').toIndexedSeq)

        def fqFieldSymbol(using Quotes)(
            path: Seq[String]
        ): quotes.reflect.Symbol =
            import quotes.reflect.*
            def work(q: Symbol, tail: Seq[String]): Symbol =
                if (tail.isEmpty) q
                else
                    val qt = q.declaredFields.filter(_.name == tail.head)
                    if (qt.length == 1) work(qt.head, tail.tail)
                    else
                        report.errorAndAbort(
                            s"""fqFieldSymbol: bad path ${path.mkString(".")}"""
                        )
                        defn.RootPackage
            if (path.isEmpty)
                defn.RootPackage
            else if (path.head == "_root_")
                work(defn.RootPackage, path.tail)
            else
                work(defn.RootPackage, path)

        def fqFieldSymbol(using Quotes)(path: String): quotes.reflect.Symbol =
            fqFieldSymbol(path.split('.').toIndexedSeq)

        def fqtn[T](using Quotes, Type[T]): Expr[String] =
            import quotes.reflect.*
            val tr = TypeRepr.of[T]
            Expr(tr.dealias.typeSymbol.fullName)

        def m[T](using Quotes, Type[T]): Expr[Map[String, String]] =
            val mm = Map("a" -> "b")
            Expr(mm)
