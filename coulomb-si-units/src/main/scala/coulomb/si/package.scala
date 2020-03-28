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

import coulomb.define._

/** Standard International (SI) units: Kilogram, Meter, Second, Ampere, Mole, Candela, Kelvin */
package object si {
  trait Meter
  implicit val defineUnitMeter = BaseUnit[Meter]()

  trait Kilogram
  implicit val defineUnitKilogram = BaseUnit[Kilogram](abbv = "kg")

  trait Second
  implicit val defineUnitSecond = BaseUnit[Second]()

  trait Ampere
  implicit val defineUnitAmpere = BaseUnit[Ampere](abbv = "A")

  trait Mole
  implicit val defineUnitMole = BaseUnit[Mole](abbv = "mol")

  trait Candela
  implicit val defineUnitCandela = BaseUnit[Candela](abbv = "cd")

  trait Kelvin
  implicit val defineUnitKelvin = BaseUnit[Kelvin](name = "Kelvin", abbv = "K")
}
