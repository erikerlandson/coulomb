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

package coulomb.parser

import scala.util.{Try, Success, Failure}

import coulomb.RuntimeUnit
import coulomb.rational.Rational

abstract class RuntimeUnitParser:
    def parse(expr: String): Either[String, RuntimeUnit]
    def render(u: RuntimeUnit): String

object standard:
    sealed abstract class RuntimeUnitExprParser extends RuntimeUnitParser:
        def unames: Map[String, String]
        def pnames: Set[String]
        def parse(expr: String): Either[String, RuntimeUnit] =
            Left("no")
        def render(u: RuntimeUnit): String =
            "no"

    object RuntimeUnitExprParser:
        inline def of[UTL <: Tuple]: RuntimeUnitExprParser =
            ${ coulomb.parser.meta.ofUTL[UTL] }

object infra:
    import _root_.cats.parse.*

    // for consuming whitespace
    val ws: Parser[Unit] = Parser.charIn(" \t\r\n").void
    val ws0: Parser0[Unit] = ws.rep0.void

    // numeric literals parse into UnitConst objects
    val numlit: Parser[RuntimeUnit] =
        cats.parse.Numbers.jsonNumber.flatMap { lit =>
            lit match
                case intlit(v) =>
                    Parser.pure(RuntimeUnit.UnitConst(Rational(v, 1)))
                case fplit(v) =>
                    Parser.pure(RuntimeUnit.UnitConst(Rational(v)))
                case _ =>
                    Parser.failWith[RuntimeUnit](s"bad numeric literal '$lit'")
        }

    object intlit:
        def unapply(lit: String): Option[BigInt] =
            Try { BigInt(lit) }.toOption

    object fplit:
        def unapply(lit: String): Option[Double] =
            Try { lit.toDouble }.toOption

    // a token representing a unit name literal
    // examples: "meter", "second", etc
    // note that for any defined prefix and unit, <prefix><unit> is also valid
    // for example if "kilo" and "meter" are defined units, "kilometer" will also
    // parse correctly as RuntimeUnit.Mul(Kilo, Meter)
    val unitlit: Parser[String] =
        // this might be extended but not until I have a reason and a principle
        // one possible extension would be "any printable char not in { '(', ')', '*', etc }"
        // however I'm not sure if there is an efficient way to express that
        // (starting char can also not be digit, + or -)
        Parser.charIn('a' to 'z').rep.string

    // used for left-factoring the parsing for sequences of mul and div
    val muldivop: Parser[(RuntimeUnit, RuntimeUnit) => RuntimeUnit] =
        (Parser.char('*') <* ws0).as(RuntimeUnit.Mul(_, _)) |
        (Parser.char('/') <* ws0).as(RuntimeUnit.Div(_, _))

    // used for left-factoring the parsing of "^" (power)
    val powop: Parser[(RuntimeUnit, RuntimeUnit) => RuntimeUnit] =
        (Parser.char('^') <* ws0).as { (b: RuntimeUnit, e: RuntimeUnit) =>
            // we do not have to check for Left value of 'evalnum' here
            // because it is verified during parsing
            RuntimeUnit.Pow(b, e.evalnum.toSeq.head)
        }

    def unit(named: Parser[RuntimeUnit]): Parser[RuntimeUnit] =
        lazy val unitexpr: Parser[RuntimeUnit] = Parser.defer {
            // sequence of mul and div operators
            // these have lowest precedence and form the top of the parse tree
            // example: <expr> * <expr> / <expr> * ...
            lazy val muldiv: Parser[RuntimeUnit] =
                chainl1(pow, muldivop)
 
            // powers of form <b> ^ <e>, where:
            // <b> may be any unit expression and
            // <e> is an expression that must evaluate to a valid numeric constant
            lazy val pow: Parser[RuntimeUnit] =
                binaryl1(atom, numeric, powop) 

            // numeric literal, named unit, or sub-expr in parens
            lazy val atom: Parser[RuntimeUnit] =
                paren | (numlit <* ws0) | (named <* ws0)

            // any unit subexpression inside of parens: (<expr>)
            lazy val paren: Parser[RuntimeUnit] =
                unitexpr.between(Parser.char('(') <* ws0, Parser.char(')') <* ws0)

            // parses a RuntimeUnit expression, but only succeeds
            // if it evaluates to a constant numeric value
            // note this is based on 'atom' so non-atomic expressions may only
            // appear inside of ()
            // used to enforce that exponent of powers is a valid numeric value
            lazy val numeric: Parser[RuntimeUnit] =
                atom.flatMap { u =>
                    u.evalnum match
                       case Right(v) =>
                           Parser.pure(RuntimeUnit.UnitConst(v))
                       case Left(e) =>
                           Parser.failWith[RuntimeUnit](e)
                }

            // return the top of the parse tree
            muldiv
        }
        // parse a unit expression, consuming any leading whitespace
        // and requiring parsing reach end of input
        // (trailing whitespace is consumed inside unitexpr)
        ws0.with1 *> unitexpr <* Parser.end

    // parses "raw" unit literals - only succeeds if the literal is
    // in the list of defined units (or unit prefixes)
    // these lists are intended to be constructed at compile-time via scala metaprogramming
    // to reduce errors
    def named(unames: Map[String, String], pnames: Set[String]): Parser[RuntimeUnit] =
        val prefixunit: Parser[(String, String)] =
            if (pnames.isEmpty || unames.isEmpty)
                Parser.fail
            else
                (strset(pnames) ~ strset(unames.keySet `diff` pnames)) <* Parser.end
        unitlit.flatMap { name =>
            if (unames.contains(name))
                // name is a defined unit, return its type
                Parser.pure(RuntimeUnit.UnitType(unames(name)))
            else
                // otherwise see if it can be parsed as <prefix><unit>
                prefixunit.parse(name) match
                    case Right((_, (pn, un))) =>
                        // <prefix><unit> => <prefix> * <unit>
                        val p = RuntimeUnit.UnitType(unames(pn))
                        val u = RuntimeUnit.UnitType(unames(un))
                        Parser.pure(RuntimeUnit.Mul(p, u))
                    case Left(_) =>
                        Parser.failWith[RuntimeUnit](s"unrecognized unit '$name'")
        }

    def strset(ss: Set[String]): Parser[String] =
        strsetvoid(ss).string

    // assumes ss is not empty and all members are length > 0
    // this is guaranteed by construction at compile time
    private def strsetvoid(ss: Set[String]): Parser[Unit] =
        // construct a parser "branch" for each starting character
        val hp = ss.map(_.head).toList.map { h =>
            // set of string tails starting with char h
            val tails = ss.filter(_.head == h).map(_.drop(1)).filter(_.length > 0)
            if (tails.isEmpty)
                // no remaining string tails, just parse char h
                Parser.char(h)
            else
                // parse h followed by parser for tails
                (Parser.char(h) ~ strsetvoid(tails)).void
        }
        // final parser is just "or" of branches: hp(0) | hp(1) | hp(2) ...
        // these are safe to "or" because by construction they share
        // no common left factor
        Parser.oneOf(hp)

    extension (u: RuntimeUnit)
        // attempt to evaluate a RuntimeUnit into a valid
        // numeric constant value. This is used to constrain
        // specialized parsing of RuntimeUnit expressions that 
        // are required to represent numeric constants
        def evalnum: Either[String, Rational] =
            u match
                case RuntimeUnit.UnitConst(v) => Right(v)
                case RuntimeUnit.Mul(lhs, rhs) =>
                    for {
                        lv <- lhs.evalnum
                        rv <- rhs.evalnum
                    } yield (lv * rv)
                case RuntimeUnit.Div(num, den) =>
                    den.evalnum match
                        case Left(e) => Left(e)
                        case Right(dv) =>
                            if (dv == Rational.const0)
                                Left("div by zero")
                            else
                                for {
                                    nv <- num.evalnum
                                } yield (nv / dv)
                case RuntimeUnit.Pow(b, e) =>
                    for {
                        bv <- b.evalnum
                    } yield bv.pow(e)
                case _ =>
                    Left(s"bad numeric expression: $u")

    // the following are combinators for factoring left-recursive grammars
    // they are taken from this paper:
    // https://github.com/j-mie6/design-patterns-for-parser-combinators#readme
    def chainl1[X](p: Parser[X], op: Parser[(X, X) => X]): Parser[X] =
        lazy val rest: Parser0[X => X] = Parser.defer0 {
          val some: Parser0[X => X] = (op, p, rest).mapN {
                // found an <op><rhs>, with possibly more
                (f, y, next) => ((x: X) => next(f(x, y)))
            }
            // none consumes no input
            val none: Parser0[X => X] = Parser.pure(identity[X])
            // "some" expected to be distinguished by leading char of "op"
            // for example lhs + rhs distinguished by '+'
            some | none
        }
        // this feels wrong but .with1 returns With1, not Parser
        rapp(p, rest).asInstanceOf[Parser[X]]

    // like chainl1 but specifically a single left-factored binary expr
    // <left> [<op> <right>]
    def binaryl1[X](pl: Parser[X], pr: Parser[X], op: Parser[(X, X) => X]): Parser[X] =
        val some: Parser0[X => X] = (op, pr).mapN {
            // found an <op><rhs>
            (f, y) => ((x: X) => f(x, y)) 
        }
        val none: Parser0[X => X] = Parser.pure(identity[X])
        rapp(pl, some | none).asInstanceOf[Parser[X]]

    // parsley <*>
    def app[X, Z](f: Parser0[X => Z], x: Parser0[X]): Parser0[Z] =
        (f ~ x).map { case (f, x) => f(x) }

    // parsley <**>
    def rapp[X, Z](x: Parser0[X], f: Parser0[X => Z]): Parser0[Z] =
        (x ~ f).map { case (x, f) => f(x) }

    // parsley zipped
    // can scala 3 '*:' clean this up?
    extension [X1, X2](p: (Parser0[X1], Parser0[X2]))
        def mapN[Z](f: (X1, X2) => Z): Parser0[Z] =
            (p._1 ~ p._2).map { case (x1, x2) => f(x1, x2) }

    extension [X1, X2, X3](p: (Parser0[X1], Parser0[X2], Parser0[X3]))
        def mapN[Z](f: (X1, X2, X3) => Z): Parser0[Z] =
            ((p._1 ~ p._2) ~ p._3).map { case ((x1, x2), x3) => f(x1, x2, x3) }

