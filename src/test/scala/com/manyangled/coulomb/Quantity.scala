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

  def beQXI[N :TypeTag, U <: UnitExpr :TypeTag](tval: Int)(implicit
      tcf: spire.math.ConvertableFrom[N]) =
    Matcher { qtuple: (Any, Type, Type) =>
      val (qA, tN, tU) = qtuple
      val (t, msg) = (tN, tU) match {
        case (tn, _) if (!(tn =:= typeOf[N])) =>
          (false, s"Representation type $tn did not match target ${typeOf[N]}")
        case (_, tu) if (!(tu =:= typeOf[U])) =>
          (false, s"Unit type $tu did not match target ${typeOf[U]}")
        case (tn, _) if (
          !(tn =:= typeOf[Byte]) &&
          !(tn =:= typeOf[Short]) &&
          !(tn =:= typeOf[Int]) &&
          !(tn =:= typeOf[Long]) &&
          !(tn =:= typeOf[BigInt])) => (false, s"unrecognized integral type $tn")
        case _ => {
          val q = qA.asInstanceOf[Quantity[N, U]]
          if (tcf.toInt(q.value) == tval) (true, "") else {
            (false, s"Value ${q.value} did not match target $tval")
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
    kg.qtup should beQXI[Int, Kilogram](1)
    a.qtup should beQ[Long, Ampere](1)
    mol.qtup should beQXI[BigInt, Mole](1)
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

  it should "implement toUnit optimization cases" in {
    // numerator only conversion
    Yard(2).toUnit[Foot].qtup should beQXI[Int, Foot](6)

    // identity
    Meter(2).toUnit[Meter].qtup should beQXI[Int, Meter](2)
    Meter(2.0).toUnit[Meter].qtup should beQ[Double, Meter](2)
  }

  it should "implement toRef over supported numeric types" in {
    37.withUnit[Second].toRep[Byte].qtup should beQXI[Byte, Second](37)
    37.withUnit[Second].toRep[Short].qtup should beQXI[Short, Second](37)
    37.withUnit[Second].toRep[Int].qtup should beQXI[Int, Second](37)
    37.withUnit[Second].toRep[Long].qtup should beQXI[Long, Second](37)
    37.withUnit[Second].toRep[BigInt].qtup should beQXI[BigInt, Second](37)

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
    (-(1.withUnit[Kilogram %/ Mole])).qtup should beQXI[Int, Kilogram %/ Mole](-1)
  }

  it should "implement +" in {
    val literToCup = 4.22675283773 // Rational(2000000000 / 473176473)

    // Full rational numerator multiplication overflows smaller integers.
    // todo: investigate heuristics on smaller rationals
    // see: https://github.com/erikerlandson/coulomb/issues/15
    (Cup(100L) + Liter(100L)).qtup should beQ[Long, Cup](1.0 + literToCup, 100)
    (Cup(BigInt(100)) + Liter(BigInt(100))).qtup should beQ[BigInt, Cup](1.0 + literToCup, 100)

    (Cup(1f) + Liter(1f)).qtup should beQ[Float, Cup](1.0 + literToCup)
    (Cup(1D) + Liter(1D)).qtup should beQ[Double, Cup](1.0 + literToCup)
    (Cup(BigDecimal(1)) + Liter(BigDecimal(1))).qtup should beQ[BigDecimal, Cup](1.0 + literToCup)
    (Cup(Rational(1)) + Liter(Rational(1))).qtup should beQ[Rational, Cup](1.0 + literToCup)
    (Cup(Algebraic(1)) + Liter(Algebraic(1))).qtup should beQ[Algebraic, Cup](1.0 + literToCup)
    (Cup(Real(1)) + Liter(Real(1))).qtup should beQ[Real, Cup](1.0 + literToCup)
    (Cup(Number(1)) + Liter(Number(1))).qtup should beQ[Number, Cup](1.0 + literToCup)
  }

  it should "implement + optimization cases" in {
    // numerator only conversion
    (Cup(1) + Quart(1)).qtup should beQXI[Int, Cup](5)

    // identity
    (Cup(1) + Cup(1)).qtup should beQXI[Int, Cup](2)
    (Cup(1.0) + Cup(1.0)).qtup should beQ[Double, Cup](2.0)
  }

  it should "implement -" in {
    val inchToCentimeter = 2.54 // Rational(127 / 50)

    (Centimeter(100.toByte) - Inch(10.toByte)).qtup should beQ[Byte, Centimeter](10.0 - inchToCentimeter, 10)
    (Centimeter(1000.toShort) - Inch(100.toShort)).qtup should beQ[Short, Centimeter](
      10.0 - inchToCentimeter, 100)
    (Centimeter(1000) - Inch(100)).qtup should beQ[Int, Centimeter](10.0 - inchToCentimeter, 100)
    (Centimeter(1000L) - Inch(100L)).qtup should beQ[Long, Centimeter](10.0 - inchToCentimeter, 100)
    (Centimeter(BigInt(1000)) - Inch(BigInt(100))).qtup should beQ[BigInt, Centimeter](
      10.0 - inchToCentimeter, 100)

    (Centimeter(10f) - Inch(1f)).qtup should beQ[Float, Centimeter](10.0 - inchToCentimeter)
    (Centimeter(10D) - Inch(1D)).qtup should beQ[Double, Centimeter](10.0 - inchToCentimeter)
    (Centimeter(BigDecimal(10)) - Inch(BigDecimal(1))).qtup should beQ[BigDecimal, Centimeter](
      10.0 - inchToCentimeter)
    (Centimeter(Rational(10)) - Inch(Rational(1))).qtup should beQ[Rational, Centimeter](
      10.0 - inchToCentimeter)
    (Centimeter(Algebraic(10)) - Inch(Algebraic(1))).qtup should beQ[Algebraic, Centimeter](
      10.0 - inchToCentimeter)
    (Centimeter(Real(10)) - Inch(Real(1))).qtup should beQ[Real, Centimeter](10.0 - inchToCentimeter)
    (Centimeter(Number(10)) - Inch(Number(1))).qtup should beQ[Number, Centimeter](10.0 - inchToCentimeter)
  }

  it should "implement - optimization cases" in {
    // numerator only conversion
    (Inch(13) - Foot(1)).qtup should beQXI[Int, Inch](1)

    // identity
    (Inch(2) - Inch(1)).qtup should beQXI[Int, Inch](1)
    (Inch(2.0) - Inch(1.0)).qtup should beQ[Double, Inch](1)
  }
}
