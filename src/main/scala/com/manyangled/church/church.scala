package com.manyangled

object church {
  import scala.language.higherKinds

  import church_detail._

  sealed trait Integer {
    type Inc <: Integer
    type Dec <: Integer
    type Add[K <: Integer] <: Integer
    type Sub[K <: Integer] <: Integer
    type Neg <: Integer

    def value: Int
  }

  def construct[N <: Integer :Constructable] = implicitly[Constructable[N]].construct()

  def value[N <: Integer :Constructable] = construct[N].value

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


  object church_detail {
    trait Constructable[+T] {
      def construct(): T
    }

    object Constructable {
      def apply[T](blk: => T): Constructable[T] = new Constructable[T] {
        def construct(): T = blk
      }
    }

    class IncInteger[N <: Integer :Constructable] extends Integer {
      type Inc = IncInteger[IncInteger[N]]
      type Dec = N
      type Add[K <: Integer] = N#Add[K]#Inc
      type Sub[K <: Integer] = N#Sub[K]#Inc
      type Neg = N#Neg#Dec

      def value = {
        implicitly[Constructable[N]].construct().value + 1
      }
    }

    class DecInteger[N <: Integer :Constructable] extends Integer {
      type Inc = N
      type Dec = DecInteger[DecInteger[N]]
      type Add[K <: Integer] = N#Add[K]#Dec
      type Sub[K <: Integer] = N#Sub[K]#Dec
      type Neg = N#Neg#Inc

      def value = {
        implicitly[Constructable[N]].construct().value - 1
      }
    }

    class Zero extends Integer {
      type Inc = IncInteger[Zero]
      type Dec = DecInteger[Zero]
      type Add[K <: Integer] = K
      type Sub[K <: Integer] = K#Neg
      type Neg = Zero

      def value = 0
    }

    implicit val constructable0: Constructable[_0] = Constructable { new _0 }
    implicit val constructable1: Constructable[_1] = Constructable { new _1 }
    implicit val constructable2: Constructable[_2] = Constructable { new _2 }
    implicit val constructable3: Constructable[_3] = Constructable { new _3 }
    implicit val constructable4: Constructable[_4] = Constructable { new _4 }
    implicit val constructable5: Constructable[_5] = Constructable { new _5 }
    implicit val constructable6: Constructable[_6] = Constructable { new _6 }
    implicit val constructable7: Constructable[_7] = Constructable { new _7 }
    implicit val constructable8: Constructable[_8] = Constructable { new _8 }
    implicit val constructable9: Constructable[_9] = Constructable { new _9 }

    implicit val constructable1n: Constructable[_neg1] = Constructable { new _neg1 }
    implicit val constructable2n: Constructable[_neg2] = Constructable { new _neg2 }
    implicit val constructable3n: Constructable[_neg3] = Constructable { new _neg3 }
    implicit val constructable4n: Constructable[_neg4] = Constructable { new _neg4 }
    implicit val constructable5n: Constructable[_neg5] = Constructable { new _neg5 }
    implicit val constructable6n: Constructable[_neg6] = Constructable { new _neg6 }
    implicit val constructable7n: Constructable[_neg7] = Constructable { new _neg7 }
    implicit val constructable8n: Constructable[_neg8] = Constructable { new _neg8 }
    implicit val constructable9n: Constructable[_neg9] = Constructable { new _neg9 }

  }
}
