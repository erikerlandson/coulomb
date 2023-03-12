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

package coulomb.parser

import scala.util.{Try, Success, Failure}

import coulomb.RuntimeUnit
import coulomb.rational.Rational

abstract class RuntimeUnitParser:
    def parse(expr: String): Either[String, RuntimeUnit]
    def render(u: RuntimeUnit): String

object standard:
    abstract class RuntimeUnitExprParser extends RuntimeUnitParser:
        def unames: Map[String, String]
        def pnames: Set[String]

        lazy val unamesinv: Map[String, String] =
            unames.map { (k, v) => (v, k) }

        private lazy val parser: (String => Either[String, RuntimeUnit]) =
            infra.parsing.parser(unames, pnames, unamesinv)

        final def parse(expr: String): Either[String, RuntimeUnit] =
            parser(expr)

        final def render(u: RuntimeUnit): String =
            def paren(s: String, tl: Boolean): String =
                if (tl) s else s"($s)"
            def rparen(r: Rational, tl: Boolean): String =
                if (r.d == 1)
                    s"${r.n}"
                else
                    paren(s"${r.n}/${r.d}", tl)
            def work(u: RuntimeUnit, tl: Boolean = false): String =
                u match
                    case RuntimeUnit.UnitConst(value) =>
                        rparen(value, tl)
                    case RuntimeUnit.UnitType(path) =>
                        if (unamesinv.contains(path))
                            // if it is in the inverse mapping write the name
                            unamesinv(path)
                        else
                            // otherwise write the fully qualified type name
                            s"@$path"
                    case RuntimeUnit.Mul(l, r) =>
                        paren(s"${work(l)}*${work(r)}", tl)
                    case RuntimeUnit.Div(n, d) =>
                        paren(s"${work(n)}/${work(d)}", tl)
                    case RuntimeUnit.Pow(b, e) =>
                        paren(s"${work(b)}^${rparen(e, false)}", tl)
            work(u, tl = true)

    object RuntimeUnitExprParser:
        inline def of[UTL <: Tuple]: RuntimeUnitExprParser =
            ${ infra.meta.ofUTL[UTL] }
