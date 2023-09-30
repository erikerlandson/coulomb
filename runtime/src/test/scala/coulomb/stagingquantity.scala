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

import scala.quoted.staging
import coulomb.conversion.runtimes.staging.StagingCoefficientRuntime

import coulomb.CoefficientRuntime

val compiler: staging.Compiler =
    staging.Compiler.make(classOf[staging.Compiler].getClassLoader)

val stagingRT: CoefficientRuntime = StagingCoefficientRuntime()(using compiler)

class StagingRuntimeQuantitySuite extends RuntimeQuantitySuite(using stagingRT)
