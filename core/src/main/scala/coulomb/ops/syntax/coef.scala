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

package coulomb.ops.syntax.coef

import _root_.algebra.ring.*

import coulomb.*
import coulomb.syntax.*
import coulomb.conversion.ValueConversion

object all:
    export int.*
    export double.*

object int:
    extension(v: Int)
        inline def *[V, U](q: Quantity[V, U])(using
            vc: ValueConversion[Int, V],
            alg: MultiplicativeSemigroup[V]
        ): Quantity[V, U] =
            alg.times(vc(v), q.value).withUnit[U]

        transparent inline def /[V, U](q: Quantity[V, U])(using
            vc: ValueConversion[Int, V],
            alg: MultiplicativeGroup[V],
            su: SimplifiedUnit[1 / U]
        ): Quantity[V, su.UO] =
            alg.div(vc(v), q.value).withUnit[su.UO]

    extension[V, U](q: Quantity[V, U])
        inline def *(v: Int)(using
            vc: ValueConversion[Int, V],
            alg: MultiplicativeSemigroup[V]
        ): Quantity[V, U] =
            alg.times(q.value, vc(v)).withUnit[U]
 
        inline def /(v: Int)(using
            vc: ValueConversion[Int, V],
            alg: MultiplicativeGroup[V]
        ): Quantity[V, U] =
            alg.div(q.value, vc(v)).withUnit[U]

object double:
    extension(v: Double)
        inline def *[V, U](q: Quantity[V, U])(using
            vc: ValueConversion[Double, V],
            alg: MultiplicativeSemigroup[V]
        ): Quantity[V, U] =
            alg.times(vc(v), q.value).withUnit[U]

    extension[V, U](q: Quantity[V, U])
        inline def *(v: Double)(using
            vc: ValueConversion[Double, V],
            alg: MultiplicativeSemigroup[V]
        ): Quantity[V, U] =
            alg.times(q.value, vc(v)).withUnit[U]
