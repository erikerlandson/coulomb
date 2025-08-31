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

package coulomb

/**
 * Represents the product of two unit expressions
 * @tparam L
 *   the left-hand unit subexpression
 * @tparam R
 *   the right-hand unit subexpression
 *   {{{
 * type AcreFoot = (Acre * Foot)
 *   }}}
 */
final type *[L, R]

/**
 * Represents unit division
 * @tparam L
 *   the left-hand unit subexpression (numerator)
 * @tparam R
 *   the right-hand unit subexpression (denominator)
 *   {{{
 * type MPS = (Meter / Second)
 *   }}}
 */
final type /[L, R]

/**
 * Represents raising unit expression B to rational power E
 * @tparam B
 *   a base unit expression
 * @tparam E
 *   a rational exponent
 *   {{{
 * type V = (Meter ^ 3)
 * type H = (Second ^ -1)
 * type R = (Meter ^ (1 / 2))
 *   }}}
 */
final type ^[B, E]

/**
 * Represents a value with an associated unit type
 * @tparam V
 *   the raw value type
 * @tparam U
 *   the unit type
 */
opaque type Quantity[V, U] = V

/** Defines Quantity constructors and extension methods */
object Quantity:
    import scala.compiletime

    import algebra.ring.*
    import cats.kernel.Order

    import spire.math.{Fractional, Rational}

    import coulomb.infra.typeexpr
    import coulomb.conversion.*
    import coulomb.io.ShowUnit
    import coulomb.infra.SimplifiedUnit

    extension [V](v: V)
        /**
         * Lift a raw value into a unit quantity
         * @tparam U
         *   the desired unit type
         * @return
         *   a Quantity with given value and unit type
         *   {{{
         * val distance = (1.0).withUnit[Meter]
         *   }}}
         */
        inline def withUnit[U]: Quantity[V, U] = v

    extension [V, U, Q[V, U] <: Quantity[V, U]](q: Q[V, U])
        /**
         * extract the raw value of a unit quantity
         * @return
         *   the underlying value, stripped of its unit information
         *   {{{
         * val q = (1.5).withUnit[Meter]
         * q.value // => 1.5
         *   }}}
         */
        inline def value: V = q

        /**
         * returns a string representing this Quantity, using unit abbreviations
         * @example
         *   {{{
         * val q = (1.5).withUnit[Meter / Second]
         * q.show // => "1.5 m/s"
         *   }}}
         */
        inline def show: String = s"${q.value.toString} ${ShowUnit[U]}"

        /**
         * returns a string representing this Quantity, using full unit names
         * @example
         *   {{{
         * val q = (1.5).withUnit[Meter / Second]
         * q.showFull // => "1.5 meter/second"
         *   }}}
         */
        inline def showFull: String =
            s"${q.value.toString} ${ShowUnit.full[U]}"

        /**
         * convert a quantity to a new value type
         * @tparam VT
         *   the new value type to use
         * @return
         *   a new `Quantity` having value type `VT`
         * @example
         *   {{{
         * val q = (1.0).withUnit[Meter]
         * q.toValue[Int] // => Quantity[Meter](1)
         *   }}}
         */
        inline def toValue[VT]: Quantity[VT, U] =
            ValueConversion[V, VT](q)

        /**
         * convert a quantity to a new unit type
         * @tparam UT
         *   the new unit type
         * @return
         *   a new `Quantity` having unit type `UT`
         * @note
         *   attempting to convert to an incompatible unit will result in a
         *   compile error
         * @example
         *   {{{
         * val q = (1.0).withUnit[Meter ^ 3]
         * q.toUnit[Liter] // => Quantity[Liter](1000.0)
         * q.toUnit[Hectare] // => compile error
         *   }}}
         */
        inline def toUnit[UT]: Quantity[V, UT] =
            UnitConversion[V, U, UT](q)

        /**
         * negate the value of a `Quantity`
         * @return
         *   a `Quantity` having the negative of the original value
         * @example
         *   {{{
         * val q = 1.withUnit[Meter]
         * -q // => Quantity[Meter](-1)
         *   }}}
         */
        inline def unary_-(using
            alg: AdditiveGroup[V]
        ): Quantity[V, U] =
            alg.negate(q)

        /**
         * add this quantity to another
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the sum of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 1.0.withUnit[Meter]
         * val q2 = 1.0.withUnit[Yard]
         * q1 + q2 // => Quantity[Meter](1.9144)
         *   }}}
         * @note
         *   unit types `U` and `UR` must be convertable
         */
        inline def +[UR](qr: Quantity[V, UR])(using
            alg: AdditiveSemigroup[V]
        ): Quantity[V, U] =
            val qrv: V = UnitConversion[V, UR, U](qr)
            alg.plus(q, qrv)

        /**
         * subtract another quantity from this one
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the result of subtracting `qr` from this
         * @example
         *   {{{
         * val q1 = 1.0.withUnit[Meter]
         * val q2 = 1.0.withUnit[Yard]
         * q1 - q2 // => Quantity[Meter](0.0856)
         *   }}}
         * @note
         *   unit types `U` and `UR` must be convertable
         */
        inline def -[UR](qr: Quantity[V, UR])(using
            alg: AdditiveGroup[V]
        ): Quantity[V, U] =
            val qrv: V = UnitConversion[V, UR, U](qr)
            alg.minus(q, qrv)

        /**
         * multiply this quantity by another
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the product of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 2.0.withUnit[Meter]
         * val q2 = 3.0.withUnit[Meter]
         * q1 * q2 // => Quantity[Meter ^ 2](6.0)
         *   }}}
         */
        inline def *[UR](qr: Quantity[V, UR])(using
            alg: MultiplicativeSemigroup[V],
            su: SimplifiedUnit[U * UR]
        ): Quantity[V, su.UO] =
            alg.times(q, qr).withUnit[su.UO]

        /**
         * multiply this quantity by a unitless scalar value
         * @param v
         *   right hand scalar value
         * @return
         *   the product of this quantity with `v`
         * @example
         *   {{{
         * val q1 = 2.0.withUnit[Meter]
         * q1 * 3.0 // => Quantity[Meter](6.0)
         *   }}}
         */
        inline def *(v: V)(using
            alg: MultiplicativeSemigroup[V]
        ): Quantity[V, U] =
            alg.times(q, v).withUnit[U]

        /**
         * divide this quantity by another
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the quotient of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 3.0.withUnit[Meter]
         * val q2 = 2.0.withUnit[Second]
         * q1 / q2 // => Quantity[Meter / Second](1.5)
         *   }}}
         */
        inline def /[UR](qr: Quantity[V, UR])(using
            alg: MultiplicativeGroup[V],
            su: SimplifiedUnit[U / UR]
        ): Quantity[V, su.UO] =
            alg.div(q, qr).withUnit[su.UO]

        /**
         * divide this quantity by unitless scalar
         * @param v
         *   right hand scalar
         * @return
         *   the quotient of this quantity with `v`
         * @example
         *   {{{
         * val q1 = 3.0.withUnit[Meter]
         * q1 / 2.0 // => Quantity[Meter](1.5)
         *   }}}
         */
        inline def /(v: V)(using
            alg: MultiplicativeGroup[V]
        ): Quantity[V, U] =
            alg.div(q, v).withUnit[U]

        /**
         * divide this quantity by another, using truncating (integer) division
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the integral quotient of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 5.withUnit[Meter]
         * val q2 = 2.withUnit[Second]
         * q1.tquot(q2) // => Quantity[Meter / Second](2)
         *   }}}
         */
        inline def tquot[UR](qr: Quantity[V, UR])(using
            alg: TruncatedDivision[V],
            su: SimplifiedUnit[U / UR]
        ): Quantity[V, su.UO] =
            alg.tquot(q, qr).withUnit[su.UO]

        /**
         * raise this quantity to a rational or integer power
         * @tparam E
         *   the power, or exponent
         * @return
         *   this quantity raised to exponent `E`
         * @example
         *   {{{
         * val q = (2.0).withUnit[Meter]
         * q.pow[2]  // => Quantity[Meter ^ 2](4.0)
         * q.pow[1/2]  // => Quantity[Meter ^ (1/2)](1.4142135623730951)
         * q.pow[-1]  // => Quantity[1 / Meter](0.5)
         * q.pow[0]  // => Quantity[1](1.0)
         *   }}}
         * @note
         *   The type of exponent supported depends on the applicable algebra.
         *   Fractional[V] supports all rational exponents for `E`.
         *   MultiplicativeGroup[V] supports all integers.
         *   MultiplicativeMonoid[V] supports integers >= 0.
         *   MultiplicativeSemigroup[V] supports integers > 0.
         */
        inline def pow[E](using
            su: SimplifiedUnit[U ^ E]
        ): Quantity[V, su.UO] =
            val v: V = compiletime.summonFrom {
                case alg: Fractional[V] =>
                    // I'd prefer to use NRoot[V] for this, however
                    // spire defines NRoot on Int and Long, and these are
                    // not numerically safe, e.g. sqrt(2) => 1.
                    val e = typeexpr.asRational[E]
                    if ((e.denominator == 1) && (e.numerator.isValidInt))
                        alg.pow(q, e.numerator.toInt)
                    else
                        alg.fpow(q, alg.fromRational(e))
                case alg: MultiplicativeGroup[V] =>
                    alg.pow(q, typeexpr.asInt[E])
                case alg: MultiplicativeMonoid[V] =>
                    alg.pow(q, typeexpr.asNonNegInt[E])
                case alg: MultiplicativeSemigroup[V] =>
                    alg.pow(q, typeexpr.asPosInt[E])
                case _ =>
                    compiletime.error(
                        "no algebra in context that supports power"
                    )
            }
            v.withUnit[su.UO]

        /**
         * test this quantity for equality with another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value equals the left (after any conversions),
         *   false otherwise
         * @example
         *   {{{
         * val q1 = 1000.0.withUnit[Liter]
         * val q2 = 1.0.withUnit[Meter ^ 3]
         * q1 === q2 // => true
         *   }}}
         */
        inline def ===[UR](qr: Quantity[V, UR])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = UnitConversion[V, UR, U](qr)
            ord.compare(q, qrv) == 0

        /**
         * test this quantity for inequality with another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value does not equal left (after any
         *   conversions), false otherwise
         * @example
         *   {{{
         * val q1 = 1000.0.withUnit[Liter]
         * val q2 = 2.0.withUnit[Meter ^ 3]
         * q1 =!= q2 // => true
         *   }}}
         */
        inline def =!=[UR](qr: Quantity[V, UR])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = UnitConversion[V, UR, U](qr)
            ord.compare(q, qrv) != 0

        /**
         * test if this quantity is less than another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is less than the right (after any
         *   conversions), false otherwise
         * @example
         *   {{{
         * val q1 = 1000.withUnit[Liter]
         * val q2 = (2.0).withUnit[Meter ^ 3]
         * q1 < q2 // => true
         *   }}}
         */
        inline def <[UR](qr: Quantity[V, UR])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = UnitConversion[V, UR, U](qr)
            ord.compare(q, qrv) < 0

        /**
         * test if this quantity is less than or equal to another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is less than or equal to the right (after
         *   any conversions), false otherwise
         * @example
         *   {{{
         * val q1 = 1000.withUnit[Liter]
         * val q2 = (2.0).withUnit[Meter ^ 3]
         * q1 <= q2 // => true
         *   }}}
         */
        inline def <=[UR](qr: Quantity[V, UR])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = UnitConversion[V, UR, U](qr)
            ord.compare(q, qrv) <= 0

        /**
         * test if this quantity is greater than another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is greater than the right (after any
         *   conversions), false otherwise
         * @example
         *   {{{
         * val q1 = 2000.withUnit[Liter]
         * val q2 = (1.0).withUnit[Meter ^ 3]
         * q1 > q2 // => true
         *   }}}
         */
        inline def >[UR](qr: Quantity[V, UR])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = UnitConversion[V, UR, U](qr)
            ord.compare(q, qrv) > 0

        /**
         * test if this quantity is greater than or equal to another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is greater than or equal to the right
         *   (after any conversions), false otherwise
         * @example
         *   {{{
         * val q1 = 2000.withUnit[Liter]
         * val q2 = (1.0).withUnit[Meter ^ 3]
         * q1 >= q2 // => true
         *   }}}
         */
        inline def >=[UR](qr: Quantity[V, UR])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = UnitConversion[V, UR, U](qr)
            ord.compare(q, qrv) >= 0
