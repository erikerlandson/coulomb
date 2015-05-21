package com.manyangled

import com.manyangled.church.{ Integer, IntegerValue }

object unit4s {

object infra {

trait Prefix[F <: Prefix[F]] extends Serializable {
  self =>
  def factor: Double
  def name: String
  final def *[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer](u: Unit[U, Q, RU, P, UnitPrefix])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = Unit[U, Q, RU, P, F](u.value)
  final override def toString = s"Prefix($name, $factor)"
}

case class PrefixMeta[F <: Prefix[F]](prefix: F)

class UnitPrefix extends Prefix[UnitPrefix] {
  def factor = 1.0
  def name = "[unit]"
}
object UnitPrefix extends UnitPrefix {
  implicit val unitPrefixMeta = PrefixMeta(new UnitPrefix)
}

trait Quantity[Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer] extends Serializable {
  type QType = Quantity[Q, RU, P]
  type QPow[K <: Integer] = Quantity[Q, RU, P#Mul[K]]
  def unit: Unit[_, Q, RU, P, _]
  def to[U <: BaseUnit[U, Q, RU], F <: Prefix[F]](u: Unit[U, Q, RU, P, F])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = {
    val v = unit.value * math.pow((unit.cf * unit.prefix.factor) / (u.cf * u.prefix.factor), u.power)
    Unit[U, Q, RU, P, F](v)
  }
  def +[U <: BaseUnit[U, Q, RU], F <: Prefix[F]](rhs: Unit[U, Q, RU, P, F])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = {
    val v = unit.value * math.pow((unit.cf * unit.prefix.factor) / (rhs.cf * rhs.prefix.factor), rhs.power)
    Unit[U, Q, RU, P, F](v + rhs.value)
  }
  def -[U <: BaseUnit[U, Q, RU], F <: Prefix[F]](rhs: Unit[U, Q, RU, P, F])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = {
    val v = unit.value * math.pow((unit.cf * unit.prefix.factor) / (rhs.cf * rhs.prefix.factor), rhs.power)
    Unit[U, Q, RU, P, F](v - rhs.value)
  }
  def *[U <: BaseUnit[U, Q, RU], P2 <: Integer, F <: Prefix[F]](rhs: Unit[U, Q, RU, P2, F])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P#Add[P2]], premeta: PrefixMeta[F]) = {
    val v = unit.value * math.pow((unit.cf * unit.prefix.factor) / (rhs.cf * rhs.prefix.factor), unit.power)
    Unit[U, Q, RU, P#Add[P2], F](v * rhs.value)
  }
  def /[U <: BaseUnit[U, Q, RU], P2 <: Integer, F <: Prefix[F]](rhs: Unit[U, Q, RU, P2, F])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P#Sub[P2]], premeta: PrefixMeta[F]) = {
    val v = unit.value * math.pow((unit.cf * unit.prefix.factor) / (rhs.cf * rhs.prefix.factor), unit.power)
    Unit[U, Q, RU, P#Sub[P2], F](v / rhs.value)
  }
}

trait BaseQuantity[Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU]] extends Quantity[Q, RU, Integer._1] {
}

abstract class Unit[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer, F <: Prefix[F]](implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) extends Quantity[Q, RU, P] {
  self =>
  type Type = Unit[U, Q, RU, P, F]
  type Pow[K <: Integer] = Unit[U, Q, RU, P#Mul[K], F]
  def name: String = bumeta.name
  def cf: Double = bumeta.cf
  def power: Int = ival.value
  def value: Double
  def prefix: Prefix[_] = premeta.prefix
  def apply(v: Double) = Unit[U, Q, RU, P, F](v)
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
  def apply[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer, F <: Prefix[F]](value_ : Double)(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = new Unit[U, Q, RU, P, F] {
      def value = value_
      def unit = this.asInstanceOf[Unit[U, Q, RU, P, F]]
    }
}

abstract class BaseUnit[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU]](implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[Integer._1], premeta: PrefixMeta[UnitPrefix]) extends Unit[U, Q, RU, Integer._1, UnitPrefix] {
  def value = 1.0
  def unit = this.asInstanceOf[Unit[U, Q, RU, Integer._1, UnitPrefix]]
}

case class BaseUnitMeta[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU]](name: String, abbv: String, cf: Double)

} // infra
} // unit4s

