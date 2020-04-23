# Integrating pureconfig + refined + coulomb

To learn more ...

* [coulomb](../README.md#tutorial)
* [coulomb-pureconfig](../coulomb-pureconfig/README.md)
* [coulomb-refined](../coulomb-refined/README.md)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.4.0",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.5.0",
  // pureconfig integration:
  "com.manyangled" %% "coulomb-pureconfig" % "0.4.0",
  "com.manyangled" %% "coulomb-parser" % "0.4.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "com.github.pureconfig" %% "pureconfig-core" % "0.12.3",
  "com.github.pureconfig" %% "pureconfig-generic" % "0.12.3",
  // refined integration
  "com.manyangled" %% "coulomb-refined" % "0.4.0",  
  "eu.timepit" %% "refined" % "0.9.13"
)
```

### How to import:

```scala
import _root_.pureconfig._
import _root_.pureconfig.generic.auto._

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._

import coulomb._
import coulomb.pureconfig._
import coulomb.refined._
import coulomb.pureconfig.refined._
```

### Examples

The `coulomb-pureconfig-refined` integration library allows pureconfig i/o for coulomb `Quantity` values
where the value type is `Refined`.
In other words, pureconfig for objects of the form `Quantity[Refined[Value, Predicate], Unit]`.

The following example shows pureconfig output for such an object:

```scala
scala> def write[V](v: V)(implicit w: ConfigWriter[V]) = w.to(v)
def write[V](v: V)(implicit w: pureconfig.ConfigWriter[V]): com.typesafe.config.ConfigValue

scala> val q = 1.withRefinedUnit[Positive, Meter]
val q: coulomb.Quantity[eu.timepit.refined.api.Refined[Int,eu.timepit.refined.numeric.Positive],coulomb.si.Meter] = Quantity(1)

scala> val conf = write(q)
val conf: com.typesafe.config.ConfigValue = SimpleConfigObject({"unit":"meter","value":1})
```

As the above example shows, Quantity objects are output with both a value and a string encoding the unit type.
In pureconfig, `Refined` values are stored without the predicate information.
A value may be re-loaded with any predicate type, and is checked for correctness when it is loaded.

The example continues by re-loading a `ConfigValue` back into a quantity.
Note that the value is being loaded using a different value type, a different predicate and also different (but convertable) unit.

```scala
scala> def read[V](conf: com.typesafe.config.ConfigValue)(implicit r: ConfigReader[V]) = r.from(conf)
def read[V](conf: com.typesafe.config.ConfigValue)(implicit r: pureconfig.ConfigReader[V]): pureconfig.ConfigReader.Result[V]

scala> implicit val qp = QuantityParser[Meter :: Foot :: HNil]
val qp: coulomb.parser.QuantityParser = coulomb.parser.QuantityParser@7177604e

scala> val qread = read[Quantity[Refined[Float, NonNegative], Foot]](conf)
val qread: pureconfig.ConfigReader.Result[coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.NonNegative],coulomb.us.Foot]] = Right(Quantity(3.28084))

scala> qread.isRight
val res1: Boolean = true

scala> qread.toOption.get.show
val res2: String = 3.28084 ft
```

If the value being read does not satisfy the Refined predicate, it is an i/o error:

```scala
scala> val qread = read[Quantity[Refined[Float, Negative], Foot]](conf)
val qread: pureconfig.ConfigReader.Result[coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.Negative],coulomb.us.Foot]] =
Left(ConfigReaderFailures(ConvertFailure(CannotConvert({
    # hardcoded value
    "unit" : "meter",
    # hardcoded value
    "value" : 1
}
,coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.Negative],coulomb.us.Foot],Predicate failed: (3.28084 < 0.0).),None,),List()))

```

Similarly, attempting to load with an incompatible unit is an error:

```scala
scala> val qread = read[Quantity[Refined[Float, NonNegative], Second]](conf)
val qread: pureconfig.ConfigReader.Result[coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.NonNegative],coulomb.si.Second]] =
Left(ConfigReaderFailures(ConvertFailure(CannotConvert({
    # hardcoded value
    "unit" : "meter",
    # hardcoded value
    "value" : 1
}
,coulomb.Quantity[Float,coulomb.si.Second],Failed to parse (1.0, meter) ==> coulomb.si.Second),None,),List()))
```
