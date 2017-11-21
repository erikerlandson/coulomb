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
 * A value (quantity) having an associated static unit type
 * @tparam N The numeric representation type for a quantity value
 * @tparam U The unit expression representing the associated unit
 * {{{
 * import com.manyangled.coulomb._
 * import ChurchInt._
 * import SIBaseUnits._
 * // a length of 5 meters
 * val length = Meter(5.0)
 * // a velocity in meters per second
 * val speed = 10.withUnit[Meter %/ Second]
 * // an acceleration in meters per second-squared
 * val acceleration = 9.8f.withUnit[Meter %/ (Second %^ _2)]
 * }}}
 * @param value The quantity value
 */
class Quantity[N, U <: UnitExpr](val value: N)
    extends AnyVal with Serializable {

  /**
   * Convert a quantity into new units.
   * @tparam U2 the new unit expression to convert to. If U2 is not a convertable unit
   * then a compile-time error will occur
   * @return a new value of type Quantity[N, U2], equivalent to `this` quantity
   */
  def toUnit[U2 <: UnitExpr]: Quantity[N, U2] = macro UnitMacros.toUnitImpl[N, U, U2]

  /** Convert a quantity of numeric type N to a new quantity of type N2, with same units */
  def toRep[N2](implicit cfN: spire.math.ConvertableFrom[N], ctN2: spire.math.ConvertableTo[N2]):
      Quantity[N2, U] =
    new Quantity[N2, U](cfN.toType[N2](this.value))

  /**
   * Obtain a new quantity with same units, but negated value
   * @return negated unit quantity
   */
  def unary_- : Quantity[N, U] = macro UnitMacros.negImpl[N, U]

  /**
   * The sum of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity.  U2 must be convertable to U, or
   * a compile-time error will occur
   * @param that the right-hand side of the quantity sum
   * @return `this` + `that`, with units of left-hand side `this`
   */
  def +[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, U] = macro UnitMacros.addImpl[N, U, U2]

  /**
   * The difference of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity.  U2 must be convertable to U, or
   * a compile-time error will occur
   * @param that the right-hand side of the difference
   * @return `this` - `that`, with units of left-hand side `this`
   */
  def -[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, U] = macro UnitMacros.subImpl[N, U, U2]

  /**
   * The product of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity
   * @param that the right-hand side of the product
   * @return `this` * `that`, with units convertable to `U %* U2`
   */
   def *[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, _] = macro UnitMacros.mulImpl[N, U, U2]

  /**
   * The quotient, or ratio, of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity
   * @param that the right-hand side of the ratio
   * @return `this` / `that`, with units convertable to `U %/ U2`
   */
   def /[U2 <: UnitExpr](that: Quantity[N, U2]): Quantity[N, _] = macro UnitMacros.divImpl[N, U, U2]

  /**
   * Raise a unit quantity to a power
   * @tparam E the church integer type representing the exponent
   * @return `this` ^ E, in units convertable to `U %^ E`
   */
  def pow[E <: ChurchInt]: Quantity[N, _] = macro UnitMacros.powImpl[N, U, E]

  /** Returns `true` if and only if `this.value` < `that.value` */
  def <(that: Quantity[N, U]): Boolean = macro UnitMacros.implLT

  /** Returns `true` if and only if `this.value` > `that.value` */
  def >(that: Quantity[N, U]): Boolean = macro UnitMacros.implGT

  /** Returns `true` if and only if `this.value` <= `that.value` */
  def <=(that: Quantity[N, U]): Boolean = macro UnitMacros.implLE

  /** Returns `true` if and only if `this.value` >= `that.value` */
  def >=(that: Quantity[N, U]): Boolean = macro UnitMacros.implGE

  /** Returns `true` if and only if `this.value` == `that.value` */
  def ===(that: Quantity[N, U]): Boolean = macro UnitMacros.implEQ

  /** Returns `true` if and only if `this.value` != `that.value` */
  def =!=(that: Quantity[N, U]): Boolean = macro UnitMacros.implNE

  /** A human-readable string representing the value and unit type of this quantity */
  def toStr: String = macro UnitMacros.toStrImpl[U]

  /**
   * A human-readable string representing the value and unit type of this quantity,
   * using the full unit names
   */
  def toStrFull: String = macro UnitMacros.toStrFullImpl[U]

  /** A human-readable string representing the unit type of this quantity */
  def unitStr: String = macro UnitMacros.unitStrImpl[U]

  /** A human-readable string representing the unit type of this quantity, using full unit names */
  def unitStrFull: String = macro UnitMacros.unitStrFullImpl[U]

  // I can define this with a macro, but its default behavior is to output string as the value-class
  // so it doesn't really buy me anything.  This at least gets invoked automatically.
  override def toString = s"Quantity(${this.value})"
}

