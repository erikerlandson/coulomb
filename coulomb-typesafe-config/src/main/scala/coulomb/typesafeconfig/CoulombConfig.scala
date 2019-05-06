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

package coulomb.typesafeconfig

import scala.language.implicitConversions
import scala.util.Try

import scala.reflect.runtime.universe.TypeTag

import com.typesafe.config.Config

import coulomb.parser.unitops.UnitTypeString
import coulomb.parser.QuantityParser

case class CoulombConfig(conf: Config, qp: QuantityParser) {
  def getQuantity[N :TypeTag, U :UnitTypeString](key: String) = {
    for {
      raw <- Try { conf.getString(key) }
      qv <- qp[N, U](raw)
    } yield { qv }
  }
}

object CoulombConfig {
  implicit def exposeConfig(cc: CoulombConfig): Config = cc.conf
}
