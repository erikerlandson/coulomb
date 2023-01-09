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

object value:
    given ctx_VC_Refined_Positive[VF, VT](using
        vc: ValueConversion[VF, VT],
        vld: Validate[VT, Positive]
    ): ValueConversion[Refined[VF, Positive], Refined[VT, Positive]] =
        (v: Refined[VF, Positive]) => refineV[Positive].unsafeFrom(vc(v.value))

    given ctx_VC_Refined_NonNegative[VF, VT](using
        vc: ValueConversion[VF, VT],
        vld: Validate[VT, NonNegative]
    ): ValueConversion[Refined[VF, NonNegative], Refined[VT, NonNegative]] =
        (v: Refined[VF, NonNegative]) =>
            refineV[NonNegative].unsafeFrom(vc(v.value))

    given ctx_TVC_Refined_Positive[VF, VT](using
        vc: TruncatingValueConversion[VF, VT],
        vld: Validate[VT, Positive]
    ): TruncatingValueConversion[Refined[VF, Positive], Refined[VT, Positive]] =
        (v: Refined[VF, Positive]) => refineV[Positive].unsafeFrom(vc(v.value))

    given ctx_TVC_Refined_NonNegative[VF, VT](using
        vc: TruncatingValueConversion[VF, VT],
        vld: Validate[VT, NonNegative]
    ): TruncatingValueConversion[Refined[VF, NonNegative], Refined[
        VT,
        NonNegative
    ]] =
        (v: Refined[VF, NonNegative]) =>
            refineV[NonNegative].unsafeFrom(vc(v.value))

    given ctx_VC_Refined_Either[VF, VT, P](using
        vc: ValueConversion[Refined[VF, P], Refined[VT, P]]
    ): ValueConversion[Either[String, Refined[VF, P]], Either[
        String,
        Refined[VT, P]
    ]] =
        (v: Either[String, Refined[VF, P]]) =>
            Try(v.map(vc)) match
                case Success(x) => x
                case Failure(e) => Left(e.getMessage)

    given ctx_TVC_Refined_Either[VF, VT, P](using
        vc: TruncatingValueConversion[Refined[VF, P], Refined[VT, P]]
    ): TruncatingValueConversion[Either[String, Refined[VF, P]], Either[
        String,
        Refined[VT, P]
    ]] =
        (v: Either[String, Refined[VF, P]]) =>
            Try(v.map(vc)) match
                case Success(x) => x
                case Failure(e) => Left(e.getMessage)
