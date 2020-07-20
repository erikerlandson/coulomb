package coulomb.cats

import cats.tests.CatsSuite
import cats._
import cats.implicits._
import cats.kernel.laws.discipline._
import cats.data.NonEmptyList
import coulomb._
import coulomb.cats.implicits._
import coulomb.si._
import coulomb.siprefix._
import utest._

final class CatsSpec extends CatsSuite {
  import coulomb.scalacheck.ArbQuantity._

  type MetersPerSecond = Meter %/ Second
  type KilometersPerSecond = (Kilo %* Meter) %/ Second
  val aq: Quantity[Double, MetersPerSecond] = 1000.0.withUnit[MetersPerSecond]
  val bq: Quantity[Double, MetersPerSecond] = 1000.0.withUnit[Meter %/ Second]
  val cq: Quantity[Int, KilometersPerSecond] = 1.withUnit[(Kilo %* Meter) %/ Second]
  val dq: Quantity[Double, Kilogram] = 1.0.withUnit[Kilogram]

  test("eq") {
    implicit val eqdms = implicitly[Eq[Quantity[Double, Meter %/ Second]]]
    // Same units and value types
    assert(eqdms.eqv(aq, bq))
    // Convertible units and value types
    assert(eqdms.eqv(aq, cq))
    // Incompatible units won't compile
    compileError("eqdms.eqv(aq, dq)")
  }

  test("order") {
    // Same units and value types
    val aq2: Quantity[Double, MetersPerSecond] = 900.0.withUnit[MetersPerSecond]
    // Sorting a NonEmptyList always uses cats' Order
    assert(NonEmptyList.of(aq, aq2).sorted === NonEmptyList.of(aq2, aq))
    val cq2: Quantity[Int, KilometersPerSecond] = 0.9.withUnit[KilometersPerSecond]
    // You can sort equivalent units if you specify the type which will auto convert
    assert(NonEmptyList.of[Quantity[Double, MetersPerSecond]](aq, cq2).sorted === NonEmptyList.of[Quantity[Double, MetersPerSecond]](cq2, aq))
    // This can't compile, the type of the list has a mix of all the units
    compileError("NonEmptyList.of(aq, cq2).sorted")
    // This can't compile, the quantitys are not comparable
    compileError("NonEmptyList.of(aq, dq).sorted")
  }

  checkAll("EqTest", EqTests[Quantity[Int, MetersPerSecond]].eqv)
  checkAll("OrderTest", OrderTests[Quantity[Int, MetersPerSecond]].order)
}
