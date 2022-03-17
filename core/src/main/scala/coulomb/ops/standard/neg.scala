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

import algebra.ring.AdditiveGroup

import coulomb.{Quantity, withUnit}
import coulomb.ops.Neg

inline given ctx_quantity_neg[V, U](using alg: AdditiveGroup[V]): Neg[V, U] =
    new Neg[V, U]:
        def apply(q: Quantity[V, U]): Quantity[V, U] =
            alg.negate(q.value).withUnit[U]

