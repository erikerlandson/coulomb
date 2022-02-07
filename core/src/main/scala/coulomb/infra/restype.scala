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

package coulomb.infra

import coulomb.*

abstract class MulRU[UL, UR]:
    type RU
object MulRU:
    transparent inline given[UL, UR]: MulRU[UL, UR] = ${ meta.mulRU[UL, UR] }

abstract class DivRU[UL, UR]:
    type RU
object DivRU:
    transparent inline given[UL, UR]: DivRU[UL, UR] = ${ meta.divRU[UL, UR] }

abstract class PowRU[U, P]:
    type RU
object PowRU:
    transparent inline given[U, P]: PowRU[U, P] = ${ meta.powRU[U, P] }
