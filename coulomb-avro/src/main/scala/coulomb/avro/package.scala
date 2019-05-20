package coulomb

import scala.language.implicitConversions

import scala.reflect.runtime.universe.TypeTag

import org.apache.avro.generic.GenericData
import org.apache.avro.Schema

import coulomb.parser.unitops.UnitTypeString
import coulomb.unitops.UnitString
import coulomb.parser.QuantityParser

package object avro {
  implicit class EnhanceGenericRecord(rec: GenericData.Record) {
    private val schema = rec.getSchema

    def getQuantity[N, U](qp: QuantityParser)(field: String)(implicit
        n: spire.math.Numeric[N],
        ut: UnitTypeString[U],
        us: UnitString[U]): Quantity[N, U] = {
      val fld = schema.getField(field)
      val uq = us.full
      val cq = fld.getObjectProp(uq)
      val coef = if (cq != null) cq.asInstanceOf[Double] else {
        val uprop = fld.getObjectProp("unit")
        if (uprop == null) throw new Exception(s"""field $field is missing "unit" metadata property""")
        val c = try {
          qp.coefficient[U](uprop.asInstanceOf[String]).get.toDouble
        } catch {
          case _: Throwable => throw new Exception(s"""unit metadata "${uprop}" incompatible with "${ut.expr}"""")
        }
        fld.addProp(uq, c)
        c
      }
      val raw = rec.get(field)
      if (raw == null) throw new Exception(s"null record field: $field")
      val dbv = fld.schema.getType match {
        case tpe if (tpe == Schema.Type.FLOAT) => raw.asInstanceOf[Float].toDouble
        case tpe if (tpe == Schema.Type.DOUBLE) => raw.asInstanceOf[Double]
        case tpe if (tpe == Schema.Type.INT) => raw.asInstanceOf[Int].toDouble
        case tpe if (tpe == Schema.Type.LONG) => raw.asInstanceOf[Long].toDouble
        case _ => throw new Exception(s"non-numeric field type: ${fld.schema.getType}")
      }
      Quantity[N, U](n.fromType[Double](coef * dbv))
    }

    def getQuantity[N :spire.math.Numeric, U :UnitString :UnitTypeString](field: String)(implicit qp: QuantityParser): Quantity[N, U] =
      getQuantity[N, U](qp)(field)
  }
}
