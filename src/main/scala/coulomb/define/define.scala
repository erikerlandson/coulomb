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

package coulomb.define

import scala.language.implicitConversions
import scala.reflect.runtime.universe._

import spire.math._
import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import coulomb._

trait UnitDefinition {
  def name: String
  def abbv: String
}

class BaseUnit[U](val name: String, val abbv: String) extends UnitDefinition {
  override def toString = s"BaseUnit($name, $abbv)"
}
object BaseUnit {
  def apply[U](name: String = "", abbv: String = "")(implicit ut: TypeTag[U]): BaseUnit[U] = {
    val n = if (name != "") name else ut.tpe.typeSymbol.name.toString.toLowerCase
    val a = if (abbv != "") abbv else n.take(1)
    new BaseUnit[U](n, a)
  }
}

class DerivedUnit[U, D](val coef: Rational, val name: String, val abbv: String) extends UnitDefinition {
  override def toString = s"DerivedUnit($coef, $name, $abbv)"
}
object DerivedUnit {
  def apply[U, D](coef: Rational = Rational(1), name: String = "", abbv: String = "")(implicit ut: TypeTag[U]): DerivedUnit[U, D] = {
    require(coef > 0, "Unit coefficients must be strictly > 0")
    val n = if (name != "") name else ut.tpe.typeSymbol.name.toString.toLowerCase
    val a = if (abbv != "") abbv else n.take(1)
    new DerivedUnit[U, D](coef, n, a)
  }
}

object PrefixUnit {
  def apply[U](coef: Rational = Rational(1), name: String = "", abbv: String = "")(implicit ut: TypeTag[U]): DerivedUnit[U, Unitless] =
    DerivedUnit[U, Unitless](coef, name, abbv)
}
