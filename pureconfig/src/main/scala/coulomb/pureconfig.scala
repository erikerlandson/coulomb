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

package coulomb.pureconfig

import coulomb.{infra => _, *}
import coulomb.syntax.*

import coulomb.rational.Rational

import coulomb.parser.RuntimeUnitParser

class PureconfigRuntime(cr: CoefficientRuntime, rup: RuntimeUnitParser)
    extends CoefficientRuntime
    with RuntimeUnitParser:

    def parse(expr: String): Either[String, RuntimeUnit] =
        rup.parse(expr)
    def render(u: RuntimeUnit): String =
        rup.render(u)

    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational] =
        cr.coefficientRational(uf, ut)

object PureconfigRuntime:
    import coulomb.conversion.runtimes.mapping.MappingCoefficientRuntime
    import coulomb.parser.standard.RuntimeUnitDslParser

    inline def of[UTL <: Tuple]: PureconfigRuntime =
        val r = MappingCoefficientRuntime.of[UTL]
        val p = RuntimeUnitDslParser.of[UTL]
        new PureconfigRuntime(r, p)
