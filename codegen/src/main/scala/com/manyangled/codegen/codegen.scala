package com.manyangled

object codegen {
  def writeString(str: String, path: String) {
    import scalax.io._
    Resource.fromFile(path).write(str)(Codec.UTF8)
  }

  val abase: Int = 'a'.toInt
  def letter(j: Int) = (abase + j).toChar

  def rhsFile(V: Int, fName: String) {
    scalax.file.Path.fromString(fName).deleteIfExists()

    val defList = rhsImplicitDefSeq(V)
    println(s"generated ${defList.length} implicit function definitions")
    val defs = defList.mkString("\n")
    val ccDef = rhsClassDef(V)
    val code = s"""|/* THIS FILE WAS MACHINE GENERATED, DO NOT EDIT */
      |package com.manyangled.unit4s
      |
      |import scala.language.implicitConversions
      |
      |import com.manyangled.church.{ Integer, IntegerValue }
      |import Integer.{ _0, _1 }
      |import Unit.factor
      |
      |$ccDef
      |
      |object RHS {
      |$defs
      |}""".stripMargin

    writeString(code, fName)
  }

  def rhsClassDef(V: Int): String = {
    val uS = (1 to V).map { j => s"U$j" }
    val uaS = (1 to V).map { j => s"UA$j" }
    val unS = (1 to V).map { j => s"UN$j" }
    val pS = (1 to V).map { j => s"P$j" }
    val uSig = (uS ++ uaS ++ unS).map { u => s"$u <: Unit[$u]" }
    val pSig = pS.map { p => s"$p <: Integer" }
    val ccSig = (uSig ++ pSig).mkString(", ")
    val udef = (1 to V).map { j => s"udef${j}: UnitDef[U$j]" }.mkString(", ")
    val udefa = (1 to V).map { j => s"udef${j}a: UnitDef[UA$j]" }.mkString(", ")
    val udefn = (1 to V).map { j => s"udef${j}n: UnitDef[UN$j]" }.mkString(", ")
    val ivdef = (1 to V).map { j => s"iv${j}: IntegerValue[P$j]" }.mkString(", ")
    s"""case class RHS[$ccSig](value: Double)(implicit
      |  $udef,
      |  $udefa,
      |  $udefn,
      |  $ivdef)""".stripMargin
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
            val rune = (for (ju <- uij; j <- (0 until V)) yield { s"ne${letter(ju)}${j + 1}: U${letter(ju)}#RU =!= U${j + 1}#RU" }).mkString(", ")
            aij.permutations.foreach { ajp =>
              val rueq = ajp.zip(aj).map { case (ja, ju) => s"eq${letter(ja)}${ju + 1}: U${letter(ja)}#RU =:= U${ju + 1}#RU" }.mkString(", ")
              val eqne = if (rune.isEmpty) rueq else if (rueq.isEmpty) rune else (rune + ",\n    " + rueq)
              val fSeq = ajp.zip(aj).map { case (ja, ju) => s"(factor[U${letter(ja)}, U${ju + 1}], iv${letter(ja)}.value)" }
              val vCode = if (fSeq.length == 0) "val v = uv.value" else s"""val fp = Seq(${fSeq.mkString(", ")})
                |    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }""".stripMargin
              val fCode = s"""
                |  implicit def rhs$$v${v}a${a}c${aj.mkString("")}p${ajp.mkString("")}[$fSig](uv: UnitValue$v[$iSig])(implicit
                |    $rudef,
                |    $iudef,
                |    $iiv,
                |    $eqne): RHS[$rhsSig] = {
                |    $vCode
                |    RHS[$rhsSig](v)
                |  }""".stripMargin
              //println(fCode)
              defs += fCode
            }
          }
        }
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
    ""
  }

  def uvImplicitDefSeq(v: Int): Seq[String] = {
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val uS = (1 to v).map { j => s"U$j" }
    val pS = (1 to v).map { j => s"P$j" }
    val iS = (0 until v).map { j => s"U${letter(j)}" }
    val fSig = ((iS ++ uS).map { u => s"$u <: Unit[$u]" } ++ pS.map { p => s"$p <: Integer" }).mkString(", ")
    val oSig = uS.zip(pS).map { case (u, p) => s"$u, $p" }.mkString(", ")
    val idef = (0 until v).map { j => s"udef${letter(j)}: UnitDef[U${letter(j)}]" }.mkString(", ")
    val odef = (1 to v).map { j => s"udef$j: UnitDef[U$j]" }.mkString(", ")
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
    defs
  }
}
