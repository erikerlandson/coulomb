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

package coulomb.testing.refined

import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

extension [V](v: V)
    // a workaround for refineMV not being available in scala3
    // https://github.com/fthomas/refined/issues/932
    def withRP[P](using Validate[V, P]): Refined[V, P] =
        refineV[P].unsafeFrom(v)
