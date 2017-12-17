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

package coulomb.infra

import spire.math._

import coulomb.define._

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
