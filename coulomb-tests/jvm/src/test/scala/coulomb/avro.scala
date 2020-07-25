/*
Copyright 2017-2020 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb.avro

import shapeless.{ ::, HNil }

import spire.math._

import singleton.ops._

import utest._

import coulomb._
import coulomb.unitops._
import coulomb.si._
import coulomb.siprefix._
import coulomb.mks._
import coulomb.accepted._
import coulomb.time._
import coulomb.info._
import coulomb.binprefix._
import coulomb.us._
import coulomb.temp._
import coulomb.parser.QuantityParser

import org.apache.avro._
import org.apache.avro.generic._

import coulomb.validators.CoulombValidators._

object AvroIntegrationTests extends TestSuite {
  val schema1 = new Schema.Parser().parse(new java.io.File("coulomb-tests/jvm/src/test/scala/coulomb/test1.avsc"))

  val record1 = {
    val rec = new GenericData.Record(schema1)
    rec.put("latency", 1.0)
    rec.put("bandwidth", 1.0)
    rec.put("nounit", 1.0)
    rec
  }

  val qp1 = QuantityParser[Second :: Byte :: Hour :: Mega :: Giga :: HNil]

  val tests = Tests {
    test("integrate with an avro schema") {
      assert(
        record1.getQuantity[Double, Milli %* Second](qp1)("latency").isValidQ[Double, Milli %* Second](1000),
        record1.getQuantity[Float, Tera %* Bit %/ Minute](qp1)("bandwidth").isValidQ[Float, Tera %* Bit %/ Minute](0.48)
      )
    }

    test("fail on incompatible units") {
      intercept[Exception] { record1.getQuantity[Double, Byte](qp1)("latency") }
    }

    test("fail on missing unit fields") {
      intercept[Exception] { record1.getQuantity[Double, Second](qp1)("nounit") }
    }

    test("support putQuantity") {
      val rec = new GenericData.Record(schema1)
      rec.putQuantity(qp1)("latency", 1.withUnit[Minute])
      assert(rec.getQuantity[Double, Second](qp1)("latency").isValidQ[Double, Second](60.0))
    }
  }
}
