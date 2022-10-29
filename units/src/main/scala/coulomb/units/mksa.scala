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
 * Mechanical and electromagnetic units derived from Meter, Kilogram, Second and
 * Ampere
 *   - https://en.wikipedia.org/wiki/MKS_system_of_units#Electromagnetic_units
 *
 * Includes the units defined in [[coulomb.units.mks]]
 */
object mksa:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*
    import coulomb.units.mks.*

    export coulomb.units.si.{Ampere, ctx_unit_Ampere}
    export coulomb.units.mks.{*, given}

    /** Unit of electric charge */
    final type Coulomb
    given ctx_unit_Coulomb
        : DerivedUnit[Coulomb, Ampere * Second, "coulomb", "C"] = DerivedUnit()

    /** Unit of electric voltage */
    final type Volt
    given ctx_unit_Volt: DerivedUnit[Volt, Watt / Ampere, "volt", "V"] =
        DerivedUnit()

    /** Unit of electric resistance */
    final type Ohm
    given ctx_unit_Ohm: DerivedUnit[Ohm, Volt / Ampere, "ohm", "Ω"] =
        DerivedUnit()

    /** Unit of electric capacitance */
    final type Farad
    given ctx_unit_Farad: DerivedUnit[Farad, Coulomb / Volt, "farad", "F"] =
        DerivedUnit()

    /** Unit of electric conductance */
    final type Siemens
    given ctx_unit_Siemens: DerivedUnit[Siemens, 1 / Ohm, "siemens", "S"] =
        DerivedUnit()

    /** Unit of magnetic flux */
    final type Weber
    given ctx_unit_Weber: DerivedUnit[Weber, Volt * Second, "weber", "Wb"] =
        DerivedUnit()

    /** Unit of magnetic flux density */
    final type Tesla
    given ctx_unit_Tesla
        : DerivedUnit[Tesla, Weber / (Meter ^ 2), "tesla", "T"] = DerivedUnit()

    /** Unit of electric inductance */
    final type Henry
    given ctx_unit_Henry: DerivedUnit[Henry, Weber / Ampere, "henry", "H"] =
        DerivedUnit()
