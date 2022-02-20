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

package coulomb.units.mks

import coulomb.define.*
import coulomb.{`*`, `/`, `^`}

import coulomb.units.si.*

final type Radian
given ctx_unit_Radian: DerivedUnit[Radian, 1, 1, "radian", "rad"] with {}

final type Steradian
given ctx_unit_Steradian: DerivedUnit[Steradian, 1, 1, "steradian", "sr"] with {}

final type Hertz
given ctx_unit_Hertz: DerivedUnit[Hertz, 1 / Second, 1, "hertz", "Hz"] with {}

final type Newton
given ctx_unit_Newton: DerivedUnit[Newton, Kilogram * Meter / (Second ^ 2), 1, "newton", "N"] with {}

final type Joule
given ctx_unit_Joule: DerivedUnit[Joule, Newton * Meter, 1, "joule", "J"] with {}

final type Watt
given ctx_unit_Watt: DerivedUnit[Watt, Joule / Second, 1, "watt", "W"] with {}