object meta:
    import scala.quoted.*
    import scala.util.{Try, Success, Failure}
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.infra.meta.{*, given}
    import coulomb.infra.runtime.meta.{*, given}
    import coulomb.conversion.runtimes.mapping.meta.moduleUnits

    import coulomb.parser.standard.RuntimeUnitExprParser

    def ofUTL[UTL](using Quotes, Type[UTL]): Expr[RuntimeUnitExprParser] =
        import quotes.reflect.*
        val (un, pn) = collect(typeReprList(TypeRepr.of[UTL]))
        // remove any unit names that are empty strings
        val pn1 = pn.filter(_.length > 0)
        val un1 = un.filter { case (k, _) =>
            k.length > 0
        }
        '{
            new RuntimeUnitExprParser:
                val unames = ${ Expr(un1) }
                val pnames = ${ Expr(pn1) }
        }

    private def collect(using Quotes)(tl: List[quotes.reflect.TypeRepr]): (Map[String, String], Set[String]) =
        import quotes.reflect.*
        tl match
            case Nil => (Map.empty[String, String], Set.empty[String])
            case head :: tail =>
                val (un, pn) = collect(tail)
                head match
                    case ConstantType(StringConstant(mname)) =>
                        val (mu, mp) = collect(moduleUnits(mname))
                        (un ++ mu, pn ++ mp)
                    case baseunitTR(tr) =>
                        val AppliedType(_, List(_, n, _)) = tr: @unchecked
                        val ConstantType(StringConstant(name)) = n: @unchecked
                        // base units are never prefix units because prefix units are
                        // derived from '1' (unitless)
                        (un + (name -> head.typeSymbol.fullName), pn)
                    case derivedunitTR(tr) =>
                        val AppliedType(_, List(_, _, n, _)) = tr: @unchecked
                        val ConstantType(StringConstant(name)) = n: @unchecked
                        // always add to unit types
                        val unr = un + (name -> head.typeSymbol.fullName)
                        // if it is derived from unitless, also add it to prefix unit set
                        val pnr = if (convertible(head, TypeRepr.of[1])) pn + name else pn
                        (unr, pnr)
                    case _ =>
                        report.errorAndAbort(s"collect: bad type ${head.show}")
                        null.asInstanceOf[Nothing]
