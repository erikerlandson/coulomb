package coulomb.validators

import scala.reflect.runtime.universe._
import scala.language.implicitConversions

import spire.math._

import coulomb._
import coulomb.temp._
import coulomb.offset.OffsetQuantity

object CoulombValidators {

  trait Equality[N] {
    def areEqual(x: N, a: Any): Boolean
  }

  val epsilon = 1e-4

  implicit val byteTolerant = new Equality[Byte] {
    def areEqual(x: Byte, a: Any) = a match {
      case y: Byte => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val shortTolerant = new Equality[Short] {
    def areEqual(x: Short, a: Any) = a match {
      case y: Short => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val intTolerant = new Equality[Int] {
    def areEqual(x: Int, a: Any) = a match {
      case y: Int => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val longTolerant = new Equality[Long] {
    def areEqual(x: Long, a: Any) = a match {
      case y: Long => math.abs(x - y) <= 1
      case _ => false
    }
  }
  implicit val bigIntTolerant = new Equality[BigInt] {
    def areEqual(x: BigInt, a: Any) = a match {
      case y: BigInt => (x - y).abs <= 1
      case _ => false
    }
  }
  implicit val floatTolerant = new Equality[Float] {
    def areEqual(x: Float, a: Any) = a match {
      case y: Float => math.abs(x - y) <= epsilon.toFloat
      case _ => false
    }
  }
  implicit val doubleTolerant = new Equality[Double] {
    def areEqual(x: Double, a: Any) = a match {
      case y: Double => math.abs(x - y) <= epsilon
      case _ => false
    }
  }
  implicit val bigDecimalTolerant = new Equality[BigDecimal] {
    def areEqual(x: BigDecimal, a: Any) = a match {
      case y: BigDecimal => (x - y).abs <= BigDecimal(epsilon)
      case _ => false
    }
  }
  implicit val rationalTolerant = new Equality[Rational] {
    def areEqual(x: Rational, a: Any) = a match {
      case y: Rational => (x - y).abs <= Rational(epsilon)
      case _ => false
    }
  }
  implicit val algebraicTolerant = new Equality[Algebraic] {
    def areEqual(x: Algebraic, a: Any) = a match {
      case y: Algebraic => (x - y).abs <= Algebraic(epsilon)
      case _ => false
    }
  }
  implicit val realTolerant = new Equality[Real] {
    def areEqual(x: Real, a: Any) = a match {
      // Strange. Real has no <= operator.
      case y: Real => (x - y).abs.toDouble <= epsilon
      case _ => false
    }
  }
  implicit val numberTolerant = new Equality[Number] {
    def areEqual(x: Number, a: Any) = a match {
      case y: Number => (x - y).abs <= Number(epsilon)
      case _ => false
    }
  }

  implicit class WithQuantityShouldMethods[N, U](q: Quantity[N, U])(implicit
      ttN: WeakTypeTag[N],
      ttU: WeakTypeTag[U],
      numN: Numeric[N]) {

    def isValidQ[NR, UR](tval: Double, f: Int = 1, tolerant: Boolean = true)(implicit
        ttNR: WeakTypeTag[NR],
        ttUR: WeakTypeTag[UR],
        teq: Equality[NR],
        num: spire.math.Numeric[NR]): Boolean = {
      (weakTypeOf[N], weakTypeOf[U]) match {
        case (tn, _) if (!(tn =:= weakTypeOf[NR])) =>
          throw new Exception(s"Numeric type $tn did not match target ${weakTypeOf[NR]}")
          false
        case (_, tu) if (!(tu =:= weakTypeOf[UR])) =>
          throw new Exception(s"Unit type $tu did not match target ${weakTypeOf[UR]}")
          false
        case _ => {
          val tv: NR = if (f == 1) num.fromDouble(tval) else num.fromInt((tval * f.toDouble).toInt)
          val eq =
            if (tolerant) teq.areEqual(tv, q.value) else (num.compare(tv, num.fromType[N](q.value)) == 0)
          if (!eq) throw new Exception(s"Value ${q.value} did not match target $tv")
          true
        }
      }
    }
  }

  implicit class WithTemperatureShouldMethods[N, U](t: Temperature[N, U])(implicit
      ttN: WeakTypeTag[N],
      ttU: WeakTypeTag[U],
      numN: Numeric[N]) {

    def isValidT[NR, UR](tval: Double, tolerant: Boolean = true)(implicit
        ttNR: WeakTypeTag[NR],
        ttUR: WeakTypeTag[UR],
        teq: Equality[NR],
        num: spire.math.Numeric[NR]): Boolean = {
      (weakTypeOf[N], weakTypeOf[U]) match {
        case (tn, _) if (!(tn =:= weakTypeOf[NR])) =>
          throw new Exception(s"Numeric type $tn did not match target ${weakTypeOf[NR]}")
          false
        case (_, tu) if (!(tu =:= weakTypeOf[UR])) =>
          throw new Exception(s"Unit type $tu did not match target ${weakTypeOf[UR]}")
          false
        case _ => {
          val tv: NR = num.fromDouble(tval)
          val eq =
            if (tolerant) teq.areEqual(tv, t.value) else (num.compare(tv, num.fromType[N](t.value)) == 0)
          if (!eq) throw new Exception(s"Value ${t.value} did not match target $tv")
          true
        }
      }
    }
  }

  implicit class WithOffsetQuantityValidateMethods[N, U](q: OffsetQuantity[N, U])(implicit
      ttN: WeakTypeTag[N],
      ttU: WeakTypeTag[U],
      numN: Numeric[N]) {

    def isValidOQ[NR, UR](tval: Double, tolerant: Boolean = true)(implicit
        ttNR: WeakTypeTag[NR],
        ttUR: WeakTypeTag[UR],
        teq: Equality[NR],
        num: spire.math.Numeric[NR]): Boolean = {
      (weakTypeOf[N], weakTypeOf[U]) match {
        case (tn, _) if (!(tn =:= weakTypeOf[NR])) =>
          throw new Exception(s"Numeric type $tn did not match target ${weakTypeOf[NR]}")
          false
        case (_, tu) if (!(tu =:= weakTypeOf[UR])) =>
          throw new Exception(s"Unit type $tu did not match target ${weakTypeOf[UR]}")
          false
        case _ => {
          val tv: NR = num.fromDouble(tval)
          val eq =
            if (tolerant) teq.areEqual(tv, q.value) else (num.compare(tv, num.fromType[N](q.value)) == 0)
          if (!eq) throw new Exception(s"Value ${q.value} did not match target $tv")
          true
        }
      }
    }
  }
}
