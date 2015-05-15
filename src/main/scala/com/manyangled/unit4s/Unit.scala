package com.manyangled

object unit4s {

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

trait UnitMeasurePower[M <: UnitMeasure[M], P <: church.Integer]

trait UnitMeasure[M <: UnitMeasure[M]] extends UnitMeasurePower[M, church.Integer._1] {
  type RefUnit <: Unit[RefUnit, M]
  type Pow[Q <: church.Integer] = UnitMeasurePower[M, Q]
}

// contravariance!
trait UnitConstraint[-UMP <: UnitMeasurePower[_,_]]

trait Length extends UnitMeasure[Length] {
  type RefUnit = Meter
}

trait Time extends UnitMeasure[Time] {
  type RefUnit = Second
}

trait Convertable[U, R] {
  def cf: Double
}

object Convertable {
  def apply[U, R](blk: => Double) = new Convertable[U, R] {
    def cf = blk
  }
}

class UnitPower[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer] {
  final def apply(v: Double) = new UnitValue[UnitPower[U, M, P]](v) with UnitConstraint[UnitMeasurePower[M, P]]
}

class Unit[U <: Unit[U, M], M <: UnitMeasure[M]] extends UnitPower[U, M, church.Integer._1] {
  type Pow[Q <: church.Integer] = UnitPower[U, M, Q]
}

object conversion {
  import scala.language.implicitConversions

  implicit class EnrichUnitValue[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer :Constructable](uv: UnitValue[UnitPower[U, M, P]]) {
    def to[U2 <: Unit[U2, M]](up: UnitPower[U2, M, P])(implicit
      cu: Convertable[U, M#RefUnit],
      cu2: Convertable[U2, M#RefUnit]) = {
      val cfU = cu.cf
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UnitValue[UnitPower[U2, M, P]](uv.value * math.pow(cfU/cfU2, p)) with UnitConstraint[UnitMeasurePower[M, P]]
    }
  }

  implicit val convertMeter = Convertable[Meter, Meter] { 1.0 }
  implicit val convertFoot = Convertable[Foot, Meter] { 0.3048 }
  implicit val convertSecond = Convertable[Second, Second] { 1.0 }
  implicit val convertMinute = Convertable[Minute, Second] { 60.0 }
}

class Meter extends Unit[Meter, Length]
object Meter extends Meter

class Foot extends Unit[Foot, Length]
object Foot extends Foot

class Second extends Unit[Second, Time]
object Second extends Second

class Minute extends Unit[Minute, Time]
object Minute extends Minute

class UnitValue[UP <: UnitPower[_,_,_]](v: Double) {
  def value = v
}

/*
object UnitValue {
  def apply[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer](v: Double, u: UnitPower[U, M, P]) = new UnitValue[UnitPower[U,M,P]](v) with UnitConstraint[UnitMeasurePower[M, P]]
}
*/

object test {
  import com.manyangled.unit4s.conversion._

  val m1 = Meter(1.2)
  val f1 = Foot(3.4)
  val s1 = Second(4.5)
  val n1 = Minute(5.6)

  val f2 = m1.to(Foot)

  def f[UV <: UnitValue[_] with UnitConstraint[Length]](v: UV) = v.value
  def g[UV <: UnitValue[_] with UnitConstraint[Time#Pow[church.Integer._1]]](v: UV) = v.value

  type Area = Length#Pow[church.Integer._2]
  type SquareMeter = Meter#Pow[church.Integer._2]
  object SquareMeter extends SquareMeter
  type SquareFoot = Foot#Pow[church.Integer._2]
  object SquareFoot extends SquareFoot

  val sqm1 = SquareMeter(4.0)
  val sqf1 = sqm1.to(SquareFoot)

  def h[UV <: UnitValue[_] with UnitConstraint[Area]](v: UV) = v.value
}

}
