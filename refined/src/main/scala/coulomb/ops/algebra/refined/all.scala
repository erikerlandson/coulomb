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

package coulomb.ops.algebra.refined

import scala.util.{Try, Success, Failure}

import algebra.ring.*

import eu.timepit.refined.*
import eu.timepit.refined.api.*
import eu.timepit.refined.numeric.*

import coulomb.ops.algebra.FractionalPower

object all:
    given ctx_AdditiveSemigroup_Refined_Positive[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, Positive]
    ): AdditiveSemigroup[Refined[V, Positive]] =
        new infra.ASGR[V, Positive]

    given ctx_AdditiveSemigroup_Refined_NonNegative[V](using
        alg: AdditiveSemigroup[V],
        vld: Validate[V, NonNegative]
    ): AdditiveSemigroup[Refined[V, NonNegative]] =
        new infra.ASGR[V, NonNegative]

    given ctx_MultiplicativeGroup_Refined_Positive[V](using
        alg: MultiplicativeGroup[V],
        vld: Validate[V, Positive]
    ): MultiplicativeGroup[Refined[V, Positive]] =
        new infra.MGR[V, Positive]

    given ctx_MultiplicativeMonoid_Refined_Positive[V](using
        alg: MultiplicativeMonoid[V],
        vld: Validate[V, Positive]
    ): MultiplicativeMonoid[Refined[V, Positive]] =
        new infra.MMR[V, Positive]

    given ctx_MultiplicativeMonoid_Refined_NonNegative[V](using
        alg: MultiplicativeMonoid[V],
        vld: Validate[V, NonNegative]
    ): MultiplicativeMonoid[Refined[V, NonNegative]] =
        new infra.MMR[V, NonNegative]

    given ctx_FractionalPower_Refined_Positive[V](using
        alg: FractionalPower[V],
        vld: Validate[V, Positive]
    ): FractionalPower[Refined[V, Positive]] =
        new infra.FPR[V, Positive]

    given ctx_AdditiveSemigroup_Refined_Either[V, P](using
        alg: AdditiveSemigroup[Refined[V, P]]
    ): AdditiveSemigroup[Either[String, Refined[V, P]]] =
        new infra.ASGRE[V, P]

    given ctx_MultiplicativeGroup_Refined_Either[V, P](using
        alg: MultiplicativeGroup[Refined[V, P]]
    ): MultiplicativeGroup[Either[String, Refined[V, P]]] =
        new infra.MGRE[V, P]

    given ctx_MultiplicativeMonoid_Refined_Either[V, P](using
        alg: MultiplicativeMonoid[Refined[V, P]]
    ): MultiplicativeMonoid[Either[String, Refined[V, P]]] =
        new infra.MMRE[V, P]

    object infra:
        class ASGR[V, P](using alg: AdditiveSemigroup[V], vld: Validate[V, P])
            extends AdditiveSemigroup[Refined[V, P]]:
            def plus(x: Refined[V, P], y: Refined[V, P]): Refined[V, P] =
                refineV[P].unsafeFrom(alg.plus(x.value, y.value))

        class MSGR[V, P](using
            alg: MultiplicativeSemigroup[V],
            vld: Validate[V, P]
        ) extends MultiplicativeSemigroup[Refined[V, P]]:
            def times(x: Refined[V, P], y: Refined[V, P]): Refined[V, P] =
                refineV[P].unsafeFrom(alg.times(x.value, y.value))

        class MMR[V, P](using alg: MultiplicativeMonoid[V], vld: Validate[V, P])
            extends MSGR[V, P]
            with MultiplicativeMonoid[Refined[V, P]]:
            def one: Refined[V, P] = refineV[P].unsafeFrom(alg.one)

        class MGR[V, P](using alg: MultiplicativeGroup[V], vld: Validate[V, P])
            extends MMR[V, P]
            with MultiplicativeGroup[Refined[V, P]]:
            def div(x: Refined[V, P], y: Refined[V, P]): Refined[V, P] =
                refineV[P].unsafeFrom(alg.div(x.value, y.value))

        class FPR[V, P](using alg: FractionalPower[V], vld: Validate[V, P])
            extends FractionalPower[Refined[V, P]]:
            def pow(v: Refined[V, P], e: Double): Refined[V, P] =
                refineV[P].unsafeFrom(alg.pow(v.value, e))

        class ASGRE[V, P](using alg: AdditiveSemigroup[Refined[V, P]])
            extends AdditiveSemigroup[Either[String, Refined[V, P]]]:
            def plus(
                x: Either[String, Refined[V, P]],
                y: Either[String, Refined[V, P]]
            ) =
                (x, y) match
                    case (Left(xe), Left(ye)) => Left(s"($xe)($ye)")
                    case (Left(xe), Right(_)) => Left(xe)
                    case (Right(_), Left(ye)) => Left(ye)
                    case (Right(xv), Right(yv)) =>
                        Try(alg.plus(xv, yv)) match
                            case Success(z) => Right(z)
                            case Failure(e) => Left(e.getMessage)

        class MSGRE[V, P](using alg: MultiplicativeSemigroup[Refined[V, P]])
            extends MultiplicativeSemigroup[Either[String, Refined[V, P]]]:
            def times(
                x: Either[String, Refined[V, P]],
                y: Either[String, Refined[V, P]]
            ) =
                (x, y) match
                    case (Left(xe), Left(ye)) => Left(s"($xe)($ye)")
                    case (Left(xe), Right(_)) => Left(xe)
                    case (Right(_), Left(ye)) => Left(ye)
                    case (Right(xv), Right(yv)) =>
                        Try(alg.times(xv, yv)) match
                            case Success(z) => Right(z)
                            case Failure(e) => Left(e.getMessage)

        class MMRE[V, P](using alg: MultiplicativeMonoid[Refined[V, P]])
            extends MSGRE[V, P]
            with MultiplicativeMonoid[Either[String, Refined[V, P]]]:
            def one: Either[String, Refined[V, P]] =
                Try(alg.one) match
                    case Success(z) => Right(z)
                    case Failure(e) => Left(e.getMessage)

        class MGRE[V, P](using alg: MultiplicativeGroup[Refined[V, P]])
            extends MMRE[V, P]
            with MultiplicativeGroup[Either[String, Refined[V, P]]]:
            def div(
                x: Either[String, Refined[V, P]],
                y: Either[String, Refined[V, P]]
            ) =
                (x, y) match
                    case (Left(xe), Left(ye)) => Left(s"($xe)($ye)")
                    case (Left(xe), Right(_)) => Left(xe)
                    case (Right(_), Left(ye)) => Left(ye)
                    case (Right(xv), Right(yv)) =>
                        Try(alg.div(xv, yv)) match
                            case Success(z) => Right(z)
                            case Failure(e) => Left(e.getMessage)

        class FPRE[V, P](using alg: FractionalPower[Refined[V, P]])
            extends FractionalPower[Either[String, Refined[V, P]]]:
            def pow(
                v: Either[String, Refined[V, P]],
                e: Double
            ): Either[String, Refined[V, P]] =
                Try(v.map(alg.pow(_, e))) match
                    case Success(z) => z
                    case Failure(e) => Left(e.getMessage)
