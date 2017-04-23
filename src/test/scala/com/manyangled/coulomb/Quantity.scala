package com.manyangled.coulomb

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

class QuantitySpec extends FlatSpec with Matchers {

  import scala.reflect.runtime.universe._

  import ChurchInt._
  import SIBaseUnits._
  import SIPrefixes._
  import USCustomaryUnits._
  import SIAcceptedUnits._

  import spire.math._
  import Integral._
  import spire.std.byte._
  import spire.std.short._

  val epsilon = 1e-4
  implicit val doubleTolerant = TolerantNumerics.tolerantDoubleEquality(epsilon)
  implicit val floatTolerant = TolerantNumerics.tolerantFloatEquality(epsilon.toFloat)
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

  implicit class WithHasType[T :TypeTag](t: T) {
    val tt = typeOf[T]
    def hasType[U :TypeTag]: Boolean = {
      val ut = typeOf[U]
      if (tt =:= ut) true else {
        println(s"\n$tt =!= $ut\n")
        false
      }
    }
  }

  implicit class WithQTupMethod[N :TypeTag, U <: UnitExpr :TypeTag](q: Quantity[N, U]) {
    def qtup = (q, typeOf[N], typeOf[U])
  }

  def beQ[N :TypeTag, U <: UnitExpr :TypeTag](tval: Double, f: Int = 1)(implicit
      teq: org.scalactic.Equality[N],
      tct: spire.math.ConvertableTo[N]) =
    Matcher { qtuple: (Any, Type, Type) =>
      val (qA, tN, tU) = qtuple
      val (t, msg) = (tN, tU) match {
        case (tn, _) if (!(tn =:= typeOf[N])) =>
          (false, s"Representation type $tn did not match target ${typeOf[N]}")
        case (_, tu) if (!(tu =:= typeOf[U])) =>
          (false, s"Unit type $tu did not match target ${typeOf[U]}")
        case _ => {
          val q = qA.asInstanceOf[Quantity[N, U]]
          val tv: N = if (f == 1) tct.fromDouble(tval) else tct.fromInt((tval * f.toDouble).toInt)
          if (teq.areEqual(q.value, tv)) (true, "") else {
            (false, s"Value ${q.value} did not match target $tv")
          }
        }
      }
      MatchResult(t, msg, "Expected Quantity type and value")
    }

  it should "allocate a Quantity" in {
    val q = new Quantity[Double, Meter](1.0)
    q.qtup should beQ[Double, Meter](1)
  }

  it should "define the Standard International Base Units" in {
    val m = Meter(1D)
    val s = Second(1f)
    val kg = Kilogram(1)
    val a = Ampere(1L)
    val mol = Mole(BigInt(1))
    val c = Candela(BigDecimal(1))
    val k = Kelvin(Rational(1))

    m.qtup should beQ[Double, Meter](1)
    s.qtup should beQ[Float, Second](1)
    kg.qtup should beQ[Int, Kilogram](1)
    a.qtup should beQ[Long, Ampere](1)
    mol.qtup should beQ[BigInt, Mole](1)
    c.qtup should beQ[BigDecimal, Candela](1)
    k.qtup should beQ[Rational, Kelvin](1)
  }

  it should "enforce unit convertability at compile time" in {
    "1D.withUnit[Meter].toUnit[Foot]" should compile
    "1D.withUnit[Meter].toUnit[Second]" shouldNot compile

    "1D.withUnit[Acre %* Foot].toUnit[Mega %* Liter]" should compile
    "1D.withUnit[Acre %* Foot].toUnit[Mega %* Hectare]" shouldNot compile

    "1D.withUnit[Mole %/ Liter].toUnit[(Kilo %* Mole) %/ (Meter %^ _3)]" should compile
    "1D.withUnit[Mole %/ Liter].toUnit[(Kilo %* Mole) %/ (Meter %^ _4)]" shouldNot compile
  }

  it should "implement toUnit over supported numeric types" in {
    val meterToFoot = 3.2808399

    // integral types
    100.toByte.withUnit[Meter].toUnit[Foot].qtup should beQ[Byte, Foot](meterToFoot, 100)
    100.toShort.withUnit[Meter].toUnit[Foot].qtup should beQ[Short, Foot](meterToFoot, 100)
    100.withUnit[Meter].toUnit[Foot].qtup should beQ[Int, Foot](meterToFoot, 100)
    100L.withUnit[Meter].toUnit[Foot].qtup should beQ[Long, Foot](meterToFoot, 100)
    BigInt(100).withUnit[Meter].toUnit[Foot].qtup should beQ[BigInt, Foot](meterToFoot, 100)

    // non-integral types
    1f.withUnit[Meter].toUnit[Foot].qtup should beQ[Float, Foot](meterToFoot)
    1D.withUnit[Meter].toUnit[Foot].qtup should beQ[Double, Foot](meterToFoot)
    BigDecimal(1D).withUnit[Meter].toUnit[Foot].qtup should beQ[BigDecimal, Foot](meterToFoot)
    Rational(1).withUnit[Meter].toUnit[Foot].qtup should beQ[Rational, Foot](meterToFoot)
    Algebraic(1).withUnit[Meter].toUnit[Foot].qtup should beQ[Algebraic, Foot](meterToFoot)
    Real(1).withUnit[Meter].toUnit[Foot].qtup should beQ[Real, Foot](meterToFoot)
    Number(1).withUnit[Meter].toUnit[Foot].qtup should beQ[Number, Foot](meterToFoot)
  }

  it should "implement toRef over supported numeric types" in {
    37.withUnit[Second].toRep[Byte].qtup should beQ[Byte, Second](37.0)
    37.withUnit[Second].toRep[Short].qtup should beQ[Short, Second](37.0)
    37.withUnit[Second].toRep[Int].qtup should beQ[Int, Second](37.0)
    37.withUnit[Second].toRep[Long].qtup should beQ[Long, Second](37.0)
    37.withUnit[Second].toRep[BigInt].qtup should beQ[BigInt, Second](37.0)

    37.withUnit[Second].toRep[Float].qtup should beQ[Float, Second](37.0)
    37.withUnit[Second].toRep[Double].qtup should beQ[Double, Second](37.0)
    37.withUnit[Second].toRep[BigDecimal].qtup should beQ[BigDecimal, Second](37.0)
    37.withUnit[Second].toRep[Rational].qtup should beQ[Rational, Second](37.0)
    37.withUnit[Second].toRep[Algebraic].qtup should beQ[Algebraic, Second](37.0)
    37.withUnit[Second].toRep[Real].qtup should beQ[Real, Second](37.0)
    37.withUnit[Second].toRep[Number].qtup should beQ[Number, Second](37.0)
  }

  it should "implement unary -" in {
    (-Kilogram(42.0)).qtup should beQ[Double, Kilogram](-42.0)
    (-(1.withUnit[Kilogram %/ Mole])).qtup should beQ[Int, Kilogram %/ Mole](-1)
  }
}
