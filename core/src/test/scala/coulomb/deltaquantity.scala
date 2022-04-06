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

class DeltaQuantitySuite extends CoulombSuite:
    import coulomb.*
    import coulomb.testing.units.{*, given}
    import algebra.instances.all.given
    import coulomb.ops.algebra.all.given

    test("lift via DeltaQuantity") {
        DeltaQuantity[Meter, Meter](3.14).assertDQ[Double, Meter](3.14)
        DeltaQuantity[Second, Second](7.7f).assertDQ[Float, Second](7.7f)
        DeltaQuantity[Kilogram, Kilogram](42L).assertDQ[Long, Kilogram](42)
        DeltaQuantity[Liter, Liter](99).assertDQ[Int, Liter](99)
        DeltaQuantity[Minute, Minute]("foo").assertDQ[String, Minute]("foo")
    }

    test("lift via withDeltaUnit") {
        1d.withDeltaUnit[Meter, Meter].assertDQ[Double, Meter](1)
        1f.withDeltaUnit[Second, Meter].assertDQ[Float, Second](1)
        1L.withDeltaUnit[Kilogram, Kilogram].assertDQ[Long, Kilogram](1)
        1.withDeltaUnit[Liter, Liter].assertDQ[Int, Liter](1)
        "foo".withDeltaUnit[Minute, Minute].assertDQ[String, Minute]("foo")
    }

    test("value") {
       7d.withDeltaUnit[Meter, Meter].value.assertVT[Double](7)
       73f.withDeltaUnit[Second, Meter].value.assertVT[Float](73)
       37L.withDeltaUnit[Kilogram, Kilogram].value.assertVT[Long](37)
       13.withDeltaUnit[Liter, Liter].value.assertVT[Int](13)
       "foo".withDeltaUnit[Minute, Minute].value.assertVT[String]("foo")
    }

    test("show") {
        assertEquals(1.withDeltaUnit[Second, Second].show, "1 s")
    }

    test("showFull") {
        assertEquals(1.withDeltaUnit[Second, Second].showFull, "1 second")
    }

    test("toValue") {
        import coulomb.policy.strict.given

        100.withDeltaUnit[Celsius, Kelvin].toValue[Int].assertDQ[Int, Celsius](100)
        100.withDeltaUnit[Celsius, Kelvin].toValue[Long].assertDQ[Long, Celsius](100)
        100.withDeltaUnit[Celsius, Kelvin].toValue[Float].assertDQ[Float, Celsius](100)
        100.withDeltaUnit[Celsius, Kelvin].toValue[Double].assertDQ[Double, Celsius](100)

        100L.withDeltaUnit[Celsius, Kelvin].toValue[Int].assertDQ[Int, Celsius](100)
        100L.withDeltaUnit[Celsius, Kelvin].toValue[Long].assertDQ[Long, Celsius](100)
        100L.withDeltaUnit[Celsius, Kelvin].toValue[Float].assertDQ[Float, Celsius](100)
        100L.withDeltaUnit[Celsius, Kelvin].toValue[Double].assertDQ[Double, Celsius](100)

        assertCE("100f.withDeltaUnit[Celsius, Kelvin].toValue[Int]")
        assertCE("100f.withDeltaUnit[Celsius, Kelvin].toValue[Long]")
        100f.withDeltaUnit[Celsius, Kelvin].toValue[Float].assertDQ[Float, Celsius](100)
        100f.withDeltaUnit[Celsius, Kelvin].toValue[Double].assertDQ[Double, Celsius](100)

        assertCE("100d.withDeltaUnit[Celsius, Kelvin].toValue[Int]")
        assertCE("100d.withDeltaUnit[Celsius, Kelvin].toValue[Long]")
        100d.withDeltaUnit[Celsius, Kelvin].toValue[Float].assertDQ[Float, Celsius](100)
        100d.withDeltaUnit[Celsius, Kelvin].toValue[Double].assertDQ[Double, Celsius](100)

        1.999f.withDeltaUnit[Minute, Second].tToValue[Int].assertDQ[Int, Minute](1)
        0.999f.withDeltaUnit[Minute, Second].tToValue[Long].assertDQ[Long, Minute](0)

        1.999d.withDeltaUnit[Minute, Second].tToValue[Int].assertDQ[Int, Minute](1)
        0.999d.withDeltaUnit[Minute, Second].tToValue[Long].assertDQ[Long, Minute](0)
    }

    test("toUnit") {
        import coulomb.policy.strict.given

        37d.withDeltaUnit[Celsius, Kelvin].toUnit[Fahrenheit].assertDQD[Double, Fahrenheit](98.6)
        37f.withDeltaUnit[Celsius, Kelvin].toUnit[Fahrenheit].assertDQD[Float, Fahrenheit](98.6)

        assertCE("37L.withDeltaUnit[Celsius, Kelvin].toUnit[Fahrenheit]")
        assertCE("37.withDeltaUnit[Celsius, Kelvin].toUnit[Fahrenheit]")

        37L.withDeltaUnit[Celsius, Kelvin].tToUnit[Fahrenheit].assertDQ[Long, Fahrenheit](98)
        37.withDeltaUnit[Celsius, Kelvin].tToUnit[Fahrenheit].assertDQ[Int, Fahrenheit](98)
    }

    test("subtraction strict") {
        import coulomb.policy.strict.given

        // 1V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50d.withDeltaUnit[Celsius, Kelvin]).assertQ[Double, Celsius](50)
        (10f.withDeltaUnit[Minute, Second] - 5f.withDeltaUnit[Minute, Second]).assertQ[Float, Minute](5)
        (100L.withDeltaUnit[Kelvin, Kelvin] - 50L.withDeltaUnit[Kelvin, Kelvin]).assertQ[Long, Kelvin](50)
        (10.withDeltaUnit[Second, Second] - 5.withDeltaUnit[Second, Second]).assertQ[Int, Second](5)
    } 

    test("subtraction standard") {
        import coulomb.policy.standard.given

        // 2V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50f.withDeltaUnit[Celsius, Kelvin]).assertQ[Double, Celsius](50)
        // 1V2U
        (100d.withDeltaUnit[Celsius, Kelvin] - 122d.withDeltaUnit[Fahrenheit, Kelvin]).assertQD[Double, Celsius](50)
        // 2V2U
        (100f.withDeltaUnit[Celsius, Kelvin] - 122d.withDeltaUnit[Fahrenheit, Kelvin]).assertQD[Double, Celsius](50)
    } 

    test("quantity subtraction strict") {
        import coulomb.policy.strict.given

        // 1V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50d.withUnit[Celsius]).assertDQ[Double, Celsius](50)
        (10f.withDeltaUnit[Minute, Second] - 5f.withUnit[Minute]).assertDQ[Float, Minute](5)
        (100L.withDeltaUnit[Kelvin, Kelvin] - 50L.withUnit[Kelvin]).assertDQ[Long, Kelvin](50)
        (10.withDeltaUnit[Second, Second] - 5.withUnit[Second]).assertDQ[Int, Second](5)
    }

    test("quantity subtraction standard") {
        import coulomb.policy.standard.given

        // 2V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50f.withUnit[Celsius]).assertDQ[Double, Celsius](50)
        // 1V2U
        (100d.withDeltaUnit[Celsius, Kelvin] - 90d.withUnit[Fahrenheit]).assertDQD[Double, Celsius](50)
        // 2V2U
        (100f.withDeltaUnit[Celsius, Kelvin] - 90d.withUnit[Fahrenheit]).assertDQD[Double, Celsius](50)
    } 

    test("quantity addition strict") {
        import coulomb.policy.strict.given

        // 1V1U
        (100d.withDeltaUnit[Celsius, Kelvin] + 50d.withUnit[Celsius]).assertDQ[Double, Celsius](150)
        (10f.withDeltaUnit[Minute, Second] + 5f.withUnit[Minute]).assertDQ[Float, Minute](15)
        (100L.withDeltaUnit[Kelvin, Kelvin] + 50L.withUnit[Kelvin]).assertDQ[Long, Kelvin](150)
        (10.withDeltaUnit[Second, Second] + 5.withUnit[Second]).assertDQ[Int, Second](15)
    }

    test("quantity addition standard") {
        import coulomb.policy.standard.given

        // 2V1U
        (100d.withDeltaUnit[Celsius, Kelvin] + 50f.withUnit[Celsius]).assertDQ[Double, Celsius](150)
        // 1V2U
        (100d.withDeltaUnit[Celsius, Kelvin] + 90d.withUnit[Fahrenheit]).assertDQD[Double, Celsius](150)
        // 2V2U
        (100f.withDeltaUnit[Celsius, Kelvin] + 90d.withUnit[Fahrenheit]).assertDQD[Double, Celsius](150)
    } 

    test("less-than strict") {
        import coulomb.policy.strict.given

        assertEquals(7d.withDeltaUnit[Minute, Second] < 8d.withDeltaUnit[Minute, Second], true)
        assertEquals(7f.withDeltaUnit[Minute, Second] < 7f.withDeltaUnit[Minute, Second], false)
        assertEquals(7L.withDeltaUnit[Minute, Second] < 8L.withDeltaUnit[Minute, Second], true)
        assertEquals(7.withDeltaUnit[Minute, Second] < 7.withDeltaUnit[Minute, Second], false)
    }

    test("less-than standard") {
        import coulomb.policy.standard.given

        // 1V2U
        assertEquals(36d.withDeltaUnit[Celsius, Kelvin] < 98.6d.withDeltaUnit[Fahrenheit, Kelvin], true)
        // 2V1U
        assertEquals(36f.withDeltaUnit[Celsius, Kelvin] < 36d.withDeltaUnit[Celsius, Kelvin], false)
        // 2V2U
        assertEquals(38d.withDeltaUnit[Celsius, Kelvin] < 98.6f.withDeltaUnit[Fahrenheit, Kelvin], false)
    }
