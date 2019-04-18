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

class QuantityParser(qpp: coulomb.parser.infra.QPP[_]) {
  import scala.reflect.runtime.universe.{ Try => _, _ }
  import scala.tools.reflect.ToolBox

  private val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()

  val lex = new coulomb.parser.lexer.UnitDSLLexer(qpp.unames, qpp.pfnames)
  val parse = new coulomb.parser.parser.UnitDSLParser(qpp.nameToType)

  val unitDecls = qpp.decls.map { d => s"$d\n" }.mkString("")

  val imports = Seq(
    "coulomb._",
    "coulomb.define._",
    "spire.math.Rational"
  ).map { i => s"import $i\n" }.mkString("")

  // figure out how to pre-compile this preamble
  val preamble = s"${imports}${unitDecls}"

  def apply[N, U](quantityExpr: String)(implicit
      ntt: TypeTag[N],
      uts: coulomb.parser.infra.UnitTypeString[U]): Try[Quantity[N, U]] = {
    val tpeN = typeOf[N]
    val cast = s".toUnit[${uts.expr}].toNumeric[$tpeN]"
    for {
      tok <- lex(quantityExpr)
      ast <- parse(tok).toTry
      code <- Try { s"${preamble}($ast)${cast}" }
      qeTree <- Try { toolbox.parse(code) }
      qeEval <- Try { toolbox.eval(qeTree) }
      qret <- Try { qeEval.asInstanceOf[Quantity[N, U]] }
    } yield {
      qret
    }
  }
}

object QuantityParser {
  def apply[U <: shapeless.HList](implicit qpp: coulomb.parser.infra.QPP[U]): QuantityParser = {
    new QuantityParser(qpp)
  }
}
