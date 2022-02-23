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

package coulomb.conversion.standard.integral

import coulomb.conversion.{ValueConversion, UnitConversion, ValueResolution}
import coulomb.Coefficient
import coulomb.rational.Rational

given ctx_VC_Double_Long: ValueConversion[Double, Long] with
    def apply(v: Double): Long = v.toLong

given ctx_VC_Double_Int: ValueConversion[Double, Int] with
    def apply(v: Double): Int = v.toInt

given ctx_VC_Float_Long: ValueConversion[Float, Long] with
    def apply(v: Float): Long = v.toLong

given ctx_VC_Float_Int: ValueConversion[Float, Int] with
    def apply(v: Float): Int = v.toInt

transparent inline given ctx_UC_Long[UF, UT](using coef: Coefficient[UF, UT]):
        UnitConversion[Long, UF, UT] =
    val nc = coef.value.n.toDouble
    val dc = coef.value.d.toDouble
    // using nc and dc is more efficient than using Rational directly in the conversion function
    // but still gives us 53 bits of integer precision for exact rational arithmetic, and also
    // graceful loss of precision if nc*v exceeds 53 bits
    new UnitConversion[Long, UF, UT]:
        def apply(v: Long): Long = ((nc * v) / dc).toLong

transparent inline given ctx_UC_Int[UF, UT](using coef: Coefficient[UF, UT]):
        UnitConversion[Int, UF, UT] =
    val nc = coef.value.n.toDouble
    val dc = coef.value.d.toDouble
    new UnitConversion[Int, UF, UT]:
        def apply(v: Int): Int = ((nc * v) / dc).toInt
