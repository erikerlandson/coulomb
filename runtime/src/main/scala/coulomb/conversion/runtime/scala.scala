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

package coulomb.conversion.runtime

object scala:
    import _root_.scala.Conversion
    import coulomb.conversion.*
    import coulomb.*
    import coulomb.syntax.*

    given ctx_RuntimeQuantity_Conversion_1V[V]
        : Conversion[RuntimeQuantity[V], RuntimeQuantity[V]] =
        (q: RuntimeQuantity[V]) => q

    given ctx_RuntimeQuantity_Conversion_2V[VF, VT](using
        vc: ValueConversion[VF, VT]
    ): Conversion[RuntimeQuantity[VF], RuntimeQuantity[VT]] =
        (q: RuntimeQuantity[VF]) => RuntimeQuantity(vc(q.value), q.unit)
