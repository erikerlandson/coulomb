package com.manyangled.coulomb

import scala.language.higherKinds

sealed trait ChurchInt {
  type Inc <: ChurchInt
  type Dec <: ChurchInt
  type Add[K <: ChurchInt] <: ChurchInt
  type Sub[K <: ChurchInt] <: ChurchInt
  type Mul[K <: ChurchInt] <: ChurchInt
  type Neg <: ChurchInt
}

case class ChurchIntValue[N <: ChurchInt](value: Int)

object ChurchInt {
  import infra._

  def value[N <: ChurchInt](implicit iv: ChurchIntValue[N]) = iv.value

  type _0 = Zero

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

  type _min = _neg9
  type _max = _9

  implicit val integerValue_0 = ChurchIntValue[_0](0)

  implicit val integerValue_1 = ChurchIntValue[_1](1)
  implicit val integerValue_2 = ChurchIntValue[_2](2)
  implicit val integerValue_3 = ChurchIntValue[_3](3)
  implicit val integerValue_4 = ChurchIntValue[_4](4)
  implicit val integerValue_5 = ChurchIntValue[_5](5)
  implicit val integerValue_6 = ChurchIntValue[_6](6)
  implicit val integerValue_7 = ChurchIntValue[_7](7)
  implicit val integerValue_8 = ChurchIntValue[_8](8)
  implicit val integerValue_9 = ChurchIntValue[_9](9)

  implicit val integerValue_neg1 = ChurchIntValue[_neg1](-1)
  implicit val integerValue_neg2 = ChurchIntValue[_neg2](-2)
  implicit val integerValue_neg3 = ChurchIntValue[_neg3](-3)
  implicit val integerValue_neg4 = ChurchIntValue[_neg4](-4)
  implicit val integerValue_neg5 = ChurchIntValue[_neg5](-5)
  implicit val integerValue_neg6 = ChurchIntValue[_neg6](-6)
  implicit val integerValue_neg7 = ChurchIntValue[_neg7](-7)
  implicit val integerValue_neg8 = ChurchIntValue[_neg8](-8)
  implicit val integerValue_neg9 = ChurchIntValue[_neg9](-9)

  object infra {
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

    class Zero extends ChurchInt {
      type Inc = IncChurchInt[Zero]
      type Dec = DecChurchInt[Zero]
      type Add[K <: ChurchInt] = K
      type Sub[K <: ChurchInt] = K#Neg
      type Mul[K <: ChurchInt] = Zero
      type Neg = Zero
    }
  }
}

class MacroCommon(val c: scala.reflect.macros.whitebox.Context) {
  import c.universe._

  def abort(msg: String) = c.abort(c.enclosingPosition, msg)

  def typeName(tpe: Type): String = tpe.typeSymbol.fullName

  def evalTree[T](tree: Tree) = c.eval(c.Expr[T](c.untypecheck(tree.duplicate)))

  def superClass(tpe: Type, sup: Type): Option[Type] = {
    val supSym = sup.typeSymbol
    val bc = tpe.baseClasses.drop(1)
    if (bc.count { bSym => bSym == supSym } < 1) None else Some(tpe.baseType(supSym))
  }
}  

class ChurchIntMacros(c0: scala.reflect.macros.whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  
}
