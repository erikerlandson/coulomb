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

object MKSUnits {
  import spire.math.Rational
  import ChurchInt._
  import SIBaseUnits._

  @UnitDecl("radian", 1)
  trait Radian extends DerivedUnit[Unitless]

  @UnitDecl("hertz", 1)
  trait Hertz extends DerivedUnit[Second %^ _neg1]

  @UnitDecl("newton", 1)
  trait Newton extends DerivedUnit[Kilogram %* Meter %/ (Second %^ _2)]

  @UnitDecl("joule", 1)
  trait Joule extends DerivedUnit[Newton %* Meter]

  @UnitDecl("watt", 1)
  trait Watt extends DerivedUnit[Joule %/ Second]

  @UnitDecl("pascal", 1)
  trait Pascal extends DerivedUnit[Newton %/ (Meter %^ _2)]

  @UnitDecl("coulomb", 1)
  trait Coulomb extends DerivedUnit[Ampere %* Second]

  @UnitDecl("volt", 1)
  trait Volt extends DerivedUnit[Watt %/ Ampere]

  @UnitDecl("ohm", 1)
  trait Ohm extends DerivedUnit[Volt %/ Ampere]

  @UnitDecl("farad", 1)
  trait Farad extends DerivedUnit[Coulomb %/ Volt]

  @UnitDecl("weber", 1)
  trait Weber extends DerivedUnit[Joule %/ Ampere]

  @UnitDecl("tesla", 1)
  trait Tesla extends DerivedUnit[Weber %/ (Meter %^ _2)]

  @UnitDecl("henry", 1)
  trait Henry extends DerivedUnit[Weber %/ Ampere]
}
