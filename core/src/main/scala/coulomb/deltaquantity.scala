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
    opaque type DeltaQuantity[V, U] = V

    abstract class Applier[U]:
        def apply[V](v: V): DeltaQuantity[V, U]
    object Applier:
        given [U]: Applier[U] = new Applier[U] { def apply[V](v: V): DeltaQuantity[V, U] = v } 

    // lift
    object DeltaQuantity:
        def apply[U](using a: Applier[U]) = a
        def apply[U](v: Int): DeltaQuantity[Int, U] = v
        def apply[U](v: Long): DeltaQuantity[Long, U] = v
        def apply[U](v: Float): DeltaQuantity[Float, U] = v
        def apply[U](v: Double): DeltaQuantity[Double, U] = v
    end DeltaQuantity

    // lift using withDeltaUnit method
    extension[V](v: V)
        def withDeltaUnit[U]: DeltaQuantity[V, U] = v
    extension(v: Int)
        def withDeltaUnit[U]: DeltaQuantity[Int, U] = v
    extension(v: Long)
        def withDeltaUnit[U]: DeltaQuantity[Long, U] = v
    extension(v: Float)
        def withDeltaUnit[U]: DeltaQuantity[Float, U] = v
    extension(v: Double)
        def withDeltaUnit[U]: DeltaQuantity[Double, U] = v

    // extract
    extension[V, U](ql: DeltaQuantity[V, U])
        def value: V = ql
    extension[U](ql: DeltaQuantity[Int, U])
        def value: Int = ql
    extension[U](ql: DeltaQuantity[Long, U])
        def value: Long = ql
    extension[U](ql: DeltaQuantity[Float, U])
        def value: Float = ql
    extension[U](ql: DeltaQuantity[Double, U])
        def value: Double = ql

    extension[VL, UL](ql: DeltaQuantity[VL, UL])
        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"
        inline def showFull: String = s"${ql.value.toString} ${showUnitFull[UL]}"
    end extension
end deltaquantity
