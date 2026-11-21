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
 * one implied unit to another, with respect to a particular value type.
 * @tparam V
 *   The value type being converted
 * @tparam UF
 *   The unit being converted from
 * @tparam UT
 *   The unit being converted to
 */
@implicitNotFound(
    "No unit conversion in scope for value type ${V}, unit types ${UF} => ${UT}"
)
abstract class UnitConversion[V, UF, UT] extends (V => V)

/** Companion functions and definitions for unit conversions */
object UnitConversion:
    import scala.compiletime.*
    import spire.math.Rational
    import algebra.ring.*
    import coulomb.infra.typeexpr
    import coulomb.conversion.coefficients.*

    // using this shim allows inlined methods from typeclasses
    // to compile out.  For some reason using summonInline directly does not.
    private inline def applyShim[V, UF, UT](v: V)(using
        alg: MultiplicativeSemigroup[V]
    ): V =
        alg.times(Coefficient[V, UF, UT], v)

    /**
     * Convert a value from one implied unit to another.
     * @tparam V
     *   The value type being converted
     * @tparam UF
     *   The unit being converted from
     * @tparam UT
     *   The unit being converted to
     * @param v
     *   The value to convert
     * @return
     *   A value representing `v` units of `UF` converted to `UT`
     * @note
     *   If UF and UT are not convertible, or if a conversion
     *   cannot be constructed for value type `V`, then a
     *   compilation failure will result.
     */
    inline def apply[V, UF, UT](v: V): V =
        if (typeexpr.uniteq[UF, UT]) v
        else
            inline erasedValue[V] match
                // This enumerates all the cases where I have an inline macro for the coefficient
                case _: Float =>
                    applyShim[V, UF, UT](v)(using
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: Double =>
                    applyShim[V, UF, UT](v)(using
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: BigDecimal =>
                    applyShim[V, UF, UT](v)(using
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: Rational =>
                    applyShim[V, UF, UT](v)(using
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: java.lang.Float =>
                    applyShim[V, UF, UT](v)(using
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _: java.lang.Double =>
                    applyShim[V, UF, UT](v)(using
                        summonInline[MultiplicativeSemigroup[V]]
                    )
                case _ =>
                    // otherwise summon a unit conversion from context
                    summonInline[UnitConversion[V, UF, UT]](v)

    inline given g_UnitConversion[V, UF, UT](using
        MultiplicativeSemigroup[V]
    ): UnitConversion[V, UF, UT] =
        inline erasedValue[V] match
            case _: Float =>
                new G_UnitConversion_Float[UF, UT](Coefficient[Float, UF, UT])
                    .asInstanceOf[UnitConversion[V, UF, UT]]
            case _: Double =>
                new G_UnitConversion_Double[UF, UT](Coefficient[Double, UF, UT])
                    .asInstanceOf[UnitConversion[V, UF, UT]]
            case _: java.lang.Float =>
                new G_UnitConversion_JFloat[UF, UT](
                    Coefficient[java.lang.Float, UF, UT]
                ).asInstanceOf[UnitConversion[V, UF, UT]]
            case _: java.lang.Double =>
                new G_UnitConversion_JDouble[UF, UT](
                    Coefficient[java.lang.Double, UF, UT]
                ).asInstanceOf[UnitConversion[V, UF, UT]]
            case _ =>
                new G_UnitConversion[V, UF, UT](Coefficient[V, UF, UT])

    class G_UnitConversion_Float[UF, UT](coef: Float)
        extends UnitConversion[Float, UF, UT]:
        def apply(v: Float): Float = coef * v

    class G_UnitConversion_Double[UF, UT](coef: Double)
        extends UnitConversion[Double, UF, UT]:
        def apply(v: Double): Double = coef * v

    class G_UnitConversion_JFloat[UF, UT](coef: java.lang.Float)
        extends UnitConversion[java.lang.Float, UF, UT]:
        def apply(v: java.lang.Float): java.lang.Float = coef * v

    class G_UnitConversion_JDouble[UF, UT](coef: java.lang.Double)
        extends UnitConversion[java.lang.Double, UF, UT]:
        def apply(v: java.lang.Double): java.lang.Double = coef * v

    /**
     * An implicitly instantiated UnitConversion typeclass.
     * @tparam V
     *   The value type being converted
     * @tparam UF
     *   The unit being converted from
     * @tparam UT
     *   The unit being converted to
     * @param coef
     *   The conversion coefficient from `UF` to `UT`
     */
    class G_UnitConversion[V, UF, UT](coef: V)(using
        alg: MultiplicativeSemigroup[V]
    ) extends UnitConversion[V, UF, UT]:
        def apply(v: V): V = alg.times(coef, v)
