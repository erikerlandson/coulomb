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

object value:
    import _root_.scala.util.NotGiven

    import _root_.spire.math.*

    import coulomb.conversion.{ValueConversion, TruncatingValueConversion}

    // coulomb's standard conversions are based on the typelevel/spire
    // typeclasses ConvertableTo and ConvertableFrom, as well as
    // spire's categorizations of Fractional vs "not Fractional",
    // i.e. integer types
    //
    // spire's system includes definitions for scala's native
    // numeric types, including BigDecimal and BigInt
    //
    // extending this conversion system to other non-spire value
    // types can be accomplished by defining ConvertableFrom,
    // ConvertableTo and Fractional (if the type is fractional),
    // appropriately for such non-spire types.

    // anything can be safely converted to a fractional value type
    given ctx_spire_VC_XF[VF, VT](using
        vtf: Fractional[VT],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
    ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    // integer types can safely convert to each other
    given ctx_spire_VC_II[VF, VT](using
        vti: NotGiven[Fractional[VT]],
        vfi: NotGiven[Fractional[VF]],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
    ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    // converting fractional types to integer types is truncating
    given ctx_spire_TVC_FI[VF, VT](using
        vti: NotGiven[Fractional[VT]],
        vff: Fractional[VF],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
    ): TruncatingValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)
