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

package coulomb.units

/** Temperature units and values */
object temperature:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.define.*

    export coulomb.units.si.{Kelvin, ctx_unit_Kelvin}

    /** Celsius degree, aka Centigrade */
    final type Celsius
    given ctx_unit_Celsius
        : DeltaUnit[Celsius, Kelvin, 27315 / 100, "celsius", "°C"] = DeltaUnit()

    /** Fahrenheit degree */
    final type Fahrenheit
    given ctx_unit_Fahrenheit: DeltaUnit[
        Fahrenheit,
        (5 / 9) * Kelvin,
        45967 / 100,
        "fahrenheit",
        "°F"
    ] = DeltaUnit()

    /**
     * A temperature value
     * @tparam V
     *   the underlying value type
     * @tparam U
     *   a temperature unit type, having base unit [[coulomb.units.si.Kelvin]]
     */
    final type Temperature[V, U] = DeltaQuantity[V, U, Kelvin]

    object Temperature:
        /**
         * obtain a new temperature value
         * @tparam U
         *   temperature unit type, having base unit [[coulomb.units.si.Kelvin]]
         * @return
         *   the new Temperature quantity
         * @example
         *   {{{
         * // standard human body temperature in Fahrenheit
         * val bodytemp = Temperature[Fahrenheit](98.6)
         *   }}}
         */
        def apply[U](using a: Applier[U]) = a

        /** a shim class for Temperature companion `apply` method */
        abstract class Applier[U]:
            def apply[V](v: V): Temperature[V, U]
        object Applier:
            given [U]: Applier[U] =
                new Applier[U]:
                    def apply[V](v: V): Temperature[V, U] =
                        v.withDeltaUnit[U, Kelvin]

    extension [V](v: V)
        /**
         * Lift a raw value to a Temperature
         * @tparam U
         *   the temperature unit type to use, having base unit
         *   [[coulomb.units.si.Kelvin]]
         * @return
         *   a Temperature object
         * @example
         *   {{{
         * // the freezing point of water on the Celsius scale
         * val freeze = (0.0).withTemperature[Celsius]
         *   }}}
         */
        def withTemperature[U]: Temperature[V, U] = v.withDeltaUnit[U, Kelvin]
