package coulomb

import scala.language.implicitConversions

import scala.reflect.runtime.universe.TypeTag

import org.apache.avro.generic.GenericData

import coulomb.parser.unitops.UnitTypeString
import coulomb.parser.QuantityParser

package object avro {
  implicit class EnhanceGenericRecord(rec: GenericData.Record) {
    val schema = rec.getSchema
    def getQuantity[N :TypeTag, U :UnitTypeString](qp: QuantityParser)(field: String): Quantity[N, U] = {
      val unit = schema.getField(field).getObjectProp("unit")
      val raw = rec.get(field)
      qp[N, U](s"${raw} ${unit}").get
    }
    def getQuantity[N :TypeTag, U :UnitTypeString](field: String)(implicit qp: QuantityParser): Quantity[N, U] =
      getQuantity(qp)(field)
  }
}
