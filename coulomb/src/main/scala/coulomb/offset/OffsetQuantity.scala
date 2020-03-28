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

package coulomb.offset

import scala.language.implicitConversions

import coulomb._
import coulomb.unitops._
import coulomb.offset.unitops._
//import coulomb.offset.define.OffsetUnit

/**
 * A quantity with a unit associated with an additional offset
 * @tparam N The value type (Double, Int, etc)
 * @tparam U The unit type (Celsius, Second, etc)
 * @param value the raw (unitless) value stored by this offset quantity
 */
class OffsetQuantity[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
  override def toString = s"OffsetQuantity($value)"

  /** A string representation of this offset-quantity, using unit abbreviations */
  def show(implicit ustr: UnitString[U]): String = s"$value ${ustr.abbv}"

  /** A string representation of this offset-quantity, using full unit names */
  def showFull(implicit ustr: UnitString[U]): String = s"$value ${ustr.full}"

  /** A string representation of this offset-quantity's associated unit, using abbreviations */
  def showUnit(implicit ustr: UnitString[U]): String = ustr.abbv

  /** A String representation of this offset-quantity's associated unit, using full names */
  def showUnitFull(implicit ustr: UnitString[U]): String = ustr.full

  /**
   * Compute the difference of two offset-units
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand operand in the difference.
   * @return result of this-rhs, expressed in units (N,U).
   */
  def -[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit sub: OffsetUnitSub[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](sub.vsub(value, rhs.value))

  /**
   * Add a quantity to this offset-quantity
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the sum.
   * @return this+rhs, expressed in units (N,U).
   */
  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ua: UnitAdd[N, U, N2, U2]): OffsetQuantity[N, U] =
    new OffsetQuantity[N, U](ua.vadd(value, rhs.value))

  /**
   * Subtract a quantity from this offset-quantity
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the difference.
   * @return this-rhs, expressed in units (N,U).
   */
  def -[N2, U2](rhs: Quantity[N2, U2])(implicit us: UnitSub[N, U, N2, U2]): OffsetQuantity[N, U] =
    new OffsetQuantity[N, U](us.vsub(value, rhs.value))

  /**
   * Test if two offset-quantities are equal
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the comparison.
   * @return true if rhs is equal to this (after conversion to types N,U), false otherwise
   */
  def ===[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit uc: OffsetUnitOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) == 0

  /**
   * Test if two quantities are not equal
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the comparison.
   * @return true if rhs is not equal to this (after conversion to types N,U), false otherwise
   */
  def =!=[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit uc: OffsetUnitOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) != 0

  /**
   * Test if this quantity is less than another
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the comparison.
   * @return true if this is less than the rhs (after conversion to types N,U), false otherwise
   */
  def <[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit uc: OffsetUnitOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) < 0

  /**
   * Test if this quantity is less than or equal to another
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the comparison.
   * @return true if this is less than or equal to the rhs (after conversion to types N,U), false otherwise
   */
  def <=[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit uc: OffsetUnitOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) <= 0

  /**
   * Test if this quantity is greater than another
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the comparison.
   * @return true if this is greater than the rhs (after conversion to types N,U), false otherwise
   */
  def >[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit uc: OffsetUnitOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) > 0

  /**
   * Test if this quantity is greater than or equal to another
   * @tparam N2 the value type of the rhs
   * @tparam U2 the unit type of the rhs. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand of the comparison.
   * @return true if this is greater than or equal to the rhs (after conversion to types N,U), false otherwise
   */
  def >=[N2, U2](rhs: OffsetQuantity[N2, U2])(implicit uc: OffsetUnitOrd[N, U, N2, U2]): Boolean =
    uc.vcmp(value, rhs.value) >= 0

  /**
   * Obtain an offset-quantity that is equivalent to this but with different compatible units
   * @tparam U2 the new units to convert to.  Must be convertable to U, or a compile-time type
   * error will result.
   * @return the offset quantity equivalent to this, but with units U2
   */
  def toUnit[U2](implicit uc: OffsetUnitConverter[N, U, N, U2]): OffsetQuantity[N, U2] =
    new OffsetQuantity[N, U2](uc.vcnv(value))

  /**
   * Obtain an offset-quantity equivalent to this but with a different value type
   * @tparam N2 the value type to convert to.
   * @return an offset-quantity equivalent to this but with value type N2 and units U
   */
  def toValue[N2](implicit uc: OffsetUnitConverter[N, U, N2, U]): OffsetQuantity[N2, U] =
    new OffsetQuantity[N2, U](uc.vcnv(value))

  /**
   * Equivalent to this.toUnit[U2].toValue[N2]
   * @tparam N2 the value type to convert to.
   * @tparam U2 the new units to convert to.  Must be convertable to U, or a compile-time type
   * error will result.
   * @return an offset-quantity equivalent to this but with value type N2 and units U2
   */
  def to[N2, U2](implicit uc: OffsetUnitConverter[N, U, N2, U2]): OffsetQuantity[N2, U2] =
    new OffsetQuantity[N2, U2](uc.vcnv(value))
}

/** methods and implicits for offset quantities associated with a unit type */
object OffsetQuantity {

  /** Create a new temperature with value type N and unit type U */
  def apply[N, U](v: N) = new OffsetQuantity[N, U](v)

  /** A string representation of unit type U, using unit abbreviations */
  def showUnit[U](implicit ustr: UnitString[U]): String = ustr.abbv

  /** A string representation of unit type U, using full unit names */
  def showUnitFull[U](implicit ustr: UnitString[U]): String = ustr.full

  /** Obtain OffsetQuantity[N,U] from a Quantity[N,U] */
  def fromQuantity[N, U](q: Quantity[N, U]): OffsetQuantity[N, U] =
    new OffsetQuantity[N, U](q.value)

  /** Obtain a Quantity[N,U] from this OffsetQuantity[N,U] */
  def toQuantity[N, U](oq: OffsetQuantity[N, U]): Quantity[N, U] =
    new Quantity[N, U](oq.value)

  implicit def implicitlyConvertOffsetQuantity[N1, U1, N2, U2](oq: OffsetQuantity[N1, U1])(implicit
      cnv: OffsetUnitConverter[N1, U1, N2, U2]): OffsetQuantity[N2, U2] =
    new OffsetQuantity[N2, U2](cnv.vcnv(oq.value))
}
