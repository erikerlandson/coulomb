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

/**
 * Units of time or duration
 */
object time:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    import coulomb.*

    export coulomb.units.si.{ Second, ctx_unit_Second }

    /** A duration of 60 seconds */
    final type Minute
    given ctx_unit_Minute: DerivedUnit[Minute, 60 * Second, "minute", "min"] = DerivedUnit()

    /** A duration of 60 minutes or 3600 seconds */
    final type Hour
    given ctx_unit_Hour: DerivedUnit[Hour, 3600 * Second, "hour", "h"] = DerivedUnit()

    /** A duration of 24 hours */
    final type Day
    given ctx_unit_Day: DerivedUnit[Day, 86400 * Second, "day", "d"] = DerivedUnit()

    /** A duration of 7 days */
    final type Week
    given ctx_unit_Week: DerivedUnit[Week, 604800 * Second, "week", "wk"] = DerivedUnit()

    /**
     * Represents an instant in time measured with respect to the
     * standard unix epoch `00:00:00 UTC January 1, 1970`
     * - https://en.wikipedia.org/wiki/Unix_time
     *
     * @tparam V the value type containing the time quantity
     * @tparam U the unit type, requiring base unit [[coulomb.units.si.Second]]
     */
    final type EpochTime[V, U] = DeltaQuantity[V, U, Second]

    object EpochTime:
        /**
         * Creates an epoch time using a given unit type
         * @example
         * {{{
         * // the instant in time one billion minutes from Jan 1, 1970
         * val instant = EpochTime[Minute](1e9)
         * }}}
         */
        def apply[U](using a: Applier[U]) = a

        /** a shim class for the EpochTime companion `apply` method */
        abstract class Applier[U]:
            def apply[V](v: V): EpochTime[V, U]
        object Applier:
            given [U]: Applier[U] =
                new Applier[U]:
                    def apply[V](v: V): EpochTime[V, U] = v.withDeltaUnit[U, Second]

    extension[V](v: V)
        /**
         * Lift a raw value to an EpochTime instant
         
         * @tparam U the unit type to use, expected to have base unit [[coulomb.units.si.Second]]
         * @return an EpochTime object representing desired instant
         * @example
         * {{{
         * // the instant in time one million hours from Jan 1, 1970
         * val instant = (1e6).withEpochTime[Hour]
         * }}}
         */
        def withEpochTime[U]: EpochTime[V, U] = v.withDeltaUnit[U, Second]

/**
 * Conversion methods between `coulomb` types and `java.time` types
 */
