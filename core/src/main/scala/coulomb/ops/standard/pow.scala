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

import coulomb.{`*`, `/`, `^`}
import coulomb.ops.{Pow, SimplifiedUnit, ValueResolution}
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.policy.AllowImplicitConversions

transparent inline given ctx_pow[V, U, E](using
    alg: CanPow[V, E],
    su: SimplifiedUnit[U ^ E]
        ): Pow[V, U, E] =
    new Pow[V, U, E]:
        type VO = V
        type UO = su.UO
        def apply(v: V): VO = alg.pow(v)

