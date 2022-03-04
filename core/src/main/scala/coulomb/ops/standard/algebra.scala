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

