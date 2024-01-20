# Integration with pureconfig

To learn more ...

* [coulomb](../README.md#tutorial)
* [coulomb-parser](../coulomb-parser/README.md)

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
  // pureconfig integration:
  "com.manyangled" %% "coulomb-pureconfig" % "0.5.8",
  "com.manyangled" %% "coulomb-parser" % "0.5.8",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0",
  "com.github.pureconfig" %% "pureconfig-core" % "0.17.5",
  "com.github.pureconfig" %% "pureconfig-generic" % "0.17.5"
)
```

### How to import:

The examples that follow can be run with the following imports:

```scala
import _root_.pureconfig._
import _root_.pureconfig.generic.auto._
import _root_.pureconfig.syntax._

import coulomb._
import coulomb.pureconfig._
import coulomb.parser.QuantityParser

import shapeless.{ ::, HNil }

import coulomb.si.{ Meter, Second }
import coulomb.siprefix.{ Mega, Giga }
import coulomb.time.{ Minute, Hour }
import coulomb.info.Byte
import coulomb.us.Mile
```

### Examples

The `coulomb-pureconfig` integration library allows coulomb `Quantity` objects to be
saved and loaded using `pureconfig`, including unit awareness.

The following example demonstrates writing a Quantity with pureconfig:

```scala
scala> val acc = 1.withUnit[(Mile %/ Hour) %/ Minute]
val acc: coulomb.Quantity[Int,coulomb.us.Mile %/ coulomb.time.Hour %/ coulomb.time.Minute] = Quantity(1)

scala> val conf = acc.toConfig
val conf: com.typesafe.config.ConfigValue = SimpleConfigObject({"unit":"(mile/hour)/minute","value":1})
```

As the above example shows, `Quantity` values are stored with unit information as well as the "raw" value.

In order to load Quantity values, a `QuantityParser` object must also be declared.
The following shows a parser being created, and using it to re-load the quantity value.
Notice that the quantity value can be loaded using a new value type and units,
as long the proper conversions exist:

```scala
scala> implicit val qp = QuantityParser[Mile :: Hour :: Minute :: HNil]
val qp: coulomb.parser.QuantityParser = coulomb.parser.QuantityParser@75f928ff

scala> val accread = conf.toOrThrow[Quantity[Float, Meter %/ (Second %^ 2)]]
val accread: coulomb.Quantity[Float,coulomb.si.Meter %/ (coulomb.si.Second %^ 2)] = Quantity(0.0074506667)

scala> accread.show
val res0: String = 0.0074506667 m/s^2
```

If a quantity is loaded using an incompatible unit type, it is an i/o error:

```scala
scala> val accread = conf.toOrThrow[Quantity[Float, Meter %/ (Second %^ 3)]]
pureconfig.error.ConfigReaderException: Cannot convert configuration to a coulomb.Quantity. Failures are:
  at the root:
    - Cannot convert '{
          # hardcoded value
          "unit" : "(mile/hour)/minute",
          # hardcoded value
          "value" : 1
      }
      'to coulomb.Quantity[Float,coulomb.si.Meter %/ (coulomb.si.Second %^ Int(3))]: Failed to parse (1.0, (mile/hour)/minute) ==> coulomb.%/[coulomb.si.Meter, coulomb.%^[coulomb.si.Second, 3]].
```

A common use case for `pureconfig` is loading configurations into well typed case classes.
The following code shows a simple example of a configuration object with Quantity values.


```scala
  // a config case class, defines two coulomb Quantity fields
  case class QC(
      duration: Quantity[Double, Second],
      memory: Quantity[Double, Mega %* Byte],
      regular: Int)

  // Define a quantity parser for the pureconfig integration to use.
  implicit val qp = QuantityParser[Byte :: Hour :: Giga :: HNil]

  // A configuration to load - note that units are different, but compatible with the QC fields
  val conf = com.typesafe.config.ConfigFactory.parseString("""{
    "duration": { "value": 1, "unit": "hour" },
    "memory": { "value": 1, "unit": "gigabyte" }
    "regular": 42
  }""")

  // load the config as usual, using pureconfig api:
  // pureconfig will correctly convert the units, or it will fail if the units are not compatible
  val qc = ConfigSource.fromConfig(conf).load[QC]
```
