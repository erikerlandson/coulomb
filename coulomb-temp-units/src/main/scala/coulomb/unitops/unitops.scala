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

import spire.math._

import coulomb.infra._
import coulomb.define._

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

trait TempConverterOps[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def n2: Numeric[N2]
  def cv12: TempConverter[N1, U1, N2, U2]
  def cv21: TempConverter[N2, U2, N1, U1]
  def cn12(x: N1): N2
  def cn21(x: N2): N1
}

object TempConverterOps {
  type Aux[N1, U1, N2, U2] = TempConverterOps[N1, U1, N2, U2] {
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      cvv12: TempConverter[N1, U1, N2, U2],
      cvv21: TempConverter[N2, U2, N1, U1]): Aux[N1, U1, N2, U2] =
    new TempConverterOps[N1, U1, N2, U2] {
      val n1 = nn1
      val n2 = nn2
      val cv12 = cvv12
      val cv21 = cvv21
      def cn12(x: N1): N2 = nn1.toType[N2](x)
      def cn21(x: N2): N1 = nn2.toType[N1](x)
    }
}
