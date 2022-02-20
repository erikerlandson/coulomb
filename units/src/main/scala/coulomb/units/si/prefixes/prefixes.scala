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
import coulomb.{`/`, `^`}

final type Deca
given ctx_unit_Deca: PrefixUnit[Deca, 10, "deca", "da"] with {}

final type Hecto
given ctx_unit_Hecto: PrefixUnit[Hecto, 100, "hecto", "h"] with {}

final type Kilo
given ctx_unit_Kilo: PrefixUnit[Kilo, 10 ^ 3, "kilo", "k"] with {}

final type Mega
given ctx_unit_Mega: PrefixUnit[Mega, 10 ^ 6, "mega", "M"] with {}

final type Giga
given ctx_unit_Giga: PrefixUnit[Giga, 10 ^ 9, "giga", "G"] with {}

final type Tera
given ctx_unit_Tera: PrefixUnit[Tera, 10 ^ 12, "tera", "T"] with {}

final type Peta
given ctx_unit_Peta: PrefixUnit[Peta, 10 ^ 15, "peta", "P"] with {}

final type Exa
given ctx_unit_Exa: PrefixUnit[Exa, 10 ^ 18, "exa", "E"] with {}

final type Zetta
given ctx_unit_Zetta: PrefixUnit[Zetta, 10 ^ 21, "zetta", "Z"] with {}

final type Yotta
given ctx_unit_Yotta: PrefixUnit[Yotta, 10 ^ 24, "yotta", "Y"] with {}

final type Deci
given ctx_unit_Deci: PrefixUnit[Deci, 1 / 10, "deci", "d"] with {}

final type Centi
given ctx_unit_Centi: PrefixUnit[Centi, 1 / 100, "centi", "c"] with {}

final type Milli
given ctx_unit_Milli: PrefixUnit[Milli, 10 ^ -3, "milli", "m"] with {}

final type Micro
given ctx_unit_Micro: PrefixUnit[Micro, 10 ^ -6, "micro", "μ"] with {}

final type Nano
given ctx_unit_Nano: PrefixUnit[Nano, 10 ^ -9, "nano", "n"] with {}

final type Pico
given ctx_unit_Pico: PrefixUnit[Pico, 10 ^ -12, "pico", "p"] with {}

final type Femto
given ctx_unit_Femto: PrefixUnit[Femto, 10 ^ -15, "femto", "f"] with {}

final type Atto
given ctx_unit_Atto: PrefixUnit[Atto, 10 ^ -18, "atto", "a"] with {}

final type Zepto
given ctx_unit_Zepto: PrefixUnit[Zepto, 10 ^ -21, "zepto", "z"] with {}

final type Yocto
given ctx_unit_Yocto: PrefixUnit[Yocto, 10 ^ -24, "yocto", "y"] with {}
