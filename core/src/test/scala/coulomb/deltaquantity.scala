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
        DeltaQuantity[Meter](3.14).assertDQ[Double, Meter](3.14)
        DeltaQuantity[Second](7.7f).assertDQ[Float, Second](7.7f)
        DeltaQuantity[Kilogram](42L).assertDQ[Long, Kilogram](42)
        DeltaQuantity[Liter](99).assertDQ[Int, Liter](99)
        DeltaQuantity[Minute]("foo").assertDQ[String, Minute]("foo")
    }

    test("lift via withDeltaUnit") {
        1d.withDeltaUnit[Meter].assertDQ[Double, Meter](1)
        1f.withDeltaUnit[Second].assertDQ[Float, Second](1)
        1L.withDeltaUnit[Kilogram].assertDQ[Long, Kilogram](1)
        1.withDeltaUnit[Liter].assertDQ[Int, Liter](1)
        "foo".withDeltaUnit[Minute].assertDQ[String, Minute]("foo")
    }

    test("value") {
       7d.withDeltaUnit[Meter].value.assertVT[Double](7)
       73f.withDeltaUnit[Second].value.assertVT[Float](73)
       37L.withDeltaUnit[Kilogram].value.assertVT[Long](37)
       13.withDeltaUnit[Liter].value.assertVT[Int](13)
       "foo".withDeltaUnit[Minute].value.assertVT[String]("foo")
    }
