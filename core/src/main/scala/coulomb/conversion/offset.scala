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
 * A typeclass representing a delta unit conversion offset.
 * @tparam V
 *   the value type the unit conversion is operating on
 * @tparam U
 *   the unit being converted
 * @tparam B
 *   the base unit scope
 * @param value
 *   the value of the conversion offset
 */
@implicitNotFound(
    "No offset could be derived for Offset[${V}, ${U}, ${B}]"
)
class Offset[V, U, B](val value: V)

/** delta unit offset companion methods and definitions */
object Offset:
    import scala.compiletime.*
    import algebra.ring.*
    import spire.math.*
    import coulomb.infra.typeexpr
    import coulomb.conversion.coefficients.*

    /**
     * Obtain the conversion offset for a delta unit
     * @tparam V
     *   the value type the unit conversion is operating on
     * @tparam U
     *   the unit being converted
     * @tparam B
     *   the base unit scope
     * @return
     *   The conversion offset for delta unit U.
     * @note
     *   If no offset is defined for `U` (with respect to base unit `B`),
     *   or the offset cannot be constructed for value type `V`, then a
     *   compilation failure will result.
     */
    inline def apply[V, U, B]: V =
        inline erasedValue[V] match
            case _: Float            => deltaOffsetFloat[U, B].asInstanceOf[V]
            case _: Double           => deltaOffsetDouble[U, B].asInstanceOf[V]
            case _: BigDecimal       => deltaOffsetBigDecimal[U, B].asInstanceOf[V]
            case _: Rational         => deltaOffsetRational[U, B].asInstanceOf[V]
            case _: java.lang.Float  => deltaOffsetFloatJ[U, B].asInstanceOf[V]
            case _: java.lang.Double => deltaOffsetDoubleJ[U, B].asInstanceOf[V]
            case _ =>
                summonInline[Offset[V, U, B]].value

    inline given g_Offset[V, U, B]: Offset[V, U, B] =
        inline erasedValue[V] match
            case _: Float =>
                new Offset[V, U, B](deltaOffsetFloat[U, B].asInstanceOf[V])
            case _: Double =>
                new Offset[V, U, B](deltaOffsetDouble[U, B].asInstanceOf[V])
            case _: BigDecimal =>
                new Offset[V, U, B](deltaOffsetBigDecimal[U, B].asInstanceOf[V])
            case _: Rational =>
                new Offset[V, U, B](deltaOffsetRational[U, B].asInstanceOf[V])
            case _: java.lang.Float =>
                new Offset[V, U, B](deltaOffsetFloatJ[U, B].asInstanceOf[V])
            case _: java.lang.Double =>
                new Offset[V, U, B](deltaOffsetDoubleJ[U, B].asInstanceOf[V])
            case _ =>
                val (_, vc) =
                    summonAll[(Fractional[V], ValueConversion[Rational, V])]
                new Offset[V, U, B](vc(deltaOffsetRational[U, B]))
