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

import scala.util.parsing.combinator.{Parsers, RegexParsers}
import scala.util.parsing.input.{NoPosition, Position, Reader}

sealed class QuantityParserException(msg: String) extends Exception(msg)
case class QPLexingException(msg: String) extends QuantityParserException(msg)
case class QPParsingException(msg: String) extends QuantityParserException(msg)

object lexer {
  sealed trait UnitDSLToken
  case class UNIT(unit: String) extends UnitDSLToken
  case class PFUNIT(prefix: String, unit: String) extends UnitDSLToken
  case class NUMLIT(n: Double, isInt: Boolean) extends UnitDSLToken
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

    // order of "x.y" alternatives matters here, to bind longest possible sequence preferentially
    val fpRE = "([+-]?(\\d+\\.\\d+|\\d+\\.|\\d+|\\.\\d+)([eE][+-]?\\d+)?)".r

    def apply(expr: String): Try[List[UnitDSLToken]] = {
      parse(tokens, expr) match {
        case NoSuccess(msg, next) => scala.util.Failure(QPLexingException(msg))
        case Success(result, next) => scala.util.Success(result)
      }
    }

    lazy val tokens: Parser[List[UnitDSLToken]] = {
      // pfunit before unit matters, to bind both prefix and unit preferntially
      phrase(rep1(
        numlit |
        pfunit | unit |
        mulop | divop | powop |
        lparen | rparen
      )) ^^ { x => x }
    }

    def unit: Parser[UNIT] = unitRE ^^ { u => UNIT(u) }

    def pfunit: Parser[PFUNIT] = pfunitRE ^^ { case pfunitRE(pf, u) => PFUNIT(pf, u) }

    def numlit: Parser[NUMLIT] = fpRE ^^ { t =>
      Try { t.toInt } match {
        case scala.util.Success(i) => NUMLIT(i, true)
        case _ => NUMLIT(t.toDouble, false)
      }
    }

    def mulop = "*" ^^ (_ => MULOP)
    def divop = "/" ^^ (_ => DIVOP)
    def powop = "^" ^^ (_ => POWOP)

    def lparen = "(" ^^ (_ => LPAREN)
    def rparen = ")" ^^ (_ => RPAREN)
  }
}

object ast {
  sealed trait UnitAST {
    override def toString = this match {
      case Quant(v, u) => s"(Rational($v)).withUnit[$u]"
      case Unit(u) => u
      case Mul(lhs, rhs) => s"%*[$lhs, $rhs]"
      case Div(num, den) => s"%/[$num, $den]"
      case Pow(rad, exp) => s"%^[$rad, $exp]"
    }
  }
  case class Quant(v: Double, u: String) extends UnitAST
  case class Unit(unitType: String) extends UnitAST
  case class Mul(lhs: UnitAST, rhs: UnitAST) extends UnitAST
  case class Div(num: UnitAST, den: UnitAST) extends UnitAST
  case class Pow(rad: UnitAST, exp: Int) extends UnitAST
}

object parser {
  import lexer._
  import ast._

  class UnitDSLParser(unitToType: Map[String, String]) extends Parsers {
    override type Elem = UnitDSLToken

    class UnitDSLTokenReader(tokens: Seq[UnitDSLToken]) extends Reader[UnitDSLToken] {
      override def first: UnitDSLToken = tokens.head
      override def atEnd: Boolean = tokens.isEmpty
      override def pos: Position = NoPosition
      override def rest: Reader[UnitDSLToken] = new UnitDSLTokenReader(tokens.tail)
    }

    def program: Parser[UnitAST] = {
      phrase(quant)
    }

    def quant: Parser[UnitAST] = {
      (qval ~ unitexpr) ^^ { case NUMLIT(v, _) ~ u => Quant(v, u.toString) }
    }

    def unitexpr: Parser[UnitAST] = {
      exprL2
    }

    def exprL2: Parser[UnitAST] = {
      val mul = (exprL1 ~ MULOP ~ unitexpr) ^^ { case lhs ~ _ ~ rhs => Mul(lhs, rhs) }
      val div = (exprL1 ~ DIVOP ~ unitexpr) ^^ { case num ~ _ ~ den => Div(num, den) }
      mul | div | exprL1
    }

    def exprL1: Parser[UnitAST] = {
      val pow = (atomic ~ POWOP ~ exponent) ^^ { case b ~ _ ~ NUMLIT(e, true) => Pow(b, e.toInt) }
      pow | atomic
    }

    def atomic: Parser[UnitAST] = {
      val paren = (LPAREN ~ unitexpr ~ RPAREN) ^^ { case _ ~ expr ~ _ => expr }
      val pfu = prefixunit ^^ { case PFUNIT(pname, uname) => Mul(Unit(unitToType(pname)), Unit(unitToType(uname))) }
      val u = unit ^^ { case UNIT(uname) => Unit(unitToType(uname)) }
      paren | pfu | u
    }

    def unit: Parser[UNIT] = {
      accept("unit", { case u @ UNIT(uname) => u })
    }

    def prefixunit: Parser[PFUNIT] = {
      accept("pfunit", { case pfu @ PFUNIT(pname, uname) => pfu })
    }

    def exponent: Parser[NUMLIT] = {
      accept("exponent", { case exp @ NUMLIT(e, true) => exp })
    }

    def qval: Parser[NUMLIT] = {
      accept("quantity value", { case qv @ NUMLIT(v, _) => qv })
    }

    def apply(tokens: Seq[UnitDSLToken]): Either[QPParsingException, UnitAST] = {
      val reader = new UnitDSLTokenReader(tokens)
      program(reader) match {
        case NoSuccess(msg, next) => Left(QPParsingException(msg))
        case Success(result, next) => Right(result)
      }
    }
  }
}
