package com.manyangled

object unit4s {

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

import infra._

object Kilo extends Prefix {
  def factor = 1e3
  def name = "kilo"
}
object Milli extends Prefix {
  def factor = 1e-3
  def name = "milli"
}

object infra {

trait Prefix {
  self =>
  def factor: Double
  def name: String
  final def ==(that: Prefix) = (this.factor == that.factor) && (this.name == that.name)
  final def *[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer](u: Unit[U, Q, P]) = {
    new Unit[U, Q, P] {
      def name = u.name
      def cf = u.cf
      def unit = u.unit
      def value = u.value
      def prefix = self
      def power = u.power
    }
  }
  final override def toString = name
}
object UnitPrefix extends Prefix {
  def factor = 1.0
  def name = "unit"
}

/*
trait UnitMeta[U] {
  def cf: Double
  def name: String
}

object UnitMeta {
  def apply[U](blk: => Double, blkName: => String) = new UnitMeta[U] {
    def cf = blk
    def name = blkName
  }
}
*/

trait Quantity[Q <: BaseQuantity[Q], P <: church.Integer] {
  type QType = Quantity[Q, P]
  type QPow[K <: church.Integer] = Quantity[Q, P#Mul[K]]
  def unit: Unit[_, Q, P]
  def to[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer](u: Unit[U, Q, P]) = {
    val v = unit.value * unit.prefix.factor * math.pow(unit.cf / u.cf, u.power) / u.prefix.factor
    new Unit[U, Q, P] {
      def value = v
      def name = u.name
      def cf = u.cf
      def unit = u.unit
      def prefix = u.prefix
      def power = u.power
    }
  }
}

trait BaseQuantity[Q <: BaseQuantity[Q]] extends Quantity[Q, church.Integer._1] {
  type RefUnit <: BaseUnit[RefUnit, Q]
}

abstract class Unit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer] extends Quantity[Q, P] {
  self =>
  type Type = Unit[U, Q, P]
  type Pow[K <: church.Integer] = Unit[U, Q, P#Mul[K]]
  def name: String
  def cf: Double
  def power: Int
  def value: Double
  def prefix: Prefix
  def apply(v: Double) = new Unit[U, Q, P] {
    def name = self.name
    def cf = self.cf
    def power = self.power
    def unit = self
    def value = v
    def prefix = self.prefix
  }
  override def toString = {
    val pre = if (prefix == UnitPrefix) "" else s"${prefix}-"
    val exp =
      if (power == 0) ""
      else if (power == 1) ""
      else if (power > 1) s"^$power"
      else s"^($power)"
    val u = if (power == 0) "" else s"($pre$name$exp)"
    s"($value)$u"
  }
}

abstract class BaseUnit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q]] extends Unit[U, Q, church.Integer._1] {
  def power = 1
  def value = 1.0
  def unit = this
  def prefix = UnitPrefix
}

} // infra
} // unit4s

object ISQ {
  type Length = infra.Length#QType
  type Time = infra.Time#QType

  object infra {
    import com.manyangled.unit4s.infra._

    trait Length extends BaseQuantity[Length] {
      type RefUnit = Meter
    }

    trait Time extends BaseQuantity[Time] {
      type RefUnit = Second
    }

    class Meter extends BaseUnit[Meter, Length] {
      def name = "meter"
      def cf = 1.0
    }

    class Second extends BaseUnit[Second, Time] {
      def name = "second"
      def cf = 1.0
    }
  }
}

object ISU {
  type Meter = ISQ.infra.Meter#Type
  object Meter extends ISQ.infra.Meter

  type Second = ISQ.infra.Second#Type
  object Second extends ISQ.infra.Second
}

// test definition of a new set of units
object custom {
  type Foot = infra.Foot#Type
  object Foot extends infra.Foot

  type Minute = infra.Minute#Type
  object Minute extends infra.Minute

  object infra {
    import com.manyangled.unit4s.infra._
    import com.manyangled.ISQ.infra._

    class Foot extends BaseUnit[Foot, Length] {
      def name = "foot"
      def cf = 0.3048
    }

    class Minute extends BaseUnit[Minute, Time] {
      def name = "minute"
      def cf = 60.0
    }
  }
}

object test {
  import com.manyangled.unit4s._
  import com.manyangled.ISQ._
  import com.manyangled.ISU._
  import com.manyangled.custom._

  val m1 = Meter(1.2)
  val f1 = Foot(3.4)
  val s1 = Second(4.5)
  val n1 = Minute(5.6)

  val f2 = m1.to(Foot)


/*
  val sum1 = m1 + (f1)

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

  val KiloMeter = Kilo*Meter
  val v1 = KiloMeter(3.0)
  val v2 = Kilo*Meter(2.0)
  val v3 = Meter(4000).to(Kilo*Meter)

  val vec1: Vector[UnitValue with QuantityOf[Length]] = Vector(Meter(1.0), Foot(2.0), Milli*Meter(3.0))

*/
} // test
