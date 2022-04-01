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
import algebra.ring.MultiplicativeGroup

import coulomb.{`^`, Quantity, withUnit}
import coulomb.ops.{Pow, SimplifiedUnit}
import coulomb.rational.typeexpr
import coulomb.ops.algebra.FractionalPower

object pow:
    transparent inline given ctx_pow_FractionalPower[V, U, E](using
        alg: FractionalPower[V],
        su: SimplifiedUnit[U ^ E]
            ): Pow[V, U, E] =
        new infra.PowFP[V, U, E, su.UO](alg, typeexpr.double[E])

    transparent inline given ctx_pow_MultiplicativeGroup[V, U, E](using
        nfp: NotGiven[FractionalPower[V]],
        alg: MultiplicativeGroup[V],
        aie: typeexpr.AllInt[E],
        su: SimplifiedUnit[U ^ E]
            ): Pow[V, U, E] =
        new infra.PowMG[V, U, E, su.UO](alg, aie.value)

    transparent inline given ctx_pow_MultiplicativeSemigroup[V, U, E](using
        nfp: NotGiven[FractionalPower[V]],
        nmg: NotGiven[MultiplicativeGroup[V]],
        alg: MultiplicativeSemigroup[V],
        pie: typeexpr.PosInt[E],
        su: SimplifiedUnit[U ^ E]
            ): Pow[V, U, E] =
        new infra.PowMSG[V, U, E, su.UO](alg, pie.value)

    object infra:
        class PowFP[V, U, E, UOp](alg: FractionalPower[V], e: Double) extends Pow[V, U, E]:
            type VO = V
            type UO = UOp
            def apply(q: Quantity[V, U]): Quantity[VO, UO] =
                alg.pow(q.value, e).withUnit[UO]

        class PowMG[V, U, E, UOp](alg: MultiplicativeGroup[V], e: Int) extends Pow[V, U, E]:
            type VO = V
            type UO = UOp
            def apply(q: Quantity[V, U]): Quantity[VO, UO] =
                alg.pow(q.value, e).withUnit[UO]

        class PowMSG[V, U, E, UOp](alg: MultiplicativeSemigroup[V], e: Int) extends Pow[V, U, E]:
            type VO = V
            type UO = UOp
            def apply(q: Quantity[V, U]): Quantity[VO, UO] =
                alg.pow(q.value, e).withUnit[UO]
