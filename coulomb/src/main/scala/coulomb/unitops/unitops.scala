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
import spire.algebra._

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
trait UnitMul[N1, U1, N2, U2] {
  /** multiply numeric values, returning "left hand" type N1 */
  def vmul(v1: N1, v2: N2): N1
  /** a type representing the unit product `U1*U2` */
  type RT
}
object UnitMul {
  type Aux[N1, U1, N2, U2, R12] = UnitMul[N1, U1, N2, U2] {
    type RT = R12
  }
  implicit def evidenceMSG1[N1, U1, N2, U2](implicit
      ms1: MultiplicativeSemigroup[N1],
      uc: UnitConverter[N2, U2, N1, U2],
      mrt12: MulResultType[U1, U2]): Aux[N1, U1, N2, U2, mrt12.Out] =
    new UnitMul[N1, U1, N2, U2] {
      def vmul(v1: N1, v2: N2): N1 = ms1.times(v1, uc.vcnv(v2))
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
trait UnitDivideP2 {
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
trait UnitDivideP1 extends UnitDivideP2 {
  implicit def evidenceMG1[N1, U1, N2, U2](implicit
      mg1: MultiplicativeGroup[N1],
      ct1: ConvertableTo[N1],
      cf2: ConvertableFrom[N2],
      mrt12: DivResultType[U1, U2]): Aux[N1, U1, N2, U2, mrt12.Out] =
    new UnitDivide[N1, U1, N2, U2] {
      def vdiv(v1: N1, v2: N2): N1 = mg1.div(v1, ct1.fromType[N2](v2))
      type RT = mrt12.Out
    }
}
object UnitDivide extends UnitDivideP1 {
  implicit def evidenceMG0[N, U1, U2](implicit
      mg: MultiplicativeGroup[N],
      drt12: DivResultType[U1, U2]): Aux[N, U1, N, U2, drt12.Out] =
    new UnitDivide[N, U1, N, U2] {
      def vdiv(v1: N, v2: N): N = mg.div(v1, v2)
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
  // a pure semigroup will fail for p <= 0, but
  // this also includes monoids (allows p=0) and groups (p<0)
  implicit def evidenceMSG0[N, U, P](implicit
      ms: MultiplicativeSemigroup[N],
      xivP: XIntValue[P],
      prt: PowResultType[U, P]): Aux[N, U, P, prt.Out] =
    new UnitPower[N, U, P] {
      type RT = prt.Out
      val p = xivP.value
      def vpow(v: N): N = ms.pow(v, p)
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
  implicit def evidenceASG0[N1, U1, N2, U2](implicit
      as1: AdditiveSemigroup[N1],
      uc: UnitConverter[N2, U2, N1, U1]): UnitAdd[N1, U1, N2, U2] =
    new UnitAdd[N1, U1, N2, U2] {
      def vadd(v1: N1, v2: N2): N1 = as1.plus(v1, uc.vcnv(v2))
    }
}

/**
 * An implicit trait that supports compile-time unit quantity subtraction
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait UnitSub[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and subtract from v1 */
  def vsub(v1: N1, v2: N2): N1
}
object UnitSub {
  implicit def evidence[N1, U1, N2, U2](implicit
      ag1: AdditiveGroup[N1],
      uc: UnitConverter[N2, U2, N1, U1]): UnitSub[N1, U1, N2, U2] =
    new UnitSub[N1, U1, N2, U2] {
      def vsub(v1: N1, v2: N2): N1 = ag1.minus(v1, uc.vcnv(v2))
    }
}

/**
 * An implicit trait that supports compile-time unit quantity negation
 * @tparam N the numeric type of the quantity value
 */
trait UnitNeg[N] {
  /** negate the unit's value */
  def vneg(v: N): N
}
object UnitNeg {
  implicit def evidence[N](implicit ag: AdditiveGroup[N]): UnitNeg[N] =
    new UnitNeg[N] {
      def vneg(v: N): N = ag.negate(v)
    }
}

/**
 * An implicit trait that supports compile-time unit comparisons / ordering
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait UnitCompare[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and compare to v1 */
  def vcmp(v1: N1, v2: N2): Int
}
object UnitCompare {
  implicit def evidence[N1, U1, N2, U2](implicit
      n1: Numeric[N1],
      n2: Numeric[N2],
      uc: UnitConverter[N2, U2, N1, U1]): UnitCompare[N1, U1, N2, U2] =
    new UnitCompare[N1, U1, N2, U2] {
      def vcmp(v1: N1, v2: N2): Int = n1.compare(v1, uc.vcnv(v2))
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
 * Define a customizable unit conversion policy.
 * If such an implicitly defined policy exists, it will override
 * any built-in policies defined for UnitConverter.
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expression type of the quantity
 * @tparam N2 numeric type of another quantity value
 * @tparam U2 unit expression type of the other quantity
 */
trait UnitConverterPolicy[N1, U1, N2, U2] {
  /**
   * a conversion from value with type `(N1,U1)` to type `(N2,U2)`, given
   * the conversion factor supplied on `cu`
   */
  def convert(v: N1, cu: ConvertableUnits[U1, U2]): N2
}

/**
 * An implicit trait that supports compile-time unit quantity conversion, when possible.
 * Also implements conversion from N1 to N2.
 * This implicit will not exist if U1 and U2 are not convertable to one another.
 * It will also not exist if there is no defined conversion from N1 to N2.
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expression type of the quantity
 * @tparam N2 numeric type of another quantity value
 * @tparam U2 unit expression type of the other quantity
 */
trait UnitConverter[N1, U1, N2, U2] {
  /** a conversion from value with type `(N1,U1)` to type `(N2,U2)` */
  def vcnv(v: N1): N2
}
trait UnitConverterDefaultPriority {
  // This could be specialized for efficiency, however this rule will
  // give an accurate conversion for any types N1 and N2 with Numeric typeclass
  implicit def witness[N1, U1, N2, U2](implicit
      cu: ConvertableUnits[U1, U2],
      n1: Numeric[N1],
      n2: Numeric[N2]): UnitConverter[N1, U1, N2, U2] =
    new UnitConverter[N1, U1, N2, U2] {
      def vcnv(v: N1): N2 = n2.fromType[Rational](n1.toType[Rational](v) * cu.coef)
    }
}
trait UnitConverterP1 extends UnitConverterDefaultPriority {
  implicit def witnessDouble[U1, U2](implicit
      cu: ConvertableUnits[U1, U2]): UnitConverter[Double, U1, Double, U2] = {
    val coef = cu.coef.toDouble
    new UnitConverter[Double, U1, Double, U2] {
      @inline def vcnv(v: Double): Double = v * coef
    }
  }
  implicit def witnessFloat[U1, U2](implicit
      cu: ConvertableUnits[U1, U2]): UnitConverter[Float, U1, Float, U2] = {
    val coef = cu.coef.toFloat
    new UnitConverter[Float, U1, Float, U2] {
      @inline def vcnv(v: Float): Float = v * coef
    }
  }
}
trait UnitConverterP0 extends UnitConverterP1 {
  implicit def witnessIdentity[N, U]: UnitConverter[N, U, N, U] = {
    new UnitConverter[N, U, N, U] {
      @inline def vcnv(v: N): N = v
    }
  }
}
object UnitConverter extends UnitConverterP0 {
  // I'm allowing customized policies to override even the "identity" rule above.
  // Practically this means someone customizing a policy has to do a bit of extra
  // work to separate optimization cases but philosophically it feels right
  // in the sense that it allows anyone who wants to customize the opportunity
  // for total control.
  implicit def witnessCustomPolicy[N1, U1, N2, U2](implicit
      cu: ConvertableUnits[U1, U2],
      ucp: UnitConverterPolicy[N1, U1, N2, U2]): UnitConverter[N1, U1, N2, U2] = {
    new UnitConverter[N1, U1, N2, U2] {
      def vcnv(v: N1): N2 = ucp.convert(v, cu)
    }
  }
}
