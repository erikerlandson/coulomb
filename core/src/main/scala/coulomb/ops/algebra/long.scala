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

object long extends LongInstances

trait LongInstances:
    given ctx_Long_is_TruncatingPower: TruncatingPower[Long] with
        def tpow(v: Long, e: Double): Long = math.pow(v.toDouble, e).toLong

    given ctx_Long_is_TruncatedDivision: TruncatedDivision[Long] with
        def tquot(x: Long, y: Long): Long = x / y
        // I don't care about these
        def tmod(x: Long, y: Long): Long = ???
        def fquot(x: Long, y: Long): Long = ???
        def fmod(x: Long, y: Long): Long = ???
        def abs(a: Long): Long = ???
        def additiveCommutativeMonoid: algebra.ring.AdditiveCommutativeMonoid[Long] = ???
        def order: cats.kernel.Order[Long] = ???
        def signum(a: Long): Int = ???
