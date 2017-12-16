/*
Copyright 2017 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb

import scala.language.implicitConversions

import scala.reflect.runtime.universe._

import spire.math._
import spire.syntax._
import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import infra._
import define._
import unitops._

import test._

class Quantity[N, U](val value: N) extends AnyVal with Serializable {

  override def toString = s"Quantity($value)"

  def show(implicit uo: UnitOps[N, U]): String = s"$value ${uo.ustr.abbv}"

  def showFull(implicit uo: UnitOps[N, U]): String = s"$value ${uo.ustr.full}"

  def showUnit(implicit uo: UnitOps[N, U]): String = uo.ustr.abbv

  def showUnitFull(implicit uo: UnitOps[N, U]): String = uo.ustr.full

  def unary_-() (implicit uo: UnitOps[N, U]): Quantity[N, U] =
    new Quantity[N, U](uo.n.negate(value))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.plus(value, ubo.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def *[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, ubo.MulRT12] =
    new Quantity[N, ubo.MulRT12](ubo.n1.times(value, ubo.cn21(rhs.value)))

  def /[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, ubo.DivRT12] =
    new Quantity[N, ubo.DivRT12](ubo.n1.div(value, ubo.cn21(rhs.value)))

  def pow[P](implicit upo: UnitPowerOps[N, U, P], p: XIntValue[P]): Quantity[N, upo.PowRT] =
    new Quantity[N, upo.PowRT](upo.n.pow(value, p.value))

  def ===[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit ubo: UnitBinaryOps[N, U, N, U2]): Quantity[N, U2] =
    new Quantity[N, U2](ubo.cv12(value))

  def toNumeric[N2](implicit ubo: UnitBinaryOps[N, U, N2, U]): Quantity[N2, U] =
    new Quantity[N2, U](ubo.cn12(value))

  def to[N2, U2](implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N2, U2] =
    new Quantity[N2, U2](ubo.cv12(value))
}

object test {
trait Meter
implicit val buMeter = BaseUnit[Meter]()

trait Yard
implicit val defineUnitYard = DerivedUnit[Yard, Meter](Rational(9144, 10000), abbv = "yd")

trait Foot
implicit val defineUnitFoot = DerivedUnit[Foot, Yard](Rational(1, 3), abbv = "ft")

trait Second
implicit val buSecond = BaseUnit[Second]()

trait Minute
implicit val duMinute = DerivedUnit[Minute, Second](Rational(60), abbv="min")

trait Kilo
implicit val defineUnitKilo = PrefixUnit[Kilo](Rational(1000))

trait Mega
implicit val defineUnitMega = PrefixUnit[Mega](Rational(10).pow(6))

trait Kelvin
implicit val defineUnitKelvin = BaseUnit[Kelvin](name = "Kelvin", abbv = "K")

trait Celsius
implicit val defineUnitCelsius = DerivedTemp[Celsius](coef = 1, off = Rational(27315, 100), name = "Celsius", abbv = "C")

trait Fahrenheit
implicit val defineUnitFahrenheit = DerivedTemp[Fahrenheit](coef = Rational(5, 9), off = Rational(45967, 100), name = "Fahrenheit", abbv = "F")
}


class DerivedTemp[U](coef: Rational, val off: Rational, name: String, abbv: String) extends DerivedUnit[U, Kelvin](coef, name, abbv) {
  override def toString = s"DerivedTemp($coef, $off, $name, $abbv)"
}
object DerivedTemp {
  def apply[U](coef: Rational = Rational(1), off: Rational = Rational(0), name: String, abbv: String)(implicit
    ut: TypeTag[U]): DerivedTemp[U] = new DerivedTemp[U](coef, off, name, abbv)

  // A slight hack that is used by TempConverter to simplify its rules
  implicit def evidencek2k: DerivedTemp[Kelvin] = DerivedTemp[Kelvin](name = "Kelvin", abbv = "K")
}

trait TempConverter[N1, U1, N2, U2] {
  def apply(v: N1): N2
}
trait TempConverterDefaultPriority {
  // this default rule should work well everywhere but may be overridden for efficiency
  implicit def evidence[N1, U1, N2, U2](implicit
      t1: DerivedTemp[U1], t2: DerivedTemp[U2],
      n1: Numeric[N1], n2: Numeric[N2]): TempConverter[N1, U1, N2, U2] = {
    val coef = t1.coef / t2.coef
    new TempConverter[N1, U1, N2, U2] {
      def apply(v: N1): N2 = {
        n2.fromType[Rational](((n1.toType[Rational](v) + t1.off) * coef) - t2.off)
      }
    }
  }
}
object TempConverter extends TempConverterDefaultPriority {
  // override the default temp-converter generation here for specific cases
}

trait TempOps[N, U] {
  def n: Numeric[N]
  def ustr: UnitString[U]
}
object TempOps {
  implicit def evidence[N, U](implicit
      t2k: DerivedTemp[U],
      nn: Numeric[N],
      us: UnitString[U]): TempOps[N, U] =
    new TempOps[N, U] {
      val n = nn
      val ustr = us
    }
}

trait TempBinaryOps[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def n2: Numeric[N2]
  def cv12: TempConverter[N1, U1, N2, U2]
  def cv21: TempConverter[N2, U2, N1, U1]
  def cn12(x: N1): N2
  def cn21(x: N2): N1
}

object TempBinaryOps {
  type Aux[N1, U1, N2, U2] = TempBinaryOps[N1, U1, N2, U2] {
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      cvv12: TempConverter[N1, U1, N2, U2],
      cvv21: TempConverter[N2, U2, N1, U1]): Aux[N1, U1, N2, U2] =
    new TempBinaryOps[N1, U1, N2, U2] {
      val n1 = nn1
      val n2 = nn2
      val cv12 = cvv12
      val cv21 = cvv21
      def cn12(x: N1): N2 = nn1.toType[N2](x)
      def cn21(x: N2): N1 = nn2.toType[N1](x)
    }
}

class Temperature[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
  override def toString = s"Temperature($value)"

  def show(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.abbv}"

  def showFull(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.full}"

  def showUnit(implicit uo: TempOps[N, U]): String = uo.ustr.abbv

  def showUnitFull(implicit uo: TempOps[N, U]): String = uo.ustr.full

  def -[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ubo.n1.plus(value, ubo.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def ===[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit ubo: TempBinaryOps[N, U, N, U2]): Temperature[N, U2] =
    new Temperature[N, U2](ubo.cv12(value))

  def toNumeric[N2](implicit ubo: TempBinaryOps[N, U, N2, U]): Temperature[N2, U] =
    new Temperature[N2, U](ubo.cn12(value))

  def to[N2, U2](implicit ubo: TempBinaryOps[N, U, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](ubo.cv12(value))
}
