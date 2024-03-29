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

class CoefficientSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.policy.standard.given
    import coulomb.testing.units.{*, given}
    import coulomb.rational.Rational
    import algebra.instances.all.given
    import coulomb.ops.algebra.all.{*, given}

    test("identical units") {
        assert(coefficient[Rational, Meter, Meter] eq Rational.const1)
        assert(coefficient[Rational, Liter, Liter] eq Rational.const1)
        assert(
            coefficient[
                Rational,
                Kilogram * Meter / (Second ^ 2),
                Kilogram * Meter / (Second ^ 2)
            ] eq Rational.const1
        )
    }

    test("convertible units") {
        assertEquals(coefficient[Rational, Meter, Yard], Rational(10000, 9144))
        assertEquals(coefficient[Rational, Meter ^ 3, Liter], Rational(1000))
        assertEquals(
            coefficient[
                Rational,
                Kilogram * Meter / (Second ^ 2),
                Pound * Yard / (Minute ^ 2)
            ],
            Rational(50000000000000L, 5760623099L)
        )
        assertEquals(
            coefficient[Rational, Meter ^ 3, 1000 * Liter],
            Rational(1)
        )
        assertEquals(
            coefficient[Rational, Meter, Kilo * Yard],
            Rational(10, 9144)
        )
    }

    test("non-convertible units") {
        assertCE("coefficient[Rational, Meter, Second]")
        assertCE("coefficient[Rational, Meter ^ 2, Liter]")
        assertCE(
            "coefficient[Rational, Kilogram * Meter / (Second ^ 2), Pound * Yard / (Minute ^ 3)]"
        )
    }

    test("units with embedded coefficients") {
        assertEquals(coefficient[Rational, 10 * Meter, Meter], Rational(10))
        assertEquals(coefficient[Rational, Meter, (1 / 3) * Meter], Rational(3))
        assertEquals(
            coefficient[Rational, (10 ^ 6) * Meter, Meter],
            Rational(1000000)
        )
        assertEquals(coefficient[Rational, Meter, 1.5 * Meter], Rational(2, 3))
        assertEquals(
            coefficient[Rational, Meter, 1.25f * Meter],
            Rational(4, 5)
        )
        assertEquals(
            coefficient[Rational, ((1 / 3L) * (10L ^ 100)) * Meter, Meter],
            Rational(1, 3) * Rational(10).pow(100)
        )
    }
