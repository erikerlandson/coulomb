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

object sub:
    import scala.util.NotGiven
    import scala.Conversion

    import algebra.ring.AdditiveGroup

    import coulomb.Quantity
    import coulomb.syntax.withUnit
    import coulomb.ops.{Sub, ValueResolution}

    transparent inline given ctx_sub_1V1U[VL, UL, VR, UR](using
        // https://github.com/lampepfl/dotty/issues/14585
        eqv: VR =:= VL,
        equ: UR =:= UL,
        alg: AdditiveGroup[VL]
    ): Sub[VL, UL, VR, UR] =
        new infra.SubNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) =>
            alg.minus(ql.value, eqv(qr.value)).withUnit[UL]
        )

    transparent inline given ctx_sub_1V2U[VL, UL, VR, UR](using
        eqv: VR =:= VL,
        neu: NotGiven[UR =:= UL],
        icr: Conversion[Quantity[VR, UR], Quantity[VL, UL]],
        alg: AdditiveGroup[VL]
    ): Sub[VL, UL, VR, UR] =
        new infra.SubNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) =>
            alg.minus(ql.value, icr(qr).value).withUnit[UL]
        )

    transparent inline given ctx_sub_2V1U[VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        equ: UR =:= UL,
        vres: ValueResolution[VL, VR],
        icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
        alg: AdditiveGroup[vres.VO]
    ): Sub[VL, UL, VR, UR] =
        new infra.SubNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) =>
            alg.minus(icl(ql).value, icr(qr).value).withUnit[UL]
        )

    transparent inline given ctx_sub_2V2U[VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        neu: NotGiven[UR =:= UL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
        alg: AdditiveGroup[vres.VO]
    ): Sub[VL, UL, VR, UR] =
        new infra.SubNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) =>
            alg.minus(icl(ql).value, icr(qr).value).withUnit[UL]
        )

    object infra:
        class SubNC[VL, UL, VR, UR, VOp, UOp](
            val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VOp, UOp]
        ) extends Sub[VL, UL, VR, UR]:
            type VO = VOp
            type UO = UOp
