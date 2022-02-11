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

import coulomb.ops.{Add,Sub,Mul,Div,Pow}

transparent inline given ctx1[VL, VR, U](using
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    num: scala.math.Numeric[vres.VO]
        ): Add[VL, U, VR, U] =
    new Add[VL, U, VR, U]:
        type VO = vres.VO
        type UO = U
        def apply(vl: VL, vr: VR): VO = num.plus(vlvo(vl), vrvo(vr))

transparent inline given ctx2[VL, UL, VR, UR](using
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    ucvo: UnitConversion[vres.VO, UR, UL],
    num: scala.math.Numeric[vres.VO]
        ): Add[VL, UL, VR, UR] =
    new Add[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = U
        def apply(vl: VL, vr: VR): VO = num.plus(vlvo(vl), num.times(ucvo, vrvo(vr)))

