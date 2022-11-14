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

class TimeUnitsSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.units.time.{*, given}
    import algebra.instances.all.given

    test("lift via EpochTime") {
        EpochTime[Second](1d).assertDQ[Double, Second](1)
        EpochTime[Second](1f).assertDQ[Float, Second](1)
        EpochTime[Second](1L).assertDQ[Long, Second](1)
        EpochTime[Second](1).assertDQ[Int, Second](1)
        EpochTime[Second]("foo").assertDQ[String, Second]("foo")
    }

    test("lift via withEpochTime") {
        1d.withEpochTime[Second].assertDQ[Double, Second](1)
        1f.withEpochTime[Second].assertDQ[Float, Second](1)
        1L.withEpochTime[Second].assertDQ[Long, Second](1)
        1.withEpochTime[Second].assertDQ[Int, Second](1)
        "foo".withEpochTime[Second].assertDQ[String, Second]("foo")
    }

    test("value") {
        1d.withEpochTime[Second].value.assertVT[Double](1)
        2f.withEpochTime[Second].value.assertVT[Float](2)
        3L.withEpochTime[Second].value.assertVT[Long](3)
        4.withEpochTime[Second].value.assertVT[Int](4)
        "foo".withEpochTime[Second].value.assertVT[String]("foo")
    }

    test("show") {
        assertEquals(1.withEpochTime[Hour].show, "1 h")
    }

    test("showFull") {
        assertEquals(1.withEpochTime[Day].showFull, "1 day")
    }

    test("toValue") {
        import coulomb.policy.standard.given

        1d.withEpochTime[Second].toValue[Float].assertDQ[Float, Second](1)
        1L.withEpochTime[Second].toValue[Int].assertDQ[Int, Second](1)
        // truncating
        assertCE("1d.withEpochTime[Second].toValue[Int]")
        1d.withEpochTime[Second].tToValue[Int].assertDQ[Int, Second](1)
    }

    test("toUnit") {
        import coulomb.policy.standard.given

        36d.withEpochTime[Hour].toUnit[Day].assertDQD[Double, Day](1.5)

        // truncating
        assertCE("36.withEpochTime[Hour].toUnit[Day]")
        36.withEpochTime[Hour].tToUnit[Day].assertDQD[Int, Day](1)
    }

    test("subtraction strict") {
        import coulomb.policy.strict.given

        (10d.withEpochTime[Second] - 1d.withEpochTime[Second])
            .assertQ[Double, Second](9)
        (10f.withEpochTime[Second] - 1f.withEpochTime[Second])
            .assertQ[Float, Second](9)
        (10L.withEpochTime[Second] - 1L.withEpochTime[Second])
            .assertQ[Long, Second](9)
        (10.withEpochTime[Second] - 1.withEpochTime[Second])
            .assertQ[Int, Second](9)

        assertCE("61d.withEpochTime[Second] - 1f.withEpochTime[Second]")
        assertCE("61d.withEpochTime[Second] - 1d.withEpochTime[Minute]")
        (61.withEpochTime[Second]
            .toValue[Double] - 1d.withEpochTime[Minute].toUnit[Second])
            .assertQ[Double, Second](1)
    }

    test("subtraction standard") {
        import coulomb.policy.standard.given

        // different value type, same unit type
        (61d.withEpochTime[Second] - 1f.withEpochTime[Second])
            .assertQ[Double, Second](60)
        // same value type, different unit type
        (61d.withEpochTime[Second] - 1d.withEpochTime[Minute])
            .assertQ[Double, Second](1)
        // both different
        (61L.withEpochTime[Second] - 1f.withEpochTime[Minute])
            .assertQ[Float, Second](1)

        // truncating
        assertCE("61.withEpochTime[Second] - 1.withEpochTime[Minute]")
        (61f.withEpochTime[Second]
            .tToValue[Int] - 1.withEpochTime[Minute].tToUnit[Second])
            .assertQ[Int, Second](1)
    }

    test("quantity subtraction strict") {
        import coulomb.policy.strict.given

        (10d.withEpochTime[Second] - 1d.withUnit[Second])
            .assertDQ[Double, Second](9)
        (10f.withEpochTime[Second] - 1f.withUnit[Second])
            .assertDQ[Float, Second](9)
        (10L.withEpochTime[Second] - 1L.withUnit[Second])
            .assertDQ[Long, Second](9)
        (10.withEpochTime[Second] - 1.withUnit[Second]).assertDQ[Int, Second](9)

        assertCE("61d.withEpochTime[Second] - 1f.withUnit[Second]")
        assertCE("61d.withEpochTime[Second] - 1d.withUnit[Minute]")
        (61.withEpochTime[Second]
            .toValue[Double] - 1d.withUnit[Minute].toUnit[Second])
            .assertDQ[Double, Second](1)
    }

    test("quantity subtraction standard") {
        import coulomb.policy.standard.given

        // different value type, same unit type
        (61d.withEpochTime[Second] - 1f.withUnit[Second])
            .assertDQ[Double, Second](60)
        // same value type, different unit type
        (61d.withEpochTime[Second] - 1d.withUnit[Minute])
            .assertDQ[Double, Second](1)
        // both different
        (61L.withEpochTime[Second] - 1f.withUnit[Minute])
            .assertDQ[Float, Second](1)

        // truncating
        assertCE("61.withEpochTime[Second] - 1.withUnit[Minute]")
        (61f.withEpochTime[Second]
            .tToValue[Int] - 1.withUnit[Minute].tToUnit[Second])
            .assertDQ[Int, Second](1)
    }

    test("quantity addition strict") {
        import coulomb.policy.strict.given

        (10d.withEpochTime[Second] + 1d.withUnit[Second])
            .assertDQ[Double, Second](11)
        (10f.withEpochTime[Second] + 1f.withUnit[Second])
            .assertDQ[Float, Second](11)
        (10L.withEpochTime[Second] + 1L.withUnit[Second])
            .assertDQ[Long, Second](11)
        (10.withEpochTime[Second] + 1.withUnit[Second])
            .assertDQ[Int, Second](11)

        assertCE("61d.withEpochTime[Second] + 1f.withUnit[Second]")
        assertCE("61d.withEpochTime[Second] + 1d.withUnit[Minute]")
        (61.withEpochTime[Second]
            .toValue[Double] + 1d.withUnit[Minute].toUnit[Second])
            .assertDQ[Double, Second](121)
    }

    test("quantity addition standard") {
        import coulomb.policy.standard.given

        // different value type, same unit type
        (61d.withEpochTime[Second] + 1f.withUnit[Second])
            .assertDQ[Double, Second](62)
        // same value type, different unit type
        (61d.withEpochTime[Second] + 1d.withUnit[Minute])
            .assertDQ[Double, Second](121)
        // both different
        (61L.withEpochTime[Second] + 1f.withUnit[Minute])
            .assertDQ[Float, Second](121)

        // truncating
        assertCE("61.withEpochTime[Second] + 1.withUnit[Minute]")
        (61f.withEpochTime[Second]
            .tToValue[Int] + 1.withUnit[Minute].tToUnit[Second])
            .assertDQ[Int, Second](121)
    }

    test("less-than strict") {
        import coulomb.policy.strict.given

        assertEquals(3f.withEpochTime[Week] < 4f.withEpochTime[Week], true)
        assertEquals(4.withEpochTime[Week] < 4.withEpochTime[Week], false)

        assertCE("3f.withEpochTime[Week] < 4d.withEpochTime[Week]")
        assertCE("3f.withEpochTime[Day] < 3f.withEpochTime[Week]")

        assertEquals(
            4f.withEpochTime[Week].toValue[Double] < 4d.withEpochTime[Week],
            false
        )
        assertEquals(
            3f.withEpochTime[Day] < 3f.withEpochTime[Week].toUnit[Day],
            true
        )
    }

    test("less-than standard") {
        import coulomb.policy.standard.given

        assertEquals(4f.withEpochTime[Week] < 4d.withEpochTime[Week], false)
        assertEquals(3f.withEpochTime[Day] < 3f.withEpochTime[Week], true)

        assertCE("3L.withEpochTime[Day] < 3L.withEpochTime[Week]")
        assertEquals(
            3L.withEpochTime[Day] < 3L.withEpochTime[Week].tToUnit[Day],
            true
        )
    }

