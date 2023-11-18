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

/** Provides cats typeclasses for DeltaQuantity */
object deltaquantity:
    import _root_.cats.kernel.{Eq, Hash, Order}
    import coulomb.DeltaQuantity

    given g_DeltaQuantity_Cats[V, U, B](using
        alg: Order[V] & Hash[V]
    ): DeltaQuantity_Cats[V, U, B] =
        new DeltaQuantity_Cats[V, U, B](alg)

    /**
     * A typeclass representing cats Eq, Order and Hash for DeltaQuantity[V, U, B].
     * @tparam V
     *   The value type of the DeltaQuantity
     * @tparam U
     *   The unit type of the DeltaQuantity
     * @tparam B
     *   The base unit scope for the delta unit `U`
     */
    class DeltaQuantity_Cats[V, U, B](alg: Order[V] & Hash[V])
        extends Order[DeltaQuantity[V, U, B]]
        with Hash[DeltaQuantity[V, U, B]]:

        override def eqv(
            x: DeltaQuantity[V, U, B],
            y: DeltaQuantity[V, U, B]
        ): Boolean =
            alg.eqv(x.value, y.value)

        def compare(x: DeltaQuantity[V, U, B], y: DeltaQuantity[V, U, B]): Int =
            alg.compare(x.value, y.value)

        def hash(x: DeltaQuantity[V, U, B]): Int =
            alg.hash(x.value)
