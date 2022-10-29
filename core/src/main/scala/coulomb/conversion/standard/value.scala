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
    import coulomb.rational.Rational

    given ctx_VC_Double[VF](using
        num: Numeric[VF]
    ): ValueConversion[VF, Double] =
        new ValueConversion[VF, Double]:
            def apply(v: VF): Double = num.toDouble(v)

    given ctx_VC_Float[VF](using num: Numeric[VF]): ValueConversion[VF, Float] =
        new ValueConversion[VF, Float]:
            def apply(v: VF): Float = num.toFloat(v)

    given ctx_VC_Long[VF](using num: Integral[VF]): ValueConversion[VF, Long] =
        new ValueConversion[VF, Long]:
            def apply(v: VF): Long = num.toLong(v)

    given ctx_TVC_Long[VF](using
        num: Fractional[VF]
    ): TruncatingValueConversion[VF, Long] =
        new TruncatingValueConversion[VF, Long]:
            def apply(v: VF): Long = num.toLong(v)

    given ctx_VC_Int[VF](using num: Integral[VF]): ValueConversion[VF, Int] =
        new ValueConversion[VF, Int]:
            def apply(v: VF): Int = num.toInt(v)

    given ctx_TVC_Int[VF](using
        num: Fractional[VF]
    ): TruncatingValueConversion[VF, Int] =
        new TruncatingValueConversion[VF, Int]:
            def apply(v: VF): Int = num.toInt(v)

    given ctx_VC_Rational_Rational: ValueConversion[Rational, Rational] with
        def apply(v: Rational): Rational = v

    given ctx_VC_Rational_Double: ValueConversion[Rational, Double] with
        def apply(v: Rational): Double = v.toDouble

    given ctx_VC_Rational_Float: ValueConversion[Rational, Float] with
        def apply(v: Rational): Float = v.toFloat

    given ctx_TVC_Rational_Long: TruncatingValueConversion[Rational, Long] with
        def apply(v: Rational): Long = v.toLong

    given ctx_TVC_Rational_Int: TruncatingValueConversion[Rational, Int] with
        def apply(v: Rational): Int = v.toInt

    given ctx_VC_Double_Rational: ValueConversion[Double, Rational] with
        def apply(v: Double): Rational = Rational(v)

    given ctx_VC_Float_Rational: ValueConversion[Float, Rational] with
        def apply(v: Float): Rational = Rational(v)

    given ctx_VC_Long_Rational: ValueConversion[Long, Rational] with
        def apply(v: Long): Rational = Rational(v)

    given ctx_VC_Int_Rational: ValueConversion[Int, Rational] with
        def apply(v: Int): Rational = Rational(v)
