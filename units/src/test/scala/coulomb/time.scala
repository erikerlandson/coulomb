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
        assertEquals(show(1.withEpochTime[Hour]), "1 h")
        // currently the compiler is confused by 'type EpochTime[V, U]'
        //assertEquals(1.withEpochTime[Hour].show, "1 h")
    }

    test("showFull") {
        assertEquals(showFull(1.withEpochTime[Day]), "1 day")
        //assertEquals(1.withEpochTime[Day].showFull, "1 day")
    }

    test("toValue") {
        import coulomb.conversion.standard.value.given

        1d.withEpochTime[Second].toValue[Float].assertDQ[Float, Second](1)
        1L.withEpochTime[Second].toValue[Int].assertDQ[Int, Second](1)
        // truncating
        assertCE("1d.withEpochTime[Second].toValue[Int]")
        1d.withEpochTime[Second].tToValue[Int].assertDQ[Int, Second](1)
    }

    test("toUnit") {
        import coulomb.conversion.standard.all.given

        36d.withEpochTime[Hour].toUnit[Day].assertDQD[Double, Day](1.5)

        //truncating
        assertCE("36.withEpochTime[Hour].toUnit[Day]")
        36.withEpochTime[Hour].tToUnit[Day].assertDQD[Int, Day](1)
    }

    test("subtraction strict") {
        import coulomb.ops.standard.given
        import coulomb.conversion.standard.explicit.given

        (10d.withEpochTime[Second] - 1d.withEpochTime[Second]).assertQ[Double, Second](9)
        (10f.withEpochTime[Second] - 1f.withEpochTime[Second]).assertQ[Float, Second](9)
        (10L.withEpochTime[Second] - 1L.withEpochTime[Second]).assertQ[Long, Second](9)
        (10.withEpochTime[Second] - 1.withEpochTime[Second]).assertQ[Int, Second](9)

        assertCE("61d.withEpochTime[Second] - 1f.withEpochTime[Second]")
        assertCE("61d.withEpochTime[Second] - 1d.withEpochTime[Minute]")
        (61.withEpochTime[Second].toValue[Double] - 1d.withEpochTime[Minute].toUnit[Second]).assertQ[Double, Second](1)
    }

    test("subtraction standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        // different value type, same unit type 
        (61d.withEpochTime[Second] - 1f.withEpochTime[Second]).assertQ[Double, Second](60)
        // same value type, different unit type
        (61d.withEpochTime[Second] - 1d.withEpochTime[Minute]).assertQ[Double, Second](1)
        // both different
        (61L.withEpochTime[Second] - 1f.withEpochTime[Minute]).assertQ[Float, Second](1)

        // truncating
        assertCE("61.withEpochTime[Second] - 1.withEpochTime[Minute]")
        (61f.withEpochTime[Second].tToValue[Int] - 1.withEpochTime[Minute].tToUnit[Second]).assertQ[Int, Second](1)
    }

    test("quantity subtraction strict") {
        import coulomb.ops.standard.given
        import coulomb.conversion.standard.explicit.given

        (10d.withEpochTime[Second] - 1d.withUnit[Second]).assertDQ[Double, Second](9)
        (10f.withEpochTime[Second] - 1f.withUnit[Second]).assertDQ[Float, Second](9)
        (10L.withEpochTime[Second] - 1L.withUnit[Second]).assertDQ[Long, Second](9)
        (10.withEpochTime[Second] - 1.withUnit[Second]).assertDQ[Int, Second](9)

        assertCE("61d.withEpochTime[Second] - 1f.withUnit[Second]")
        assertCE("61d.withEpochTime[Second] - 1d.withUnit[Minute]")
        (61.withEpochTime[Second].toValue[Double] - 1d.withUnit[Minute].toUnit[Second]).assertDQ[Double, Second](1)
    }

    test("quantity subtraction standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        // different value type, same unit type 
        (61d.withEpochTime[Second] - 1f.withUnit[Second]).assertDQ[Double, Second](60)
        // same value type, different unit type
        (61d.withEpochTime[Second] - 1d.withUnit[Minute]).assertDQ[Double, Second](1)
        // both different
        (61L.withEpochTime[Second] - 1f.withUnit[Minute]).assertDQ[Float, Second](1)

        // truncating
        assertCE("61.withEpochTime[Second] - 1.withUnit[Minute]")
        (61f.withEpochTime[Second].tToValue[Int] - 1.withUnit[Minute].tToUnit[Second]).assertDQ[Int, Second](1)
    }

    test("quantity addition strict") {
        import coulomb.ops.standard.given
        import coulomb.conversion.standard.explicit.given

        (10d.withEpochTime[Second] + 1d.withUnit[Second]).assertDQ[Double, Second](11)
        (10f.withEpochTime[Second] + 1f.withUnit[Second]).assertDQ[Float, Second](11)
        (10L.withEpochTime[Second] + 1L.withUnit[Second]).assertDQ[Long, Second](11)
        (10.withEpochTime[Second] + 1.withUnit[Second]).assertDQ[Int, Second](11)

        assertCE("61d.withEpochTime[Second] + 1f.withUnit[Second]")
        assertCE("61d.withEpochTime[Second] + 1d.withUnit[Minute]")
        (61.withEpochTime[Second].toValue[Double] + 1d.withUnit[Minute].toUnit[Second]).assertDQ[Double, Second](121)
    }

    test("quantity addition standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        // different value type, same unit type 
        (61d.withEpochTime[Second] + 1f.withUnit[Second]).assertDQ[Double, Second](62)
        // same value type, different unit type
        (61d.withEpochTime[Second] + 1d.withUnit[Minute]).assertDQ[Double, Second](121)
        // both different
        (61L.withEpochTime[Second] + 1f.withUnit[Minute]).assertDQ[Float, Second](121)

        // truncating
        assertCE("61.withEpochTime[Second] + 1.withUnit[Minute]")
        (61f.withEpochTime[Second].tToValue[Int] + 1.withUnit[Minute].tToUnit[Second]).assertDQ[Int, Second](121)
    }

    test("less-than strict") {
        import coulomb.ops.standard.given
        import coulomb.conversion.standard.explicit.given

        assertEquals(3f.withEpochTime[Week] < 4f.withEpochTime[Week], true)
        assertEquals(4.withEpochTime[Week] < 4.withEpochTime[Week], false)

        assertCE("3f.withEpochTime[Week] < 4d.withEpochTime[Week]")
        assertCE("3f.withEpochTime[Day] < 3f.withEpochTime[Week]")

        assertEquals(4f.withEpochTime[Week].toValue[Double] < 4d.withEpochTime[Week], false)
        assertEquals(3f.withEpochTime[Day] < 3f.withEpochTime[Week].toUnit[Day], true)
    }

    test("less-than standard") {
        import coulomb.ops.standard.given
        import coulomb.ops.resolution.standard.given
        import coulomb.conversion.standard.all.given

        assertEquals(4f.withEpochTime[Week] < 4d.withEpochTime[Week], false)
        assertEquals(3f.withEpochTime[Day] < 3f.withEpochTime[Week], true)

        assertCE("3L.withEpochTime[Day] < 3L.withEpochTime[Week]")
        assertEquals(3L.withEpochTime[Day] < 3L.withEpochTime[Week].tToUnit[Day], true)
    }
