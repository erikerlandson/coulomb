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

package coulomb

import spire.math._

import coulomb.define._

package object temp {
  /** An infix type alias for [[Temperature]] */
  type WithTemperature[N, U] = Temperature[N, U]

  /** enhances numeric types with utility methods for `coulomb` */
  implicit class CoulombExtendWithTemp[N](val v: N) extends Serializable {
    /** create a new unit Temperature of type U with numeric value of `this` */
    def withTemperature[U](implicit t2k: DerivedTemp[U]): Temperature[N, U] =
      new Temperature[N, U](v)
  }

  trait Celsius
  implicit val defineUnitCelsius = DerivedTemp[Celsius](Rational(1), Rational(27315, 100), name = "Celsius", abbv = "°C")

  trait Fahrenheit
  implicit val defineUnitFahrenheit = DerivedTemp[Fahrenheit](Rational(5, 9), Rational(45967, 100), name = "Fahrenheit", abbv = "°F")
}
