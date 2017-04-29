### coulomb
A statically typed unit analysis library for Scala

 [ ![Download](https://api.bintray.com/packages/manyangled/maven/coulomb/images/download.svg) ](https://bintray.com/manyangled/maven/coulomb/_latestVersion)

##### Why name it `coulomb`?
`coulomb` is a library for "static units", and 'coulomb' is the "unit of static" (aka charge).

### Documentation
API documentation for `coulomb` is available at: https://erikerlandson.github.io/coulomb/latest/api/

There is also a [tutorial](#tutorial) below.

### How to include `coulomb` in your project
```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies += "com.manyangled" %% "coulomb" % "0.1.0"
```

### Tutorial

#### Table of Contents

* [Features](#features)
* [Quantity and UnitExpr](#quantity-and-unitexpr)
* [Quantity Values](#quantity-values)
* [String Representations](#string-representations)
* [Unit Exponents and Church Integers](#unit-exponents-and-church-integers)
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

Allow a programmer to assocate unit analysis with values, in the form of static types
```scala
val length = 10.withUnit[Meter]
val duration = Second(30.0)
val mass = new Quantity[Float, Kilogram](100)
```
Express those types with arbitrary and natural static type expressions
```scala
val speed = (100.0).withUnit[(Kilo %* Meter) %/ Hour]
val acceleration = (9.8).withUnit[Meter %/ (Second %^ _2)]
```
Let the compiler determine which unit expressions are equivalent (aka _convertable_)
and transparently convert beween them
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
// a new length:
@UnitDecl("smoot", 67)
trait Smoot extends DerivedType[Inch]
// a unit of acceleration:
@UnitDecl("earthgravity", 9.8, "g")
trait EarthGravity extends DerivedType[Meter %/ (Second %^ _2)]
```

#### `Quantity` and `UnitExpr`

`coulomb` defines the
[class `Quantity`](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.Quantity)
for representing values with associated units.
Quantities are represented by their two type parameters: A numeric representation parameter `N`
(e.g. Int or Double) and a unit type `U` which is a sub-type of
[`UnitExpr`](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.UnitExpr).
Here are some simple declarations of `Quantity` objects:
```scala
import com.manyangled.coulomb._
import SIBaseUnits._

val length = 10.withUnit[Meter]                // An Int value of meters
val duration = Second(30.0)                    // a Double value in seconds
val mass = new Quantity[Float, Kilogram](100)  // a Float value in kg
```

The `UnitExpr` trait hierarchy provides three operator types for building more complex unit types:
`%*`, `%/`, and `%^`.
```scala
import com.manyangled.coulomb._
import ChurchInt._   // integer exponents are represented as types _1, _2, ...
import SIBaseUnits._
import SIPrefixes._

val area = 100.withUnit[Meter %* Meter]    // unit product
val speed = 10.withUnit[Meter %/ Second]   // unit ratio
val volume = 50.withUnit[Meter %^ _3]      // unit power
```

Using these operators, a `UnitExpr` can be composed into unit type expressions of arbitrary
complexity.
```scala
val acceleration = (9.8).withUnit[Meter %/ (Second %^ _2)]
val ohms = (0.01).withUnit[(Kilogram %* (Meter %^ _2)) %/ ((Second %^ _3) %* (Ampere %^ _2))]
```

#### Quantity Values
The internal representation type of a `Quantity` is given by its first type parameter.
Each quantity's value is accessible via the `value` field
```scala
val memory = 100.withUnit[Giga %* Byte]  // type is: Quantity[Int, Giga %* Byte]
val raw: Int = memory.value              // memory's raw integer value
```

Standard Scala types `Float`, `Double`, `Int` and `Long` are supported, as well as `BigInt`,
`BigDecimal`, `Byte` and `Short`. Additionally, the [spire](https://github.com/non/spire)
numeric types `Rational`, `Algebraic`, `Real` and `Number` are supported.

#### String representations
The `toStr` method can be used to obtain a human-readable string that represents a quantity's
type and value using standard unit abbreviations.  The `toStrFull` method uses full unit names.
The methods `unitStr` and `unitStrFull` output only the unit without the value.
```scala
scala> val bandwidth = 10.withUnit[(Giga %* Bit) %/ Second]
bandwidth: com.manyangled.coulomb.Quantity[...] = com.manyangled.coulomb.Quantity@40240000

scala> bandwidth.toStr
res1: String = 10 Gb / s

scala> bandwidth.toStrFull
res2: String = 10 giga-bit / second

scala> bandwidth.unitStr
res3: String = Gb / s

scala> bandwidth.unitStrFull
res4: String = giga-bit / second
```

#### Unit Exponents and Church Integers
The `coulomb` library allows unit expression types to include integer exponents.
Representing integers in the type system is accomplished using a type encoding of
[Church numerals](https://en.wikipedia.org/wiki/Church_encoding).
In `coulomb`, these are defined using the
[`ChurchInt` type](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.ChurchInt),
which pre-defines integer type "constants" `_0`, `_1`, `_2` ... `_9` and negative integers
`_neg1`, `_neg2`, ... `_neg9`.
(the `ChurchInt` type is not bounded, and can represent values outside this range,
for example `_9#Add[_9]` or `_neg9#Mul[_9]`)
The following examples demonstrate typical uses of type exponents in unit expressions:
```scala
import com.manyangled.coulomb._
import SIBaseUnits._
import ChurchInt._

val frequency = 60.withUnit[Second %^ _neg1]
val volume = (1.5).withUnit[Meter %^ _3]
```

#### Predefined Units
The `coulomb` library pre-defines a variety of units and prefixes, which are summarized here:

* [SIBaseUnits](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIBaseUnits$): The Standard International [base units](https://en.wikipedia.org/wiki/International_System_of_Units#Base_units) Meter, Kilogram, Second, Ampere, Kelvin, Mole and Candela.
* [SIPrefixes](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIPrefixes$): Standard International [prefixes](https://en.wikipedia.org/wiki/International_System_of_Units#Prefixes) Kilo, Mega, Milli, Micro, etc.
* [MKSUnits](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.MKSUnits$): The common "Meter-Kilogram-Second" [derived units](http://scienceworld.wolfram.com/physics/MKS.html).
* [SIAcceptedUnits](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIAcceptedUnits$): A selection of units [accepted](https://en.wikipedia.org/wiki/Non-SI_units_mentioned_in_the_SI) by the Standard International system.
* [InfoUnits](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.InfoUnits$): Units of information: Byte, Bit and Nat.
* [USCustomaryUnits](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.USCustomaryUnits$): Some [customary non-SI units](https://en.wikipedia.org/wiki/United_States_customary_units) commonly used in the United States.
* [BinaryPrefixes](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.BinaryPrefixes$): The [binary prefixes](https://en.wikipedia.org/wiki/Binary_prefix) Kibi, Mebi, Gibi, etc.

#### Unit Types and Convertability
The concept of unit _convertability_ is fundamental to the `coulomb` library and its
implementation of unit analysis.
Two unit type expressions are _convertable_ if they encode an equivalent
"[abstract quantity](https://en.wikipedia.org/wiki/International_System_of_Quantities)."
For example, `Meter` and `Mile` are convertable because they both encode the abstract quantity of Length.
`Foot %^ _3` and `Liter` are convertable because they both encode a volume, or Length^3.
`Kilo %* Meter %/ Hour` and `Foot %* (Second %^ _neg1)` are convertable because they encode a velocity, or Length\*Time^-1.

In `coulomb`, a unit quantity will be implicitly converted into a quantity of a different unit type whenever those types are convertable.
Any attempt to convert between _non-convertable_ unit types results in a compile-time type error.

```scala
scala> def foo(q: Quantity[Double, Meter %/ Second]) = q.toStrFull

scala> foo(60f.withUnit[Mile %/ Hour])
res5: String = 26.8224 meter / second

scala> foo(1f.withUnit[Mile %/ Minute])
res6: String = 26.8224 meter / second

scala> foo(1f.withUnit[Foot %/ Day])
res7: String = 3.5277778E-6 meter / second

scala> foo(1f.withUnit[Foot %* Day])
<console>:40: error: non-convertable unit types:
```

#### Unit Conversions
As described in the previous section, unit quantities can be converted from one unit type to another when the two types are convertable.
Unit conversions come in a few different forms:
```scala
// Implicit conversion
scala> val vol: Quantity[Double, Meter %^ _3] = Liter(4000.0)
vol: com.manyangled.coulomb.Quantity[...] = Quantity(4.0)

scala> vol.toStrFull
res0: String = 4.0 meter ^ 3

// Explicit conversion using the `toUnit` method
scala> Liter(4000.0).toUnit[Meter %^ _3].toStrFull
res1: String = 4.0 meter ^ 3

// Creation of a conversion function using the `converter` factory function:
scala> val f = Quantity.converter[Double, Liter, Meter %^ _3]

scala> f(Liter(4000.0)).toStrFull
res2: String = 4.0 meter ^ 3
```

#### Unit Operations
Unit quantities support math operations `+`, `-`, `*`, `/`, and `pow`.
Quantities must be of convertable unit types to be added or subtracted.
The unit of the left-hand argument is taken as the unit of the output:
```scala
scala> (Foot(1) + Yard(1)).toStr
res12: String = 4 ft

scala> (Foot(4) - Yard(1)).toStr
res13: String = 1 ft
```

Quantities of any unit types may be multipied or divided.
Result types are different than either argument:
```scala
scala> (Mile(60) / Hour(1)).toStr
res14: String = 60 mi / h

scala> (Yard(1) * Yard(1)).toStr
res15: String = 1 yd^2

scala> (Yard(1) / Inch(1)).toStr
res16: String = 1 yd / in

scala> (Yard(1) / Inch(1)).toUnit[Percent].toStr
res17: String = 3600 %
```

When raising a unit to a power, the exponent is given as a type, in Church integer representation:
```scala
scala> Meter(3.0).pow[_2].toStrFull
res25: String = 9.0 meter ^ 2

scala> Meter(Rational(3)).pow[_neg1].toStrFull
res26: String = 1/3 meter ^ -1

scala> Meter(3).pow[_0].toStrFull
res27: String = 1 unitless
```

#### Declaring New Units
The `coulomb` library strives to make it easy to add new units which work seamlessly with the unit analysis type system.
There are two varieties of unit declaration: _base units_ and _derived units_.

A base unit, as its name suggests, is not defined in terms of any other unit; it is axiomatic.
The Standard International [Base Units](https://en.wikipedia.org/wiki/SI_base_unit) are all declared as base units in the `coulomb` [`SIBaseUnits` subpackage](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIBaseUnits$).
In the [`InfoUnits` sub-package](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.InfoUnits$), `Byte` is declared as the base unit of information.
Declaring a base unit is special in the sense that it also defines a new kind of fundamental _abstract quantity_.
For example, by declaring `Meter` as a base unit, `coulomb` establishes `Meter` as the canonical representation of the abstract quantity of Length.
Any other unit of Length must be declared as a _derived unit_, or it would be considered _non-convertable_ with other lengths.

Here is an example of defining a new base unit `Scoville`, representing an abstract quantity of [Spicy Heat](https://en.wikipedia.org/wiki/Scoville_scale):
```scala
object SpiceUnits {
  // conversion coefficient defaults to 1.  Include a standard abbreviation "sco"
  @UnitDecl("scoville", abbv = "sco")
  trait Scoville extends BaseUnit
}
```

The second variety of unit declarations is the _derived_ unit, which is defined in terms of some unit expression involving previously-defined units.
Derived units do _not_ define new kinds of abstract quantity, and are generally more common than base units:
```scala
object NewUnits {
  import SIBaseUnits._
  import USCustomaryUnits.Foot

  // a furlong is 660 feet
  @UnitDecl("furlong", 660)
  trait Furlong extends DerivedUnit[Foot]

  // speed of sound is 1130 feet/second (at sea level, 20C)
  @UnitDecl("mach", 1130)
  trait Mach extends DerivedUnit[Foot %/ Second]

  // a standard earth gravity is 9.807 meters per second-squared
  // Define an abbreviation "g"
  @UnitDecl("earthgravity", 9.807, "g")
  trait EarthGravity extends DerivedUnit[Meter %/ (Second %^ _2)]
}
```

Due to certain Scala compiler [behaviors](https://github.com/scala/scala-dev/issues/353),
unit definitions must be fully compiled before they can be used.
Pragmatically, this means that new units should either be defined in a separate project,
or in a [sub-project](http://www.scala-sbt.org/0.13/docs/Multi-Project.html)
configured to compile before the calling code.

#### Unitless Quantities

When units in an expression all cancel out -- for example, a ratio of quanties with convertable units -- the value is said to be "unitless."
In `coulomb` the unit expression subtype `Unitless` represents this particular state.
Here are a few examples of situations when `Unitless` values arise:
```scala
// ratios of convertable unit types are always unitless
scala> (1.withUnit[Yard] / 1.withUnit[Foot]).toUnit[Unitless].toStrFull
res1: String = 3 unitless

// raising to the zeroth power
scala> 100.withUnit[Second].pow[_0].toStrFull
res2: String = 1 unitless

// Radians and other angular units are derived from Unitless
scala> math.Pi.withUnit[Radian].toUnit[Unitless].toStrFull
res3: String = 3.141592653589793 unitless
```

#### Unit Prefixes

Unit prefixes are "first-class" objects in `coulomb`.
In fact, prefixes are derived units of `Unitless`:
```scala
scala> Kilo(1).toUnit[Unitless].toStrFull
res1: String = 1000 unitless

scala> Kibi(1).toUnit[Unitless].toStrFull
res2: String = 1024 unitless
```

Because they are just another kind of derived unit, prefixes work seamlessly with all other units.
```scala
scala> 3.withUnit[Meter %^ _3].toUnit[Kilo %* Liter].toStrFull
res1: String = 3 kilo-liter

scala> 3D.withUnit[Meter %^ _3].toUnit[Mega %* Liter].toStrFull
res2: String = 0.003 mega-liter

scala> (Kilo(1) * Meter(1)).toUnit[Meter].toStrFull
res3: String = 1000 meter

scala> (Meter(1D) / Mega(1D)).toUnit[Meter].toStrFull
res4: String = 1.0E-6 meter
```

The `coulomb` library comes with definitions for the standard [SI prefixes](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIPrefixes$), and also standard [binary prefixes](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.BinaryPrefixes$).
It is also easy to declare new prefix units:
```scala
@UnitDecl("dozen", 12, "doz")
trait Dozen extends PrefixUnit
```

#### Using `WithUnit`
The `WithUnit` type alias can be used to make unit definitions more readable.  The following two
function definitions are equivalent:
```scala
def f1(duration: Quantity[Float, Second]) = duration + Minute(1f)
def f2(duration: Float WithUnit Second) = duration + Minute(1f)
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
Another difference is that `Temperature` values convert between temperature units using the temperature scale offests.
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
