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
import coulomb.Quantity

object quantity:

    given ctx_Quantity_Order[V, U](using p0: priority.Prio0, ord: Order[V]): Order[Quantity[V, U]] =
        new QOrder[V, U](ord)

    given ctx_Quantity_Hash[V, U](using p1: priority.Prio1, h: Hash[V]): Hash[Quantity[V, U]] =
        new QHash[V, U](h)

    given ctx_Quantity_Eq[V, U](using p2: priority.Prio2, e: Eq[V]): Eq[Quantity[V, U]] =
        new QEq[V, U](e)

    class QOrder[V, U](ord: Order[V]) extends Order[Quantity[V, U]]:
        def compare(x: Quantity[V, U], y: Quantity[V, U]) = ord.compare(x.value, y.value)

    class QEq[V, U](e: Eq[V]) extends Eq[Quantity[V, U]]:
        def eqv(x: Quantity[V, U], y: Quantity[V, U]) = e.eqv(x.value, y.value)

    class QHash[V, U](h: Hash[V]) extends QEq[V, U](h) with Hash[Quantity[V, U]]:
        def hash(x: Quantity[V, U]) = h.hash(x.value)

object priority:
    // lower number = higher priority
    class Prio0 extends Prio1
    object Prio0 { given Prio0() }

    class Prio1 extends Prio2
    object Prio1 { given Prio1() }

    class Prio2
    object Prio2 { given Prio2() }

object repro:
    // analogous to cats Eq, Hash, Order:
    class A[V]
    class B[V] extends A[V]
    class C[V] extends A[V]

    class Q[V]

    object context:
        // prios work here, which is cool
        given[V](using priority.Prio0): C[V] = new C[V]
        given[V](using priority.Prio1): B[V] = new B[V]
        given[V](using priority.Prio2): A[V] = new A[V]

    object exports:
        // so will these exports
        export context.given

    object qcontext:
        // base defs, like from cats
        given B[Int] = new B[Int]
        given C[Int] = new C[Int]

        // these seem like they should work but don't
        given[V](using p0: priority.Prio0, c: C[V]): C[Q[V]] = new C[Q[V]]
        given[V](using p1: priority.Prio1, b: B[V]): B[Q[V]] = new B[Q[V]]
        given[V](using p2: priority.Prio2, a: A[V]): A[Q[V]] = new A[Q[V]]

object test1:
    import repro.*
    import repro.exports.given
    // these will work
    val c = summon[C[Int]]
    val b = summon[B[Int]]
    val a = summon[A[Int]]

object test2:
    import repro.*
    import repro.qcontext.given

    // will fail as ambiguous - prios aren't having an effect
    val a = summon[A[Q[Int]]]
