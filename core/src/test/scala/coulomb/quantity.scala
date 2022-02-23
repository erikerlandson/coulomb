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

import coulomb.*
import coulomb.rational.Rational

import coulomb.testing.CoulombSuite
import coulomb.testing.units.{*,given}

class QuantitySuite extends CoulombSuite:
    import coulomb.conversion.standard.given

    test("lift via Quantity") {
        Quantity[Meter](3.14).assertQ[Double, Meter](3.14)
        Quantity[Second](7.7f).assertQ[Float, Second](7.7f)
        Quantity[Kilogram](42L).assertQ[Long, Kilogram](42)
        Quantity[Liter](99).assertQ[Int, Liter](99)
        Quantity[Minute]("foo").assertQ[String, Minute]("foo")
    }

    test("lift via withUnit") {
        1d.withUnit[Meter].assertQ[Double, Meter](1)
        1f.withUnit[Second].assertQ[Float, Second](1)
        1L.withUnit[Kilogram].assertQ[Long, Kilogram](1)
        1.withUnit[Liter].assertQ[Int, Liter](1)
        "foo".withUnit[Minute].assertQ[String, Minute]("foo")
    }

    test("value") {
       7d.withUnit[Meter].value.assertVT[Double](7)
       73f.withUnit[Second].value.assertVT[Float](73)
       37L.withUnit[Kilogram].value.assertVT[Long](37)
       13.withUnit[Liter].value.assertVT[Int](13)
       "foo".withUnit[Minute].value.assertVT[String]("foo")
    }
