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

package coulomb

import scala.util.{ Try, Success, Failure }

import _root_.pureconfig.{ConfigConvert, ConfigCursor}
import _root_.pureconfig.error.{CannotConvert, ConfigReaderFailures, ConvertFailure}
import com.typesafe.config.ConfigValue
import scala.reflect.runtime.universe.{WeakTypeTag, TypeTag}

import coulomb.unitops.UnitString
import coulomb.parser.unitops.UnitTypeString
import coulomb.parser.QuantityParser

package object pureconfig {
  case class ConfigCoulomb[N](value: N, unit: String)

  object ConfigCoulomb {
    def apply[N, U](q: Quantity[N, U])(implicit ustr: UnitString[U]): ConfigCoulomb[N] =
      ConfigCoulomb(q.value, q.showUnitFull)
  }

  implicit def coulombQuantityConfigConvert[N, U](implicit
    ntt: TypeTag[N],
    qtt: TypeTag[Quantity[N, U]],
    ustr: UnitString[U],
    uts: UnitTypeString[U],
    qp: QuantityParser,
    coulombConvert: ConfigConvert[ConfigCoulomb[N]]
  ): ConfigConvert[Quantity[N, U]] = new ConfigConvert[Quantity[N, U]] {
    def from(cur: ConfigCursor): Either[ConfigReaderFailures, Quantity[N, U]] = {
      coulombConvert.from(cur) match {
        case Left(readFailure) => Left(readFailure)
        case Right(ConfigCoulomb(value, unit)) => {
          qp[N, U](s"$value $unit") match {
            case Success(q) => Right(q)
            case Failure(parseFailure) => Left(ConfigReaderFailures(ConvertFailure(
              reason = CannotConvert(
                value = cur.value.render(),
                toType = qtt.tpe.toString,
                because = s"Incompatible unit: $unit"),
              cur = cur)))
          }
        }
      }
    }

    def to(q: Quantity[N, U]): ConfigValue = coulombConvert.to(ConfigCoulomb(q))
  }
}
