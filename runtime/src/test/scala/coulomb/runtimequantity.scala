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
import coulomb.CoefficientRuntime

abstract class RuntimeQuantitySuite(using CoefficientRuntime)
    extends CoulombSuite:
    import coulomb.*
    import coulomb.syntax.*

    import algebra.instances.all.given
    import coulomb.ops.algebra.all.{*, given}

    import coulomb.units.si.{*, given}
    import coulomb.units.si.prefixes.{*, given}
    import coulomb.units.us.{*, given}

    test("runtimeCoefficient") {
        import coulomb.policy.strict.given
        runtimeCoefficient[Double](
            RuntimeUnit.of[Kilo * Meter],
            RuntimeUnit.of[Meter]
        ).assertRVT[Double](1000d)

        runtimeCoefficient[Double](
            RuntimeUnit.of[Kilo * Meter],
            RuntimeUnit.of[Second]
        ).assertL
    }

    test("toQuantity") {
        import coulomb.policy.strict.given
        RuntimeQuantity(1d, RuntimeUnit.of[Kilo * Meter])
            .toQuantity[Float, Meter]
            .assertRQ[Float, Meter](1000f)

        RuntimeQuantity(1d, RuntimeUnit.of[Kilo * Meter])
            .toQuantity[Float, Second]
            .assertL
    }

    test("addition strict") {
        import coulomb.policy.strict.given
        import coulomb.policy.overlay.runtime.strict.given

        (1d.withRuntimeUnit[Meter] + 1d.withRuntimeUnit[Kilo * Meter])
            .assertR(RuntimeQuantity[Meter](1001d))

        (1d.withRuntimeUnit[Second] + 1d.withRuntimeUnit[Kilo * Meter]).assertL

        assertCE("""
            (1.withRuntimeUnit[Meter] + 1d.withRuntimeUnit[Kilo * Meter])
        """)
    }

    test("addition standard") {
        import coulomb.policy.standard.given
        import coulomb.policy.overlay.runtime.standard.given

        (1.withRuntimeUnit[Meter] + 1d.withRuntimeUnit[Kilo * Meter])
            .assertR(RuntimeQuantity[Meter](1001d))

        (1.withRuntimeUnit[Second] + 1d.withRuntimeUnit[Kilo * Meter]).assertL
    }
