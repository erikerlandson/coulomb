package com.manyangled.coulomb

import scala.util.Try

class QuantityParser(
  val unitDeclarationObjects: Seq[_],
  val unitCompanionObjects: Seq[_ <: UnitCompanion[_]]) {

  import scala.reflect.runtime.universe.{ Try => _, _ }
  import scala.tools.reflect.ToolBox

  val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()

  private def objectName[O](obj: O) = {
    val c = obj.getClass
    runtimeMirror(c.getClassLoader).classSymbol(c).fullName
  }

  val declNames = unitDeclarationObjects.map(objectName)
  val compNames = unitCompanionObjects.map(objectName)

  val importSeq = Seq("com.manyangled.coulomb._", "extensions._", "ChurchInt._") ++
    declNames.map(n => s"${n}._") ++
    compNames

  val importStr = importSeq.map(s => s"import ${s}\n").mkString("")

  def apply[U <: UnitExpr :TypeTag](quantityExpr: String): Try[Quantity[U]] = {
    val tpeU = typeOf[U]
    for (
      qeTree <- Try { toolbox.parse(s"$importStr ($quantityExpr)") };
      qeEval <- Try { toolbox.eval(q"${qeTree}.as[$tpeU]") };
      qret <- Try { qeEval.asInstanceOf[Quantity[U]] }
    ) yield {
      qret
    }
  }
}
