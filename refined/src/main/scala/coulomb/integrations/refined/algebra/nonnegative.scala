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

package coulomb.integrations.refined.algebra

import scala.util.{Try, Success, Failure}

import algebra.ring.*

import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

object nonnegative:
    given g_AdditiveSemigroup_Refined_NonNegative[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, NonNegative]
    ): AdditiveSemigroup[Refined[V, NonNegative]] =
        new infra.ASGR[V, NonNegative]

    given g_MultiplicativeMonoid_Refined_NonNegative[V](using
        alg: MultiplicativeMonoid[V],
        vld: Validate[V, NonNegative]
    ): MultiplicativeMonoid[Refined[V, NonNegative]] =
        new infra.MMR[V, NonNegative]
