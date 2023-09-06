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
import coulomb.units.si.{*, given}
import coulomb.units.si.prefixes.{*, given}

// pureconfig defs
import _root_.pureconfig.{*, given}

// import coulomb-pureconfig defs
import coulomb.pureconfig.*
// use the DSL-based io definitions for RuntimeUnit objects
import coulomb.pureconfig.policy.DSL.given
```

### examples
Define a pureconfig runtime to enable io of coulomb objects.
You can list either package and object names, or type definitions.
When you provide a package or object name, as in the example below,
any type definitions inside that object will be included in the runtime.

```scala mdoc
// define a pureconfig runtime with SI and SI prefix unit definitions
given given_pureconfig: PureconfigRuntime = PureconfigRuntime.of[
    "coulomb.units.si" *:
    "coulomb.units.si.prefixes" *:
    EmptyTuple
]
```

```scala mdoc
// a simple configuration with one quantity
val conf = ConfigSource.string("""{value: 3, unit: "kilometer / second^2"}""")

// load the value, using a different (but compatible) unit type
val q = conf.load[Quantity[Float, Meter / (Second ^ 2)]]

// attempting to load as an incompatible unit will fail
val f = conf.load[Quantity[Float, Meter / Second]]
```

