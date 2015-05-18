package com.manyangled

object unit4s {

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

import infra._

trait QuantityOf[UMP <: Quantity[_,_]]
trait UnitOf[UP <: Unit[_,_,_]]

trait UnitValue {
  def value: Double
  def cfRef: Double
}

object UnitValue {
  implicit class EnrichQuantityOf[Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UnitValue with QuantityOf[Quantity[Q, P]]) {
    def to[U2 <: BaseUnit[U2, Q]](up: Unit[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UVal[U2, Q, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
  implicit class EnrichUnitOf[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UnitValue with UnitOf[Unit[U, Q, P]]) {
    def to[U2 <: BaseUnit[U2, Q]](up: Unit[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UVal[U2, Q, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
}

object infra {

trait Quantity[Q <: BaseQuantity[Q], P <: church.Integer] {
  type Type = Quantity[Q, P]
  type Pow[K <: church.Integer] = Quantity[Q, P#Mul[K]]
}

trait BaseQuantity[Q <: BaseQuantity[Q]] extends Quantity[Q, church.Integer._1] {
  type RefUnit <: BaseUnit[RefUnit, Q]
}

trait Convertable[U, R] {
  def cf: Double
}

object Convertable {
  def apply[U, R](blk: => Double) = new Convertable[U, R] {
    def cf = blk
  }
}

abstract class Unit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable] {
  final def apply(v: Double)(implicit x: Convertable[U, Q#RefUnit]) = new UVal[U, Q, P](v)
  type Type = Unit[U, Q, P]
  type Pow[K <: church.Integer] = Unit[U, Q, P#Mul[K]]
}

abstract class BaseUnit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q]] extends Unit[U, Q, church.Integer._1] {
}


class UVal[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: Double)(implicit cu: Convertable[U, Q#RefUnit]) extends UnitValue with UnitOf[Unit[U, Q, P]] with QuantityOf[Quantity[Q, P]] {
  def value = v
  def cfRef = cu.cf
}

object UVal {
  import scala.language.implicitConversions

  implicit class EnrichUVal[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UVal[U, Q, P]) {
    def to[U2 <: BaseUnit[U2, Q]](up: Unit[U2, Q, P])(implicit
      cu: Convertable[U, Q#RefUnit],
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = cu.cf
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UVal[U2, Q, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
}

} // infra
} // unit4s

// test definition of a new set of units
object testunits {
import com.manyangled.unit4s.infra._

object defined {

trait Length extends BaseQuantity[Length] {
  type RefUnit = Meter
}

trait Time extends BaseQuantity[Time] {
  type RefUnit = Second
}

class Meter extends BaseUnit[Meter, Length]
class Foot extends BaseUnit[Foot, Length]

class Second extends BaseUnit[Second, Time]
class Minute extends BaseUnit[Minute, Time]

} // defined

object conversion {
  implicit val convertMeter = Convertable[defined.Meter, defined.Meter] { 1.0 }
  implicit val convertFoot = Convertable[defined.Foot, defined.Meter] { 0.3048 }
  implicit val convertSecond = Convertable[defined.Second, defined.Second] { 1.0 }
  implicit val convertMinute = Convertable[defined.Minute, defined.Second] { 60.0 }
}

type Length = defined.Length#Type
type Time = defined.Time#Type

type Meter = defined.Meter#Type
object Meter extends defined.Meter

type Foot = defined.Foot#Type
object Foot extends defined.Foot

type Second = defined.Second#Type
object Second extends defined.Second

type Minute = defined.Minute#Type
object Minute extends defined.Minute

} // testunits


object test {
  import com.manyangled.unit4s._
  import com.manyangled.testunits._
  import com.manyangled.testunits.conversion._

  val m1 = Meter(1.2)
  val f1 = Foot(3.4)
  val s1 = Second(4.5)
  val n1 = Minute(5.6)

  val f2 = m1.to(Foot)

  def f(v: UnitValue with QuantityOf[Length]) = v.to(Meter)
  def j(v: UnitValue with UnitOf[Meter]) = v.value

  def g(v: UnitValue with QuantityOf[Time#Pow[church.Integer._1]]) = v.to(Second)

  type Area = Length#Pow[church.Integer._2]
  type SquareMeter = Meter#Pow[church.Integer._2]
  object SquareMeter extends SquareMeter
  type SquareFoot = Foot#Pow[church.Integer._2]
  object SquareFoot extends SquareFoot

  val sqm1 = SquareMeter(4.0)
  val sqf1 = sqm1.to(SquareFoot)

  def h(v: UnitValue with QuantityOf[Area]) = v.to(SquareMeter)

  object subtest {
    import com.manyangled.unit4s.infra._
    import com.manyangled.util.Constructable
    def k[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: UnitValue with UnitOf[Unit[U, Q, P]]) = {
      val p = church.Integer.value[P]
      println(s"v= ${v.value}  p= $p")
      v.value
    }
  }
} // test
