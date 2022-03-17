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
import scala.Conversion

import coulomb.{`*`, `/`, `^`}
import coulomb.{Quantity, withUnit}
import coulomb.ops.*

import coulomb.conversion.coefficients.*

object double:
    inline given ctx_quantity_neg_Double[U]: Neg[Double, U] =
        new Neg[Double, U]:
            def apply(q: Quantity[Double, U]): Quantity[Double, U] = (-(q.value)).withUnit[U]

    transparent inline given ctx_add_Double_1U[U]: Add[Double, U, Double, U] =
        new Add[Double, U, Double, U]:
            type VO = Double
            type UO = U
            def apply(ql: Quantity[Double, U], qr: Quantity[Double, U]): Quantity[Double, U] =
                (ql.value + qr.value).withUnit[U]

    transparent inline given ctx_add_Double_2U[UL, UR](using
        Conversion[Quantity[Double, UR], Quantity[Double, UL]]
            ): Add[Double, UL, Double, UR] =
        val c = coefficientDouble[UR, UL]
        new Add[Double, UL, Double, UR]:
            type VO = Double
            type UO = UL
            def apply(ql: Quantity[Double, UL], qr: Quantity[Double, UR]): Quantity[Double, UL] =
                (ql.value + (c * qr.value)).withUnit[UL]

    transparent inline given ctx_sub_Double_1U[U]: Sub[Double, U, Double, U] =
        new Sub[Double, U, Double, U]:
            type VO = Double
            type UO = U
            def apply(ql: Quantity[Double, U], qr: Quantity[Double, U]): Quantity[Double, U] =
                (ql.value - qr.value).withUnit[U]

    transparent inline given ctx_sub_Double_2U[UL, UR](using
        Conversion[Quantity[Double, UR], Quantity[Double, UL]]
            ): Sub[Double, UL, Double, UR] =
        val c = coefficientDouble[UR, UL]
        new Sub[Double, UL, Double, UR]:
            type VO = Double
            type UO = UL
            def apply(ql: Quantity[Double, UL], qr: Quantity[Double, UR]): Quantity[Double, UL] =
                (ql.value - (c * qr.value)).withUnit[UL]

    transparent inline given ctx_mul_Double_2U[UL, UR](using su: SimplifiedUnit[UL * UR]):
            Mul[Double, UL, Double, UR] =
        new Mul[Double, UL, Double, UR]:
            type VO = Double
            type UO = su.UO
            def apply(ql: Quantity[Double, UL], qr: Quantity[Double, UR]): Quantity[Double, UO] =
                (ql.value * qr.value).withUnit[UO]

    transparent inline given ctx_div_Double_2U[UL, UR](using su: SimplifiedUnit[UL / UR]):
            Div[Double, UL, Double, UR] =
        new Div[Double, UL, Double, UR]:
            type VO = Double
            type UO = su.UO
            def apply(ql: Quantity[Double, UL], qr: Quantity[Double, UR]): Quantity[Double, UO] =
                (ql.value / qr.value).withUnit[UO]
