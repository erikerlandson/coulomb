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
    import algebra.ring.*
    import cats.kernel.Order

    import coulomb.Quantity.withUnit
    import coulomb.conversion.*
    import coulomb.io.ShowUnit

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
        inline def withDeltaUnit[U, B]: DeltaQuantity[V, U, B] = v

    extension [V, U, B, DQ[V, U, B] <: DeltaQuantity[V, U, B]](q: DQ[V, U, B])
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
        inline def value: V = q

        /**
         * returns a string representing this DeltaQuantity, using unit
         * abbreviations
         * @example
         *   {{{
         * val t = (37.0).withTemperature[Celsius]
         * t.show // => "37.0 °C"
         *   }}}
         */
        inline def show: String =
            s"${q.value.toString} ${ShowUnit[U]}"

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
            s"${q.value.toString} ${ShowUnit.full[U]}"

        /**
         * convert a delta-quantity to a new value type
         * @tparam VT
         *   the new value type to use
         * @return
         *   a new `DeltaQuantity` having value type `VT`
         * @example
         *   {{{
         * val t = 37.withTemperature[Celsius]
         * t.toValue[Float] // => Temperature[Float, Celsius](37.0)
         *   }}}
         */
        inline def toValue[VT]: DeltaQuantity[VT, U, B] =
            ValueConversion[V, VT](q)

        /**
         * convert a delta-quantity to a new unit type
         * @tparam UT
         *   the new unit type
         * @return
         *   a new `DeltaQuantity` having unit type `UT`
         * @note
         *   attempting to convert to an incompatible unit will result in a
         *   compile error
         * @example
         *   {{{
         * val t = 37d.withTemperature[Celsius]
         * t.toUnit[Fahrenheit] // => Temperature[Double, Fahrenheit](98.6)
         *   }}}
         */
        inline def toUnit[UT]: DeltaQuantity[V, UT, B] =
            DeltaUnitConversion[V, B, U, UT](q)

        /**
         * subtract another delta-quantity from this one
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   right hand delta-quantity
         * @return
         *   the result of subtracting `qr` from this, as a Quantity value
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 1.0.withEpochTime[Week]
         * t1 - t2 // => Quantity[Double, Day](7.0)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        inline def -[UR](qr: DeltaQuantity[V, UR, B])(using
            alg: AdditiveGroup[V]
        ): Quantity[V, U] =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            alg.minus(q, qrv).withUnit[U]

        /**
         * subtract quantity from this delta-quantity
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   right hand quantity
         * @return
         *   the result of subtracting `qr` from this, as a DeltaQuantity value
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val q = 1.0.withUnit[Week]
         * t1 - q // => EpochTime[Double, Day](7.0)
         *   }}}
         * @note
         *   result may depend on what algebras, policies, and other typeclasses
         *   are in scope
         */
        // work around a weird type erasure problem,
        // spcifically with '-' operator overloadings
        @scala.annotation.targetName("dqMinusQ")
        inline def -[UR](qr: Quantity[V, UR])(using
            alg: AdditiveGroup[V]
        ): DeltaQuantity[V, U, B] =
            val qrv: V = UnitConversion[V, UR, U](qr.value)
            alg.minus(q, qrv)

        /**
         * add a quantity to this delta-quantity
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   right hand quantity
         * @return
         *   the result of adding `qr` to this, as a DeltaQuantity value
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val q = 1.0.withUnit[Week]
         * t1 + q // => EpochTime[Double, Day](21.0)
         *   }}}
         */
        inline def +[UR](qr: Quantity[V, UR])(using
            alg: AdditiveSemigroup[V]
        ): DeltaQuantity[V, U, B] =
            val qrv: V = UnitConversion[V, UR, U](qr.value)
            alg.plus(q, qrv)

        /**
         * test this delta-quantity for equality with another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value equals the left, false otherwise
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 2.0.withEpochTime[Week]
         * t1 === t2 // => true
         *   }}}
         */
        inline def ===[UR](qr: DeltaQuantity[V, UR, B])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            ord.compare(q, qrv) == 0

        /**
         * test this delta-quantity for inequality with another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if right hand value does not equal the left, false otherwise
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 2.0.withEpochTime[Week]
         * t1 =!= t2 // => false
         *   }}}
         */
        inline def =!=[UR](qr: DeltaQuantity[V, UR, B])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            ord.compare(q, qrv) != 0

        /**
         * test if this delta-quantity is less than another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is less than the right (after any
         *   conversions), false otherwise
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 3.0.withEpochTime[Week]
         * t1 < t2 // => true
         *   }}}
         */
        inline def <[UR](qr: DeltaQuantity[V, UR, B])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            ord.compare(q, qrv) < 0

        /**
         * test if this delta-quantity is less than or equal to than another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is less than or equal to the right (after
         *   any conversions), false otherwise
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 3.0.withEpochTime[Week]
         * t1 <= t2 // => true
         *   }}}
         */
        inline def <=[UR](qr: DeltaQuantity[V, UR, B])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            ord.compare(q, qrv) <= 0

        /**
         * test if this delta-quantity is greater than another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is greater than the right (after
         *   any conversions), false otherwise
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 3.0.withEpochTime[Week]
         * t1 > t2 // => false
         *   }}}
         */
        inline def >[UR](qr: DeltaQuantity[V, UR, B])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            ord.compare(q, qrv) > 0

        /**
         * test if this delta-quantity is greater than or equal to another
         * @tparam UR
         *   unit type of the right hand quantity
         * @param qr
         *   the right hand quantity
         * @return
         *   true if left-hand value is greater than or equal to the right (after
         *   any conversions), false otherwise
         * @example
         *   {{{
         * val t1 = 14.0.withEpochTime[Day]
         * val t2 = 3.0.withEpochTime[Week]
         * t1 >= t2 // => false
         *   }}}
         */
        inline def >=[UR](qr: DeltaQuantity[V, UR, B])(using
            ord: Order[V]
        ): Boolean =
            val qrv: V = DeltaUnitConversion[V, B, UR, U](qr)
            ord.compare(q, qrv) >= 0
