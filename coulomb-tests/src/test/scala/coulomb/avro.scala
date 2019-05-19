package coulomb.avro

import org.scalatest._
import org.scalactic._
import org.scalatest.matchers.{Matcher, MatchResult}
import TripleEquals._

import shapeless._

import spire.math._

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

import org.apache.avro._

class AvroIntegrationSpec extends FlatSpec with Matchers {
  it should "integrate with an avro schema" in {
    val schema = new Schema.Parser().parse(new java.io.File("coulomb-tests/src/test/scala/coulomb/test1.avsc"))
    println(s"schema= $schema")
  }
}
