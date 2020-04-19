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
import eu.timepit.refined.boolean.{ And, Not }

import coulomb._
import coulomb.si._
import coulomb.us._

import coulomb.validators.CoulombValidators._

object RefinedTests extends TestSuite {
  val tests = Tests {
    test("toRefined") {
      assert(1.withUnit[Meter].toRefined[Positive].isValidQ[Refined[Int, Positive], Meter](1))
      intercept[CoulombRefinedException] { 0.withUnit[Meter].toRefined[Positive] }
    }

    test("withRefinedUnit") {
      assert(1.withRefinedUnit[Positive, Meter].isValidQ[Refined[Int, Positive], Meter](1))
      intercept[CoulombRefinedException] { 0.withRefinedUnit[Positive, Meter] }
    }

    test("refined addition") {
      assert((1D.withRefinedUnit[Positive, Meter] + 1D.withRefinedUnit[NonNegative, Foot])
        .isValidQ[Refined[Double, Positive], Meter](1.3048))

      assert((1D.withRefinedUnit[NonNegative, Foot] + 1f.withRefinedUnit[Positive, Meter])
        .isValidQ[Refined[Double, NonNegative], Foot](4.2808))

      assert((1f.withUnit[Meter] + 1D.withRefinedUnit[Positive, Foot])
        .isValidQ[Float, Meter](1.3048))

      assert((1D.withRefinedUnit[Not[Less[-100D]], Meter] + 1D.withRefinedUnit[Greater[0.999], Foot])
        .isValidQ[Refined[Double, Not[Less[-100D]]], Meter](1.3048))

      assert(((-1D).withRefinedUnit[Negative, Meter] + (-1D).withRefinedUnit[NonPositive, Foot])
        .isValidQ[Refined[Double, Negative], Meter](-1.3048))

      assert(((-1D).withRefinedUnit[Less[10D], Meter] + (-1f).withRefinedUnit[Less[-0.25f], Foot])
        .isValidQ[Refined[Double, Less[10D]], Meter](-1.3048))

      // unsound compile errors by default
      compileError("1D.withRefinedUnit[Positive, Meter] + 1f.withUnit[Foot]")
      compileError("1D.withRefinedUnit[Positive, Foot] + 1f.withRefinedUnit[Greater[-10f], Meter]")
      compileError("(-1D).withRefinedUnit[Negative, Foot] + (-1f).withRefinedUnit[Less[10f], Meter]")

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert((1D.withRefinedUnit[Positive, Foot] + 1f.withUnit[Meter])
        .isValidQ[Refined[Double, Positive], Foot](4.2808))

      intercept[CoulombRefinedException] {
        1D.withRefinedUnit[Positive, Foot] + (-1f).withUnit[Meter]
      }

      assert((1D.withRefinedUnit[Positive, Foot] + 1f.withRefinedUnit[Greater[-10f], Meter])
        .isValidQ[Refined[Double, Positive], Foot](4.2808))

      assert(((-1D).withRefinedUnit[Negative, Foot] + (-1f).withRefinedUnit[Less[10f], Meter])
        .isValidQ[Refined[Double, Negative], Foot](-4.2808))

      intercept[CoulombRefinedException] {
        1D.withRefinedUnit[Positive, Foot] + (-1f).withRefinedUnit[Greater[-10f], Meter]
      }
    }

    test("refined subtraction") {
      val q3 = refineMV[Positive](1.0).withUnit[Meter]
      val q4 = refineMV[Negative](-1.0).withUnit[Foot]
      assert((q3 - q4).isValidQ[Refined[Double, Positive], Meter](1.3048))
    }

    test("refined multiplication") {
      val q1 = refineMV[NonNegative](0.0).withUnit[Meter]
      val q3 = refineMV[Positive](1.0).withUnit[Meter]
      val q5 = refineMV[Positive](2.0).withUnit[Foot]
      assert((q1 * q3).isValidQ[Refined[Double, NonNegative], Meter %^ 2](0))
      assert((q3 * q5).isValidQ[Refined[Double, Positive], Meter %* Foot](2))
    }

    test("refined division") {
      val q1 = refineMV[NonNegative](0.0).withUnit[Meter]
      val q3 = refineMV[Positive](1.0).withUnit[Meter]
      val q5 = refineMV[Positive](2.0).withUnit[Foot]
      assert((q1 / q3).isValidQ[Refined[Double, NonNegative], Unitless](0))
      assert((q3 / q5).isValidQ[Refined[Double, Positive], Meter %/ Foot](0.5))
    }

    test("refined power") {
      assert(2f.withRefinedUnit[Positive, Second].pow[3]
        .isValidQ[Refined[Float, Positive], Second %^ 3](8))
      assert(0f.withRefinedUnit[NonNegative, Second].pow[2]
        .isValidQ[Refined[Float, NonNegative], Second %^ 2](0))
      assert(2f.withRefinedUnit[Positive, Second].pow[-3]
        .isValidQ[Refined[Float, Positive], Second %^ -3](1.0/8.0))
    }
  }
}
