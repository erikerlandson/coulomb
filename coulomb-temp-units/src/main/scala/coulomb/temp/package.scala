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

package coulomb

import spire.math.Rational

import coulomb.define._

import coulomb.unitops._

import coulomb.si.Kelvin

/** Temperature and Temperature units: Celsius and Fahrenheit */
package object temp {

  type Temperature[N, U] = OffsetQuantity[N, U]

  /** An infix type alias for [[Temperature]] */
  type WithTemperature[N, U] = Temperature[N, U]

  /** enhances numeric types with utility methods for `coulomb` */
  implicit class CoulombExtendWithTemp[N](val v: N) extends Serializable {
    /** create a new unit Temperature of type U with numeric value of `this` */
    def withTemperature[U](implicit t2k: OffsetUnit[U, Kelvin]): Temperature[N, U] =
      new Temperature[N, U](v)
  }

  trait Celsius
  implicit val defineUnitCelsius =
    OffsetUnit[Celsius, Kelvin](Rational(1), Rational(27315, 100), name = "Celsius", abbv = "°C")

  trait Fahrenheit
  implicit val defineUnitFahrenheit =
    OffsetUnit[Fahrenheit, Kelvin](Rational(5, 9), Rational(45967, 100), name = "Fahrenheit", abbv = "°F")
}

package temp {
  object Temperature {
    def apply[N, U](v: N)(implicit t2k: OffsetUnit[U, Kelvin]) =
      new Temperature[N, U](v)

    /** A string representation of unit type U, using unit abbreviations */
    def showUnit[U](implicit ustr: UnitString[U]): String =
      OffsetQuantity.showUnit[U]

    /** A string representation of unit type U, using full unit names */
    def showUnitFull[U](implicit ustr: UnitString[U]): String =
      OffsetQuantity.showUnitFull[U]

    /** Obtain Temperature[N,U] from a Quantity[N,U] */
    def fromQuantity[N, U](q: Quantity[N, U])(implicit
        t2k: OffsetUnit[U, Kelvin]): Temperature[N, U] =
      OffsetQuantity.fromQuantity(q)

    /** Obtain a Quantity[N,U] from this OffsetQuantity[N,U] */
    def toQuantity[N, U](t: Temperature[N, U]): Quantity[N, U] =
      OffsetQuantity.toQuantity(t)
  }
}
