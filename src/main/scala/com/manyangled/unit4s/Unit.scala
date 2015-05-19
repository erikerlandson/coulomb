package com.manyangled

object unit4s {

import scala.language.higherKinds

import com.manyangled.church
import com.manyangled.util.{Constructable,ConstructableFromDouble}

import infra._

trait QuantityOf[UMP <: Quantity[_,_]]
trait UnitOf[UP <: Unit[_,_,_]]

trait UnitValue {
  def value: Double
  def cfRef: Double
  def prefix: Prefix
}

object UnitValue {
  implicit class EnrichQuantityOf[Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UnitValue with QuantityOf[Quantity[Q, P]]) {
    def to[U2 <: BaseUnit[U2, Q]](u: Unit[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UVal[U2, Q, P](uv.value * uv.prefix.factor * math.pow(cfU/cfU2, p))
    }
    def to[U2 <: BaseUnit[U2, Q]](u: PrefixUnit[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new PrefixUVal[U2, Q, P](uv.value * uv.prefix.factor * math.pow(cfU/cfU2, p) / u.prefix.factor, u.prefix)
    }
  }
  implicit class EnrichUnitOf[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UnitValue with UnitOf[Unit[U, Q, P]]) {
    def to[U2 <: BaseUnit[U2, Q]](u: Unit[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UVal[U2, Q, P](uv.value * uv.prefix.factor * math.pow(cfU/cfU2, p))
    }
    def to[U2 <: BaseUnit[U2, Q]](u: PrefixUnit[U2, Q, P])(implicit
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = uv.cfRef
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new PrefixUVal[U2, Q, P](uv.value * uv.prefix.factor * math.pow(cfU/cfU2, p) / u.prefix.factor, u.prefix)
    }
  }
}

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
  def factor: Double
  def name: String
  final def ==(that: Prefix) = (this.factor == that.factor) && (this.name == that.name)
  final def *[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](u: Unit[U, Q, P])(implicit x: Convertable[U, Q#RefUnit]): PrefixUnit[U, Q, P] = {
    new PrefixUnit[U, Q, P](this, u)
  }
  final def *[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UVal[U, Q, P])(implicit c: Convertable[U, Q#RefUnit]): PrefixUVal[U, Q, P] = {
    require(uv.prefix == UnitPrefix, s"UnitValue already has a prefix: (${uv.prefix})")
    new PrefixUVal[U, Q, P](uv.value, this)
  }
  final override def toString = name
}
object UnitPrefix extends Prefix {
  def factor = 1.0
  def name = ""
}

trait Quantity[Q <: BaseQuantity[Q], P <: church.Integer] {
  type Type = Quantity[Q, P]
  type Pow[K <: church.Integer] = Quantity[Q, P#Mul[K]]
}

trait BaseQuantity[Q <: BaseQuantity[Q]] extends Quantity[Q, church.Integer._1] {
  type RefUnit <: BaseUnit[RefUnit, Q]
}

trait Convertable[U, R] {
  def cf: Double
}

object Convertable {
  def apply[U, R](blk: => Double) = new Convertable[U, R] {
    def cf = blk
  }
}

class Unit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable] {
  type Type = Unit[U, Q, P]
  type Pow[K <: church.Integer] = Unit[U, Q, P#Mul[K]]
  final def apply(v: Double)(implicit x: Convertable[U, Q#RefUnit]) = new UVal[U, Q, P](v)
}

class BaseUnit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q]] extends Unit[U, Q, church.Integer._1]

class PrefixUnit[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](pre: Prefix, u: Unit[U, Q, P])(implicit x: Convertable[U, Q#RefUnit]) {
  def prefix = pre
  def apply(v: Double) = new PrefixUVal[U, Q, P](v, pre)
}

class UVal[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: Double)(implicit cu: Convertable[U, Q#RefUnit]) extends UnitValue with UnitOf[Unit[U, Q, P]] with QuantityOf[Quantity[Q, P]] {
  def value = v
  def cfRef = cu.cf
  def prefix = UnitPrefix
}

class PrefixUVal[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: Double, pre: Prefix)(implicit cu: Convertable[U, Q#RefUnit]) extends UnitValue with UnitOf[Unit[U, Q, P]] with QuantityOf[Quantity[Q, P]] {
  def value = v
  def cfRef = cu.cf
  def prefix = pre
}

object UVal {
  import scala.language.implicitConversions

  implicit class EnrichUVal[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](uv: UVal[U, Q, P]) {
    def to[U2 <: BaseUnit[U2, Q]](u: Unit[U2, Q, P])(implicit
      cu: Convertable[U, Q#RefUnit],
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = cu.cf
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new UVal[U2, Q, P](uv.value * uv.prefix.factor * math.pow(cfU/cfU2, p))
    }
    def to[U2 <: BaseUnit[U2, Q]](u: PrefixUnit[U2, Q, P])(implicit
      cu: Convertable[U, Q#RefUnit],
      cu2: Convertable[U2, Q#RefUnit]) = {
      val cfU = cu.cf
      val cfU2 = cu2.cf
      val p = church.Integer.value[P]
      new PrefixUVal[U2, Q, P](uv.value * uv.prefix.factor * math.pow(cfU/cfU2, p) / u.prefix.factor, u.prefix)
    }
  }
}

} // infra
} // unit4s

object ISQ {
  type Length = infra.Length#Type
  type Time = infra.Time#Type

  object infra {
    import com.manyangled.unit4s.infra._

    trait Length extends BaseQuantity[Length] {
      type RefUnit = Meter
    }

    trait Time extends BaseQuantity[Time] {
      type RefUnit = Second
    }

    class Meter extends BaseUnit[Meter, Length]
    object Meter {
      implicit val convertMeter = Convertable[Meter, Meter] { 1.0 }
    }

    class Second extends BaseUnit[Second, Time]
    object Second {
      implicit val convertSecond = Convertable[Second, Second] { 1.0 }
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

    class Foot extends BaseUnit[Foot, Length]
    object Foot {
      implicit val convertFoot = Convertable[Foot, Meter] { 0.3048 }
    }

    class Minute extends BaseUnit[Minute, Time]
    object Minute {
      implicit val convertMinute = Convertable[Minute, Second] { 60.0 }
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

  object subtest {
    import com.manyangled.unit4s.infra._
    import com.manyangled.util.Constructable
    def k[U <: BaseUnit[U, Q], Q <: BaseQuantity[Q], P <: church.Integer :Constructable](v: UnitValue with UnitOf[Unit[U, Q, P]]) = {
      val p = church.Integer.value[P]
      println(s"v= ${v.value}  p= $p")
      v.value
    }
  }
} // test
