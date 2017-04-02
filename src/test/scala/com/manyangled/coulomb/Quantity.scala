package com.manyangled.coulomb

import org.scalatest._
import org.scalactic._

class QuantitySpec extends FlatSpec with Matchers {

  implicit val epsilon = 1e-6f
  implicit val doubleTolerant = TolerantNumerics.tolerantDoubleEquality(epsilon)
  implicit val floatTolerant = TolerantNumerics.tolerantFloatEquality(epsilon)

  it should "allocate a Quantity" in {
    import SIBaseUnits._
    import USCustomaryUnits._
    val q = new Quantity[Double, Meter](1.0)
  }
}
