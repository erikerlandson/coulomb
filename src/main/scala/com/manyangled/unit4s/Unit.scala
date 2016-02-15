package com.manyangled.unit4s

import scala.language.implicitConversions

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

trait Unit[U <: Unit[U]] {
  type RU <: Unit[_]

  def apply(value: Double = 1.0)(implicit udef: UnitDef[U]): UnitValue1[U, _1] = UnitValue1[U, _1](value)
}

object Unit {
  def factor[U1 <: Unit[U1], U2 <: Unit[U2]](implicit udef1: UnitDef[U1], udef2: UnitDef[U2], eq: U1#RU =:= U2#RU) = udef1.cfr / udef2.cfr

  def toString(value: Double, units: Seq[(String, Int)]) = {
    val unitstr = units.map { case (name, exp) =>
      if (exp == 1) s"$name" else s"($name)^($exp)"
    }.mkString(" ")
    s"UnitValue${units.length}($value $unitstr)"
  }
}

trait UnitDef[U <: Unit[U]] {
  def name: String
  def cfr: Double
}

case class UnitSpec[U <: Unit[U]](val name: String, val cfr: Double) extends UnitDef[U]

case class PrefixSpec[P <: Unit[P], U <: Unit[U]](udef: UnitDef[U], val pfn: String, val pcf: Double) extends UnitDef[P] {
  val name = pfn + udef.name
  val cfr = pcf * udef.cfr
}

trait U$ <: Unit[U$] {
  type RU = U$
}
object U$ {
  implicit val udef = UnitSpec[U$]("U$", 1.0)
}

trait Unitless <: Unit[Unitless] {
  type RU = Unitless
}
object Unitless {
  implicit val udef = UnitSpec[Unitless]("unitless-ratio", 1.0)
}

/*
import scala.language.higherKinds
trait Factory[P[_] <: Unit[P[_]]] {
  def apply[U <: Unit[U]](uv: UnitValue1[U, _1])(implicit pdef: PrefixSpec[P[U], U]): UnitValue1[P[U], _1] = UnitValue1[P[U], _1](uv.value)
}
*/

package prefix {
  trait Micro[U <: Unit[U]] extends Unit[Micro[U]] {
    type RU = U#RU
  }
  object Micro {
    implicit def factory[U <: Unit[U]](implicit udef: UnitDef[U]) = PrefixSpec[Micro[U], U](udef, "micro", 1e-6)
  }

  trait Milli[U <: Unit[U]] extends Unit[Milli[U]] {
    type RU = U#RU
  }
  object Milli {
    implicit def factory[U <: Unit[U]](implicit udef: UnitDef[U]) = PrefixSpec[Milli[U], U](udef, "milli", 1e-3)
  }

  trait Kilo[U <: Unit[U]] extends Unit[Kilo[U]] {
    type RU = U#RU
  }
  object Kilo {
    implicit def factory[U <: Unit[U]](implicit udef: UnitDef[U]) = PrefixSpec[Kilo[U], U](udef, "kilo", 1e3)
  }

  trait Mega[U <: Unit[U]] extends Unit[Mega[U]] {
    type RU = U#RU
  }
  object Mega {
    implicit def factory[U <: Unit[U]](implicit udef: UnitDef[U]) = PrefixSpec[Mega[U], U](udef, "mega", 1e6)
  }
}

package testunits {
  trait Meter extends Unit[Meter] {
    type RU = Meter
  }
  object Meter extends Meter {
    implicit val udef = UnitSpec[Meter]("meter", 1.0)
  }

  trait Foot extends Unit[Foot] {
    type RU = Meter
  }
  object Foot extends Foot {
    implicit val udef = UnitSpec[Foot]("foot", 0.3048)
  }

  trait Yard extends Unit[Yard] {
    type RU = Meter
  }
  object Yard extends Yard {
    implicit val udef = UnitSpec[Yard]("yard", 0.9144)
  }

  trait Second extends Unit[Second] {
    type RU = Second
  }
  object Second extends Second {
    implicit val udef = UnitSpec[Second]("second", 1.0)
  }

  trait Minute extends Unit[Minute] {
    type RU = Second
  }
  object Minute extends Minute {
    implicit val udef = UnitSpec[Minute]("minute", 60.0)
  }
}

object test {
  import testunits._
  import prefix._

  def f1(uv: UnitValue1[Foot, Integer._1]) = uv.value
  def f2(uv: UnitValue2[Foot, Integer._1, Second, Integer._neg1]) = uv.value
}
