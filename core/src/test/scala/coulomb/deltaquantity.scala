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

    test("DeltaQuantity subtraction strict") {
        import coulomb.ops.standard.given
        import coulomb.conversion.standard.explicit.given

        // 1V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50d.withDeltaUnit[Celsius, Kelvin]).assertQ[Double, Celsius](50)
        (10f.withDeltaUnit[Minute, Second] - 5f.withDeltaUnit[Minute, Second]).assertQ[Float, Minute](5)
        (100L.withDeltaUnit[Kelvin, Kelvin] - 50L.withDeltaUnit[Kelvin, Kelvin]).assertQ[Long, Kelvin](50)
        (10.withDeltaUnit[Second, Second] - 5.withDeltaUnit[Second, Second]).assertQ[Int, Second](5)
    } 

    test("DeltaQuantity subtraction standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        // 2V1U
        (100d.withDeltaUnit[Celsius, Kelvin] - 50f.withDeltaUnit[Celsius, Kelvin]).assertQ[Double, Celsius](50)

        // 1V2U
        (100d.withDeltaUnit[Celsius, Kelvin] - 122d.withDeltaUnit[Fahrenheit, Kelvin]).assertQD[Double, Celsius](50)
        // 2V2U
        (100f.withDeltaUnit[Celsius, Kelvin] - 122d.withDeltaUnit[Fahrenheit, Kelvin]).assertQD[Double, Celsius](50)
    } 
