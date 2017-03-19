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
