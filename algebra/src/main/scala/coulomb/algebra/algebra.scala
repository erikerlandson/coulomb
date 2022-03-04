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

given Hash[Rational] with
  def eqv(x: Rational, y: Rational) = x == y
  def hash(x: Rational) = x.hashCode
