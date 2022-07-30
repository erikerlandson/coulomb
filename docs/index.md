![coulomb splash](/assets/coulomb-splash-800x400.png)

# coulomb: Unit Analysis for Scala

## Quick Start

### documentation
API
[documentation](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/index.html)
for `coulomb` is hosted at javadoc.io

### packages
Include `coulomb` with your Scala project:
```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```

### import statements
Import `coulomb` definitions:
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
```

### examples
Use `coulomb` to do typelevel unit analysis in Scala!
```scala mdoc:nest
// acceleration
val a = 9.8.withUnit[Meter / (Second ^ 2)]

// time or duration
val t = 10.withUnit[Second]

// velocity
val v = a * t

// position
val x = a * (t.pow[2]) / 2
```

Improper unit analysis is a compile-time failure.
```scala mdoc:nest:fail
val time = 1.withUnit[Second]
val dist = 1.withUnit[Meter]

// Incompatible unit operations are a compile-time type error
val fail = time + dist
```

## Code of Conduct

The `coulomb` project supports the
[Scala Code of Conduct](https://typelevel.org/code-of-conduct.html).
All contributors are expected to respect this code.
Any violations of this code of conduct should be reported to
[the author](https://github.com/erikerlandson/).

## Concepts

### Unit Analysis

Unit analysis - aka
[dimensional analysis](https://en.wikipedia.org/wiki/Dimensional_analysis)
- is the practice of tracking units of measure along with numeric computations as an informational aid and consistency check.
For example, if one has a duration `t = 10 seconds` and a distance `d = 100 meters`,
then unit analysis tells us that the value `d/t` has the unit `meters/second`.

Unit analysis performs a very similar role to a
[type system](https://en.wikipedia.org/wiki/Type_system)
in programming languages such as Scala.
Like data types, unit analysis provides us information about what operations may be allowed or disallowed.
Just as
[Scala's type system](https://docs.scala-lang.org/scala3/book/types-introduction.html)
informs us that the expression `7 + false` is not a valid expression:

```scala mdoc:nest:fail
val bad = 7 + false
```

unit analysis informs us that adding `meters + seconds` is not a valid computation.

As such, unit analysis is an excellent use case for representation in programming language type systems.
The `coulomb` library implements unit analysis using Scala's powerful type system features.

### Quantity

In order to do unit analysis, we have to keep track of unit information along with our computations.
The `coulomb` library represents a value paired with a unit expression using the type `Quantity[V, U]`:

```scala mdoc:nest
val time = 10.0.withUnit[Second]
val dist = 100.0.withUnit[Meter]

val v = dist / time
v.show
```

`Quantity[V, U]` has two type arguments: the type `V` represents the value type such as `Double`,
and `U` represents the unit type such as `Meter` or `Meter / Second`.
As the example above shows, when you do operations such as division on a `Quantity`,
`coulomb` automatically computes both the resulting value and its corresponding unit.

### Unit Expressions

In the previous example we saw that a unit type `U` contains a unit expression.
Unit expressions may be a named type such as `Meter` or `Second`,
or more complex unit types can be constructed with the operator types `*`, `/` and `^`:

```scala mdoc:nest
val time = 60.0.withUnit[Second]

val speed = 100.0.withUnit[Meter / Second]

val force = 5.0.withUnit[Kilogram * Meter / (Second ^ 2)]
```

Unit expression types in `coulomb` can be composed to express units with arbitrary complexity.

```scala mdoc:nest
import coulomb.units.mksa.{*, given}

// Electrical resistance expressed with SI base units
val resistance = 1.0.withUnit[(Kilogram * (Meter ^ 2)) / ((Second ^ 3) * (Ampere ^ 2))]

resistance.show

// a shorter but equivalent unit type
resistance.toUnit[Ohm].show
```

### Unit Conversions

In unit analysis, some units are convertible (aka
[commensurable](https://en.wikipedia.org/wiki/Dimensional_analysis)).
The `coulomb` library performs unit conversions, and corresponding checks for convertability or inconvertability,
using
[algorithmic unit analysis](http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/).

For example one may convert a quantity of kilometers to miles, or meter/second to miles/hour,
or cubic meters to liters:
```scala mdoc:nest
import coulomb.units.si.prefixes.{*, given}
import coulomb.units.us.{*, given}
import coulomb.units.accepted.{*, given}
import coulomb.units.time.{*, given}

val distance = 100.0.withUnit[Kilo * Meter]

distance.toUnit[Mile].show

val speed = 10.0.withUnit[Meter / Second]

speed.toUnit[Mile / Hour].show

val volume = 1.0.withUnit[Meter ^ 3]

volume.toUnit[Liter].show
```

Other quantities are *not* convertible (aka incommensurable).
Attempting to convert such quantities in `coulomb` is a compile time type error.

```scala mdoc:fail
// acre-feet is a unit of volume, and so will succeed:
volume.toUnit[Acre * Foot].show

// converting a volume to an area is a type error!
volume.toUnit[Acre].show
```

### Base Units and Derived Units

### Prefix Units

### Value Types

### Numeric Operations

### ValueConversion and UnitConversion

### Coulomb Policies

### Time and Temperature

## Explore

The following resources expand the concepts behind `coulomb` and typelevel unit analysis:

- [API documentation for `coulomb`](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/index.html)
- [github discussions](https://github.com/erikerlandson/coulomb/discussions)
- [Your Data Type is a Unit](http://erikerlandson.github.io/blog/2020/04/26/your-data-type-is-a-unit/)
- [Why Your Data Schema Should Include Units](https://www.youtube.com/watch?v=qrQmB2KFKE8)
- [Algorithmic Unit Analysis](http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/)
- [A Unit Analysis of Linear Regression](http://erikerlandson.github.io/blog/2020/05/06/unit-analysis-for-linear-regression/)

