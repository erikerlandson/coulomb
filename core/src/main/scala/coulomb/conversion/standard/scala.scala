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

package coulomb.conversion.standard

object scala:
    import _root_.scala.Conversion
    import coulomb.conversion.*
    import coulomb.*

    // Enable the compiler to implicitly convert Quantity[V1, U1] -> Quantity[V2, U2], 
    // whenever a valid conversion exists:
    // https://docs.scala-lang.org/scala3/reference/contextual/conversions.html

    given ctx_Quantity_Conversion_1V1U[V, U]: Conversion[Quantity[V, U], Quantity[V, U]] =
        (q: Quantity[V, U]) => q

    given ctx_Quantity_Conversion_1V2U[V, UF, UT](using
        uc: UnitConversion[V, UF, UT]
            ): Conversion[Quantity[V, UF], Quantity[V, UT]] =
        (q: Quantity[V, UF]) => uc(q.value).withUnit[UT]

    given ctx_Quantity_Conversion_2V1U[U, VF, VT](using
        vc: ValueConversion[VF, VT],
            ): Conversion[Quantity[VF, U], Quantity[VT, U]] =
        (q: Quantity[VF, U]) => vc(q.value).withUnit[U]

    given ctx_Quantity_Conversion_2V2U[VF, UF, VT, UT](using
        vc: ValueConversion[VF, VT],
        uc: UnitConversion[VT, UF, UT]
            ): Conversion[Quantity[VF, UF], Quantity[VT, UT]] =
        (q: Quantity[VF, UF]) => uc(vc(q.value)).withUnit[UT]

    given ctx_DeltaQuantity_conversion_2V2U[B, VF, UF, VT, UT](using
        vc: ValueConversion[VF, VT],
        uc: DeltaUnitConversion[VT, B, UF, UT]
            ): Conversion[DeltaQuantity[VF, UF, B], DeltaQuantity[VT, UT, B]] =
        (q: DeltaQuantity[VF, UF, B]) => uc(vc(q.value)).withDeltaUnit[UT, B]
