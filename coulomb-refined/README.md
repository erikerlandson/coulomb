# Integration with refined

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
  "com.manyangled" %% "coulomb" % "0.4.5",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.5.0",
  // refined dependencies
  "com.manyangled" %% "coulomb-refined" % "0.4.5",  
  "eu.timepit" %% "refined" % "0.9.14"
)
```

### How to import

The following examples can be run with these imports:

```scala
import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._

// algebras used by coulomb numeric ops
import spire.std.double._
import spire.std.float._
import spire.std.int._
// alternatively use:
// import spire.std.any._

import coulomb._
import coulomb.refined._

import coulomb.si.{ Kilogram, Meter, Second }
import coulomb.siprefix.Kilo
import coulomb.us.Mile
```

### Examples

#### Applying refined predicates to Quantity values

Coulomb imposes no restrictions on what kind of value is stored in a Quantity.
In fact, a Quantity can be instantiated with a Refined value even without any special integrations:
```scala
scala> val mass = refineMV[NonNegative](1D).withUnit[Kilogram]
mass: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.NonNegative],coulomb.si.Kilogram] = Quantity(1.0)
```

The `coulomb-refined` package defines two additional methods for creating refined Quantity values:
`toRefined` and `withRefinedUnit`.
As the following example shows, `toRefined` applies a refined predicate to a `Quantity`, and
`withRefinedUnit` applies both a predicate and a unit to a raw value:
```scala
scala> val meters = 1D.withUnit[Meter].toRefined[NonNegative]
meters: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.NonNegative],coulomb.si.Meter] = Quantity(1.0)

scala> val seconds = 1D.withRefinedUnit[NonNegative, Second]
seconds: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.NonNegative],coulomb.si.Second] = Quantity(1.0)
```

#### Quantity Operations with Refined values

The `coulomb-refined` package allows all the standard `Quantity` operations to work with refined values:

```scala
scala> val kilometers = 1D.withRefinedUnit[NonNegative, Kilo %* Meter]
kilometers: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.NonNegative],coulomb.siprefix.Kilo %* coulomb.si.Meter] = Quantity(1.0)

scala> val add = 1D.withRefinedUnit[Positive, Mile] + kilometers
add: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.Positive],coulomb.us.Mile] = Quantity(1.621371192237334)

scala> add.show
res12: String = 1.621371192237334 mi

scala> val sub = 1D.withRefinedUnit[LessEqual[1D], Mile] - kilometers
sub: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.LessEqual[1.0]],coulomb.us.Mile] = Quantity(0.37862880776266605)

scala> sub.show
res13: String = 0.37862880776266605 mi

scala> val velocity = 1D.withRefinedUnit[Positive, (Kilo %* Meter) %/ Second]
velocity: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.Positive],coulomb.siprefix.Kilo %* coulomb.si.Meter %/ coulomb.si.Second] = Quantity(1.0)

scala> val time = 60.withRefinedUnit[Greater[30], Second]
time: coulomb.Quantity[eu.timepit.refined.api.Refined[Int,eu.timepit.refined.numeric.Greater[30]],coulomb.si.Second] = Quantity(60)

scala> val dist = velocity * time
dist: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.Greater[shapeless._0]],coulomb.siprefix.Kilo %* coulomb.si.Meter] = Quantity(60.0)

scala> dist.show
res14: String = 60.0 km

scala> val acc = velocity / time
acc: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.Greater[shapeless._0]],coulomb.siprefix.Kilo %* coulomb.si.Meter %/ (coulomb.si.Second %^ Int(2))] = Quantity(0.016666666666666666)

scala> acc.show
res15: String = 0.016666666666666666 km/s^2

scala> 1f.withRefinedUnit[Positive, Mile] > 1D.withRefinedUnit[Positive, Kilo %* Meter]
res16: Boolean = true

scala> val d = 1D.withRefinedUnit[Positive, Mile].to[Refined[Float, NonNegative], Kilo %* Meter]
d: coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.NonNegative],coulomb.siprefix.Kilo %* coulomb.si.Meter] = Quantity(1.609344)

scala> d.show
res17: String = 1.609344 km
```

Additionally, operations that involve converting from a Refined value to a raw value are supported:

```scala
scala> 1D.withUnit[Mile] + 1.withRefinedUnit[Greater[-100], Kilo %* Meter]
res19: coulomb.Quantity[Double,coulomb.us.Mile] = Quantity(1.621371192237334)

