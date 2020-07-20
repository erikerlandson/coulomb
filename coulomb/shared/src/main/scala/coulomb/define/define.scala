/*
Copyright 2017-2020 Erik Erlandson

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

import spire.math._
import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import coulomb._

/** Methods and values common to all unit and temperature definitions */
trait UnitDefinition {
  /** the full name of a unit, e.g. "meter" */
  def name: String

  /** the abbreviation of a unit, e.g. "m" for "meter" */
  def abbv: String
}

/**
 * Defines a type U as a base unit: A base unit represents the "reference" unit for an
 * abstract quantity; for example Meter is the reference unit for the abstract quantity "length".
 * Each abstract quantity (length, time, mass, etc) has a unique base unit. Other units are
 * defined using [[DerivedUnit]]. A BaseUnit instance is intended to be defined as an implicit value.
 * {{{
 * import coulomb.define._
 * trait Meter
 * implicit val defineUnitMeter = BaseUnit[Meter](name = "meter", abbv = "m")
 * }}}
 * @tparam U the type representing base unit
 */
class BaseUnit[U](val name: String, val abbv: String) extends UnitDefinition {
  override def toString = s"BaseUnit($name, $abbv)"
}
/** methods, constructors and other static definitions for BaseUnit */
object BaseUnit {
  /**
   * Obtain a new BaseUnit instance.
   * @tparam U the type to define as a base unit.
   * @param name the full name of the unit, e.g. "meter"
   * @param abbv an abbreviation for the unit, e.g. "m"
   */
  def apply[U](name: String = "", abbv: String = "")(implicit ut: UnitTypeName[U]): BaseUnit[U] = {
    val n = if (name != "") name else ut.name.toLowerCase()
    val a = if (abbv != "") abbv else n.take(1)
    new BaseUnit[U](n, a)
  }
}

/**
 * Defines a type U as a derived unit represented by a coefficient times unit expression D
 * {{{
 * import coulomb.define._
 * import coulomb.si._
 * trait Liter
 * implicit val defineUnitLiter = DerivedUnit[Liter, Meter %^ 3](1, "liter", "l")
 * }}}
 * @tparam U the type representing this derived unit
 * @tparam D the type representing another unit, or unit expression, that U is defined in terms of
 */
class DerivedUnit[U, D](val coef: Rational, val name: String, val abbv: String) extends UnitDefinition {
  override def toString = s"DerivedUnit($coef, $name, $abbv)"
}
/** methods, constructors and other static definitions for DerivedUnit */
object DerivedUnit {
  /**
   * Obtain a new BaseUnit instance.
   * @tparam U the type representing this derived unit
   * @tparam D the type representing another unit, or unit expression, that U is defined in terms of
   * @param coef the coefficient for this unit definition
   * @param name the full name of the unit, e.g. "liter"
   * @param abbv an abbreviation for the unit, e.g. "l"
   */
  def apply[U, D](coef: Rational = Rational(1), name: String = "", abbv: String = "")(implicit
      ut: UnitTypeName[U]): DerivedUnit[U, D] = {
    require(coef > 0, "Unit coefficients must be strictly > 0")
    val n = if (name != "") name else ut.name.toLowerCase()
    val a = if (abbv != "") abbv else n.take(1)
    new DerivedUnit[U, D](coef, n, a)
  }
}

/** methods, constructors and other static definitions for defining prefix units */
object PrefixUnit {
  /**
   * Define a prefix unit. Prefix units are shorthand for a derived unit of [[Unitless]].
   * @tparam U the unit type to define for this prefix unit
   * @param coef the coefficient for this unit definition
   * @param name the full name of the unit, e.g. "kilo"
   * @param abbv an abbreviation for the unit, e.g. "k"
   */
  def apply[U](coef: Rational = Rational(1), name: String = "", abbv: String = "")(implicit
      ut: UnitTypeName[U]): DerivedUnit[U, Unitless] =
    DerivedUnit[U, Unitless](coef, name, abbv)
}
