package coulomb

import coulomb.define.DerivedUnit
import coulomb.si._
import coulomb.mks._
import spire.math.Rational
import spire.math.ConvertableTo

package object physicalconstants {
  import infra._
  import units._

  // The wikipedia link below is used as an authoritative source for constants
  // https://en.wikipedia.org/wiki/List_of_physical_constants
  //
  // The name for each individual constant is taken directly from the link above
  // to avoid ambiguites

  def speedOfLightInVacuum[V](implicit pcq: PhysicalConstantQuantity[V, SpeedOfLightInVacuum]): Quantity[V, pcq.QU] = pcq.q

  def planckConstant[V](implicit pcq: PhysicalConstantQuantity[V, PlanckConstant]): Quantity[V, pcq.QU] = pcq.q

  def reducedPlanckConstant[V](implicit pcq: PhysicalConstantQuantity[V, ReducedPlanckConstant]): Quantity[V, pcq.QU] = pcq.q

  def newtonianConstantOfGravitation[V](implicit pcq: PhysicalConstantQuantity[V, NewtonianConstantOfGravitation]): Quantity[V, pcq.QU] = pcq.q

  def vacuumElectricPermittivity[V](implicit pcq: PhysicalConstantQuantity[V, VacuumElectricPermittivity]): Quantity[V, pcq.QU] = pcq.q

  def vacuumMagneticPermeability[V](implicit pcq: PhysicalConstantQuantity[V, VacuumMagneticPermeability]): Quantity[V, pcq.QU] = pcq.q

  def characteristicImpedanceOfVacuum[V](implicit pcq: PhysicalConstantQuantity[V, CharacteristicImpedanceOfVacuum]): Quantity[V, pcq.QU] = pcq.q

  def elementaryCharge[V](implicit pcq: PhysicalConstantQuantity[V, ElementaryCharge]): Quantity[V, pcq.QU] = pcq.q

  def hyperfineTransitionFrequencyOfCs133[V](implicit pcq: PhysicalConstantQuantity[V, HyperfineTransitionFrequencyOfCs133]): Quantity[V, pcq.QU] = pcq.q

  def avogadroConstant[V](implicit pcq: PhysicalConstantQuantity[V, AvogadroConstant]): Quantity[V, pcq.QU] = pcq.q

  def boltzmannConstant[V](implicit pcq: PhysicalConstantQuantity[V, BoltzmannConstant]): Quantity[V, pcq.QU] = pcq.q

  def conductanceQuantum[V](implicit pcq: PhysicalConstantQuantity[V, ConductanceQuantum]): Quantity[V, pcq.QU] = pcq.q

  def josephsonConstant[V](implicit pcq: PhysicalConstantQuantity[V, JosephsonConstant]): Quantity[V, pcq.QU] = pcq.q

  def vonKlitzingConstant[V](implicit pcq: PhysicalConstantQuantity[V, VonKlitzingConstant]): Quantity[V, pcq.QU] = pcq.q

  def magneticFluxQuantum[V](implicit pcq: PhysicalConstantQuantity[V, MagneticFluxQuantum]): Quantity[V, pcq.QU] = pcq.q

  def inverseConductanceQuantum[V](implicit pcq: PhysicalConstantQuantity[V, InverseConductanceQuantum]): Quantity[V, pcq.QU] = pcq.q

  def bohrMagneton[V](implicit pcq: PhysicalConstantQuantity[V, BohrMagneton]): Quantity[V, pcq.QU] = pcq.q

  def nuclearMagneton[V](implicit pcq: PhysicalConstantQuantity[V, NuclearMagneton]): Quantity[V, pcq.QU] = pcq.q

  def fineStructureConstant[V](implicit pcq: PhysicalConstantQuantity[V, FineStructureConstant]): Quantity[V, pcq.QU] = pcq.q

  def inverseFineStructureConstant[V](implicit pcq: PhysicalConstantQuantity[V, InverseFineStructureConstant]): Quantity[V, pcq.QU] = pcq.q

  def electronMass[V](implicit pcq: PhysicalConstantQuantity[V, ElectronMass]): Quantity[V, pcq.QU] = pcq.q

  def protonMass[V](implicit pcq: PhysicalConstantQuantity[V, ProtonMass]): Quantity[V, pcq.QU] = pcq.q

  def neutronMass[V](implicit pcq: PhysicalConstantQuantity[V, NeutronMass]): Quantity[V, pcq.QU] = pcq.q

  def bohrRadius[V](implicit pcq: PhysicalConstantQuantity[V, BohrRadius]): Quantity[V, pcq.QU] = pcq.q

  def classicalElectronRadius[V](implicit pcq: PhysicalConstantQuantity[V, ClassicalElectronRadius]): Quantity[V, pcq.QU] = pcq.q

  def electronGFactor[V](implicit pcq: PhysicalConstantQuantity[V, ElectronGFactor]): Quantity[V, pcq.QU] = pcq.q

  def fermiCouplingConstant[V](implicit pcq: PhysicalConstantQuantity[V, FermiCouplingConstant]): Quantity[V, pcq.QU] = pcq.q

  def hartreeEnergy[V](implicit pcq: PhysicalConstantQuantity[V, HartreeEnergy]): Quantity[V, pcq.QU] = pcq.q

  def quantumOfReaction[V](implicit pcq: PhysicalConstantQuantity[V, QuantumOfCirculation]): Quantity[V, pcq.QU] = pcq.q

  def rydbergConstant[V](implicit pcq: PhysicalConstantQuantity[V, RydbergConstant]): Quantity[V, pcq.QU] = pcq.q

  def thomsonCrossSection[V](implicit pcq: PhysicalConstantQuantity[V, ThomsonCrossSection]): Quantity[V, pcq.QU] = pcq.q

  def wToZMassRatio[V](implicit pcq: PhysicalConstantQuantity[V, WtoZMassRatio]): Quantity[V, pcq.QU] = pcq.q

  def weakMixingAngle[V](implicit pcq: PhysicalConstantQuantity[V, WeakMixingAngle]): Quantity[V, pcq.QU] = pcq.q

  def atomicMassConstant[V](implicit pcq: PhysicalConstantQuantity[V, AtomicMassConstant]): Quantity[V, pcq.QU] = pcq.q

  def faradayConstant[V](implicit pcq: PhysicalConstantQuantity[V, FaradayConstant]): Quantity[V, pcq.QU] = pcq.q

  def molarGasConstant[V](implicit pcq: PhysicalConstantQuantity[V, MolarGasConstant]): Quantity[V, pcq.QU] = pcq.q

  def molarMassConstant[V](implicit pcq: PhysicalConstantQuantity[V, MolarMassConstant]): Quantity[V, pcq.QU] = pcq.q

  def stefanBoltzmannConstant[V](implicit pcq: PhysicalConstantQuantity[V, StefanBoltzmannConstant]): Quantity[V, pcq.QU] = pcq.q

  def firstRadiationConstant[V](implicit pcq: PhysicalConstantQuantity[V, FirstRadiationConstant]): Quantity[V, pcq.QU] = pcq.q

  def firstRadiationConstantForSpectralRadiance[V](implicit pcq: PhysicalConstantQuantity[V, FirstRadiationConstantForSpectralRadiance]): Quantity[V, pcq.QU] = pcq.q

  def molarMassOfCarbon12[V](implicit pcq: PhysicalConstantQuantity[V, MolarMassOfCarbon12]): Quantity[V, pcq.QU] = pcq.q

  def molarPlanckConstant[V](implicit pcq: PhysicalConstantQuantity[V, MolarPlanckConstant]): Quantity[V, pcq.QU] = pcq.q

  def secondRadiationConstant[V](implicit pcq: PhysicalConstantQuantity[V, SecondRadiationConstant]): Quantity[V, pcq.QU] = pcq.q

  def wienWavelengthDisplacementLawConstant[V](implicit pcq: PhysicalConstantQuantity[V, WienWavelengthDisplacementLawConstant]): Quantity[V, pcq.QU] = pcq.q

  def wienFrequencyDisplacementLawConstant[V](implicit pcq: PhysicalConstantQuantity[V, WienFrequencyDisplacementLawConstant]): Quantity[V, pcq.QU] = pcq.q

  def wienEntropyDisplacementLawConstant[V](implicit pcq: PhysicalConstantQuantity[V, WienEntropyDisplacementLawConstant]): Quantity[V, pcq.QU] = pcq.q

}

