package com.manyangled

object unit4s {

import com.manyangled.church

import infra._

class Kilo extends Prefix[Kilo] {
  def factor = 1e3
  def name = "kilo"
}
object Kilo extends Kilo

class Milli extends Prefix[Milli] {
  def factor = 1e-3
  def name = "milli"
}
object Milli extends Milli

object infra {

trait Prefix[F <: Prefix[F]] extends Serializable {
  self =>
  def factor: Double
  def name: String
  final def *[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer](u: Unit[U, Q, P, UnitPrefix]) = Unit[U, Q, P, F](u.name, u.cf, self, u.value, u.power)
  final override def toString = s"Prefix($name, $factor)"
}

class UnitPrefix extends Prefix[UnitPrefix] {
  def factor = 1.0
  def name = "[unit]"
}
object UnitPrefix extends UnitPrefix

trait Quantity[Q <: BaseQuantity[Q], P <: church.Integer] extends Serializable {
  type QType = Quantity[Q, P]
  type QPow[K <: church.Integer] = Quantity[Q, P#Mul[K]]
  def unit: Unit[_, Q, P, _]
  def to[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer, F <: Prefix[F]](u: Unit[U, Q, P, F]) = {
    val v = unit.value * unit.prefix.factor * math.pow(unit.cf / u.cf, u.power) / u.prefix.factor
    Unit[U, Q, P, F](u.name, u.cf, u.prefix, v, u.power)
  }
  def +[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer, F <: Prefix[F]](rhs: Unit[U, Q, P, F]) = {
    val lhs = unit.to(rhs)
    Unit[U, Q, P, F](rhs.name, rhs.cf, rhs.prefix, lhs.value + rhs.value, rhs.power)
  }
  def -[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer, F <: Prefix[F]](rhs: Unit[U, Q, P, F]) = {
    val lhs = unit.to(rhs)
    Unit[U, Q, P, F](rhs.name, rhs.cf, rhs.prefix, lhs.value - rhs.value, rhs.power)
  }
}

trait BaseQuantity[Q <: BaseQuantity[Q]] extends Quantity[Q, church.Integer._1] {
  type RefUnit <: BaseUnit[RefUnit, Q]
}

abstract class Unit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer, F <: Prefix[F]] extends Quantity[Q, P] {
  self =>
  type Type = Unit[U, Q, P, F]
  type Pow[K <: church.Integer] = Unit[U, Q, P#Mul[K], F]
  def name: String
  def cf: Double
  def power: Int
  def value: Double
  def prefix: Prefix[_]
  def apply(v: Double) = Unit[U, Q, P, F](self.name, self.cf, self.prefix, v, self.power)
  override def toString = {
    val pre = if ((prefix.factor == UnitPrefix.factor) && (prefix.name == UnitPrefix.name)) "" else s"${prefix.name}-"
    val exp =
      if (power == 0) ""
      else if (power == 1) ""
      else if (power > 1) s"^$power"
      else s"^($power)"
    val u = if (power == 0) "" else s"($pre$name$exp)"
    s"($value)$u"
  }
}

object Unit {
  def apply[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer, F <: Prefix[F]](
    name_ : String,
    cf_ : Double,
    prefix_ : Prefix[_],
    value_ : Double,
    power_ : Int) = new Unit[U, Q, P, F] {
      def name = name_
      def cf = cf_
      def prefix = prefix_
      def value = value_
      def power = power_
      def unit = this.asInstanceOf[Unit[U, Q, P, F]]
    }
}

abstract class BaseUnit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q]] extends Unit[U, Q, church.Integer._1, UnitPrefix] {
  def power = 1
  def value = 1.0
  def unit = this.asInstanceOf[Unit[U, Q, church.Integer._1, UnitPrefix]]
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

  def f(v: Length) = v.to(Meter)
  def j(v: Meter) = v.value

  def g(v: Time#QPow[church.Integer._1]) = v.to(Second)

  val KiloMeter = Kilo*Meter
  val v1 = KiloMeter(3.0)
  val v2 = Kilo*Meter(2.0)
  val v3 = Meter(4000).to(Kilo*Meter)

  val vec1: Vector[Length] = Vector(Meter(1.0), Foot(2.0), Milli*Meter(3.0))

/*
  val sum1 = m1 + (f1)

  def g(v: UnitValue with QuantityOf[Time#Pow[church.Integer._1]]) = v.to(Second)

  type Area = Length#Pow[church.Integer._2]
  type SquareMeter = Meter#Pow[church.Integer._2]
  object SquareMeter extends SquareMeter
  type SquareFoot = Foot#Pow[church.Integer._2]
  object SquareFoot extends SquareFoot

  val sqm1 = SquareMeter(4.0)
  val sqf1 = sqm1.to(SquareFoot)

  def h(v: UnitValue with QuantityOf[Area]) = v.to(SquareMeter)



*/
} // test
