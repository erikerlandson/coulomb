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
  def factor[U1 <: Unit, U2 <: Unit](implicit udef1: UnitDef[U1], udef2: UnitDef[U2], eq: U1#RU =:= U2#RU) = udef1.cfr / udef2.cfr

  def toString(value: Double, units: Seq[(String, Int)]) = {
    val unitstr = units.map { case (name, exp) =>
      if (exp == 1) s"$name" else s"($name)^($exp)"
    }.mkString(" ")
    s"UnitValue${units.length}($value $unitstr)"
  }
}

trait UnitDef[U <: Unit] {
  def name: String
  def cfr: Double
}

case class UnitSpec[U <: Unit](name: String, cfr: Double) extends UnitDef[U]

case class PrefixSpec[P[_ <: Unit] <: Unit](pfn: String, pcf: Double)

trait U$ <: Unit {
  type RU = U$
}
object U$ {
  implicit val udef = UnitSpec[U$]("U$", 1.0)
}

trait Unitless <: Unit {
  type RU = Unitless
}
object Unitless {
  implicit val udef = UnitSpec[Unitless]("unitless-ratio", 1.0)
}

trait UnitFactory[U <: Unit] {
  def apply(value: Double = 1.0)(implicit udef: UnitDef[U]): UnitValue1[U, _1] = UnitValue1[U, _1](value)
  implicit def fromObject[T <: U](t: T)(implicit udef: UnitDef[U]): UnitValue1[U, _1] = UnitValue1[U, _1](1.0)
}

trait PrefixFactory[P[_ <: Unit] <: Unit] {
  def apply[U <: Unit](uv: UnitValue1[U, _1])(implicit pudef: UnitDef[P[U]]): UnitValue1[P[U], _1] = UnitValue1[P[U], _1](uv.value)
  implicit def pudef[U <: Unit](implicit pdef: PrefixSpec[P], udef: UnitDef[U]): UnitDef[P[U]] = new UnitDef[P[U]] {
    def name = pdef.pfn + udef.name
    def cfr = pdef.pcf * udef.cfr
  }
}

package prefix {
  trait Milli[U <: Unit] extends Unit {
    type RU = U#RU
  }
  object Milli extends PrefixFactory[Milli] {
    implicit val pdef = PrefixSpec[Milli]("milli", 1e-3)
  }

  trait Kilo[U <: Unit] extends Unit {
    type RU = U#RU
  }
  object Kilo extends PrefixFactory[Kilo] {
    implicit val pdef = PrefixSpec[Kilo]("kilo", 1e+3)
  }
}

package testunits {
  trait Meter extends Unit {
    type RU = Meter
  }
  object Meter extends Meter with UnitFactory[Meter] {
    implicit val udef = UnitSpec[Meter]("meter", 1.0)
  }

  trait Foot extends Unit {
    type RU = Meter
  }
  object Foot extends Foot {
    implicit val udef = UnitSpec[Foot]("foot", 0.3048)
  }

  trait Yard extends Unit {
    type RU = Meter
  }
  object Yard extends Yard {
    implicit val udef = UnitSpec[Yard]("yard", 0.9144)
  }

  trait Second extends Unit {
    type RU = Second
  }
  object Second extends Second {
    implicit val udef = UnitSpec[Second]("second", 1.0)
  }

  trait Minute extends Unit {
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
