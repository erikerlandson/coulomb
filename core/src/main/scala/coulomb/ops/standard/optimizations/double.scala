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

object double:
    inline given ctx_quantity_neg_Double[U]: Neg[Double, U] =
        new Neg[Double, U]:
            def apply(v: Double): Double = -v

    transparent inline given ctx_add_Double_1U[U]: Add[Double, U, Double, U] =
        new Add[Double, U, Double, U]:
            type VO = Double
            type UO = U
            def apply(vl: Double, vr: Double): Double = vl + vr

    transparent inline given ctx_add_Double_2U[UL, UR](using
        AllowImplicitConversions,
        NotGiven[UL =:= UR]
            ): Add[Double, UL, Double, UR] =
        val c = coulomb.conversion.infra.coefficientDouble[UR, UL]
        new Add[Double, UL, Double, UR]:
            type VO = Double
            type UO = UL
            def apply(vl: Double, vr: Double): Double = vl + (c * vr)

    transparent inline given ctx_sub_Double_1U[U]: Sub[Double, U, Double, U] =
        new Sub[Double, U, Double, U]:
            type VO = Double
            type UO = U
            def apply(vl: Double, vr: Double): Double = vl - vr

    transparent inline given ctx_sub_Double_2U[UL, UR](using
        AllowImplicitConversions,
        NotGiven[UL =:= UR]
            ): Sub[Double, UL, Double, UR] =
        val c = coulomb.conversion.infra.coefficientDouble[UR, UL]
        new Sub[Double, UL, Double, UR]:
            type VO = Double
            type UO = UL
            def apply(vl: Double, vr: Double): Double = vl - (c * vr)

    transparent inline given ctx_mul_Double_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
            Mul[Double, UL, Double, UR] =
        new Mul[Double, UL, Double, UR]:
            type VO = Double
            type UO = su.UO
            def apply(vl: Double, vr: Double): Double = vl * vr

    transparent inline given ctx_div_Double_2U[UL, UR](using su: SimplifiedUnit[UL / UR]):
            Div[Double, UL, Double, UR] =
        new Div[Double, UL, Double, UR]:
            type VO = Double
            type UO = su.UO
            def apply(vl: Double, vr: Double): Double = vl / vr

