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
  import spire.math.Rational
  import ChurchInt._
  import SIBaseUnits._
  import SIAcceptedUnits.Milliliter
  import MKSUnits.{ Joule, Watt }

  @unitDecl("inch", Rational(1, 12))
  trait Inch extends DerivedUnit[Foot]

  @unitDecl("foot", Rational(1, 3))
  trait Foot extends DerivedUnit[Yard]

  @unitDecl("yard", Rational(9144, 10000))
  trait Yard extends DerivedUnit[Meter]

  @unitDecl("mile", 1760)
  trait Mile extends DerivedUnit[Yard]

  @unitDecl("acre", 43560)
  trait Acre extends DerivedUnit[Foot <^> _2]

  @unitDecl("ounce", Rational(1, 16))
  trait Ounce extends DerivedUnit[Pound]

  @unitDecl("pound", Rational(45359237, 100000000))
  trait Pound extends DerivedUnit[Kilogram]

  @unitDecl("shortton", 2000)
  trait ShortTon extends DerivedUnit[Pound]

  @tempUnitDecl("fahrenheit", Rational(5, 9), Rational(45967, 100))
  trait Fahrenheit extends DerivedTemperature

  @unitDecl("BTU", 1055)
  trait BTU extends DerivedUnit[Joule]

  @unitDecl("calorie", 4.184)
  trait Calorie extends DerivedUnit[Joule]

  @unitDecl("foodcalorie", 1000)
  trait FoodCalorie extends DerivedUnit[Calorie]

  @unitDecl("footpound", 1.356)
  trait FootPound extends DerivedUnit[Joule]

  @unitDecl("horsepower", 745.7)
  trait Horsepower extends DerivedUnit[Watt]

  @unitDecl("teaspoon", Rational(1, 3))
  trait Teaspoon extends DerivedUnit[Tablespoon]

  @unitDecl("tablespoon", Rational(1, 2))
  trait Tablespoon extends DerivedUnit[FluidOunce]

  @unitDecl("fluidounce", Rational(1, 8))
  trait FluidOunce extends DerivedUnit[Cup]

  @unitDecl("cup", Rational(2365882365L, 10000000L))
  trait Cup extends DerivedUnit[Milliliter]

  @unitDecl("pint", 2)
  trait Pint extends DerivedUnit[Cup]

  @unitDecl("quart", 4)
  trait Quart extends DerivedUnit[Cup]

  @unitDecl("gallon", 16)
  trait Gallon extends DerivedUnit[Cup]
}
