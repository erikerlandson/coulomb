package coulomb

import org.scalatest._
import org.scalactic._
import TripleEquals._

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import coulomb.si._
import coulomb.temp._

import org.scalatest.QMatchers._

class TemperatureSpec extends FlatSpec with Matchers {

  it should "allocate a Temperature" in {
    val t = new Temperature[Double, Kelvin](1.0)
    t shouldBeT[Double, Kelvin](1, tolerant = false)
  }

  it should "define common temperature types" in {
    val k = 1D.withTemperature[Kelvin]
    val c = 1.withTemperature[Celsius]
    val f = 1f.withTemperature[Fahrenheit]

    k shouldBeT[Double, Kelvin](1)
    c shouldBeT[Int, Celsius](1, tolerant = false)
    f shouldBeT[Float, Fahrenheit](1)
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
    32.withTemperature[Fahrenheit].toUnit[Celsius] shouldBeT[Int, Celsius](0, tolerant = false)
    32D.withTemperature[Fahrenheit].toUnit[Celsius] shouldBeT[Double, Celsius](0)

    // identity
    32.withTemperature[Fahrenheit].toUnit[Fahrenheit] shouldBeT[Int, Fahrenheit](32, tolerant = false)
    32f.withTemperature[Fahrenheit].toUnit[Fahrenheit] shouldBeT[Float, Fahrenheit](32)

    // unit coefficient
    20L.withTemperature[Celsius].toUnit[Kelvin] shouldBeT[Long, Kelvin](293)
    BigDecimal(20).withTemperature[Celsius].toUnit[Kelvin] shouldBeT[BigDecimal, Kelvin](293.15)
  }

  it should "implement toNumeric" in {
    //(37.0).withTemperature[Celsius].toNumeric[Byte] shouldBeT[Byte, Celsius](37, tolerant = false)
    (37.0).withTemperature[Celsius].toNumeric[Short] shouldBeT[Short, Celsius](37, tolerant = false)
    (37.0).withTemperature[Celsius].toNumeric[Int] shouldBeT[Int, Celsius](37, tolerant = false)
    (37.0).withTemperature[Celsius].toNumeric[Long] shouldBeT[Long, Celsius](37, tolerant = false)
    (37.0).withTemperature[Celsius].toNumeric[BigInt] shouldBeT[BigInt, Celsius](37, tolerant = false)

    (37.0).withTemperature[Celsius].toNumeric[Float] shouldBeT[Float, Celsius](37)
    (37.0).withTemperature[Celsius].toNumeric[Double] shouldBeT[Double, Celsius](37)
    (37.0).withTemperature[Celsius].toNumeric[BigDecimal] shouldBeT[BigDecimal, Celsius](37)
    (37.0).withTemperature[Celsius].toNumeric[Rational] shouldBeT[Rational, Celsius](37)
    (37.0).withTemperature[Celsius].toNumeric[Algebraic] shouldBeT[Algebraic, Celsius](37)
    (37.0).withTemperature[Celsius].toNumeric[Real] shouldBeT[Real, Celsius](37)
    //(37.0).withTemperature[Celsius].toNumeric[Number] shouldBeT[Number, Celsius](37)
  }

  it should "implement T + Q => T" in {
    (0.withTemperature[Celsius] + 18.withUnit[Fahrenheit]) shouldBeT[Int, Celsius](10, tolerant = false)
    (0D.withTemperature[Celsius] + 18D.withUnit[Fahrenheit]) shouldBeT[Double, Celsius](10)

    (0.withTemperature[Fahrenheit] + 10.withUnit[Celsius]) shouldBeT[Int, Fahrenheit](18, tolerant = false)
    (0D.withTemperature[Fahrenheit] + 10D.withUnit[Celsius]) shouldBeT[Double, Fahrenheit](18)
  }

  it should "implement T - Q => T" in {
    (32.withTemperature[Celsius] - 18.withUnit[Fahrenheit]) shouldBeT[Int, Celsius](22, tolerant = false)
    (32D.withTemperature[Celsius] - 18D.withUnit[Fahrenheit]) shouldBeT[Double, Celsius](22)

    (32.withTemperature[Fahrenheit] - 10.withUnit[Celsius]) shouldBeT[Int, Fahrenheit](14, tolerant = false)
    (32D.withTemperature[Fahrenheit] - 10D.withUnit[Celsius]) shouldBeT[Double, Fahrenheit](14)
  }

