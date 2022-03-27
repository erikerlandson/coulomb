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

class TemperatureUnitsSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.units.temperature.{*, given}
    import algebra.instances.all.given

    // There are various cases and combinations that are not enumerated here,
    // because they are enumerated in TimeUnitsSuite, and both are implemented
    // with DeltaQuantity under the hood

    test("lift via Temperature") {
        Temperature[Kelvin](1d).assertDQ[Double, Kelvin](1)
        Temperature[Kelvin](1).assertDQ[Int, Kelvin](1)
    }

    test("lift via withTemperature") {
        1d.withTemperature[Celsius].assertDQ[Double, Celsius](1)
        1f.withTemperature[Celsius].assertDQ[Float, Celsius](1)
    }

    test("value") {
        1f.withTemperature[Fahrenheit].value.assertVT[Float](1)
        1L.withTemperature[Fahrenheit].value.assertVT[Long](1)
    }

    test("show") {
        // currently the compiler is confused by 'type Temperature[V, U]'
        assertEquals(1.withTemperature[Celsius]
            .asInstanceOf[DeltaQuantity[Int, Celsius, Kelvin]].show, "1 °C")
    }

    test("showFull") {
        assertEquals(1.withTemperature[Celsius]
            .asInstanceOf[DeltaQuantity[Int, Celsius, Kelvin]].showFull, "1 celsius")
    }

    test("toValue") {
        import coulomb.conversion.standard.value.given
        1.withTemperature[Celsius].toValue[Float].assertDQ[Float, Celsius](1)
        1d.withTemperature[Celsius].tToValue[Int].assertDQ[Int, Celsius](1)
    }

    test("toUnit") {
        import coulomb.conversion.standard.all.given
        37d.withTemperature[Celsius].toUnit[Fahrenheit].assertDQD[Double, Fahrenheit](98.6)
        37.withTemperature[Celsius].tToUnit[Fahrenheit].assertDQD[Int, Fahrenheit](98)
    }

    test("subtraction standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        (100.withTemperature[Celsius] - 122d.withTemperature[Fahrenheit]).assertQD[Double, Celsius](50)
    }

    test("quantity subtraction standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        (100.withTemperature[Celsius] - 90d.withUnit[Fahrenheit]).assertDQD[Double, Celsius](50)
    }

    test("quantity addition standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        (100.withTemperature[Celsius] + 90d.withUnit[Fahrenheit]).assertDQD[Double, Celsius](150)
    }

    test("less-than standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        assertEquals(100d.withTemperature[Celsius] < 100f.withTemperature[Fahrenheit], false)
        assertEquals(0f.withTemperature[Fahrenheit] < 0L.withTemperature[Celsius], true)
    }
