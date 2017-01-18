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

object SIBaseUnits {
  trait Meter extends BaseUnit
  object Meter extends UnitCompanion[Meter]("meter")

  trait Second extends BaseUnit
  object Second extends UnitCompanion[Second]("second")

  trait Kilogram extends BaseUnit
  object Kilogram extends UnitCompanion[Kilogram]("kilogram")

  trait Ampere extends BaseUnit
  object Ampere extends UnitCompanion[Ampere]("ampere")

  trait Mole extends BaseUnit
  object Mole extends UnitCompanion[Mole]("mole")

  trait Candela extends BaseUnit
  object Candela extends UnitCompanion[Candela]("candela")

  trait Kelvin extends BaseTemperature
  object Kelvin extends TempUnitCompanion[Kelvin]("kelvin", 1.0, 0.0)
}
