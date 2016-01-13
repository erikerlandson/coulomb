package com.manyangled

import com.manyangled.church.{ Integer, IntegerValue }
import Integer.{ _0, _1 }

package unit4s {
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

    final def apply(value: Double = 1.0)(implicit udef: UnitDef[U]): UnitValue1[U, _1] = UnitValue1[U, _1](value)

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

  case class RHS[U1 <: Unit[U1], U2 <: Unit[U2], U3 <: Unit[U3], UA1 <: Unit[UA1], UA2 <: Unit[UA2], UA3 <: Unit[UA3], UN1 <: Unit[UN1], UN2 <: Unit[UN2], UN3 <: Unit[UN3], P1 <: Integer, P2 <: Integer, P3 <: Integer](value: Double)(implicit
    udef1: UnitDef[U1], udef1a: UnitDef[UA1], udef1n: UnitDef[UN1], iv1: IntegerValue[P1],
    udef2: UnitDef[U2], udef2a: UnitDef[UA2], udef2n: UnitDef[UN2], iv2: IntegerValue[P2],
    udef3: UnitDef[U3], udef3a: UnitDef[UA3], udef3n: UnitDef[UN3], iv3: IntegerValue[P3])


  object RHS {
    import Unit.factor

    implicit def idf1[U1 <: Unit[U1], U2 <: Unit[U2], U3 <: Unit[U3], Ua <: Unit[Ua], Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
      udef1: UnitDef[U1], udef2: UnitDef[U2], udef3: UnitDef[U3],
      udefa: UnitDef[Ua],
      iva: IntegerValue[Pa],
      eq1a: Ua#RU =:= U1#RU): RHS[U1, U2, U3,  U1, U$, U$,  U$, U$, U$,  Pa, _0, _0] = {
      val fp = Seq((factor[Ua, U1], iva.value))
      RHS[U1, U2, U3,  U1, U$, U$,  U$, U$, U$,  Pa, _0, _0](fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) })
    }

    implicit def idf2[U1 <: Unit[U1], U2 <: Unit[U2], U3 <: Unit[U3], Ua <: Unit[Ua], Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
      udef1: UnitDef[U1], udef2: UnitDef[U2], udef3: UnitDef[U3],
      udefa: UnitDef[Ua],
      iva: IntegerValue[Pa],
      ne1a: Ua#RU =!= U1#RU, ne2a: Ua#RU =!= U2#RU, ne3a: Ua#RU =!= U3#RU): RHS[U1, U2, U3,  U$, U$, U$,  Ua, U$, U$,  Pa, _0, _0] = {
      val fp = Seq((1.0, 0))
      RHS[U1, U2, U3,  U$, U$, U$,  Ua, U$, U$,  Pa, _0, _0](fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) })
    }

    
  }

  case class UnitValue1[U1 <: Unit[U1], P1 <: Integer](value: Double)(implicit
    udef1: UnitDef[U1], iv1: IntegerValue[P1]) {

    def +(uv: UnitValue1[U1, P1]) = UnitValue1[U1, P1](value + uv.value)
    def -(uv: UnitValue1[U1, P1]) = UnitValue1[U1, P1](value - uv.value)

    def *(v: Double) = UnitValue1[U1, P1](v * value)

    def *[Pa <: Integer](rhs: RHS[U1, U$, U$,  U1, U$, U$,  U$, U$, U$,  Pa, _0, _0])(implicit
      iva: IntegerValue[P1#Add[Pa]]
    ): UnitValue1[U1, P1#Add[Pa]] = UnitValue1[U1, P1#Add[Pa]](value * rhs.value)

    def *[Ua <: Unit[Ua], Pa <: Integer](rhs: RHS[U1, U$, U$,  U$, U$, U$,  Ua, U$, U$,  Pa, _0, _0])(implicit
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

  object codegen {
    def writeString(str: String, path: String) {
      import scalax.io._
      Resource.fromFile(path).write(str)(Codec.UTF8)
    }

    val abase: Int = 'a'.toInt
    def letter(j: Int) = (abase + j).toChar

    def rhsFile(V: Int, fName: String) {
      val defList = rhsImplicitDefSeq(V)
      println(s"generated ${defList.length} implicit function definitions")
      val defs = defList.mkString("\n")
      val code = s"""|/* THIS FILE WAS MACHINE GENERATED, DO NOT EDIT */
        |package com.manyangled
        |
        |import com.manyangled.church.{ Integer, IntegerValue }
        |import Integer.{ _0, _1 }
        |
        |package unit4s {
        |  import scala.language.implicitConversions
        |
        |  object RHS {
        |$defs
        |  }
        |}""".stripMargin
      scalax.file.Path.fromString(fName).deleteIfExists()
      writeString(code, fName)
    }

    // V is maximum unit valence
    def rhsImplicitDefSeq(V: Int): Seq[String] = {
      val rU = (1 to V).map { j => s"U$j" }
      val rudef = (1 to V).map { j => s"udef${j}: UnitDef[U${j}]" }.mkString(", ")
      val defs = scala.collection.mutable.ArrayBuffer.empty[String]
      (1 to V).foreach { v =>
        // v is current valence of input: >= 1, <= V
        val iU = (0 until v).map { j => "U" + letter(j) }
        val iP = (0 until v).map { j => "P" + letter(j) }
        val iUP = iU.zip(iP)
        val iSig = iUP.map { case (u, p) => s"$u, $p" }.mkString(", ")
        val fSig = ((rU ++ iU).map { tn => s"$tn <: Unit[$tn]" } ++ iP.map { tn => s"$tn <: Integer" }).mkString(", ")
        val pP = iP ++ Seq.fill(V - v) { "_0" }
        val iudef = (0 until v).map { j => s"udef${letter(j)}: UnitDef[U${letter(j)}]" }.mkString(", ")
        val iiv = (0 until v).map { j => s"iv${letter(j)}: IntegerValue[P${letter(j)}]" }.mkString(", ")
        (0 to v).foreach { a =>
          // a is current number aligned, >= 0, <= v
          // u is current number un-aligned: >= 0, <= v
          val u = v - a
          val uU = (a until v).map { j => s"U${letter(j)}" } ++ Seq.fill(V - u) { "U$" }
          // (V) choose (a) ways to be aligned
          (0 until V).combinations(a).foreach { aj =>
            val aU = Seq.tabulate(V) { j => if (aj.contains(j)) s"U${j + 1}" else "U$" }
            val rhsSig = (rU ++ aU ++ uU ++ pP).mkString(", ")
            (0 until v).combinations(a).foreach { aij =>
              val uij = (0 until v).filter { j => !aij.contains(j) }
              val rune = (for (ju <- uij; j <- (0 until V)) yield { s"U${letter(ju)}#RU =!= U${j + 1}#RU" }).mkString(", ")
              aij.permutations.foreach { ajp =>
                val rueq = ajp.zip(aj).map { case (ja, ju) => s"U${letter(ja)}#RU =:= U${ju + 1}#RU" }.mkString(", ")
                val eqne = if (rune.isEmpty) rueq else if (rueq.isEmpty) rune else (rune + ",\n      " + rueq)
                val fSeq = ajp.zip(aj).map { case (ja, ju) => s"(factor[U${letter(ja)}, U${ju + 1}], iv${letter(ja)}.value)" }
                val vCode = if (fSeq.length == 0) "val v = uv.value" else s"""val fp = Seq(${fSeq.mkString(", ")})
                  |      val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }""".stripMargin
                val fCode = s"""
                  |    implicit def rhs$$v${v}a${a}c${aj.mkString("")}p${ajp.mkString("")}[$fSig](uv: UnitValue$v[$iSig])(implicit
                  |      $rudef,
                  |      $iudef,
                  |      $iiv,
                  |      $eqne)[$rhsSig] = {
                  |      $vCode
                  |      RHS[$rhsSig](v)
                  |    }""".stripMargin
                //println(fCode)
                defs += fCode
              }
            }
          }
        }
      }
      defs
    }
  }
}
