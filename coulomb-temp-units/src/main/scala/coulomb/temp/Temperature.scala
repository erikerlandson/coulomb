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
import coulomb.define.DerivedTemp

class Temperature[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
  override def toString = s"Temperature($value)"

  def show(implicit ustr: UnitString[U]): String = s"$value ${ustr.abbv}"

  def showFull(implicit ustr: UnitString[U]): String = s"$value ${ustr.full}"

  def showUnit(implicit ustr: UnitString[U]): String = ustr.abbv

  def showUnitFull(implicit ustr: UnitString[U]): String = ustr.full

  def -[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](uc.n1.minus(value, uc.cv21(rhs.value)))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](uc.n1.plus(value, uc.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](uc.n1.minus(value, uc.cv21(rhs.value)))

  def ===[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit uc: TempConverter[N, U, N, U2]): Temperature[N, U2] =
    new Temperature[N, U2](uc.cv12(value))

  def toNumeric[N2](implicit uc: TempConverter[N, U, N2, U]): Temperature[N2, U] =
    new Temperature[N2, U](uc.cv12(value))

  def to[N2, U2](implicit uc: TempConverter[N, U, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](uc.cv12(value))
}

object Temperature {
  def apply[N, U](v: N)(implicit t2k: DerivedTemp[U]) = new Temperature[N, U](v)

  def showUnit[U](implicit t2k: DerivedTemp[U], ustr: UnitString[U]): String = ustr.abbv

  def showUnitFull[U](implicit t2k: DerivedTemp[U], ustr: UnitString[U]): String = ustr.full

  def fromQuantity[N, U](q: Quantity[N, U])(implicit t2k: DerivedTemp[U]): Temperature[N, U] =
    new Temperature[N, U](q.value)

  def toQuantity[N, U](t: Temperature[N, U]): Quantity[N, U] = new Quantity[N, U](t.value)

  implicit def implicitlyConvertTemperature[N1, U1, N2, U2](t1: Temperature[N1, U1])(implicit
      uc: TempConverter[N1, U1, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](uc.cv12(t1.value))
}
