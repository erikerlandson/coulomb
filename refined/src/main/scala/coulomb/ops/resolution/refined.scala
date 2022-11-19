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

package coulomb.ops.resolution

import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

import coulomb.ops.ValueResolution

import coulomb.policy.priority.*

object refined:
    transparent inline given ctx_VR_Refined_Positive[VL, VR, Positive](using Prio0)(using
        vres: ValueResolution[VL, VR]
    ): ValueResolution[Refined[VL, Positive], Refined[VR, Positive]] =
        new ValueResolution.NC[Refined[VL, Positive], Refined[VR, Positive], Refined[vres.VO, Positive]]

    transparent inline given ctx_VR_Refined_NonNegative[VL, VR, NonNegative](using Prio1)(using
        vres: ValueResolution[VL, VR]
    ): ValueResolution[Refined[VL, NonNegative], Refined[VR, NonNegative]] =
        new ValueResolution.NC[Refined[VL, NonNegative], Refined[VR, NonNegative], Refined[vres.VO, NonNegative]]
        
