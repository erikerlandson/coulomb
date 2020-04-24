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

package coulomb.parser

import scala.util.{ Try, Success, Failure }

import spire.math.Rational

import coulomb._

import coulomb.parser.unitops.UnitTypeString

/**
 * A class that can parse an expression into a unit-typed Quantity
 * {{{
 * import coulomb.parser._
 * // declare a parser with a particular set of legal units
 * val qp = QuantityParser[Meter :: Second :: Kilo :: HNil]
 * val duration = qp[Int, Minute]("60 second") // a duration of one minute
 * val speed = qp[Double, Mile %/ Hour]("10.0 kilometer / second") // prefix units are parsed
 * }}}
 */
class QuantityParser private (
    private val qpp: coulomb.parser.infra.QPP[_],
    private val iseq: Seq[String]) extends Serializable {
  import scala.reflect.runtime.universe.{ Try => _, _ }
  import scala.tools.reflect.ToolBox

  @transient private lazy val lex = new coulomb.parser.lexer.UnitDSLLexer(qpp.unames, qpp.pfnames)
  @transient private lazy val parse = new coulomb.parser.parser.UnitDSLParser(qpp.nameToType)

  @transient private lazy val imports = iseq.map(i => s"import $i\n").mkString("")

  @transient private lazy val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()

  /**
   * Parse an expression into a unit typed Quantity
   * @tparam N numeric type of the quantity
   * @tparam U the unit type - must be compatible (convertable) with the unit type in the expression
   * @param quantityExpr the unit quantity expression. A number followed by a unit expression,
   * where individual units are given by their full names (e.g. "meter", "second", etc), and operators
   * "/", "*", "&#94;". Prefix units may be prepended, e.g. "kilometer". Sub-expressions may be contained
   * in parentheses, e.g. "9.8 meter / (second &#94; 2)"
   * @return a Try value wrapping a Quantity with type parameters N, U. This Try will be Failure(...)
   * in the event of eiher a parsing error or failure to convert the unit expression into Quantity[N,U]
   */
  def apply[N, U](quantityExpr: String)(implicit
      ntt: WeakTypeTag[N],
      uts: UnitTypeString[U]): Try[Quantity[N, U]] = {
    val tpeN = weakTypeOf[N]
    val cast = s".to[$tpeN, ${uts.expr}]"
    for {
      tok <- lex(quantityExpr)
      ast <- parse(tok).toTry
      qret <- Try {
        val code = s"${imports}($ast)${cast}"
        toolbox.eval(toolbox.parse(code)).asInstanceOf[Quantity[N, U]]
      }
    } yield {
      qret
    }
  }

  /**
   * Parse a unit expression and apply it to a value
   * @tparam V the value type
   * @tparam U2 the output unit type
   * @param v the raw value
   * @param u1 the unit expression string, encodes a unit type `U1`
   * e.g. "meter / (second &#94; 2)"
   * @return a Try value wrapping `Quantity[V, U2]`.
   * Effectively, generates `v.withUnit[U1].toUnit[U2]`
   */
  def applyUnitExpr[V, U2](v: V, u1: String)(implicit
    vtt: WeakTypeTag[V],
    ut2: UnitTypeString[U2]
  ): Try[Quantity[V, U2]] = {
    val tpeV = weakTypeOf[V]
    for {
      tok <- lex(u1)
      ut1 <- parse.parseUnit(tok).toTry
      v2q <- Try {
        val code = s"${imports}(v: $tpeV) => (coulomb.Quantity[$tpeV, $ut1](v)).toUnit[${ut2.expr}]"
        toolbox.eval(toolbox.parse(code)).asInstanceOf[V => Quantity[V, U2]]
      }
    } yield {
      v2q(v)
    }
  }

  /**
   * Parse a unit expression and obtain the conversion coefficient to another unit
   * @tparam U2 the unit being converted to
   * @param u1 the unit expression string, encodes a unit type `U1`
   * @return a Try value wrapping the coefficient from U1 -> U2
   */
  def coefficient[U2](u1: String)(implicit
    ut2: UnitTypeString[U2]): Try[Rational] = {
    for {
      tok <- lex(u1)
      ut1 <- parse.parseUnit(tok).toTry
      qret <- Try {
        val code = s"${imports}coulomb.Quantity.coefficient[$ut1, ${ut2.expr}]"
        toolbox.eval(toolbox.parse(code)).asInstanceOf[Rational]
      }
    } yield {
      qret
    }
  }
}

object QuantityParser {
  /**
   * Construct a QuantityParser instance that recognizes a given list of units.
   * @tparam UL a list of unit types to expect, as a shapeless HList
   * @return a QuantityParser object that is aware of the units in `UL`
   * {{{
   * // declare a quantity parser that will recognize "meter", "second" and prefix unit "kilo"
   * // prefix units are automatically detected and parsed as prefixes, e.g. "kilometer"
   * val qp = QuantityParser[Meter :: Second :: Kilo :: HNil]
   * }}}
   */
  def apply[UL <: shapeless.HList](implicit
      qpp: coulomb.parser.infra.QPP[UL]): QuantityParser = {
    new QuantityParser(qpp, List.empty[String])
  }

  /**
   * Similar to `apply[UL]` method but also accepts a list of imports for
   * expression compiling.
   * Used for importing implicits that may not be picked up
   * by default global scope.
   * @tparam UL a list of unit types to expect, as a shapeless HList
   * @param ilist a variable arg list of strings representing imports.
   * Example `"org.custom.algebras._"`
   * @return a QuantityParser object that is aware of the units in `UL`
   * and will `import` the expressions given on `ilist` before
   * parsing unit expressions
   * {{{
   * val qp = QuantityParser[Meter :: Second :: Kilo :: HNil]("org.custom.algebras._", ...)
   * }}}
   */
  def withImports[UL <: shapeless.HList](ilist: String*)(implicit
      qpp: coulomb.parser.infra.QPP[UL]): QuantityParser = {
    new QuantityParser(qpp, ilist)
  }
}
