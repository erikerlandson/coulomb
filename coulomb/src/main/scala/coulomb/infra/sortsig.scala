/*
Copyright 2017-2020 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb.infra

import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

// assuming inputs (U, P) are already from some canonical sig; so a type U will never pre-exist in M
trait InsertSortedUnitSig[U, P, M] {
  type Out
}
object InsertSortedUnitSig {
  type Aux[U, P, M, O] = InsertSortedUnitSig[U, P, M] { type Out = O }

  implicit def evidence0[U, P]: Aux[U, P, HNil, (U, P) :: HNil] =
    new InsertSortedUnitSig[U, P, HNil] { type Out = (U, P) :: HNil }

  implicit def evidence1[U, P, U0, P0, MT <: HList](implicit lte: XIntGT.Aux[P, P0, false]): Aux[U, P, (U0, P0) :: MT, (U, P) :: (U0, P0) :: MT] =
    new InsertSortedUnitSig[U, P, (U0, P0) :: MT] { type Out = (U, P) :: (U0, P0) :: MT }

  implicit def evidence2[U, P, U0, P0, MT <: HList, O <: HList](implicit gt: XIntGT.Aux[P, P0, true], rc: Aux[U, P, MT, O]): Aux[U, P, (U0, P0) :: MT, (U0, P0) :: O] =
    new InsertSortedUnitSig[U, P, (U0, P0) :: MT] { type Out = (U0, P0) :: O }

}

trait SortUnitSigCall[M, N, D] {
  type OutN
  type OutD
}

object SortUnitSigCall {
  type Aux[M, N, D, ON, OD] = SortUnitSigCall[M, N, D] { type OutN = ON; type OutD = OD }

  implicit def evidence0[N, D]: Aux[HNil, N, D, N, D] =
    new SortUnitSigCall[HNil, N, D] { type OutN = N; type OutD = D }

  implicit def evidence1[U, P, MT <: HList, N, D, NO, NF, DF](implicit
      pos: XIntGT.Aux[P, 0, true],
      ins: InsertSortedUnitSig.Aux[U, P, N, NO],
      rc: Aux[MT, NO, D, NF, DF]): Aux[(U, P) :: MT, N, D, NF, DF] =
    new SortUnitSigCall[(U, P) :: MT, N, D] { type OutN = NF; type OutD = DF }

  implicit def evidence2[U, P, MT <: HList, N, D, NP, DO, NF, DF](implicit
      neg: XIntLT.Aux[P, 0, true],
      n: XIntNeg.Aux[P, NP],
      ins: InsertSortedUnitSig.Aux[U, NP, D, DO],
      rc: Aux[MT, N, DO, NF, DF]): Aux[(U, P) :: MT, N, D, NF, DF] =
    new SortUnitSigCall[(U, P) :: MT, N, D] { type OutN = NF; type OutD = DF }
}

trait SortUnitSig[S] {
  type OutN
  type OutD
}
object SortUnitSig {
  type Aux[S, ON, OD] = SortUnitSig[S] { type OutN = ON; type OutD = OD }

  implicit def evidence[S, ON, OD](implicit ssc: SortUnitSigCall.Aux[S, HNil, HNil, ON, OD]): Aux[S, ON, OD] =
    new SortUnitSig[S] { type OutN = ON; type OutD = OD }
}
