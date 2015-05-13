package com.manyangled.unit4s

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

trait UnitMeasure[M <: UnitMeasure[M]] {
  type RefUnit <: Unit[RefUnit, M]
}

trait Length extends UnitMeasure[Length] {
  type RefUnit = Meter
}

trait Time extends UnitMeasure[Time] {
  type RefUnit = Second
}

trait Convertable[U] {
  def cf: Double
}

object Convertable {
  def apply[U](blk: => Double): Convertable[U] = new Convertable[U] {
    def cf = blk
  }
}

class UnitPower[U <: Unit[U, M], M <: UnitMeasure[M], P <: church.Integer](v: Double) {
  type Unit = U
  type Measure = M
  type Power = P
  final def value = v
}

class Unit[U <: Unit[U, M], M <: UnitMeasure[M]](v: Double) extends UnitPower[U, M, church.Integer._1](v)

object conversion {
  import scala.language.implicitConversions

  class EnrichUnitPower[U <: Unit[U, M] :Convertable, M <: UnitMeasure[M], P <: church.Integer :Constructable](up: UnitPower[U, M, P]) {
    val toRef = implicitly[Convertable[U]].cf
    val p = church.Integer.value[P]
    def to[U2 <: Unit[U2, M] :Convertable] = {
      val toU2 = implicitly[Convertable[U2]].cf
      new UnitPower[U2, M, P](up.value * math.pow(toRef/toU2, p))
    }
  }

  implicit def toEnrichUnitPower[U <: Unit[U, M] :Convertable, M <: UnitMeasure[M], P <: church.Integer :Constructable](up: UnitPower[U, M, P]) = new EnrichUnitPower(up)

  implicit val convertMeter: Convertable[Meter] = Convertable[Meter] { 1.0 }
  implicit val convertFoot = Convertable[Foot] { 0.3048 }
  implicit val convertSecond = Convertable[Second] { 1.0 }
  implicit val convertMinute = Convertable[Minute] { 60.0 }
}

class Meter(value: Double) extends Unit[Meter, Length](value)

class Foot(value: Double) extends Unit[Foot, Length](value)

class Second(value: Double) extends Unit[Second, Time](value)

class Minute(value: Double) extends Unit[Minute, Time](value)

object test {
  import com.manyangled.unit4s.conversion._
  import com.manyangled.church.Integer._
  import com.manyangled.church.church$detail._

  val m1 = new Meter(1.2)
  val f1 = new Foot(3.4)
  val s1 = new Second(4.5)
  val mm1 = new Minute(5.6)

  val f2 = m1.to[Foot]
}
