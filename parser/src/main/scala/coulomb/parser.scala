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

import coulomb.RuntimeUnit

abstract class RuntimeUnitParser:
    def parse(expr: String): Either[String, RuntimeUnit]
    def render(u: RuntimeUnit): String

object standard:
    sealed abstract class RuntimeUnitExprParser extends RuntimeUnitParser:
        protected def unames: Map[String, String]
        protected def pnames: Set[String]
        def parse(expr: String): Either[String, RuntimeUnit] =
            Left("no")
        def render(u: RuntimeUnit): String =
            "no"

    object RuntimeUnitExprParser:
        inline def of[UTL]: RuntimeUnitExprParser = ${ meta.ofUTL[UTL] }

object infra:
    import _root_.cats.parse.*

    val ws: Parser[Unit] = Parser.charIn(" \t\r\n").void
    val ws0: Parser0[Unit] = ws.rep0.void

    def unit(named: Parser[RuntimeUnit]): Parser[RuntimeUnit] =
        lazy val expr: Parser[RuntimeUnit] = Parser.defer {
            val termops: Parser[(RuntimeUnit, RuntimeUnit) => RuntimeUnit] =
                (Parser.char('*') <* ws0).as(RuntimeUnit.Mul(_, _)) |
                (Parser.char('/') <* ws0).as(RuntimeUnit.Div(_, _))
            lazy val term: Parser[RuntimeUnit] =
                chainl1(atom, termops)
            lazy val atom: Parser[RuntimeUnit] =
                paren | (named <* ws0)
            lazy val paren: Parser[RuntimeUnit] =
                expr.between(Parser.char('(') <* ws0, Parser.char(')') <* ws0)
            term
        }
        ws0.with1 *> expr <* Parser.end

    def chainl1[X](p: Parser[X], op: Parser[(X, X) => X]): Parser[X] =
        lazy val rest: Parser0[X => X] = Parser.defer0 {
          val some: Parser0[X => X] = (op, p, rest).mapN {
                // found an <op><rhs>, with possibly more
                (f, y, next) => ((x: X) => next(f(x, y)))
            }
            // none consumes no input
            val none: Parser0[X => X] = Parser.pure(identity[X])
            some | none
        }
        // this feels wrong but .with1 returns With1, not Parser
        rapp(p, rest).asInstanceOf[Parser[X]]

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

    def unitname: Parser[String] =
        // this might be extended but not until I have a reason and a principle
        // one possible extension would be "any printable char not in { '(', ')', '*', etc }"
        // however I'm not sure if there is an efficient way to express that
        // (starting char can also not be digit, + or -)
        Parser.charIn('a' to 'z').rep.string

    def named(unames: Map[String, String], pnames: Set[String]): Parser[RuntimeUnit] =
        val prefixunit = (strset(pnames) ~ strset(unames.keySet `diff` pnames)) <* Parser.end
        unitname.flatMap { name =>
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

object meta:
    import scala.quoted.*
    import scala.util.{Try, Success, Failure}
    import scala.unchecked
    import scala.language.implicitConversions

    import coulomb.infra.meta.{*, given}
    import coulomb.infra.runtime.meta.{*, given}
    import coulomb.conversion.runtimes.mapping.meta.{*, given}

    import coulomb.parser.standard.RuntimeUnitExprParser

    import coulomb.syntax.typelist.{TNil, &:}

    def ofUTL[UTL](using Quotes, Type[UTL]): Expr[RuntimeUnitExprParser] =
        import quotes.reflect.*
        val (un, pn) = collect(typeReprList(TypeRepr.of[UTL]))
        // remove any unit names that are empty strings
        val pn1 = pn.filter(_.length > 0)
        val un1 = un.filter { case (k, _) =>
            k.length > 0
        }
        if (un1.isEmpty)
            // unit map must be non-empty
            report.errorAndAbort(s"ofUTL: no defined unit names")
        '{
            new RuntimeUnitExprParser:
                protected val unames = ${ Expr(un1) }
                protected val pnames = ${ Expr(pn1) }
        }

    private def collect(using Quotes)(tl: List[quotes.reflect.TypeRepr]): (Map[String, String], Set[String]) =
        import quotes.reflect.*
        tl match
            case Nil => (Map.empty[String, String], Set.empty[String])
            case head :: tail =>
                val (un, pn) = collect(tail)
                head match
                    case ConstantType(StringConstant(mname)) =>
                        collect(moduleUnits(mname))
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
