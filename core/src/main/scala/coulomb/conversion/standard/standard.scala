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

import coulomb.conversion.{ValueConversion, UnitConversion, ValueResolution}
import coulomb.Coefficient

// 16 of these - full crossproduct
transparent inline given ctx_VR_Double_Double: ValueResolution[Double, Double] =
    new ValueResolution[Double, Double]:
        type VO = Double

transparent inline given ctx_VR_Double_Float: ValueResolution[Double, Float] =
    new ValueResolution[Double, Float]:
        type VO = Double

transparent inline given ctx_VR_Float_Double: ValueResolution[Float, Double] =
    new ValueResolution[Float, Double]:
        type VO = Double

// 16 of these
// some will live in 'integral' subpackage to be enabled specifically
given ctx_VC_Double_Double: ValueConversion[Double, Double] with
    def apply(v: Double): Double = v

given ctx_VC_Float_Double: ValueConversion[Float, Double] with
    def apply(v: Float): Double = v.toDouble

// 4 of these - one for each of the big 4
// some will live in 'integral' subpackage to be enabled specifically
transparent inline given ctx_UC_Double[UF, UT](using coef: Coefficient[UF, UT]):
        UnitConversion[Double, UF, UT] =
    new UnitConversion[Double, UF, UT]:
        val c = coef.value.toDouble
        def apply(v: Double): Double = c * v

transparent inline given ctx_UC_Float[UF, UT](using coef: Coefficient[UF, UT]):
        UnitConversion[Float, UF, UT] =
    new UnitConversion[Float, UF, UT]:
        val c = coef.value.toFloat
        def apply(v: Float): Float = c * v
