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
import algebra.ring.TruncatedDivision

import coulomb.`/`
import coulomb.ops.{Div, SimplifiedUnit, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions
import coulomb.policy.AllowTruncation

transparent inline given ctx_div_Double_2U[UL, UR](using su: SimplifiedUnit[UL / UR]):
        Div[Double, UL, Double, UR] =
    new Div[Double, UL, Double, UR]:
        type VO = Double
        type UO = su.UO
        def apply(vl: Double, vr: Double): Double = vl / vr

transparent inline given ctx_div_Float_2U[UL, UR](using su: SimplifiedUnit[UL / UR]):
        Div[Float, UL, Float, UR] =
    new Div[Float, UL, Float, UR]:
        type VO = Float
        type UO = su.UO
        def apply(vl: Float, vr: Float): Float = vl / vr

transparent inline given ctx_div_1V2U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    alg: CanDiv[VL],
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
    alg: CanDiv[vres.VO],
    su: SimplifiedUnit[UL / UR]
        ): Div[VL, UL, VR, UR] =
    new Div[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = su.UO
        def apply(vl: VL, vr: VR): VO = alg.div(vlvo(vl), vrvo(vr))

abstract class CanDiv[V]:
    def div(vl: V, vr: V): V

object CanDiv:
    inline given ctx_CanDiv_MultiplicativeGroup[V](using alg: MultiplicativeGroup[V]): CanDiv[V] =
        new CanDiv[V]:
            def div(vl: V, vr: V): V = alg.div(vl, vr)
    
    inline given ctx_CanDiv_TruncatedDivision[V](using
        nmg: NotGiven[MultiplicativeGroup[V]],
        at: AllowTruncation,
        alg: TruncatedDivision[V]): CanDiv[V] =
        new CanDiv[V]:
            def div(vl: V, vr: V): V = alg.tquot(vl, vr)
