package com.manyangled

import com.manyangled.church.{ Integer, IntegerValue }
import Integer.{ _0, _1 }

object unit4s {
  import scala.language.implicitConversions

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

  trait Unit[U <: Unit[U]] {
    type RU <: Unit[_]

    final def apply(value: Double)(implicit udef: UnitDef[U]): UnitValue1[U, _1] = UnitValue1[U, _1](value)

    final def *(value: Double)(implicit udef: UnitDef[U]): UnitValue1[U, _1] = UnitValue1[U, _1](value)
  }

  object Unit {
    def factor[U1 <: Unit[U1], U2 <: Unit[U2]](implicit udef1: UnitDef[U1], udef2: UnitDef[U2], eq: U1#RU =:= U2#RU) = udef1.cfr / udef2.cfr

    def toString(value: Double, units: Seq[(String, Int)]) = {
      val unitstr = units.map { case (name, exp) =>
        if (exp == 1) s"$name" else s"($name)^($exp)"
      }.mkString(" ")
      s"UnitValue${units.length}($value $unitstr)"
    }
  }

  trait UnitDef[U <: Unit[U]] {
    def name: String
    def cfr: Double
  }

  case class UnitSpec[U <: Unit[U]](val name: String, val cfr: Double) extends UnitDef[U]

  case class PrefixUnit[P <: Unit[P], U <: Unit[U]](udef: UnitDef[U], val pfn: String, val pcf: Double) extends UnitDef[P] {
    val name = pfn + udef.name
    val cfr = pcf * udef.cfr
  }

  trait Kilo[U <: Unit[U]] extends Unit[Kilo[U]] {
    type RU = U#RU
  }
  object Kilo {
    implicit def factory[U <: Unit[U]](implicit udef: UnitDef[U]) = PrefixUnit[Kilo[U], U](udef, "kilo", 1000.0)
  }

  trait Meter extends Unit[Meter] {
    type RU = Meter
  }
  object Meter extends Meter {
    implicit val udef = UnitSpec[Meter]("meter", 1.0)
  }

  trait Foot extends Unit[Foot] {
    type RU = Meter
  }
  object Foot extends Foot {
    implicit val udef = UnitSpec[Foot]("foot", 0.3048)
  }

  trait Yard extends Unit[Yard] {
    type RU = Meter
  }
  object Yard extends Yard {
    implicit val udef = UnitSpec[Yard]("yard", 0.9144)
  }

  trait Second extends Unit[Second] {
    type RU = Second
  }
  object Second extends Second {
    implicit val udef = UnitSpec[Second]("second", 1.0)
  }

  trait Minute extends Unit[Minute] {
    type RU = Second
  }
  object Minute extends Minute {
    implicit val udef = UnitSpec[Minute]("minute", 60.0)
  }

  trait U$ <: Unit[U$] {
    type RU = U$
  }
  object U$ {
    implicit val udef = UnitSpec[U$]("U$", 1.0)
  } 

  case class RHS[U1 <: Unit[U1], U2 <: Unit[U2], U3 <: Unit[U3], UA1 <: Unit[UA1], PA1 <: Integer, UA2 <: Unit[UA2], PA2 <: Integer, UA3 <: Unit[UA3], PA3 <: Integer](value: Double)(implicit
    udef1: UnitDef[U1],
    udef2: UnitDef[U2],
    udef3: UnitDef[U3],
    udef1a: UnitDef[UA1], iv1a: IntegerValue[PA1],
    udef2a: UnitDef[UA2], iv2a: IntegerValue[PA2],
    udef3a: UnitDef[UA3], iv3a: IntegerValue[PA3])

  object RHS {
    import Unit.factor

    implicit def idf1[U1 <: Unit[U1], Ua <: Unit[Ua], Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
      udef1: UnitDef[U1],
      udefa: UnitDef[Ua],
      iva: IntegerValue[Pa],
      eq1a: Ua#RU =:= U1#RU): RHS[U1, U$, U$,   U1, Pa, U$, _0, U$, _0] = {
      val fp = Seq((factor[Ua, U1], iva.value))
      RHS[U1, U$, U$,   U1, Pa, U$, _0, U$, _0](fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) })
    }

