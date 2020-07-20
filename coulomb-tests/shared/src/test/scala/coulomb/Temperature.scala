package coulomb

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import utest._

import coulomb.si._
import coulomb.temp._

import coulomb.validators.CoulombValidators._

object TemperatureTests extends TestSuite {
  val tests = Tests {
    test("allocate a Temperature") {
      val t = new Temperature[Double, Kelvin](1.0)
      assert(t.isValidT[Double, Kelvin](1, tolerant = false))
    }

    test("define common temperature types") {
      assert(
        (1D.withTemperature[Kelvin]).isValidT[Double, Kelvin](1),
        (1.withTemperature[Celsius]).isValidT[Int, Celsius](1, tolerant = false),
        (1f.withTemperature[Fahrenheit]).isValidT[Float, Fahrenheit](1)
      )
    }

    test("enforce convertability at compile time") {
      assert(1D.withTemperature[Kelvin].toUnit[Celsius].isValidT[Double, Celsius](-272.15000))
      compileError("1D.withTemperature[Kelvin].toUnit[Meter]")

      assert(1D.withTemperature[Celsius].toUnit[Fahrenheit].isValidT[Double, Fahrenheit](33.80000))
      compileError("1D.withTemperature[Celsius].toUnit[Second]")

      assert(1D.withTemperature[Fahrenheit].toUnit[Kelvin].isValidT[Double, Kelvin](255.92778))
      compileError("1D.withTemperature[Fahrenheit].toUnit[Kilogram]")
    }

    test("implement toUnit") {
      assert(
        32.withTemperature[Fahrenheit].toUnit[Celsius].isValidT[Int, Celsius](0, tolerant = false),
        32D.withTemperature[Fahrenheit].toUnit[Celsius].isValidT[Double, Celsius](0),

        // identity
        32.withTemperature[Fahrenheit].toUnit[Fahrenheit].isValidT[Int, Fahrenheit](32, tolerant = false),
        32f.withTemperature[Fahrenheit].toUnit[Fahrenheit].isValidT[Float, Fahrenheit](32),

        // unit coefficient
        20L.withTemperature[Celsius].toUnit[Kelvin].isValidT[Long, Kelvin](293),
        BigDecimal(20).withTemperature[Celsius].toUnit[Kelvin].isValidT[BigDecimal, Kelvin](293.15)
      )
    }

    test("implement toValue") {
      assert(
        (37.0).withTemperature[Celsius].toValue[Short].isValidT[Short, Celsius](37, tolerant = false),
        (37.0).withTemperature[Celsius].toValue[Int].isValidT[Int, Celsius](37, tolerant = false),
        (37.0).withTemperature[Celsius].toValue[Long].isValidT[Long, Celsius](37, tolerant = false),
        (37.0).withTemperature[Celsius].toValue[BigInt].isValidT[BigInt, Celsius](37, tolerant = false),

        (37.0).withTemperature[Celsius].toValue[Float].isValidT[Float, Celsius](37),
        (37.0).withTemperature[Celsius].toValue[Double].isValidT[Double, Celsius](37),
        (37.0).withTemperature[Celsius].toValue[BigDecimal].isValidT[BigDecimal, Celsius](37),
        (37.0).withTemperature[Celsius].toValue[Rational].isValidT[Rational, Celsius](37),
        (37.0).withTemperature[Celsius].toValue[Algebraic].isValidT[Algebraic, Celsius](37),
        (37.0).withTemperature[Celsius].toValue[Real].isValidT[Real, Celsius](37)
      )
    }

    test("implement T + Q => T") {
      assert(
        (0.withTemperature[Celsius] + 18.withUnit[Fahrenheit]).isValidT[Int, Celsius](10, tolerant = false),
        (0D.withTemperature[Celsius] + 18D.withUnit[Fahrenheit]).isValidT[Double, Celsius](10),

        (0.withTemperature[Fahrenheit] + 10.withUnit[Celsius]).isValidT[Int, Fahrenheit](18, tolerant = false),
        (0D.withTemperature[Fahrenheit] + 10D.withUnit[Celsius]).isValidT[Double, Fahrenheit](18)
      )
    }

    test("implement T - Q => T") {
      assert(
        (32.withTemperature[Celsius] - 18.withUnit[Fahrenheit]).isValidT[Int, Celsius](22, tolerant = false),
        (32D.withTemperature[Celsius] - 18D.withUnit[Fahrenheit]).isValidT[Double, Celsius](22),

        (32.withTemperature[Fahrenheit] - 10.withUnit[Celsius]).isValidT[Int, Fahrenheit](14, tolerant = false),
        (32D.withTemperature[Fahrenheit] - 10D.withUnit[Celsius]).isValidT[Double, Fahrenheit](14)
      )
    }

    test("implement T - T => Q") {
      assert(
        (150.withTemperature[Celsius] - 212.withTemperature[Fahrenheit]).isValidQ[Int, Celsius](50),
        (150D.withTemperature[Celsius] - 212D.withTemperature[Fahrenheit]).isValidQ[Double, Celsius](50),

        (212.withTemperature[Fahrenheit] - 50.withTemperature[Celsius]).isValidQ[Int, Fahrenheit](90),
        (212f.withTemperature[Fahrenheit] - 50f.withTemperature[Celsius]).isValidQ[Float, Fahrenheit](90)
      )
    }

    test("implement <") {
      assert(
        (1.withTemperature[Celsius] < 2.withTemperature[Celsius]) == (true),
        (1.withTemperature[Celsius] < 1.withTemperature[Celsius]) == (false),
        (1.withTemperature[Celsius] < 0.withTemperature[Celsius]) == (false),

        (Rational(0).withTemperature[Celsius] < Rational(33).withTemperature[Fahrenheit]) == (true),
        (Rational(0).withTemperature[Celsius] < Rational(32).withTemperature[Fahrenheit]) == (false),
        (Rational(0).withTemperature[Celsius] < Rational(31).withTemperature[Fahrenheit]) == (false)
      )
    }

    test("implement >") {
      assert(
        (1.withTemperature[Celsius] > 2.withTemperature[Celsius]) == (false),
        (1.withTemperature[Celsius] > 1.withTemperature[Celsius]) == (false),
        (1.withTemperature[Celsius] > 0.withTemperature[Celsius]) == (true),

        (Rational(0).withTemperature[Celsius] > Rational(33).withTemperature[Fahrenheit]) == (false),
        (Rational(0).withTemperature[Celsius] > Rational(32).withTemperature[Fahrenheit]) == (false),
        (Rational(0).withTemperature[Celsius] > Rational(31).withTemperature[Fahrenheit]) == (true)
      )
    }

    test("implement <=") {
      assert(
        (1.withTemperature[Celsius] <= 2.withTemperature[Celsius]) == (true),
        (1.withTemperature[Celsius] <= 1.withTemperature[Celsius]) == (true),
        (1.withTemperature[Celsius] <= 0.withTemperature[Celsius]) == (false),

        (Rational(0).withTemperature[Celsius] <= Rational(33).withTemperature[Fahrenheit]) == (true),
        (Rational(0).withTemperature[Celsius] <= Rational(32).withTemperature[Fahrenheit]) == (true),
        (Rational(0).withTemperature[Celsius] <= Rational(31).withTemperature[Fahrenheit]) == (false)
      )
    }

    test("implement >=") {
      assert(
        (1.withTemperature[Celsius] >= 2.withTemperature[Celsius]) == (false),
        (1.withTemperature[Celsius] >= 1.withTemperature[Celsius]) == (true),
        (1.withTemperature[Celsius] >= 0.withTemperature[Celsius]) == (true),

        (Rational(0).withTemperature[Celsius] >= Rational(33).withTemperature[Fahrenheit]) == (false),
        (Rational(0).withTemperature[Celsius] >= Rational(32).withTemperature[Fahrenheit]) == (true),
        (Rational(0).withTemperature[Celsius] >= Rational(31).withTemperature[Fahrenheit]) == (true)
      )
    }

    test("implement ===") {
      assert(
        (1.withTemperature[Celsius] === 2.withTemperature[Celsius]) == (false),
        (1.withTemperature[Celsius] === 1.withTemperature[Celsius]) == (true),
        (1.withTemperature[Celsius] === 0.withTemperature[Celsius]) == (false),

        (Rational(0).withTemperature[Celsius] === Rational(33).withTemperature[Fahrenheit]) == (false),
        (Rational(0).withTemperature[Celsius] === Rational(32).withTemperature[Fahrenheit]) == (true),
        (Rational(0).withTemperature[Celsius] === Rational(31).withTemperature[Fahrenheit]) == (false)
      )
    }

    test("implement =!=") {
      assert(
        (1.withTemperature[Celsius] =!= 2.withTemperature[Celsius]) == (true),
        (1.withTemperature[Celsius] =!= 1.withTemperature[Celsius]) == (false),
        (1.withTemperature[Celsius] =!= 0.withTemperature[Celsius]) == (true),

        (Rational(0).withTemperature[Celsius] =!= Rational(33).withTemperature[Fahrenheit]) == (true),
        (Rational(0).withTemperature[Celsius] =!= Rational(32).withTemperature[Fahrenheit]) == (false),
        (Rational(0).withTemperature[Celsius] =!= Rational(31).withTemperature[Fahrenheit]) == (true)
      )
    }

    test("implement show") {

        println(0f.withTemperature[Kelvin].show)
      assert(
        1.withTemperature[Celsius].show == ("1 °C"),
        (1.5).withTemperature[Fahrenheit].show == ("1.5 °F"),
        0f.withTemperature[Kelvin].show == ("0 K") || 0f.withTemperature[Kelvin].show == ("0.0 K")
      )
    }

    test("implement showFull") {
      assert(
        1.withTemperature[Celsius].showFull == ("1 Celsius"),
        (1.5).withTemperature[Fahrenheit].showFull == ("1.5 Fahrenheit"),
        0f.withTemperature[Kelvin].showFull == ("0 Kelvin") || 0f.withTemperature[Kelvin].showFull == ("0.0 Kelvin")
      )
    }

    test("implement showUnit") {
      assert(
        1.withTemperature[Celsius].showUnit == ("°C"),
        (1.5).withTemperature[Fahrenheit].showUnit == ("°F"),
        0f.withTemperature[Kelvin].showUnit == ("K")
      )
    }

    test("implement showUnitFull") {
      assert(
        1.withTemperature[Celsius].showUnitFull == ("Celsius"),
        (1.5).withTemperature[Fahrenheit].showUnitFull == ("Fahrenheit"),
        0f.withTemperature[Kelvin].showUnitFull == ("Kelvin")
      )
    }

    test("implement showUnit companion method") {
      assert(
        Temperature.showUnit[Celsius] == ("°C"),
        Temperature.showUnit[Fahrenheit] == ("°F"),
        Temperature.showUnit[Kelvin] == ("K")
      )
    }

    test("implement showUnitFull companion method") {
      assert(
        Temperature.showUnitFull[Celsius] == ("Celsius"),
        Temperature.showUnitFull[Fahrenheit] == ("Fahrenheit"),
        Temperature.showUnitFull[Kelvin] == ("Kelvin")
      )
    }

    test("implement implicit conversion between convertable temps") {
      assert((37D.withTemperature[Celsius] :Temperature[Double, Fahrenheit]).isValidT[Double, Fahrenheit](98.6))

      val t: Temperature[Double, Fahrenheit] = 37D.withTemperature[Celsius]
      assert(t.isValidT[Double, Fahrenheit](98.6))

      def f(t: Float WithTemperature Celsius) = t
      assert(f(98.6f.withTemperature[Fahrenheit]).isValidT[Float, Celsius](37))
    }

    test("support fromQuantity and toQuantity") {
      assert(
        Temperature.fromQuantity(32.withUnit[Fahrenheit]).isValidT[Int, Fahrenheit](32),
        Temperature.fromQuantity(300f.withUnit[Kelvin]).isValidT[Float, Kelvin](300),
        Temperature.fromQuantity(BigDecimal(100).withUnit[Celsius]).isValidT[BigDecimal, Celsius](100),

        Temperature.toQuantity(32.withTemperature[Fahrenheit]).isValidQ[Int, Fahrenheit](32),
        Temperature.toQuantity(300f.withTemperature[Kelvin]).isValidQ[Float, Kelvin](300),
        Temperature.toQuantity(Algebraic(100).withTemperature[Celsius]).isValidQ[Algebraic, Celsius](100)
      )
    }

  }
}
