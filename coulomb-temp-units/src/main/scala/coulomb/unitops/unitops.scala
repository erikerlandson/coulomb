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

import spire.math.{ Rational, ConvertableFrom, ConvertableTo }
import spire.algebra._

import coulomb.infra._
import coulomb.define._

/**
 * An implicit trait that supports compile-time temperature subtraction
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait TempSub[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and subtract from v1 */
  def vsub(v1: N1, v2: N2): N1
}
object TempSub {
  implicit def evidence[N1, U1, N2, U2](implicit
      ag1: AdditiveGroup[N1],
      uc: TempConverter[N2, U2, N1, U1]): TempSub[N1, U1, N2, U2] =
    new TempSub[N1, U1, N2, U2] {
      def vsub(v1: N1, v2: N2): N1 = ag1.minus(v1, uc.vcnv(v2))
    }
}

/**
 * An implicit trait that supports compile-time temperature comparisons / ordering
 * @tparam N1 the numeric type of the quantity value
 * @tparam U1 the unit expresion type of the quantity
 * @tparam N2 numeric type of a RHS quantity value
 * @tparam U2 unit expression type of the RHS quantity
 */
trait TempOrd[N1, U1, N2, U2] {
  /** convert value v2 to units of (U1,N1) (if necessary), and compare to v1 */
  def vcmp(v1: N1, v2: N2): Int
}
object TempOrd {
  implicit def evidence[N1, U1, N2, U2](implicit
      ord1: Order[N1],
      uc: TempConverter[N2, U2, N1, U1]): TempOrd[N1, U1, N2, U2] =
    new TempOrd[N1, U1, N2, U2] {
      def vcmp(v1: N1, v2: N2): Int = ord1.compare(v1, uc.vcnv(v2))
    }
}

/**
 * An implicit trait that supports compile-time temperature conversion, when possible.
 * Also used to support addition, subtraction and comparisons.
 * This implicit will not exist if U1 and U2 are not convertable to one another.
 * @tparam N1 the numeric type of the temperature value
 * @tparam U1 the unit expresion type of the temperature
 * @tparam N2 numeric type of another temperature value
 * @tparam U2 unit expression type of the other temperature
 */
trait TempConverter[N1, U1, N2, U2] {
  /** a conversion from temperature value with type `(N1,U1)` to type `(N2,U2)` */
  def vcnv(v: N1): N2
}
trait TempConverterDefaultPriority {
  // this default rule should work well everywhere but may be overridden for efficiency
  implicit def evidence[N1, U1, N2, U2](implicit
      t1: DerivedTemp[U1], t2: DerivedTemp[U2],
      cf1: ConvertableFrom[N1],
      ct2: ConvertableTo[N2]): TempConverter[N1, U1, N2, U2] = {
    val coef = t1.coef / t2.coef
    new TempConverter[N1, U1, N2, U2] {
      def vcnv(v: N1): N2 = {
        ct2.fromType[Rational](((cf1.toType[Rational](v) + t1.off) * coef) - t2.off)
      }
    }
  }
}
object TempConverter extends TempConverterDefaultPriority {
  // override the default temp-converter generation here for specific cases
}
