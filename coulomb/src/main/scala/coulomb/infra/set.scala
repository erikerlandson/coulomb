/*
Copyright 2017 Erik Erlandson

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

private [coulomb] trait IsMember[E, L] {
  type Out
}
private [coulomb] object IsMember {
  type Aux[E, L, O] = IsMember[E, L] { type Out = O }
  implicit def ismember0[E]: Aux[E, HNil, false] = new IsMember[E, HNil] { type Out = false }
  implicit def ismember1[E, T <: HList]: Aux[E, E :: T, true] = new IsMember[E, E :: T] { type Out = true }
  implicit def ismember2[E, E0, T <: HList, O](implicit ne: E =:!= E0, r: Aux[E, T, O]): Aux[E, E0 :: T, O] = {
    new IsMember[E, E0 :: T] { type Out = O }
  }
}

private [coulomb] trait Subset[S1, S2] {
  type Out
}
private [coulomb] object Subset {
  type Aux[S1, S2, O] = Subset[S1, S2] { type Out = O }
  implicit def subset0[S]: Aux[HNil, S, true] = new Subset[HNil, S] { type Out = true }
  implicit def subset1[E, T <: HList, S2](implicit m: IsMember.Aux[E, S2, false]): Aux[E :: T, S2, false] =
    new Subset[E :: T, S2] { type Out = false }
  implicit def subset2[E, T <: HList, S2, O](implicit m: IsMember.Aux[E, S2, true], s: Aux[T, S2, O]): Aux[E :: T, S2, O] =
    new Subset[E :: T, S2] { type Out = O }
}

private [coulomb] trait SetEqual[S1, S2] {
  type Out
}
private [coulomb] object SetEqual {
  type Aux[S1, S2, O] = SetEqual[S1, S2] { type Out = O }
  implicit def equal0[S1, S2, O1, O2](implicit s1: Subset.Aux[S1, S2, O1], s2: Subset.Aux[S2, S1, O2], a: &&[O1, O2]): Aux[S1, S2, a.Out] =
    new SetEqual[S1, S2] { type Out = a.Out }
}
