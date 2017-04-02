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

package com.manyangled

package object coulomb {
  /** obtain the integer value of a ChurchInt type */
  def churchToInt[N <: ChurchInt](implicit iv: ChurchIntValue[N]) = iv.value

  /** enhances numeric types with utility methods for `coulomb` */
  implicit class CoulombExtendWithUnits[N](val v: N) extends AnyVal with Serializable {
    /** create a new unit Quantity of type U with numeric value of `this` */
    def withUnit[U <: UnitExpr](implicit num: spire.math.ConvertableTo[N]): Quantity[N, U] =
      new Quantity[N, U](v)

    /** create a new unit Temperature of type U with numeric value of `this` */
    def withTemperature[U <: TemperatureExpr](implicit
        num: spire.math.ConvertableTo[N]): Temperature[N, U] =
      new Temperature[N, U](v)
  }
}
