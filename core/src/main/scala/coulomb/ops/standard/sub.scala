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

import algebra.ring.AdditiveGroup

import coulomb.ops.{Sub, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions

transparent inline given ctx_sub_Double_1U[U]: Sub[Double, U, Double, U] =
    new Sub[Double, U, Double, U]:
        type VO = Double
        type UO = U
        def apply(vl: Double, vr: Double): Double = vl - vr

transparent inline given ctx_sub_Float_1U[U]: Sub[Float, U, Float, U] =
    new Sub[Float, U, Float, U]:
        type VO = Float
        type UO = U
        def apply(vl: Float, vr: Float): Float = vl - vr

transparent inline given ctx_sub_Double_2U[UL, UR](using
    AllowImplicitConversions,
    NotGiven[UL =:= UR]
        ): Sub[Double, UL, Double, UR] =
    val c = coulomb.conversion.infra.coefficientDouble[UR, UL]
    new Sub[Double, UL, Double, UR]:
        type VO = Double
        type UO = UL
        def apply(vl: Double, vr: Double): Double = vl - (c * vr)

transparent inline given ctx_sub_Float_2U[UL, UR](using
    AllowImplicitConversions,
    NotGiven[UL =:= UR]
        ): Sub[Float, UL, Float, UR] =
    val c = coulomb.conversion.infra.coefficientFloat[UR, UL]
    new Sub[Float, UL, Float, UR]:
        type VO = Float
        type UO = UL
        def apply(vl: Float, vr: Float): Float = vl - (c * vr)

transparent inline given ctx_sub_1V1U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    equ: UR =:= UL,
    alg: AdditiveGroup[VL]
        ): Sub[VL, UL, VR, UR] =
    new Sub[VL, UL, VR, UR]:
        type VO = VL
        type UO = UL
        def apply(vl: VL, vr: VR): VL = alg.minus(vl, eqv(vr))

transparent inline given ctx_sub_1V2U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    eqv: VR =:= VL,
    neu: NotGiven[UR =:= UL],
    ucv: UnitConversion[VL, UR, UL],
    alg: AdditiveGroup[VL]
        ): Sub[VL, UL, VR, UR] =
    new Sub[VL, UL, VR, UR]:
        type VO = VL
        type UO = UL
        def apply(vl: VL, vr: VR): VL = alg.minus(vl, ucv(eqv(vr)))

transparent inline given ctx_sub_2V1U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    nev: NotGiven[VR =:= VL],
    equ: UR =:= UL,
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    alg: AdditiveGroup[vres.VO]
        ): Sub[VL, UL, VR, UR] =
    new Sub[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = UL
        def apply(vl: VL, vr: VR): VO = alg.minus(vlvo(vl), vrvo(vr))

transparent inline given ctx_sub_2V2U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    nev: NotGiven[VL =:= VR],
    neu: NotGiven[UL =:= UR],
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    ucvo: UnitConversion[vres.VO, UR, UL],
    alg: AdditiveGroup[vres.VO]
        ): Sub[VL, UL, VR, UR] =
    new Sub[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = UL
        def apply(vl: VL, vr: VR): VO = alg.minus(vlvo(vl), ucvo(vrvo(vr)))
