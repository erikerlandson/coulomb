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

package coulomb.ops.algebra.spire

import coulomb.*
import coulomb.syntax.*
import coulomb.ops.*

object real:
    import _root_.spire.math.*
    import coulomb.ops.algebra.*

    given ctx_Real_is_FractionalPower: FractionalPower[Real] =
        (v: Real, e: Double) => summon[Fractional[Real]].fpow(v, e)

    extension (vl: Real)
        transparent inline def *[VR, UR](qr: Quantity[VR, UR])(using
            mul: Mul[Real, 1, VR, UR]
        ): Quantity[mul.VO, mul.UO] =
            mul.eval(vl.withUnit[1], qr)

        transparent inline def /[VR, UR](qr: Quantity[VR, UR])(using
            div: Div[Real, 1, VR, UR]
        ): Quantity[div.VO, div.UO] =
            div.eval(vl.withUnit[1], qr)
