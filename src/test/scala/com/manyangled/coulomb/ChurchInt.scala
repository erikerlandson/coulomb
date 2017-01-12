package com.manyangled.coulomb

import org.scalatest._

class ChurchIntSpec extends FlatSpec with Matchers {

  case class Equal[A >: B <: B, B]()

  it should "have expected min and max defined constants" in {
    import ChurchInt._

    churchToInt[_min] should be (-9)
    churchToInt[_max] should be (9)
  }

  it should "implement churchToInt[N] method on ChurchInt" in {
    import ChurchInt._

    churchToInt[_0] should be (0)

    churchToInt[_1] should be (1)
    churchToInt[_2] should be (2)
    churchToInt[_3] should be (3)
    churchToInt[_4] should be (4)
    churchToInt[_5] should be (5)
    churchToInt[_6] should be (6)
    churchToInt[_7] should be (7)
    churchToInt[_8] should be (8)
    churchToInt[_9] should be (9)

    churchToInt[_neg1] should be (-1)
    churchToInt[_neg2] should be (-2)
    churchToInt[_neg3] should be (-3)
    churchToInt[_neg4] should be (-4)
    churchToInt[_neg5] should be (-5)
    churchToInt[_neg6] should be (-6)
    churchToInt[_neg7] should be (-7)
    churchToInt[_neg8] should be (-8)
    churchToInt[_neg9] should be (-9)
  }

  it should "support churchToInt[N] on arbitrarily large N" in {
    import ChurchInt._

    // Sufficiently large ChurchInt will cause a compiler stack-overflow, which
    // is not too surprising since they operate via recursion
    churchToInt[_9#Mul[_9]] should be (81)
    churchToInt[_9#Mul[_neg9]] should be (-81)
  }

  it should "demonstrate strong typing" in {
    import ChurchInt._

    "Equal[_0, _0]" should compile
    "Equal[_1, _1]" should compile
    "Equal[_neg1, _neg1]" should compile
    "Equal[_min, _min]" should compile
    "Equal[_max, _max]" should compile

    "Equal[_0, _1]" shouldNot typeCheck
    "Equal[_1, _neg1]" shouldNot typeCheck
    "Equal[_1, _2]" shouldNot typeCheck
  }

  it should "support Inc type operator" in {
    import ChurchInt._

    "Equal[_0#Inc, _1]" should compile
    "Equal[_1#Inc, _2]" should compile
    "Equal[_0#Inc#Inc, _2]" should compile
    "Equal[_8#Inc, _9]" should compile
    "Equal[_neg9#Inc, _neg8]" should compile
    "Equal[_neg2#Inc, _neg1]" should compile
  }

  it should "support Dec type operator" in {
    import ChurchInt._

    "Equal[_0#Dec, _neg1]" should compile
    "Equal[_1#Dec, _0]" should compile
    "Equal[_0#Dec#Dec, _neg2]" should compile
    "Equal[_9#Dec, _8]" should compile
    "Equal[_neg8#Dec, _neg9]" should compile
    "Equal[_neg2#Dec, _neg3]" should compile
  }

  it should "support Neg type operator" in {
    import ChurchInt._

    "Equal[_0#Neg, _0]" should compile
    "Equal[_1#Neg, _neg1]" should compile
    "Equal[_2#Neg#Neg, _2]" should compile
    "Equal[_9#Neg, _neg9]" should compile
    "Equal[_neg9#Neg, _9]" should compile
    "Equal[_neg8#Neg, _8]" should compile
    "Equal[_neg2#Neg, _2]" should compile
  }

  it should "support Add type operator" in {
    import ChurchInt._

    "Equal[_0#Add[_1], _1]" should compile
    "Equal[_0#Add[_1]#Add[_1], _2]" should compile
    "Equal[_1#Add[_1], _2]" should compile
    "Equal[_neg1#Add[_2], _1]" should compile
    "Equal[_5#Add[_neg9], _neg4]" should compile
    "Equal[_9#Add[_neg9], _0]" should compile
    "Equal[_9#Add[_neg9]#Add[_neg9], _neg9]" should compile
    "Equal[_neg9#Add[_9]#Add[_9], _9]" should compile
  }

  it should "support Sub type operator" in {
    import ChurchInt._

    "Equal[_0#Sub[_1], _neg1]" should compile
    "Equal[_0#Sub[_1]#Sub[_1], _neg2]" should compile
    "Equal[_1#Sub[_2], _neg1]" should compile
    "Equal[_neg4#Sub[_neg9], _5]" should compile
    "Equal[_9#Sub[_9], _0]" should compile
    "Equal[_9#Sub[_9]#Sub[_9], _neg9]" should compile
  }

  it should "support Mul type operator" in {
    import ChurchInt._
    "Equal[_0#Mul[_1], _0]" should compile
    "Equal[_0#Mul[_2], _0]" should compile
    "Equal[_2#Mul[_0], _0]" should compile
    "Equal[_2#Mul[_2], _4]" should compile
    "Equal[_2#Mul[_neg2], _neg4]" should compile
    "Equal[_neg2#Mul[_2], _neg4]" should compile
    "Equal[_neg2#Mul[_neg2], _4]" should compile
  }

  it should "support composed type operators" in {
    import ChurchInt._

    "Equal[_0#Inc#Dec, _0#Dec#Inc]" should compile
    "Equal[_0#Inc#Neg, _neg2#Add[_3]#Sub[_2]]" should compile
    "Equal[_4#Neg#Inc, _5#Sub[_3]#Neg#Dec]" should compile
    "Equal[_9#Sub[_8]#Inc, _neg9#Neg#Sub[_6]#Dec]" should compile 
  }

  it should "support type system outside of value range" in {
    import ChurchInt._

    "Equal[_max#Inc, _max#Add[_1]]" should compile
    "Equal[_min#Dec, _min#Sub[_1]]" should compile
  }
}
