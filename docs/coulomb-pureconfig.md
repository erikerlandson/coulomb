# coulomb-pureconfig

The `coulomb-pureconfig` package defines `pureconfig` `ConfigReader` and `ConfigWriter` implicit context rules for `Quantity`, `RuntimeQuantity`, and `RuntimeUnit` objects.

## Quick Start

Before you begin, it is recommended to first familiarize yourself with the
[coulomb introduction](README.md)
and
[coulomb concepts](concepts.md).

### packages

```scala
// coulomb pureconfig integrations
libraryDependencies += "com.manyangled" %% "coulomb-pureconfig" % "@VERSION@"

// dependencies
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-runtime" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-parser" % "@VERSION@"

// coulomb predefined units
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
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
any unit type definitions inside that object will be included in the runtime.

```scala mdoc
// define a pureconfig runtime with SI and SI prefix unit definitions
given given_pureconfig: PureconfigRuntime = PureconfigRuntime.of[
    "coulomb.units.si.prefixes" *:
    "coulomb.units.info" *:
    "coulomb.units.time" *:
    EmptyTuple
]
```

For our example, we define a simple configuration class,
using values with units.

```scala mdoc
case class Config(
    duration: Quantity[Double, Second],
    storage: Quantity[Double, Giga * Byte],
    bandwidth: Quantity[Float, (Mega * Bit) / Second]
)
```

We will also define a ConfigReader for our config class,
because pureconfig in scala 3 does not currently support automatic
derivation of ConfigReader for case classes.

```scala mdoc
// defined with 'using' context so that this function defers
// resolution and can operate with multiple pureconfig io policies
given given_ConfigLoader(using
    ConfigReader[Quantity[Double, Second]],
    ConfigReader[Quantity[Double, Giga * Byte]],
    ConfigReader[Quantity[Float, (Mega * Bit) / Second]]
): ConfigReader[Config] =
    ConfigReader.forProduct3("duration", "storage", "bandwidth") {
        (d: Quantity[Double, Second],
        s: Quantity[Double, Giga * Byte],
        b: Quantity[Float, (Mega * Bit) / Second]) =>
        Config(d, s, b)
    }
```

In this example, we will import the DSL-based derivations for
RuntimeUnit objects, and demonstrate that these rules will 
automatically convert compatible units, and load successfully.

If a configuration value has _incompatible_ units,
the load will fail with a corresponding error.
```scala mdoc:nest
// use the DSL-based io definitions for RuntimeUnit objects
import coulomb.pureconfig.policy.DSL.given

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

