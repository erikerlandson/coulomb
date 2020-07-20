package coulomb

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import utest._

import coulomb.si._
import coulomb.temp._

import coulomb.validators.CoulombValidators._

object SerializationTests extends TestSuite {
  val tests = Tests {
    test("quantity be serializable") {
      import coulomb.scalatest.serde._
      val ts = Temperature[Int, Celsius](100)
      val td = roundTripSerDe(ts)
      assert(td.isValidT[Int, Celsius](100))
      assert(td === ts)
    }

    test("temperature be serializable") {
      import coulomb.scalatest.serde._
      val ts = Temperature[Int, Celsius](100)
      val td = roundTripSerDe(ts)
      assert(td.isValidT[Int, Celsius](100))
      assert(td === ts)
    }
  }
}