scala> 1.withRefinedUnit[Greater[-100], Kilo %* Meter].to[Float, Mile]
res20: coulomb.Quantity[Float,coulomb.us.Mile] = Quantity(0.6213712)
```

Operations involving conversion from a raw value to a Refined value are also supported,
but considered "unsound" (See the following section on
[soundness](#sound-and-unsound-operations)
for a discussion about operations that are sound or unsound, and why)

#### Sound and unsound operations

##### Sound operations

The `refined` library includes a notion of _soundness_.
Some conversions between `Refined` predicates can be guaranteed by the type system to never fail at runtime;
Such conversions are said to be "sound," in the sense of
[type soundness](https://cs.stackexchange.com/questions/82155/is-there-a-difference-between-type-safety-and-type-soundness).

The `coulomb-refined` integration library also supports this concept.
For example, the following operation is understood by the library to be sound, because adding a provably
non-negative value _cannot_ make the LHS value smaller and violate the `GreaterEqual` predicate.
```scala
scala> val q = 1D.withRefinedUnit[GreaterEqual[-10D], Kilo %* Meter] + 500D.withRefinedUnit[Greater[100D], Meter]
q: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.GreaterEqual[-10.0]],coulomb.siprefix.Kilo %* coulomb.si.Meter] = Quantity(1.5)

scala> q.show
res0: String = 1.5 km
```

As mentioned in the
[previous section](#quantity-operations-with-refined-values),
Operations that convert refined values into raw values cannot fail,
and are always sound.

##### Unsound operations

Conversely, an operation may be _unsound_.
The following operation would succeed at runtime, but subtracting a positive value _could_
violate the `GreaterEqual` predicate, and so it does not compile:
```scala
scala> val q = 1D.withRefinedUnit[GreaterEqual[-10D], Kilo %* Meter] - 500D.withRefinedUnit[Greater[100D], Meter]
                                                                     ^
       error: could not find implicit value for parameter us ...
```

Operations that involve converting a raw value into a refined one can always potentially fail,
and so they are considered unsound:
```scala
scala> 1.withUnit[Kilo %* Meter].to[Refined[Float, Positive], Mile]
                                   ^
       error: could not find implicit value for parameter uc ...
```

The programmer can configure `coulomb-refined` to ignore soundness during compilation.
By disabling soundness checking, the example above will compile, and succeed during runtime:
```scala
scala> import coulomb.refined.policy.unsoundRefinedConversions._
import coulomb.refined.policy.unsoundRefinedConversions._

scala> val q = 1D.withRefinedUnit[GreaterEqual[-10D], Kilo %* Meter] - 500D.withRefinedUnit[Greater[100D], Meter]
q: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.GreaterEqual[-10.0]],coulomb.siprefix.Kilo %* coulomb.si.Meter] = Quantity(0.5)
```

When soundness checking is disabled, conversions may fail at run-time:
```scala
scala> val q = 1D.withRefinedUnit[GreaterEqual[-10D], Kilo %* Meter] - 20000D.withRefinedUnit[Greater[100D], Meter]
coulomb.refined.package$CoulombRefinedException: Predicate (-19.0 < -10.0) did not fail.
```

##### `toRefined` and `withRefinedUnit`

Note that the `toRefined` and `withRefinedUnit` methods described in the previous section are not sound,
by the above definitions.
Applying a Refined predicate to a raw value may or may not succeed.

However, in order to allow refined quantity values to be created, these methods may always be used,
regardless of whether soundness checking is enabled or disabled.

##### Soundness checking is not (quite) perfect

Type soundness checking in `coulomb-refined` works in practically all cases,
but there are a few particular scenarios where it can fail.

Numeric underflow can cause runtime failures even on type-sound expressions.
The following expressions are all sound from a type checking perspective;
Any positive number squared is also positive. However, floating point underflow
causes this multiplication to fail when the product rounds down to zero:
```scala
scala> val smol = (1e-300).withRefinedUnit[Positive, Meter]
smol: coulomb.Quantity[eu.timepit.refined.api.Refined[Double,eu.timepit.refined.numeric.Positive],coulomb.si.Meter] = Quantity(1.0E-300)

scala> smol * smol
coulomb.refined.package$CoulombRefinedException: Predicate failed: (0.0 > 0.0).
```

Another source of soundness leaks is integer quantization effects.
In this example, the conversion to `Int` causes the result to round to zero,
and creates a runtime failure:
```scala
scala> 100D.withRefinedUnit[Positive, Meter].to[Refined[Int, Positive], Kilo %* Meter]
coulomb.refined.package$CoulombRefinedException: Predicate failed: (0 > 0).
```

Type soundness leaks primarily (always?) occur at open-interval boundaries.
In other words, predicates involving `>` or `<`.
Predicates involving `>=` or `<=`, such as `NonNegative` or `GreaterEqual`,
are not as vulnerable to type soundness leaks.
