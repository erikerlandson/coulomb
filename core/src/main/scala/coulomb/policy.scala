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

package coulomb.policy

/**
 * A policy that supports all standard operation definitions, including those involving
 * implicit conversions of units or value types.
 *
 * {{{
 * import coulomb.*
 * import coulomb.policy.standard.given
 *
 * import algebra.instances.all.given
 * import coulomb.ops.algebra.all.given
 * }}}
 */
object standard:
    export coulomb.ops.standard.all.given
    export coulomb.ops.resolution.standard.given
    export coulomb.conversion.standard.value.given
    export coulomb.conversion.standard.unit.given
    export coulomb.conversion.standard.scala.given
    export coulomb.cats.all.given

/**
 * A policy that supports all standard operations, but does not support operations that
 * involve any implicit conversions of either units or value types.
 * For example, one may add two quantities having the same unit and value type, but
 * not quantities differing in either their value type or unit.
 *
 * Explicit conversions such as `q.toUnit` or `q.toValue` are allowed.
 *
 * {{{
 * import coulomb.*
 * import coulomb.policy.strict.given
 *
 * import algebra.instances.all.given
 * import coulomb.ops.algebra.all.given
 * }}}
 */
object strict:
    export coulomb.ops.standard.all.given
    export coulomb.conversion.standard.value.given
    export coulomb.conversion.standard.unit.given
    export coulomb.cats.all.given

/**
 * By default, coulomb will treat any unrecognized type as a base unit.
 * To disable this behavior and raise a compile error on any type not declared as
 * a BaseUnit, DerivedUnit, etc:
 *
 * {{{
 * import coulomb.policy.strictUnitExpressions.given
 * }}}
 */
object strictUnitExpressions:
    import coulomb.policy.infra.StrictUnitExpressions
    given ctx_StrictUnitExpressions: StrictUnitExpressions with {}

object infra:
    /**
     * When a context variable of this type is in scope, coulomb will raise a compile error
     * if it encounters a type not declared as a unit.
     */
    sealed trait StrictUnitExpressions
