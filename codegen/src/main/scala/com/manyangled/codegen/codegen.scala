package com.manyangled

object codegen {
  def writeString(str: String, path: String) {
    import scalax.io._
    Resource.fromFile(path).write(str)(Codec.UTF8)
  }

  def cross[A, B](sa: TraversableOnce[A], sb: TraversableOnce[B]): Iterator[(A, B)] =
    for(
      a <- sa.toIterator;
      b <- sb.toIterator
    ) yield (a, b)

  val abase: Int = 'a'.toInt
  def letter(j: Int) = (abase + j).toChar

  def padstr(n: Int, c: Char = ' ') = Seq.fill(n)(c).mkString("")

  def params(pseq: Seq[Seq[String]], imp: Boolean = false, indent: Int = 2, sep: String = " "): String = {
    require(sep == " " || sep == "\n")
    val lines = pseq.filter(_.length > 0).map(_.mkString(", "))
    if (lines.isEmpty) "" else {
      val pad = if (indent > 0 && sep == "\n") padstr(indent) else ""
      val ipre = if (imp) s"implicit$sep$pad" else ""
      val pcode = ipre + lines.mkString(s",$sep$pad")
      s"($pcode)"
    }
  }

  def rhsFile(V: Int, fName: String) {
    scalax.file.Path.fromString(fName).deleteIfExists()

    val defList = rhsClassDef(V)
    println(s"generated ${defList.length} RHS class definitions")
    val code = s"""|/* THIS FILE WAS MACHINE GENERATED, DO NOT EDIT */
      |package com.manyangled.unit4s
      |
      |import scala.language.implicitConversions
      |
      |import com.manyangled.church.{ Integer, IntegerValue }
      |import Integer.{ _0, _1 }
      |import Unit.factor
      |
      |${defList.mkString("\n")}
      |""".stripMargin

    writeString(code, fName)
  }

  def rhsClassDef(V: Int): Seq[String] = {
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val rU = (1 to V).map { j => s"U$j" }
    val rUrec = rU.map { t => s"urec$t: UnitRec[$t]" }
    (1 to V).foreach { v =>
      (0 to v).foreach { a =>
        val u = v - a
        val uU = (0 until u).map { j => s"U${letter(j)}" }
        val uP = (0 until u).map { j => s"P${letter(j)}" }
        val uUrec = uU.map { t => s"urec$t: UnitRec[$t]" }
        val uPiv = uP.map { t => s"iv$t: IntegerValue[$t]" }
        (0 until V).combinations(a).foreach { aj =>
          val aP = aj.map { j => s"P${j + 1}" }
          val aPiv = aP.map { t => s"iv$t: IntegerValue[$t]" }
          val rhsName = s"""RHSv${v}a${a}s${aj.mkString("")}"""
          val rhsSig = Seq(rU.map { t => s"$t <: Unit" }, aP.map { t => s"$t <: Integer" }, uU.map { t => s"$t <: Unit" }, uP.map { t => s"$t <: Integer" })
            .fold(Seq.empty[String])(_ ++ _).mkString(", ")
          val imps = Seq(rUrec, aPiv, uUrec, uPiv).filter(_.length > 0).map(_.mkString(", ")).mkString(",\n  ")
          val impDef = rhsImplicitDefs(V, v, aj).mkString("\n\n")
          val code = s"""
            |class $rhsName[$rhsSig](val value: Double)(implicit
            |  $imps)
            |
            |object $rhsName {
            |$impDef
            |}""".stripMargin
          defs += code
        }
      }
    }
    defs
  }

