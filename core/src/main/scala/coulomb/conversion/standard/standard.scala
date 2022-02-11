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

package coulomb.conversion.standard

import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.Coefficient

given ValueResolution[Double, Double] with
    type VO = Double


given ValueConversion[Double, Double] with
    def apply(v: Double): Double = v


transparent inline given ctx[Double, UF, UT](using coef: Coefficient[UF, UT]):
        UnitConversion[Double, UF, UT] =
    new UnitConversion[Double, UF, UT]:
        val c = coef.toDouble
        def apply(v: Double) = c * v
