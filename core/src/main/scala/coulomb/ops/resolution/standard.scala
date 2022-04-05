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

package coulomb.ops.resolution

object standard:
    import coulomb.ops.ValueResolution

    transparent inline given ctx_VR_Double_Double: ValueResolution[Double, Double] =
        new infra.VRNC[Double, Double, Double]

    transparent inline given ctx_VR_Double_Float: ValueResolution[Double, Float] =
        new infra.VRNC[Double, Float, Double]

    transparent inline given ctx_VR_Double_Long: ValueResolution[Double, Long] =
        new infra.VRNC[Double, Long, Double]

    transparent inline given ctx_VR_Double_Int: ValueResolution[Double, Int] =
        new infra.VRNC[Double, Int, Double]

    transparent inline given ctx_VR_Float_Double: ValueResolution[Float, Double] =
        new infra.VRNC[Float, Double, Double]

    transparent inline given ctx_VR_Float_Float: ValueResolution[Float, Float] =
        new infra.VRNC[Float, Float, Float]

    transparent inline given ctx_VR_Float_Long: ValueResolution[Float, Long] =
        new infra.VRNC[Float, Long, Float]

    transparent inline given ctx_VR_Float_Int: ValueResolution[Float, Int] =
        new infra.VRNC[Float, Int, Float]

    transparent inline given ctx_VR_Long_Double: ValueResolution[Long, Double] =
        new infra.VRNC[Long, Double, Double]

    transparent inline given ctx_VR_Long_Float: ValueResolution[Long, Float] =
        new infra.VRNC[Long, Float, Float]

    transparent inline given ctx_VR_Long_Long: ValueResolution[Long, Long] =
        new infra.VRNC[Long, Long, Long]

    transparent inline given ctx_VR_Long_Int: ValueResolution[Long, Int] =
        new infra.VRNC[Long, Int, Long]

    transparent inline given ctx_VR_Int_Double: ValueResolution[Int, Double] =
        new infra.VRNC[Int, Double, Double]

    transparent inline given ctx_VR_Int_Float: ValueResolution[Int, Float] =
        new infra.VRNC[Int, Float, Float]

    transparent inline given ctx_VR_Int_Long: ValueResolution[Int, Long] =
        new infra.VRNC[Int, Long, Long]

    transparent inline given ctx_VR_Int_Int: ValueResolution[Int, Int] =
        new infra.VRNC[Int, Int, Int]

    object infra:
        class VRNC[VL, VR, VOp] extends ValueResolution[VL, VR]:
            type VO = VOp
