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

package coulomb.ops.algebra

import scala.annotation.implicitNotFound

// there is no typelevel community typeclass that expresses the concept
// "supports raising to fractional powers, without truncation"
// The closest thing is spire NRoot, but it is also defined on truncating integer types,
// so it is not helpful for distinguishing "pow" from "tpow", and in any case requires spire
// https://github.com/typelevel/spire/issues/741

@implicitNotFound("Fractional power not defined for value type ${V}")
abstract class FractionalPower[V]:
    /** returns v^e */
    def pow(v: V, e: Double): V

@implicitNotFound("Truncating power not defined for value type ${V}")
abstract class TruncatingPower[V]:
    /** returns v^e, truncated to integer value (toward zero) */
    def tpow(v: V, e: Double): V
