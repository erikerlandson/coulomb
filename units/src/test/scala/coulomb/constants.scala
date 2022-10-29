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

import coulomb.testing.CoulombSuite

class PhysicalConstantsSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.policy.standard.given
    import coulomb.units.constants.{*, given}

    test("physical constant values") {
        constant[Double, SpeedOfLight].assertQD[Double, Meter / Second](
            299792458
        )
        constant[Double, PlanckConstant].assertQD[Double, Joule * Second](
            6.62607015e-34
        )
        constant[Double, ReducedPlanckConstant]
            .assertQD[Double, Joule * Second](1.054571817e-34)
        constant[Double, GravitationalConstant]
            .assertQD[Double, (Meter ^ 3) / (Kilogram * (Second ^ 2))](
                6.67430e-11
            )
        constant[Double, VacuumElectricPermittivity]
            .assertQD[Double, Farad / Meter](
                8.8541878128e-12
            )
        constant[Double, VacuumMagneticPermeability]
            .assertQD[Double, Newton / (Ampere ^ 2)](
                1.25663706212e-6
            )
        constant[Double, CharacteristicImpedanceOfVacuum].assertQD[Double, Ohm](
            376.730313668
        )
        constant[Double, ElementaryCharge].assertQD[Double, Coulomb](
            1.602176634e-19
        )
        constant[Double, AvogadroConstant].assertQD[Double, 1 / Mole](
            6.02214076e23
        )
        constant[Double, BoltzmannConstant].assertQD[Double, Joule / Kelvin](
            1.380649e-23
        )
        constant[Double, ConductanceQuantum].assertQD[Double, Siemens](
            7.748091729e-5
        )
        constant[Double, JosephsonConstant].assertQD[Double, Hertz / Volt](
            483597.8484e9
        )
        constant[Double, VonKlitzingConstant].assertQD[Double, Ohm](25812.80745)
        constant[Double, MagneticFluxQuantum].assertQD[Double, Weber](
            2.067833848e-15
        )
        constant[Double, BohrMagneton].assertQD[Double, Joule / Tesla](
            9.2740100783e-24
        )
        constant[Double, NuclearMagneton].assertQD[Double, Joule / Tesla](
            5.0507837461e-27
        )
        constant[Double, FineStructureConstant].assertQD[Double, 1](
            7.2973525693e-3
        )
        constant[Double, InverseFineStructureConstant].assertQD[Double, 1](
            137.035999084
        )
        constant[Double, ElectronMass].assertQD[Double, Kilogram](
            9.1093837015e-31
        )
        constant[Double, ProtonMass].assertQD[Double, Kilogram](
            1.67262192369e-27
        )
        constant[Double, NeutronMass].assertQD[Double, Kilogram](
            1.67492749804e-27
        )
        constant[Double, BohrRadius].assertQD[Double, Meter](5.29177210903e-11)
        constant[Double, ClassicalElectronRadius].assertQD[Double, Meter](
            2.8179403262e-15
        )
        constant[Double, ElectronGFactor].assertQD[Double, 1](-2.00231930436256)
        constant[Double, FermiCouplingConstant]
            .assertQD[Double, 1 / ((Giga ^ 2) * (ElectronVolt ^ 2))](
                1.1663787e-5
            )
        constant[Double, ElectronVolt].assertQD[Double, Joule](1.602176634e-19)
        constant[Double, AtomicMassConstant].assertQD[Double, Kilogram](
            1.66053906660e-27
        )
        constant[Double, FaradayConstant].assertQD[Double, Coulomb / Mole](
            96485.33212
        )
        constant[Double, MolarGasConstant]
            .assertQD[Double, Joule / (Mole * Kelvin)](8.314462618)
        constant[Double, MolarMassConstant].assertQD[Double, Kilogram / Mole](
            0.99999999965e-3
        )
        constant[Double, StefanBoltzmannConstant]
            .assertQD[Double, Watt / ((Meter ^ 2) * (Kelvin ^ 4))](
                5.670374419e-8
            )
    }
