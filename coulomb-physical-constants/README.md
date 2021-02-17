# Physical constants

Learn more:

This package provides a set of physical constants using `coulomb` quantities.
The list of constants, values and base unites come from:

[https://en.wikipedia.org/wiki/List_of_physical_constants](https://en.wikipedia.org/wiki/List_of_physical_constants)

### How to use in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.5.6",
  "org.typelevel" %% "spire" % "0.17.0",
  "eu.timepit" %% "singleton-ops" % "0.5.2",
  // constant dependency
  "com.manyangled" %% "coulomb-physical-constants" % "0.5.6", 
)
```

### How to import

The following examples can be run with these imports:

```scala
import coulomb._
import coulomb.physicalconstants._
import coulomb.si._
```

### Examples

In your code you can make a constant for any compatible numeric type and units, e.g.

```scala
val c: Quantity[BigDecimal, Meter %/ Second] = speedOfLightInVacuum[BigDecimal]
```
