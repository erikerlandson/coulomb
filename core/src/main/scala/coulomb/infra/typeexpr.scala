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

package coulomb.infra

object typeexpr:
    import scala.util.{Try, Success}
    import scala.quoted.*
    import scala.language.implicitConversions

    import spire.math.Rational

    import coulomb.infra.meta.{rationalTE, bigintTE, g_RationalToExpr, typestr}
    import coulomb.infra.utils.*

    inline def asInt[E]: Int = ${ teToInt[E] }

    inline def asPosInt[E]: Int = ${ teToPosInt[E] }

    inline def asNonNegInt[E]: Int = ${ teToNonNegInt[E] }

    inline def asDouble[E]: Double = ${ teToDouble[E] }

    inline def asBigInt[E]: BigInt = ${ teToBigInt[E] }

    inline def asRational[E]: Rational = ${ teToRational[E] }

    inline def teq[T1, T2]: Boolean = ${ teqImpl[T1, T2] }

    inline def uniteq[U1, U2]: Boolean = ${ uniteqImpl[U1, U2] }

    private def teqImpl[T1, T2](using
        Quotes,
        Type[T1],
        Type[T2]
    ): Expr[Boolean] =
        import quotes.reflect.*
        if (TypeRepr.of[T1] =:= TypeRepr.of[T2])
            '{ true }
        else
            '{ false }

    private def uniteqImpl[U1, U2](using
        Quotes,
        Type[U1],
        Type[U2]
    ): Expr[Boolean] =
        import quotes.reflect.*
        import coulomb.infra.meta.*
        val (u1, u2) = (TypeRepr.of[U1], TypeRepr.of[U2])
        if (u1 =:= u2) '{ true }
        else
            given sigmode: SigMode = SigMode.Canonical
            val (rcoef, rsig) = cansig(
                TypeRepr.of[coulomb.`/`].appliedTo(List(u1, u2))
            )
            if ((rsig == Nil) && (rcoef == Rational.one)) '{ true }
            else '{ false }

    private def teToInt[E](using Quotes, Type[E]): Expr[Int] =
        import quotes.reflect.*
        TypeRepr.of[E] match
            case ConstantType(IntConstant(v)) => Expr(v)
            case rationalTE(v)
                if ((v.denominator == 1) && v.numerator.isValidInt) =>
                Expr(v.numerator.toInt)
            case tr =>
                report.error(s"type ${typestr(tr)} cannot be converted to Int")
                Expr(0)

    private def teToPosInt[E](using Quotes, Type[E]): Expr[Int] =
        import quotes.reflect.*
        TypeRepr.of[E] match
            case ConstantType(IntConstant(v)) if (v > 0) => Expr(v)
            case rationalTE(v)
                if ((v.denominator == 1) && v.numerator.isValidInt && (v.numerator > 0)) =>
                Expr(v.numerator.toInt)
            case tr =>
                report.error(
                    s"type ${typestr(tr)} cannot be converted to positive Int"
                )
                Expr(0)

    private def teToNonNegInt[E](using Quotes, Type[E]): Expr[Int] =
        import quotes.reflect.*
        TypeRepr.of[E] match
            case ConstantType(IntConstant(v)) if (v >= 0) => Expr(v)
            case rationalTE(v)
                if ((v.denominator == 1) && v.numerator.isValidInt && (v.numerator >= 0)) =>
                Expr(v.numerator.toInt)
            case tr =>
                report.error(
                    s"type ${typestr(tr)} cannot be converted to non-negative Int"
                )
                Expr(0)

    private def teToDouble[E](using Quotes, Type[E]): Expr[Double] =
        import quotes.reflect.*
        TypeRepr.of[E] match
            case ConstantType(DoubleConstant(v)) => Expr(v)
            case rationalTE(v)                   => Expr(v.toDouble)
            case tr =>
                report.error(
                    s"type ${typestr(tr)} cannot be converted to Double"
                )
                Expr(0.0)

    private def teToRational[E](using Quotes, Type[E]): Expr[Rational] =
        import quotes.reflect.*
        TypeRepr.of[E] match
            case rationalTE(v) => Expr(v)
            case tr =>
                report.error(
                    s"type ${typestr(tr)} cannot be converted to Rational"
                )
                Expr(Rational.zero)

    private def teToBigInt[E](using Quotes, Type[E]): Expr[BigInt] =
        import quotes.reflect.*
        TypeRepr.of[E] match
            case bigintTE(v) => Expr(v)
            case tr =>
                report.error(
                    s"type ${typestr(tr)} cannot be converted to BigInt"
                )
                Expr(BigInt(0))
