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

import coulomb.syntax.*
import coulomb.conversion.*

package syntax {
    // this has to be in a separated namespace:
    // https://github.com/lampepfl/dotty/issues/15255
    extension [V](v: V)
        /**
         * Lift a raw value into a delta-unit quantity
         * @tparam U
         *   the desired unit type
         * @tparam B
         *   base unit to anchor with
         * @return
         *   a DeltaQuantity with given value and unit type
         *   {{{
         * val date = (1.0).withDeltaUnit[Day, Second]
         *   }}}
         */
        inline def withDeltaUnit[U, B]: DeltaQuantity[V, U, B] =
            DeltaQuantity[U, B](v)
}

/**
 * Represents a value with an associated unit type and "delta" offset, for
 * example [[coulomb.units.temperature.Temperature]] or
 * [[coulomb.units.time.EpochTime]]
 * @tparam V
 *   the raw value type
 * @tparam U
 *   the unit type
 * @tparam B
 *   base unit type (the base unit of U)
 */
opaque type DeltaQuantity[V, U, B] = V

/** Defines DeltaQuantity constructors and extension methods */
object DeltaQuantity:
    import _root_.algebra.ring.*
    import cats.kernel.Order

    /**
     * Lift a raw value of type V into a unit quantity
     * @tparam U
     *   the desired unit type
     * @tparam B
     *   base unit type of U
     * @return
     *   a DeltaQuantity with given value and unit type
     *   {{{
     * val temp = DeltaQuantity[Celsius, Kelvin](100.0)
     *   }}}
     */
    def apply[U, B](using a: Applier[U, B]) = a

    /** A shim class for DeltaQuantity companion object constructors */
    class Applier[U, B]:
        def apply[V](v: V): DeltaQuantity[V, U, B] = v
    object Applier:
        given [U, B]: Applier[U, B] = new Applier[U, B]

    extension [VL, UL, B](ql: DeltaQuantity[VL, UL, B])
        /**
         * extract the raw value of a delta-unit quantity
         * @return
         *   the underlying value, stripped of its unit information
         *   {{{
         * val t = (37.0).withTemperature[Celsius]
         * t.value // => 37.0
         * val d = (1.0).withEpochTime[Week]
         * d.value // => 1.0
         *   }}}
         */
        inline def value: VL = ql

        /**
         * returns a string representing this DeltaQuantity, using unit
         * abbreviations
         * @example
         *   {{{
         * val t = (37.0).withTemperature[Celsius]
         * t.show // => "37.0 °C"
         *   }}}
         */
        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"

        /**
         * returns a string representing this DeltaQuantity, using full unit
         * names
         * @example
         *   {{{
         * val t = (37.0).withTemperature[Celsius]
         * t.showFull // => "37.0 celsius"
         *   }}}
         */
        inline def showFull: String =
            s"${ql.value.toString} ${showUnitFull[UL]}"

        /**
         * convert a delta-quantity to a new value type
         * @tparam V
         *   the new value type to use
         * @return
         *   a new `DeltaQuantity` having value type `V`
         * @example
         *   {{{
         * val t = 37.withTemperature[Celsius]
         * t.toValue[Float] // => Temperature[Float, Celsius](37.0)
         *   }}}
         */
        inline def toValue[V](using
            conv: ValueConversion[VL, V]
        ): DeltaQuantity[V, UL, B] =
            conv(ql.value).withDeltaUnit[UL, B]

        /**
         * convert a delta-quantity to a new unit type
         * @tparam U
         *   the new unit type
         * @return
         *   a new `DeltaQuantity` having unit type `U`
         * @note
         *   attempting to convert to an incompatible unit will result in a
         *   compile error
         * @example
         *   {{{
         * val t = 37d.withTemperature[Celsius]
         * t.toUnit[Fahrenheit] // => Temperature[Double, Fahrenheit](98.6)
         *   }}}
         */
        inline def toUnit[U](using
            conv: DeltaUnitConversion[VL, B, UL, U]
        ): DeltaQuantity[VL, U, B] =
            conv(ql.value).withDeltaUnit[U, B]

        /**
         * convert a delta-quantity from a fractional value type to an integer
         * type
         * @tparam V
         *   the new value type to use
         * @return
         *   a new `DeltaQuantity` having value type `V`
         * @example
         *   {{{
         * val t = (98.6).withTemperature[Fahrenheit]
         * t.tToValue[Int] // => Temperature[Int, Fahrenheit](98)
         *   }}}
         */
        inline def tToValue[V](using
            conv: TruncatingValueConversion[VL, V]
        ): DeltaQuantity[V, UL, B] =
            conv(ql.value).withDeltaUnit[UL, B]

        /**
         * convert a delta-quantity to a new unit type, using an integer value
         * type
         * @tparam U
         *   the new unit type
         * @return
         *   a new `DeltaQuantity` having unit type `U`
         * @note
         *   attempting to convert to an incompatible unit will result in a
         *   compile error
         * @example
         *   {{{
         * val t = 37.withTemperature[Celsius]
         * t.tToUnit[Fahrenheit] // => Temperature[Int, Fahrenheit](98)
         *   }}}
         */
        inline def tToUnit[U](using
            conv: TruncatingDeltaUnitConversion[VL, B, UL, U]
        ): DeltaQuantity[VL, U, B] =
            conv(ql.value).withDeltaUnit[U, B]

        /**
         * subtract another delta-quantity from this one
         * @param qr
         *   right hand delta-quantity
         * @return
         *   the result of subtracting `qr` from this, as a Quantity value
         * @example
         *   {{{
         * val t1 = 14.withEpochTime[Day]
         * val t2 = (1.0).withEpochTime[Week]
         * t1 - t2 // => Quantity[Double, Day](7.0)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def -(qr: DeltaQuantity[VL, UL, B])(using
            alg: AdditiveGroup[VL]
        ): Quantity[VL, UL] =
            alg.minus(ql.value, qr.value).withUnit[UL]

        /**
         * subtract quantity from this delta-quantity
         * @param qr
         *   right hand quantity
         * @return
         *   the result of subtracting `qr` from this, as a DeltaQuantity value
         * @example
         *   {{{
         * val t1 = 14.withEpochTime[Day]
         * val q = (1.0).withUnit[Week]
         * t1 - q // => EpochTime[Double, Day](7.0)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        // work around a weird type erasure problem,
        // spcifically with '-' operator overloadings
        @scala.annotation.targetName("dqMinusQ")
        inline def -(qr: Quantity[VL, UL])(using
            alg: AdditiveGroup[VL]
        ): DeltaQuantity[VL, UL, B] =
            alg.minus(ql.value, qr.value).withDeltaUnit[UL, B]

        /**
         * add a quantity to this delta-quantity
         * @param qr
         *   right hand quantity
         * @return
         *   the result of adding `qr` to this, as a DeltaQuantity value
         * @example
         *   {{{
         * val t1 = 14.withEpochTime[Day]
         * val q = (1.0).withUnit[Week]
         * t1 + q // => EpochTime[Double, Day](21.0)
         *   }}}
         */
        inline def +(qr: Quantity[VL, UL])(using
            alg: AdditiveSemigroup[VL]
        ): DeltaQuantity[VL, UL, B] =
            alg.plus(ql.value, qr.value).withDeltaUnit[UL, B]

        /**
         * test this delta-quantity for equality with another
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value equals the left, false otherwise
         * @example
         *   {{{
         * val t1 = (14.0).withEpochTime[Day]
         * val t2 = 2.withEpochTime[Week]
         * t1 === t2 // => true
         *   }}}
         */
        inline def ===(qr: DeltaQuantity[VL, UL, B])(using
            ord: Order[VL]
        ): Boolean =
            ord.compare(ql.value, qr.value) == 0

        /**
         * test this delta-quantity for inequality with another
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value does not equal the left, false otherwise
         * @example
         *   {{{
         * val t1 = (14.0).withEpochTime[Day]
         * val t2 = 2.withEpochTime[Week]
         * t1 =!= t2 // => false
         *   }}}
         */
        inline def =!=(qr: DeltaQuantity[VL, UL, B])(using
            ord: Order[VL]
        ): Boolean =
            ord.compare(ql.value, qr.value) != 0

        /**
         * test if this delta-quantity is less than another
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is less than the right (after any
         *   conversions), false otherwise
         * @example
         *   {{{
         * val t1 = (14.0).withEpochTime[Day]
         * val t2 = 3.withEpochTime[Week]
         * t1 < t2 // => true
         *   }}}
         */
        inline def <(qr: DeltaQuantity[VL, UL, B])(using
            ord: Order[VL]
        ): Boolean =
            ord.compare(ql.value, qr.value) < 0

        /**
         * test if this delta-quantity is less than or equal to than another
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is less than or equal to the right (after
         *   any conversions), false otherwise
         * @example
         *   {{{
         * val t1 = (14.0).withEpochTime[Day]
         * val t2 = 3.withEpochTime[Week]
         * t1 <= t2 // => true
         *   }}}
         */
        inline def <=(qr: DeltaQuantity[VL, UL, B])(using
            ord: Order[VL]
        ): Boolean =
            ord.compare(ql.value, qr.value) <= 0

        inline def >(qr: DeltaQuantity[VL, UL, B])(using
            ord: Order[VL]
        ): Boolean =
            ord.compare(ql.value, qr.value) > 0

        inline def >=(qr: DeltaQuantity[VL, UL, B])(using
            ord: Order[VL]
        ): Boolean =
            ord.compare(ql.value, qr.value) >= 0
