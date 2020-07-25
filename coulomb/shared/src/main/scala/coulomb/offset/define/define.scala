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

package coulomb.offset.define

import spire.math._

import coulomb.define._
import coulomb.UnitTypeName

/**
 * An offset unit extends derived unit, with an offset as well as a coefficient
 * {{{
 * import coulomb.define._
 * trait Fahrenheit
 * implicit val defineUnitFahrenheit = DerivedTemp[Fahrenheit](Rational(5, 9), Rational(45967, 100), name = "Fahrenheit", abbv = "°F")
 * }}}
 * @tparam U the unit type to define for this temperature scale
 */
class OffsetUnit[U, D](coef: Rational, val off: Rational, name: String, abbv: String) extends DerivedUnit[U, D](coef, name, abbv) {
  override def toString = s"OffsetUnit($coef, $off, $name, $abbv)"
}

object OffsetUnit {
  import coulomb.infra.CanonicalSig
  import shapeless.{ ::, HNil }

  /**
   * Obtain an instance of an offset unit scale.
   * @tparam U the unit type to define for this temperature scale
   * @param coef the coefficient for this temperature definition
   * @param off the offset for this definition
   * @param name the full name of the offset unit, e.g. "Celsius"
   * @param abbv an abbreviation for the offset unit, e.g. "C"
   */
  def apply[U, D](coef: Rational = Rational(1), off: Rational = Rational(0), name: String = "", abbv: String = "")(implicit
      ut: UnitTypeName[U]): OffsetUnit[U, D] = {
    require(coef > 0, "Unit coefficients must be strictly > 0")
    val n = if (name != "") name else ut.name.toLowerCase()
    val a = if (abbv != "") abbv else n.take(1)
    new OffsetUnit[U, D](coef, off, n, a)
  }

  /** lift a base unit to an offset unit */
  implicit def liftBUtoOU[U](implicit bu: BaseUnit[U]): OffsetUnit[U, U] =
    new OffsetUnit[U, U](coef = Rational(1), off = Rational(0), name = bu.name, abbv = bu.abbv)

  /** lift a defined unit to an offset unit */
  implicit def liftDUtoOU[U, D, B](implicit
      du: DerivedUnit[U, D],
      cs: CanonicalSig.Aux[U, (B, 1) :: HNil],
      bb: BaseUnit[B]): OffsetUnit[U, B] = {
    new OffsetUnit[U, B](coef = cs.coef, off = Rational(0), name = du.name, abbv = du.abbv)
  }
}
