package coulomb.parser

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

import shapeless._

import spire.math._
import spire.std.any._

import singleton.ops._

import coulomb._
import coulomb.unitops._
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

class QuantityParserSpec extends FlatSpec with Matchers {
  import coulomb.parser.QuantityParser
  import ConfigIntegration._

  it should "return configured unit quantities" in {
    conf.getQuantity[Double, Second]("duration").get.shouldBeQ[Double, Second](60)
    conf.getQuantity[Int, Giga %* Byte]("memory").get.shouldBeQ[Int, Giga %* Byte](100)
    conf.getQuantity[Float, Mega %* Byte %/ Second]("bandwidth").get.shouldBeQ[Float, Mega %* Byte %/ Second](10)
  }

  it should "return convertable unit quantities" in {
    conf.getQuantity[Double, Minute]("duration").get.shouldBeQ[Double, Minute](1)
    conf.getQuantity[Int, Giga %* Bit]("memory").get.shouldBeQ[Int, Giga %* Bit](800)
    conf.getQuantity[Float, Giga %* Bit %/ Minute]("bandwidth").get.shouldBeQ[Float, Giga %* Bit %/ Minute](4.8)
  }

  it should "fail on incompatible units" in {
    val try1 = conf.getQuantity[Double, Meter]("duration")
    try1.isInstanceOf[scala.util.Failure[_]] should be (true)
  }

  it should "support ser/de" in {
    import coulomb.scalatest.serde._
    val expr = "3.14 gigabyte / second"
    val qp1 = QuantityParser[Byte :: Second :: Giga :: Mega :: HNil]
    val t1 = qp1[Float, Giga %* Byte %/ Second](expr).get
    val qp2 = roundTripSerDe(qp1)
    val t2 = qp2[Float, Giga %* Byte %/ Second](expr).get
    (t1 === t2) shouldBe (true)
    t2.shouldBeQ[Float, Giga %* Byte %/ Second](3.14)
  }
}
