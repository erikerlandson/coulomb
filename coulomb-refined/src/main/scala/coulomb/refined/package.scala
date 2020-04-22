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

package coulomb

import eu.timepit.refined._
import eu.timepit.refined.api._
import eu.timepit.refined.numeric._
import eu.timepit.refined.boolean.{ And, Not }

import shapeless.{ =:!= }

import coulomb.unitops._
import coulomb.infra.NoImplicit

package refined.infra {
  import singleton.ops._
  import shapeless.Nat
  import shapeless.nat.{ _0, _1 }
  import coulomb.refined.policy._
  import coulomb.refined.CoulombRefinedException

  // in theory refined lib could support this if it resolves:
  // https://github.com/fthomas/refined/issues/755
  // see also:
  // https://github.com/fthomas/refined/pull/758
  trait Implies[P1, P2]
  trait ImpliesP3 {
    type ==>[P1, P2] = Implies[P1, P2]

    implicit def impliesID[P]: P ==> P =
      new Implies[P, P] {}
  }
  trait ImpliesP2 extends ImpliesP3 {
    implicit def impliesPGE[P, R](implicit
        p2ge: P ==> Greater[R]): P ==> Not[Less[R]] =
      new Implies[P, Not[Less[R]]] {}

    implicit def impliesPLE[P, R](implicit
        p2le: P ==> Less[R]): P ==> Not[Greater[R]] =
      new Implies[P, Not[Greater[R]]] {}
  }
  trait ImpliesP1 extends ImpliesP2 {
    implicit def impliesGTGT[L, R](implicit
        q: OpAuxBoolean[L >= R, true]): Greater[L] ==> Greater[R] =
      new Implies[Greater[L], Greater[R]] {}

    implicit def impliesGEGT[L, R](implicit
        q: OpAuxBoolean[L > R, true]): Not[Less[L]] ==> Greater[R] =
      new Implies[Not[Less[L]], Greater[R]] {}

    implicit def impliesLTLT[L, R](implicit
        q: OpAuxBoolean[L <= R, true]): Less[L] ==> Less[R] =
      new Implies[Less[L], Less[R]] {}

    implicit def impliesLELT[L, R](implicit
        q: OpAuxBoolean[L < R, true]): Not[Greater[L]] ==> Less[R] =
      new Implies[Not[Greater[L]], Less[R]] {}
  }
  object Implies extends ImpliesP1 {
    // Nat is the "universal recipient" for numeric predicates, as near as
    // I can figure out from playing with refined
    implicit def impliesGTGTNat[L, R <: Nat, LD <: XDouble, RD <: XDouble](implicit
        ld: OpAuxDouble[ToDouble[L], LD],
        rd: OpAuxDouble[ToDouble[R], RD],
        q: OpAuxBoolean[LD >= RD, true]): Greater[L] ==> Greater[R] =
      new Implies[Greater[L], Greater[R]] {}

    implicit def impliesGEGTNat[L, R <: Nat, LD <: XDouble, RD <: XDouble](implicit
        ld: OpAuxDouble[ToDouble[L], LD],
        rd: OpAuxDouble[ToDouble[R], RD],
        q: OpAuxBoolean[LD > RD, true]): Not[Less[L]] ==> Greater[R] =
      new Implies[Not[Less[L]], Greater[R]] {}

    // not handled by P==>P since L may not be Nat
    implicit def impliesGEGENat[L, R <: Nat, LD <: XDouble, RD <: XDouble](implicit
        ld: OpAuxDouble[ToDouble[L], LD],
        rd: OpAuxDouble[ToDouble[R], RD],
        q: OpAuxBoolean[LD >= RD, true]): Not[Less[L]] ==> Not[Less[R]] =
      new Implies[Not[Less[L]], Not[Less[R]]] {}

    implicit def impliesLTLTNat[L, R <: Nat, LD <: XDouble, RD <: XDouble](implicit
        ld: OpAuxDouble[ToDouble[L], LD],
        rd: OpAuxDouble[ToDouble[R], RD],
        q: OpAuxBoolean[LD <= RD, true]): Less[L] ==> Less[R] =
      new Implies[Less[L], Less[R]] {}

    implicit def impliesLELTNat[L, R <: Nat, LD <: XDouble, RD <: XDouble](implicit
        ld: OpAuxDouble[ToDouble[L], LD],
        rd: OpAuxDouble[ToDouble[R], RD],
        q: OpAuxBoolean[LD < RD, true]): Not[Greater[L]] ==> Less[R] =
      new Implies[Not[Greater[L]], Less[R]] {}

    // not handled by P==>P since L may not be Nat
    implicit def impliesLELENat[L, R <: Nat, LD <: XDouble, RD <: XDouble](implicit
        ld: OpAuxDouble[ToDouble[L], LD],
        rd: OpAuxDouble[ToDouble[R], RD],
        q: OpAuxBoolean[LD <= RD, true]): Not[Greater[L]] ==> Not[Greater[R]] =
      new Implies[Not[Greater[L]], Not[Greater[R]]] {}
  }
  import Implies.==>

  trait NoUpperBound[P]
  object NoUpperBound {
    implicit def noUpperBoundGT[P <: Greater[_]]: NoUpperBound[P] =
      new NoUpperBound[P] {}
    implicit def noUpperBoundGE[P <: Not[_ <: Less[_]]]: NoUpperBound[P] =
      new NoUpperBound[P] {}
  }

  trait NoLowerBound[P]
  object NoLowerBound {
    implicit def noLowerBoundGT[P <: Less[_]]: NoLowerBound[P] =
      new NoLowerBound[P] {}
    implicit def noLowerBoundGE[P <: Not[_ <: Greater[_]]]: NoLowerBound[P] =
      new NoLowerBound[P] {}
  }

  object enhance {
    implicit class EnhanceWithApplyPred[V](v: V) {
      @inline def applyPred[P](implicit vvp: Validate[V, P]) = refineV[P](v) match {
        case Left(err) => throw CoulombRefinedException(err)
        case Right(ref) => ref
      }
    }
  }
  import enhance._

  object soundness {
    trait AddSoundnessPolicy[P1, P2]
    trait AddSoundnessPolicyP1 {
      implicit def unsoundAdd[P1, P2](implicit
          enable: EnableUnsoundRefinedConversions): AddSoundnessPolicy[P1, P2] =
        new AddSoundnessPolicy[P1, P2] {}
    }
    object AddSoundnessPolicy extends AddSoundnessPolicyP1 {
      implicit def soundAddGE[P1, P2](implicit
          nub1: NoUpperBound[P1],
          gez2: P2 ==> NonNegative): AddSoundnessPolicy[P1, P2] =
        new AddSoundnessPolicy[P1, P2] {}

      implicit def soundAddLE[P1, P2](implicit
          nlb1: NoLowerBound[P1],
          lez2: P2 ==> NonPositive): AddSoundnessPolicy[P1, P2] =
        new AddSoundnessPolicy[P1, P2] {}
    }

    trait SubSoundnessPolicy[P1, P2]
    trait SubSoundnessPolicyP1 {
      implicit def unsoundSub[P1, P2](implicit
          enable: EnableUnsoundRefinedConversions): SubSoundnessPolicy[P1, P2] =
        new SubSoundnessPolicy[P1, P2] {}
    }
    object SubSoundnessPolicy extends SubSoundnessPolicyP1 {
      implicit def soundSubGE[P1, P2](implicit
          nub1: NoUpperBound[P1],
          lez2: P2 ==> NonPositive): SubSoundnessPolicy[P1, P2] =
        new SubSoundnessPolicy[P1, P2] {}

      implicit def soundSubLE[P1, P2](implicit
          nlb1: NoLowerBound[P1],
          gez2: P2 ==> NonNegative): SubSoundnessPolicy[P1, P2] =
        new SubSoundnessPolicy[P1, P2] {}
    }

    trait MulSoundnessPolicy[P1, P2]
    trait MulSoundnessPolicyP2 {
      implicit def unsoundMul[P1, P2](implicit
          enable: EnableUnsoundRefinedConversions): MulSoundnessPolicy[P1, P2] =
        new MulSoundnessPolicy[P1, P2] {}
    }
    trait MulSoundnessPolicyP1 extends MulSoundnessPolicyP2 {
      implicit def soundMulGTX[P1, P2](implicit
          nub1: NoUpperBound[P1],
          gez1: P1 ==> NonNegative,
          gt12: P2 ==> Not[Less[_1]]): MulSoundnessPolicy[P1, P2] =
        new MulSoundnessPolicy[P1, P2] {}
      
      implicit def soundMulLTX[P1, P2](implicit
          nlb1: NoLowerBound[P1],
          lez1: P1 ==> NonPositive,
          gt12: P2 ==> Not[Less[_1]]): MulSoundnessPolicy[P1, P2] =
        new MulSoundnessPolicy[P1, P2] {}
    }
    object MulSoundnessPolicy extends MulSoundnessPolicyP1 {
      implicit def soundMulGTZ[P2](implicit
          pos: P2 ==> Positive): MulSoundnessPolicy[Positive, P2] =
        new MulSoundnessPolicy[Positive, P2] {}

      implicit def soundMulGEZ[P2](implicit
          pos: P2 ==> NonNegative): MulSoundnessPolicy[NonNegative, P2] =
        new MulSoundnessPolicy[NonNegative, P2] {}

      implicit def soundMulLTZ[P2](implicit
          pos: P2 ==> Positive): MulSoundnessPolicy[Negative, P2] =
        new MulSoundnessPolicy[Negative, P2] {}

      implicit def soundMulLEZ[P2](implicit
          pos: P2 ==> NonNegative): MulSoundnessPolicy[NonPositive, P2] =
        new MulSoundnessPolicy[NonPositive, P2] {}
    }

    trait DivSoundnessPolicy[P1, P2]
    trait DivSoundnessPolicyP1 {
      implicit def unsoundDiv[P1, P2](implicit
          enable: EnableUnsoundRefinedConversions): DivSoundnessPolicy[P1, P2] =
        new DivSoundnessPolicy[P1, P2] {}
    }
    object DivSoundnessPolicy extends DivSoundnessPolicyP1 {
      implicit def soundDivGTZ[P2](implicit
          pos: P2 ==> Positive): DivSoundnessPolicy[Positive, P2] =
        new DivSoundnessPolicy[Positive, P2] {}

      implicit def soundDivGEZ[P2](implicit
          pos: P2 ==> Positive): DivSoundnessPolicy[NonNegative, P2] =
        new DivSoundnessPolicy[NonNegative, P2] {}

      implicit def soundDivLTZ[P2](implicit
          pos: P2 ==> Positive): DivSoundnessPolicy[Negative, P2] =
        new DivSoundnessPolicy[Negative, P2] {}

      implicit def soundDivLEZ[P2](implicit
          pos: P2 ==> Positive): DivSoundnessPolicy[NonPositive, P2] =
        new DivSoundnessPolicy[NonPositive, P2] {}
    }

    trait PowSoundnessPolicy[P, E]
    trait PowSoundnessPolicyP2 {
      implicit def unsoundPow[P, E](implicit
          enable: EnableUnsoundRefinedConversions): PowSoundnessPolicy[P, E] =
        new PowSoundnessPolicy[P, E] {}
    }
    trait PowSoundnessPolicyP1 extends PowSoundnessPolicyP2 {
      implicit def soundPowIncreasing[P, E](implicit
          nub1: NoUpperBound[P],
          gez1: P ==> Not[Less[_1]],          
          epos: OpAuxBoolean[E >= 1, true]): PowSoundnessPolicy[P, E] =
        new PowSoundnessPolicy[P, E] {}
    }
    object PowSoundnessPolicy extends PowSoundnessPolicyP1 {
      implicit def soundPowGEZ[E](implicit
          epos: OpAuxBoolean[E > 0, true]): PowSoundnessPolicy[NonNegative, E] =
        new PowSoundnessPolicy[NonNegative, E] {}

      implicit def soundPowGTZ[E]: PowSoundnessPolicy[Positive, E] =
        new PowSoundnessPolicy[Positive, E] {}
    }

    trait NegSoundnessPolicy[P]
    trait NegSoundnessPolicyP1 {
      implicit def unsoundNeg[P](implicit
          enable: EnableUnsoundRefinedConversions): NegSoundnessPolicy[P] =
        new NegSoundnessPolicy[P] {}
    }
    object NegSoundnessPolicy extends NegSoundnessPolicyP1 {
      // currently placeholder - intervals symmetric about zero would
      // be sound under negation, if/when I work on support for refined intervals
    }
  }

  trait CoulombRefinedP2 {
    implicit def addRefinedRHS[V1, U1, V2, P2, U2](implicit
        add: UnitAdd[V1, U1, V2, U2]): UnitAdd[V1, U1, Refined[V2, P2], U2] =
      new UnitAdd[V1, U1, Refined[V2, P2], U2] {
        def vadd(v1: V1, v2: Refined[V2, P2]): V1 = {
          add.vadd(v1, v2.value)
        }
      }

    implicit def addRefinedLHS[V1, P1, U1, V2, U2](implicit
        enable: EnableUnsoundRefinedConversions,
        vv1: Validate[V1, P1],
        add: UnitAdd[V1, U1, V2, U2]): UnitAdd[Refined[V1, P1], U1, V2, U2] =
      new UnitAdd[Refined[V1, P1], U1, V2, U2] {
        def vadd(v1: Refined[V1, P1], v2: V2): Refined[V1, P1] = {
          add.vadd(v1.value, v2).applyPred[P1]
        }
      }

    implicit def subRefinedRHS[V1, U1, V2, P2, U2](implicit
        sub: UnitSub[V1, U1, V2, U2]): UnitSub[V1, U1, Refined[V2, P2], U2] =
      new UnitSub[V1, U1, Refined[V2, P2], U2] {
        def vsub(v1: V1, v2: Refined[V2, P2]): V1 = {
          sub.vsub(v1, v2.value)
        }
      }

    implicit def subRefinedLHS[V1, P1, U1, V2, U2](implicit
        enable: EnableUnsoundRefinedConversions,
        vv1: Validate[V1, P1],
        sub: UnitSub[V1, U1, V2, U2]): UnitSub[Refined[V1, P1], U1, V2, U2] =
      new UnitSub[Refined[V1, P1], U1, V2, U2] {
        def vsub(v1: Refined[V1, P1], v2: V2): Refined[V1, P1] = {
          sub.vsub(v1.value, v2).applyPred[P1]
        }
      }

    implicit def mulRefinedRHS[V1, U1, V2, P2, U2, ORT](implicit
        mul: UnitMul.Aux[V1, U1, V2, U2, ORT]): UnitMul.Aux[V1, U1, Refined[V2, P2], U2, ORT] =
      new UnitMul[V1, U1, Refined[V2, P2], U2] {
        type RT = ORT
        def vmul(v1: V1, v2: Refined[V2, P2]): V1 = {
          mul.vmul(v1, v2.value)
        }
      }

    implicit def mulRefinedLHS[V1, P1, U1, V2, U2, ORT](implicit
        enable: EnableUnsoundRefinedConversions,
        vv1: Validate[V1, P1],
        mul: UnitMul.Aux[V1, U1, V2, U2, ORT]): UnitMul.Aux[Refined[V1, P1], U1, V2, U2, ORT] =
      new UnitMul[Refined[V1, P1], U1, V2, U2] {
        type RT = ORT
        def vmul(v1: Refined[V1, P1], v2: V2): Refined[V1, P1] = {
          mul.vmul(v1.value, v2).applyPred[P1]
        }
      }

    implicit def divRefinedRHS[V1, U1, V2, P2, U2, ORT](implicit
        div: UnitDiv.Aux[V1, U1, V2, U2, ORT]): UnitDiv.Aux[V1, U1, Refined[V2, P2], U2, ORT] =
      new UnitDiv[V1, U1, Refined[V2, P2], U2] {
        type RT = ORT
        def vdiv(v1: V1, v2: Refined[V2, P2]): V1 = {
          div.vdiv(v1, v2.value)
        }
      }

    implicit def divRefinedLHS[V1, P1, U1, V2, U2, ORT](implicit
        enable: EnableUnsoundRefinedConversions,
        vv1: Validate[V1, P1],
        div: UnitDiv.Aux[V1, U1, V2, U2, ORT]): UnitDiv.Aux[Refined[V1, P1], U1, V2, U2, ORT] =
      new UnitDiv[Refined[V1, P1], U1, V2, U2] {
        type RT = ORT
        def vdiv(v1: Refined[V1, P1], v2: V2): Refined[V1, P1] = {
          div.vdiv(v1.value, v2).applyPred[P1]
        }
      }

    implicit def ordRefinedRHS[V1, U1, V2, P2, U2](implicit
        ord: UnitOrd[V1, U1, V2, U2]): UnitOrd[V1, U1, Refined[V2, P2], U2] =
      new UnitOrd[V1, U1, Refined[V2, P2], U2] {
        def vcmp(v1: V1, v2: Refined[V2, P2]): Int = {
          ord.vcmp(v1, v2.value)
        }
      }

    implicit def ordRefinedLHS[V1, P1, U1, V2, U2](implicit
        ord: UnitOrd[V1, U1, V2, U2]): UnitOrd[Refined[V1, P1], U1, V2, U2] =
      new UnitOrd[Refined[V1, P1], U1, V2, U2] {
        def vcmp(v1: Refined[V1, P1], v2: V2): Int = {
          ord.vcmp(v1.value, v2)
        }
      }

    implicit def valueToRefined[V1, U1, V2, P2, U2](implicit
        enable: EnableUnsoundRefinedConversions,
        vv2: Validate[V2, P2],
        cnv: UnitConverter[V1, U1, V2, U2]): UnitConverter[V1, U1, Refined[V2, P2], U2] =
      new UnitConverter[V1, U1, Refined[V2, P2], U2] {
        def vcnv(v: V1): Refined[V2, P2] = {
          cnv.vcnv(v).applyPred[P2]
        }
      }

    implicit def refinedToValue[V1, P1, U1, V2, U2](implicit
        cnv: UnitConverter[V1, U1, V2, U2]): UnitConverter[Refined[V1, P1], U1, V2, U2] =
      new UnitConverter[Refined[V1, P1], U1, V2, U2] {
        def vcnv(v: Refined[V1, P1]): V2 = {
          cnv.vcnv(v.value)
        }
      }
  }

  trait CoulombRefinedP1 extends CoulombRefinedP2 {
    implicit def unsoundRefToRef[V1, P1, U1, V2, P2, U2](implicit
        enable: EnableUnsoundRefinedConversions,
        vv2: Validate[V2, P2],
        cnv: UnitConverter[V1, U1, V2, U2]): UnitConverter[Refined[V1, P1], U1, Refined[V2, P2], U2] =
      new UnitConverter[Refined[V1, P1], U1, Refined[V2, P2], U2] {
        def vcnv(v: Refined[V1, P1]): Refined[V2, P2] = {
          cnv.vcnv(v.value).applyPred[P2]
        }
      }  
  }
}

