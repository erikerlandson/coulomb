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

package coulomb.testing.checks

abstract class TypeString[T]:
  /** the name of type.path.Foo[Int] is 'Foo' */
  def name: String
  /** the typeString of type.path.Foo[Int] is Foo[Int] */
  def typeString: String

  override def toString(): String = typeString

  def ==(that: TypeString[?]): Boolean =
    typeString == that.typeString

object TypeString:
    import scala.quoted.*

    inline given [T]: TypeString[T] = ${ meta[T] }

    private def meta[T](using Type[T], Quotes): Expr[TypeString[T]] =
        import quotes.reflect.*
        def work(tr: TypeRepr): String = tr match
            case AppliedType(tc, ta) =>
                val tcn = tc.typeSymbol.name
                val as = ta.map(work)
                if (as.length == 0) tcn else
                    tcn + "[" + as.mkString(",") + "]"
            case t => t.typeSymbol.name
        val t = TypeRepr.of[T].dealias
        val n = t.typeSymbol.name
        val ts = work(t)
        '{ new TypeString[T] {
               val name = ${Expr(n)}
               val typeString = ${Expr(ts)}
          }
        }

abstract class TypesEq[T1, T2]:
    val value: Boolean

object TypesEq:
    import scala.quoted.*

    inline given [T1, T2]: TypesEq[T1, T2] = ${ meta[T1, T2] }

    private def meta[T1, T2](using Type[T1], Type[T2], Quotes): Expr[TypesEq[T1, T2]] =
        import quotes.reflect.*
        val eql = TypeRepr.of[T1] =:= TypeRepr.of[T2]
        '{ new TypesEq[T1, T2] {
            val value = ${Expr(eql)}
         }}

def typesEQ[T1, T2](using te: TypesEq[T1, T2]): Boolean = te.value

