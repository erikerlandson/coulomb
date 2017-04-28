package com.manyangled.coulomb

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

import ChurchInt._
import SIBaseUnits._
import SIPrefixes._
import USCustomaryUnits._
import SIAcceptedUnits._

import spire.math._

import matchers._

class TemperatureSpec extends FlatSpec with Matchers {
  it should "allocate a Temperature" in {
    val t = new Temperature[Double, Kelvin](1.0)
    t.ttup should beT[Double, Kelvin](1)
  }

  it should "define common temperature types" in {
    val k = 1D.withTemperature[Kelvin]
    val c = 1.withTemperature[Celsius]
    val f = 1f.withTemperature[Fahrenheit]

    k.ttup should beT[Double, Kelvin](1)
    c.ttup should beTXI[Int, Celsius](1)
    f.ttup should beT[Float, Fahrenheit](1)
  }

  it should "enforce convertability at compile time" in {
    "1D.withTemperature[Kelvin].toUnit[Celsius]" should compile
    "1D.withTemperature[Kelvin].toUnit[Meter]" shouldNot compile

    "1D.withTemperature[Celsius].toUnit[Fahrenheit]" should compile
    "1D.withTemperature[Celsius].toUnit[Second]" shouldNot compile

    "1D.withTemperature[Fahrenheit].toUnit[Kelvin]" should compile
    "1D.withTemperature[Fahrenheit].toUnit[Kilogram]" shouldNot compile
  }

  it should "implement toUnit" in {
    32.withTemperature[Fahrenheit].toUnit[Celsius].ttup should beT[Int, Celsius](0)
    32D.withTemperature[Fahrenheit].toUnit[Celsius].ttup should beT[Double, Celsius](0)

    // identity
    32.withTemperature[Fahrenheit].toUnit[Fahrenheit].ttup should beTXI[Int, Fahrenheit](32)
    32f.withTemperature[Fahrenheit].toUnit[Fahrenheit].ttup should beT[Float, Fahrenheit](32)

    // unit coefficient
    20L.withTemperature[Celsius].toUnit[Kelvin].ttup should beT[Long, Kelvin](293)
    BigDecimal(20).withTemperature[Celsius].toUnit[Kelvin].ttup should beT[BigDecimal, Kelvin](293.15)
  }

