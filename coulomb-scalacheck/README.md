# Integration with scalacheck

Learn more:

* [coulomb tutorial](../README.md#tutorial)

This package provides instances of `scalacheck` `Arbitrary` and `Cogen` for coulomb `Quantity`

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.5.8",
  "org.typelevel" %% "spire" % "0.18.0",
  "eu.timepit" %% "singleton-ops" % "0.5.2",
  // scalacheck dependencies
  "com.manyangled" %% "coulomb-scalacheck" % "0.5.8",
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test
)
```

### How to import

Simply add

```scala
import coulomb.scalacheck.ArbQuantity._
```

