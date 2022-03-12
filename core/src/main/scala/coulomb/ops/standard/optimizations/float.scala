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

package coulomb.ops.standard.optimizations

import scala.util.NotGiven

import coulomb.{`*`, `/`, `^`}
import coulomb.ops.*
import coulomb.policy.AllowImplicitConversions

import coulomb.conversion.coefficients.*

object float:
    inline given ctx_quantity_neg_Float[U]: Neg[Float, U] =
        new Neg[Float, U]:
            def apply(v: Float): Float = -v

    transparent inline given ctx_add_Float_1U[U]: Add[Float, U, Float, U] =
        new Add[Float, U, Float, U]:
            type VO = Float
            type UO = U
            def apply(vl: Float, vr: Float): Float = vl + vr

    transparent inline given ctx_add_Float_2U[UL, UR](using
        AllowImplicitConversions,
        NotGiven[UL =:= UR]
            ): Add[Float, UL, Float, UR] =
        val c = coefficientFloat[UR, UL]
        new Add[Float, UL, Float, UR]:
            type VO = Float
            type UO = UL
            def apply(vl: Float, vr: Float): Float = vl + (c * vr)

    transparent inline given ctx_sub_Float_1U[U]: Sub[Float, U, Float, U] =
        new Sub[Float, U, Float, U]:
            type VO = Float
            type UO = U
            def apply(vl: Float, vr: Float): Float = vl - vr

    transparent inline given ctx_sub_Float_2U[UL, UR](using
        AllowImplicitConversions,
        NotGiven[UL =:= UR]
            ): Sub[Float, UL, Float, UR] =
        val c = coefficientFloat[UR, UL]
        new Sub[Float, UL, Float, UR]:
            type VO = Float
            type UO = UL
            def apply(vl: Float, vr: Float): Float = vl - (c * vr)

    transparent inline given ctx_mul_Float_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
            Mul[Float, UL, Float, UR] =
        new Mul[Float, UL, Float, UR]:
            type VO = Float
            type UO = su.UO
            def apply(vl: Float, vr: Float): Float = vl * vr

    transparent inline given ctx_div_Float_2U[UL, UR](using su: SimplifiedUnit[UL / UR]):
            Div[Float, UL, Float, UR] =
        new Div[Float, UL, Float, UR]:
            type VO = Float
            type UO = su.UO
            def apply(vl: Float, vr: Float): Float = vl / vr

