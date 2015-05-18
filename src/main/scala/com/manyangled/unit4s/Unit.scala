package com.manyangled

object unit4s {

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

object framework {

trait Quantity[Q <: BaseQuantity[Q], P <: church.Integer] {
  type Type = Quantity[Q, P]
  type Pow[K <: church.Integer] = Quantity[Q, P#Mul[K]]
}

trait BaseQuantity[Q <: BaseQuantity[Q]] extends Quantity[Q, church.Integer._1] {
  type RefUnit <: Unit[RefUnit, Q]
}

trait UnitConstraint[UMP <: Quantity[_,_]]
trait WithUnit[UP <: UnitPower[_,_,_]]

trait Convertable[U, R] {
  def cf: Double
}

object Convertable {
  def apply[U, R](blk: => Double) = new Convertable[U, R] {
    def cf = blk
  }
}

abstract class UnitPower[U <: Unit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable] {
  final def apply(v: Double)(implicit x: Convertable[U, Q#RefUnit]) = new UnitValue[U, Q, P](v) with UnitConstraint[Quantity[Q, P]]
  type Type = UnitPower[U, Q, P]
  type Pow[K <: church.Integer] = UnitPower[U, Q, P#Mul[K]]
}

abstract class Unit[U <: Unit[U, Q], Q <: BaseQuantity[Q]] extends UnitPower[U, Q, church.Integer._1] {
}

trait UValue {
  def value: Double
  def cfRef: Double
}

object UValue {
  implicit class EnrichWithDimension[Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UValue with UnitConstraint[Quantity[Q, P]]) {
    def to[U2 <: Unit[U2, Q]](up: UnitPower[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[U2, Q, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
  implicit class EnrichWithUnit[U <: Unit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UValue with WithUnit[UnitPower[U, Q, P]]) {
    def to[U2 <: Unit[U2, Q]](up: UnitPower[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[U2, Q, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
}

class UnitValue[U <: Unit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: Double)(implicit cu: Convertable[U, Q#RefUnit]) extends UValue with WithUnit[UnitPower[U, Q, P]] with UnitConstraint[Quantity[Q, P]] {
  def value = v
  def cfRef = cu.cf
}

object UnitValue {
  import scala.language.implicitConversions

  implicit class EnrichUnitValue[U <: Unit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UnitValue[U, Q, P]) {
    def to[U2 <: Unit[U2, Q]](up: UnitPower[U2, Q, P])(implicit
      cu: Convertable[U, Q#RefUnit],
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = cu.cf
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[U2, Q, P](uv.value * math.pow(cfU/cfU2, p)) with UnitConstraint[Quantity[Q, P]]
    }
  }
}

} // framework
} // unit4s

// test definition of a new set of units
object testunits {
import com.manyangled.unit4s.framework._

object defined {

trait Length extends BaseQuantity[Length] {
  type RefUnit = Meter
}

trait Time extends BaseQuantity[Time] {
  type RefUnit = Second
}

class Meter extends Unit[Meter, Length]
class Foot extends Unit[Foot, Length]

class Second extends Unit[Second, Time]
class Minute extends Unit[Minute, Time]

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
  import com.manyangled.unit4s.framework._
  import com.manyangled.testunits._
  import com.manyangled.testunits.conversion._

  val m1 = Meter(1.2)
  val f1 = Foot(3.4)
  val s1 = Second(4.5)
  val n1 = Minute(5.6)

  val f2 = m1.to(Foot)

  def f(v: UValue with UnitConstraint[Length]) = v.to(Meter)
  def j(v: UValue with WithUnit[Meter]) = v.value

  def g(v: UValue with UnitConstraint[Time#Pow[church.Integer._1]]) = v.to(Second)

  type Area = Length#Pow[church.Integer._2]
  type SquareMeter = Meter#Pow[church.Integer._2]
  object SquareMeter extends SquareMeter
  type SquareFoot = Foot#Pow[church.Integer._2]
  object SquareFoot extends SquareFoot

  val sqm1 = SquareMeter(4.0)
  val sqf1 = sqm1.to(SquareFoot)

  def h(v: UValue with UnitConstraint[Area]) = v.to(SquareMeter)

  import com.manyangled.util.Constructable
  def k[U <: Unit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: UValue with WithUnit[UnitPower[U, Q, P]]) = {
    val p = church.Integer.value[P]
    println(s"v= ${v.value}  p= $p")
    v.value
  }
} // test
