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

import algebra.ring.TruncatedDivision

import coulomb.`/`
import coulomb.ops.{TQuot, SimplifiedUnit, ValueResolution}
import coulomb.conversion.{ValueConversion}
import coulomb.policy.AllowImplicitConversions
import coulomb.policy.AllowTruncation

transparent inline given ctx_tquot_1V2U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    alg: CanTQuot[VL],
    su: SimplifiedUnit[UL / UR]
        ): TQuot[VL, UL, VR, UR] =
    new TQuot[VL, UL, VR, UR]:
        type VO = VL
        type UO = su.UO
        def apply(vl: VL, vr: VR): VL = alg.tquot(vl, eqv(vr))

transparent inline given ctx_tquot_2V2U[VL, UL, VR, UR](using
    nev: NotGiven[VL =:= VR],
    ice: AllowImplicitConversions,
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    alg: CanTQuot[vres.VO],
    su: SimplifiedUnit[UL / UR]
        ): TQuot[VL, UL, VR, UR] =
    new TQuot[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = su.UO
        def apply(vl: VL, vr: VR): VO = alg.tquot(vlvo(vl), vrvo(vr))

// if algebra ever implements TruncatedDivision for Int and Long I can get rid of this
abstract class CanTQuot[V]:
    def tquot(vl: V, vr: V): V

object CanTQuot:
    inline given ctx_TruncatedDivision_CanTQuot[V](using td: TruncatedDivision[V]): CanTQuot[V] =
        new CanTQuot[V]:
            def tquot(vl: V, vr: V): V = td.tquot(vl, vr)

    inline given ctx_Integral_CanTQuot[V](using
        ntd: NotGiven[TruncatedDivision[V]],
        num: scala.math.Integral[V]): CanTQuot[V] =
        new CanTQuot[V]:
            def tquot(vl: V, vr: V): V = num.quot(vl, vr)

    inline given ctx_Fractional_CanTQuot[V](using
        ntd: NotGiven[TruncatedDivision[V]],
        num: scala.math.Fractional[V]): CanTQuot[V] =
        new CanTQuot[V]:
            def tquot(vl: V, vr: V): V = num.fromInt(num.toInt(num.div(vl, vr)))
