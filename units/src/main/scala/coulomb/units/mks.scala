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
 * Common mechanical units derived from Meter, Kilogram and Second (MKS)
 *   - https://en.wikipedia.org/wiki/MKS_system_of_units#Mechanical_units
 */
object mks:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    // exports are safe to "union" from multiple imports
    export coulomb.units.si.{
        Meter,
        ctx_unit_Meter,
        Kilogram,
        ctx_unit_Kilogram,
        Second,
        ctx_unit_Second
    }

    /** Unit of planar angle. */
    final type Radian
    given ctx_unit_Radian: DerivedUnit[Radian, 1, "radian", "rad"] =
        DerivedUnit()

    /** Unit of solid angle */
    final type Steradian
    given ctx_unit_Steradian: DerivedUnit[Steradian, 1, "steradian", "sr"] =
        DerivedUnit()

    /** Unit of temporal frequency */
    final type Hertz
    given ctx_unit_Hertz: DerivedUnit[Hertz, 1 / Second, "hertz", "Hz"] =
        DerivedUnit()

    /** Unit of mechanical force */
    final type Newton
    given ctx_unit_Newton
        : DerivedUnit[Newton, Kilogram * Meter / (Second ^ 2), "newton", "N"] =
        DerivedUnit()

    /** Unit of energy */
    final type Joule
    given ctx_unit_Joule: DerivedUnit[Joule, Newton * Meter, "joule", "J"] =
        DerivedUnit()

    /** Unit of power */
    final type Watt
    given ctx_unit_Watt: DerivedUnit[Watt, Joule / Second, "watt", "W"] =
        DerivedUnit()

    /** Unit of pressure */
    final type Pascal
    given ctx_unit_Pascal
        : DerivedUnit[Pascal, Newton / (Meter ^ 2), "pascal", "Pa"] =
        DerivedUnit()
