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

import coulomb.`^`
import coulomb.ops.{TPow, SimplifiedUnit}

transparent inline given ctx_quantity_tpow[V, U, E](using
    alg: CanTPow[V, E],
    su: SimplifiedUnit[U ^ E]
        ): TPow[V, U, E] =
    new TPow[V, U, E]:
        type VO = V
        type UO = su.UO
        def apply(v: V): VO = alg.tpow(v)

abstract class CanTPow[V, E]:
    def tpow(v: V): V

object CanTPow:
    // there is no typelevel community typeclass that expresses the concept
    // "supports raising to fractional powers with truncation"
    // The closest thing is spire NRoot, but it does not formally distinguish between
    // "pow" and "tpow" or which types support which operation,
    // so it is not helpful for distinguishing "pow" from "tpow", and in any case requires spire
    // https://github.com/typelevel/spire/issues/741

    import coulomb.rational.typeexpr

    inline given ctx_Double_CanTPow[E]: CanTPow[Double, E] =
        val e: Double = typeexpr.double[E]
        new CanTPow[Double, E]:
            def tpow(v: Double): Double = math.pow(v, e).toLong.toDouble 

    inline given ctx_Float_CanTPow[E]: CanTPow[Float, E] =
        val e: Double = typeexpr.double[E]
        new CanTPow[Float, E]:
            def tpow(v: Float): Float = math.pow(v.toDouble, e).toLong.toFloat

    inline given ctx_Long_CanTPow[E]: CanTPow[Long, E] =
        val e: Double = typeexpr.double[E]
        new CanTPow[Long, E]:
            def tpow(v: Long): Long = math.pow(v.toDouble, e).toLong

    inline given ctx_Int_CanTPow[E]: CanTPow[Int, E] =
        val e: Double = typeexpr.double[E]
        new CanTPow[Int, E]:
            def tpow(v: Int): Int = math.pow(v.toDouble, e).toInt
