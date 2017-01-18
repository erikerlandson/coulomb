package com.manyangled.coulomb

import scala.util.Try

/**
 * A run-time parser for string expressions that evaluate to [[Quantity]] objects.
 * Parsing returns a compatible unit Quantity type, or failure.
 * @param unitDeclarationObjects a collection of objects to import units from. For each such object
 * the run-time parsing will `import Object._` to obtain any unit definitions prior to attempting
 * to parse each input string.
 * @param unitCompanionObjects a collection of unit names referring to the unit's companion object.
 * For each such object the run-time parsing will `import Unit` to obtain that unit's definition
 * prior to attempting to parse each input string.
 * @note `QuantityParser` always imports `com.manyangled.coulomb._`, `extensions._` and `ChurchInt._`
 * {{{
 * import SIBaseUnits._
 * // create a parser that knows about all SIBaseUnit units, Mile and Hour
 * val qp = QuantityParser(
 *   unitDeclarationObjects = Seq(SIBaseUnits),
 *   unitCompanionObjects = Seq(USCustomaryUnits.Mile, SIAcceptedUnits.Hour))
 * // parse an expression in miles per hour, return as meters per second
 * val mps = qp[Meter </> Second]("60.withUnit[Mile &lt;/&gt; Hour]")
 * }}}
 */
class QuantityParser(
  val unitDeclarationObjects: Seq[_],
  val unitCompanionObjects: Seq[_ <: UnitCompanion[_]]) {

  import scala.reflect.runtime.universe.{ Try => _, _ }
  import scala.tools.reflect.ToolBox

  private val toolbox = runtimeMirror(getClass.getClassLoader).mkToolBox()

  private def objectName[O](obj: O) = {
    val c = obj.getClass
    runtimeMirror(c.getClassLoader).classSymbol(c).fullName
  }

  private val importSeq = Seq("com.manyangled.coulomb._", "extensions._", "ChurchInt._") ++
    unitDeclarationObjects.map(objectName).map(n => s"${n}._") ++
    unitCompanionObjects.map(objectName)

  private val importStr = importSeq.map(s => s"import ${s}\n").mkString("")

  /**
   * Parse a string containing an expression that evaluates to a unit Quantity object.
   * @tparam U a [[UnitExpr]] type that specifies a `Quantity[U]` to return.  `U` is expected to
   * be compatible with the unit type of the expression in the string, or parsing will fail.
   * @param quantityExpr a string containing an expression that evaluates to a `Quantity` object.
   * @return the Quantity object created by run-time parsing, converted to compatible `Quantity[U]`,
   * or failure if any stage of the run-time parsing and unit conversion was unsuccessful.
   */
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

/**
 * Factory methods for instantiating `QuantityParser` objects
 */
object QuantityParser {
  /** default unit declaration imports; Seq(SIBaseUnits, SIPrefixes) */
  val unitDeclDefaults: Seq[_] = Seq(SIBaseUnits, SIPrefixes)

  /** default unit companion imports; empty */
  val unitCompDefaults: Seq[_ <: UnitCompanion[_]] = Seq.empty

  /**
   * Create a QuantityParser with the given (optional) unit declaration objects and unit companion
   * objects.
   * @param unitDeclarationObjects list of unit declaration objects, defaults to
   * [[unitDeclDefaults]]
   * @param unitCompanionObjects list of unit companion objects, defaults to [[unitCompDefaults]]
   * @return a new QuantityParser with the given arguments
   */
  def apply(
    unitDeclarationObjects: Seq[_] = unitDeclDefaults,
    unitCompanionObjects: Seq[_ <: UnitCompanion[_]] = unitCompDefaults): QuantityParser =
      new QuantityParser(unitDeclarationObjects, unitCompanionObjects)

  /** Create a QuantityParser that imports all units and prefixes defined by the `coulomb` package */
  def withAllUnits: QuantityParser =
      new QuantityParser(Seq(
        SIBaseUnits,
        SIPrefixes,
        BinaryPrefixes,
        SIAcceptedUnits,
        MKSUnits,
        InfoUnits,
        USCustomaryUnits),
        Seq.empty)
}
