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

    export coulomb.units.si.{Kelvin, unit_Kelvin}

    /** Celsius degree, aka Centigrade */
    final type Celsius
    given unit_Celsius: DeltaUnit[
        Celsius,
        Kelvin,
        27315 / 100,
        "celsius",
        "°C"
    ] = DeltaUnit()

    /** Fahrenheit degree */
    final type Fahrenheit
    given unit_Fahrenheit: DeltaUnit[
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
            inline def withTemperature[U]: Temperature[V, U] =
                v.withDeltaUnit[U, Kelvin]
