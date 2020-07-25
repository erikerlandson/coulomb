package coulomb

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import utest._

import coulomb.time._
import coulomb.javatime._
import java.time._

import coulomb.si._
import coulomb.siprefix._

import coulomb.validators.CoulombValidators._

object TimeTests extends TestSuite {
  val tests = Tests {
    test("EpochTime") {
      // At the moment I'm spot-checking the core properties.
      // EpochTime and Temperature are both specializations of OffsetQuantity and
      // Temperature exercises these pretty thoroughly.
      val t1 = 1D.withEpochTime[Minute]
      val t2 = 1D.withEpochTime[Second]
      assert((t2 - t1).isValidQ[Double, Second](-59))
      assert((t1 - t2).isValidQ[Double, Minute](0.98333))
      assert((t1 + 1D.withUnit[Hour]).isValidOQ[Double, Minute](61))
      assert((t1 - 1D.withUnit[Hour]).isValidOQ[Double, Minute](-59))
      assert(t1 > t2)
      assert(t2 < t1)
      assert(t1.show == "1.0 min" || t1.show == "1 min")
    }

    test("integrate Duration with coulomb") {
      // 10.4 seconds
      val dur = Duration.ofSeconds(10, 400000000)
      assert(dur.toQuantity[Float, Minute].isValidQ[Float, Minute](0.17333))
      assert(dur.plus(1D.withUnit[Minute]).toQuantity[Double, Second].isValidQ[Double, Second](70.4))
      assert(dur.minus(1D.withUnit[Kilo %* Second]).toQuantity[Double, Second].isValidQ[Double, Second](-989.6))
    }

    test("integrate Quantity with java.time") {
      val q = 1D.withUnit[Hour] + 777.1D.withUnit[Nano %* Second]
      val dur = q.toDuration
      assert(dur.getSeconds() == 3600L)
      assert(dur.getNano() == 777)
      assert(1f.withUnit[Second].plus(Duration.ofSeconds(10, 777000000)).isValidQ[Float, Second](11.777))
      assert(1f.withUnit[Minute].minus(Duration.ofSeconds(10, 777000000)).isValidQ[Float, Minute](0.82038))
    }

    test("integrate Instant with coulomb") {
      val ins = Instant.parse("1969-07-20T00:00:00Z")
      assert(ins.toEpochTime[Double, Day].isValidOQ[Double, Day](-165.0))
      assert(Instant.parse("1969-07-20T00:00:00Z").plus(1D.withUnit[Week]).toString == "1969-07-27T00:00:00Z")
      assert(Instant.parse("1969-07-20T00:00:00Z").minus(1D.withUnit[Week]).toString == "1969-07-13T00:00:00Z")
    }

    test("integrate EpochTime with java.time") {
      val et = (-165L).withEpochTime[Day]
      assert(et.toInstant.toString == "1969-07-20T00:00:00Z")
      assert(et.plus(Duration.ofSeconds(86400, 0)).isValidOQ[Long, Day](-164))
      assert(et.minus(Duration.ofSeconds(86400, 0)).isValidOQ[Long, Day](-166))
    }
  }
}
