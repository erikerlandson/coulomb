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
import scala.util.{Try, Success, Failure}

import coulomb.{infra => _, *}
import coulomb.syntax.*
import coulomb.rational.Rational
import coulomb.conversion.*

sealed abstract class RuntimeUnit:
    def *(rhs: RuntimeUnit): RuntimeUnit.Mul = RuntimeUnit.Mul(this, rhs)
    def /(den: RuntimeUnit): RuntimeUnit.Div = RuntimeUnit.Div(this, den)
    def ^(e: Rational): RuntimeUnit.Pow = RuntimeUnit.Pow(this, e)
    override def toString: String =
        def paren(s: String, tl: Boolean): String =
            if (tl) s else s"($s)"
        def work(u: RuntimeUnit, tl: Boolean = false): String =
            u match
                case RuntimeUnit.UnitType(path) => path.split('.').last
                case RuntimeUnit.Mul(l, r) => paren(s"${work(l)}*${work(r)}", tl)
                case RuntimeUnit.Div(n, d) => paren(s"${work(n)}/${work(d)}", tl)
                case RuntimeUnit.Pow(b, e) => paren(s"${work(b)}^$e", tl)
        work(this, tl = true)

object RuntimeUnit:
    case class UnitType(path: String) extends RuntimeUnit
    case class Mul(lhs: RuntimeUnit, rhs: RuntimeUnit) extends RuntimeUnit
    case class Div(num: RuntimeUnit, den: RuntimeUnit) extends RuntimeUnit
    case class Pow(b: RuntimeUnit, e: Rational) extends RuntimeUnit
    inline def of[U]: RuntimeUnit = ${ infra.meta.unitRTU[U] }

def coefficient[V](uf: RuntimeUnit, ut: RuntimeUnit)(using
    scmp: staging.Compiler,
    vc: ValueConversion[Rational, V]
): Either[String, V] =
    import coulomb.runtime.conversion.coefficients.coefficientRational
    coefficientRational(uf, ut).map(vc)

case class RuntimeQuantity[V](value: V, unit: RuntimeUnit)

object RuntimeQuantity:
    inline def apply[V, U](q: Quantity[V, U]): RuntimeQuantity[V] =
        RuntimeQuantity(q.value, RuntimeUnit.of[U])

extension [VL](ql: RuntimeQuantity[VL])
    inline def toQuantity[VR, UR](using
        scmp: staging.Compiler,
        vi: ValueConversion[VL, Rational],
        vo: ValueConversion[Rational, VR]
    ): Either[String, Quantity[VR, UR]] =
        infra.meta.coefExpr[UR](ql.unit).map { coef =>
            vo(coef * vi(ql.value)).withUnit[UR]
        }

package syntax {
    extension [V](v: V)
        inline def withRuntimeUnit(u: RuntimeUnit): RuntimeQuantity[V] =
            RuntimeQuantity(v, u)
}

package conversion {
    abstract class RuntimeUnitConversion[V] extends (V => V)
    object RuntimeUnitConversion:
        def apply[V](uf: RuntimeUnit, ut: RuntimeUnit)(using
            scmp: staging.Compiler,
            vi: ValueConversion[V, Rational],
            vo: ValueConversion[Rational, V]
        ): Either[String, RuntimeUnitConversion[V]] =
            coefficients.coefficientRational(uf, ut).map { coef =>
                new RuntimeUnitConversion[V] {
                    def apply(v: V): V = vo(coef * vi(v))
                }
            }

    object coefficients:
        import coulomb.runtime.infra.meta.*
        import coulomb.conversion.coefficients.{coefficientRational => staticCR}

        def coefficientRational(uf: RuntimeUnit, ut: RuntimeUnit)(using
            staging.Compiler
        ): Either[String, Rational] =
            Try {
                staging.run {
                    import quotes.reflect.*
                    (rtuTypeRepr(uf).asType, rtuTypeRepr(ut).asType) match
                        case ('[f], '[t]) => '{ staticCR[f, t] }
                }
            } match
                case Success(coef) => Right(coef)
                case Failure(e)    => Left(e.getMessage)
}

package infra {

    object meta:
        import scala.unchecked
        import scala.language.implicitConversions

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

        inline def coefExpr[UT](uf: RuntimeUnit)(using
            scmp: staging.Compiler
        ): Either[String, Rational] =
            ${ coefExprMeta[UT]('uf, 'scmp) }

        def coefExprMeta[UT](
            uf: Expr[RuntimeUnit],
            scmp: Expr[staging.Compiler]
        )(using
            Quotes,
            Type[UT]
        ): Expr[Either[String, Rational]] =
            import quotes.reflect.*
            import coulomb.runtime.conversion.coefficients.coefficientRational
            val ut = typeReprRTU(TypeRepr.of[UT])
            '{ coefficientRational($uf, ${ Expr(ut) })(using $scmp) }

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
}
