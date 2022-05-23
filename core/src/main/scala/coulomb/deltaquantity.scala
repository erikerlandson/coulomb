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

import coulomb.syntax.*
import coulomb.ops.*
import coulomb.conversion.*

package syntax {
    // lift using withDeltaUnit method
    extension[V](v: V)
        inline def withDeltaUnit[U, B]: DeltaQuantity[V, U, B] = DeltaQuantity[U, B](v)
}

opaque type DeltaQuantity[V, U, B] = V

object DeltaQuantity:
    def apply[U, B](using a: Applier[U, B]) = a

    abstract class Applier[U, B]:
        def apply[V](v: V): DeltaQuantity[V, U, B]
    object Applier:
        given [U, B]: Applier[U, B] = new Applier[U, B] { def apply[V](v: V): DeltaQuantity[V, U, B] = v } 

    extension[VL, UL, B](ql: DeltaQuantity[VL, UL, B])
        inline def value: VL = ql

        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"
        inline def showFull: String = s"${ql.value.toString} ${showUnitFull[UL]}"

        inline def toValue[V](using conv: ValueConversion[VL, V]): DeltaQuantity[V, UL, B] =
            conv(ql.value).withDeltaUnit[UL, B]
        inline def toUnit[U](using conv: DeltaUnitConversion[VL, B, UL, U]): DeltaQuantity[VL, U, B] =
            conv(ql.value).withDeltaUnit[U, B]

        inline def tToValue[V](using conv: TruncatingValueConversion[VL, V]): DeltaQuantity[V, UL, B] =
            conv(ql.value).withDeltaUnit[UL, B]
        inline def tToUnit[U](using conv: TruncatingDeltaUnitConversion[VL, B, UL, U]): DeltaQuantity[VL, U, B] =
            conv(ql.value).withDeltaUnit[U, B]

        transparent inline def -[VR, UR](qr: DeltaQuantity[VR, UR, B])(using sub: DeltaSub[B, VL, UL, VR, UR]): Quantity[sub.VO, sub.UO] =
            sub.eval(ql, qr)

        transparent inline def -[VR, UR](qr: Quantity[VR, UR])(using sub: DeltaSubQ[B, VL, UL, VR, UR]): DeltaQuantity[sub.VO, sub.UO, B] =
            sub.eval(ql, qr)

        transparent inline def +[VR, UR](qr: Quantity[VR, UR])(using add: DeltaAddQ[B, VL, UL, VR, UR]): DeltaQuantity[add.VO, add.UO, B] =
            add.eval(ql, qr)

        inline def ===[VR, UR](qr: DeltaQuantity[VR, UR, B])(using ord: DeltaOrd[B, VL, UL, VR, UR]): Boolean =
            ord(ql, qr) == 0

        inline def =!=[VR, UR](qr: DeltaQuantity[VR, UR, B])(using ord: DeltaOrd[B, VL, UL, VR, UR]): Boolean =
            ord(ql, qr) != 0

        inline def <[VR, UR](qr: DeltaQuantity[VR, UR, B])(using ord: DeltaOrd[B, VL, UL, VR, UR]): Boolean =
            ord(ql, qr) < 0

        inline def <=[VR, UR](qr: DeltaQuantity[VR, UR, B])(using ord: DeltaOrd[B, VL, UL, VR, UR]): Boolean =
            ord(ql, qr) <= 0

        inline def >[VR, UR](qr: DeltaQuantity[VR, UR, B])(using ord: DeltaOrd[B, VL, UL, VR, UR]): Boolean =
            ord(ql, qr) > 0

        inline def >=[VR, UR](qr: DeltaQuantity[VR, UR, B])(using ord: DeltaOrd[B, VL, UL, VR, UR]): Boolean =
            ord(ql, qr) >= 0
