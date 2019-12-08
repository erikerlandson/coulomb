package org.scalatest

import scala.reflect.runtime.universe._
import scala.language.implicitConversions

import org.scalatest._
import org.scalactic._
import TripleEquals._
import matchers._
import MatchersHelper.indicateFailure
import MatchersHelper.indicateSuccess

import spire.math._

import coulomb._
import coulomb.temp._

object QMatchers {

  val epsilon = 1e-4

  implicit val byteTolerant = new org.scalactic.Equality[Byte] {
    def areEqual(x: Byte, a: Any) = a match {
      case y: Byte => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val shortTolerant = new org.scalactic.Equality[Short] {
    def areEqual(x: Short, a: Any) = a match {
      case y: Short => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val intTolerant = new org.scalactic.Equality[Int] {
    def areEqual(x: Int, a: Any) = a match {
      case y: Int => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val longTolerant = new org.scalactic.Equality[Long] {
    def areEqual(x: Long, a: Any) = a match {
      case y: Long => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val bigIntTolerant = new org.scalactic.Equality[BigInt] {
    def areEqual(x: BigInt, a: Any) = a match {
      case y: BigInt => (x - y).abs <= 1
      case _ => false
    }
  }
  implicit val floatTolerant = new org.scalactic.Equality[Float] {
    def areEqual(x: Float, a: Any) = a match {
      case y: Float => math.abs(x - y) <= epsilon.toFloat
      case _ => false
    }
  }
  implicit val doubleTolerant = new org.scalactic.Equality[Double] {
    def areEqual(x: Double, a: Any) = a match {
      case y: Double => math.abs(x - y) <= epsilon
      case _ => false
    }
  }
  implicit val bigDecimalTolerant = new org.scalactic.Equality[BigDecimal] {
    def areEqual(x: BigDecimal, a: Any) = a match {
      case y: BigDecimal => (x - y).abs <= BigDecimal(epsilon)
      case _ => false
    }
  }
  implicit val rationalTolerant = new org.scalactic.Equality[Rational] {
    def areEqual(x: Rational, a: Any) = a match {
      case y: Rational => (x - y).abs <= Rational(epsilon)
      case _ => false
    }
  }
  implicit val algebraicTolerant = new org.scalactic.Equality[Algebraic] {
    def areEqual(x: Algebraic, a: Any) = a match {
      case y: Algebraic => (x - y).abs <= Algebraic(epsilon)
      case _ => false
    }
  }
  implicit val realTolerant = new org.scalactic.Equality[Real] {
    def areEqual(x: Real, a: Any) = a match {
      // Strange. Real has no <= operator.
      case y: Real => (x - y).abs.toDouble <= epsilon
      case _ => false
    }
  }
  implicit val numberTolerant = new org.scalactic.Equality[Number] {
    def areEqual(x: Number, a: Any) = a match {
      case y: Number => (x - y).abs <= Number(epsilon)
      case _ => false
    }
  }

  implicit class WithQuantityShouldMethods[N, U](q: Quantity[N, U])(implicit
      ttN: TypeTag[N],
      ttU: TypeTag[U],
      numN: Numeric[N],
      prettifier: Prettifier,
      pos: source.Position) {

    def shouldBeQ[NR, UR](tval: Double, f: Int = 1, tolerant: Boolean = true)(implicit
        ttNR: TypeTag[NR],
        ttUR: TypeTag[UR],
        teq: org.scalactic.Equality[NR],
        num: spire.math.Numeric[NR]): Assertion = {
      val (passed, msg) = (typeOf[N], typeOf[U]) match {
        case (tn, _) if (!(tn =:= typeOf[NR])) =>
          (false, s"Numeric type $tn did not match target ${typeOf[NR]}")
        case (_, tu) if (!(tu =:= typeOf[UR])) =>
          (false, s"Unit type $tu did not match target ${typeOf[UR]}")
        case _ => {
          val tv: NR = if (f == 1) num.fromDouble(tval) else num.fromInt((tval * f.toDouble).toInt)
          val eq =
            if (tolerant) teq.areEqual(tv, q.value) else (num.compare(tv, num.fromType[N](q.value)) == 0)
          if (eq) (true, "") else (false, s"Value ${q.value} did not match target $tv")
        }
      }
      if (passed) {
        indicateSuccess(true, s"Quantity test passed", "")
      } else {
        indicateFailure(msg, None, pos)
      }
    }
  }

  implicit class WithTemperatureShouldMethods[N, U](t: Temperature[N, U])(implicit
      ttN: TypeTag[N],
      ttU: TypeTag[U],
      numN: Numeric[N],
      prettifier: Prettifier,
      pos: source.Position) {

    def shouldBeT[NR, UR](tval: Double, tolerant: Boolean = true)(implicit
        ttNR: TypeTag[NR],
        ttUR: TypeTag[UR],
        teq: org.scalactic.Equality[NR],
        num: spire.math.Numeric[NR]): Assertion = {
      val (passed, msg) = (typeOf[N], typeOf[U]) match {
        case (tn, _) if (!(tn =:= typeOf[NR])) =>
          (false, s"Numeric type $tn did not match target ${typeOf[NR]}")
        case (_, tu) if (!(tu =:= typeOf[UR])) =>
          (false, s"Unit type $tu did not match target ${typeOf[UR]}")
        case _ => {
          val tv: NR = num.fromDouble(tval)
          val eq =
            if (tolerant) teq.areEqual(tv, t.value) else (num.compare(tv, num.fromType[N](t.value)) == 0)
          if (eq) (true, "") else (false, s"Value ${t.value} did not match target $tv")
        }
      }
      if (passed) {
        indicateSuccess(true, s"Temperature test passed", "")
      } else {
        indicateFailure(msg, None, pos)
      }
    }
  }
}
