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

import algebra.ring.AdditiveSemigroup

import coulomb.ops.{Add, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions

transparent inline given ctx_add_1V1U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    equ: UR =:= UL,
    alg: AdditiveSemigroup[VL]
        ): Add[VL, UL, VR, UR] =
    new Add[VL, UL, VR, UR]:
        type VO = VL
        type UO = UL
        def apply(vl: VL, vr: VR): VL = alg.plus(vl, eqv(vr))

transparent inline given ctx_add_1V2U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    eqv: VR =:= VL,
    neu: NotGiven[UR =:= UL],
    ucv: UnitConversion[VL, UR, UL],
    alg: AdditiveSemigroup[VL]
        ): Add[VL, UL, VR, UR] =
    new Add[VL, UL, VR, UR]:
        type VO = VL
        type UO = UL
        def apply(vl: VL, vr: VR): VL = alg.plus(vl, ucv(eqv(vr)))

transparent inline given ctx_add_2V1U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    nev: NotGiven[VR =:= VL],
    equ: UR =:= UL,
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    alg: AdditiveSemigroup[vres.VO]
        ): Add[VL, UL, VR, UR] =
    new Add[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = UL
        def apply(vl: VL, vr: VR): VO = alg.plus(vlvo(vl), vrvo(vr))

transparent inline given ctx_add_2V2U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    nev: NotGiven[VR =:= VL],
    neu: NotGiven[UR =:= UL],
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    ucvo: UnitConversion[vres.VO, UR, UL],
    alg: AdditiveSemigroup[vres.VO]
        ): Add[VL, UL, VR, UR] =
    new Add[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = UL
        def apply(vl: VL, vr: VR): VO = alg.plus(vlvo(vl), ucvo(vrvo(vr)))

