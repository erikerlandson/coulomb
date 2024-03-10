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

import spire.math.*
import spire.algebra.*
import coulomb.*

object utils:
    extension(v: Rational)
        def fpow(e: Rational): Rational =
            if ((e.denominator == 1) && (e.numerator.isValidInt))
                v.pow(e.numerator.toInt)
            else
                Fractional[Rational].fpow(v, e)

    extension(v: SafeLong)
        def isValidInt: Boolean =
            if (v.isValidLong) then
                val vl = v.toLong
                (vl >= Int.MinValue) && (vl <= Int.MaxValue)
            else
                false
