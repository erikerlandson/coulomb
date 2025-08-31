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
 * A typeclass for conversion between value types.
 * @tparam VF
 *   The value type to convert from
 * @tparam VT
 *   The value type to convert to
 */
@implicitNotFound("No value conversion in scope for value types ${VF} => ${VT}")
abstract class ValueConversion[VF, VT] extends (VF => VT)

/** Companion functions and definitions for value conversions */
object ValueConversion:
    import scala.compiletime.*

    import spire.math.*

    import coulomb.infra.typeexpr

    private inline def applyShim[VF, VT](v: VF)(using
        vc: ValueConversion[VF, VT]
    ): VT =
        vc(v)

    /**
     * Convert a value from one value type to another.
     * @tparam VF
     *   The value type to convert from
     * @tparam VT
     *   The value type to convert to
     * @param vf
     *   The value to convert.
     * @return
     *   The value `vf` converted to type `VT`
     * @note
     *   Results in a compile error if no conversion typeclass from
     *   `VF` to `VT` can be instantiated.
     */
    inline def apply[VF, VT](vf: VF): VT =
        if (typeexpr.teq[VF, VT])
            vf.asInstanceOf[VT]
        else
            applyShim[VF, VT](vf)(using summonInline[ValueConversion[VF, VT]])

    // coulomb's standard conversions are based on the typelevel/spire
    // typeclasses ConvertableTo and ConvertableFrom
    //
    // spire's system includes definitions for scala's native
    // numeric types, including BigDecimal and BigInt
    //
    // extending this conversion system to other non-spire value
    // types can be accomplished by defining ConvertableFrom,
    // ConvertableTo appropriately for such non-spire types.

    given g_ValueConversion[VF, VT](using
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
    ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    // native types can generate efficient code via inlining

    given g_ValueConversion_Int_Int: ValueConversion[Int, Int] with
        inline def apply(v: Int): Int = v

    given g_ValueConversion_Int_Long: ValueConversion[Int, Long] with
        inline def apply(v: Int): Long = v.toLong

    given g_ValueConversion_Int_Float: ValueConversion[Int, Float] with
        inline def apply(v: Int): Float = v.toFloat

    given g_ValueConversion_Int_Double: ValueConversion[Int, Double] with
        inline def apply(v: Int): Double = v.toDouble

    given g_ValueConversion_Long_Int: ValueConversion[Long, Int] with
        inline def apply(v: Long): Int = v.toInt

    given g_ValueConversion_Long_Long: ValueConversion[Long, Long] with
        inline def apply(v: Long): Long = v

    given g_ValueConversion_Long_Float: ValueConversion[Long, Float] with
        inline def apply(v: Long): Float = v.toFloat

    given g_ValueConversion_Long_Double: ValueConversion[Long, Double] with
        inline def apply(v: Long): Double = v.toDouble

    given g_ValueConversion_Float_Int: ValueConversion[Float, Int] with
        inline def apply(v: Float): Int = v.toInt

    given g_ValueConversion_Float_Long: ValueConversion[Float, Long] with
        inline def apply(v: Float): Long = v.toLong

    given g_ValueConversion_Float_Float: ValueConversion[Float, Float] with
        inline def apply(v: Float): Float = v

    given g_ValueConversion_Float_Double: ValueConversion[Float, Double] with
        inline def apply(v: Float): Double = v.toDouble

    given g_ValueConversion_Double_Int: ValueConversion[Double, Int] with
        inline def apply(v: Double): Int = v.toInt

    given g_ValueConversion_Double_Long: ValueConversion[Double, Long] with
        inline def apply(v: Double): Long = v.toLong

    given g_ValueConversion_Double_Float: ValueConversion[Double, Float] with
        inline def apply(v: Double): Float = v.toFloat

    given g_ValueConversion_Double_Double: ValueConversion[Double, Double] with
        inline def apply(v: Double): Double = v
