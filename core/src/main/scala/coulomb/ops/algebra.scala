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

import scala.annotation.implicitNotFound

import _root_.algebra.ring.*

import _root_.spire.math.*

import coulomb.*
import coulomb.syntax.*

// there is no typelevel community typeclass that expresses the concept
// "supports raising to fractional powers, without truncation"
// The closest thing is spire NRoot, but it is also defined on truncating integer types,
// so it is not helpful for distinguishing "pow" from "tpow", and in any case requires spire
// https://github.com/typelevel/spire/issues/741

@implicitNotFound("Fractional power not defined for value type ${V}")
abstract class FractionalPower[V]:
    /** returns v^e */
    def pow(v: V, e: Double): V

@implicitNotFound("Truncating power not defined for value type ${V}")
abstract class TruncatingPower[V]:
    /** returns v^e, truncated to integer value (toward zero) */
    def tpow(v: V, e: Double): V

object all:
    export coulomb.ops.algebra.int.given
    export coulomb.ops.algebra.long.given
    export coulomb.ops.algebra.bigint.given
    export coulomb.ops.algebra.float.given
    export coulomb.ops.algebra.double.given
    export coulomb.ops.algebra.bigdecimal.given
    export coulomb.ops.algebra.rational.given
    export coulomb.ops.algebra.real.given
    export coulomb.ops.algebra.algebraic.given

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
        def additiveCommutativeMonoid
            : _root_.algebra.ring.AdditiveCommutativeMonoid[Int] = ???
        def order: _root_.cats.kernel.Order[Int] = ???
        def signum(a: Int): Int = ???

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
        def additiveCommutativeMonoid
            : _root_.algebra.ring.AdditiveCommutativeMonoid[Long] = ???
        def order: _root_.cats.kernel.Order[Long] = ???
        def signum(a: Long): Int = ???

object bigint:
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
        def additiveCommutativeMonoid
            : _root_.algebra.ring.AdditiveCommutativeMonoid[BigInt] = ???
        def order: _root_.cats.kernel.Order[BigInt] = ???
        def signum(a: BigInt): Int = ???

object float:
    given ctx_Float_is_FractionalPower: FractionalPower[Float] =
        (v: Float, e: Double) => math.pow(v.toDouble, e).toFloat

object double:
    given ctx_Double_is_FractionalPower: FractionalPower[Double] =
        (v: Double, e: Double) => math.pow(v, e)

object bigdecimal:
    given ctx_BigDecimal_is_FractionalPower: FractionalPower[BigDecimal] =
        (v: BigDecimal, e: Double) => summon[Fractional[BigDecimal]].fpow(v, e)

object rational:
    given ctx_Rational_is_FractionalPower: FractionalPower[Rational] =
        (v: Rational, e: Double) =>
            if (e.isValidInt) v.pow(e.toInt)
            else summon[Fractional[Rational]].fpow(v, e)

object real:
    given ctx_Real_is_FractionalPower: FractionalPower[Real] =
        (v: Real, e: Double) => summon[Fractional[Real]].fpow(v, e)

object algebraic:
    import coulomb.conversion.ValueConversion
    import coulomb.conversion.standard.value.given

    given ctx_Algebraic_is_FractionalPower: FractionalPower[Algebraic] =
        // Fractional[Algebraic] exists but throws errors, so
        // do the fpow() in BigDecimal
        val a2b = summon[ValueConversion[Algebraic, BigDecimal]]
        val b2a = summon[ValueConversion[BigDecimal, Algebraic]]
        val bf = summon[Fractional[BigDecimal]]
        (v: Algebraic, e: Double) => b2a(bf.fpow(a2b(v), e))
