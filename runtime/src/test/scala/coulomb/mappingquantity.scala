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

class MappingRuntimeQuantitySuite extends CoulombSuite:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.runtime.*

    import algebra.instances.all.given
    import coulomb.ops.algebra.all.{*, given}

    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.us.{*, given}

    import coulomb.runtime.conversion.runtimes.mapping.MappingCoefficientRuntime

    import MappingCoefficientRuntime.{TNil, &:}
    given CoefficientRuntime =
        MappingCoefficientRuntime
            .of["coulomb.units.si" &: "coulomb.units.si.prefixes" &: TNil]

    test("runtimeCoefficient") {
        import coulomb.policy.strict.given
        val coef = runtimeCoefficient[Double](
            RuntimeUnit.of[Kilo * Meter],
            RuntimeUnit.of[Meter]
        )
        assert(coef.contains(1000d))
    }
