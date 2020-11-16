package coulomb

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import coulomb.si._
import coulomb.temp._

import coulomb.validators.CoulombValidators._

class SerializationTests extends munit.FunSuite {
  test("quantity be serializable") {
    import coulomb.scalatest.serde._
    val qs = Quantity[Int, Meter %/ Second](10)
    val qd = roundTripSerDe(qs)
    assert(qd.isValidQ[Int, Meter %/ Second](10))
    assertEquals(qd, qs)
  }

  test("temperature be serializable") {
    import coulomb.scalatest.serde._
    val ts = Temperature[Int, Celsius](100)
    val td = roundTripSerDe(ts)
    assert(td.isValidT[Int, Celsius](100))
    assertEquals(td, ts)
  }
}
