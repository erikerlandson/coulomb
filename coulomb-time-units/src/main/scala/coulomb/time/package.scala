/*
Copyright 2017-2020 Erik Erlandson

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

package coulomb

import coulomb.define._

import coulomb.si._

/** Time units: Minute, Hour, Day, Week */
package object time {
  trait Minute
  implicit val defineUnitMinute = DerivedUnit[Minute, Second](60, abbv = "min")

  trait Hour
  implicit val defineUnitHour = DerivedUnit[Hour, Second](3600)

  trait Day
  implicit val defineUnitDay = DerivedUnit[Day, Second](86400)

  trait Week
  implicit val defineUnitWeek = DerivedUnit[Week, Day](7, abbv = "wk")
}
