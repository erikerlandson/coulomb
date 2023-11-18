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

package coulomb.infra

import scala.annotation.implicitNotFound

@implicitNotFound("Unable to simplify unit type ${U}")
abstract class SimplifiedUnit[U]:
    type UO

object SimplifiedUnit:
    import scala.quoted.*

    transparent inline given g_SimplifiedUnit[U]: SimplifiedUnit[U] =
        ${ simplifiedUnit[U] }

    class NC[U, UOp] extends SimplifiedUnit[U]:
        type UO = UOp

    private def simplifiedUnit[U](using
        Quotes,
        Type[U]
    ): Expr[SimplifiedUnit[U]] =
        import quotes.reflect.*
        coulomb.infra.meta.simplify(TypeRepr.of[U]).asType match
            case '[uo] => '{ new NC[U, uo] }
