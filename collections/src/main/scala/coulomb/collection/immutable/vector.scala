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

package coulomb.collection.immutable

import scala.collection.{ AbstractIterator, StrictOptimizedSeqOps, View, mutable }
import scala.collection.immutable.{ IndexedSeq, IndexedSeqOps }
import scala.reflect.ClassTag

import coulomb.*
import coulomb.syntax.*

final class QuantityVector[V, U] private (
    val values: Vector[V]
) extends
    IndexedSeq[Quantity[V, U]],
    IndexedSeqOps[Quantity[V, U], IndexedSeq, QuantityVector[V, U]],
    StrictOptimizedSeqOps[Quantity[V, U], IndexedSeq, QuantityVector[V, U]]:

    def length: Int = values.length

    def apply(idx: Int): Quantity[V, U] =
        values(idx).withUnit[U]

    override def empty: QuantityVector[V, U] = QuantityVector.empty[V, U]

    override protected def fromSpecific(it: IterableOnce[Quantity[V, U]]): QuantityVector[V, U] =
        QuantityVector.from(it)

    override protected def newSpecificBuilder: mutable.Builder[Quantity[V, U], QuantityVector[V, U]] =
        mutable.ArrayBuffer.newBuilder[Quantity[V, U]]
            .mapResult(QuantityVector.from)

    override def iterator: Iterator[Quantity[V, U]] =
        values.iterator.map(_.withUnit[U])

    override def className = "QuantityVector"

object QuantityVector:
    def apply[V, U](args: Quantity[V, U]*): QuantityVector[V, U] =
        from(args)

    def empty[V, U]: QuantityVector[V, U] =
        new QuantityVector[V, U](Vector.empty[V])

    def from[V, U](it: IterableOnce[Quantity[V, U]]): QuantityVector[V, U] =
        // may be able to optimize with a case statement here
        new QuantityVector[V, U](Vector.from(it.iterator.map(_.value)))

