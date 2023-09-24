# coulomb-runtime

The [coulomb-runtime] package implements
@:api(coulomb.RuntimeQuantity) and @:api(coulomb.RuntimeUnit).
Its primary use case at the time of this documentation is to support runtime I/O,
for example the [coulomb-pureconfig] package.

## Quick Start

### packages

```scala
libraryDependencies += "com.manyangled" %% "coulomb-runtime" % "@VERSION@"

// dependencies
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"

// coulomb predefined units
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```

### import

```scala mdoc
// fundamental coulomb types and methods
// these include RuntimeUnit and RuntimeQuantity
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions

// unit definitions
import coulomb.units.si.{*, given}
import coulomb.units.si.prefixes.{*, given}
import coulomb.units.info.{*, given}
import coulomb.units.time.{*, given}

// runtime definitions
import coulomb.conversion.runtimes.mapping.MappingCoefficientRuntime
```

### examples

@:api(coulomb.RuntimeUnit) is the core data structure of the [coulomb-runtime] package.
It is a parallel runtime implementation of the standard static unit types and analysis
defined in [coulomb-core].

The `RuntimeUnit.of` method makes it easy to create RuntimeUnit values from static unit types.
Additionally, you can apply the standard unit type operators `*`, `/` and `^` to build up unit expressions.

```scala mdoc
// create RuntimeUnit values from static unit types
val k = RuntimeUnit.of[Kilo]
val d = RuntimeUnit.of[Meter]
val t = RuntimeUnit.of[Second]

// Build up unit expression from other expressions
val kps = (k * d) / t

// values can be displayed with toString for readability
kps.toString
```

The `RuntimeUnit.of` method can be used with static unit types of arbitrary form.

```scala mdoc
val kps2 = RuntimeUnit.of[Kilo * Meter / Second]
kps2.toString
```

A @:api(coulomb.RuntimeQuantity) is a value paired with a RuntimeUnit,
and is the runtime analog of @:api(coulomb.Quantity$).
The following example demonstrates some ways to create RuntimeQuantity objects.

```scala mdoc
// a RuntimeQuantity is a value paired with a RuntimeUnit
val rq = RuntimeQuantity(1f, kps)

// declare a RuntimeQuantity with a given RuntimeUnit
1f.withRuntimeUnit(kps).toString

// an equivalent RuntimeQuantity based on a static unit type
1f.withRuntimeUnit[(Kilo * Meter) / Second].toString
```

It is also possible to convert from @:api(coulomb.RuntimeQuantity) to @:api(coulomb.Quantity).
This is accomplished using a @:api(coulomb.CoefficientRuntime) in context.

As this example shows, you can list package names, which will import any unit types
into the CoefficientRuntime.

@:callout(info)
The @:api(coulomb.CoefficientRuntime) bridges the gap between runtime unit expresions and
static unit types.
It is what allows loading unit aware configurations, for example in [coulomb-pureconfig].
@:@

```scala mdoc
// declare a coefficient runtime that knows about SI units and prefixes
given given_CRT: CoefficientRuntime = MappingCoefficientRuntime.of[
    "coulomb.units.si" *:
    "coulomb.units.si.prefixes" *:
    EmptyTuple
]
```

With a @:api(coulomb.CoefficientRuntime), we can convert from runtime quantities to
static typed quantities.
We cannot know at compile time if these conversions will succeed,
so these operations return an @:api(scala.util.Either) value.

```scala mdoc
// reconstruct the equivalent Quantity
rq.toQuantity[Float, Kilo * Meter / Second]

// valid conversions of value types or unit types will also succeed
rq.toQuantity[Double, Meter / Second]

// attempting to convert to incompatible units will fail
rq.toQuantity[Float, Second]
```

