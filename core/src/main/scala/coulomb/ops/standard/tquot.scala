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

object tquot:
    import scala.util.NotGiven
    import scala.Conversion

    import algebra.ring.TruncatedDivision

    import coulomb.{`/`, Quantity, withUnit}
    import coulomb.ops.{TQuot, SimplifiedUnit, ValueResolution}

    transparent inline given ctx_tquot_1V2U[VL, UL, VR, UR](using
        // https://github.com/lampepfl/dotty/issues/14585
        eqv: VR =:= VL,
        alg: TruncatedDivision[VL],
        su: SimplifiedUnit[UL / UR]
            ): TQuot[VL, UL, VR, UR] =
        new infra.TQuot1V2U[VL, UL, VR, UR, su.UO](alg, eqv)

    transparent inline given ctx_tquot_2V2U[VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UR]],
        alg: TruncatedDivision[vres.VO],
        su: SimplifiedUnit[UL / UR]
            ): TQuot[VL, UL, VR, UR] =
        new infra.TQuot2V2U[VL, UL, VR, UR, vres.VO, su.UO](alg, icl, icr)

    object infra:
        class TQuot1V2U[VL, UL, VR, UR, UOp](
            alg: TruncatedDivision[VL],
            eqv: VR =:= VL) extends TQuot[VL, UL, VR, UR]:
            type VO = VL
            type UO = UOp 
            def apply(ql: Quantity[VL, UL], qr: Quantity[VR, UR]): Quantity[VO, UO] =
                alg.tquot(ql.value, eqv(qr.value)).withUnit[UO]

        class TQuot2V2U[VL, UL, VR, UR, VOp, UOp](
            alg: TruncatedDivision[VOp],
            icl: Conversion[Quantity[VL, UL], Quantity[VOp, UL]], 
            icr: Conversion[Quantity[VR, UR], Quantity[VOp, UR]]) extends TQuot[VL, UL, VR, UR]:
            type VO = VOp
            type UO = UOp 
            def apply(ql: Quantity[VL, UL], qr: Quantity[VR, UR]): Quantity[VO, UO] =
                alg.tquot(icl(ql).value, icr(qr).value).withUnit[UO]
