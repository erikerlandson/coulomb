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

package coulomb.refined

import utest._

import spire.std.any._
import spire.algebra._

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._

import coulomb._
import coulomb.si._
import coulomb.us._

import coulomb.validators.CoulombValidators._

object RefinedTests extends TestSuite {
  val tests = Tests {
    test("refined NonNegative") {
      val q1 = refineMV[NonNegative](0.0).withUnit[Meter]
      val q2 = refineMV[NonNegative](0.0).withUnit[Foot]
      assert(q1.isValidQ[Refined[Double, NonNegative], Meter](0))
      assert(q2.isValidQ[Refined[Double, NonNegative], Foot](0))
      val q3 = refineMV[NonNegative](1.0).withUnit[Meter]
      val q4 = refineMV[NonNegative](1.0).withUnit[Foot]
      val q5 = refineMV[NonNegative](2.0).withUnit[Foot]
      assert((q3 + q4).isValidQ[Refined[Double, NonNegative], Meter](1.3048))
      assert((q4 + q3).isValidQ[Refined[Double, NonNegative], Foot](4.2808))
      assert((q1 * q3).isValidQ[Refined[Double, NonNegative], Meter %^ 2](0))
      assert((q1 / q3).isValidQ[Refined[Double, NonNegative], Unitless](0))
      assert((q3 * q5).isValidQ[Refined[Double, NonNegative], Meter %* Foot](2))
      assert((q3 / q5).isValidQ[Refined[Double, NonNegative], Meter %/ Foot](0.5))
    }
  }
}
