package coulomb

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

import spire.math._

import singleton.ops._

import coulomb.si._
import coulomb.siprefix._
import coulomb.mks._
import coulomb.accepted._
import coulomb.time._
import coulomb.info._
import coulomb.binprefix._
import coulomb.us._
import coulomb.temp._

import org.scalatest.QMatchers._

class QuantitySpec extends FlatSpec with Matchers {

  type _1 = W.`1`.T
  type _2 = W.`2`.T
  type _3 = W.`3`.T
  type _4 = W.`4`.T

  it should "allocate a Quantity" in {
    val q = new Quantity[Double, Meter](1.0)
    q shouldBeQ[Double, Meter](1.0)
  }

  it should "define the Standard International Base Units" in {
    val m = 1D.withUnit[Meter]
    val s = 1f.withUnit[Second]
    val kg = 1.withUnit[Kilogram]
    val a = 1L.withUnit[Ampere]
    val mol = BigInt(1).withUnit[Mole]
    val c = BigDecimal(1).withUnit[Candela]
    val k = Rational(1).withUnit[Kelvin]

    m shouldBeQ[Double, Meter](1, tolerant = false)
    s shouldBeQ[Float, Second](1, tolerant = false)
    kg shouldBeQ[Int, Kilogram](1, tolerant = false)
    a shouldBeQ[Long, Ampere](1, tolerant = false)
    mol shouldBeQ[BigInt, Mole](1, tolerant = false)
    c shouldBeQ[BigDecimal, Candela](1, tolerant = false)
    k shouldBeQ[Rational, Kelvin](1, tolerant = false)
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
    //100.toByte.withUnit[Meter].toUnit[Foot] shouldBeQ[Byte, Foot](meterToFoot, 100)
    100.toShort.withUnit[Meter].toUnit[Foot] shouldBeQ[Short, Foot](meterToFoot, 100)
    100.withUnit[Meter].toUnit[Foot] shouldBeQ[Int, Foot](meterToFoot, 100)
    100L.withUnit[Meter].toUnit[Foot] shouldBeQ[Long, Foot](meterToFoot, 100)
    BigInt(100).withUnit[Meter].toUnit[Foot] shouldBeQ[BigInt, Foot](meterToFoot, 100)

    // non-integral types
    1f.withUnit[Meter].toUnit[Foot] shouldBeQ[Float, Foot](meterToFoot)
    1D.withUnit[Meter].toUnit[Foot] shouldBeQ[Double, Foot](meterToFoot)
    BigDecimal(1D).withUnit[Meter].toUnit[Foot] shouldBeQ[BigDecimal, Foot](meterToFoot)
    Rational(1).withUnit[Meter].toUnit[Foot] shouldBeQ[Rational, Foot](meterToFoot)
    Algebraic(1).withUnit[Meter].toUnit[Foot] shouldBeQ[Algebraic, Foot](meterToFoot)
    Real(1).withUnit[Meter].toUnit[Foot] shouldBeQ[Real, Foot](meterToFoot)
    //Number(1).withUnit[Meter].toUnit[Foot] shouldBeQ[Number, Foot](meterToFoot)
  }

  it should "implement toUnit optimization cases" in {
    // numerator only conversion
    2.withUnit[Yard].toUnit[Foot] shouldBeQ[Int, Foot](6, tolerant = false)

    // identity
    2.withUnit[Meter].toUnit[Meter] shouldBeQ[Int, Meter](2, tolerant = false)
    2D.withUnit[Meter].toUnit[Meter] shouldBeQ[Double, Meter](2, tolerant = false)
  }

