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

package coulomb.units.si

import coulomb.define.*

final type Meter
given BaseUnit[Meter] with
    val name = "meter"
    val abbv = "m"

final type Kilogram
given BaseUnit[Kilogram] with
    val name = "kilogram"
    val abbv = "kg"

final type Second
given BaseUnit[Second] with
    val name = "second"
    val abbv = "s"

final type Ampere
given BaseUnit[Ampere] with
    val name = "ampere"
    val abbv = "A"

final type Mole
given BaseUnit[Mole] with
    val name = "mole"
    val abbv = "mol"

final type Candela
given BaseUnit[Candela] with
    val name = "candela"
    val abbv = "cd"

final type Kelvin
given BaseUnit[Kelvin] with
    val name = "Kelvin"
    val abbv = "K"
