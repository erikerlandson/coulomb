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

package coulomb.runtime.conversion.runtimes.mapping

import scala.collection.immutable.HashMap

import coulomb.runtime.*
import coulomb.runtime.infra.meta
import coulomb.rational.Rational

class MappingCoefficientRuntime(
    baseUnits: Set[RuntimeUnit.UnitType],
    derivedUnits: Map[RuntimeUnit.UnitType, RuntimeUnit]
) extends CoefficientRuntime:
    def coefficientRational(
        uf: RuntimeUnit,
        ut: RuntimeUnit
    ): Either[String, Rational] =
        Left("No.")

case class Canonical(coef: Rational, sig: Map[RuntimeUnit.UnitType, Rational]):
    def *(that: Canonical): Canonical =
        val Canonical(rcoef, rsig) = that
        val s = Canonical
            .merge(sig, rsig)(_ + _)
            .filter { case (_, e) => e != Rational.const0 }
        Canonical(coef * rcoef, s)

    def /(that: Canonical): Canonical =
        val Canonical(rcoef, rsig) = that
        val rneg = rsig.map { case (u, e) => (u, -e) }
        val s = Canonical
            .merge(sig, rneg)(_ + _)
            .filter { case (_, e) => e != Rational.const0 }
        Canonical(coef / rcoef, s)

    def pow(e: Rational): Canonical =
        if (e == Rational.const0) Canonical.one
        else if (e == Rational.const1) this
        else
            val s = sig.map { case (u, ue) => (u, ue * e) }
            Canonical(coef.pow(e), s)

object Canonical:
    def merge[K, V](m1: Map[K, V], m2: Map[K, V])(f: (V, V) => V): Map[K, V] =
        val ki = m1.keySet & m2.keySet
        val r1 = m1.filter { case (k, _) => !ki.contains(k) }
        val r2 = m2.filter { case (k, _) => !ki.contains(k) }
        val ri = ki.map { k => (k, f(m1(k), m2(k))) }
        r1.concat(r2).concat(ri)

    private val s1 = HashMap.empty[RuntimeUnit.UnitType, Rational]
    private val r1 = Rational.const1

    val one: Canonical = Canonical(r1, s1)

    def apply(u: RuntimeUnit): Canonical =
        u match
            case RuntimeUnit.Mul(l, r)    => Canonical(l) * Canonical(r)
            case RuntimeUnit.Div(n, d)    => Canonical(n) / Canonical(d)
            case RuntimeUnit.Pow(b, e)    => Canonical(b).pow(e)
            case RuntimeUnit.UnitConst(c) => Canonical(c, s1)
            case u: RuntimeUnit.UnitType  => Canonical(r1, HashMap(u -> r1))
