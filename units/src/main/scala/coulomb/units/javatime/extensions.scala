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

package coulomb.units.javatime

import java.time.{Duration, Instant}
import coulomb.*
import coulomb.syntax.*
import coulomb.units.time.*

import coulomb.units.javatime.conversion.*

extension (duration: Duration)
    /**
     * Convert a `Duration` to a coulomb `Quantity`
     * @tparam V
     *   the desired value type
     * @tparam U
     *   the desired unit type
     * @return
     *   the quantity object equivalent to the Duration
     * @example
     *   {{{
     * val d: Duration = ...
     * // convert d to seconds
     * d.toQuantity[Double, Second]
     *   }}}
     */
    def toQuantity[V, U](using
        d2q: DurationQuantity[V, U]
    ): Quantity[V, U] =
        d2q(duration)

extension [V, U](quantity: Quantity[V, U])
    /**
     * Convert a coulomb Quantity to `java.time.Duration`
     * @return
     *   a `Duration` equivalent to the original `Quantity`
     * @example
     *   {{{
     * val q: Quantity[Double, Minute] = ...
     * // convert to an equivalent Duration
     * q.toDuration
     *   }}}
     */
    def toDuration(using q2d: QuantityDuration[V, U]): Duration =
        q2d(quantity)

extension (instant: Instant)
    /**
     * Convert a java.time Instant to an EpochTime value
     * @tparam V
     *   the desired value type
     * @tparam U
     *   the desired unit type
     * @return
     *   equivalent EpochTime value
     * @example
     *   {{{
     * val i: Instant = ...
     * // convert i to days from Jan 1, 1970
     * i.toEpochTime[Double, Day]
     *   }}}
     */
    def toEpochTime[V, U](using
        i2e: InstantEpochTime[V, U]
    ): EpochTime[V, U] =
        i2e(instant)

extension [V, U](epochTime: EpochTime[V, U])
    /**
     * Convert an EpochTime value to a java.time Instant
     * @return
     *   the equivalent Instant value
     * @example
     *   {{{
     * val e: EpochTime[Double, Hour] = ...
     * // convert to an equivalent java.time Instant
     * e.toInstant
     *   }}}
     */
    def toInstant(using e2i: EpochTimeInstant[V, U]): Instant =
        e2i(epochTime)
