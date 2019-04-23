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

/**
 * A numeric quantity with an associated unit
 * @tparam N The numeric type (Double, Int, etc)
 * @tparam U The unit type (Second, Byte, Byte %/ Second, etc)
 * @param value the raw (unitless) value stored by this quantity
 */
class Quantity[N, U](val value: N) extends AnyVal with Serializable {

  override def toString = s"Quantity($value)"

  /** A string representation of this quantity, using unit abbreviations */
  def show(implicit ustr: UnitString[U]): String = s"$value ${ustr.abbv}"

  /** A string representation of this quantity, using full unit names */
  def showFull(implicit ustr: UnitString[U]): String = s"$value ${ustr.full}"

  /** A string representation of this quantity's associated unit, using abbreviations */
  def showUnit(implicit ustr: UnitString[U]): String = ustr.abbv

  /** A String representation of this quantity's associated unit, using full names */
  def showUnitFull(implicit ustr: UnitString[U]): String = ustr.full

  /** Obtain a quantity with the same unit but negated numeric value */
  def unary_-() (implicit n: Numeric[N]): Quantity[N, U] =
    new Quantity[N, U](n.negate(value))

  /**
   * Compute the sum of two quantities
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return this + rhs, expressed in units (N, U).
   */
  def +[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](uc.n1.plus(value, uc.cv21(rhs.value)))

  /**
   * Compute the difference of two quantities
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the difference.
   * @return this - rhs, expressed in units (N, U).
   */
  def -[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](uc.n1.minus(value, uc.cv21(rhs.value)))

  /**
   * Compute the product of two quantities
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity.
   * @param rhs the right hand quantity in the product.
   * @return this * rhs, with numeric type N and unit type U*U2.
   */
  def *[N2, U2](rhs: Quantity[N2, U2])(implicit um: UnitMultiply[N, U, N2, U2]): Quantity[N, um.RT12] =
    new Quantity[N, um.RT12](um.n1.times(value, um.cn21(rhs.value)))

  /**
   * Divide this quantity by another
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity.
   * @param rhs the right hand quantity.
   * @return this / rhs, with numeric type N and unit type U/U2.
   */
  def /[N2, U2](rhs: Quantity[N2, U2])(implicit ud: UnitDivide[N, U, N2, U2]): Quantity[N, ud.RT12] =
    new Quantity[N, ud.RT12](ud.n1.div(value, ud.cn21(rhs.value)))

  /**
   * Raise this quantity to an integer power
   * @tparam P the literal type representing the integer exponent
   * @return this quantity raised to power P, with unit type U^P
   */
  def pow[P](implicit up: UnitPower[N, U, P]): Quantity[N, up.PowRT] =
    new Quantity[N, up.PowRT](up.n.pow(value, up.p))

  /**
   * Test if two quantities are equal
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return true if rhs is equal to this quantity (after conversion to types N,U), false otherwise
   */
  def ===[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) == 0

  /**
   * Test if two quantities are not equal
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return true if rhs is not equal to this quantity (after conversion to types N,U), false otherwise
   */
  def =!=[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) != 0

  /**
   * Test if this quantity is less than another
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return true if this quantity is less than the right quantity (after conversion to types N,U), false otherwise
   */
  def <[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) < 0

  /**
   * Test if this quantity is less than or equal to another
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return true if this quantity is less than or equal to the right quantity (after conversion to types N,U), false otherwise
   */
  def <=[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) <= 0

  /**
   * Test if this quantity is greater than another
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return true if this quantity is greater than the right quantity (after conversion to types N,U), false otherwise
   */
  def >[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) > 0

  /**
   * Test if this quantity is greater than or equal to another
   * @tparam N2 the numeric type of the rhs quantity
   * @tparam U2 the unit type of the rhs quantity. Must be convertable to U, or a compile-time type
   * error will result.
   * @param rhs the right hand quantity in the sum.
   * @return true if this quantity is greater than or equal to the right quantity (after conversion to types N,U), false otherwise
   */
  def >=[N2, U2](rhs: Quantity[N2, U2])(implicit uc: UnitConverter[N, U, N2, U2]): Boolean =
    uc.n1.compare(value, uc.cv21(rhs.value)) >= 0

  /**
   * Obtain a quantity that is equivalent to this but with different compatible units
   * @tparam U2 the new units to convert to.  Must be convertable to U, or a compile-time type
   * error will result.
   * @return a quantity equivalent to this, but with units U2
   */
  def toUnit[U2](implicit uc: UnitConverter[N, U, N, U2]): Quantity[N, U2] =
    new Quantity[N, U2](uc.cv12(value))

  /**
   * Obtain a quantity equivalent to this but with a different numeric type
   * @tparam N2 the numeric type to convert to.
   * @return a quantity equivalent to this but with numeric type N2 and units U
   */
  def toNumeric[N2](implicit uc: UnitConverter[N, U, N2, U]): Quantity[N2, U] =
    new Quantity[N2, U](uc.cv12(value))

  /**
   * Equivalent to this.toUnit[U2].toNumeric[N2]
   * @tparam N2 the numeric type to convert to.
   * @tparam U2 the new units to convert to.  Must be convertable to U, or a compile-time type
   * error will result.
   * @return a quantity equivalent to this but with numeric type N2 and units U2
   */
  def to[N2, U2](implicit uc: UnitConverter[N, U, N2, U2]): Quantity[N2, U2] =
    new Quantity[N2, U2](uc.cv12(value))
}

/** static methods for quantities with units */
object Quantity {
  /** Create a new quantity with numeric type N and unit type U */
  def apply[N, U](v: N) = new Quantity[N, U](v)

  /** Obtain the coefficient of conversion from unit U1 to U2. U1 and U2 must be
   * convertable units or a compile time error will result. */
  def coefficient[U1, U2](implicit cu: ConvertableUnits[U1, U2]): Rational = cu.coef

  /** A string representation of unit type U, using unit abbreviations */
  def showUnit[U](implicit ustr: UnitString[U]): String = ustr.abbv

  /** A string representation of unit type U, using full unit names */
  def showUnitFull[U](implicit ustr: UnitString[U]): String = ustr.full

  implicit def implicitlyConvertQuantity[N1, U1, N2, U2](q1: Quantity[N1, U1])(implicit
      uc: UnitConverter[N1, U1, N2, U2]): Quantity[N2, U2] =
    new Quantity[N2, U2](uc.cv12(q1.value))
}
