# Integration with typesafe config

To learn more about coulomb in general refer to the
[coulomb tutorial](../README.md#tutorial)

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions.

The package `coulomb-typesafe-config` provides an integration of unit parsing with the Typesafe Config.
```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-typesafe-config" % "0.5.0"
  "com.manyangled" %% "coulomb-parser" % "0.5.0",
  "com.typesafe" % "config" % "1.4.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)
```

### Examples

As an example of `coulomb` applied to unit type safety for application configuration
settings, the following demonstrates the `coulomb.typesafeconfig` integration of the
[Typesafe `config` package](https://github.com/lightbend/config)
with `coulomb` unit analysis, using `QuantityParser`.

To see this in action, build the examples and load the demo into a REPL:

```bash
% cd /path/to/coulomb
% sbt coulomb_tests/console
```

First import a selection of `coulomb` units, and the `coulomb.typesafeconfig` integration package:

```scala
scala> import shapeless._, coulomb._, coulomb.si._, coulomb.siprefix._, coulomb.info._, coulomb.time._, coulomb.parser._

scala> import scala.collection.JavaConverters._, com.typesafe.config.ConfigFactory, coulomb.typesafeconfig._
```

Here we set up a typesafe configuration and bind it to a QuantityParser to enable unit awareness: 
```scala
scala> val confTS = ConfigFactory.parseString("""
     |     "duration" = "60 second"
     |     "memory" = "100 gigabyte"
     |     "bandwidth" = "10 megabyte / second"
     |   """)
confTS: com.typesafe.config.Config = Config(SimpleConfigObject({"bandwidth":"10 megabyte / second","duration":"60 second","memory":"100 gigabyte"}))

scala> val qp = QuantityParser[Byte :: Second :: Giga :: Mega :: HNil]
qp: coulomb.parser.QuantityParser = coulomb.parser.QuantityParser@6c8590b1

scala> val conf = confTS.withQuantityParser(qp)
conf: coulomb.typesafeconfig.CoulombConfig = CoulombConfig(Config(SimpleConfigObject({"bandwidth":"10 megabyte / second","duration":"60 second","memory":"100 gigabyte"})),coulomb.parser.QuantityParser@6c8590b1)
```

The `coulomb-typesafe-config` package defines the
[CoulombConfig](https://erikerlandson.github.io/coulomb/latest/api/coulomb/typesafeconfig/CoulombConfig.html)
class and a new method `getQuantity`.
This new getter method applies a `QuantityParser` like the one above to transform the configuration values into a
`Quantity` expression:
```scala
scala> conf.getQuantity[Double, Minute]("duration").get.show
res1: String = 1.0 min

scala> conf.getQuantity[Int, Mega %* Byte]("memory").get.show
res2: String = 100000 MB

scala> conf.getQuantity[Float, Giga %* Bit %/ Second]("bandwidth").get.show
res3: String = 0.08 Gb/s
```

If we ask for a unit type that is incompatible with the configuration, an error is returned:
```scala
scala> conf.getQuantity[Int, Giga %* Bit]("bandwidth")
res4: scala.util.Try[coulomb.Quantity[Int,coulomb.siprefix.Giga %* coulomb.info.Bit]] =
Failure(scala.tools.reflect.ToolBoxError: reflective compilation has failed...
```
