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

  def select[A](s: Seq[A], idx: TraversableOnce[Int]): Seq[A] = idx.map(s(_)).toVector

  val abase: Int = 'a'.toInt
  def letter(j: Int) = (abase + j).toChar

  def padstr(n: Int, c: Char = ' ') = Seq.fill(n)(c).mkString

  def pCode(pseq: Seq[Seq[String]], imp: Boolean, ind: Int, sep: Char, enc: Char): String = {
    require(Seq('(', '[').contains(enc))
    require(Seq(' ', '\n').contains(sep))
    val lines = pseq.filter(_.length > 0).map(_.mkString(", "))
    if (lines.isEmpty) "" else {
      val (lp, rp) = if (enc == '(') ('(', ')') else ('[', ']')
      val pad = if (ind > 0 && sep == '\n') padstr(ind) else ""
      val ipre = if (imp) s"implicit$sep$pad" else ""
      val pcode = ipre + lines.mkString(s",$sep$pad")
      s"$lp$pcode$rp"
    }
  }

  def mlparams(pseq: Seq[Seq[String]], imp: Boolean = false, ind: Int = 2): String =
    pCode(pseq, imp, ind, '\n', '(')

  def params(pseq: Seq[String], imp: Boolean = false): String =
    pCode(Seq(pseq), imp, 0, ' ', '(')

  def typesig(tseq: Seq[String]) = pCode(Seq(tseq), false, 0, ' ', '[')

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
    val rP = (1 to V).map { j => s"P$j" }
    val rUrec = rU.map { t => s"urec$t: UnitRec[$t]" }
    (0 to V).foreach { v =>
      (0 to v).foreach { a =>
        val u = v - a
        val uU = (0 until u).map { j => s"U${letter(j)}" }
        val uQ = (0 until u).map { j => s"Q${letter(j)}" }
        val uUrec = uU.map { t => s"urec$t: UnitRec[$t]" }
        val uQiv = uQ.map { t => s"iv$t: IntegerValue[$t]" }
        (0 until V).combinations(a).foreach { aj =>
          val aQ = aj.map { j => s"Q${j + 1}" }
          val aQiv = aQ.map { t => s"iv$t: IntegerValue[$t]" }
          val rhsSig = Seq(rU.map { t => s"$t <: Unit" }, rP.map { t => s"$t <: Integer" }, aQ.map { t => s"$t <: Integer" }, uU.map { t => s"$t <: Unit" }, uQ.map { t => s"$t <: Integer" })
            .fold(Seq.empty[String])(_ ++ _)
          val imps = Seq(rUrec, aQiv, uUrec, uQiv)
          (0 to aj.length).foreach { c =>
            aj.combinations(c).foreach { cj =>
              val rhsName = s"RHSv${v}a${a}s${aj.mkString}c${c}s${cj.mkString}"
              val impDefM = rhsImplicitDefs(V, v, aj, cj, 'M').mkString("\n\n")
              defs += s"""
                |class ${rhsName}M${typesig(rhsSig)}(val value: Double)${mlparams(imps,imp=true)}
                |
                |object ${rhsName}M {
                |$impDefM
                |}""".stripMargin
              val impDefD = rhsImplicitDefs(V, v, aj, cj, 'D').mkString("\n\n")
              defs += s"""
                |class ${rhsName}D${typesig(rhsSig)}(val value: Double)${mlparams(imps,imp=true)}
                |
                |object ${rhsName}D {
                |$impDefD
                |}""".stripMargin
            }
          }
        }
      }
    }
    defs
  }

  def rhsImplicitDefs(V: Int, v: Int, aj: Seq[Int], cj: Seq[Int], op: Char): Seq[String] = {
    require(Seq('M', 'D').contains(op))
    val cmod = if (op == 'M') "#Neg" else ""
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val a = aj.length
    val u = v - a
    val c = cj.length
    val kj = aj.diff(cj)
    val cjj = cj.map { j => aj.indexOf(j) }
    val kjj = kj.map { j => aj.indexOf(j) }
    val rU = (1 to V).map { j => s"U$j" }
    val rP = (1 to V).map { j => s"P$j" }
    val rUa = aj.map { j => rU(j) }
    val rPa = aj.map { j => rP(j) }
    val rUrec = rU.map { t => s"urec$t: UnitRec[$t]" }
    val uvU = (0 until v).map { j => "U" + letter(j) }
    val uvP = (0 until v).map { j => "P" + letter(j) }
    val uvSig = uvU.zip(uvP).map { case (u, p) => s"$u, $p" }
    val uvType = s"UnitValue$v${typesig(uvSig)}"
    val rhsName = s"RHSv${v}a${a}s${aj.mkString}c${c}s${cj.mkString}$op"
    val uvUrec = uvU.map { t => s"urec$t: UnitRec[$t]" }
    val uvPiv = uvP.map { p => s"iv$p: IntegerValue[$p]" }
    val fSig = (rU ++ uvU).map { t => s"$t <: Unit" } ++ (rP ++ uvP).map { t => s"$t <: Integer" }
    (0 until v).combinations(a).foreach { aij =>
      val uij = (0 until v).filter { j => !aij.contains(j) }
      val (uvUa, uvPa) = (aij.map { j => uvU(j) }, aij.map { j => uvP(j) })
      val (uvUu, uvPu) = (uij.map { j => uvU(j) }, uij.map { j => uvP(j) })
      val rune = for (uu <- uvUu; ru <- rU) yield s"ne$uu$ru: ${uu}#RU =!= ${ru}#RU"
      aij.permutations.foreach { ajp =>
        val uvUp = ajp.map { j => uvU(j) }
        val uvPp = ajp.map { j => uvP(j) }
        val rueq = uvUp.zip(rUa).map { case (iu, ru) => s"eq$iu$ru: ${iu}#RU =:= ${ru}#RU" }
        //val cqp = cjj.map { j => uvPp(j) }.zip(cjj.map { j => rPa).map { case (ip, rp) => s"cqp$ip: $ip =:= $rp$cmod" }
        val cqp = select(uvPp, cjj).zip(select(rPa, cjj)).map { case (ip, rp) => s"cqp$ip: $ip =:= $rp$cmod" }
        //val kqp = kjj.map { j => uvPp(j) }.zip(rPa).map { case (ip, rp) => s"kqp$ip: $ip =!= $rp$cmod" }
        val kqp = select(uvPp, kjj).zip(select(rPa, kjj)).map { case (ip, rp) => s"kqp$ip: $ip =!= $rp$cmod" }
        val rhsSig = (rU ++ rP ++ uvPp ++ uvUu ++ uvPu)
        val rhsType = s"$rhsName${typesig(rhsSig)}"
        val fSeq = uvUp.zip(rUa).zip(uvPp).map { case ((iu, ru), ip) => s"(factor[$iu, $ru], iv${ip}.value)" }
        val imps = Seq(rUrec, uvUrec, uvPiv, rueq, rune, cqp, kqp)
        val fName = s"""rhs$$v${v}a${a}s${aj.mkString}c${aij.mkString}p${ajp.mkString}"""
        val vCode = if (fSeq.length == 0) "val v = uv.value" else s"""val fp = Seq${params(fSeq)}
           |    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }""".stripMargin
        defs += s"""|  implicit def $fName${typesig(fSig)}(uv: $uvType)${mlparams(imps,imp=true,ind=4)}: $rhsType = {
          |    $vCode
          |    new $rhsType(v)
          |  }""".stripMargin
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

    (0 to V).foreach { v =>
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
    val ccSig = uS.zip(pS).map { case (u, p) => s"$u <: Unit, $p <: Integer" }
    val uvSig = uS.zip(pS).map { case (u, p) => s"$u, $p" }
    val urec = (1 to v).map { j => s"urec$j: UnitRec[U$j]" }
    val iiv = (1 to v).map { j => s"iv$j: IntegerValue[P$j]" }
    val uvx = s"UnitValue$v${typesig(uvSig)}"
    val tsSeq = (1 to v).map { j => s"(urec${j}.name, iv${j}.value)" }.mkString(", ")
    val mulDefs = uvOpDefs(v, V, 'M').mkString("\n")
    val divDefs = uvOpDefs(v, V, 'D').mkString("\n")
    s"""
    |case class UnitValue$v${typesig(ccSig)}(value: Double)${mlparams(Seq(urec,iiv),imp=true)} {
    |  override def toString = Unit.toString(value, Seq($tsSeq))
    |
    |  def +(uv: $uvx): $uvx = $uvx(value + uv.value)
    |  def -(uv: $uvx): $uvx = $uvx(value - uv.value)
    |
    |  def *(v: Double): $uvx = $uvx(value * v)
    |
    |$mulDefs
    |$divDefs
    |}""".stripMargin
  }

  def uvOpDefs(v: Int, V: Int, op: Char): Seq[String] = {
    require(Seq('M', 'D').contains(op))
    val (cmod, kmod, qmod, opmod) = if (op == 'M') ("#Neg", "#Add", "", "*") else ("", "#Sub", "#Neg", "/")
    val defs = scala.collection.mutable.ArrayBuffer.empty[String]
    val rU = (1 to v).map { j => s"U$j" } ++ Seq.fill(V - v)("UZ")
    val rP = (1 to v).map { j => s"P$j" } ++ Seq.fill(V - v)("_0")
    // vi is (implicit) valence of the RHS
    (0 to V).foreach { vi =>
      val amax = math.min(v, vi)
      val umax = math.min(V - v, vi)
      // (a) is number aligned, (u) is number un-aligned
      cross(0 to amax, 0 to umax)
        .filter { case (a, u) => (a + u == vi) && (v + u <= V) }
        .foreach { case (a, u) =>
        val uU = (0 until u).map { j => s"U${letter(j)}" }
        val uQ = (0 until u).map { j => s"Q${letter(j)}" }
        val oQ = uQ.map { t => s"$t$qmod" }
        // aj is a combination of aligned units
        (0 until v).combinations(a).foreach { aj =>
          // (c) is number of the aligned that are canceling out a LHS unit
          (0 to aj.length).foreach { c =>
            val vo = v + u - c
            // (cj) is a particular combination of aligned units that are being canceled
            aj.combinations(c).foreach { cj =>
              // kj is aligned units that are being kept (non-canceled)
              val kj = aj.diff(cj)
              val kQ = kj.map { j => s"Q${j + 1}" }
              val aQ = aj.map { j => if (cj.contains(j)) s"P${j + 1}$cmod" else s"Q${j + 1}" }
              val fSig = (kQ.map(_ + " <: Integer") ++ uU.map(_ + " <: Unit") ++ uQ.map(_ + " <: Integer"))
              val rhsName = s"RHSv${vi}a${a}s${aj.mkString}c${c}s${cj.mkString}$op"
              val rhsSig = (rU ++ rP ++ aQ ++ uU ++ uQ)
              val pq = kj.map { j => s"P${j + 1}" }.zip(kQ).map { case (p, q) => s"$p${kmod}[$q]" }
              val aP = (0 until v).diff(cj).map { j => if (kj.contains(j)) s"${pq(kj.indexOf(j))}" else s"P${j + 1}" }
              val kU = (0 until v).diff(cj).map { j => s"U${j + 1}" }
              val oSig = (kU ++ uU).zip(aP ++ oQ).map { case (u, p) => s"$u, $p" }
              val urecs = uU.map { u => s"urec$u: UnitRec[$u]" }
              val ivs = uQ.map { q => s"iv$q: IntegerValue[$q$qmod]" }
              val ivs2 = pq.zipWithIndex.map { case (t, j) => s"ivpq$j: IntegerValue[$t]" }
              val imps = Seq(urecs, ivs, ivs2)
              val uvvo = s"UnitValue$vo${typesig(oSig)}"
              defs += s"""
                |  def ${opmod}${typesig(fSig)}(rhs: $rhsName${typesig(rhsSig)})${mlparams(imps,imp=true,ind=4)}: $uvvo =
                |    $uvvo(value $opmod rhs.value)""".stripMargin
            }
          }
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
    val fSig = ((iS ++ uS).map { u => s"$u <: Unit" } ++ pS.map { p => s"$p <: Integer" })
    val oSig = uS.zip(pS).map { case (u, p) => s"$u, $p" }
    val idef = (0 until v).map { j => s"urec${letter(j)}: UnitRec[U${letter(j)}]" }
    val odef = (1 to v).map { j => s"urec$j: UnitRec[U$j]" }
    val iiv = (1 to v).map { j => s"iv$j: IntegerValue[P$j]" }
    val rueq = (0 until v).map { j => s"eq${letter(j)}${j + 1}: U${letter(j)}#RU =:= U${j + 1}#RU" }
    val imps = Seq(idef, odef, iiv, rueq)
    val fSeq = (0 until v).map { j => s"(factor[U${letter(j)}, U${j + 1}], iv${j + 1}.value)" }
    val uvov = s"UnitValue$v${typesig(oSig)}"
    val vCode = s"""val fp = Seq(${fSeq.mkString(", ")})
      |    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }""".stripMargin
    (0 until v).permutations.filter(!_.isEmpty).foreach { jp =>
      val fName = s"uvc$$v${v}p${jp.mkString}"
      val iSig = jp.map { j => s"U${letter(j)}, P${j + 1}" }
      val fCode = s"""
        |  implicit def $fName${typesig(fSig)}(uv: UnitValue$v${typesig(iSig)})${mlparams(imps,imp=true,ind=4)}: $uvov = {
        |    $vCode
        |    $uvov(v)
        |  }""".stripMargin
      defs += fCode
    }
    defs
  }
}
