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
        new ValueResolution[Double, Double]:
            type VO = Double

    transparent inline given ctx_VR_Double_Float: ValueResolution[Double, Float] =
        new ValueResolution[Double, Float]:
            type VO = Double

    transparent inline given ctx_VR_Double_Long: ValueResolution[Double, Long] =
        new ValueResolution[Double, Long]:
            type VO = Double

    transparent inline given ctx_VR_Double_Int: ValueResolution[Double, Int] =
        new ValueResolution[Double, Int]:
            type VO = Double

    transparent inline given ctx_VR_Float_Double: ValueResolution[Float, Double] =
        new ValueResolution[Float, Double]:
            type VO = Double

    transparent inline given ctx_VR_Float_Float: ValueResolution[Float, Float] =
        new ValueResolution[Float, Float]:
            type VO = Float

    transparent inline given ctx_VR_Float_Long: ValueResolution[Float, Long] =
        new ValueResolution[Float, Long]:
            type VO = Float

    transparent inline given ctx_VR_Float_Int: ValueResolution[Float, Int] =
        new ValueResolution[Float, Int]:
            type VO = Float

    transparent inline given ctx_VR_Long_Double: ValueResolution[Long, Double] =
        new ValueResolution[Long, Double]:
            type VO = Double

    transparent inline given ctx_VR_Long_Float: ValueResolution[Long, Float] =
        new ValueResolution[Long, Float]:
            type VO = Float

    transparent inline given ctx_VR_Long_Long: ValueResolution[Long, Long] =
        new ValueResolution[Long, Long]:
            type VO = Long

    transparent inline given ctx_VR_Long_Int: ValueResolution[Long, Int] =
        new ValueResolution[Long, Int]:
            type VO = Long

    transparent inline given ctx_VR_Int_Double: ValueResolution[Int, Double] =
        new ValueResolution[Int, Double]:
            type VO = Double

    transparent inline given ctx_VR_Int_Float: ValueResolution[Int, Float] =
        new ValueResolution[Int, Float]:
            type VO = Float

    transparent inline given ctx_VR_Int_Long: ValueResolution[Int, Long] =
        new ValueResolution[Int, Long]:
            type VO = Long

    transparent inline given ctx_VR_Int_Int: ValueResolution[Int, Int] =
        new ValueResolution[Int, Int]:
            type VO = Int
