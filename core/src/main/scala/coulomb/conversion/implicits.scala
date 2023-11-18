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

/**
 * Implicit conversion typeclasses for Quantity and DeltaQuantity.
 * @note
 *   For more information on scala.Conversion and scala 3 implicit conversions,
 *   see: https://docs.scala-lang.org/scala3/reference/contextual/conversions.html
 */
object implicits:
    import scala.Conversion
    import coulomb.*
    import coulomb.syntax.*

    // https://docs.scala-lang.org/scala3/reference/contextual/conversions.html

    given g_Quantity_Conversion[V, UF, UT](using
        uc: UnitConversion[V, UF, UT]
    ): Conversion[Quantity[V, UF], Quantity[V, UT]] =
        (q: Quantity[V, UF]) => uc(q.value).withUnit[UT]

    given g_DeltaQuantity_Conversion[B, V, UF, UT](using
        uc: DeltaUnitConversion[V, B, UF, UT]
    ): Conversion[DeltaQuantity[V, UF, B], DeltaQuantity[V, UT, B]] =
        (q: DeltaQuantity[V, UF, B]) => uc(q.value).withDeltaUnit[UT, B]

    // also support implicit lift of values to unitless quantity
    given g_Value_to_Unitless[V]: Conversion[V, Quantity[V, 1]] =
        (v: V) => v.withUnit[1]
