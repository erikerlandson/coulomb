# coulomb Concepts

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

// comparisons

v <= v

v > v

v === v

v =!= v
```

## Truncating Operations

Some operations involving integral types such as `Int`, `Long`, or `BigInt`,
are considered "truncating" - they lose the fractional component of the result.
In coulomb these are distinguished with specific "truncating" operators:

```scala mdoc
// fractional values (Double, Float, BigDecimal, Rational, etc)
val fractional = 10.5.withUnit[Meter]

// truncating value conversions (fractional -> integral)
val integral = fractional.tToValue[Int]

// truncating unit conversions
integral.tToUnit[Yard]

// truncating division
integral `tquot` 3

// truncating power
integral.tpow[1/2]
```

Non-truncating operations are defined in cases where the result will not discard fractional components:
```scala mdoc
// "normal" (aka truncating) operations work when fractional component of results are preserved
fractional / 3
```

Non-truncating operations are undefined on types that would cause truncation.
```scala mdoc:fail
// standard division is undefined for cases that would truncate
integral / 3
```

## Value and Unit Conversions

In `coulomb`, a `Quantity[V, U]` may experience conversions along two possible axes:
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

As you can see from the signature `UnitConversion[V, UF, UT]`, any unit conversion is with respect to a particular value type.
This is because the best way of converting from unit `UF` to `UT` will depend on the specific value type being converted.
You can look at examples of unit conversions defined in `coulomb-core`
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/conversion/standard/unit$.html).

### truncating conversions

As we saw in
[previous sections][Truncating Operations],
some operations on coulomb quantities may result in "truncation" - the loss of fractional parts of values.
As with operations, truncating conversions are represented by distinct conversions
`TruncatingValueConversion[VF, VT]` and `TruncatingUnitConversion[V, UF, UT]`.

```scala mdoc
// a truncating value conversion (fractional -> integral)
val qi = q.tToValue[Int]

// a truncating unit conversion (on an integral type)
qi.tToUnit[Yard]
```

### implicit conversions

In Scala 3, implicit conversions are represented by `scala.Conversion[F, T]`,
which you can read more about
[here](https://scala-lang.org/api/3.x/scala/Conversion.html).

The `coulomb-core` library
[pre-defines](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/conversion/standard/scala$.html)
such implicit conversions,
based on ValueConversion and UnitConversion context in scope.
By convention, `coulomb` performs value conversions first, then unit conversions.

Implicit quantity conversions can be used in typical Scala scenarios:

```scala mdoc
// implicitly convert double to float, and then cubic meters to liters
val iconv: Quantity[Float, Liter] = 1.0.withUnit[Meter ^ 3]
```

However, numeric operators in `coulomb` may also make use of these implicit conversions:
```scala mdoc
val q1 = 1d.withUnit[Second]
val q2 = 1.withUnit[Minute]

// in this operation, q2's integer value is implicitly converted to double,
// and then minutes are converted to seconds, and added to q1:
q1 + q2
```

### defining conversions

The `coulomb-core` and `coulomb-spire` libraries define value and unit conversions for a wide variety of
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

## Value Promotion and Resolution

We saw in our
[earlier example][implicit conversions]
that `coulomb` can perform implicit value and unit conversions when doing numeric operations.
The high level logic (implemented in chained context rules) is:

1. "Resolve" left and right value types (`VL` and `VR`) into a final output type `VO`
1. Apply implicit conversion `Quantity[VL, UL]` -> `Quantity[VO, UL]`
1. Apply implicit conversion `Quantity[VR, UR]` -> `Quantity[VO, UL]`
1. Perform the relevant algebraic operation, in value space `VO`
1. Return the resulting value as `Quantity[VO, UL]`

Note that not all numeric operations require all of these steps,
however here is an example fragment of such code for addition which demonstrates them all:

```scala
transparent inline given ctx_add_2V2U[VL, UL, VR, UR](using
    vres: ValueResolution[VL, VR],
    icl: Conversion[Quantity[VL, UL], Quantity[vres.VO, UL]],
    icr: Conversion[Quantity[VR, UR], Quantity[vres.VO, UL]],
    alg: AdditiveSemigroup[vres.VO]
        ): Add[VL, UL, VR, UR] =
    new infra.AddNC((ql: Quantity[VL, UL], qr: Quantity[VR, UR]) => alg.plus(icl(ql).value, icr(qr).value).withUnit[UL])
