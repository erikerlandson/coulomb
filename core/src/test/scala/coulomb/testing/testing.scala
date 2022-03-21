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
        inline def assertQ[VT, UT](vt: VT): Unit =
            // checking types first
            // checking in string form gives better idiomatic test failure outputs
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // if types check, then asInstanceOf should succeed
            assertEquals(q.value.asInstanceOf[VT], vt)

        inline def assertQD[VT, UT](vt: Double, eps: Option[Double] = None)(using
                vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // epsilon governed by V, but scale by |vt|
            val e = math.abs(vt) * eps.getOrElse(typeEps[V])
            assertEqualsDouble(vc(q.value), vt, e)

    extension[V, U, B](q: DeltaQuantity[V, U, B])
        inline def assertDQ[VT, UT](vt: VT): Unit =
            // checking types first
            // checking in string form gives better idiomatic test failure outputs
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // if types check, then asInstanceOf should succeed
            assertEquals(q.value.asInstanceOf[VT], vt)

        inline def assertDQD[VT, UT](vt: Double, eps: Option[Double] = None)(using
                vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(typeStr[U], typeStr[UT])
            // epsilon governed by V, but scale by |vt|
            val e = math.abs(vt) * eps.getOrElse(typeEps[V])
            assertEqualsDouble(vc(q.value), vt, e)

    extension[V](v: V)
        inline def assertVT[VT](vt: VT): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            assertEquals(v.asInstanceOf[VT], vt)

        inline def assertVTD[VT](vt: Double, eps: Option[Double] = None)(using
                vc: ValueConversion[V, Double]): Unit =
            assertEquals(typeStr[V], typeStr[VT])
            val e = math.abs(vt) * eps.getOrElse(typeEps[V])
            assertEqualsDouble(vc(v), vt, e)

    inline def assertCE(inline code: String): Unit =
        assert(compileErrors(code).nonEmpty)

object types:
    import scala.quoted.*

    /** typeStr(type.path.Foo[type.path.Bar]) => Foo[Bar] */
    inline def typeStr[T]: String = ${ tsmeta[T] }
    inline def typesEq[T1, T2]: Boolean = ${ temeta[T1, T2] }
    inline def typeEps[V]: Double = ${ tepsmeta[V] }

    private def tsmeta[T](using Type[T], Quotes): Expr[String] =
        import quotes.reflect.*
        Expr(coulomb.infra.meta.typestr(TypeRepr.of[T]))

    private def temeta[T1, T2](using Type[T1], Type[T2], Quotes): Expr[Boolean] =
        import quotes.reflect.*
        val eql = TypeRepr.of[T1] =:= TypeRepr.of[T2]
        Expr(eql)

    private def tepsmeta[V](using Type[V], Quotes): Expr[Double] =
        import quotes.reflect.*
        TypeRepr.of[V] match
            case vt if vt =:= TypeRepr.of[Float] => Expr(1e-5)
            case vt if vt =:= TypeRepr.of[Double] => Expr(1e-10)
            case _ => Expr(1e-10)

object serde:
    import java.io.*

    private class SerDeInputStream(inputStream: InputStream) extends ObjectInputStream(inputStream):
        override def resolveClass(desc: ObjectStreamClass): Class[?] =
            try
                Class.forName(desc.getName, false, getClass.getClassLoader)
            catch
                case ex: ClassNotFoundException => super.resolveClass(desc)

    def roundTripSerDe[T](v: T): T =
        val bufout = new ByteArrayOutputStream()
        val obout = new ObjectOutputStream(bufout)

        obout.writeObject(v)

        val bufin = new ByteArrayInputStream(bufout.toByteArray)
        val obin = new SerDeInputStream(bufin)

        obin.readObject().asInstanceOf[T]
