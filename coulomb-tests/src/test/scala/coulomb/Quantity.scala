package coulomb

import spire.math.{ Rational, Real, Algebraic }
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

import singleton.ops._

import utest._

import coulomb.si._
import coulomb.siprefix._
import coulomb.mks._
import coulomb.accepted._
import coulomb.time._
import coulomb.info._
import coulomb.binprefix._
import coulomb.us._
import coulomb.temp._

import coulomb.validators.CoulombValidators._

object QuantityTests extends TestSuite {
  val tests = Tests {
    test("allocate a Quantity") {
      val q = new Quantity[Double, Meter](1.0)
      assert(q.isValidQ[Double, Meter](1.0))
    }

    test("define the SI Base Units") {
      assert(
        (1D.withUnit[Meter]).isValidQ[Double, Meter](1, tolerant = false),
        (1f.withUnit[Second]).isValidQ[Float, Second](1, tolerant = false),
        (1.withUnit[Kilogram]).isValidQ[Int, Kilogram](1, tolerant = false),
        (1L.withUnit[Ampere]).isValidQ[Long, Ampere](1, tolerant = false),
        (BigInt(1).withUnit[Mole]).isValidQ[BigInt, Mole](1, tolerant = false),
        (BigDecimal(1).withUnit[Candela]).isValidQ[BigDecimal, Candela](1, tolerant = false),
        (Rational(1).withUnit[Kelvin]).isValidQ[Rational, Kelvin](1, tolerant = false)
      )
    }

    test("enforce unit convertability at compile time") {
      assert((1D.withUnit[Meter].toUnit[Foot]).isValidQ[Double, Foot](3.28084))
      compileError("1D.withUnit[Meter].toUnit[Second]")

      assert((1D.withUnit[Acre %* Foot].toUnit[Mega %* Liter]).isValidQ[Double, Mega %* Liter](1.2335))
      compileError("1D.withUnit[Acre %* Foot].toUnit[Mega %* Hectare]")

      assert(1D.withUnit[Mole %/ Liter].toUnit[(Kilo %* Mole) %/ (Meter %^ 3)]
        .isValidQ[Double, (Kilo %* Mole) %/ (Meter %^ 3)](1.0))
      compileError("1D.withUnit[Mole %/ Liter].toUnit[(Kilo %* Mole) %/ (Meter %^ 4)]")
    }

    test("implement toUnit over supported numeric types") {
      val meterToFoot = 3.2808399

      assert(
        // integral types
        //100.toByte.withUnit[Meter].toUnit[Foot].isValidQ[Byte, Foot](meterToFoot, 100),
        100.toShort.withUnit[Meter].toUnit[Foot].isValidQ[Short, Foot](meterToFoot, 100),
        100.withUnit[Meter].toUnit[Foot].isValidQ[Int, Foot](meterToFoot, 100),
        100L.withUnit[Meter].toUnit[Foot].isValidQ[Long, Foot](meterToFoot, 100),
        BigInt(100).withUnit[Meter].toUnit[Foot].isValidQ[BigInt, Foot](meterToFoot, 100),

        // non-integral types
        1f.withUnit[Meter].toUnit[Foot].isValidQ[Float, Foot](meterToFoot),
        1D.withUnit[Meter].toUnit[Foot].isValidQ[Double, Foot](meterToFoot),
        BigDecimal(1D).withUnit[Meter].toUnit[Foot].isValidQ[BigDecimal, Foot](meterToFoot),
        Rational(1).withUnit[Meter].toUnit[Foot].isValidQ[Rational, Foot](meterToFoot),
        Algebraic(1).withUnit[Meter].toUnit[Foot].isValidQ[Algebraic, Foot](meterToFoot),
        Real(1).withUnit[Meter].toUnit[Foot].isValidQ[Real, Foot](meterToFoot)
        //Number(1).withUnit[Meter].toUnit[Foot].isValidQ[Number, Foot](meterToFoot)
      )
    }

    test("implement toUnit optimization cases") {
      assert(
        // numerator only conversion
        2.withUnit[Yard].toUnit[Foot].isValidQ[Int, Foot](6, tolerant = false),

        // identity
        2.withUnit[Meter].toUnit[Meter].isValidQ[Int, Meter](2, tolerant = false),
        2D.withUnit[Meter].toUnit[Meter].isValidQ[Double, Meter](2, tolerant = false)
      )
    }

    test("implement toValue over various numeric types") {
      assert(
        37.withUnit[Second].toValue[Short].isValidQ[Short, Second](37, tolerant = false),
        37.withUnit[Second].toValue[Int].isValidQ[Int, Second](37, tolerant = false),
        37.withUnit[Second].toValue[Long].isValidQ[Long, Second](37, tolerant = false),
        37.withUnit[Second].toValue[BigInt].isValidQ[BigInt, Second](37, tolerant = false),

        37.withUnit[Second].toValue[Float].isValidQ[Float, Second](37.0),
        37.withUnit[Second].toValue[Double].isValidQ[Double, Second](37.0),
        37.withUnit[Second].toValue[BigDecimal].isValidQ[BigDecimal, Second](37.0),
        37.withUnit[Second].toValue[Rational].isValidQ[Rational, Second](37.0),
        37.withUnit[Second].toValue[Algebraic].isValidQ[Algebraic, Second](37.0),
        37.withUnit[Second].toValue[Real].isValidQ[Real, Second](37.0)
      )
    }

    test("implement unary -") {
      assert(
        (-(42D.withUnit[Kilogram])).isValidQ[Double, Kilogram](-42.0),
        (-(1.withUnit[Kilogram %/ Mole])).isValidQ[Int, Kilogram %/ Mole](-1, tolerant = false)
      )
    }

    test("implement +") {
      val literToCup = 4.22675283773 // Rational(2000000000 / 473176473)
      assert(

        (100.withUnit[Cup] + 100.withUnit[Liter]).isValidQ[Int, Cup](1.0 + literToCup, 100),
        (100L.withUnit[Cup] + 100L.withUnit[Liter]).isValidQ[Long, Cup](1.0 + literToCup, 100),

        (1f.withUnit[Cup] + 1f.withUnit[Liter]).isValidQ[Float, Cup](1.0 + literToCup),
        (1D.withUnit[Cup] + 1D.withUnit[Liter]).isValidQ[Double, Cup](1.0 + literToCup)
      )
    }

    test("implement + optimization cases") {
      assert(
        // numerator only conversion
        (1.withUnit[Cup] + 1.withUnit[Quart]).isValidQ[Int, Cup](5, tolerant = false),

        // identity
        (1.withUnit[Cup] + 1.withUnit[Cup]).isValidQ[Int, Cup](2, tolerant = false),
        (1D.withUnit[Cup] + 1D.withUnit[Cup]).isValidQ[Double, Cup](2.0)
      )
    }

    test("implement -") {
      val inchToCentimeter = 2.54 // Rational(127 / 50)

      assert(
        (1000.withUnit[Centimeter] - 100.withUnit[Inch]).isValidQ[Int, Centimeter](10.0 - inchToCentimeter, 100),
        (1000L.withUnit[Centimeter] - 100L.withUnit[Inch]).isValidQ[Long, Centimeter](10.0 - inchToCentimeter, 100),

        (10f.withUnit[Centimeter] - 1f.withUnit[Inch]).isValidQ[Float, Centimeter](10.0 - inchToCentimeter),
        (10D.withUnit[Centimeter] - 1D.withUnit[Inch]).isValidQ[Double, Centimeter](10.0 - inchToCentimeter)
      )
    }

    test("implement - optimization cases") {
      assert(
        // numerator only conversion
        (13.withUnit[Inch] - 1.withUnit[Foot]).isValidQ[Int, Inch](1, tolerant = false),

        // identity
        (2.withUnit[Inch] - 1.withUnit[Inch]).isValidQ[Int, Inch](1, tolerant = false),
        (2D.withUnit[Inch] - 1D.withUnit[Inch]).isValidQ[Double, Inch](1)
      )
    }

    test("implement *") {
      assert(
        (2.withUnit[Acre] * 3.withUnit[Foot]).isValidQ[Int, Acre %* Foot](6, tolerant = false),
        (2L.withUnit[Acre] * 3L.withUnit[Foot]).isValidQ[Long, Acre %* Foot](6, tolerant = false),

        (2f.withUnit[Acre] * 3f.withUnit[Foot]).isValidQ[Float, Acre %* Foot](6),
        (2D.withUnit[Acre] * 3D.withUnit[Foot]).isValidQ[Double, Acre %* Foot](6)
      )
    }

    test("implement * miscellaneous") {
      assert(
        (2.withUnit[Meter %/ Second] * 3.withUnit[Second]).isValidQ[Int, Meter](6, tolerant = false),
        (2D.withUnit[Mole %/ Liter] * 2D.withUnit[Liter %/ Second] * 2D.withUnit[Second]).isValidQ[Double, Mole](8)
      )
    }

    test("implement /") {
      assert(
        (10.withUnit[Meter] / 3.withUnit[Second]).isValidQ[Int, Meter %/ Second](3, tolerant = false),
        (10L.withUnit[Meter] / 3L.withUnit[Second]).isValidQ[Long, Meter %/ Second](3, tolerant = false),

        (10f.withUnit[Meter] / 3f.withUnit[Second]).isValidQ[Float, Meter %/ Second](3.3333),
        (10D.withUnit[Meter] / 3D.withUnit[Second]).isValidQ[Double, Meter %/ Second](3.3333)
      )
    }

    test("implement pow") {
      assert(
        3.withUnit[Meter].pow[2].isValidQ[Int, Meter %^ 2](9, tolerant = false),
        3L.withUnit[Meter].pow[2].isValidQ[Long, Meter %^ 2](9, tolerant = false),

        3f.withUnit[Meter].pow[2].isValidQ[Float, Meter %^ 2](9),
        3D.withUnit[Meter].pow[2].isValidQ[Double, Meter %^ 2](9)
      )
    }

    test("implement pow miscellaneous") {
      assert(
        5D.withUnit[Meter %/ Second].pow[0].isValidQ[Double, Unitless](1, tolerant = false),
        7.withUnit[Meter].pow[1].isValidQ[Int, Meter](7, tolerant = false),
        Rational(1, 11).withUnit[Second].pow[-1].isValidQ[Rational, Second %^ -1](11)
      )
    }

    test("implement <") {
      assert(
        (1.withUnit[Meter] < 2.withUnit[Meter]) == (true),
        (1.withUnit[Meter] < 1.withUnit[Meter]) == (false),
        (2.withUnit[Meter] < 1.withUnit[Meter]) == (false),

        (1D.withUnit[Meter] < 2D.withUnit[Meter]) == (true),
        (1D.withUnit[Meter] < 1D.withUnit[Meter]) == (false),
        (2D.withUnit[Meter] < 1D.withUnit[Meter]) == (false),

        (1.withUnit[Yard] < 6.withUnit[Foot]) == (true),
        (1.withUnit[Yard] < 4.withUnit[Foot]) == (false),
        (1.withUnit[Yard] < 3.withUnit[Foot]) == (false),
        (1.withUnit[Yard] < 2.withUnit[Foot]) == (false),

        (1f.withUnit[Yard] < 4f.withUnit[Foot]) == (true),
        (1f.withUnit[Yard] < 3f.withUnit[Foot]) == (false),
        (1f.withUnit[Yard] < 2f.withUnit[Foot]) == (false)
      )
    }

    test("implement >") {
      assert(
        (1.withUnit[Meter] > 2.withUnit[Meter]) == (false),
        (1.withUnit[Meter] > 1.withUnit[Meter]) == (false),
        (2.withUnit[Meter] > 1.withUnit[Meter]) == (true),

        (1D.withUnit[Meter] > 2D.withUnit[Meter]) == (false),
        (1D.withUnit[Meter] > 1D.withUnit[Meter]) == (false),
        (2D.withUnit[Meter] > 1D.withUnit[Meter]) == (true),

        (1.withUnit[Yard] > 6.withUnit[Foot]) == (false),
        (1.withUnit[Yard] > 4.withUnit[Foot]) == (false),
        (1.withUnit[Yard] > 3.withUnit[Foot]) == (false),
        (1.withUnit[Yard] > 2.withUnit[Foot]) == (true),

        (1f.withUnit[Yard] > 4f.withUnit[Foot]) == (false),
        (1f.withUnit[Yard] > 3f.withUnit[Foot]) == (false),
        (1f.withUnit[Yard] > 2f.withUnit[Foot]) == (true)
      )
    }

    test("implement <=") {
      assert(
        (1.withUnit[Meter] <= 2.withUnit[Meter]) == (true),
        (1.withUnit[Meter] <= 1.withUnit[Meter]) == (true),
        (2.withUnit[Meter] <= 1.withUnit[Meter]) == (false),

        (1D.withUnit[Meter] <= 2D.withUnit[Meter]) == (true),
        (1D.withUnit[Meter] <= 1D.withUnit[Meter]) == (true),
        (2D.withUnit[Meter] <= 1D.withUnit[Meter]) == (false),

        (1.withUnit[Yard] <= 6.withUnit[Foot]) == (true),
        (1.withUnit[Yard] <= 4.withUnit[Foot]) == (true),
        (1.withUnit[Yard] <= 3.withUnit[Foot]) == (true),
        (1.withUnit[Yard] <= 2.withUnit[Foot]) == (false),

        (1f.withUnit[Yard] <= 4f.withUnit[Foot]) == (true),
        (1f.withUnit[Yard] <= 3f.withUnit[Foot]) == (true),
        (1f.withUnit[Yard] <= 2f.withUnit[Foot]) == (false)
      )
    }

    test("implement >=") {
      assert(
        (1.withUnit[Meter] >= 2.withUnit[Meter]) == (false),
        (1.withUnit[Meter] >= 1.withUnit[Meter]) == (true),
        (2.withUnit[Meter] >= 1.withUnit[Meter]) == (true),

        (1D.withUnit[Meter] >= 2D.withUnit[Meter]) == (false),
        (1D.withUnit[Meter] >= 1D.withUnit[Meter]) == (true),
        (2D.withUnit[Meter] >= 1D.withUnit[Meter]) == (true),

        (1.withUnit[Yard] >= 6.withUnit[Foot]) == (false),
        (1.withUnit[Yard] >= 4.withUnit[Foot]) == (true),
        (1.withUnit[Yard] >= 3.withUnit[Foot]) == (true),
        (1.withUnit[Yard] >= 2.withUnit[Foot]) == (true),

        (1f.withUnit[Yard] >= 4f.withUnit[Foot]) == (false),
        (1f.withUnit[Yard] >= 3f.withUnit[Foot]) == (true),
        (1f.withUnit[Yard] >= 2f.withUnit[Foot]) == (true)
      )
    }

    test("implement ===") {
      assert(
        (1.withUnit[Meter] === 2.withUnit[Meter]) == (false),
        (1.withUnit[Meter] === 1.withUnit[Meter]) == (true),
        (2.withUnit[Meter] === 1.withUnit[Meter]) == (false),

        (1D.withUnit[Meter] === 2D.withUnit[Meter]) == (false),
        (1D.withUnit[Meter] === 1D.withUnit[Meter]) == (true),
        (2D.withUnit[Meter] === 1D.withUnit[Meter]) == (false),

        (1.withUnit[Yard] === 6.withUnit[Foot]) == (false),
        (1.withUnit[Yard] === 4.withUnit[Foot]) == (true),
        (1.withUnit[Yard] === 3.withUnit[Foot]) == (true),
        (1.withUnit[Yard] === 2.withUnit[Foot]) == (false),

        (1f.withUnit[Yard] === 4f.withUnit[Foot]) == (false),
        (1f.withUnit[Yard] === 3f.withUnit[Foot]) == (true),
        (1f.withUnit[Yard] === 2f.withUnit[Foot]) == (false)
      )
    }

    test("implement =!=") {
      assert(
        (1.withUnit[Meter] =!= 2.withUnit[Meter]) == (true),
        (1.withUnit[Meter] =!= 1.withUnit[Meter]) == (false),
        (2.withUnit[Meter] =!= 1.withUnit[Meter]) == (true),

        (1D.withUnit[Meter] =!= 2D.withUnit[Meter]) == (true),
        (1D.withUnit[Meter] =!= 1D.withUnit[Meter]) == (false),
        (2D.withUnit[Meter] =!= 1D.withUnit[Meter]) == (true),

        (1.withUnit[Yard] =!= 6.withUnit[Foot]) == (true),
        (1.withUnit[Yard] =!= 4.withUnit[Foot]) == (false),
        (1.withUnit[Yard] =!= 3.withUnit[Foot]) == (false),
        (1.withUnit[Yard] =!= 2.withUnit[Foot]) == (true),

        (1f.withUnit[Yard] =!= 4f.withUnit[Foot]) == (true),
        (1f.withUnit[Yard] =!= 3f.withUnit[Foot]) == (false),
        (1f.withUnit[Yard] =!= 2f.withUnit[Foot]) == (true)
      )
    }

    test("implement show") {
      assert(
        1.withUnit[Meter].show == ("1 m"),
        1.withUnit[Kilo %* Meter].show == ("1 km"),
        (1.5.withUnit[Meter] / 1.0.withUnit[Second]).show == ("1.5 m/s"),
        1.0.withUnit[Second].pow[-1].show == ("1.0 s^(-1)"),
        1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].show == ("1 (acre ft)/(m s)"),
        1.withUnit[Meter %/ (Second %^ 2)].show == ("1 m/s^2")
      )
    }

