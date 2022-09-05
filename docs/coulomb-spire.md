# coulomb-spire

The `coulomb-spire` package defines unit conversion, value conversion,
and value resolution policies for
[spire](https://typelevel.org/spire/)
numeric types (and standard Scala numeric types).

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