```

In the example above, you can see that the context object that maps
`(VL, VR) => VO` is of type `ValueResolution[VL, VR]`.

It is possible to define all the necessary `ValueResolution[VL, VR]` for all possible pairs of
`(VL, VR)`, however for more than a small number of such types the number of pairs grows unwieldy
rather fast (quadratically fast in fact).
However, there is another preferred alternative that allows you to only define "key" pairs that
define a Directed Acyclic Graph, and the `coulomb` typeclass system will efficiently search this
space to identify the correct value of `ValueResolution[VL, VR]` at compile time.

Here is one example that captures the "total ordering" relation among value type resolutions
for `{Int, Long, Float, Double}` that comes with `coulomb-core`:

```scala
// ValuePromotion infers the transitive closure of all promotions
given ctx_vpp_standard: ValuePromotionPolicy[
    (Int, Long) &: (Long, Float) &: (Float, Double) &: TNil
] = ValuePromotionPolicy()
```

Using this, we can finish off our `Wrapper` example with some rules for generating `ValueResolution`.

```scala mdoc
object wrappervr:
    import coulomb.ops.*
    transparent inline given vr_Wrapper[VL, VR](using vres: ValueResolution[VL, VR]): ValueResolution[Wrapper[VL], Wrapper[VR]] =
        new ValueResolution[Wrapper[VL], Wrapper[VR]]:
            type VO = Wrapper[vres.VO]

import wrappervr.given

val wq1 = Wrapper(1d).withUnit[Second]
val wq2 = Wrapper(1f).withUnit[Minute]

wq1 + wq2
```

## Coulomb Policies

The `coulomb-core` library is designed so that very few typeclasses are hard-coded.
As previous sections demonstrate, it is relatively easy to implement your own typeclasses
if you need to work with custom types, or would prefer behaviors that are different than
available out-of-box typeclasses.

However, one tradeoff is that to obtain out-of-box features,
the programmer needs to import a somewhat unweildy number of typeclasses.

To help reduce the number of imports and make it easier to understand various behavior options,
`coulomb` takes advantage of the new Scala 3
[export clauses](https://docs.scala-lang.org/scala3/reference/other-new-features/export.html)
to provide predefined groupings of imports which represent different "policies" for behavior.

The `coulomb-core` library defines two policies, which you can import from `coulomb.policy`.
The first, which is used by most of the examples in this documentation, is `coulomb.policy.standard`.
This policy supports:

- implicit value type promotions
- implicit unit conversions
- implicit value conversions

Here is an example of using `coulomb.policy.standard`

```scala mdoc:nest
// note this does not include other necessary imports
import coulomb.policy.standard.given
import scala.language.implicitConversions

val q1 = 1d.withUnit[Liter]
val q2 = 1.withUnit[Meter ^ 3]

// with "standard" policy, coulomb can resolve differing value types and unit types
q1 + q2
```

```scala mdoc:reset:invisible
// here we are resetting the compile context
// to demonstrate strict policy

// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.given

// unit definitions
import coulomb.units.si.{*, given}
import coulomb.units.mksa.{*, given}
import coulomb.units.time.{*, given}
import coulomb.units.accepted.{*, given}
```

The second pre-defined policy is `couomb.policy.strict`,
which does *not* allow implicit conversions of values or units.
Operations involving identical value and unit types are always allowed,
as are *explicit* conversions:

```scala mdoc
import coulomb.policy.strict.given

val q1 = 1d.withUnit[Liter]
val q2 = 2d.withUnit[Liter]

// strict policy allows operating with same unit and value
q1 + q2

// explicit value and unit conversions are always allowed
q1.toValue[Float]
q1.toUnit[Meter ^ 3]
```

```scala mdoc:fail
val q3 = 1.withUnit[Meter ^ 3]

// strict policy does not allow implicit value or unit conversions
q1 + q3
```

The `coulomb-spire` library provides additional predefined policies that
support the standard Scala numeric types as well as spire's specialized types.

@:callout(info)
If you import `coulomb-spire` policies, do not also import `coulomb-core` policies.
Only one policy at a time should be imported.
@:@

## Time and Temperature
