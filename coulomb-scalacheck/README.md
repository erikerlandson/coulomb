# Integration with cats

Learn more:

* [coulomb tutorial](../README.md#tutorial)

This package provides instances of `scalacheck` `Arbitrary` and `Cogen` for coulomb `Quantity`

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
  // scalacheck dependencies
  "com.manyangled" %% "coulomb-scalacheck" % "0.4.6",  
  "org.scalacheck" %% "scalacheck" % "1.14.1" % Test
)
```

### How to import

Simply add

```scala
import coulomb.scalacheck.ArbQuantity._
```

