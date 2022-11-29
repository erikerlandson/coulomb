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

package coulomb.conversion.refined

import scala.util.{Try, Success, Failure}

import coulomb.conversion.* 

import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

object unit:
    given ctx_UC_Refined_Positive[V, UF, UT](using
        uc: UnitConversion[V, UF, UT],
        vld: Validate[V, Positive]
    ): UnitConversion[Refined[V, Positive], UF, UT] =
        (v: Refined[V, Positive]) =>
            refineV[Positive].unsafeFrom(uc(v.value))

    given ctx_UC_Refined_NonNegative[V, UF, UT](using
        uc: UnitConversion[V, UF, UT],
        vld: Validate[V, NonNegative]
    ): UnitConversion[Refined[V, NonNegative], UF, UT] =
        (v: Refined[V, NonNegative]) =>
            refineV[NonNegative].unsafeFrom(uc(v.value))

    given ctx_TUC_Refined_Positive[V, UF, UT](using
        uc: TruncatingUnitConversion[V, UF, UT],
        vld: Validate[V, Positive]
    ): TruncatingUnitConversion[Refined[V, Positive], UF, UT] =
        (v: Refined[V, Positive]) =>
            refineV[Positive].unsafeFrom(uc(v.value))

    given ctx_TUC_Refined_NonNegative[V, UF, UT](using
        uc: TruncatingUnitConversion[V, UF, UT],
        vld: Validate[V, NonNegative]
    ): TruncatingUnitConversion[Refined[V, NonNegative], UF, UT] =
        (v: Refined[V, NonNegative]) =>
            refineV[NonNegative].unsafeFrom(uc(v.value))

    given ctx_DUC_Refined_Positive[V, B, UF, UT](using
        uc: DeltaUnitConversion[V, B, UF, UT],
        vld: Validate[V, Positive]
    ): DeltaUnitConversion[Refined[V, Positive], B, UF, UT] =
        (v: Refined[V, Positive]) =>
            refineV[Positive].unsafeFrom(uc(v.value))

    given ctx_DUC_Refined_NonNegative[V, B, UF, UT](using
        uc: DeltaUnitConversion[V, B, UF, UT],
        vld: Validate[V, NonNegative]
    ): DeltaUnitConversion[Refined[V, NonNegative], B, UF, UT] =
        (v: Refined[V, NonNegative]) =>
            refineV[NonNegative].unsafeFrom(uc(v.value))

    given ctx_TDUC_Refined_Positive[V, B, UF, UT](using
        uc: TruncatingDeltaUnitConversion[V, B, UF, UT],
        vld: Validate[V, Positive]
    ): TruncatingDeltaUnitConversion[Refined[V, Positive], B, UF, UT] =
        (v: Refined[V, Positive]) =>
            refineV[Positive].unsafeFrom(uc(v.value))

    given ctx_TDUC_Refined_NonNegative[V, B, UF, UT](using
        uc: TruncatingDeltaUnitConversion[V, B, UF, UT],
        vld: Validate[V, NonNegative]
    ): TruncatingDeltaUnitConversion[Refined[V, NonNegative], B, UF, UT] =
        (v: Refined[V, NonNegative]) =>
            refineV[NonNegative].unsafeFrom(uc(v.value))

    given ctx_UC_Refined_Either[V, P, UF, UT](using
        uc: UnitConversion[Refined[V, P], UF, UT]
    ): UnitConversion[Either[String, Refined[V, P]], UF, UT] =
        (v: Either[String, Refined[V, P]]) =>
            Try(v.map(uc)) match
                case Success(x) => x
                case Failure(e) => Left(e.getMessage)
