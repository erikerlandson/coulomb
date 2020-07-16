package coulomb.cats

import cats._
import cats.implicits._
import coulomb.Quantity

trait CatsImplicits {
  /**
    * Eq[Quantity[A, B]] when there is an Eq[A]
    *
    * @group typeclass
    */
  implicit def eqQuantity[A: Eq, B]: Eq[Quantity[A, B]] = Eq.by(_.value)

  /**
    * Order[Quantity[A, B]] when there is an Order[A]
    *
    * @group typeclass
    */
  implicit def orderQuantity[A: Order, B]: Order[Quantity[A, B]] = Order.by(_.value)

}

object implicits extends CatsImplicits
