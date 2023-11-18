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
 * A selection of physical constants.
 *
 * Constants are as as defined in the following table:
 *   - https://en.wikipedia.org/wiki/List_of_physical_constants
 *
 * All units tagged as either "universal" or "frequently-used" in this table are
 * defined. Other constant definitions can be included on request.
 */
object constants:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.define.*
    import spire.math.Rational
    import coulomb.conversion.ValueConversion

    export coulomb.units.mksa.{*, given}
    export coulomb.units.si.{Mole, unit_Mole, Kelvin, unit_Kelvin}
    export coulomb.units.si.prefixes.{Giga, unit_Giga}

    /**
     * Obtain a Quantity representing a physical constant
     * @tparam V
     *   the desired value type of the constant
     * @tparam CU
     *   the unit type name representing the constant
     * @return
     *   a value Quantity[V, QU] where QU is the unit type of the constant
     *
     * {{{
     * import coulomb.units.constants.{*, given}
     *
     * val planck = constant[Double, PlanckConstant]
     * planck.show // => "6.62607015E-34 J s"
     * }}}
     */
    transparent inline def constant[V, CU](using
        cq: infra.ConstQ[CU],
        vc: ValueConversion[Rational, V]
    ): Quantity[V, cq.QU] =
        vc(cq.value).withUnit[cq.QU]

    /** Speed of light in a vacuum: 299792458 m/s */
    final type SpeedOfLight
    given unit_SpeedOfLight: DerivedUnit[
        SpeedOfLight,
        299792458 * Meter / Second,
        "speed-of-light",
        "c"
    ] =
        DerivedUnit()

    /** Planck's constant: 6.62607015×10−34 J⋅s */
    final type PlanckConstant
    given unit_PlanckConstant: DerivedUnit[
        PlanckConstant,
        (662607015 / (10 ^ 42)) * Joule * Second,
        "planck-constant",
        "ℎ"
    ] = DerivedUnit()

    /** Reduced Planck Constant: 1.054571817×10−34 J⋅s */
    final type ReducedPlanckConstant
    given unit_ReducedPlanckConstant: DerivedUnit[
        ReducedPlanckConstant,
        (1054571817 / (10 ^ 43)) * Joule * Second,
        "reduced-planck-constant",
        "ℏ"
    ] = DerivedUnit()

    /** Newton's constant of gravitation: 6.67430(15)×10−11 m3⋅kg−1⋅s−2 */
    final type GravitationalConstant
    given unit_GravitationalConstant: DerivedUnit[
        GravitationalConstant,
        (667430 / (10 ^ 16)) * (Meter ^ 3) / (Kilogram * (Second ^ 2)),
        "gravitational-constant",
        "G"
    ] = DerivedUnit()

    /** Vacuum electric permittivity: 8.8541878128(13)×10−12 F/m */
    final type VacuumElectricPermittivity
    given unit_VacuumElectricPermittivity: DerivedUnit[
        VacuumElectricPermittivity,
        (88541878128L / (10 ^ 22)) * Farad / Meter,
        "vacuum-electric-permittivity",
        "ε₀"
    ] = DerivedUnit()

    /** Vacuum magnetic permeability: 1.25663706212(19)×10−6 N⋅A−2 */
    final type VacuumMagneticPermeability
    given unit_VacuumMagneticPermeability: DerivedUnit[
        VacuumMagneticPermeability,
        (125663706212L / (10 ^ 17)) * Newton / (Ampere ^ 2),
        "vacuum-magnetic-permeability",
        "μ₀"
    ] = DerivedUnit()

    /** Characteristic impedance of vacuum: 376.730313668(57) Ω */
    final type CharacteristicImpedanceOfVacuum
    given unit_CharacteristicImpedanceOfVacuum: DerivedUnit[
        CharacteristicImpedanceOfVacuum,
        (376730313668L / (10 ^ 9)) * Ohm,
        "characteristic-impedance-of-vacuum",
        "Z₀"
    ] = DerivedUnit()

    /** The elementary charge: 1.602176634×10−19 C */
    final type ElementaryCharge
    given unit_ElementaryCharge: DerivedUnit[
        ElementaryCharge,
        (1602176634L / (10 ^ 28)) * Coulomb,
        "elementary-charge",
        "e"
    ] = DerivedUnit()

    /** Avogadro's number: 6.02214076×10+23 mol-1 */
    final type AvogadroConstant
    given unit_AvogadroConstant: DerivedUnit[
        AvogadroConstant,
        (602214076L * (10 ^ 15)) / Mole,
        "avogadro-constant",
        "Nₐ"
    ] = DerivedUnit()

    /** Boltzmann's constant: 1.380649×10−23 J⋅K−1 */
    final type BoltzmannConstant
    given unit_BoltzmannConstant: DerivedUnit[
        BoltzmannConstant,
        (1380649L / (10 ^ 29)) * Joule / Kelvin,
        "boltzmann-constant",
        "k"
    ] = DerivedUnit()

    /** Conductance quantum: 7.748091729...×10−5 S */
    final type ConductanceQuantum
    given unit_ConductanceQuantum: DerivedUnit[
        ConductanceQuantum,
        (7748091729L / (10 ^ 14)) * Siemens,
        "conductance-quantum",
        "G₀"
    ] = DerivedUnit()

    /** Josephson constant: 483597.8484...×10+9 Hz/V */
    final type JosephsonConstant
    given unit_JosephsonConstant: DerivedUnit[
        JosephsonConstant,
        (4835978484L * (10 ^ 5)) * Hertz / Volt,
        "josephson-constant",
        "Kⱼ"
    ] = DerivedUnit()

    /** Von Klitzing constant: 25812.80745... Ω */
    final type VonKlitzingConstant
    given unit_VonKlitzingConstant: DerivedUnit[
        VonKlitzingConstant,
        (2581280745L / (10 ^ 5)) * Ohm,
        "von-klitzing-constant",
        "Rₖ"
    ] = DerivedUnit()

    /** Magnetic flux quantum: 2.067833848...×10−15 Wb */
    final type MagneticFluxQuantum
    given unit_MagneticFluxQuantum: DerivedUnit[
        MagneticFluxQuantum,
        (2067833848L / (10 ^ 24)) * Weber,
        "magnetic-flux-quantum",
        "Φ₀"
    ] = DerivedUnit()

    /** Bohr magneton: 9.2740100783(28)×10−24 J/T */
    final type BohrMagneton
    given unit_BohrMagneton: DerivedUnit[
        BohrMagneton,
        (92740100783L / (10 ^ 34)) * Joule / Tesla,
        "bohr-magneton",
        "μB"
    ] = DerivedUnit()

    /** Nuclear magneton: 5.0507837461(15)×10−27 J/T */
    final type NuclearMagneton
    given unit_NuclearMagneton: DerivedUnit[
        NuclearMagneton,
        (50507837461L / (10 ^ 37)) * Joule / Tesla,
        "nuclear-magneton",
        "μₙ"
    ] = DerivedUnit()

    /** Fine structure constant: 7.2973525693(11)×10−3 */
    final type FineStructureConstant
    given unit_FineStructureConstant: DerivedUnit[
        FineStructureConstant,
        (72973525693L / (10 ^ 13)),
        "fine-structure-constant",
        "α"
    ] = DerivedUnit()

    /** Inverse fine structure constant: 137.035999084 */
    final type InverseFineStructureConstant
    given unit_InverseFineStructureConstant: DerivedUnit[
        InverseFineStructureConstant,
        (137035999084L / (10 ^ 9)),
        "inverse-fine-structure-constant",
        "α⁻ⁱ"
    ] = DerivedUnit()

    /** Electron mass: 9.1093837015(28)×10−31 kg */
    final type ElectronMass
    given unit_ElectronMass: DerivedUnit[
        ElectronMass,
        (91093837015L / (10 ^ 41)) * Kilogram,
        "electron-mass",
        "mₑ"
    ] =
        DerivedUnit()

    /** Proton mass: 1.67262192369(51)×10−27 kg */
    final type ProtonMass
    given unit_ProtonMass: DerivedUnit[
        ProtonMass,
        (167262192369L / (10 ^ 38)) * Kilogram,
        "proton-mass",
        "mₚ"
    ] =
        DerivedUnit()

    /** Neutron mass: 1.67492749804(95)×10−27 kg */
    final type NeutronMass
    given unit_NeutronMass: DerivedUnit[
        NeutronMass,
        (167492749804L / (10 ^ 38)) * Kilogram,
        "neutron-mass",
        "mₚ"
    ] =
        DerivedUnit()

    /** Bohr radius: 5.29177210903(80)×10−11 m */
    final type BohrRadius
    given unit_BohrRadius: DerivedUnit[
        BohrRadius,
        (529177210903L / (10 ^ 22)) * Meter,
        "bohr-radius",
        "a₀"
    ] =
        DerivedUnit()

    /** Classical electron radius: 2.8179403262(13)×10−15 m */
    final type ClassicalElectronRadius
    given unit_ClassicalElectronRadius: DerivedUnit[
        ClassicalElectronRadius,
        (28179403262L / (10 ^ 25)) * Meter,
        "classical-electron-radius",
        "rₑ"
    ] = DerivedUnit()

    /** Electron g-factor: −2.00231930436256(35) */
    final type ElectronGFactor
    given unit_ElectronGFactor: DerivedUnit[
        ElectronGFactor,
        (-200231930436256L / (10 ^ 14)),
        "electron-g-factor",
        "rₑ"
    ] =
        DerivedUnit()

    /** Fermi coupling constant: 1.1663787(6)×10−5 GeV−2 */
    final type FermiCouplingConstant
    given unit_FermiCouplingConstant: DerivedUnit[
        FermiCouplingConstant,
        (11663787L / (10 ^ 12)) * ((Giga * ElectronVolt) ^ -2),
        "fermi-coupling-constant",
        "GF"
    ] = DerivedUnit()

    /** electron volt: 1.602176634×10−19 J */
    final type ElectronVolt
    given unit_ElectronVolt: DerivedUnit[
        ElectronVolt,
        (1602176634L / (10 ^ 28)) * Joule,
        "electron-volt",
        "eV"
    ] =
        DerivedUnit()

    /** atomic mass constant: 1.66053906660(50)×10−27 kg */
    final type AtomicMassConstant
    given unit_AtomicMassConstant: DerivedUnit[
        AtomicMassConstant,
        (166053906660L / (10 ^ 38)) * Kilogram,
        "atomic-mass-constant",
        "mᵤ"
    ] = DerivedUnit()

    /** Faraday constant: 96485.33212... C/mol */
    final type FaradayConstant
    given unit_FaradayConstant: DerivedUnit[
        FaradayConstant,
        (9648533212L / (10 ^ 5)) * Coulomb / Mole,
        "faraday-constant",
        "F"
    ] = DerivedUnit()

    /** Molar gas constant: 8.314462618... J/(mol⋅K) */
    final type MolarGasConstant
    given unit_MolarGasConstant: DerivedUnit[
        MolarGasConstant,
        (8314462618L / (10 ^ 9)) * Joule / (Mole * Kelvin),
        "molar-gas-constant",
        "R"
    ] = DerivedUnit()

    /** Molar mass constant: 0.99999999965(30)×10−3 kg/mol */
    final type MolarMassConstant
    given unit_MolarMassConstant: DerivedUnit[
        MolarMassConstant,
        (99999999965L / (10 ^ 14)) * Kilogram / Mole,
        "molar-mass-constant",
        "Mᵤ"
    ] = DerivedUnit()

    /** Stefan-Boltzmann constant: 5.670374419...×10−8 W⋅m−2⋅K−4 */
    final type StefanBoltzmannConstant
    given unit_StefanBoltzmannConstant: DerivedUnit[
        StefanBoltzmannConstant,
        (5670374419L / (10 ^ 17)) * Watt / ((Meter ^ 2) * (Kelvin ^ 4)),
        "stefan-boltzmann-constant",
        "𝞼"
    ] = DerivedUnit()

    object infra:
        /** a typeclass for manifesting constant values and unit types */
        abstract class ConstQ[CU]:
            /** this is the unit type of the constant */
            type QU

            /** this is the numeric value of the constant */
            val value: Rational

        object ConstQ:
            import scala.quoted.*
            import coulomb.infra.meta.{*, given}

            /** named (sub)class to avoid anonymous class code generation */
            class NC[CU, QUp](val value: Rational) extends ConstQ[CU]:
                type QU = QUp

            transparent inline given g_ConstQ[CU]: ConstQ[CU] = ${
                constq[CU]
            }

            def constq[CU](using Quotes, Type[CU]): Expr[ConstQ[CU]] =
                import quotes.reflect.*
                // set the signature mode to extract constant's value from its unit type
                given sigmode: SigMode = SigMode.Constant
                TypeRepr.of[CU] match
                    // identify the DerivedUnit definition for the constant,
                    // and obtain its value and unit type as separate objects
                    case derivedunit(v, sig) =>
                        simplifysig(sig).asType match
                            case '[qu] => '{ new NC[CU, qu](${ Expr(v) }) }
                    case u =>
                        report.error(
                            s"constq: unrecognized unit declaration: ${typestr(u)}"
                        )
                        '{ new NC[CU, Nothing](Rational.zero) }
