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

class QuantitySuite extends CoulombSuite:
    import coulomb.*
    import coulomb.testing.units.{*, given}
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

    test("toValue standard") {
        1.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)
 
        1L.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1L.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1L.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1L.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)

        // fp to integral types is an error without importing integral rules
        assertCE("1f.withUnit[Meter].toValue[Int]")
        assertCE("1f.withUnit[Meter].toValue[Long]")
        1f.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1f.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)

        assertCE("1d.withUnit[Meter].toValue[Int]")
        assertCE("1d.withUnit[Meter].toValue[Long]")
        1d.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1d.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)
    }

    test("toValue standard integral") {
        import coulomb.conversion.standard.integral.given

        1.5f.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        0.999f.withUnit[Meter].toValue[Long].assertQ[Long, Meter](0)

        1.5d.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        0.999d.withUnit[Meter].toValue[Long].assertQ[Long, Meter](0)
    }

    test("toUnit standard") {
        1.5f.withUnit[Minute].toUnit[Second].assertQ[Float, Second](90)
        1d.withUnit[Meter].toUnit[Yard].assertQD[Double, Yard](1.0936132983377078)
        // conversions on integral types require importing integral conversion rules
        assertCE("1.withUnit[Minute].toUnit[Second]")
        assertCE("1L.withUnit[Yard].toUnit[Meter]")
    }

    test("toUnit standard integral") {
        import coulomb.conversion.standard.integral.given

        1.withUnit[Minute].toUnit[Second].assertQ[Int, Second](60)
        1L.withUnit[Yard].toUnit[Meter].assertQ[Long, Meter](0)
    }
