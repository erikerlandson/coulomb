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

package coulomb.ops.algebra.cats

object deltaquantity:
    import scala.util.NotGiven
    import _root_.cats.kernel.{Eq, Hash, Order}
    import coulomb.DeltaQuantity

    given ctx_DeltaQuantity_Order[V, U, B](using
        ord: Order[V]
    ): Order[DeltaQuantity[V, U, B]] =
        new infra.QOrder[V, U, B](ord)

    given ctx_DeltaQuantity_Hash[V, U, B](using
        noo: NotGiven[Order[V]],
        h: Hash[V]
    ): Hash[DeltaQuantity[V, U, B]] =
        new infra.QHash[V, U, B](h)

    given ctx_DeltaQuantity_Eq[V, U, B](using
        noo: NotGiven[Order[V]],
        noh: NotGiven[Hash[V]],
        e: Eq[V]
    ): Eq[DeltaQuantity[V, U, B]] =
        new infra.QEq[V, U, B](e)

    object infra:
        class QOrder[V, U, B](ord: Order[V])
            extends Order[DeltaQuantity[V, U, B]]:
            def compare(x: DeltaQuantity[V, U, B], y: DeltaQuantity[V, U, B]) =
                ord.compare(x.value, y.value)

        class QEq[V, U, B](e: Eq[V]) extends Eq[DeltaQuantity[V, U, B]]:
            def eqv(x: DeltaQuantity[V, U, B], y: DeltaQuantity[V, U, B]) =
                e.eqv(x.value, y.value)

        class QHash[V, U, B](h: Hash[V])
            extends QEq[V, U, B](h)
            with Hash[DeltaQuantity[V, U, B]]:
            def hash(x: DeltaQuantity[V, U, B]) = h.hash(x.value)
