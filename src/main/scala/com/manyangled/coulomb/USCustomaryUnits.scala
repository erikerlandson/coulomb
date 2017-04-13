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

  @UnitDecl("inch", Rational(1, 12))
  trait Inch extends DerivedUnit[Foot]

  @UnitDecl("foot", Rational(1, 3))
  trait Foot extends DerivedUnit[Yard]

  @UnitDecl("yard", Rational(9144, 10000))
  trait Yard extends DerivedUnit[Meter]

  @UnitDecl("mile", 1760)
  trait Mile extends DerivedUnit[Yard]

  @UnitDecl("acre", 43560)
  trait Acre extends DerivedUnit[Foot %^ _2]

  @UnitDecl("ounce", Rational(1, 16))
  trait Ounce extends DerivedUnit[Pound]

  @UnitDecl("pound", Rational(45359237, 100000000))
  trait Pound extends DerivedUnit[Kilogram]

  @UnitDecl("shortton", 2000)
  trait ShortTon extends DerivedUnit[Pound]

  @TempUnitDecl("fahrenheit", Rational(5, 9), Rational(45967, 100))
  trait Fahrenheit extends DerivedTemperature

  @UnitDecl("BTU", 1055)
  trait BTU extends DerivedUnit[Joule]

  @UnitDecl("calorie", 4.184)
  trait Calorie extends DerivedUnit[Joule]

  @UnitDecl("foodcalorie", 1000)
  trait FoodCalorie extends DerivedUnit[Calorie]

  @UnitDecl("footpound", 1.356)
  trait FootPound extends DerivedUnit[Joule]

  @UnitDecl("horsepower", 745.7)
  trait Horsepower extends DerivedUnit[Watt]

  @UnitDecl("teaspoon", Rational(1, 3))
  trait Teaspoon extends DerivedUnit[Tablespoon]

  @UnitDecl("tablespoon", Rational(1, 2))
  trait Tablespoon extends DerivedUnit[FluidOunce]

  @UnitDecl("fluidounce", Rational(1, 8))
  trait FluidOunce extends DerivedUnit[Cup]

  @UnitDecl("cup", Rational(2365882365L, 10000000L))
  trait Cup extends DerivedUnit[Milliliter]

  @UnitDecl("pint", 2)
  trait Pint extends DerivedUnit[Cup]

  @UnitDecl("quart", 4)
  trait Quart extends DerivedUnit[Cup]

  @UnitDecl("gallon", 16)
  trait Gallon extends DerivedUnit[Cup]
}
