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

import singleton.ops._

import spire.math._

import coulomb.define._
import coulomb.si._

package object accepted {
  trait Percent
  implicit val defineUnitPercent = DerivedUnit[Percent, Unitless](Rational(1, 100), abbv = "%")

  trait Degree
  implicit val defineUnitDegree = DerivedUnit[Degree, Unitless](scala.math.Pi / 180.0, abbv = "°")

  trait ArcMinute
  implicit val defineUnitArcMinute = DerivedUnit[ArcMinute, Degree](Rational(1, 60), abbv = "'")

  trait ArcSecond
  implicit val defineUnitArcSecond = DerivedUnit[ArcSecond, Degree](Rational(1, 3600), abbv = "\"")

  trait Hectare
  implicit val defineUnitHectare = DerivedUnit[Hectare, Meter %^ 2](10000, abbv = "ha")

  trait Liter
  implicit val defineUnitLiter = DerivedUnit[Liter, Meter %^ 3](Rational(1, 1000))

  trait Milliliter
  implicit val defineUnitMilliliter = DerivedUnit[Milliliter, Liter](Rational(1, 1000), abbv = "ml")

  trait Tonne
  implicit val defineUnitTonne = DerivedUnit[Tonne, Kilogram](1000)

  trait Millibar
  implicit val defineUnitMillibar = DerivedUnit[Millibar, Kilogram %/ (Meter %* (Second %^ 2))](100, abbv = "mbar")

  trait Kilometer
  implicit val defineUnitKilometer = DerivedUnit[Kilometer, Meter](1000, abbv = "km")

  trait Millimeter
  implicit val defineUnitMillimeter = DerivedUnit[Millimeter, Meter](Rational(1, 1000), abbv = "mm")

  trait Centimeter
  implicit val defineUnitCentimeter = DerivedUnit[Centimeter, Meter](Rational(1, 100), abbv = "cm")

  trait Gram
  implicit val defineUnitGram = DerivedUnit[Gram, Kilogram](Rational(1, 1000), abbv = "g")
}
