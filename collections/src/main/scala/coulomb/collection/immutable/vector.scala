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

import scala.collection.{AbstractIterator, StrictOptimizedSeqOps, View, mutable}
import scala.collection.immutable.{IndexedSeq, IndexedSeqOps}
import scala.reflect.ClassTag

import coulomb.*
import coulomb.syntax.*
import coulomb.conversion.*

final class QuantityVector[V, U] private (
    val values: Vector[V]
) extends IndexedSeq[Quantity[V, U]],
      IndexedSeqOps[Quantity[V, U], IndexedSeq, QuantityVector[V, U]],
      StrictOptimizedSeqOps[Quantity[V, U], IndexedSeq, QuantityVector[V, U]]:

    override def className = "QuantityVector"

    def length: Int = values.length

    def apply(idx: Int): Quantity[V, U] =
        values(idx).withUnit[U]

    override def iterator: Iterator[Quantity[V, U]] =
        values.iterator.map(_.withUnit[U])

    inline def ++(suffix: IterableOnce[Quantity[V, U]]): QuantityVector[V, U] =
        concat(suffix)

    inline def ++[VS, US](suffix: IterableOnce[Quantity[VS, US]])(using
        qc: scala.Conversion[Quantity[VS, US], Quantity[V, U]]
    ): QuantityVector[V, U] =
        concat(suffix)

    def concat(suffix: IterableOnce[Quantity[V, U]]): QuantityVector[V, U] =
        val svec: Vector[V] = suffix match
            case qve: QuantityVector[?, ?] =>
                qve.asInstanceOf[QuantityVector[V, U]].values
            case seqe: scala.collection.Seq[?] =>
                val seq =
                    seqe.asInstanceOf[scala.collection.Seq[Quantity[V, U]]]
                Vector.from(seq.map(_.value))
            case _ =>
                Vector.from(suffix.iterator.map(_.value))
        new QuantityVector[V, U](values ++ svec)

    def concat[VS, US](suffix: IterableOnce[Quantity[VS, US]])(using
        cnv: scala.Conversion[Quantity[VS, US], Quantity[V, U]]
    ): QuantityVector[V, U] =
        val svec: Vector[V] = cnv match
            // if we have a QuantityConversion we can optimize
            // by applying directly to raw values
            case qce: QuantityConversion[?, ?, ?, ?] =>
                val qc = qce.asInstanceOf[QuantityConversion[VS, US, V, U]]
                suffix match
                    case qve: QuantityVector[?, ?] =>
                        qve.asInstanceOf[QuantityVector[VS, US]]
                            .values
                            .map(qc.raw)
                    case seqe: scala.collection.Seq[?] =>
                        val seq = seqe.asInstanceOf[
                            scala.collection.Seq[Quantity[VS, US]]
                        ]
                        Vector.from(seq.map { e => qc.raw(e.value) })
                    case _ =>
                        Vector.from(suffix.iterator.map { e =>
                            qc.raw(e.value)
                        })
            case _ =>
                // if it isn't a QuantityConversion, we can only assume basic
                // scala.Conversion function
                suffix match
                    case qve: QuantityVector[?, ?] =>
                        qve.asInstanceOf[QuantityVector[VS, US]].map(cnv).values
                    case seqe: scala.collection.Seq[?] =>
                        val seq = seqe.asInstanceOf[
                            scala.collection.Seq[Quantity[VS, US]]
                        ]
                        Vector.from(seq.map(cnv(_).value))
                    case _ =>
                        Vector.from(suffix.iterator.map(cnv(_).value))
        new QuantityVector[V, U](values ++ svec)

    def map[VF, UF](
        f: Quantity[V, U] => Quantity[VF, UF]
    ): QuantityVector[VF, UF] =
        strictOptimizedMap(newSpecificBuilderQV[VF, UF], f)

    def flatMap[VF, UF](
        f: Quantity[V, U] => IterableOnce[Quantity[VF, UF]]
    ): QuantityVector[VF, UF] =
        strictOptimizedFlatMap(newSpecificBuilderQV[VF, UF], f)

    override def empty: QuantityVector[V, U] = QuantityVector.empty[V, U]

    override protected def fromSpecific(
        it: IterableOnce[Quantity[V, U]]
    ): QuantityVector[V, U] =
        QuantityVector.from(it)

    override protected def newSpecificBuilder
        : mutable.Builder[Quantity[V, U], QuantityVector[V, U]] =
        newSpecificBuilderQV[V, U]

    protected def newSpecificBuilderQV[VB, UB]
        : mutable.Builder[Quantity[VB, UB], QuantityVector[VB, UB]] =
        mutable.ArrayBuffer
            .newBuilder[Quantity[VB, UB]]
            .mapResult(QuantityVector.from)

    def toValue[VO](using
        vc: ValueConversion[V, VO]
    ): QuantityVector[VO, U] =
        QuantityVector[U](values.map { v => vc(v) })

    def toUnit[UO](using
        uc: UnitConversion[V, U, UO]
    ): QuantityVector[V, UO] =
        QuantityVector[UO](values.map { v => uc(v) })

    def toVU[VO, UO](using
        vc: ValueConversion[V, VO],
        uc: UnitConversion[VO, U, UO]
    ): QuantityVector[VO, UO] =
        QuantityVector[UO](values.map { v => uc(vc(v)) })

object QuantityVector:
    def apply[U](using a: Applier[U]) = a

    class Applier[U]:
        def apply[V](vs: IterableOnce[V]): QuantityVector[V, U] =
            new QuantityVector[V, U](Vector.from(vs))
        def apply[V](vs: V*): QuantityVector[V, U] =
            new QuantityVector[V, U](Vector.from(vs))

    object Applier:
        given ctx_Applier[U]: Applier[U] = new Applier[U]

    def apply[V, U](args: Quantity[V, U]*): QuantityVector[V, U] =
        from(args)

    def empty[V, U]: QuantityVector[V, U] =
        new QuantityVector[V, U](Vector.empty[V])

    def from[V, U](it: IterableOnce[Quantity[V, U]]): QuantityVector[V, U] =
        it match
            case qv: QuantityVector[?, ?] =>
                // this trick works because we already know that the
                // element type is Quantity[V, U]
                qv.asInstanceOf[QuantityVector[V, U]]
            case seq: scala.collection.Seq[?] =>
                val qs = seq.asInstanceOf[scala.collection.Seq[Quantity[V, U]]]
                new QuantityVector[V, U](Vector.from(qs.map(_.value)))
            case _ =>
                new QuantityVector[V, U](Vector.from(it.iterator.map(_.value)))

object benchmark:
    import java.time.*

    def time[X](x: => X, n: Int = 11): Double = {
        var t0 = Instant.now.toEpochMilli
        val times = for {
            _ <- 0 until n
        } yield {
            val _ = x
            val t = Instant.now.toEpochMilli
            val tt = (t - t0).toDouble / 1000.0
            t0 = t
            tt
        }
        times(times.length / 2)
    }
