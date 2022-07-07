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

object int:
    given ctx_Int_is_TruncatingPower: TruncatingPower[Int] with
        def tpow(v: Int, e: Double): Int = math.pow(v.toDouble, e).toInt

    given ctx_Int_is_TruncatedDivision: TruncatedDivision[Int] with
        def tquot(x: Int, y: Int): Int = x / y
        // I don't care about these
        def tmod(x: Int, y: Int): Int = ???
        def fquot(x: Int, y: Int): Int = ???
        def fmod(x: Int, y: Int): Int = ???
        def abs(a: Int): Int = ???
        def additiveCommutativeMonoid: algebra.ring.AdditiveCommutativeMonoid[Int] = ???
        def order: _root_.cats.kernel.Order[Int] = ???
        def signum(a: Int): Int = ???

