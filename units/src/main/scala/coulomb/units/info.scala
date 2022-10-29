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

/**
 * Units of information
 *
 * These definitions use byte as the base unit, and therefore may be thought of
 * as "storage centric" as opposed to information centric:
 *   - https://en.wikipedia.org/wiki/Shannon_(unit)
 *
 * or physics centric:
 *   - https://en.wikipedia.org/wiki/Nat_(unit)#Entropy
 *
 * For example in physics a nat is unitless, where in these definitions it is a
 * derived unit of byte.
 */
object info:
    import coulomb.define.*
    import coulomb.{`*`, `/`, `^`}

    /**
     * A byte is 8 bits, and corresponds to one ASCII character in typical
     * computer memory.
     * @note
     *   care should be taken to distinguish from `scala.Byte`
     */
    final type Byte
    given ctx_unit_Byte: BaseUnit[Byte, "byte", "B"] = BaseUnit()

    /**
     * The fundamental unit of information: a single "yes/no" answer.
     *
     * Equivalent to one shannon:
     *   - https://en.wikipedia.org/wiki/Shannon_(unit)
     */
    final type Bit
    given ctx_unit_Bit: DerivedUnit[Bit, Byte / 8, "bit", "b"] = DerivedUnit()

    /**
     * Logarithmic unit of information
     *
     * The logarithmic form of Shannon entropy yields quantities in nats:
     *   - https://en.wikipedia.org/wiki/Entropy_(information_theory)
     */
    final type Nat
    given ctx_unit_Nat: DerivedUnit[Nat, 1.4426950409 * Bit, "nat", "nat"] =
        DerivedUnit()

    /**
     * Standard binary prefixes
     *
     * Binary prefixes used for computing storage
     *   - https://en.wikipedia.org/wiki/Binary_prefix
     *
     * Standard SI base-10 prefixes are defined in [[coulomb.units.si.prefixes]]
     */
    object prefixes:
        /** Binary prefix for 1024 */
        final type Kibi
        given ctx_unit_Kibi: DerivedUnit[Kibi, 1024, "kibi", "Ki"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 2 */
        final type Mebi
        given ctx_unit_Mebi: DerivedUnit[Mebi, 1024 ^ 2, "mebi", "Mi"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 3 */
        final type Gibi
        given ctx_unit_Gibi: DerivedUnit[Gibi, 1024 ^ 3, "gibi", "Gi"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 4 */
        final type Tebi
        given ctx_unit_Tebi: DerivedUnit[Tebi, 1024 ^ 4, "tebi", "Ti"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 5 */
        final type Pebi
        given ctx_unit_Pebi: DerivedUnit[Pebi, 1024 ^ 5, "pebi", "Pi"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 6 */
        final type Exbi
        given ctx_unit_Exbi: DerivedUnit[Exbi, 1024 ^ 6, "exbi", "Ei"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 7 */
        final type Zebi
        given ctx_unit_Zebi: DerivedUnit[Zebi, 1024 ^ 7, "zebi", "Zi"] =
            DerivedUnit()

        /** Binary prefix for 1024 ^ 8 */
        final type Yobi
        given ctx_unit_Yobi: DerivedUnit[Yobi, 1024 ^ 8, "yobi", "Yi"] =
            DerivedUnit()
