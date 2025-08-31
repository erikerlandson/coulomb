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
 * A typeclass representing a unit conversion coefficient.
 * @tparam V
 *   the value type the unit conversion is operating on
 * @tparam UF
 *   the unit being converted from
 * @tparam UT
 *   the unit being converted to
 * @param value
 *   the value of the conversion coefficient
 */
@implicitNotFound(
    "No coefficient could be derived for value type ${V}, unit types ${UF} => ${UT}"
)
class Coefficient[V, UF, UT](val value: V)

/** unit conversion companion methods and definitions */
object Coefficient:
    import scala.compiletime.*
    import algebra.ring.*
    import spire.math.*
    import coulomb.infra.typeexpr
    import coulomb.conversion.coefficients.*

    /**
     * Obtain the coefficient of conversion from one unit expression to another
     * @tparam UF
     *   the input unit expression
     * @tparam UT
     *   the output unit expression
     * @tparam V
     *   the value type to return
     * @return
     *   The coefficient of conversion from UF to UT.
     * @note
     *   If UF and UT are not convertible, or if a coefficient
     *   cannot be constructed for value type `V`, then a
     *   compilation failure will result.
     */
    inline def apply[V, UF, UT]: V =
        inline erasedValue[V] match
            case _: Float      => coefficientFloat[UF, UT].asInstanceOf[V]
            case _: Double     => coefficientDouble[UF, UT].asInstanceOf[V]
            case _: BigDecimal => coefficientBigDecimal[UF, UT].asInstanceOf[V]
            case _: Rational   => coefficientRational[UF, UT].asInstanceOf[V]
            case _: java.lang.Float =>
                coefficientFloatJ[UF, UT].asInstanceOf[V]
            case _: java.lang.Double =>
                coefficientDoubleJ[UF, UT].asInstanceOf[V]
            case _ =>
                summonInline[Coefficient[V, UF, UT]].value

    inline given g_Coefficient[V, UF, UT]: Coefficient[V, UF, UT] =
        inline erasedValue[V] match
            case _: Float =>
                new Coefficient[V, UF, UT](
                    coefficientFloat[UF, UT].asInstanceOf[V]
                )
            case _: Double =>
                new Coefficient[V, UF, UT](
                    coefficientDouble[UF, UT].asInstanceOf[V]
                )
            case _: BigDecimal =>
                new Coefficient[V, UF, UT](
                    coefficientBigDecimal[UF, UT].asInstanceOf[V]
                )
            case _: Rational =>
                new Coefficient[V, UF, UT](
                    coefficientRational[UF, UT].asInstanceOf[V]
                )
            case _: java.lang.Float =>
                new Coefficient[V, UF, UT](
                    coefficientFloatJ[UF, UT].asInstanceOf[V]
                )
            case _: java.lang.Double =>
                new Coefficient[V, UF, UT](
                    coefficientDoubleJ[UF, UT].asInstanceOf[V]
                )
            case _ =>
                val (_, vc) =
                    summonAll[(Fractional[V], ValueConversion[Rational, V])]
                new Coefficient[V, UF, UT](vc(coefficientRational[UF, UT]))
