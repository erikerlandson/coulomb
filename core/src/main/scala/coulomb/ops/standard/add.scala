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

package coulomb.ops.standard

import scala.util.NotGiven

import coulomb.ops.{Add, Sub, Mul, Div, Neg, Pow}
import coulomb.conversion.{ValueConversion, UnitConversion, ValueResolution}

// specialize these for efficiency, and as a
// proof of concept that specializations can operate in this system
transparent inline given ctx_add_Double_1U[U]: Add[Double, U, Double, U] =
    new Add[Double, U, Double, U]:
        type VO = Double
        type UO = U
        def apply(vl: Double, vr: Double): Double = vl + vr

transparent inline given ctx_add_Float_1U[U]: Add[Float, U, Float, U] =
    new Add[Float, U, Float, U]:
        type VO = Float
        type UO = U
        def apply(vl: Float, vr: Float): Float = vl + vr

// this could also be specialized for numeric types but
// the integrals specializations would have to live in the integral subpackage
// for now I'm going to drive that policy via UnitConversion
// as part of proof of concept
transparent inline given ctx_add_1V2U[V, UL, UR](using
    neu: NotGiven[UL =:= UR],
    ucv: UnitConversion[V, UR, UL],
    num: Algebra[V]
        ): Add[V, UL, V, UR] =
    new Add[V, UL, V, UR]:
        type VO = V
        type UO = UL
        def apply(vl: V, vr: V): VO = num.add(vl, ucv(vr))

transparent inline given ctx_add_2V1U[VL, VR, U](using
    nev: NotGiven[VL =:= VR],
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    num: Algebra[vres.VO]
        ): Add[VL, U, VR, U] =
    new Add[VL, U, VR, U]:
        type VO = vres.VO
        type UO = U
        def apply(vl: VL, vr: VR): VO = num.add(vlvo(vl), vrvo(vr))

transparent inline given ctx_add_2V2U[VL, UL, VR, UR](using
    nev: NotGiven[VL =:= VR],
    neu: NotGiven[UL =:= UR],
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    ucvo: UnitConversion[vres.VO, UR, UL],
    num: Algebra[vres.VO]
        ): Add[VL, UL, VR, UR] =
    new Add[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = UL
        def apply(vl: VL, vr: VR): VO = num.add(vlvo(vl), ucvo(vrvo(vr)))

