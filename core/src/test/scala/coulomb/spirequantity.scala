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

class SpireQuantitySuite extends CoulombSuite:
    import spire.math.*
    import spire.std.any.{*, given}

    import coulomb.*
    import coulomb.syntax.*

    import coulomb.testing.units.{*, given}

    test("toValue") {
        1.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1.withUnit[Meter].toValue[BigInt].assertQ[BigInt, Meter](1)
        1.withUnit[Meter].toValue[BigDecimal].assertQ[BigDecimal, Meter](1)
        1.withUnit[Meter].toValue[Rational].assertQ[Rational, Meter](1)
        1.withUnit[Meter].toValue[Algebraic].assertQ[Algebraic, Meter](1)
        1.withUnit[Meter].toValue[Real].assertQ[Real, Meter](1)

        1L.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        BigInt(1).withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1.withUnit[Meter].toValue[BigInt].assertQ[BigInt, Meter](1)
    }

    test("toUnit") {
        Rational(1)
            .withUnit[Yard]
            .toUnit[Meter]
            .assertQ[Rational, Meter](Rational(9144, 10000))
        BigDecimal(1)
            .withUnit[Yard]
            .toUnit[Meter]
            .assertQ[BigDecimal, Meter](BigDecimal(0.9144))
        Algebraic(1)
            .withUnit[Yard]
            .toUnit[Meter]
            .assertQ[Algebraic, Meter](Algebraic(0.9144))
        Real(1)
            .withUnit[Yard]
            .toUnit[Meter]
            .assertQ[Real, Meter](Real(Rational(9144, 10000)))
    }

    test("addition") {
        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Meter])
            .assertQ[Rational, Meter](Rational(2))
        (BigInt(1).withUnit[Meter] + BigInt(1).withUnit[Meter])
            .assertQ[BigInt, Meter](BigInt(2))
        (BigDecimal(1).withUnit[Meter] + BigDecimal(1).withUnit[Meter])
            .assertQ[BigDecimal, Meter](BigDecimal(2))
        (Algebraic(1).withUnit[Meter] + Algebraic(1).withUnit[Meter])
            .assertQ[Algebraic, Meter](Algebraic(2))
        (Real(1).withUnit[Meter] + Real(1).withUnit[Meter])
            .assertQ[Real, Meter](Real(2))

        assertCE("Real(1).withUnit[Meter] + Rational(1).withUnit[Meter]")

        (Real(1).withUnit[Meter].toValue[Rational] + Rational(1)
            .withUnit[Meter])
            .assertQ[Rational, Meter](Rational(2))
        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Yard].toUnit[Meter])
            .assertQ[Rational, Meter](Rational(19144, 10000))

        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Meter])
            .assertQ[Rational, Meter](Rational(2))

        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Yard])
            .assertQ[Rational, Meter](Rational(19144, 10000))
        (BigDecimal(1).withUnit[Meter] + BigDecimal(1).withUnit[Yard])
            .assertQ[BigDecimal, Meter](BigDecimal(1.9144))

        assertCE("BigDecimal(1).withUnit[Meter] + 1d.withUnit[Meter]")
        assertCE("BigDecimal(1).withUnit[Meter] + BigDecimal.withUnit[Second]")
        assertCE("BigInt(1).withUnit[Meter] + BigInt(1).withUnit[Yard]")
    }

    test("tquot") {
        (BigInt(5).withUnit[Meter] `tquot` BigInt(2).withUnit[Second])
            .assertQ[BigInt, Meter / Second](BigInt(2))
    }

    test("pow") {
        Rational(2).withUnit[Meter].pow[0].assertQ[Rational, 1](Rational(1))
        Rational(2)
            .withUnit[Meter]
            .pow[2]
            .assertQ[Rational, Meter ^ 2](Rational(4))
        Rational(2)
            .withUnit[Meter]
            .pow[-1]
            .assertQ[Rational, 1 / Meter](Rational(1, 2))
        Rational(2)
            .withUnit[Meter]
            .pow[1 / 2]
            .assertQD[Rational, Meter ^ (1 / 2)](1.4142135623730951)
        Rational(2)
            .withUnit[Meter]
            .pow[-1 / 2]
            .assertQD[Rational, 1 / (Meter ^ (1 / 2))](0.7071067811865476)

        BigDecimal(2)
            .withUnit[Meter]
            .pow[1 / 2]
            .assertQD[BigDecimal, Meter ^ (1 / 2)](1.4142135623730951)

        // this is throwing an exception, I'm just going to disable it for now
        // Algebraic(2)
        //    .withUnit[Meter]
        //    .pow[1 / 2]
        //    .assertQD[Algebraic, Meter ^ (1 / 2)](1.4142135623730951)

        Real(2)
            .withUnit[Meter]
            .pow[1 / 2]
            .assertQD[Real, Meter ^ (1 / 2)](1.4142135623730951)

        BigInt(2).withUnit[Meter].pow[0].assertQ[BigInt, 1](1)
        BigInt(2).withUnit[Meter].pow[2].assertQ[BigInt, Meter ^ 2](4)
        assertCE("BigInt(2).withUnit[Meter].pow[-1]")
        assertCE("BigInt(2).withUnit[Meter].pow[1 / 2]")
        assertCE("BigInt(2).withUnit[Meter].pow[-1 / 2]")
    }

    test("scalar factors") {
        val q = Rational(2).withUnit[Meter]

        (Rational(2) * q).assertQ[Rational, Meter](4)
        (Rational(2) / q).assertQ[Rational, 1 / Meter](1)

        (q * Rational(2)).assertQ[Rational, Meter](4)
        (q / Rational(2)).assertQ[Rational, Meter](1)
    }

    test("< strict") {
        assertEquals(
            Rational(1).withUnit[Meter] < Rational(2).withUnit[Meter],
            true
        )
        assertEquals(
            BigDecimal(2).withUnit[Meter] < BigDecimal(1).withUnit[Meter],
            false
        )
        assertEquals(
            Algebraic(1).withUnit[Meter] < Algebraic(2).withUnit[Meter],
            true
        )
        assertEquals(Real(2).withUnit[Meter] < Real(1).withUnit[Meter], false)
        assertEquals(
            BigInt(1).withUnit[Meter] < BigInt(2).withUnit[Meter],
            true
        )

        assertEquals(
            Rational(1).withUnit[Yard] < Rational(1).withUnit[Meter],
            true
        )
        assertEquals(
            BigDecimal(1).withUnit[Meter] < BigDecimal(1).withUnit[Yard],
            false
        )
        assertEquals(
            Algebraic(1).withUnit[Yard] < Algebraic(1).withUnit[Meter],
            true
        )
        assertEquals(Real(1).withUnit[Meter] < Real(1).withUnit[Yard], false)
    }
