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

```scala mdoc
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
```

If a configuration value has _incompatible_ units,
the load will fail with a corresponding error.

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

## Integer Values

In coulomb, conversion operations on integer values are considered to be
[truncating][truncating conversions].
They may lose precision due to integer truncation.
Truncating conversions are generally explicit only,
because this loss of precision is numerically unsafe.

In pureconfig I/O, however, there is no way to explicitly invoke a truncating conversion.
To mitigate this difficulty, the `coulomb-pureconfig` integrations will load without error
if the conversion factor is exactly 1.

@:callout(info)
The safest way to ensure unit conversions will always succeed is to use fractional value types
such as Float or Double.
If desired,
[coulomb-spire](coulomb-spire.md)
provides integrations for fractional value types of higher precision.
@:@

```scala mdoc
// source for a quantity value
val qsrc = ConfigSource.string("""
{
    value: 3
    unit: megabyte
}
""")

// loading integer value types will succeed when type matches the config
qsrc.load[Quantity[Int, Mega * Byte]]

// it will also succeed whenever the conversion coefficient is exactly 1.
qsrc.load[Quantity[Long, Byte * Mega]]

// if the conversion is not exactly 1, load will fail
qsrc.load[Quantity[Int, Kilo * Byte]]
```

## IO Policies

The `coulomb-pureconfig` integrations currently support two options for I/O "policies"
which differ primarily in how one represents unit information.
In the quick-start example above, the DSL-based policy was demonstrated.

The second option is a JSON-based unit representation.
Here, the units are defined using a JSON structured unit expression.
This representation is more verbose,
but it is more amenable to explicitly structured expressions.

```scala mdoc:reset:invisible
// if mdoc had push/pop, I would not have to copy all this
import coulomb.*
import coulomb.syntax.*
import algebra.instances.all.given
import coulomb.ops.algebra.all.given
import coulomb.policy.standard.given
import scala.language.implicitConversions
import coulomb.units.si.prefixes.{*, given}
import coulomb.units.info.{*, given}
import coulomb.units.time.{*, given}
import _root_.pureconfig.{*, given}
import coulomb.pureconfig.*

given given_pureconfig: PureconfigRuntime = PureconfigRuntime.of[
    "coulomb.units.si.prefixes" *:
    "coulomb.units.info" *:
    "coulomb.units.time" *:
    EmptyTuple
]

case class Config(
    duration: Quantity[Double, Second],
    storage: Quantity[Double, Giga * Byte],
    bandwidth: Quantity[Float, (Mega * Bit) / Second]
)

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

```scala mdoc
// use the JSON-based io definitions for RuntimeUnit objects
import coulomb.pureconfig.policy.JSON.given

// this configuration source represents units in structured JSON
val source = ConfigSource.string("""
{
    duration: {value: 10, unit: minute},
    storage: {value: 100, unit: {lhs: mega, op: "*", rhs: byte}},
    bandwidth: {value: 200, unit: {lhs: {lhs: giga, op: "*", rhs: byte}, op: "/", rhs: second}}
}
""")

// this load will succeed, with automatic unit conversions
val conf = source.load[Config]
```
