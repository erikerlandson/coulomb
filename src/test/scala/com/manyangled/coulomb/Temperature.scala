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
}
