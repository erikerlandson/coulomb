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

package coulomb.conversion.runtimes.staging

import scala.quoted.staging

import coulomb.*
import coulomb.infra.runtime.meta
import coulomb.rational.Rational

// a CoefficientRuntime that leverages a staging compiler to do runtime magic
// it will be possible to define other flavors of CoefficientRuntime that
// do not require staging compiler and so can work with JS and Native
class StagingCoefficientRuntime(using
    scmp: staging.Compiler
) extends CoefficientRuntime:
    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational] =
        meta.coefStaging(uf, ut)(using scmp)

object StagingCoefficientRuntime:
    def apply()(using
        scmp: staging.Compiler
    ): StagingCoefficientRuntime =
        new StagingCoefficientRuntime(using scmp)
