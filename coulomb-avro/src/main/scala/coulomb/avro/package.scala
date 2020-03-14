/*
Copyright 2017-2020 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package coulomb

import scala.language.implicitConversions

import scala.reflect.runtime.universe.TypeTag

import org.apache.avro.generic.GenericData
import org.apache.avro.Schema

import coulomb.parser.unitops.UnitTypeString
import coulomb.unitops.UnitString
import coulomb.parser.QuantityParser

/** Integrations for Apache Avro schema */
package object avro {
  /** Adds enhancement methods for coulomb-avro integration */
  implicit class EnhanceGenericRecord(rec: GenericData.Record) {
    private val schema = rec.getSchema

    /**
     * get a field's value in a generic record as a unit Quantity
     * @tparam N the numeric type to use for the Quantity
     * @tparam U the unit type
     * @param qp the QuantityParser to use
     * @param field the name of the field to get. This field must have an additional
     * metadata property "unit", which contains a unit-expression parseable by the given QuantityParser 'qp'
     */
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

    /** Similar to getQuantity[N, U](qp)(field), but the QuantityParser is resolved implicitly */
    def getQuantity[N :spire.math.Numeric, U :UnitString :UnitTypeString](field: String)(implicit qp: QuantityParser): Quantity[N, U] =
      getQuantity[N, U](qp)(field)

    /**
     * put a field's value into a generic record, given a unit Quantity
     * @tparam N the numeric type of the Quantity
     * @tparam U the unit type
     * @param qp the QuantityParser to use
     * @param field the name of the field to put. This field must have an additional
     * metadata property "unit", which contains a unit-expression parseable by the given QuantityParser 'qp'
     */
    def putQuantity[N, U](qp: QuantityParser)(field: String, q: Quantity[N, U])(implicit
        n: spire.math.Numeric[N],
        ut: UnitTypeString[U],
        us: UnitString[U]): Unit = {
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
      val dbv = n.toType[Double](q.value) / coef
      fld.schema.getType match {
        case tpe if (tpe == Schema.Type.FLOAT) => rec.put(field, dbv.toFloat)
        case tpe if (tpe == Schema.Type.DOUBLE) => rec.put(field, dbv)
        case tpe if (tpe == Schema.Type.INT) => rec.put(field, dbv.toInt)
        case tpe if (tpe == Schema.Type.LONG) => rec.put(field, dbv.toLong)
        case _ => throw new Exception(s"non-numeric field type: ${fld.schema.getType}")
      }
    }

    /** Similar to putQuantity[N, U](qp)(field, q), but the QuantityParser is resolved implicitly */
    def putQuantity[N :spire.math.Numeric, U :UnitString :UnitTypeString](field: String, q: Quantity[N, U])(implicit qp: QuantityParser): Unit =
      putQuantity(qp)(field, q)
  }
}
