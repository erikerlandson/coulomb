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

package coulomb.conversion

import scala.annotation.implicitNotFound

/** Resolve the operator output type for left and right output types */
@implicitNotFound("No output type resolution in scope for operand value types {VL} and {VR}")
abstract class ValueResolution[VL, VR]:
    type VO

/** conversion of value types, assuming some constant unit type */
@implicitNotFound("No value conversion in scope for value types {VF} => {VT}")
abstract class ValueConversion[VF, VT] extends (VF => VT)

/** Convert a value of type V from implied units UF to UT */
@implicitNotFound("No unit conversion in scope for value type {V}, unit types {UF} => {UT}")
abstract class UnitConversion[V, UF, UT] extends (V => V)
