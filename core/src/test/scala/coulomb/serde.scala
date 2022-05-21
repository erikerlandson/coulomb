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

import coulomb.testing.CoulombSuite

// keeping this in a separate file makes it easier to exclude from certain
// platform builds that can't support it, such as scala.js
class QuantitySerDeSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}
    import algebra.instances.all.given
    import coulomb.ops.algebra.all.given

    test("serde") {
        import coulomb.testing.serde.roundTripSerDe

        val qo = 1d.withUnit[Meter]
        val qi = roundTripSerDe(qo)
        qi.assertQ[Double, Meter](1)
    }
