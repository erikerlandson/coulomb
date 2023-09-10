# coulomb-pureconfig

The `coulomb-pureconfig` package defines `pureconfig` `ConfigReader` and `ConfigWriter` implicit context rules for `Quantity`, `RuntimeQuantity`, and `RuntimeUnit` objects.

## Quick Start

Before you begin, it is recommended to first familiarize yourself with the
[coulomb introduction](README.md)
and
[coulomb concepts](concepts.md).

### packages

```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-pureconfig" % "@VERSION@"
```

### import

The following example imports basic `coulomb` and `coulomb-pureconfig` definitions 

```scala mdoc
// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions

// unit definitions
import coulomb.units.si.prefixes.{*, given}
import coulomb.units.info.{*, given}
import coulomb.units.time.{*, given}

// pureconfig defs
import _root_.pureconfig.{*, given}

// import basic coulomb-pureconfig defs
import coulomb.pureconfig.*
```

### examples
Define a pureconfig runtime to enable io of coulomb objects.
You can list either package and object names, or type definitions.
When you provide a package or object name, as in the example below,
any type definitions inside that object will be included in the runtime.

```scala mdoc
// define a pureconfig runtime with SI and SI prefix unit definitions
given given_pureconfig: PureconfigRuntime = PureconfigRuntime.of[
    "coulomb.units.si.prefixes" *:
    "coulomb.units.info" *:
    "coulomb.units.time" *:
    EmptyTuple
]
```

```scala mdoc
case class Config(
    duration: Quantity[Double, Second],
    storage: Quantity[Double, Giga * Byte],
    bandwidth: Quantity[Float, (Mega * Bit) / Second]
)

// use the DSL-based io definitions for RuntimeUnit objects
import coulomb.pureconfig.policy.DSL.given

// pureconfig case class io is not yet automatically defined in scala 3
given given_ConfigLoader: ConfigReader[Config] =
    ConfigReader.forProduct3("duration", "storage", "bandwidth") {
        (d: Quantity[Double, Second],
        s: Quantity[Double, Giga * Byte],
        b: Quantity[Float, (Mega * Bit) / Second]) =>
        Config(d, s, b)
    }
```

```scala mdoc
// define a configuration source
// this source uses units that are different than the Config type
// definition, but they are convertable
val source = ConfigSource.string("""
{
    duration: {value: 10, unit: minute},
    storage: {value: 100, unit: megabyte},
    bandwidth: {value: 200, unit: "gigabyte / second"}
}
""")

// this load will succeed, with automatic unit conversions
val conf = source.load[Config]
```

```scala mdoc
// this config has the wrong unit for bandwidth
val bad = ConfigSource.string("""
{
    duration: {value: 10, unit: minute},
    storage: {value: 100, unit: megabyte},
    bandwidth: {value: 200, unit: "gigabyte"}
}
""")

// this load will fail because bandwidth units are incompatible
val fail = bad.load[Config]
```

