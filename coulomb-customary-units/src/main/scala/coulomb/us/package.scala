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

import singleton.ops._

import coulomb.define._

import coulomb.si._

package object us {
  trait Inch
  implicit val defineUnitInch = DerivedUnit[Inch, Foot](Rational(1, 12), abbv = "in")

  trait Foot
  implicit val defineUnitFoot = DerivedUnit[Foot, Yard](Rational(1, 3), abbv = "ft")

  trait Yard
  implicit val defineUnitYard = DerivedUnit[Yard, Meter](Rational(9144, 10000), abbv = "yd")

  trait Mile
  implicit val defineUnitMile = DerivedUnit[Mile, Yard](1760, abbv = "mi")

  trait Acre
  implicit val defineUnitAcre = DerivedUnit[Acre, Foot %^ W.`2`.T](43560, abbv = "acre")

  trait Ounce
  implicit val defineUnitOunce = DerivedUnit[Ounce, Pound](Rational(1, 16), abbv = "oz")

  trait Pound
  implicit val defineUnitPound = DerivedUnit[Pound, Kilogram](Rational(45359237, 100000000), abbv = "lb")

  trait ShortTon
  implicit val defineUnitShortTon = DerivedUnit[ShortTon, Pound](2000, abbv = "ton")

  trait BTU
  implicit val defineUnitBTU = DerivedUnit[BTU, Kilogram %* (Meter %^ W.`2`.T) %/ (Second %^ W.`2`.T)](1054.3503, abbv = "BTU")

  trait Calorie
  implicit val defineUnitCalorie = DerivedUnit[Calorie, Kilogram %* (Meter %^ W.`2`.T) %/ (Second %^ W.`2`.T)](4.184, abbv = "cal")

  trait FoodCalorie
  implicit val defineUnitFoodCalorie = DerivedUnit[FoodCalorie, Calorie](1000, abbv = "kcal")

  trait FootPound
  implicit val defineUnitFootPound = DerivedUnit[FootPound, Kilogram %* (Meter %^ W.`2`.T) %/ (Second %^ W.`2`.T)](1.355817948331, abbv = "ft·lbf")

  trait Horsepower
  implicit val defineUnitHorsepower = DerivedUnit[Horsepower, Kilogram %* (Meter %^ W.`2`.T) %/ (Second %^ W.`3`.T)](745.69987158227, abbv = "hp")

  trait Teaspoon
  implicit val defineUnitTeaspoon = DerivedUnit[Teaspoon, Tablespoon](Rational(1, 3), abbv = "tsp")

  trait Tablespoon
  implicit val defineUnitTablespoon = DerivedUnit[Tablespoon, FluidOunce](Rational(1, 2), abbv = "tbsp")

  trait FluidOunce
  implicit val defineUnitFluidOunce = DerivedUnit[FluidOunce, Cup](Rational(1, 8), abbv = "floz")

  trait Cup
  implicit val defineUnitCup = DerivedUnit[Cup, Meter %^ W.`3`.T](Rational(2365882365L, 10000000000000L), abbv = "cp")

  trait Pint
  implicit val defineUnitPint = DerivedUnit[Pint, Cup](2, abbv = "pt")

  trait Quart
  implicit val defineUnitQuart = DerivedUnit[Quart, Pint](2, abbv = "qt")

  trait Gallon
  implicit val defineUnitGallon = DerivedUnit[Gallon, Quart](4, abbv = "gal")
}
