package com.manyangled.unit4s

import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.annotation.implicitNotFound
import scala.language.higherKinds

import com.manyangled.church.{ Integer, IntegerValue }
import Integer.{ _0, _1 }

sealed class =!=[A,B]

trait NeqLowerPriorityImplicits {
  implicit def equal[A]: =!=[A, A] = {
    sys.error("should not be called")
  }
}
object =!= extends NeqLowerPriorityImplicits {
  implicit def nequal[A,B](implicit same: A =:= B = null): =!=[A,B] = {
    if (same != null) sys.error("should not be called explicitly with same type")
    else new =!=[A,B]
  }
}

trait UnitExpr

trait FULike extends UnitExpr {
  type RU <: FULike
}

trait FundamentalUnit[U <: FULike] extends FULike {
  type RU = U
}

object FundamentalUnit {
  def factor[U1 <: FULike, U2 <: FULike](implicit cfu: CompatFU[U1, U2]) = cfu.cf
}

trait PULike extends UnitExpr {
  type RU <: PULike
}

trait PrefixUnit[PU <: PULike] extends PULike {
  type RU = PU
}

trait DerivedUnit[U <: UnitExpr] extends UnitExpr

// compound unit expressions
sealed trait CULike extends UnitExpr {
  type RU <: CULike
}

trait <*> [LUE <: UnitExpr, RUE <: UnitExpr] extends CULike {
  type RU = <*> [LUE, RUE]
}

trait </> [LUE <: UnitExpr, RUE <: UnitExpr] extends CULike {
  type RU = </> [LUE, RUE]
}

trait <^> [UE <: UnitExpr, P <: Integer] extends CULike {
  type RU = <^> [UE, P]
}

trait <-> [PU <: PULike, UE <: UnitExpr] extends CULike {
  type RU = <-> [PU, UE]
}

// unitless values (any units have canceled)
sealed trait Unity extends UnitExpr


trait UnitRec[UE <: UnitExpr] {
  def name: String
  def coef: Double
}

class UCompanion[U <: UnitExpr](uname: String, ucoef: Double) {
  implicit val furec: UnitRec[U] = new UnitRec[U] {
    def name = uname
    def coef = ucoef
  }
}

@implicitNotFound("Implicit not found: CompatFU[${U1}, ${U2}].\nIncompatible Fundamental Units: ${U1} and ${U2}")
case class CompatFU[U1 <: FULike, U2 <: FULike](
  // conversion factor from U1 to U2
  cf: Double
)

object CompatFU {
  implicit def witnessCompatFU[U1 <: FULike, U2 <: FULike](implicit
      urec1: UnitRec[U1],
      urec2: UnitRec[U2],
      eq: U1#RU =:= U2#RU): CompatFU[U1, U2] =
    CompatFU[U1, U2](urec1.coef / urec2.coef)
}

case class Unit[U <: UnitExpr](value: Double) {
  def +[U2 <: UnitExpr](that: Unit[U2])(implicit cu: CompatUnits[U2, U]): Unit[U] = {
    println(s"cu= $cu")
    Unit[U](this.value + cu.coef * that.value)
  }
}

object Unit {
  implicit class ExtendWithUnits[N](v: N)(implicit num: Numeric[N]) {
    def withUnit[U <: UnitExpr]: Unit[U] = Unit[U](num.toDouble(v))
  }
}

@implicitNotFound("Implicit not found: CompatUnits[${U1}, ${U2}].\nIncompatible Unit Expressions: ${U1} and ${U2}")
case class CompatUnits[U1 <: UnitExpr, U2 <: UnitExpr](
  coef: Double // conversion factor from U1 to U2
)

object CompatUnits {
  implicit def witnessCompatUnits[U1 <: UnitExpr, U2 <: UnitExpr]:
    CompatUnits[U1, U2] = macro infra.UnitMacros.compatUnits[U1, U2]
}


package fundamental {
  trait Meter extends FundamentalUnit[Meter]
  object Meter extends UCompanion[Meter]("meter", 1.0)

  trait Foot extends FundamentalUnit[Meter]
  object Foot extends UCompanion[Foot]("foot", 0.3048)

  trait Yard extends FundamentalUnit[Meter]
  object Yard extends UCompanion[Yard]("yard", 0.9144)

  trait Second extends FundamentalUnit[Second]
  object Second extends UCompanion[Second]("second", 1.0)

  trait Minute extends FundamentalUnit[Second]
  object Minute extends UCompanion[Minute]("minute", 60.0)

  trait Kilogram extends FundamentalUnit[Kilogram]
  object Kilogram extends UCompanion[Kilogram]("kilogram", 1.0)

  trait Pound extends FundamentalUnit[Kilogram]
  object Pound extends UCompanion[Pound]("pound", 0.453592)
}

package derived {
  import Integer._
  import fundamental._

  trait Liter extends DerivedUnit[Meter <^> _3]
  object Liter extends UCompanion[Liter]("liter", 0.001)

  trait EarthGravity extends DerivedUnit[Meter </> (Second <^> _2)]
  object EarthGravity extends UCompanion[EarthGravity]("g", 9.807)
}

package prefix {
  trait Milli extends PrefixUnit[Milli]
  object Milli extends UCompanion[Milli]("milli", 1e-3)

  trait Kilo extends PrefixUnit[Kilo]
  object Kilo extends UCompanion[Kilo]("kilo", 1e+3)
}

object infra {
  import scala.language.experimental.macros
  import scala.reflect.macros.whitebox

  trait DummyU extends FundamentalUnit[DummyU]
  object DummyU extends UCompanion[DummyU]("dummy", 1.0)
  trait DummyP extends PrefixUnit[DummyP]
  object DummyP extends UCompanion[DummyP]("dummy", 1.0)

