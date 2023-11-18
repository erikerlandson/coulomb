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

package coulomb.integrations.cats

/** Provides cats typeclasses for Quantity */
object quantity:
    import _root_.cats.kernel.{Eq, Hash, Order}
    import coulomb.Quantity

    // there are some surprisingly thorny problems with resolving
    // Eq, Hash and Order orthogonally, caused by ambiguous
    // implicit resolutions due to Hash <: Eq and Order <: Eq.
    // I'm going to try the optimistic policy that most users
    // will be working with 'V' types that satisfy all 3.

    given g_Quantity_Cats[V, U](using
        alg: Order[V] & Hash[V]
    ): Quantity_Cats[V, U] =
        new Quantity_Cats[V, U](alg)

    /**
     * A typeclass representing cats Eq, Order and Hash for Quantity[V, U].
     * @tparam V
     *   The value type of the Quantity
     * @tparam U
     *   The unit type of the Quantity
     */
    class Quantity_Cats[V, U](alg: Order[V] & Hash[V])
        extends Order[Quantity[V, U]]
        with Hash[Quantity[V, U]]:

        override def eqv(x: Quantity[V, U], y: Quantity[V, U]): Boolean =
            alg.eqv(x.value, y.value)

        def compare(x: Quantity[V, U], y: Quantity[V, U]): Int =
            alg.compare(x.value, y.value)

        def hash(x: Quantity[V, U]): Int =
            alg.hash(x.value)
