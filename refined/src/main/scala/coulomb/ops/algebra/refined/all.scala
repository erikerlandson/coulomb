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

package coulomb.ops.algebra.refined

import algebra.ring.*

import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

object all:
    inline given ctx_AdditiveSemigroup_Refined_Positive[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, Positive]
    ): AdditiveSemigroup[Refined[V, Positive]] =
        new AdditiveSemigroup[Refined[V, Positive]]:
            def plus(x: Refined[V, Positive], y: Refined[V, Positive]): Refined[V, Positive] =
                refineV[Positive].unsafeFrom(alg.plus(x.value, y.value))

    inline given ctx_AdditiveSemigroup_Refined_NonNegative[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, NonNegative]
    ): AdditiveSemigroup[Refined[V, NonNegative]] =
        new AdditiveSemigroup[Refined[V, NonNegative]]:
            def plus(x: Refined[V, NonNegative], y: Refined[V, NonNegative]): Refined[V, NonNegative] =
                refineV[NonNegative].unsafeFrom(alg.plus(x.value, y.value))

    inline given ctx_MultiplicativeSemigroup_Refined_Positive[V](using
        alg: MultiplicativeSemigroup[V],
        vld: Validate[V, Positive]
    ): MultiplicativeSemigroup[Refined[V, Positive]] =
        new MultiplicativeSemigroup[Refined[V, Positive]]:
            def times(x: Refined[V, Positive], y: Refined[V, Positive]): Refined[V, Positive] =
                refineV[Positive].unsafeFrom(alg.times(x.value, y.value))

    inline given ctx_MultiplicativeSemigroup_Refined_NonNegative[V](using
        alg: MultiplicativeSemigroup[V],
        vld: Validate[V, NonNegative]
    ): MultiplicativeSemigroup[Refined[V, NonNegative]] =
        new MultiplicativeSemigroup[Refined[V, NonNegative]]:
            def times(x: Refined[V, NonNegative], y: Refined[V, NonNegative]): Refined[V, NonNegative] =
                refineV[NonNegative].unsafeFrom(alg.times(x.value, y.value))
