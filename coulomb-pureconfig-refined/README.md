# Integrating pureconfig + refined + coulomb

To learn more ...

* [coulomb](../README.md#tutorial)
* [coulomb-pureconfig](../coulomb-pureconfig/README.md)
* [coulomb-refined](../coulomb-refined/README.md)
* [coulomb-parser](../coulomb-parser/README.md)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.5.8",
  "org.typelevel" %% "spire" % "0.18.0",
  "eu.timepit" %% "singleton-ops" % "0.5.2",
  // pureconfig integration:
  "com.manyangled" %% "coulomb-pureconfig" % "0.5.8",
  "com.manyangled" %% "coulomb-parser" % "0.5.8",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  "com.github.pureconfig" %% "pureconfig-core" % "0.17.1",
  "com.github.pureconfig" %% "pureconfig-generic" % "0.17.1",
  // refined integration
  "com.manyangled" %% "coulomb-refined" % "0.5.8",
  "eu.timepit" %% "refined" % "0.10.1"
)
```

### How to import:

The examples that follow can be run with the following imports:

```scala
import _root_.pureconfig._
import _root_.pureconfig.generic.auto._
import _root_.pureconfig.syntax._

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._

import coulomb._
import coulomb.pureconfig._
import coulomb.parser.QuantityParser

import shapeless.{ ::, HNil }

import coulomb.refined._
import coulomb.pureconfig.refined._

import coulomb.si.{ Meter, Second }
import coulomb.us.Foot
```

### Examples

The `coulomb-pureconfig-refined` integration library allows pureconfig i/o for coulomb `Quantity` values
where the value type is `Refined`.
In other words, pureconfig for objects of the form `Quantity[Refined[Value, Predicate], Unit]`.

The following example shows pureconfig output for such an object:

```scala
scala> val q = 1.withRefinedUnit[Positive, Meter]
val q: coulomb.Quantity[eu.timepit.refined.api.Refined[Int,eu.timepit.refined.numeric.Positive],coulomb.si.Meter] = Quantity(1)

scala> val conf = q.toConfig
val conf: com.typesafe.config.ConfigValue = SimpleConfigObject({"unit":"meter","value":1})
```

As the above example shows, Quantity objects are output with both a value and a string encoding the unit type.
In pureconfig, `Refined` values are stored without the predicate information.
A value may be re-loaded with any predicate type, and is checked for correctness when it is loaded.

The example continues by re-loading a `ConfigValue` back into a quantity.
Loading values from a configuration requires a `QuantityParser` that is aware of the units
appearing in the configuration (in this case, only Meter).
The value is being loaded using a different value type (Float),
a different predicate (NonNegative) and also different unit (Foot):

```scala
scala> implicit val qp = QuantityParser[Meter :: HNil]
val qp: coulomb.parser.QuantityParser = coulomb.parser.QuantityParser@7177604e

scala> val qread = conf.toOrThrow[Quantity[Refined[Float, NonNegative], Foot]]
val qread: coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.NonNegative],coulomb.us.Foot] = Quantity(3.28084)

scala> qread.show
val res0: String = 3.28084 ft
```

If the value being read does not satisfy the Refined predicate, it is an i/o error:

```scala
scala> val qread = conf.toOrThrow[Quantity[Refined[Float, Negative], Foot]]
pureconfig.error.ConfigReaderException: Cannot convert configuration to a coulomb.Quantity. Failures are:
  at the root:
    - Cannot convert '{
          # hardcoded value
          "unit" : "meter",
          # hardcoded value
          "value" : 1
      }
      'to coulomb.Quantity[eu.timepit.refined.api.Refined[Float,eu.timepit.refined.numeric.Negative],coulomb.us.Foot]: Predicate failed: (3.28084 < 0.0)..
```

Similarly, attempting to load with an incompatible unit is an error:

```scala
scala> val qread = conf.toOrThrow[Quantity[Refined[Float, NonNegative], Second]]
pureconfig.error.ConfigReaderException: Cannot convert configuration to a coulomb.Quantity. Failures are:
  at the root:
    - Cannot convert '{
          # hardcoded value
          "unit" : "meter",
          # hardcoded value
          "value" : 1
      }
      'to coulomb.Quantity[Float,coulomb.si.Second]: Failed to parse (1.0, meter) ==> coulomb.si.Second.
```
