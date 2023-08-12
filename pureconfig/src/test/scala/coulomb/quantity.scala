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

class QuantityDSLSuite extends CoulombSuite:
    import pureconfig.{*, given}

    import coulomb.*
    import coulomb.syntax.*

    import coulomb.policy.standard.given
    import algebra.instances.all.given
    import coulomb.ops.algebra.all.given

    import coulomb.pureconfig.*
    import coulomb.pureconfig.policy.DSL.given

    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}

    given given_pureconfig: PureconfigRuntime =
        PureconfigRuntime.of[
            "coulomb.units.si" *:
            "coulomb.units.si.prefixes" *:
            EmptyTuple
        ]

    test("smoke test") {
        ConfigSource.string("""{ value: 3, unit: kilometer}""")
            .load[Quantity[Float, Meter]]
            .assertRQ[Float, Meter](3000f)

        ConfigSource.string("""{ value: 3, unit: kilometer}""")
            .load[Quantity[Float, Second]]
            .assertL
    }

