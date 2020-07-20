# Integration with cats

Learn more:

* [coulomb tutorial](../README.md#tutorial)

This package provides some instances of cats typeclasses for coulomb

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.4.6",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.5.0",
  // cats dependencies
  "com.manyangled" %% "coulomb-cats" % "0.4.6",  
  "org.typelevel" %% "cats-core" % "2.1.1"
)
```

### How to import

The following examples can be run with these imports:

```scala
import cats._
import cats.implicits._

// instead of cats implicits you could use spire Eq instances
// import spire.std.double._
// import spire.std.float._
// import spire.std.int._
// or even:
// import spire.std.any._

import coulomb._
// Cats instances
import coulomb.cats.implicits._

import coulomb.si._
import coulomb.siprefix._
```

And for the examples:


### Examples

#### Eq instances

`coulomb-cats` provides a `Eq[V, U]` instance if there is an `Eq[V]` instance in scope.

Note the instances requires both the Value type `V` and the unit type `U` to be the same,
but if there are proper unit conversions you can compare them.
Totally unrelated units will produce a compilation error though

Coulomb `Quantity` has already a smart `===` operator that will compare quantities if
their units are compatible. Regardless of the `===` operator `Eq` is often required when
using other libraries, e.g. when using scalacheck

You can request an `Eq` instance doing e.g.
```scala
@ val eqdms = implicitly[Eq[Quantity[Double, Meter %/ Second]]]
eqdms: Eq[Quantity[Double, Meter %/ Second]] = cats.kernel.Order$$anon$2@5f193335

@ val a = 1.0.withUnit[Meter %/ Second]
a: Quantity[Double, Meter %/ Second] = Quantity(1.0)

@ val b = 1.0.withUnit[Meter %/ Second]
b: Quantity[Double, Meter %/ Second] = Quantity(1.0)

@ val c = 1.withUnit[(Kilo %* Meter) %/ Second]
c: Quantity[Int, Meter %/ Second] = Quantity(1)

@ val d: Quantity[Double, Kilogram] = 1.0.withUnit[Kilogram]
d: Quantity[Double, Kilogram] = Quantity(1.0)

```
Same units can be compared as expected
```scala
@ a.eqv(b)
res7: Boolean = true
```

Convertible units can be compared too 
```scala
@ a.eqv(c)
  res9: Boolean = true
```

Incompatible units will produce a compilation error

```scala
@ a.eqv(d)
cmd15.sc:1: type mismatch;
 found   : coulomb.Quantity[Double,coulomb.si.Kilogram]
 required: coulomb.Quantity[Double,coulomb.si.Meter %/ coulomb.si.Second]
val res15 = a.eqv(d)
                  ^
Compilation Failed

```

#### Order instances

`coulomb-cats` provides a `Order[V, U]` instance if there is an `Order[V]` instance in scope.

This can be used to sort, e.g. `NonEmptyList` or in general anything that takes an `Order`. for example:

```scala
NonEmptyList.of(a, b).sorted
```

In case of combining units you may need to unify the types on the list

```scala
NonEmptyList.of[Quantity[Double, Meter %/ Second]](a, c).sorted
```