  class UnitMacros(val c: whitebox.Context) {
    import scala.reflect.runtime.currentMirror 
    import scala.tools.reflect.ToolBox 
    val toolbox = currentMirror.mkToolBox()

    import c.universe._

    def abort(msg: String) = c.abort(c.enclosingPosition, msg)

    def typeName(tpe: Type): String = tpe.typeSymbol.fullName

    def evalTree[T](tree: Tree) = c.eval(c.Expr[T](c.untypecheck(tree.duplicate)))

    def superClass(tpe: Type, sup: Type): Option[Type] = {
      val supSym = sup.typeSymbol
      val bc = tpe.baseClasses.drop(1)
      if (bc.count { bSym => bSym == supSym } < 1) None else Some(tpe.baseType(supSym))
    }

    val ivalType = typeOf[IntegerValue[Integer._0]].typeConstructor
    val urecType = typeOf[UnitRec[DummyU]].typeConstructor
    val fuType = typeOf[FundamentalUnit[DummyU]].typeConstructor
    val duType = typeOf[DerivedUnit[DummyU]].typeConstructor
    val puType = typeOf[PrefixUnit[DummyP]].typeConstructor

    def intVal(intT: Type): Int = {
      val ivt = appliedType(ivalType, List(intT))
      val ival = c.inferImplicitValue(ivt, silent = false)
      evalTree[Int](q"${ival}.value")
    }
    
    def coefVal(unitT: Type): Double = {
      val urt = appliedType(urecType, List(unitT))
      val ur = c.inferImplicitValue(urt, silent = false)
      evalTree[Double](q"${ur}.coef")
    }

    val mulType = typeOf[<*>[DummyU, DummyU]].typeConstructor
    val divType = typeOf[</>[DummyU, DummyU]].typeConstructor
    val powType = typeOf[<^>[DummyU, Integer._0]].typeConstructor
    val preType = typeOf[<->[DummyP, DummyU]].typeConstructor

    object MulOp {
      def unapply(tpe: Type): Option[(Type, Type)] = {
        if (tpe.typeConstructor =:= mulType) {
          val (lht :: rht :: Nil) = tpe.typeArgs
          Option(lht, rht)
        } else None
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

    object PreOp {
      def unapply(tpe: Type): Option[(Double, Type)] = {
        if (tpe.typeConstructor =:= preType) {
          val (pre :: uexp :: Nil) = tpe.typeArgs
          Option(coefVal(pre), uexp)
        } else None
      }
    }

    object FUnit {
      def unapply(tpe: Type): Option[(Double, Type)] = {
        val fu = superClass(tpe, fuType)
        if (fu.isEmpty) None else Option(coefVal(tpe), fu.get.typeArgs(0))
      }
    }

    object DUnit {
      def unapply(tpe: Type): Option[(Double, Type)] = {
        val du = superClass(tpe, duType)
        if (du.isEmpty) None else Option(coefVal(tpe), du.get.typeArgs(0))
      }
    }

    def canonical(typeU: Type): (Double, Map[Type, Int]) = {
      println(s"canonical: ${typeName(typeU)}")
      typeU match {
        case FUnit(coef, refU) => {
          println(s"FUNDAMENTAL: coef= $coef  refU= ${typeName(refU)}")
          (coef, Map(refU -> 1))
        }
        case DUnit(coef, dsub) => {
          println(s"DERIVED: coef= $coef  dsub= ${typeName(dsub)}")
          val (dcoef, dmap) = canonical(dsub)
          (coef * dcoef, dmap)
        }
        case MulOp(lsub, rsub) => {
          println(s"MUL: ${typeName(lsub)} ${typeName(rsub)}")
          val (lcoef, lmap) = canonical(lsub)
          val (rcoef, rmap) = canonical(rsub)
          val mmap = rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
            if (m.contains(t)) {
              val ne = m(t) + e
              if (ne == 0) m else m + ((t, ne))
            } else {
              m + ((t, e))
            }
          }
          (lcoef * rcoef, mmap)
        }
        case DivOp(lsub, rsub) => {
          println(s"MUL: ${typeName(lsub)} ${typeName(rsub)}")
          val (lcoef, lmap) = canonical(lsub)
          val (rcoef, rmap) = canonical(rsub)
          val dmap = rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
            if (m.contains(t)) {
              val ne = m(t) - e
              if (ne == 0) m else m + ((t, ne))
            } else {
              m + ((t, -e))
            }
          }
          (lcoef / rcoef, dmap)
        }
        case PowOp(bsub, exp) => {
          println(s"POW: ${typeName(bsub)} $exp")
          val (bcoef, bmap) = canonical(bsub)
          if (exp == 0)
            (1.0, Map.empty[Type, Int])
          else
            (math.pow(bcoef, exp), bmap.mapValues(_ * exp))
        }
        case PreOp(coef, sub) => {
          println(s"PRE: $coef ${typeName(sub)}")
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

      println(s"coef1= $coef1  map1=\n$map1")
      println(s"coef2= $coef2  map2=\n$map2")

      val compat = (map1 == map2)

      if (!compat) q"" // implicit resolution will fail
      else {
        val cq = q"${coef1 / coef2}"
        q"""
          _root_.com.manyangled.unit4s.CompatUnits[$tpeU1, $tpeU2]($cq)
        """
      }
    }
  }
}



object test {
  import shapeless._
  import prefix._

/*
  def f1(uv: UnitValue1[Foot, Integer._1]) = uv.value
  def f2(uv: UnitValue2[Foot, Integer._1, Second, Integer._neg1]) = uv.value
*/

}
