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

/**
 * Represents a typesafe Config object, augmented with a coulomb QuantityParser
 * to provide additional static type checking for unit expressions.
 * {{{
 * import coulomb.typesafeconfig._
 *
 * val confTS = ConfigFactory.parseString("""
 *   "duration" = "60 second"
 *   "memory" = "100 gigabyte"
 *   "bandwidth" = "10 megabyte / second"
 * """)
 *
 * val qp = QuantityParser[Byte :: Second :: Giga :: Mega :: HNil]
 *
 * val conf = confTS.withQuantityParser(qp)
 *
 * val bw = conf.getQuantity[Float, Giga %* Bit %/ Minute]("bandwidth")
 * }}}
 */
case class CoulombConfig(conf: Config, qp: QuantityParser) {
  /**
   * Obtain the value at the given key, as a coulomb Quantity
   * @tparam N the numeric value type
   * @tparam U the unit type
   * @param key the Config key to look up
   * @return a Quantity[N, U] parsed from value at the key, wrapped in a Try
   */
  def getQuantity[N :TypeTag, U :UnitTypeString](key: String) = {
    for {
      raw <- Try { conf.getString(key) }
      qv <- qp[N, U](raw)
    } yield { qv }
  }
}

/** Static methods and values for CoulombConfig */
object CoulombConfig {
  /** Enable the Config methods to be called on a CoulombConfig object */
  implicit def exposeConfig(cc: CoulombConfig): Config = cc.conf
}
