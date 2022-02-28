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

import coulomb.{`*`, `/`, `^`}
import coulomb.ops.{Mul, SimplifiedUnit}
import coulomb.conversion.{ValueConversion, UnitConversion, ValueResolution}
import coulomb.policy.ImplicitConversionsEnabled

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

transparent inline given ctx_mul_Long_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
        Mul[Long, UL, Long, UR] =
    new Mul[Long, UL, Long, UR]:
        type VO = Long
        type UO = su.UO
        def apply(vl: Long, vr: Long): Long = vl * vr

transparent inline given ctx_mul_Int_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
        Mul[Int, UL, Int, UR] =
    new Mul[Int, UL, Int, UR]:
        type VO = Int
        type UO = su.UO
        def apply(vl: Int, vr: Int): Int = vl * vr

transparent inline given ctx_mul_1V2U[VL, UL, VR, UR](using
    // this is a workaround
    // https://github.com/lampepfl/dotty/issues/14585
    eqv: VR =:= VL,
    alg: Algebra[VL],
    su: SimplifiedUnit[UL * UR]
        ): Mul[VL, UL, VR, UR] =
    new Mul[VL, UL, VR, UR]:
        type VO = VL
        type UO = su.UO
        def apply(vl: VL, vr: VR): VL = alg.mul(vl, eqv(vr))

transparent inline given ctx_mul_2V2U[VL, UL, VR, UR](using
    nev: NotGiven[VL =:= VR],
    vres: ValueResolution[VL, VR],
    vlvo: ValueConversion[VL, vres.VO],
    vrvo: ValueConversion[VR, vres.VO],
    alg: Algebra[vres.VO],
    su: SimplifiedUnit[UL * UR]
        ): Mul[VL, UL, VR, UR] =
    new Mul[VL, UL, VR, UR]:
        type VO = vres.VO
        type UO = su.UO
        def apply(vl: VL, vr: VR): VO = alg.mul(vlvo(vl), vrvo(vr))

