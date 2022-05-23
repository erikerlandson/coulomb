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

object pow:
    import scala.util.NotGiven

    import algebra.ring.MultiplicativeSemigroup
    import algebra.ring.MultiplicativeGroup

    import coulomb.{`^`, Quantity}
    import coulomb.syntax.withUnit
    import coulomb.ops.{Pow, SimplifiedUnit}
    import coulomb.rational.typeexpr
    import coulomb.ops.algebra.FractionalPower

    transparent inline given ctx_pow_FractionalPower[V, U, E](using
        alg: FractionalPower[V],
        su: SimplifiedUnit[U ^ E]
            ): Pow[V, U, E] =
        val e = typeexpr.double[E]
        new infra.PowNC[V, U, E, V, su.UO]((q: Quantity[V, U]) => alg.pow(q.value, e).withUnit[su.UO])

    transparent inline given ctx_pow_MultiplicativeGroup[V, U, E](using
        nfp: NotGiven[FractionalPower[V]],
        alg: MultiplicativeGroup[V],
        aie: typeexpr.AllInt[E],
        su: SimplifiedUnit[U ^ E]
            ): Pow[V, U, E] =
        val e = aie.value
        new infra.PowNC[V, U, E, V, su.UO]((q: Quantity[V, U]) => alg.pow(q.value, e).withUnit[su.UO])

    transparent inline given ctx_pow_MultiplicativeSemigroup[V, U, E](using
        nfp: NotGiven[FractionalPower[V]],
        nmg: NotGiven[MultiplicativeGroup[V]],
        alg: MultiplicativeSemigroup[V],
        pie: typeexpr.PosInt[E],
        su: SimplifiedUnit[U ^ E]
            ): Pow[V, U, E] =
        val e = pie.value
        new infra.PowNC[V, U, E, V, su.UO]((q: Quantity[V, U]) => alg.pow(q.value, e).withUnit[su.UO])

    object infra:
        class PowNC[V, U, E, VOp, UOp](val eval: Quantity[V, U] => Quantity[VOp, UOp]) extends Pow[V, U, E]:
            type VO = VOp
            type UO = UOp
