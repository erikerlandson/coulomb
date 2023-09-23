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

The core API for `coulomb-parser` is
@:api(coulomb.parser.RuntimeUnitParser).
A programmer may define their own parsing implementations against this API.
This package defines a reference implementation named
@:api(coulomb.parser.standard.RuntimeUnitDslParser),
which implements a DSL for representing
@:api(coulomb.RuntimeUnit)
types.
The examples that follow illustrate the DSL syntax and semantics.

A
@:api(coulomb.parser.standard.RuntimeUnitDslParser)
is defined by giving it a list of package or object names,
which contain unit type definitions.
The following declaration creates a DSL parser that can understand
unit definitions for SI units, SI prefixes and information units.

```scala mdoc
val dslparser: RuntimeUnitParser = RuntimeUnitDslParser.of[
    "coulomb.units.si" *:
    "coulomb.units.si.prefixes" *:
    "coulomb.units.info" *:
    EmptyTuple
]
```

Parsing can fail, and so the `parse` method returns an `Either` object.
In the following code, parsing a known unit results in a successful `Right` value.

@:callout(info)
Most of the following examples will display `toString` values to improve readability.
@:@

```scala mdoc
val u1 = dslparser.parse("meter")
u1.toString
```

A parsing failure, such as a unit name that the parser does not know about,
results in a `Left` value containing a parsing error message. 

```scala mdoc
dslparser.parse("nope")
```

The reference DSL will parse any unit name that is composed of
a prefix followed by a unit, into its correct product:

```scala mdoc
dslparser.parse("kilometer").toString

dslparser.parse("megabyte").toString
```

As with static unit types and runtime units,
DSL units can be inductively combined with the operators `*`, `/` and `^`:

```scala mdoc
dslparser.parse("kilometer/second^2").toString
```

Numeric literals can also appear, and are equivalent to literal
types in static unit type expressions:

```scala mdoc
dslparser.parse("(1000 * meter) / (second ^ 2)").toString
```

You can also directly specify a fully qualified unit type name,
by prepending with an `@` symbol.
Note that these fully qualified names may use `name$` instead of `name`,
as with `si$` in the following:

```scala mdoc
dslparser.parse("@coulomb.units.si$.Second")
```
