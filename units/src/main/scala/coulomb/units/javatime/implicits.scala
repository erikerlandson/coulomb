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

package coulomb.units.javatime.conversion

import java.time.{Duration, Instant}

import coulomb.*
import coulomb.units.time.*

/**
 * defines implicit `scala.Conversion` typeclasses
 * @example
 *   {{{
 * // import implicit conversion typeclasses into scope
 * import coulomb.units.javatime.conversion.implicits.given
 *   }}}
 */
object implicits:
    given g_Conversion_QD[V, U](using
        q2d: QuantityDuration[V, U]
    ): Conversion[Quantity[V, U], Duration] =
        (q: Quantity[V, U]) => q2d(q)

    given g_Conversion_DQ[V, U](using
        d2q: DurationQuantity[V, U]
    ): Conversion[Duration, Quantity[V, U]] =
        (d: Duration) => d2q(d)

    given g_Conversion_EI[V, U](using
        e2i: EpochTimeInstant[V, U]
    ): Conversion[EpochTime[V, U], Instant] =
        (e: EpochTime[V, U]) => e2i(e)

    given g_Conversion_IE[V, U](using
        i2e: InstantEpochTime[V, U]
    ): Conversion[Instant, EpochTime[V, U]] =
        (i: Instant) => i2e(i)
