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

package com.manyangled.coulomb

import scala.language.implicitConversions
import scala.language.experimental.macros

/**
 * Define a derived unit of temperature. By definition, all derived temperatures are defined in
 * terms of [[SIBaseUnits.Kelvin]].
 * The three common temperatures Kelvin, Celsius and Fahrenheit are already defined in `coulomb`
 * and so use cases for new derived temperatures are expected to be rare.
 * {{{
 * // example defining Fahrenheit temperature unit (already defined by coulomb)
 * trait Fahrenheit extends DerivedTemperature
 * object Fahrenheit extends TempUnitCompanion[Fahrenheit]("fahrenheit", 5.0 / 9.0, 459.67)
 * }}}
 */
trait DerivedTemperature extends DerivedUnit[SIBaseUnits.Kelvin] with TemperatureExpr

/**
 * A value (quantity) having an associated static unit type
 * @tparam U The unit expression representing the associated unit
 * {{{
 * import MKSUnits._
 * // a length of 5 meters
 * val length = Quantity[Meter](5)
 * // a velocity in meters per second
 * val speed = Quantity[Meter </> Second](10)
 * // an acceleration in meters per second-squared
 * val acceleration = Quantity[Meter </> (Second <^> _2)](9.8)
 * }}}
 */
class Quantity[N, U <: UnitExpr](val value: N)
    extends AnyVal with Serializable {

  /**
   * Convert a quantity into new units.
   * @tparam U2 the new unit expression to convert to. If U2 is not a compatible unit
   * then a compile-time error will occur
   * @return a new value of type Quantity[U2], equivalent to `this` quantity
   */
  def toUnit[U2 <: UnitExpr]: Quantity[N, U2] = macro UnitMacros.toUnitImpl[N, U, U2]

  /**
   * Obtain a new quantity with same units, but negated value
   * @return negated unit quantity
   */
  def unary_- : Quantity[N, U] = macro UnitMacros.negImpl[N, U]

  /**
   * The sum of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity.  U2 must be compatible with U, or
   * a compile-time error will occur
   * @param that the right-hand side of the quantity sum
   * @return `this` + `that`, with units of left-hand side `this`
   */
  def +[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, U] = macro UnitMacros.addImpl[N, U, U2]

  /**
   * The difference of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity.  U2 must be compatible with U, or
   * a compile-time error will occur
   * @param that the right-hand side of the difference
   * @return `this` - `that`, with units of left-hand side `this`
   */
  def -[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, U] = macro UnitMacros.subImpl[N, U, U2]

  /**
   * The product of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity
   * @tparam RU the unit type of the result quantity, is compatible with `U <*> U2`
   * @param that the right-hand side of the product
   * @return `this` * `that`, with units of RU
   */
   def *[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, _] = macro UnitMacros.mulImpl[N, U, U2]

  /**
   * The quotient, or ratio, of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity
   * @tparam RU the unit type of the result quantity, is compatible with `U </> U2`
   * @param that the right-hand side of the ratio
   * @return `this` / `that`, with units of RU
   */
   def /[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, _] = macro UnitMacros.divImpl[N, U, U2]

/*
  /**
   * Raise a unit quantity to a power
   * @tparam E the church integer type representing the exponent
   * @return `this` ^ E, in units compatible with `U <^> E`
   */
  def pow[E <: ChurchInt](implicit exp: ChurchIntValue[E]): Quantity[U <^> E] =
    new Quantity[U <^> E](math.pow(this.value, exp.value))

*/

  /** A human-readable string representing the unit quantity with its associated type */
  def str(implicit uesU: UnitExprString[U]) = s"$value ${uesU.str}"

  /** A human-readable string representing the unit type of this quantity */
  def unitStr(implicit uesU: UnitExprString[U]) = uesU.str

  override def toString = s"Quantity($value)"
}

/** Factory functions and implicit definitions associated with Quantity objects */
object Quantity {
/*
  /**
   * Obtain a function that converts objects of Quantity[U] into compatible Quantity[U2]
   * @tparam U the unit type of input quantity.
   * @tparam U2 the unit type of the output. If U2 is not compatible with U,
   * then a compile-time error will occur.
   * @return a function for converting Quantity[U] into Quantity[U2]
   */
  def converter[U <: UnitExpr, U2 <: UnitExpr](implicit cu: ConvertableUnits[U, U2]):
      Quantity[U] => Quantity[U2] =
    cu.converter

