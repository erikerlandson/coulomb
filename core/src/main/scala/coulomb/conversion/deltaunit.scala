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

package coulomb.conversion

import scala.annotation.implicitNotFound

/**
 * A typeclass representing a function that converts a value from
 * one implied delta unit to another, with respect to a particular value type.
 * @tparam V
 *   The value type being converted
 * @tparam B
 *   The base unit scope of the delta unit
 * @tparam UF
 *   The unit being converted from
 * @tparam UT
 *   The unit being converted to
 */
@implicitNotFound(
    "No unit conversion in scope for value type ${V}, unit types ${UF} => ${UT}"
)
abstract class DeltaUnitConversion[V, B, UF, UT] extends (V => V)

/** Companion functions and definitions for delta unit conversions */
object DeltaUnitConversion:
    import scala.compiletime.*
    import algebra.ring.*
    import spire.math.*
    import coulomb.infra.typeexpr
    import coulomb.conversion.coefficients.*

    private inline def applyShim[V, B, UF, UT](v: V)(using
        ag: AdditiveGroup[V],
        msg: MultiplicativeSemigroup[V]
    ): V =
        // (c * (v + df)) - dt
        ag.minus(
            msg.times(
                Coefficient[V, UF, UT],
                ag.plus(
                    v,
                    Offset[V, UF, B]
                )
            ),
            Offset[V, UT, B]
        )

    /**
     * Convert a value from one implied delta unit to another.
     * @tparam V
     *   The value type being converted
     * @tparam B
     *   The base unit scope of delta unit `UF` and `UT`
     * @tparam UF
     *   The unit being converted from
     * @tparam UT
     *   The unit being converted to
     * @param v
     *   The value to convert
     * @return
     *   A value representing `v` delta units of `UF` converted to `UT`
     * @note
     *   If UF and UT are not convertible, or if a conversion
     *   cannot be constructed for value type `V`, then a
     *   compilation failure will result.
     */
    inline def apply[V, B, UF, UT](v: V): V =
        if (typeexpr.teq[UF, UT]) v
        else
            inline erasedValue[V] match
                case _: Float =>
                    applyShim[V, B, UF, UT](v)(using
                        summonInline[AdditiveGroup[V]],
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: Double =>
                    applyShim[V, B, UF, UT](v)(using
                        summonInline[AdditiveGroup[V]],
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: BigDecimal =>
                    applyShim[V, B, UF, UT](v)(using
                        summonInline[AdditiveGroup[V]],
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: Rational =>
                    applyShim[V, B, UF, UT](v)(using
                        summonInline[AdditiveGroup[V]],
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: java.lang.Float =>
                    applyShim[V, B, UF, UT](v)(using
                        summonInline[AdditiveGroup[V]],
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: java.lang.Double =>
                    applyShim[V, B, UF, UT](v)(using
                        summonInline[AdditiveGroup[V]],
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _ =>
                    summonInline[DeltaUnitConversion[V, B, UF, UT]](v)

    inline given g_DeltaUnitConversion[V, B, UF, UT](using
        AdditiveGroup[V],
        MultiplicativeSemigroup[V]
    ): DeltaUnitConversion[V, B, UF, UT] =
        new G_DeltaUnitConversion[V, B, UF, UT](
            Coefficient[V, UF, UT],
            Offset[V, UF, B],
            Offset[V, UT, B]
        )

    /**
     * An implicitly instantiated DeltaUnitConversion typeclass.
     * @tparam V
     *   The value type being converted
     * @tparam B
     *   The base unit scope of delta unit `UF` and `UT`
     * @tparam UF
     *   The unit being converted from
     * @tparam UT
     *   The unit being converted to
     * @param coef
     *   The conversion coefficient from `UF` to `UT`
     * @param df
     *   The offset of `UF`
     * @param dt
     *   The offset of `UT`
     */
    class G_DeltaUnitConversion[V, B, UF, UT](coef: V, df: V, dt: V)(using
        ag: AdditiveGroup[V],
        msg: MultiplicativeSemigroup[V]
    ) extends DeltaUnitConversion[V, B, UF, UT]:
        def apply(v: V): V =
            // (c * (v + df)) - dt
            ag.minus(msg.times(coef, ag.plus(v, df)), dt)
