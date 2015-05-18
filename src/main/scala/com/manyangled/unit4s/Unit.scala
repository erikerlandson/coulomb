package com.manyangled

object unit4s {

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

object framework {

trait UnitMeasurePower[M <: UnitMeasure[M], P <: church.Integer] {
  type Type = UnitMeasurePower[M, P]
  type Pow[Q <: church.Integer] = UnitMeasurePower[M, P#Mul[Q]]
}

trait UnitMeasure[M <: UnitMeasure[M]] extends UnitMeasurePower[M, church.Integer._1] {
  type RefUnit <: Unit[RefUnit, M]
}

trait UnitConstraint[UMP <: UnitMeasurePower[_,_]]
trait WithUnit[UP <: UnitPower[_,_,_]]

trait Convertable[U, R] {
  def cf: Double
}

object Convertable {
  def apply[U, R](blk: => Double) = new Convertable[U, R] {
    def cf = blk
  }
}

abstract class UnitPower[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer :Constructable] {
  final def apply(v: Double)(implicit x: Convertable[U, M#RefUnit]) = new UnitValue[U, M, P](v) with UnitConstraint[UnitMeasurePower[M, P]]
  type Type = UnitPower[U, M, P]
  type Pow[Q <: church.Integer] = UnitPower[U, M, P#Mul[Q]]
}

abstract class Unit[U <: Unit[U, M], M <: UnitMeasure[M]] extends UnitPower[U, M, church.Integer._1] {
}

trait UValue {
  def value: Double
  def cfRef: Double
}

object UValue {
  implicit class EnrichWithDimension[M <: UnitMeasure[M], P <: church.Integer :Constructable](uv: UValue with UnitConstraint[UnitMeasurePower[M, P]]) {
    def to[U2 <: Unit[U2, M]](up: UnitPower[U2, M, P])(implicit
      cu2: Convertable[U2, M#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[U2, M, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
  implicit class EnrichWithUnit[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer :Constructable](uv: UValue with WithUnit[UnitPower[U, M, P]]) {
    def to[U2 <: Unit[U2, M]](up: UnitPower[U2, M, P])(implicit
      cu2: Convertable[U2, M#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[U2, M, P](uv.value * math.pow(cfU/cfU2, p))
    }
  }
}

class UnitValue[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer :Constructable](v: Double)(implicit cu: Convertable[U, M#RefUnit]) extends UValue with WithUnit[UnitPower[U, M, P]] with UnitConstraint[UnitMeasurePower[M, P]] {
  def value = v
  def cfRef = cu.cf
}

object UnitValue {
  import scala.language.implicitConversions

  implicit class EnrichUnitValue[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer :Constructable](uv: UnitValue[U, M, P]) {
    def to[U2 <: Unit[U2, M]](up: UnitPower[U2, M, P])(implicit
      cu: Convertable[U, M#RefUnit],
      cu2: Convertable[U2, M#RefUnit]) = {
      val cfU = cu.cf
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[U2, M, P](uv.value * math.pow(cfU/cfU2, p)) with UnitConstraint[UnitMeasurePower[M, P]]
    }
  }
}

} // framework
} // unit4s

// test definition of a new set of units
object testunits {
import com.manyangled.unit4s.framework._

object defined {

trait Length extends UnitMeasure[Length] {
  type RefUnit = Meter
}

trait Time extends UnitMeasure[Time] {
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
  def k[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer :Constructable](v: UValue with WithUnit[UnitPower[U, M, P]]) = {
    val p = church.Integer.value[P]
    println(s"v= ${v.value}  p= $p")
    v.value
  }
} // test
