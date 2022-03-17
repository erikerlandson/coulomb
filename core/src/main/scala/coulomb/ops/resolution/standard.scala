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

    given ctx_VR_Double_Double: ValueResolution[Double, Double] with
        type VO = Double

    given ctx_VR_Double_Float: ValueResolution[Double, Float] with
        type VO = Double

    given ctx_VR_Double_Long: ValueResolution[Double, Long] with
        type VO = Double

    given ctx_VR_Double_Int: ValueResolution[Double, Int] with
        type VO = Double

    given ctx_VR_Float_Double: ValueResolution[Float, Double] with
        type VO = Double

    given ctx_VR_Float_Float: ValueResolution[Float, Float] with
        type VO = Float

    given ctx_VR_Float_Long: ValueResolution[Float, Long] with
        type VO = Float

    given ctx_VR_Float_Int: ValueResolution[Float, Int] with
        type VO = Float

    given ctx_VR_Long_Double: ValueResolution[Long, Double] with
        type VO = Double

    given ctx_VR_Long_Float: ValueResolution[Long, Float] with
        type VO = Float

    given ctx_VR_Long_Long: ValueResolution[Long, Long] with
        type VO = Long

    given ctx_VR_Long_Int: ValueResolution[Long, Int] with
        type VO = Long

    given ctx_VR_Int_Double: ValueResolution[Int, Double] with
        type VO = Double

    given ctx_VR_Int_Float: ValueResolution[Int, Float] with
        type VO = Float

    given ctx_VR_Int_Long: ValueResolution[Int, Long] with
        type VO = Long

    given ctx_VR_Int_Int: ValueResolution[Int, Int] with
        type VO = Int
