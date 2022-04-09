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
    inline def coefficientRational[U1, U2]: Rational = ${ meta.coefficientRational[U1, U2] }

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

        given ctx_RationalToExpr: ToExpr[Rational] with
            def apply(r: Rational)(using Quotes): Expr[Rational] = r match
                case v if (v == Rational.zero) => '{ Rational.zero }
                case v if (v == Rational.one) => '{ Rational.one }
                case _ => '{ Rational(${Expr(r.numerator)}, ${Expr(r.denominator)}) }

        def coefficientRational[U1, U2](using Quotes, Type[U1], Type[U2]): Expr[Rational] =
            import quotes.reflect.*
            val c: CoulombRational = coef(TypeRepr.of[U1], TypeRepr.of[U2])
            Expr(Rational(c.n, c.d))
