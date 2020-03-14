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

package coulomb.temp

import scala.language.implicitConversions

import coulomb._
import coulomb.unitops._
import coulomb.define.DerivedTemp

/**
 * A numeric temperature with an associated unit
 * @tparam N The numeric type (Double, Int, etc)
 * @tparam U The unit type (Second, Byte, Byte %/ Second, etc)
 * @param value the raw (unitless) value stored by this temperature
 */
class Temperature[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
  override def toString = s"Temperature($value)"

  /** A string representation of this temperature, using unit abbreviations */
  def show(implicit ustr: UnitString[U]): String = s"$value ${ustr.abbv}"

  /** A string representation of this temperature, using full unit names */
  def showFull(implicit ustr: UnitString[U]): String = s"$value ${ustr.full}"

  /** A string representation of this temperature's associated unit, using abbreviations */
  def showUnit(implicit ustr: UnitString[U]): String = ustr.abbv

  /** A String representation of this temperature's associated unit, using full names */
  def showUnitFull(implicit ustr: UnitString[U]): String = ustr.full

  /**
   * Compute the difference of two temperatures
   * @tparam N2 the numeric type of the rhs temperature
   * @tparam U2 the unit type of the rhs temperature. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand temperature in the difference.
   * @return a quantity this-rhs, expressed in units (N,U).
   */
  def -[N2, U2](rhs: Temperature[N2, U2])(implicit ts: TempSub[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ts.vsub(value, rhs.value))

  /**
   * Add a quantity to this temperature
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return temperature this+rhs, expressed in units (N,U).
   */
  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ua: UnitAdd[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ua.vadd(value, rhs.value))

  /**
   * Subtract a quantity from this temperature
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the difference.
   * @return temperature this-rhs, expressed in units (N,U).
   */
  def -[N2, U2](rhs: Quantity[N2, U2])(implicit us: UnitSub[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](us.vsub(value, rhs.value))

  /**
   * Test if two temperatures are equal
   * @tparam N2 the numeric type of the rhs temperature
   * @tparam U2 the unit type of the rhs temperature. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand temperature.
   * @return true if rhs is equal to this temperature (after conversion to types N,U), false otherwise
   */
  def ===[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) == 0

  /**
   * Test if two quantities are not equal
   * @tparam N2 the numeric type of the rhs temperature
   * @tparam U2 the unit type of the rhs temperature. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand temperature.
   * @return true if rhs is not equal to this temperature (after conversion to types N,U), false otherwise
   */
  def =!=[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) != 0

  /**
   * Test if this quantity is less than another
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity.
   * @return true if this quantity is less than the right quantity (after conversion to types N,U), false otherwise
   */
  def <[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) < 0

  /**
   * Test if this temperature is less than or equal to another
   * @tparam N2 the numeric type of the rhs temperature
   * @tparam U2 the unit type of the rhs temperature. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand temperature.
   * @return true if this temperature is less than or equal to the right temperature (after conversion to types N,U), false otherwise
   */
  def <=[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) <= 0

  /**
   * Test if this temperature is greater than another
   * @tparam N2 the numeric type of the rhs temperature
   * @tparam U2 the unit type of the rhs temperature. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand temperature.
   * @return true if this temperature is greater than the right temperature (after conversion to types N,U), false otherwise
   */
  def >[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) > 0

  /**
   * Test if this temperature is greater than or equal to another
   * @tparam N2 the numeric type of the rhs temperature
   * @tparam U2 the unit type of the rhs temperature. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand temperature.
   * @return true if this temperature is greater than or equal to the right temperature (after conversion to types N,U), false otherwise
   */
  def >=[N2, U2](rhs: Temperature[N2, U2])(implicit uc: TempOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) >= 0

  /**
   * Obtain a temperature that is equivalent to this but with different compatible units
   * @tparam U2 the new units to convert to.  Must be convertable to U, or a compile-time type
   * error will result.
   * @return a temperature equivalent to this, but with units U2
   */
  def toUnit[U2](implicit uc: TempConverter[N, U, N, U2]): Temperature[N, U2] =
    new Temperature[N, U2](uc.vcnv(value))

  /**
   * Obtain a temperature equivalent to this but with a different numeric type
   * @tparam N2 the numeric type to convert to.
   * @return a temperature equivalent to this but with numeric type N2 and units U
   */
  def toNumeric[N2](implicit uc: TempConverter[N, U, N2, U]): Temperature[N2, U] =
    new Temperature[N2, U](uc.vcnv(value))

  /**
   * Equivalent to this.toUnit[U2].toNumeric[N2]
   * @tparam N2 the numeric type to convert to.
   * @tparam U2 the new units to convert to.  Must be convertable to U, or a compile-time type
   * error will result.
   * @return a temperature equivalent to this but with numeric type N2 and units U2
   */
  def to[N2, U2](implicit uc: TempConverter[N, U, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](uc.vcnv(value))
}

/** methods and implicits for Temperature quantities associated with a temperature unit type */
object Temperature {
  /** Create a new temperature with numeric type N and unit type U */
  def apply[N, U](v: N)(implicit t2k: DerivedTemp[U]) = new Temperature[N, U](v)

  /** A string representation of unit type U, using unit abbreviations */
  def showUnit[U](implicit t2k: DerivedTemp[U], ustr: UnitString[U]): String = ustr.abbv

  /** A string representation of unit type U, using full unit names */
  def showUnitFull[U](implicit t2k: DerivedTemp[U], ustr: UnitString[U]): String = ustr.full

  /** Obtain a Temperature[N,U] from a Quantity[N,U] */
  def fromQuantity[N, U](q: Quantity[N, U])(implicit t2k: DerivedTemp[U]): Temperature[N, U] =
    new Temperature[N, U](q.value)

  /** Obtain a Quantity[N,U] from this Temperature[N,U] */
  def toQuantity[N, U](t: Temperature[N, U]): Quantity[N, U] = new Quantity[N, U](t.value)

  implicit def implicitlyConvertTemperature[N1, U1, N2, U2](t1: Temperature[N1, U1])(implicit
      uc: TempConverter[N1, U1, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](uc.vcnv(t1.value))
}
