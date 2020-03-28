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

package coulomb.pureconfig

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

import com.typesafe.config.ConfigFactory

import _root_.pureconfig._
import _root_.pureconfig.generic.auto._

import coulomb.validators.CoulombValidators._

object PureconfigTests extends TestSuite {
  case class QC(duration: Quantity[Double, Second], memory: Quantity[Double, Mega %* Byte], regular: Int)

  implicit val qp = QuantityParser[Second :: Byte :: Hour :: Giga :: HNil]

  val conf = ConfigFactory.parseString("""{
    "duration": { "value": 1, "unit": "hour" },
    "memory": { "value": 1, "unit": "gigabyte" }
    "regular": 42
  }""")

  val tests = Tests {
    test("load a pureconfic case class with unit conversions") {
      val qc = ConfigSource.fromConfig(conf).load[QC].toOption.get
      assert(
        qc.duration.isValidQ[Double, Second](3600.0),
        qc.memory.isValidQ[Double, Mega %* Byte](1000.0),
        qc.regular == 42
      )
    }

    test("fail on incompatible units") {
      case class Wrong(duration: Quantity[Double, Meter], memory: Quantity[Double, Mega %* Byte])
      val qc = ConfigSource.fromConfig(conf).load[Wrong]
      assert(qc.isLeft)
    }
  }
}
