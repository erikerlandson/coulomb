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
package testkit

import coulomb.rational.*
import coulomb.syntax.*
import org.scalacheck.*

given Arbitrary[Rational] = Arbitrary(
    for
        n <- Arbitrary.arbitrary[BigInt]
        d <- Arbitrary.arbitrary[BigInt].suchThat(_ != 0)
    yield Rational(n, d)
)

given Cogen[Rational] =
    summon[Cogen[(BigInt, BigInt)]].contramap(r => (r.n, r.d))

given [V, U](using arbV: Arbitrary[V]): Arbitrary[Quantity[V, U]] =
    Arbitrary(arbV.arbitrary.map(_.withUnit[U]))

given [V, U, B](using arbV: Arbitrary[V]): Arbitrary[DeltaQuantity[V, U, B]] =
    Arbitrary(arbV.arbitrary.map(_.withDeltaUnit[U, B]))

given [V, U](using cogenV: Cogen[V]): Cogen[Quantity[V, U]] =
    cogenV.contramap(_.value)

given [V, U, B](using cogenV: Cogen[V]): Cogen[DeltaQuantity[V, U, B]] =
    cogenV.contramap(_.value)
