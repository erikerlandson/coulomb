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

