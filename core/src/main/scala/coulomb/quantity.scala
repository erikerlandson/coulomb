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

import coulomb.ops.*
import coulomb.rational.Rational
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.conversion.{TruncatingValueConversion, TruncatingUnitConversion}

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

@deprecated("Unitless should be replaced by integer literal type '1'")
final type Unitless = 1

/**
 * obtain a string representation of a unit type, using unit abbreviation forms
 * @tparam U
 *   the unit type
 * @return
 *   the unit in string form
 *   {{{
 * showUnit[Meter / Second] // => "m/s"
 *   }}}
 */
inline def showUnit[U]: String = ${ coulomb.infra.show.show[U] }

/**
 * obtain a string representation of a unit type, using full unit names
 * @tparam U
 *   the unit type
 * @return
 *   the unit in string form
 *   {{{
 * showUnitFull[Meter / Second] // => "meter/second"
 *   }}}
 */
inline def showUnitFull[U]: String = ${ coulomb.infra.show.showFull[U] }

/**
 * Obtain the coefficient of conversion from one unit expression to another
 * @tparam UF
 *   the input unit expression
 * @tparam UT
 *   the output unit expression
 * @tparam V
 *   the value type to return
 * @return
 *   the coefficient of conversion from UF to UT If UF and UT are not
 *   convertible, causes a compilation failure.
 */
inline def coefficient[V, UF, UT](using vc: ValueConversion[Rational, V]): V =
    import coulomb.conversion.coefficients.coefficientRational
    vc(coefficientRational[UF, UT])

