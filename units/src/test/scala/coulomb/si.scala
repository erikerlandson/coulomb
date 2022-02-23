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

import coulomb.testing.CoulombSuite

class SIUnitsSuite extends CoulombSuite:
    test("defines si units") {
        import coulomb.units.si.{*,given}
        1.withUnit[Meter].assertQ[Int, Meter](1)
        1L.withUnit[Kilogram].assertQ[Long, Kilogram](1)
        1f.withUnit[Second].assertQ[Float, Second](1)
        1d.withUnit[Ampere].assertQ[Double, Ampere](1)
        "goo".withUnit[Mole].assertQ[String, Mole]("goo")
        true.withUnit[Candela].assertQ[Boolean, Candela](true)
        List("moo").withUnit[Kelvin].assertQ[List[String], Kelvin](List("moo"))
    }
