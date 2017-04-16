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

object InfoUnits {
  import spire.math.Rational

  @UnitDecl("byte", abbv = "B")
  trait Byte extends BaseUnit

  @UnitDecl("bit", Rational(1, 8), "b")
  trait Bit extends DerivedUnit[Byte]

  @UnitDecl("nat", 1.4426950409)
  trait Nat extends DerivedUnit[Bit]
}
