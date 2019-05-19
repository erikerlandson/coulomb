/*
Copyright 2019 Erik Erlandson

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
  it should "integrate with an avro schema" in {
    val schema = new Schema.Parser().parse(new java.io.File("coulomb-tests/src/test/scala/coulomb/test1.avsc"))
    val rec = new GenericData.Record(schema)
    rec.put("latency", 1.0)
    val qp = QuantityParser[Second :: Byte :: Hour :: Mega :: HNil]
    val qlat = rec.getQuantity[Double, Milli %* Second](qp)("latency")
    qlat.shouldBeQ[Double, Milli %* Second](1000)
  }
}
