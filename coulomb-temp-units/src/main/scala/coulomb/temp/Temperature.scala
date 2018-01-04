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

package coulomb.temp

import scala.language.implicitConversions

import coulomb._
import coulomb.unitops._

class Temperature[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
  override def toString = s"Temperature($value)"

  def show(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.abbv}"

  def showFull(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.full}"

  def showUnit(implicit uo: TempOps[N, U]): String = uo.ustr.abbv

  def showUnitFull(implicit uo: TempOps[N, U]): String = uo.ustr.full

  def -[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitConverterOps[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ubo.n1.plus(value, ubo.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitConverterOps[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def ===[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempConverterOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit ubo: TempConverterOps[N, U, N, U2]): Temperature[N, U2] =
    new Temperature[N, U2](ubo.cv12(value))

  def toNumeric[N2](implicit ubo: TempConverterOps[N, U, N2, U]): Temperature[N2, U] =
    new Temperature[N2, U](ubo.cn12(value))

  def to[N2, U2](implicit ubo: TempConverterOps[N, U, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](ubo.cv12(value))
}

object test {
import coulomb.si._
import spire.math._
import coulomb.define._

trait Yard
implicit val defineUnitYard = DerivedUnit[Yard, Meter](Rational(9144, 10000), abbv = "yd")

trait Foot
implicit val defineUnitFoot = DerivedUnit[Foot, Yard](Rational(1, 3), abbv = "ft")

trait Minute
implicit val duMinute = DerivedUnit[Minute, Second](Rational(60), abbv="min")

trait Celsius
implicit val defineUnitCelsius = DerivedTemp[Celsius](coef = 1, off = Rational(27315, 100), name = "Celsius", abbv = "C")

trait Fahrenheit
implicit val defineUnitFahrenheit = DerivedTemp[Fahrenheit](coef = Rational(5, 9), off = Rational(45967, 100), name = "Fahrenheit", abbv = "F")
}
