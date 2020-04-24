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

import spire.std.double._
import spire.std.float._
import spire.std.int._
import spire.std.long._

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.boolean.Not

import shapeless.nat.{ _0, _1 }

import coulomb._
import coulomb.si.{ Meter, Second }
import coulomb.us.{ Foot, Yard }

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
      assert((1D.withRefinedUnit[Positive, Meter] - (-1D).withRefinedUnit[NonPositive, Foot])
        .isValidQ[Refined[Double, Positive], Meter](1.3048))

      assert((1D.withRefinedUnit[NonNegative, Foot] - (-1f).withRefinedUnit[Negative, Meter])
        .isValidQ[Refined[Double, NonNegative], Foot](4.2808))

      assert((1f.withUnit[Meter] - (-1D).withRefinedUnit[Negative, Foot])
        .isValidQ[Float, Meter](1.3048))

      assert((1D.withRefinedUnit[Not[Less[-100D]], Meter] - (-1D).withRefinedUnit[Less[-0.999], Foot])
        .isValidQ[Refined[Double, Not[Less[-100D]]], Meter](1.3048))

      assert(((-1D).withRefinedUnit[Negative, Meter] - (1D).withRefinedUnit[NonNegative, Foot])
        .isValidQ[Refined[Double, Negative], Meter](-1.3048))

      assert(((-1D).withRefinedUnit[Less[10D], Meter] - (1f).withRefinedUnit[Greater[0.25f], Foot])
        .isValidQ[Refined[Double, Less[10D]], Meter](-1.3048))

      // unsound compile errors by default
      compileError("1D.withRefinedUnit[Positive, Meter] - 1f.withUnit[Foot]")
      compileError("1D.withRefinedUnit[Positive, Foot] - (-1f).withRefinedUnit[Less[10f], Meter]")
      compileError("(-1D).withRefinedUnit[Negative, Foot] - (1f).withRefinedUnit[Greater[-10f], Meter]")

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert((1D.withRefinedUnit[Positive, Foot] - (-1f).withUnit[Meter])
        .isValidQ[Refined[Double, Positive], Foot](4.2808))

      intercept[CoulombRefinedException] {
        1D.withRefinedUnit[Positive, Foot] - (1f).withUnit[Meter]
      }

      assert((1D.withRefinedUnit[Positive, Foot] - (-1f).withRefinedUnit[Less[10f], Meter])
        .isValidQ[Refined[Double, Positive], Foot](4.2808))

      assert(((-1D).withRefinedUnit[Negative, Foot] - (1f).withRefinedUnit[Greater[-10f], Meter])
        .isValidQ[Refined[Double, Negative], Foot](-4.2808))

      intercept[CoulombRefinedException] {
        1D.withRefinedUnit[Positive, Foot] - (1f).withRefinedUnit[Less[10f], Meter]
      }
    }

    test("refined multiplication") {
      assert((2D.withRefinedUnit[Positive, Meter] * 2D.withRefinedUnit[GreaterEqual[1D], Meter])
        .isValidQ[Refined[Double, Positive], Meter %^ 2](4))

      assert((2D.withRefinedUnit[NonNegative, Meter] * 2D.withRefinedUnit[Greater[1D], Meter])
        .isValidQ[Refined[Double, NonNegative], Meter %^ 2](4))

      assert(((-2D).withRefinedUnit[Negative, Meter] * 2D.withRefinedUnit[Greater[1D], Meter])
        .isValidQ[Refined[Double, Negative], Meter %^ 2](-4))

      assert(((-2D).withRefinedUnit[NonPositive, Meter] * 2D.withRefinedUnit[GreaterEqual[1D], Meter])
        .isValidQ[Refined[Double, NonPositive], Meter %^ 2](-4))

      assert(((2D).withRefinedUnit[Greater[1D], Meter] * 2D.withRefinedUnit[GreaterEqual[1D], Meter])
        .isValidQ[Refined[Double, Greater[1D]], Meter %^ 2](4))

      assert(((-2D).withRefinedUnit[Less[-1D], Meter] * 2D.withRefinedUnit[Greater[1D], Meter])
        .isValidQ[Refined[Double, Less[-1D]], Meter %^ 2](-4))

      assert((2D.withUnit[Meter] * 2D.withRefinedUnit[GreaterEqual[1D], Meter])
        .isValidQ[Double, Meter %^ 2](4))      

      // soundness abstraction leaks on underflow
      intercept[CoulombRefinedException] {
        (1e-300).withRefinedUnit[Positive, Meter] * (1e-300).withRefinedUnit[Positive, Meter]
      }

      compileError("2D.withRefinedUnit[Positive, Meter] * 2D.withRefinedUnit[GreaterEqual[0D], Meter]")
      compileError("2D.withRefinedUnit[Greater[1D], Meter] * 2D.withRefinedUnit[Positive, Meter]")
      compileError("2D.withRefinedUnit[Positive, Meter] * 2D.withUnit[Meter]")

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert((2D.withRefinedUnit[Positive, Meter] * 2D.withUnit[Meter])
        .isValidQ[Refined[Double, Positive], Meter %^ 2](4))

      assert((2D.withRefinedUnit[Positive, Meter] * 2D.withRefinedUnit[GreaterEqual[0D], Meter])
        .isValidQ[Refined[Double, Positive], Meter %^ 2](4))

      assert((2D.withRefinedUnit[Greater[1D], Meter] * 2D.withRefinedUnit[Positive, Meter])
        .isValidQ[Refined[Double, Greater[1D]], Meter %^ 2](4))

      intercept[CoulombRefinedException] {
        2D.withRefinedUnit[Positive, Meter] * 0D.withUnit[Meter]
      }

      intercept[CoulombRefinedException] {
        2D.withRefinedUnit[Positive, Meter] * (-1D).withRefinedUnit[GreaterEqual[-1D], Meter]
      }

      intercept[CoulombRefinedException] {
        2D.withRefinedUnit[Greater[1D], Meter] * (0.1).withRefinedUnit[Positive, Meter]
      }
    }

    test("refined division") {
      assert(((1D).withRefinedUnit[Positive, Meter] / (2D).withRefinedUnit[Not[Less[_1]], Second])
        .isValidQ[Refined[Double, Positive], Meter %/ Second](0.5))

      assert(((1D).withRefinedUnit[NonNegative, Meter] / (2D).withRefinedUnit[Greater[_1], Second])
        .isValidQ[Refined[Double, NonNegative], Meter %/ Second](0.5))

      assert(((-1D).withRefinedUnit[Negative, Meter] / (2D).withRefinedUnit[Not[Less[_1]], Second])
        .isValidQ[Refined[Double, Negative], Meter %/ Second](-0.5))

      assert(((-1D).withRefinedUnit[NonPositive, Meter] / (2D).withRefinedUnit[Greater[_1], Second])
        .isValidQ[Refined[Double, NonPositive], Meter %/ Second](-0.5))

      // underflow leaks through soundness
      intercept[CoulombRefinedException] {
        (1e-300).withRefinedUnit[Positive, Meter] / (1e300).withRefinedUnit[Positive, Second]
      }

      compileError("(1D).withRefinedUnit[Positive, Meter] / 2D.withUnit[Second]")
      compileError("(1D).withRefinedUnit[NonNegative, Meter] / (2D).withRefinedUnit[Greater[-1D], Second]")
      compileError("(-1D).withRefinedUnit[Negative, Meter] / (2D).withRefinedUnit[Not[Less[-1D]], Second]")

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert(((1D).withRefinedUnit[Positive, Meter] / 2D.withUnit[Second])
        .isValidQ[Refined[Double, Positive], Meter %/ Second](0.5))

      assert(((1D).withRefinedUnit[NonNegative, Meter] / (2D).withRefinedUnit[Greater[-1D], Second])
        .isValidQ[Refined[Double, NonNegative], Meter %/ Second](0.5))

      assert(((-1D).withRefinedUnit[Negative, Meter] / (2D).withRefinedUnit[Not[Less[-1D]], Second])
        .isValidQ[Refined[Double, Negative], Meter %/ Second](-0.5))

      intercept[CoulombRefinedException] {
        (1D).withRefinedUnit[Positive, Meter] / (-2D).withUnit[Second]
      }
      intercept[CoulombRefinedException] {
        (1D).withRefinedUnit[NonNegative, Meter] / (-0.5D).withRefinedUnit[Greater[-1D], Second]
      }
      intercept[CoulombRefinedException] {
        (-1D).withRefinedUnit[Negative, Meter] / (-1D).withRefinedUnit[Not[Less[-1D]], Second]
      }
    }

    test("refined power") {
      assert(0f.withRefinedUnit[NonNegative, Second].pow[2]
        .isValidQ[Refined[Float, NonNegative], Second %^ 2](0))

      assert(2f.withRefinedUnit[Positive, Second].pow[-3]
        .isValidQ[Refined[Float, Positive], Second %^ -3](1.0/8.0))

      assert(2f.withRefinedUnit[Greater[1f], Second].pow[3]
        .isValidQ[Refined[Float, Greater[1f]], Second %^ 3](8))

      compileError("2f.withRefinedUnit[NonNegative, Second].pow[-3]")
      compileError("2f.withRefinedUnit[Greater[-1f], Second].pow[3]")

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert((2f.withRefinedUnit[NonNegative, Second].pow[-3])
        .isValidQ[Refined[Float, NonNegative], Second %^ -3](1.0/8.0))

      assert((2f.withRefinedUnit[Greater[-1f], Second].pow[3])
        .isValidQ[Refined[Float, Greater[-1f]], Second %^ 3](8.0))

      intercept[Exception] {
        0.withRefinedUnit[NonNegative, Second].pow[-3]
      }

      intercept[CoulombRefinedException] {
        (0.5).withRefinedUnit[Greater[0.4], Second].pow[3]
      }
    }

    test("refined negative") {
      // currently no refined constraints my integrations recognize
      // are sound under negation.  Symmetric intervals would be.

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert((-(2.withRefinedUnit[Less[10], Meter]))
        .isValidQ[Refined[Int, Less[10]], Meter](-2))

      intercept[CoulombRefinedException] {
        -(2.withRefinedUnit[NonNegative, Meter])
      }
    }

    test("refined orderings") {
      // there are no unsound ordering comparisons
      assert((3D).withRefinedUnit[Positive, Foot] === (1f).withRefinedUnit[Not[Less[_1]], Yard])

      assert((1D).withRefinedUnit[Positive, Meter] > (1f).withRefinedUnit[Not[Less[_1]], Foot])

      assert((1D).withRefinedUnit[Positive, Foot] < (1f).withRefinedUnit[Not[Less[_1]], Meter])

      assert((1D).withUnit[Meter] >= (1f).withRefinedUnit[Not[Less[_1]], Foot])

      assert((1D).withRefinedUnit[Positive, Foot] < (1f).withUnit[Meter])
    }

    test("refined conversion") {
      // when units are not changing, only require that P1 ==> P2
      assert(((5D).withRefinedUnit[Not[Less[4D]], Meter].toValue[Refined[Float, Greater[_1]]])
        .isValidQ[Refined[Float, Greater[_1]], Meter](5))

      // unit conversions are only sound for pos and non-neg outputs
      // since conversion factors can be arbitrarily small
      assert(((1D).withRefinedUnit[NonNegative, Meter].toUnit[Foot])
        .isValidQ[Refined[Double, NonNegative], Foot](3.2808))

      assert(((1D).withRefinedUnit[Positive, Meter].toUnit[Foot])
        .isValidQ[Refined[Double, Positive], Foot](3.2808))

      assert(((1D).withRefinedUnit[Greater[0.9], Meter].to[Refined[Float, NonNegative], Foot])
        .isValidQ[Refined[Float, NonNegative], Foot](3.2808))

      assert(((1D).withRefinedUnit[Greater[0.9], Meter].to[Refined[Float, Positive], Foot])
        .isValidQ[Refined[Float, Positive], Foot](3.2808))

      // stripping refined always sound
      assert(((1D).withRefinedUnit[NonNegative, Meter].to[Float, Foot])
        .isValidQ[Float, Foot](3.2808))

      compileError("(1D).withRefinedUnit[NonNegative, Meter].toValue[Refined[Float, Positive]]")
      compileError("(1D).withUnit[Meter].toValue[Refined[Float, Positive]]")
      compileError("(1D).withRefinedUnit[Greater[-1D], Meter].to[Refined[Float, NonNegative], Foot]")

      // enable unsound
      import coulomb.refined.policy.unsoundRefinedConversions._

      assert(((1D).withRefinedUnit[NonNegative, Meter].toValue[Refined[Float, Positive]])
        .isValidQ[Refined[Float, Positive], Meter](1))

      assert(((1D).withUnit[Meter].toValue[Refined[Float, Positive]])
        .isValidQ[Refined[Float, Positive], Meter](1))

      assert(((1D).withRefinedUnit[Greater[-1D], Meter].to[Refined[Float, NonNegative], Foot])
        .isValidQ[Refined[Float, NonNegative], Foot](3.2808))

      intercept[CoulombRefinedException] {
        (0D).withRefinedUnit[NonNegative, Meter].toValue[Refined[Float, Positive]]
      }
      intercept[CoulombRefinedException] {
        (0D).withUnit[Meter].toValue[Refined[Float, Positive]]
      }
      intercept[CoulombRefinedException] {
        (-0.5D).withRefinedUnit[Greater[-1D], Meter].to[Refined[Float, NonNegative], Foot]
      }
    }
  }
}
