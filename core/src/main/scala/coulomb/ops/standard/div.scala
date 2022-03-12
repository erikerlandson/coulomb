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

import algebra.ring.MultiplicativeGroup

import coulomb.`/`
import coulomb.ops.{Div, SimplifiedUnit, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions

transparent inline given ctx_div_1V2U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    alg: MultiplicativeGroup[VL],
    su: SimplifiedUnit[UL / UR]
        ): Div[VL, UL, VR, UR] =
    new Div[VL, UL, VR, UR]:
        type VO = VL
        type UO = su.UO
        def apply(vl: VL, vr: VR): VL = alg.div(vl, eqv(vr))

transparent inline given ctx_div_2V2U[VL, UL, VR, UR](using
    nev: NotGiven[VL =:= VR],
    ice: AllowImplicitConversions,
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    alg: MultiplicativeGroup[vres.VO],
    su: SimplifiedUnit[UL / UR]
        ): Div[VL, UL, VR, UR] =
    new Div[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = su.UO
        def apply(vl: VL, vr: VR): VO = alg.div(vlvo(vl), vrvo(vr))
