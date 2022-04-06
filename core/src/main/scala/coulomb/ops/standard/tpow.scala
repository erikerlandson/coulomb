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

object tpow:
    import scala.util.NotGiven

    import coulomb.{`^`, Quantity, withUnit}
    import coulomb.ops.{TPow, SimplifiedUnit}
    import coulomb.ops.algebra.TruncatingPower
    import coulomb.rational.typeexpr

    transparent inline given ctx_quantity_tpow[V, U, E](using
        alg: TruncatingPower[V],
        su: SimplifiedUnit[U ^ E]
            ): TPow[V, U, E] =
        val e = typeexpr.double[E]
        new infra.TPowNC[V, U, E, V, su.UO]((q: Quantity[V, U]) => alg.tpow(q.value, e).withUnit[su.UO])

    object infra:
        class TPowNC[V, U, E, VOp, UOp](val eval: Quantity[V, U] => Quantity[VOp, UOp]) extends TPow[V, U, E]:
            type VO = VOp
            type UO = UOp
