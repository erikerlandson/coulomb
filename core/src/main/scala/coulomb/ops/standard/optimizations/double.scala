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
import coulomb.Quantity
import coulomb.syntax.withUnit
import coulomb.ops.*
import coulomb.ops.standard.named.*
import coulomb.conversion.coefficients.*

object double:
    given ctx_quantity_neg_Double[U]: Neg[Double, U] =
        (q: Quantity[Double, U]) => (-(q.value)).withUnit[U]

    transparent inline given ctx_add_Double_1U[U]: Add[Double, U, Double, U] =
        new AddNC((ql: Quantity[Double, U], qr: Quantity[Double, U]) =>
            (ql.value + qr.value).withUnit[U]
        )

    transparent inline given ctx_add_Double_2U[UL, UR](using
        Conversion[Quantity[Double, UR], Quantity[Double, UL]]
    ): Add[Double, UL, Double, UR] =
        val c = coefficientDouble[UR, UL]
        new AddNC((ql: Quantity[Double, UL], qr: Quantity[Double, UR]) =>
            (ql.value + (c * qr.value)).withUnit[UL]
        )

    transparent inline given ctx_sub_Double_1U[U]: Sub[Double, U, Double, U] =
        new SubNC((ql: Quantity[Double, U], qr: Quantity[Double, U]) =>
            (ql.value - qr.value).withUnit[U]
        )

    transparent inline given ctx_sub_Double_2U[UL, UR](using
        Conversion[Quantity[Double, UR], Quantity[Double, UL]]
    ): Sub[Double, UL, Double, UR] =
        val c = coefficientDouble[UR, UL]
        new SubNC((ql: Quantity[Double, UL], qr: Quantity[Double, UR]) =>
            (ql.value - (c * qr.value)).withUnit[UL]
        )

    transparent inline given ctx_mul_Double_2U[UL, UR](using
        su: SimplifiedUnit[UL * UR]
    ): Mul[Double, UL, Double, UR] =
        new MulNC((ql: Quantity[Double, UL], qr: Quantity[Double, UR]) =>
            (ql.value * qr.value).withUnit[su.UO]
        )

    transparent inline given ctx_div_Double_2U[UL, UR](using
        su: SimplifiedUnit[UL / UR]
    ): Div[Double, UL, Double, UR] =
        new DivNC((ql: Quantity[Double, UL], qr: Quantity[Double, UR]) =>
            (ql.value / qr.value).withUnit[su.UO]
        )
