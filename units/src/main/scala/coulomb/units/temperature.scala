/*
 * Copyright 2022 Erik Erlandson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package coulomb.units

object temperature:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.Kelvin

    import coulomb.*

    export coulomb.units.si.{ Kelvin, ctx_unit_Kelvin }

    final type Celsius
    given ctx_unit_Celsius: DeltaUnit[Celsius, Kelvin, 27315 / 100, "celsius", "°C"] = DeltaUnit()

    final type Fahrenheit
    given ctx_unit_Fahrenheit: DeltaUnit[Fahrenheit, (5 / 9) * Kelvin, 45967 / 100, "fahrenheit", "°F"] = DeltaUnit()

    final type Temperature[V, U] = DeltaQuantity[V, U, Kelvin]

    object Temperature:
        def apply[U](using a: Applier[U]) = a

        abstract class Applier[U]:
            def apply[V](v: V): Temperature[V, U]
        object Applier:
            given [U]: Applier[U] =
                new Applier[U]:
                    def apply[V](v: V): Temperature[V, U] = v.withDeltaUnit[U, Kelvin]

    extension[V](v: V)
        def withTemperature[U]: Temperature[V, U] = v.withDeltaUnit[U, Kelvin]
