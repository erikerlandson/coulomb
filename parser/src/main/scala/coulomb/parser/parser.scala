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

final class RuntimeContext[UnitDefs]
object RuntimeContext:
    final type &:[H, T]
    final type TNil

    def apply[D](): RuntimeContext[D] = new RuntimeContext[D]

object runtime:
    import coulomb.define.*
    import RuntimeContext.{&:, TNil}
    import coulomb.units.si.*
    import coulomb.units.si.prefixes.*
    given given_context: RuntimeContext[
        BaseUnit[Meter, "meter", "m"] &:
            DerivedUnit[Kilo, 10 ^ 3, "kilo", "k"] &: TNil
    ] = RuntimeContext()

sealed abstract class UnitAST:
    def *(rhs: UnitAST): UnitAST.Mul = UnitAST.Mul(this, rhs)
    def /(den: UnitAST): UnitAST.Div = UnitAST.Div(this, den)
    def ^(e: Rational): UnitAST.Pow = UnitAST.Pow(this, e)

object UnitAST:
    case class UnitType(path: String) extends UnitAST
    case class Mul(lhs: UnitAST, rhs: UnitAST) extends UnitAST
    case class Div(num: UnitAST, den: UnitAST) extends UnitAST
    case class Pow(b: UnitAST, e: Rational) extends UnitAST
    inline def of[U]: UnitType = ${ meta.astunit[U] }

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

    inline def bu: Unit =
        ${ meta.bu }

object meta:
    import scala.unchecked
    import scala.language.implicitConversions

    def bu(using Quotes): Expr[Unit] =
        import quotes.reflect.*
        val m = baseunittree(fqTypeRepr("coulomb.units.si.Meter"))
        println(s"m= $m")
        println(s"m= ${m.symbol.fullName}")
        val t = symbolValueType(fqFieldSymbol(m.symbol.fullName))
        println(s"t= ${t.show}")
        /*
        val s2 = fqFieldSymbol("coulomb.units.si.ctx_unit_Meter")
        val s3 = fqFieldSymbol("coulomb.units.us.ctx_unit_Meter")
        println(s"${s2.tree.show}")
        println(s"${s3.tree.show}")
        println(s"${s2.typeRef}")
        println(s"${s3.typeRef}")
        println(s"${s2.termRef}")
        println(s"${s3.termRef}")
        val r2 = Ref.term(s2.termRef)
        println(s"$r2")
        val r3 = Ref.term(s3.termRef)
        println(s"$r3")
        val u2 = r2.underlying
        println(s"${u2.show}")
        val u3 = r3.underlying
        println(s"${u3.show}")
        val t2 = symbolValueType(fqFieldSymbol("coulomb.units.si$.ctx_unit_Meter"))
        println(s"${t2.show}")
        println(s"${t2.widen.show}")
        val t3 = symbolValueType(fqFieldSymbol("coulomb.units.us$.ctx_unit_Meter"))
        println(s"${t3.show}")
        println(s"${t3.widen.show}")
         */
        '{ () }

    def baseunittree(using Quotes)(
        u: quotes.reflect.TypeRepr
    ): quotes.reflect.Tree =
        import quotes.reflect.*
        Implicits.search(
            TypeRepr
                .of[coulomb.define.BaseUnit]
                .appliedTo(List(u, TypeBounds.empty, TypeBounds.empty))
        ) match
            case iss: ImplicitSearchSuccess => iss.tree
            case _                          => Literal(UnitConstant())

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

        val g = '{ given given_test: String = "foooooo" }
        println(s"g= ${g.show}")

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
        tr.memberType(sym).widen

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
                    s"""fqTypeRepr: bad path ${path.mkString(
                            "."
                        )} at ${path.last}"""
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
                // println(s"\nsymbol $q")
                // look for modules first
                // this includes packages and objects
                val qt = q.declarations.filter { x =>
                    (x.name == tail.head) && x.flags.is(Flags.Module)
                }
                // println(s"${qt.map{x => (x, x.flags.show)}}")
                val tt = q.declaredFields.filter(_.name == tail.head)
                // println(s"${tt.map{x => (x, x.flags.show)}}")
                // there are sometimes two symbols representing a module
                // is the difference important to this function?
                if (qt.length > 0) work(qt.head, tail.tail)
                else
                    // if no module exists, look for declared symbol
                    val f = q.declarations.filter { x => x.name == tail.head }
                    // println(s"${f.map{x => (x, x.flags.show)}}")
                    // expect a unique field declaration in this case
                    if ((f.length == 1) && (tail.length == 1)) f.head
                    else
                        // if we cannot find a field here, it is a failure
                        report.errorAndAbort(
                            s"""fqFieldSymbol: bad path ${path.mkString(
                                    "."
                                )} at ${tail.head}"""
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

    def astunit[T](using Quotes, Type[T]): Expr[UnitAST.UnitType] =
        import quotes.reflect.*
        val tr = TypeRepr.of[T]
        // filtering is a hack - these dollar sign variant symbols do not
        // show up in my fqTypRepr navigation
        val path = tr.dealias.typeSymbol.fullName.filter(_ != '$')
        '{ UnitAST.UnitType(${ Expr(path) }) }

    def fqtn[T](using Quotes, Type[T]): Expr[String] =
        import quotes.reflect.*
        val tr = TypeRepr.of[T]
        Expr(tr.dealias.typeSymbol.fullName)

    def m[T](using Quotes, Type[T]): Expr[Map[String, String]] =
        val mm = Map("a" -> "b")
        Expr(mm)
