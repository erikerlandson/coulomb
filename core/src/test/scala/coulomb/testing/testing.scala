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

package coulomb.testing

import coulomb.Quantity
import coulomb.conversion.ValueConversion

abstract class CoulombSuite extends munit.FunSuite:
    import coulomb.testing.types.*

    extension[V, U](q: Quantity[V, U])
        inline def checkQ[VT, UT](tval: Double, eps: Double = 1e-4)(using vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])  
            assertEquals(typeStr[U], typeStr[UT])
            assertEqualsDouble(vc(q.value), tval, eps)

    extension[V](v: V)
        inline def checkVT[VT](tval: Double, eps: Double = 1e-4)(using vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            assertEqualsDouble(vc(v), tval, eps)

object types:
    import scala.quoted.*

    /** typeStr(type.path.Foo[type.path.Bar]) => Foo[Bar] */
    inline def typeStr[T]: String = ${ tsmeta[T] }
    inline def typesEq[T1, T2]: Boolean = ${ temeta[T1, T2] }

    private def tsmeta[T](using Type[T], Quotes): Expr[String] =
        import quotes.reflect.*
        def work(tr: TypeRepr): String = tr match
            case AppliedType(tc, ta) =>
                val tcn = tc.typeSymbol.name
                val as = ta.map(work)
                if (as.length == 0) tcn else
                    tcn + "[" + as.mkString(",") + "]"
            case t => t.typeSymbol.name
        val t = TypeRepr.of[T].dealias
        val ts = work(t)
        Expr(ts)

    private def temeta[T1, T2](using Type[T1], Type[T2], Quotes): Expr[Boolean] =
        import quotes.reflect.*
        val eql = TypeRepr.of[T1] =:= TypeRepr.of[T2]
        Expr(eql)


