package com.manyangled.unit4s

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

trait UnitMeasure[+M <: UnitMeasure[M]] {
  type RefUnit <: Unit[RefUnit]
}

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

abstract class UnitPower[+U <: Unit[U], P <: church.Integer] {
  type Measure <: UnitMeasure[Measure]
  type Unit <: U
}

abstract class Unit[+U <: Unit[U]] extends UnitPower[U, church.Integer._1]

object conversion {
  import scala.language.implicitConversions

  implicit class EnrichUnitValue[U <: Unit[U], P <: church.Integer :Constructable](uv: UnitValue[UnitPower[U, P]])(implicit cu: Convertable[U, U#Measure#RefUnit]) {
    val cfU = cu.cf
    val p = church.Integer.value[P]

    def to[UP <: UnitPower[_, P]](implicit cu2: Convertable[UP#Unit, U#Measure#RefUnit]) = {
      val cfU2 = cu2.cf
      new UnitValue[UP](uv.value * math.pow(cfU/cfU2, p))
    }
  }

  implicit val convertMeter = Convertable[Meter, Meter] { 1.0 }
  implicit val convertFoot = Convertable[Foot, Meter] { 0.3048 }
  implicit val convertSecond = Convertable[Second, Second] { 1.0 }
  implicit val convertMinute = Convertable[Minute, Second] { 60.0 }

  implicit def convertUV[U1 <: Unit[U1], U2 <: Unit[U2], P <: church.Integer: Constructable](uv1: UnitValue[UnitPower[U1, P]])(implicit cu1: Convertable[U1, U1#Measure#RefUnit], cu2: Convertable[U2, U2#Measure#RefUnit]): UnitValue[UnitPower[U2, P]] = {
    val p = church.Integer.value[P]
    val cf1 = cu1.cf
    val cf2 = cu2.cf
    new UnitValue[UnitPower[U2, P]](uv1.value * math.pow(cf1/cf2, p))
  }
}

class Meter extends Unit[Meter] {
  type Measure = Length
  type Unit = Meter
}

class Foot extends Unit[Foot] {
  type Measure = Length
  type Unit = Foot
}

class Second extends Unit[Second] {
  type Measure = Time
  type Unit = Second
}

class Minute extends Unit[Minute] {
  type Measure = Time
  type Unit = Minute
}

class UnitValue[+UP <: UnitPower[_,_]](v: Double) {
  def value = v
}

object test {
  import com.manyangled.unit4s.conversion._

  val m1 = new UnitValue[Meter](1.2)
  val f1 = new UnitValue[Foot](3.4)
  val s1 = new UnitValue[Second](4.5)
  val n1 = new UnitValue[Minute](5.6)

  val f2 = m1.to[Foot]

  case class Equal[A >: B <: B, B]()

  
}
