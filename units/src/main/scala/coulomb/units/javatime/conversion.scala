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

import spire.math.Rational

import coulomb.*
import coulomb.syntax.*
import coulomb.conversion.*
import coulomb.units.time.*
import coulomb.units.time.EpochTime.withEpochTime

/**
 * A typeclass for converting a `Duration` to an equivalent `Quantity`
 * @tparam V
 *   the quantity value type
 * @tparam U
 *   the quantity unit type
 */
abstract class DurationQuantity[V, U] extends (Duration => Quantity[V, U])

object DurationQuantity:
    given g_DurationQuantity[V, U](using
        uc: UnitConversion[Rational, Second, U],
        vc: ValueConversion[Rational, V]
    ): DurationQuantity[V, U] =
        (duration: Duration) =>
            val seconds: Long = duration.getSeconds()
            val nano: Int = duration.getNano()
            val qsec: Rational =
                Rational(seconds) + Rational(nano, 1000000000)
            vc(uc(qsec)).withUnit[U]

/**
 * A typeclass for converting a `Quantity` to an equivalent `Duration`
 * @tparam V
 *   the quantity value type
 * @tparam U
 *   the quantity unit type
 */
abstract class QuantityDuration[V, U] extends (Quantity[V, U] => Duration)

object QuantityDuration:
    given g_QuantityDuration[V, U](using
        vc: ValueConversion[V, Rational],
        uc: UnitConversion[Rational, U, Second]
    ): QuantityDuration[V, U] =
        (q: Quantity[V, U]) =>
            val qsec: Rational = uc(vc(q.value))
            val secs: Long = qsec.toLong
            val nano: Int =
                ((qsec - Rational(secs)) * Rational(1000000000)).toInt
            Duration.ofSeconds(secs, nano)

abstract class InstantEpochTime[V, U] extends (Instant => EpochTime[V, U])

object InstantEpochTime:
    given g_InstantEpochTime[V, U](using
        vc: ValueConversion[Rational, V],
        uc: DeltaUnitConversion[
            Rational,
            coulomb.units.si.Second,
            coulomb.units.si.Second,
            U
        ]
    ): InstantEpochTime[V, U] =
        (instant: Instant) =>
            val duration: Duration =
                Duration.between(Instant.EPOCH, instant)
            val seconds: Long = duration.getSeconds()
            val nano: Int = duration.getNano()
            val qsec: Rational =
                Rational(seconds) + Rational(nano, 1000000000)
            vc(uc(qsec)).withEpochTime[U]

abstract class EpochTimeInstant[V, U] extends (EpochTime[V, U] => Instant)

object EpochTimeInstant:
    given g_EpochTimeInstant[V, U](using
        vc: ValueConversion[V, Rational],
        uc: DeltaUnitConversion[
            Rational,
            coulomb.units.si.Second,
            U,
            coulomb.units.si.Second
        ]
    ): EpochTimeInstant[V, U] =
        (et: EpochTime[V, U]) =>
            val qsec: Rational = uc(vc(et.value))
            val secs: Long = qsec.toLong
            val nano: Int =
                ((qsec - Rational(secs)) * Rational(1000000000)).toInt
            Instant.EPOCH.plus(Duration.ofSeconds(secs, nano))
