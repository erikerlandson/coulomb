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

import coulomb.rational.Rational

// could do most of this with scala.math.Numeric, but not powers
abstract class Algebra[V]:
    def add(vl: V, vr: V): V
    def sub(vl: V, vr: V): V
    def mul(vl: V, vr: V): V
    def div(vl: V, vr: V): V
    def neg(v: V): V
    def pow(v: V, p: Rational): V

object Algebra:
    given ctx_alg_Double: Algebra[Double] with
        def add(vl: Double, vr: Double): Double = vl + vr
        def sub(vl: Double, vr: Double): Double = vl - vr
        def mul(vl: Double, vr: Double): Double = vl * vr
        def div(vl: Double, vr: Double): Double = vl / vr
        def neg(v: Double): Double = -v
        def pow(v: Double, p: Rational): Double = scala.math.pow(v, p.toDouble)

    given ctx_alg_Float: Algebra[Float] with
        def add(vl: Float, vr: Float): Float = vl + vr
        def sub(vl: Float, vr: Float): Float = vl - vr
        def mul(vl: Float, vr: Float): Float = vl * vr
        def div(vl: Float, vr: Float): Float = vl / vr
        def neg(v: Float): Float = -v
        def pow(v: Float, p: Rational): Float = scala.math.pow(v, p.toDouble).toFloat

    given ctx_alg_Long: Algebra[Long] with
        def add(vl: Long, vr: Long): Long = vl + vr
        def sub(vl: Long, vr: Long): Long = vl - vr
        def mul(vl: Long, vr: Long): Long = vl * vr
        def div(vl: Long, vr: Long): Long = vl / vr
        def neg(v: Long): Long = -v
        def pow(v: Long, p: Rational): Long = scala.math.pow(v.toDouble, p.toDouble).toLong

    given ctx_alg_Int: Algebra[Int] with
        def add(vl: Int, vr: Int): Int = vl + vr
        def sub(vl: Int, vr: Int): Int = vl - vr
        def mul(vl: Int, vr: Int): Int = vl * vr
        def div(vl: Int, vr: Int): Int = vl / vr
        def neg(v: Int): Int = -v
        def pow(v: Int, p: Rational): Int = scala.math.pow(v, p.toDouble).toInt

