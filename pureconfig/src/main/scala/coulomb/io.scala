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

package coulomb.pureconfig.io

import scala.util.{Try, Success, Failure}

import scala.jdk.CollectionConverters.*

import com.typesafe.config.{ConfigValue, ConfigValueFactory}

import _root_.pureconfig.*
import _root_.pureconfig.error.CannotConvert

import algebra.ring.MultiplicativeSemigroup

import coulomb.{infra => _, *}
import coulomb.syntax.*
import coulomb.rational.Rational
import coulomb.conversion.ValueConversion

import coulomb.parser.RuntimeUnitParser

object testing:
    // probably useful for unit testing, will keep them here for now
    extension [V, U](q: Quantity[V, U])
        inline def toCV(using ConfigWriter[Quantity[V, U]]): ConfigValue =
            ConfigWriter[Quantity[V, U]].to(q)
    extension (conf: ConfigValue)
        inline def toQuantity[V, U](using
            ConfigReader[Quantity[V, U]]
        ): Quantity[V, U] =
            ConfigReader[Quantity[V, U]].from(conf).toSeq.head

object rational:
    import _root_.pureconfig.{*, given}

    extension (v: BigInt)
        def toCV: ConfigValue =
            if (v.isValidInt) ConfigWriter[Int].to(v.toInt)
            else ConfigWriter[BigInt].to(v)

    val reader: ConfigReader[Rational] =
        ConfigReader[BigInt].map(Rational(_, 1)) `orElse`
            ConfigReader[Double].map(Rational(_)) `orElse`
            ConfigReader.forProduct2("n", "d") { (n: BigInt, d: BigInt) =>
                Rational(n, d)
            }

    val writer: ConfigWriter[Rational] =
        ConfigWriter.fromFunction[Rational] { r =>
            if (r.d == 1)
                ConfigValueFactory.fromAnyRef(r.n.toCV)
            else
                ConfigValueFactory.fromAnyRef(
                    Map("n" -> r.n.toCV, "d" -> r.d.toCV).asJava
                )
        }

    given ctx_RationalReader: ConfigReader[Rational] =
        reader

    given ctx_RationalWriter: ConfigWriter[Rational] =
        writer

object ruDSL:
    given ctx_RuntimeUnit_DSL_Reader(using
        parser: RuntimeUnitParser
    ): ConfigReader[RuntimeUnit] =
        ConfigReader.fromCursor[RuntimeUnit] { cur =>
            cur.asString.flatMap { expr =>
                parser.parse(expr) match
                    case Right(u) => Right(u)
                    case Left(e) =>
                        cur.failed(CannotConvert(expr, "RuntimeUnit", e))
            }
        }

    given ctx_RuntimeUnit_DSL_Writer(using
        parser: RuntimeUnitParser
    ): ConfigWriter[RuntimeUnit] =
        ConfigWriter[String].contramap[RuntimeUnit] { u =>
            parser.render(u)
        }

object ruJSON:
    import coulomb.pureconfig.UnitPathMapper

    given ctx_RuntimeUnit_JSON_Reader(using
        rr: ConfigReader[Rational],
        upm: UnitPathMapper
    ): ConfigReader[RuntimeUnit] =
        ConfigReader[Rational].map(RuntimeUnit.UnitConst(_))
            `orElse` ConfigReader[String].emap { id =>
                upm.path(id) match
                    case Right(path) => Right(RuntimeUnit.UnitType(path))
                    case Left(e) =>
                        Left(CannotConvert(s"$id", "RuntimeUnit", e))
            }
            `orElse` ConfigReader
                .forProduct3("lhs", "op", "rhs") {
                    (lhs: RuntimeUnit, op: String, rhs: RuntimeUnit) =>
                        (lhs, op, rhs)
                }
                .emap { (lhs, op, rhs) =>
                    op match
                        case "*" => Right(RuntimeUnit.Mul(lhs, rhs))
                        case "/" => Right(RuntimeUnit.Div(lhs, rhs))
                        case _ =>
                            Left(
                                CannotConvert(
                                    s"${(lhs, op, rhs)}",
                                    "RuntimeUnit",
                                    s"unrecognized operator: '$op'"
                                )
                            )
                }

object quantity:
    given ctx_RuntimeQuantity_Reader[V](using
        ConfigReader[V],
        ConfigReader[RuntimeUnit]
    ): ConfigReader[RuntimeQuantity[V]] =
        ConfigReader.forProduct2("value", "unit") { (v: V, u: RuntimeUnit) =>
            RuntimeQuantity(v, u)
        }

    given ctx_RuntimeQuantity_Writer[V](using
        ConfigWriter[V],
        ConfigWriter[RuntimeUnit]
    ): ConfigWriter[RuntimeQuantity[V]] =
        ConfigWriter.forProduct2("value", "unit") { (q: RuntimeQuantity[V]) =>
            (q.value, q.unit)
        }

    inline given ctx_Quantity_Reader[V, U](using
        crq: ConfigReader[RuntimeQuantity[V]],
        crt: CoefficientRuntime,
        vcr: ValueConversion[Rational, V],
        mul: MultiplicativeSemigroup[V]
    ): ConfigReader[Quantity[V, U]] =
        ConfigReader[RuntimeQuantity[V]].emap { rq =>
            crt.coefficient[V](rq.unit, RuntimeUnit.of[U]) match
                case Right(coef) => Right(mul.times(coef, rq.value).withUnit[U])
                case Left(e)     => Left(CannotConvert(s"$rq", "Quantity", e))
        }

    inline given ctx_Quantity_Writer[V, U](using
        ConfigWriter[RuntimeQuantity[V]]
    ): ConfigWriter[Quantity[V, U]] =
        ConfigWriter[RuntimeQuantity[V]].contramap[Quantity[V, U]] { q =>
            RuntimeQuantity(q.value, RuntimeUnit.of[U])
        }
