# coulomb-core

This page describes the fundamental `coulomb` concepts, implemented in `coulomb-core`.

## Quick Start

### packages

Include `coulomb-core` with your Scala project:

```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"

// coulomb's predefined units package
// (optional if you are defining your own units)
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```

### import

```scala mdoc
// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given

// SI unit definitions
import coulomb.units.si.{*, given}
```

## Unit Analysis

Unit analysis - aka
[dimensional analysis](https://en.wikipedia.org/wiki/Dimensional_analysis)
- is the practice of tracking units of measure along with numeric computations as an informational aid and consistency check.
For example, if one has a duration `t = 10 seconds` and a distance `d = 100 meters`,
then unit analysis tells us that the value `d/t` has the unit `meters/second`.

Unit analysis performs a role very similar to a
[type system](https://en.wikipedia.org/wiki/Type_system)
in programming languages such as Scala.
Like data types, unit analysis provides us information about what operations may be allowed or disallowed.
Just as
[Scala's type system](https://docs.scala-lang.org/scala3/book/types-introduction.html)
informs us that the expression `7 + false` is not a valid expression:

```scala mdoc:fail
val bad = 7 + false
```

unit analysis informs us that adding `meters + seconds` is not a valid computation.

As such, unit analysis is an excellent use case for representation in programming language type systems.
The `coulomb` library implements unit analysis using Scala's powerful type system features.

## Quantity

In order to do unit analysis, we have to keep track of unit information along with our computations.
The `coulomb` library represents a value paired with a unit expression using the type `Quantity[V, U]`:

```scala mdoc
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

    export coulomb.units.si.{ Meter, unit_Meter }
    export coulomb.units.time.{ Hour, unit_Hour }

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

@:callout(info)
Allowing arbitrary types to be manipulated as units introduces some interesting programming possibilities,
which are discussed in
[this blog post](http://erikerlandson.github.io/blog/2020/04/26/your-data-type-is-a-unit/).
@:@

### Prefix Units

The standard SI unit system
[prefixes](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units/si$$prefixes$.html)
such as "kilo" to represent 1000, "micro" to represent 10^-6, etc, are provided by the `coulomb-units` package.

```scala mdoc:nest
import coulomb.units.si.prefixes.{*, given}

val dist = 5.0.withUnit[Kilo * Meter]

dist.toUnit[Meter].show
```

The standard
[binary prefixes](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/units/info$$prefixes$.html)
which are frequently used in computing are also provided.

```scala mdoc:nest
import coulomb.units.info.{*, given}
import coulomb.units.info.prefixes.{*, given}

val memory = 1.0.withUnit[Mebi * Byte]

memory.toUnit[Byte].show
```

Unit prefixes in `coulomb` take advantage of Scala literal types,
and are defined as `DerivedUnit` for types that encode Rational numbers.
The numeric literal types `Int`, `Long`, `Float` and `Double` can all be
combined with the operator types `*`, `/` and `^` to express numeric constants.
Numeric values are processed at compile-time as arbitrary precision rationals,
and so you can easily express values with high precision and magnitudes.

```scala mdoc
object prefixes:
    import coulomb.define.*

    final type Dozen
    given unit_Dozen: DerivedUnit[Dozen, 12, "dozen", "doz"] = DerivedUnit()

    final type Googol
    given unit_Googol: DerivedUnit[Googol, 10 ^ 100, "googol", "gog"] = DerivedUnit()

    final type Percent
    given unit_Percent: DerivedUnit[Percent, 1 / 100, "percent", "%"] = DerivedUnit()

    final type Degree
    given unit_Degree: DerivedUnit[Degree, 3.14159265 / 180, "degree", "deg"] = DerivedUnit()
```

## Value Types

Each `coulomb` quantity `Quantity[V, U]` pairs a value type `V` with a unit type `U`.
Value types are often numeric, however `coulomb` places no restriction on value types.
We have already seen that there are also no restrictions on unit type,
and so the following are perfectly legitimate:

```scala mdoc
case class Wrapper[T](t: T)

val w = Wrapper(42f).withUnit[Meter]

w.show

val w2 = Wrapper(37D).withUnit[Vector[Int]]

w2.show
```

### Numeric Value Types

The coulomb core libraries provide out-of-box support for the following numeric types:

| Value Type | Defined In |
| --- | --- |
| Int | Scala |
| Long | Scala |
| Float | Scala |
| Double | Scala |
| BigInt | Scala |
| BigDecimal | Scala |
| Rational | Spire |
| Algebraic | Spire |
| Real | Spire |

### Value Types and Algebras

Although value and unit types are arbitrary for a `Quantity`, there is no free lunch.
Operations on quantity objects will only work if they are defined.
The following example will not compile because we have not defined what it means to add `Wrapper` objects:

```scala mdoc:fail
// addition has not been defined for Wrapper types
val wsum = w + w
```

The compiler error above indicates that it was unable to find an algebra that tells coulomb's
standard predefined rules how to add `Wrapper` objects.
Providing such a definition allows our example to compile.

```scala mdoc
object wrapperalgebra:
    import algebra.ring.AdditiveSemigroup
    given add_Wrapper[V](using vadd: AdditiveSemigroup[V]): AdditiveSemigroup[Wrapper[V]] =
        new AdditiveSemigroup[Wrapper[V]]:
            def plus(x: Wrapper[V], y: Wrapper[V]): Wrapper[V] =
                Wrapper(vadd.plus(x.t, y.t))

// import Wrapper algebras into scope
import wrapperalgebra.given

// adding Wrapper objects works now that we have defined an algebra
val wsum = w + w
```

## Numeric Operations

`coulomb` supports the following numeric operations for any value type that defines the required algebras:

```scala mdoc
val v = 2.0.withUnit[Meter]

// negation
-v

// addition
v + v

// subtraction
v - v

// multiplication
v * v

// division
v / v

// power
v.pow[3]

// comparison operators
v <= v

v > v

v === v

v =!= v
```

### Truncating Division

`coulomb` also supports truncating division via the `tquot` operator,
typically used for integral types.
The standard typelevel `cats` algebras do not define truncating division,
however you can import these typeclasses from `spire`.

```scala mdoc
// import spire algebra typeclasses to include TruncatedDivision
import spire.std.any.given

// truncating integer division
5.withUnit[Meter] `tquot` 2.withUnit[Second]
```

### Operations and Conversions

#### Unit Conversions

`coulomb` will implicitly convert units to align them for operations,
such as addition, subtraction or comparisons.
Units are always converted to the units of the left-hand operand.
In the following examples, you can see that kilometers are converted to meters before operating.

```scala mdoc
1000.0.withUnit[Meter] + 1.0.withUnit[Kilo * Meter]

1000.0.withUnit[Meter] - 0.5.withUnit[Kilo * Meter]

1000.0.withUnit[Meter] === 1.0.withUnit[Kilo * Meter]

1000.0.withUnit[Meter] > 0.5.withUnit[Kilo * Meter]
```

@:callout(info)
`coulomb` only defines implicit unit conversions on fractional value types,
such as `Double`, `Float`, `BigDecimal` and Spire types like `Rational` or `Real`.
Unit conversions on integral types such as `Int` or `Long` are not supported
due to the numeric instability caused by integer value truncations.
@:@

#### Value Conversions

`coulomb` does not perform implicit value conversions, as illustrated in the following:

```scala mdoc:fail
// coulomb will not convert double to float implicitly
1f.withUnit[Meter] + 1d.withUnit[Meter]
```

However, you can always convert values using the `toValue` method.

```scala mdoc
// use toValue method to align value types
1f.withUnit[Meter] + 1d.withUnit[Meter].toValue[Float]
```

@:callout(info)
Releases of `coulomb` prior to 0.9 supported implicit value conversions.
Implicit value conversions were removed for a variety of reasons.
Changing value types involves subtle tradeoffs in numeric precision that are best left to the developer.
Additionally, implicit value conversions interact with Scala's native value conversions 
(e.g. promoting Float to Double)
in ways that are difficult to untangle.
Removing implicit value conversions has allowed substantial simplifications to `coulomb`'s
system of typeclasses.
@:@

## Value and Unit Conversions

In `coulomb`, a `Quantity[V, U]` may experience two kinds of conversion:
converting value type `V` to a new value type `V2`, or converting unit `U` to a new unit `U2`:

```scala mdoc
val q = 10d.withUnit[Meter]

// convert value type
q.toValue[Float]

// convert unit type
q.toUnit[Yard]
```

Value conversions are successful whenever the corresponding `ValueConversion[VF, VT]` context is in scope,
and similarly unit conversions are successful whenever the necessary `UnitConversion[V, UF, UT]` is in scope.

As you can see from the signature `UnitConversion[V, UF, UT]`,
any unit conversion is with respect to a particular value type.
This is because the best way of converting from unit `UF` to `UT` will
depend on the specific value type being converted.
You can look at examples of unit conversions defined in `coulomb-core`
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/conversion/standard/unit$.html).

@:callout(info)
`coulomb` only predefines `UnitConversion` typeclasses for fractional types
such as `Double`, `Float`, `BigDecimal`, etc.
Unit conversions on non fractional integral types are not numerically safe,
due to the presence of integer truncations.
@:@

### Implicit Conversions

In Scala 3, implicit conversions are represented by `scala.Conversion[F, T]`,
which you can read more about
[here](https://scala-lang.org/api/3.x/scala/Conversion.html).

The `coulomb-core` library
[pre-defines](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/conversion/standard/scala$.html)
implicit unit conversions,
based on `UnitConversion` context in scope.

@:callout(info)
The `coulomb` libraries themselves do not make use of Scala implicit conversions.
You only need to import them if you want to use them in your own code.
@:@

Implicit quantity conversions can be used in typical Scala scenarios:

```scala mdoc
import scala.language.implicitConversions
import coulomb.conversion.implicits.given

// implicitly convert cubic meters to liters
val iconv: Quantity[Double, Liter] = 1.0.withUnit[Meter ^ 3]

// implicitly convert "raw" values to unitless Quantity
val uq: Quantity[Int, 1] = 100
```

### Defining Conversions
The `coulomb-core` libraries define value and unit conversions for a wide variety of
popular numeric types, however you can also easily define your own.

```scala mdoc
object wrapperconv:
    import coulomb.conversion.*

    given vconv_Wrapper[VF, VT](using vcv: ValueConversion[VF, VT]): ValueConversion[Wrapper[VF], Wrapper[VT]] =
        new ValueConversion[Wrapper[VF], Wrapper[VT]]:
            def apply(w: Wrapper[VF]): Wrapper[VT] = Wrapper[VT](vcv(w.t))

    given uconv_Wrapper[V, UF, UT](using ucv: UnitConversion[V, UF, UT]): UnitConversion[Wrapper[V], UF, UT] =
        new UnitConversion[Wrapper[V], UF, UT]:
            def apply(w: Wrapper[V]): Wrapper[V] = Wrapper[V](ucv(w.t))

import wrapperconv.given

val wq = Wrapper(1d).withUnit[Minute]

wq.toUnit[Second]

wq.toValue[Wrapper[Float]]
```

## Temperature and Time

The `coulomb-units` library defines units for temperature and time.
When working with temperatures,
one must distinguish between absolute temperatures, and units of degrees.
Similarly, one must distinguish between absolute moments in time
(aka timestamps, or instants)
and units of duration.
Consider the following example:

```scala mdoc
// import temperature units
import coulomb.units.temperature.{*, given}
// import extensions for temp and time
import coulomb.units.syntax.*

// Here are two absolute temperatures
val cels1 = 10d.withTemperature[Celsius]
val cels2 = 20d.withTemperature[Celsius]

// subtracting temperatures yields a Quantity of degrees:
val dcels = cels2 - cels1

// you can add or subtract a quantity from a temperature, and get a new temperature
cels2 + dcels
cels2 - dcels
```

As we saw in the example above, subtracting two absolute temperatures yields a Quantity of degrees.
However, temperature scales are "anchored" at an absolute reference point,
which makes _adding_ absolute temperatures undefined.
For example `10C + 20C` does not equal `30C`.
Hence, it's an error to add two absolute temperatures.

```scala mdoc:fail
// adding absolute temperatures is a type error!
cels1 + cels2
```

We can see that `coulomb` time units support an algebra that behaves the same as temperatures above:

```scala mdoc
import coulomb.units.time.{*, given}

// Two absolute timestamps, relative to Jan 1 1970 (Unix epoch)
val date1 = 50d.withEpochTime[365 * Day]
val date2 = 51d.withEpochTime[365 * Day]

// subtracting two absolute times yields a Quantity of time units
val dyear = date2 - date1

// one can add or subtract a Quantity from an absolute time
date2 + dyear
date2 - dyear
```

```scala mdoc:fail
// adding absolute time values is an error
date1 + date2
```

### Delta Units

As these examples show, time and temperature units both involve a distinction between
"absolute" or "positional" values, and standard "quantities", and the algebras
of these absolute values are the same under the hood.

In `coulomb-core` these kinds of absolute value are represented by a type `DeltaUnit[V, U, B]`,
where `V` and `U` are standard value and unit types, and `B` represents a base unit.
In the case of temperatures, the base unit is `Kelvin`, and in the case of time it is `Second`.

@:callout(info)
`DeltaUnit[V, U, B]` is so named because the only algebraic operation one can do with
a pair of Delta Units is to subtract them.
@:@

The `DeltaUnit[V, U, B]` type supports the following algebra:
```
DeltaUnit - DeltaUnit => Quantity
DeltaUnit + Quantity => DeltaUnit
DeltaUnit - Quantity => DeltaUnit
```
