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

object accepted:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    export coulomb.units.si.{
        Meter, ctx_unit_Meter,
        Kilogram, ctx_unit_Kilogram,
        Second, ctx_unit_Second
    }

    final type Percent
    given ctx_unit_Percent: DerivedUnit[Percent, 1 / 100, "percent", "%"] with {} 

    final type Degree
    given ctx_unit_Degree: DerivedUnit[Degree, 3.141592653589793 / 180, "degree", "°"] with {}

    final type ArcMinute
    given ctx_unit_ArcMinute: DerivedUnit[ArcMinute, Degree / 60, "arcminute", "'"] with {}

    final type ArcSecond
    given ctx_unit_ArcSecond: DerivedUnit[ArcSecond, Degree / 3600, "arcsecond", "\""] with {}

    final type Hectare
    given ctx_unit_Hectare: DerivedUnit[Hectare, 10000 * (Meter ^ 2), "hectare", "ha"] with {}

    final type Liter
    given ctx_unit_Liter: DerivedUnit[Liter, (Meter ^ 3) / 1000, "liter", "l"] with {}

    final type Milliliter
    given ctx_unit_Milliliter: DerivedUnit[Milliliter, Liter / 1000, "milliliter", "ml"] with {}

    final type Gram
    given ctx_unit_Gram: DerivedUnit[Gram, Kilogram / 1000, "gram", "g"] with {}

    final type Tonne
    given ctx_unit_Tonne: DerivedUnit[Tonne, 1000 * Kilogram, "tonne", "t"] with {}

    final type Millibar
    given ctx_unit_Millibar: DerivedUnit[Millibar, 100 * Kilogram / (Meter * (Second ^ 2)), "millibar", "mbar"] with {}

    final type Kilometer
    given ctx_unit_Kilometer: DerivedUnit[Kilometer, 1000 * Meter, "kilometer", "km"] with {}

    final type Centimeter
    given ctx_unit_Centimeter: DerivedUnit[Centimeter, Meter / 100, "centimeter", "cm"] with {}

    final type Millimeter
    given ctx_unit_Millimeter: DerivedUnit[Millimeter, Meter / 1000, "millimeter", "mm"] with {}
