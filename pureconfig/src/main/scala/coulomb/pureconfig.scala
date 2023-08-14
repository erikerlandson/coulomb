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

trait UnitPathMapper:
    def path(unit: String): Either[String, String]

trait PathUnitMapper:
    def unit(path: String): String

class PureconfigRuntime(
    cr: CoefficientRuntime,
    rup: RuntimeUnitParser,
    upm: String => Either[String, String],
    pum: String => String
) extends CoefficientRuntime
    with RuntimeUnitParser
    with UnitPathMapper
    with PathUnitMapper:

    def parse(expr: String): Either[String, RuntimeUnit] =
        rup.parse(expr)
    def render(u: RuntimeUnit): String =
        rup.render(u)

    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational] =
        cr.coefficientRational(uf, ut)

    def path(unit: String): Either[String, String] =
        upm(unit)
    def unit(path: String): String =
        pum(path)

object PureconfigRuntime:
    import coulomb.conversion.runtimes.mapping.MappingCoefficientRuntime
    import coulomb.parser.standard.RuntimeUnitDslParser

    inline def of[UTL <: Tuple]: PureconfigRuntime =
        val r = MappingCoefficientRuntime.of[UTL]
        val p = RuntimeUnitDslParser.of[UTL]
        val upm: (String => Either[String, String]) = (unit: String) => {
            if (unit.isEmpty) Left(unit)
            else if (unit.head == '@') Right(unit.tail)
            else if (p.unames.contains(unit)) Right(p.unames(unit))
            else Left(unit)
        }
        val pum: (String => String) = (path: String) => {
            if (path.isEmpty) throw new Exception("empty path string")
            else if (p.unamesinv.contains(path)) p.unamesinv(path)
            else s"@${path}"
        }
        new PureconfigRuntime(r, p, upm, pum)
