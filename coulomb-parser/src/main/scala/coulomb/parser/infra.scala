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

package coulomb.parser

import scala.language.implicitConversions
import scala.reflect.runtime.universe._

import shapeless._

import coulomb._
import coulomb.infra._
import coulomb.define._

object unitops {
  trait UnitTypeString[U] {
    def expr: String
  }
  object UnitTypeString {
    implicit def evidenceUnitless: UnitTypeString[Unitless] =
      new UnitTypeString[Unitless] { val expr = "coulomb.Unitless" }

    implicit def evidenceBase[U](implicit utt: WeakTypeTag[U], bu: BaseUnit[U]): UnitTypeString[U] =
      new UnitTypeString[U] { val expr = utt.tpe.typeSymbol.fullName }

    implicit def evidenceDerived[U](implicit utt: WeakTypeTag[U], du: DerivedUnit[U, _]): UnitTypeString[U] =
      new UnitTypeString[U] { val expr = utt.tpe.typeSymbol.fullName }

    implicit def evidenceMul[L, R](implicit udfL: UnitTypeString[L], udfR: UnitTypeString[R]): UnitTypeString[%*[L, R]] =
      new UnitTypeString[%*[L, R]] { val expr = s"coulomb.%*[${udfL.expr}, ${udfR.expr}]" }

    implicit def evidenceDiv[L, R](implicit udfL: UnitTypeString[L], udfR: UnitTypeString[R]): UnitTypeString[%/[L, R]] =
      new UnitTypeString[%/[L, R]] { val expr = s"coulomb.%/[${udfL.expr}, ${udfR.expr}]" }

    implicit def evidencePow[B, E](implicit udfB: UnitTypeString[B], e: XIntValue[E]): UnitTypeString[%^[B, E]] =
      new UnitTypeString[%^[B, E]] { val expr = s"coulomb.%^[${udfB.expr}, ${e.value}]" }
  }
}

object infra {
  import coulomb.parser.unitops._

  trait Evidence[T] {
    type Out
  }
  trait EvidenceLowPriority {
    type Aux[T, O] = Evidence[T] { type Out = O }
    implicit def evidenceFalse[T]: Aux[T, false] =
      new Evidence[T] { type Out = false }
  }
  object Evidence extends EvidenceLowPriority {
    implicit def evidenceTrue[T](implicit t: T): Aux[T, true] =
      new Evidence[T] { type Out = true }
  }

  trait NoEvidence[T]
  object NoEvidence {
    // Note: if you try to directly ask for Evidence.Aux[T, false], it will match the
    // low-priority rule above, and this won't work right.
    implicit def evidence0[T, V](implicit no: Evidence.Aux[T, V], vf: V =:= false): NoEvidence[T] =
      new NoEvidence[T] {}
  }

  trait FilterPrefixUnits[S] {
    type Out
  }
  object FilterPrefixUnits {
    type Aux[S, O] = FilterPrefixUnits[S] { type Out = O }
    implicit def evidence0: Aux[HNil, HNil] = new FilterPrefixUnits[HNil] { type Out = HNil }
    implicit def evidence1[U, T <: HList, TF <: HList](implicit
        pfu: DerivedUnit[U, Unitless],
        tf: Aux[T, TF]): Aux[U :: T, U :: TF] = {
      new FilterPrefixUnits[U :: T] { type Out = U :: TF }
    }
    implicit def evidence2[U, T <: HList, TF <: HList](implicit
        npfu: NoEvidence[DerivedUnit[U, Unitless]],
        tf: Aux[T, TF]): Aux[U :: T, TF] = {
      new FilterPrefixUnits[U :: T] { type Out = TF }
    }
  }

  trait FilterNonPrefixUnits[S] {
    type Out
  }
  trait FilterNonPrefixUnitsP1 {
    type Aux[S, O] = FilterNonPrefixUnits[S] { type Out = O }
    implicit def evidence3[U, T <: HList, TF <: HList](implicit
        tf: Aux[T, TF]): Aux[U :: T, TF] = {
      new FilterNonPrefixUnits[U :: T] { type Out = TF }
    }
  }
  object FilterNonPrefixUnits extends FilterNonPrefixUnitsP1 {
    implicit def evidence0: Aux[HNil, HNil] = new FilterNonPrefixUnits[HNil] { type Out = HNil }
    implicit def evidence1[U, T <: HList, TF <: HList](implicit
        pfu: DerivedUnit[U, _],
        npfu: NoEvidence[DerivedUnit[U, Unitless]],
        tf: Aux[T, TF]): Aux[U :: T, U :: TF] = {
      new FilterNonPrefixUnits[U :: T] { type Out = U :: TF }
    }
    implicit def evidence2[U, T <: HList, TF <: HList](implicit
        pfu: BaseUnit[U],
        tf: Aux[T, TF]): Aux[U :: T, U :: TF] = {
      new FilterNonPrefixUnits[U :: T] { type Out = U :: TF }
    }
  }

  case class UnitDefCode[U](name: String, tpeFull: String)
  object UnitDefCode {
    implicit def evidenceBase[U](implicit utt: WeakTypeTag[U], bu: BaseUnit[U]): UnitDefCode[U] = {
      val tpeFull = utt.tpe.typeSymbol.fullName
      UnitDefCode[U](
        bu.name,
        tpeFull.toString)
    }

    implicit def evidenceDerived[U](implicit utt: WeakTypeTag[U], du: DerivedUnit[U, _]): UnitDefCode[U] = {
      val tpeFull = utt.tpe.typeSymbol.fullName
      UnitDefCode[U](
        du.name,
        tpeFull.toString)
    }
  }

  case class UnitDefs[S](codes: List[UnitDefCode[_]])
  object UnitDefs {
    implicit def evidence0: UnitDefs[HNil] = UnitDefs[HNil](Nil)
    implicit def evidence1[U, T <: HList](implicit c: UnitDefCode[U], t: UnitDefs[T]): UnitDefs[U :: T] =
      UnitDefs[U :: T](c :: t.codes)
  }

  case class QPP[S](unames: Seq[String], pfnames: Seq[String], nameToType: Map[String, String])
  object QPP {
    implicit def evidence0[S, SU, SPU](implicit
        units: FilterNonPrefixUnits.Aux[S, SU],
        pfunits: FilterPrefixUnits.Aux[S, SPU],
        udefs: UnitDefs[SU],
        pfdefs: UnitDefs[SPU]): QPP[S] = {
      val alldefs = udefs.codes ++ pfdefs.codes
      val nameToType = Map(alldefs.map { u => (u.name, u.tpeFull) } :_*)
      QPP[S](udefs.codes.map(_.name), pfdefs.codes.map(_.name), nameToType)
    }
  }
}
