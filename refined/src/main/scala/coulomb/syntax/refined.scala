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

package coulomb.syntax.refined

import coulomb.*
import coulomb.syntax.*

import eu.timepit.refined.*
import eu.timepit.refined.api.*

/**
 * Lift a raw value into a unit quantity with a Refined value type
 * @tparam P
 *   the Refined predicate type (e.g. Positive, NonNegative)
 * @tparam U
 *   the desired unit type
 * @param v
 *   the raw value to lift
 * @return
 *   a unit quantity whose value is refined by P
 *   {{{
 * val dist = refineVU[NonNegative, Meter](1.0)
 *   }}}
 */
def refineVU[P, U]: infra.ApplyRefineVU[P, U] =
    new infra.ApplyRefineVU[P, U]

extension [V](v: V)
    def withRefinedUnit[P, U](using
        Validate[V, P]
    ): Quantity[Either[String, Refined[V, P]], U] =
        refineV[P](v).withUnit[U]
        
object infra:
    class ApplyRefineVU[P, U]:
        def apply[V](v: V)(using
            Validate[V, P]
        ): Quantity[Either[String, Refined[V, P]], U] =
            refineV[P](v).withUnit[U]
