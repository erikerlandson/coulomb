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
  import ChurchInt._
  import SIBaseUnits._

  trait Radian extends DerivedUnit[Unitless]
  object Radian extends UnitCompanion[Radian]("radian")

  trait Hertz extends DerivedUnit[Second <^> _neg1]
  object Hertz extends UnitCompanion[Hertz]("hertz")

  trait Newton extends DerivedUnit[Kilogram <*> Meter </> (Second <^> _2)]
  object Newton extends UnitCompanion[Newton]("newton")

  trait Joule extends DerivedUnit[Newton <*> Meter]
  object Joule extends UnitCompanion[Joule]("joule")

  trait Watt extends DerivedUnit[Joule </> Second]
  object Watt extends UnitCompanion[Watt]("watt")

  trait Pascal extends DerivedUnit[Newton </> (Meter <^> _2)]
  object Pascal extends UnitCompanion[Pascal]("pascal")

  trait Coulomb extends DerivedUnit[Ampere <*> Second]
  object Coulomb extends UnitCompanion[Coulomb]("coulomb")

  trait Volt extends DerivedUnit[Watt </> Ampere]
  object Volt extends UnitCompanion[Volt]("volt")

  trait Ohm extends DerivedUnit[Volt </> Ampere]
  object Ohm extends UnitCompanion[Ohm]("ohm")

  trait Farad extends DerivedUnit[Coulomb </> Volt]
  object Farad extends UnitCompanion[Farad]("farad")

  trait Weber extends DerivedUnit[Joule </> Ampere]
  object Weber extends UnitCompanion[Weber]("weber")

  trait Tesla extends DerivedUnit[Weber </> (Meter <^> _2)]
  object Tesla extends UnitCompanion[Tesla]("tesla")

  trait Henry extends DerivedUnit[Weber </> Ampere]
  object Henry extends UnitCompanion[Henry]("henry")
}
