# Concepts

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

// unit definitions
import coulomb.units.si.{*, given}
```

## Unit Analysis

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

## Quantity

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

## Unit Expressions

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

## Unit Conversions

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

## Base Units and Derived Units

In unit analysis,
[base units](https://en.wikipedia.org/wiki/Dimensional_analysis#Concrete_numbers_and_base_units)
are the axiomatic units.
They typically express fundamental quantites, for example meters, seconds or kilograms in the
[SI unit system](https://en.wikipedia.org/wiki/International_System_of_Units).

The `coulomb-units` package defines the
[SI base units](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units/si$.html)
but `coulomb` also makes it easy to define your own base units:

```scala mdoc
// a new base unit for spicy heat
object spicy:
    import coulomb.define.*

    final type Scoville
    given unit_Scoville: BaseUnit[Scoville, "scoville", "sco"] = BaseUnit()

import spicy.{*, given}

val jalapeno = 5000.withUnit[Scoville]

val ghost = 1000000.withUnit[Scoville]
```

Other units may be defined as compositions of base units.
These are referred to as derived units, or compound units.

```scala mdoc
object nautical:
    import coulomb.define.*

    export coulomb.units.si.{ Meter, ctx_unit_Meter }
    export coulomb.units.time.{ Hour, ctx_unit_Hour }

    final type NauticalMile
    given unit_NauticalMile: DerivedUnit[NauticalMile, 1852 * Meter, "nmile", "nmi"] =
        DerivedUnit()

    final type Knot
    given unit_Knot: DerivedUnit[Knot, NauticalMile / Hour, "knot", "kt"] =
        DerivedUnit()

import nautical.{*, given}

val dist = (1.0.withUnit[Knot] * 1.0.withUnit[Hour]).toUnit[Meter]
```

`coulomb` permits any type to be treated as a unit.
Types not explicitly defined as units are treated as base units.

```scala mdoc
class Apple

val apples = 1.withUnit[Apple] + 2.withUnit[Apple]

val s = apples.show
```

Allowing arbitrary types to be manipulated as units introduces some interesting programming possibilities,
which are discussed in
[this blog post](http://erikerlandson.github.io/blog/2020/04/26/your-data-type-is-a-unit/).

## Prefix Units

The SI unit system defines standard
[prefixes](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units/si$$prefixes$.html)
such as "kilo" to represent 1000, "micro" to represent 10^-6, etc.
Unit prefixes in `coulomb` take advantage of Scala literal types,
and are defined as `DerivedUnit` for a types that encode Rational numbers.

```scala mdoc
object prefixes:
    import coulomb.define.*

    final type Dozen
    given unit_Dozen: DerivedUnit[Dozen, 12, "dozen", "doz"] = DerivedUnit()

    final type Googol
    given unit_Googol: DerivedUnit[Googol, 10 ^ 100, "googol", "gog"] = DerivedUnit()
```

## Value Types

## Numeric Operations

## Truncating and Non Truncating Operations

## ValueConversion and UnitConversion

## Coulomb Policies

## Time and Temperature

