# coulomb-spire

The `coulomb-spire` package defines unit conversion, value conversion,
and value resolution
[policies][policy-concepts]
for
[spire](https://typelevel.org/spire/)
numeric types (and standard Scala numeric types).

The following numeric types are supported:

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

@:callout(info)
You may notice that `BigInt` and `BigDecimal` are not supported in `coulomb-core`,
even though they are Scala native types.
This is because Scala's native numeric typeclasses do not uniformly treat the
core types `Int, Long, Float, Double` the same as `BigInt` and `BigDecimal`,
while spire's typeclasses do.
For this reason, it is much easier to support `BigInt` and `BigDecimal` as part of
the `coulomb-spire` package.
@:@

## Quick Start

### documentation

You can browse the `coulomb-spire` policies
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/policy/spire.html).

### packages

Include `coulomb-spire` with your Scala project:

```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-spire" % "@VERSION@"
```

@:callout(info)
To use coulomb unit definitions:
```scala
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```
@:@

### import

To import the standard (non-strict) spire policy:

```scala mdoc
import spire.math.*

// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.spire.all.{*, given}

// standard policy for spire and scala types
import coulomb.policy.spire.standard.given
import scala.language.implicitConversions
```

@:callout(info)
Never import more than one `policy` at a time.
For example, if you import `coulomb.policy.spire.standard.given`,
do not also import `coulomb.policy.standard.given.`
@:@

### examples

```scala mdoc
import coulomb.units.si.{*, given}
import coulomb.units.us.{*, given}

// coulomb-spire policies allow the use of spire and Scala types
val rq = Rational(3, 2).withUnit[Meter]
val bq = BigDecimal(1).withUnit[Yard]
val iq = 1.withUnit[Meter]

// The standard policy supports implicit conversions of unit and value types
rq + bq + iq
```

## Policies

As with `coulomb-core`, the `coulomb-spire` package provides two predefined
[policy][policy-concepts]
options:

- `coulomb.policy.spire.standard` - Supports implicit and explicit value and unit conversions, and value promotions for spire and Scala numeric types.
- `coulomb.policy.spire.strict` - Only explicit value and unit conversions are supported.

The
[value resolution][value-resolution-concepts]
and promotion rules for `coulomb-spire` are extended to
include `BigDecimal`, `BigInt`, `Rational`, `Algebraic` and `Real`.
The resulting lattice of promotions looks like this:

| Promote From | To |
| --- | --- |
| Int | Long |
| Long | BigInt |
| BigInt | Float |
| Long | Float |
| Float | Double |
| Double | BigDecimal |
| BigDecimal | Rational |
| Rational | Algebraic |
| Algebraic | Real |

@:callout(info)
Remember that the promotions above are transitive,
and so `Int` promotes to `Real`, etc.
@:@

The definition of promotions for `coulomb-spire` can be browsed
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/ops/resolution/spire$.html)

[value-resolution-concepts]: concepts.md#value-promotion-and-resolution
[policy-concepts]: concepts.md#coulomb-policies
