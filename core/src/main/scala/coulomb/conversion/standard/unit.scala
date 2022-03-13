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

    inline given ctx_UC_Double[UF, UT]: UnitConversion[Double, UF, UT] =
        val c = coefficientDouble[UF, UT]
        new UnitConversion[Double, UF, UT]:
            def apply(v: Double): Double = c * v

    inline given ctx_UC_Float[UF, UT]: UnitConversion[Float, UF, UT] =
        val c = coefficientFloat[UF, UT]
        new UnitConversion[Float, UF, UT]:
            def apply(v: Float): Float = c * v

    inline given ctx_TUC_Long[UF, UT]: TruncatingUnitConversion[Long, UF, UT] =
        val nc = coefficientNumDouble[UF, UT]
        val dc = coefficientDenDouble[UF, UT]
        // using nc and dc is more efficient than using Rational directly in the conversion function
        // but still gives us 53 bits of integer precision for exact rational arithmetic, and also
        // graceful loss of precision if nc*v exceeds 53 bits
        new TruncatingUnitConversion[Long, UF, UT]:
            def apply(v: Long): Long = ((nc * v) / dc).toLong

    inline given ctx_TUC_Int[UF, UT]: TruncatingUnitConversion[Int, UF, UT] =
        val nc = coefficientNumDouble[UF, UT]
        val dc = coefficientDenDouble[UF, UT]
        new TruncatingUnitConversion[Int, UF, UT]:
            def apply(v: Int): Int = ((nc * v) / dc).toInt
