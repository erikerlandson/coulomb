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
import coulomb.{`*`, `/`, `^`}

final type Meter
given ctx_Unit_Meter: BaseUnit[Meter, "meter", "m"] with {}

final type Kilogram
given ctx_Unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] with {}

final type Second
given ctx_Unit_Second: BaseUnit[Second, "second", "s"] with {}

final type Minute
given ctx_Unit_Minute: DerivedUnit[Minute, Second, 60, "minute", "min"] with {}


