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

import scala.reflect.runtime.universe.WeakTypeTag

import _root_.pureconfig.{ConfigReader, ConfigWriter, ConfigCursor}
import _root_.pureconfig.error.{CannotConvert, ConfigReaderFailures, ConvertFailure}

import com.typesafe.config.ConfigValue

import eu.timepit.refined.refineV
import eu.timepit.refined.api.{ Refined, Validate }

import coulomb._

package object refined {
  import coulomb.pureconfig.infra.CoulombPureconfigOverride

  // prevent the "main" pureconfig rule from ambiguity on `Refined[V, P]`
  implicit def overridePureconfigRefined[V, P]: CoulombPureconfigOverride[Refined[V, P]] =
    new CoulombPureconfigOverride[Refined[V, P]] {}

  /** Manifest a ConfigWriter for `Quantity[Refined[P, V], U]` */
  implicit def coulombRefinedConfigWriter[V, P, U](implicit
    qcw: ConfigWriter[Quantity[V, U]]
  ): ConfigWriter[Quantity[Refined[V, P], U]] = new ConfigWriter[Quantity[Refined[V, P], U]] {
    def to(q: Quantity[Refined[V, P], U]): ConfigValue =
      qcw.to(Quantity[V, U](q.value.value))
  }

  /** Manifest a ConfigReader for `Quantity[Refined[P, V], U]` */
  implicit def coulombRefinedConfigReader[V, P, U](implicit
    qcr: ConfigReader[Quantity[V, U]],
    qpv: Validate[V, P],
    qtt: WeakTypeTag[Quantity[Refined[V, P], U]]
  ): ConfigReader[Quantity[Refined[V, P], U]] = new ConfigReader[Quantity[Refined[V, P], U]] {
    def from(cur: ConfigCursor): Either[ConfigReaderFailures, Quantity[Refined[V, P], U]] = {
      qcr.from(cur) match {
        case Left(readFailure) => Left(readFailure)
        case Right(q) => {
          refineV[P](q.value) match {
            case Right(rval) => Right(rval.withUnit[U])
            case Left(because) => Left(
              ConfigReaderFailures(
                ConvertFailure(
                  reason = CannotConvert(
                    value = s"$q",
                    toType = qtt.tpe.toString,
                    because = because
                  ),
                  cur = cur)))
          }
        }
      }
    }
  }
}
