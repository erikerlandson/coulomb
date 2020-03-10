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

package coulomb.unitops

import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import spire.math._

import coulomb.infra._

import coulomb.define.UnitDefinition

/**
 * An implicit trait that allows compile-time access to unit names and abbreviations.
 * This includes compound unit expressions as well as base units and derived units.
 * @tparam U the unit, or unit expression
 */
trait UnitString[U] {
  /** the full name of a unit, or expression using full unit names */
  def full: String
  /** the abbreviation of a unit, or expression using unit abbreviations */
  def abbv: String
}
object UnitString {
  import UnitStringAST._

  implicit def evidence[U](implicit uast: HasUnitStringAST[U]): UnitString[U] = {
    val fs = UnitStringAST.render(uast.ast, (d: UnitDefinition) => d.name)
    val as = UnitStringAST.render(uast.ast, (d: UnitDefinition) => d.abbv)
    new UnitString[U] {
      val full = fs
      val abbv = as
    }
  }
}

/**
 * An implicit trait that supports compile-time unit quantity multiplication
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait UnitMultiply[N1, U1, N2, U2] {
  /** multiply numeric values, returning "left hand" type N1 */
  def vmul(v1: N1, v2: N2): N1
  /** a type representing the unit product `U1*U2` */
  type RT
}
object UnitMultiply {
  type Aux[N1, U1, N2, U2, R12] = UnitMultiply[N1, U1, N2, U2] {
    type RT = R12
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      n1: Numeric[N1],
      n2: Numeric[N2],
      mrt12: MulResultType[U1, U2]): Aux[N1, U1, N2, U2, mrt12.Out] =
    new UnitMultiply[N1, U1, N2, U2] {
      def vmul(v1: N1, v2: N2): N1 = n1.times(v1, n1.fromType[N2](v2))
      type RT = mrt12.Out
    }
}

/**
 * An implicit trait that supports compile-time unit quantity division
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait UnitDivide[N1, U1, N2, U2] {
  /** divide numeric values, returning "left hand" type N1 */
  def vdiv(v1: N1, v2: N2): N1
  /** a type representing the unit division `U1/U2` */
  type RT
}
object UnitDivide {
  type Aux[N1, U1, N2, U2, R12] = UnitDivide[N1, U1, N2, U2] {
    type RT = R12
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      n1: Numeric[N1],
      n2: Numeric[N2],
      drt12: DivResultType[U1, U2]): Aux[N1, U1, N2, U2, drt12.Out] =
    new UnitDivide[N1, U1, N2, U2] {
      def vdiv(v1: N1, v2: N2): N1 = n1.div(v1, n1.fromType[N2](v2))
      type RT = drt12.Out
    }
}

/**
 * An implicit trait that supports compile time unit exponents
 * @tparam N the numeric type of a quantity value
 * @tparam U the unit expression type of the quantity
 * @tparam P a literal type representing an integer exponent
 */
trait UnitPower[N, U, P] {
  /** the integer value of literal type exponent P */
  def p: Int
  /** returns a value raised to the power P */
  def vpow(v: N): N
  /** a unit type corresponding to `U^P` */
  type RT
}
object UnitPower {
  type Aux[N, U, P, PRT] = UnitPower[N, U, P] { type RT = PRT }
  implicit def evidence[N, U, P](implicit
      n: Numeric[N],
      xivP: XIntValue[P],
      prt: PowResultType[U, P]): Aux[N, U, P, prt.Out] =
    new UnitPower[N, U, P] {
      type RT = prt.Out
      val p = xivP.value
      def vpow(v: N): N = n.pow(v, p)
    }
}

