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

import algebra.ring.MultiplicativeSemigroup

import coulomb.{infra => _, *}
import coulomb.syntax.*
import coulomb.rational.Rational
import coulomb.conversion.ValueConversion

object pureconfig:
    import scala.util.{Try, Success, Failure}

    import _root_.pureconfig.error.CannotConvert

    import coulomb.parser.RuntimeUnitParser
    
    import com.typesafe.config.ConfigValue

    // probably useful for unit testing, will keep them here for now
    extension [V, U](q: Quantity[V, U])
        inline def toCV(using ConfigWriter[Quantity[V, U]]): ConfigValue =
            ConfigWriter[Quantity[V, U]].to(q)
    extension (conf: ConfigValue)
        inline def toQuantity[V, U](using ConfigReader[Quantity[V, U]]): Quantity[V, U] =
            ConfigReader[Quantity[V, U]].from(conf).toSeq.head

    object intlit:
        def unapply(lit: String): Option[BigInt] =
            Try { BigInt(lit) }.toOption

    object fplit:
        def unapply(lit: String): Option[Double] =
            Try { lit.toDouble }.toOption

    object ratlit:
        def unapply(lit: String): Option[Rational] =
            Try {
                val x = lit.filter(_ != ' ').split('/')
                assert(x.length == 2)
                Rational(BigInt(x(0)), BigInt(x(1)))
            }.toOption

    given ctx_Rational_Reader: ConfigReader[Rational] =
        ConfigReader.fromCursor[Rational] { cur =>
            cur.asString.flatMap { lit =>
                lit match
                    case intlit(v) => Right(Rational(v, 1))
                    case fplit(v) => Right(Rational(v))
                    case ratlit(v) => Right(v)
                    case _ => cur.failed(CannotConvert(lit, "Rational", s"bad rational literal"))
            }
        }

    given ctx_RuntimeUnit_Reader(using
        parser: RuntimeUnitParser
    ): ConfigReader[RuntimeUnit] =
        ConfigReader.fromCursor[RuntimeUnit] { cur =>
            cur.asString.flatMap { expr =>
                parser.parse(expr) match
                    case Right(u) => Right(u)
                    case Left(e) => cur.failed(CannotConvert(expr, "RuntimeUnit", e))
            }
        }

    given ctx_RuntimeUnit_Writer(using
        parser: RuntimeUnitParser
    ): ConfigWriter[RuntimeUnit] =
        ConfigWriter[String].contramap[RuntimeUnit] { u =>
            parser.render(u)
        }

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
                case Left(e) => Left(CannotConvert(s"$rq", "Quantity", e))
        }

    inline given ctx_Quantity_Writer[V, U](using
        ConfigWriter[RuntimeQuantity[V]]
    ): ConfigWriter[Quantity[V, U]] =
      ConfigWriter[RuntimeQuantity[V]].contramap[Quantity[V, U]] { q =>
          RuntimeQuantity(q.value, RuntimeUnit.of[U])
      }
