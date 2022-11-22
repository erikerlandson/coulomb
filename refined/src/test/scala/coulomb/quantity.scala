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

class RefinedQuantityAlgebraicSuite extends CoulombSuite:
    import eu.timepit.refined.*
    import eu.timepit.refined.api.*
    import eu.timepit.refined.numeric.*

    import coulomb.*
    import coulomb.syntax.*

    import algebra.instances.all.given
    import coulomb.ops.algebra.all.{*, given}

    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.mksa.{*, given}
    import coulomb.units.us.{*, given}

    import coulomb.testing.refined.*

    test("lift") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        1.withRP[Positive].withUnit[Meter].assertQ[Refined[Int, Positive], Meter](1.withRP[Positive])
    }
