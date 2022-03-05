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

package coulomb.ops.standard

import scala.util.NotGiven
import scala.math.{Fractional, Integral, Numeric}

import coulomb.rational.Rational
import coulomb.policy.AllowTruncation

abstract class CanAdd[V]:
   def add(vl: V, vr: V): V

object CanAdd:
    inline given ctx_CanAdd_Numeric[V](using num: Numeric[V]): CanAdd[V] =
        new CanAdd[V]:
            def add(vl: V, vr: V): V = num.plus(vl, vr)

abstract class CanSub[V]:
   def sub(vl: V, vr: V): V

object CanSub:
    inline given ctx_CanSub_Numeric[V](using num: Numeric[V]): CanSub[V] =
        new CanSub[V]:
            def sub(vl: V, vr: V): V = num.minus(vl, vr)

abstract class CanMul[V]:
    def mul(vl: V, vr: V): V

object CanMul:
    inline given ctx_CanMul_Numeric[V](using num: Numeric[V]): CanMul[V] =
        new CanMul[V]:
            def mul(vl: V, vr: V): V = num.times(vl, vr)

abstract class CanDiv[V]:
    def div(vl: V, vr: V): V

object CanDiv:
    inline given ctx_CanDiv_Fractional[V](using num: Fractional[V]): CanDiv[V] =
        new CanDiv[V]:
            def div(vl: V, vr: V): V = num.div(vl, vr)
    
    inline given ctx_CanDiv_Integral[V](using num: Integral[V], at: AllowTruncation): CanDiv[V] =
        new CanDiv[V]:
            def div(vl: V, vr: V): V = num.quot(vl, vr)

abstract class CanPow[V, E]:
    def pow(v: V): V

object CanPow:
    import coulomb.rational.typeexpr
    import scala.math

    inline given ctx_CanPow_Double[E]: CanPow[Double, E] =
        val e: Double = typeexpr.double[E]
        new CanPow[Double, E]:
            def pow(v: Double): Double = math.pow(v, e)

    inline given ctx_CanPow_Float[E]: CanPow[Float, E] =
        val e: Double = typeexpr.double[E]
        new CanPow[Float, E]:
            def pow(v: Float): Float = math.pow(v.toDouble, e).toFloat

    inline given ctx_CanPow_Long[E](using gez: typeexpr.NonNegInt[E]): CanPow[Long, E] =
        new CanPow[Long, E]:
            def pow(v: Long): Long = math.pow(v.toDouble, gez.value.toDouble).toLong

    inline given ctx_CanPow_Long_trunc[E](using NotGiven[typeexpr.NonNegInt[E]], AllowTruncation):
            CanPow[Long, E] =
        val e = typeexpr.double[E]
        new CanPow[Long, E]:
            def pow(v: Long): Long = math.pow(v.toDouble, e).toLong

    inline given ctx_CanPow_Int[E](using gez: typeexpr.NonNegInt[E]): CanPow[Int, E] =
        new CanPow[Int, E]:
            def pow(v: Int): Int = math.pow(v.toDouble, gez.value.toDouble).toInt

    inline given ctx_CanPow_Int_trunc[E](using NotGiven[typeexpr.NonNegInt[E]], AllowTruncation):
            CanPow[Int, E] =
        val e = typeexpr.double[E]
        new CanPow[Int, E]:
            def pow(v: Int): Int = math.pow(v.toDouble, e).toInt

