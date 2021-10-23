# Integration with Apache Avro

To learn more about coulomb in general refer to the
[coulomb tutorial](../README.md#tutorial)

### How to include in your project

For more information on available coulomb packages, see this
[guide](../README.md#how-to-include-coulomb-in-your-project)

The coulomb libraries include most dependencies `%Provided` to allow maximum flexibility
of binary compatible dependency versions.

The package `coulomb-avro` provides an integration of unit parsing with Avro schemas:
```scala
libraryDependencies ++= Seq(
  "com.manyangled" %% "coulomb-avro" % "0.5.7",
  "com.manyangled" %% "coulomb-parser" % "0.5.7",
  "org.apache.avro" % "avro" % "1.10.2",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.0"
)
```

### Examples

The following example is also written up as a
[blog post](http://erikerlandson.github.io/blog/2019/05/23/unit-types-for-avro-schema-integrating-avro-with-coulomb/).

This example can be run from the sbt REPL.
```bash
$ cd /path/to/coulomb
$ sbt coulomb_tests/console
```

Import some coulomb units, and apache avro integration.
```scala
scala> import shapeless._, coulomb._, coulomb.si._, coulomb.siprefix._, coulomb.info._, coulomb.time._, coulomb.parser._
scala> import org.apache.avro._, org.apache.avro.generic._, coulomb.avro._
```

Load a simple data schema, that is augmented with an additional "unit" field.
Declare a data record that uses this schema.
```scala
scala> val schema = new Schema.Parser().parse("""
     | {
     |     "type": "record",
     |     "name": "smol",
     |     "fields": [
     |         { "name": "latency", "type": "double", "unit": "second" },
     |         { "name": "bandwidth", "type": "double", "unit": "gigabyte / second" }
     |     ]
     | }""")
schema: org.apache.avro.Schema = {"type":"record","name":"smol","fields":[{"name":"latency","type":"double","unit":"second"},{"name":"bandwidth","type":"double","unit":"gigabyte / second"}]}

scala> val rec = new GenericData.Record(schema)
rec: org.apache.avro.generic.GenericData.Record = {"latency": null, "bandwidth": null}
```

Declare a QuantityParser that will allow the "unit" schema information to be parsed into coulomb unit types.
Use the enhancement `putQuantity` method to write data with unit information that can do automatic unit conversions.
```scala
scala> val qp = QuantityParser[Second :: Byte :: Hour :: Giga :: HNil]
qp: coulomb.parser.QuantityParser = coulomb.parser.QuantityParser@182d67f2

scala> rec.putQuantity(qp)("latency", 100.withUnit[Milli %* Second])

scala> rec.putQuantity(qp)("bandwidth", 1.withUnit[Tera %* Bit %/ Minute])

scala> rec
res2: org.apache.avro.generic.GenericData.Record = {"latency": 0.1, "bandwidth": 2.0833333333333335}
```

The coulomb `putQuantity` method prevents incorrect units and will fail with an error message:
```scala
scala> rec.putQuantity(qp)("latency", 100.withUnit[Milli %* Meter])
java.lang.Exception: unit metadata "second" incompatible with "coulomb.%*[coulomb.siprefix.Milli, coulomb.si.Meter]"
  at coulomb.avro.package$EnhanceGenericRecord.liftedTree2$1(package.scala:99)
  at coulomb.avro.package$EnhanceGenericRecord.putQuantity(package.scala:96)
  ... 36 elided
```

The corresponding `getQuantity` method allows unit-aware data read operations.
```scala
scala> rec.getQuantity[Double, Micro %* Second](qp)("latency")
res4: coulomb.Quantity[Double,coulomb.siprefix.Micro %* coulomb.si.Second] = Quantity(100000.0)

scala> rec.getQuantity[Double, Giga %* Bit %/ Minute](qp)("bandwidth")
res5: coulomb.Quantity[Double,coulomb.siprefix.Giga %* coulomb.info.Bit %/ coulomb.time.Minute] = Quantity(1000.0000000000001)
```

Like `putQuantity`, `getQuantity` will fail on incorrect units.
```scala
scala> rec.getQuantity[Double, Byte](qp)("latency")
java.lang.Exception: unit metadata "second" incompatible with "coulomb.info.Byte"
  at coulomb.avro.package$EnhanceGenericRecord.liftedTree1$1(package.scala:57)
  at coulomb.avro.package$EnhanceGenericRecord.getQuantity(package.scala:54)
  ... 36 elided
```
