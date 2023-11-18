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
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}

    import spire.std.any.{*, given}

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
        100.withDeltaUnit[Celsius, Kelvin]
            .toValue[Int]
            .assertDQ[Int, Celsius](100)
        100.withDeltaUnit[Celsius, Kelvin]
            .toValue[Long]
            .assertDQ[Long, Celsius](100)
        100.withDeltaUnit[Celsius, Kelvin]
            .toValue[Float]
            .assertDQ[Float, Celsius](100)
        100.withDeltaUnit[Celsius, Kelvin]
            .toValue[Double]
            .assertDQ[Double, Celsius](100)

        100L.withDeltaUnit[Celsius, Kelvin]
            .toValue[Int]
            .assertDQ[Int, Celsius](100)
        100L.withDeltaUnit[Celsius, Kelvin]
            .toValue[Long]
            .assertDQ[Long, Celsius](100)
        100L.withDeltaUnit[Celsius, Kelvin]
            .toValue[Float]
            .assertDQ[Float, Celsius](100)
        100L.withDeltaUnit[Celsius, Kelvin]
            .toValue[Double]
            .assertDQ[Double, Celsius](100)

        100f.withDeltaUnit[Celsius, Kelvin]
            .toValue[Float]
            .assertDQ[Float, Celsius](100)
        100f.withDeltaUnit[Celsius, Kelvin]
            .toValue[Double]
            .assertDQ[Double, Celsius](100)

        100d.withDeltaUnit[Celsius, Kelvin]
            .toValue[Float]
            .assertDQ[Float, Celsius](100)
        100d.withDeltaUnit[Celsius, Kelvin]
            .toValue[Double]
            .assertDQ[Double, Celsius](100)
    }

    test("toUnit") {
        37d.withDeltaUnit[Celsius, Kelvin]
            .toUnit[Fahrenheit]
            .assertDQD[Double, Fahrenheit](98.6)
        37f.withDeltaUnit[Celsius, Kelvin]
            .toUnit[Fahrenheit]
            .assertDQD[Float, Fahrenheit](98.6)

        assertCE("37L.withDeltaUnit[Celsius, Kelvin].toUnit[Fahrenheit]")
        assertCE("37.withDeltaUnit[Celsius, Kelvin].toUnit[Fahrenheit]")
    }

    test("subtraction") {
        // 1V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50d
            .withDeltaUnit[Celsius, Kelvin])
            .assertQ[Double, Celsius](50)
        (10f.withDeltaUnit[Minute, Second] - 5f.withDeltaUnit[Minute, Second])
            .assertQ[Float, Minute](5)
        (100L.withDeltaUnit[Kelvin, Kelvin] - 50L.withDeltaUnit[Kelvin, Kelvin])
            .assertQ[Long, Kelvin](50)
        (10.withDeltaUnit[Second, Second] - 5.withDeltaUnit[Second, Second])
            .assertQ[Int, Second](5)

        (100d.withDeltaUnit[Celsius, Kelvin] - 50d
            .withDeltaUnit[Celsius, Kelvin])
            .assertQ[Double, Celsius](50)
        (100f.withDeltaUnit[Celsius, Kelvin] - 122f
            .withDeltaUnit[Fahrenheit, Kelvin])
            .assertQD[Float, Celsius](50)
    }

    test("quantity subtraction") {
        (100d.withDeltaUnit[Celsius, Kelvin] - 50d.withUnit[Celsius])
            .assertDQ[Double, Celsius](50)
        (10f.withDeltaUnit[Minute, Second] - 5f.withUnit[Minute])
            .assertDQ[Float, Minute](5)
        (100L.withDeltaUnit[Kelvin, Kelvin] - 50L.withUnit[Kelvin])
            .assertDQ[Long, Kelvin](50)
        (10.withDeltaUnit[Second, Second] - 5.withUnit[Second])
            .assertDQ[Int, Second](5)

        (100d.withDeltaUnit[Celsius, Kelvin] - 50d.withUnit[Celsius])
            .assertDQ[Double, Celsius](50)
        (100f.withDeltaUnit[Celsius, Kelvin] - 90f.withUnit[Fahrenheit])
            .assertDQD[Float, Celsius](50)
    }

    test("quantity addition") {
        (100d.withDeltaUnit[Celsius, Kelvin] + 50d.withUnit[Celsius])
            .assertDQ[Double, Celsius](150)
        (10f.withDeltaUnit[Minute, Second] + 5f.withUnit[Minute])
            .assertDQ[Float, Minute](15)
        (100L.withDeltaUnit[Kelvin, Kelvin] + 50L.withUnit[Kelvin])
            .assertDQ[Long, Kelvin](150)
        (10.withDeltaUnit[Second, Second] + 5.withUnit[Second])
            .assertDQ[Int, Second](15)

        (100d.withDeltaUnit[Celsius, Kelvin] + 50d.withUnit[Celsius])
            .assertDQ[Double, Celsius](150)
        (100f.withDeltaUnit[Celsius, Kelvin] + 90f.withUnit[Fahrenheit])
            .assertDQD[Float, Celsius](150)
    }

    test("less-than") {
        assertEquals(
            7d.withDeltaUnit[Minute, Second] < 8d.withDeltaUnit[Minute, Second],
            true
        )
        assertEquals(
            7f.withDeltaUnit[Minute, Second] < 7f.withDeltaUnit[Minute, Second],
            false
        )
        assertEquals(
            7L.withDeltaUnit[Minute, Second] < 8L.withDeltaUnit[Minute, Second],
            true
        )
        assertEquals(
            7.withDeltaUnit[Minute, Second] < 7.withDeltaUnit[Minute, Second],
            false
        )

        assertEquals(
            36d.withDeltaUnit[Celsius, Kelvin] < 98.6d
                .withDeltaUnit[Fahrenheit, Kelvin],
            true
        )
        assertEquals(
            36f.withDeltaUnit[Celsius, Kelvin] < 36f
                .withDeltaUnit[Celsius, Kelvin],
            false
        )
    }

    test("cats Eq, Ord, Hash") {
        import cats.kernel.{Eq, Hash, Order}
        import coulomb.integrations.cats.all.given

        val q1 = 1.withDeltaUnit[Meter, Meter]
        val q2 = 1.withDeltaUnit[Meter, Meter]
        val q3 = 2.withDeltaUnit[Meter, Meter]

        val eqv = summon[Eq[DeltaQuantity[Int, Meter, Meter]]]
        assertEquals(eqv.eqv(q1, q2), true)
        assertEquals(eqv.eqv(q1, q3), false)

        val ord = summon[Order[DeltaQuantity[Int, Meter, Meter]]]
        assertEquals(ord.compare(q1, q2), 0)
        assertEquals(ord.compare(q1, q3), -1)
        assertEquals(ord.compare(q3, q1), 1)

        val hash = summon[Hash[DeltaQuantity[Int, Meter, Meter]]]
        assertEquals(hash.hash(q1), hash.hash(q2))
        assertNotEquals(hash.hash(q1), hash.hash(q3))
    }
