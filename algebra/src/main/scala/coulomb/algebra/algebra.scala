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

package coulomb.algebra

import algebra.ring.*
import cats.kernel.*
import coulomb.ops.standard.*
import coulomb.rational.*

given [A](using A: AdditiveSemigroup[A]): CanAdd[A] = A.plus(_, _)
given [A](using A: AdditiveGroup[A]): CanSub[A] = A.minus(_, _)
given [A](using A: MultiplicativeSemigroup[A]): CanMul[A] = A.times(_, _)
given [A](using A: MultiplicativeGroup[A]): CanDiv[A] = A.div(_, _)

given Field[Rational] with
  def zero = Rational.const0
  def one = Rational.const1
  def plus(x: Rational, y: Rational) = x + y
  override def minus(x: Rational, y: Rational) = x - y
  def times(x: Rational, y: Rational) = x * y
  def div(x: Rational, y: Rational) = x / y
  def negate(x: Rational) = -x
  override def pow(x: Rational, n: Int) = x.pow(n)

given CommutativeGroup[Rational] = summon[Field[Rational]].additive

given Order[Rational] with Hash[Rational] with
  def hash(x: Rational) = x.hashCode
  def compare(x: Rational, y: Rational) =
    if x == y then 0
    else if x > y then 1
    else -1
