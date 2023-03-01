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

package coulomb

import _root_.pureconfig.*

import coulomb.{infra => _, *}
import coulomb.rational.Rational

object pureconfig:
    val stub = 0

object pureconfig_save:
    // it would be nice to handle polymorphic inputs
    // https://github.com/pureconfig/pureconfig/issues/1472
    given rationalReader: ConfigReader[Rational] =
        ConfigReader.forProduct2("n", "d") { (n: BigInt, d: BigInt) =>
            Rational(n, d)
        }

    given rationalWriter: ConfigWriter[Rational] =
        ConfigWriter.forProduct2("n", "d") { (r: Rational) =>
            (r.n, r.d)
        }

    given runtimeUnitReader: ConfigReader[RuntimeUnit] =
        ConfigReader.fromCursor { xcur =>
            for {
                cur <- xcur.asObjectCursor
                typecur <- cur.atKey("type")
                typestr <- typecur.asString
                u <- infra.readByType(typestr, cur)
            } yield u
        }

    given runtimeUnitWriter: ConfigWriter[RuntimeUnit] =
         ConfigWriter.fromFunction { (x: RuntimeUnit) =>
             x match
                 case u: RuntimeUnit.UnitConst => infra.constWriter.to(u)
                 case u: RuntimeUnit.UnitType => infra.typeWriter.to(u)
                 case u: RuntimeUnit.Mul => infra.mulWriter.to(u)
                 case u: RuntimeUnit.Div => infra.divWriter.to(u)
                 case u: RuntimeUnit.Pow => infra.powWriter.to(u)
         }

    object infra:
        import _root_.pureconfig.error.CannotConvert

        def readByType(typ: String, cur: ConfigObjectCursor): ConfigReader.Result[RuntimeUnit] =
            typ match
                case "const" => constReader.from(cur)
                case "type" => typeReader.from(cur)
                case "mul" => mulReader.from(cur)
                case "div" => divReader.from(cur)
                case "pow" => powReader.from(cur)
                case t =>
                    cur.failed(CannotConvert(cur.objValue.toString, "RuntimeUnit", s"unknown type $t"))

        val constReader = ConfigReader.forProduct1("value")(RuntimeUnit.UnitConst(_))
        val typeReader = ConfigReader.forProduct1("path")(RuntimeUnit.UnitType(_))
        val mulReader = ConfigReader.forProduct2("lhs", "rhs")(RuntimeUnit.Mul(_, _))
        val divReader = ConfigReader.forProduct2("num", "den")(RuntimeUnit.Div(_, _))
        val powReader = ConfigReader.forProduct2("b", "e")(RuntimeUnit.Pow(_, _))

        val constWriter = ConfigWriter.forProduct2("type", "value") {
            (u: RuntimeUnit.UnitConst) => ("const", u.value)
        }
        val typeWriter = ConfigWriter.forProduct2("type", "path") {
            (u: RuntimeUnit.UnitType) => ("type", u.path)
        }
        val mulWriter = ConfigWriter.forProduct3("type", "lhs", "rhs") {
            (u: RuntimeUnit.Mul) => ("mul", u.lhs, u.rhs)
        }
        val divWriter = ConfigWriter.forProduct3("type", "num", "den") {
            (u: RuntimeUnit.Div) => ("div", u.num, u.den)
        }
        val powWriter = ConfigWriter.forProduct3("type", "b", "e") {
            (u: RuntimeUnit.Pow) => ("pow", u.b, u.e)
        }

