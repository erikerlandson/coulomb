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

package coulomb.syntax

import _root_.algebra.ring.*
import spire.math.*

import coulomb.*
import coulomb.infra.SimplifiedUnit

export coulomb.Quantity.withUnit
export coulomb.DeltaQuantity.withDeltaUnit

// there are three tricks I applied to get scalar factors to work.
// 1. I use the signature:
//     extension[V, U, Q[V, U] <: Quantity[V, U]](q: Q[V, U])
// for the Quantity methods, which helps the type system to
// distinguish from the left-factor overloadings defined in this file.
// 2. I define the right-factor overloadings in the Quantity extension,
// because defining them separately here is confusing the compiler
// 3. I curry `using alg: ...` first below, which allows the compiler to
// pick the correct typeclass.

extension [V](v: V)
    /**
     * Multiply a Quantity on the left by a unitless scalar value
     * @tparam U
     *   the unit type of the Quantity
     * @param q
     *   The quantity being multiplied
     * @return
     *   the product of this value with `q`
     * @example
     *   {{{
     * val q1 = 2.0.withUnit[Meter]
     * 3.0 * q1 // => Quantity[Meter](6.0)
     *   }}}
     */
    inline def *[U](using
        alg: MultiplicativeSemigroup[V]
    )(q: Quantity[V, U]): Quantity[V, U] =
        alg.times(v, q.value).withUnit[U]

    /**
     * Divide a unitless scalar value by a Quantity
     * @tparam U
     *   the unit type of the Quantity
     * @param q
     *   The quantity being divided by
     * @return
     *   the quotient of this value with `q`
     * @example
     *   {{{
     * val q1 = 2.0.withUnit[Meter]
     * 3.0 / q1 // => Quantity[1/Meter](1.5)
     *   }}}
     */
    inline def /[U](using
        alg: MultiplicativeGroup[V]
    )(q: Quantity[V, U])(using
        su: SimplifiedUnit[1 / U]
    ): Quantity[V, su.UO] =
        alg.div(v, q.value).withUnit[su.UO]
