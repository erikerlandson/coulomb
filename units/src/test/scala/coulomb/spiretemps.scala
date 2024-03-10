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

class SpireTemperatureSuite extends CoulombSuite:
    import spire.math.*
    import coulomb.*
    import coulomb.syntax.*

    import algebra.instances.all.given

    import coulomb.units.temperature.{*, given}

    test("toValue") {
        import coulomb.policy.spire.strict.given

        Real(1)
            .withTemperature[Celsius]
            .toValue[Algebraic]
            .assertDQ[Algebraic, Celsius](Algebraic(1))
        Real(1)
            .withTemperature[Celsius]
            .toValue[Rational]
            .assertDQ[Rational, Celsius](Rational(1))
        Real(1)
            .withTemperature[Celsius]
            .toValue[BigDecimal]
            .assertDQ[BigDecimal, Celsius](BigDecimal(1))
        Real(1)
            .withTemperature[Celsius]
            .toValue[Double]
            .assertDQ[Double, Celsius](1)
        Real(1)
            .withTemperature[Celsius]
            .toValue[Float]
            .assertDQ[Float, Celsius](1)
        Real(1)
            .withTemperature[Celsius]
            .tToValue[BigInt]
            .assertDQ[BigInt, Celsius](BigInt(1))
        Real(1)
            .withTemperature[Celsius]
            .tToValue[Long]
            .assertDQ[Long, Celsius](1)
        Real(1).withTemperature[Celsius].tToValue[Int].assertDQ[Int, Celsius](1)
    }

    test("toUnit") {
        import coulomb.policy.spire.standard.given

        Rational(37)
            .withTemperature[Celsius]
            .toUnit[Fahrenheit]
            .assertDQD[Rational, Fahrenheit](98.6)
        BigDecimal(37)
            .withTemperature[Celsius]
            .toUnit[Fahrenheit]
            .assertDQD[BigDecimal, Fahrenheit](98.6)
        Algebraic(37)
            .withTemperature[Celsius]
            .toUnit[Fahrenheit]
            .assertDQD[Algebraic, Fahrenheit](98.6)
        Real(37)
            .withTemperature[Celsius]
            .toUnit[Fahrenheit]
            .assertDQD[Real, Fahrenheit](98.6)
        BigInt(37)
            .withTemperature[Celsius]
            .tToUnit[Fahrenheit]
            .assertDQ[BigInt, Fahrenheit](98)
    }

    test("subtraction") {
        import coulomb.policy.spire.standard.given

        (BigInt(100).withTemperature[Celsius] - Rational(122)
            .withTemperature[Fahrenheit])
            .assertQ[Rational, Celsius](50)
    }

    test("quantity subtraction") {
        import coulomb.policy.spire.standard.given

        (100.withTemperature[Celsius] - BigDecimal(90).withUnit[Fahrenheit])
            .assertDQD[BigDecimal, Celsius](50)
    }

    test("less-than standard") {
        import coulomb.policy.spire.standard.given

        assertEquals(
            100d.withTemperature[Celsius] < Rational(100)
                .withTemperature[Fahrenheit],
            false
        )
        assertEquals(
            BigDecimal(0).withTemperature[Fahrenheit] < 0L
                .withTemperature[Celsius],
            true
        )
    }
