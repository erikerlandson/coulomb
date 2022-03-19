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
    given ctx_unit_Meter: BaseUnit[Meter, "meter", "m"] with {}

    final type Kilogram
    given ctx_unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] with {}

    final type Second
    given ctx_unit_Second: BaseUnit[Second, "second", "s"] with {}

    final type Ampere
    given ctx_unit_Ampere: BaseUnit[Ampere, "ampere", "A"] with {}

    final type Mole
    given ctx_unit_Mole: BaseUnit[Mole, "mole", "mol"] with {}

    final type Candela
    given ctx_unit_Candela: BaseUnit[Candela, "candela", "cd"] with {}

    final type Kelvin
    given ctx_unit_Kelvin: BaseUnit[Kelvin, "Kelvin", "K"] with {}

    object prefixes:
        import coulomb.{`/`, `^`}

        final type Deka
        given ctx_unit_Deka: DerivedUnit[Deka, 10, "deka", "da"] with {}

        final type Hecto
        given ctx_unit_Hecto: DerivedUnit[Hecto, 100, "hecto", "h"] with {}

        final type Kilo
        given ctx_unit_Kilo: DerivedUnit[Kilo, 10 ^ 3, "kilo", "k"] with {}

        final type Mega
        given ctx_unit_Mega: DerivedUnit[Mega, 10 ^ 6, "mega", "M"] with {}

        final type Giga
        given ctx_unit_Giga: DerivedUnit[Giga, 10 ^ 9, "giga", "G"] with {}

        final type Tera
        given ctx_unit_Tera: DerivedUnit[Tera, 10 ^ 12, "tera", "T"] with {}

        final type Peta
        given ctx_unit_Peta: DerivedUnit[Peta, 10 ^ 15, "peta", "P"] with {}

        final type Exa
        given ctx_unit_Exa: DerivedUnit[Exa, 10 ^ 18, "exa", "E"] with {}

        final type Zetta
        given ctx_unit_Zetta: DerivedUnit[Zetta, 10 ^ 21, "zetta", "Z"] with {}

        final type Yotta
        given ctx_unit_Yotta: DerivedUnit[Yotta, 10 ^ 24, "yotta", "Y"] with {}

        final type Deci
        given ctx_unit_Deci: DerivedUnit[Deci, 1 / 10, "deci", "d"] with {}

        final type Centi
        given ctx_unit_Centi: DerivedUnit[Centi, 1 / 100, "centi", "c"] with {}

        final type Milli
        given ctx_unit_Milli: DerivedUnit[Milli, 10 ^ -3, "milli", "m"] with {}

        final type Micro
        given ctx_unit_Micro: DerivedUnit[Micro, 10 ^ -6, "micro", "μ"] with {}

        final type Nano
        given ctx_unit_Nano: DerivedUnit[Nano, 10 ^ -9, "nano", "n"] with {}

        final type Pico
        given ctx_unit_Pico: DerivedUnit[Pico, 10 ^ -12, "pico", "p"] with {}

        final type Femto
        given ctx_unit_Femto: DerivedUnit[Femto, 10 ^ -15, "femto", "f"] with {}

        final type Atto
        given ctx_unit_Atto: DerivedUnit[Atto, 10 ^ -18, "atto", "a"] with {}

        final type Zepto
        given ctx_unit_Zepto: DerivedUnit[Zepto, 10 ^ -21, "zepto", "z"] with {}

        final type Yocto
        given ctx_unit_Yocto: DerivedUnit[Yocto, 10 ^ -24, "yocto", "y"] with {}
