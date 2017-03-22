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
  @unitDecl("meter")
  trait Meter extends BaseUnit

  @unitDecl("second")
  trait Second extends BaseUnit

  @unitDecl("kilogram")
  trait Kilogram extends BaseUnit

  @unitDecl("ampere")
  trait Ampere extends BaseUnit

  @unitDecl("mole")
  trait Mole extends BaseUnit

  @unitDecl("candela")
  trait Candela extends BaseUnit

  @tempUnitDecl("kelvin", 1, 0)
  trait Kelvin extends BaseTemperature
}
