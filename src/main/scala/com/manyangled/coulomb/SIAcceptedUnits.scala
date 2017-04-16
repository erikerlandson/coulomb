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

object SIAcceptedUnits {
  import spire.math.Rational
  import ChurchInt._
  import SIBaseUnits._
  import MKSUnits.{ Radian, Pascal }

  @TempUnitDecl("celsius", 1, Rational(27315, 100), "°C")
  trait Celsius extends DerivedTemperature

  @UnitDecl("minute", 60, "min")
  trait Minute extends DerivedUnit[Second]

  @UnitDecl("hour", 3600, "h")
  trait Hour extends DerivedUnit[Second]

  @UnitDecl("day", 86400, "d")
  trait Day extends DerivedUnit[Second]

  @UnitDecl("degree", math.Pi / 180.0, "°")
  trait Degree extends DerivedUnit[Radian]

  @UnitDecl("arcminute", Rational(1, 60), "'")
  trait ArcMinute extends DerivedUnit[Degree]

  @UnitDecl("arcsecond", Rational(1, 3600), "\"")
  trait ArcSecond extends DerivedUnit[Degree]

  @UnitDecl("hectare", 10000, "ha")
  trait Hectare extends DerivedUnit[Meter %^ _2]

  @UnitDecl("liter", Rational(1, 1000), "l")
  trait Liter extends DerivedUnit[Meter %^ _3]

  @UnitDecl("milliliter", Rational(1, 1000), "ml")
  trait Milliliter extends DerivedUnit[Liter]

  @UnitDecl("tonne", 1000, "t")
  trait Tonne extends DerivedUnit[Kilogram]

  @UnitDecl("millibar", 100, "mbar")
  trait Millibar extends DerivedUnit[Pascal]

  @UnitDecl("kilometer", 1000, "km")
  trait Kilometer extends DerivedUnit[Meter]

  @UnitDecl("millimeter", Rational(1, 1000), "mm")
  trait Millimeter extends DerivedUnit[Meter]

  @UnitDecl("gram", Rational(1, 1000), "g")
  trait Gram extends DerivedUnit[Kilogram]
}
