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

object bigint:
    import algebra.ring.TruncatedDivision
    import _root_.spire.math.*
    import coulomb.ops.algebra.*

    given ctx_BigInt_is_TruncatingPower: TruncatingPower[BigInt] =
        (v: BigInt, e: Double) =>
            summon[Fractional[Rational]].fpow(Rational(v), e).toBigInt

    given ctx_BigInt_is_TruncatedDivision: TruncatedDivision[BigInt] with
        def tquot(x: BigInt, y: BigInt): BigInt = x / y
        // I don't care about these
        def tmod(x: BigInt, y: BigInt): BigInt = ???
        def fquot(x: BigInt, y: BigInt): BigInt = ???
        def fmod(x: BigInt, y: BigInt): BigInt = ???
        def abs(a: BigInt): BigInt = ???
        def additiveCommutativeMonoid: algebra.ring.AdditiveCommutativeMonoid[BigInt] = ???
        def order: _root_.cats.kernel.Order[BigInt] = ???
        def signum(a: BigInt): Int = ???
