# coulomb-parser

The `coulomb-parser` package defines a unit expression parsing API and a reference unit expression DSL,
which is used by runtime I/O integrations such as
[coulomb-pureconfig](coulomb-pureconfig.md)

## Quick Start

Before you begin, it is recommended to first familiarize yourself with the
[coulomb-runtime](coulomb-runtime.md)
documentation.

### packages

```scala
libraryDependencies += "com.manyangled" %% "coulomb-parser" % "@VERSION@"

// dependencies
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-runtime" % "@VERSION@"

// coulomb predefined units
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```

### import

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
import coulomb.units.info.{*, given}
import coulomb.units.time.{*, given}

// parsing definitions
import coulomb.parser.RuntimeUnitParser
import coulomb.parser.standard.RuntimeUnitDslParser
```

### examples

test link:
@:api(coulomb.Quantity$)

```scala mdoc
val dslparser: RuntimeUnitParser = RuntimeUnitDslParser.of[
    "coulomb.units.si" *:
    "coulomb.units.si.prefixes" *:
    "coulomb.units.info" *:
    EmptyTuple
]
```

```scala mdoc
val u1 = dslparser.parse("meter")
u1.toString
```

```scala mdoc
dslparser.parse("nope")
```

```scala mdoc
dslparser.parse("kilometer").toString

dslparser.parse("megabyte").toString
```

```scala mdoc
dslparser.parse("kilometer/second^2").toString
```

```scala mdoc
dslparser.parse("(1000 * meter) / (second ^ 2)").toString
```

```scala mdoc
dslparser.parse("@coulomb.units.si$.Second")
```


