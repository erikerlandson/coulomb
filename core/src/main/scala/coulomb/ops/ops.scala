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

package coulomb.ops

import scala.annotation.implicitNotFound

import coulomb.*

@implicitNotFound("Negation not defined in scope for Quantity[${V}, ${U}]")
abstract class Neg[V, U]:
    def apply(v: V): V

@implicitNotFound("Addition not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Add[VL, UL, VR, UR]:
    type VO
    type UO
    def apply(vl: VL, vr: VR): VO

@implicitNotFound("Addition not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Add2[VL, UL, VR, UR]:
    type VO
    type UO
    def apply(ql: Quantity[VL, UL], qr: Quantity[VR, UR]): Quantity[VO, UO]

@implicitNotFound("Subtraction not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Sub[VL, UL, VR, UR]:
    type VO
    type UO
    def apply(vl: VL, vr: VR): VO

@implicitNotFound("Multiplication not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Mul[VL, UL, VR, UR]:
    type VO
    type UO
    def apply(vl: VL, vr: VR): VO

@implicitNotFound("Division not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Div[VL, UL, VR, UR]:
    type VO
    type UO
    def apply(vl: VL, vr: VR): VO

@implicitNotFound("Truncating Division not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class TQuot[VL, UL, VR, UR]:
    type VO
    type UO
    def apply(vl: VL, vr: VR): VO

@implicitNotFound("Power not defined in scope for Quantity[${V}, ${U}] ^ ${P}")
abstract class Pow[V, U, P]:
    type VO
    type UO
    def apply(v: V): VO

@implicitNotFound("Truncating Power not defined in scope for Quantity[${V}, ${U}] ^ ${P}")
abstract class TPow[V, U, P]:
    type VO
    type UO
    def apply(v: V): VO

@implicitNotFound("Ordering not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Ord[VL, UL, VR, UR]:
    def apply(vl: VL, vr: VR): Int

/** Resolve the operator output type for left and right argument types */
@implicitNotFound("No output type resolution in scope for argument value types {VL} and {VR}")
abstract class ValueResolution[VL, VR]:
    type VO

@implicitNotFound("Unable to simplify unit type ${U}")
abstract class SimplifiedUnit[U]:
    type UO

object SimplifiedUnit:
    transparent inline given ctx_SimplifiedUnit[U]: SimplifiedUnit[U] =
        ${ coulomb.infra.meta.simplifiedUnit[U] }
