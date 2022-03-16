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
    import coulomb.{withUnit, Quantity}

    // Enable the compiler to implicitly convert Quantity[V1, U1] -> Quantity[V2, U2], 
    // whenever a valid conversion exists: reference:
    // https://docs.scala-lang.org/scala3/reference/contextual/conversions.html
    // I gate this with AllowImplicitConversions because many operations on coulomb
    // Quantity are logically equivalent to converting unit or value types, and
    // I can't use scala.language.implicitConversions as a gate 
    given ctx_implicit_quantity_conversion_1V1U[V, U]: Conversion[Quantity[V, U], Quantity[V, U]] =
        new Conversion[Quantity[V, U], Quantity[V, U]]:
            def apply(q: Quantity[V, U]): Quantity[V, U] = q 

    given ctx_implicit_quantity_conversion_1V2U[V, UF, UT](using
        uc: UnitConversion[V, UF, UT]
            ): Conversion[Quantity[V, UF], Quantity[V, UT]] =
        new Conversion[Quantity[V, UF], Quantity[V, UT]]:
            def apply(q: Quantity[V, UF]): Quantity[V, UT] = 
                uc(q.value).withUnit[UT]

    given ctx_implicit_quantity_conversion_2V1U[U, VF, VT](using
        vc: ValueConversion[VF, VT],
            ): Conversion[Quantity[VF, U], Quantity[VT, U]] =
        new Conversion[Quantity[VF, U], Quantity[VT, U]]:
            def apply(q: Quantity[VF, U]): Quantity[VT, U] = 
                vc(q.value).withUnit[U]

    given ctx_implicit_quantity_conversion_2V2U[VF, UF, VT, UT](using
        vc: ValueConversion[VF, VT],
        uc: UnitConversion[VT, UF, UT]
            ): Conversion[Quantity[VF, UF], Quantity[VT, UT]] =
        new Conversion[Quantity[VF, UF], Quantity[VT, UT]]:
            def apply(q: Quantity[VF, UF]): Quantity[VT, UT] = 
                uc(vc(q.value)).withUnit[UT]