/** Factory functions and implicit definitions associated with Quantity objects */
object Quantity {
  /**
   * Obtain a function that converts objects of Quantity[N, U1] into convertable Quantity[N, U2]
   * @tparam N the numeric representation type
   * @tparam U1 the unit type of input quantity.
   * @tparam U2 the unit type of the output. If U2 is not convertable to U1,
   * then a compile-time error will occur.
   * @return a function for converting Quantity[N, U1] into Quantity[N, U2]
   */
  def converter[N, U1 <: UnitExpr, U2 <: UnitExpr]: Quantity[N, U1] => Quantity[N, U2] =
    macro UnitMacros.converterImpl[N, U1, U2]

  /**
   * Obtain the numeric coefficient that represents the conversion factor from
   * a quantity with units U1 to a quantity of unit type U2
   * @tparam U1 the unit type of input quantity.
   * @tparam U2 the unit type of the output. If U2 is not convertable to U1,
   * then a compile-time error will occur.
   * @return numeric coefficient, aka the conversion factor from U1 into U2
   */
  def coefficient[U1 <: UnitExpr, U2 <: UnitExpr]: spire.math.Rational =
    macro UnitMacros.coefficientImpl[U1, U2]

  /** A human-readable string representing the unit type U */
  def unitStr[U <: UnitExpr]: String = macro UnitMacros.unitStrImpl[U]

  /** A human-readable string representing the unit type U, using full unit names */
  def unitStrFull[U <: UnitExpr]: String = macro UnitMacros.unitStrFullImpl[U]

  /**
   * Obtain a unit quantity from a Temperature with the same raw value and temperature unit
   * @tparam N the numeric representation type
   * @tparam U a unit of temperature, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
   * or USCustomaryUnits.Fahrenheit
   * @param t the temperature value of unit type U
   * @return a unit quantity of the same unit type U and raw numeric value of t
   */
  def fromTemperature[N, U <: TemperatureExpr](t: Temperature[N, U]) = new Quantity[N, U](t.value)

  /** Implicit conversion between quantities of convertable units and same numeric type */
  implicit def implicitUnitConvert[N, U <: UnitExpr, U2 <: UnitExpr](q: Quantity[N, U]):
      Quantity[N, U2] =
    macro UnitMacros.unitConvertImpl[N, U, U2]
}

/**
 * A temperature value.
 * @tparam N The numeric representation type for a temperature value
 * @tparam U a temperature unit, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
 * or USCustomaryUnits.Fahrenheit
 * {{{
 * import com.manyangled.coulomb._
 * import SIBaseUnits._
 * import SIAcceptedUnits._
 * import USCustomaryUnits._
 * // a Temperature takes temperature baseline offsets into account during conversion
 * val c = (1.0).withTemperature[Celsius]
 * val f = c.toUnit[Fahrenheit]       // f = Temperature[Double, Fahrenheit](33.8)
 * // a Quantity of temperature only considers amounts of unit
 * val cq = (1.0).withUnit[Celsius]
 * val fq = cq.toUnit[Fahrenheit]     // fq = Quantity[Double, Fahrenheit](1.8)
 * }}}
 */