    implicit def idf2[U1 <: Unit[U1], Ua <: Unit[Ua], Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
      udef1: UnitDef[U1],
      udefa: UnitDef[Ua],
      iva: IntegerValue[Pa],
      ne1a: Ua#RU =!= U1#RU): RHS[U1, U$, U$,   U$, _0, U$, _0, Ua, Pa] = {
      val fp = Seq((1.0, 0))
      RHS[U1, U$, U$,   U$, _0, U$, _0, Ua, Pa](fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) })
    }
  }

  case class UnitValue1[U1 <: Unit[U1], P1 <: Integer](value: Double)(implicit
    udef1: UnitDef[U1], iv1: IntegerValue[P1]) {

    def +(uv: UnitValue1[U1, P1]) = UnitValue1[U1, P1](value + uv.value)
    def -(uv: UnitValue1[U1, P1]) = UnitValue1[U1, P1](value - uv.value)

    def *(v: Double) = UnitValue1[U1, P1](v * value)

    def *[Pa <: Integer](rhs: RHS[U1, U$, U$,  U1, Pa, U$, _0, U$, _0])(implicit
      iva: IntegerValue[P1#Add[Pa]]
    ): UnitValue1[U1, P1#Add[Pa]] = UnitValue1[U1, P1#Add[Pa]](value * rhs.value)

    def *[Ua <: Unit[Ua], Pa <: Integer](rhs: RHS[U1, U$, U$, U$, _0, U$, _0, Ua, Pa])(implicit
      udefa: UnitDef[Ua],
      iva: IntegerValue[Pa]
    ): UnitValue2[U1, P1, Ua, Pa] = UnitValue2[U1, P1, Ua, Pa](value * rhs.value)

    override def toString = Unit.toString(value, Seq((udef1.name, iv1.value)))
  }

  case class UnitValue2[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer](value: Double)(implicit
    udef1: UnitDef[U1], iv1: IntegerValue[P1],
    udef2: UnitDef[U2], iv2: IntegerValue[P2] /*,
    ne12: U1#RU =!= U2#RU */) {

    def +(uv: UnitValue2[U1, P1, U2, P2]) = UnitValue2[U1, P1, U2, P2](value + uv.value)
    def -(uv: UnitValue2[U1, P1, U2, P2]) = UnitValue2[U1, P1, U2, P2](value - uv.value)

    def *(v: Double): UnitValue2[U1, P1, U2, P2] = UnitValue2[U1, P1, U2, P2](value * v)

    override def toString = Unit.toString(value, Seq((udef1.name, iv1.value), (udef2.name, iv2.value)))
  }

  case class UnitValue3[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer, U3 <: Unit[U3], P3 <: Integer](value: Double)(implicit
    udef1: UnitDef[U1], iv1: IntegerValue[P1],
    udef2: UnitDef[U2], iv2: IntegerValue[P2],
    udef3: UnitDef[U3], iv3: IntegerValue[P3] /*,
    ne12: U1#RU =!= U2#RU, ne23: U2#RU =!= U3#RU, ne13: U1#RU =!= U3#RU*/) {

    def +(uv: UnitValue3[U1, P1, U2, P2, U3, P3]) = UnitValue3[U1, P1, U2, P2, U3, P3](value + uv.value)
    def -(uv: UnitValue3[U1, P1, U2, P2, U3, P3]) = UnitValue3[U1, P1, U2, P2, U3, P3](value - uv.value)

    def *(v: Double): UnitValue3[U1, P1, U2, P2, U3, P3] = UnitValue3[U1, P1, U2, P2, U3, P3](value * v)

    override def toString = Unit.toString(value, Seq((udef1.name, iv1.value), (udef2.name, iv2.value), (udef3.name, iv3.value)))
  }



/*
// V = maximum valence
// w is a valence value
(1 to V).foreach { w =>
  // n is number 'unaligned' with existing unit in target
  (0 to w).foreach { n =>
    val a = w - n
    (1 to V-n).combinations(a).foreach { aligned =>
      val unaligned = ((1 to V-n).toSet -- aligned.toSet).toSeq
      // generate implicit function that maps aligned (with conversion) and unaligned
      // unaligned do not contribute to conversion factor
      // unaligned occupy right-most places
      // unaligned have no #RU constraints
      // generate full permutations for all alligned units
    }
  }
}
*/

  object UnitValue1 {
    implicit def fromUnit[U1 <: Unit[U1]](unit: U1)(implicit udef: UnitDef[U1]): UnitValue1[U1, _1] = UnitValue1[U1, _1](1.0)

    implicit def commute1[U1 <: Unit[U1], P1 <: Integer, Ua <: Unit[Ua]](uv: UnitValue1[U1, P1])(implicit
      udef1: UnitDef[U1],
      udefa: UnitDef[Ua],
      eq: U1#RU =:= Ua#RU,
      iv1: IntegerValue[P1]): UnitValue1[Ua, P1] = {
      val f = math.pow(Unit.factor[U1, Ua], iv1.value)
      UnitValue1[Ua, P1](uv.value * f)
    }
  }

  object UnitValue2 {
    implicit def fromUV1pa[U1 <: Unit[U1], P1 <: Integer, Ua <: Unit[Ua], Ub <: Unit[Ub]](uv: UnitValue1[U1, P1])(implicit
      udef1: UnitDef[U1],
      udefa: UnitDef[Ua], udefb: UnitDef[Ub],
      eq1a: U1#RU =:= Ua#RU,
      iv1: IntegerValue[P1]): UnitValue2[Ua, P1, Ub, _0] = {
      val f =
        math.pow(Unit.factor[U1, Ua], iv1.value)
      UnitValue2[Ua, P1, Ub, Integer._0](uv.value * f)
    }

    implicit def fromUV1pb[U1 <: Unit[U1], P1 <: Integer, Ua <: Unit[Ua], Ub <: Unit[Ub]](uv: UnitValue1[U1, P1])(implicit
      udef1: UnitDef[U1],
      udefa: UnitDef[Ua], udefb: UnitDef[Ub],
      eq1a: U1#RU =:= Ub#RU,
      iv1: IntegerValue[P1]): UnitValue2[Ua, Integer._0, Ub, P1] = {
      val f =
        math.pow(Unit.factor[U1, Ub], iv1.value)
      UnitValue2[Ua, Integer._0, Ub, P1](uv.value * f)
    }

    implicit def commute12[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer, Ua <: Unit[Ua], Ub <: Unit[Ub]](uv: UnitValue2[U1, P1, U2, P2])(implicit
      udef1: UnitDef[U1], udef2: UnitDef[U2],
      udefa: UnitDef[Ua], udefb: UnitDef[Ub],
      eq1a: U1#RU =:= Ua#RU, eq2b: U2#RU =:= Ub#RU,
      v1: IntegerValue[P1], v2: IntegerValue[P2]): UnitValue2[Ua, P1, Ub, P2] = {
      val f =
        math.pow(Unit.factor[U1, Ua], Integer.value[P1]) *
        math.pow(Unit.factor[U2, Ub], Integer.value[P2])
      UnitValue2[Ua, P1, Ub, P2](uv.value * f)
    }

    implicit def commute21[U1 <: Unit[U1], P1 <: Integer, U2 <: Unit[U2], P2 <: Integer, Ua <: Unit[Ua], Ub <: Unit[Ub]](uv: UnitValue2[U2, P2, U1, P1])(implicit
      udef1: UnitDef[U1], udef2: UnitDef[U2],
      udefa: UnitDef[Ua], udefb: UnitDef[Ub],
      eq1a: U1#RU =:= Ua#RU, eq2b: U2#RU =:= Ub#RU,
      v1: IntegerValue[P1], v2: IntegerValue[P2]): UnitValue2[Ua, P1, Ub, P2] = {
      val f =
        math.pow(Unit.factor[U1, Ua], Integer.value[P1]) *
        math.pow(Unit.factor[U2, Ub], Integer.value[P2])
      UnitValue2[Ua, P1, Ub, P2](uv.value * f)
    }    
  }

  object test {
    def f1(uv: UnitValue1[Foot, Integer._1]) = uv.value
    def f2(uv: UnitValue2[Foot, Integer._1, Second, Integer._neg1]) = uv.value
  }
}
