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

package coulomb.conversion.spire

import spire.math.{ Rational, SafeLong }

import coulomb.rational.{ Rational => CoulombRational }

object coefficients:
    inline def coefficientRational[UF, UT]: Rational = ${ meta.coefficientSpireRational[UF, UT] }
    inline def coefficientBigDecimal[UF, UT]: BigDecimal = ${ meta.coefficientBigDecimal[UF, UT] }

    inline def deltaOffsetRational[U, B]: Rational = ${ meta.deltaOffsetSpireRational[U, B] }
    inline def deltaOffsetBigDecimal[U, B]: BigDecimal = ${ meta.deltaOffsetBigDecimal[U, B] }

    object meta:
        import scala.quoted.*
        import coulomb.infra.meta.{*, given}

        import scala.language.implicitConversions

        given ctx_SafeLongToExpr: ToExpr[SafeLong] with
            def apply(s: SafeLong)(using Quotes): Expr[SafeLong] = s match
                case v if (v == SafeLong.zero) => '{ SafeLong.zero }
                case v if (v == SafeLong.one) => '{ SafeLong.one }
                case v if (v.isValidLong) => '{ SafeLong(${Expr(s.getLong.get)}) }
                case _ => '{ SafeLong(${Expr(s.toBigInt)}) }

        given ctx_SpireRationalToExpr: ToExpr[Rational] with
            def apply(r: Rational)(using Quotes): Expr[Rational] = r match
                case v if (v == Rational.zero) => '{ Rational.zero }
                case v if (v == Rational.one) => '{ Rational.one }
                case _ => '{ Rational(${Expr(r.numerator)}, ${Expr(r.denominator)}) }

        def coefficientSpireRational[UF, UT](using Quotes, Type[UF], Type[UT]): Expr[Rational] =
            import quotes.reflect.*
            val c: CoulombRational = coef(TypeRepr.of[UF], TypeRepr.of[UT])
            Expr(Rational(c.n, c.d))

        def coefficientBigDecimal[UF, UT](using Quotes, Type[UF], Type[UT]): Expr[BigDecimal] =
            import quotes.reflect.*
            val c: CoulombRational = coef(TypeRepr.of[UF], TypeRepr.of[UT])
            val bd: BigDecimal = Rational(c.n, c.d).toBigDecimal(java.math.MathContext.DECIMAL128)
            Expr(bd)

        def deltaOffsetSpireRational[U, B](using Quotes, Type[U], Type[B]): Expr[Rational] =
            import quotes.reflect.*
            val doff: CoulombRational = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(Rational(doff.n, doff.d))

        def deltaOffsetBigDecimal[U, B](using Quotes, Type[U], Type[B]): Expr[BigDecimal] =
            import quotes.reflect.*
            val doff: CoulombRational = offset(TypeRepr.of[U], TypeRepr.of[B])
            val bd: BigDecimal = Rational(doff.n, doff.d).toBigDecimal(java.math.MathContext.DECIMAL128)
            Expr(bd)
