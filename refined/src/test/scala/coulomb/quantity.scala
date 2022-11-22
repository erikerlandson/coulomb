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
        1.withRP[Positive].withUnit[Meter].assertQ[Refined[Int, Positive], Meter](1.withRP[Positive])
    }

    test("add strict") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        (1d.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Double, Positive], Meter](3d.withRP[Positive])
        (1f.withRP[Positive].withUnit[Meter] + 2f.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Float, Positive], Meter](3f.withRP[Positive])
        (1L.withRP[Positive].withUnit[Meter] + 2L.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Long, Positive], Meter](3L.withRP[Positive])
        (1.withRP[Positive].withUnit[Meter] + 2.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Int, Positive], Meter](3.withRP[Positive])

        (1d.withRP[NonNegative].withUnit[Meter] + 2d.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Double, NonNegative], Meter](3d.withRP[NonNegative])
        (1f.withRP[NonNegative].withUnit[Meter] + 2f.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Float, NonNegative], Meter](3f.withRP[NonNegative])
        (1L.withRP[NonNegative].withUnit[Meter] + 2L.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Long, NonNegative], Meter](3L.withRP[NonNegative])
        (1.withRP[NonNegative].withUnit[Meter] + 2.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Int, NonNegative], Meter](3.withRP[NonNegative])

        // differing value or unit types are not supported in strict policy
        assertCE("1.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter]")
        assertCE("1d.withRP[NonNegative].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter]")
        assertCE("1d.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Yard]")
    }
