/*
Copyright 2017-2020 Erik Erlandson

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

package coulomb.offset.unitops

import shapeless.{ ::, HNil }

import spire.math.{ Rational, ConvertableFrom, ConvertableTo }
import spire.algebra._

import coulomb.infra._
import coulomb.define._
import coulomb.offset.define._

/**
 * An implicit trait that supports compile-time offset unit subtraction
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait OffsetUnitSub[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and subtract from v1 */
  def vsub(v1: N1, v2: N2): N1
}
object OffsetUnitSub {
  implicit def evidence[N1, U1, N2, U2](implicit
      ag1: AdditiveGroup[N1],
      uc: OffsetUnitConverter[N2, U2, N1, U1]): OffsetUnitSub[N1, U1, N2, U2] =
    new OffsetUnitSub[N1, U1, N2, U2] {
      def vsub(v1: N1, v2: N2): N1 = ag1.minus(v1, uc.vcnv(v2))
    }
}

/**
 * An implicit trait that supports compile-time comparisons / ordering
 * for offset quantities
 * @tparam N1 the numeric type of the lhs
 * @tparam U1 the unit type of the rhs
 * @tparam N2 numeric type of the rhs
 * @tparam U2 unit type of the rhs
 */
trait OffsetUnitOrd[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and compare to v1 */
  def vcmp(v1: N1, v2: N2): Int
}
object OffsetUnitOrd {
  implicit def evidence[N1, U1, N2, U2](implicit
      ord1: Order[N1],
      uc: OffsetUnitConverter[N2, U2, N1, U1]): OffsetUnitOrd[N1, U1, N2, U2] =
    new OffsetUnitOrd[N1, U1, N2, U2] {
      def vcmp(v1: N1, v2: N2): Int = ord1.compare(v1, uc.vcnv(v2))
    }
}

/** Resolves for any unit that is linearly reducible to a single base unit */
trait ReduceToBaseUnit[U] {
  type Out
}
object ReduceToBaseUnit {
  type Aux[U, O] = ReduceToBaseUnit[U] { type Out = O }
  implicit def reduceBU[U](implicit bu: BaseUnit[U]): Aux[U, U] =
    new ReduceToBaseUnit[U] { type Out = U }
  implicit def reduceDU[U, D, B](implicit
      du: DerivedUnit[U, D],
      ru: ReduceToBaseUnit.Aux[D, B]): Aux[U, B] =
    new ReduceToBaseUnit[U] { type Out = B }
  // these rules might be augmented to include multiples of prefix-units
  // but I'd need to work that logic through the entire offset-unit system
}

/**
 * An implicit trait that supports compile-time offset unit conversion, when possible.
 * Also used to support addition, subtraction and comparisons.
 * This implicit will not exist if U1 and U2 are not convertable to one another.
 * @tparam N1 the numeric type of the offset-unit value
 * @tparam U1 the unit expresion type of the offset-unit
 * @tparam N2 numeric type of another offset-unit value
 * @tparam U2 unit expression type of the other offset-unit
 */
trait OffsetUnitConverter[N1, U1, N2, U2] {
  /** a conversion from offset-unit value with type `(N1,U1)` to type `(N2,U2)` */
  def vcnv(v: N1): N2
}
trait OffsetUnitConverterDefaultPriority {
  // this default rule should work well everywhere but may be overridden for efficiency
  implicit def evidence[N1, U1, B, N2, U2](implicit
      // binding B first is crucial to making this implicit chain resolve
      bu1: ReduceToBaseUnit.Aux[U1, B],
      ou1: OffsetUnit[U1, B], ou2: OffsetUnit[U2, B],
      cf1: ConvertableFrom[N1],
      ct2: ConvertableTo[N2]): OffsetUnitConverter[N1, U1, N2, U2] = {
    val coef = ou1.coef / ou2.coef
    new OffsetUnitConverter[N1, U1, N2, U2] {
      def vcnv(v: N1): N2 = {
        ct2.fromType[Rational](((cf1.toType[Rational](v) + ou1.off) * coef) - ou2.off)
      }
    }
  }
}
object OffsetUnitConverter extends OffsetUnitConverterDefaultPriority {
  // override the default converter generation here for specific cases
}
