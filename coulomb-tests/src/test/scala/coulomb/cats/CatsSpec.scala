package coulomb.cats

import cats.tests.CatsSuite
import cats.{ Eq, Show, Order }
import cats.kernel.laws.discipline._
import coulomb._
import coulomb.cats.implicits._
import coulomb.si._
import coulomb.siprefix._

final class CatsSpec extends CatsSuite {
  import coulomb.scalacheck.ArbQuantity._

  type Units = (Kilo %* Meter) %/ Second

  checkAll("EqTest", EqTests[Quantity[Int, Units]].eqv)
  checkAll("OrderTest", OrderTests[Quantity[Int, Units]].order)
}