  def rhsImplicitDefs(V: Int, v: Int, aj: Seq[Int]): Seq[String] = {
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val a = aj.length
    val u = v - a
    val rU = (1 to V).map { j => s"U$j" }
    val rUa = aj.map { j => rU(j) }
    val rUrec = rU.map { t => s"urec$t: UnitRec[$t]" }
    val uvU = (0 until v).map { j => "U" + letter(j) }
    val uvP = (0 until v).map { j => "P" + letter(j) }
    val uvSig = uvU.zip(uvP).map { case (u, p) => s"$u, $p" }.mkString(", ")
    val rhsName = s"""RHSv${v}a${a}s${aj.mkString("")}"""
    val uvUrec = uvU.map { t => s"urec$t: UnitRec[$t]" }
    val uvPiv = uvP.map { p => s"iv$p: IntegerValue[$p]" }
    val fSig = ((rU ++ uvU).map { t => s"$t <: Unit" } ++ uvP.map { t => s"$t <: Integer" }).mkString(", ")
    (0 until v).combinations(a).foreach { aij =>
      val uij = (0 until v).filter { j => !aij.contains(j) }
      val (uvUa, uvPa) = (aij.map { j => uvU(j) }, aij.map { j => uvP(j) })
      val (uvUu, uvPu) = (uij.map { j => uvU(j) }, uij.map { j => uvP(j) })
      val rune = for (uu <- uvUu; ru <- rU) yield s"ne$uu$ru: ${uu}#RU =!= ${ru}#RU"
      val rhsSig = (rU ++ uvPa ++ uvUu ++ uvPu).mkString(", ")
      aij.permutations.foreach { ajp =>
        val uvUp = ajp.map { j => uvU(j) }
        val uvPp = ajp.map { j => uvP(j) }
        val rueq = uvUp.zip(rUa).map { case (iu, ru) => s"eq$iu$ru: ${iu}#RU =:= ${ru}#RU" }
        val fSeq = uvUp.zip(rUa).zip(uvPp).map { case ((iu, ru), ip) => s"(factor[$iu, $ru], iv${ip}.value)" }
        val imps = Seq(rUrec, uvUrec, uvPiv, rueq, rune).filter(_.length > 0).map(_.mkString(", ")).mkString(",\n    ")
        val fName = s"""rhs$$v${v}a${a}s${aj.mkString("")}c${aij.mkString("")}p${ajp.mkString("")}"""
        val vCode = if (fSeq.length == 0) "val v = uv.value" else s"""val fp = Seq(${fSeq.mkString(", ")})
           |    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }""".stripMargin
        val fCode = s"""|  implicit def $fName[$fSig](uv: UnitValue$v[$uvSig])(implicit
          |    $imps
          |  ): $rhsName[$rhsSig] = {
          |    $vCode
          |    new $rhsName[$rhsSig](v)
          |  }""".stripMargin
        defs += fCode
      }
    }
    defs
  }

  def uvFile(V: Int, fName: String) {
    scalax.file.Path.fromString(fName).deleteIfExists()

    val head = s"""|/* THIS FILE WAS MACHINE GENERATED, DO NOT EDIT */
      |package com.manyangled.unit4s
      |
      |import scala.language.implicitConversions
      |
      |import com.manyangled.church.{ Integer, IntegerValue }
      |import Integer.{ _0, _1 }
      |import Unit.factor
      |""".stripMargin

    writeString(head, fName)

    (1 to V).foreach { v =>
      val cdef = uvClassDef(v, V)
      writeString("\n", fName)
      writeString(cdef, fName)
      val defs = uvImplicitDefSeq(v).mkString("\n")
      val code = s"""
        |object UnitValue$v {
        |  $defs
        |}""".stripMargin
      writeString("\n", fName)
      writeString(code, fName)
    }
  }

  def uvClassDef(v: Int, V: Int): String = {
    val uS = (1 to v).map { j => s"U$j" }
    val pS = (1 to v).map { j => s"P$j" }
    val ccSig = uS.zip(pS).map { case (u, p) => s"$u <: Unit, $p <: Integer" }.mkString(", ")
    val uvSig = uS.zip(pS).map { case (u, p) => s"$u, $p" }.mkString(", ")
    val urec = (1 to v).map { j => s"urec$j: UnitRec[U$j]" }.mkString(", ")
    val iiv = (1 to v).map { j => s"iv$j: IntegerValue[P$j]" }.mkString(", ")
    val uvx = s"UnitValue$v[$uvSig]"
    val tsSeq = (1 to v).map { j => s"(urec${j}.name, iv${j}.value)" }.mkString(", ")
    val timesDefs = uvTimes(v, V).mkString("\n")
    s"""
    |case class UnitValue$v[$ccSig](value: Double)(implicit
    |  $urec,
    |  $iiv) {
    |
    |  override def toString = Unit.toString(value, Seq($tsSeq))
    |
    |  def +(uv: $uvx): $uvx = $uvx(value + uv.value)
    |  def -(uv: $uvx): $uvx = $uvx(value - uv.value)
    |
    |  def *(v: Double): $uvx = $uvx(value * v)
    |
    |$timesDefs
    |}""".stripMargin
  }

