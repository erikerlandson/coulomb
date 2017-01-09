package com.manyangled.church

import scala.language.higherKinds

sealed trait Integer {
  type Inc <: Integer
  type Dec <: Integer
  type Add[K <: Integer] <: Integer
  type Sub[K <: Integer] <: Integer
  type Mul[K <: Integer] <: Integer
  type Neg <: Integer
}

case class IntegerValue[N <: Integer](value: Int)

trait NonZero

object Integer {
  import infra._

  def value[N <: Integer](implicit iv: IntegerValue[N]) = iv.value

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

  implicit val integerValue_0 = IntegerValue[_0](0)

  implicit val integerValue_1 = IntegerValue[_1](1)
  implicit val integerValue_2 = IntegerValue[_2](2)
  implicit val integerValue_3 = IntegerValue[_3](3)
  implicit val integerValue_4 = IntegerValue[_4](4)
  implicit val integerValue_5 = IntegerValue[_5](5)
  implicit val integerValue_6 = IntegerValue[_6](6)
  implicit val integerValue_7 = IntegerValue[_7](7)
  implicit val integerValue_8 = IntegerValue[_8](8)
  implicit val integerValue_9 = IntegerValue[_9](9)

  implicit val integerValue_neg1 = IntegerValue[_neg1](-1)
  implicit val integerValue_neg2 = IntegerValue[_neg2](-2)
  implicit val integerValue_neg3 = IntegerValue[_neg3](-3)
  implicit val integerValue_neg4 = IntegerValue[_neg4](-4)
  implicit val integerValue_neg5 = IntegerValue[_neg5](-5)
  implicit val integerValue_neg6 = IntegerValue[_neg6](-6)
  implicit val integerValue_neg7 = IntegerValue[_neg7](-7)
  implicit val integerValue_neg8 = IntegerValue[_neg8](-8)
  implicit val integerValue_neg9 = IntegerValue[_neg9](-9)
}

object infra {
  class IncInteger[N <: Integer] extends Integer with NonZero {
    type Inc = IncInteger[IncInteger[N]]
    type Dec = N
    type Add[K <: Integer] = N#Add[K]#Inc
    type Sub[K <: Integer] = N#Sub[K]#Inc
    type Mul[K <: Integer] = K#Add[N#Mul[K]]
    type Neg = N#Neg#Dec
  }

  class DecInteger[N <: Integer] extends Integer with NonZero {
    type Inc = N
    type Dec = DecInteger[DecInteger[N]]
    type Add[K <: Integer] = N#Add[K]#Dec
    type Sub[K <: Integer] = N#Sub[K]#Dec
    type Mul[K <: Integer] = Neg#Mul[K]#Neg
    type Neg = N#Neg#Inc
  }

  class Zero extends Integer {
    type Inc = IncInteger[Zero]
    type Dec = DecInteger[Zero]
    type Add[K <: Integer] = K
    type Sub[K <: Integer] = K#Neg
    type Mul[K <: Integer] = Zero
    type Neg = Zero
  }
}
