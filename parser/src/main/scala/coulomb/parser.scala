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

package coulomb

import coulomb.RuntimeUnit

package parser {
    abstract class RuntimeUnitParser:
        def parse(expr: String): RuntimeUnit
        def render(u: RuntimeUnit): String

    object standard:
        sealed abstract class RuntimeUnitExprParser extends RuntimeUnitParser:
            protected def unames: Map[String, String]
            protected def pnames: Set[String]
            def parse(expr: String): RuntimeUnit = ???
            def render(u: RuntimeUnit): String = ???

        object RuntimeUnitExprParser:
            inline def of[UTL]: RuntimeUnitExprParser = ${ meta.ofUTL[UTL] }

    object meta:
        import scala.quoted.*
        import scala.util.{Try, Success, Failure}
        import scala.unchecked
        import scala.language.implicitConversions

        import coulomb.infra.meta.{*, given}
        import coulomb.infra.runtime.meta.{*, given}

        import coulomb.parser.standard.RuntimeUnitExprParser

        def ofUTL[UTL](using Quotes, Type[UTL]): Expr[RuntimeUnitExprParser] =
            val un = Map.empty[String, String]
            val pn = Set.empty[String]
            '{
                new RuntimeUnitExprParser:
                    protected val unames = ${ Expr(un) }
                    protected val pnames = ${ Expr(pn) }
            }
}
