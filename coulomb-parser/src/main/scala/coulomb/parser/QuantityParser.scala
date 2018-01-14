/*
Copyright 2017-2018 Erik Erlandson

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

import scala.util.parsing.combinator.RegexParsers

sealed class QuantityParserException(msg: String) extends Exception(msg)
case class QPLexingException(msg: String) extends QuantityParserException(msg)
case class QPParsingException(msg: String) extends QuantityParserException(msg)

object lexer {
  sealed trait UnitDSLToken
  case class UNIT(unit: String) extends UnitDSLToken
  case class PFUNIT(prefix: String, unit: String) extends UnitDSLToken
  case class EXP(e: Int) extends UnitDSLToken
  case object MULOP extends UnitDSLToken
  case object DIVOP extends UnitDSLToken
  case object POWOP extends UnitDSLToken
  case object LPAREN extends UnitDSLToken
  case object RPAREN extends UnitDSLToken

  class UnitDSLLexer(units: Seq[String], pfunits: Seq[String]) extends RegexParsers {
    override def skipWhitespace = true
    override val whiteSpace = "[ \t]+".r

    val unitRE = {
      val t = (pfunits ++ units).mkString("|")
      (s"($t)").r
    }

    val pfunitRE = {
      val t1 = pfunits.mkString("|")
      val t2 = units.mkString("|")
      (s"($t1)($t2)").r
    }

    val expRE = "([+-]?\\d+)".r

    def apply(expr: String): Try[List[UnitDSLToken]] = ???

    def unit: Parser[UNIT] = unitRE ^^ { u => UNIT(u) }

    def pfunit: Parser[PFUNIT] = pfunitRE ^^ { uu =>
      uu match { case pfunitRE(pf, u) => PFUNIT(pf, u) }
    }

    def exp: Parser[EXP] = expRE ^^ { e => EXP(e.toInt) }

    def mulop = "*" ^^ (_ => MULOP)
    def divop = "/" ^^ (_ => DIVOP)
    def powop = "^" ^^ (_ => POWOP)

    def lparen = "(" ^^ (_ => LPAREN)
    def rparen = ")" ^^ (_ => RPAREN)
  }
}

object parser {
}
