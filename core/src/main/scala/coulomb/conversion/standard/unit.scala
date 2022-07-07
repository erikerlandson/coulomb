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

object unit:
    import coulomb.conversion.*
    import coulomb.conversion.coefficients.*
    import coulomb.rational.Rational

    inline given ctx_UC_Rational[UF, UT]: UnitConversion[Rational, UF, UT] =
        new infra.RationalUC[UF, UT](coefficientRational[UF, UT])

    inline given ctx_UC_Double[UF, UT]: UnitConversion[Double, UF, UT] =
        new infra.DoubleUC[UF, UT](coefficientDouble[UF, UT])

    inline given ctx_UC_Float[UF, UT]: UnitConversion[Float, UF, UT] =
        new infra.FloatUC[UF, UT](coefficientFloat[UF, UT])

    inline given ctx_TUC_Long[UF, UT]: TruncatingUnitConversion[Long, UF, UT] =
        new infra.LongTUC[UF, UT](coefficientNumDouble[UF, UT], coefficientDenDouble[UF, UT])

    inline given ctx_TUC_Int[UF, UT]: TruncatingUnitConversion[Int, UF, UT] =
        new infra.IntTUC[UF, UT](coefficientNumDouble[UF, UT], coefficientDenDouble[UF, UT])

    inline given ctx_DUC_Double[B, UF, UT]: DeltaUnitConversion[Double, B, UF, UT] =
        new infra.DoubleDUC[B, UF, UT](
            coefficientDouble[UF, UT], deltaOffsetDouble[UF, B], deltaOffsetDouble[UT, B])

    inline given ctx_DUC_Float[B, UF, UT]: DeltaUnitConversion[Float, B, UF, UT] =
        new infra.FloatDUC[B, UF, UT](
            coefficientFloat[UF, UT], deltaOffsetFloat[UF, B], deltaOffsetFloat[UT, B])

    inline given ctx_TDUC_Long[B, UF, UT]: TruncatingDeltaUnitConversion[Long, B, UF, UT] =
        new infra.LongTDUC[B, UF, UT](
            coefficientNumDouble[UF, UT], coefficientDenDouble[UF, UT],
            deltaOffsetDouble[UF, B], deltaOffsetDouble[UT, B])

    inline given ctx_TDUC_Int[B, UF, UT]: TruncatingDeltaUnitConversion[Int, B, UF, UT] =
        new infra.IntTDUC[B, UF, UT](
            coefficientNumDouble[UF, UT], coefficientDenDouble[UF, UT],
            deltaOffsetDouble[UF, B], deltaOffsetDouble[UT, B])

    object infra:
        class RationalUC[UF, UT](c: Rational) extends UnitConversion[Rational, UF, UT]:
            def apply(v: Rational): Rational = c * v

        class DoubleUC[UF, UT](c: Double) extends UnitConversion[Double, UF, UT]:
            def apply(v: Double): Double = c * v

        class FloatUC[UF, UT](c: Float) extends UnitConversion[Float, UF, UT]:
            def apply(v: Float): Float = c * v

        class LongTUC[UF, UT](nc: Double, dc: Double) extends TruncatingUnitConversion[Long, UF, UT]:
            // using nc and dc is more efficient than using Rational directly in the conversion function
            // but still gives us 53 bits of integer precision for exact rational arithmetic, and also
            // graceful loss of precision if nc*v exceeds 53 bits
            def apply(v: Long): Long = ((nc * v) / dc).toLong

        class IntTUC[UF, UT](nc: Double, dc: Double) extends TruncatingUnitConversion[Int, UF, UT]:
            def apply(v: Int): Int = ((nc * v) / dc).toInt

        class DoubleDUC[B, UF, UT](c: Double, df: Double, dt: Double) extends DeltaUnitConversion[Double, B, UF, UT]:
            def apply(v: Double): Double = ((v + df) * c) - dt

        class FloatDUC[B, UF, UT](c: Float, df: Float, dt: Float) extends DeltaUnitConversion[Float, B, UF, UT]:
            def apply(v: Float): Float = ((v + df) * c) - dt

        class LongTDUC[B, UF, UT](nc: Double, dc: Double, df: Double, dt: Double) extends
                TruncatingDeltaUnitConversion[Long, B, UF, UT]:
            def apply(v: Long): Long = (((nc * (v + df)) / dc) - dt).toLong

        class IntTDUC[B, UF, UT](nc: Double, dc: Double, df: Double, dt: Double) extends
                TruncatingDeltaUnitConversion[Int, B, UF, UT]:
            def apply(v: Int): Int = (((nc * (v + df)) / dc) - dt).toInt
