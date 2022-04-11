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

package coulomb.conversion.spire

object unit:
    import coulomb.conversion.*
    import coulomb.conversion.spire.coefficients.*
    import _root_.spire.math.Rational

    export coulomb.conversion.standard.unit.given

    inline given ctx_UC_SpireRational[UF, UT]: UnitConversion[Rational, UF, UT] =
        new infra.RationalUC[UF, UT](coefficientRational[UF, UT])

    object infra:
        class RationalUC[UF, UT](c: Rational) extends UnitConversion[Rational, UF, UT]:
            def apply(v: Rational): Rational = c * v