  it should "implement toNumeric over various numeric types" in {
    //37.withUnit[Second].toNumeric[Byte] shouldBeQ[Byte, Second](37, tolerant = false)
    37.withUnit[Second].toNumeric[Short] shouldBeQ[Short, Second](37, tolerant = false)
    37.withUnit[Second].toNumeric[Int] shouldBeQ[Int, Second](37, tolerant = false)
    37.withUnit[Second].toNumeric[Long] shouldBeQ[Long, Second](37, tolerant = false)
    37.withUnit[Second].toNumeric[BigInt] shouldBeQ[BigInt, Second](37, tolerant = false)

    37.withUnit[Second].toNumeric[Float] shouldBeQ[Float, Second](37.0)
    37.withUnit[Second].toNumeric[Double] shouldBeQ[Double, Second](37.0)
    37.withUnit[Second].toNumeric[BigDecimal] shouldBeQ[BigDecimal, Second](37.0)
    37.withUnit[Second].toNumeric[Rational] shouldBeQ[Rational, Second](37.0)
    37.withUnit[Second].toNumeric[Algebraic] shouldBeQ[Algebraic, Second](37.0)
    37.withUnit[Second].toNumeric[Real] shouldBeQ[Real, Second](37.0)
    //37.withUnit[Second].toNumeric[Number] shouldBeQ[Number, Second](37.0)
  }

