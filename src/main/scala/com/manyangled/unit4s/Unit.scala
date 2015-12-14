package com.manyangled

import com.manyangled.church.{ Integer, IntegerValue }

object unit4s {
  import scala.language.implicitConversions

  sealed class =!=[A,B]

  trait LowerPriorityImplicits {
    implicit def equal[A]: =!=[A, A] = sys.error("should not be called")
  }
  object =!= extends LowerPriorityImplicits {
    implicit def nequal[A,B](implicit same: A =:= B = null): =!=[A,B] =
      if (same != null) sys.error("should not be called explicitly with same type")
      else new =!=[A,B]
  }

  trait Unit[U <: Unit[U]] {
    type RU <: Unit[_]
    final def apply(value: Double)(implicit spec: UnitSpec[U]): UnitValue1[U, Integer._1] = UnitValue1[U, Integer._1](value)
    final def *(value: Double)(implicit spec: UnitSpec[U]): UnitValue1[U, Integer._1] = UnitValue1[U, Integer._1](value)
  }

  object Unit {
    def factor[U1 <: Unit[U1], U2 <: Unit[U2]](implicit spec1: UnitSpec[U1], spec2: UnitSpec[U2], eq: U1#RU =:= U2#RU) = spec1.cfr / spec2.cfr

    def toString(value: Double, units: Seq[(String, Int)]) = {
      val unitstr = units.map { case (name, exp) =>
        if (exp == 1) s"$name" else s"($name)^($exp)"
      }.mkString(" ")
      s"UnitValue${units.length}($value $unitstr)"
    }
  }

  trait UnitSpec[U <: Unit[U]] {
    def cfr: Double
    def name: String
    def plural = name + "s"
  }

  trait Kilo[U <: Unit[U]] extends Unit[Kilo[U]] {
    type RU = U#RU
  }
  object Kilo {
    val (pfval, pfname) = (1000.0, "kilo")
    implicit def factory[U <: Unit[U]](implicit spec: UnitSpec[U]): UnitSpec[Kilo[U]] = new UnitSpec[Kilo[U]] {
      val cfr = pfval * spec.cfr
      val name = pfname + spec.name
      override val plural = pfname + spec.plural
    }
  }

  trait Meter extends Unit[Meter] {
    type RU = Meter
  }
  object Meter extends Meter {
    implicit object spec extends UnitSpec[Meter] {
      val cfr = 1.0
      val name = "meter"
    }
  }

  trait Foot extends Unit[Foot] {
    type RU = Meter
  }
  object Foot extends Foot {
    implicit object spec extends UnitSpec[Foot] {
      val cfr = 0.3048
      val name = "foot"
      override val plural = "feet"
    }
  }

  trait Yard extends Unit[Yard] {
    type RU = Meter
  }
  object Yard extends Yard {
    implicit object spec extends UnitSpec[Yard] {
      val cfr = 0.9144
      val name = "yard"
    }
  }

  trait Second extends Unit[Second] {
    type RU = Second
  }
  object Second extends Second {
    implicit object spec extends UnitSpec[Second] {
      val cfr = 1.0
      val name = "second"
    }
  }

  trait Minute extends Unit[Minute] {
    type RU = Second
  }
  object Minute extends Minute {
    implicit object spec extends UnitSpec[Minute] {
      val cfr = 60.0
      val name = "minute"
    }
  }

  case class UnitValue1[U1 <: Unit[U1], P1 <: Integer](value: Double)(implicit spec1: UnitSpec[U1], p1: IntegerValue[P1]) {
    def *(v: Double) = UnitValue1[U1, P1](v * value)
    override def toString = Unit.toString(value, Seq((spec1.name, Integer.value[P1])))
  }

  object UnitValue1 {
    implicit def fromUnit[U1 <: Unit[U1]](unit: U1)(implicit spec: UnitSpec[U1]): UnitValue1[U1, Integer._1] = UnitValue1[U1, Integer._1](1.0)

    implicit def commute1[U1 <: Unit[U1], P1 <: Integer, Ua <: Unit[Ua]](uv: UnitValue1[U1, P1])(implicit
      spec1: UnitSpec[U1],
      speca: UnitSpec[Ua],
      eq: U1#RU =:= Ua#RU,
      v1: IntegerValue[P1]): UnitValue1[Ua, P1] = {
      val f = math.pow(Unit.factor[U1, Ua], Integer.value[P1])
      UnitValue1[Ua, P1](uv.value * f)
    }
  }

  case class UnitValue2[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer](value: Double)(implicit
    spec1: UnitSpec[U1], p1: IntegerValue[P1],
    spec2: UnitSpec[U2], p2: IntegerValue[P2],
    ne12: U1#RU =!= U2#RU) {
    def *(v: Double) = UnitValue2[U1, P1, U2, P2](v * value)
    override def toString = Unit.toString(value, Seq((spec1.name, Integer.value[P1]), (spec2.name, Integer.value[P2])))
  }

