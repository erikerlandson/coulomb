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

import scala.util.Try

import scala.util.parsing.combinator.{Parsers, RegexParsers}
import scala.util.parsing.input.{NoPosition, Position, Reader}

object infra {
  import scala.language.implicitConversions
  import scala.reflect.runtime.universe._

  import shapeless._

  import coulomb._
  import coulomb.infra._
  import coulomb.define._

  trait SetInsert[E, S] {
    type Out
  }
  object SetInsert {
    type Aux[E, S, O] = SetInsert[E, S] { type Out = O }
    implicit def evidence0[E]: Aux[E, HNil, E :: HNil] = new SetInsert[E, HNil] { type Out = E :: HNil }
    implicit def evidence1[E, T <: HList]: Aux[E, E :: T, E :: T] =
      new SetInsert[E, E :: T] { type Out = E :: T }
    implicit def evidence2[E, H, T <: HList, O <: HList ](implicit
        ne: E =:!= H,
        rc: Aux[E, T, O]): Aux[E, H :: T, H :: O] =
      new SetInsert[E, H :: T] { type Out = H :: O }
  }

  trait SetUnion[S1, S2] {
    type Out
  }
  object SetUnion {
    type Aux[S1, S2, O] = SetUnion[S1, S2] { type Out = O }
    implicit def evidence0[S2]: Aux[HNil, S2, S2] = new SetUnion[HNil, S2] { type Out = S2 }
    implicit def evidence1[H, T <: HList, S2, T2, O](implicit
        ih: SetInsert.Aux[H, S2, T2],
        rc: Aux[T, T2, O]): Aux[H :: T, S2, O] =
      new SetUnion[H :: T, S2] { type Out = O }
  }

  trait UnitClosure[U] {
    type Out
  }
  object UnitClosure {
    type Aux[U, O] = UnitClosure[U] { type Out = O }

    implicit def evidenceUnitless: Aux[Unitless, Unitless :: HNil] =
      new UnitClosure[Unitless] { type Out = Unitless :: HNil }

    implicit def evidenceBase[U](implicit bu: BaseUnit[U]): Aux[U, U :: HNil] =
      new UnitClosure[U] { type Out = U :: HNil }

    implicit def evidenceDerivedUnit[U, D, DC <: HList](implicit
        du: DerivedUnit[U, D],
        dc: Aux[D, DC]): Aux[U, U :: DC] = {
      new UnitClosure[U] { type Out = U :: DC }
    }

    implicit def evidenceMul[L, LC, R, RC, OC](implicit
        l: Aux[L, LC],
        r: Aux[R, RC],
        u: SetUnion.Aux[LC, RC, OC]): Aux[%*[L, R], OC] = {
      new UnitClosure[%*[L, R]] { type Out = OC }
    }

    implicit def evidenceDiv[L, LC, R, RC, OC](implicit
        l: Aux[L, LC],
        r: Aux[R, RC],
        u: SetUnion.Aux[LC, RC, OC]): Aux[%/[L, R], OC] = {
      new UnitClosure[%/[L, R]] { type Out = OC }
    }

    implicit def evidencePow[B, BC, E](implicit
        b: Aux[B, BC],
        e: XIntValue[E]): Aux[%^[B, E], BC] = {
      new UnitClosure[%^[B, E]] {
        type Out = BC
      }
    }

    implicit def evidenceHNil: Aux[HNil, HNil] =
      new UnitClosure[HNil] { type Out = HNil }

    implicit def evidenceHList[H, T <: HList, HC, TC, OC](implicit
        h: Aux[H, HC],
        t: Aux[T, TC],
        u: SetUnion.Aux[HC, TC, OC]): Aux[H :: T, OC] = {
      new UnitClosure[H :: T] { type Out = OC }
    }
  }

  trait UnitTypeString[U] {
    def expr: String
  }
  object UnitTypeString {
    implicit def evidenceUnitless: UnitTypeString[Unitless] =
      new UnitTypeString[Unitless] { val expr = "coulomb.Unitless" }

