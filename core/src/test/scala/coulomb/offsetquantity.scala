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

class OffsetQuantitySuite extends CoulombSuite:
    import coulomb.*
    import coulomb.testing.units.{*, given}
    import algebra.instances.all.given
    import coulomb.ops.algebra.all.given

    test("lift via OffsetQuantity") {
        OffsetQuantity[Meter](3.14).assertOQ[Double, Meter](3.14)
        OffsetQuantity[Second](7.7f).assertOQ[Float, Second](7.7f)
        OffsetQuantity[Kilogram](42L).assertOQ[Long, Kilogram](42)
        OffsetQuantity[Liter](99).assertOQ[Int, Liter](99)
        OffsetQuantity[Minute]("foo").assertOQ[String, Minute]("foo")
    }

    test("lift via withOffsetUnit") {
        1d.withOffsetUnit[Meter].assertOQ[Double, Meter](1)
        1f.withOffsetUnit[Second].assertOQ[Float, Second](1)
        1L.withOffsetUnit[Kilogram].assertOQ[Long, Kilogram](1)
        1.withOffsetUnit[Liter].assertOQ[Int, Liter](1)
        "foo".withOffsetUnit[Minute].assertOQ[String, Minute]("foo")
    }

    test("value") {
       7d.withOffsetUnit[Meter].value.assertVT[Double](7)
       73f.withOffsetUnit[Second].value.assertVT[Float](73)
       37L.withOffsetUnit[Kilogram].value.assertVT[Long](37)
       13.withOffsetUnit[Liter].value.assertVT[Int](13)
       "foo".withOffsetUnit[Minute].value.assertVT[String]("foo")
    }