    test("implement showFull") {
      assert(
        1.withUnit[Meter].showFull == ("1 meter"),
        1.withUnit[Kilo %* Meter].showFull == ("1 kilometer"),
        (1.5.withUnit[Meter] / 1.0.withUnit[Second]).showFull == ("1.5 meter/second"),
        1.0.withUnit[Second].pow[-1].showFull == ("1.0 second^(-1)"),
        1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].showFull == ("1 (acre foot)/(meter second)"),
        1.withUnit[Meter %/ (Second %^ 2)].showFull == ("1 meter/second^2")
      )
    }

    test("implement showUnit") {
      assert(
        1.withUnit[Meter].showUnit == ("m"),
        1.withUnit[Kilo %* Meter].showUnit == ("km"),
        (1.5.withUnit[Meter] / 1.0.withUnit[Second]).showUnit == ("m/s"),
        1.0.withUnit[Second].pow[-1].showUnit == ("s^(-1)"),
        1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].showUnit == ("(acre ft)/(m s)"),
        1.withUnit[Meter %/ (Second %^ 2)].showUnit == ("m/s^2")
      )
    }

    test("implement showUnitFull") {
      assert(
        1.withUnit[Meter].showUnitFull == ("meter"),
        1.withUnit[Kilo %* Meter].showUnitFull == ("kilometer"),
        (1.5.withUnit[Meter] / 1.0.withUnit[Second]).showUnitFull == ("meter/second"),
        1.0.withUnit[Second].pow[-1].showUnitFull == ("second^(-1)"),
        1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].showUnitFull == ("(acre foot)/(meter second)"),
        1.withUnit[Meter %/ (Second %^ 2)].showUnitFull == ("meter/second^2")
      )
    }

    test("implement coefficient companion method") {
      assert(
        Quantity.coefficient[Yard, Meter] == (Rational(9144, 10000)),
        Quantity.coefficient[Mile %/ Hour, Meter %/ Second] == (Rational(1397, 3125)),
        Quantity.coefficient[(Kilo %* Meter) %/ (Second %^ 2), Mile %/ (Minute %^ 2)] == (
          Rational(3125000, 1397))
      )

      compileError("Quantity.coefficient[Mile %/ Hour, Meter %/ Kilogram]")
      compileError("Quantity.coefficient[(Kilo %* Meter) %/ (Second %^ 2), Mile %/ (Ampere %^ 2)]")
    }

    test("implement showUnit companion method") {
      assert(
        Quantity.showUnit[Meter] == ("m"),
        Quantity.showUnit[Kilo %* Meter] == ("km"),
        Quantity.showUnit[Meter %/ Second] == ("m/s")
      )
    }

    test("implement showUnitFull companion method") {
      assert(
        Quantity.showUnitFull[Meter] == ("meter"),
        Quantity.showUnitFull[Kilo %* Meter] == ("kilometer"),
        Quantity.showUnitFull[Meter %/ Second] == ("meter/second")
      )
    }

    test("implement implicit conversion between convertable units") {
      assert((1D.withUnit[Yard] :Quantity[Double, Foot]).isValidQ[Double, Foot](3))

      val q: Quantity[Double, Mile %/ Hour] = 1D.withUnit[Kilo %* Meter %/ Second]
      assert(q.isValidQ[Double, Mile %/ Hour](2236.936292))

      def f(a: Double WithUnit (Meter %/ (Second %^ 2))) = a
      assert(f(32D.withUnit[Foot %/ (Second %^ 2)]).isValidQ[Double, Meter %/ (Second %^ 2)](9.7536))
    }

    test("be serializable") {
      import coulomb.scalatest.serde._
      val qs = Quantity[Int, Meter %/ Second](10)
      val qd = roundTripSerDe(qs)
      assert(qd.isValidQ[Int, Meter %/ Second](10))
      assert(qd === qs)
    }

    test("support semigroup multiply") {
      case class AG(value: Int)
      implicit val sgsg: MultiplicativeSemigroup[AG] = new MultiplicativeSemigroup[AG] {
        def times(x: AG, y: AG): AG = AG(x.value * y.value)
      }
      val q = (AG(2).withUnit[Meter]) * (AG(3).withUnit[Meter])
      assert(q.show == "AG(6) m^2")
    }

    test("support convertable semigroup multiply") {
      import coulomb.unitops._
      case class AG(value: Int)
      implicit val sgsg: MultiplicativeSemigroup[AG] = new MultiplicativeSemigroup[AG] {
        def times(x: AG, y: AG): AG = AG(x.value * y.value)
      }
      implicit def custom[U1, U2]: UnitConverterPolicy[Int, U1, AG, U2] =
        new UnitConverterPolicy[Int, U1, AG, U2] {
          def convert(v: Int, cu: ConvertableUnits[U1, U2]): AG = AG((cu.coef * v).toInt)
        }
      val q = (AG(2).withUnit[Meter]) * (3.withUnit[Meter])
      assert(q.show == "AG(6) m^2")
    }

    test("support group divide") {
      case class AG(value: Int)
      implicit val sgsg: MultiplicativeGroup[AG] = new MultiplicativeGroup[AG] {
        def times(x: AG, y: AG): AG = AG(x.value * y.value)
        def div(x: AG, y: AG): AG = AG(x.value / y.value)
        def one: AG = AG(1)
      }
      val q = (AG(6).withUnit[Meter]) / (AG(3).withUnit[Second])
      assert(q.show == "AG(2) m/s")
    }

    test("support convertable group divide") {
      import coulomb.unitops._
      case class AG(value: Int)
      implicit val sgsg: MultiplicativeGroup[AG] = new MultiplicativeGroup[AG] {
        def times(x: AG, y: AG): AG = AG(x.value * y.value)
        def div(x: AG, y: AG): AG = AG(x.value / y.value)
        def one: AG = AG(1)
      }
      implicit def custom[U1, U2]: UnitConverterPolicy[Int, U1, AG, U2] =
        new UnitConverterPolicy[Int, U1, AG, U2] {
          def convert(v: Int, cu: ConvertableUnits[U1, U2]): AG = AG((cu.coef * v).toInt)
        }
      val q = (AG(6).withUnit[Meter]) / (3.withUnit[Second])
      assert(q.show == "AG(2) m/s")
    }

    test("support semigroup power") {
      case class AG(value: Int)
      implicit val sgsg: MultiplicativeSemigroup[AG] = new MultiplicativeSemigroup[AG] {
        def times(x: AG, y: AG): AG = AG(x.value * y.value)
      }
      val q = AG(2).withUnit[Meter].pow[3]
      assert(q.show == "AG(8) m^3")
    }

    test("support semigroup add") {
      import coulomb.unitops._
      case class AG(value: Int)
      implicit val sgsg: AdditiveSemigroup[AG] = new AdditiveSemigroup[AG] {
        def plus(x: AG, y: AG): AG = AG(x.value + y.value)
      }
      implicit def custom[U1, U2]: UnitConverterPolicy[AG, U1, AG, U2] =
        new UnitConverterPolicy[AG, U1, AG, U2] {
          def convert(v: AG, cu: ConvertableUnits[U1, U2]): AG = AG((cu.coef * v.value).toInt)
        }
      val q = (AG(1).withUnit[Meter]) + (AG(2).withUnit[Kilo %* Meter])
      assert(q.show == "AG(2001) m")
    }

    test("support group subtract") {
      import coulomb.unitops._
      case class AG(value: Int)
      implicit val sgsg: AdditiveGroup[AG] = new AdditiveGroup[AG] {
        def plus(x: AG, y: AG): AG = AG(x.value + y.value)
        def negate(x: AG): AG = AG(-x.value)
        def zero: AG = AG(0)
      }
      implicit def custom[U1, U2]: UnitConverterPolicy[AG, U1, AG, U2] =
        new UnitConverterPolicy[AG, U1, AG, U2] {
          def convert(v: AG, cu: ConvertableUnits[U1, U2]): AG = AG((cu.coef * v.value).toInt)
        }
      val q = (AG(2000).withUnit[Meter]) - (AG(1).withUnit[Kilo %* Meter])
      assert(q.show == "AG(1000) m")
    }

    test("support UnitConverterPolicy") {
      import coulomb.unitops._
      implicit def custom[U1, U2]: UnitConverterPolicy[Int, U1, Int, U2] =
        new UnitConverterPolicy[Int, U1, Int, U2] {
          def convert(v: Int, cu: ConvertableUnits[U1, U2]): Int = 777
        }
      val q = 4.withUnit[Kilo %* Meter].toUnit[Meter]
      assert(q.value == 777)
    }

    test("support undeclared base units") {
      import coulomb.policy.undeclaredBaseUnits._
      trait MooUnit
      val q1 = (1.withUnit[MooUnit]) + (1.withUnit[Kilo %* MooUnit])
      assert(q1.show == "1001 MooUnit")
      val q2 = (10.withUnit[Seq[Int]]) / (5.withUnit[Second])
      assert(q2.show == "2 Seq[Int]/s")
    }
  }
}
