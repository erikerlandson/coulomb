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
    import coulomb.conversion.*
 
    given ctx_VC_Double[VF](using num: Numeric[VF]): ValueConversion[VF, Double] =
        new ValueConversion[VF, Double]:
            def apply(v: VF): Double = num.toDouble(v)

    given ctx_VC_Float[VF](using num: Numeric[VF]): ValueConversion[VF, Float] =
        new ValueConversion[VF, Float]:
            def apply(v: VF): Float = num.toFloat(v)

    given ctx_VC_Long[VF](using num: Integral[VF]): ValueConversion[VF, Long] =
        new ValueConversion[VF, Long]:
            def apply(v: VF): Long = num.toLong(v)

    given ctx_TVC_Long[VF](using num: Fractional[VF]): TruncatingValueConversion[VF, Long] =
        new TruncatingValueConversion[VF, Long]:
            def apply(v: VF): Long = num.toLong(v)

    given ctx_VC_Int[VF](using num: Integral[VF]): ValueConversion[VF, Int] =
        new ValueConversion[VF, Int]:
            def apply(v: VF): Int = num.toInt(v)

    given ctx_TVC_Int[VF](using num: Fractional[VF]): TruncatingValueConversion[VF, Int] =
        new TruncatingValueConversion[VF, Int]:
            def apply(v: VF): Int = num.toInt(v)
