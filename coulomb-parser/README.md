# Working with QuantityParser

To learn more about coulomb in general refer to the
[coulomb tutorial](../README.md#tutorial)

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions.

```scala
libraryDependencies ++= Seq(
  // coulomb and core dependencies
  "com.manyangled" %% "coulomb" % "0.4.0",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.4.3"
  // coulomb-parser and deps
  "com.manyangled" %% "coulomb-parser" % "0.4.0",                    // QuantityParser
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"   // %Provided parser dependency
)
```

The `coulomb` package `coulomb-parser` provides a utility for parsing a quantity expression DSL into
correctly typed `Quantity` values, called `QuantityParser`.
The following coulomb packages make use of QuantityParser:

* [coulomb-avro](../coulomb-avro/)
* [coulomb-pureconfig](../coulomb-pureconfig/)
* [coulomb-typesafe-config](../coulomb-typesafe-config/)

### Examples
A `QuantityParser` is instantiated with a list of types that it will recognize.
This example shows a quantity parser that can recognize values in bytes, seconds,
and the two prefixes mega and giga:
```scala
scala> import shapeless._, coulomb._, coulomb.si._, coulomb.siprefix._, coulomb.info._, coulomb.time._, coulomb.parser._

scala> val qp = QuantityParser[Byte :: Second :: Giga :: Mega :: HNil]
qp: coulomb.parser.QuantityParser = coulomb.parser.QuantityParser@43356dd9
```

Parsing an expression requires an expected numeric and unit type.
Here we see a quantity given in seconds, successfully being parsed and converted to minutes:
```scala
scala> qp[Double, Minute]("60 second")
res1: scala.util.Try[coulomb.Quantity[Double,coulomb.time.Minute]] = Success(Quantity(1.0))
```

A quantity parser recognizes and understands how to interpret prefix units, as well as
compound unit expressions:
```scala
scala> qp[Double, Mega %* Byte %/ Second]("1.0 gigabyte/second").get.show
res2: String = 1000.0 MB/s
```

The quantity parser in this example was not created to recognize minutes inside the DSL, and so
the following parse will fail.
```scala
scala> qp[Double, Minute]("60 minute")
res3: scala.util.Try[coulomb.Quantity[Double,coulomb.time.Minute]] = Failure(coulomb.parser.QPLexingException: ')' expected but 'm' found)
```

As usual, incompatible units will also cause a parsing error:
```scala
scala> qp[Double, Minute]("60 byte")
res4: scala.util.Try[coulomb.Quantity[Double,coulomb.time.Minute]] =
Failure(scala.tools.reflect.ToolBoxError: reflective compilation has failed ...
```
