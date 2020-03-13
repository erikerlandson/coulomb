package coulomb

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

import spire.math._
import spire.algebra._
// pick up the various group/semigroup typeclasses
import spire.std.any._

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

    "1D.withUnit[Mole %/ Liter].toUnit[(Kilo %* Mole) %/ (Meter %^ 3)]" should compile
    "1D.withUnit[Mole %/ Liter].toUnit[(Kilo %* Mole) %/ (Meter %^ 4)]" shouldNot compile
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

  it should "implement +" in {
    val literToCup = 4.22675283773 // Rational(2000000000 / 473176473)

    (100.withUnit[Cup] + 100.withUnit[Liter]) shouldBeQ[Int, Cup](1.0 + literToCup, 100)
    (100L.withUnit[Cup] + 100L.withUnit[Liter]) shouldBeQ[Long, Cup](1.0 + literToCup, 100)

    (1f.withUnit[Cup] + 1f.withUnit[Liter]) shouldBeQ[Float, Cup](1.0 + literToCup)
    (1D.withUnit[Cup] + 1D.withUnit[Liter]) shouldBeQ[Double, Cup](1.0 + literToCup)
  }

  it should "implement + optimization cases" in {
    // numerator only conversion
    (1.withUnit[Cup] + 1.withUnit[Quart]) shouldBeQ[Int, Cup](5, tolerant = false)

    // identity
    (1.withUnit[Cup] + 1.withUnit[Cup]) shouldBeQ[Int, Cup](2, tolerant = false)
    (1D.withUnit[Cup] + 1D.withUnit[Cup]) shouldBeQ[Double, Cup](2.0)
  }

  it should "implement -" in {
    val inchToCentimeter = 2.54 // Rational(127 / 50)

    (1000.withUnit[Centimeter] - 100.withUnit[Inch]) shouldBeQ[Int, Centimeter](10.0 - inchToCentimeter, 100)
    (1000L.withUnit[Centimeter] - 100L.withUnit[Inch]) shouldBeQ[Long, Centimeter](10.0 - inchToCentimeter, 100)

    (10f.withUnit[Centimeter] - 1f.withUnit[Inch]) shouldBeQ[Float, Centimeter](10.0 - inchToCentimeter)
    (10D.withUnit[Centimeter] - 1D.withUnit[Inch]) shouldBeQ[Double, Centimeter](10.0 - inchToCentimeter)
  }

  it should "implement - optimization cases" in {
    // numerator only conversion
    (13.withUnit[Inch] - 1.withUnit[Foot]) shouldBeQ[Int, Inch](1, tolerant = false)

    // identity
    (2.withUnit[Inch] - 1.withUnit[Inch]) shouldBeQ[Int, Inch](1, tolerant = false)
    (2D.withUnit[Inch] - 1D.withUnit[Inch]) shouldBeQ[Double, Inch](1)
  }

  it should "implement *" in {
    (2.withUnit[Acre] * 3.withUnit[Foot]) shouldBeQ[Int, Acre %* Foot](6, tolerant = false)
    (2L.withUnit[Acre] * 3L.withUnit[Foot]) shouldBeQ[Long, Acre %* Foot](6, tolerant = false)

    (2f.withUnit[Acre] * 3f.withUnit[Foot]) shouldBeQ[Float, Acre %* Foot](6)
    (2D.withUnit[Acre] * 3D.withUnit[Foot]) shouldBeQ[Double, Acre %* Foot](6)
  }

  it should "implement * miscellaneous" in {
    (2.withUnit[Meter %/ Second] * 3.withUnit[Second]) shouldBeQ[Int, Meter](6, tolerant = false)
    (2D.withUnit[Mole %/ Liter] * 2D.withUnit[Liter %/ Second] * 2D.withUnit[Second]) shouldBeQ[Double, Mole](8)
  }

  it should "implement /" in {
    (10.withUnit[Meter] / 3.withUnit[Second]) shouldBeQ[Int, Meter %/ Second](3, tolerant = false)
    (10L.withUnit[Meter] / 3L.withUnit[Second]) shouldBeQ[Long, Meter %/ Second](3, tolerant = false)

    (10f.withUnit[Meter] / 3f.withUnit[Second]) shouldBeQ[Float, Meter %/ Second](3.3333)
    (10D.withUnit[Meter] / 3D.withUnit[Second]) shouldBeQ[Double, Meter %/ Second](3.3333)
  }

  it should "implement pow" in {
    3.withUnit[Meter].pow[2] shouldBeQ[Int, Meter %^ 2](9, tolerant = false)
    3L.withUnit[Meter].pow[2] shouldBeQ[Long, Meter %^ 2](9, tolerant = false)

    3f.withUnit[Meter].pow[2] shouldBeQ[Float, Meter %^ 2](9)
    3D.withUnit[Meter].pow[2] shouldBeQ[Double, Meter %^ 2](9)
  }

  it should "implement pow miscellaneous" in {
    5D.withUnit[Meter %/ Second].pow[0] shouldBeQ[Double, Unitless](1, tolerant = false)
    7.withUnit[Meter].pow[1] shouldBeQ[Int, Meter](7, tolerant = false)
    Rational(1, 11).withUnit[Second].pow[-1] shouldBeQ[Rational, Second %^ -1](11)
  }

  it should "implement <" in {
    (1.withUnit[Meter] < 2.withUnit[Meter]) should be (true)
    (1.withUnit[Meter] < 1.withUnit[Meter]) should be (false)
    (2.withUnit[Meter] < 1.withUnit[Meter]) should be (false)

    (1D.withUnit[Meter] < 2D.withUnit[Meter]) should be (true)
    (1D.withUnit[Meter] < 1D.withUnit[Meter]) should be (false)
    (2D.withUnit[Meter] < 1D.withUnit[Meter]) should be (false)

    (1.withUnit[Yard] < 6.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] < 4.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] < 3.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] < 2.withUnit[Foot]) should be (false)

    (1f.withUnit[Yard] < 4f.withUnit[Foot]) should be (true)
    (1f.withUnit[Yard] < 3f.withUnit[Foot]) should be (false)
    (1f.withUnit[Yard] < 2f.withUnit[Foot]) should be (false)
  }

  it should "implement >" in {
    (1.withUnit[Meter] > 2.withUnit[Meter]) should be (false)
    (1.withUnit[Meter] > 1.withUnit[Meter]) should be (false)
    (2.withUnit[Meter] > 1.withUnit[Meter]) should be (true)

    (1D.withUnit[Meter] > 2D.withUnit[Meter]) should be (false)
    (1D.withUnit[Meter] > 1D.withUnit[Meter]) should be (false)
    (2D.withUnit[Meter] > 1D.withUnit[Meter]) should be (true)

    (1.withUnit[Yard] > 6.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] > 4.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] > 3.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] > 2.withUnit[Foot]) should be (true)

    (1f.withUnit[Yard] > 4f.withUnit[Foot]) should be (false)
    (1f.withUnit[Yard] > 3f.withUnit[Foot]) should be (false)
    (1f.withUnit[Yard] > 2f.withUnit[Foot]) should be (true)
  }

  it should "implement <=" in {
    (1.withUnit[Meter] <= 2.withUnit[Meter]) should be (true)
    (1.withUnit[Meter] <= 1.withUnit[Meter]) should be (true)
    (2.withUnit[Meter] <= 1.withUnit[Meter]) should be (false)

    (1D.withUnit[Meter] <= 2D.withUnit[Meter]) should be (true)
    (1D.withUnit[Meter] <= 1D.withUnit[Meter]) should be (true)
    (2D.withUnit[Meter] <= 1D.withUnit[Meter]) should be (false)

    (1.withUnit[Yard] <= 6.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] <= 4.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] <= 3.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] <= 2.withUnit[Foot]) should be (false)

    (1f.withUnit[Yard] <= 4f.withUnit[Foot]) should be (true)
    (1f.withUnit[Yard] <= 3f.withUnit[Foot]) should be (true)
    (1f.withUnit[Yard] <= 2f.withUnit[Foot]) should be (false)
  }

  it should "implement >=" in {
    (1.withUnit[Meter] >= 2.withUnit[Meter]) should be (false)
    (1.withUnit[Meter] >= 1.withUnit[Meter]) should be (true)
    (2.withUnit[Meter] >= 1.withUnit[Meter]) should be (true)

    (1D.withUnit[Meter] >= 2D.withUnit[Meter]) should be (false)
    (1D.withUnit[Meter] >= 1D.withUnit[Meter]) should be (true)
    (2D.withUnit[Meter] >= 1D.withUnit[Meter]) should be (true)

    (1.withUnit[Yard] >= 6.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] >= 4.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] >= 3.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] >= 2.withUnit[Foot]) should be (true)

    (1f.withUnit[Yard] >= 4f.withUnit[Foot]) should be (false)
    (1f.withUnit[Yard] >= 3f.withUnit[Foot]) should be (true)
    (1f.withUnit[Yard] >= 2f.withUnit[Foot]) should be (true)
  }

  it should "implement ===" in {
    (1.withUnit[Meter] === 2.withUnit[Meter]) should be (false)
    (1.withUnit[Meter] === 1.withUnit[Meter]) should be (true)
    (2.withUnit[Meter] === 1.withUnit[Meter]) should be (false)

    (1D.withUnit[Meter] === 2D.withUnit[Meter]) should be (false)
    (1D.withUnit[Meter] === 1D.withUnit[Meter]) should be (true)
    (2D.withUnit[Meter] === 1D.withUnit[Meter]) should be (false)

    (1.withUnit[Yard] === 6.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] === 4.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] === 3.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] === 2.withUnit[Foot]) should be (false)

    (1f.withUnit[Yard] === 4f.withUnit[Foot]) should be (false)
    (1f.withUnit[Yard] === 3f.withUnit[Foot]) should be (true)
    (1f.withUnit[Yard] === 2f.withUnit[Foot]) should be (false)
  }

  it should "implement =!=" in {
    (1.withUnit[Meter] =!= 2.withUnit[Meter]) should be (true)
    (1.withUnit[Meter] =!= 1.withUnit[Meter]) should be (false)
    (2.withUnit[Meter] =!= 1.withUnit[Meter]) should be (true)

    (1D.withUnit[Meter] =!= 2D.withUnit[Meter]) should be (true)
    (1D.withUnit[Meter] =!= 1D.withUnit[Meter]) should be (false)
    (2D.withUnit[Meter] =!= 1D.withUnit[Meter]) should be (true)

    (1.withUnit[Yard] =!= 6.withUnit[Foot]) should be (true)
    (1.withUnit[Yard] =!= 4.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] =!= 3.withUnit[Foot]) should be (false)
    (1.withUnit[Yard] =!= 2.withUnit[Foot]) should be (true)

    (1f.withUnit[Yard] =!= 4f.withUnit[Foot]) should be (true)
    (1f.withUnit[Yard] =!= 3f.withUnit[Foot]) should be (false)
    (1f.withUnit[Yard] =!= 2f.withUnit[Foot]) should be (true)
  }

  it should "implement show" in {
    1.withUnit[Meter].show should be ("1 m")
    1.withUnit[Kilo %* Meter].show should be ("1 km")
    (1.5.withUnit[Meter] / 1.0.withUnit[Second]).show should be ("1.5 m/s")
    1.0.withUnit[Second].pow[-1].show should be ("1.0 s^(-1)")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].show should be ("1 (acre ft)/(m s)")
    1.withUnit[Meter %/ (Second %^ 2)].show should be ("1 m/s^2")
  }

  it should "implement showFull" in {
    1.withUnit[Meter].showFull should be ("1 meter")
    1.withUnit[Kilo %* Meter].showFull should be ("1 kilometer")
    (1.5.withUnit[Meter] / 1.0.withUnit[Second]).showFull should be ("1.5 meter/second")
    1.0.withUnit[Second].pow[-1].showFull should be ("1.0 second^(-1)")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].showFull should be ("1 (acre foot)/(meter second)")
    1.withUnit[Meter %/ (Second %^ 2)].showFull should be ("1 meter/second^2")
  }

  it should "implement showUnit" in {
    1.withUnit[Meter].showUnit should be ("m")
    1.withUnit[Kilo %* Meter].showUnit should be ("km")
    (1.5.withUnit[Meter] / 1.0.withUnit[Second]).showUnit should be ("m/s")
    1.0.withUnit[Second].pow[-1].showUnit should be ("s^(-1)")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].showUnit should be ("(acre ft)/(m s)")
    1.withUnit[Meter %/ (Second %^ 2)].showUnit should be ("m/s^2")
  }

  it should "implement showUnitFull" in {
    1.withUnit[Meter].showUnitFull should be ("meter")
    1.withUnit[Kilo %* Meter].showUnitFull should be ("kilometer")
    (1.5.withUnit[Meter] / 1.0.withUnit[Second]).showUnitFull should be ("meter/second")
    1.0.withUnit[Second].pow[-1].showUnitFull should be ("second^(-1)")
    1.withUnit[(Acre %* Foot) %/ (Meter %* Second)].showUnitFull should be ("(acre foot)/(meter second)")
    1.withUnit[Meter %/ (Second %^ 2)].showUnitFull should be ("meter/second^2")
  }

  it should "implement coefficient companion method" in {
    Quantity.coefficient[Yard, Meter] should be (Rational(9144, 10000))
    Quantity.coefficient[Mile %/ Hour, Meter %/ Second] should be (Rational(1397, 3125))
    Quantity.coefficient[(Kilo %* Meter) %/ (Second %^ 2), Mile %/ (Minute %^ 2)] should be (
      Rational(3125000, 1397))

    "Quantity.coefficient[Mile %/ Hour, Meter %/ Kilogram]" shouldNot compile
    "Quantity.coefficient[(Kilo %* Meter) %/ (Second %^ 2), Mile %/ (Ampere %^ 2)]" shouldNot compile
  }

  it should "implement showUnit companion method" in {
    Quantity.showUnit[Meter] should be ("m")
    Quantity.showUnit[Kilo %* Meter] should be ("km")
    Quantity.showUnit[Meter %/ Second] should be ("m/s")
  }

  it should "implement showUnitFull companion method" in {
    Quantity.showUnitFull[Meter] should be ("meter")
    Quantity.showUnitFull[Kilo %* Meter] should be ("kilometer")
    Quantity.showUnitFull[Meter %/ Second] should be ("meter/second")
  }

  it should "implement implicit conversion between convertable units" in {
    (1D.withUnit[Yard] :Quantity[Double, Foot]) shouldBeQ[Double, Foot](3)

    val q: Quantity[Double, Mile %/ Hour] = 1D.withUnit[Kilo %* Meter %/ Second]
    q shouldBeQ[Double, Mile %/ Hour](2236.936292)

    def f(a: Double WithUnit (Meter %/ (Second %^ 2))) = a
    f(32D.withUnit[Foot %/ (Second %^ 2)]) shouldBeQ[Double, Meter %/ (Second %^ 2)](9.7536)
  }

  it should "be serializable" in {
    import coulomb.scalatest.serde._
    val qs = Quantity[Int, Meter %/ Second](10)
    val qd = roundTripSerDe(qs)
    qd.shouldBeQ[Int, Meter %/ Second](10)
    (qd === qs) shouldBe (true)
  }

  it should "support semigroup multiply" in {
    case class AG(value: Int)
    implicit val sgsg: MultiplicativeSemigroup[AG] = new MultiplicativeSemigroup[AG] {
      def times(x: AG, y: AG): AG = AG(x.value * y.value)
    }
    val q = (AG(2).withUnit[Meter]) * (AG(3).withUnit[Meter])
    assert(q.show == "AG(6) m^2")
  }

  it should "support convertable semigroup multiply" in {
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

  it should "support group divide" in {
    case class AG(value: Int)
    implicit val sgsg: MultiplicativeGroup[AG] = new MultiplicativeGroup[AG] {
      def times(x: AG, y: AG): AG = AG(x.value * y.value)
      def div(x: AG, y: AG): AG = AG(x.value / y.value)
      def one: AG = AG(1)
    }
    val q = (AG(6).withUnit[Meter]) / (AG(3).withUnit[Second])
    assert(q.show == "AG(2) m/s")
  }

  it should "support convertable group divide" in {
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

  it should "support semigroup power" in {
    case class AG(value: Int)
    implicit val sgsg: MultiplicativeSemigroup[AG] = new MultiplicativeSemigroup[AG] {
      def times(x: AG, y: AG): AG = AG(x.value * y.value)
    }
    val q = AG(2).withUnit[Meter].pow[3]
    assert(q.show == "AG(8) m^3")
  }

  it should "support semigroup add" in {
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

  it should "support group subtract" in {
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

  it should "support UnitConverterPolicy" in {
    import coulomb.unitops._
    implicit def custom[U1, U2]: UnitConverterPolicy[Int, U1, Int, U2] =
      new UnitConverterPolicy[Int, U1, Int, U2] {
        def convert(v: Int, cu: ConvertableUnits[U1, U2]): Int = 777
      }
    val q = 4.withUnit[Kilo %* Meter].toUnit[Meter]
    assert(q.value == 777)
  }
}
