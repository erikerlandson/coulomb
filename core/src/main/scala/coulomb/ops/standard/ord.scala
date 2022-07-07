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

object ord:
    import scala.util.NotGiven
    import scala.Conversion

    import cats.kernel.Order

    import coulomb.Quantity
    import coulomb.ops.{Ord, ValueResolution}

    given ctx_ord_1V1U[VL, UL, VR, UR](using
        // https://github.com/lampepfl/dotty/issues/14585
        eqv: VR =:= VL,
        equ: UR =:= UL,
        ord: Order[VL]
            ): Ord[VL, UL, VR, UR] =
        (ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => ord.compare(ql.value, eqv(qr.value))

    given ctx_ord_1V2U[VL, UL, VR, UR](using
        eqv: VR =:= VL,
        neu: NotGiven[UR =:= UL],
        icr: Conversion[Quantity[VR, UR], Quantity[VL, UL]],
        ord: Order[VL]
            ): Ord[VL, UL, VR, UR] =
        (ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => ord.compare(ql.value, icr(qr).value)

    given ctx_ord_2V1U[VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        equ: UR =:= UL,
        vres: ValueResolution[VL, VR],
        icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
        ord: Order[vres.VO]
            ): Ord[VL, UL, VR, UR] =
        (ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => ord.compare(icl(ql).value, icr(qr).value)

    given ctx_ord_2V2U[VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        neu: NotGiven[UR =:= UL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
        ord: Order[vres.VO]
            ): Ord[VL, UL, VR, UR] =
        (ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => ord.compare(icl(ql).value, icr(qr).value)
