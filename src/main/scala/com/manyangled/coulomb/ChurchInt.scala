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

package com.manyangled.coulomb

import scala.language.higherKinds
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

sealed trait ChurchInt {
  type Inc <: ChurchInt
  type Dec <: ChurchInt
  type Add[K <: ChurchInt] <: ChurchInt
  type Sub[K <: ChurchInt] <: ChurchInt
  type Mul[K <: ChurchInt] <: ChurchInt
  type Neg <: ChurchInt
}

object ChurchInt {
  import infraChurchInt._

  def churchToInt[N <: ChurchInt](implicit iv: ChurchIntValue[N]) = iv.value

  type _0 = ZeroChurchInt

  type _1 = _0#Inc
  type _2 = _1#Inc
  type _3 = _2#Inc
  type _4 = _3#Inc
  type _5 = _4#Inc
  type _6 = _5#Inc
  type _7 = _6#Inc
  type _8 = _7#Inc
  type _9 = _8#Inc

  type _neg1 = _0#Dec
  type _neg2 = _neg1#Dec
  type _neg3 = _neg2#Dec
  type _neg4 = _neg3#Dec
  type _neg5 = _neg4#Dec
  type _neg6 = _neg5#Dec
  type _neg7 = _neg6#Dec
  type _neg8 = _neg7#Dec
  type _neg9 = _neg8#Dec
}

object infraChurchInt {
  class IncChurchInt[N <: ChurchInt] extends ChurchInt {
    type Inc = IncChurchInt[IncChurchInt[N]]
    type Dec = N
    type Add[K <: ChurchInt] = N#Add[K]#Inc
    type Sub[K <: ChurchInt] = N#Sub[K]#Inc
    type Mul[K <: ChurchInt] = K#Add[N#Mul[K]]
    type Neg = N#Neg#Dec
  }

  class DecChurchInt[N <: ChurchInt] extends ChurchInt {
    type Inc = N
    type Dec = DecChurchInt[DecChurchInt[N]]
    type Add[K <: ChurchInt] = N#Add[K]#Dec
    type Sub[K <: ChurchInt] = N#Sub[K]#Dec
    type Mul[K <: ChurchInt] = Neg#Mul[K]#Neg
    type Neg = N#Neg#Inc
  }

  class ZeroChurchInt extends ChurchInt {
    type Inc = IncChurchInt[ZeroChurchInt]
    type Dec = DecChurchInt[ZeroChurchInt]
    type Add[K <: ChurchInt] = K
    type Sub[K <: ChurchInt] = K#Neg
    type Mul[K <: ChurchInt] = ZeroChurchInt
    type Neg = ZeroChurchInt
  }
}

case class ChurchIntValue[N <: ChurchInt](value: Int)

object ChurchIntValue {
  implicit def witnessChurchIntValue[N <: ChurchInt]: ChurchIntValue[N] =
    macro ChurchIntMacros.churchIntValue[N]
}

private [coulomb] class MacroCommon(val c: whitebox.Context) {
  import c.universe._

  import infraChurchInt._

  val zeroType = typeOf[ZeroChurchInt]
  val incType = typeOf[IncChurchInt[ZeroChurchInt]].typeConstructor
  val decType = typeOf[DecChurchInt[ZeroChurchInt]].typeConstructor

  def abort(msg: String) = c.abort(c.enclosingPosition, msg)

  def typeName(tpe: Type): String = tpe.typeSymbol.fullName

  def evalTree[T](tree: Tree) = c.eval(c.Expr[T](c.untypecheck(tree.duplicate)))

  def superClass(tpe: Type, sup: Type): Option[Type] = {
    val supSym = sup.typeSymbol
    val bc = tpe.baseClasses.drop(1)
    if (bc.count { bSym => bSym == supSym } < 1) None else Some(tpe.baseType(supSym))
  }

  // translates types like 'this.U' into the "actual" type
  def fixType(tpe: Type) = tpe.baseType(tpe.baseClasses.head)

  def churchType(i: Int): Type = {
    i match {
      case v if (v > 0) => appliedType(incType, List(churchType(v - 1)))
      case v if (v < 0) => appliedType(decType, List(churchType(v + 1)))
      case _ => zeroType
    }
  }
}  

private [coulomb] class ChurchIntMacros(c0: whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  object IsZero {
    def unapply(tpe: Type): Boolean = (tpe =:= zeroType)
  }

  object IsInc {
    def unapply(tpe: Type): Option[Type] =
      if (tpe.typeConstructor =:= incType) Option(tpe.typeArgs(0)) else None
  }

  object IsDec {
    def unapply(tpe: Type): Option[Type] =
      if (tpe.typeConstructor =:= decType) Option(tpe.typeArgs(0)) else None
  }

  def computeValue(typeN: Type): Int = {
    typeN.dealias match {
      case IsZero() => 0
      case IsInc(ta) => computeValue(ta) + 1
      case IsDec(ta) => computeValue(ta) - 1
      case _ => {
        abort(s"UNIMPLEMENTED: type ${typeName(typeN)}")
        0
      }
    }
  }

  def churchIntValue[N: WeakTypeTag]: Tree = {
    val tpeN = weakTypeOf[N].dealias

    val n = computeValue(tpeN)
    val nq = q"$n"
    q"""
      _root_.com.manyangled.coulomb.ChurchIntValue[$tpeN]($nq)
    """
  }
}
