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

import coulomb.{DeltaQuantity, Quantity}
import coulomb.conversion.ValueConversion

abstract class CoulombSuite extends munit.FunSuite:
    import coulomb.testing.types.*

    extension[V, U](q: Quantity[V, U])
        transparent inline def assertQ[VT, UT](vt: VT): Unit =
            // checking types first
            // checking in string form gives better idiomatic test failure outputs
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // if types check, then asInstanceOf should succeed
            assertEquals(q.value.asInstanceOf[VT], vt)

        transparent inline def assertQD[VT, UT](vt: Double, eps: Option[Double] = None)(using
                vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // epsilon governed by V, but scale by |vt|
            val e = math.abs(vt) * eps.getOrElse(typeEps[V])
            assertEqualsDouble(vc(q.value), vt, e)

    extension[V, U, B](q: DeltaQuantity[V, U, B])
        transparent inline def assertDQ[VT, UT](vt: VT): Unit =
            // checking types first
            // checking in string form gives better idiomatic test failure outputs
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // if types check, then asInstanceOf should succeed
            assertEquals(q.value.asInstanceOf[VT], vt)

    extension[V](v: V)
        transparent inline def assertVT[VT](vt: VT): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(v.asInstanceOf[VT], vt)

        transparent inline def assertVTD[VT](vt: Double, eps: Option[Double] = None)(using
                vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            val e = math.abs(vt) * eps.getOrElse(typeEps[V])
            assertEqualsDouble(vc(v), vt, e)

    inline def assertCE(inline code: String): Unit =
        assert(compileErrors(code).nonEmpty)

object types:
    import scala.quoted.*

    /** typeStr(type.path.Foo[type.path.Bar]) => Foo[Bar] */
    transparent inline def typeStr[T]: String = ${ tsmeta[T] }
    transparent inline def typesEq[T1, T2]: Boolean = ${ temeta[T1, T2] }
    transparent inline def typeEps[V]: Double = ${ tepsmeta[V] }

    private def tepsmeta[V](using Type[V], Quotes): Expr[Double] =
        import quotes.reflect.*
        TypeRepr.of[V] match
            case vt if vt =:= TypeRepr.of[Float] => Expr(1e-5)
            case vt if vt =:= TypeRepr.of[Double] => Expr(1e-10)
            case _ => Expr(1e-10)

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


