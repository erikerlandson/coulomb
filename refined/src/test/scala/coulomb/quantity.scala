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
    import coulomb.syntax.refined.*

    import algebra.instances.all.given
    import coulomb.ops.algebra.all.{*, given}

    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.mksa.{*, given}
    import coulomb.units.us.{*, given}

    import coulomb.testing.refined.*

    type RefinedE[V, P] = Either[String, Refined[V, P]]

    test("lift") {
        1d.withRP[Positive].withUnit[Meter].assertQ[Refined[Double, Positive], Meter](1d.withRP[Positive])
        1f.withRP[Positive].withUnit[Meter].assertQ[Refined[Float, Positive], Meter](1f.withRP[Positive])
        1L.withRP[Positive].withUnit[Meter].assertQ[Refined[Long, Positive], Meter](1L.withRP[Positive])
        1.withRP[Positive].withUnit[Meter].assertQ[Refined[Int, Positive], Meter](1.withRP[Positive])

        1d.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Double, NonNegative], Meter](1d.withRP[NonNegative])
        1f.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Float, NonNegative], Meter](1f.withRP[NonNegative])
        1L.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Long, NonNegative], Meter](1L.withRP[NonNegative])
        1.withRP[NonNegative].withUnit[Meter].assertQ[Refined[Int, NonNegative], Meter](1.withRP[NonNegative])

        refineV[Positive](1d).withUnit[Meter]
            .assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](1d))
        refineVU[Positive, Meter](1d)
            .assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](1d))
        1d.withRefinedUnit[Positive, Meter]
            .assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](1d))
    }

    test("value") {
        1.withRP[Positive].withUnit[Meter].value.assertVT[Refined[Int, Positive]](1.withRP[Positive])

        refineVU[Positive, Meter](1).value.assertVT[RefinedE[Int, Positive]](refineV[Positive](1))
    }

    test("show") {
        assertEquals(1.withRP[Positive].withUnit[Meter].show, "1 m")
        assertEquals(refineVU[Positive, Meter](1).show, "Right(1) m")
    }

    test("showFull") {
        assertEquals(1.withRP[NonNegative].withUnit[Second].showFull, "1 second")
        assertEquals(refineVU[Positive, Meter](1).showFull, "Right(1) meter")
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

        refineVU[Positive, Meter](1).toValue[RefinedE[Double, Positive]]
            .assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](1d))
        assert(refineVU[Positive, Meter](Double.MinPositiveValue)
                   .toValue[RefinedE[Float, Positive]].value.isLeft)
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

        refineVU[Positive, Kilo * Meter](1d).toUnit[Meter]
            .assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](1000d))
        assert(refineVU[Positive, Meter](Double.MinPositiveValue)
                   .toUnit[Kilo * Meter].value.isLeft)
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

        val x = refineVU[Positive, Meter](1d)
        val z = refineVU[Positive, Meter](0d)
        (x + x).assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](2d))
        assert((x + z).value.isLeft)
        assert((z + x).value.isLeft)
        assert((z + z).value.isLeft)
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

        val x = refineVU[Positive, Meter](1d)
        val y = refineVU[Positive, Kilo * Meter](1)
        val z = refineVU[Positive, Kilo * Meter](0)
        (x + y).assertQ[RefinedE[Double, Positive], Meter](refineV[Positive](1001d))
        assert((x + z).value.isLeft)
        assert((z + x).value.isLeft)
        assert((z + z).value.isLeft)
    }

    test("multiply strict") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        (2d.withRP[Positive].withUnit[Meter] * 3d.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Double, Positive], Meter ^ 2](6d.withRP[Positive])
        (2.withRP[NonNegative].withUnit[Meter] * 3.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Int, NonNegative], Meter ^ 2](6.withRP[NonNegative])

        // differing value types are not supported in strict policy
        assertCE("2.withRP[Positive].withUnit[Meter] * 3d.withRP[Positive].withUnit[Meter]")
        assertCE("2d.withRP[NonNegative].withUnit[Meter] * 3d.withRP[Positive].withUnit[Meter]")

        val x = refineVU[Positive, Meter](2d)
        val z = refineVU[Positive, Meter](0d)
        (x * x).assertQ[RefinedE[Double, Positive], Meter ^ 2](refineV[Positive](4d))
        assert((x * z).value.isLeft)
        assert((z * x).value.isLeft)
        assert((z * z).value.isLeft)
    }

    test("multiply standard") {
        import coulomb.policy.standard.given
        import coulomb.policy.overlay.refined.algebraic.given

        (2f.withRP[Positive].withUnit[Meter] * 3f.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Float, Positive], Meter ^ 2](6f.withRP[Positive])
        (2L.withRP[NonNegative].withUnit[Meter] * 3L.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Long, NonNegative], Meter ^ 2](6L.withRP[NonNegative])

        (2.withRP[Positive].withUnit[Meter] * 3d.withRP[Positive].withUnit[Meter])
            .assertQ[Refined[Double, Positive], Meter ^ 2](6d.withRP[Positive])
        (2f.withRP[NonNegative].withUnit[Meter] * 3L.withRP[NonNegative].withUnit[Meter])
            .assertQ[Refined[Float, NonNegative], Meter ^ 2](6f.withRP[NonNegative])

        val x = refineVU[Positive, Meter](2d)
        val y = refineVU[Positive, Meter](2)
        val z = refineVU[Positive, Meter](0)
        (x * y).assertQ[RefinedE[Double, Positive], Meter ^ 2](refineV[Positive](4d))
        assert((x * z).value.isLeft)
        assert((z * x).value.isLeft)
        assert((z * z).value.isLeft)
    }

    test("divide strict") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        (12d.withRP[Positive].withUnit[Meter] / 3d.withRP[Positive].withUnit[Second])
            .assertQ[Refined[Double, Positive], Meter / Second](4d.withRP[Positive])
        (12f.withRP[Positive].withUnit[Meter] / 3f.withRP[Positive].withUnit[Second])
            .assertQ[Refined[Float, Positive], Meter / Second](4f.withRP[Positive])

        // differing values types not supported
        assertCE("12d.withRP[Positive].withUnit[Meter] / 3f.withRP[Positive].withUnit[Second]")
        // NonNegative is not multiplicative group
        assertCE("12d.withRP[NonNegative].withUnit[Meter] / 3d.withRP[NonNegative].withUnit[Second]")
        // Integrals are not a multiplicative group
        assertCE("12.withRP[Positive].withUnit[Meter] / 3.withRP[Positive].withUnit[Second]")
    }

    test("divide standard") {
        import coulomb.policy.standard.given
        import coulomb.policy.overlay.refined.algebraic.given

        (12d.withRP[Positive].withUnit[Meter] / 3d.withRP[Positive].withUnit[Second])
            .assertQ[Refined[Double, Positive], Meter / Second](4d.withRP[Positive])

        (12d.withRP[Positive].withUnit[Meter] / 3.withRP[Positive].withUnit[Second])
            .assertQ[Refined[Double, Positive], Meter / Second](4d.withRP[Positive])
        (12.withRP[Positive].withUnit[Meter] / 3f.withRP[Positive].withUnit[Second])
            .assertQ[Refined[Float, Positive], Meter / Second](4f.withRP[Positive])
 
        // NonNegative is not multiplicative group
        assertCE("12d.withRP[NonNegative].withUnit[Meter] / 3d.withRP[NonNegative].withUnit[Second]")
        // Integrals are not a multiplicative group
        assertCE("12.withRP[Positive].withUnit[Meter] / 3.withRP[Positive].withUnit[Second]")
    }

    test("power") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.refined.algebraic.given

        // FractionalPower (algebras supporting rational exponents)
        2d.withRP[Positive].withUnit[Meter].pow[0]
            .assertQ[Refined[Double, Positive], 1](1d.withRP[Positive])
        2d.withRP[Positive].withUnit[Meter].pow[2]
            .assertQ[Refined[Double, Positive], Meter ^ 2](4d.withRP[Positive])
        2d.withRP[Positive].withUnit[Meter].pow[-1]
            .assertQ[Refined[Double, Positive], 1 / Meter](0.5d.withRP[Positive])
        4d.withRP[Positive].withUnit[Meter].pow[1 / 2]
            .assertQ[Refined[Double, Positive], Meter ^ (1 / 2)](2d.withRP[Positive])

        // non-negative integer exponents allowed by multiplicative monoid
        2.withRP[Positive].withUnit[Meter].pow[0]
            .assertQ[Refined[Int, Positive], 1](1.withRP[Positive])
        2.withRP[Positive].withUnit[Meter].pow[2]
            .assertQ[Refined[Int, Positive], Meter ^ 2](4.withRP[Positive])
        assertCE("2.withRP[Positive].withUnit[Meter].pow[-1]")
    }
