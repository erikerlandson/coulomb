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

package coulomb.runtime

import scala.quoted.*

import coulomb.*
import coulomb.syntax.*
import coulomb.rational.Rational

sealed abstract class RuntimeUnit:
    def *(rhs: RuntimeUnit): RuntimeUnit.Mul = RuntimeUnit.Mul(this, rhs)
    def /(den: RuntimeUnit): RuntimeUnit.Div = RuntimeUnit.Div(this, den)
    def ^(e: Rational): RuntimeUnit.Pow = RuntimeUnit.Pow(this, e)

object RuntimeUnit:
    case class UnitType(path: String) extends RuntimeUnit
    case class Mul(lhs: RuntimeUnit, rhs: RuntimeUnit) extends RuntimeUnit
    case class Div(num: RuntimeUnit, den: RuntimeUnit) extends RuntimeUnit
    case class Pow(b: RuntimeUnit, e: Rational) extends RuntimeUnit
    inline def of[U]: RuntimeUnit = ${ meta.unitRTU[U] }

package syntax {
    import scala.util.{Try, Success, Failure}
    import coulomb.conversion.*

    extension [V](vu: (V, RuntimeUnit))
        inline def toQuantity[U](using
            vi: ValueConversion[V, Rational],
            vo: ValueConversion[Rational, V]
        ): Either[String, Quantity[V, U]] =
            val (v, u) = vu
            Try(vo(meta.kernelExpr[U](vi(v), u)).withUnit[U]) match
                case Success(q) => Right(q)
                case Failure(e) => Left(e.getMessage)
}

object meta:
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.conversion.coefficients.{meta => _, *}
    import coulomb.infra.meta.{*, given}

    given ctx_RuntimeUnitToExpr: ToExpr[RuntimeUnit] with
        def apply(rtu: RuntimeUnit)(using Quotes): Expr[RuntimeUnit] =
            rtu match
                case RuntimeUnit.UnitType(path) =>
                    '{ RuntimeUnit.UnitType(${ Expr(path) }) }
                case RuntimeUnit.Mul(l, r) =>
                    '{ RuntimeUnit.Mul(${ Expr(l) }, ${ Expr(r) }) }
                case RuntimeUnit.Div(n, d) =>
                    '{ RuntimeUnit.Div(${ Expr(n) }, ${ Expr(d) }) }
                case RuntimeUnit.Pow(b, e) =>
                    '{ RuntimeUnit.Pow(${ Expr(b) }, ${ Expr(e) }) }

    inline def kernelExpr[U](v: Rational, u: RuntimeUnit): Rational =
        ${ kernelExprMeta[U]('v, 'u) }

    def kernelExprMeta[U](v: Expr[Rational], u: Expr[RuntimeUnit])(using
        Quotes,
        Type[U]
    ): Expr[Rational] =
        import quotes.reflect.*
        val cmp = stagingCompiler
        val rtuU = typeReprRTU(TypeRepr.of[U])
        '{ kernelRuntime($v, $u, ${ Expr(rtuU) })(using $cmp) }

    def kernelRuntime(v: Rational, rtuF: RuntimeUnit, rtuT: RuntimeUnit)(using
        staging.Compiler
    ): Rational =
        staging.run {
            import quotes.reflect.*
            (rtuTypeRepr(rtuF).asType, rtuTypeRepr(rtuT).asType) match
                case ('[uf], '[ut]) =>
                    '{ ${ Expr(v) } * coefficientRational[uf, ut] }
        }

    def stagingCompiler(using Quotes): Expr[staging.Compiler] =
        import quotes.reflect.*
        Implicits.search(TypeRepr.of[staging.Compiler]) match
            case iss: ImplicitSearchSuccess =>
                iss.tree.asExprOf[staging.Compiler]
            case _ =>
                report.errorAndAbort("no 'given' staging.Compiler is in scope")
                // I'm not even sorry.
                null.asInstanceOf[Expr[staging.Compiler]]

    def unitRTU[U](using Quotes, Type[U]): Expr[RuntimeUnit] =
        import quotes.reflect.*
        Expr(typeReprRTU(TypeRepr.of[U]))

    def rtuTypeRepr(using Quotes)(
        rtu: RuntimeUnit
    ): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        rtu match
            case RuntimeUnit.UnitType(path) => fqTypeRepr(path)
            case RuntimeUnit.Mul(l, r) =>
                val ltr = rtuTypeRepr(l)
                val rtr = rtuTypeRepr(r)
                TypeRepr.of[coulomb.`*`].appliedTo(List(ltr, rtr))
            case RuntimeUnit.Div(n, d) =>
                val ntr = rtuTypeRepr(n)
                val dtr = rtuTypeRepr(d)
                TypeRepr.of[coulomb.`/`].appliedTo(List(ntr, dtr))
            case RuntimeUnit.Pow(b, e) =>
                val btr = rtuTypeRepr(b)
                val etr = rationalTE(e)
                TypeRepr.of[coulomb.`^`].appliedTo(List(btr, etr))

    def typeReprRTU(using Quotes)(
        tr: quotes.reflect.TypeRepr
    ): RuntimeUnit =
        import quotes.reflect.*
        tr match
            case AppliedType(op, List(lu, ru))
                if (op =:= TypeRepr.of[coulomb.`*`]) =>
                RuntimeUnit.Mul(typeReprRTU(lu), typeReprRTU(ru))
            case AppliedType(op, List(lu, ru))
                if (op =:= TypeRepr.of[coulomb.`/`]) =>
                RuntimeUnit.Div(typeReprRTU(lu), typeReprRTU(ru))
            case AppliedType(op, List(b, e))
                if (op =:= TypeRepr.of[coulomb.`^`]) =>
                val rationalTE(ev) = e: @unchecked
                RuntimeUnit.Pow(typeReprRTU(b), ev)
            case t =>
                // should add checking for types with type-args here
                // possibly an explicit non dealirtuing policy here would allow
                // parameterized types to be handled via typedef aliases?
                RuntimeUnit.UnitType(t.typeSymbol.fullName)

    def fqTypeRepr(using Quotes)(path: String): quotes.reflect.TypeRepr =
        fqTypeRepr(path.split('.').toIndexedSeq)

    def fqFieldSymbol(using Quotes)(path: String): quotes.reflect.Symbol =
        fqFieldSymbol(path.split('.').toIndexedSeq)

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

    def fqFieldSymbol(using Quotes)(
        path: Seq[String]
    ): quotes.reflect.Symbol =
        import quotes.reflect.*
        def work(q: Symbol, tail: Seq[String]): Symbol =
            if (tail.isEmpty) q
            else
                // look for modules first
                // this includes packages and objects
                val qt = q.declarations.filter { x =>
                    (x.name == tail.head) && x.flags.is(Flags.Module)
                }
                // there are sometimes two symbols representing a module
                // is the difference important to this function?
                if (qt.length > 0) work(qt.head, tail.tail)
                else
                    // if no module exists, look for declared symbol
                    val f = q.declarations.filter { x => x.name == tail.head }
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
