<img src="https://raw.githubusercontent.com/erikerlandson/coulomb/develop/assets/coulomb-splash-800x400.png" alt="coulomb: a statically typed unit analysis library for Scala" width="300" height="150">

 [![Build Status](https://travis-ci.org/erikerlandson/coulomb.svg?branch=develop)](https://travis-ci.org/erikerlandson/coulomb)
 [ ![Download](https://api.bintray.com/packages/manyangled/maven/coulomb/images/download.svg) ](https://bintray.com/manyangled/maven/coulomb/_latestVersion)
 [![Join the chat at https://gitter.im/erikerlandson/community](https://badges.gitter.im/erikerlandson/community.svg)](https://gitter.im/erikerlandson/coulomb?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

### Documentation
API documentation for `coulomb` is available at: https://erikerlandson.github.io/coulomb/latest/api/

There is also a [tutorial](#tutorial) below.

Blog post: Preventing configuration errors with
[unit types](http://erikerlandson.github.io/blog/2019/05/09/preventing-configuration-errors-with-unit-types/).

Blog post: Integrating Apache Avro
[with coulomb](http://erikerlandson.github.io/blog/2019/05/23/unit-types-for-avro-schema-integrating-avro-with-coulomb/).

Blog post: The logical representation coulomb uses for
[units and unit analysis](http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/).

Talk:
["Why Your Data Schema Should Include Units"](https://www.youtube.com/watch?v=qrQmB2KFKE8)
at Berlin Buzzwords 2019.

### Scala Requirements

The `coulomb` libraries currently require scala 2.13.0, or higher.

### How to include `coulomb` in your project

The core `coulomb` package can be included by adding the dependencies shown below.
Note that its two 3rd-party dependencies -- `spire` and `singleton-ops` -- are `%Provided`,
and so you must also include them, if your project does not already do so. The `shapeless`
package is also a dependency, but is included transitively via `spire`.

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb" % "0.3.6",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.4.0"
)
```

The `coulomb` project also provides a selection of predefined units, which are available as
separate sub-packages.

```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-si-units" % "0.3.6",        // The seven SI units: meter, second, kilogram, etc
  "com.manyangled" %% "coulomb-accepted-units" % "0.3.6",  // Common non-SI metric: liter, centimeter, gram, etc
  "com.manyangled" %% "coulomb-time-units" % "0.3.6",      // minute, hour, day, week
  "com.manyangled" %% "coulomb-info-units" % "0.3.6",      // bit, byte, nat
  "com.manyangled" %% "coulomb-mks-units" % "0.3.6",       // MKS units: Joule, Newton, Watt, Volt, etc
  "com.manyangled" %% "coulomb-customary-units" % "0.3.6", // non-metric units: foot, mile, pound, gallon, pint, etc
  "com.manyangled" %% "coulomb-temp-units" % "0.3.6"       // Celsius and Fahrenheit temperature scales
)
```

This project also provides a parsing facility, `QuantityParser`, that can parse a
unit expression language into properly typed `Quantity` objects. This tool can be
used for extending standard configuration systems with type-safe unit quantities.

```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-parser" % "0.3.6",                    // QuantityParser
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"   // %Provided parser dependency
)
```

The package `coulomb-typesafe-config` provides an integration of unit parsing with the Typesafe Config.
```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-typesafe-config" % "0.3.6"
  "com.manyangled" %% "coulomb-parser" % "0.3.6",
  "com.typesafe" % "config" % "1.3.4",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)
```

The package `coulomb-avro` provides an integration of unit parsing with Avro schemas:
```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-avro" % "0.3.6",
  "com.manyangled" %% "coulomb-parser" % "0.3.6",
  "org.apache.avro" % "avro" % "1.9.1",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)
```

The package `coulomb-pureconfig` provides an integration with pureconfig configurations:

https://github.com/erikerlandson/coulomb/tree/develop/coulomb-pureconfig

### Code of Conduct
The `coulomb` project supports the [Scala Code of Conduct](https://typelevel.org/code-of-conduct.html);
all contributors are expected to respect this code.
Any violations of this code of conduct should be reported to [the author](https://github.com/erikerlandson/).

### Tutorial

#### Table of Contents

* [Running Tutorial Examples](#running-tutorial-examples)
* [Features](#features)
* [`Quantity` and Unit Expressions](#quantity-and-unit-expressions)
* [Quantity Values](#quantity-values)
* [String Representations](#string-representations)
* [Predefined Units](#predefined-units)
* [Unit Types and Convertability](#unit-types-and-convertability)
* [Unit Conversions](#unit-conversions)
* [Unit Operations](#unit-operations)
* [Declaring New Units](#declaring-new-units)
* [Unitless Quantities](#unitless-quantities)
* [Unit Prefixes](#unit-prefixes)
* [Using `WithUnit`](#using-withunit)
* [Quantity Parsing](#quantity-parsing)
* [Type Safe Configurations](#type-safe-configurations)
* [Temperature Values](#temperature-values)
* [Type Parameter Integrations](#type-parameter-integrations)

#### Running Tutorial Examples

Except where otherwise noted, the following tutorial examples can be run in a scala REPL as follows:
```
% cd /path/to/scala
% sbt coulomb_tests/console

scala> import shapeless._, coulomb._, coulomb.si._, coulomb.siprefix._, coulomb.mks._, coulomb.time._, coulomb.info._, coulomb.binprefix._, coulomb.accepted._, coulomb.us._, coulomb.temp._, coulomb.define._, coulomb.parser._
```

#### Features

The `coulomb` provides the following features:

Allow a programmer to associate unit analysis with values, in the form of static types
```scala
val length = 10.withUnit[Meter]
val duration = (30.0).withUnit[Second]
val mass = Quantity[Float, Kilogram](100)
```
Express those types with arbitrary and natural static type expressions
```scala
val speed = (100.0).withUnit[(Kilo %* Meter) %/ Hour]
val acceleration = (9.8).withUnit[Meter %/ (Second %^ 2)]
```
Let the compiler determine which unit expressions are equivalent (aka _convertable_)
and transparently convert between them
```scala
val mps: Quantity[Double, Meter %/ Second] = (60.0).withUnit[Mile %/ Hour]
```
Cause a compile-time error when operations are attempted with _non-convertable_ unit types
```scala
val mps: Quantity[Double, Meter %/ Second] = (60.0).withUnit[Mile] // compile-time type error!
```
Automatically determine correct output unit types for operations on unit quantities
```scala
val mps: Quantity[Double, Meter %/ Second] = 60D.withUnit[Mile] / 1D.withUnit[Hour]
```
Allow a programmer to easily declare new units that will work seamlessly with existing units
```scala
// a new unit of length:
trait Smoot
implicit val defineUnitSmoot = DerivedUnit[Smoot, Inch](67, name = "Smoot", abbv = "Smt")

// a unit of acceleration:
trait EarthGravity
implicit val defineUnitEG = DerivedUnit[EarthGravity, Meter %/ (Second %^ 2)](9.8, abbv = "g")
```

#### `Quantity` and Unit Expressions

`coulomb` defines the
[class `Quantity`](https://erikerlandson.github.io/coulomb/latest/api/coulomb/Quantity.html)
for representing values with associated units.
Quantities are represented by their two type parameters: A numeric representation parameter `N`
(e.g. Int or Double) and a unit type `U` which represents the unit associated with the value.
Here are some simple declarations of `Quantity` objects:
```scala
import coulomb._
import coulomb.si._

val length = 10.withUnit[Meter]            // An Int value of meters
val duration = (30.0).withUnit[Second]     // a Double value in seconds
val mass = Quantity[Float, Kilogram](100)  // a Float value in kg
```

Three operator types can be used for building more complex unit types:
`%*`, `%/`, and `%^`.
```scala
import coulomb._
import coulomb.si._

val area = 100.withUnit[Meter %* Meter]   // unit product
val speed = 10.withUnit[Meter %/ Second]  // unit ratio
val volume = 50.withUnit[Meter %^ 3]      // unit power
```

Using these operators, units can be composed into unit type expressions of arbitrary
complexity.
```scala
val acceleration = (9.8).withUnit[Meter %/ (Second %^ 2)]
val ohms = (0.01).withUnit[(Kilogram %* (Meter %^ 2)) %/ ((Second %^ 3) %* (Ampere %^ 2))]
```

#### Quantity Values
The internal representation type of a `Quantity` is given by its first type parameter.
Each quantity's value is accessible via the `value` field
```scala
import coulomb._, coulomb.info._, coulomb.siprefix._

val memory = 100.withUnit[Giga %* Byte]  // type is: Quantity[Int, Giga %* Byte]
val raw: Int = memory.value              // memory's raw integer value
```

Standard Scala types `Float`, `Double`, `Int` and `Long` are supported, as well as
any other numeric type `N` for which the `spire` implicit `Numeric[N]` is defined,
for example `BigDecimal` or `spire` `Rational`.

#### String representations
The `show` method can be used to obtain a human-readable string that represents a quantity's
type and value using standard unit abbreviations.  The `showFull` method uses full unit names.
The methods `showUnit` and `showUnitFull` output only the unit without the value.
```scala
scala> val bandwidth = 10.withUnit[(Giga %* Bit) %/ Second]
bandwidth: coulomb.Quantity[...] = coulomb.Quantity@40240000

scala> bandwidth.show
res1: String = 10 Gb/s

scala> bandwidth.showFull
res2: String = 10 gigabit/second

scala> bandwidth.showUnit
res3: String = Gb/s

scala> bandwidth.showUnitFull
res4: String = gigabit/second
```

#### Predefined Units
A variety of units and prefixes are predefined by several `coulomb` sub-packages, which are summarized here.
The relation between the packages below and maven packages is at the top of this page.

* [coulomb.si](https://erikerlandson.github.io/coulomb/latest/api/coulomb/si/index.html): The Standard International [base units](https://en.wikipedia.org/wiki/International_System_of_Units#Base_units) Meter, Kilogram, Second, Ampere, Kelvin, Mole and Candela.
* [coulomb.siprefix](https://erikerlandson.github.io/coulomb/latest/api/coulomb/siprefix/index.html): Standard International [prefixes](https://en.wikipedia.org/wiki/International_System_of_Units#Prefixes) Kilo, Mega, Milli, Micro, etc.
* [coulomb.mks](https://erikerlandson.github.io/coulomb/latest/api/coulomb/mks/index.html): The common "Meter-Kilogram-Second" [derived units](http://scienceworld.wolfram.com/physics/MKS.html).
* [coulomb.accepted](https://erikerlandson.github.io/coulomb/latest/api/coulomb/accepted/index.html): A selection of units [accepted](https://en.wikipedia.org/wiki/Non-SI_units_mentioned_in_the_SI) by the Standard International system.
* [coulomb.time](https://erikerlandson.github.io/coulomb/latest/api/coulomb/time/index.html): time units minute, hour, day, week
* [coulomb.info](https://erikerlandson.github.io/coulomb/latest/api/coulomb/info/index.html): Units of information: Byte, Bit and Nat.
* [coulomb.us](https://erikerlandson.github.io/coulomb/latest/api/coulomb/us/index.html): Some [customary non-SI units](https://en.wikipedia.org/wiki/United_States_customary_units) commonly used in the United States.
* [coulomb.binprefix](https://erikerlandson.github.io/coulomb/latest/api/coulomb/binprefix/index.html): The [binary prefixes](https://en.wikipedia.org/wiki/Binary_prefix) Kibi, Mebi, Gibi, etc.
* [coulomb.temp](https://erikerlandson.github.io/coulomb/latest/api/coulomb/temp/index.html): Temperature units and scales for Fahrenheit and Celsius

#### Unit Types and Convertability
The concept of unit _convertability_ is fundamental to the `coulomb` library and its
implementation of unit analysis.
Two unit type expressions are _convertable_ if they encode an equivalent
"[abstract quantity](https://en.wikipedia.org/wiki/International_System_of_Quantities)."
For example, `Meter` and `Mile` are convertable because they both encode the abstract quantity of `length`.
`Foot %^ 3` and `Liter` are convertable because they both encode a volume, or `length^3`.
`Kilo %* Meter %/ Hour` and `Foot %* (Second %^ -1)` are convertable because they encode a velocity, or `length / time`.

In `coulomb`, abstract quantities like `length` are represented by a unique `BaseUnit`.
For example the base unit for length is the type `coulomb.si.Meter`.
Compound abstract quantities such as `length / time` or `length ^ 3` are internally represented
by pairs of base units with exponents:

* `velocity` <=> `length / time` <=> `(Meter ^ 1)(Second ^ -1)`
* `volume` <=> `length ^ 3` <=> `(Meter ^ 3)`
* `acceleration` <=> `length / time^2` <=> `(Meter ^ 1)(Second ^ -2)`
* `bandwidth` <=> `information / time` <=> `(Byte ^ 1)(Second ^ -1)` 

In `coulomb`, a unit quantity will be implicitly converted into a quantity of a different unit type whenever those types are convertable.
Any attempt to convert between _non-convertable_ unit types results in a compile-time type error.

```scala
scala> def foo(q: Quantity[Double, Meter %/ Second]) = q.showFull

scala> foo(60f.withUnit[Mile %/ Hour])
res5: String = 26.8224 meter/second

scala> foo(1f.withUnit[Mile %/ Minute])
res6: String = 26.8224 meter/second

scala> foo(1f.withUnit[Foot %/ Day])
res7: String = 3.5277778E-6 meter/second

scala> foo(1f.withUnit[Foot %* Day])
       error: type mismatch;
```

#### Unit Conversions
As described in the previous section, unit quantities can be converted from one unit type to another when the two types are convertable.
Unit conversions come in a couple different forms:
```scala
// Implicit conversion
scala> val vol: Quantity[Double, Meter %^ 3] = 4000D.withUnit[Liter]
vol: coulomb.Quantity[Double,coulomb.si.Meter %^ 3] = Quantity(4.0)

scala> vol.showFull
res2: String = 4.0 meter^3

// Explicit conversion using the `toUnit` method
scala> 4000D.withUnit[Liter].toUnit[Meter %^ 3].showFull
res3: String = 4.0 meter^3
```

#### Unit Operations
Unit quantities support math operations `+`, `-`, `*`, `/`, and `pow`.
Quantities must be of convertable unit types to be added or subtracted.
The type of the left-hand argument is taken as the type of the output:
```scala
scala> (1.withUnit[Foot] + 1.withUnit[Yard]).show
res4: String = 4 ft

scala> (4.withUnit[Foot] - 1.withUnit[Yard]).show
res5: String = 1 ft
```

Quantities of any unit types may be multiplied or divided.
Result types are different than either argument:
```scala
scala> (60.withUnit[Mile] / 1.withUnit[Hour]).show
res6: String = 60 mi/h

scala> (1.withUnit[Yard] * 1.withUnit[Yard]).show
res7: String = 1 yd^2

scala> (1.withUnit[Yard] / 1.withUnit[Inch]).toUnit[Percent].show
res8: String = 3600 %
```

When raising a unit to a power, the exponent is given as a literal type:
```scala
scala> 3D.withUnit[Meter].pow[2].show
res13: String = 9.0 m^2

scala> Rational(3).withUnit[Meter].pow[-1].show
res14: String = 1/3 m^(-1)

scala>  3.withUnit[Meter].pow[0].show
res15: String = 1 unitless
```

#### Declaring New Units
The `coulomb` library strives to make it easy to add new units which work seamlessly with the unit analysis type system.
There are two varieties of unit declaration: _base units_ and _derived units_.

A base unit, as its name suggests, is not defined in terms of any other unit; it is axiomatic.
The Standard International [Base Units](https://en.wikipedia.org/wiki/SI_base_unit) are all declared as base units in the [`coulomb.si` subpackage](https://erikerlandson.github.io/coulomb/latest/api/coulomb/si/index.html).
In the [`coulomb.info` sub-package](https://erikerlandson.github.io/coulomb/latest/api/coulomb/info/index.html), `Byte` is declared as the base unit of information.

Declaring a base unit is special in the sense that it also defines a new kind of fundamental _abstract quantity_.
For example, by declaring `coulomb.si.Meter` as a base unit, `coulomb` establishes `Meter` as the canonical representation of the abstract quantity of Length.
Any other unit of length must be declared as a _derived unit_ of `Meter`, or it would be considered _non-convertable_ with other lengths.

Here is an example of defining a new base unit `Scoville`, representing an abstract quantity of [Spicy Heat](https://en.wikipedia.org/wiki/Scoville_scale).
The `BaseUnit` value must be defined as an implicit value:
```scala
import coulomb._
import coulomb.define._  // BaseUnit and DerivedUnit
object SpiceUnits {
  trait Scoville
  implicit val defineUnitScoville = BaseUnit[Scoville](name = "scoville", abbv = "sco")
}
```

The second variety of unit declarations is the _derived_ unit, which is defined in terms of some unit expression involving previously-defined units.
Derived units do _not_ define new kinds of abstract quantity, and are generally more common than base units:
```scala
object NewUnits {
  import coulomb._, coulomb.define._, coulomb.si._, coulomb.us._

  // a furlong is 660 feet
  trait Furlong
  implicit val defineUnitFurlong = DerivedUnit[Furlong, Foot](coef = 660, abbv = "flg")

  // speed of sound is 1130 feet/second (at sea level, 20C)
  trait Mach
  implicit val defineUnitMach = DerivedUnit[Mach, Foot %/ Second](coef = 1130, abbv = "mach")

  // a standard earth gravity is 9.807 meters per second-squared
  // Define an abbreviation "g"
  trait EarthGravity
  implicit val defineUnitEG = DerivedUnit[EarthGravity, Meter %/ (Second %^ 2)](coef = 9.807, abbv = "g")
}
```

Notice that there are no constraints or requrements associated with the unit types `Scoville`, `Furlong`, etc.
These may simply be declared, as shown above, however they _may also be pre-existing types_.
In other words, you may define any type, pre-existing or otherwise, to be a `coulomb` unit by declaring the
appropriate implicit value.

#### Unitless Quantities

When units in an expression all cancel out -- for example, a ratio of quantities with convertable units -- the value is said to be "unitless".
In `coulomb` the unit expression type `Unitless` represents this particular state.
Here are a few examples of situations when `Unitless` values arise:
```scala
// ratios of convertable unit types are always unitless
scala> (1.withUnit[Yard] / 1.withUnit[Foot]).toUnit[Unitless].show
res1: String = 3 unitless

// raising to the zeroth power
scala> 100.withUnit[Second].pow[0].show
res2: String = 1 unitless

// Radians and other angular units are derived from Unitless
scala> math.Pi.withUnit[Radian].toUnit[Unitless].show
res3: String = 3.141592653589793 unitless

// Percentages
scala> 90D.withUnit[Percent].toUnit[Unitless].show
res4: String = 0.9 unitless
```

#### Unit Prefixes

Unit prefixes are a first-class concept in `coulomb`.
In fact, prefixes are derived units of `Unitless`:
```scala
scala> 1.withUnit[Kilo].toUnit[Unitless].show
res1: String = 1000 unitless

scala> 1.withUnit[Kibi].toUnit[Unitless].show
res2: String = 1024 unitless
```

Because they are just another kind of unit, prefixes work seamlessly with all other units.
```scala
scala> 3.withUnit[Meter %^ 3].toUnit[Kilo %* Liter].showFull
res1: String = 3 kiloliter

scala> 3D.withUnit[Meter %^ 3].toUnit[Mega %* Liter].showFull
res2: String = 0.003 megaliter

scala> (1.withUnit[Kilo] * 1.withUnit[Meter]).toUnit[Meter].showFull
res3: String = 1000 meter

scala> (1D.withUnit[Meter] / 1D.withUnit[Mega]).toUnit[Meter].showFull
res4: String = 1.0E-6 meter
```

The `coulomb` library comes with definitions for the standard [SI prefixes](https://erikerlandson.github.io/coulomb/latest/api/coulomb/siprefix/index.html), and also standard [binary prefixes](https://erikerlandson.github.io/coulomb/latest/api/coulomb/binprefix/index.html).

It is also easy to declare new prefix units using `coulomb.define.PrefixUnit`
```scala
scala> trait Dozen
defined trait Dozen

scala> implicit val defineUnitDozen = PrefixUnit[Dozen](coef = 12, abbv = "doz")
defineUnitDozen: coulomb.define.DerivedUnit[Dozen,coulomb.Unitless] = DerivedUnit(12, dozen, doz)

scala> 1D.withUnit[Dozen %* Inch].toUnit[Foot].show
res1: String = 1.0 ft
```

#### Using `WithUnit`
The `WithUnit` type alias can be used to make unit definitions more readable.  The following two
function definitions are equivalent:
```scala
def f1(duration: Quantity[Float, Second]) = duration + 1f.withUnit[Minute]
def f2(duration: Float WithUnit Second) = duration + 1f.withUnit[Minute]
```

There is a similar `WithTemperature` alias for working with `Temperature` values.

#### Quantity Parsing
The `coulomb` package `coulomb-parser` provides a utility for parsing a quantity expression DSL into
correctly typed `Quantity` values, called `QuantityParser`.
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

#### Type Safe Configurations
As an example of `coulomb` applied to unit type safety for application configuration
settings, the following demonstrates the `coulomb.typesafeconfig` integration of the
[Typesafe `config` package](https://github.com/lightbend/config)
with `coulomb` unit analysis, using `QuantityParser`.

To see this in action, build the examples and load the demo into a REPL:

```scala
% cd /path/to/coulomb
% xsbt coulomb_tests/test:console
Welcome to Scala 2.13.0-M5 (OpenJDK 64-Bit Server VM, Java 1.8.0_201).

scala>

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

#### Temperature Values

In `coulomb` you can also work with [`Temperature` values](https://erikerlandson.github.io/coulomb/latest/api/coulomb/temp/index.html).
A temperature object has a unit type like `Quantity`, but it is constrained to be a unit of temperature, for example
Kelvin, Celsius or Fahrenheit.
Another difference is that `Temperature` values convert between temperature units using the temperature scale offsets.
They are not just quantities of temperature, but temperature values:
```scala
import coulomb._, coulomb.si._, coulomb.temp._

scala> 212f.withTemperature[Fahrenheit].toUnit[Celsius].show
res1: String = 100.0 °C

scala> 0f.withTemperature[Celsius].toUnit[Fahrenheit].show
res2: String = 32.0 °F
```

You can add or subtract a convertable temperature `Quantity` from a `Temperature`, and get a new `Temperature` value.
Conversely, if you subtract one `Temperature` from another, you will get a `Quantity`.
```scala
// Add a quantity to a temperature to get a new temperature
scala> val t1 = 50f.withTemperature[Fahrenheit] + 1f.withUnit[Celsius]
t1: coulomb.temp.Temperature[Float,coulomb.temp.Fahrenheit] = Temperature(51.8)

scala> t1.show
res3: String = 51.8 °F

// subtract a quantity from a temperature to get a new temperature
scala> val t2 = 50f.withTemperature[Fahrenheit] - 1f.withUnit[Celsius]
t2: coulomb.temp.Temperature[Float,coulomb.temp.Fahrenheit] = Temperature(48.2)

scala> t2.toStrFull
res4: String = 48.2 fahrenheit

// subtract two temperatures to get a quantity
scala> val q = t1 - t2
q: coulomb.Quantity[Float,coulomb.temp.Fahrenheit] = Quantity(3.6)

scala> q.show
res5: String = 3.6 °F
```

#### Type Parameter Integrations

Previous topics have focused on how to work with specific `Quantity` and unit expressions.
However, suppose you wish to write your own "generic" functions or classes, where `Quantity` values
have parameterized types? For these situations, `coulomb` provides a set of type-classes that
allow `Quantity` operations to be supported with type parameters.

The `UnitString` type class supports unit names and abbreviations:
```scala
scala> import coulomb.unitops._

scala> def uname[N, U](q: Quantity[N, U])(implicit us: UnitString[U]): String = us.full
uname: [N, U](q: coulomb.Quantity[N,U])(implicit us: coulomb.unitops.UnitString[U])String

scala> uname(3.withUnit[Meter %/ Second])
res0: String = meter/second
```

The `UnitConverter` type class supports unit (and numeric) conversions, in addition to
subtraction, addition, and ordering predicates `<`, `>`, etc:
```scala
scala> def convert[N1, U1, N2, U2](q1: Quantity[N1, U1], q2: Quantity[N2, U2])(implicit
    s1: UnitString[U1], s2: UnitString[U2],
    uc: UnitConverter[N1, U1, N2, U2]) = {
  val r1 = q1.to[N2, U2]
  val r2 = q1 + q2
  (r1.show, r2.show)
}

scala> convert(2f.withUnit[Mile], 1f.withUnit[Kilo %* Meter])
res1: (String, String) = (3.218688 km,2.6213713 mi)
```

The three typeclasses `UnitMultiply`, `UnitDivide` and `UnitPower` support quantity operations
`*`, `/` and `pow`, respectively:
```scala
scala> def operate[N1, U1, N2, U2](q1: Quantity[N1, U1], q2: Quantity[N2, U2])(implicit
    mul: UnitMultiply[N1, U1, N2, U2],
    div: UnitDivide[N1, U1, N2, U2],
    pow: UnitPower[N1, U1, 3]) = {
  val r1 = q1 * q2
  val r2 = q1 / q2
  val r3 = q1.pow[3]
  (r1, r2, r3)
}

scala> val (q1, q2, q3) = operate(3f.withUnit[Meter], 3f.withUnit[Meter])
q1: coulomb.Quantity[Float,coulomb.si.Meter %^ Int(2)] = Quantity(9.0)
q2: coulomb.Quantity[Float,coulomb.Unitless] = Quantity(1.0)
q3: coulomb.Quantity[Float,coulomb.si.Meter %^ Int(3)] = Quantity(27.0)

scala> List(q1.show, q2.show, q3.show)
res3: List[String] = List(9.0 m^2, 1.0 unitless, 27.0 m^3)
```
