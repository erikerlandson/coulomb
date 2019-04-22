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

trait UnitString[U] {
  def full: String
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

trait UnitMultiply[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def cn21(x: N2): N1
  type RT12
}
object UnitMultiply {
  type Aux[N1, U1, N2, U2, R12] = UnitMultiply[N1, U1, N2, U2] {
    type RT12 = R12
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      mrt12: MulResultType[U1, U2]): Aux[N1, U1, N2, U2, mrt12.Out] =
    new UnitMultiply[N1, U1, N2, U2] {
      val n1 = nn1
      def cn21(x: N2): N1 = n1.fromType[N2](x)
      type RT12 = mrt12.Out
    }
}

trait UnitDivide[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def cn21(x: N2): N1
  type RT12
}
object UnitDivide {
  type Aux[N1, U1, N2, U2, R12] = UnitDivide[N1, U1, N2, U2] {
    type RT12 = R12
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      drt12: DivResultType[U1, U2]): Aux[N1, U1, N2, U2, drt12.Out] =
    new UnitDivide[N1, U1, N2, U2] {
      val n1 = nn1
      def cn21(x: N2): N1 = n1.fromType[N2](x)
      type RT12 = drt12.Out
    }
}

trait UnitPower[N, U, P] {
  def n: Numeric[N]
  def p: Int
  type PowRT
}
object UnitPower {
  type Aux[N, U, P, PRT] = UnitPower[N, U, P] { type PowRT = PRT }
  implicit def evidence[N, U, P](implicit
      nn: Numeric[N],
      xivP: XIntValue[P],
      prt: PowResultType[U, P]): Aux[N, U, P, prt.Out] =
    new UnitPower[N, U, P] {
      type PowRT = prt.Out
      val n = nn
      val p = xivP.value
    }
}

class ConvertableUnits[U1, U2](val coef: Rational)

object ConvertableUnits {
  implicit def witnessCU[U1, U2, C1, C2](implicit
      u1: CanonicalSig.Aux[U1, C1],
      u2: CanonicalSig.Aux[U2, C2],
      eq: SetEqual.Aux[C1, C2, True]): ConvertableUnits[U1, U2] =
    new ConvertableUnits[U1, U2](u1.coef / u2.coef)
}

trait UnitConverter[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def n2: Numeric[N2]
  def cv12(v: N1): N2
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
object UnitConverter extends UnitConverterDefaultPriority {
  implicit def witnessDouble[U1, U2](implicit
      cu: ConvertableUnits[U1, U2],
      nD: Numeric[Double]): UnitConverter[Double, U1, Double, U2] = {
    val coef = cu.coef.toDouble
    new UnitConverter[Double, U1, Double, U2] {
      val n1 = nD
      val n2 = nD
      def cv12(v: Double): Double = v * coef
      def cv21(v: Double): Double = v / coef
    }
  }
}
