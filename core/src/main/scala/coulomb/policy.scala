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

object priority:
    // lower number = higher priority
    class Prio0 extends Prio1
    object Prio0 { given p: Prio0 = Prio0() }

    class Prio1 extends Prio2
    object Prio1 { given p: Prio1 = Prio1() }

    class Prio2 extends Prio3
    object Prio2 { given p: Prio2 = Prio2() }

    class Prio3 extends Prio4
    object Prio3 { given p: Prio3 = Prio3() }

    class Prio4 extends Prio5
    object Prio4 { given p: Prio4 = Prio4() }

    class Prio5 extends Prio6
    object Prio5 { given p: Prio5 = Prio5() }

    class Prio6 extends Prio7
    object Prio6 { given p: Prio6 = Prio6() }

    class Prio7 extends Prio8
    object Prio7 { given p: Prio7 = Prio7() }

    class Prio8 extends Prio9
    object Prio8 { given p: Prio8 = Prio8() }

    class Prio9
    object Prio9 { given p: Prio9 = Prio9() }

/**
 * A policy that supports all standard operation definitions, including those
 * involving implicit conversions of units or value types.
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
    export coulomb.ops.algebra.cats.all.given

/**
 * A policy that supports all standard operations, but does not support
 * operations that involve any implicit conversions of either units or value
 * types. For example, one may add two quantities having the same unit and value
 * type, but not quantities differing in either their value type or unit.
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
    export coulomb.ops.algebra.cats.all.given