package object refined extends coulomb.refined.infra.CoulombRefinedP1 {
  import coulomb.refined.infra.Implies.==>
  import coulomb.refined.infra.enhance._
  import coulomb.refined.infra.soundness._
  import coulomb.unitops._

  object policy {
    trait EnableUnsoundRefinedConversions
    object unsoundRefinedConversions {
      implicit object enableUnsoundRefinedConversions extends
          EnableUnsoundRefinedConversions {}
    }
  }
  import policy._

  case class CoulombRefinedException(msg: String) extends Exception(msg)

  implicit class EnhanceWithToRefined[V, U](q: Quantity[V, U]) {
    import coulomb._
    @inline def toRefined[P](implicit vv: Validate[V, P]): Quantity[Refined[V, P], U] =
      refineV[P](q.value) match {
        case Left(err) => throw CoulombRefinedException(err)
        case Right(ref) => ref.withUnit[U]
      }
  }

  implicit class EnhanceWithRefinedUnit[V](value: V) {
    import coulomb._
    @inline def withRefinedUnit[P, U](implicit vv: Validate[V, P]): Quantity[Refined[V, P], U] =
      refineV[P](value) match {
        case Left(err) => throw CoulombRefinedException(err)
        case Right(ref) => ref.withUnit[U]
      }
  }

  implicit def addRefinedSound[V1, P1, U1, V2, P2, U2](implicit
      sp: AddSoundnessPolicy[P1, P2],
      vv1: Validate[V1, P1],
      add: UnitAdd[V1, U1, V2, U2]): UnitAdd[Refined[V1, P1], U1, Refined[V2, P2], U2] =
    new UnitAdd[Refined[V1, P1], U1, Refined[V2, P2], U2] {
      def vadd(v1: Refined[V1, P1], v2: Refined[V2, P2]): Refined[V1, P1] = {
        add.vadd(v1.value, v2.value).applyPred[P1]
      }
    }

  implicit def subRefinedSound[V1, P1, U1, V2, P2, U2](implicit
      sp: SubSoundnessPolicy[P1, P2],
      vv1: Validate[V1, P1],
      sub: UnitSub[V1, U1, V2, U2]): UnitSub[Refined[V1, P1], U1, Refined[V2, P2], U2] =
    new UnitSub[Refined[V1, P1], U1, Refined[V2, P2], U2] {
      def vsub(v1: Refined[V1, P1], v2: Refined[V2, P2]): Refined[V1, P1] = {
        sub.vsub(v1.value, v2.value).applyPred[P1]
      }
    }

  implicit def mulRefinedSound[V1, P1, U1, V2, P2, U2, ORT](implicit
      sp: MulSoundnessPolicy[P1, P2],
      vv1: Validate[V1, P1],
      mul: UnitMul.Aux[V1, U1, V2, U2, ORT]): UnitMul.Aux[Refined[V1, P1], U1, Refined[V2, P2], U2, ORT] =
    new UnitMul[Refined[V1, P1], U1, Refined[V2, P2], U2] {
      type RT = ORT
      def vmul(v1: Refined[V1, P1], v2: Refined[V2, P2]): Refined[V1, P1] = {
        mul.vmul(v1.value, v2.value).applyPred[P1]
      }
    }

  implicit def divRefinedSound[V1, P1, U1, V2, P2, U2, ORT](implicit
      sp: DivSoundnessPolicy[P1, P2],
      vv1: Validate[V1, P1],
      div: UnitDiv.Aux[V1, U1, V2, U2, ORT]): UnitDiv.Aux[Refined[V1, P1], U1, Refined[V2, P2], U2, ORT] =
    new UnitDiv[Refined[V1, P1], U1, Refined[V2, P2], U2] {
      type RT = ORT
      def vdiv(v1: Refined[V1, P1], v2: Refined[V2, P2]): Refined[V1, P1] = {
        div.vdiv(v1.value, v2.value).applyPred[P1]
      }
    }

  implicit def powRefinedSound[V, P, U, E, ORT](implicit
      sp: PowSoundnessPolicy[P, E],
      vv: Validate[V, P],
      pow: UnitPow.Aux[V, U, E, ORT]): UnitPow.Aux[Refined[V, P], U, E, ORT] =
    new UnitPow[Refined[V, P], U, E] {
      type RT = ORT
      def vpow(v: Refined[V, P]): Refined[V, P] = {
        pow.vpow(v.value).applyPred[P]
      }
    }

  implicit def negRefinedSound[V, P](implicit
      sp: NegSoundnessPolicy[P],
      vv: Validate[V, P],
      neg: UnitNeg[V]): UnitNeg[Refined[V, P]] =
    new UnitNeg[Refined[V, P]] {
      def vneg(v: Refined[V, P]): Refined[V, P] = {
        neg.vneg(v.value).applyPred[P]
      }
    }

  implicit def ordRefRef[V1, P1, U1, V2, P2, U2](implicit
      ord: UnitOrd[V1, U1, V2, U2]): UnitOrd[Refined[V1, P1], U1, Refined[V2, P2], U2] =
    new UnitOrd[Refined[V1, P1], U1, Refined[V2, P2], U2] {
      def vcmp(v1: Refined[V1, P1], v2: Refined[V2, P2]): Int = {
        ord.vcmp(v1.value, v2.value)
      }
    }

  implicit def soundRefToRefNon[V1, P1, U1, V2, U2](implicit
      du: U1 =:!= U2,
      s1: P1 ==> NonNegative,
      vv2: Validate[V2, NonNegative],
      cnv: UnitConverter[V1, U1, V2, U2]): UnitConverter[Refined[V1, P1], U1, Refined[V2, NonNegative], U2] =
    new UnitConverter[Refined[V1, P1], U1, Refined[V2, NonNegative], U2] {
      def vcnv(v: Refined[V1, P1]): Refined[V2, NonNegative] = {
        cnv.vcnv(v.value).applyPred[NonNegative]
      }
    }

  implicit def soundRefToRefPos[V1, P1, U1, V2, U2](implicit
      du: U1 =:!= U2,
      s1: P1 ==> Positive,
      vv2: Validate[V2, Positive],
      cnv: UnitConverter[V1, U1, V2, U2]): UnitConverter[Refined[V1, P1], U1, Refined[V2, Positive], U2] =
    new UnitConverter[Refined[V1, P1], U1, Refined[V2, Positive], U2] {
      def vcnv(v: Refined[V1, P1]): Refined[V2, Positive] = {
        cnv.vcnv(v.value).applyPred[Positive]
      }
    }

  implicit def soundRefToRefNoUnit[V1, P1, V2, P2, U](implicit
      s12: P1 ==> P2,
      vv2: Validate[V2, P2],
      cnv: UnitConverter[V1, U, V2, U]): UnitConverter[Refined[V1, P1], U, Refined[V2, P2], U] =
    new UnitConverter[Refined[V1, P1], U, Refined[V2, P2], U] {
      def vcnv(v: Refined[V1, P1]): Refined[V2, P2] = {
        cnv.vcnv(v.value).applyPred[P2]
      }
    }
}
