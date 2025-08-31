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

/** Units of time or duration */
object time:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.define.*

    import coulomb.units.si.*
    export coulomb.units.si.{Second, unit_Second}

    /** A duration of 60 seconds */
    final type Minute
    given unit_Minute: DerivedUnit[Minute, 60 * Second, "minute", "min"] =
        DerivedUnit()

    /** A duration of 60 minutes or 3600 seconds */
    final type Hour
    given unit_Hour: DerivedUnit[Hour, 3600 * Second, "hour", "h"] =
        DerivedUnit()

    /** A duration of 24 hours */
    final type Day
    given unit_Day: DerivedUnit[Day, 86400 * Second, "day", "d"] =
        DerivedUnit()

    /** A duration of 7 days */
    final type Week
    given unit_Week: DerivedUnit[Week, 604800 * Second, "week", "wk"] =
        DerivedUnit()

    /**
     * Represents an instant in time measured with respect to the standard unix
     * epoch `00:00:00 UTC January 1, 1970`
     *   - https://en.wikipedia.org/wiki/Unix_time
     *
     * @tparam V
     *   the value type containing the time quantity
     * @tparam U
     *   the unit type, requiring base unit [[coulomb.units.si.Second]]
     */
    final type EpochTime[V, U] = DeltaQuantity[V, U, coulomb.units.si.Second]

    object EpochTime:
        extension [V](v: V)
            /**
             * Lift a raw value to an EpochTime instant
             * @tparam U
             *   the unit type to use, expected to have base unit
             *   [[coulomb.units.si.Second]]
             * @return
             *   an EpochTime object representing desired instant
             * @example
             *   {{{
             * // the instant in time one million hours from Jan 1, 1970
             * val instant = (1e6).withEpochTime[Hour]
             *   }}}
             */
            inline def withEpochTime[U]: EpochTime[V, U] =
                v.withDeltaUnit[U, coulomb.units.si.Second]
