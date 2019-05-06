/*
Copyright 2017-2019 Erik Erlandson

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

import coulomb._

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
class QuantityParser private (private val qpp: coulomb.parser.infra.QPP[_]) extends Serializable {
  import scala.reflect.runtime.universe.{ Try => _, _ }
  import scala.tools.reflect.ToolBox

  @transient private lazy val lex = new coulomb.parser.lexer.UnitDSLLexer(qpp.unames, qpp.pfnames)
  @transient private lazy val parse = new coulomb.parser.parser.UnitDSLParser(qpp.nameToType)

  @transient lazy val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()

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
      ntt: TypeTag[N],
      uts: coulomb.parser.unitops.UnitTypeString[U]): Try[Quantity[N, U]] = {
    val tpeN = typeOf[N]
    val cast = s".toUnit[${uts.expr}].toNumeric[$tpeN]"
    for {
      tok <- lex(quantityExpr)
      ast <- parse(tok).toTry
      code <- Try { s"($ast)${cast}" }
      qeTree <- Try { toolbox.parse(code) }
      qeEval <- Try { toolbox.eval(qeTree) }
      qret <- Try { qeEval.asInstanceOf[Quantity[N, U]] }
    } yield {
      qret
    }
  }
}

object QuantityParser {
  /**
   * Construct a QuantityParser instance that recognizes a given list of units.
   * @tparam UL a list of unit types to expect, as a shapeless HList
   * {{{
   * // declare a quantity parser that will recognize "meter", "second" and prefix unit "kilo"
   * // prefix units are automatically detected and parsed as prefixes, e.g. "kilometer"
   * val qp = QuantityParser[Meter :: Second :: Kilo :: HNil]
   * }}}
   */
  def apply[UL <: shapeless.HList](implicit qpp: coulomb.parser.infra.QPP[UL]): QuantityParser = {
    new QuantityParser(qpp)
  }
}
