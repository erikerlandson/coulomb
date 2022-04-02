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

object deltaord:
    import scala.util.NotGiven
    import scala.Conversion

    import cats.kernel.Order

    import coulomb.*
    import coulomb.ops.{DeltaOrd, ValueResolution}

    transparent inline given ctx_deltaord_1V1U[B, VL, UL, VR, UR](using
        eqv: VR =:= VL,
        equ: UR =:= UL,
        ord: Order[VL]
            ): DeltaOrd[B, VL, UL, VR, UR] =
        new infra.DeltaOrd1V1U[B, VL, UL, VR, UR](ord, eqv)

    transparent inline given ctx_deltaord_1V2U[B, VL, UL, VR, UR](using
        eqv: VR =:= VL,
        neu: NotGiven[UR =:= UL],
        icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[VL, UL, B]],
        ord: Order[VL]
            ): DeltaOrd[B, VL, UL, VR, UR] =
        new infra.DeltaOrd1V2U[B, VL, UL, VR, UR](ord, icr)

    transparent inline given ctx_deltaord_2V1U[B, VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        equ: UR =:= UL,
        vres: ValueResolution[VL, VR],
        icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[vres.VO, UL, B]],
        icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[vres.VO, UL, B]],
        ord: Order[vres.VO]
            ): DeltaOrd[B, VL, UL, VR, UR] =
        new infra.DeltaOrd2V2U[B, VL, UL, VR, UR, vres.VO](ord, icl, icr)

    transparent inline given ctx_deltaord_2V2U[B, VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        neu: NotGiven[UR =:= UL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[vres.VO, UL, B]],
        icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[vres.VO, UL, B]],
        ord: Order[vres.VO]
            ): DeltaOrd[B, VL, UL, VR, UR] =
        new infra.DeltaOrd2V2U[B, VL, UL, VR, UR, vres.VO](ord, icl, icr)

    object infra:
        class DeltaOrd1V1U[B, VL, UL, VR, UR](
            ord: Order[VL],
            eqv: VR =:= VL) extends DeltaOrd[B, VL, UL, VR, UR]:
            def apply(ql: DeltaQuantity[VL, UL, B], qr: DeltaQuantity[VR, UR, B]): Int =
                ord.compare(ql.value, eqv(qr.value))
 
        class DeltaOrd1V2U[B, VL, UL, VR, UR](
            ord: Order[VL],
            icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[VL, UL, B]]) extends DeltaOrd[B, VL, UL, VR, UR]:
            def apply(ql: DeltaQuantity[VL, UL, B], qr: DeltaQuantity[VR, UR, B]): Int =
                ord.compare(ql.value, icr(qr).value)

        class DeltaOrd2V2U[B, VL, UL, VR, UR, VOp](
            ord: Order[VOp],
            icl: Conversion[DeltaQuantity[VL, UL, B], DeltaQuantity[VOp, UL, B]], 
            icr: Conversion[DeltaQuantity[VR, UR, B], DeltaQuantity[VOp, UL, B]]) extends DeltaOrd[B, VL, UL, VR, UR]:
            def apply(ql: DeltaQuantity[VL, UL, B], qr: DeltaQuantity[VR, UR, B]): Int =
                ord.compare(icl(ql).value, icr(qr).value)

