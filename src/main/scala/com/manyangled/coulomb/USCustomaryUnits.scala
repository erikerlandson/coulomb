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

package com.manyangled.coulomb

// United States Customary Units
// https://en.wikipedia.org/wiki/United_States_customary_units
object USCustomaryUnits {
  import ChurchInt._
  import SIBaseUnits._
  import SIAcceptedUnits.Milliliter
  import MKSUnits.{ Joule, Watt }
  
  trait Inch extends DerivedUnit[Meter]
  object Inch extends UnitCompanion[Inch]("inch", 0.0254)

  trait Foot extends DerivedUnit[Meter]
  object Foot extends UnitCompanion[Foot]("foot", 0.3048)

  trait Yard extends DerivedUnit[Meter]
  object Yard extends UnitCompanion[Yard]("yard", 0.9144)

  trait Mile extends DerivedUnit[Meter]
  object Mile extends UnitCompanion[Mile]("mile", 1609.344)

  trait Acre extends DerivedUnit[Meter <^> _2]
  object Acre extends UnitCompanion[Acre]("acre", 4046.873)

  trait Ounce extends DerivedUnit[Kilogram]
  object Ounce extends UnitCompanion[Ounce]("ounce", 0.028349523125)

  trait Pound extends DerivedUnit[Kilogram]
  object Pound extends UnitCompanion[Pound]("pound", 0.45359237)

  trait ShortTon extends DerivedUnit[Kilogram]
  object ShortTon extends UnitCompanion[ShortTon]("shortton", 907.18474)

  trait Fahrenheit extends DerivedTemperature
  object Fahrenheit extends TempUnitCompanion[Fahrenheit]("fahrenheit", 5.0 / 9.0, 459.67)

  trait BTU extends DerivedUnit[Joule]
  object BTU extends UnitCompanion[BTU]("BTU", 1055.0)

  trait Calorie extends DerivedUnit[Joule]
  object Calorie extends UnitCompanion[Calorie]("calorie", 4.184)

  trait FoodCalorie extends DerivedUnit[Joule]
  object FoodCalorie extends UnitCompanion[FoodCalorie]("foodcalorie", 4184.0)

  trait FootPound extends DerivedUnit[Joule]
  object FootPound extends UnitCompanion[FootPound]("footpound", 1.356)

  trait Horsepower extends DerivedUnit[Watt]
  object Horsepower extends UnitCompanion[Horsepower]("horsepower", 745.7)

  trait Teaspoon extends DerivedUnit[Milliliter]
  object Teaspoon extends UnitCompanion[Teaspoon]("teaspoon", 4.928958333)

  trait Tablespoon extends DerivedUnit[Milliliter]
  object Tablespoon extends UnitCompanion[Tablespoon]("tablespoon", 14.786875)

  trait FluidOunce extends DerivedUnit[Milliliter]
  object FluidOunce extends UnitCompanion[FluidOunce]("fluidounce", 29.57375)

  trait Cup extends DerivedUnit[Milliliter]
  object Cup extends UnitCompanion[Cup]("cup", 236.59)

  trait Pint extends DerivedUnit[Milliliter]
  object Pint extends UnitCompanion[Pint]("pint", 473.18)

  trait Quart extends DerivedUnit[Milliliter]
  object Quart extends UnitCompanion[Quart]("quart", 946.36)

  trait Gallon extends DerivedUnit[Milliliter]
  object Gallon extends UnitCompanion[Gallon]("gallon", 3785.44)
}