  it should "implement unary -" in {
    -(42D.withUnit[Kilogram]) shouldBeQ[Double, Kilogram](-42.0)
    -(1.withUnit[Kilogram %/ Mole]) shouldBeQ[Int, Kilogram %/ Mole](-1, tolerant = false)
  }

/*
  it should "implement +" in {
    val literToCup = 4.22675283773 // Rational(2000000000 / 473176473)

    // Full rational numerator multiplication overflows smaller integers.
    // todo: investigate heuristics on smaller rationals
    // see: https://github.com/erikerlandson/coulomb/issues/15
    (Cup(100L) + Liter(100L)) shouldBeQ[Long, Cup](1.0 + literToCup, 100)
    (Cup(BigInt(100)) + Liter(BigInt(100))) shouldBeQ[BigInt, Cup](1.0 + literToCup, 100)

    (Cup(1f) + Liter(1f)) shouldBeQ[Float, Cup](1.0 + literToCup)
    (Cup(1D) + Liter(1D)) shouldBeQ[Double, Cup](1.0 + literToCup)
    (Cup(BigDecimal(1)) + Liter(BigDecimal(1))) shouldBeQ[BigDecimal, Cup](1.0 + literToCup)
    (Cup(Rational(1)) + Liter(Rational(1))) shouldBeQ[Rational, Cup](1.0 + literToCup)
    (Cup(Algebraic(1)) + Liter(Algebraic(1))) shouldBeQ[Algebraic, Cup](1.0 + literToCup)
    (Cup(Real(1)) + Liter(Real(1))) shouldBeQ[Real, Cup](1.0 + literToCup)
    (Cup(Number(1)) + Liter(Number(1))) shouldBeQ[Number, Cup](1.0 + literToCup)
  }

  it should "implement + optimization cases" in {
    // numerator only conversion
    (Cup(1) + Quart(1)) shouldBeQXI[Int, Cup](5)

    // identity
    (Cup(1) + Cup(1)) shouldBeQXI[Int, Cup](2)
    (Cup(1.0) + Cup(1.0)) shouldBeQ[Double, Cup](2.0)
  }

  it should "implement -" in {
    val inchToCentimeter = 2.54 // Rational(127 / 50)

    (Centimeter(100.toByte) - Inch(10.toByte)) shouldBeQ[Byte, Centimeter](10.0 - inchToCentimeter, 10)
    (Centimeter(1000.toShort) - Inch(100.toShort)) shouldBeQ[Short, Centimeter](
      10.0 - inchToCentimeter, 100)
    (Centimeter(1000) - Inch(100)) shouldBeQ[Int, Centimeter](10.0 - inchToCentimeter, 100)
    (Centimeter(1000L) - Inch(100L)) shouldBeQ[Long, Centimeter](10.0 - inchToCentimeter, 100)
    (Centimeter(BigInt(1000)) - Inch(BigInt(100))) shouldBeQ[BigInt, Centimeter](
      10.0 - inchToCentimeter, 100)

    (Centimeter(10f) - Inch(1f)) shouldBeQ[Float, Centimeter](10.0 - inchToCentimeter)
    (Centimeter(10D) - Inch(1D)) shouldBeQ[Double, Centimeter](10.0 - inchToCentimeter)
    (Centimeter(BigDecimal(10)) - Inch(BigDecimal(1))) shouldBeQ[BigDecimal, Centimeter](
      10.0 - inchToCentimeter)
    (Centimeter(Rational(10)) - Inch(Rational(1))) shouldBeQ[Rational, Centimeter](
      10.0 - inchToCentimeter)
    (Centimeter(Algebraic(10)) - Inch(Algebraic(1))) shouldBeQ[Algebraic, Centimeter](
      10.0 - inchToCentimeter)
    (Centimeter(Real(10)) - Inch(Real(1))) shouldBeQ[Real, Centimeter](10.0 - inchToCentimeter)
    (Centimeter(Number(10)) - Inch(Number(1))) shouldBeQ[Number, Centimeter](10.0 - inchToCentimeter)
  }

  it should "implement - optimization cases" in {
    // numerator only conversion
    (Inch(13) - Foot(1)) shouldBeQXI[Int, Inch](1)

    // identity
    (Inch(2) - Inch(1)) shouldBeQXI[Int, Inch](1)
    (Inch(2.0) - Inch(1.0)) shouldBeQ[Double, Inch](1)
  }

  it should "implement *" in {
    (Acre(2.toByte) * Foot(3.toByte)) shouldBeQXI[Byte, Acre %* Foot](6)
    (Acre(2.toShort) * Foot(3.toShort)) shouldBeQXI[Short, Acre %* Foot](6)
    (Acre(2) * Foot(3)) shouldBeQXI[Int, Acre %* Foot](6)
    (Acre(2L) * Foot(3L)) shouldBeQXI[Long, Acre %* Foot](6)
    (Acre(BigInt(2)) * Foot(BigInt(3))) shouldBeQXI[BigInt, Acre %* Foot](6)

    (Acre(2f) * Foot(3f)) shouldBeQ[Float, Acre %* Foot](6)
    (Acre(2D) * Foot(3D)) shouldBeQ[Double, Acre %* Foot](6)
    (Acre(BigDecimal(2)) * Foot(BigDecimal(3))) shouldBeQ[BigDecimal, Acre %* Foot](6)
    (Acre(Rational(2)) * Foot(Rational(3))) shouldBeQ[Rational, Acre %* Foot](6)
    (Acre(Algebraic(2)) * Foot(Algebraic(3))) shouldBeQ[Algebraic, Acre %* Foot](6)
    (Acre(Real(2)) * Foot(Real(3))) shouldBeQ[Real, Acre %* Foot](6)
    (Acre(Number(2)) * Foot(Number(3))) shouldBeQ[Number, Acre %* Foot](6)
  }

  it should "implement * miscellaneous" in {
    (2.withUnit[Meter %/ Second] * Second(3)) shouldBeQXI[Int, Meter](6)
    (2D.withUnit[Mole %/ Liter] * 2D.withUnit[Liter %/ Second] * 2D.withUnit[Second])
       shouldBeQ[Double, Mole](8)
  }

  it should "implement /" in {
    (Meter(10.toByte) / Second(3.toByte)) shouldBeQXI[Byte, Meter %/ Second](3)
    (Meter(10.toShort) / Second(3.toShort)) shouldBeQXI[Short, Meter %/ Second](3)
    (Meter(10) / Second(3)) shouldBeQXI[Int, Meter %/ Second](3)
    (Meter(10L) / Second(3L)) shouldBeQXI[Long, Meter %/ Second](3)
    (Meter(BigInt(10)) / Second(BigInt(3))) shouldBeQXI[BigInt, Meter %/ Second](3)

    (Meter(10f) / Second(3f)) shouldBeQ[Float, Meter %/ Second](3.33333)
    (Meter(10D) / Second(3D)) shouldBeQ[Double, Meter %/ Second](3.33333)
    (Meter(BigDecimal(10)) / Second(BigDecimal(3))) shouldBeQ[BigDecimal, Meter %/ Second](3.33333)
    (Meter(Rational(10)) / Second(Rational(3))) shouldBeQ[Rational, Meter %/ Second](3.33333)
    (Meter(Algebraic(10)) / Second(Algebraic(3))) shouldBeQ[Algebraic, Meter %/ Second](3.33333)
    (Meter(Real(10)) / Second(Real(3))) shouldBeQ[Real, Meter %/ Second](3.33333)
    (Meter(Number(10)) / Second(Number(3))) shouldBeQ[Number, Meter %/ Second](3.33333)
  }

  it should "implement pow" in {
    Meter(3.toByte).pow[_2] shouldBeQXI[Byte, Meter %^ _2](9)
    Meter(3.toShort).pow[_2] shouldBeQXI[Short, Meter %^ _2](9)
    Meter(3).pow[_2] shouldBeQXI[Int, Meter %^ _2](9)
    Meter(3L).pow[_2] shouldBeQXI[Long, Meter %^ _2](9)
    Meter(BigInt(3)).pow[_2] shouldBeQXI[BigInt, Meter %^ _2](9)

    Meter(3f).pow[_2] shouldBeQ[Float, Meter %^ _2](9)
    Meter(3D).pow[_2] shouldBeQ[Double, Meter %^ _2](9)
    Meter(BigDecimal(3)).pow[_2] shouldBeQ[BigDecimal, Meter %^ _2](9)
    Meter(Rational(3)).pow[_2] shouldBeQ[Rational, Meter %^ _2](9)
    Meter(Algebraic(3)).pow[_2] shouldBeQ[Algebraic, Meter %^ _2](9)
    Meter(Real(3)).pow[_2] shouldBeQ[Real, Meter %^ _2](9)
    Meter(Number(3)).pow[_2] shouldBeQ[Number, Meter %^ _2](9)
  }

  it should "implement pow miscellaneous" in {
    5D.withUnit[Meter %/ Second].pow[_0] shouldBeQ[Double, Unitless](1)
    Meter(7).pow[_1] shouldBeQXI[Int, Meter](7)
    Second(Rational(1, 11)).pow[_neg1] shouldBeQ[Rational, Second %^ _neg1](11)
  }

  it should "implement <" in {
    (Meter(1) < Meter(2)) should be (true)
    (Meter(1) < Meter(1)) should be (false)
    (Meter(2) < Meter(1)) should be (false)

    (Meter(1D) < Meter(2D)) should be (true)
    (Meter(1D) < Meter(1D)) should be (false)
    (Meter(2D) < Meter(1D)) should be (false)

    (Yard(1) < Foot(6)) should be (true)
    (Yard(1) < Foot(4)) should be (false)
    (Yard(1) < Foot(3)) should be (false)
    (Yard(1) < Foot(2)) should be (false)

    (Yard(1f) < Foot(4f)) should be (true)
    (Yard(1f) < Foot(3f)) should be (false)
    (Yard(1f) < Foot(2f)) should be (false)
  }

  it should "implement >" in {
    (Meter(1) > Meter(2)) should be (false)
    (Meter(1) > Meter(1)) should be (false)
    (Meter(2) > Meter(1)) should be (true)

    (Meter(1D) > Meter(2D)) should be (false)
    (Meter(1D) > Meter(1D)) should be (false)
    (Meter(2D) > Meter(1D)) should be (true)

    (Yard(1) > Foot(6)) should be (false)
    (Yard(1) > Foot(4)) should be (false)
    (Yard(1) > Foot(3)) should be (false)
    (Yard(1) > Foot(2)) should be (true)

    (Yard(1f) > Foot(4f)) should be (false)
    (Yard(1f) > Foot(3f)) should be (false)
    (Yard(1f) > Foot(2f)) should be (true)
  }

  it should "implement <=" in {
    (Meter(1) <= Meter(2)) should be (true)
    (Meter(1) <= Meter(1)) should be (true)
    (Meter(2) <= Meter(1)) should be (false)

    (Meter(1D) <= Meter(2D)) should be (true)
    (Meter(1D) <= Meter(1D)) should be (true)
    (Meter(2D) <= Meter(1D)) should be (false)

    (Yard(1) <= Foot(6)) should be (true)
    (Yard(1) <= Foot(4)) should be (true)
    (Yard(1) <= Foot(3)) should be (true)
    (Yard(1) <= Foot(2)) should be (false)

    (Yard(1f) <= Foot(4f)) should be (true)
    (Yard(1f) <= Foot(3f)) should be (true)
    (Yard(1f) <= Foot(2f)) should be (false)
  }

  it should "implement >=" in {
    (Meter(1) >= Meter(2)) should be (false)
    (Meter(1) >= Meter(1)) should be (true)
    (Meter(2) >= Meter(1)) should be (true)

    (Meter(1D) >= Meter(2D)) should be (false)
    (Meter(1D) >= Meter(1D)) should be (true)
    (Meter(2D) >= Meter(1D)) should be (true)

    (Yard(1) >= Foot(6)) should be (false)
    (Yard(1) >= Foot(4)) should be (true)
    (Yard(1) >= Foot(3)) should be (true)
    (Yard(1) >= Foot(2)) should be (true)

    (Yard(1f) >= Foot(4f)) should be (false)
    (Yard(1f) >= Foot(3f)) should be (true)
    (Yard(1f) >= Foot(2f)) should be (true)
  }

  it should "implement ===" in {
    (Meter(1) === Meter(2)) should be (false)
    (Meter(1) === Meter(1)) should be (true)
    (Meter(2) === Meter(1)) should be (false)

    (Meter(1D) === Meter(2D)) should be (false)
    (Meter(1D) === Meter(1D)) should be (true)
    (Meter(2D) === Meter(1D)) should be (false)

    (Yard(1) === Foot(6)) should be (false)
    (Yard(1) === Foot(4)) should be (true)
    (Yard(1) === Foot(3)) should be (true)
    (Yard(1) === Foot(2)) should be (false)

    (Yard(1f) === Foot(4f)) should be (false)
    (Yard(1f) === Foot(3f)) should be (true)
    (Yard(1f) === Foot(2f)) should be (false)
  }

  it should "implement =!=" in {
    (Meter(1) =!= Meter(2)) should be (true)
    (Meter(1) =!= Meter(1)) should be (false)
    (Meter(2) =!= Meter(1)) should be (true)

    (Meter(1D) =!= Meter(2D)) should be (true)
    (Meter(1D) =!= Meter(1D)) should be (false)
    (Meter(2D) =!= Meter(1D)) should be (true)

    (Yard(1) =!= Foot(6)) should be (true)
    (Yard(1) =!= Foot(4)) should be (false)
    (Yard(1) =!= Foot(3)) should be (false)
    (Yard(1) =!= Foot(2)) should be (true)

    (Yard(1f) =!= Foot(4f)) should be (true)
    (Yard(1f) =!= Foot(3f)) should be (false)
    (Yard(1f) =!= Foot(2f)) should be (true)
  }

  it should "implement toStr" in {
    Meter(1).toStr should be ("1 m")
    1.withUnit[Kilo %* Meter].toStr should be ("1 km")
    (Meter(1.5) / Second(1.0)).toStr should be ("1.5 m / s")
    Second(1.0).pow[_neg1].toStr should be ("1.0 s^-1")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].toStr should be ("1 (acre ft) / (m s)")
    1.withUnit[Meter %/ (Second %^ _2)].toStr should be ("1 m / (s^2)")
  }

  it should "implement toStrFull" in {
    Meter(1).toStrFull should be ("1 meter")
    1.withUnit[Kilo %* Meter].toStrFull should be ("1 kilo-meter")
    (Meter(1.5) / Second(1.0)).toStrFull should be ("1.5 meter / second")
    Second(1.0).pow[_neg1].toStrFull should be ("1.0 second ^ -1")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].toStrFull should be ("1 (acre * foot) / (meter * second)")
    1.withUnit[Meter %/ (Second %^ _2)].toStrFull should be ("1 meter / (second ^ 2)")
  }

  it should "implement unitStr" in {
    Meter(1).unitStr should be ("m")
    1.withUnit[Kilo %* Meter].unitStr should be ("km")
    (Meter(1.5) / Second(1.0)).unitStr should be ("m / s")
    Second(1.0).pow[_neg1].unitStr should be ("s^-1")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].unitStr should be ("(acre ft) / (m s)")
    1.withUnit[Meter %/ (Second %^ _2)].unitStr should be ("m / (s^2)")
  }

  it should "implement unitStrFull" in {
    Meter(1).unitStrFull should be ("meter")
    1.withUnit[Kilo %* Meter].unitStrFull should be ("kilo-meter")
    (Meter(1.5) / Second(1.0)).unitStrFull should be ("meter / second")
    Second(1.0).pow[_neg1].unitStrFull should be ("second ^ -1")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].unitStrFull should be ("(acre * foot) / (meter * second)")
    1.withUnit[Meter %/ (Second %^ _2)].unitStrFull should be ("meter / (second ^ 2)")
  }

  it should "implement converter companion method" in {
    val f1 = Quantity.converter[Double, Kilo %* Meter, Mile]
    f1(1D.withUnit[Kilo %* Meter]) shouldBeQ[Double, Mile](0.62137)
    f1(Mile(1.0)) shouldBeQ[Double, Mile](1.0)

    val f2 = Quantity.converter[Algebraic, Meter %/ (Second %^ _2), Foot %/ (Second %^ _2)]
    f2(Algebraic(9.801).withUnit[Meter %/ (Second %^ _2)])
       shouldBeQ[Algebraic, Foot %/ (Second %^ _2)](32.1555)
    f2(Algebraic(1).withUnit[Foot %/ (Second %^ _2)])
       shouldBeQ[Algebraic, Foot %/ (Second %^ _2)](1.0)

    "Quantity.converter[Algebraic, Meter %/ (Second %^ _2), Mole %/ (Second %^ _2)]" shouldNot compile
    "Quantity.converter[Algebraic, Meter %/ (Second %^ _2), Foot %/ (Second %^ _3)]" shouldNot compile
  }

  it should "implement coefficient companion method" in {
    Quantity.coefficient[Yard, Meter] should be (Rational(9144, 10000))
    Quantity.coefficient[Mile %/ Hour, Meter %/ Second] should be (Rational(1397, 3125))
    Quantity.coefficient[(Kilo %* Meter) %/ (Second %^ _2), Mile %/ (Minute %^ _2)] should be (
      Rational(3125000, 1397))

    "Quantity.coefficient[Mile %/ Hour, Meter %/ Kilogram]" shouldNot compile
    "Quantity.coefficient[(Kilo %* Meter) %/ (Second %^ _2), Mile %/ (Ampere %^ _2)]" shouldNot compile
  }

  it should "implement unitStr companion method" in {
    Quantity.unitStr[Meter] should be ("m")
    Quantity.unitStr[Kilo %* Meter] should be ("km")
    Quantity.unitStr[Meter %/ Second] should be ("m / s")
  }

  it should "implement unitStrFull companion method" in {
    Quantity.unitStrFull[Meter] should be ("meter")
    Quantity.unitStrFull[Kilo %* Meter] should be ("kilo-meter")
    Quantity.unitStrFull[Meter %/ Second] should be ("meter / second")
  }

  it should "implement implicit conversion between convertable units" in {
    (1D.withUnit[Yard] :Quantity[Double, Foot]) shouldBeQ[Double, Foot](3)

    val q: Quantity[Double, Mile %/ Hour] = 1D.withUnit[Kilo %* Meter %/ Second]
    q shouldBeQ[Double, Mile %/ Hour](2236.936292)

    def f(a: Double WithUnit (Meter %/ (Second %^ _2))) = a
    f(32D.withUnit[Foot %/ (Second %^ _2)]) shouldBeQ[Double, Meter %/ (Second %^ _2)](9.7536)
  }
*/
}