  it should "implement toRef" in {
    (37.0).withTemperature[Celsius].toRep[Byte].ttup should beTXI[Byte, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Short].ttup should beTXI[Short, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Int].ttup should beTXI[Int, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Long].ttup should beTXI[Long, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[BigInt].ttup should beTXI[BigInt, Celsius](37)

    (37.0).withTemperature[Celsius].toRep[Float].ttup should beT[Float, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Double].ttup should beT[Double, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[BigDecimal].ttup should beT[BigDecimal, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Rational].ttup should beT[Rational, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Algebraic].ttup should beT[Algebraic, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Real].ttup should beT[Real, Celsius](37)
    (37.0).withTemperature[Celsius].toRep[Number].ttup should beT[Number, Celsius](37)
  }

  it should "implement T + Q => T" in {
    (0.withTemperature[Celsius] + 18.withUnit[Fahrenheit]).ttup should beTXI[Int, Celsius](10)
    (0D.withTemperature[Celsius] + 18D.withUnit[Fahrenheit]).ttup should beT[Double, Celsius](10)

    (0.withTemperature[Fahrenheit] + 10.withUnit[Celsius]).ttup should beTXI[Int, Fahrenheit](18)
    (0D.withTemperature[Fahrenheit] + 10D.withUnit[Celsius]).ttup should beT[Double, Fahrenheit](18)
  }

  it should "implement T - Q => T" in {
    (32.withTemperature[Celsius] - 18.withUnit[Fahrenheit]).ttup should beTXI[Int, Celsius](22)
    (32D.withTemperature[Celsius] - 18D.withUnit[Fahrenheit]).ttup should beT[Double, Celsius](22)

    (32.withTemperature[Fahrenheit] - 10.withUnit[Celsius]).ttup should beTXI[Int, Fahrenheit](14)
    (32D.withTemperature[Fahrenheit] - 10D.withUnit[Celsius]).ttup should beT[Double, Fahrenheit](14)
  }

  it should "implement T - T => Q" in {
    (150.withTemperature[Celsius] - 212.withTemperature[Fahrenheit]).qtup should beQ[Int, Celsius](50)
    (150D.withTemperature[Celsius] - 212D.withTemperature[Fahrenheit]).qtup should beQ[Double, Celsius](50)

    (212.withTemperature[Fahrenheit] - 50.withTemperature[Celsius]).qtup should beQ[Int, Fahrenheit](90)
    (212f.withTemperature[Fahrenheit] - 50f.withTemperature[Celsius]).qtup should beQ[Float, Fahrenheit](90)
  }

  it should "implement <" in {
    (1.withTemperature[Celsius] < 2.withTemperature[Celsius]) should be (true)
    (1.withTemperature[Celsius] < 1.withTemperature[Celsius]) should be (false)
    (1.withTemperature[Celsius] < 0.withTemperature[Celsius]) should be (false)

    (Rational(0).withTemperature[Celsius] < Rational(33).withTemperature[Fahrenheit]) should be (true)
    (Rational(0).withTemperature[Celsius] < Rational(32).withTemperature[Fahrenheit]) should be (false)
    (Rational(0).withTemperature[Celsius] < Rational(31).withTemperature[Fahrenheit]) should be (false)
  }

  it should "implement >" in {
    (1.withTemperature[Celsius] > 2.withTemperature[Celsius]) should be (false)
    (1.withTemperature[Celsius] > 1.withTemperature[Celsius]) should be (false)
    (1.withTemperature[Celsius] > 0.withTemperature[Celsius]) should be (true)

    (Rational(0).withTemperature[Celsius] > Rational(33).withTemperature[Fahrenheit]) should be (false)
    (Rational(0).withTemperature[Celsius] > Rational(32).withTemperature[Fahrenheit]) should be (false)
    (Rational(0).withTemperature[Celsius] > Rational(31).withTemperature[Fahrenheit]) should be (true)
  }

  it should "implement <=" in {
    (1.withTemperature[Celsius] <= 2.withTemperature[Celsius]) should be (true)
    (1.withTemperature[Celsius] <= 1.withTemperature[Celsius]) should be (true)
    (1.withTemperature[Celsius] <= 0.withTemperature[Celsius]) should be (false)

    (Rational(0).withTemperature[Celsius] <= Rational(33).withTemperature[Fahrenheit]) should be (true)
    (Rational(0).withTemperature[Celsius] <= Rational(32).withTemperature[Fahrenheit]) should be (true)
    (Rational(0).withTemperature[Celsius] <= Rational(31).withTemperature[Fahrenheit]) should be (false)
  }

  it should "implement >=" in {
    (1.withTemperature[Celsius] >= 2.withTemperature[Celsius]) should be (false)
    (1.withTemperature[Celsius] >= 1.withTemperature[Celsius]) should be (true)
    (1.withTemperature[Celsius] >= 0.withTemperature[Celsius]) should be (true)

    (Rational(0).withTemperature[Celsius] >= Rational(33).withTemperature[Fahrenheit]) should be (false)
    (Rational(0).withTemperature[Celsius] >= Rational(32).withTemperature[Fahrenheit]) should be (true)
    (Rational(0).withTemperature[Celsius] >= Rational(31).withTemperature[Fahrenheit]) should be (true)
  }

  it should "implement ===" in {
    (1.withTemperature[Celsius] === 2.withTemperature[Celsius]) should be (false)
    (1.withTemperature[Celsius] === 1.withTemperature[Celsius]) should be (true)
    (1.withTemperature[Celsius] === 0.withTemperature[Celsius]) should be (false)

    (Rational(0).withTemperature[Celsius] === Rational(33).withTemperature[Fahrenheit]) should be (false)
    (Rational(0).withTemperature[Celsius] === Rational(32).withTemperature[Fahrenheit]) should be (true)
    (Rational(0).withTemperature[Celsius] === Rational(31).withTemperature[Fahrenheit]) should be (false)
  }

  it should "implement =!=" in {
    (1.withTemperature[Celsius] =!= 2.withTemperature[Celsius]) should be (true)
    (1.withTemperature[Celsius] =!= 1.withTemperature[Celsius]) should be (false)
    (1.withTemperature[Celsius] =!= 0.withTemperature[Celsius]) should be (true)

    (Rational(0).withTemperature[Celsius] =!= Rational(33).withTemperature[Fahrenheit]) should be (true)
    (Rational(0).withTemperature[Celsius] =!= Rational(32).withTemperature[Fahrenheit]) should be (false)
    (Rational(0).withTemperature[Celsius] =!= Rational(31).withTemperature[Fahrenheit]) should be (true)
  }
}
