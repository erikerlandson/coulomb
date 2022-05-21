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

package coulomb.cats

import scala.util.NotGiven
import _root_.cats.kernel.{Eq, Hash, Order}
import coulomb.{DeltaQuantity, Quantity}

object quantity:
    given ctx_Quantity_Order[V, U](using ord: Order[V]): Order[Quantity[V, U]] =
        new infra.QOrder[V, U](ord)

    given ctx_Quantity_Hash[V, U](using h: Hash[V]): Hash[Quantity[V, U]] =
        new infra.QHash[V, U](h)

    given ctx_Quantity_Eq[V, U](using
        nohash: NotGiven[Hash[V]],
        e: Eq[V]
            ): Eq[Quantity[V, U]] =
        new infra.QEq[V, U](e)

    object infra:
        class QOrder[V, U](ord: Order[V]) extends Order[Quantity[V, U]]:
            def compare(x: Quantity[V, U], y: Quantity[V, U]) = ord.compare(x.value, y.value)

        class QEq[V, U](e: Eq[V]) extends Eq[Quantity[V, U]]:
            def eqv(x: Quantity[V, U], y: Quantity[V, U]) = e.eqv(x.value, y.value)

        class QHash[V, U](h: Hash[V]) extends QEq[V, U](h) with Hash[Quantity[V, U]]:
            def hash(x: Quantity[V, U]) = h.hash(x.value)
