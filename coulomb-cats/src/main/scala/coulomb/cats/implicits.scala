package coulomb.cats

import cats._
import cats.syntax.all._
import coulomb._
import scala.annotation.tailrec

trait CatsImplicits {
  /**
    * Eq[Quantity[V, U]] when there is an Eq[V]
    *
    * @group typeclass
    */
  implicit def eqQuantity[V: Eq, U]: Eq[Quantity[V, U]] = Eq.by(_.value)

  /**
    * Order[Quantity[V, U]] when there is an Order[V]
    *
    * @group typeclass
    */
  implicit def orderQuantity[V: Order, U]: Order[Quantity[V, U]] = Order.by(_.value)

  /**
    * CommutativeMonad[Qunatity[*, U]] and Traversable[Quantity[*, U]] allows to map, flatMap and fold on the value field
    *
    * @group typeclass
    */
  implicit def traverseQuantity[U]: CommutativeMonad[Quantity[*, U]] with Traverse[Quantity[*, U]] =
    new CommutativeMonad[Quantity[*, U]] with Traverse[Quantity[*, U]] {

    def pure[A](x: A): Quantity[A, U] = x.withUnit[U]

    def flatMap[A, B](fa: Quantity[A,U])(f: A => Quantity[B,U]): Quantity[B,U] =
      f(fa.value)

    @tailrec
    def tailRecM[A, B](a: A)(f: A => Quantity[Either[A, B],U]): Quantity[B,U] =
      f(a).value match {
        case Right(a) => a.withUnit[U]
        case Left(a) => tailRecM(a)(f)
      }

    def traverse[F[_], A, B](fa: Quantity[A, U])(f: A => F[B])(implicit F: Applicative[F]): F[Quantity[B, U]] =
      F.map(f(fa.value))(_.withUnit[U])

    def foldLeft[B, C](fa: Quantity[B, U], c: C)(f: (C, B) => C): C =
      f(c, fa.value)

    def foldRight[B, C](fa: Quantity[B, U], lc: Eval[C])(f: (B, Eval[C]) => Eval[C]): Eval[C] =
      f(fa.value, lc)
  }

  /**
    * Monoid[Quantity[V, U]] to combine Quantity values
    *
    * @group typeclass
    */
  implicit def monoidQuantity[V: Monoid, U]: Monoid[Quantity[V, U]] =
    Monoid.instance(Monoid[V].empty.withUnit[U], (a, b) => (a.value |+| b.value).withUnit[U])

}

object implicits extends CatsImplicits
