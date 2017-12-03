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
  import scala.language.experimental.macros
  import scala.reflect.macros.whitebox
  import scala.reflect.runtime.universe._
  import spire.math._
  import shapeless._
  import shapeless.syntax.singleton._
  import shapeless.record._
  import singleton.ops._

  // return a human-readable type string for type argument 'T'
  // typeString[Int] returns "Int"
  def typeString[T :TypeTag]: String = {
    def work(t: Type): String = {
      t match { case TypeRef(pre, sym, args) =>
        val ss = sym.toString.stripPrefix("trait ").stripPrefix("class ").stripPrefix("type ")
        val as = args.map(work)
        if (ss.startsWith("Function")) {
          val arity = args.length - 1
          "(" + (as.take(arity).mkString(",")) + ")" + "=>" + as.drop(arity).head
        } else {
          if (args.length <= 0) ss else (ss + "[" + as.mkString(",") + "]")
        }
      }
    }
    work(typeOf[T])
  }

  // get the type string of an argument:
  // typeString(2) returns "Int"
  def typeString[T :TypeTag](x: T): String = typeString[T]

  case class TestResult[O]()
  //def test1[X](implicit r: CollectTerms[X]): TestResult[r.Out] = TestResult[r.Out]()
  //def test2[X, Y](implicit r: InsertKVSub[X, Y]): TestResult[r.Out] = TestResult[r.Out]()
  //def test3[X, Y, Z](implicit r: InsertKVSub[X, Y, Z]): TestResult[r.Out] = TestResult[r.Out]()

  type True = Witness.`true`.T
  type False = Witness.`false`.T

  type XInt0 = Witness.`0`.T
  type XInt1 = Witness.`1`.T

  trait XIntAdd[L, R] {
    type Out
  }

  object XIntAdd {
    type Aux[L, R, O] = XIntAdd[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: +[L, R]): Aux[L, R, op.Out] = new XIntAdd[L, R] { type Out = op.Out }
  }

  trait XIntSub[L, R] {
    type Out
  }

  object XIntSub {
    type Aux[L, R, O] = XIntSub[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: -[L, R]): Aux[L, R, op.Out] = new XIntSub[L, R] { type Out = op.Out }
  }

  trait XIntMul[L, R] {
    type Out
  }

  object XIntMul {
    type Aux[L, R, O] = XIntMul[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: *[L, R]): Aux[L, R, op.Out] = new XIntMul[L, R] { type Out = op.Out }
  }

  trait IsMember[E, L] {
    type Out
  }
  object IsMember {
    type Aux[E, L, O] = IsMember[E, L] { type Out = O }
    implicit def ismember0[E]: Aux[E, HNil, False] = new IsMember[E, HNil] { type Out = False }
    implicit def ismember1[E, T <: HList]: Aux[E, E :: T, True] = new IsMember[E, E :: T] { type Out = True }
    implicit def ismember2[E, E0, T <: HList, O](implicit ne: E =:!= E0, r: Aux[E, T, O]): Aux[E, E0 :: T, O] = {
      new IsMember[E, E0 :: T] { type Out = O }
    }
  }

  trait Subset[S1, S2] {
    type Out
  }
  object Subset {
    type Aux[S1, S2, O] = Subset[S1, S2] { type Out = O }
    implicit def subset0[S]: Aux[HNil, S, True] = new Subset[HNil, S] { type Out = True }
    implicit def subset1[E, T <: HList, S2](implicit m: IsMember.Aux[E, S2, False]): Aux[E :: T, S2, False] =
      new Subset[E :: T, S2] { type Out = False }
    implicit def subset2[E, T <: HList, S2, O](implicit m: IsMember.Aux[E, S2, True], s: Aux[T, S2, O]): Aux[E :: T, S2, O] =
      new Subset[E :: T, S2] { type Out = O }
  }

  trait SetEqual[S1, S2] {
    type Out
  }
  object SetEqual {
    type Aux[S1, S2, O] = SetEqual[S1, S2] { type Out = O }
    implicit def equal0[S1, S2](implicit s1: Subset.Aux[S1, S2, True], s2: Subset.Aux[S2, S1, True]): Aux[S1, S2, True] =
      new SetEqual[S1, S2] { type Out = True }
    implicit def equal1[S1, S2](implicit s1: Subset.Aux[S1, S2, True], s2: Subset.Aux[S2, S1, False]): Aux[S1, S2, False] =
      new SetEqual[S1, S2] { type Out = False }
    implicit def equal2[S1, S2](implicit s1: Subset.Aux[S1, S2, False], s2: Subset.Aux[S2, S1, True]): Aux[S1, S2, False] =
      new SetEqual[S1, S2] { type Out = False }
    implicit def equal3[S1, S2](implicit s1: Subset.Aux[S1, S2, False], s2: Subset.Aux[S2, S1, False]): Aux[S1, S2, False] =
      new SetEqual[S1, S2] { type Out = False }
  }

  trait InsertKVMul[K, V, M] {
    type Out
  }
  object InsertKVMul {
    type Aux[K, V, M, O] = InsertKVMul[K, V, M] { type Out = O }

    implicit def insert0[K, V]: Aux[K, V, HNil, (K, V) :: HNil] =
      new InsertKVMul[K, V, HNil] { type Out = (K, V) :: HNil }

    implicit def insert1[K, V, V0, MT <: HList, P](implicit op: XIntAdd.Aux[V0, V, P], nz: P =:!= XInt0): Aux[K, V, (K, V0) :: MT, (K, P) :: MT] =
      new InsertKVMul[K, V, (K, V0) :: MT] { type Out = (K, P) :: MT }
 
    implicit def insert1z[K, V, V0, MT <: HList](implicit op: XIntAdd.Aux[V0, V, XInt0]): Aux[K, V, (K, V0) :: MT, MT] =
      new InsertKVMul[K, V, (K, V0) :: MT] { type Out = MT }
 
    implicit def insert2[K, V, K0, V0, MT <: HList, O <: HList](implicit ne: K =:!= K0, rc: Aux[K, V, MT, O]): Aux[K, V, (K0, V0) :: MT, (K0, V0) :: O] =
      new InsertKVMul[K, V, (K0, V0) :: MT] { type Out = (K0, V0) :: O }
  }

  trait InsertKVDiv[K, V, M] {
    type Out
  }
  object InsertKVDiv {
    type Aux[K, V, M, O] = InsertKVDiv[K, V, M] { type Out = O }

    implicit def insert0[K, V](implicit n: Negate[V]): Aux[K, V, HNil, (K, n.Out) :: HNil] =
      new InsertKVDiv[K, V, HNil] { type Out = (K, n.Out) :: HNil }

    implicit def insert1[K, V, V0, MT <: HList, P](implicit op: XIntSub.Aux[V0, V, P], nz: P =:!= XInt0): Aux[K, V, (K, V0) :: MT, (K, P) :: MT] =
      new InsertKVDiv[K, V, (K, V0) :: MT] { type Out = (K, P) :: MT }

    implicit def insert1z[K, V, V0, MT <: HList](implicit op: XIntSub.Aux[V0, V, XInt0]): Aux[K, V, (K, V0) :: MT, MT] =
      new InsertKVDiv[K, V, (K, V0) :: MT] { type Out = MT }

    implicit def insert2[K, V, K0, V0, MT <: HList, O <: HList](implicit ne: K =:!= K0, rc: Aux[K, V, MT, O]): Aux[K, V, (K0, V0) :: MT, (K0, V0) :: O] =
      new InsertKVDiv[K, V, (K0, V0) :: MT] { type Out = (K0, V0) :: O }
  }

  trait UnifyKVMul[M1, M2] {
    type Out
  }
  object UnifyKVMul {
    type Aux[M1, M2, O] = UnifyKVMul[M1, M2] { type Out = O }
    implicit def unify0[M2]: Aux[HNil, M2, M2] =
      new UnifyKVMul[HNil, M2] { type Out = M2 }
    implicit def unify1[K, V, MT <: HList, M2, O, O2](implicit ui: InsertKVMul.Aux[K, V, M2, O], rc: Aux[MT, O, O2]): Aux[(K, V) :: MT, M2, O2] =
      new UnifyKVMul[(K, V) :: MT, M2] { type Out = O2 }
  }

  // Note, this is like "M2 / M1" so careful with type argument order
  trait UnifyKVDiv[M1, M2] {
    type Out
  }
  object UnifyKVDiv {
    type Aux[M1, M2, O] = UnifyKVDiv[M1, M2] { type Out = O }
    implicit def unify0[M2]: Aux[HNil, M2, M2] =
      new UnifyKVDiv[HNil, M2] { type Out = M2 }
    implicit def unify1[K, V, MT <: HList, M2, O, O2](implicit ui: InsertKVDiv.Aux[K, V, M2, O], rc: Aux[MT, O, O2]): Aux[(K, V) :: MT, M2, O2] =
      new UnifyKVDiv[(K, V) :: MT, M2] { type Out = O2 }
  }

  case class BaseUnit[U]()
  case class UnitName[U](name: String)
  case class UnitAbbv[U](abbv: String)
  case class DerivedUnit[U, D](coef: Rational)

  trait Unitless
  trait %*[L, R]
  trait %/[L, R]

  trait Canonical[U] {
    type Out
    def coef: Rational
  }

  object Canonical {
    type Aux[U, O] = Canonical[U] { type Out = O }

    implicit def witnessUnitlessCM: Aux[Unitless, HNil] = {
      new Canonical[Unitless] {
        type Out = HNil
        val coef = Rational(1)
      }
    }

    implicit def witnessBaseUnitCM[U](implicit buU: BaseUnit[U]): Aux[U, (U, XInt1) :: HNil] = {
      new Canonical[U] {
        type Out = (U, XInt1) :: HNil
        val coef = Rational(1)
      }
    }

    implicit def witnessDerivedUnitCM[U, D, DC](implicit du: DerivedUnit[U, D], dm: Aux[D, DC]): Aux[U, DC] = {
      new Canonical[U] {
        type Out = DC
        val coef = du.coef * dm.coef
      }
    }

    implicit def witnessMulCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifyKVMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] = {
      new Canonical[%*[L, R]] {
        type Out = OC
        val coef = l.coef * r.coef
      }
    }

    implicit def witnessDivCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifyKVDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] = {
      new Canonical[%/[L, R]] {
        type Out = OC
        val coef = l.coef / r.coef
      }
    }
  }

  def ctest[U](implicit u: Canonical[U]): TestResult[u.Out] = TestResult[u.Out]()

  case class ConvertableUnits[U1, U2](coef: Rational)

  object ConvertableUnits {
    implicit def witnessCU[U1, U2, C1, C2](implicit u1: Canonical.Aux[U1, C1], u2: Canonical.Aux[U2, C2], eq: SetEqual.Aux[C1, C2, True]): ConvertableUnits[U1, U2] =
      ConvertableUnits[U1, U2](u1.coef / u2.coef)
  }

  def coefficient[U1, U2](implicit cu: ConvertableUnits[U1, U2]): Rational = cu.coef

  trait Meter
  implicit val buMeter = BaseUnit[Meter]()

  trait Second
  implicit val buSecond = BaseUnit[Second]()

  trait Minute
  implicit val duMinute = DerivedUnit[Minute, Second](Rational(60))
}
