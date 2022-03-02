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

import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.coefficient
import coulomb.policy.AllowTruncation

import scala.util.NotGiven
import scala.math.{Fractional, Numeric}

inline given ctx_VC_Double[VF](using num: Numeric[VF]): ValueConversion[VF, Double] =
    new ValueConversion[VF, Double]:
        def apply(v: VF): Double = num.toDouble(v)

inline given ctx_VC_Float[VF](using num: Numeric[VF]): ValueConversion[VF, Float] =
    new ValueConversion[VF, Float]:
        def apply(v: VF): Float = num.toFloat(v)

inline given ctx_VC_Long[VF](using num: Numeric[VF],
    ivf: NotGiven[Fractional[VF]]
        ): ValueConversion[VF, Long] =
    new ValueConversion[VF, Long]:
        def apply(v: VF): Long = num.toLong(v)

inline given ctx_VC_Long_trunc[VF](using num: Numeric[VF],
    fvf: Fractional[VF],
    tre: AllowTruncation
        ): ValueConversion[VF, Long] =
    new ValueConversion[VF, Long]:
        def apply(v: VF): Long = num.toLong(v)

inline given ctx_VC_Int[VF](using num: Numeric[VF],
    ivf: NotGiven[Fractional[VF]]
        ): ValueConversion[VF, Int] =
    new ValueConversion[VF, Int]:
        def apply(v: VF): Int = num.toInt(v)

inline given ctx_VC_Int_trunc[VF](using num: Numeric[VF],
    fvf: Fractional[VF],
    tre: AllowTruncation
        ): ValueConversion[VF, Int] =
    new ValueConversion[VF, Int]:
        def apply(v: VF): Int = num.toInt(v)

// unit conversions that discard fractional values can be imported from 
// the 'truncating' subpackage
inline given ctx_UC_Double[UF, UT]:
        UnitConversion[Double, UF, UT] =
    val c = coulomb.conversion.infra.coefficientDouble[UF, UT]
    new UnitConversion[Double, UF, UT]:
        def apply(v: Double): Double = c * v

inline given ctx_UC_Float[UF, UT]:
        UnitConversion[Float, UF, UT] =
    val c = coulomb.conversion.infra.coefficientFloat[UF, UT]
    new UnitConversion[Float, UF, UT]:
        def apply(v: Float): Float = c * v

inline given ctx_UC_Long[UF, UT](using AllowTruncation):
        UnitConversion[Long, UF, UT] =
    val nc = coulomb.conversion.infra.coefficientNumDouble[UF, UT]
    val dc = coulomb.conversion.infra.coefficientDenDouble[UF, UT]
    // using nc and dc is more efficient than using Rational directly in the conversion function
    // but still gives us 53 bits of integer precision for exact rational arithmetic, and also
    // graceful loss of precision if nc*v exceeds 53 bits
    new UnitConversion[Long, UF, UT]:
        def apply(v: Long): Long = ((nc * v) / dc).toLong

inline given ctx_UC_Int[UF, UT](using AllowTruncation):
        UnitConversion[Int, UF, UT] =
    val nc = coulomb.conversion.infra.coefficientNumDouble[UF, UT]
    val dc = coulomb.conversion.infra.coefficientDenDouble[UF, UT]
    new UnitConversion[Int, UF, UT]:
        def apply(v: Int): Int = ((nc * v) / dc).toInt
