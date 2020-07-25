package coulomb.parser

import shapeless.{ ::, HNil }

import spire.std.any._

import utest._

import coulomb._
import coulomb.si.{ Second, Meter }
import coulomb.siprefix.{ Mega, Giga }
import coulomb.time.Minute
import coulomb.info.{ Byte, Bit }
import coulomb.us.Foot

import coulomb.validators.CoulombValidators._

object ConfigIntegration {
  import com.typesafe.config.ConfigFactory
  import coulomb.parser.QuantityParser
  import coulomb.typesafeconfig._

  val confTS = ConfigFactory.parseString("""
    "duration" = "60 second"
    "memory" = "100 gigabyte"
    "bandwidth" = "10 megabyte / second"
  """)

  private val qp = QuantityParser[Byte :: Second :: Giga :: Mega :: HNil]

  val conf = confTS.withQuantityParser(qp)
}

object ComplexTest {
  import coulomb.unitops._, spire.math.Complex, spire.algebra._, spire.std.any._
  implicit def complexPolicy[U1, U2]: UnitConverterPolicy[Complex[Double], U1, Complex[Double], U2] =
     new UnitConverterPolicy[Complex[Double], U1, Complex[Double], U2] {
       def convert(v: Complex[Double], cu: ConvertableUnits[U1, U2]): Complex[Double] = v * cu.coef.toDouble
     }
}

object QuantityParserTests extends TestSuite {
  import coulomb.parser.QuantityParser
  import ConfigIntegration._

  val tests = Tests {
    test("return configured unit quantities") {
      assert(
        conf.getQuantity[Double, Second]("duration").get.isValidQ[Double, Second](60),
        conf.getQuantity[Int, Giga %* Byte]("memory").get.isValidQ[Int, Giga %* Byte](100),
        conf.getQuantity[Float, Mega %* Byte %/ Second]("bandwidth").get.isValidQ[Float, Mega %* Byte %/ Second](10)
      )
    }

    test("return convertable unit quantities") {
      assert(
        conf.getQuantity[Double, Minute]("duration").get.isValidQ[Double, Minute](1),
        conf.getQuantity[Int, Giga %* Bit]("memory").get.isValidQ[Int, Giga %* Bit](800),
        conf.getQuantity[Float, Giga %* Bit %/ Minute]("bandwidth").get.isValidQ[Float, Giga %* Bit %/ Minute](4.8)
      )
    }

    test("fail on incompatible units") {
      val try1 = conf.getQuantity[Double, Meter]("duration")
      assert(try1.isInstanceOf[scala.util.Failure[_]])
    }

    // this is also testing empty prefix-unit list, which exposed a bug
    test("customizing with applyUnitExpr and withImports") {
      import spire.math.Complex, spire.algebra._, spire.std.any._
      val v = Complex[Double](1, 2)
      val qpw = QuantityParser.withImports[Foot :: Meter :: HNil]("coulomb.parser.ComplexTest._")
      val res = qpw.applyUnitExpr[Complex[Double], Foot](v, "meter")
      assert(res.isInstanceOf[scala.util.Success[_]])
      val q = res.toOption.get
      assert(scala.math.abs(q.value.real - 3.28084) < 1e-4)
      assert(scala.math.abs(q.value.imag - 6.56168) < 1e-4)
    }

    test("support ser/de") {
      import coulomb.scalatest.serde._
      val expr = "3.14 gigabyte / second"
      val qp1 = QuantityParser[Byte :: Second :: Giga :: Mega :: HNil]
      val t1 = qp1[Float, Giga %* Byte %/ Second](expr).get
      val qp2 = roundTripSerDe(qp1)
      val t2 = qp2[Float, Giga %* Byte %/ Second](expr).get
      assert(t1 === t2)
      assert(t2.isValidQ[Float, Giga %* Byte %/ Second](3.14))
    }
  }
}
