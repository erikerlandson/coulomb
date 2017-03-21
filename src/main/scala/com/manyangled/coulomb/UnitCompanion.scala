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

import spire.math.{ Rational, ConvertableFrom }

class UnitCompanion[U <: UnitExpr](uname: String, ucoef: Rational) {
  def this(n: String) = this(n, 1.0)

  def apply[N](v: N)(implicit num: Numeric[N]): Quantity[U] =
    new Quantity[U](num.toDouble(v))

  def apply(): Quantity[U] = new Quantity[U](1.0)

  implicit val furec: UnitRec[U] = UnitRec[U](uname, Rational(ucoef))
}

class TempUnitCompanion[U <: TemperatureExpr](uname: String, ucoef: Rational, uoffset: Rational)
    extends UnitCompanion[U](uname, ucoef) {
  implicit val turec: TempUnitRec[U] = TempUnitRec[U](uoffset)
}
