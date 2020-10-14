package coulomb.physical

import coulomb._
import coulomb.physicalconstants._
import utest._

object PhysicalConstantsSuite extends TestSuite {
  val tests =  Tests {
    test("physical constants") {
      assert(speedOfLightInVacuum[Int].show == "299792458 m/s")
      assert(planckConstant[BigDecimal].show == "6.2607015E-34 J s")
      assert(reducedPlanckConstant[BigDecimal].show == "1.054571817E-34 J s")
      assert(newtonianConstantOfGravitation[Double].show == "6.6743E-11 m^3/(kg s^2)")
      assert(vacuumElectricPermittivity[Double].show == "8.8541878128E-12 F/m")
      assert(vacuumMagneticPermeability[Double].show == "1.25663706212E-6 N/A^2")
      assert(characteristicImpedanceOfVacuum[BigDecimal].show == "376.730313668 Ω")
      assert(elementaryCharge[Double].show == "1.602176634E-19 C")
      assert(hyperfineTransitionFrequencyOfCs133[Long].show == "9192631770 Hz")
      assert(avogadroConstant[Double].show == "6.02214076E-23 mol^(-1)")
      assert(boltzmannConstant[Double].show == "1.380649E-23 J/K")
      assert(conductanceQuantum[Double].show == "7.748091729E-5 S")
      assert(josephsonConstant[Double].show == "4.835978484E14 Hz/V")
      assert(vonKlitzingConstant[Double].show == "25812.80745 Ω")
      assert(magneticFluxQuantum[Double].show == "2.067833848E-15 Wb")
      assert(inverseConductanceQuantum[Double].show == "12906.40372 Ω")
      assert(bohrMagneton[Double].show == "9.2740100783E-24 J/T")
      assert(nuclearMagneton[Double].show == "5.0507837461E-27 J/T")
      assert(fineStructureConstant[Double].show == "0.0072973525693 unitless")
      assert(inverseFineStructureConstant[Double].show == "137.035999084 unitless")
      assert(electronMass[Double].show == "9.1093837015E-31 kg")
      assert(protonMass[Double].show == "1.67262192369E-27 kg")
      assert(neutronMass[Double].show == "1.67492749804E-27 kg")
      assert(bohrRadius[Double].show == "5.29177210903E-11 m")
      assert(classicalElectronRadius[Double].show == "2.8179403262E-15 m")
      assert(electronGFactor[Double].show == "2.00231930436256 unitless")
      assert(fermiCouplingConstant[Double].show == "1.1663787E-5 GeV^(-2)")
      assert(hartreeEnergy[Double].show == "4.3597447222071E-18 J")
      assert(quantumOfReaction[Double].show == "3.6369475516E-4 m^2/s")
    }
  }
}
