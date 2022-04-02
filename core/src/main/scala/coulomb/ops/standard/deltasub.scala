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

object deltasub:
    import scala.util.NotGiven
    import scala.Conversion

    import algebra.ring.AdditiveGroup

    import coulomb.*
    import coulomb.ops.{DeltaSub, ValueResolution}

    transparent inline given ctx_deltasub_1V1U[B, VL, UL, VR, UR](using
        eqv: VR =:= VL,
        equ: UR =:= UL,
        alg: AdditiveGroup[VL]
            ): DeltaSub[B, VL, UL, VR, UR] =
        new infra.DeltaSub1V1U[B, VL, UL, VR, UR](alg, eqv)

    transparent inline given ctx_deltasub_1V2U[B, VL, UL, VR, UR](using
        eqv: VR =:= VL,
        neu: NotGiven[UR =:= UL],
        icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[VL, UL, B]],
        alg: AdditiveGroup[VL]
            ): DeltaSub[B, VL, UL, VR, UR] =
        new infra.DeltaSub1V2U[B, VL, UL, VR, UR](alg, icr)

    transparent inline given ctx_deltasub_2V1U[B, VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        equ: UR =:= UL,
        vres: ValueResolution[VL, VR],
        icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[vres.VO, UL, B]],
        icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[vres.VO, UL, B]],
        alg: AdditiveGroup[vres.VO]
            ): DeltaSub[B, VL, UL, VR, UR] =
        new infra.DeltaSub2V2U[B, VL, UL, VR, UR, vres.VO](alg, icl, icr)

    transparent inline given ctx_deltasub_2V2U[B, VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        neu: NotGiven[UR =:= UL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[vres.VO, UL, B]],
        icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[vres.VO, UL, B]],
        alg: AdditiveGroup[vres.VO]
            ): DeltaSub[B, VL, UL, VR, UR] =
        new infra.DeltaSub2V2U[B, VL, UL, VR, UR, vres.VO](alg, icl, icr)

    object infra:
        class DeltaSub1V1U[B, VL, UL, VR, UR](
            alg: AdditiveGroup[VL],
            eqv: VR =:= VL) extends DeltaSub[B, VL, UL, VR, UR]:
            type VO = VL
            type UO = UL
            def apply(ql: DeltaQuantity[VL, UL, B], qr: DeltaQuantity[VR, UR, B]): Quantity[VO, UO] =
                alg.minus(ql.value, eqv(qr.value)).withUnit[UO]
 
        class DeltaSub1V2U[B, VL, UL, VR, UR](
            alg: AdditiveGroup[VL],
            icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[VL, UL, B]]) extends DeltaSub[B, VL, UL, VR, UR]:
            type VO = VL
            type UO = UL
            def apply(ql: DeltaQuantity[VL, UL, B], qr: DeltaQuantity[VR, UR, B]): Quantity[VO, UO] =
                alg.minus(ql.value, icr(qr).value).withUnit[UO]

        class DeltaSub2V2U[B, VL, UL, VR, UR, VOp](
            alg: AdditiveGroup[VOp],
            icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[VOp, UL, B]], 
            icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[VOp, UL, B]]) extends DeltaSub[B, VL, UL, VR, UR]:
            type VO = VOp
            type UO = UL
            def apply(ql: DeltaQuantity[VL, UL, B], qr: DeltaQuantity[VR, UR, B]): Quantity[VO, UO] =
                alg.minus(icl(ql).value, icr(qr).value).withUnit[UO]

