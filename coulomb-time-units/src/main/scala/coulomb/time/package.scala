/*
Copyright 2017-2020 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb

import spire.math.Rational

import coulomb.define._
import coulomb.offset.OffsetQuantity
import coulomb.offset.define.OffsetUnit

import coulomb.si.Second

/**
 * Time units: Minute, Hour, Day, Week
 * Also defines EpochTime, which represents a number of
 * time units from unix epoch.
 */
package object time {

  /** A number of time units (seconds, minutes, etc) from unix epoch */
  type EpochTime[N, U] = OffsetQuantity[N, U]

  /** enhances value types with utility methods for `coulomb` */
  implicit class CoulombExtendWithEpochTime[N](val v: N) extends Serializable {
    /** create a new epoch time of type U with numeric value of `this` */
    def withEpochTime[U](implicit ou: OffsetUnit[U, Second]): EpochTime[N, U] =
      new EpochTime[N, U](v)
  }

  trait Minute
  implicit val defineUnitMinute = DerivedUnit[Minute, Second](60, abbv = "min")

  trait Hour
  implicit val defineUnitHour = DerivedUnit[Hour, Second](3600)

  trait Day
  implicit val defineUnitDay = DerivedUnit[Day, Second](86400)

  trait Week
  implicit val defineUnitWeek = DerivedUnit[Week, Day](7, abbv = "wk")
}

/** define types, objects and other entities relating to time units */
package time {
  /** utility methods for epoch time */
  object EpochTime {
    /** generate an EpochTime instance from a raw value */
    def apply[N, U](v: N)(implicit ou: OffsetUnit[U, Second]) =
      new EpochTime[N, U](v)

    /** construct an EpochTime instance from the value in a (non-offset) Quantity */
    def fromQuantity[N, U](q: Quantity[N, U])(implicit
        ou: OffsetUnit[U, Second]): EpochTime[N, U] =
      OffsetQuantity.fromQuantity(q)

    /** construct a non-offset Quantity from the value in an EpochTime instance */
    def toQuantity[N, U](t: EpochTime[N, U]): Quantity[N, U] =
      OffsetQuantity.toQuantity(t)
  }
}

/** defines integrations between coulomb time units and java.time objects */
object javatime {
  import java.time.{ Instant, Duration }
  import coulomb.unitops._
  import time.EpochTime

  /** enhances java.time Duration with additional coulomb integrations */
  implicit class CoulombExtendDuration(val duration: Duration) extends Serializable {
    /** convert a duration to a coulomb Quantity */
    def toQuantity[N, U](implicit toQ: Duration => Quantity[N, U]): Quantity[N, U] = {
      toQ(duration)
    }

    /** add a coulomb Quantity to a java.time Duration */
    def plus[N, U](q: Quantity[N, U])(implicit toD: Quantity[N, U] => Duration): Duration = {
      duration.plus(toD(q))
    }

    /** subtract a coulomb Quantity from a java.time Duration */
    def minus[N, U](q: Quantity[N, U])(implicit toD: Quantity[N, U] => Duration): Duration = {
      duration.minus(toD(q))
    }
  }

  /** enhances coulomb Quantity with some java.time integrations */
  implicit class CoulombExtendQuantity[N, U](val quantity: Quantity[N, U]) extends Serializable {
    /** convert a coulomb Quantity to a java.time Duration */
    def toDuration(implicit
        toD: Quantity[N, U] => Duration): Duration = {
      toD(quantity)
    }

    /** add a Duration to a coulomb Quantity */
    def plus(d: Duration)(implicit
        add: UnitAdd[N, U, N, U],
        toQ: Duration => Quantity[N, U]): Quantity[N, U] = {
      quantity + toQ(d)
    }

    /** subtract a Duration from a coulomb Quantity */
    def minus(d: Duration)(implicit
        sub: UnitSub[N, U, N, U],
        toQ: Duration => Quantity[N, U]): Quantity[N, U] = {
      quantity - toQ(d)
    }
  }

  /** enhances java.time Instant with coulomb integrations */
  implicit class CoulombExtendInstant(val instant: Instant) extends Serializable {
    /** convert an Instant to a coulomb EpochTime */
    def toEpochTime[N, U](implicit toQ: Duration => Quantity[N, U]): EpochTime[N, U] = {
      val d = Duration.ofSeconds(instant.getEpochSecond(), instant.getNano())
      new EpochTime[N, U](toQ(d).value)
    }

    /** add a coulomb quantity to an Instant */
    def plus[N, U](q: Quantity[N, U])(implicit toD: Quantity[N, U] => Duration): Instant = {
      instant.plus(toD(q))
    }

    /** subtract a coulomb quantity from an Instant */
    def minus[N, U](q: Quantity[N, U])(implicit toD: Quantity[N, U] => Duration): Instant = {
      instant.minus(toD(q))
    }
  }

  /** enhances coulomb EpochTime with java.time integrations */
  implicit class CoulombExtendEpochTime[N, U](val t: EpochTime[N, U]) extends Serializable {
    /** convert an EpochTime to an Instant  */
    def toInstant(implicit toD: Quantity[N, U] => Duration): Instant = {
      Instant.EPOCH.plus(EpochTime.toQuantity(t))
    }
    /** add a Duration to an EpochTime */
    def plus(d: Duration)(implicit
        add: UnitAdd[N, U, N, U],
        toQ: Duration => Quantity[N, U]): OffsetQuantity[N, U] = {
      t + toQ(d)
    }
    /** subtract a Duration from an EpochTime */
    def minus(d: Duration)(implicit
        sub: UnitSub[N, U, N, U],
        toQ: Duration => Quantity[N, U]): OffsetQuantity[N, U] = {
      t - toQ(d)
    }
  }

  /** infer a function from java.time Duration to an equivalent Quantity */
  implicit def convertDurationToQuantity[N, U](implicit
      s2u: UnitConverter[Rational, Second, N, U]): Duration => Quantity[N, U] = {
    (duration: Duration) => {
      val seconds = duration.getSeconds()
      val nano = duration.getNano()
      val qsec = if (nano == 0) Rational(seconds) else Rational(seconds) + Rational(nano, 1000000000)
      new Quantity[N, U](s2u.vcnv(qsec))
    }
  }

  /** infer a function from a coulomb Quantity to a java.time Duration */
  implicit def convertQuantityToDuration[N, U](implicit
      u2s: UnitConverter[N, U, Rational, Second]): Quantity[N, U] => Duration = {
    (q: Quantity[N, U]) => {
      val qsec = u2s.vcnv(q.value)
      val secs = qsec.longValue
      val nano = ((qsec - secs) * 1000000000).intValue
      Duration.ofSeconds(secs, nano)
    }
  }
}
