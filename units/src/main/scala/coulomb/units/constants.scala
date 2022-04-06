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

package coulomb.units

object constants:
    import coulomb.*
    import coulomb.define.*
    import coulomb.rational.Rational

    import coulomb.units.si.{*, given}
    import coulomb.units.mksa.{*, given}

    final type SpeedOfLight
    given ctx_unit_SpeedOfLight: DerivedUnit[SpeedOfLight, 299792458 * Meter / Second, "speed-of-light", "c"] = DerivedUnit()

    final type PlanckConstant
    given ctx_unit_PlanckConstant: DerivedUnit[PlanckConstant, (662607015 * (10 ^ -42)) * Joule * Second, "planck-constant", "ℎ"] = DerivedUnit()

    // would prefer to use something like `Quantity[Rational, ?]` instead of `Any` here
    transparent inline def constq[CU]: Any = ${ meta.constq[CU] }

    object meta:
        import scala.quoted.*
        import coulomb.infra.meta.{*, given}

        def constq[CU](using Quotes, Type[CU]): Expr[Any] =
            import quotes.reflect.*
            given sigmode: SigMode = SigMode.Separate
            TypeRepr.of[CU] match
                case derivedunit(v, sig) =>
                    simplifysig(sig).asType match
                        case '[qu] => '{ ${Expr(v)}.withUnit[qu] }
                case u =>
                    report.error(s"unrecognized unit declaration: ${typestr(u)}")
                    '{ Rational.const0.withUnit[Nothing] }
