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

package coulomb.infra.runtime

import scala.quoted.*
import scala.util.{Try, Success, Failure}

import coulomb.*
import coulomb.rational.Rational

object meta:
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.infra.meta.{*, given}
    import coulomb.conversion.coefficients.coefficientRational

    given ctx_RuntimeUnitConstToExpr: ToExpr[RuntimeUnit.UnitConst] with
        def apply(uc: RuntimeUnit.UnitConst)(using
            Quotes
        ): Expr[RuntimeUnit.UnitConst] =
            '{ RuntimeUnit.UnitConst(${ Expr(uc.value) }) }

    given ctx_RuntimeUnitTypeToExpr: ToExpr[RuntimeUnit.UnitType] with
        def apply(ut: RuntimeUnit.UnitType)(using
            Quotes
        ): Expr[RuntimeUnit.UnitType] =
            '{ RuntimeUnit.UnitType(${ Expr(ut.path) }) }

    given ctx_RuntimeUnitToExpr: ToExpr[RuntimeUnit] with
        def apply(rtu: RuntimeUnit)(using Quotes): Expr[RuntimeUnit] =
            rtu match
                case uc: RuntimeUnit.UnitConst => Expr(uc)
                case ut: RuntimeUnit.UnitType  => Expr(ut)
                case RuntimeUnit.Mul(l, r) =>
                    '{ RuntimeUnit.Mul(${ Expr(l) }, ${ Expr(r) }) }
                case RuntimeUnit.Div(n, d) =>
                    '{ RuntimeUnit.Div(${ Expr(n) }, ${ Expr(d) }) }
                case RuntimeUnit.Pow(b, e) =>
                    '{ RuntimeUnit.Pow(${ Expr(b) }, ${ Expr(e) }) }

    def coefStaging(uf: RuntimeUnit, ut: RuntimeUnit)(using
        staging.Compiler
    ): Either[String, Rational] =
        Try {
            staging.run {
                import quotes.reflect.*
                (rtuTypeRepr(uf).asType, rtuTypeRepr(ut).asType) match
                    case ('[f], '[t]) => '{ coefficientRational[f, t] }
            }
        } match
            case Success(coef) => Right(coef)
            case Failure(e)    => Left(e.getMessage)

    inline def crExpr[UT](
        cr: CoefficientRuntime,
        uf: RuntimeUnit
    ): Either[String, Rational] =
        ${ crExprMeta[UT]('cr, 'uf) }

    def crExprMeta[UT](cr: Expr[CoefficientRuntime], uf: Expr[RuntimeUnit])(
        using
        Quotes,
        Type[UT]
    ): Expr[Either[String, Rational]] =
        import quotes.reflect.*
        val ut = typeReprRTU(TypeRepr.of[UT])
        '{ ${ cr }.coefficientRational($uf, ${ Expr(ut) }) }

    def unitRTU[U](using Quotes, Type[U]): Expr[RuntimeUnit] =
        import quotes.reflect.*
        Expr(typeReprRTU(TypeRepr.of[U]))

    def rtuTypeRepr(using Quotes)(
        rtu: RuntimeUnit
    ): quotes.reflect.TypeRepr =
        import quotes.reflect.*
        rtu match
            case RuntimeUnit.UnitConst(value) => rationalTE(value)
            case RuntimeUnit.UnitType(path)   => fqTypeRepr(path)
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
            case rationalTE(v) =>
                RuntimeUnit.UnitConst(v)
            case ut => typeReprUT(ut)

    def typeReprUT(using Quotes)(
        tr: quotes.reflect.TypeRepr
    ): RuntimeUnit.UnitType =
        import quotes.reflect.*
        // should add checking for types with type-args here
        // possibly an explicit non dealiasing policy here would allow
        // parameterized types to be handled via typedef aliases?
        RuntimeUnit.UnitType(tr.typeSymbol.fullName)

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
                    val f = q.declarations.filter { x =>
                        x.name == tail.head
                    }
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
