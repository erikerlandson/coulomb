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

import spire.math._

import coulomb._
import coulomb.define._

trait CanonicalSig[U] {
  type Out
  def coef: Rational
}

object CanonicalSig {
  type Aux[U, O] = CanonicalSig[U] { type Out = O }

  implicit def evidenceUnitless: Aux[Unitless, HNil] = {
    new CanonicalSig[Unitless] {
      type Out = HNil
      val coef = Rational(1)
    }
  }

  implicit def evidenceBaseUnit[U](implicit buU: BaseUnit[U]): Aux[U, (U, 1) :: HNil] = {
    new CanonicalSig[U] {
      type Out = (U, 1) :: HNil
      val coef = Rational(1)
    }
  }

  implicit def evidenceDerivedUnit[U, D, DC](implicit du: DerivedUnit[U, D], dm: Aux[D, DC]): Aux[U, DC] = {
    new CanonicalSig[U] {
      type Out = DC
      val coef = du.coef * dm.coef
    }
  }

  implicit def evidenceMul[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] = {
    new CanonicalSig[%*[L, R]] {
      type Out = OC
      val coef = l.coef * r.coef
    }
  }

  implicit def evidenceDiv[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] = {
    new CanonicalSig[%/[L, R]] {
      type Out = OC
      val coef = l.coef / r.coef
    }
  }

  implicit def evidencePow[B, BC, E, OC](implicit b: Aux[B, BC], u: ApplySigPow.Aux[E, BC, OC], e: XIntValue[E]): Aux[%^[B, E], OC] = {
    new CanonicalSig[%^[B, E]] {
      type Out = OC
      val coef = b.coef.pow(e.value)
    }
  }
}

trait StandardSig[U] {
  type Out
}
object StandardSig {
  type Aux[U, O] = StandardSig[U] { type Out = O }

  implicit def evidenceUnitless: Aux[Unitless, HNil] =
    new StandardSig[Unitless] { type Out = HNil }

  implicit def evidenceBaseUnit[U](implicit buU: BaseUnit[U]): Aux[U, (U, 1) :: HNil] =
    new StandardSig[U] { type Out = (U, 1) :: HNil }

  implicit def evidenceDerivedUnit[U](implicit du: DerivedUnit[U, _]): Aux[U, (U, 1) :: HNil] =
    new StandardSig[U] { type Out = (U, 1) :: HNil }

  implicit def evidenceMul[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] =
    new StandardSig[%*[L, R]] { type Out = OC }

  implicit def evidenceDiv[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] =
    new StandardSig[%/[L, R]] { type Out = OC }

  implicit def evidencePow[B, BC, E, OC](implicit b: Aux[B, BC], u: ApplySigPow.Aux[E, BC, OC], e: singleton.ops.Id[E]): Aux[%^[B, E], OC] =
    new StandardSig[%^[B, E]] { type Out = OC }
}
