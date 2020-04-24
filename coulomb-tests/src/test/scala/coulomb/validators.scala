package coulomb.validators

import scala.reflect.runtime.universe._
import scala.language.implicitConversions

import spire.math.ConvertableFrom

import eu.timepit.refined.api.Refined

import coulomb._
import coulomb.temp._
import coulomb.offset.OffsetQuantity

object CoulombValidators {

  val epsilon = 1e-4

  trait Castable[V, C] {
    def cast(v: V): C
  }

  implicit def convertableToDouble[V](implicit
      cf: ConvertableFrom[V]): Castable[V, Double] = new Castable[V, Double] {
    def cast(v: V): Double = cf.toType[Double](v)
  }
  implicit def refinedToDouble[V, P](implicit
      cf: ConvertableFrom[V]): Castable[Refined[V, P], Double] = new Castable[Refined[V, P], Double] {
    def cast(v: Refined[V, P]): Double = cf.toType[Double](v.value)
  }

  implicit class WithQuantityShouldMethods[V, U](q: Quantity[V, U])(implicit
      ttV: WeakTypeTag[V],
      ttU: WeakTypeTag[U],
      cdV: Castable[V, Double]) {
    def isValidQ[VR, UR](tval: Double, f: Int = 1, tolerant: Boolean = true)(implicit
        ttVR: WeakTypeTag[VR],
        ttUR: WeakTypeTag[UR]): Boolean = {
      (weakTypeOf[V], weakTypeOf[U]) match {
        case (tn, _) if (!(tn =:= weakTypeOf[VR])) =>
          throw new Exception(s"Value type $tn did not match target ${weakTypeOf[VR]}")
          false
        case (_, tu) if (!(tu =:= weakTypeOf[UR])) =>
          throw new Exception(s"Unit type $tu did not match target ${weakTypeOf[UR]}")
          false
        case _ => {
          val tv = if (f == 1) tval else ((tval * f.toDouble).toInt).toDouble
          val qv = cdV.cast(q.value)
          val eq = if (tolerant) (math.abs(qv - tv) <= epsilon) else (qv == tv)
          if (!eq) throw new Exception(s"Value ${q.value} did not match target $tv")
          true
        }
      }
    }
  }

  implicit class WithTemperatureShouldMethods[V, U](t: Temperature[V, U])(implicit
      ttV: WeakTypeTag[V],
      ttU: WeakTypeTag[U],
      cdV: Castable[V, Double]) {
    def isValidT[VR, UR](tval: Double, tolerant: Boolean = true)(implicit
        ttVR: WeakTypeTag[VR],
        ttUR: WeakTypeTag[UR]): Boolean = {
      (weakTypeOf[V], weakTypeOf[U]) match {
        case (tn, _) if (!(tn =:= weakTypeOf[VR])) =>
          throw new Exception(s"Value type $tn did not match target ${weakTypeOf[VR]}")
          false
        case (_, tu) if (!(tu =:= weakTypeOf[UR])) =>
          throw new Exception(s"Unit type $tu did not match target ${weakTypeOf[UR]}")
          false
        case _ => {
          val tv = cdV.cast(t.value)
          val eq = if (tolerant) (math.abs(tv - tval) <= epsilon) else (tv == tval)
          if (!eq) throw new Exception(s"Value ${t.value} did not match target $tval")
          true
        }
      }
    }
  }

  implicit class WithOffsetQuantityValidateMethods[V, U](q: OffsetQuantity[V, U])(implicit
      ttV: WeakTypeTag[V],
      ttU: WeakTypeTag[U],
      cdV: Castable[V, Double]) {
    def isValidOQ[VR, UR](tval: Double, tolerant: Boolean = true)(implicit
        ttVR: WeakTypeTag[VR],
        ttUR: WeakTypeTag[UR]): Boolean = {
      (weakTypeOf[V], weakTypeOf[U]) match {
        case (tn, _) if (!(tn =:= weakTypeOf[VR])) =>
          throw new Exception(s"Value type $tn did not match target ${weakTypeOf[VR]}")
          false
        case (_, tu) if (!(tu =:= weakTypeOf[UR])) =>
          throw new Exception(s"Unit type $tu did not match target ${weakTypeOf[UR]}")
          false
        case _ => {
          val qv = cdV.cast(q.value)
          val eq = if (tolerant) (math.abs(qv - tval) <= epsilon) else (qv == tval)
          if (!eq) throw new Exception(s"Value ${q.value} did not match target $tval")
          true
        }
      }
    }
  }
}
