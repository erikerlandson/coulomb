# Integration with pureconfig

To learn more about coulomb in general see:
https://github.com/erikerlandson/coulomb#tutorial

### How to include in your project

For more information on available coulomb packages, see:
https://github.com/erikerlandson/coulomb#how-to-include-coulomb-in-your-project

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions:

```scala
resolvers += "manyangled" at "https://dl.bintray.com/manyangled/maven/"

libraryDependencies ++= Seq(
  // basic coulomb dependencies
  "com.manyangled" %% "coulomb" % "0.3.6",
  "org.typelevel" %% "spire" % "0.17.0-M1",
  "eu.timepit" %% "singleton-ops" % "0.4.0",
  // pureconfig integration:
  "com.manyangled" %% "coulomb-pureconfig" % "0.3.5",
  "com.github.pureconfig" %% "pureconfig" % "0.12.0"
)
```

### How to import:

```scala
import _root_.pureconfig._
import _root_.pureconfig.generic.auto._

import coulomb._
import coulomb.pureconfig._
```

### Example

```scala
  // a config case class, defines two coulomb Quantity fields
  case class QC(duration: Quantity[Double, Second], memory: Quantity[Double, Mega %* Byte], regular: Int)

  // Define a quantity parser for the pureconfig integration to use.
  // See also: https://github.com/erikerlandson/coulomb#quantity-parsing
  implicit val qp = QuantityParser[Second :: Byte :: Hour :: Giga :: HNil]

  // A configuration to load - note that units are different, but compatible with the QC fields
  val conf = ConfigFactory.parseString("""{
    "duration": { "value": 1, "unit": "hour" },
    "memory": { "value": 1, "unit": "gigabyte" }
    "regular": 42
  }""")

  // load the config as usual, using pureconfig api:
  // pureconfig will correctly convert the units, or it will fail if the units are not compatible
  val qc = loadConfig[QC](conf)
```
