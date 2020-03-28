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

import com.typesafe.config.Config
import coulomb.parser.QuantityParser

/** Integrations for Lightbend's typesafe config system */
package object typesafeconfig {
  /** enhance a Config object to add a QuantityParser */
  implicit class EnhanceTSConfig(conf: Config) {
    /**
     * Generate a CoulombConfig object from this Config and the provided QuantityParser
     * @param qp the QuantityParser to associate with this Config
     * @return a CoulombConfig that associates this Config with 'qp'
     */
    def withQuantityParser(qp: QuantityParser): CoulombConfig =
      CoulombConfig(conf, qp)
  }
}