  object UnitValue2 {
    implicit def commute12[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer, Ua <: Unit[Ua], Ub <: Unit[Ub]](uv: UnitValue2[U1, P1, U2, P2])(implicit
      spec1: UnitSpec[U1], spec2: UnitSpec[U2],
      speca: UnitSpec[Ua], specb: UnitSpec[Ub],
      eq1a: U1#RU =:= Ua#RU, eq2b: U2#RU =:= Ub#RU,
      v1: IntegerValue[P1], v2: IntegerValue[P2]): UnitValue2[Ua, P1, Ub, P2] = {
      val f =
        math.pow(Unit.factor[U1, Ua], Integer.value[P1]) *
        math.pow(Unit.factor[U2, Ub], Integer.value[P2])
      UnitValue2[Ua, P1, Ub, P2](uv.value * f)
    }

    implicit def commute21[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer, Ua <: Unit[Ua], Ub <: Unit[Ub]](uv: UnitValue2[U2, P2, U1, P1])(implicit
      spec1: UnitSpec[U1], spec2: UnitSpec[U2],
      speca: UnitSpec[Ua], specb: UnitSpec[Ub],
      eq1a: U1#RU =:= Ua#RU, eq2b: U2#RU =:= Ub#RU,
      v1: IntegerValue[P1], v2: IntegerValue[P2]): UnitValue2[Ua, P1, Ub, P2] = {
      val f =
        math.pow(Unit.factor[U1, Ua], Integer.value[P1]) *
        math.pow(Unit.factor[U2, Ub], Integer.value[P2])
      UnitValue2[Ua, P1, Ub, P2](uv.value * f)
    }
    
  }

