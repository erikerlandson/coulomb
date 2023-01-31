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

sealed abstract class UnitAST:
    def *(rhs: UnitAST): UnitAST.Mul = UnitAST.Mul(this, rhs)
    def /(den: UnitAST): UnitAST.Div = UnitAST.Div(this, den)
    def ^(e: Rational): UnitAST.Pow = UnitAST.Pow(this, e)

object UnitAST:
    case class UnitType(path: String) extends UnitAST
    case class Mul(lhs: UnitAST, rhs: UnitAST) extends UnitAST
    case class Div(num: UnitAST, den: UnitAST) extends UnitAST
    case class Pow(b: UnitAST, e: Rational) extends UnitAST
    inline def of[U]: UnitAST = ${ meta.unitAST[U] }

package syntax {
    import coulomb.conversion.*

    extension [V](v: V)
        inline def withUnitRuntime[U](u: UnitAST)(using
            vi: ValueConversion[V, Rational],
            vo: ValueConversion[Rational, V]
        ): Quantity[V, U] =
            vo(meta.kernelExpr[U](vi(v), u)).withUnit[U]
}

object meta:
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.conversion.coefficients.{meta => _, *}
    import coulomb.infra.meta.{*, given}

    given ctx_UnitASTToExpr: ToExpr[UnitAST] with
        def apply(ast: UnitAST)(using Quotes): Expr[UnitAST] =
            ast match
                case UnitAST.UnitType(path) =>
                    '{ UnitAST.UnitType(${ Expr(path) }) }
                case UnitAST.Mul(l, r) =>
                    '{ UnitAST.Mul(${ Expr(l) }, ${ Expr(r) }) }
                case UnitAST.Div(n, d) =>
                    '{ UnitAST.Div(${ Expr(n) }, ${ Expr(d) }) }
                case UnitAST.Pow(b, e) =>
                    '{ UnitAST.Pow(${ Expr(b) }, ${ Expr(e) }) }

    inline def kernelExpr[U](v: Rational, u: UnitAST): Rational =
        ${ kernelExprMeta[U]('v, 'u) }

    def kernelExprMeta[U](v: Expr[Rational], u: Expr[UnitAST])(using
        Quotes,
        Type[U]
    ): Expr[Rational] =
        import quotes.reflect.*
        val cmp = stagingCompiler
        val astU = typeReprAST(TypeRepr.of[U])
        '{ kernelRuntime($v, $u, ${ Expr(astU) })(using $cmp) }

    def kernelRuntime(v: Rational, astF: UnitAST, astT: UnitAST)(using
        staging.Compiler
    ): Rational =
        staging.run {
            import quotes.reflect.*
            (astTypeRepr(astF).asType, astTypeRepr(astT).asType) match
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

    def unitAST[U](using Quotes, Type[U]): Expr[UnitAST] =
        import quotes.reflect.*
        Expr(typeReprAST(TypeRepr.of[U]))

    def astTypeRepr(using Quotes)(
        ast: UnitAST
    ): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        ast match
            case UnitAST.UnitType(path) => fqTypeRepr(path)
            case UnitAST.Mul(l, r) =>
                val ltr = astTypeRepr(l)
                val rtr = astTypeRepr(r)
                TypeRepr.of[coulomb.`*`].appliedTo(List(ltr, rtr))
            case UnitAST.Div(n, d) =>
                val ntr = astTypeRepr(n)
                val dtr = astTypeRepr(d)
                TypeRepr.of[coulomb.`/`].appliedTo(List(ntr, dtr))
            case UnitAST.Pow(b, e) =>
                val btr = astTypeRepr(b)
                val etr = rationalTE(e)
                TypeRepr.of[coulomb.`^`].appliedTo(List(btr, etr))

    def typeReprAST(using Quotes)(
        tr: quotes.reflect.TypeRepr
    ): UnitAST =
        import quotes.reflect.*
        tr match
            case AppliedType(op, List(lu, ru))
                if (op =:= TypeRepr.of[coulomb.`*`]) =>
                UnitAST.Mul(typeReprAST(lu), typeReprAST(ru))
            case AppliedType(op, List(lu, ru))
                if (op =:= TypeRepr.of[coulomb.`/`]) =>
                UnitAST.Div(typeReprAST(lu), typeReprAST(ru))
            case AppliedType(op, List(b, e))
                if (op =:= TypeRepr.of[coulomb.`^`]) =>
                val rationalTE(ev) = e: @unchecked
                UnitAST.Pow(typeReprAST(b), ev)
            case t =>
                // should add checking for types with type-args here
                // possibly an explicit non dealiasting policy here would allow
                // parameterized types to be handled via typedef aliases?
                UnitAST.UnitType(t.typeSymbol.fullName)

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
