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

object double:
    given ctx_Double_is_FractionalPower: FractionalPower[Double] with
        def pow(v: Double, e: Double): Double = math.pow(v, e)

    given ctx_Double_is_TruncatingPower: TruncatingPower[Double] with
        def tpow(v: Double, e: Double): Double = math.pow(v, e).toLong.toDouble

    given ctx_Double_is_TruncatedDivision: TruncatedDivision[Double] with
        def tquot(x: Double, y: Double): Double = (x / y).toLong.toDouble
        // I don't care about these
        def tmod(x: Double, y: Double): Double = ???
        def fquot(x: Double, y: Double): Double = ???
        def fmod(x: Double, y: Double): Double = ???
        def abs(a: Double): Double = ???
        def additiveCommutativeMonoid: algebra.ring.AdditiveCommutativeMonoid[Double] = ???
        def order: cats.kernel.Order[Double] = ???
        def signum(a: Double): Int = ???
