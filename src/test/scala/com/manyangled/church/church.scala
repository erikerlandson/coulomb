package com.manyangled.church

import org.scalatest._

class IntegerSpec extends FlatSpec with Matchers {

  case class Equal[A >: B <: B, B]()

  it should "have expected min and max defined constants" in {
    import Integer._

    Integer.value[_min] should be (-9)
    Integer.value[_max] should be (9)
  }

  it should "implement value[N] method on defined subset of integers" in {
    import Integer._

    Integer.value[_0] should be (0)

    Integer.value[_1] should be (1)
    Integer.value[_2] should be (2)
    Integer.value[_3] should be (3)
    Integer.value[_4] should be (4)
    Integer.value[_5] should be (5)
    Integer.value[_6] should be (6)
    Integer.value[_7] should be (7)
    Integer.value[_8] should be (8)
    Integer.value[_9] should be (9)

    Integer.value[_neg1] should be (-1)
    Integer.value[_neg2] should be (-2)
    Integer.value[_neg3] should be (-3)
    Integer.value[_neg4] should be (-4)
    Integer.value[_neg5] should be (-5)
    Integer.value[_neg6] should be (-6)
    Integer.value[_neg7] should be (-7)
    Integer.value[_neg8] should be (-8)
    Integer.value[_neg9] should be (-9)
  }

  it should "demonstrate strong typing" in {
    import Integer._

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
    import Integer._

    "Equal[_0#Inc, _1]" should compile
    "Equal[_1#Inc, _2]" should compile
    "Equal[_0#Inc#Inc, _2]" should compile
    "Equal[_8#Inc, _9]" should compile
    "Equal[_neg9#Inc, _neg8]" should compile
    "Equal[_neg2#Inc, _neg1]" should compile
  }

  it should "support Dec type operator" in {
    import Integer._

    "Equal[_0#Dec, _neg1]" should compile
    "Equal[_1#Dec, _0]" should compile
    "Equal[_0#Dec#Dec, _neg2]" should compile
    "Equal[_9#Dec, _8]" should compile
    "Equal[_neg8#Dec, _neg9]" should compile
    "Equal[_neg2#Dec, _neg3]" should compile
  }

  it should "support Neg type operator" in {
    import Integer._

    "Equal[_0#Neg, _0]" should compile
    "Equal[_1#Neg, _neg1]" should compile
    "Equal[_2#Neg#Neg, _2]" should compile
    "Equal[_9#Neg, _neg9]" should compile
    "Equal[_neg9#Neg, _9]" should compile
    "Equal[_neg8#Neg, _8]" should compile
    "Equal[_neg2#Neg, _2]" should compile
  }

  it should "support Add type operator" in {
    import Integer._

    "Equal[_0#Add[_1], _1]" should compile
    "Equal[_0#Add[_1]#Add[_1], _2]" should compile
    "Equal[_1#Add[_1], _2]" should compile
    "Equal[_neg1#Add[_2], _1]" should compile
    "Equal[_5#Add[_neg9], _neg4]" should compile
    "Equal[_9#Add[_neg9], _0]" should compile
    "Equal[_9#Add[_neg9]#Add[_neg9], _neg9]" should compile
    "Equal[_neg9#Add[_9]#Add[_9], _9]" should compile
  }
}
