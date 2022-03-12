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

class SIUnitsSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.conversion.standard.all.given

    test("defines si units") {
        1.withUnit[Meter].assertQ[Int, Meter](1)
        1L.withUnit[Kilogram].assertQ[Long, Kilogram](1)
        1f.withUnit[Second].assertQ[Float, Second](1)
        1d.withUnit[Ampere].assertQ[Double, Ampere](1)
        "goo".withUnit[Mole].assertQ[String, Mole]("goo")
        true.withUnit[Candela].assertQ[Boolean, Candela](true)
        List("moo").withUnit[Kelvin].assertQ[List[String], Kelvin](List("moo"))
    }

    test("defines si prefixes") {
        assertEquals(1d.withUnit[Kilo].toUnit[1].value, 1e3)
        assertEquals(1d.withUnit[Mega].toUnit[1].value, 1e6)
        assertEquals(1d.withUnit[Giga].toUnit[1].value, 1e9)
        assertEquals(1d.withUnit[Tera].toUnit[1].value, 1e12)
        assertEquals(1d.withUnit[Peta].toUnit[1].value, 1e15)
        assertEquals(1d.withUnit[Exa].toUnit[1].value, 1e18)
        assertEquals(1d.withUnit[Zetta].toUnit[1].value, 1e21)
        assertEquals(1d.withUnit[Yotta].toUnit[1].value, 1e24)

        assertEquals(1d.withUnit[Milli].toUnit[1].value, 1e-3)
        assertEquals(1d.withUnit[Micro].toUnit[1].value, 1e-6)
        assertEquals(1d.withUnit[Nano].toUnit[1].value, 1e-9)
        assertEquals(1d.withUnit[Pico].toUnit[1].value, 1e-12)
        assertEquals(1d.withUnit[Femto].toUnit[1].value, 1e-15)
        // picking up a bit of fp artifacts at least significant bits on very small vals
        assertEqualsDouble(1d.withUnit[Atto].toUnit[1].value, 1e-18, 1e-18 * 1e-15)
        assertEqualsDouble(1d.withUnit[Zepto].toUnit[1].value, 1e-21, 1e-21 * 1e-15)
        assertEqualsDouble(1d.withUnit[Yocto].toUnit[1].value, 1e-24, 1e-24 * 1e-15)
    }
