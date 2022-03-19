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
 */
abstract class BaseUnit[U, Name, Abbv] extends NamedUnit[Name, Abbv]

/**
 * @tparam U unit type
 * @tparam D unit it is derived from
 * @tparam Name unit name
 * @tparam Abbv unit abbreviation
 */
abstract class DerivedUnit[U, D, Name, Abbv] extends NamedUnit[Name, Abbv]

/**
 * Delta Units represent units with an delta in their transforms, for example temperatures or times
 *
 * @tparam U unit type
 * @tparam D unit it is derived from
 * @tparam O unit transform delta
 * @tparam Name unit name
 * @tparam Abbv unit abbreviation
 */
abstract class DeltaUnit[U, D, O, Name, Abbv] extends DerivedUnit[U, D, Name, Abbv]
