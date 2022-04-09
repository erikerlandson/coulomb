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

class ConstantsUnitsSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.policy.standard.given
    import coulomb.units.constants.{*, given}

    test("physical constant values") {
        constant[Double, SpeedOfLight].assertQD[Double, Meter / Second](299792458)
        constant[Double, PlanckConstant].assertQD[Double, Joule * Second](6.62607015e-34)
        constant[Double, ElectronGFactor].assertQD[Double, 1](-2.00231930436256)
        constant[Double, FermiCouplingConstant].assertQD[Double, 1 / ((Giga ^ 2) * (ElectronVolt ^ 2))](1.1663787e-5)
    }
