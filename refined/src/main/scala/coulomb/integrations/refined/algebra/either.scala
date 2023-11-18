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

object either:
    given g_AdditiveSemigroup_Refined_Either[V, P](using
        alg: AdditiveSemigroup[Refined[V, P]]
    ): AdditiveSemigroup[Either[String, Refined[V, P]]] =
        new infra.ASGRE[V, P]

    given g_MultiplicativeGroup_Refined_Either[V, P](using
        alg: MultiplicativeGroup[Refined[V, P]]
    ): MultiplicativeGroup[Either[String, Refined[V, P]]] =
        new infra.MGRE[V, P]

    given g_MultiplicativeMonoid_Refined_Either[V, P](using
        alg: MultiplicativeMonoid[Refined[V, P]]
    ): MultiplicativeMonoid[Either[String, Refined[V, P]]] =
        new infra.MMRE[V, P]