  /**
   * Obtain the numeric coefficient that represents the conversion factor from
   * a quantity with units U to a quantity of unit type U2
   * @tparam U the unit type of input quantity.
   * @tparam U2 the unit type of the output. If U2 is not compatible with U,
   * then a compile-time error will occur.
   * @return numeric coefficient, aka the conversion factor from Quantity[U] into Quantity[U2]
   */
  def coefficient[U <: UnitExpr, U2 <: UnitExpr](implicit cu: CompatUnits[U, U2]): Double =
    cu.coef

  /**
   * Obtain a human-readable string representing a unit type U
   * @tparam U a unit type
   * @return human readable string representing U
   */
  def unitStr[U <: UnitExpr](implicit uesU: UnitExprString[U]) = uesU.str

  /** Obtain a unit quantity of unit type U from an Int */
  def apply[U <: UnitExpr](v: Int) = new Quantity[U](v.toDouble)

  /** Obtain a unit quantity of unit type U from a Long */
  def apply[U <: UnitExpr](v: Long) = new Quantity[U](v.toDouble)

  /** Obtain a unit quantity of unit type U from a Float */
  def apply[U <: UnitExpr](v: Float) = new Quantity[U](v.toDouble)

  /** Obtain a unit quantity of unit type U from a Double */
  def apply[U <: UnitExpr](v: Double) = new Quantity[U](v)

  /**
   * Obtain a unit quantity from a Temperature with the same raw value and temperature unit
   * @tparam U a unit of temperature, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
   * or USCustomaryUnits.Fahrenheit
   * @param t the temperature value of unit type U
   * @return a unit quantity of the same unit type U and raw numeric value of t
   */
  def fromTemperature[U <: TemperatureExpr](t: Temperature[U]) = new Quantity[U](t.value)

  implicit def implicitUnitConvert[U <: UnitExpr, U2 <: UnitExpr](q: Quantity[U])(implicit
    cu: ConvertableUnits[U, U2]): Quantity[U2] = cu.convert(q)
*/
}

/*
/**
 * A temperature value.
 * @tparam U a temperature unit, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
 * or USCustomaryUnits.Fahrenheit
 * {{{
 * // a Temperature takes temperature baseline offsets into account during conversion
 * val c = Temperature[Celsius](1)
 * val f = c.as[Fahrenheit]        // == Temperature[Fahrenheit](33.8)
 * // a Quantity of temperature only considers amounts of unit
 * val cq = Quantity[Celsius](1)
 * val fq = cq.as[Fahrenheit]      // == Quantity[Fahrenheit](1.8)
 * }}}
 */
