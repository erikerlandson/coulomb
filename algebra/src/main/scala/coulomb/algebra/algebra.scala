package coulomb.algebra

import algebra.ring.*
import coulomb.ops.standard.*

given [A](using A: AdditiveSemigroup[A]): CanAdd[A] = A.plus(_, _)
given [A](using A: AdditiveGroup[A]): CanSub[A] = A.minus(_, _)
given [A](using A: MultiplicativeSemigroup[A]): CanMul[A] = A.times(_, _)
given [A](using A: MultiplicativeGroup[A]): CanDiv[A] = A.div(_, _)