  it should "implement T - T => Q" in {
    (150.withTemperature[Celsius] - 212.withTemperature[Fahrenheit]) shouldBeQ[Int, Celsius](50)
    (150D.withTemperature[Celsius] - 212D.withTemperature[Fahrenheit]) shouldBeQ[Double, Celsius](50)

    (212.withTemperature[Fahrenheit] - 50.withTemperature[Celsius]) shouldBeQ[Int, Fahrenheit](90)
    (212f.withTemperature[Fahrenheit] - 50f.withTemperature[Celsius]) shouldBeQ[Float, Fahrenheit](90)
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

  it should "implement show" in {
    1.withTemperature[Celsius].show should be ("1 °C")
    (1.5).withTemperature[Fahrenheit].show should be ("1.5 °F")
    0f.withTemperature[Kelvin].show should be ("0.0 K")
  }

  it should "implement showFull" in {
    1.withTemperature[Celsius].showFull should be ("1 Celsius")
    (1.5).withTemperature[Fahrenheit].showFull should be ("1.5 Fahrenheit")
    0f.withTemperature[Kelvin].showFull should be ("0.0 Kelvin")
  }

  it should "implement showUnit" in {
    1.withTemperature[Celsius].showUnit should be ("°C")
    (1.5).withTemperature[Fahrenheit].showUnit should be ("°F")
    0f.withTemperature[Kelvin].showUnit should be ("K")
  }

  it should "implement showUnitFull" in {
    1.withTemperature[Celsius].showUnitFull should be ("Celsius")
    (1.5).withTemperature[Fahrenheit].showUnitFull should be ("Fahrenheit")
    0f.withTemperature[Kelvin].showUnitFull should be ("Kelvin")
  }

  it should "implement showUnit companion method" in {
    Temperature.showUnit[Celsius] should be ("°C")
    Temperature.showUnit[Fahrenheit] should be ("°F")
    Temperature.showUnit[Kelvin] should be ("K")
  }

  it should "implement showUnitFull companion method" in {
    Temperature.showUnitFull[Celsius] should be ("Celsius")
    Temperature.showUnitFull[Fahrenheit] should be ("Fahrenheit")
    Temperature.showUnitFull[Kelvin] should be ("Kelvin")
  }

  it should "implement implicit conversion between convertable temps" in {
    (37D.withTemperature[Celsius] :Temperature[Double, Fahrenheit]) shouldBeT[Double, Fahrenheit](98.6)

    val t: Temperature[Double, Fahrenheit] = 37D.withTemperature[Celsius]
    t shouldBeT[Double, Fahrenheit](98.6)

    def f(t: Float WithTemperature Celsius) = t
    f(98.6f.withTemperature[Fahrenheit]) shouldBeT[Float, Celsius](37)
  }

  it should "support fromQuantity and toQuantity" in {
    Temperature.fromQuantity(32.withUnit[Fahrenheit]) shouldBeT[Int, Fahrenheit](32)
    Temperature.fromQuantity(300f.withUnit[Kelvin]) shouldBeT[Float, Kelvin](300)
    Temperature.fromQuantity(BigDecimal(100).withUnit[Celsius]) shouldBeT[BigDecimal, Celsius](100)

    Temperature.toQuantity(32.withTemperature[Fahrenheit]) shouldBeQ[Int, Fahrenheit](32)
    Temperature.toQuantity(300f.withTemperature[Kelvin]) shouldBeQ[Float, Kelvin](300)
    Temperature.toQuantity(Algebraic(100).withTemperature[Celsius]) shouldBeQ[Algebraic, Celsius](100)
  }

  it should "be serializable" in {
    import coulomb.scalatest.serde._
    val ts = Temperature[Int, Celsius](100)
    val td = roundTripSerDe(ts)
    td.shouldBeT[Int, Celsius](100)
    (td === ts).shouldBe(true)
  }
}
