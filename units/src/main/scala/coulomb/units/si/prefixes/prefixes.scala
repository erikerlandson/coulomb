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
import coulomb.rational.Rational

final type Kilo
given PrefixUnit[Kilo] with
    val name = "kilo"
    val abbv = "k"
    val coef = Rational(1000)

final type Mega
given PrefixUnit[Mega] with
    val name = "mega"
    val abbv = "M"
    val coef = Rational(1000).pow(2)

final type Giga
given PrefixUnit[Giga] with
    val name = "giga"
    val abbv = "G"
    val coef = Rational(1000).pow(3)

final type Milli
given PrefixUnit[Milli] with
    val name = "milli"
    val abbv = "m"
    val coef = Rational(1000).pow(-1)

final type Micro
given PrefixUnit[Micro] with
    val name = "micro"
    val abbv = "μ"
    val coef = Rational(1000).pow(-2)

final type Nano
given PrefixUnit[Nano] with
    val name = "nano"
    val abbv = "n"
    val coef = Rational(1000).pow(-3)
