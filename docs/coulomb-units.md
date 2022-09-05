# coulomb-units

The `coulomb-units` package defines several groups of commonly used units.

| import package | description |
| --- | --- |
| `coulomb.units.si` | Standard International base units |
| `coulomb.units.si.prefixes` | Standard International prefix units |
| `coulomb.units.mks` | Meter/Kilogram/Second (MKS) units |
| `coulomb.units.mksa` | Meter/Kilogram/Second/Ampere (MKSA) units |
| `coulomb.units.accepted` | Accepted metric units (Liter, Gram, Millibar, etc) |
| `coulomb.units.us` | Traditional non-metric units (United States definitions) |
| `coulomb.units.info` | Information units (Bit, Byte, Nat) |
| `coulomb.units.info.prefixes` | Binary prefixes (Kibi, Mebi, Tebi, etc) |
| `coulomb.units.time` | Time units (Second, Minute, Hour, etc) |
| `coulomb.units.temperature` | Temperature units (Kelvin, Celsius, Fahrenheit) |
| `coulomb.units.constants` | Physical constants (Planck, Boltzmann, molar gas constant, etc) |
| `coulomb.units.javatime` | Conversions between `coulomb` and `java.time` values |

## Quick Start

```scala mdoc:invisible
// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions
```

### documentation

API documentation for `coulomb-units` can be viewed
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units.html).

### packages

Include `coulomb-units` with your Scala project:

```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```

### import

In order to use a unit definition, both the unit type and its corresponding context variable
(aka "given") must be in scope.
In Scala 3 one does this with the following idiom:

```scala mdoc
// import both types and context variables ("givens")
import coulomb.units.mksa.{*, given}
import coulomb.units.us.{*, given}
import coulomb.units.accepted.{*, given}
```

### examples

```scala mdoc
// Working with units of power
val watts = 1d.withUnit[Watt]

// watts are equivalent to volt-amps
watts.toUnit[Volt * Ampere]

// convert watts to horsepower
watts.toUnit[Horsepower]
```

```scala mdoc
// Working with units of volume
val volume = 1d.withUnit[Meter ^ 3]

// common metric unit of volume
volume.toUnit[Liter]

// United States Gallons
volume.toUnit[Gallon]

// Traditional hydrological units
volume.toUnit[Acre * Foot]
```

## physical constants

@:callout(info)
The full catalog of physical constants defined in `coulomb-units` is listed
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units/constants$.html).
@:@

Physical constants are closely related to units,
in that any physical constant can also be thought of (and used) as a unit in and of itself.

For example, the speed of light (in a vacuum) is a physical constant whose value is
close to 300 million meters per second, but we also use this constant as a unit,
for example physicists may say that an object traveling at 30 million m/s has a velocity of "0.1c"

The `coulomb-units` package provides a selection of physical constants,
and allows the user to work with these as either units or constant values:

```scala mdoc
import coulomb.units.constants.{*, given}

// the "constant" function gives a physical constant as a Quantity object with a particular value type
val c = constant[Double, SpeedOfLight]

// use the speed of light as a unit to express a velocity as a fraction of light speed
val v = 30e6.withUnit[Meter / Second]
v.toUnit[SpeedOfLight]
```

## java.time conversions

@:callout(info)
You can browse the full list of available `java.time` conversions
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units/javatime$.html)
@:@

In `coulomb-units`, standard `Quantity` of time units such as `Second`, `Minute` or `Hour`
corresponds to `Duration` in `java.time`.
Similarly, absolute time instants represented by `EpochTime` correspond to `Instant` in `java.time`.

The `coulomb-units` package implements both explicit and implicit conversions between
`coulomb` and `java.time` values, some of which are shown here:

```scala mdoc
import java.time.{ Duration, Instant }
import coulomb.units.time.{*, given}

// explicit conversion methods
import coulomb.units.javatime.*
// implicit and explicit conversions
import coulomb.units.javatime.conversions.all.given

val dur = Duration.ofSeconds(70, 400000000)

// explicit conversion from java.time duration to a coulomb quantity
dur.toQuantity[Double, Minute]

// corresponding implicit conversion
val dq: Quantity[Double, Minute] = dur

// convert back to java.time
dq.toDuration

// the day of the first human moon landing
val ins = Instant.parse("1969-07-20T00:00:00Z")

// days relative to standard Unix epoch
ins.toEpochTime[Double, Day]

// corresponding implicit conversion
val et: EpochTime[Double, Day] = ins

// convert back to java.time
et.toInstant
```
