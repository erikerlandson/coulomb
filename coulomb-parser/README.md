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
  "eu.timepit" %% "singleton-ops" % "0.5.0"
  // coulomb-parser and deps
  "com.manyangled" %% "coulomb-parser" % "0.4.0",                    // QuantityParser
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"   // %Provided parser dependency
)
```

The `coulomb-parser` package provides a utility for parsing a quantity expression DSL into
correctly typed `Quantity` values, called `QuantityParser`.
The following coulomb packages make use of QuantityParser:

* [coulomb-avro](../coulomb-avro/)
* [coulomb-pureconfig](../coulomb-pureconfig/)
* [coulomb-pureconfig-refined](../coulomb-pureconfig-refined/)
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

A quantity parser can be used to generate coefficients of conversion between
unit expression strings and a unit type, using the `coefficient` method.
Coulomb uses spire `Rational` as its internal representation of coefficients.
Incompatible units will result in a compile failure:

```scala

scala> val coef = qp.coefficient[Nat]("byte")
val coef: scala.util.Try[spire.math.Rational] = Success(18014398509481984/3248660424303251)

scala> coef.get
val res14: spire.math.Rational = 18014398509481984/3248660424303251

scala> val coef = qp.coefficient[Nat]("second")
val coef: scala.util.Try[spire.math.Rational] =
Failure(scala.tools.reflect.ToolBoxError: reflective compilation has failed:
```

Quantity parsers can be used to apply units to a raw value, using the `applyUnitExpr` method.
This method can be useful when writing i/o routines.

```scala
scala> val qv = qp.applyUnitExpr[Float, Minute](1f, "second")
val qv: scala.util.Try[coulomb.Quantity[Float,coulomb.time.Minute]] = Success(Quantity(0.016666668))

scala> qv.get.show
val res13: String = 0.016666668 min
```

#### include files

A QuantityParser is typically aware of necessary implicit definitions at global scope,
however in some scenarios it may not see needed definitions.
For example, this can happen with
[customized definitions](../README.md#unit-conversions-for-custom-value-types).
You can create a QuantityParser with additional imports using the `withImports` method:

```scala
val qp = QuantityParser.withImports[Foot :: Meter :: HNil]("my.custom1._", "my.custom2._", ...)
```

#### serialization

`QuantityParser` is `<: Serializable`.
Once created, these objects can be serialized and deserialized for use in
multiple contexts.
An example of "ser/de" can be seen in the quantity parser
[unit tests](../coulomb-tests/src/test/scala/coulomb/QuantityParser.scala)