package physicalconstants {

import coulomb.siprefix.Giga

  // actual unit defs in a subpackage
  object units {
    // define physical constants as derived units
    trait SpeedOfLightInVacuum
    implicit val defineSpeedOfLightInVacuum =
      DerivedUnit[SpeedOfLightInVacuum, Meter %/ Second](coef = 299792458, name = "speed-of-light", abbv = "c")

    trait PlanckConstant
    implicit val definePlanckConstant = {
      // original value = 6.62607015×10−34 J⋅s
      val denom = Rational(10).pow(34)
      val num = Rational(662607015) / Rational(10).pow(8)
      DerivedUnit[PlanckConstant, Joule %* Second](coef = num / denom, name = "planck-constant", abbv = "ℎ")
    }

    trait ReducedPlanckConstant
    implicit val defineReducedPlanckConstant = {
      // original value = 1.054571817×10−34 J⋅s
      val denom = Rational(10).pow(34)
      val num = Rational(1054571817) / Rational(10).pow(9)
      DerivedUnit[ReducedPlanckConstant, Joule %* Second](coef = num / denom, name = "reduced-planck-constant", abbv = "ℏ")
    }

    trait NewtonianConstantOfGravitation
    implicit val defineNewtonianConstantOfGravitation = {
      // original value = 6.67430(15)×10−11 m3⋅kg−1⋅s−2
      val denom = Rational(10).pow(11)
      val num = Rational(667430) / Rational(10).pow(5)
      DerivedUnit[NewtonianConstantOfGravitation, (Meter %^ 3) %/ (Kilogram %* (Second %^ 2))](coef = num / denom, name = "gravitational-constant", abbv = "G")
    }

    trait VacuumElectricPermittivity
    implicit val defineVacuumElectricPermittivity = {
      // original value = 8.8541878128(13)×10−12 F⋅m−1
      val denom = Rational(10).pow(12)
      val num = Rational(88541878128L) / Rational(10).pow(10)
      DerivedUnit[VacuumElectricPermittivity, Farad %/ Meter](coef = num / denom, name = "vacuum-electric-permittivity", abbv = "ε₀")
    }

    trait VacuumMagneticPermeability
    implicit val defineVacuumMagneticPermeability = {
      // original value = 1.25663706212(19)×10−6 N⋅A−2
      val denom = Rational(10).pow(6)
      val num = Rational(125663706212L) / Rational(10).pow(11)
      DerivedUnit[VacuumMagneticPermeability, Newton %/ (Ampere %^ 2)](coef = num / denom, name = "vacuum-magnetic-permeability", abbv = "μ₀")
    }

    trait CharacteristicImpedanceOfVacuum
    implicit val defineCharacteristicImpedanceOfVacuum = {
      // original value = 376.730313668(57) Ω
      val coef = Rational(376730313668L) / Rational(10).pow(9)
      DerivedUnit[CharacteristicImpedanceOfVacuum, Ohm](coef = coef, name = "characteristic-impedance-of-vacuum", abbv = "Z₀")
    }

    trait ElementaryCharge
    implicit val defineElementaryCharge = {
      // original value = 1.602176634×10−19 C
      val denom = Rational(10).pow(19)
      val num = Rational(1602176634L) / Rational(10).pow(9)
      DerivedUnit[ElementaryCharge, Coulomb](coef = num / denom, name = "elementary-charge", abbv = "e")
    }

    trait HyperfineTransitionFrequencyOfCs133
    implicit val defineHyperfineTransitionFrequencyOfCs133 = {
      // original value = 9192631770 Hz
      DerivedUnit[HyperfineTransitionFrequencyOfCs133, Hertz](coef = 9192631770L, name = "hyperfine-transition-frequency-of-cs-133", abbv = "∆Cs")
    }

    trait AvogadroConstant
    implicit val defineAvogadrConstant = {
      // original value = 6.02214076×1023 mol-1
      val factor = Rational(10).pow(23)
      val value = Rational(602214076L) / Rational(10).pow(8)
      DerivedUnit[AvogadroConstant, Mole %^ -1](coef = value * factor, name = "avogadro-constant", abbv = "Nₐ")
    }

    trait BoltzmannConstant
    implicit val defineBoltzmannConstant = {
      // original value = 1.380649×10−23 J⋅K−1
      val denom = Rational(10).pow(23)
      val num = Rational(1380649L) / Rational(10).pow(6)
      DerivedUnit[BoltzmannConstant, Joule %/ Kelvin](coef = num / denom, name = "boltzmann-constant", abbv = "k")
    }

    trait ConductanceQuantum
    implicit val defineConductanceQuantum = {
      // original value = 7.748091729...×10−5
      val denom = Rational(10).pow(5)
      val num = Rational(7748091729L) / Rational(10).pow(9)
      DerivedUnit[ConductanceQuantum, Siemens](coef = num / denom, name = "conductance-quantum", abbv = "G₀")
    }

    trait JosephsonConstant
    implicit val defineJosephsonConstant = {
      // original value = 483597.8484...×109 Hz⋅V−1
      val factor = Rational(10).pow(9)
      val num = Rational(4835978484L) / Rational(10).pow(4)
      DerivedUnit[JosephsonConstant, Hertz %/ Volt](coef = num * factor, name = "josephson-constant", abbv = "Kⱼ")
    }

    trait VonKlitzingConstant
    implicit val defineVonKlitzingConstant = {
      // original value = 25812.80745... Ω
      val coef = Rational(2581280745L) / Rational(10).pow(5)
      DerivedUnit[VonKlitzingConstant, Ohm](coef = coef, name = "von-klitzing-constant", abbv = "Rₖ")
    }

    trait MagneticFluxQuantum
    implicit val defineMagneticFluxQuantum = {
      // original value = 2.067833848...×10−15 Wb
      val denom = Rational(10).pow(15)
      val num = Rational(2067833848L) / Rational(10).pow(9)
      DerivedUnit[MagneticFluxQuantum, Weber](coef = num / denom, name = "magnetic-flux-quantum", abbv = "Φ₀")
    }

    trait InverseConductanceQuantum
    implicit val defineInverseConductanceQuantum = {
      // original value = 12906.40372... Ω
      val coef = Rational(1290640372L) / Rational(10).pow(5)
      DerivedUnit[InverseConductanceQuantum, Ohm](coef = coef, name = "inverse-conductance-quantum", abbv = "G₀")
    }

    trait BohrMagneton
    implicit val defineBohrMagneton = {
      // original value = 9.2740100783(28)×10−24 J⋅T−1
      val denom = Rational(10).pow(24)
      val num = Rational(92740100783L) / Rational(10).pow(10)
      DerivedUnit[BohrMagneton, Joule %/ Tesla](coef = num / denom, name = "bohr-magneton", abbv = "μB")
    }

    trait NuclearMagneton
    implicit val defineNuclearMagneton = {
      // original value = 5.0507837461(15)×10−27 J⋅T−1
      val denom = Rational(10).pow(27)
      val num = Rational(50507837461L) / Rational(10).pow(10)
      DerivedUnit[NuclearMagneton, Joule %/ Tesla](coef = num / denom, name = "nuclear-magneton", abbv = "μₙ")
    }

    trait FineStructureConstant
    implicit val defineFineStructureConstant = {
      // original value = 7.2973525693(11)×10−3
      val denom = Rational(10).pow(3)
      val num = Rational(72973525693L) / Rational(10).pow(10)
      DerivedUnit[FineStructureConstant, Unitless](coef = num / denom, name = "fine-structure-constant", abbv = "α")
    }

    trait InverseFineStructureConstant
    implicit val defineInverseFineStructureConstant = {
      // original value = 137.035999084
      val coef = Rational(137035999084L) / Rational(10).pow(9)
      DerivedUnit[InverseFineStructureConstant, Unitless](coef = coef, name = "inverse-fine-structure-constant", abbv = "α⁻ⁱ")
    }

    trait ElectronMass
    implicit val defineElectronMass = {
      // original value = 9.1093837015(28)×10−31 kg
      val denom = Rational(10).pow(31)
      val num = Rational(91093837015L) / Rational(10).pow(10)
      DerivedUnit[ElectronMass, Kilogram](coef = num / denom, name = "electron-mass", abbv = "mₑ")
    }

    trait ProtonMass
    implicit val defineProtonMass = {
      // original value = 1.67262192369(51)×10−27 kg
      val denom = Rational(10).pow(27)
      val num = Rational(167262192369L) / Rational(10).pow(11)
      DerivedUnit[ProtonMass, Kilogram](coef = num / denom, name = "proton-mass", abbv = "mₚ")
    }

    trait NeutronMass
    implicit val defineNeutronMass = {
      // original value = 1.67492749804(95)×10−27 k
      val denom = Rational(10).pow(27)
      val num = Rational(167492749804L) / Rational(10).pow(11)
      DerivedUnit[NeutronMass, Kilogram](coef = num / denom, name = "neutron-mass", abbv = "mₚ")
    }

    trait BohrRadius
    implicit val defineBohrRadius = {
      // original value = 5.29177210903(80)×10−11 m
      val denom = Rational(10).pow(11)
      val num = Rational(529177210903L) / Rational(10).pow(11)
      DerivedUnit[BohrRadius, Meter](coef = num / denom, name = "bohr-radius", abbv = "a₀")
    }

    trait ClassicalElectronRadius
    implicit val defineClassicalElectronRadius = {
      // original value = 2.8179403262(13)×10−15 m
      val denom = Rational(10).pow(15)
      val num = Rational(28179403262L) / Rational(10).pow(10)
      DerivedUnit[ClassicalElectronRadius, Meter](coef = num / denom, name = "classical-electron-radius", abbv = "rₑ")
    }

    trait ElectronGFactor
    implicit val defineElectronGFactor = {
      // original value = −2.00231930436256(35)
      val coef = Rational(200231930436256L) / Rational(10).pow(14)
      DerivedUnit[ElectronGFactor, Unitless](coef = coef, name = "electron-g-factor", abbv = "rₑ")
    }

    trait FermiCouplingConstant
    implicit val defineFermiCouplingConstant = {
      // original value = 1.1663787(6)×10−5 GeV−2
      val denom = Rational(10).pow(5)
      val num = Rational(11663787L) / Rational(10).pow(7)
      DerivedUnit[FermiCouplingConstant, (Giga %* ElectronVolt) %^ -2](coef = num / denom, name = "fermi-coupling-constant", abbv = "GF")
    }

    trait HartreeEnergy
    implicit val defineHartreeEnergy = {
      // original value = 4.3597447222071(85)×10−18 J
      val denom = Rational(10).pow(18)
      val num = Rational(43597447222071L) / Rational(10).pow(13)
      DerivedUnit[HartreeEnergy, Joule](coef = num / denom, name = "hartree-energy", abbv = "Eₕ")
    }

    trait QuantumOfCirculation
    implicit val defineQuantumOfCirculation = {
      // original value = 3.6369475516(11)×10−4 m2⋅s−1
      val denom = Rational(10).pow(4)
      val num = Rational(36369475516L) / Rational(10).pow(10)
      DerivedUnit[QuantumOfCirculation, (Meter %^ 2) %/ Second](coef = num / denom, name = "quantum-of-reaction", abbv = "ℎ/2mₑ")
    }

    trait RydbergConstant
    implicit val defineRydbergConstant = {
      // original value = 10973731.568160(21) m−1
      val coef = Rational(10973731568160L) / Rational(10).pow(6)
      DerivedUnit[RydbergConstant, Meter %^ -1](coef = coef, name = "rydberg-constant", abbv = "R∞")
    }

    trait ThomsonCrossSection
    implicit val defineThomsonCrossSection = {
      // original value = 6.6524587321(60)×10−29 m2
      val denom = Rational(10).pow(29)
      val num = Rational(66524587321L) / Rational(10).pow(10)
      DerivedUnit[ThomsonCrossSection, Meter %^ 2](coef = num / denom, name = "thomson-cross-section", abbv = "𝞼ₑ")
    }

    trait WtoZMassRatio
    implicit val defineWtoZMassRatio = {
      // original value = 0.88153(17)
      val coef = Rational(88153L) / Rational(10).pow(5)
      DerivedUnit[WtoZMassRatio, Unitless](coef = coef, name = "w-to-z-mass-ratio", abbv = "mw/mz")
    }

    trait WeakMixingAngle
    implicit val defineWeakMixingAngle = {
      // original value = 0.22290(30)
      val coef = Rational(22290L) / Rational(10).pow(5)
      DerivedUnit[WeakMixingAngle, Unitless](coef = coef, name = "weak-mixing-angle", abbv = "mw/mz")
    }

    trait AtomicMassConstant
    implicit val defineAtomicMassConstant = {
      // original value = 1.66053906660(50)×10−27 kg
      val denom = Rational(10).pow(27)
      val num = Rational(166053906660L) / Rational(10).pow(11)
      DerivedUnit[AtomicMassConstant, Kilogram](coef = num / denom, name = "atomic-mass-constant", abbv = "mᵤ")
    }

    trait FaradayConstant
    implicit val defineFaradayConstant = {
      // original value = 96485.33212... C⋅mol−1
      val coef = Rational(9648533212L) / Rational(10).pow(5)
      DerivedUnit[FaradayConstant, Coulomb %/ Mole](coef = coef, name = "faraday-constant", abbv = "F")
    }

    trait MolarGasConstant
    implicit val defineMolarGasConstant = {
      // original value = 8.314462618... J⋅mol−1⋅K−1
      val coef = Rational(8314462618L) / Rational(10).pow(9)
      DerivedUnit[MolarGasConstant, Joule %/ (Mole %* Kelvin)](coef = coef, name = "molar-gas-constant", abbv = "R")
    }

    trait MolarMassConstant
    implicit val defineMolarMassConstant = {
      // original value = 0.99999999965(30)×10−3 kg⋅mol−1
      val denom = Rational(10).pow(3)
      val num = Rational(99999999965L) / Rational(10).pow(11)
      DerivedUnit[MolarMassConstant, Kilogram %/ Mole](coef = num / denom, name = "molar-mass-constant", abbv = "Mᵤ")
    }

    trait StefanBoltzmannConstant
    implicit val defineStefanBoltzmannConstant = {
      // original value = 5.670374419...×10−8 W⋅m−2⋅K−4
      val denom = Rational(10).pow(8)
      val num = Rational(5670374419L) / Rational(10).pow(9)
      DerivedUnit[StefanBoltzmannConstant, Watt %/ ((Meter %^ 2) %* (Kelvin %^ 4))](coef = num / denom, name = "stefan-boltzmann-constant", abbv = "𝞼")
    }

    trait FirstRadiationConstant
    implicit val defineFirstRadiationConstant = {
      // original value = 3.741771852...×10−16 W⋅m2
      val denom = Rational(10).pow(16)
      val num = Rational(3741771852L) / Rational(10).pow(9)
      DerivedUnit[FirstRadiationConstant, Watt %* (Meter %^ 2)](coef = num / denom, name = "first-radiation-constant", abbv = "c₁")
    }

    trait FirstRadiationConstantForSpectralRadiance
    implicit val defineFirstRadiationConstantForSpectralRadiance = {
      // original value = 1.191042972...×10−16 W⋅m2⋅sr−1
      val denom = Rational(10).pow(16)
      val num = Rational(1191042972L) / Rational(10).pow(9)
      DerivedUnit[FirstRadiationConstantForSpectralRadiance, Watt %* (Meter %^ 2) %/ Steradian](coef = num / denom, name = "first-radiation-constant-for-spectral-ladiance", abbv = "c₁ₗ")
    }

    trait MolarMassOfCarbon12
    implicit val defineMolarMassOfCarbon12 = {
      // original value = 11.9999999958(36)×10−3 kg⋅mol−1
      val denom = Rational(10).pow(3)
      val num = Rational(119999999958L) / Rational(10).pow(10)
      DerivedUnit[MolarMassOfCarbon12, Kilogram %/ Mole](coef = num / denom, name = "molar-mass-of-carbon-12", abbv = "M₁₂C")
    }

    trait MolarPlanckConstant
    implicit val defineMolarPlanckConstant = {
      // original value = 3.990312712...×10−10 J⋅Hz−1⋅mol−1
      val denom = Rational(10).pow(10)
      val num = Rational(3990312712L) / Rational(10).pow(9)
      DerivedUnit[MolarPlanckConstant, Joule %/ (Hertz %* Mole)](coef = num / denom, name = "molar-pack-constant", abbv = "Nₐℎ")
    }

    trait SecondRadiationConstant
    implicit val defineSecondRadiationConstant = {
      // original value = 1.438776877...×10−2 m⋅K
      val denom = Rational(10).pow(2)
      val num = Rational(1438776877L) / Rational(10).pow(9)
      DerivedUnit[SecondRadiationConstant, Meter %* Kelvin](coef = num / denom, name = "second-radiation-constant", abbv = "c₂")
    }

    trait WienWavelengthDisplacementLawConstant
    implicit val defineWienWavelengthDisplacementLawConstant = {
      // original value = 2.897771955...×10−3 m⋅K
      val denom = Rational(10).pow(3)
      val num = Rational(2897771955L) / Rational(10).pow(9)
      DerivedUnit[WienWavelengthDisplacementLawConstant, Meter %* Kelvin](coef = num / denom, name = "wien-wavelength-displacement-law-constant", abbv = "𝑏")
    }

    trait WienFrequencyDisplacementLawConstant
    implicit val defineWienFrequencyDisplacementLawConstant = {
      // original value = 5.878925757...×1010 Hz⋅K−1
      val denom = Rational(10).pow(10)
      val num = Rational(5878925757L) / Rational(10).pow(9)
      DerivedUnit[WienFrequencyDisplacementLawConstant, Hertz %/ Kelvin](coef = num / denom, name = "wien-frequency-displacement-law-constant", abbv = "𝑏′")
    }

    trait WienEntropyDisplacementLawConstant
    implicit val defineWienEntropyDisplacementLawConstant = {
      // original value = 3.002916077...×10−3 m⋅K
      val denom = Rational(10).pow(3)
      val num = Rational(3002916077L) / Rational(10).pow(9)
      DerivedUnit[WienEntropyDisplacementLawConstant, Meter %* Kelvin](coef = num / denom, name = "wien-entropy-displacement-law-constant", abbv = "𝑏entropy")
    }


  }

  package infra {

    trait PhysicalConstantQuantity[V, CU] {
      type QU
      val q: Quantity[V, QU]
    }
    object PhysicalConstantQuantity {
      type Aux[T, V, CU] = PhysicalConstantQuantity[V, CU] {type QU = T}

      implicit def summonConstant[V, CU, U](implicit
        dcu: DerivedUnit[CU, U],
        ctv: ConvertableTo[V]): Aux[U, V, CU] =
        new PhysicalConstantQuantity[V, CU] {
          type QU = U
          val q: Quantity[V, QU] = new Quantity[V, U](ctv.fromType[Rational](dcu.coef))
        }
    }
  }

}

