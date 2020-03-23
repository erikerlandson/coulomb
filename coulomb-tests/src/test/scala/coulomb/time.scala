package coulomb

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import utest._

import coulomb.si._
import coulomb.time._
import coulomb.javatime._

import coulomb.validators.CoulombValidators._

object TimeTests extends TestSuite {
  val tests = Tests {
    test("EpochTime") {
      val t1 = 1D.withEpochTime[Minute]
      val t2 = 1D.withEpochTime[Second]
      assert((t2 - t1).isValidQ[Double, Second](-59))
      assert((t1 + 1D.withUnit[Hour]).isValidOQ[Double, Minute](61))
      assert((t1 - 1D.withUnit[Hour]).isValidOQ[Double, Minute](-59))
    }
  }
}
