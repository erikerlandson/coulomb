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

/**
 * Physical constants as defined here:
 * https://en.wikipedia.org/wiki/List_of_physical_constants
 */
object constants:
    import coulomb.*
    import coulomb.define.*
    import coulomb.rational.Rational
    import coulomb.conversion.ValueConversion

    export coulomb.units.mksa.{*, given}

    /**
     * Obtain a Quantity representing a physical constant
     * @tparam V the desired value type of the constant
     * @tparam CU the unit type name representing the constant
     * @return a value Quantity[V, QU] where QU is the unit type of
     * the constant
     *
     * {{{
     * import coulomb.units.constants.{*, given}
     *
     * val planck = constant[Double, PlanckConstant]
     * // planck.show => "6.62607015E-34 J s"
     * }}}
     */
    transparent inline def constant[V, CU](using
        cq: infra.ConstQ[CU],
        vc: ValueConversion[Rational, V]
            ): Quantity[V, cq.QU] =
        vc(cq.value).withUnit[cq.QU]

    /** Speed of light in a vacuum */
    final type SpeedOfLight
    given ctx_unit_SpeedOfLight: DerivedUnit[SpeedOfLight, 299792458 * Meter / Second, "speed-of-light", "c"] = DerivedUnit()

    /** Planck's constant */
    final type PlanckConstant
    given ctx_unit_PlanckConstant: DerivedUnit[PlanckConstant, (662607015 * (10 ^ -42)) * Joule * Second, "planck-constant", "ℎ"] = DerivedUnit()

    object infra:
        abstract class ConstQ[CU]:
            type QU
            val value: Rational

        object ConstQ:
            import scala.quoted.*
            import coulomb.infra.meta.{*, given}

            class NC[CU, QUp](val value: Rational) extends ConstQ[CU]:
                type QU = QUp

            transparent inline given ctx_ConstQ[CU]: ConstQ[CU] = ${ constq[CU] }

            def constq[CU](using Quotes, Type[CU]): Expr[ConstQ[CU]] =
                import quotes.reflect.*
                given sigmode: SigMode = SigMode.Separate
                TypeRepr.of[CU] match
                    case derivedunit(v, sig) =>
                        simplifysig(sig).asType match
                            case '[qu] => '{ new NC[CU, qu](${Expr(v)}) }
                    case u =>
                        report.error(s"unrecognized unit declaration: ${typestr(u)}")
                        '{ new NC[CU, Nothing](Rational.const0) }
