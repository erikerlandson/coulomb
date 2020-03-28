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

import coulomb._

trait SigToUnit[S] {
  type Out
}
object SigToUnit {
  type Aux[S, U] = SigToUnit[S] { type Out = U }
  implicit def evidence0: Aux[HNil, Unitless] = new SigToUnit[HNil] { type Out = Unitless }

  implicit def evidence1[U, ST <: HList, UC](implicit cont: Aux[ST, UC]): Aux[(U, 0) :: ST, UC] =
    new SigToUnit[(U, 0) :: ST] { type Out = UC }

  implicit def evidence2[U, ST <: HList, UC](implicit cont: Aux[ST, UC], nu: UC =:!= Unitless): Aux[(U, 1) :: ST, %*[U, UC]] =
    new SigToUnit[(U, 1) :: ST] { type Out = %*[U, UC] }

  implicit def evidence3[U, P, ST <: HList, UC](implicit ne01: XIntNon01.Aux[P, true], cont: Aux[ST, UC], nu: UC =:!= Unitless): Aux[(U, P) :: ST, %*[%^[U, P], UC]] =
    new SigToUnit[(U, P) :: ST] { type Out = %*[%^[U, P], UC] }

  implicit def evidence4[U, ST <: HList](implicit cont: Aux[ST, Unitless]): Aux[(U, 1) :: ST, U] =
    new SigToUnit[(U, 1) :: ST] { type Out = U }

  implicit def evidence5[U, P, ST <: HList](implicit ne01: XIntNon01.Aux[P, true], cont: Aux[ST, Unitless]): Aux[(U, P) :: ST, %^[U, P]] =
    new SigToUnit[(U, P) :: ST] { type Out = %^[U, P] }
}

trait ResTypeCase[NS, DS] {
  type Out
}
object ResTypeCase {
  type Aux[NS, DS, O] = ResTypeCase[NS, DS] { type Out = O }

  implicit def evidence0: Aux[HNil, HNil, Unitless] =
    new ResTypeCase[HNil, HNil] { type Out = Unitless }

  implicit def evidence1[NS, NU](implicit
      nns: NS =:!= HNil,
      s2u: SigToUnit.Aux[NS, NU]): Aux[NS, HNil, NU] =
    new ResTypeCase[NS, HNil] { type Out = NU }

  implicit def evidence2[DS, TDS, DU](implicit
      nnd: DS =:!= HNil,
      neg: ApplySigPow.Aux[-1, DS, TDS],
      s2u: SigToUnit.Aux[TDS, DU]): Aux[HNil, DS, DU] =
    new ResTypeCase[HNil, DS] { type Out = DU }

  implicit def evidence3[NS, DS, NU, DU](implicit
      nns: NS =:!= HNil,
      nnd: DS =:!= HNil,
      n2u: SigToUnit.Aux[NS, NU],
      d2u: SigToUnit.Aux[DS, DU]): Aux[NS, DS, %/[NU, DU]] =
    new ResTypeCase[NS, DS] { type Out = %/[NU, DU] }
}

trait ResType[S] {
  type Out
}
object ResType {
  type Aux[S, O] = ResType[S] { type Out = O }
  implicit def evidence[S, NS, DS, RT](implicit
      ss: SortUnitSig.Aux[S, NS, DS],
      rtc: ResTypeCase.Aux[NS, DS, RT]): Aux[S, RT] =
    new ResType[S] { type Out = RT }
}

trait MulResultType[LU, RU] {
  type Out
}
object MulResultType {
  type Aux[LU, RU, O] = MulResultType[LU, RU] { type Out = O }

  implicit def result[LU, RU, OL, OR, OU, RT](implicit
    cnL: StandardSig.Aux[LU, OL],
    cnR: StandardSig.Aux[RU, OR],
    mu: UnifySigMul.Aux[OL, OR, OU],
    rt: ResType.Aux[OU, RT]): Aux[LU, RU, RT] =
    new MulResultType[LU, RU] { type Out = RT }
}

trait DivResultType[LU, RU] {
  type Out
}
object DivResultType {
  type Aux[LU, RU, O] = DivResultType[LU, RU] { type Out = O }

  implicit def result[LU, RU, OL, OR, OU, RT](implicit
      cnL: StandardSig.Aux[LU, OL],
      cnR: StandardSig.Aux[RU, OR],
      mu: UnifySigDiv.Aux[OR, OL, OU],
      rt: ResType.Aux[OU, RT]): Aux[LU, RU, RT] =
    new DivResultType[LU, RU] { type Out = RT }
}

trait PowResultType[U, P] {
  type Out
}
object PowResultType {
  type Aux[U, P, O] = PowResultType[U, P] { type Out = O }
  implicit def evidence[U, P, SS, AP, RT](implicit
      ss: StandardSig.Aux[U, SS],
      ap: ApplySigPow.Aux[P, SS, AP],
      rt: ResType.Aux[AP, RT]): Aux[U, P, RT] =
    new PowResultType[U, P] { type Out = RT }
}

object invoke {
  trait Invoke[O]
  def apply[I <: { type Out } ](implicit i: I): Invoke[i.Out] = new Invoke[i.Out] {}
  def ss[I <: { type OutN; type OutD }](implicit i: I): Invoke[(i.OutN, i.OutD)] = new Invoke[(i.OutN, i.OutD)] {}
}
