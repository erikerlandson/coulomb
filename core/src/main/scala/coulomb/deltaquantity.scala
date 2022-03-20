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

package coulomb

export deltaquantity.DeltaQuantity as DeltaQuantity
export deltaquantity.withDeltaUnit as withDeltaUnit

object deltaquantity:
    import coulomb.ops.*

    opaque type DeltaQuantity[V, U, B] = V

    abstract class Applier[U, B]:
        def apply[V](v: V): DeltaQuantity[V, U, B]
    object Applier:
        given [U, B]: Applier[U, B] = new Applier[U, B] { def apply[V](v: V): DeltaQuantity[V, U, B] = v } 

    // lift
    object DeltaQuantity:
        def apply[U, B](using a: Applier[U, B]) = a
        def apply[U, B](v: Int): DeltaQuantity[Int, U, B] = v
        def apply[U, B](v: Long): DeltaQuantity[Long, U, B] = v
        def apply[U, B](v: Float): DeltaQuantity[Float, U, B] = v
        def apply[U, B](v: Double): DeltaQuantity[Double, U, B] = v
    end DeltaQuantity

    // lift using withDeltaUnit method
    extension[V](v: V)
        def withDeltaUnit[U, B]: DeltaQuantity[V, U, B] = v
    extension(v: Int)
        def withDeltaUnit[U, B]: DeltaQuantity[Int, U, B] = v
    extension(v: Long)
        def withDeltaUnit[U, B]: DeltaQuantity[Long, U, B] = v
    extension(v: Float)
        def withDeltaUnit[U, B]: DeltaQuantity[Float, U, B] = v
    extension(v: Double)
        def withDeltaUnit[U, B]: DeltaQuantity[Double, U, B] = v

    // extract
    extension[V, U, B](ql: DeltaQuantity[V, U, B])
        def value: V = ql
    extension[U, B](ql: DeltaQuantity[Int, U, B])
        def value: Int = ql
    extension[U, B](ql: DeltaQuantity[Long, U, B])
        def value: Long = ql
    extension[U, B](ql: DeltaQuantity[Float, U, B])
        def value: Float = ql
    extension[U, B](ql: DeltaQuantity[Double, U, B])
        def value: Double = ql

    extension[VL, UL, BL](ql: DeltaQuantity[VL, UL, BL])
        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"
        inline def showFull: String = s"${ql.value.toString} ${showUnitFull[UL]}"

        transparent inline def -[B, VR, UR](qr: DeltaQuantity[VR, UR, B])(using sub: DeltaSub[B, VL, UL, VR, UR]): Quantity[sub.VO, sub.UO] =
            sub(ql, qr)

    end extension
end deltaquantity
