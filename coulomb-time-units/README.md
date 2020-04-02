# Time Units, EpochTime and java.time

To learn more about coulomb in general refer to the
[coulomb tutorial](../README.md#tutorial)

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.4.0",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.4.4",
  // coulomb time units, EpochTime, and java.time integrations
  "com.manyangled" %% "coulomb-time-units" % "0.4.0"
)
```

### Examples

#### Time Units and `EpochTime`

```scala
scala> import coulomb._, coulomb.si._, coulomb.time._, spire.std.any._
import coulomb._
import coulomb.si._
import coulomb.time._
import spire.std.any._

// an EpochTime represents absolute time, in units since midnight 1970
scala> val t1 = 61.withEpochTime[Second]
t1: coulomb.time.EpochTime[Int,coulomb.si.Second] = OffsetQuantity(61)

scala> val t2 = 1.withEpochTime[Minute]
t2: coulomb.time.EpochTime[Int,coulomb.time.Minute] = OffsetQuantity(1)

// T - T => Q: The difference between two absolute EpochTime values is a Quantity
scala> t1 - t2
res4: coulomb.Quantity[Int,coulomb.si.Second] = Quantity(1)

scala> (t1 - t2).show
res5: String = 1 s

// T + Q => T: Add a Quantity to an EpochTime to get a new absolute EpochTime
scala> t2 + 1.withUnit[Hour]
res6: coulomb.offset.OffsetQuantity[Int,coulomb.time.Minute] = OffsetQuantity(61)

// T - Q => T
scala> t1 - 1.withUnit[Minute]
res7: coulomb.offset.OffsetQuantity[Int,coulomb.si.Second] = OffsetQuantity(1)

// absolute EpochTime values can be compared like Quantity
scala> t1 < t2
res8: Boolean = false

scala> t1 > t2
res9: Boolean = true
```

#### Integrating `coulomb` with `java.time.Duration`

```scala
scala> import coulomb._, coulomb.si._, coulomb.siprefix._, coulomb.time._, coulomb.javatime._, java.time._, spire.std.any._
import coulomb._
import coulomb.si._
import coulomb.siprefix._
import coulomb.time._
import coulomb.javatime._
import java.time._
import spire.std.any._

scala> val dur = Duration.ofSeconds(90, 0)
dur: java.time.Duration = PT1M30S

scala> dur.plus(1f.withUnit[Kilo %* Second]).toQuantity[Float, Second].show
res11: String = 1090.0 s

scala> dur.toQuantity[Float, Minute].show
res12: String = 1.5 min

scala> dur.plus(1f.withUnit[Kilo %* Second]).toQuantity[Float, Second].show
res13: String = 1090.0 s

scala> dur.minus(1f.withUnit[Minute]).toQuantity[Float, Second].show
res14: String = 30.0 s

scala> val q = 1D.withUnit[Hour] + 777.1D.withUnit[Nano %* Second]
q: coulomb.Quantity[Double,coulomb.time.Hour] = Quantity(1.000000000215861)

scala> val dur = q.toDuration
dur: java.time.Duration = PT1H0.000000777S

scala> dur.getSeconds()
res15: Long = 3600

scala> dur.getNano()
res16: Int = 777

scala> 1f.withUnit[Second].plus(Duration.ofSeconds(10, 777000000)).show
res17: String = 11.777 s

scala> 1f.withUnit[Minute].minus(Duration.ofSeconds(10, 777000000)).show
res18: String = 0.8203833 min
```

#### Integrating `coulomb` with `java.time.Instant`

```scala
scala> import coulomb._, coulomb.si._, coulomb.time._, coulomb.javatime._, java.time._, spire.std.any._
import coulomb._
import coulomb.si._
import coulomb.time._
import coulomb.javatime._
import java.time._
import spire.std.any._

scala> val ins = Instant.parse("1969-07-20T00:00:00Z")
ins: java.time.Instant = 1969-07-20T00:00:00Z

scala> ins.toEpochTime[Double, Day]
res1: coulomb.time.EpochTime[Double,coulomb.time.Day] = OffsetQuantity(-165.0)

scala> ins.toEpochTime[Double, Day].show
res2: String = -165.0 d

scala> ins.plus(1D.withUnit[Week])
res3: java.time.Instant = 1969-07-27T00:00:00Z

scala> ins.minus(1D.withUnit[Week])
res4: java.time.Instant = 1969-07-13T00:00:00Z

scala> val et = (-165L).withEpochTime[Day]
et: coulomb.time.EpochTime[Long,coulomb.time.Day] = OffsetQuantity(-165)

scala> et.toInstant
res5: java.time.Instant = 1969-07-20T00:00:00Z

scala> et.plus(Duration.ofSeconds(86400, 0))
res6: coulomb.offset.OffsetQuantity[Long,coulomb.time.Day] = OffsetQuantity(-164)

scala> res6.toInstant
res7: java.time.Instant = 1969-07-21T00:00:00Z

scala> et.minus(Duration.ofSeconds(86400, 0)).toInstant
res8: java.time.Instant = 1969-07-19T00:00:00Z
```
