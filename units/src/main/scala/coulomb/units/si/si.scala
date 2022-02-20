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
given ctx_unit_Meter: BaseUnit[Meter, "meter", "m"] with {}

final type Kilogram
given ctx_unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] with {}

final type Second
given ctx_unit_Second: BaseUnit[Second, "second", "s"] with {}

final type Ampere
given ctx_unit_Ampere: BaseUnit[Ampere, "ampere", "A"] with {}

final type Mole
given ctx_unit_Mole: BaseUnit[Mole, "mole", "mol"] with {}

final type Candela
given ctx_unit_Candela: BaseUnit[Candela, "candela", "cd"] with {}

final type Kelvin
given ctx_unit_Kelvin: BaseUnit[Kelvin, "Kelvin", "K"] with {}
