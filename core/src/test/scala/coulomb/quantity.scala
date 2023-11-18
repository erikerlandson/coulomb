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
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}

    // spire.std.any is similar to algebra.instances.all, but
    // spire also includes the TruncatedDivision typeclass defs
    import spire.std.any.{*, given}

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

    test("show") {
        assertEquals(1.withUnit[Second].show, "1 s")
    }

    test("showFull") {
        assertEquals(1.withUnit[Second].showFull, "1 second")
    }

    test("toValue") {
        1.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)

        1L.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1L.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1L.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1L.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)

        1f.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1f.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1f.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1f.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)

        1d.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        1d.withUnit[Meter].toValue[Long].assertQ[Long, Meter](1)
        1d.withUnit[Meter].toValue[Float].assertQ[Float, Meter](1)
        1d.withUnit[Meter].toValue[Double].assertQ[Double, Meter](1)

        1.5f.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        0.999f.withUnit[Meter].toValue[Long].assertQ[Long, Meter](0)

        1.5d.withUnit[Meter].toValue[Int].assertQ[Int, Meter](1)
        0.999d.withUnit[Meter].toValue[Long].assertQ[Long, Meter](0)
    }

    test("toUnit") {
        1.5f.withUnit[Minute].toUnit[Second].assertQ[Float, Second](90)
        1d.withUnit[Meter]
            .toUnit[Yard]
            .assertQD[Double, Yard](1.0936132983377078)

        assertCE("1.withUnit[Minute].toUnit[Second]")
        assertCE("1L.withUnit[Yard].toUnit[Meter]")
    }

    test("implicit conversions") {
        // implicit conversions only happen if you import them into scope
        // https://docs.scala-lang.org/scala3/reference/contextual/conversions.html
        def f(q: Quantity[Double, Meter]): Double = q.value
        assertCE("f(1d.withUnit[Yard])")

        // toUnit and toValue will operate, because they are explicit conversion request
        f(1d.withUnit[Yard].toUnit[Meter]).assertVTD[Double](0.9144)
        f(1.withUnit[Meter].toValue[Double]).assertVTD[Double](1.0)

        object t {
            // puts implicit conversions in scope
            import coulomb.conversion.implicits.given
            import scala.language.implicitConversions
            f(1d.withUnit[Yard]).assertVTD[Double](0.9144)
        }
    }

    test("addition") {
        // adding w/ same value and unit requires no implicit conversion
        (1d.withUnit[Second] + 1d.withUnit[Second]).assertQ[Double, Second](2)
        (1f.withUnit[Meter] + 1f.withUnit[Meter]).assertQ[Float, Meter](2)
        (1L.withUnit[Kilogram] + 1L.withUnit[Kilogram])
            .assertQ[Long, Kilogram](2)
        (1.withUnit[Meter / Second] + 1.withUnit[Meter / Second])
            .assertQ[Int, Meter / Second](2)

        // same value type, different units
        (1d.withUnit[Kilo * Second] + 1d.withUnit[Second])
            .assertQD[Double, Kilo * Second](1.001)
        (1f.withUnit[Meter] + 1f.withUnit[Yard]).assertQD[Float, Meter](1.9144)

        // non convertible units should fail
        assertCE("1d.withUnit[Meter] + 1d.withUnit[Second]")
    }

    test("subtraction standard") {
        // same value and unit requires no implicit conversion
        (3d.withUnit[Second] - 1d.withUnit[Second]).assertQ[Double, Second](2)
        (3f.withUnit[Meter] - 1f.withUnit[Meter]).assertQ[Float, Meter](2)
        (3L.withUnit[Kilogram] - 1L.withUnit[Kilogram])
            .assertQ[Long, Kilogram](2)
        (3.withUnit[Meter / Second] - 1.withUnit[Meter / Second])
            .assertQ[Int, Meter / Second](2)

        // same value type, different units
        (1d.withUnit[Kilo * Second] - 1d.withUnit[Second])
            .assertQD[Double, Kilo * Second](0.999)
        (1f.withUnit[Meter] - 1f.withUnit[Yard]).assertQD[Float, Meter](0.0856)

        // non convertible units should fail
        assertCE("1d.withUnit[Meter] - 1d.withUnit[Second]")

        // unsafe truncating conversions should fail
        assertCE("1.withUnit[Meter] - 1.withUnit[Yard]")
        assertCE("1L.withUnit[Meter] - 1L.withUnit[Yard]")
    }

    test("multiplication") {
        (3f.withUnit[Meter / Second] * 5f.withUnit[Second / Meter])
            .assertQ[Float, 1](15)

        (3L.withUnit[1 / Second] * 5L.withUnit[Meter / 1])
            .assertQ[Long, Meter / Second](15)

        (3.withUnit[Second] * 5.withUnit[Kilogram])
            .assertQ[Int, Second * Kilogram](15)

        // changing value should error out
        assertCE("2d.withUnit[Meter] * 3.withUnit[Meter]")

        // explicit conversion will still work
        (2d.withUnit[Meter] * 3.withUnit[Meter].toValue[Double])
            .assertQ[Double, Meter ^ 2](6)
    }

    test("division") {
        (5d.withUnit[Meter] / 2d.withUnit[Second])
            .assertQ[Double, Meter / Second](2.5)

        (5f.withUnit[Meter] / 2f.withUnit[Second])
            .assertQ[Float, Meter / Second](2.5)

        // changing value should error out
        assertCE("12d.withUnit[Meter] / 3.withUnit[Second]")

        // explicit conversion will still work
        (12d.withUnit[Meter] / 3.withUnit[Second].toValue[Double])
            .assertQ[Double, Meter / Second](4)
    }

    test("truncating division") {
        (5L.withUnit[Meter] `tquot` 2L.withUnit[Second])
            .assertQ[Long, Meter / Second](2)

        (5.withUnit[Meter] `tquot` 2.withUnit[Second])
            .assertQ[Int, Meter / Second](2)
    }

    test("power") {
        2d.withUnit[Meter].pow[0].assertQ[Double, 1](1)
        2d.withUnit[Meter].pow[2].assertQ[Double, Meter ^ 2](4)
        2d.withUnit[Meter].pow[-1].assertQ[Double, 1 / Meter](0.5)
        2d.withUnit[Meter]
            .pow[1 / 2]
            .assertQD[Double, Meter ^ (1 / 2)](1.4142135623730951)
        2d.withUnit[Meter]
            .pow[-1 / 2]
            .assertQD[Double, 1 / (Meter ^ (1 / 2))](0.7071067811865476)

        2f.withUnit[Meter].pow[0].assertQ[Float, 1](1)
        2f.withUnit[Meter].pow[2].assertQ[Float, Meter ^ 2](4)
        2f.withUnit[Meter].pow[-1].assertQ[Float, 1 / Meter](0.5)
        2f.withUnit[Meter]
            .pow[1 / 2]
            .assertQD[Float, Meter ^ (1 / 2)](1.4142135623730951)
        2f.withUnit[Meter]
            .pow[-1 / 2]
            .assertQD[Float, 1 / (Meter ^ (1 / 2))](0.7071067811865476)

        // non-negative integer exponents are supported via multiplicative monoid
        2L.withUnit[Meter].pow[0].assertQ[Long, 1](1)
        2L.withUnit[Meter].pow[2].assertQ[Long, Meter ^ 2](4)
        assertCE("2L.withUnit[Meter].pow[-1]")
        assertCE("2L.withUnit[Meter].pow[1 / 2]")
        assertCE("2L.withUnit[Meter].pow[-1 / 2]")

        2.withUnit[Meter].pow[0].assertQ[Int, 1](1)
        2.withUnit[Meter].pow[2].assertQ[Int, Meter ^ 2](4)
        assertCE("2.withUnit[Meter].pow[-1]")
        assertCE("2.withUnit[Meter].pow[1 / 2]")
        assertCE("2.withUnit[Meter].pow[-1 / 2]")
    }

    test("constants in simplified unit types") {
        // changes/improvements to simplification algorithm may change these -
        // it is more important that they be correct than have some particular form, but
        // better forms may be more pleasing to humans

        (2.withUnit[1000 * Meter] * 3.withUnit[Meter])
            .assertQ[Int, 1000 * (Meter ^ 2)](6)
        (2.withUnit[Meter] * 3.withUnit[Meter * 1000])
            .assertQ[Int, (Meter ^ 2) * 1000](6)

        (5d.withUnit[((10 ^ 100) / 3) * Meter] / 2d.withUnit[Second])
            .assertQ[Double, (((10 ^ 100) / 3) * Meter) / Second](2.5)
    }

    test("negation") {
        (-(7d.withUnit[Liter])).assertQ[Double, Liter](-7)
        (-(7f.withUnit[Liter])).assertQ[Float, Liter](-7)
        (-(7L.withUnit[Liter])).assertQ[Long, Liter](-7)
        (-(7.withUnit[Liter])).assertQ[Int, Liter](-7)
    }

    test("equality") {
        assertEquals(1d.withUnit[Meter] === 1d.withUnit[Meter], true)
        assertEquals(1f.withUnit[Meter] === 1f.withUnit[Meter], true)
        assertEquals(1L.withUnit[Meter] === 1L.withUnit[Meter], true)
        assertEquals(1.withUnit[Meter] === 1.withUnit[Meter], true)

        assertEquals(1d.withUnit[Meter] === 2d.withUnit[Meter], false)
        assertEquals(1f.withUnit[Meter] === 2f.withUnit[Meter], false)
        assertEquals(1L.withUnit[Meter] === 2L.withUnit[Meter], false)
        assertEquals(1.withUnit[Meter] === 2.withUnit[Meter], false)

        assertCE("1d.withUnit[Meter] === 1.withUnit[Meter]")

        assertEquals(2d.withUnit[(1 / 2) * Meter] === 1d.withUnit[Meter], true)

        assertEquals(2f.withUnit[(1 / 2) * Meter] === 1f.withUnit[Meter], true)

        assertCE("2d.withUnit[(1 / 2) * Meter] === 1.withUnit[Meter]")

        assertEquals(2f.withUnit[(1 / 2) * Meter] === 2f.withUnit[Meter], false)
        assertEquals(1d.withUnit[(1 / 2) * Meter] === 1d.withUnit[Meter], false)
    }

    test("type aliases") {
        import coulomb.io.ShowUnit

        // units mixed in with type aliases
        object defs {
            import coulomb.define.*
            type Thousand = 1000
            type KiloMeter = Thousand * Meter
            final type MegaMeter
            given DerivedUnit[
                MegaMeter,
                Thousand * KiloMeter,
                "megameter",
                "Mm"
            ] = DerivedUnit()
        }
        import defs.{*, given}

        // My policy goal for type aliases is that type aliases are never expanded.
        // However, scala's implicit resolution logic undermines this somewhat because it
        // pre-dealiases type aliases, so the "outer" type is always dealiases
        // by the time my metaprogramming sees it.
        // Note that type *parameters* are not de-aliased, so any aliased types that
        // occur in a type parameter list will not be altered by Scala.

        // unit conversions should expand type aliases fully
        1d.withUnit[MegaMeter].toUnit[Meter].assertQ[Double, Meter](1e6)

        // conversion in operators should also operate correctly
        (1d.withUnit[MegaMeter] + 1d.withUnit[KiloMeter])
            .assertQD[Double, MegaMeter](1.001)

        // scala's implicit resolution appears to "pre-dealias", and so the result type is
        // not KiloMeter but its expansion.  Note that type parameters to '*' are NOT
        // expanded (so we still see Thousand).
        (1d.withUnit[KiloMeter] + 1d.withUnit[Meter])
            .assertQD[Double, Thousand * Meter](1.001)

        // type aliases should be respected (not expanded) in simplification mode,
        // for constructing the output unit types
        (2d.withUnit[KiloMeter / Second] * 3d.withUnit[Second / Thousand])
            .assertQ[Double, KiloMeter / Thousand](6)

        // scala is de-aliasing KiloMeter
        // using Int here helps JS tests work same as JVM
        assertEquals(1.withUnit[KiloMeter].show, "1 Thousand m")
        assertEquals(1.withUnit[KiloMeter].showFull, "1 Thousand meter")

        // standard derived units work as usual
        assertEquals(summon[ShowUnit[MegaMeter]].abbv, "Mm")
        assertEquals(summon[ShowUnit[MegaMeter]].full, "megameter")

        // scala de-aliasing KiloMeter again
        assertEquals(summon[ShowUnit[KiloMeter]].abbv, "Thousand m")
        assertEquals(summon[ShowUnit[KiloMeter]].full, "Thousand meter")

        // calling inline function directly: scala is not de-aliasing KiloMeter
        // and so my "respect aliases" policy is in full effect:
        assertEquals(ShowUnit[KiloMeter], "KiloMeter")
        assertEquals(ShowUnit.full[KiloMeter], "KiloMeter")

        object su {
            import coulomb.define.ShowUnitAlias
            // customize a show-unit string
            given ShowUnitAlias[KiloMeter, "kilometer", "km"] = ShowUnitAlias()
        }

        // Overriding default ShowUnit defs for a type
        import su.given
        assertEquals(summon[ShowUnit[KiloMeter]].abbv, "km")
        assertEquals(summon[ShowUnit[KiloMeter]].full, "kilometer")
    }

    test("scalar factors") {
        val q = 2d.withUnit[Meter]

        (2d * q).assertQ[Double, Meter](4)
        (2d / q).assertQ[Double, 1 / Meter](1)

        (q * 2d).assertQ[Double, Meter](4)
        (q / 2d).assertQ[Double, Meter](1)
    }

    test("cats Eq, Ord, Hash") {
        import cats.kernel.{Eq, Hash, Order}
        import coulomb.integrations.cats.all.given

        val q1 = 1.withUnit[Meter]
        val q2 = 1.withUnit[Meter]
        val q3 = 2.withUnit[Meter]

        val eqv = summon[Eq[Quantity[Int, Meter]]]
        assertEquals(eqv.eqv(q1, q2), true)
        assertEquals(eqv.eqv(q1, q3), false)

        val ord = summon[Order[Quantity[Int, Meter]]]
        assertEquals(ord.compare(q1, q2), 0)
        assertEquals(ord.compare(q1, q3), -1)
        assertEquals(ord.compare(q3, q1), 1)

        val hash = summon[Hash[Quantity[Int, Meter]]]
        assertEquals(hash.hash(q1), hash.hash(q2))
        assertNotEquals(hash.hash(q1), hash.hash(q3))
    }
