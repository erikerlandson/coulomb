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
  import spire.syntax._
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
  def test2[X, Y](implicit r: ApplyKVPow[X, Y]): TestResult[r.Out] = TestResult[r.Out]()
  //def test3[X, Y, Z](implicit r: InsertKVSub[X, Y, Z]): TestResult[r.Out] = TestResult[r.Out]()

  type True = Witness.`true`.T
  type False = Witness.`false`.T

  type XInt0 = Witness.`0`.T
  type XInt1 = Witness.`1`.T

  trait XIntValue[I] {
    def value: Int
  }
  object XIntValue {
    implicit def evidence[I](implicit i: singleton.ops.Id[I]): XIntValue[I] =
      new XIntValue[I] {
        val value = i.value.asInstanceOf[Int]
      }
  }

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

  trait XIntNeg[N] {
    type Out
  }
  object XIntNeg {
    type Aux[N, O] = XIntNeg[N] { type Out = O }
    implicit def witness[N](implicit op: Negate[N]): Aux[N, op.Out] = new XIntNeg[N] { type Out = op.Out }
  }

  trait XIntLT[L, R] {
    type Out
  }
  object XIntLT {
    type Aux[L, R, O] = XIntLT[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: <[L, R]): Aux[L, R, op.Out] = new XIntLT[L, R] { type Out = op.Out }
  }

  trait XIntGT[L, R] {
    type Out
  }
  object XIntGT {
    type Aux[L, R, O] = XIntGT[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: >[L, R]): Aux[L, R, op.Out] = new XIntGT[L, R] { type Out = op.Out }
  }

  trait XIntEQ[L, R] {
    type Out
  }
  object XIntEQ {
    type Aux[L, R, O] = XIntEQ[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: ==[L, R]): Aux[L, R, op.Out] = new XIntEQ[L, R] { type Out = op.Out }
  }

  trait XIntNE[L, R] {
    type Out
  }
  object XIntNE {
    type Aux[L, R, O] = XIntNE[L, R] { type Out = O }
    implicit def witness[L, R](implicit op: !=[L, R]): Aux[L, R, op.Out] = new XIntNE[L, R] { type Out = op.Out }
  }

  trait XIntNon01[N] {
    type Out
  }
  object XIntNon01 {
    type Aux[N, O] = XIntNon01[N] { type Out = O }
    implicit def evidence[N, R0, R1](implicit ne0: XIntNE.Aux[N, XInt0, R0], ne1: XIntNE.Aux[N, XInt1, R1], a01: &&[R0, R1]): Aux[N, a01.Out] =
      new XIntNon01[N] { type Out = a01.Out }
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
    implicit def equal0[S1, S2, O1, O2](implicit s1: Subset.Aux[S1, S2, O1], s2: Subset.Aux[S2, S1, O2], a: &&[O1, O2]): Aux[S1, S2, a.Out] =
      new SetEqual[S1, S2] { type Out = a.Out }
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

  trait ApplyKVPow[P, M] {
    type Out
  }
  object ApplyKVPow {
    type Aux[P, M, O] = ApplyKVPow[P, M] { type Out = O }
    implicit def apply0[P](implicit nz: P =:!= XInt0): Aux[P, HNil, HNil] = new ApplyKVPow[P, HNil] { type Out = HNil }
    implicit def apply1[P, K, V, MT <: HList, O <: HList, Q](implicit nz: P =:!= XInt0, rc: Aux[P, MT, O], op: XIntMul.Aux[V, P, Q]): Aux[P, (K, V) :: MT, (K, Q) :: O] =
      new ApplyKVPow[P, (K, V) :: MT] { type Out = (K, Q) :: O }
    implicit def apply1z[M]: Aux[XInt0, M, HNil] = new ApplyKVPow[XInt0, M] { type Out = HNil }
  }

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

  trait Unitless
  trait %*[L, R]
  trait %/[L, R]
  trait %^[B, E]

  trait CanonicalSig[U] {
    type Out
    def coef: Rational
  }

  object CanonicalSig {
    type Aux[U, O] = CanonicalSig[U] { type Out = O }

    implicit def witnessUnitlessCM: Aux[Unitless, HNil] = {
      new CanonicalSig[Unitless] {
        type Out = HNil
        val coef = Rational(1)
      }
    }

    implicit def witnessBaseUnitCM[U](implicit buU: BaseUnit[U]): Aux[U, (U, XInt1) :: HNil] = {
      new CanonicalSig[U] {
        type Out = (U, XInt1) :: HNil
        val coef = Rational(1)
      }
    }

    implicit def witnessDerivedUnitCM[U, D, DC](implicit du: DerivedUnit[U, D], dm: Aux[D, DC]): Aux[U, DC] = {
      new CanonicalSig[U] {
        type Out = DC
        val coef = du.coef * dm.coef
      }
    }

    implicit def witnessMulCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifyKVMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] = {
      new CanonicalSig[%*[L, R]] {
        type Out = OC
        val coef = l.coef * r.coef
      }
    }

    implicit def witnessDivCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifyKVDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] = {
      new CanonicalSig[%/[L, R]] {
        type Out = OC
        val coef = l.coef / r.coef
      }
    }

    implicit def witnessPowCM[B, BC, E, OC](implicit b: Aux[B, BC], u: ApplyKVPow.Aux[E, BC, OC], e: singleton.ops.Id[E]): Aux[%^[B, E], OC] = {
      new CanonicalSig[%^[B, E]] {
        type Out = OC
        val coef = b.coef.pow(e.value.asInstanceOf[Int])
      }
    }
  }

  trait StandardSig[U] {
    type Out
  }
  object StandardSig {
    type Aux[U, O] = StandardSig[U] { type Out = O }

    implicit def witnessUnitlessCM: Aux[Unitless, HNil] =
      new StandardSig[Unitless] { type Out = HNil }

    implicit def witnessBaseUnitCM[U](implicit buU: BaseUnit[U]): Aux[U, (U, XInt1) :: HNil] =
      new StandardSig[U] { type Out = (U, XInt1) :: HNil }

    implicit def witnessDerivedUnitCM[U](implicit du: DerivedUnit[U, _]): Aux[U, (U, XInt1) :: HNil] =
      new StandardSig[U] { type Out = (U, XInt1) :: HNil }

    implicit def witnessMulCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifyKVMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] =
      new StandardSig[%*[L, R]] { type Out = OC }

    implicit def witnessDivCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifyKVDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] =
      new StandardSig[%/[L, R]] { type Out = OC }

    implicit def witnessPowCM[B, BC, E, OC](implicit b: Aux[B, BC], u: ApplyKVPow.Aux[E, BC, OC], e: singleton.ops.Id[E]): Aux[%^[B, E], OC] =
      new StandardSig[%^[B, E]] { type Out = OC }
  }

  // assuming inputs (U, P) are already from some canonical sig; so a type U will never pre-exist in M
  trait InsertSortedUnitSig[U, P, M] {
    type Out
  }
  object InsertSortedUnitSig {
    type Aux[U, P, M, O] = InsertSortedUnitSig[U, P, M] { type Out = O }

    implicit def evidence0[U, P]: Aux[U, P, HNil, (U, P) :: HNil] =
      new InsertSortedUnitSig[U, P, HNil] { type Out = (U, P) :: HNil }

    implicit def evidence1[U, P, U0, P0, MT <: HList](implicit lte: XIntGT.Aux[P, P0, False]): Aux[U, P, (U0, P0) :: MT, (U, P) :: (U0, P0) :: MT] =
      new InsertSortedUnitSig[U, P, (U0, P0) :: MT] { type Out = (U, P) :: (U0, P0) :: MT }

    implicit def evidence2[U, P, U0, P0, MT <: HList, O <: HList](implicit gt: XIntGT.Aux[P, P0, True], rc: Aux[U, P, MT, O]): Aux[U, P, (U0, P0) :: MT, (U0, P0) :: O] =
      new InsertSortedUnitSig[U, P, (U0, P0) :: MT] { type Out = (U0, P0) :: O }

  }

  trait SortUnitSig[M, N, D] {
    type OutN
    type OutD
  }

  object SortUnitSig {
    type Aux[M, N, D, ON, OD] = SortUnitSig[M, N, D] { type OutN = ON; type OutD = OD }

    implicit def evidence0[N, D]: Aux[HNil, N, D, N, D] =
      new SortUnitSig[HNil, N, D] { type OutN = N; type OutD = D }

    implicit def evidence1[U, P, MT <: HList, N, D, NO, NF, DF](implicit pos: XIntGT.Aux[P, XInt0, True], ins: InsertSortedUnitSig.Aux[U, P, N, NO], rc: Aux[MT, NO, D, NF, DF]): Aux[(U, P) :: MT, N, D, NF, DF] =
      new SortUnitSig[(U, P) :: MT, N, D] { type OutN = NF; type OutD = DF }

    implicit def evidence2[U, P, MT <: HList, N, D, NP, DO, NF, DF](implicit neg: XIntLT.Aux[P, XInt0, True], n: XIntNeg.Aux[P, NP], ins: InsertSortedUnitSig.Aux[U, NP, D, DO], rc: Aux[MT, N, DO, NF, DF]): Aux[(U, P) :: MT, N, D, NF, DF] =
      new SortUnitSig[(U, P) :: MT, N, D] { type OutN = NF; type OutD = DF }
  }

  trait SigToUnit[S] {
    type Out
  }
  object SigToUnit {
    type Aux[S, U] = SigToUnit[S] { type Out = U }
    implicit def evidence0: Aux[HNil, Unitless] = new SigToUnit[HNil] { type Out = Unitless }

    implicit def evidence1[U, ST <: HList, UC](implicit cont: Aux[ST, UC]): Aux[(U, XInt0) :: ST, UC] =
      new SigToUnit[(U, XInt0) :: ST] { type Out = UC }

    implicit def evidence2[U, ST <: HList, UC](implicit cont: Aux[ST, UC], nu: UC =:!= Unitless): Aux[(U, XInt1) :: ST, %*[U, UC]] =
      new SigToUnit[(U, XInt1) :: ST] { type Out = %*[U, UC] }

    implicit def evidence3[U, P, ST <: HList, UC](implicit ne01: XIntNon01.Aux[P, True], cont: Aux[ST, UC], nu: UC =:!= Unitless): Aux[(U, P) :: ST, %*[%^[U, P], UC]] =
      new SigToUnit[(U, P) :: ST] { type Out = %*[%^[U, P], UC] }

    implicit def evidence4[U, ST <: HList](implicit cont: Aux[ST, Unitless]): Aux[(U, XInt1) :: ST, U] =
      new SigToUnit[(U, XInt1) :: ST] { type Out = U }

    implicit def evidence5[U, P, ST <: HList](implicit ne01: XIntNon01.Aux[P, True], cont: Aux[ST, Unitless]): Aux[(U, P) :: ST, %^[U, P]] =
      new SigToUnit[(U, P) :: ST] { type Out = %^[U, P] }
  }

  def tests2u[S](implicit s2u: SigToUnit[S]): TestResult[s2u.Out] = new TestResult[s2u.Out]()

  trait MulResultType[LU, RU] {
    type Out
  }
  object MulResultType {
    type Aux[LU, RU, O] = MulResultType[LU, RU] { type Out = O }

    implicit def result[LU, RU, OL, OR, OU, RT](implicit
      cnL: StandardSig.Aux[LU, OL],
      cnR: StandardSig.Aux[RU, OR],
      mu: UnifyKVMul.Aux[OL, OR, OU],
      rt: SigToUnit.Aux[OU, RT]): Aux[LU, RU, RT] =
      new MulResultType[LU, RU] { type Out = RT }
  }

  trait DivResultType[LU, RU] {
    type Out
  }
  object DivResultType {
    type Aux[LU, RU, O] = DivResultType[LU, RU] { type Out = O }

    implicit def result[LU, RU, OL, OR, OU, RT](implicit
        cnL: StandardSig.Aux[LU, OL],
        cnR: StandardSig.Aux[RU, OR],
        mu: UnifyKVDiv.Aux[OR, OL, OU],
        rt: SigToUnit.Aux[OU, RT]): Aux[LU, RU, RT] =
      new DivResultType[LU, RU] { type Out = RT }
  }

  def ctest[U](implicit u: CanonicalSig[U]): TestResult[u.Out] = TestResult[u.Out]()

  trait PowResultType[U, P] {
    type Out
  }
  object PowResultType {
    type Aux[U, P, O] = PowResultType[U, P] { type Out = O }
    implicit def evidence[U, P, SS, AP, RT](implicit
        ss: StandardSig.Aux[U, SS],
        ap: ApplyKVPow.Aux[P, SS, AP],
        rt: SigToUnit.Aux[AP, RT]): Aux[U, P, RT] =
      new PowResultType[U, P] { type Out = RT }
  }

  class ConvertableUnits[U1, U2](val coef: Rational)

  object ConvertableUnits {
    implicit def witnessCU[U1, U2, C1, C2](implicit u1: CanonicalSig.Aux[U1, C1], u2: CanonicalSig.Aux[U2, C2], eq: SetEqual.Aux[C1, C2, True]): ConvertableUnits[U1, U2] =
      new ConvertableUnits[U1, U2](u1.coef / u2.coef)
  }

  def coefficient[U1, U2](implicit cu: ConvertableUnits[U1, U2]): Rational = cu.coef

  trait Converter[N1, U1, N2, U2] {
    def apply(v: N1): N2
  }
  trait ConverterDefaultPriority {
    // This should be specialized for efficiency, however this rule would give an accurate conversion for any type
    implicit def witness[N1, U1, N2, U2](implicit cu: ConvertableUnits[U1, U2], cfN1: Numeric[N1], ctN2: Numeric[N2]): Converter[N1, U1, N2, U2] =
      new Converter[N1, U1, N2, U2] {
        def apply(v: N1): N2 = ctN2.fromType[Rational](cfN1.toType[Rational](v) * cu.coef)
      }
  }
  object Converter extends ConverterDefaultPriority {
    implicit def witnessDouble[U1, U2](implicit cu: ConvertableUnits[U1, U2]): Converter[Double, U1, Double, U2] = {
      val coef = cu.coef.toDouble
      new Converter[Double, U1, Double, U2] {
        def apply(v: Double): Double = v * coef
      }
    }
  }

  trait UnitStringAST
  object UnitStringAST {
    case object Uni extends UnitStringAST
    case class Def(d: UnitDefinition) extends UnitStringAST
    case class Pre(p: UnitDefinition) extends UnitStringAST
    case class Mul(l: UnitStringAST, r: UnitStringAST) extends UnitStringAST
    case class Div(n: UnitStringAST, d: UnitStringAST) extends UnitStringAST
    case class Pow(b: UnitStringAST, e: Int) extends UnitStringAST
  }

  trait HasUnitStringAST[U] {
    def ast: UnitStringAST
    override def toString = ast.toString
  }
  object HasUnitStringAST {
    import UnitStringAST._

    implicit def evidence0: HasUnitStringAST[Unitless] =
      new HasUnitStringAST[Unitless] { val ast = Uni }

    implicit def evidence1[P](implicit d: DerivedUnit[P, Unitless]): HasUnitStringAST[P] =
      new HasUnitStringAST[P] { val ast = Pre(d) }

    implicit def evidence2[U, D](implicit d: DerivedUnit[U, D], nu: D =:!= Unitless): HasUnitStringAST[U] =
      new HasUnitStringAST[U] { val ast = Def(d) }

    implicit def evidence3[U](implicit d: BaseUnit[U]): HasUnitStringAST[U] =
      new HasUnitStringAST[U] { val ast = Def(d) }

    implicit def evidence4[L, R](implicit l: HasUnitStringAST[L], r: HasUnitStringAST[R]): HasUnitStringAST[%*[L, R]] =
      new HasUnitStringAST[%*[L, R]] { val ast = Mul(l.ast, r.ast) }

    implicit def evidence5[N, D](implicit n: HasUnitStringAST[N], d: HasUnitStringAST[D]): HasUnitStringAST[%/[N, D]] =
      new HasUnitStringAST[%/[N, D]] { val ast = Div(n.ast, d.ast) }

    implicit def evidence6[B, E](implicit b: HasUnitStringAST[B], e: XIntValue[E]): HasUnitStringAST[%^[B, E]] =
      new HasUnitStringAST[%^[B, E]] { val ast = Pow(b.ast, e.value) }
  }

  trait UnitString[U] {
    def full: String
    def abbv: String
  }
  object UnitString {
    import UnitStringAST._

    implicit def evidence[U](implicit uast: HasUnitStringAST[U]): UnitString[U] = {
      val fs = render(uast.ast, (d: UnitDefinition) => d.name)
      val as = render(uast.ast, (d: UnitDefinition) => d.abbv)
      new UnitString[U] {
        val full = fs
        val abbv = as
      }
    }

    def render(ast: UnitStringAST, f: UnitDefinition => String): String = ast match {
      case FlatMul(t) => termStrings(t, f).mkString(" ")
      case Div(Uni, d) => s"1/${paren(d, f)}"
      case Div(n, Uni) => render(n, f)
      case Div(n, d) => s"${paren(n, f)}/${paren(d, f)}"
      case Pow(b, e) => {
        val es = if (e < 0) s"($e)" else s"$e"
        s"${paren(b, f)}^$es"
      }
      case Uni => "unitless"
      case Def(d) => f(d)
      case Pre(d) => f(d)
      case _ => "!!!"
    }

    def paren(ast: UnitStringAST, f: UnitDefinition => String): String = {
      val str = render(ast, f)
      if (isAtomic(ast)) str else s"($str)"
    }

    object FlatMul {
      def unapply(ast: UnitStringAST): Option[List[UnitStringAST]] = ast match {
        case Mul(l, r) => {
          val lflat = l match {
            case FlatMul(lf) => lf
            case _ => List(l)
          }
          val rflat = r match {
            case FlatMul(rf) => rf
            case _ => List(r)
          }
          Option(lflat ++ rflat)
        }
        case _ => None
      }
    }

    def termStrings(terms: List[UnitStringAST], f: UnitDefinition => String): List[String] = terms match {
      case Nil => Nil
      case Pre(p) +: Def(d) +: tail => s"${f(p)}${f(d)}" :: termStrings(tail, f)
      case term +: tail => s"${paren(term, f)}" :: termStrings(tail, f)
      case _ => List("!!!")
    }

    def isAtomic(ast: UnitStringAST): Boolean = ast match {
      case Uni => true
      case Pre(_) => true
      case Def(_) => true
      case Pow(Def(_), _) => true
      case Pow(FlatMul(Pre(_) +: Def(_) +: Nil), _) => true
      case FlatMul(Pre(_) +: Def(_) +: Nil) => true
      case _ => false
    }
  }

  trait UnitOps[N, U] {
    def n: Numeric[N]
    def ustr: UnitString[U]
  }
  object UnitOps {
    implicit def evidence[N, U](implicit nn: Numeric[N], us: UnitString[U]): UnitOps[N, U] =
      new UnitOps[N, U] {
        val n = nn
        val ustr = us
      }
  }

  trait UnitPowerOps[N, U, P] {
    def n: Numeric[N]
    type PowRT
  }
  object UnitPowerOps {
    type Aux[N, U, P, PRT] = UnitPowerOps[N, U, P] { type PowRT = PRT }
    implicit def evidence[N, U, P](implicit
        nn: Numeric[N],
        prt: PowResultType[U, P]): Aux[N, U, P, prt.Out] =
      new UnitPowerOps[N, U, P] {
        type PowRT = prt.Out
        val n = nn
      }
  }

  trait UnitBinaryOps[N1, U1, N2, U2] {
    def n1: Numeric[N1]
    def n2: Numeric[N2]
    def cv12: Converter[N1, U1, N2, U2]
    def cv21: Converter[N2, U2, N1, U1]
    def cn12(x: N1): N2
    def cn21(x: N2): N1
    type MulRT12
    type MulRT21
    type DivRT12
    type DivRT21
  }

  object UnitBinaryOps {
    type Aux[N1, U1, N2, U2, MR12, MR21, DR12, DR21] = UnitBinaryOps[N1, U1, N2, U2] {
      type MulRT12 = MR12
      type MulRT21 = MR21
      type DivRT12 = DR12
      type DivRT21 = DR21      
    }
    implicit def evidence[N1, U1, N2, U2](implicit
        nn1: Numeric[N1],
        nn2: Numeric[N2],
        cvv12: Converter[N1, U1, N2, U2],
        cvv21: Converter[N2, U2, N1, U1],
        mrt12: MulResultType[U1, U2],
        mrt21: MulResultType[U2, U1],
        drt12: DivResultType[U1, U2],
        drt21: DivResultType[U2, U1]): Aux[N1, U1, N2, U2, mrt12.Out, mrt21.Out, drt12.Out, drt21.Out] =
      new UnitBinaryOps[N1, U1, N2, U2] {
        val n1 = nn1
        val n2 = nn2
        val cv12 = cvv12
        val cv21 = cvv21
        def cn12(x: N1): N2 = nn1.toType[N2](x)
        def cn21(x: N2): N1 = nn2.toType[N1](x)
        type MulRT12 = mrt12.Out
        type MulRT21 = mrt21.Out
        type DivRT12 = drt12.Out
        type DivRT21 = drt21.Out
      }
  }

  class Quantity[N, U](val value: N)
    extends AnyVal with Serializable {

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

  implicit class WithUnit[N](v: N) {
    def withUnit[U]: Quantity[N, U] = new Quantity[N, U](v)
  }

  trait Meter
  implicit val buMeter = BaseUnit[Meter]()

  trait Yard
  implicit val defineUnitYard = DerivedUnit[Yard, Meter](Rational(9144, 10000), abbv = "yd")

  trait Foot
  implicit val defineUnitFoot = DerivedUnit[Foot, Yard](Rational(1, 3), abbv = "ft")

  trait Second
  implicit val buSecond = BaseUnit[Second]()

  trait Minute
  implicit val duMinute = DerivedUnit[Minute, Second](Rational(60), abbv="min")

  trait Kilo
  implicit val defineUnitKilo = PrefixUnit[Kilo](Rational(1000))

  trait Mega
  implicit val defineUnitMega = PrefixUnit[Mega](Rational(10).pow(6))

  trait Kelvin
  implicit val defineUnitKelvin = BaseUnit[Kelvin](name = "Kelvin", abbv = "K")

  trait Celsius
  implicit val defineUnitCelsius = DerivedTemp[Celsius](coef = 1, off = Rational(27315, 100), name = "Celsius", abbv = "C")

  trait Fahrenheit
  implicit val defineUnitFahrenheit = DerivedTemp[Fahrenheit](coef = Rational(5, 9), off = Rational(45967, 100), name = "Fahrenheit", abbv = "F")

  class DerivedTemp[U](coef: Rational, val off: Rational, name: String, abbv: String) extends DerivedUnit[U, Kelvin](coef, name, abbv) {
    override def toString = s"DerivedTemp($coef, $off, $name, $abbv)"
  }
  object DerivedTemp {
    def apply[U](coef: Rational = Rational(1), off: Rational = Rational(0), name: String, abbv: String)(implicit
      ut: TypeTag[U]): DerivedTemp[U] = new DerivedTemp[U](coef, off, name, abbv)

    // A slight hack that is used by TempConverter to simplify its rules
    implicit def evidencek2k: DerivedTemp[Kelvin] = DerivedTemp[Kelvin](name = "Kelvin", abbv = "K")
  }

  trait TempConverter[N1, U1, N2, U2] {
    def apply(v: N1): N2
  }
  trait TempConverterDefaultPriority {
    // this default rule should work well everywhere but may be overridden for efficiency
    implicit def evidence[N1, U1, N2, U2](implicit
        t1: DerivedTemp[U1], t2: DerivedTemp[U2],
        n1: Numeric[N1], n2: Numeric[N2]): TempConverter[N1, U1, N2, U2] = {
      val coef = t1.coef / t2.coef
      new TempConverter[N1, U1, N2, U2] {
        def apply(v: N1): N2 = {
          n2.fromType[Rational](((n1.toType[Rational](v) + t1.off) * coef) - t2.off)
        }
      }
    }
  }
  object TempConverter extends TempConverterDefaultPriority {
    // override the default temp-converter generation here for specific cases
  }

  trait TempOps[N, U] {
    def n: Numeric[N]
    def ustr: UnitString[U]
  }
  object TempOps {
    implicit def evidence[N, U](implicit
        t2k: DerivedTemp[U],
        nn: Numeric[N],
        us: UnitString[U]): TempOps[N, U] =
      new TempOps[N, U] {
        val n = nn
        val ustr = us
      }
  }

  trait TempBinaryOps[N1, U1, N2, U2] {
    def n1: Numeric[N1]
    def n2: Numeric[N2]
    def cv12: TempConverter[N1, U1, N2, U2]
    def cv21: TempConverter[N2, U2, N1, U1]
    def cn12(x: N1): N2
    def cn21(x: N2): N1
  }

  object TempBinaryOps {
    type Aux[N1, U1, N2, U2] = TempBinaryOps[N1, U1, N2, U2] {
    }
    implicit def evidence[N1, U1, N2, U2](implicit
        nn1: Numeric[N1],
        nn2: Numeric[N2],
        cvv12: TempConverter[N1, U1, N2, U2],
        cvv21: TempConverter[N2, U2, N1, U1]): Aux[N1, U1, N2, U2] =
      new TempBinaryOps[N1, U1, N2, U2] {
        val n1 = nn1
        val n2 = nn2
        val cv12 = cvv12
        val cv21 = cvv21
        def cn12(x: N1): N2 = nn1.toType[N2](x)
        def cn21(x: N2): N1 = nn2.toType[N1](x)
      }
  }

  class Temperature[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
    override def toString = s"Temperature($value)"

    def show(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.abbv}"

    def showFull(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.full}"

    def showUnit(implicit uo: TempOps[N, U]): String = uo.ustr.abbv

    def showUnitFull(implicit uo: TempOps[N, U]): String = uo.ustr.full

    def -[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Quantity[N, U] =
      new Quantity[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

    def +[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Temperature[N, U] =
      new Temperature[N, U](ubo.n1.plus(value, ubo.cv21(rhs.value)))

    def -[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Temperature[N, U] =
      new Temperature[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

    def ===[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
      ubo.n1.compare(value, ubo.cv21(rhs.value)) == 0

    def =!=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
      ubo.n1.compare(value, ubo.cv21(rhs.value)) != 0

    def <[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
      ubo.n1.compare(value, ubo.cv21(rhs.value)) < 0

    def <=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
      ubo.n1.compare(value, ubo.cv21(rhs.value)) <= 0

    def >[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
      ubo.n1.compare(value, ubo.cv21(rhs.value)) > 0

    def >=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
      ubo.n1.compare(value, ubo.cv21(rhs.value)) >= 0

    def toUnit[U2](implicit ubo: TempBinaryOps[N, U, N, U2]): Temperature[N, U2] =
      new Temperature[N, U2](ubo.cv12(value))

    def toNumeric[N2](implicit ubo: TempBinaryOps[N, U, N2, U]): Temperature[N2, U] =
      new Temperature[N2, U](ubo.cn12(value))

    def to[N2, U2](implicit ubo: TempBinaryOps[N, U, N2, U2]): Temperature[N2, U2] =
      new Temperature[N2, U2](ubo.cv12(value))
  }

  implicit class WithTemperature[N](v: N) {
    // requires a derived-temp for U
    def withTemp[U](implicit t2k: DerivedTemp[U]): Temperature[N, U] = new Temperature[N, U](v)
  }

  def foo[N, U, RT](x: Quantity[N, U])(implicit ubo: UnitBinaryOps.Aux[N, U, N, U, RT, _, _, _], uot: UnitOps[N, RT]) = -(x * x)
  def goo(x: Quantity[Int, Second]) = -(x * x)
}
