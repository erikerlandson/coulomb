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

object mul:
    import scala.util.NotGiven
    import scala.Conversion

    import algebra.ring.MultiplicativeSemigroup

    import coulomb.{`*`, Quantity, withUnit}
    import coulomb.ops.{Mul, SimplifiedUnit, ValueResolution}

    transparent inline given ctx_mul_1V2U[VL, UL, VR, UR](using
        // https://github.com/lampepfl/dotty/issues/14585
        eqv: VR =:= VL,
        alg: MultiplicativeSemigroup[VL],
        su: SimplifiedUnit[UL * UR]
            ): Mul[VL, UL, VR, UR] =
        new infra.MulNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => alg.times(ql.value, eqv(qr.value)).withUnit[su.UO])

    transparent inline given ctx_mul_2V2U[VL, UL, VR, UR](using
        nev: NotGiven[VR =:= VL],
        vres: ValueResolution[VL, VR],
        icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
        icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UR]],
        alg: MultiplicativeSemigroup[vres.VO],
        su: SimplifiedUnit[UL * UR]
            ): Mul[VL, UL, VR, UR] =
        new infra.MulNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => alg.times(icl(ql).value, icr(qr).value).withUnit[su.UO])

    object infra:
        class MulNC[VL, UL, VR, UR, VOp, UOp](val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VOp, UOp]) extends Mul[VL, UL, VR, UR]:
            type VO = VOp
            type UO = UOp