    implicit def evidenceBase[U](implicit utt: TypeTag[U], bu: BaseUnit[U]): UnitTypeString[U] =
      new UnitTypeString[U] { val expr = utt.tpe.typeSymbol.fullName }

    implicit def evidenceDerived[U](implicit utt: TypeTag[U], du: DerivedUnit[U, _]): UnitTypeString[U] =
      new UnitTypeString[U] { val expr = utt.tpe.typeSymbol.fullName }

    implicit def evidenceMul[L, R](implicit udfL: UnitTypeString[L], udfR: UnitTypeString[R]): UnitTypeString[%*[L, R]] =
      new UnitTypeString[%*[L, R]] { val expr = s"%*[${udfL.expr}, ${udfR.expr}]" }

    implicit def evidenceDiv[L, R](implicit udfL: UnitTypeString[L], udfR: UnitTypeString[R]): UnitTypeString[%/[L, R]] =
      new UnitTypeString[%/[L, R]] { val expr = s"%/[${udfL.expr}, ${udfR.expr}]" }

    implicit def evidencePow[B, E](implicit udfB: UnitTypeString[B], e: XIntValue[E]): UnitTypeString[%^[B, E]] =
      new UnitTypeString[%^[B, E]] { val expr = s"%^[${udfB.expr}, Witness.`${e.value}`.T]" }
  }

  trait UnitDefCode[U] {
    def code: String
  }
  object UnitDefCode {
    implicit def evidenceBase[U](implicit utt: TypeTag[U], bu: BaseUnit[U]): UnitDefCode[U] = {
      val tpe = utt.tpe.typeSymbol.name
      val tpeFull = utt.tpe.typeSymbol.fullName
      new UnitDefCode[U] {
        val code = s"""implicit val qpDefineUnit$tpe = new BaseUnit[$tpeFull]("${bu.name}", "${bu.abbv}")"""
      }
    }

    implicit def evidenceDerived[U, D](implicit utt: TypeTag[U], du: DerivedUnit[U, D], uts: UnitTypeString[D]): UnitDefCode[U] = {
      val tpe = utt.tpe.typeSymbol.name
      val tpeFull = utt.tpe.typeSymbol.fullName
      new UnitDefCode[U] {
        val code = s"""implicit val qpDefineUnit$tpe = new DerivedUnit[$tpeFull, ${uts.expr}](Rational("${du.coef}"), "${du.name}", "${du.abbv}")"""
      }
    }
  }
}

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

    def apply(expr: String): Try[List[UnitDSLToken]] = {
      parse(tokens, expr) match {
        case NoSuccess(msg, next) => scala.util.Failure(QPLexingException(msg))
        case Success(result, next) => scala.util.Success(result)
      }
    }

    lazy val tokens: Parser[List[UnitDSLToken]] = {
      phrase(rep1(
        mulop | divop | powop |
        lparen | rparen |
        exp | unit | pfunit
      )) ^^ { x => x }
    }

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

object ast {
  sealed trait UnitAST {
    override def toString = this match {
      case Unit(u) => u
      case Mul(lhs, rhs) => s"%*[$lhs, $rhs]"
      case Div(num, den) => s"%/[$num, $den]"
      case Pow(rad, exp) => s"%^[$rad, Witness.`$exp`.T]"
    }
  }
  case class Unit(unitType: String) extends UnitAST
  case class Mul(lhs: UnitAST, rhs: UnitAST) extends UnitAST
  case class Div(num: UnitAST, den: UnitAST) extends UnitAST
  case class Pow(rad: UnitAST, exp: Int) extends UnitAST

  object UnitAST {
  }
}

object parser {
  import lexer._
  import ast._

  class UnitDSLTokenReader(tokens: Seq[UnitDSLToken]) extends Reader[UnitDSLToken] {
    override def first: UnitDSLToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[UnitDSLToken] = new UnitDSLTokenReader(tokens.tail)
  }

  
}
