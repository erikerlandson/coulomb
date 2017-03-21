/*
Copyright 2017 Erik Erlandson

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

package com.manyangled.coulomb

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly

import spire.math.{ Rational, ConvertableFrom }

@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class unitDecl[N](name: String, coef: N) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.unitDecl
}

@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class baseUnitDecl(name: String) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.baseUnitDecl
}

@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class tempUnitDecl[N](name: String, coef: N, off: N) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.tempUnitDecl
}

private [coulomb] class UnitMacros(c0: whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  trait DummyU extends BaseUnit
  trait DummyT extends BaseTemperature

  val ivalType = typeOf[ChurchIntValue[ChurchInt._0]].typeConstructor
  val urecType = typeOf[UnitRec[DummyU]].typeConstructor
  val turecType = typeOf[TempUnitRec[DummyT]].typeConstructor

  val fuType = typeOf[BaseUnit]
  val puType = typeOf[PrefixUnit]
  val duType = typeOf[DerivedUnit[DummyU]].typeConstructor

  val mulType = typeOf[<*>[DummyU, DummyU]].typeConstructor
  val divType = typeOf[</>[DummyU, DummyU]].typeConstructor
  val powType = typeOf[<^>[DummyU, ChurchInt._0]].typeConstructor

  def intVal(intT: Type): Int = {
    val ivt = appliedType(ivalType, List(intT))
    val ival = c.inferImplicitValue(ivt, silent = false)
    evalTree[Int](q"${ival}.value")
  }
  
  def urecVal(unitT: Type): (String, Rational) = {
    val urt = appliedType(urecType, List(unitT))
    val ur = c.inferImplicitValue(urt, silent = false)
    evalTree[(String, Rational)](q"(${ur}.name, ${ur}.coef)")
  }

  def turecVal(unitT: Type): Rational = {
    val urt = appliedType(turecType, List(unitT))
    val ur = c.inferImplicitValue(urt, silent = false)
    evalTree[Rational](q"${ur}.offset")
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

  object DUnit {
    def unapply(tpe: Type): Option[(String, Rational, Type)] = {
      val du = superClass(tpe, duType)
      if (du.isEmpty) None else {
        val (name, coef) = urecVal(tpe)
        Option(name, coef, du.get.typeArgs(0))
      }
    }
  }

  object Prefix {
    def unapply(tpe: Type): Option[(String, Rational, Type)] = {
      if (tpe.typeConstructor =:= mulType) {
        val (pre :: uexp :: Nil) = tpe.typeArgs
        val pu = superClass(pre, puType)
        if (pu.isEmpty) None else uexp match {
          case FUnit(_) | DUnit(_, _, _) => {
            val (name, coef) = urecVal(pre)
            Option(name, coef, uexp)
          }
          case _ => None
        }
      } else None
    }
  }

  object IsUnitless {
    def unapply(tpe: Type): Boolean = tpe =:= typeOf[Unitless]
  }

  case class TypeKey(tpe: Type) {
    override def canEqual(a: Any): Boolean = a.isInstanceOf[TypeKey]
    override def equals(a: Any): Boolean = a match {
      case TypeKey(t) => (tpe =:= t)
      case _ => false
    }
    override def hashCode: Int = typeName(tpe).hashCode
  }

  def mapMul(lmap: Map[TypeKey, Int], rmap: Map[TypeKey, Int]): Map[TypeKey, Int] = {
    rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
      if (m.contains(t)) {
        val ne = m(t) + e
        if (ne == 0) (m - t) else m + ((t, ne))
      } else {
        m + ((t, e))
      }
    }
  }

  def mapDiv(lmap: Map[TypeKey, Int], rmap: Map[TypeKey, Int]): Map[TypeKey, Int] = {
    rmap.iterator.foldLeft(lmap) { case (m, (t, e)) =>
      if (m.contains(t)) {
        val ne = m(t) - e
        if (ne == 0) (m - t) else m + ((t, ne))
      } else {
        m + ((t, -e))
      }
    }
  }

  def mapPow(bmap: Map[TypeKey, Int], exp: Int): Map[TypeKey, Int] = {
    if (exp == 0) Map.empty[TypeKey, Int] else bmap.mapValues(_ * exp)
  }

  def canonical(typeU: Type): (Rational, Map[TypeKey, Int]) = {
    typeU.dealias match {
      case IsUnitless() => (1.0, Map.empty[TypeKey, Int])
      case FUnit(_) => {
        (1.0, Map(TypeKey(typeU) -> 1))
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
        (bcoef.pow(exp), mapPow(bmap, exp))
      }
      case _ => {
        // This should never execute
        abort(s"Undefined Unit Type: ${typeName(typeU)}")
        (0.0, Map.empty[TypeKey, Int])
      }
    }
  }

  def compatUnits[U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])

    val (coef1, map1) = canonical(tpeU1)
    val (coef2, map2) = canonical(tpeU2)

    // units are compatible if their canonical representations are equal
    val compat = (map1 == map2)

    if (!compat) q"" // fail implicit resolution if they aren't compatible
    else {
      // if they are compatible, then create the corresponding witness
      val coef = (coef1 / coef2).toDouble
      val cq = q"$coef"
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
      case Prefix(name, _, sub) => {
        val str = ueString(sub)
        val s = if (ueAtomicString(sub)) str else s"($str)"
        s"${name}-$s"
      }
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
      case _ => {
        // This should never execute
        abort(s"Undefined Unit Type: ${typeName(typeU)}")
        ""
      }
    }
  }

  def unitExprString[U: WeakTypeTag]: Tree = {
    val tpeU = fixType(weakTypeOf[U])
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

  def mapToType(map: Map[TypeKey, Int]): Type = {
    val vec = map.toVector.map { case (TypeKey(t), e) => (t, e) }
    val ePos = vec.filter { case(_, e) => e > 0 }.sortBy { case (_, e) => e }
    val eNeg = vec.filter { case(_, e) => e < 0 }
      .map { case (u, e) => (u -> -e) }.sortBy { case (_, e) => e }
    val tPos = seqToType(ePos)
    val tNeg = seqToType(eNeg)
    if (eNeg.isEmpty) tPos else appliedType(divType, List(tPos, tNeg))
  }

  def unitExprMul[U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])

    val (coef1, map1) = canonical(tpeU1)
    val (coef2, map2) = canonical(tpeU2)

    val mt = mapToType(mapMul(map1, map2))

    val coef = (coef1 * coef2).toDouble
    val cq = q"$coef"

    q"""
      new _root_.com.manyangled.coulomb.UnitExprMul[$tpeU1, $tpeU2] {
        type U = $mt
        def coef = $cq
      }
    """
  }

  def unitExprDiv[U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])

    val (coef1, map1) = canonical(tpeU1)
    val (coef2, map2) = canonical(tpeU2)

    val mt = mapToType(mapDiv(map1, map2))

    val coef = (coef1 / coef2).toDouble
    val cq = q"$coef"

    q"""
      new _root_.com.manyangled.coulomb.UnitExprDiv[$tpeU1, $tpeU2] {
        type U = $mt
        def coef = $cq
      }
    """
  }

  def unitDecl(annottees: c.Expr[Any]*): c.Expr[Any] = {
    val (qname, qcoef) = c.prefix.tree match {
      // todo: type-arg list to unitDecl is actually empty here.
      // figure out how to find type of $coef and get implicit ConvertableFrom object
      // instead of calling the Rational factory function here
      case q"new unitDecl[..$_]($qname, $coef)" => (qname, q"spire.math.Rational($coef)")
      case _ => abort("Unexpected calling scope!")
    }
    val unitName = annottees.map(_.tree) match {
      case (decl: ClassDef) :: Nil => decl match {
        case q"trait $uname extends DerivedUnit[$_]" => uname
        case q"trait $uname extends PrefixUnit" => uname
        case _ => abort("unitDecl must be applied to a unit or prefix trait")
      }
      case _ => abort("unitDecl must be applied to a unit or prefix trait")
    }
    c.Expr(q"""
      ${annottees(0)}
      object ${unitName.toTermName} extends
          com.manyangled.coulomb.UnitCompanion[$unitName]($qname, $qcoef)
    """)
  }

  def baseUnitDecl(annottees: c.Expr[Any]*): c.Expr[Any] = {
    val qname = c.prefix.tree match {
      case q"new baseUnitDecl($qname)" => qname
      case _ => abort("Unexpected calling scope!")
    }
    val unitName = annottees.map(_.tree) match {
      case (decl: ClassDef) :: Nil => decl match {
        case q"trait $uname extends BaseUnit" => uname
        case _ => abort("baseUnitDecl must be applied to a base unit trait")
      }
      case _ => abort("baseUnitDecl must be applied to a base unit trait")
    }
    c.Expr(q"""
      ${annottees(0)}
      object ${unitName.toTermName} extends
          com.manyangled.coulomb.UnitCompanion[$unitName]($qname, spire.math.Rational(1))
    """)
  }

  def tempUnitDecl(annottees: c.Expr[Any]*): c.Expr[Any] = {
    val (qname, qcoef, qoff) = c.prefix.tree match {
      case q"new tempUnitDecl[..$_]($qname, $coef, $off)" =>
        (qname, q"spire.math.Rational($coef)", q"spire.math.Rational($off)")
      case _ => abort("Unexpected calling scope!")
    }
    val unitName = annottees.map(_.tree) match {
      case (decl: ClassDef) :: Nil => decl match {
        case q"trait $uname extends BaseTemperature" => uname
        case q"trait $uname extends DerivedTemperature" => uname
        case _ => abort("unitDecl must be applied to a temperature unit trait")
      }
      case _ => abort("unitDecl must be applied to a temperature unit trait")
    }
    c.Expr(q"""
      ${annottees(0)}
      object ${unitName.toTermName} extends
          com.manyangled.coulomb.TempUnitCompanion[$unitName]($qname, $qcoef, $qoff)
    """)
  }
}
