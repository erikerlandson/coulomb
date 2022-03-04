/*
 * Copyright 2022 Erik Erlandson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package coulomb.testkit

import coulomb.rational.*
import munit.ScalaCheckSuite
import org.scalacheck.Prop.*

class RationalSuite extends ScalaCheckSuite:
  property("rational identity") {
    forAll { (r: Rational) =>
      r == Rational.const0 || (Rational.const1 / r) * r == Rational.const1
    }
  }

  property("functions of rationals are pure") {
    forAll { (x: Rational, y: Rational, f: Rational => Rational) =>
      x != y || f(x) == f(y)
    }
  }
