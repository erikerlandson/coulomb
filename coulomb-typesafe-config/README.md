# Integration with pureconfig

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
  "com.manyangled" %% "coulomb-typesafe-config" % "0.3.6"
  "com.manyangled" %% "coulomb-parser" % "0.3.6",
  "com.typesafe" % "config" % "1.3.4",
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

First import a selection of `coulomb` units, and the demo objects:

```scala
scala> import shapeless._, coulomb._, coulomb.si._, coulomb.siprefix._, coulomb.info._, coulomb.time._, coulomb.parser._

scala> import ConfigIntegration._, scala.collection.JavaConverters._
```

The demo pre-defines a simple configuration object:
```scala
scala> confTS.entrySet().asScala.map { e => s"${e.getKey()} = ${e.getValue()}" }.mkString("\n")
res0: String =
bandwidth = Quoted("10 megabyte / second")
memory = Quoted("100 gigabyte")
duration = Quoted("60 second")
```

It also imports definitions from the `coulomb-typesafe-config` sub-package,
which defines [CoulombConfig](https://erikerlandson.github.io/coulomb/latest/api/coulomb/typesafeconfig/CoulombConfig.html),
and a new method `getQuantity`.
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
