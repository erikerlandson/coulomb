# coulomb-refined

The `coulomb-refined` package defines algebra typeclasses for integrating the
[refined](https://github.com/fthomas/refined#refined-simple-refinement-types-for-scala)
typelevel libraries with `coulomb`.

## Quick Start

### documentation

You can browse the `coulomb-refined` api definitions
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/integrations/refined/index.html).

### packages

Include `coulomb-refined` with your Scala project:

```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-refined" % "@VERSION@"
```

### import

```scala mdoc
// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// common refined definitions
import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

// algebraic definitions
import algebra.instances.all.given

// algebra typeclasses for refined integrations
import coulomb.integrations.refined.all.given

// coulomb syntax for refined integrations
import coulomb.integrations.refined.syntax.*
```

### examples

Examples in this section will use the following workaround as a replacement for
[refineMV][refinedapidocs]
until it is
[ported forward](https://github.com/fthomas/refined/issues/932)
to Scala 3.

```scala mdoc
// a workaround for refineMV not being available in scala3
// https://github.com/fthomas/refined/issues/932
object workaround:
    extension [V](v: V)
        def withRP[P](using Validate[V, P]): Refined[V, P] =
            refineV[P].unsafeFrom(v)

import workaround.*
```

The `coulomb-refined` package supports `refined` predicates that are algebraically well-behaved for applicable operations.
Primarily this means the predicates
[Positive][refinedapidocs]
and
[NonNegative][refinedapidocs].
For example, the positive doubles are an additive semigroup and multiplicative group,
as the following code demonstrates.

@:callout(info)
The
[table][algebra-support-table]
below summarizes the full list of supported `refined` predicates and associated algebras.
@:@

```scala mdoc
import coulomb.units.si.{*, given}
import coulomb.units.us.{*, given}

val pos1 = 1d.withRP[Positive].withUnit[Meter]
val pos2 = 2d.withRP[Positive].withUnit[Meter]
val pos3 = 3d.withRP[Positive].withUnit[Second]

// positive doubles are an additive semigroup
pos1 + pos2

// also a multiplicative semigroup
pos1 * pos2
pos2.pow[2]

// also a multiplicative group
pos2 / pos3
pos2.pow[0]
```

The standard `refined` function for refining values with run-time checking is
[refineV][refinedapidocs],
which returns an
@:api(scala.util.Either).
The `coulomb-refined` package supplies a similar variation `refinedVU`.
These objects are also supported by algebras.

```scala mdoc
// This refinement succeeds, and returns a Right value
val pe1 = refineVU[Positive, Meter](1)

// This refinement fails, and returns a Left value
val pe2 = refineVU[Positive, Meter](0)

// positives are an additive semigroup
pe1 + pe1

// algebras operating on Left values result in a Left
pe1 + pe2
```

### algebra support table

The following table summarizes the algebras and operations supported by this package.
Examples of Fractional value types include `Double`, `Float`,
@:api(scala.math.BigDecimal),
spire @:api(spire.math.Rational), etc.
Integral value types include `Int`, `Long`, @:api(scala.math.BigInt), etc.

| Value Type | Predicate | Add Alg | Mult Alg | `+` | `*` | `/` | `pow` (exponent) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| Fractional | Positive | semigroup | group | Y | Y | Y | Y (rational) |
| Fractional | NonNegative | semigroup | semigroup | Y | Y | N | Y (pos int) |
| Integral | Positive | semigroup | semigroup | Y | Y | N | Y (pos int) |
| Integral | NonNegative | semigroup | semigroup | Y | Y | N | Y (pos int) |

@:callout(info)
The table above also applies to `Either` objects returned by `refineVU` as discussed
in the examples section above.
@:@
