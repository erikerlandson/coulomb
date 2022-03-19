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

export offsetquantity.OffsetQuantity as OffsetQuantity

object offsetquantity:
    opaque type OffsetQuantity[V, U] = V

    // The only two methods I need in scope of the opaque type
    // are a way to lift raw values into a Quantity
    // and a way to extract raw values from a quantity

    abstract class Applier[U]:
        def apply[V](v: V): OffsetQuantity[V, U]
    object Applier:
        given [U]: Applier[U] = new Applier[U] { def apply[V](v: V): OffsetQuantity[V, U] = v } 

    // lift
    object OffsetQuantity:
        def apply[U](using a: Applier[U]) = a
        def apply[U](v: Int): OffsetQuantity[Int, U] = v
        def apply[U](v: Long): OffsetQuantity[Long, U] = v
        def apply[U](v: Float): OffsetQuantity[Float, U] = v
        def apply[U](v: Double): OffsetQuantity[Double, U] = v
    end OffsetQuantity

    // extract
    extension[V, U](ql: OffsetQuantity[V, U])
        def value: V = ql
    extension[U](ql: OffsetQuantity[Int, U])
        def value: Int = ql
    extension[U](ql: OffsetQuantity[Long, U])
        def value: Long = ql
    extension[U](ql: OffsetQuantity[Float, U])
        def value: Float = ql
    extension[U](ql: OffsetQuantity[Double, U])
        def value: Double = ql

    extension[VL, UL](ql: OffsetQuantity[VL, UL])
        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"
        inline def showFull: String = s"${ql.value.toString} ${showUnitFull[UL]}"
    end extension
end offsetquantity
