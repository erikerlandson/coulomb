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
 * The base units for the International System of Units, aka SI Units
 *
 * https://en.wikipedia.org/wiki/International_System_of_Units#Base_units
 */
object si:
    import coulomb.define.*

    /** The SI unit for length, or extent */
    final type Meter
    given ctx_unit_Meter: BaseUnit[Meter, "meter", "m"] = BaseUnit()

    /** The SI unit for mass */
    final type Kilogram
    given ctx_unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] = BaseUnit()

    /** The SI unit for time, or duration */
    final type Second
    given ctx_unit_Second: BaseUnit[Second, "second", "s"] = BaseUnit()

    /** The SI unit for electric current */
    final type Ampere
    given ctx_unit_Ampere: BaseUnit[Ampere, "ampere", "A"] = BaseUnit() 

    /** The SI unit for amount of substance */
    final type Mole
    given ctx_unit_Mole: BaseUnit[Mole, "mole", "mol"] = BaseUnit()

    /** The SI unit for luminous intensity */
    final type Candela
    given ctx_unit_Candela: BaseUnit[Candela, "candela", "cd"] = BaseUnit()

    /** The SI unit for thermodynamic temperature */
    final type Kelvin
    given ctx_unit_Kelvin: BaseUnit[Kelvin, "Kelvin", "K"] = BaseUnit()

    /**
     * Standard base-10 SI prefixes
     *
     * https://en.wikipedia.org/wiki/International_System_of_Units#Prefixes
     *
     * Standard binary prefixes are defined in [[coulomb.units.info.prefixes]]
     */
    object prefixes:
        import coulomb.{`/`, `^`}

        /** SI prefix for 10 */
        final type Deka
        given ctx_unit_Deka: DerivedUnit[Deka, 10, "deka", "da"] = DerivedUnit()

        /** SI prefix for 100 */
        final type Hecto
        given ctx_unit_Hecto: DerivedUnit[Hecto, 100, "hecto", "h"] = DerivedUnit()

        /** SI prefix for 10 ^ 3 */
        final type Kilo
        given ctx_unit_Kilo: DerivedUnit[Kilo, 10 ^ 3, "kilo", "k"] = DerivedUnit()

        /** SI prefix for 10 ^ 6 */
        final type Mega
        given ctx_unit_Mega: DerivedUnit[Mega, 10 ^ 6, "mega", "M"] = DerivedUnit()

        /** SI prefix for 10 ^ 9 */
        final type Giga
        given ctx_unit_Giga: DerivedUnit[Giga, 10 ^ 9, "giga", "G"] = DerivedUnit()

        /** SI prefix for 10 ^ 12 */
        final type Tera
        given ctx_unit_Tera: DerivedUnit[Tera, 10 ^ 12, "tera", "T"] = DerivedUnit()

        /** SI prefix for 10 ^ 15 */
        final type Peta
        given ctx_unit_Peta: DerivedUnit[Peta, 10 ^ 15, "peta", "P"] = DerivedUnit()

        /** SI prefix for 10 ^ 18 */
        final type Exa
        given ctx_unit_Exa: DerivedUnit[Exa, 10 ^ 18, "exa", "E"] = DerivedUnit()

        /** SI prefix for 10 ^ 21 */
        final type Zetta
        given ctx_unit_Zetta: DerivedUnit[Zetta, 10 ^ 21, "zetta", "Z"] = DerivedUnit()

        /** SI prefix for 10 ^ 24 */
        final type Yotta
        given ctx_unit_Yotta: DerivedUnit[Yotta, 10 ^ 24, "yotta", "Y"] = DerivedUnit()

        /** SI prefix for 1/10 */
        final type Deci
        given ctx_unit_Deci: DerivedUnit[Deci, 1 / 10, "deci", "d"] = DerivedUnit()

        /** SI prefix for 1/100 */
        final type Centi
        given ctx_unit_Centi: DerivedUnit[Centi, 1 / 100, "centi", "c"] = DerivedUnit()

        /** SI prefix for 10 ^ -3 */
        final type Milli
        given ctx_unit_Milli: DerivedUnit[Milli, 10 ^ -3, "milli", "m"] = DerivedUnit()

        /** SI prefix for 10 ^ -6 */
        final type Micro
        given ctx_unit_Micro: DerivedUnit[Micro, 10 ^ -6, "micro", "μ"] = DerivedUnit()

        /** SI prefix for 10 ^ -9 */
        final type Nano
        given ctx_unit_Nano: DerivedUnit[Nano, 10 ^ -9, "nano", "n"] = DerivedUnit()

        /** SI prefix for 10 ^ -12 */
        final type Pico
        given ctx_unit_Pico: DerivedUnit[Pico, 10 ^ -12, "pico", "p"] = DerivedUnit()

        /** SI prefix for 10 ^ -15 */
        final type Femto
        given ctx_unit_Femto: DerivedUnit[Femto, 10 ^ -15, "femto", "f"] = DerivedUnit()

        /** SI prefix for 10 ^ -18 */
        final type Atto
        given ctx_unit_Atto: DerivedUnit[Atto, 10 ^ -18, "atto", "a"] = DerivedUnit()

        /** SI prefix for 10 ^ -21 */
        final type Zepto
        given ctx_unit_Zepto: DerivedUnit[Zepto, 10 ^ -21, "zepto", "z"] = DerivedUnit()

        /** SI prefix for 10 ^ -24 */
        final type Yocto
        given ctx_unit_Yocto: DerivedUnit[Yocto, 10 ^ -24, "yocto", "y"] = DerivedUnit()
