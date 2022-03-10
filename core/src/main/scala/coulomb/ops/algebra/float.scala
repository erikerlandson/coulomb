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

import algebra.ring.TruncatedDivision

object float extends FloatInstances

trait FloatInstances:
    given ctx_Float_is_FractionalPower: FractionalPower[Float] with
        def pow(v: Float, e: Double): Float = math.pow(v.toDouble, e).toFloat

    given ctx_Float_is_TruncatingPower: TruncatingPower[Float] with
        def tpow(v: Float, e: Double): Float = math.pow(v.toDouble, e).toLong.toFloat

    given ctx_Float_is_TruncatedDivision: TruncatedDivision[Float] with
        def tquot(x: Float, y: Float): Float = (x / y).toLong.toFloat
        // I don't care about these
        def tmod(x: Float, y: Float): Float = ???
        def fquot(x: Float, y: Float): Float = ???
        def fmod(x: Float, y: Float): Float = ???
        def abs(a: Float): Float = ???
        def additiveCommutativeMonoid: algebra.ring.AdditiveCommutativeMonoid[Float] = ???
        def order: cats.kernel.Order[Float] = ???
        def signum(a: Float): Int = ???
