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

package coulomb.testing.units

import coulomb.define.*
import coulomb.{`*`, `/`, `^`}

final type Meter
given ctx_unit_Meter: BaseUnit[Meter, "meter", "m"] with {}

final type Kilogram
given ctx_unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] with {}

final type Second
given ctx_unit_Second: BaseUnit[Second, "second", "s"] with {}

final type Kilo
given ctx_unit_Kilo: DerivedUnit[Kilo, 10 ^ 3, "kilo", "k"] with {}

final type Mega
given ctx_unit_Mega: DerivedUnit[Mega, 10 ^ 6, "mega", "M"] with {}

final type Milli
given ctx_unit_Milli: DerivedUnit[Milli, 10 ^ -3, "milli", "m"] with {}

final type Micro
given ctx_unit_Micro: DerivedUnit[Micro, 10 ^ -6, "micro", "μ"] with {}

final type Yard
given ctx_unit_Yard: DerivedUnit[Yard, Meter *  (9144 / 10000), "yard", "yd"] with {}

final type Foot
given ctx_unit_Foot: DerivedUnit[Foot, Yard / 3, "foot", "ft"] with {}

final type Liter
given ctx_unit_Liter: DerivedUnit[Liter, (Meter ^ 3) / 1000, "liter", "l"] with {}

final type Pound
given ctx_unit_Pound: DerivedUnit[Pound, Kilogram * (45359237 / 100000000), "pound", "lb"] with {}

final type Minute
given ctx_unit_Minute: DerivedUnit[Minute, Second * 60, "minute", "min"] with {}

final type Hour
given ctx_unit_Hour: DerivedUnit[Hour, Minute * 60, "hour", "hr"] with {}
