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

/** SI prefix units (Kilo, Mega, Milli, Micro, etc) */
package object siprefix {
  trait Deca
  implicit val defineUnitDeca = PrefixUnit[Deca](10, abbv = "da")

  trait Hecto
  implicit val defineUnitHecto = PrefixUnit[Hecto](100)

  trait Kilo
  implicit val defineUnitKilo = PrefixUnit[Kilo](1000)

  trait Mega
  implicit val defineUnitMega = PrefixUnit[Mega](Rational(10).pow(6), abbv = "M")

  trait Giga
  implicit val defineUnitGiga = PrefixUnit[Giga](Rational(10).pow(9), abbv = "G")

  trait Tera
  implicit val defineUnitTera = PrefixUnit[Tera](Rational(10).pow(12), abbv = "T")

  trait Peta
  implicit val defineUnitPeta = PrefixUnit[Peta](Rational(10).pow(15), abbv = "P")

  trait Exa
  implicit val defineUnitExa = PrefixUnit[Exa](Rational(10).pow(18), abbv = "E")

  trait Zetta
  implicit val defineUnitZetta = PrefixUnit[Zetta](Rational(10).pow(21), abbv = "Z")

  trait Yotta
  implicit val defineUnitYotta = PrefixUnit[Yotta](Rational(10).pow(24), abbv = "Y")

  trait Deci
  implicit val defineUnitDeci = PrefixUnit[Deci](Rational(10).pow(-1))

  trait Centi
  implicit val defineUnitCenti = PrefixUnit[Centi](Rational(10).pow(-2))

  trait Milli
  implicit val defineUnitMilli = PrefixUnit[Milli](Rational(10).pow(-3))

  trait Micro
  implicit val defineUnitMicro = PrefixUnit[Micro](Rational(10).pow(-6), abbv = "μ")

  trait Nano
  implicit val defineUnitNano = PrefixUnit[Nano](Rational(10).pow(-9))

  trait Pico
  implicit val defineUnitPico = PrefixUnit[Pico](Rational(10).pow(-12))

  trait Femto
  implicit val defineUnitFemto = PrefixUnit[Femto](Rational(10).pow(-15))

  trait Atto
  implicit val defineUnitAtto = PrefixUnit[Atto](Rational(10).pow(-18))

  trait Zepto
  implicit val defineUnitZepto = PrefixUnit[Zepto](Rational(10).pow(-21))

  trait Yocto
  implicit val defineUnitYocto = PrefixUnit[Yocto](Rational(10).pow(-24))
}
