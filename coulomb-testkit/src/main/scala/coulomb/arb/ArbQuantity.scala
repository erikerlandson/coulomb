package coulomb.arb

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary._
import org.scalacheck.Cogen
import coulomb._

trait ArbQuantity {
  implicit def arbQuantity[A: Arbitrary, B]: Arbitrary[Quantity[A, B]] = Arbitrary {
    for {
      a <- arbitrary[A]
    } yield Quantity(a)
  }

  implicit def arbCogen[A: Cogen, B]: Cogen[Quantity[A, B]] =
    Cogen[A].contramap(_.value)
}

object ArbQuantity extends ArbQuantity
