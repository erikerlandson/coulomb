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
