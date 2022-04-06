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

// to use with 'export' these need to be in objects
object si:
    import coulomb.define.*

    final type Meter
    given ctx_unit_Meter: BaseUnit[Meter, "meter", "m"] = BaseUnit()

    final type Kilogram
    given ctx_unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] = BaseUnit()

    final type Second
    given ctx_unit_Second: BaseUnit[Second, "second", "s"] = BaseUnit()

    final type Ampere
    given ctx_unit_Ampere: BaseUnit[Ampere, "ampere", "A"] = BaseUnit() 

    final type Mole
    given ctx_unit_Mole: BaseUnit[Mole, "mole", "mol"] = BaseUnit()

    final type Candela
    given ctx_unit_Candela: BaseUnit[Candela, "candela", "cd"] = BaseUnit()

    final type Kelvin
    given ctx_unit_Kelvin: BaseUnit[Kelvin, "Kelvin", "K"] = BaseUnit()

    object prefixes:
        import coulomb.{`/`, `^`}

        final type Deka
        given ctx_unit_Deka: DerivedUnit[Deka, 10, "deka", "da"] = DerivedUnit()

        final type Hecto
        given ctx_unit_Hecto: DerivedUnit[Hecto, 100, "hecto", "h"] = DerivedUnit()

        final type Kilo
        given ctx_unit_Kilo: DerivedUnit[Kilo, 10 ^ 3, "kilo", "k"] = DerivedUnit()

        final type Mega
        given ctx_unit_Mega: DerivedUnit[Mega, 10 ^ 6, "mega", "M"] = DerivedUnit()

        final type Giga
        given ctx_unit_Giga: DerivedUnit[Giga, 10 ^ 9, "giga", "G"] = DerivedUnit()

        final type Tera
        given ctx_unit_Tera: DerivedUnit[Tera, 10 ^ 12, "tera", "T"] = DerivedUnit()

        final type Peta
        given ctx_unit_Peta: DerivedUnit[Peta, 10 ^ 15, "peta", "P"] = DerivedUnit()

        final type Exa
        given ctx_unit_Exa: DerivedUnit[Exa, 10 ^ 18, "exa", "E"] = DerivedUnit()

        final type Zetta
        given ctx_unit_Zetta: DerivedUnit[Zetta, 10 ^ 21, "zetta", "Z"] = DerivedUnit()

        final type Yotta
        given ctx_unit_Yotta: DerivedUnit[Yotta, 10 ^ 24, "yotta", "Y"] = DerivedUnit()

        final type Deci
        given ctx_unit_Deci: DerivedUnit[Deci, 1 / 10, "deci", "d"] = DerivedUnit()

        final type Centi
        given ctx_unit_Centi: DerivedUnit[Centi, 1 / 100, "centi", "c"] = DerivedUnit()

        final type Milli
        given ctx_unit_Milli: DerivedUnit[Milli, 10 ^ -3, "milli", "m"] = DerivedUnit()

        final type Micro
        given ctx_unit_Micro: DerivedUnit[Micro, 10 ^ -6, "micro", "μ"] = DerivedUnit()

        final type Nano
        given ctx_unit_Nano: DerivedUnit[Nano, 10 ^ -9, "nano", "n"] = DerivedUnit()

        final type Pico
        given ctx_unit_Pico: DerivedUnit[Pico, 10 ^ -12, "pico", "p"] = DerivedUnit()

        final type Femto
        given ctx_unit_Femto: DerivedUnit[Femto, 10 ^ -15, "femto", "f"] = DerivedUnit()

        final type Atto
        given ctx_unit_Atto: DerivedUnit[Atto, 10 ^ -18, "atto", "a"] = DerivedUnit()

        final type Zepto
        given ctx_unit_Zepto: DerivedUnit[Zepto, 10 ^ -21, "zepto", "z"] = DerivedUnit()

        final type Yocto
        given ctx_unit_Yocto: DerivedUnit[Yocto, 10 ^ -24, "yocto", "y"] = DerivedUnit()
