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

package coulomb.ops.algebra

import _root_.algebra.ring.TruncatedDivision

import coulomb.*
import coulomb.syntax.*
import coulomb.ops.*

object long:
    given ctx_Long_is_TruncatingPower: TruncatingPower[Long] with
        def tpow(v: Long, e: Double): Long = math.pow(v.toDouble, e).toLong

    given ctx_Long_is_TruncatedDivision: TruncatedDivision[Long] with
        def tquot(x: Long, y: Long): Long = x / y
        // I don't care about these
        def tmod(x: Long, y: Long): Long = ???
        def fquot(x: Long, y: Long): Long = ???
        def fmod(x: Long, y: Long): Long = ???
        def abs(a: Long): Long = ???
        def additiveCommutativeMonoid: _root_.algebra.ring.AdditiveCommutativeMonoid[Long] = ???
        def order: _root_.cats.kernel.Order[Long] = ???
        def signum(a: Long): Int = ???

    extension(vl: Long)
        transparent inline def *[VR, UR](qr: Quantity[VR, UR])(using mul: Mul[Long, 1, VR, UR]): Quantity[mul.VO, mul.UO] =
            mul.eval(vl.withUnit[1], qr)

        transparent inline def /[VR, UR](qr: Quantity[VR, UR])(using div: Div[Long, 1, VR, UR]): Quantity[div.VO, div.UO] =
            div.eval(vl.withUnit[1], qr)
