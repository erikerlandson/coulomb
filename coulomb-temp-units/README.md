# Temperature Units in coulomb

To learn more about coulomb in general refer to the
[coulomb tutorial](../README.md#tutorial)

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions.

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  // coulomb libraries used by temperature units
  "com.manyangled" %% "coulomb" % "0.5.0",
  "com.manyangled" %% "coulomb-si-units" % "0.5.0",
  "com.manyangled" %% "coulomb-temp-units" % "0.5.0",
  // coulomb external %Provided deps
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.5.0"
)
```

### Examples

In `coulomb` you can also work with [`Temperature` values](https://erikerlandson.github.io/coulomb/latest/api/coulomb/temp/index.html).
A temperature object has a unit type like `Quantity`, but it is constrained to be a unit of temperature, for example
Kelvin, Celsius or Fahrenheit.
Another difference is that `Temperature` values convert between temperature units using the temperature scale offsets.
They are not just quantities of temperature, but temperature values:
```scala
scala> import coulomb._, coulomb.si._, coulomb.temp._, spire.std.any._

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
t1: coulomb.temp.Temperature[Float,coulomb.temp.Fahrenheit] = OffsetQuantity(51.8)

scala> t1.show
res3: String = 51.8 °F

// subtract a quantity from a temperature to get a new temperature
scala> val t2 = 50f.withTemperature[Fahrenheit] - 1f.withUnit[Celsius]
t2: coulomb.temp.Temperature[Float,coulomb.temp.Fahrenheit] = OffsetQuantity(48.2)

scala> t2.showFull
res4: String = 48.2 fahrenheit

// subtract two temperatures to get a quantity
scala> val q = t1 - t2
q: coulomb.Quantity[Float,coulomb.temp.Fahrenheit] = Quantity(3.6)

scala> q.show
res5: String = 3.6 °F
```

In coulomb, Temperature quantities are a specialization of the more general
[OffsetQuantity](https://erikerlandson.github.io/coulomb/latest/api/coulomb/offset/index.html).
An `OffsetQuantity` represents a variation of the `Quantity` concept that is augmented with
an absolute offset. Both `Temperature` and `EpochTime` are specializations of `OffsetQuantity`.
