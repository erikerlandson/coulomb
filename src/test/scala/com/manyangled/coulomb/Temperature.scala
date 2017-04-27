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
}
