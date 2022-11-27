# coulomb-refined

The `coulomb-refined` package defines policies and utilities for integrating the
[refined](https://github.com/fthomas/refined#refined-simple-refinement-types-for-scala)
typelevel libraries with `coulomb`.

## Quick Start

### documentation

You can browse the `coulomb-spire` policies
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb/policy/overlay/refined.html).

### packages

Include `coulomb-spire` with your Scala project:

```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-refined" % "@VERSION@"
```

### import

To import the standard coulomb policy with the refined overlay:

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
import coulomb.ops.algebra.all.{*, given}

// standard policy for spire and scala types
import coulomb.policy.standard.given
import scala.language.implicitConversions

// overlay policy for refined integrations
import coulomb.policy.overlay.refined.algebraic.given
```

### examples

Examples in this section will use the following workaround as a replacement for
[refineMV](https://github.com/fthomas/refined/issues/932)
until it is ported forward to Scala 3.

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
Primarily this means the predicates `Positive` and `NonNegative`.
For example, the positive doubles are an additive semigroup and multiplicative group,
as the example below demonstrates.

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

## Policies

The `coulomb-refined` package currently provides a single "overlay" policy.
An overlay policy is designed to work with any other policies currently in scope,
and lift them into another abstraction;
in this case, lifting policies for value type(s) `V` into `Refined[V, P]`.
The `Refined` abstraction guarantees that a value of type `V` satisfies some predicate `P`,
and the semantics of `V` remain otherwise unchanged.

For example, given any algebra in scope for a type `V` that defines addition,
the `coulomb-refined` overlay defines the corresponding `Refined[V, P]` addition
like so:
```scala
plus(x: Refined[V, P], y: Refined[V, P]) => // (x.value + y.value) refined by P
```

