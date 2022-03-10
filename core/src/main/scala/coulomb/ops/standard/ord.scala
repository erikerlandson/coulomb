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

import cats.kernel.Order

import coulomb.ops.{Ord, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions

transparent inline given ctx_ord_1V1U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    equ: UR =:= UL,
    ord: Order[VL]
        ): Ord[VL, UL, VR, UR] =
    new Ord[VL, UL, VR, UR]:
        def apply(vl: VL, vr: VR): Int = ord.compare(vl, eqv(vr))

transparent inline given ctx_ord_1V2U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    eqv: VR =:= VL,
    neu: NotGiven[UR =:= UL],
    ucv: UnitConversion[VL, UR, UL],
    ord: Order[VL]
        ): Ord[VL, UL, VR, UR] =
    new Ord[VL, UL, VR, UR]:
        def apply(vl: VL, vr: VR): Int = ord.compare(vl, ucv(eqv(vr)))

transparent inline given ctx_ord_2V1U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    nev: NotGiven[VR =:= VL],
    equ: UR =:= UL,
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    ord: Order[vres.VO]
        ): Ord[VL, UL, VR, UR] =
    new Ord[VL, UL, VR, UR]:
        def apply(vl: VL, vr: VR): Int = ord.compare(vlvo(vl), vrvo(vr))

transparent inline given ctx_ord_2V2U[VL, UL, VR, UR](using
    ice: AllowImplicitConversions,
    nev: NotGiven[VR =:= VL],
    neu: NotGiven[UR =:= UL],
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    ucvo: UnitConversion[vres.VO, UR, UL],
    ord: Order[vres.VO]
        ): Ord[VL, UL, VR, UR] =
    new Ord[VL, UL, VR, UR]:
        def apply(vl: VL, vr: VR): Int = ord.compare(vlvo(vl), ucvo(vrvo(vr)))

