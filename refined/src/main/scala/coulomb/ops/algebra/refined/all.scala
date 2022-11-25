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
    given ctx_AdditiveSemigroup_Refined_Positive[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, Positive]
    ): AdditiveSemigroup[Refined[V, Positive]] =
        new infra.ASGR[V, Positive]

    given ctx_AdditiveSemigroup_Refined_NonNegative[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, NonNegative]
    ): AdditiveSemigroup[Refined[V, NonNegative]] =
        new infra.ASGR[V, NonNegative]

    given ctx_MultiplicativeGroup_Refined_Positive[V](using
        alg: MultiplicativeGroup[V],
        vld: Validate[V, Positive]
    ): MultiplicativeGroup[Refined[V, Positive]] =
        new infra.MGR[V, Positive]

    given ctx_MultiplicativeSemigroup_Refined_Positive[V](using
        alg: MultiplicativeSemigroup[V],
        vld: Validate[V, Positive]
    ): MultiplicativeSemigroup[Refined[V, Positive]] =
        new infra.MSGR[V, Positive]

    given ctx_MultiplicativeSemigroup_Refined_NonNegative[V](using
        alg: MultiplicativeSemigroup[V],
        vld: Validate[V, NonNegative]
    ): MultiplicativeSemigroup[Refined[V, NonNegative]] =
        new infra.MSGR[V, NonNegative]

    object infra:
        class ASGR[V, P](using alg: AdditiveSemigroup[V], vld: Validate[V, P]) extends
            AdditiveSemigroup[Refined[V, P]]:
            def plus(x: Refined[V, P], y: Refined[V, P]): Refined[V, P] =
                refineV[P].unsafeFrom(alg.plus(x.value, y.value))

        class MSGR[V, P](using alg: MultiplicativeSemigroup[V], vld: Validate[V, P]) extends
            MultiplicativeSemigroup[Refined[V, P]]:
            def times(x: Refined[V, P], y: Refined[V, P]): Refined[V, P] =
                refineV[P].unsafeFrom(alg.times(x.value, y.value))

        class MGR[V, P](using alg: MultiplicativeGroup[V], vld: Validate[V, P]) extends
            MSGR[V, P] with MultiplicativeGroup[Refined[V, P]]:
            def div(x: Refined[V, P], y: Refined[V, P]): Refined[V, P] =
                refineV[P].unsafeFrom(alg.div(x.value, y.value))
            def one: Refined[V, P] = refineV[P].unsafeFrom(alg.one)
