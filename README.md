### coulomb
A statically typed unit analysis library for Scala

`coulomb` is still under construction, but I hope to have a consumable release soon.  Feel free to play with it in the mean time! I'll keep the head of `develop` branch unbroken.

### Documentation
API documentation for `coulomb` is available at: https://erikerlandson.github.io/coulomb/latest/api/

The key user-facing type, `Quantity`, is [documented here](https://erikerlandson.github.io/coulomb/latest/api/#com.manyangled.coulomb.Quantity)

##### Why name it `coulomb` ?
`coulomb` is a library for "static units", and 'coulomb' is the "unit of static" (aka charge).

### Tutorial

#### Motivation

The motivation for `coulomb` is to support the following features:

1. allow a programmer to assocate unit analysis with values, in the form of static types
  1. `val length = Quantity[Meter](1)`
1. express those types with arbitrary and natural static type expressions
  1. `val speed = Quantity[(Kilo <-> Meter) </> Hour](100.0)`
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

#### examples

```scala
scala> import com.manyangled.coulomb._; import ChurchInt._; import SIBaseUnits._; import SIPrefixes._; import SIAcceptedUnits._; import USCustomaryUnits._; import extensions._
import com.manyangled.coulomb._
import ChurchInt._
import SIBaseUnits._
import SIPrefixes._
import SIAcceptedUnits._
import USCustomaryUnits._
import extensions._

scala> 1.withUnit[Liter].as[Meter <^> _3].str
res1: String = 0.001 meter ^ 3

scala> (1.withUnit[Foot] + 1.withUnit[Yard]).str
res2: String = 4.0 foot

scala> (1.withUnit[Foot] + 1.withUnit[Meter]).str
res3: String = 4.2808398950131235 foot

scala> (1000.withUnit[Meter] + 1.withUnit[Kilo <-> Meter]).str
res4: String = 2000.0 meter

scala> 5280.withUnit[Foot].as[Kilo <-> Meter].str
res5: String = 1.609344 kilo-meter

scala> (2.withUnit[Meter] * 2.withUnit[Meter <^> _2]).str
res6: String = 4.0 meter ^ 3

scala> (2.withUnit[Meter] * 2.withUnit[Meter <^> _2]).as[Liter].str
res8: String = 4000.0 liter

scala> (2.withUnit[Meter] * 2.withUnit[Meter <^> _2]).as[Kilo <-> Liter].str
res9: String = 4.0 kilo-liter

scala> (100.withUnit[Meter] / 9.withUnit[Second]).str
res10: String = 11.11111111111111 (meter ^ 1) * (second ^ -1)

scala> (11.withUnit[Meter </> Second] * 1.withUnit[Minute]).as[Yard].str
res11: String = 721.7847769028872 yard

scala> (9.8).withUnit[Meter </> (Second <^> _2)].as[Foot </> (Second <^> _2)].str
res12: String = 32.15223097112861 foot / (second ^ 2)

scala> (1.withUnit[Yard] / 1.withUnit[Foot]).str
res13: String = 3.0 unitless
```