object ISQ {
  type Length = infra.Length#QType
  type Time = infra.Time#QType

  object infra {
    import com.manyangled.unit4s.infra._

    trait Length extends BaseQuantity[Length, Meter] {
    }

    trait Time extends BaseQuantity[Time, Second] {
    }

    class Meter(implicit bumeta: BaseUnitMeta[Meter, Length, Meter], ival: IntegerValue[Integer._1], premeta: PrefixMeta[UnitPrefix]) extends BaseUnit[Meter, Length, Meter]
    object Meter {
      implicit val meterMeta = BaseUnitMeta[Meter, Length, Meter]("meter", "m", 1.0)
    }

    class Second(implicit bumeta: BaseUnitMeta[Second, Time, Second], ival: IntegerValue[Integer._1], premeta: PrefixMeta[UnitPrefix]) extends BaseUnit[Second, Time, Second]
    object Second {
      implicit val secondMeta = BaseUnitMeta[Second, Time, Second]("second", "s", 1.0)
    }
  }

  class Kilo extends com.manyangled.unit4s.infra.Prefix[Kilo] {
    def factor = 1e3
    def name = "kilo"
  }
  object Kilo extends Kilo {
    implicit val kiloMeta = com.manyangled.unit4s.infra.PrefixMeta[Kilo](new Kilo)
  }

  class Milli extends com.manyangled.unit4s.infra.Prefix[Milli] {
    def factor = 1e-3
    def name = "milli"
  }
  object Milli extends Milli {
    implicit val milliMeta = com.manyangled.unit4s.infra.PrefixMeta[Milli](new Milli)
  }

}

object ISU {
  type Meter = ISQ.infra.Meter#Type
  object Meter extends ISQ.infra.Meter

  type Second = ISQ.infra.Second#Type
  object Second extends ISQ.infra.Second
}

// test definition of a new set of units
object customInfra {
  import com.manyangled.unit4s.infra._
  import com.manyangled.ISQ.infra._

  class Foot(implicit bumeta: BaseUnitMeta[Foot, Length, Meter], ival: IntegerValue[Integer._1], premeta: PrefixMeta[UnitPrefix]) extends BaseUnit[Foot, Length, Meter]
  object Foot {
    implicit val footMeta = BaseUnitMeta[Foot, Length, Meter]("foot", "ft", 0.3048)
  }

  class Minute(implicit bumeta: BaseUnitMeta[Minute, Time, Second], ival: IntegerValue[Integer._1], premeta: PrefixMeta[UnitPrefix]) extends BaseUnit[Minute, Time, Second]
  object Minute {
    implicit val minuteMeta = BaseUnitMeta[Minute, Time, Second]("minute", "min", 60.0)
  }
}

object custom {
  type Foot = customInfra.Foot#Type
  object Foot extends customInfra.Foot

  type Minute = customInfra.Minute#Type
  object Minute extends customInfra.Minute
}

object test {
  import com.manyangled.church.Integer
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

  def g(v: Time#QPow[Integer._1]) = v.to(Second)

  val KiloMeter = Kilo*Meter
  val v1 = KiloMeter(3.0)
  val v2 = Kilo*Meter(2.0)
  val v3 = Meter(4000).to(Kilo*Meter)

  val vec1: Vector[Length] = Vector(Meter(1.0), Foot(2.0), Milli*Meter(3.0))

/*
  val sum1 = m1 + (f1)

  def g(v: UnitValue with QuantityOf[Time#Pow[Integer._1]]) = v.to(Second)

  type Area = Length#Pow[Integer._2]
  type SquareMeter = Meter#Pow[Integer._2]
  object SquareMeter extends SquareMeter
  type SquareFoot = Foot#Pow[Integer._2]
  object SquareFoot extends SquareFoot

  val sqm1 = SquareMeter(4.0)
  val sqf1 = sqm1.to(SquareFoot)

  def h(v: UnitValue with QuantityOf[Area]) = v.to(SquareMeter)



*/
} // test
