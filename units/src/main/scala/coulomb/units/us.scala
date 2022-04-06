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
 * United States customary units
 * Via international definitions, conversion coefficients to SI units are exact.
 * https://en.wikipedia.org/wiki/International_yard_and_pound
 * https://en.wikipedia.org/wiki/Standard_gravity
 * https://en.wikipedia.org/wiki/Calorie
 */
object us:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    export coulomb.units.si.{
        Meter, ctx_unit_Meter,
        Kilogram, ctx_unit_Kilogram,
        Second, ctx_unit_Second
    }

    /**
     * A yard is exactly 0.9144 meters by definition
     * https://en.wikipedia.org/wiki/Yard
     * https://en.wikipedia.org/wiki/International_yard_and_pound
     */
    final type Yard
    given ctx_unit_Yard: DerivedUnit[Yard, (9144 / 10000) * Meter, "yard", "yd"] = DerivedUnit()

    /**
     * A pound is exactly 0.45359237 kilograms by definition
     * https://en.wikipedia.org/wiki/International_yard_and_pound
     */
    final type Pound
    given ctx_unit_Pound: DerivedUnit[Pound, (45359237 / 100000000) * Kilogram, "pound", "lb"] = DerivedUnit()

    /**
     * United States Gallon
     * Exactly 231 cubic inches, and therefore exactly 3.785411784 liters
     * https://en.wikipedia.org/wiki/Gallon
     * https://en.wikipedia.org/wiki/International_yard_and_pound
     */
    final type Gallon
    given ctx_unit_Gallon: DerivedUnit[Gallon, (3785411784L / 1000000000000L) * (Meter ^ 3), "gallon", "gal"] = DerivedUnit()

    /**
     * The force (weight) of one pound in standard earth gravity, defined as 9.80665 m/s^2
     * Conversion coefficient is exact at 4.4482216152605 Newtons
     * https://en.wikipedia.org/wiki/Pound_(force)
     * https://en.wikipedia.org/wiki/Standard_gravity
     */
    final type PoundForce
    given ctx_unit_PoundForce: DerivedUnit[PoundForce, (44482216152605L / 10000000000000L) * Kilogram * Meter / (Second ^ 2), "poundforce", "lbf"] = DerivedUnit()

    /**
     * Thermochemical calorie (small):
     * Exactly 4.184 J by definition
     * https://en.wikipedia.org/wiki/Calorie
     */
    final type Calorie
    given ctx_unit_Calorie: DerivedUnit[Calorie, (4184 / 1000) * Kilogram * (Meter ^ 2) / (Second ^ 2), "calorie", "cal"] = DerivedUnit()

    /**
     * British Thermal Unit (thermochemical definition)
     * Conversion coefficient to Joules is exact, via thermochemical calorie
     * https://en.wikipedia.org/wiki/British_thermal_unit
     */
    final type BTU
    given ctx_unit_BTU: DerivedUnit[BTU, (23722880951L / 22500000) * Kilogram * (Meter ^ 2) / (Second ^ 2), "BTU", "BTU"] = DerivedUnit()

    final type Foot
    given ctx_unit_Foot: DerivedUnit[Foot, Yard / 3, "foot", "ft"] = DerivedUnit()

    final type Inch
    given ctx_unit_Inch: DerivedUnit[Inch, Yard / 36, "inch", "in"] = DerivedUnit()

    final type Mile
    given ctx_unit_Mile: DerivedUnit[Mile, 1760 * Yard, "mile", "mi"] = DerivedUnit()

    final type Acre
    given ctx_unit_Acre: DerivedUnit[Acre, 4840 * (Yard ^ 2), "acre", "acre"] = DerivedUnit()

    final type Ounce
    given ctx_unit_Ounce: DerivedUnit[Ounce, Pound / 16, "ounce", "oz"] = DerivedUnit()

    /**
     * A short ton is 2000 pounds
     * https://en.wikipedia.org/wiki/Short_ton
     */
    final type ShortTon
    given ctx_unit_ShortTon: DerivedUnit[ShortTon, 2000 * Pound, "shortton", "ton"] = DerivedUnit()

    /**
     * One pound(force) applied over a linear foot
     * https://en.wikipedia.org/wiki/Pound_(force)
     */
    final type FootPound
    given ctx_unit_FootPound: DerivedUnit[FootPound, Foot * PoundForce, "footpound", "ft·lbf"] = DerivedUnit()

    /**
     * Mechanical horsepower, aka Imperial horsepower
     * https://en.wikipedia.org/wiki/Horsepower#Mechanical_horsepower
     */
    final type Horsepower
    given ctx_unit_Horsepower: DerivedUnit[Horsepower, 550 * Foot * PoundForce / Second, "horsepower", "hp"] = DerivedUnit()

    /**
     * Large calorie, aka food calorie or kilogram calorie
     * https://en.wikipedia.org/wiki/Calorie
     */
    final type LargeCalorie
    given ctx_unit_LargeCalorie: DerivedUnit[LargeCalorie, 1000 * Calorie, "Calorie", "Cal"] = DerivedUnit()

    final type Quart
    given ctx_unit_Quart: DerivedUnit[Quart, Gallon / 4, "quart", "qt"] = DerivedUnit()

    final type Pint
    given ctx_unit_Pint: DerivedUnit[Pint, Gallon / 8, "pint", "qt"] = DerivedUnit()

    final type Cup
    given ctx_unit_Cup: DerivedUnit[Cup, Gallon / 16, "cup", "cp"] = DerivedUnit()

    final type FluidOunce
    given ctx_unit_FluidOunce: DerivedUnit[FluidOunce, Gallon / 128, "fluidounce", "floz"] = DerivedUnit()

    final type Tablespoon
    given ctx_unit_Tablespoon: DerivedUnit[Tablespoon, Gallon / 256, "tablespoon", "tbsp"] = DerivedUnit()

    final type Teaspoon
    given ctx_unit_Teaspoon: DerivedUnit[Teaspoon, Gallon / 768, "teaspoon", "tsp"] = DerivedUnit()
