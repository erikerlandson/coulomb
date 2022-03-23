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

object time:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}
    import coulomb.units.si.*

    import coulomb.*

    type Second = coulomb.units.si.Second
    export coulomb.units.si.ctx_unit_Second

    final type Minute
    given ctx_unit_Minute: DerivedUnit[Minute, 60 * Second, "minute", "min"] with {}

    final type Hour
    given ctx_unit_Hour: DerivedUnit[Hour, 60 * Minute, "hour", "h"] with {}

    final type Day
    given ctx_unit_Day: DerivedUnit[Day, 24 * Hour, "day", "d"] with {}

    final type Week
    given ctx_unit_Week: DerivedUnit[Week, 7 * Day, "week", "wk"] with {}

    //final type EpochTime[V, U] = DeltaQuantity[V, U, Second]

    object EpochTime:
        def apply[U](using a: Applier[U]) = a

        abstract class Applier[U]:
            def apply[V](v: V): DeltaQuantity[V, U, Second]
        object Applier:
            given [U]: Applier[U] =
                new Applier[U]:
                    def apply[V](v: V): DeltaQuantity[V, U, Second] = v.withDeltaUnit[U, Second] 

    extension[V](v: V)
        def withEpochTime[U]: DeltaQuantity[V, U, Second] = v.withDeltaUnit[U, Second]
