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

import spire.math.Rational

import unitops._

class Quantity[N, U](val value: N) extends AnyVal with Serializable {

  override def toString = s"Quantity($value)"

  def show(implicit ustr: UnitString[U]): String = s"$value ${ustr.abbv}"

  def showFull(implicit ustr: UnitString[U]): String = s"$value ${ustr.full}"

  def showUnit(implicit ustr: UnitString[U]): String = ustr.abbv

  def showUnitFull(implicit ustr: UnitString[U]): String = ustr.full

  def unary_-() (implicit n: Numeric[N]): Quantity[N, U] =
    new Quantity[N, U](n.negate(value))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](uc.n1.plus(value, uc.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](uc.n1.minus(value, uc.cv21(rhs.value)))

  def *[N2, U2](rhs: Quantity[N2, U2])(implicit um: UnitMultiply[N, U, N2, U2]): Quantity[N, um.RT12] =
    new Quantity[N, um.RT12](um.n1.times(value, um.cn21(rhs.value)))

  def /[N2, U2](rhs: Quantity[N2, U2])(implicit ud: UnitDivide[N, U, N2, U2]): Quantity[N, ud.RT12] =
    new Quantity[N, ud.RT12](ud.n1.div(value, ud.cn21(rhs.value)))

  def pow[P](implicit up: UnitPower[N, U, P]): Quantity[N, up.PowRT] =
    new Quantity[N, up.PowRT](up.n.pow(value, up.p))

  def ===[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit uc: UnitConverter[N, U, N, U2]): Quantity[N, U2] =
    new Quantity[N, U2](uc.cv12(value))

  def toNumeric[N2](implicit uc: UnitConverter[N, U, N2, U]): Quantity[N2, U] =
    new Quantity[N2, U](uc.cv12(value))

  def to[N2, U2](implicit uc: UnitConverter[N, U, N2, U2]): Quantity[N2, U2] =
    new Quantity[N2, U2](uc.cv12(value))
}

object Quantity {
  def apply[N, U](v: N) = new Quantity[N, U](v)

  def coefficient[U1, U2](implicit cu: infra.ConvertableUnits[U1, U2]): Rational = cu.coef

  def showUnit[U](implicit ustr: UnitString[U]): String = ustr.abbv

  def showUnitFull[U](implicit ustr: UnitString[U]): String = ustr.full

  implicit def implicitlyConvertQuantity[N1, U1, N2, U2](q1: Quantity[N1, U1])(implicit
      uc: UnitConverter[N1, U1, N2, U2]): Quantity[N2, U2] =
    new Quantity[N2, U2](uc.cv12(q1.value))
}
