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

package coulomb.conversion

import spire.math.Rational

object coefficients:
    inline def coefficientRational[U1, U2]: Rational = ${
        meta.coefficientRational[U1, U2]
    }
    inline def coefficientDouble[U1, U2]: Double = ${
        meta.coefficientDouble[U1, U2]
    }
    inline def coefficientDoubleJ[U1, U2]: java.lang.Double = ${
        meta.coefficientDoubleJ[U1, U2]
    }
    inline def coefficientFloat[U1, U2]: Float = ${
        meta.coefficientFloat[U1, U2]
    }
    inline def coefficientBigDecimal[UF, UT]: BigDecimal = ${
        meta.coefficientBigDecimal[UF, UT]
    }

    inline def coefficientNumDouble[U1, U2]: Double = ${
        meta.coefficientNumDouble[U1, U2]
    }
    inline def coefficientDenDouble[U1, U2]: Double = ${
        meta.coefficientDenDouble[U1, U2]
    }

    inline def deltaOffsetRational[U, B]: Rational = ${
        meta.deltaOffsetRational[U, B]
    }
    inline def deltaOffsetDouble[U, B]: Double = ${
        meta.deltaOffsetDouble[U, B]
    }
    inline def deltaOffsetFloat[U, B]: Float = ${ meta.deltaOffsetFloat[U, B] }

    inline def deltaOffsetBigDecimal[U, B]: BigDecimal = ${
        meta.deltaOffsetBigDecimal[U, B]
    }

    object meta:
        import scala.quoted.*
        import coulomb.infra.meta.{*, given}

        given ctx_JavaDoubleToExpr: ToExpr[java.lang.Double] with
            def apply(v: java.lang.Double)(using Quotes): Expr[java.lang.Double] =
                val vd: Double = v
                '{ java.lang.Double.valueOf(${Expr(vd)}) }

        def coefficientRational[U1, U2](using
            Quotes,
            Type[U1],
            Type[U2]
        ): Expr[Rational] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c)

        def coefficientDouble[U1, U2](using
            Quotes,
            Type[U1],
            Type[U2]
        ): Expr[Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.toDouble)

        def coefficientDoubleJ[U1, U2](using
            Quotes,
            Type[U1],
            Type[U2]
        ): Expr[java.lang.Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(java.lang.Double.valueOf(c.toDouble))

        def coefficientFloat[U1, U2](using
            Quotes,
            Type[U1],
            Type[U2]
        ): Expr[Float] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.toFloat)

        def coefficientBigDecimal[UF, UT](using
            Quotes,
            Type[UF],
            Type[UT]
        ): Expr[BigDecimal] =
            import quotes.reflect.*
            val c: Rational = coef(TypeRepr.of[UF], TypeRepr.of[UT])
            val bd: BigDecimal = c.toBigDecimal(
                java.math.MathContext.DECIMAL128
            )
            Expr(bd)

        def coefficientNumDouble[U1, U2](using
            Quotes,
            Type[U1],
            Type[U2]
        ): Expr[Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.numerator.toDouble)

        def coefficientDenDouble[U1, U2](using
            Quotes,
            Type[U1],
            Type[U2]
        ): Expr[Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.denominator.toDouble)

        def deltaOffsetRational[U, B](using
            Quotes,
            Type[U],
            Type[B]
        ): Expr[Rational] =
            import quotes.reflect.*
            val doff = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(doff)

        def deltaOffsetDouble[U, B](using
            Quotes,
            Type[U],
            Type[B]
        ): Expr[Double] =
            import quotes.reflect.*
            val doff = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(doff.toDouble)

        def deltaOffsetFloat[U, B](using
            Quotes,
            Type[U],
            Type[B]
        ): Expr[Float] =
            import quotes.reflect.*
            val doff = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(doff.toFloat)

        def deltaOffsetBigDecimal[U, B](using
            Quotes,
            Type[U],
            Type[B]
        ): Expr[BigDecimal] =
            import quotes.reflect.*
            val doff: Rational = offset(TypeRepr.of[U], TypeRepr.of[B])
            val bd: BigDecimal =
                doff.toBigDecimal(
                    java.math.MathContext.DECIMAL128
                )
            Expr(bd)
