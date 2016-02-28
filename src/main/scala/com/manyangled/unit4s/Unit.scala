package com.manyangled.unit4s

import scala.language.implicitConversions
import scala.language.higherKinds

import com.manyangled.church.{ Integer, IntegerValue }
import Integer.{ _0, _1 }

sealed class =!=[A,B]

trait NeqLowerPriorityImplicits {
  implicit def equal[A]: =!=[A, A] = {
    sys.error("should not be called")
  }
}
object =!= extends NeqLowerPriorityImplicits {
  implicit def nequal[A,B](implicit same: A =:= B = null): =!=[A,B] = {
    if (same != null) sys.error("should not be called explicitly with same type")
    else new =!=[A,B]
  }
}

trait Unit {
  type RU <: Unit
}

object Unit {
  def factor[U1 <: Unit, U2 <: Unit](implicit urec1: UnitRec[U1], urec2: UnitRec[U2], eq: U1#RU =:= U2#RU) = urec1.cfr / urec2.cfr

  def toString(value: Double, units: Seq[(String, Int)]) = {
    val unitstr = units.map { case (name, exp) =>
      if (exp == 1) s"$name" else s"($name)^($exp)"
    }.mkString(" ")
    s"UnitValue${units.length}($value $unitstr)"
  }
}

trait UnitLike[U <: Unit] extends Unit {
  type RU = U
}

trait PrefixLike[U <: Unit] extends Unit {
  type RU = U#RU
}

trait UnitRec[U <: Unit] {
  def name: String
  def cfr: Double
}

trait PrefixRec[P[_ <: Unit] <: Unit] {
  def pfn: String
  def pcf: Double
}

class UnitCompanion[U <: Unit](uname: String, ucfr: Double) {
  def apply(value: Double = 1.0)(implicit urec: UnitRec[U]): UnitValue1[U, _1] = UnitValue1[U, _1](value)
  implicit val urec: UnitRec[U] = new UnitRec[U] {
    def name = uname
    def cfr = ucfr
  }
  implicit def fromObject[T <: U](t: T)(implicit urec: UnitRec[U]): UnitValue1[U, _1] = UnitValue1[U, _1](1.0)
}

class PrefixCompanion[P[_ <: Unit] <: Unit](upfn: String, upcf: Double) {
  def apply[U <: Unit](uv: UnitValue1[U, _1])(implicit purec: UnitRec[P[U]]): UnitValue1[P[U], _1] = UnitValue1[P[U], _1](uv.value)
  implicit val prec: PrefixRec[P] = new PrefixRec[P] {
    def pfn = upfn
    def pcf = upcf
  }
  implicit def purec[U <: Unit](implicit pdef: PrefixRec[P], urec: UnitRec[U]): UnitRec[P[U]] = new UnitRec[P[U]] {
    def name = pdef.pfn + urec.name
    def cfr = pdef.pcf * urec.cfr
  }
}

package prefix {
  trait Milli[U <: Unit] extends PrefixLike[U]
  object Milli extends PrefixCompanion[Milli]("milli", 1e-3)

  trait Kilo[U <: Unit] extends PrefixLike[U]
  object Kilo extends PrefixCompanion[Kilo]("kilo", 1e+3)
}

package testunits {
  trait Meter extends UnitLike[Meter]
  object Meter extends UnitCompanion[Meter]("meter", 1.0)

  trait Foot extends UnitLike[Meter]
  object Foot extends UnitCompanion[Foot]("foot", 0.3048)

  trait Yard extends UnitLike[Meter]
  object Yard extends UnitCompanion[Yard]("yard", 0.9144)

  trait Second extends UnitLike[Second]
  object Second extends UnitCompanion[Second]("second", 1.0)

  trait Minute extends UnitLike[Second]
  object Minute extends UnitCompanion[Minute]("minute", 60.0)
}

trait UZ extends UnitLike[UZ]
object UZ extends UnitCompanion[UZ]("UZ", 1.0) with UZ

object test {
  import testunits._
  import prefix._

  def f1(uv: UnitValue1[Foot, Integer._1]) = uv.value
  def f2(uv: UnitValue2[Foot, Integer._1, Second, Integer._neg1]) = uv.value
}
