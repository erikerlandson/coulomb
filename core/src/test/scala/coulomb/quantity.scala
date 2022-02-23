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

    test("withUnit") {
        1d.withUnit[Meter].checkQ[Double, Meter](1.0, eps=0)
        1f.withUnit[Second].checkQ[Float, Second](1.0, eps=0)
        1L.withUnit[Kilogram].checkQ[Long, Kilogram](1.0, eps=0)
        1.withUnit[Liter].checkQ[Int, Liter](1.0, eps=0)
    }

    test("value") {
       7d.withUnit[Meter].value.checkVT[Double](7.0, eps=0)
       73f.withUnit[Second].value.checkVT[Float](73.0, eps=0)
       37L.withUnit[Kilogram].value.checkVT[Long](37.0, eps=0)
       13.withUnit[Liter].value.checkVT[Int](13.0, eps=0)
       assertEquals("foo".withUnit[Minute].value, "foo")
    }
