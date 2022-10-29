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
given ctx_unit_Meter: BaseUnit[Meter, "meter", "m"] = BaseUnit()

final type Kilogram
given ctx_unit_Kilogram: BaseUnit[Kilogram, "kilogram", "kg"] = BaseUnit()

final type Second
given ctx_unit_Second: BaseUnit[Second, "second", "s"] = BaseUnit()

final type Kilo
given ctx_unit_Kilo: DerivedUnit[Kilo, 10 ^ 3, "kilo", "k"] = DerivedUnit()

final type Mega
given ctx_unit_Mega: DerivedUnit[Mega, 10 ^ 6, "mega", "M"] = DerivedUnit()

final type Milli
given ctx_unit_Milli: DerivedUnit[Milli, 10 ^ -3, "milli", "m"] = DerivedUnit()

final type Micro
given ctx_unit_Micro: DerivedUnit[Micro, 10 ^ -6, "micro", "μ"] = DerivedUnit()

final type Yard
given ctx_unit_Yard: DerivedUnit[Yard, Meter * (9144 / 10000), "yard", "yd"] =
    DerivedUnit()

final type Foot
given ctx_unit_Foot: DerivedUnit[Foot, Yard / 3, "foot", "ft"] = DerivedUnit()

final type Liter
given ctx_unit_Liter: DerivedUnit[Liter, (Meter ^ 3) / 1000, "liter", "l"] =
    DerivedUnit()

final type Pound
given ctx_unit_Pound
    : DerivedUnit[Pound, Kilogram * (45359237 / 100000000), "pound", "lb"] =
    DerivedUnit()

final type Minute
given ctx_unit_Minute: DerivedUnit[Minute, Second * 60, "minute", "min"] =
    DerivedUnit()

final type Hour
given ctx_unit_Hour: DerivedUnit[Hour, Minute * 60, "hour", "hr"] =
    DerivedUnit()

final type Kelvin
given ctx_unit_Kelvin: BaseUnit[Kelvin, "kelvin", "K"] = BaseUnit()

final type Celsius
given ctx_unit_Celsius
    : DeltaUnit[Celsius, Kelvin, 27315 / 100, "celsius", "°C"] = DeltaUnit()

final type Fahrenheit
given ctx_unit_Fahrenheit
    : DeltaUnit[Fahrenheit, (5 / 9) * Kelvin, 45967 / 100, "fahrenheit", "°F"] =
    DeltaUnit()
