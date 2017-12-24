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

package object binprefix {
  trait Kibi
  implicit val defineUnitKibi = PrefixUnit[Kibi](1024, abbv = "Ki")

  trait Mebi
  implicit val defineUnitMebi = PrefixUnit[Mebi](Rational(1024).pow(2), abbv = "Mi")

  trait Gibi
  implicit val defineUnitGibi = PrefixUnit[Gibi](Rational(1024).pow(3), abbv = "Gi")

  trait Tebi
  implicit val defineUnitTebi = PrefixUnit[Tebi](Rational(1024).pow(4), abbv = "Ti")

  trait Pebi
  implicit val defineUnitPebi = PrefixUnit[Pebi](Rational(1024).pow(5), abbv = "Pi")

  trait Exbi
  implicit val defineUnitExbi = PrefixUnit[Exbi](Rational(1024).pow(6), abbv = "Ei")

  trait Zebi
  implicit val defineUnitZebi = PrefixUnit[Zebi](Rational(1024).pow(7), abbv = "Zi")

  trait Yobi
  implicit val defineUnitYobi = PrefixUnit[Yobi](Rational(1024).pow(8), abbv = "Yi")
}