class Temperature[N, U <: TemperatureExpr](val value: N)
    extends AnyVal with Serializable {

  /**
   * Convert a temperature into a new unit of temperature.
   * @tparam U2 the new temperature unit expression to convert to.
   * @return a new value of type Temperature[U2], equivalent to `this`
   */
  def toUnit[U2 <: TemperatureExpr]: Temperature[N, U2] =
    macro UnitMacros.toUnitTempImpl[N, U, U2]

  /** Convert a quantity of representation type N to a new quantity of type N2, with same units */
  def toRep[N2](implicit cfN: spire.math.ConvertableFrom[N], ctN2: spire.math.ConvertableTo[N2]):
      Temperature[N2, U] =
    new Temperature[N2, U](cfN.toType[N2](this.value))

  /**
   * Add a Quantity of temperature units to a temperature to get a new temperature
   * @tparam U2 the temperature unit of right side.  If U2 is not a convertable unit (temperature)
   * a compile-time error will ocurr.
   * @param that the right hand side of sum
   * @return a new temperature that is sum of left-hand temp plus right-hand temp quantity
   */
  def +[U2 <: UnitExpr](that: Quantity[N, U2]): Temperature[N, U] =
    macro UnitMacros.addTQImpl[N, U, U2]

  /**
   * Subtract a Quantity of temperature units from a temperature to get a new temperature
   * @tparam U2 the temperature unit of right side.  If U2 is not a convertable unit (temperature)
   * a compile-time error will ocurr.
   * @param that the right hand side of difference
   * @return a new temperature that is the left-hand temp minus right-hand temp quantity
   */
  def -[U2 <: UnitExpr](that: Quantity[N, U2]): Temperature[N, U] =
    macro UnitMacros.subTQImpl[N, U, U2]

  /**
   * Subtract two temperatures to get a Quantity of temperature units
   * @tparam U2 the temperature unit of right side.
   * @param that the right hand side of difference
   * @return a new unit Quantity equal to `this` - `that`
   */
  def -[U2 <: TemperatureExpr](that: Temperature[N, U2]): Quantity[N, U] =
    macro UnitMacros.subTTImpl[N, U, U2]

  /** Returns `true` if and only if `this.value` < `that.value` */
  def <(that: Temperature[N, U]): Boolean = macro UnitMacros.implLT

  /** Returns `true` if and only if `this.value` > `that.value` */
  def >(that: Temperature[N, U]): Boolean = macro UnitMacros.implGT

  /** Returns `true` if and only if `this.value` <= `that.value` */
  def <=(that: Temperature[N, U]): Boolean = macro UnitMacros.implLE

  /** Returns `true` if and only if `this.value` >= `that.value` */
  def >=(that: Temperature[N, U]): Boolean = macro UnitMacros.implGE

  /** Returns `true` if and only if `this.value` == `that.value` */
  def ===(that: Temperature[N, U]): Boolean = macro UnitMacros.implEQ

  /** Returns `true` if and only if `this.value` != `that.value` */
  def =!=(that: Temperature[N, U]): Boolean = macro UnitMacros.implNE

  /** A human-readable string representing the temperature with its associated unit type */  
  def toStr: String = macro UnitMacros.toStrImpl[U]

  /**
   * A human-readable string representing the value and unit type of this quantity,
   * using the full unit names
   */
  def toStrFull: String = macro UnitMacros.toStrFullImpl[U]

  /** A human-readable string representing the unit type of this quantity */
  def unitStr: String = macro UnitMacros.unitStrImpl[U]

  /** A human-readable string representing the unit type of this quantity, using full unit names */
  def unitStrFull: String = macro UnitMacros.unitStrFullImpl[U]

  override def toString = s"Temperature(${this.value})"
}

/** Factory functions and implicit definitions associated with Temperature objects */
object Temperature {
  /**
   * Obtain a function that converts objects of Temperature[U] into convertable Temperature[U2]
   * @tparam N the numeric representation type
   * @tparam U the unit type of input temp.
   * @tparam U2 the unit type of the output.
   * @return a function for converting Temperature[U] into Temperature[U2]
   */
  def converter[N, U1 <: TemperatureExpr, U2 <: TemperatureExpr]:
      Temperature[N, U1] => Temperature[N, U2] =
    macro UnitMacros.tempConverterImpl[N, U1, U2]

  /** A human-readable string representing the unit type U */
  def unitStr[U <: TemperatureExpr]: String = macro UnitMacros.unitStrImpl[U]

