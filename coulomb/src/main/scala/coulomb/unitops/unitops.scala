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

trait UnitProductOps[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def n2: Numeric[N2]
  def cn12(x: N1): N2
  def cn21(x: N2): N1
  type MulRT12
  type MulRT21
  type DivRT12
  type DivRT21
}
object UnitProductOps {
  type Aux[N1, U1, N2, U2, MR12, MR21, DR12, DR21] = UnitProductOps[N1, U1, N2, U2] {
    type MulRT12 = MR12
    type MulRT21 = MR21
    type DivRT12 = DR12
    type DivRT21 = DR21      
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      mrt12: MulResultType[U1, U2],
      mrt21: MulResultType[U2, U1],
      drt12: DivResultType[U1, U2],
      drt21: DivResultType[U2, U1]): Aux[N1, U1, N2, U2, mrt12.Out, mrt21.Out, drt12.Out, drt21.Out] =
    new UnitProductOps[N1, U1, N2, U2] {
      val n1 = nn1
      val n2 = nn2
      def cn12(x: N1): N2 = nn1.toType[N2](x)
      def cn21(x: N2): N1 = nn2.toType[N1](x)
      type MulRT12 = mrt12.Out
      type MulRT21 = mrt21.Out
      type DivRT12 = drt12.Out
      type DivRT21 = drt21.Out
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
