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

import coulomb.rational.Rational

object coefficients:
    inline def coefficientRational[U1, U2]: Rational = ${ meta.coefficientRational[U1, U2] }
    inline def coefficientDouble[U1, U2]: Double = ${ meta.coefficientDouble[U1, U2] }
    inline def coefficientFloat[U1, U2]: Float = ${ meta.coefficientFloat[U1, U2] }

    inline def coefficientNumDouble[U1, U2]: Double = ${ meta.coefficientNumDouble[U1, U2] }
    inline def coefficientDenDouble[U1, U2]: Double = ${ meta.coefficientDenDouble[U1, U2] }

    inline def deltaOffsetRational[U, B]: Rational = ${ meta.deltaOffsetRational[U, B] }
    inline def deltaOffsetDouble[U, B]: Double = ${ meta.deltaOffsetDouble[U, B] }
    inline def deltaOffsetFloat[U, B]: Float = ${ meta.deltaOffsetFloat[U, B] }

    object meta:
        import scala.quoted.*
        import coulomb.infra.meta.{*, given}

        def coefficientRational[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Rational] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c)

        def coefficientDouble[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.toDouble)

        def coefficientFloat[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Float] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.toFloat)

        def coefficientNumDouble[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.n.toDouble)

        def coefficientDenDouble[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Double] =
            import quotes.reflect.*
            val c = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(c.d.toDouble)

        def deltaOffsetRational[U, B](using Quotes, Type[U], Type[B]): Expr[Rational] =
            import quotes.reflect.*
            val doff = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(doff)

        def deltaOffsetDouble[U, B](using Quotes, Type[U], Type[B]): Expr[Double] =
            import quotes.reflect.*
            val doff = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(doff.toDouble)

        def deltaOffsetFloat[U, B](using Quotes, Type[U], Type[B]): Expr[Float] =
            import quotes.reflect.*
            val doff = offset(TypeRepr.of[U], TypeRepr.of[B])
            Expr(doff.toFloat)

