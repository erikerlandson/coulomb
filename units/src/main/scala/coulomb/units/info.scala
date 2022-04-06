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

package coulomb.units

object info:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}

    final type Byte
    given ctx_unit_Byte: BaseUnit[Byte, "byte", "B"] = BaseUnit()

    final type Bit
    given ctx_unit_Bit: DerivedUnit[Bit, Byte / 8, "bit", "b"] = DerivedUnit()

    final type Nat
    given ctx_unit_Nat: DerivedUnit[Nat, 1.4426950409 * Bit, "nat", "nat"] = DerivedUnit()

    object prefixes:
        final type Kibi
        given ctx_unit_Kibi: DerivedUnit[Kibi, 1024, "kibi", "Ki"] = DerivedUnit()

        final type Mebi
        given ctx_unit_Mebi: DerivedUnit[Mebi, 1024 ^ 2, "mebi", "Mi"] = DerivedUnit()

        final type Gibi
        given ctx_unit_Gibi: DerivedUnit[Gibi, 1024 ^ 3, "gibi", "Gi"] = DerivedUnit()

        final type Tebi
        given ctx_unit_Tebi: DerivedUnit[Tebi, 1024 ^ 4, "tebi", "Ti"] = DerivedUnit()

        final type Pebi
        given ctx_unit_Pebi: DerivedUnit[Pebi, 1024 ^ 5, "pebi", "Pi"] = DerivedUnit()

        final type Exbi
        given ctx_unit_Exbi: DerivedUnit[Exbi, 1024 ^ 6, "exbi", "Ei"] = DerivedUnit()

        final type Zebi
        given ctx_unit_Zebi: DerivedUnit[Zebi, 1024 ^ 7, "zebi", "Zi"] = DerivedUnit()

        final type Yobi
        given ctx_unit_Yobi: DerivedUnit[Yobi, 1024 ^ 8, "yobi", "Yi"] = DerivedUnit()
