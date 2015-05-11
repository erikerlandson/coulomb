package com.manyangled.church

import org.scalatest._

class IntegerSpec extends FlatSpec with Matchers {
  it should "implement value[N] method on defined subset of integers" in {
    import Integer._

    Integer.value[_min] should be (-9)
    Integer.value[_max] should be (9)

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
}
