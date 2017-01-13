package com.manyangled.coulomb

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.language.implicitConversions
import scala.annotation.implicitNotFound

trait UnitExpr

trait BaseUnit extends UnitExpr

trait PrefixUnit extends UnitExpr

trait DerivedUnit[U <: UnitExpr] extends UnitExpr

sealed trait <*> [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

sealed trait </> [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

sealed trait <^> [UE <: UnitExpr, P <: ChurchInt] extends UnitExpr

sealed trait <-> [PU <: PrefixUnit, UE <: UnitExpr] extends UnitExpr

// unitless values (any units have canceled)
sealed trait Unitless extends UnitExpr

class Unit[U <: UnitExpr](val value: Double)(implicit uesU: UnitExprString[U]) {
  def as[U2 <: UnitExpr](implicit cu: CompatUnits[U, U2], uesU2: UnitExprString[U2]): Unit[U2] =
    cu.convert(this)

  def unary_- : Unit[U] = new Unit[U](-this.value)

  def +[U2 <: UnitExpr](that: Unit[U2])(implicit cu: CompatUnits[U2, U]): Unit[U] =
    new Unit[U](this.value + cu.coef * that.value)

  def -[U2 <: UnitExpr](that: Unit[U2])(implicit cu: CompatUnits[U2, U]): Unit[U] =
    new Unit[U](this.value - cu.coef * that.value)

  def *[U2 <: UnitExpr, RU <: UnitExpr](that: Unit[U2])(implicit uer: UnitExprMul[U, U2] { type U = RU }, uesRU: UnitExprString[RU]) =
    new Unit[RU](uer.coef * this.value * that.value)

  def /[U2 <: UnitExpr, RU <: UnitExpr](that: Unit[U2])(implicit uer: UnitExprDiv[U, U2] { type U = RU }, uesRU: UnitExprString[RU]) =
    new Unit[RU](uer.coef * this.value / that.value)

  def pow[E <: ChurchInt](implicit exp: ChurchIntValue[E], uesRU: UnitExprString[U <^> E]) =
    new Unit[U <^> E](math.pow(this.value, exp.value))

  override def toString = s"$value ${uesU.str}"
}

object Unit {
  implicit def implicitUnitConvert[U <: UnitExpr, U2 <: UnitExpr](u: Unit[U])(implicit
    cu: CompatUnits[U, U2],
    uesU2: UnitExprString[U2]): Unit[U2] = cu.convert(u)

  implicit class ExtendWithUnits[N](v: N)(implicit num: Numeric[N]) {
    def withUnit[U <: UnitExpr](implicit uesU: UnitExprString[U]): Unit[U] = new Unit[U](num.toDouble(v))
  }
}

case class UnitRec[UE <: UnitExpr](name: String, coef: Double)

class UCompanion[U <: UnitExpr](uname: String, ucoef: Double) {
  def this(n: String) = this(n, 1.0)

  implicit val furec: UnitRec[U] = UnitRec[U](uname, ucoef)
}

case class TempUnitRec[UE <: UnitExpr](offset: Double)

class TempUnitCompanion[U <: UnitExpr](uname: String, ucoef: Double, uoffset: Double) extends UCompanion[U](uname, ucoef) {
  implicit val turec: TempUnitRec[U] = TempUnitRec[U](uoffset)
}

@implicitNotFound("Implicit not found: CompatUnits[${U1}, ${U2}].\nIncompatible Unit Expressions: ${U1} and ${U2}")
class CompatUnits[U1 <: UnitExpr, U2 <: UnitExpr](val coef: Double) {
  def convert(u: Unit[U1])(implicit uesU2: UnitExprString[U2]): Unit[U2] = new Unit[U2](coef * u.value)
}

object CompatUnits {
  implicit def witnessCompatUnits[U1 <: UnitExpr, U2 <: UnitExpr]: CompatUnits[U1, U2] =
    macro UnitMacros.compatUnits[U1, U2]
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

object UnitExprDiv {
  implicit def witnessUnitExprDiv[U1 <: UnitExpr, U2 <: UnitExpr]: UnitExprDiv[U1, U2] =
    macro UnitMacros.unitExprDiv[U1, U2]
}

trait UnitExprDiv[U1 <: UnitExpr, U2 <: UnitExpr] {
  type U <: UnitExpr
  def coef: Double
}

object UnitExprMul {
  implicit def witnessUnitExprMul[U1 <: UnitExpr, U2 <: UnitExpr]: UnitExprMul[U1, U2] =
    macro UnitMacros.unitExprMul[U1, U2]
}

private [coulomb] class UnitMacros(c0: whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  trait DummyU extends BaseUnit
  trait DummyP extends PrefixUnit

  val ivalType = typeOf[ChurchIntValue[ChurchInt._0]].typeConstructor
  val urecType = typeOf[UnitRec[DummyU]].typeConstructor
  val turecType = typeOf[TempUnitRec[DummyU]].typeConstructor

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

  def directTempCompat(map1: Map[Type, Int], map2: Map[Type, Int]): Boolean = {
    map1 == Map(typeOf[base.Kelvin] -> 1) && map2 == Map(typeOf[base.Kelvin] -> 1)
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
      if (directTempCompat(map1, map2)) {
        // todo: add handling of rudimentary type simplifications to identify
        // nontrivial cases of direct temperature unit exprs?
        val (_, coef1) = urecVal(tpeU1)
        val (_, coef2) = urecVal(tpeU2)
        val (coef1q, coef2q) = (q"$coef1", q"$coef2")
        val (off1q, off2q) = (q"${turecVal(tpeU1)}", q"${turecVal(tpeU2)}")
        q"""
          new _root_.com.manyangled.coulomb.CompatUnits[$tpeU1, $tpeU2]($cq) {
            override def convert(u: _root_.com.manyangled.coulomb.Unit[$tpeU1])(implicit uesU2: _root_.com.manyangled.coulomb.UnitExprString[$tpeU2]): _root_.com.manyangled.coulomb.Unit[$tpeU2] = {
              val k = (u.value + $off1q) * $coef1q
              val r = (k / $coef2q) - $off2q
              new _root_.com.manyangled.coulomb.Unit[$tpeU2](r)
            }
          }
        """
      } else {
        q"""
          new _root_.com.manyangled.coulomb.CompatUnits[$tpeU1, $tpeU2]($cq)
        """
      }
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

package base {
  trait Meter extends BaseUnit
  object Meter extends UCompanion[Meter]("meter")

  trait Second extends BaseUnit
  object Second extends UCompanion[Second]("second")

  trait Kilogram extends BaseUnit
  object Kilogram extends UCompanion[Kilogram]("kilogram")

  trait Kelvin extends BaseUnit
  object Kelvin extends TempUnitCompanion[Kelvin]("kelvin", 1.0, 0.0)
}

package derived {
  import ChurchInt._
  import base._

  trait Foot extends DerivedUnit[Meter]
  object Foot extends UCompanion[Foot]("foot", 0.3048)

  trait Yard extends DerivedUnit[Meter]
  object Yard extends UCompanion[Yard]("yard", 0.9144)

  trait Minute extends DerivedUnit[Second]
  object Minute extends UCompanion[Minute]("minute", 60.0)

  trait Pound extends DerivedUnit[Kilogram]
  object Pound extends UCompanion[Pound]("pound", 0.453592)

  trait Liter extends DerivedUnit[Meter <^> _3]
  object Liter extends UCompanion[Liter]("liter", 0.001)

  trait EarthGravity extends DerivedUnit[Meter </> (Second <^> _2)]
  object EarthGravity extends UCompanion[EarthGravity]("g", 9.807)

  trait Celsius extends DerivedUnit[Kelvin]
  object Celsius extends TempUnitCompanion[Celsius]("celsius", 1.0, 273.15)

  trait Fahrenheit extends DerivedUnit[Kelvin]
  object Fahrenheit extends TempUnitCompanion[Fahrenheit]("fahrenheit", 5.0 / 9.0, 459.67)
}

package prefix {
  trait Milli extends PrefixUnit
  object Milli extends UCompanion[Milli]("milli", 1e-3)

  trait Kilo extends PrefixUnit
  object Kilo extends UCompanion[Kilo]("kilo", 1e+3)
}
