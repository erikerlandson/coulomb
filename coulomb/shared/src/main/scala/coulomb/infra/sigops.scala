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

trait InsertSigMul[K, V, M] {
  type Out
}
object InsertSigMul {
  type Aux[K, V, M, O] = InsertSigMul[K, V, M] { type Out = O }

  implicit def insert0[K, V]: Aux[K, V, HNil, (K, V) :: HNil] =
    new InsertSigMul[K, V, HNil] { type Out = (K, V) :: HNil }

  implicit def insert1[K, V, V0, MT <: HList, P](implicit op: XIntAdd.Aux[V0, V, P], nz: P =:!= 0): Aux[K, V, (K, V0) :: MT, (K, P) :: MT] =
    new InsertSigMul[K, V, (K, V0) :: MT] { type Out = (K, P) :: MT }

  implicit def insert1z[K, V, V0, MT <: HList](implicit op: XIntAdd.Aux[V0, V, 0]): Aux[K, V, (K, V0) :: MT, MT] =
    new InsertSigMul[K, V, (K, V0) :: MT] { type Out = MT }

  implicit def insert2[K, V, K0, V0, MT <: HList, O <: HList](implicit ne: K =:!= K0, rc: Aux[K, V, MT, O]): Aux[K, V, (K0, V0) :: MT, (K0, V0) :: O] =
    new InsertSigMul[K, V, (K0, V0) :: MT] { type Out = (K0, V0) :: O }
}

trait InsertSigDiv[K, V, M] {
  type Out
}
object InsertSigDiv {
  type Aux[K, V, M, O] = InsertSigDiv[K, V, M] { type Out = O }

  implicit def insert0[K, V](implicit n: Negate[V]): Aux[K, V, HNil, (K, n.Out) :: HNil] =
    new InsertSigDiv[K, V, HNil] { type Out = (K, n.Out) :: HNil }

  implicit def insert1[K, V, V0, MT <: HList, P](implicit op: XIntSub.Aux[V0, V, P], nz: P =:!= 0): Aux[K, V, (K, V0) :: MT, (K, P) :: MT] =
    new InsertSigDiv[K, V, (K, V0) :: MT] { type Out = (K, P) :: MT }

  implicit def insert1z[K, V, V0, MT <: HList](implicit op: XIntSub.Aux[V0, V, 0]): Aux[K, V, (K, V0) :: MT, MT] =
    new InsertSigDiv[K, V, (K, V0) :: MT] { type Out = MT }

  implicit def insert2[K, V, K0, V0, MT <: HList, O <: HList](implicit ne: K =:!= K0, rc: Aux[K, V, MT, O]): Aux[K, V, (K0, V0) :: MT, (K0, V0) :: O] =
    new InsertSigDiv[K, V, (K0, V0) :: MT] { type Out = (K0, V0) :: O }
}

trait UnifySigMul[M1, M2] {
  type Out
}
object UnifySigMul {
  type Aux[M1, M2, O] = UnifySigMul[M1, M2] { type Out = O }
  implicit def unify0[M2]: Aux[HNil, M2, M2] =
    new UnifySigMul[HNil, M2] { type Out = M2 }
  implicit def unify1[K, V, MT <: HList, M2, O, O2](implicit ui: InsertSigMul.Aux[K, V, M2, O], rc: Aux[MT, O, O2]): Aux[(K, V) :: MT, M2, O2] =
    new UnifySigMul[(K, V) :: MT, M2] { type Out = O2 }
}

// Note, this is like "M2 / M1" so careful with type argument order
trait UnifySigDiv[M1, M2] {
  type Out
}
object UnifySigDiv {
  type Aux[M1, M2, O] = UnifySigDiv[M1, M2] { type Out = O }
  implicit def unify0[M2]: Aux[HNil, M2, M2] =
    new UnifySigDiv[HNil, M2] { type Out = M2 }
  implicit def unify1[K, V, MT <: HList, M2, O, O2](implicit ui: InsertSigDiv.Aux[K, V, M2, O], rc: Aux[MT, O, O2]): Aux[(K, V) :: MT, M2, O2] =
    new UnifySigDiv[(K, V) :: MT, M2] { type Out = O2 }
}

trait ApplySigPow[P, M] {
  type Out
}
object ApplySigPow {
  type Aux[P, M, O] = ApplySigPow[P, M] { type Out = O }
  implicit def apply0[P](implicit nz: P =:!= 0): Aux[P, HNil, HNil] = new ApplySigPow[P, HNil] { type Out = HNil }
  implicit def apply1[P, K, V, MT <: HList, O <: HList, Q](implicit nz: P =:!= 0, rc: Aux[P, MT, O], op: XIntMul.Aux[V, P, Q]): Aux[P, (K, V) :: MT, (K, Q) :: O] =
    new ApplySigPow[P, (K, V) :: MT] { type Out = (K, Q) :: O }
  implicit def apply1z[M]: Aux[0, M, HNil] = new ApplySigPow[0, M] { type Out = HNil }
}
