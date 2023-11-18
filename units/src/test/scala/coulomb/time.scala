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
    import coulomb.units.syntax.*
    import algebra.instances.all.given

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
        1d.withEpochTime[Second].toValue[Float].assertDQ[Float, Second](1)
        1L.withEpochTime[Second].toValue[Int].assertDQ[Int, Second](1)
        // truncating
        1.5d.withEpochTime[Second].toValue[Int].assertDQ[Int, Second](1)
    }

    test("toUnit") {
        36d.withEpochTime[Hour].toUnit[Day].assertDQD[Double, Day](1.5)

        assertCE("36.withEpochTime[Hour].toUnit[Day]")
    }

    test("subtraction") {
        (10d.withEpochTime[Second] - 1d.withEpochTime[Second])
            .assertQ[Double, Second](9)
        (10f.withEpochTime[Second] - 1f.withEpochTime[Second])
            .assertQ[Float, Second](9)
        (10L.withEpochTime[Second] - 1L.withEpochTime[Second])
            .assertQ[Long, Second](9)
        (10.withEpochTime[Second] - 1.withEpochTime[Second])
            .assertQ[Int, Second](9)

        assertCE("61d.withEpochTime[Second] - 1f.withEpochTime[Second]")
        (61.withEpochTime[Second]
            .toValue[Double] - 1d.withEpochTime[Minute].toUnit[Second])
            .assertQ[Double, Second](1)

        // same value type, different unit type
        (61d.withEpochTime[Second] - 1d.withEpochTime[Minute])
            .assertQ[Double, Second](1)

        // truncating
        assertCE("61.withEpochTime[Second] - 1.withEpochTime[Minute]")
    }

    test("quantity subtraction") {
        (10d.withEpochTime[Second] - 1d.withUnit[Second])
            .assertDQ[Double, Second](9)
        (10f.withEpochTime[Second] - 1f.withUnit[Second])
            .assertDQ[Float, Second](9)
        (10L.withEpochTime[Second] - 1L.withUnit[Second])
            .assertDQ[Long, Second](9)
        (10.withEpochTime[Second] - 1.withUnit[Second]).assertDQ[Int, Second](9)

        assertCE("61d.withEpochTime[Second] - 1f.withUnit[Second]")
        (61.withEpochTime[Second]
            .toValue[Double] - 1d.withUnit[Minute].toUnit[Second])
            .assertDQ[Double, Second](1)

        // same value type, different unit type
        (61d.withEpochTime[Second] - 1d.withUnit[Minute])
            .assertDQ[Double, Second](1)

        // truncating
        assertCE("61.withEpochTime[Second] - 1.withUnit[Minute]")
    }

    test("quantity addition") {
        (10d.withEpochTime[Second] + 1d.withUnit[Second])
            .assertDQ[Double, Second](11)
        (10f.withEpochTime[Second] + 1f.withUnit[Second])
            .assertDQ[Float, Second](11)
        (10L.withEpochTime[Second] + 1L.withUnit[Second])
            .assertDQ[Long, Second](11)
        (10.withEpochTime[Second] + 1.withUnit[Second])
            .assertDQ[Int, Second](11)

        assertCE("61d.withEpochTime[Second] + 1f.withUnit[Second]")
        (61.withEpochTime[Second]
            .toValue[Double] + 1d.withUnit[Minute].toUnit[Second])
            .assertDQ[Double, Second](121)

        // same value type, different unit type
        (61d.withEpochTime[Second] + 1d.withUnit[Minute])
            .assertDQ[Double, Second](121)

        // truncating
        assertCE("61.withEpochTime[Second] + 1.withUnit[Minute]")
    }

    test("less-than") {
        assertEquals(3f.withEpochTime[Week] < 4f.withEpochTime[Week], true)
        assertEquals(4.withEpochTime[Week] < 4.withEpochTime[Week], false)

        assertCE("3f.withEpochTime[Week] < 4d.withEpochTime[Week]")

        assertEquals(
            4f.withEpochTime[Week].toValue[Double] < 4d.withEpochTime[Week],
            false
        )
        assertEquals(
            3f.withEpochTime[Day] < 3f.withEpochTime[Week].toUnit[Day],
            true
        )

        assertEquals(4d.withEpochTime[Week] < 4d.withEpochTime[Week], false)
        assertEquals(3f.withEpochTime[Day] < 3f.withEpochTime[Week], true)

        assertCE("3L.withEpochTime[Day] < 3L.withEpochTime[Week]")
    }