  def uvTimes(v: Int, V: Int): Seq[String] = {
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val rUSig = (1 to v).map { j => s"U$j" }
    val rU = rUSig ++ Seq.fill(V - v)("UZ")
    (1 to V).foreach { vi =>
      val amax = math.min(v, vi)
      val umax = math.min(V - v, vi)
      cross(0 to amax, 0 to umax)
        .filter { case (a, u) => (a + u == vi) && (v + u <= V) }
        .foreach { case (a, u) =>
        val vo = v + u
        val uU = (0 until u).map { j => s"U${letter(j)}" }
        val uQ = (0 until u).map { j => s"Q${letter(j)}" }
        (0 until v).combinations(a).foreach { aj =>
          val aQ = aj.map { j => s"Q${j + 1}" }
          val fSig = (aQ.map(_ + " <: Integer") ++ uU.map(_ + " <: Unit") ++ uQ.map(_ + " <: Integer")).mkString(", ")
          val rhsName = s"""RHSv${vi}a${a}s${aj.mkString("")}"""
          val rhsSig = (rU ++ aQ ++ uU ++ uQ).mkString(", ")
          val pq = aj.map { j => s"P${j + 1}" }.zip(aQ).map { case (p, q) => s"$p#Add[$q]" }
          val aP = Seq.tabulate(v) { j => if (aj.contains(j)) s"${pq(aj.indexOf(j))}" else s"P${j + 1}" }
          val oSig = (rUSig ++ uU).zip(aP ++ uQ).map { case (u, p) => s"$u, $p" }.mkString(", ")
          val urecs = uU.map { u => s"urec$u: UnitRec[$u]" }.mkString(", ")
          val ivs = uQ.map { q => s"iv$q: IntegerValue[$q]" }.mkString(", ")
          val ivs2 = (0 until a).map { j => s"ivpq$j: IntegerValue[${pq(j)}]" }.mkString(", ")
          val imps = Seq(urecs, ivs, ivs2).filter(_.length > 0).mkString(",\n    ")
          defs += s"""
            |  def *[$fSig](rhs: $rhsName[$rhsSig])(implicit
            |    $imps): UnitValue$vo[$oSig] =
            |    UnitValue$vo[$oSig](value * rhs.value)""".stripMargin
        }
      }
    }
    defs
  }

  def uvImplicitDefSeq(v: Int): Seq[String] = {
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val uS = (1 to v).map { j => s"U$j" }
    val pS = (1 to v).map { j => s"P$j" }
    val iS = (0 until v).map { j => s"U${letter(j)}" }
    val fSig = ((iS ++ uS).map { u => s"$u <: Unit" } ++ pS.map { p => s"$p <: Integer" }).mkString(", ")
    val oSig = uS.zip(pS).map { case (u, p) => s"$u, $p" }.mkString(", ")
    val idef = (0 until v).map { j => s"urec${letter(j)}: UnitRec[U${letter(j)}]" }.mkString(", ")
    val odef = (1 to v).map { j => s"urec$j: UnitRec[U$j]" }.mkString(", ")
    val iiv = (1 to v).map { j => s"iv$j: IntegerValue[P$j]" }.mkString(", ")
    val rueq = (0 until v).map { j => s"eq${letter(j)}${j + 1}: U${letter(j)}#RU =:= U${j + 1}#RU" }.mkString(", ")
    val fSeq = (0 until v).map { j => s"(factor[U${letter(j)}, U${j + 1}], iv${j + 1}.value)" }
    val vCode = s"""val fp = Seq(${fSeq.mkString(", ")})
      |    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }""".stripMargin

    (0 until v).permutations.foreach { jp =>
      val iSig = jp.map { j => s"U${letter(j)}, P${j + 1}" }.mkString(", ")
      val fCode = s"""
        |  implicit def uvc$$v${v}p${jp.mkString("")}[$fSig](uv: UnitValue$v[$iSig])(implicit
        |    $idef,
        |    $odef,
        |    $iiv,
        |    $rueq): UnitValue$v[$oSig] = {
        |    $vCode
        |    UnitValue$v[$oSig](v)
        |  }""".stripMargin
      defs += fCode
    }
    if (v == 1) {
      // I can only get this to work right when UnitRec is contravariant so that
      // unit companion classes can access their parent UnitRec implicits
      val fCode = s"""
        |  implicit def fromObj[Ua <: Unit, U1 <: Unit](u: Ua)(implicit
        |    ureca: UnitRec[Ua],
        |    urec1: UnitRec[U1],
        |    eqa1: Ua#RU =:= U1#RU): UnitValue1[U1, _1] =
        |    UnitValue1[U1, _1](factor[Ua, U1])"""
      defs += fCode
    }
    defs
  }
}
