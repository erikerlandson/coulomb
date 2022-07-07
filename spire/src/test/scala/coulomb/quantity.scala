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
    import coulomb.*
    import coulomb.syntax.*

    import algebra.instances.all.given
    import coulomb.ops.algebra.spire.all.given

    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.mksa.{*, given}
    import coulomb.units.us.{*, given}
    
    test("toValue") {
        import coulomb.policy.spire.strict.given

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

        assertCE("1d.withUnit[Meter].toValue[Int]")
        2.5d.withUnit[Meter].tToValue[Int].assertQ[Int, Meter](2)
        assertCE("Rational(1).withUnit[Meter].toValue[BigInt]")
        Rational(5,2).withUnit[Meter].tToValue[BigInt].assertQ[BigInt, Meter](2)
        assertCE("BigDecimal(1).withUnit[Meter].toValue[BigInt]")
        BigDecimal(2.5).withUnit[Meter].tToValue[Long].assertQ[Long, Meter](2)
    }

    test("toUnit") {
        import coulomb.policy.spire.strict.given

        Rational(1).withUnit[Yard].toUnit[Meter].assertQ[Rational, Meter](Rational(9144, 10000))
        BigDecimal(1).withUnit[Yard].toUnit[Meter].assertQ[BigDecimal, Meter](BigDecimal(0.9144))
        Algebraic(1).withUnit[Yard].toUnit[Meter].assertQ[Algebraic, Meter](Algebraic(0.9144))
        Real(1).withUnit[Yard].toUnit[Meter].assertQ[Real, Meter](Real(Rational(9144, 10000)))

        assertCE("BigInt(10).withUnit[Yard].toUnit[Meter]")
        BigInt(10).withUnit[Yard].tToUnit[Meter].assertQ[BigInt, Meter](9)
    }

    test("addition strict") {
        import coulomb.policy.spire.strict.given

        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Meter]).assertQ[Rational, Meter](Rational(2))
        (BigInt(1).withUnit[Meter] + BigInt(1).withUnit[Meter]).assertQ[BigInt, Meter](BigInt(2))
        (BigDecimal(1).withUnit[Meter] + BigDecimal(1).withUnit[Meter]).assertQ[BigDecimal, Meter](BigDecimal(2))
        (Algebraic(1).withUnit[Meter] + Algebraic(1).withUnit[Meter]).assertQ[Algebraic, Meter](Algebraic(2))
        (Real(1).withUnit[Meter] + Real(1).withUnit[Meter]).assertQ[Real, Meter](Real(2))

        assertCE("Real(1).withUnit[Meter] + Rational(1).withUnit[Meter]")
        assertCE("Rational(1).withUnit[Meter] + Rational(1).withUnit[Yard]")

        (Real(1).withUnit[Meter].toValue[Rational] + Rational(1).withUnit[Meter]).assertQ[Rational, Meter](Rational(2))
        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Yard].toUnit[Meter]).assertQ[Rational, Meter](Rational(19144, 10000))
    }

    test("addition standard") {
        import coulomb.policy.spire.standard.given

        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Meter]).assertQ[Rational, Meter](Rational(2))

        (Rational(1).withUnit[Meter] + Rational(1).withUnit[Yard]).assertQ[Rational, Meter](Rational(19144, 10000))
        (BigDecimal(1).withUnit[Meter] + BigDecimal(1).withUnit[Yard]).assertQ[BigDecimal, Meter](BigDecimal(1.9144))

        (1.withUnit[Meter] + BigInt(1).withUnit[Meter]).assertQ[BigInt, Meter](BigInt(2))
        (BigInt(1).withUnit[Meter] + 1.withUnit[Meter]).assertQ[BigInt, Meter](BigInt(2))
        (1f.withUnit[Meter] + BigDecimal(1).withUnit[Meter]).assertQ[BigDecimal, Meter](BigDecimal(2))
        (BigDecimal(1).withUnit[Meter] + 1f.withUnit[Meter]).assertQ[BigDecimal, Meter](BigDecimal(2))
        (1.withUnit[Meter] + Real(1).withUnit[Meter]).assertQ[Real, Meter](Real(2))
        (Real(1).withUnit[Meter] + 1.withUnit[Meter]).assertQ[Real, Meter](Real(2))

        (Rational(1).withUnit[Meter] + BigDecimal(1).withUnit[Yard]).assertQ[Rational, Meter](Rational(19144, 10000))
        (BigDecimal(1).withUnit[Meter] + Rational(1).withUnit[Yard]).assertQ[Rational, Meter](Rational(19144, 10000))

        assertCE("BigDecimal(1).withUnit[Meter] + BigDecimal.withUnit[Second]")
        
        assertCE("BigInt(1).withUnit[Meter] + BigInt(1).withUnit[Yard]")

        (BigInt(1).withUnit[Meter] + BigInt(1).withUnit[Yard].tToUnit[Meter]).assertQ[BigInt, Meter](BigInt(1))
    }

    test("tquot") {
        import coulomb.policy.spire.standard.given

        (BigInt(5).withUnit[Meter] `tquot` BigInt(2).withUnit[Second]).assertQ[BigInt, Meter / Second](BigInt(2))
    }

    test("pow") {
        import coulomb.policy.spire.standard.given

        Rational(2).withUnit[Meter].pow[0].assertQ[Rational, 1](Rational(1))
        Rational(2).withUnit[Meter].pow[2].assertQ[Rational, Meter ^ 2](Rational(4))
        Rational(2).withUnit[Meter].pow[-1].assertQ[Rational, 1 / Meter](Rational(1, 2))
        Rational(2).withUnit[Meter].pow[1 / 2].assertQD[Rational, Meter ^ (1 / 2)](1.4142135623730951)
        Rational(2).withUnit[Meter].pow[-1 / 2].assertQD[Rational, 1 / (Meter ^ (1 / 2))](0.7071067811865476)

        BigDecimal(2).withUnit[Meter].pow[1 / 2].assertQD[BigDecimal, Meter ^ (1 / 2)](1.4142135623730951)
        Algebraic(2).withUnit[Meter].pow[1 / 2].assertQD[Algebraic, Meter ^ (1 / 2)](1.4142135623730951)
        Real(2).withUnit[Meter].pow[1 / 2].assertQD[Real, Meter ^ (1 / 2)](1.4142135623730951)

        BigInt(2).withUnit[Meter].pow[0].assertQ[BigInt, 1](1)
        BigInt(2).withUnit[Meter].pow[2].assertQ[BigInt, Meter ^ 2](4)
        assertCE("BigInt(2).withUnit[Meter].pow[-1]")
        assertCE("BigInt(2).withUnit[Meter].pow[1 / 2]")
        assertCE("BigInt(2).withUnit[Meter].pow[-1 / 2]")
    }

    test("tpow") {
        import coulomb.policy.spire.standard.given

        BigInt(10).withUnit[Meter].tpow[0].assertQ[BigInt, 1](BigInt(1))
        BigInt(10).withUnit[Meter].tpow[2].assertQ[BigInt, Meter ^ 2](BigInt(100))
        BigInt(10).withUnit[Meter].tpow[-1].assertQ[BigInt, 1 / Meter](BigInt(0))
        BigInt(10).withUnit[Meter].tpow[1 / 2].assertQ[BigInt, Meter ^ (1 / 2)](BigInt(3))
        BigInt(10).withUnit[Meter].tpow[-1 / 2].assertQ[BigInt, 1 / (Meter ^ (1 / 2))](BigInt(0))
    }

    test("< strict") {
        import coulomb.policy.spire.strict.given

        assertEquals(Rational(1).withUnit[Meter] < Rational(2).withUnit[Meter], true)
        assertEquals(BigDecimal(2).withUnit[Meter] < BigDecimal(1).withUnit[Meter], false)
        assertEquals(Algebraic(1).withUnit[Meter] < Algebraic(2).withUnit[Meter], true)
        assertEquals(Real(2).withUnit[Meter] < Real(1).withUnit[Meter], false)
        assertEquals(BigInt(1).withUnit[Meter] < BigInt(2).withUnit[Meter], true)
    }

    test("< standard") {
        import coulomb.policy.spire.standard.given

        assertEquals(Rational(1).withUnit[Yard] < Rational(1).withUnit[Meter], true)
        assertEquals(BigDecimal(1).withUnit[Meter] < BigDecimal(1).withUnit[Yard], false)
        assertEquals(Algebraic(1).withUnit[Yard] < Algebraic(1).withUnit[Meter], true)
        assertEquals(Real(1).withUnit[Meter] < Real(1).withUnit[Yard], false)

        assertEquals(Rational(1).withUnit[Yard] < BigInt(1).withUnit[Meter], true)
        assertEquals(BigDecimal(1).withUnit[Meter] < Rational(1).withUnit[Yard], false)
        assertEquals(Algebraic(1).withUnit[Yard] < Real(1).withUnit[Meter], true)
        assertEquals(Rational(1).withUnit[Meter] < BigDecimal(1).withUnit[Yard], false)
    }
