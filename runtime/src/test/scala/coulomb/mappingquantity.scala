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

import coulomb.units.si.{*, given}
import coulomb.units.si.prefixes.{*, given}
import coulomb.units.us.{*, given}

import coulomb.CoefficientRuntime
import coulomb.conversion.runtimes.mapping.MappingCoefficientRuntime

val mappingRT: CoefficientRuntime =
    MappingCoefficientRuntime
        .of["coulomb.units.si" *: "coulomb.units.si.prefixes" *: EmptyTuple]

class MappingRuntimeQuantitySuite extends RuntimeQuantitySuite(using mappingRT)
