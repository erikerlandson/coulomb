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

trait UnitTypeName[T]:
  /** the name of type.path.Foo[Int] is 'Foo' */
  def name: String
  /** the typeString of type.path.Foo[Int] is Foo[Int] */
  def typeString: String

  override def toString(): String = typeString
  def ==(that: UnitTypeName[?]): Boolean =
    typeString == that.typeString

object UnitTypeName:
    import scala.quoted.*

    inline given [T]: UnitTypeName[T] = ${ meta[T] }

    private def meta[T](using Type[T], Quotes): Expr[UnitTypeName[T]] =
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
        '{ new _root_.coulomb.infra.UnitTypeName[T] {
               val name = ${Expr(n)}
               val typeString = ${Expr(ts)}
          }
        }
