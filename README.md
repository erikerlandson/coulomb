### coulomb
A statically typed unit analysis library for Scala

 [ ![Download](https://api.bintray.com/packages/manyangled/maven/coulomb/images/download.svg) ](https://bintray.com/manyangled/maven/coulomb/_latestVersion)

### Documentation
API documentation for `coulomb` is available at: https://erikerlandson.github.io/coulomb/latest/api/

There is also a [tutorial](#tutorial) below.

### How to include `coulomb` in your project

The core `coulomb` package can be included by adding the dependencies shown below.
Note that its two 3rd-party dependencies -- `spire` and `singleton-ops` -- are `%Provided`,
and so you must also include them, if your project does not already do so. The `shapeless`
package is also a dependency, but is included transitively via `spire`.

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb" % "0.3.0",
  "org.typelevel" %% "spire" % "0.16.1",
  "eu.timepit" %% "singleton-ops" % "0.3.1"
)
```

The `coulomb` project also provides a selection of predefined units, which are available as
separate sub-packages.

```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-si-units" % "0.3.0",        // The seven SI units: meter, second, kilogram, etc
  "com.manyangled" %% "coulomb-accepted-units" % "0.3.0",  // Common non-SI metric: liter, centimeter, gram, etc
  "com.manyangled" %% "coulomb-time-units" % "0.3.0",      // minute, hour, day, week
  "com.manyangled" %% "coulomb-info-units" % "0.3.0",      // bit, byte, nat
  "com.manyangled" %% "coulomb-mks-units" % "0.3.0",       // MKS units: Joule, Newton, Watt, Volt, etc
  "com.manyangled" %% "coulomb-customary-units" % "0.3.0", // non-metric units: foot, mile, pound, gallon, pint, etc
  "com.manyangled" %% "coulomb-temp-units" % "0.3.0"       // Celsius and Fahrenheit temperature scales
)
```

This project also provides a parsing facility, `QuantityParser`, that can parse a
unit expression language into properly typed `Quantity` objects. This tool can be
used for extending standard configuration systems with type-safe unit quantities.

```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-parser" % "0.3.0"   // QuantityParser
)
```

### Code of Conduct
The `coulomb` project supports the [typelevel code of conduct](http://typelevel.org/conduct.html);
all contributors are expected to respect this code.
Any violations of this code of conduct should be reported to [the author](https://github.com/erikerlandson/).

### Tutorial

#### Table of Contents

* [Features](#features)
* [`Quantity` and Unit Expressions](#quantity-and-unitexpr)
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
* [Runtime Parsing](#runtime-parsing)
* [Temperature Values](#temperature-values)

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
val mps: Quantity[Double, Meter %/ Second] = Mile(60.0) / Hour(1.0)
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
[class `Quantity`](https://erikerlandson.github.io/coulomb/latest/api/#coulomb.Quantity)
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

#### Runtime Parsing
As a demonstration of `coulomb` applied to improved type safety for application configuration
settings, the `examples` sub-project defines a class `QuantityParser` for
[run-time parsing](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.QuantityParser)
of `Quantity` expressions, and applies it to enhance the
[Typesafe `config` package](https://typesafehub.github.io/config/)
with `coulomb` unit analysis.

To see this in action, build the examples and load the demo into a REPL:

```scala
% cd /path/to/coulomb
% xsbt examples/console
Welcome to Scala 2.11.8 (OpenJDK 64-Bit Server VM, Java 1.8.0_131).
Type in expressions for evaluation. Or try :help.

scala>
```
First import a selection of `coulomb` units, and the demo objects:

```scala
scala> import com.manyangled.coulomb._; import SIBaseUnits._; import InfoUnits._; import SIPrefixes._; import SIAcceptedUnits._; import ChurchInt._; import scala.collection.JavaConverters._

scala> import DemoQP._
```

The demo pre-defines a simple configuration object:
```scala
scala> conf.entrySet().asScala.map { e => s"${e.getKey()} = ${e.getValue()}" }.mkString("\n")
res0: String =
duration = Quoted("60 Second")
bandwidth = Quoted("10 Mega * Byte / Second")
memory = Quoted("100 Giga * Byte")
```

It also defines an implicit enhancement to add a new method `getUnitQuantity`, which transforms
the configuration values into a simple `Quantity` expression, and converts that expression into
the specified representation type and unit:
```scala
scala> conf.getUnitQuantity[Double, Minute]("duration").get.toStrFull
res1: String = 1.0 minute

scala> conf.getUnitQuantity[Int, Mega %* Byte]("memory").get.toStrFull
res2: String = 100000 mega-byte

scala> conf.getUnitQuantity[Float, Giga %* Bit %/ Second]("bandwidth").get.toStrFull
res3: String = 0.08 giga-bit / second
```

If we ask for a unit type that is non-convertable to the configuration, an error is returned:
```scala
scala> conf.getUnitQuantity[Int, Giga %* Bit]("bandwidth")
res5: scala.util.Try[com.manyangled.coulomb.Quantity[Int,com.manyangled.coulomb.%*[com.manyangled.coulomb.SIPrefixes.Giga,com.manyangled.coulomb.InfoUnits.Bit]]] =
Failure(scala.tools.reflect.ToolBoxError: reflective compilation has failed:

non-convertable unit types...
```

#### Temperature Values

In `coulomb` you can also work with [`Temperature` values](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.Temperature).
A temperature object has a unit type like `Quantity`, but it is constrained to be a unit of temperature, for example
Kelvin, Celsius or Fahrenheit.
Another difference is that `Temperature` values convert between temperature units using the temperature scale offsets.
They are not just quantities of temperature, but temperature values:
```scala
import SIAcceptedUnits.Celsius; import USCustomaryUnits.Fahrenheit

scala> 212f.withTemperature[Fahrenheit].toUnit[Celsius].toStrFull
res1: String = 100.0 celsius

scala> 0f.withTemperature[Celsius].toUnit[Fahrenheit].toStrFull
res2: String = 32.0 fahrenheit
```

You can add or subtract a convertable temperature `Quantity` from a `Temperature`, and get a new `Temperature` value.
Conversely, if you subtract one `Temperature` from another, you will get a `Quantity`.
```scala
// Add a quantity to a temperature to get a new temperature
scala> val t1 = 50f.withTemperature[Fahrenheit] + 1f.withUnit[Celsius]
t1: com.manyangled.coulomb.Temperature[com.manyangled.coulomb.USCustomaryUnits.Fahrenheit] = Temperature(51.8)

scala> t1.toStrFull
res19: String = 51.8 fahrenheit

// subtract a quantity from a temperature to get a new temperature
scala> val t2 = 50f.withTemperature[Fahrenheit] - 1f.withUnit[Celsius]
t2: com.manyangled.coulomb.Temperature[com.manyangled.coulomb.USCustomaryUnits.Fahrenheit] = Temperature(48.2)

scala> t2.toStrFull
res22: String = 48.2 fahrenheit

// subtract two temperatures to get a quantity
scala> val q = t1 - t2
q: com.manyangled.coulomb.Quantity[com.manyangled.coulomb.USCustomaryUnits.Fahrenheit] = Quantity(3.6)

scala> q.toStrFull
res23: String = 3.6 fahrenheit
```