class JavaTimeSuite extends CoulombSuite:
    import java.time.{Duration, Instant}
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.units.time.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.javatime.*
    import algebra.instances.all.given
    import coulomb.policy.standard.given

    object bc:
        import coulomb.define.*
        final type YearsBC
        given ctx_unit_YearsBC: define.DeltaUnit[
            YearsBC,
            -31536000 * Second,
            1970,
            "years BC",
            "BC"
        ] = define.DeltaUnit()

    test("toQuantity") {
        import coulomb.units.javatime.conversions.explicit.given

        val dur = Duration.ofSeconds(70, 400000000)
        dur.toQuantity[Float, Minute].assertQD[Float, Minute](1.17333)

        // truncation
        assertCE("dur.toQuantity[Int, Minute]")
        dur.tToQuantity[Int, Minute].assertQ[Int, Minute](1)
    }

    test("toDuration") {
        import coulomb.units.javatime.conversions.explicit.given

        val q = 1d.withUnit[Hour] + 777.1d.withUnit[Nano * Second]
        val dur = q.toDuration
        assertEquals(dur.getSeconds(), 3600L)
        assertEquals(dur.getNano(), 777)
    }

    test("toEpochTime") {
        import coulomb.units.javatime.conversions.explicit.given

        val ins = Instant.parse("1969-07-20T00:00:00Z")
        ins.toEpochTime[Double, Day].assertDQD[Double, Day](-165)

        // truncation
        assertCE("ins.toEpochTime[Long, Day]")
        ins.tToEpochTime[Long, Day].assertDQ[Long, Day](-165)
    }

    test("toInstant") {
        import coulomb.units.javatime.conversions.explicit.given

        val et = (-165L).withEpochTime[Day]
        assertEquals(et.toInstant.toString, "1969-07-20T00:00:00Z")
    }

    test("toInstant YearsBC") {
        import coulomb.units.javatime.conversions.explicit.given

        import bc.{*, given}

        val et = 100.withEpochTime[YearsBC]
        assertEquals(et.toInstant.toString, "-0099-05-18T00:00:00Z")
    }

    test("implicit Q -> D") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversions.all.given

        def f(d: Duration): (Long, Int) = (d.getSeconds(), d.getNano())
        val q = 1d.withUnit[Hour] + 777.1d.withUnit[Nano * Second]
        assertEquals(f(q), (3600L, 777))
    }

    test("implicit D -> Q") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversions.all.given

        def f(q: Quantity[Float, Minute]): Float = q.value
        val dur = Duration.ofSeconds(70, 400000000)
        f(dur).assertVTD[Float](1.17333)
    }

    test("implicit ET -> I") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversions.all.given

        def f(i: Instant): String = i.toString()
        val et = (-165L).withEpochTime[Day]
        assertEquals(f(et), "1969-07-20T00:00:00Z")
    }

    test("implicit I -> ET") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversions.all.given

        def f(et: EpochTime[Double, Day]): Double = et.value
        val ins = Instant.parse("1969-07-20T00:00:00Z")
        f(ins).assertVTD[Double](-165)
    }
