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

package coulomb.conversion.standard.implicitConversion

import coulomb.Quantity
import coulomb.conversion.{ValueConversion, UnitConversion}

transparent inline given contextImplicitConversion[VF, UF, VT, UT](using
    vc: ValueConversion[VF, VT],
    uc: UnitConversion[VT, UF, UT]
        ): scala.Conversion[Quantity[VF, UF], Quantity[VT, UT]] =
    new scala.Conversion[Quantity[VF, UF], Quantity[VT, UT]]:
        def apply(q: Quantity[VF, UF]): Quantity[VT, UT] = 
            uc(vc(q.value)).withUnit[UT]