object javatime:
    import java.time.{ Duration, Instant }
    import coulomb.*
    import coulomb.units.time.*

    import conversions.*
    import _root_.scala.Conversion

    extension(duration: Duration)
        /**
         * Convert a `Duration` to a coulomb `Quantity`
         * @tparam V the desired value type
         * @tparam U the desired unit type
         * @return the quantity object equivalent to the Duration
         * @example
         * {{{
         * val d: Duration = ...
         * // convert d to seconds
         * d.toQuantity[Double, Second]
         * }}}
         */
        def toQuantity[V, U](using d2q: DurationQuantity[V, U]): Quantity[V, U] = d2q(duration)

        /**
         * Convert a `Duration` to a coulomb `Quantity` using a truncating conversion
         * @tparam V the desired value type - integral
         * @tparam U the desired unit type
         * @return the quantity object equivalent to the Duration
         * @example
         * {{{
         * val d: Duration = ...
         * // convert to an integral quantity of seconds
         * d.tToQuantity[Long, Second]
         * }}}
         */
        def tToQuantity[V, U](using d2q: TruncatingDurationQuantity[V, U]): Quantity[V, U] = d2q(duration)

    extension[V, U](quantity: Quantity[V, U])
        /**
         * Convert a coulomb Quantity to `java.time.Duration`
         * @return a `Duration` equivalent to the original `Quantity`
         * @example
         * {{{
         * val q: Quantity[Double, Minute] = ...
         * // convert to an equivalent Duration
         * q.toDuration
         * }}}
         */
        def toDuration(using q2d: QuantityDuration[V, U]): Duration = q2d(quantity)

    extension(instant: Instant)
        /**
         * Convert a java.time Instant to an EpochTime value
         * @tparam V the desired value type
         * @tparam U the desired unit type
         * @return equivalent EpochTime value
         * @example
         * {{{
         * val i: Instant = ...
         * // convert i to days from Jan 1, 1970
         * i.toEpochTime[Double, Day]
         * }}}
         */
        def toEpochTime[V, U](using d2q: DurationQuantity[V, U]): EpochTime[V, U] =
            // this is cheating a bit, but it works because both Instant and Epochtime
            // are based on 1970 epoch
            val d = Duration.ofSeconds(instant.getEpochSecond(), instant.getNano())
            d2q(d).value.withEpochTime[U]

        /**
         * Convert a java.time Instant to an EpochTime value using a truncating conversion
         * @tparam V a desired integral value type
         * @tparam U the desired unit type
         * @return equivalent EpochTime value
         * @example
         * {{{
         * val i: Instant = ...
         * // convert i to an integer number of days from Jan 1, 1970
         * i.tToEpochTime[Long, Day]
         * }}}
         */
        def tToEpochTime[V, U](using d2q: TruncatingDurationQuantity[V, U]): EpochTime[V, U] =
            val d = Duration.ofSeconds(instant.getEpochSecond(), instant.getNano())
            d2q(d).value.withEpochTime[U]

    extension[V, U](epochTime: EpochTime[V, U])
        /**
         * Convert an EpochTime value to a java.time Instant
         * @return the equivalent Instant value
         * @example
         * {{{
         * val e: EpochTime[Double, Hour] = ...
         * // convert to an equivalent java.time Instant
         * e.toInstant
         * }}}
         */
        def toInstant(using q2d: QuantityDuration[V, U]): Instant =
            // taking advantage of Instant and EpochTime sharing same 1970 reference
            Instant.EPOCH.plus(q2d(epochTime.value.withUnit[U]))

    /**
     * Conversion typeclasses between `coulomb` types and `java.time` types
     */
    object conversions:
        import coulomb.conversion.*
        import coulomb.rational.Rational

        /**
         * A typeclass for converting a `Duration` to an equivalent `Quantity`
         * @tparam V the quantity value type
         * @tparam U the quantity unit type
         */
        abstract class DurationQuantity[V, U] extends (Duration => Quantity[V, U])

        /**
         * A typeclass for converting a `Quantity` to an equivalent `Duration`
         * @tparam V the quantity value type
         * @tparam U the quantity unit type
         */
        abstract class QuantityDuration[V, U] extends (Quantity[V, U] => Duration)
        // Quantity -> Duration will never truncate

        /**
         * A typeclass for converting a `Duration` to an equivalent `Quantity` involving a truncation
         * to some integral value type
         * @tparam V the quantity value type
         * @tparam U the quantity unit type
         */
        abstract class TruncatingDurationQuantity[V, U] extends (Duration => Quantity[V, U])

        /**
         * exports both explicit and implicit conversion typeclasses
         * @example
         * {{{
         * // import both explicit and implicit conversion typeclasses into scope
         * import coulomb.units.javatime.conversions.all.given
         * }}}
         */
        object all:
            export coulomb.units.javatime.conversions.scala.given
            export coulomb.units.javatime.conversions.explicit.given

        /**
         * defines implicit `scala.Conversion` typeclasses
         * @example
         * {{{
         * // import implicit conversion typeclasses into scope
         * import coulomb.units.javatime.conversions.scala.given
         * }}}
         */
        object scala:
            given ctx_Conversion_QD[V, U](using q2d: QuantityDuration[V, U]):
                    Conversion[Quantity[V, U], Duration] =
                (q: Quantity[V, U]) => q2d(q)

            given ctx_Conversion_DQ[V, U](using d2q: DurationQuantity[V, U]):
                    Conversion[Duration, Quantity[V, U]] =
                (d: Duration) => d2q(d)

            given ctx_Conversion_EI[V, U](using q2d: QuantityDuration[V, U]):
                    Conversion[EpochTime[V, U], Instant] =
                (e: EpochTime[V, U]) => Instant.EPOCH.plus(q2d(e.value.withUnit[U]))

            given ctx_Conversion_IE[V, U](using d2q: DurationQuantity[V, U]):
                    Conversion[Instant, EpochTime[V, U]] =
                (i: Instant) =>
                    val d = Duration.ofSeconds(i.getEpochSecond(), i.getNano())
                    d2q(d).value.withEpochTime[U]

        /**
         * defines typeclasses for explicit conversions
         * @example
         * {{{
         * // import typeclasses for explicit conversions into scope
         * import coulomb.units.javatime.conversions.explicit.given
         * }}}
         */
        object explicit:
            given ctx_DurationQuantity[V, U](using
                uc: UnitConversion[Rational, Second, U],
                vc: ValueConversion[Rational, V]
                    ): DurationQuantity[V, U] =
                (duration: Duration) =>
                    val seconds: Long = duration.getSeconds()
                    val nano: Int = duration.getNano()
                    val qsec: Rational = Rational(seconds) + Rational(nano, 1000000000)
                    vc(uc(qsec)).withUnit[U]

            given ctx_TruncatingDurationQuantity[V, U](using
                uc: UnitConversion[Rational, Second, U],
                vc: TruncatingValueConversion[Rational, V]
                    ): TruncatingDurationQuantity[V, U] =
                (duration: Duration) =>
                    val seconds: Long = duration.getSeconds()
                    val nano: Int = duration.getNano()
                    val qsec: Rational = Rational(seconds) + Rational(nano, 1000000000)
                    vc(uc(qsec)).withUnit[U]

            given ctx_QuantityDuration[V, U](using
                vc: ValueConversion[V, Rational],
                uc: UnitConversion[Rational, U, Second]
                    ): QuantityDuration[V, U] =
                (q: Quantity[V, U]) =>
                    val qsec: Rational = uc(vc(q.value))
                    val secs: Long = qsec.toLong
                    val nano: Int = ((qsec - Rational(secs)) * Rational(1000000000)).toInt
                    Duration.ofSeconds(secs, nano)
