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

package coulomb.pureconfig

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

import com.typesafe.config.ConfigFactory

import _root_.pureconfig._
import _root_.pureconfig.generic.auto._

class PureconfigIntegrationSpec extends FlatSpec with Matchers {
  case class QC(duration: Quantity[Double, Second], memory: Quantity[Double, Mega %* Byte], regular: Int)

  implicit val qp = QuantityParser[Second :: Byte :: Hour :: Giga :: HNil]

  val conf = ConfigFactory.parseString("""{
    "duration": { "value": 1, "unit": "hour" },
    "memory": { "value": 1, "unit": "gigabyte" }
    "regular": 42
  }""")

  it should "load a pureconfic case class with unit conversions" in {
    val qc = loadConfig[QC](conf).toOption.get
    qc.duration.shouldBeQ[Double, Second](3600.0)
    qc.memory.shouldBeQ[Double, Mega %* Byte](1000.0)
    qc.regular should be(42)
  }

  it should "fail on incompatible units" in {
    case class Wrong(duration: Quantity[Double, Meter], memory: Quantity[Double, Mega %* Byte])
    val qc = loadConfig[Wrong](conf)
    qc.isLeft.shouldBe(true)
  }
}
