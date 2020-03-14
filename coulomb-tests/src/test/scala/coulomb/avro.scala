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

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

import shapeless._

import spire.math._

import singleton.ops._

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

import org.scalatest.QMatchers._

import org.apache.avro._
import org.apache.avro.generic._

class AvroIntegrationSpec extends FlatSpec with Matchers {

  val schema1 = new Schema.Parser().parse(new java.io.File("coulomb-tests/src/test/scala/coulomb/test1.avsc"))

  val record1 = {
    val rec = new GenericData.Record(schema1)
    rec.put("latency", 1.0)
    rec.put("bandwidth", 1.0)
    rec.put("nounit", 1.0)
    rec
  }

  val qp1 = QuantityParser[Second :: Byte :: Hour :: Mega :: Giga :: HNil]

  it should "integrate with an avro schema" in {
    record1.getQuantity[Double, Milli %* Second](qp1)("latency").shouldBeQ[Double, Milli %* Second](1000)
    record1.getQuantity[Float, Tera %* Bit %/ Minute](qp1)("bandwidth").shouldBeQ[Float, Tera %* Bit %/ Minute](0.48)
  }

  it should "fail on incompatible units" in {
    intercept[Exception] { record1.getQuantity[Double, Byte](qp1)("latency") }
  }

  it should "fail on missing unit fields" in {
    intercept[Exception] { record1.getQuantity[Double, Second](qp1)("nounit") }
  }

  it should "support putQuantity" in {
    val rec = new GenericData.Record(schema1)
    rec.putQuantity(qp1)("latency", 1.withUnit[Minute])
    rec.getQuantity[Double, Second](qp1)("latency").shouldBeQ[Double, Second](60.0)
  }
}
