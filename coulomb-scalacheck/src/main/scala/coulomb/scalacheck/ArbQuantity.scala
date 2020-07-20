package coulomb.scalacheck

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary._
import org.scalacheck.Cogen
import coulomb._

trait ArbQuantity {
  implicit def arbQuantity[V: Arbitrary, U]: Arbitrary[Quantity[V, U]] = Arbitrary {
    for {
      a <- arbitrary[V]
    } yield Quantity(a)
  }

  implicit def arbCogen[V: Cogen, U]: Cogen[Quantity[V, U]] =
    Cogen[V].contramap(_.value)
}

object ArbQuantity extends ArbQuantity
