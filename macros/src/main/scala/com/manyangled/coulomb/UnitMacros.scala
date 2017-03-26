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

import spire.math._

@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class unitDecl(val name: String, val coef: Rational = 1) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.unitDecl
}

@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class tempUnitDecl(name: String, coef: Rational, off: Rational) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.tempUnitDecl
}

private [coulomb] class UnitMacros(c0: whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  implicit val liftRational = Liftable[Rational] { r =>
    val rnStr = r.numerator.toBigInt.toString
    val rdStr = r.denominator.toBigInt.toString
    q"spire.math.Rational.apply(BigInt($rnStr), BigInt($rdStr))"
  }

  implicit val liftBigInt = Liftable[BigInt] { v =>
    q"BigInt(${v.toString})"
  }

  implicit val liftBigDecimal = Liftable[BigDecimal] { v =>
    q"BigDecimal(${v.toString})"
  }

  trait DummyU extends BaseUnit
  trait DummyT extends BaseTemperature

  val ivalType = typeOf[ChurchIntValue[ChurchInt._0]].typeConstructor
  val urecType = typeOf[UnitRec[DummyU]].typeConstructor
  val turecType = typeOf[TempUnitRec[DummyT]].typeConstructor
  val cuType = typeOf[CompatUnits[DummyU, DummyU]].typeConstructor
  val isIntType = typeOf[spire.algebra.IsIntegral[Int]].typeConstructor
  val cToType = typeOf[spire.math.ConvertableTo[Int]].typeConstructor
  val uesType = typeOf[UnitExprString[DummyU]].typeConstructor

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

  def uesVal(unitT: Type): String = {
    val uest = appliedType(uesType, List(unitT))
    val ues = c.inferImplicitValue(uest, silent = false)
    evalTree[String](q"${ues}.str")
  }

  def cuTree(u1T: Type, u2T: Type): Tree = {
    try {
      val cut = appliedType(cuType, List(u1T, u2T))
      c.inferImplicitValue(cut, silent = false)
    } catch {
      case _: Throwable => abort(s"Imcompatible unit types:\n$u1T\nand\n$u2T")
    }
  }

  def isIntegral(nT: Type): Boolean = {
    try {
      val t = appliedType(isIntType, List(nT))
      c.inferImplicitValue(t, silent = false)
      true
    } catch {
      case _: Throwable => false
    }
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
        // defensive: this should be caught at compile time in unitDecl
        if (coef <= 0) abort(s"Unit coefficient for $typeU was <= 0")
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

  def signature(typeU: Type): Map[TypeKey, Int] = {
    typeU.dealias match {
      case IsUnitless() => Map.empty[TypeKey, Int]
      case FUnit(_) => Map(TypeKey(typeU) -> 1)
      case DUnit(_, _, _) => Map(TypeKey(typeU) -> 1)
      case MulOp(lsub, rsub) => mapMul(signature(lsub), signature(rsub))
      case DivOp(lsub, rsub) => mapDiv(signature(lsub), signature(rsub))
      case PowOp(bsub, exp) => mapPow(signature(bsub), exp)
      case _ => {
        // This should never execute
        abort(s"Undefined Unit Type: ${typeName(typeU)}")
        Map.empty[TypeKey, Int]
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
      val coef = coef1 / coef2
      q"""
        new _root_.com.manyangled.coulomb.CompatUnits[$tpeU1, $tpeU2]($coef)
      """
    }
  }

  def cuCoef(cu: Tree): Rational = {
    // I'm doing it this way because evaluating Rational(n,d) expressions w/ evalTree
    // directly is failing inside the macro context.
    val q"new $_.CompatUnits[..$_]($_.Rational.apply($nq, $dq))" = cu
    val (n, d) = (evalTree[BigInt](nq), evalTree[BigInt](dq))
    Rational(n, d)
  }

  // This is hackery to evaluate the user's value for coefficient in the unit
  // declaration at compile time.  If I could figure out a way to evaluate
  // general expressions, it would be way cleaner and more robust.
  def treeToRational(t: Tree): Rational = {
    try {
      t match {
        case q"$_.Rational($arg1, $arg2)" => {
          val a1 = evalTree[String](q"${arg1}.toString")
          val a2 = evalTree[String](q"${arg2}.toString")
          Rational(BigInt(a1), BigInt(a2))
        }
        case q"Rational($arg1, $arg2)" => {
          val a1 = evalTree[String](q"${arg1}.toString")
          val a2 = evalTree[String](q"${arg2}.toString")
          Rational(BigInt(a1), BigInt(a2))
        }
        case q"$_.Rational($arg)" => {
          val a = evalTree[String](q"${arg}.toString")
          Rational(BigDecimal(a))
        }
        case q"Rational($arg)" => {
          val a = evalTree[String](q"${arg}.toString")
          Rational(BigDecimal(a))
        }
        case q"${arg}.toRational" => {
          val a = evalTree[String](q"${arg}.toString")
          Rational(BigDecimal(a))
        }
        case arg => {
          val a = evalTree[String](q"${arg}.toString")
          Rational(BigDecimal(a))
        }
      }
    } catch {
      case e: Throwable => {
        println(s"e= $e")
        abort(s"failed to evaluate expression ($t) as a Rational")
      }
    }
  }

  def cfpTree(coef: Rational, tpeN: Type): Tree = {
    tpeN match {
      case t if (t =:= typeOf[Float]) => {
        val cv = implicitly[ConvertableTo[Float]].fromRational(coef)
        q"$cv"
      }
      case t if (t =:= typeOf[Double]) => {
        val cv = implicitly[ConvertableTo[Double]].fromRational(coef)
        q"$cv"
      }
      case t if (t =:= typeOf[BigDecimal]) => {
        val cv = implicitly[ConvertableTo[BigDecimal]].fromRational(coef)
        q"$cv"
      }
      case t if (t =:= typeOf[Rational]) => {
        q"$coef"
      }
      case _ => abort(s"Unimplemented non-integral type $tpeN")
    }
  }

  def coefLimit(coef: Rational, lim: BigInt): Rational = {
    val clim = coef.limitTo(lim)
    val pdif = ((clim - coef).abs / coef).toDouble * 100.0
    if (pdif > 0.01) c.info(c.enclosingPosition,
      s"WARNING: approximated coefficient $coef with $clim, with $pdif percent error", false)
    clim
  }

  def ndTree(coef: Rational, tpeN: Type): (Tree, Tree) = {
    tpeN match {
      case t if ((t =:= typeOf[Int]) || (t =:= typeOf[Byte]) || (t =:= typeOf[Short])) => {
        // Byte and Short appear to be cast to Int for '*' and '/' anyway
        val cx = coefLimit(coef, 32767)
        val (n, d) = (cx.numerator.toInt, cx.denominator.toInt)
        (q"$n", q"$d")
      }
      case t if (t =:= typeOf[Long]) => {
        val cx = coefLimit(coef, 2147483647L)
        val (n, d) = (cx.numerator.toLong, cx.denominator.toLong)
        (q"$n", q"$d")
      }
      case t if (t =:= typeOf[BigInt]) => {
        val (n, d) = (coef.numerator.toBigInt, coef.denominator.toBigInt)
        (q"$n", q"$d")
      }
      case _ => abort(s"unexpected integral type $tpeN")
    }
  }

  def xCoefTree(coef: Rational, n: Tree, tpeN: Type): Tree = {
    if (coef == 1) n else {
      if (isIntegral(tpeN)) {
        val (nq, dq) = ndTree(coef, tpeN)
        if (coef.isWhole) q"($n) * ($nq)" else q"(($n) * ($nq)) / ($dq)"
      } else {
        val cq = cfpTree(coef, tpeN)
        q"($n) * ($cq)"
      }
    }
  }

  def fixByteShort(et: Tree, tpeN: Type): Tree = (et, tpeN) match {
    case (x, t) if (t =:= typeOf[Byte]) => q"($x).toByte"
    case (x, t) if (t =:= typeOf[Short]) => q"($x).toShort"
    case (x, _) => x
  }

  def toUnitImpl[N: WeakTypeTag, U1: WeakTypeTag, U2: WeakTypeTag]: Tree = {
    val tpeN = fixType(weakTypeOf[N])
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])
    val cu = cuTree(tpeU1, tpeU2)
    val coef = cuCoef(cu)
    val xt = fixByteShort(xCoefTree(coef, q"(${c.prefix.tree}).value", tpeN), tpeN)
    q"new com.manyangled.coulomb.Quantity[$tpeN, $tpeU2]($xt)"
  }

  def addImpl[N: WeakTypeTag, U1: WeakTypeTag, U2: WeakTypeTag](that: Tree): Tree = {
    val tpeN = fixType(weakTypeOf[N])
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])
    val cu = cuTree(tpeU2, tpeU1)
    val coef = cuCoef(cu)
    val xt = xCoefTree(coef, q"($that).value", tpeN)
    val rt = fixByteShort(q"(${c.prefix.tree}.value) + ($xt)", tpeN)
    q"new com.manyangled.coulomb.Quantity[$tpeN, $tpeU1]($rt)"
  }

  def subImpl[N: WeakTypeTag, U1: WeakTypeTag, U2: WeakTypeTag](that: Tree): Tree = {
    val tpeN = fixType(weakTypeOf[N])
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])
    val cu = cuTree(tpeU2, tpeU1)
    val coef = cuCoef(cu)
    val xt = xCoefTree(coef, q"($that).value", tpeN)
    val rt = fixByteShort(q"(${c.prefix.tree}.value) - ($xt)", tpeN)
    q"new com.manyangled.coulomb.Quantity[$tpeN, $tpeU1]($rt)"
  }

  def negImpl[N: WeakTypeTag, U: WeakTypeTag]: Tree = {
    val tpeN = fixType(weakTypeOf[N])
    val tpeU = fixType(weakTypeOf[U])
    val nt = fixByteShort(q"-(${c.prefix.tree}.value)", tpeN)
    q"new com.manyangled.coulomb.Quantity[$tpeN, $tpeU]($nt)"
  }

  def mulImpl[N: WeakTypeTag, U1: WeakTypeTag, U2: WeakTypeTag](that: Tree): Tree = {
    val tpeN = fixType(weakTypeOf[N])
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])
    val (map1, map2) = (signature(tpeU1), signature(tpeU2))
    val mt = mapToType(mapMul(map1, map2))
    val xt = fixByteShort(q"(${c.prefix.tree}.value) * (${that}.value)", tpeN)
    q"new com.manyangled.coulomb.Quantity[$tpeN, $mt]($xt)"
  }

  def divImpl[N: WeakTypeTag, U1: WeakTypeTag, U2: WeakTypeTag](that: Tree): Tree = {
    val tpeN = fixType(weakTypeOf[N])
    val tpeU1 = fixType(weakTypeOf[U1])
    val tpeU2 = fixType(weakTypeOf[U2])
    val (map1, map2) = (signature(tpeU1), signature(tpeU2))
    val mt = mapToType(mapDiv(map1, map2))
    val dt = fixByteShort(q"(${c.prefix.tree}.value) / (${that}.value)", tpeN)
    q"new com.manyangled.coulomb.Quantity[$tpeN, $mt]($dt)"
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

  def unitStrImpl[U: WeakTypeTag]: Tree = {
    val str = uesVal(fixType(weakTypeOf[U]))
    q"$str"
  }

  def strImpl[U: WeakTypeTag]: Tree = {
    val str = unitStrImpl[U]
    q"""${c.prefix.tree}.value.toString + " " + $str"""
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

  def unitDecl(annottees: c.Expr[Any]*): c.Expr[Any] = {
    val (qname, qcoef) = c.prefix.tree match {
      case q"new unitDecl($qname, coef = $coef)" => (qname, coef)
      case q"new unitDecl($qname, $coef)" => (qname, coef)
      case q"new unitDecl($qname)" => (qname, q"spire.math.Rational(1)")
      case _ => abort("Unexpected calling scope!")
    }
    val name = evalTree[String](qname)
    val coef = treeToRational(qcoef)
    if (coef <= 0) abort(s"Coefficient for unit $name must be > 0")
    val unitName = annottees.map(_.tree) match {
      case (decl: ClassDef) :: Nil => decl match {
        case q"trait $uname extends BaseUnit" => {
          if (coef != 1) abort(s"Coefficient for base unit $name must be 1")
          uname
        }
        case q"trait $uname extends PrefixUnit" => uname
        case q"trait $uname extends DerivedUnit[$_]" => uname
        case _ => abort("unitDecl must be applied to a unit trait declaration")
      }
      case _ => abort("unitDecl must be applied to a unit trait declaration")
    }
    c.Expr(q"""
      ${annottees(0)}
      object ${unitName.toTermName} extends
          com.manyangled.coulomb.UnitCompanion[$unitName]($qname, $qcoef)
    """)
  }

  def tempUnitDecl(annottees: c.Expr[Any]*): c.Expr[Any] = {
    val (qname, qcoef, qoff) = c.prefix.tree match {
      case q"new tempUnitDecl($qname, $coef, $off)" => (qname, coef, off)
      case _ => abort("Unexpected calling scope!")
    }
    val name = evalTree[String](qname)
    val coef = treeToRational(qcoef)
    val off = treeToRational(qoff)
    if (coef <= 0) abort(s"Coefficient for unit $name must be > 0")
    val unitName = annottees.map(_.tree) match {
      case (decl: ClassDef) :: Nil => decl match {
        case q"trait $uname extends BaseTemperature" => {
          if (coef != 1) abort(s"Coefficient for base temperature $name must be 1")
          if (off != 0) abort(s"Offset for base temperature $name must be 0")
          uname
        }
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
