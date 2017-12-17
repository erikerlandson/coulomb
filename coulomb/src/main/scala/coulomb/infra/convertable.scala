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

import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import spire.math._

import coulomb._

class ConvertableUnits[U1, U2](val coef: Rational)

object ConvertableUnits {
  implicit def witnessCU[U1, U2, C1, C2](implicit u1: CanonicalSig.Aux[U1, C1], u2: CanonicalSig.Aux[U2, C2], eq: SetEqual.Aux[C1, C2, True]): ConvertableUnits[U1, U2] =
    new ConvertableUnits[U1, U2](u1.coef / u2.coef)
}

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
