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

package coulomb

import scala.util.{ Try, Success, Failure }
import scala.reflect.runtime.universe.WeakTypeTag

import _root_.pureconfig.{ConfigReader, ConfigWriter, ConfigCursor}
import _root_.pureconfig.error.{CannotConvert, ConfigReaderFailures, ConvertFailure}

import com.typesafe.config.ConfigValue

import coulomb.unitops.UnitString
import coulomb.parser.unitops.UnitTypeString
import coulomb.parser.QuantityParser

package pureconfig.infra {
  // other libs can override with more specific pureconfig rules
  trait CoulombPureconfigOverride[V]

  case class ConfigQuantity[V](value: V, unit: String)
}

/**
 * Defines implicit ConfigConvert materializers to save and load coulomb Quantity fields
 */
package object pureconfig {
  import coulomb.infra.NoImplicit
  import coulomb.pureconfig.infra._

  implicit def coulombQuantityConfigWriter[V, U](implicit
    ovr: NoImplicit[CoulombPureconfigOverride[V]],
    qcw: ConfigWriter[ConfigQuantity[V]],
    ustr: UnitString[U]
  ): ConfigWriter[Quantity[V, U]] = new ConfigWriter[Quantity[V, U]] {
    def to(q: Quantity[V, U]): ConfigValue =
      qcw.to(ConfigQuantity(q.value, q.showUnitFull))
  }

  implicit def coulombQuantityConfigReader[V, U](implicit
    ovr: NoImplicit[CoulombPureconfigOverride[V]],
    qcr: ConfigReader[ConfigQuantity[V]],
    qp: QuantityParser,
    ntt: WeakTypeTag[V],
    qtt: WeakTypeTag[Quantity[V, U]],
    uts: UnitTypeString[U]
  ): ConfigReader[Quantity[V, U]] = new ConfigReader[Quantity[V, U]] {
    def from(cur: ConfigCursor): Either[ConfigReaderFailures, Quantity[V, U]] = {
      qcr.from(cur) match {
        case Left(readFailure) => Left(readFailure)
        case Right(ConfigQuantity(value, unit)) => {
          qp.applyUnitExpr[V, U](value, unit) match {
            case Success(q) => Right(q)
            case Failure(_) => Left(ConfigReaderFailures(ConvertFailure(
              reason = CannotConvert(
                value = cur.value.render(),
                toType = qtt.tpe.toString,
                because = s"Failed to parse ($value, $unit) ==> ${uts.expr}"),
              cur = cur)))
          }
        }
      }
    }
  }  
}
