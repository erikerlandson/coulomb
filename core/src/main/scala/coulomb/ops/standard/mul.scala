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

import algebra.ring.MultiplicativeSemigroup

import coulomb.`*`
import coulomb.ops.{Mul, SimplifiedUnit, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions

transparent inline given ctx_mul_Double_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
        Mul[Double, UL, Double, UR] =
    new Mul[Double, UL, Double, UR]:
        type VO = Double
        type UO = su.UO
        def apply(vl: Double, vr: Double): Double = vl * vr

transparent inline given ctx_mul_Float_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
        Mul[Float, UL, Float, UR] =
    new Mul[Float, UL, Float, UR]:
        type VO = Float
        type UO = su.UO
        def apply(vl: Float, vr: Float): Float = vl * vr

transparent inline given ctx_mul_1V2U[VL, UL, VR, UR](using
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    alg: MultiplicativeSemigroup[VL],
    su: SimplifiedUnit[UL * UR]
        ): Mul[VL, UL, VR, UR] =
    new Mul[VL, UL, VR, UR]:
        type VO = VL
        type UO = su.UO
        def apply(vl: VL, vr: VR): VL = alg.times(vl, eqv(vr))

transparent inline given ctx_mul_2V2U[VL, UL, VR, UR](using
    nev: NotGiven[VL =:= VR],
    ice: AllowImplicitConversions,
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    alg: MultiplicativeSemigroup[vres.VO],
    su: SimplifiedUnit[UL * UR]
        ): Mul[VL, UL, VR, UR] =
    new Mul[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = su.UO
        def apply(vl: VL, vr: VR): VO = alg.times(vlvo(vl), vrvo(vr))

