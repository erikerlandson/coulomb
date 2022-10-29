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

/**
 * Accepted historic SI metric units
 *
 * Selection of units from the following table, along with other commonly used
 * "metric" units.
 *   - https://en.wikipedia.org/wiki/International_System_of_Units#Non-SI_units_accepted_for_use_with_SI
 *
 * Time units are defined in [[coulomb.units.time]]. Degrees Celsius are defined
 * in [[coulomb.units.temperature]].
 */
object accepted:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    export coulomb.units.si.{
        Meter,
        ctx_unit_Meter,
        Kilogram,
        ctx_unit_Kilogram,
        Second,
        ctx_unit_Second
    }

    final type Percent
    given ctx_unit_Percent: DerivedUnit[Percent, 1 / 100, "percent", "%"] =
        DerivedUnit()

    final type Degree
    given ctx_unit_Degree
        : DerivedUnit[Degree, 3.141592653589793 / 180, "degree", "°"] =
        DerivedUnit()

    final type ArcMinute
    given ctx_unit_ArcMinute
        : DerivedUnit[ArcMinute, Degree / 60, "arcminute", "'"] = DerivedUnit()

    final type ArcSecond
    given ctx_unit_ArcSecond
        : DerivedUnit[ArcSecond, Degree / 3600, "arcsecond", "\""] =
        DerivedUnit()

    final type Hectare
    given ctx_unit_Hectare
        : DerivedUnit[Hectare, 10000 * (Meter ^ 2), "hectare", "ha"] =
        DerivedUnit()

    final type Liter
    given ctx_unit_Liter: DerivedUnit[Liter, (Meter ^ 3) / 1000, "liter", "l"] =
        DerivedUnit()

    final type Milliliter
    given ctx_unit_Milliliter
        : DerivedUnit[Milliliter, Liter / 1000, "milliliter", "ml"] =
        DerivedUnit()

    final type Gram
    given ctx_unit_Gram: DerivedUnit[Gram, Kilogram / 1000, "gram", "g"] =
        DerivedUnit()

    final type Tonne
    given ctx_unit_Tonne: DerivedUnit[Tonne, 1000 * Kilogram, "tonne", "t"] =
        DerivedUnit()

    final type Millibar
    given ctx_unit_Millibar: DerivedUnit[
        Millibar,
        100 * Kilogram / (Meter * (Second ^ 2)),
        "millibar",
        "mbar"
    ] =
        DerivedUnit()

    final type Kilometer
    given ctx_unit_Kilometer
        : DerivedUnit[Kilometer, 1000 * Meter, "kilometer", "km"] =
        DerivedUnit()

    final type Centimeter
    given ctx_unit_Centimeter
        : DerivedUnit[Centimeter, Meter / 100, "centimeter", "cm"] =
        DerivedUnit()

    final type Millimeter
    given ctx_unit_Millimeter
        : DerivedUnit[Millimeter, Meter / 1000, "millimeter", "mm"] =
        DerivedUnit()
