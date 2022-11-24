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
        1d.withRP[Positive].withUnit[Meter].assertQ[Refined[Double, Positive], Meter](1d.withRP[Positive])
        1f.withRP[Positive].withUnit[Meter].assertQ[Refined[Float, Positive], Meter](1f.withRP[Positive])
        1L.withRP[Positive].withUnit[Meter].assertQ[Refined[Long, Positive], Meter](1L.withRP[Positive])
        1.withRP[Positive].withUnit[Meter].assertQ[Refined[Int, Positive], Meter](1.withRP[Positive])

        1d.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Double, NonNegative], Meter](1d.withRP[NonNegative])
        1f.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Float, NonNegative], Meter](1f.withRP[NonNegative])
        1L.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Long, NonNegative], Meter](1L.withRP[NonNegative])
        1.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Int, NonNegative], Meter](1.withRP[NonNegative])
    }

    test("value") {
        1.withRP[Positive].withUnit[Meter].value.assertVT[Refined[Int, Positive]](1.withRP[Positive])
    }

    test("show") {
        assertEquals(1.withRP[Positive].withUnit[Meter].show, "1 m")
    }

    test("showFull") {
        assertEquals(1.withRP[NonNegative].withUnit[Second].showFull, "1 second")
    }

    test("toValue") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        1.withRP[Positive].withUnit[Meter].toValue[Refined[Double, Positive]]
            .assertQ[Refined[Double, Positive], Meter](1d.withRP[Positive])
        1L.withRP[NonNegative].withUnit[Meter].toValue[Refined[Float, NonNegative]]
            .assertQ[Refined[Float, NonNegative], Meter](1f.withRP[NonNegative])

        assertCE("1d.withRP[Positive].withUnit[Meter].toValue[Refined[Int, Positive]]")

        1.5.withRP[Positive].withUnit[Meter].tToValue[Refined[Int, Positive]]
            .assertQ[Refined[Int, Positive], Meter](1.withRP[Positive])
        1.5f.withRP[NonNegative].withUnit[Meter].tToValue[Refined[Int, NonNegative]]
            .assertQ[Refined[Int, NonNegative], Meter](1.withRP[NonNegative])
    }

    test("toUnit") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        1d.withRP[Positive].withUnit[Kilo * Meter].toUnit[Meter]
            .assertQ[Refined[Double, Positive], Meter](1000d.withRP[Positive])
        1f.withRP[NonNegative].withUnit[Kilo * Meter].toUnit[Meter]
            .assertQ[Refined[Float, NonNegative], Meter](1000f.withRP[NonNegative])

        assertCE("1.withRP[Positive].withUnit[Kilo * Meter].toUnit[Meter]")

        1.withRP[Positive].withUnit[Meter].tToUnit[Yard]
            .assertQ[Refined[Int, Positive], Yard](1.withRP[Positive])
        1.withRP[NonNegative].withUnit[Meter].tToUnit[Yard]
            .assertQ[Refined[Int, NonNegative], Yard](1.withRP[NonNegative])
    }

    test("add strict") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        (1d.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Double, Positive], Meter](3d.withRP[Positive])
        (1.withRP[Positive].withUnit[Meter] + 2.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Int, Positive], Meter](3.withRP[Positive])

        (1f.withRP[NonNegative].withUnit[Meter] + 2f.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Float, NonNegative], Meter](3f.withRP[NonNegative])
        (1L.withRP[NonNegative].withUnit[Meter] + 2L.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Long, NonNegative], Meter](3L.withRP[NonNegative])

        // differing value or unit types are not supported in strict policy
        assertCE("1.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter]")
        assertCE("1d.withRP[NonNegative].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter]")
        assertCE("1d.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Yard]")
    }

    test("add standard") {
        import coulomb.policy.standard.given
        import coulomb.policy.overlay.refined.algebraic.given

        // same unit and value type
        (1d.withRP[Positive].withUnit[Meter] + 2d.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Double, Positive], Meter](3d.withRP[Positive])

        // same unit, differing value types
        (1L.withRP[NonNegative].withUnit[Meter] + 2f.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Float, NonNegative], Meter](3f.withRP[NonNegative])

        // same value, differing units
        (1f.withRP[Positive].withUnit[Meter] + 1f.withRP[Positive].withUnit[Kilo * Meter])
            .assertQ[Refined[Float, Positive], Meter](1001f.withRP[Positive])

        // value and unit type are different
        (1.withRP[Positive].withUnit[Meter] + 1d.withRP[Positive].withUnit[Kilo * Meter])
            .assertQ[Refined[Double, Positive], Meter](1001d.withRP[Positive])
        (1f.withRP[NonNegative].withUnit[Meter] + 1L.withRP[NonNegative].withUnit[Kilo * Meter])
            .assertQ[Refined[Float, NonNegative], Meter](1001f.withRP[NonNegative])
    }