  /** A human-readable string representing the unit type U, using full unit names */
  def unitStrFull[U <: TemperatureExpr]: String = macro UnitMacros.unitStrFullImpl[U]

  /**
   * Obtain a temperature from a unit Quantity with same raw value and temperature unit
   * @tparam N the numeric representation type
   * @tparam U a unit of temperature, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
   * or USCustomaryUnits.Fahrenheit
   * @param q the quantity of temperature-unit type U
   * @return a temperature of same unit type U and raw numeric value of q
   */
  def fromQuantity[N, U <: TemperatureExpr](q: Quantity[N, U]) = new Temperature[N, U](q.value)

  /** Implicit conversion between temperatures of convertable units and same numeric type */
  implicit def implicitTempConvert[N, U1 <: TemperatureExpr, U2 <: TemperatureExpr](
      t: Temperature[N, U1]): Temperature[N, U2] =
    macro UnitMacros.unitConvertTempImpl[N, U1, U2]
}

object recursive {
  import scala.reflect.runtime.universe._
  import spire.math._

  trait Unitless

  type CUMapType = Map[BaseUnit[_], Int]

  case class CUMap[U](coef: Rational, map: CUMapType)

  object CUMap {
    def mul[L, R](l: CUMap[L], r: CUMap[R]): CUMap[%*[L, R]] =
      CUMap[%*[L, R]](l.coef * r.coef, mapMul(l.map, r.map))

    def div[L, R](l: CUMap[L], r: CUMap[R]): CUMap[%/[L, R]] =
      CUMap[%/[L, R]](l.coef / r.coef, mapDiv(l.map, r.map))

    def mapMul(lmap: CUMapType, rmap: CUMapType): CUMapType = {
      rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
        if (m.contains(t)) {
          val ne = m(t) + e
          if (ne == 0) (m - t) else m + ((t, ne))
        } else {
          m + ((t, e))
        }
      }
    }

    def mapDiv(lmap: CUMapType, rmap: CUMapType): CUMapType = {
      rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
        if (m.contains(t)) {
          val ne = m(t) - e
          if (ne == 0) (m - t) else m + ((t, ne))
        } else {
          m + ((t, -e))
        }
      }
    }
  }

  case class BaseUnit[U]()
  case class UnitName[U](name: String)
  case class UnitAbbv[U](abbv: String)
  case class DerivedUnit[U, D](coef: Rational)

  trait %*[L, R]
  trait %/[L, R]

  implicit def witnessUnitlessCM: CUMap[Unitless] = {
    CUMap[Unitless](Rational(1), Map.empty[BaseUnit[_], Int])
  }

  implicit def witnessBaseUnitCM[U](implicit buU: BaseUnit[U]): CUMap[U] = {
    CUMap[U](Rational(1), Map(buU -> 1))
  }

  implicit def witnessDerivedUnitCM[U, D](implicit du: DerivedUnit[U, D], dm: CUMap[D]): CUMap[U] = {
    CUMap[U](du.coef * dm.coef, dm.map)
  }

  implicit def witnessMulCM[L, R](implicit l: CUMap[L], r: CUMap[R]): CUMap[%*[L, R]] = {
    CUMap.mul(l, r)
  }

  implicit def witnessDivCM[L, R](implicit l: CUMap[L], r: CUMap[R]): CUMap[%/[L, R]] = {
    CUMap.div(l, r)
  }

  case class ConvertableUnits[L, R](coef: Rational)

  implicit def witnessConvertableUnits[L, R](implicit l: CUMap[L], r: CUMap[R]): ConvertableUnits[L, R] = {
    if (l.map != r.map) null
    else ConvertableUnits[L, R](l.coef / r.coef)
  }

  def coefficient[L, R](implicit cu: ConvertableUnits[L, R]): Rational = cu.coef

  trait Meter
  implicit val buMeter = BaseUnit[Meter]()

  trait Second
  implicit val buSecond = BaseUnit[Second]()

  trait Minute
  implicit val duMinute = DerivedUnit[Minute, Second](Rational(60))
}