class Temperature[U <: TemperatureExpr](private [coulomb] val value: Double)
    extends AnyVal with Serializable {

  /**
   * Convert a temperature into a new unit of temperature.
   * @tparam U2 the new temperature unit expression to convert to.
   * @return a new value of type Temperature[U2], equivalent to `this`
   */
  def as[U2 <: TemperatureExpr](implicit ct: ConvertableTemps[U, U2]): Temperature[U2] =
    ct.convert(this)

  /**
   * Add a Quantity of temperature units to a temperature to get a new temperature
   * @tparam U2 the temperature unit of right side.  If U2 is not a compatible unit (temperature)
   * a compile-time error will ocurr.
   * @param that the right hand side of sum
   * @return a new temperature that is sum of left-hand temp plus right-hand temp quantity
   */
  def +[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Temperature[U] =
    new Temperature[U](this.value + cu.coef * that.value)

  /**
   * Subtract a Quantity of temperature units from a temperature to get a new temperature
   * @tparam U2 the temperature unit of right side.  If U2 is not a compatible unit (temperature)
   * a compile-time error will ocurr.
   * @param that the right hand side of difference
   * @return a new temperature that is the left-hand temp minus right-hand temp quantity
   */
  def -[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Temperature[U] =
    new Temperature[U](this.value - cu.coef * that.value)

  /**
   * Subtract two temperatures to get a Quantity of temperature units
   * @tparam U2 the temperature unit of right side.
   * @param that the right hand side of difference
   * @return a new unit Quantity equal to `this` - `that`
   */
  def -[U2 <: TemperatureExpr](that: Temperature[U2])(implicit
      ct: ConvertableTemps[U2, U]): Quantity[U] =
    new Quantity[U](this.value - ct.convert(that).value)

  /** The raw value of the temperature, rounded to nearest integer and returned as an Int */
  def toInt: Int = value.round.toInt

  /** The raw value of the temperature, rounded to nearest integer and returned as a Long */
  def toLong: Long = value.round

  /** The raw value of the temperature, rounded to nearest integer and returned as a Float */
  def toFloat: Float = value.toFloat

  /** The raw value of the temperature, rounded to nearest integer and returned as a Double */
  def toDouble: Double = value

  /** A human-readable string representing the temperature with its associated unit type */  
  def str(implicit uesU: UnitExprString[U]) = s"$value ${uesU.str}"

  /** A human-readable string representing the unit type of this temperature */
  def unitStr(implicit uesU: UnitExprString[U]) = uesU.str
}

/** Factory functions and implicit definitions associated with Temperature objects */
object Temperature {
  /**
   * Obtain a function that converts objects of Temperature[U] into compatible Temperature[U2]
   * @tparam U the unit type of input temp.
   * @tparam U2 the unit type of the output.
   * @return a function for converting Temperature[U] into Temperature[U2]
   */
  def converter[U <: TemperatureExpr, U2 <: TemperatureExpr](implicit
      ct: ConvertableTemps[U, U2]): Temperature[U] => Temperature[U2] =
    ct.converter

  /**
   * Obtain a human-readable string representing a unit type U
   * @tparam U a unit type representing a temperature
   * @return human readable string representing U
   */
  def unitStr[U <: TemperatureExpr](implicit uesU: UnitExprString[U]) = uesU.str

  /** Obtain a temperature of type U from an Int */
  def apply[U <: TemperatureExpr](v: Int) = new Temperature[U](v.toDouble)

  /** Obtain a temperature of type U from a Long */
  def apply[U <: TemperatureExpr](v: Long) = new Temperature[U](v.toDouble)

  /** Obtain a temperature of type U from a Float */
  def apply[U <: TemperatureExpr](v: Float) = new Temperature[U](v.toDouble)

  /** Obtain a temperature of type U from a Doiuble */
  def apply[U <: TemperatureExpr](v: Double) = new Temperature[U](v)

  /**
   * Obtain a temperature from a unit Quantity with same raw value and temperature unit
   * @tparam U a unit of temperature, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
   * or USCustomaryUnits.Fahrenheit
   * @param q the quantity of temperature-unit type U
   * @return a temperature of same unit type U and raw numeric value of q
   */
  def fromQuantity[U <: TemperatureExpr](q: Quantity[U]) = new Temperature[U](q.value)

  implicit def implicitTempConvert[U <: TemperatureExpr, U2 <: TemperatureExpr](t: Temperature[U])(
      implicit ct: ConvertableTemps[U, U2]): Temperature[U2] =
    ct.convert(t)
}
*/

/*
class ConvertableUnits[U1 <: UnitExpr, U2 <: UnitExpr](val coef: Double) {
  val converter = ConvertableUnits.converter[U1, U2](coef)
  def convert(q1: Quantity[U1]): Quantity[U2] = converter(q1)
}

object ConvertableUnits {
  def converter[U1 <: UnitExpr, U2 <: UnitExpr](coef: Double): Quantity[U1] => Quantity[U2] =
    (q1: Quantity[U1]) => new Quantity[U2](coef * q1.value)

  implicit def witnessConvertableUnits[U1 <: UnitExpr, U2 <: UnitExpr](implicit
      cu: CompatUnits[U1, U2]): ConvertableUnits[U1, U2] =
    new ConvertableUnits[U1, U2](cu.coef)
}
*/

/*
class ConvertableTemps[U1 <: TemperatureExpr, U2 <: TemperatureExpr](
    val coef1: Double, val off1: Double, val coef2: Double, val off2: Double) {
  val converter = ConvertableTemps.converter[U1, U2](coef1, off1, coef2, off2)
  def convert(t1: Temperature[U1]): Temperature[U2] = converter(t1)
}

object ConvertableTemps {
  def converter[U1 <: TemperatureExpr, U2 <: TemperatureExpr](
    coef1: Double, off1: Double, coef2: Double, off2: Double): Temperature[U1] => Temperature[U2] =
    (t1: Temperature[U1]) => {
      val k = (t1.value + off1) * coef1
      val v2 = (k / coef2) - off2
      new Temperature[U2](v2)
    }

  implicit def witnessConvertableTemps[U1 <: TemperatureExpr, U2 <: TemperatureExpr](implicit
      turecU1: TempUnitRec[U1], urecU1: UnitRec[U1],
      turecU2: TempUnitRec[U2], urecU2: UnitRec[U2]): ConvertableTemps[U1, U2] =
    new ConvertableTemps[U1, U2](
      urecU1.coef.toDouble, turecU1.offset.toDouble, urecU2.coef.toDouble, turecU2.offset.toDouble)
}
*/