/**
 * An implicit trait that supports compile-time unit quantity addition
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait UnitAdd[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and add to v1  */
  def vadd(v1: N1, v2: N2): N1
}
object UnitAdd {
  implicit def evidence[N1, U1, N2, U2](implicit
      n1: Numeric[N1],
      n2: Numeric[N2],
      uc: UnitConverter[N1, U1, N2, U2]): UnitAdd[N1, U1, N2, U2] =
    new UnitAdd[N1, U1, N2, U2] {
      def vadd(v1: N1, v2: N2): N1 = n1.plus(v1, uc.cv21(v2))
    }
}

/**
 * An implicit trait that supports compile time checking of whether
 * two unit types are convertable (aka compatible), and if so what
 * their coefficient of conversion is.
 * This implicit value will not exist if U1 and U2 are not convertable to one another.
 * @tparam U1 a unit expression type
 * @tparam U2 another unit expression type
 */
class ConvertableUnits[U1, U2](val coef: Rational)
object ConvertableUnits {
  import coulomb.%/
  implicit def witnessCU[U1, U2](implicit cs: CanonicalSig.Aux[%/[U1, U2], HNil]): ConvertableUnits[U1, U2] =
    new ConvertableUnits[U1, U2](cs.coef)
}

/**
 * An implicit trait that supports compile-time unit quantity conversion, when possible.
 * Also used to support addition, subtraction and comparisons of quantities.
 * This implicit will not exist if U1 and U2 are not convertable to one another.
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of another quantity value
 * @tparam U2 unit expression type of the other quantity
 */
trait UnitConverter[N1, U1, N2, U2] {
  /** the `Numeric` implicit for quantity numeric type N1 */
  def n1: Numeric[N1]
  /** the `Numeric` implicit for quantity numeric type N2 */
  def n2: Numeric[N2]
  /** a conversion from value with type `(N1,U1)` to type `(N2,U2)` */
  def cv12(v: N1): N2
  /** a conversion from value with type `(N2,U2)` to type `(N1,U1)` */
  def cv21(v: N2): N1
}
trait UnitConverterDefaultPriority {
  // This should be specialized for efficiency, however this rule would give an accurate conversion for any type
  implicit def witness[N1, U1, N2, U2](implicit
      cu: ConvertableUnits[U1, U2],
      nN1: Numeric[N1],
      nN2: Numeric[N2]): UnitConverter[N1, U1, N2, U2] =
    new UnitConverter[N1, U1, N2, U2] {
      val n1 = nN1
      val n2 = nN2
      def cv12(v: N1): N2 = nN2.fromType[Rational](nN1.toType[Rational](v) * cu.coef)
      def cv21(v: N2): N1 = nN1.fromType[Rational](nN2.toType[Rational](v) / cu.coef)
    }
}
trait UnitConverterP1 extends UnitConverterDefaultPriority {
  implicit def witnessDouble[U1, U2](implicit
      cu: ConvertableUnits[U1, U2],
      n: Numeric[Double]): UnitConverter[Double, U1, Double, U2] = {
    val coef = cu.coef.toDouble
    new UnitConverter[Double, U1, Double, U2] {
      val n1 = n
      val n2 = n
      @inline def cv12(v: Double): Double = v * coef
      @inline def cv21(v: Double): Double = v / coef
    }
  }
  implicit def witnessFloat[U1, U2](implicit
      cu: ConvertableUnits[U1, U2],
      n: Numeric[Float]): UnitConverter[Float, U1, Float, U2] = {
    val coef = cu.coef.toFloat
    new UnitConverter[Float, U1, Float, U2] {
      val n1 = n
      val n2 = n
      @inline def cv12(v: Float): Float = v * coef
      @inline def cv21(v: Float): Float = v / coef
    }
  }
}
object UnitConverter extends UnitConverterP1 {
  implicit def witnessIdentity[N, U](implicit
      n: Numeric[N]): UnitConverter[N, U, N, U] = {
    new UnitConverter[N, U, N, U] {
      val n1 = n
      val n2 = n
      @inline def cv12(v: N): N = v
      @inline def cv21(v: N): N = v
    }
  }
}
