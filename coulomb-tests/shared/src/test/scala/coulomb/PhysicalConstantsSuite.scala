package coulomb.physical

import coulomb._
import coulomb.physicalconstants._
import coulomb.physicalconstants.units.ElectronVolt
import coulomb.unitops._
import coulomb.mks._
import coulomb.si._
import coulomb.siprefix.Giga
import scala.math.BigDecimal
import spire.std.double._
import utest._

object PhysicalConstantsSuite extends TestSuite {
  implicit class WithEqQMEqOp[V, U](q: Quantity[V, U]) {
    // requires exact V & U types of 'q', which removes the ambiguities of "smart" conversions
    // and makes sure we are getting exactly the type we defined.
    def =?=(qt: Quantity[V, U]): Boolean = {
      val r = (q.value == qt.value)
      if (!r) {
        // help debug test when it fails
        println(s"${q.value} != ${qt.value}")
      }
      r
    }
  }

  def tests =  Tests {
    test("physical constants") {
      assert(speedOfLightInVacuum[Int] =?= Quantity[Int, Meter %/ Second](299792458))
      assert(planckConstant[BigDecimal] =?= Quantity[BigDecimal, Joule %* Second](BigDecimal("6.62607015E-34")))
      assert(reducedPlanckConstant[BigDecimal] =?= Quantity[BigDecimal, Joule %* Second](BigDecimal("1.054571817E-34")))
      assert(newtonianConstantOfGravitation[Double] =?= Quantity[Double, (Meter %^ 3) %/ (Kilogram %* (Second %^ 2))](6.6743E-11))
      assert(vacuumElectricPermittivity[Double] =?= Quantity[Double, Farad %/ Meter](8.8541878128E-12))
      assert(vacuumMagneticPermeability[Double] =?= Quantity[Double, Newton %/ (Ampere %^ 2)](1.25663706212E-6))
      assert(characteristicImpedanceOfVacuum[BigDecimal] =?= Quantity[BigDecimal, Ohm](376.730313668))
      assert(elementaryCharge[Double] =?= Quantity[Double, Coulomb](1.602176634E-19))
      assert(hyperfineTransitionFrequencyOfCs133[Long] == Quantity[Long, Hertz](9192631770L))
      assert(avogadroConstant[Double] =?= Quantity[Double, Mole %^ -1](6.02214076E23))
      assert(boltzmannConstant[Double] =?= Quantity[Double, Joule %/ Kelvin](1.380649E-23))
      assert(conductanceQuantum[Double] =?= Quantity[Double, Siemens](7.748091729E-5))
      assert(josephsonConstant[Double] =?= Quantity[Double, Hertz %/ Volt](4.835978484E14))
      assert(vonKlitzingConstant[Double] =?= Quantity[Double, Ohm](25812.80745))
      assert(magneticFluxQuantum[Double] =?= Quantity[Double, Weber](2.067833848E-15))
      assert(inverseConductanceQuantum[Double] =?= Quantity[Double, Ohm](12906.40372))
      assert(bohrMagneton[Double] =?= Quantity[Double, Joule %/ Tesla](9.2740100783E-24))
      assert(nuclearMagneton[Double] =?= Quantity[Double, Joule %/ Tesla](5.0507837461E-27))
      assert(fineStructureConstant[Double] =?= Quantity[Double, Unitless](0.0072973525693))
      assert(inverseFineStructureConstant[Double] =?= Quantity[Double, Unitless](137.035999084))
      assert(electronMass[Double] =?= Quantity[Double, Kilogram](9.1093837015E-31))
      assert(protonMass[Double] =?= Quantity[Double, Kilogram](1.67262192369E-27))
      assert(neutronMass[Double] =?= Quantity[Double, Kilogram](1.67492749804E-27))
      assert(bohrRadius[Double] =?= Quantity[Double, Meter](5.29177210903E-11))
      assert(classicalElectronRadius[Double] =?= Quantity[Double, Meter](2.8179403262E-15))
      assert(electronGFactor[Double] =?= Quantity[Double, Unitless](-2.00231930436256))
      assert(fermiCouplingConstant[Double] =?= Quantity[Double, ((Giga %* ElectronVolt) %^ -2)](1.1663787E-5))
      assert(hartreeEnergy[Double] =?= Quantity[Double, Joule](4.3597447222071E-18))
      assert(quantumOfReaction[Double] =?= Quantity[Double, (Meter %^ 2) %/ Second](3.6369475516E-4))
      assert(rydbergConstant[Double] =?= Quantity[Double, Meter %^ -1](1.097373156816E7))
      assert(thomsonCrossSection[Double] =?= Quantity[Double, Meter %^ 2](6.6524587321E-29))
      assert(wToZMassRatio[Double] =?= Quantity[Double, Unitless](0.88153))
      assert(weakMixingAngle[Double] =?= Quantity[Double, Unitless](0.2229))
      assert(atomicMassConstant[Double] =?= Quantity[Double, Kilogram](1.6605390666E-27))
      assert(faradayConstant[Double] =?= Quantity[Double, Coulomb %/ Mole](96485.33212))
      assert(molarGasConstant[Double] =?= Quantity[Double, Joule %/ (Mole %* Kelvin)](8.314462618))
      assert(molarMassConstant[Double] =?= Quantity[Double, Kilogram %/ Mole](9.9999999965E-4))
      assert(stefanBoltzmannConstant[Double] =?= Quantity[Double, Watt %/ ((Meter %^ 2) %* (Kelvin %^ 4))](5.670374419E-8))
      assert(firstRadiationConstant[Double] =?= Quantity[Double, Watt %* (Meter %^ 2)](3.741771852E-16))
      assert(firstRadiationConstantForSpectralRadiance[Double] =?= Quantity[Double, Watt %* (Meter %^ 2) %/ Steradian](1.191042972E-16))
      assert(molarMassOfCarbon12[Double] =?= Quantity[Double, Kilogram %/ Mole](0.0119999999958))
      assert(molarPlanckConstant[Double] =?= Quantity[Double, Joule %/ (Hertz %* Mole)](3.990312712E-10))
      assert(secondRadiationConstant[Double] =?= Quantity[Double, Meter %* Kelvin](0.01438776877))
      assert(wienWavelengthDisplacementLawConstant[Double] =?= Quantity[Double, Meter %* Kelvin](0.002897771955))
      assert(wienFrequencyDisplacementLawConstant[Double] =?= Quantity[Double, Hertz %/ Kelvin](5.878925757E-10))
      assert(wienEntropyDisplacementLawConstant[Double] =?= Quantity[Double, Meter %* Kelvin](0.003002916077))
    }
  }
}
