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

package coulomb.units

object time:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    import coulomb.*

    export coulomb.units.si.{ Second, ctx_unit_Second }

    final type Minute
    given ctx_unit_Minute: DerivedUnit[Minute, 60 * Second, "minute", "min"] with {}

    final type Hour
    given ctx_unit_Hour: DerivedUnit[Hour, 60 * Minute, "hour", "h"] with {}

    final type Day
    given ctx_unit_Day: DerivedUnit[Day, 24 * Hour, "day", "d"] with {}

    final type Week
    given ctx_unit_Week: DerivedUnit[Week, 7 * Day, "week", "wk"] with {}

    final type EpochTime[V, U] = DeltaQuantity[V, U, Second]

    object EpochTime:
        def apply[U](using a: Applier[U]) = a

        abstract class Applier[U]:
            def apply[V](v: V): EpochTime[V, U]
        object Applier:
            given [U]: Applier[U] =
                new Applier[U]:
                    def apply[V](v: V): EpochTime[V, U] = v.withDeltaUnit[U, Second]

    extension[V](v: V)
        def withEpochTime[U]: EpochTime[V, U] = v.withDeltaUnit[U, Second]

object javatime:
    import java.time.{ Duration, Instant }
    import coulomb.*
    import coulomb.units.time.*

    import conversions.*
    import _root_.scala.Conversion

    extension(duration: Duration)
        def toQuantity[V, U](using d2q: DurationQuantity[V, U]): Quantity[V, U] = d2q(duration)
        def tToQuantity[V, U](using d2q: TruncatingDurationQuantity[V, U]): Quantity[V, U] = d2q(duration)

    extension[V, U](quantity: Quantity[V, U])
        def toDuration(using q2d: QuantityDuration[V, U]): Duration = q2d(quantity)

    extension(instant: Instant)
        def toEpochTime[V, U](using d2q: DurationQuantity[V, U]): EpochTime[V, U] =
            // this is cheating a bit, but it works because both Instant and Epochtime
            // are based on 1970 epoch
            val d = Duration.ofSeconds(instant.getEpochSecond(), instant.getNano())
            d2q(d).value.withEpochTime[U]

        def tToEpochTime[V, U](using d2q: TruncatingDurationQuantity[V, U]): EpochTime[V, U] =
            val d = Duration.ofSeconds(instant.getEpochSecond(), instant.getNano())
            d2q(d).value.withEpochTime[U]

    extension[V, U](epochTime: EpochTime[V, U])
        def toInstant(using q2d: QuantityDuration[V, U]): Instant =
            // taking advantage of Instant and EpochTime sharing same 1970 reference
            Instant.EPOCH.plus(q2d(epochTime.value.withUnit[U]))

    object conversions:
        import coulomb.conversion.*
        import coulomb.rational.Rational

        abstract class DurationQuantity[V, U] extends (Duration => Quantity[V, U])
        abstract class QuantityDuration[V, U] extends (Quantity[V, U] => Duration)
        // Quantity -> Duration will never truncate
        abstract class TruncatingDurationQuantity[V, U] extends (Duration => Quantity[V, U])

        object all:
            export coulomb.units.javatime.conversions.scala.given
            export coulomb.units.javatime.conversions.explicit.given

        object scala:
            given ctx_Conversion_QD[V, U](using q2d: QuantityDuration[V, U]):
                    Conversion[Quantity[V, U], Duration] =
                new Conversion[Quantity[V, U], Duration]:
                    def apply(q: Quantity[V, U]): Duration = q2d(q)

            given ctx_Conversion_DQ[V, U](using d2q: DurationQuantity[V, U]):
                    Conversion[Duration, Quantity[V, U]] =
                new Conversion[Duration, Quantity[V, U]]:
                    def apply(d: Duration): Quantity[V, U] = d2q(d)

            given ctx_Conversion_EI[V, U](using q2d: QuantityDuration[V, U]):
                    Conversion[EpochTime[V, U], Instant] =
                new Conversion[EpochTime[V, U], Instant]:
                    def apply(e: EpochTime[V, U]): Instant =
                        Instant.EPOCH.plus(q2d(e.value.withUnit[U]))

            given ctx_Conversion_IE[V, U](using d2q: DurationQuantity[V, U]):
                    Conversion[Instant, EpochTime[V, U]] =
                new Conversion[Instant, EpochTime[V, U]]:
                    def apply(i: Instant): EpochTime[V, U] =
                        val d = Duration.ofSeconds(i.getEpochSecond(), i.getNano())
                        d2q(d).value.withEpochTime[U]

        object explicit:
            given ctx_DurationQuantity[V, U](using
                uc: UnitConversion[Rational, Second, U],
                vc: ValueConversion[Rational, V]
                    ): DurationQuantity[V, U] =
                new DurationQuantity[V, U]:
                    def apply(duration: Duration): Quantity[V, U] =
                          val seconds: Long = duration.getSeconds()
                          val nano: Int = duration.getNano()
                          val qsec: Rational = Rational(seconds) + Rational(nano, 1000000000)
                          vc(uc(qsec)).withUnit[U]

            given ctx_TruncatingDurationQuantity[V, U](using
                uc: UnitConversion[Rational, Second, U],
                vc: TruncatingValueConversion[Rational, V]
                    ): TruncatingDurationQuantity[V, U] =
                new TruncatingDurationQuantity[V, U]:
                    def apply(duration: Duration): Quantity[V, U] =
                          val seconds: Long = duration.getSeconds()
                          val nano: Int = duration.getNano()
                          val qsec: Rational = Rational(seconds) + Rational(nano, 1000000000)
                          vc(uc(qsec)).withUnit[U]

            given ctx_QuantityDuration[V, U](using
                vc: ValueConversion[V, Rational],
                uc: UnitConversion[Rational, U, Second]
                    ): QuantityDuration[V, U] =
                new QuantityDuration[V, U]:
                    def apply(q: Quantity[V, U]): Duration =
                        val qsec: Rational = uc(vc(q.value))
                        val secs: Long = qsec.toLong
                        val nano: Int = ((qsec - Rational(secs)) * Rational(1000000000)).toInt
                        Duration.ofSeconds(secs, nano)