  object test {
    def f1(uv: UnitValue1[Foot, Integer._1]) = uv.value
    def f2(uv: UnitValue2[Foot, Integer._1, Second, Integer._neg1]) = uv.value
  }
}

/*

object unit4s {

object infra {

trait Prefix[F <: Prefix[F]] extends Serializable {
  def factor: Double
  def name: String
  def abbv: String

  final def *[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer](u: Unit[U, Q, RU, P, UnitPrefix])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = Unit[U, Q, RU, P, F](u.value)
  final override def toString = s"Prefix($name, $factor)"
}

case class PrefixMeta[F <: Prefix[F]](prefix: F)

class UnitPrefix extends Prefix[UnitPrefix] {
  def factor = 1.0
  def name = "[unit]"
  def abbv = "[unit]"
}
object UnitPrefix extends UnitPrefix {
  implicit val unitPrefixMeta = PrefixMeta(new UnitPrefix)
}

trait Quantity[Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer] extends Serializable {
  type QType = Quantity[Q, RU, P]
  type QPow[K <: Integer] = Quantity[Q, RU, P#Mul[K]]
  def unit: Unit[_, Q, RU, P, _]
  def as[U <: BaseUnit[U, Q, RU], F <: Prefix[F]](u: Unit[U, Q, RU, P, F])(implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) = {
    val v = unit.value * math.pow((unit.cf * unit.prefix.factor) / (u.cf * u.prefix.factor), u.power)
    Unit[U, Q, RU, P, F](v)
  }
  def valueAs[U <: BaseUnit[U, Q, RU], F <: Prefix[F]](u: Unit[U, Q, RU, P, F]) = {
    unit.value * math.pow((unit.cf * unit.prefix.factor) / (u.cf * u.prefix.factor), u.power)
  }
  def add(rhs: Quantity[Q, RU, P])(implicit bumeta: BaseUnitMeta[RU, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[UnitPrefix]) = {
    val vL = unit.value * unit.prefix.factor * math.pow(unit.cf, unit.power)
    val vR = rhs.unit.value * rhs.unit.prefix.factor * math.pow(rhs.unit.cf, rhs.unit.power)
    Unit[RU, Q, RU, P, UnitPrefix](vL + vR)
  }
  def sub(rhs: Quantity[Q, RU, P])(implicit bumeta: BaseUnitMeta[RU, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[UnitPrefix]) = {
    val vL = unit.value * unit.prefix.factor * math.pow(unit.cf, unit.power)
    val vR = rhs.unit.value * rhs.unit.prefix.factor * math.pow(rhs.unit.cf, rhs.unit.power)
    Unit[RU, Q, RU, P, UnitPrefix](vL - vR)
  }
  def mul[P2 <: Integer](rhs: Quantity[Q, RU, P2])(implicit bumeta: BaseUnitMeta[RU, Q, RU], ival: IntegerValue[P#Add[P2]], premeta: PrefixMeta[UnitPrefix]) = {
    val vL = unit.value * unit.prefix.factor * math.pow(unit.cf, unit.power)
    val vR = rhs.unit.value * rhs.unit.prefix.factor * math.pow(rhs.unit.cf, rhs.unit.power)
    Unit[RU, Q, RU, P#Add[P2], UnitPrefix](vL * vR)
  }
  def div[P2 <: Integer](rhs: Quantity[Q, RU, P2])(implicit bumeta: BaseUnitMeta[RU, Q, RU], ival: IntegerValue[P#Sub[P2]], premeta: PrefixMeta[UnitPrefix]) = {
    val vL = unit.value * unit.prefix.factor * math.pow(unit.cf, unit.power)
    val vR = rhs.unit.value * rhs.unit.prefix.factor * math.pow(rhs.unit.cf, rhs.unit.power)
    Unit[RU, Q, RU, P#Sub[P2], UnitPrefix](vL / vR)
  }
  def inv(implicit bumeta: BaseUnitMeta[RU, Q, RU], ival: IntegerValue[P#Neg], premeta: PrefixMeta[UnitPrefix]) = {
    val v = unit.value * unit.prefix.factor * math.pow(unit.cf, unit.power)
    Unit[RU, Q, RU, P#Neg, UnitPrefix](1.0 / v)
  }
  def pow[K <: Integer](implicit bumeta: BaseUnitMeta[RU, Q, RU], ival: IntegerValue[P#Mul[K]], kval: IntegerValue[K], premeta: PrefixMeta[UnitPrefix]) = {
    val v = unit.value * unit.prefix.factor * math.pow(unit.cf, unit.power)
    Unit[RU, Q, RU, P#Mul[K], UnitPrefix](math.pow(v, kval.value))
  }
}

trait BaseQuantity[Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU]] extends Quantity[Q, RU, Integer._1]

abstract class Unit[U <: BaseUnit[U, Q, RU], Q <: BaseQuantity[Q, RU], RU <: BaseUnit[RU, Q, RU], P <: Integer, F <: Prefix[F]](implicit bumeta: BaseUnitMeta[U, Q, RU], ival: IntegerValue[P], premeta: PrefixMeta[F]) extends Quantity[Q, RU, P] {
  self =>
  type Type = Unit[U, Q, RU, P, F]
  type Pow[K <: Integer] = Unit[U, Q, RU, P#Mul[K], F]

  def value: Double

  def name = bumeta.name
  def abbv = bumeta.abbv
  def cf = bumeta.cf
  def power = ival.value
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

  def uAdd[U2 <: BaseUnit[U2, Q, RU], F2 <: Prefix[F2]](rhs: Unit[U2, Q, RU, P, F2]) = {
    val vR = rhs.value * rhs.prefix.factor * math.pow(rhs.cf / this.cf, rhs.power) / this.prefix.factor
    Unit[U, Q, RU, P, F](this.value + vR)
  }
  def uSub[U2 <: BaseUnit[U2, Q, RU], F2 <: Prefix[F2]](rhs: Unit[U2, Q, RU, P, F2]) = {
    val vR = rhs.value * rhs.prefix.factor * math.pow(rhs.cf / this.cf, rhs.power) / this.prefix.factor
    Unit[U, Q, RU, P, F](this.value - vR)
  }
  def uMul[U2 <: BaseUnit[U2, Q, RU], P2 <: Integer, F2 <: Prefix[F2]](rhs: Unit[U2, Q, RU, P2, F2])(implicit ival: IntegerValue[P#Add[P2]]) = {
    val vR = rhs.value * rhs.prefix.factor * math.pow(rhs.cf / this.cf, rhs.power) / this.prefix.factor
    Unit[U, Q, RU, P#Add[P2], F](this.value * vR)
  }
  def uDiv[U2 <: BaseUnit[U2, Q, RU], P2 <: Integer, F2 <: Prefix[F2]](rhs: Unit[U2, Q, RU, P2, F2])(implicit ival: IntegerValue[P#Sub[P2]]) = {
    val vR = rhs.value * rhs.prefix.factor * math.pow(rhs.cf / this.cf, rhs.power) / this.prefix.factor
    Unit[U, Q, RU, P#Sub[P2], F](this.value / vR)
  }
  def uInv(implicit ival: IntegerValue[P#Neg]) = {
    Unit[U, Q, RU, P#Neg, F](1.0 / this.value)
  }
  def uPow[K <: Integer](implicit ival: IntegerValue[P#Mul[K]], kval: IntegerValue[K]) = {
    Unit[U, Q, RU, P#Mul[K], F](math.pow(this.value, kval.value))
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

    trait Length extends BaseQuantity[Length, Meter]

    trait Time extends BaseQuantity[Time, Second]

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
    def abbv = "k"
  }
  object Kilo extends Kilo {
    implicit val kiloMeta = com.manyangled.unit4s.infra.PrefixMeta[Kilo](new Kilo)
  }

  class Milli extends com.manyangled.unit4s.infra.Prefix[Milli] {
    def factor = 1e-3
    def name = "milli"
    def abbv = "m"
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

  val f2 = m1.as(Foot)

  def f(v: Length) = v.as(Meter)
  def j(v: Meter) = v.value

  def g(v: Time#QPow[Integer._1]) = v.as(Second)

  val KiloMeter = Kilo*Meter
  val v1 = KiloMeter(3.0)
  val v2 = Kilo*Meter(2.0)
  val v3 = Meter(4000).as(Kilo*Meter)

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

*/
