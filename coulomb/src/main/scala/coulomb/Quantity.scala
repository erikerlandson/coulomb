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

object Quantity {
  def apply[N, U](v: N) = new Quantity[N, U](v)
}
