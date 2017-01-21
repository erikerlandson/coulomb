### coulomb
A statically typed unit analysis library for Scala

`coulomb` is still under construction, but I hope to have a consumable release soon.  Feel free to play with it in the mean time! I'll keep the head of `develop` branch unbroken.

### Documentation
API documentation for `coulomb` is available at: https://erikerlandson.github.io/coulomb/latest/api/

The key user-facing type, `Quantity`, is [documented here](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.Quantity)

##### Why name it `coulomb` ?
`coulomb` is a library for "static units", and 'coulomb' is the "unit of static" (aka charge).

### Tutorial

#### Table of Contents

* [Motivation](#motivation)
* [Quantity and UnitExpr](#quantity-and-unitexpr)
* [Quantity Values](#quantity-values)

#### Motivation

The motivation for `coulomb` is to support the following features:

1. allow a programmer to assocate unit analysis with values, in the form of static types
  1. `val length = 10.withUnit[Meter]`
  1. `val duration = Second(30.0)`
  1. `val mass = Quantity[Kilogram](100)`
1. express those types with arbitrary and natural static type expressions
  1. `val speed = Quantity[(Kilo <*> Meter) </> Hour](100.0)`
  1. `val acceleration = Quantity[Meter </> (Second <^> _2)](9.8)`
1. let the compiler determine which unit expressions are equivalent (aka _compatible_) and transparently convert beween them
  1. `val mps: Quantity[Meter </> Second] = Quantity[Mile </> Hour](60.0)`
1. cause compile-time error when operations are attempted with _incompatible_ unit types
  1. `val mps: Quantity[Meter </> Second] = Quantity[Mile](60.0) // compile-time type error!`
1. automatically determine correct output unit types for operations on unit quantities
  1. `val mps: Quantity[Meter </> Second] = Mile(60) / Hour()`
1. allow a programmer to easily declare new units that will work seamlessly with existing units
  1. `// a new length:`
  1. `trait Smoot extends DerivedType[Inch]`
  1. `object Smoot extends UnitCompanion[Smoot]("smoot", 67.0)`
  1. `// a unit of acceleration:`
  1. `trait EarthGravity extends DerivedType[Meter </> (Second <^> _2)]`
  1. `object EarthGravity extends UnitCompanion[EarthGravity]("g", 9.8)`

#### `Quantity` and `UnitExpr`

`coulomb` defines the [class `Quantity`](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.Quantity) for representing values with associated units.
These units are represented by a type parameter of `Quantity`, which is some sub-trait of [`UnitExpr`](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.UnitExpr).
Here are some simple declarations of `Quantity` objects:

```scala
import com.manyangled.coulomb._
import extensions._
import SIBaseUnits._

val length = 10.withUnit[Meter]
val duration = Second(30.0)
val mass = Quantity[Kilogram](100)
```

The `UnitExpr` trait hierarchy provides three operator types for building more complex unit types: `<*>`, `</>`, and `<^>`:

```scala
import ChurchInt._   // exponents are represented as Church integer types _1, _2, ...
import SIPrefixes._

val area = Quantity[Meter <*> Meter](100)      // unit product
val speed = Quantity[Meter </> Second](10)     // unit ratio
val volume = Quantity[Meter <^> _3](50)        // unit power
```

Using these operators, a `UnitExpr` can be composed into unit type expressions of arbitrary complexity.

```scala
val acceleration = Quantity[Meter </> (Second <^> _2)](9.8)
val ohms = Quantity[(Kilogram <*> (Meter <^> _2)) </> ((Second <^> _3) <*> (Ampere <^> _2))](0.01)
```

#### Quantity Values

The internal representation of a `Quantity` is opaque; however in the current design `Quantity` values are represented internally as `Double`.
A quantity's value can be obtained in any of the standard Scala numeric types `Int`, `Long`, `Float` and `Double`:

```scala
val memory = 100.withUnit[Giga <*> Byte>]

val memInt: Int = memory.toInt
val memLong: Long = memory.toLong
val memFloat: Float = memory.toFloat
val memDouble: Double = memory.toDouble
```

#### The `str` And `unitStr` Method

The `str` method can be used to obtain a human-readable string that represents a quantity's type and value.
The `unitStr` method returns a similar string representing just the unit type:

```scala
scala> val bandwidth = Quantity[(Giga <*> Bit) </> Second](10)
bandwidth: com.manyangled.coulomb.Quantity[...] = com.manyangled.coulomb.Quantity@40240000

scala> bandwidth.str
res0: String = 10.0 (giga-bit) / second

scala> bandwidth.unitStr
res1: String = (giga-bit) / second
```

#### Unit Exponents and Church Integers

The `coulomb` library allows unit expression types to include integer exponents.
Representing integers in the type system is accomplished using a type encoding of [Church numerals](https://en.wikipedia.org/wiki/Church_encoding).
In `coulomb`, these are defined using the [`ChurchInt` type](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.ChurchInt), which pre-defines integer type "constants" `_0`, `_1`, `_2` ... `_9` and negative integers `_neg1`, `_neg2`, ... `_neg9`.
(however, the `ChurchInt` type is not bounded, and can represent values outside this range, for example `_9#Add[_9]` or `_neg9#Mul[_9]`)
The following examples demonstrate typical uses of type exponents in unit expressions:

```scala
import com.manyangled.coulomb._
import SIBaseUnits._
import ChurchInt._

val frequency = Quantity[Second <^> _neg1](60)
val volume = Quantity[Meter <^> _3](1.5)
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

#### Unit Types and Compatibility

The concept of unit _compatibility_ is fundamental to the `coulomb` library and its implementation of unit analysis.
Two unit type expressions are _compatible_ if they encode an equivalent "[abstract quantity](https://en.wikipedia.org/wiki/International_System_of_Quantities)."
For example, `Meter` and `Mile` are compatible because they both encode the abstract quantity of Length.
`Foot <^> _3` and `Liter` are compatible because they both encode a volume, or Length^3.
`Kilo <*> Meter </> Hour` and `Foot <*> (Second <^> _neg1)` are compatible because they encode a velocity, or Length\*Time^-1.

If two unit types are compatible, then they can be converted.
Meters can always be converted to miles, and cubic feet can be converted to liters, etc.
In `coulomb`, a unit quantity will be implicitly converted into a quantity of a different unit type whenever those types are compatible.
Any attempt to convert between _incompatible_ unit types results in a compile-time type error.

```scala
scala> def foo(q: Quantity[Meter </> Second]) = q.str

scala> foo(60.withUnit[Mile </> Hour])
res3: String = 26.8224 meter / second

scala> foo(1.withUnit[Mile <*> (Minute <^> _neg1)])
res5: String = 26.8224 meter / second

scala> foo(1.withUnit[Foot </> Day])
res6: String = 3.527777777777778E-6 meter / second

scala> foo(1.withUnit[Foot <*> Day])
<console>:37: error: type mismatch;
```

#### Unit Conversions

As described in the previous section, unit quantities can be converted from one unit type to another when the two types are compatible.
Unit conversions come in a few different forms:
```scala
// Implicit conversion
scala> val vol: Quantity[Meter <^> _3] = Liter(4000)
vol: com.manyangled.coulomb.Quantity[...] = com.manyangled.coulomb.Quantity@40100000

scala> vol.str
res0: String = 4.0 meter ^ 3

// Explicit conversion using the `as` method
scala> Liter(4000).as[Meter <^> _3].str
res1: String = 4.0 meter ^ 3

// Creation of a conversion function using the `converter` factory function:
scala> val f = Quantity.converter[Liter, Meter <^> _3]
f: com.manyangled.coulomb.Quantity[...] => com.manyangled.coulomb.Quantity[...] = <function1>

scala> f(Liter(4000)).str
res2: String = 4.0 meter ^ 3
```

#### Unit Operations

Unit quantities support math operations `+`, `-`, `*`, `/`, and `pow`.
Quantities must be of compatible unit types to be added or subtracted.
The unit of the left-hand argument is taken as the unit of the output:
```scala
scala> (Foot() + Yard()).str
res0: String = 4.0 foot

scala> (Foot(4) - Yard()).str
res1: String = 1.0 foot
```

Quantities of any unit types may be multipied or divided.
Result types are different than either argument, and are expressed in terms of Base units:
```scala
scala> (Mile(60) / Hour()).str
res0: String = 26.8224 meter / second

scala> (Yard() * Yard()).str
res1: String = 0.83612736 meter ^ 2

scala> (Yard() / Inch()).str
res2: String = 36.0 unitless
```

When raising a unit to a power, the exponent is given as a type, in Church integer representation:
```scala
scala> Meter(3).pow[_2].str
res25: String = 9.0 meter ^ 2

scala> Meter(3).pow[_neg1].str
res26: String = 0.3333333333333333 meter ^ -1

scala> Meter(3).pow[_0].str
res27: String = 1.0 unitless
```

#### Declaring New Units

The `coulomb` library strives to make it easy to add new units which work seamlessly with the unit analysis type system.
There are two varieties of unit declaration: _base units_ and _derived units_.

A base unit, as its name suggests, is not defined in terms of any other unit; it is axiomatic.
The Standard International [Base Units](https://en.wikipedia.org/wiki/SI_base_unit) are all declared as base units in the `coulomb` [`SIBaseUnits` subpackage](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIBaseUnits$).
In the [`InfoUnits` sub-package](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.InfoUnits$), `Byte` is declared as the base unit of information.
Declaring a base unit is special in the sense that it also defines a new kind of fundamental _abstract quantity_.
For example, by declaring `Meter` as a base unit, `coulomb` establishes `Meter` as the canonical representation of the abstract quantity of Length.
Any other unit of Length must be declared as a _derived unit_, or it would be considered _incompatible_ with other lengths.

Here is an example of defining a new base unit `Scoville`, representing an abstract quantity of [Spicy Heat](https://en.wikipedia.org/wiki/Scoville_scale):
```scala
object SpiceUnits {
  trait Scoville extends BaseUnit
  object Scoville extends UnitCompanion[Scoville]("scoville")
}
```

The second variety of unit declarations is the _derived_ unit, which is defined in terms of some unit expression involving previously-defined units.
Derived units do _not_ define new kinds of abstract quantity, and are generally more common than base units:
```scala
object NewUnits {
  import SIBaseUnits._
  import USCustomaryUnits.Foot
  
  // a furlong is 660 feet
  trait Furlong extends DerivedUnit[Foot]
  object Furlong extends UnitCompanion[Furlong]("furlong", 660.0)
  
  // speed of sound is 1130 feet/second (at sea level, 20C)
  trait Mach extends DerivedUnit[Foot </> Second]
  object Mach extends UnitCompanion[Mach]("mach", 1130.0)
  
  // a standard earth gravity is 9.807 meters per second-squared
  trait EarthGravity extends DerivedUnit[Meter </> (Second <^> _2)]
  object EarthGravity extends UnitCompanion[EarthGravity]("earthgravity", 9.807)
}
```

#### Unitless Quantities

When units in an expression all cancel out -- for example, a ratio of quanties with compatible units -- the value is said to be "unitless."
In `coulomb` the unit expression subtype `Unitless` represents this particular state.
Here are a few examples of situations when `Unitless` values arise:
```scala
// ratios of compatible unit types are always unitless
scala> (1.withUnit[Yard] / 1.withUnit[Foot]).str
res1: String = 3.0 unitless

// raising to the zeroth power
scala> 100.withUnit[Second].pow[_0].str
res2: String = 1.0 unitless

// Radians and other angular units are derived from Unitless
scala> math.Pi.withUnit[Radian].as[Unitless].str
res3: String = 3.141592653589793 unitless
```

#### Unit Prefixes

Unit prefixes are "first-class" objects in `coulomb`.
In fact, prefixes are derived units of `Unitless`:
```scala
scala> Kilo().as[Unitless].str
res1: String = 1000.0 unitless

scala> Kibi().as[Unitless].str
res2: String = 1024.0 unitless
```

Because they are just another kind of derived unit, prefixes work seamlessly with all other units.
```scala
scala> Quantity[Meter <^> _3](3).as[Kilo <*> Liter].str
res1: String = 3.0 kilo-liter

scala> Quantity[Meter <^> _3](3).as[Mega <*> Liter].str
res2: String = 0.003 mega-liter

scala> (Kilo() * Meter()).str
res3: String = 1000.0 meter

scala> (Meter() / Mega()).str
res4: String = 1.0E-6 meter
```

The `coulomb` library comes with definitions for the standard [SI prefixes](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.SIPrefixes$), and also standard [binary prefixes](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.BinaryPrefixes$).
It is also easy to declare new prefix units:
```scala
trait Dozen extends PrefixUnit
object Dozen extends UnitCompanion[Dozen]("dozen", 12.0)
```

#### Runtime Parsing

`coulomb` supplies a class `QuantityParser` for [run-time parsing](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.QuantityParser) of `Quantity` objects.
This section demonstrates `QuantityParser` with an application to enhancing the [Typesafe `config` package](https://typesafehub.github.io/config/) with `coulomb` unit analysis.

First import a selection of `coulomb` units, and the `ConfigFactory` for generating `Config` objects.
We generate a simple configuration with entries for "duration", "memory" and "bandwidth".
However, the values for these entries are expressions that evaluate to `Quantity` objects:

```scala
scala> import com.manyangled.coulomb._; import SIBaseUnits._; import SIPrefixes._; import InfoUnits._; import extensions._
import com.manyangled.coulomb._
import SIBaseUnits._
import SIPrefixes._
import InfoUnits._
import extensions._

scala> import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigFactory

scala> val conf = ConfigFactory.parseString("""
     | "duration" = "60.withUnit[Second]"
     | "memory" = "100.withUnit[Giga <*> Byte]"
     | "bandwidth" = "10.withUnit[Mega <*> Byte </> Second]"
     | """)
conf: com.typesafe.config.Config = Config(SimpleConfigObject({"bandwidth":"10.withUnit[Mega <*> Byte </> Second]","duration":"60.withUnit[Second]","memory":"100.withUnit[Giga <*> Byte]"}))
```

In order to make use of these `Quantity` expressions in a configuration we must enhance `Config` with a new method `getUnitQuantity`, which we do via an `implicit class` pattern below.
The contents of this enhancement class are simple: A `QuantityParser` object is declared that imports `SIBaseUnits._`, `SIPrefixes._` and `InfoUnits._`.
The `getUnitQuantity` method is just a call to the `apply` method of the `QuantityParser`, that passes the configured value to the parser, along with an expected `UnitExpr` type.
If this type is compatible with the run-time evaluation of the string expression, it will be converted in the usual manner.
If the expected unit is incompatible, an error will be returned.

```scala
scala> object enhance {
     |   implicit class WithUnits(conf: com.typesafe.config.Config) {
     |     val qp = QuantityParser(Seq(SIBaseUnits, SIPrefixes, InfoUnits))
     |     def getUnitQuantity[U <: UnitExpr :scala.reflect.runtime.universe.TypeTag](key: String) =
     |       qp[U](conf.getString(key))
     |   }
     | }
defined object enhance

scala> import enhance._
import enhance._
```

Lastly, we demonstrate the new enhancement, by invoking the `getUnitQuantity` method to evaluate and convert our three config settings:

```scala

scala> conf.getUnitQuantity[SIAcceptedUnits.Minute]("duration").get.str
res0: String = 1.0 minute

scala> conf.getUnitQuantity[Mega <*> Byte]("memory").get.str
res1: String = 100000.0 mega-byte

scala> conf.getUnitQuantity[Giga <*> Bit </> Second]("bandwidth").get.str
res2: String = 0.08 (giga-bit) / second
```

If we ask for a unit type that is incompatible with the configuration, an error is returned:

```scala
scala> conf.getUnitQuantity[Giga <*> Bit </> Meter]("bandwidth")
res3: scala.util.Try[com.manyangled.coulomb.Quantity[...]] =
Failure(scala.tools.reflect.ToolBoxError: reflective compilation has failed:

Implicit not found: CompatUnits[com.manyangled.coulomb.</>[com.manyangled.coulomb.<*>[com.manyangled.coulomb.SIPrefixes.Mega,com.manyangled.coulomb.InfoUnits.Byte],com.manyangled.coulomb.SIBaseUnits.Second], com.manyangled.coulomb.</>[com.manyangled.coulomb.<*>[com.manyangled.coulomb.SIPrefixes.Giga,com.manyangled.coulomb.InfoUnits.Bit],com.manyangled.coulomb.SIBaseUnits.Meter]]...
```

#### Temperature Values

In `coulomb` you can also work with [`Temperature` values](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.Temperature).
A temperature object has a unit type like `Quantity`, but it is constrained to be a unit of temperature, for example
Kelvin, Celsius or Fahrenheit.
Another difference is that `Temperature` values convert between temperature units using the temperature scale offests.
They are not just quantities of temperature, but temperature values:
```scala
import SIAcceptedUnits.Celsius; import USCustomaryUnits.Fahrenheit

scala> 212.withTemperature[Fahrenheit].as[Celsius].str
res1: String = 100.0 celsius

scala> 0.withTemperature[Celsius].as[Fahrenheit].str
res2: String = 32.0 fahrenheit
```

You can add or subtract a compatible temperature `Quantity` from a `Temperature`, and get a new `Temperature` value.
Conversely, if you subtract one `Temperature` from another, you will get a `Quantity`.
```scala
// Add a quantity to a temperature to get a new temperature
scala> val t1 = 50.withTemperature[Fahrenheit] + 1.withUnit[Celsius]
t1: com.manyangled.coulomb.Temperature[com.manyangled.coulomb.USCustomaryUnits.Fahrenheit] = com.manyangled.coulomb.Temperature@262f8000

scala> t1.str
res19: String = 51.8 fahrenheit

// subtract a quantity from a temperature to get a new temperature
scala> val t2 = 50.withTemperature[Fahrenheit] - 1.withUnit[Celsius]
t2: com.manyangled.coulomb.Temperature[com.manyangled.coulomb.USCustomaryUnits.Fahrenheit] = com.manyangled.coulomb.Temperature@d9d18003

scala> t2.str
res22: String = 48.2 fahrenheit

// subtract two temperatures to get a quantity
scala> val q = t1 - t2
q: com.manyangled.coulomb.Quantity[com.manyangled.coulomb.USCustomaryUnits.Fahrenheit] = com.manyangled.coulomb.Quantity@8cc000ac

scala> q.str
res23: String = 3.6 fahrenheit
```
