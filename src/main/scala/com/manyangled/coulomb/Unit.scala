package com.manyangled.coulomb

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.language.implicitConversions
import scala.annotation.implicitNotFound

trait UnitExpr

trait BaseUnit extends UnitExpr

trait DerivedUnit[U <: UnitExpr] extends UnitExpr

trait PrefixUnit extends DerivedUnit[Unitless]

sealed trait <*> [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

sealed trait </> [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

sealed trait <^> [UE <: UnitExpr, P <: ChurchInt] extends UnitExpr

sealed trait <-> [PU <: PrefixUnit, UE <: UnitExpr] extends UnitExpr

sealed trait Unitless extends UnitExpr

trait TemperatureExpr extends UnitExpr

trait BaseTemperature extends BaseUnit with TemperatureExpr

trait DerivedTemperature extends DerivedUnit[SIBaseUnits.Kelvin] with TemperatureExpr

class Quantity[U <: UnitExpr](private [coulomb] val value: Double)
    extends AnyVal with Serializable {

  def as[U2 <: UnitExpr](implicit
      cu: CompatUnits[U, U2]): Quantity[U2] =
    cu.convert(this)

  def unary_- : Quantity[U] = new Quantity[U](-this.value)

  def +[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Quantity[U] =
    new Quantity[U](this.value + cu.coef * that.value)

  def -[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Quantity[U] =
    new Quantity[U](this.value - cu.coef * that.value)

  def *[U2 <: UnitExpr, RU <: UnitExpr](that: Quantity[U2])(implicit uer: UnitExprMul[U, U2] { type U = RU }): Quantity[RU] =
    new Quantity[RU](uer.coef * this.value * that.value)

  def /[U2 <: UnitExpr, RU <: UnitExpr](that: Quantity[U2])(implicit uer: UnitExprDiv[U, U2] { type U = RU }): Quantity[RU] =
    new Quantity[RU](uer.coef * this.value / that.value)

  def pow[E <: ChurchInt](implicit exp: ChurchIntValue[E]): Quantity[U <^> E] =
    new Quantity[U <^> E](math.pow(this.value, exp.value))

  def toInt: Int = value.round.toInt
  def toLong: Long = value.round
  def toFloat: Float = value.toFloat
  def toDouble: Double = value

  def str(implicit uesU: UnitExprString[U]) = s"$value ${uesU.str}"
  def unitStr(implicit uesU: UnitExprString[U]) = uesU.str
}

object Quantity {
  def converter[U <: UnitExpr, U2 <: UnitExpr](implicit cu: CompatUnits[U, U2]):
      Quantity[U] => Quantity[U2] =
    cu.converter

  def coefficient[U <: UnitExpr, U2 <: UnitExpr](implicit cu: CompatUnits[U, U2]): Double =
    cu.coef

  def unitStr[U <: UnitExpr](implicit uesU: UnitExprString[U]) = uesU.str

  def apply[U <: UnitExpr](v: Int) = new Quantity[U](v.toDouble)
  def apply[U <: UnitExpr](v: Long) = new Quantity[U](v.toDouble)
  def apply[U <: UnitExpr](v: Float) = new Quantity[U](v.toDouble)
  def apply[U <: UnitExpr](v: Double) = new Quantity[U](v)

  def fromTemperature[U <: TemperatureExpr](t: Temperature[U]) = new Quantity[U](t.value)

  implicit def implicitUnitConvert[U <: UnitExpr, U2 <: UnitExpr](q: Quantity[U])(implicit
    cu: CompatUnits[U, U2]): Quantity[U2] = cu.convert(q)
}

class Temperature[U <: TemperatureExpr](private [coulomb] val value: Double)
    extends AnyVal with Serializable {

  def as[U2 <: TemperatureExpr](implicit
      ct: CompatTemps[U, U2]): Temperature[U2] =
    ct.convert(this)

  def +[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Temperature[U] =
    new Temperature[U](this.value + cu.coef * that.value)

  def -[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Temperature[U] =
    new Temperature[U](this.value - cu.coef * that.value)

  def -[U2 <: TemperatureExpr](that: Temperature[U2])(implicit
      ct: CompatTemps[U2, U]): Quantity[U] =
    new Quantity[U](this.value - ct.convert(that).value)

  def toInt: Int = value.round.toInt
  def toLong: Long = value.round
  def toFloat: Float = value.toFloat
  def toDouble: Double = value

  def str(implicit uesU: UnitExprString[U]) = s"$value ${uesU.str}"
  def unitStr(implicit uesU: UnitExprString[U]) = uesU.str
}

object Temperature {
  def converter[U <: TemperatureExpr, U2 <: TemperatureExpr](implicit
      ct: CompatTemps[U, U2]): Temperature[U] => Temperature[U2] =
    ct.converter

  def unitStr[U <: TemperatureExpr](implicit uesU: UnitExprString[U]) = uesU.str

  def apply[U <: TemperatureExpr](v: Int) = new Temperature[U](v.toDouble)
  def apply[U <: TemperatureExpr](v: Long) = new Temperature[U](v.toDouble)
  def apply[U <: TemperatureExpr](v: Float) = new Temperature[U](v.toDouble)
  def apply[U <: TemperatureExpr](v: Double) = new Temperature[U](v)

  def fromQuantity[U <: TemperatureExpr](q: Quantity[U]) = new Temperature[U](q.value)

  implicit def implicitTempConvert[U <: TemperatureExpr, U2 <: TemperatureExpr](t: Temperature[U])(
      implicit ct: CompatTemps[U, U2]): Temperature[U2] =
    ct.convert(t)
}

object extensions {
  implicit class ExtendWithUnits[N](v: N)(implicit num: Numeric[N]) {
    def withUnit[U <: UnitExpr]: Quantity[U] =
      new Quantity[U](num.toDouble(v))

    def withTemperature[U <: TemperatureExpr]: Temperature[U] =
      new Temperature[U](num.toDouble(v))
  }
}

case class UnitRec[UE <: UnitExpr](name: String, coef: Double)

class UnitCompanion[U <: UnitExpr](uname: String, ucoef: Double) {
  def this(n: String) = this(n, 1.0)

  def apply[N](v: N)(implicit num: Numeric[N]): Quantity[U] =
    new Quantity[U](num.toDouble(v))

  def apply(): Quantity[U] = new Quantity[U](1.0)

  implicit val furec: UnitRec[U] = UnitRec[U](uname, ucoef)
}

case class TempUnitRec[UE <: TemperatureExpr](offset: Double)

class TempUnitCompanion[U <: TemperatureExpr](uname: String, ucoef: Double, uoffset: Double) extends UnitCompanion[U](uname, ucoef) {
  implicit val turec: TempUnitRec[U] = TempUnitRec[U](uoffset)
}

@implicitNotFound("Implicit not found: CompatUnits[${U1}, ${U2}].\nIncompatible Unit Expressions: ${U1} and ${U2}")
class CompatUnits[U1 <: UnitExpr, U2 <: UnitExpr](val coef: Double) {
  val converter = CompatUnits.converter[U1, U2](coef)
  def convert(q1: Quantity[U1]): Quantity[U2] = converter(q1)
}

object CompatUnits {
  def converter[U1 <: UnitExpr, U2 <: UnitExpr](coef: Double): Quantity[U1] => Quantity[U2] =
    (q1: Quantity[U1]) => new Quantity[U2](coef * q1.value)

  implicit def witnessCompatUnits[U1 <: UnitExpr, U2 <: UnitExpr]: CompatUnits[U1, U2] =
    macro UnitMacros.compatUnits[U1, U2]
}

class CompatTemps[U1 <: TemperatureExpr, U2 <: TemperatureExpr](
    val coef1: Double, val off1: Double, val coef2: Double, val off2: Double) {
  val converter = CompatTemps.converter[U1, U2](coef1, off1, coef2, off2)
  def convert(t1: Temperature[U1]): Temperature[U2] = converter(t1)
}

object CompatTemps {
  def converter[U1 <: TemperatureExpr, U2 <: TemperatureExpr](
    coef1: Double, off1: Double, coef2: Double, off2: Double): Temperature[U1] => Temperature[U2] =
    (t1: Temperature[U1]) => {
      val k = (t1.value + off1) * coef1
      val v2 = (k / coef2) - off2
      new Temperature[U2](v2)
    }

  implicit def witnessCompatTemps[U1 <: TemperatureExpr, U2 <: TemperatureExpr](implicit
      turecU1: TempUnitRec[U1], urecU1: UnitRec[U1],
      turecU2: TempUnitRec[U2], urecU2: UnitRec[U2]): CompatTemps[U1, U2] =
    new CompatTemps[U1, U2](urecU1.coef, turecU1.offset, urecU2.coef, turecU2.offset)
}

case class UnitExprString[U <: UnitExpr](str: String)

object UnitExprString {
  implicit def witnessUnitExprString[U <: UnitExpr]: UnitExprString[U] =
    macro UnitMacros.unitExprString[U]
}

trait UnitExprMul[U1 <: UnitExpr, U2 <: UnitExpr] {
  type U <: UnitExpr
  def coef: Double
}

object UnitExprMul {
  implicit def witnessUnitExprMul[U1 <: UnitExpr, U2 <: UnitExpr]: UnitExprMul[U1, U2] =
    macro UnitMacros.unitExprMul[U1, U2]
}

trait UnitExprDiv[U1 <: UnitExpr, U2 <: UnitExpr] {
  type U <: UnitExpr
  def coef: Double
}

object UnitExprDiv {
  implicit def witnessUnitExprDiv[U1 <: UnitExpr, U2 <: UnitExpr]: UnitExprDiv[U1, U2] =
    macro UnitMacros.unitExprDiv[U1, U2]
}

private [coulomb] class UnitMacros(c0: whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  trait DummyU extends BaseUnit
  trait DummyT extends BaseTemperature
  trait DummyP extends PrefixUnit

  val ivalType = typeOf[ChurchIntValue[ChurchInt._0]].typeConstructor
  val urecType = typeOf[UnitRec[DummyU]].typeConstructor
  val turecType = typeOf[TempUnitRec[DummyT]].typeConstructor

  val fuType = typeOf[BaseUnit]
  val puType = typeOf[PrefixUnit]
  val duType = typeOf[DerivedUnit[DummyU]].typeConstructor

  val mulType = typeOf[<*>[DummyU, DummyU]].typeConstructor
  val divType = typeOf[</>[DummyU, DummyU]].typeConstructor
  val powType = typeOf[<^>[DummyU, ChurchInt._0]].typeConstructor
  val preType = typeOf[<->[DummyP, DummyU]].typeConstructor

  def intVal(intT: Type): Int = {
    val ivt = appliedType(ivalType, List(intT))
    val ival = c.inferImplicitValue(ivt, silent = false)
    evalTree[Int](q"${ival}.value")
  }
  
  def urecVal(unitT: Type): (String, Double) = {
    val urt = appliedType(urecType, List(unitT))
    val ur = c.inferImplicitValue(urt, silent = false)
    evalTree[(String, Double)](q"(${ur}.name, ${ur}.coef)")
  }

  def turecVal(unitT: Type): Double = {
    val urt = appliedType(turecType, List(unitT))
    val ur = c.inferImplicitValue(urt, silent = false)
    evalTree[Double](q"${ur}.offset")
  }

  object MulOp {
    def unapply(tpe: Type): Option[(Type, Type)] = {
      if (tpe.typeConstructor =:= mulType) {
        val (lht :: rht :: Nil) = tpe.typeArgs
        Option(lht, rht)
      } else None
    }
  }

  object FlatMulOp {
    def unapply(tpe: Type): Option[Seq[Type]] = {
      tpe match {
        case MulOp(lsub, rsub) => {
          val lseq = lsub match {
            case FlatMulOp(ls) => ls
            case _ => Vector(lsub)
          }
          val rseq = rsub match {
            case FlatMulOp(rs) => rs
            case _ => Vector(rsub)
          }
          Option(lseq ++ rseq)
        }
        case _ => None
      }
    }
  }

  object DivOp {
    def unapply(tpe: Type): Option[(Type, Type)] = {
      if (tpe.typeConstructor =:= divType) {
        val (lht :: rht :: Nil) = tpe.typeArgs
        Option(lht, rht)
      } else None
    }
  }

  object PowOp {
    def unapply(tpe: Type): Option[(Type, Int)] = {
      if (tpe.typeConstructor =:= powType) {
        val (bT :: expT :: Nil) = tpe.typeArgs
        Option(bT, intVal(expT))
      } else None
    }
  }

  object FUnit {
    def unapply(tpe: Type): Option[String] = {
      if (superClass(tpe, fuType).isEmpty) None else {
        val (name, _) = urecVal(tpe)
        Option(name)
      }
    }
  }

  object PreOp {
    def unapply(tpe: Type): Option[(String, Double, Type)] = {
      if (tpe.typeConstructor =:= preType) {
        val (pre :: uexp :: Nil) = tpe.typeArgs
        val pu = superClass(pre, puType)
        if (pu.isEmpty) None else {
          val (name, coef) = urecVal(pre)
          Option(name, coef, uexp)
        }
      } else None
    }
  }

  object DUnit {
    def unapply(tpe: Type): Option[(String, Double, Type)] = {
      val du = superClass(tpe, duType)
      if (du.isEmpty) None else {
        val (name, coef) = urecVal(tpe)
        Option(name, coef, du.get.typeArgs(0))
      }
    }
  }

  object IsUnitless {
    def unapply(tpe: Type): Boolean = tpe =:= typeOf[Unitless]
  }

  def mapMul(lmap: Map[Type, Int], rmap: Map[Type, Int]): Map[Type, Int] = {
    rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
      if (m.contains(t)) {
        val ne = m(t) + e
        if (ne == 0) (m - t) else m + ((t, ne))
      } else {
        m + ((t, e))
      }
    }
  }

  def mapDiv(lmap: Map[Type, Int], rmap: Map[Type, Int]): Map[Type, Int] = {
    rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
      if (m.contains(t)) {
        val ne = m(t) - e
        if (ne == 0) (m - t) else m + ((t, ne))
      } else {
        m + ((t, -e))
      }
    }
  }

  def mapPow(bmap: Map[Type, Int], exp: Int): Map[Type, Int] = {    
    if (exp == 0) Map.empty[Type, Int] else bmap.mapValues(_ * exp)
  }

  def canonical(typeU: Type): (Double, Map[Type, Int]) = {
    typeU.dealias match {
      case IsUnitless() => (1.0, Map.empty[Type, Int])
      case FUnit(_) => {
        (1.0, Map(typeU -> 1))
      }
      case DUnit(_, coef, dsub) => {
        val (dcoef, dmap) = canonical(dsub)
        (coef * dcoef, dmap)
      }
      case MulOp(lsub, rsub) => {
        val (lcoef, lmap) = canonical(lsub)
        val (rcoef, rmap) = canonical(rsub)
        (lcoef * rcoef, mapMul(lmap, rmap))
      }
      case DivOp(lsub, rsub) => {
        val (lcoef, lmap) = canonical(lsub)
        val (rcoef, rmap) = canonical(rsub)
        (lcoef / rcoef, mapDiv(lmap, rmap))
      }
      case PowOp(bsub, exp) => {
        val (bcoef, bmap) = canonical(bsub)
        (math.pow(bcoef, exp), mapPow(bmap, exp))
      }
      case PreOp(_, coef, sub) => {
        val (scoef, smap) = canonical(sub)
        (coef * scoef, smap)
      }
      case _ => {
        // This should never execute
        abort(s"Undefined Unit Type: ${typeName(typeU)}")
        (0.0, Map.empty[Type, Int])
      }
    }
  }

  def compatUnits[U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeU1 = weakTypeOf[U1].dealias
    val tpeU2 = weakTypeOf[U2].dealias

    val (coef1, map1) = canonical(tpeU1)
    val (coef2, map2) = canonical(tpeU2)

    // units are compatible if their canonical representations are equal
    val compat = (map1 == map2)

    if (!compat) q"" // fail implicit resolution if they aren't compatible
    else {
      // if they are compatible, then create the corresponding witness
      val cq = q"${coef1 / coef2}"
      q"""
        new _root_.com.manyangled.coulomb.CompatUnits[$tpeU1, $tpeU2]($cq)
      """
    }
  }

  def ueAtomicString(typeU: Type): Boolean = {
    typeU.dealias match {
      case IsUnitless() => true
      case FUnit(_) => true
      case DUnit(_, _, _) => true
      case PowOp(_, exp) if (exp == 0) => true
      case PowOp(bsub, exp) if (exp == 1 && ueAtomicString(bsub)) => true
      case _ => false
    }
  }

  def ueString(typeU: Type): String = {
    typeU.dealias match {
      case IsUnitless() => "unitless"
      case FUnit(name) => name
      case DUnit(name, _, _) => name
      case FlatMulOp(ssub) => {
        ssub.map { sub =>
          val str = ueString(sub)
          if (ueAtomicString(sub)) str else s"($str)"
        }.mkString(" * ")
      }
      case DivOp(lsub, rsub) => {
        val lstr = ueString(lsub)
        val rstr = ueString(rsub)
        val ls = if (ueAtomicString(lsub)) lstr else s"($lstr)"
        val rs = if (ueAtomicString(rsub)) rstr else s"($rstr)"
        s"$ls / $rs"
      }
      case PowOp(bsub, exp) => {
        if (exp == 0) "unitless"
        else if (exp == 1) ueString(bsub)
        else {
          val bstr = ueString(bsub)
          val bs = if (ueAtomicString(bsub)) bstr else s"($bstr)"
          s"$bs ^ $exp"
        }
      }
      case PreOp(name, _, sub) => {
        val str = ueString(sub)
        val s = if (ueAtomicString(sub)) str else s"($str)"
        s"${name}-$s"
      }        
      case _ => {
        // This should never execute
        abort(s"Undefined Unit Type: ${typeName(typeU)}")
        ""
      }
    }
  }

  def unitExprString[U: WeakTypeTag]: Tree = {
    val tpeU = weakTypeOf[U]
    val str = ueString(tpeU)
    val sq = q"$str"
    q"""
      _root_.com.manyangled.coulomb.UnitExprString[$tpeU]($sq)
    """
  }

  def ueToType(u: Type, e: Int): Type =
    if (e == 1) u else appliedType(powType, List(u, churchType(e)))

  def seqToType(seq: Seq[(Type, Int)]): Type = {
    if (seq.isEmpty) typeOf[Unitless] else {
      val (u0, e0) = seq.head
      seq.tail.foldLeft(ueToType(u0, e0)) { case (tc, (u, e)) =>
        appliedType(mulType, List(tc, ueToType(u, e)))
      }
    }
  }

  def mapToType(map: Map[Type, Int]): Type = {
    val vec = map.toVector
    val ePos = vec.filter { case(_, e) => e > 0 }.sortBy { case (_, e) => e }
    val eNeg = vec.filter { case(_, e) => e < 0 }
      .map { case (u, e) => (u -> -e) }.sortBy { case (_, e) => e }
    val tPos = seqToType(ePos)
    val tNeg = seqToType(eNeg)
    if (eNeg.isEmpty) tPos else appliedType(divType, List(tPos, tNeg))
  }

  def unitExprMul[U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeU1 = weakTypeOf[U1]
    val tpeU2 = weakTypeOf[U2]

    val (coef1, map1) = canonical(tpeU1)
    val (coef2, map2) = canonical(tpeU2)

    val mt = mapToType(mapMul(map1, map2))

    val cq = q"${coef1 * coef2}"

    q"""
      new _root_.com.manyangled.coulomb.UnitExprMul[$tpeU1, $tpeU2] {
        type U = $mt
        def coef = $cq
      }
    """
  }

  def unitExprDiv[U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeU1 = weakTypeOf[U1]
    val tpeU2 = weakTypeOf[U2]

    val (coef1, map1) = canonical(tpeU1)
    val (coef2, map2) = canonical(tpeU2)

    val mt = mapToType(mapDiv(map1, map2))

    val cq = q"${coef1 / coef2}"

    q"""
      new _root_.com.manyangled.coulomb.UnitExprDiv[$tpeU1, $tpeU2] {
        type U = $mt
        def coef = $cq
      }
    """
  }
}
