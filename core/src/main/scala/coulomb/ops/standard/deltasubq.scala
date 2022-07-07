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

object deltasubq:
    import scala.util.NotGiven
    import scala.Conversion

    import algebra.ring.AdditiveGroup

    import coulomb.*
    import coulomb.syntax.*
    import coulomb.ops.{DeltaSubQ, ValueResolution}

    transparent inline given ctx_deltasubq_1V1U[B, VL, UL, VR, UR](using
        eqv: VR =:= VL,
        equ: UR =:= UL,
        alg: AdditiveGroup[VL]
            ): DeltaSubQ[B, VL, UL, VR, UR] =
        new infra.DeltaSubQNC((ql: DeltaQuantity[VL, UL, B], qr: Quantity[VR, UR]) => alg.minus(ql.value, eqv(qr.value)).withDeltaUnit[UL, B])

    transparent inline given ctx_deltasubq_1V2U[B, VL, UL, VR, UR](using
        eqv: VR =:= VL,
        neu: NotGiven[UR =:= UL],
        icr: Conversion[Quantity[VR, UR], Quantity[VL, UL]],
        alg: AdditiveGroup[VL]
            ): DeltaSubQ[B, VL, UL, VR, UR] =
        new infra.DeltaSubQNC((ql: DeltaQuantity[VL, UL, B], qr: Quantity[VR, UR]) => alg.minus(ql.value, icr(qr).value).withDeltaUnit[UL, B])

    transparent inline given ctx_deltasubq_2V1U[B, VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        equ: UR =:= UL,
        vres: ValueResolution[VL, VR],
        icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[vres.VO, UL, B]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
        alg: AdditiveGroup[vres.VO]
            ): DeltaSubQ[B, VL, UL, VR, UR] =
        new infra.DeltaSubQNC((ql: DeltaQuantity[VL, UL, B], qr: Quantity[VR, UR]) => alg.minus(icl(ql).value, icr(qr).value).withDeltaUnit[UL, B])

    transparent inline given ctx_deltasubq_2V2U[B, VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        neu: NotGiven[UR =:= UL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[vres.VO, UL, B]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
        alg: AdditiveGroup[vres.VO]
            ): DeltaSubQ[B, VL, UL, VR, UR] =
        new infra.DeltaSubQNC((ql: DeltaQuantity[VL, UL, B], qr: Quantity[VR, UR]) => alg.minus(icl(ql).value, icr(qr).value).withDeltaUnit[UL, B])

    object infra:
        class DeltaSubQNC[B, VL, UL, VR, UR, VOp, UOp](val eval: (DeltaQuantity[VL, UL, B], Quantity[VR, UR]) => DeltaQuantity[VOp, UOp, B]) extends DeltaSubQ[B, VL, UL, VR, UR]:
            type VO = VOp
            type UO = UOp
