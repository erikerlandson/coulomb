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

package coulomb.define

/**
 * @tparam Name unit name
 * @tparam Abbv unit abbreviation
 */
abstract class NamedUnit[Name, Abbv]

/**
 * @tparam U unit type
 * @tparam Name unit name
 * @tparam Abbv unit abbreviation
 *
 * {{{
 * import coulomb.define.*
 *
 * // define a unit of spicy heat
 * type Scoville
 * given unit_Scoville: BaseUnit[Scoville, "scoville", "sco"] = BaseUnit()
 * }}}
 */
abstract class BaseUnit[U, Name, Abbv] extends NamedUnit[Name, Abbv]

/** Companion object utilities for BaseUnit declarations */
object BaseUnit:
    def apply[U, Name, Abbv](): BaseUnit[U, Name, Abbv] =
        new NC[U, Name, Abbv]

    /** internal named class for avoiding anonymous class code generation */
    class NC[U, Name, Abbv] extends BaseUnit[U, Name, Abbv]

/**
 * @tparam U unit type
 * @tparam D unit it is derived from
 * @tparam Name unit name
 * @tparam Abbv unit abbreviation
 *
 * {{{
 * import coulomb.define.*
 * import coulomb.units.us.{*, given}
 *
 * // a unit of length based on the height of Oliver R. Smoot in 1958
 * type Smoot
 * given unit_Smoot: DerivedUnit[Smoot, 67 * Inch, "smoot", "smt"] = DerivedUnit()
 * }}}
 */
abstract class DerivedUnit[U, D, Name, Abbv] extends NamedUnit[Name, Abbv]

/** Companion object utilities for DerivedUnit declarations */
object DerivedUnit:
    def apply[U, D, Name, Abbv](): DerivedUnit[U, D, Name, Abbv] =
        new NC[U, D, Name, Abbv]

    /** internal named class for avoiding anonymous class code generation */
    class NC[U, D, Name, Abbv] extends DerivedUnit[U, D, Name, Abbv]

/**
 * Delta Units represent units with an offset in their transforms, for example temperatures or times
 *
 * @tparam U unit type
 * @tparam D unit it is derived from
 * @tparam O unit transform offset
 * @tparam Name unit name
 * @tparam Abbv unit abbreviation
 * {{{
 * import coulomb.define.*
 * import coulomb.units.si.{*, given}
 *
 * type Fahrenheit
 * given unit_Fahrenheit: DeltaUnit[Fahrenheit, (5 / 9) * Kelvin, 45967 / 100, "fahrenheit", "F"] = DeltaUnit()
 * }}}
 */
abstract class DeltaUnit[U, D, O, Name, Abbv] extends DerivedUnit[U, D, Name, Abbv]

/** Companion object utilities for DeltaUnit declarations */
object DeltaUnit:
    def apply[U, D, O, Name, Abbv](): DeltaUnit[U, D, O, Name, Abbv] =
        new NC[U, D, O, Name, Abbv]

    /** internal named class for avoiding anonymous class code generation */
    class NC[U, D, O, Name, Abbv] extends DeltaUnit[U, D, O, Name, Abbv]