class JavaTimeSuite extends CoulombSuite:
    import java.time.{Duration, Instant}
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.units.time.{*, given}
    import coulomb.units.syntax.*
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.javatime.*
    import algebra.instances.all.given

    object bc:
        import coulomb.define.*
        final type YearsBC
        given unit_YearsBC: define.DeltaUnit[
            YearsBC,
            -31536000 * Second,
            1970,
            "years BC",
            "BC"
        ] = define.DeltaUnit()

    test("toQuantity") {
        val dur = Duration.ofSeconds(70, 400000000)
        dur.toQuantity[Float, Minute].assertQD[Float, Minute](1.17333)

        // truncation
        assertCE("dur.toQuantity[Int, Minute]")
    }

    test("toDuration") {
        val q = 1d.withUnit[Hour] + 777.1d.withUnit[Nano * Second]
        val dur = q.toDuration
        assertEquals(dur.getSeconds(), 3600L)
        assertEquals(dur.getNano(), 777)
    }

    test("toEpochTime") {
        val ins = Instant.parse("1969-07-20T00:00:00Z")
        ins.toEpochTime[Double, Day].assertDQD[Double, Day](-165)

        // truncation
        assertCE("ins.toEpochTime[Long, Day]")
    }

    test("toEpochTime YearsBC") {
        import bc.{*, given}

        val ins = Instant.parse("-0099-05-18T00:00:00Z")
        ins.toEpochTime[Double, YearsBC].assertDQD[Double, YearsBC](100)

        // truncation
        assertCE("ins.toEpochTime[Long, YearsBC]")
    }

    test("toInstant") {
        val et = (-165L).withEpochTime[Day]
        assertEquals(et.toInstant.toString, "1969-07-20T00:00:00Z")
    }

    test("toInstant Milli * Seconds") {
        // verify it handles arbitrary unit types that are compatible
        val et = (1000L).withEpochTime[Milli * Second]
        assertEquals(et.toInstant.toString, "1970-01-01T00:00:01Z")
    }

    test("toInstant YearsBC") {
        import bc.{*, given}

        // verify it handles negative directionality
        val et = 100.withEpochTime[YearsBC]
        assertEquals(et.toInstant.toString, "-0099-05-18T00:00:00Z")
    }

    test("implicit Q -> D") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversion.implicits.given

        def f(d: Duration): (Long, Int) = (d.getSeconds(), d.getNano())
        val q = 1d.withUnit[Hour] + 777.1d.withUnit[Nano * Second]
        assertEquals(f(q), (3600L, 777))
    }

    test("implicit D -> Q") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversion.implicits.given

        def f(q: Quantity[Float, Minute]): Float = q.value
        val dur = Duration.ofSeconds(70, 400000000)
        f(dur).assertVTD[Float](1.17333)
    }

    test("implicit ET -> I") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversion.implicits.given

        def f(i: Instant): String = i.toString()
        val et = (-165L).withEpochTime[Day]
        assertEquals(f(et), "1969-07-20T00:00:00Z")
    }

    test("implicit I -> ET") {
        import scala.language.implicitConversions
        import coulomb.units.javatime.conversion.implicits.given

        def f(et: EpochTime[Double, Day]): Double = et.value
        val ins = Instant.parse("1969-07-20T00:00:00Z")
        f(ins).assertVTD[Double](-165)
    }
