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

package coulomb.units.si.prefixes

import coulomb.define.*
import coulomb.{`*`, `/`, `^`}

final type Kilo
given ctx_Unit_Kilo: PrefixUnit[Kilo, 10 ^ 3, "kilo", "k"] with {}

final type Mega
given ctx_Unit_Mega: PrefixUnit[Mega, 10 ^ 6, "mega", "M"] with {}

final type Giga
given ctx_Unit_Giga: PrefixUnit[Giga, 10 ^ 9, "giga", "G"] with {}

final type Milli
given ctx_Unit_Milli: PrefixUnit[Milli, 10 ^ -3, "milli", "m"] with {}

final type Micro
given ctx_Unit_Micro: PrefixUnit[Micro, 10 ^ -6, "micro", "μ"] with {}

final type Nano
given ctx_Unit_Nano: PrefixUnit[Nano, 10 ^ -9, "nano", "n"] with {}
