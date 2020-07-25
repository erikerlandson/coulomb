/*
Copyright 2017-2020 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb.infra

import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import coulomb._
import coulomb.define._

trait UnitStringAST

object UnitStringAST {
  case object Uni extends UnitStringAST
  case class Def(d: UnitDefinition) extends UnitStringAST
  case class Pre(p: UnitDefinition) extends UnitStringAST
  case class Mul(l: UnitStringAST, r: UnitStringAST) extends UnitStringAST
  case class Div(n: UnitStringAST, d: UnitStringAST) extends UnitStringAST
  case class Pow(b: UnitStringAST, e: Int) extends UnitStringAST

  def render(ast: UnitStringAST, f: UnitDefinition => String): String = ast match {
    case FlatMul(t) => termStrings(t, f).mkString(" ")
    case Div(Uni, d) => s"1/${paren(d, f)}"
    case Div(n, Uni) => render(n, f)
    case Div(n, d) => s"${paren(n, f)}/${paren(d, f)}"
    case Pow(b, e) => {
      val es = if (e < 0) s"($e)" else s"$e"
      s"${paren(b, f)}^$es"
    }
    case Uni => "unitless"
    case Def(d) => f(d)
    case Pre(d) => f(d)
    case _ => "!!!"
  }

  def paren(ast: UnitStringAST, f: UnitDefinition => String): String = {
    val str = render(ast, f)
    if (isAtomic(ast)) str else s"($str)"
  }

  object FlatMul {
    def unapply(ast: UnitStringAST): Option[List[UnitStringAST]] = ast match {
      case Mul(l, r) => {
        val lflat = l match {
          case FlatMul(lf) => lf
          case _ => List(l)
        }
        val rflat = r match {
          case FlatMul(rf) => rf
          case _ => List(r)
        }
        Option(lflat ++ rflat)
      }
      case _ => None
    }
  }

  def termStrings(terms: List[UnitStringAST], f: UnitDefinition => String): List[String] = terms match {
    case Nil => Nil
    case Pre(p) +: Def(d) +: tail => s"${f(p)}${f(d)}" :: termStrings(tail, f)
    case term +: tail => s"${paren(term, f)}" :: termStrings(tail, f)
    case _ => List("!!!")
  }

  def isAtomic(ast: UnitStringAST): Boolean = ast match {
    case Uni => true
    case Pre(_) => true
    case Def(_) => true
    case Pow(Def(_), _) => true
    case Pow(FlatMul(Pre(_) +: Def(_) +: Nil), _) => true
    case FlatMul(Pre(_) +: Def(_) +: Nil) => true
    case _ => false
  }
}

trait HasUnitStringAST[U] {
  def ast: UnitStringAST
  override def toString = ast.toString
}
object HasUnitStringAST {
  import UnitStringAST._

  implicit def evidence0: HasUnitStringAST[Unitless] =
    new HasUnitStringAST[Unitless] { val ast = Uni }

  implicit def evidence1[P](implicit d: DerivedUnit[P, Unitless]): HasUnitStringAST[P] =
    new HasUnitStringAST[P] { val ast = Pre(d) }

  implicit def evidence2[U, D](implicit d: DerivedUnit[U, D], nu: D =:!= Unitless): HasUnitStringAST[U] =
    new HasUnitStringAST[U] { val ast = Def(d) }

  implicit def evidence3[U](implicit bu: GetBaseUnit[U]): HasUnitStringAST[U] =
    new HasUnitStringAST[U] { val ast = Def(bu.bu) }

  implicit def evidence4[L, R](implicit l: HasUnitStringAST[L], r: HasUnitStringAST[R]): HasUnitStringAST[%*[L, R]] =
    new HasUnitStringAST[%*[L, R]] { val ast = Mul(l.ast, r.ast) }

  implicit def evidence5[N, D](implicit n: HasUnitStringAST[N], d: HasUnitStringAST[D]): HasUnitStringAST[%/[N, D]] =
    new HasUnitStringAST[%/[N, D]] { val ast = Div(n.ast, d.ast) }

  implicit def evidence6[B, E](implicit b: HasUnitStringAST[B], e: XIntValue[E]): HasUnitStringAST[%^[B, E]] =
    new HasUnitStringAST[%^[B, E]] { val ast = Pow(b.ast, e.value) }
}
