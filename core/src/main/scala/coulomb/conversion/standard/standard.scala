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

// conversions that discard fractional values can be imported from 
// the 'truncating' subpackage
given ctx_VC_Double_Double: ValueConversion[Double, Double] with
    def apply(v: Double): Double = v

given ctx_VC_Double_Float: ValueConversion[Double, Float] with
    def apply(v: Double): Float = v.toFloat

given ctx_VC_Float_Double: ValueConversion[Float, Double] with
    def apply(v: Float): Double = v.toDouble

given ctx_VC_Float_Float: ValueConversion[Float, Float] with
    def apply(v: Float): Float = v

given ctx_VC_Long_Double: ValueConversion[Long, Double] with
    def apply(v: Long): Double = v.toDouble

given ctx_VC_Long_Float: ValueConversion[Long, Float] with
    def apply(v: Long): Float = v.toFloat

given ctx_VC_Long_Long: ValueConversion[Long, Long] with
    def apply(v: Long): Long = v

given ctx_VC_Long_Int: ValueConversion[Long, Int] with
    def apply(v: Long): Int = v.toInt

given ctx_VC_Int_Double: ValueConversion[Int, Double] with
    def apply(v: Int): Double = v.toDouble

given ctx_VC_Int_Float: ValueConversion[Int, Float] with
    def apply(v: Int): Float = v.toFloat

given ctx_VC_Int_Long: ValueConversion[Int, Long] with
    def apply(v: Int): Long = v.toLong

given ctx_VC_Int_Int: ValueConversion[Int, Int] with
    def apply(v: Int): Int = v

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
