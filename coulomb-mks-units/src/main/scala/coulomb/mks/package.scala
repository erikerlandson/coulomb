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

import singleton.ops._

import coulomb.define._
import coulomb.si._

/** MKS (Meter, Kilogram, Second) unit definitions (Newton, Joule, Watt, etc) */
package object mks {
  trait Radian
  implicit val defineUnitRadian = DerivedUnit[Radian, Unitless](abbv = "rad")

  trait Hertz
  implicit val defineUnitHertz = DerivedUnit[Hertz, Second %^ -1](abbv = "Hz")

  trait Newton
  implicit val defineUnitNewton = DerivedUnit[Newton, Kilogram %* Meter %/ (Second %^ 2)](abbv = "N")

  trait Joule
  implicit val defineUnitJoule = DerivedUnit[Joule, Newton %* Meter](abbv = "J")

  trait Watt
  implicit val defineUnitWatt = DerivedUnit[Watt, Joule %/ Second](abbv = "W")

  trait Pascal
  implicit val defineUnitPascal = DerivedUnit[Pascal, Newton %/ (Meter %^ 2)](abbv = "Pa")

  trait Coulomb
  implicit val defineUnitCoulomb = DerivedUnit[Coulomb, Ampere %* Second](abbv = "C")

  trait Volt
  implicit val defineUnitVolt = DerivedUnit[Volt, Watt %/ Ampere](abbv = "V")

  trait Ohm
  implicit val defineUnitOhm = DerivedUnit[Ohm, Volt %/ Ampere](abbv = "Ω")

  trait Farad
  implicit val defineUnitFarad = DerivedUnit[Farad, Coulomb %/ Volt](abbv = "F")

  trait Weber
  implicit val defineUnitWeber = DerivedUnit[Weber, Joule %/ Ampere](abbv = "Wb")

  trait Tesla
  implicit val defineUnitTesla = DerivedUnit[Tesla, Weber %/ (Meter %^ 2)](abbv = "T")

  trait Henry
  implicit val defineUnitHenry = DerivedUnit[Henry, Weber %/ Ampere](abbv = "H")
}