package syntax {
    // this has to be in a separated namespace:
    // https://github.com/lampepfl/dotty/issues/15255
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
        inline def withUnit[U]: Quantity[V, U] = Quantity[U](v)

    package typelist {
        final type &:[Head, Tail]
        final type TNil
    }
}

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
    import syntax.withUnit

    /**
     * Lift a raw value of type V into a unit quantity
     * @tparam U
     *   the desired unit type
     * @return
     *   a Quantity with given value and unit type
     *   {{{
     * val distance = Quantity[Meter](1.0)
     *   }}}
     */
    def apply[U](using a: Applier[U]) = a

    /** A shim class for Quantity companion object constructors */
    class Applier[U]:
        def apply[V](v: V): Quantity[V, U] = v
    object Applier:
        given ctx_Applier[U]: Applier[U] = new Applier[U]

    extension [VL, UL](ql: Quantity[VL, UL])
        /**
         * extract the raw value of a unit quantity
         * @return
         *   the underlying value, stripped of its unit information
         *   {{{
         * val q = (1.5).withUnit[Meter]
         * q.value // => 1.5
         *   }}}
         */
        inline def value: VL = ql

        /**
         * returns a string representing this Quantity, using unit abbreviations
         * @example
         *   {{{
         * val q = (1.5).withUnit[Meter / Second]
         * q.show // => "1.5 m/s"
         *   }}}
         */
        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"

        /**
         * returns a string representing this Quantity, using full unit names
         * @example
         *   {{{
         * val q = (1.5).withUnit[Meter / Second]
         * q.showFull // => "1.5 meter/second"
         *   }}}
         */
        inline def showFull: String =
            s"${ql.value.toString} ${showUnitFull[UL]}"

        /**
         * convert a quantity to a new value type
         * @tparam V
         *   the new value type to use
         * @return
         *   a new `Quantity` having value type `V`
         * @example
         *   {{{
         * val q = (1.0).withUnit[Meter]
         * q.toValue[Float] // => Quantity[Meter](1.0f)
         *   }}}
         */
        inline def toValue[V](using
            conv: ValueConversion[VL, V]
        ): Quantity[V, UL] =
            conv(ql.value).withUnit[UL]

        /**
         * convert a quantity to a new unit type
         * @tparam U
         *   the new unit type
         * @return
         *   a new `Quantity` having unit type `U`
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
        inline def toUnit[U](using
            conv: UnitConversion[VL, UL, U]
        ): Quantity[VL, U] =
            conv(ql.value).withUnit[U]

        /**
         * convert a quantity to an integer value type from a fractional type
         * @tparam V
         *   a new integral value type
         * @return
         *   a new `Quantity` having value type `V`
         * @example
         *   {{{
         * val q = (1.0).withUnit[Meter]
         * q.tToUnit[Long] // => Quantity[Meter](1L)
         *   }}}
         */
        inline def tToValue[V](using
            conv: TruncatingValueConversion[VL, V]
        ): Quantity[V, UL] =
            conv(ql.value).withUnit[UL]

        /**
         * convert a quantity to a new unit type, using an integral value type
         * @tparam U
         *   the new unit type
         * @return
         *   a new `Quantity` having unit type `U`
         * @note
         *   attempting to convert to an incompatible unit will result in a
         *   compile error
         * @example
         *   {{{
         * val q = 10.withUnit[Yard]
         * q.tToUnit[Meter] // => Quantity[Meter](9)
         *   }}}
         */
        inline def tToUnit[U](using
            conv: TruncatingUnitConversion[VL, UL, U]
        ): Quantity[VL, U] =
            conv(ql.value).withUnit[U]

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
        inline def unary_-(using neg: Neg[VL, UL]): Quantity[VL, UL] =
            neg(ql)

        /**
         * add this quantity to another
         * @tparam VR
         *   right hand value type
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the sum of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 1.withUnit[Meter]
         * val q2 = (1.0).withUnit[Yard]
         * q1 + q2 // => Quantity[Meter](1.9144)
         *   }}}
         * @note
         *   unit types `UL` and `UR` must be convertable
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        transparent inline def +[VR, UR](qr: Quantity[VR, UR])(using
            add: Add[VL, UL, VR, UR]
        ): Quantity[add.VO, add.UO] =
            add.eval(ql, qr)

        /**
         * subtract another quantity from this one
         * @tparam VR
         *   right hand value type
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the result of subtracting `qr` from this
         * @example
         *   {{{
         * val q1 = 1.withUnit[Meter]
         * val q2 = (1.0).withUnit[Yard]
         * q1 - q2 // => Quantity[Meter](0.0856)
         *   }}}
         * @note
         *   unit types `UL` and `UR` must be convertable
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        transparent inline def -[VR, UR](qr: Quantity[VR, UR])(using
            sub: Sub[VL, UL, VR, UR]
        ): Quantity[sub.VO, sub.UO] =
            sub.eval(ql, qr)

        /**
         * multiply this quantity by another
         * @tparam VR
         *   right hand value type
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the product of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 2.withUnit[Meter]
         * val q2 = (3.0).withUnit[Meter]
         * q1 * q2 // => Quantity[Meter ^ 2](6.0)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        transparent inline def *[VR, UR](qr: Quantity[VR, UR])(using
            mul: Mul[VL, UL, VR, UR]
        ): Quantity[mul.VO, mul.UO] =
            mul.eval(ql, qr)

        /**
         * divide this quantity by another
         * @tparam VR
         *   right hand value type
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the quotient of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 3.withUnit[Meter]
         * val q2 = (2.0).withUnit[Second]
         * q1 / q2 // => Quantity[Meter / Second](1.5)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        transparent inline def /[VR, UR](qr: Quantity[VR, UR])(using
            div: Div[VL, UL, VR, UR]
        ): Quantity[div.VO, div.UO] =
            div.eval(ql, qr)

        /**
         * divide this quantity by another, using truncating (integer) division
         * @tparam VR
         *   right hand value type
         * @tparam UR
         *   right hand unit type
         * @param qr
         *   right hand quantity
         * @return
         *   the integral quotient of this quantity with `qr`
         * @example
         *   {{{
         * val q1 = 5.withUnit[Meter]
         * val q2 = 2L.withUnit[Second]
         * q1.tquot(q2) // => Quantity[Meter / Second](2L)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        transparent inline def tquot[VR, UR](qr: Quantity[VR, UR])(using
            tq: TQuot[VL, UL, VR, UR]
        ): Quantity[tq.VO, tq.UO] =
            tq.eval(ql, qr)

        /**
         * raise this quantity to a rational or integer power
         * @tparam P
         *   the power, or exponent
         * @return
         *   this quantity raised to exponent `P`
         * @example
         *   {{{
         * val q = (2.0).withUnit[Meter]
         * q.pow[2]  // => Quantity[Meter ^ 2](4.0)
         * q.pow[1/2]  // => Quantity[Meter ^ (1/2)](1.4142135623730951)
         * q.pow[-1]  // => Quantity[1 / Meter](0.5)
         *   }}}
         */
        transparent inline def pow[P](using
            pow: Pow[VL, UL, P]
        ): Quantity[pow.VO, pow.UO] =
            pow.eval(ql)

        /**
         * raise this quantity to a rational or integer power, with integer
         * truncated result
         * @tparam P
         *   the power, or exponent
         * @return
         *   this quantity raised to exponent `P`, truncated to integer type
         * @example
         *   {{{
         * val q = 10.withUnit[Meter]
         * q.tpow[2]  // => Quantity[Meter ^ 2](100)
         * q.tpow[1/2]  // => Quantity[Meter ^ (1/2)](3)
         * q.tpow[-1]  // => Quantity[1 / Meter](0)
         *   }}}
         */
        transparent inline def tpow[P](using
            tp: TPow[VL, UL, P]
        ): Quantity[tp.VO, tp.UO] =
            tp.eval(ql)

        /**
         * test this quantity for equality with another
         * @tparam VR
         *   value type of the right hand quantity
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value equals the left (after any conversions),
         *   false otherwise
         * @example
         *   {{{
         * val q1 = 1000.withUnit[Liter]
         * val q2 = (1.0).withUnit[Meter ^ 3]
         * q1 === q2 // => true
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def ===[VR, UR](qr: Quantity[VR, UR])(using
            ord: Ord[VL, UL, VR, UR]
        ): Boolean =
            ord(ql, qr) == 0

        /**
         * test this quantity for inequality with another
         * @tparam VR
         *   value type of the right hand quantity
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value does not equal left (after any
         *   conversions), false otherwise
         * @example
         *   {{{
         * val q1 = 1000.withUnit[Liter]
         * val q2 = (2.0).withUnit[Meter ^ 3]
         * q1 =!= q2 // => true
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def =!=[VR, UR](qr: Quantity[VR, UR])(using
            ord: Ord[VL, UL, VR, UR]
        ): Boolean =
            ord(ql, qr) != 0

        /**
         * test if this quantity is less than another
         * @tparam VR
         *   value type of the right hand quantity
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
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def <[VR, UR](qr: Quantity[VR, UR])(using
            ord: Ord[VL, UL, VR, UR]
        ): Boolean =
            ord(ql, qr) < 0

        /**
         * test if this quantity is less than or equal to another
         * @tparam VR
         *   value type of the right hand quantity
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
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def <=[VR, UR](qr: Quantity[VR, UR])(using
            ord: Ord[VL, UL, VR, UR]
        ): Boolean =
            ord(ql, qr) <= 0

        /**
         * test if this quantity is greater than another
         * @tparam VR
         *   value type of the right hand quantity
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
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def >[VR, UR](qr: Quantity[VR, UR])(using
            ord: Ord[VL, UL, VR, UR]
        ): Boolean =
            ord(ql, qr) > 0

        /**
         * test if this quantity is greater than or equal to another
         * @tparam VR
         *   value type of the right hand quantity
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
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def >=[VR, UR](qr: Quantity[VR, UR])(using
            ord: Ord[VL, UL, VR, UR]
        ): Boolean =
            ord(ql, qr) >= 0
