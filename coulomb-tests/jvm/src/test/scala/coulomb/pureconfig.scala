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

import utest._

import _root_.pureconfig._
import _root_.pureconfig.generic.auto._
import _root_.pureconfig.syntax._

import com.typesafe.config.ConfigFactory

import coulomb._
import coulomb.si.{ Meter, Second }
import coulomb.siprefix.{ Giga, Mega }
import coulomb.time.Hour
import coulomb.info.Byte

import coulomb.parser.QuantityParser

import coulomb.validators.CoulombValidators._

object CaseClassTest {
  case class Author(name: String)
  case class Book(title: String, author: String)
}

object PureconfigTests extends TestSuite {

  implicit val qp = QuantityParser[Second :: Byte :: Hour :: Giga :: HNil]

  val conf = ConfigFactory.parseString("""{
    "duration": { "value": 1, "unit": "hour" },
    "memory": { "value": 1, "unit": "gigabyte" }
    "regular": 42
  }""")

  val tests = Tests {
    test("load a case class with Quantity unit conversions") {
      case class QC(duration: Quantity[Double, Second],
                    memory: Quantity[Double, Mega %* Byte],
                    regular: Int)

      val qc = ConfigSource.fromConfig(conf).load[QC].toOption.get
      assert(
        qc.duration.isValidQ[Double, Second](3600.0),
        qc.memory.isValidQ[Double, Mega %* Byte](1000.0),
        qc.regular == 42
      )
    }

    test("fail on incompatible units") {
      case class Wrong(duration: Quantity[Double, Meter],      // oh no!
                       memory: Quantity[Double, Mega %* Byte],
                       regular: Int)

      val qc = ConfigSource.fromConfig(conf).load[Wrong]
      assert(qc.isLeft)
    }

    test("load a case class with a refined Quantity") {
      import eu.timepit.refined._
      import eu.timepit.refined.api._
      import eu.timepit.refined.numeric._
      import coulomb.pureconfig.refined._

      case class QC(duration: Quantity[Refined[Double, Positive], Second],
                    memory: Quantity[Double, Mega %* Byte],
                    regular: Int)

      val qc = ConfigSource.fromConfig(conf).load[QC].toOption.get
      assert(
        qc.duration.isValidQ[Refined[Double, Positive], Second](3600.0),
        qc.memory.isValidQ[Double, Mega %* Byte](1000.0),
        qc.regular == 42
      )
    }

    test("fail on un-validated refined predicate") {
      import eu.timepit.refined._
      import eu.timepit.refined.api._
      import eu.timepit.refined.numeric._
      import coulomb.pureconfig.refined._

      case class Wrong(duration: Quantity[Refined[Double, Negative], Second], // oh no!
                       memory: Quantity[Double, Mega %* Byte],
                       regular: Int)

      val qc = ConfigSource.fromConfig(conf).load[Wrong]
      assert(qc.isLeft)
    }

    test("undeclared base units") {
      import coulomb.policy.undeclaredBaseUnits._
      import CaseClassTest._

      val conf = (3.withUnit[Book], 5.withUnit[Author]).toConfig
      assert(conf.toString == """SimpleConfigList([{"unit":"Book","value":3},{"unit":"Author","value":5}])""")

      implicit val qp = QuantityParser.withImports[Book :: Author :: HNil]("coulomb.policy.undeclaredBaseUnits._")
      val load = conf.toOrThrow[(Quantity[Float, Book], Quantity[Int, Author])]
      assert(load._1.isValidQ[Float, Book](3))
      assert(load._2.isValidQ[Int, Author](5))

      intercept[Exception] {
        conf.toOrThrow[(Quantity[Int, Author], Quantity[Int, Book])]
      }
    }
  }
}
