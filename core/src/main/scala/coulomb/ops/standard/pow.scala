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
import coulomb.ops.{Pow, SimplifiedUnit}

transparent inline given ctx_quantity_pow[V, U, E](using
    alg: CanPow[V, E],
    su: SimplifiedUnit[U ^ E]
        ): Pow[V, U, E] =
    new Pow[V, U, E]:
        type VO = V
        type UO = su.UO
        def apply(v: V): VO = alg.pow(v)

abstract class CanPow[V, E]:
    def pow(v: V): V

object CanPow:
    import coulomb.rational.typeexpr
    import algebra.ring.MultiplicativeSemigroup
    import algebra.ring.MultiplicativeGroup

    inline given ctx_FracPow_CanPow[V, E](using fp: FracPow[V]): CanPow[V, E] =
        val e: Double = typeexpr.double[E]
        new CanPow[V, E]:
            def pow(v: V): V = fp.pow(v, e)

    inline given ctx_MultiplicativeGroup_CanPow[V, E](using
        nfp: NotGiven[FracPow[V]],
        mgp: MultiplicativeGroup[V],
        aie: typeexpr.AllInt[E]): CanPow[V, E] =
        val e: Int = aie.value
        new CanPow[V, E]:
            def pow(v: V): V = mgp.pow(v, e)

    inline given ctx_MultiplicativeSemigroup_CanPow[V, E](using
        nfp: NotGiven[FracPow[V]],
        nmg: NotGiven[MultiplicativeGroup[V]],
        msg: MultiplicativeSemigroup[V],
        pie: typeexpr.PosInt[E]): CanPow[V, E] =
        val e: Int = pie.value
        new CanPow[V, E]:
            def pow(v: V): V = msg.pow(v, e)

    // there is no typelevel community typeclass that expresses the concept
    // "supports raising to fractional powers without truncation"
    // The closest thing is spire NRoot, but it is also defined on truncating integer types,
    // so it is not helpful for distinguishing "pow" from "tpow", and in any case requires spire
    // https://github.com/typelevel/spire/issues/741
    abstract class FracPow[V]:
        def pow(v: V, e: Double): V

    object FracPow:
        given ctx_Double_is_FracPow: FracPow[Double] with
            def pow(v: Double, e: Double): Double = math.pow(v, e) 

        given ctx_Float_is_FracPow: FracPow[Float] with
            def pow(v: Float, e: Double): Float = math.pow(v, e).toFloat 
