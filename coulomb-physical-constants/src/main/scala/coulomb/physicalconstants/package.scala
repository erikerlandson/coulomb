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

}

package physicalconstants {

  // actual unit defs in a subpackage
  object units {
    // define physical constants as derived units
    trait SpeedOfLightInVacuum
    implicit def defineSpeedOfLightInVacuum =
      DerivedUnit[SpeedOfLightInVacuum, Meter %/ Second](coef = 299792458, name= "speed-of-light", abbv = "c")

    trait PlanckConstant
    implicit def definePlanckConstant = {
      // original value = 6.62607015×10−34 J⋅s
      val denom = Rational(10).pow(34)
      val num = Rational(62607015) / Rational(10).pow(7)
      DerivedUnit[PlanckConstant, Joule %* Second](coef = num / denom, name= "planck-constant", abbv = "ℎ")
    }

    trait ReducedPlanckConstant
    implicit def defineReducedPlanckConstant = {
      // original value = 1.054571817×10−34 J⋅s
      val denom = Rational(10).pow(34)
      val num = Rational(1054571817) / Rational(10).pow(9)
      DerivedUnit[ReducedPlanckConstant, Joule %* Second](coef = num / denom, name= "reduced-planck-constant", abbv = "ℏ")
    }

    trait NewtonianConstantOfGravitation
    implicit def defineNewtonianConstantOfGravitation = {
      // original value = 6.67430(15)×10−11 m3⋅kg−1⋅s−2
      val denom = Rational(10).pow(11)
      val num = Rational(667430) / Rational(10).pow(5)
      DerivedUnit[NewtonianConstantOfGravitation, (Meter %^ 3) %/ (Kilogram %* (Second %^ 2))](coef = num / denom, name= "gravitational-constant", abbv = "G")
    }

    trait VacuumElectricPermittivity
    implicit def defineVacuumElectricPermittivity = {
      // original value = 8.8541878128(13)×10−12 F⋅m−1
      val denom = Rational(10).pow(12)
      val num = Rational(88541878128L) / Rational(10).pow(10)
      DerivedUnit[VacuumElectricPermittivity, Farad %/ Meter](coef = num / denom, name= "vacuum-electric-permittivity", abbv = "ε₀")
    }

    trait VacuumMagneticPermeability
    implicit def defineVacuumMagneticPermeability = {
      // original value = 1.25663706212(19)×10−6 N⋅A−2
      val denom = Rational(10).pow(6)
      val num = Rational(125663706212L) / Rational(10).pow(11)
      DerivedUnit[VacuumMagneticPermeability, Newton %/ (Ampere %^ 2)](coef = num / denom, name= "vacuum-magnetic-permeability", abbv = "μ₀")
    }

    trait CharacteristicImpedanceOfVacuum
    implicit def defineCharacteristicImpedanceOfVacuum = {
      // original value = 376.730313668(57) Ω
      val coef = Rational(376730313668L) / Rational(10).pow(9)
      DerivedUnit[CharacteristicImpedanceOfVacuum, Ohm](coef = coef, name= "characteristic-impedance-of-vacuum", abbv = "Z₀")
    }

    trait ElementaryCharge
    implicit def defineElementaryCharge = {
      // original value = 1.602176634×10−19 C
      val denom = Rational(10).pow(19)
      val num = Rational(1602176634L) / Rational(10).pow(9)
      DerivedUnit[ElementaryCharge, Coulomb](coef = num / denom, name= "elementary-charge", abbv = "e")
    }

    trait HyperfineTransitionFrequencyOfCs133
    implicit def defineHyperfineTransitionFrequencyOfCs133 = {
      // original value = 9192631770 Hz
      DerivedUnit[HyperfineTransitionFrequencyOfCs133, Hertz](coef = 9192631770L, name= "hyperfine-transition-frequency-of-cs-133", abbv = "∆Cs")
    }

    trait AvogadroConstant
    implicit def defineAvogadrConstant = {
      // original value = 6.02214076×1023 mol-1
      val denom = Rational(10).pow(23)
      val num = Rational(602214076L) / Rational(10).pow(8)
      DerivedUnit[AvogadroConstant, Mole %^ -1](coef = num / denom, name= "avogadro-constant", abbv = "Nₐ")
    }

    trait BoltzmannConstant
    implicit def defineBoltzmannConstant = {
      // original value = 1.380649×10−23 J⋅K−1
      val denom = Rational(10).pow(23)
      val num = Rational(1380649L) / Rational(10).pow(6)
      DerivedUnit[BoltzmannConstant, Joule %/ Kelvin](coef = num / denom, name= "boltzmann-constant", abbv = "k")
    }

    trait ConductanceQuantum
    implicit def defineConductanceQuantum = {
      // original value = 7.748091729...×10−5
      val denom = Rational(10).pow(5)
      val num = Rational(7748091729L) / Rational(10).pow(9)
      DerivedUnit[ConductanceQuantum, Siemens](coef = num / denom, name= "conductance-quantum", abbv = "G₀")
    }

    trait JosephsonConstant
    implicit def defineJosephsonConstant = {
      // original value = 483597.8484...×109 Hz⋅V−1
      //
      val factor = Rational(10).pow(9)
      val num = Rational(4835978484L) / Rational(10).pow(4)
      DerivedUnit[JosephsonConstant, Hertz %/ Volt](coef = num * factor, name= "josephson-constant", abbv = "Kⱼ")
    }

    trait VonKlitzingConstant
    implicit def defineVonKlitzingConstant = {
      // original value = 25812.80745... Ω
      val coef = Rational(2581280745L) / Rational(10).pow(5)
      DerivedUnit[VonKlitzingConstant, Ohm](coef = coef, name= "von-klitzing-constant", abbv = "Rₖ")
    }

    trait MagneticFluxQuantum
    implicit def defineMagneticFluxQuantum = {
      // original value = 2.067833848...×10−15 Wb
      val denom = Rational(10).pow(15)
      val num = Rational(2067833848L) / Rational(10).pow(9)
      DerivedUnit[MagneticFluxQuantum, Weber](coef = num / denom, name= "magnetic-flux-quantum", abbv = "Φ₀")
    }

  }

  package infra {

    trait PhysicalConstantQuantity[V, CU] {
      type QU
      val q: Quantity[V, QU]
    }
    object PhysicalConstantQuantity {
      type Aux[T, V, CU] = PhysicalConstantQuantity[V, CU] {type QU = T}

      implicit def summon[V, CU, U](implicit
        dcu: DerivedUnit[CU, U],
        ctv: ConvertableTo[V]): Aux[U, V, CU] =
        new PhysicalConstantQuantity[V, CU] {
          type QU = U
          val q: Quantity[V, QU] = new Quantity[V, U](ctv.fromType[Rational](dcu.coef))
        }
    }
  }

}

