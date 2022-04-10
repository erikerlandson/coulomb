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

package coulomb.conversion.spire

object value:
    import scala.util.NotGiven

    import spire.math.{ ConvertableFrom, ConvertableTo, Fractional }

    import coulomb.conversion.*

    given ctx_spire_VC_XF[VF, VT](using
        vtf: Fractional[VT],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
            ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    given ctx_spire_VC_II[VF, VT](using
        vti: NotGiven[Fractional[VT]],
        vfi: NotGiven[Fractional[VF]],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
            ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    given ctx_spire_TVC_FI[VF, VT](using
        vti: NotGiven[Fractional[VT]],
        vff: Fractional[VF],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
            ): TruncatingValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)
