package com.manyangled.coulomb

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.language.implicitConversions
import scala.annotation.implicitNotFound

/**
 * In `coulomb` all units are static types of a form given by the `UnitExpr` trait hierarchy
 */
trait UnitExpr

/**
 * Base units are axiomatic units. The
 * [[https://en.wikipedia.org/wiki/International_System_of_Units Standard International]]
 * (SI) unit system defines seven Base units, which `coulomb` defines in [[SIBaseUnits]].
 * Exactly one `BaseUnit` is defined for each category of quantity (e.g. length, mass, time, etc).
 */
trait BaseUnit extends UnitExpr

/**
 * Derived units are defined in terms of another [[UnitExpr]]
 * @tparam U The [[UnitExpr]] used to define the new unit
 * {{{
 * // define a new unit Furlong in terms of Meter
 * import SIBaseUnits._
 * trait Furlong extends DerivedUnit[Meter]
 * object Furlong extends UnitCompanion[Furlong]("furlong", 201.168)
 * }}}
 */
trait DerivedUnit[U <: UnitExpr] extends UnitExpr

/**
 * Prefix units represent standard unitless multipliers applied to units, e.g. [[SIPrefixes]]
 * defines units `Kilo` (10^3), `Mega` (10^6), etc.
 * {{{
 * // define a new prefix Dozen, representing multiplier of 12
 * trait Dozen extends PrefixUnit
 * object Dozen extends UnitCompanion[Dozen]("dozen", 12.0)
 * }}}
 */
trait PrefixUnit extends DerivedUnit[Unitless]

/**
 * The unit product of two other unit expressions
 * @tparam LUE the left-hand unit expression
 * @tparam RUE the right-hand unit expression
 * {{{
 * import USCustomaryUnits._
 * // a quantity of acre-feet (a unit of volume, usually for water)
 * val afq = Quantity[Acre <*> Foot](10)
 * }}}
 */
sealed trait <*> [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

/**
 * The unit quotient of two unit expressions
 * @tparam LUE the left-hand unit expression
 * @tparam RUE the right-hand unit expression
 * {{{
 * import SIBaseUnits._
 * // a velocity in meters per second
 * val v = Quantity[Meter </> Second](10)
 * }}}
 */
sealed trait </> [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

/**
 * A power (exponent) of a unit expression
 * @tparam UE the unit expression being raised to a power
 * @tparam P the exponent (power)
 * {{{
 * import SIBaseUnits._
 * // an area in square meters
 * val area = Quantity[Meter <^> _2](10)
 * }}}
 */
sealed trait <^> [UE <: UnitExpr, P <: ChurchInt] extends UnitExpr

/**
 * A unitless value.  Each [[PrefixUnit]] is derived from Unitless.  Unit Expressions where all
 * units cancel out will also be of type Unitless
 * {{{
 * import USCustomaryUnits._
 * // ratio will have type Unitless, since length units cancel
 * val ratio = Quantity[Yard](1) / Quantity[Foot](1)
 * }}}
 */
sealed trait Unitless extends UnitExpr

object Unitless {
  def apply[N](v: N)(implicit num: Numeric[N]): Quantity[Unitless] =
    new Quantity[Unitless](num.toDouble(v))

  def apply(): Quantity[Unitless] = new Quantity[Unitless](1.0)
}

/**
 * An expression representing a temperature given in some defined temperature unit.
 */
trait TemperatureExpr extends UnitExpr

/**
 * Define a base unit of temperature.  There should be exactly one such base unit, which is
 * [[SIBaseUnits.Kelvin]].  Any other unit of temperature should be a [[DerivedTemperature]].
 */
trait BaseTemperature extends BaseUnit with TemperatureExpr

/**
 * Define a derived unit of temperature. By definition, all derived temperatures are defined in
 * terms of [[SIBaseUnits.Kelvin]].
 * The three common temperatures Kelvin, Celsius and Fahrenheit are already defined in `coulomb`
 * and so use cases for new derived temperatures are expected to be rare.
 * {{{
 * // example defining Fahrenheit temperature unit (already defined by coulomb)
 * trait Fahrenheit extends DerivedTemperature
 * object Fahrenheit extends TempUnitCompanion[Fahrenheit]("fahrenheit", 5.0 / 9.0, 459.67)
 * }}}
 */
trait DerivedTemperature extends DerivedUnit[SIBaseUnits.Kelvin] with TemperatureExpr

/**
 * A value (quantity) having an associated static unit type
 * @tparam U The unit expression representing the associated unit
 * {{{
 * import MKSUnits._
 * // a length of 5 meters
 * val length = Quantity[Meter](5)
 * // a velocity in meters per second
 * val speed = Quantity[Meter </> Second](10)
 * // an acceleration in meters per second-squared
 * val acceleration = Quantity[Meter </> (Second <^> _2)](9.8)
 * }}}
 */
class Quantity[U <: UnitExpr](private [coulomb] val value: Double)
    extends AnyVal with Serializable {

  /**
   * Convert a quantity into new units.
   * @tparam U2 the new unit expression to convert to. If U2 is not a compatible unit
   * then a compile-time error will occur
   * @return a new value of type Quantity[U2], equivalent to `this` quantity
   */
  def as[U2 <: UnitExpr](implicit cu: CompatUnits[U, U2]): Quantity[U2] =
    cu.convert(this)

  /**
   * Obtain a new quantity with same units, but negated value
   * @return negated unit quantity
   */
  def unary_- : Quantity[U] = new Quantity[U](-this.value)

  /**
   * The sum of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity.  U2 must be compatible with U, or
   * a compile-time error will occur
   * @param that the right-hand side of the quantity sum
   * @return `this` + `that`, with units of left-hand side `this`
   */
  def +[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Quantity[U] =
    new Quantity[U](this.value + cu.coef * that.value)

  /**
   * The difference of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity.  U2 must be compatible with U, or
   * a compile-time error will occur
   * @param that the right-hand side of the difference
   * @return `this` - `that`, with units of left-hand side `this`
   */
  def -[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Quantity[U] =
    new Quantity[U](this.value - cu.coef * that.value)

  /**
   * The product of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity
   * @tparam RU the unit type of the result quantity, is compatible with `U <*> U2`
   * @param that the right-hand side of the product
   * @return `this` * `that`, with units of RU
   */
  def *[U2 <: UnitExpr, RU <: UnitExpr](that: Quantity[U2])(implicit uer: UnitExprMul[U, U2] { type U = RU }): Quantity[RU] =
    new Quantity[RU](uer.coef * this.value * that.value)

  /**
   * The quotient, or ratio, of two unit quantities
   * @tparam U2 the unit type of the right-hand quantity
   * @tparam RU the unit type of the result quantity, is compatible with `U </> U2`
   * @param that the right-hand side of the ratio
   * @return `this` / `that`, with units of RU
   */
  def /[U2 <: UnitExpr, RU <: UnitExpr](that: Quantity[U2])(implicit uer: UnitExprDiv[U, U2] { type U = RU }): Quantity[RU] =
    new Quantity[RU](uer.coef * this.value / that.value)

  /**
   * Raise a unit quantity to a power
   * @tparam E the church integer type representing the exponent
   * @return `this` ^ E, in units compatible with `U <^> E`
   */
  def pow[E <: ChurchInt](implicit exp: ChurchIntValue[E]): Quantity[U <^> E] =
    new Quantity[U <^> E](math.pow(this.value, exp.value))

  /** The raw value of the unit quantity, rounded to nearest integer and returned as an Int */
  def toInt: Int = value.round.toInt

  /** The raw value of the unit quantity, rounded to nearest integer and return as a Long */
  def toLong: Long = value.round

  /** The raw value of the unit quantity returned as a Float */
  def toFloat: Float = value.toFloat

  /** The raw value of the unit quantity returned as a Double */
  def toDouble: Double = value

  /** A human-readable string representing the unit quantity with its associated type */
  def str(implicit uesU: UnitExprString[U]) = s"$value ${uesU.str}"

  /** A human-readable string representing the unit type of this quantity */
  def unitStr(implicit uesU: UnitExprString[U]) = uesU.str
}

/** Factory functions and implicit definitions associated with Quantity objects */
object Quantity {
  /**
   * Obtain a function that converts objects of Quantity[U] into compatible Quantity[U2]
   * @tparam U the unit type of input quantity.
   * @tparam U2 the unit type of the output. If U2 is not compatible with U,
   * then a compile-time error will occur.
   * @return a function for converting Quantity[U] into Quantity[U2]
   */
  def converter[U <: UnitExpr, U2 <: UnitExpr](implicit cu: CompatUnits[U, U2]):
      Quantity[U] => Quantity[U2] =
    cu.converter

  /**
   * Obtain the numeric coefficient that represents the conversion factor from
   * a quantity with units U to a quantity of unit type U2
   * @tparam U the unit type of input quantity.
   * @tparam U2 the unit type of the output. If U2 is not compatible with U,
   * then a compile-time error will occur.
   * @return numeric coefficient, aka the conversion factor from Quantity[U] into Quantity[U2]
   */
  def coefficient[U <: UnitExpr, U2 <: UnitExpr](implicit cu: CompatUnits[U, U2]): Double =
    cu.coef

  /**
   * Obtain a human-readable string representing a unit type U
   * @tparam U a unit type
   * @return human readable string representing U
   */
  def unitStr[U <: UnitExpr](implicit uesU: UnitExprString[U]) = uesU.str

  /** Obtain a unit quantity of unit type U from an Int */
  def apply[U <: UnitExpr](v: Int) = new Quantity[U](v.toDouble)

  /** Obtain a unit quantity of unit type U from a Long */
  def apply[U <: UnitExpr](v: Long) = new Quantity[U](v.toDouble)

  /** Obtain a unit quantity of unit type U from a Float */
  def apply[U <: UnitExpr](v: Float) = new Quantity[U](v.toDouble)

  /** Obtain a unit quantity of unit type U from a Double */
  def apply[U <: UnitExpr](v: Double) = new Quantity[U](v)

  /**
   * Obtain a unit quantity from a Temperature with the same raw value and temperature unit
   * @tparam U a unit of temperature, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
   * or USCustomaryUnits.Fahrenheit
   * @param t the temperature value of unit type U
   * @return a unit quantity of the same unit type U and raw numeric value of t
   */
  def fromTemperature[U <: TemperatureExpr](t: Temperature[U]) = new Quantity[U](t.value)

  implicit def implicitUnitConvert[U <: UnitExpr, U2 <: UnitExpr](q: Quantity[U])(implicit
    cu: CompatUnits[U, U2]): Quantity[U2] = cu.convert(q)
}

/**
 * A temperature value.
 * @tparam U a temperature unit, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
 * or USCustomaryUnits.Fahrenheit
 * {{{
 * // a Temperature takes temperature baseline offsets into account during conversion
 * val c = Temperature[Celsius](1)
 * val f = c.as[Fahrenheit]        // == Temperature[Fahrenheit](33.8)
 * // a Quantity of temperature only considers amounts of unit
 * val cq = Quantity[Celsius](1)
 * val fq = cq.as[Fahrenheit]      // == Quantity[Fahrenheit](1.8)
 * }}}
 */
class Temperature[U <: TemperatureExpr](private [coulomb] val value: Double)
    extends AnyVal with Serializable {

  /**
   * Convert a temperature into a new unit of temperature.
   * @tparam U2 the new temperature unit expression to convert to.
   * @return a new value of type Temperature[U2], equivalent to `this`
   */
  def as[U2 <: TemperatureExpr](implicit ct: CompatTemps[U, U2]): Temperature[U2] =
    ct.convert(this)

  /**
   * Add a Quantity of temperature units to a temperature to get a new temperature
   * @tparam U2 the temperature unit of right side.  If U2 is not a compatible unit (temperature)
   * a compile-time error will ocurr.
   * @param that the right hand side of sum
   * @return a new temperature that is sum of left-hand temp plus right-hand temp quantity
   */
  def +[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Temperature[U] =
    new Temperature[U](this.value + cu.coef * that.value)

  /**
   * Subtract a Quantity of temperature units from a temperature to get a new temperature
   * @tparam U2 the temperature unit of right side.  If U2 is not a compatible unit (temperature)
   * a compile-time error will ocurr.
   * @param that the right hand side of difference
   * @return a new temperature that is the left-hand temp minus right-hand temp quantity
   */
  def -[U2 <: UnitExpr](that: Quantity[U2])(implicit cu: CompatUnits[U2, U]): Temperature[U] =
    new Temperature[U](this.value - cu.coef * that.value)

  /**
   * Subtract two temperatures to get a Quantity of temperature units
   * @tparam U2 the temperature unit of right side.
   * @param that the right hand side of difference
   * @return a new unit Quantity equal to `this` - `that`
   */
  def -[U2 <: TemperatureExpr](that: Temperature[U2])(implicit
      ct: CompatTemps[U2, U]): Quantity[U] =
    new Quantity[U](this.value - ct.convert(that).value)

  /** The raw value of the temperature, rounded to nearest integer and returned as an Int */
  def toInt: Int = value.round.toInt

  /** The raw value of the temperature, rounded to nearest integer and returned as a Long */
  def toLong: Long = value.round

  /** The raw value of the temperature, rounded to nearest integer and returned as a Float */
  def toFloat: Float = value.toFloat

  /** The raw value of the temperature, rounded to nearest integer and returned as a Double */
  def toDouble: Double = value

  /** A human-readable string representing the temperature with its associated unit type */  
  def str(implicit uesU: UnitExprString[U]) = s"$value ${uesU.str}"

  /** A human-readable string representing the unit type of this temperature */
  def unitStr(implicit uesU: UnitExprString[U]) = uesU.str
}

/** Factory functions and implicit definitions associated with Temperature objects */
object Temperature {
  /**
   * Obtain a function that converts objects of Temperature[U] into compatible Temperature[U2]
   * @tparam U the unit type of input temp.
   * @tparam U2 the unit type of the output.
   * @return a function for converting Temperature[U] into Temperature[U2]
   */
  def converter[U <: TemperatureExpr, U2 <: TemperatureExpr](implicit
      ct: CompatTemps[U, U2]): Temperature[U] => Temperature[U2] =
    ct.converter

  /**
   * Obtain a human-readable string representing a unit type U
   * @tparam U a unit type representing a temperature
   * @return human readable string representing U
   */
  def unitStr[U <: TemperatureExpr](implicit uesU: UnitExprString[U]) = uesU.str

  /** Obtain a temperature of type U from an Int */
  def apply[U <: TemperatureExpr](v: Int) = new Temperature[U](v.toDouble)

  /** Obtain a temperature of type U from a Long */
  def apply[U <: TemperatureExpr](v: Long) = new Temperature[U](v.toDouble)

  /** Obtain a temperature of type U from a Float */
  def apply[U <: TemperatureExpr](v: Float) = new Temperature[U](v.toDouble)

  /** Obtain a temperature of type U from a Doiuble */
  def apply[U <: TemperatureExpr](v: Double) = new Temperature[U](v)

  /**
   * Obtain a temperature from a unit Quantity with same raw value and temperature unit
   * @tparam U a unit of temperature, e.g. SIBaseUnits.Kelvin, SIAcceptedUnits.Celsius,
   * or USCustomaryUnits.Fahrenheit
   * @param q the quantity of temperature-unit type U
   * @return a temperature of same unit type U and raw numeric value of q
   */
  def fromQuantity[U <: TemperatureExpr](q: Quantity[U]) = new Temperature[U](q.value)

  implicit def implicitTempConvert[U <: TemperatureExpr, U2 <: TemperatureExpr](t: Temperature[U])(
      implicit ct: CompatTemps[U, U2]): Temperature[U2] =
    ct.convert(t)
}

/** type extensions and implicits for working with units */
object extensions {
  /** enhances numeric types with utility methods for `coulomb` */
  implicit class ExtendWithUnits[N](v: N)(implicit num: Numeric[N]) {
    /** create a new unit Quantity of type U with numeric value of `this` */
    def withUnit[U <: UnitExpr]: Quantity[U] =
      new Quantity[U](num.toDouble(v))

    /** create a new unit Temperature of type U with numeric value of `this` */
    def withTemperature[U <: TemperatureExpr]: Temperature[U] =
      new Temperature[U](num.toDouble(v))
  }
}

case class UnitRec[UE <: UnitExpr](name: String, coef: Double)

class UnitCompanion[U <: UnitExpr](uname: String, ucoef: Double) {
  def this(n: String) = this(n, 1.0)

  def apply[N](v: N)(implicit num: Numeric[N]): Quantity[U] =
    new Quantity[U](num.toDouble(v))

  def apply(): Quantity[U] = new Quantity[U](1.0)

  implicit val furec: UnitRec[U] = UnitRec[U](uname, ucoef)
}

case class TempUnitRec[UE <: TemperatureExpr](offset: Double)

class TempUnitCompanion[U <: TemperatureExpr](uname: String, ucoef: Double, uoffset: Double) extends UnitCompanion[U](uname, ucoef) {
  implicit val turec: TempUnitRec[U] = TempUnitRec[U](uoffset)
}

@implicitNotFound("Implicit not found: CompatUnits[${U1}, ${U2}].\nIncompatible Unit Expressions: ${U1} and ${U2}")
class CompatUnits[U1 <: UnitExpr, U2 <: UnitExpr](val coef: Double) {
  val converter = CompatUnits.converter[U1, U2](coef)
  def convert(q1: Quantity[U1]): Quantity[U2] = converter(q1)
}

object CompatUnits {
  def converter[U1 <: UnitExpr, U2 <: UnitExpr](coef: Double): Quantity[U1] => Quantity[U2] =
    (q1: Quantity[U1]) => new Quantity[U2](coef * q1.value)

  implicit def witnessCompatUnits[U1 <: UnitExpr, U2 <: UnitExpr]: CompatUnits[U1, U2] =
    macro UnitMacros.compatUnits[U1, U2]
}

class CompatTemps[U1 <: TemperatureExpr, U2 <: TemperatureExpr](
    val coef1: Double, val off1: Double, val coef2: Double, val off2: Double) {
  val converter = CompatTemps.converter[U1, U2](coef1, off1, coef2, off2)
  def convert(t1: Temperature[U1]): Temperature[U2] = converter(t1)
}

object CompatTemps {
  def converter[U1 <: TemperatureExpr, U2 <: TemperatureExpr](
    coef1: Double, off1: Double, coef2: Double, off2: Double): Temperature[U1] => Temperature[U2] =
    (t1: Temperature[U1]) => {
      val k = (t1.value + off1) * coef1
      val v2 = (k / coef2) - off2
      new Temperature[U2](v2)
    }

  implicit def witnessCompatTemps[U1 <: TemperatureExpr, U2 <: TemperatureExpr](implicit
      turecU1: TempUnitRec[U1], urecU1: UnitRec[U1],
      turecU2: TempUnitRec[U2], urecU2: UnitRec[U2]): CompatTemps[U1, U2] =
    new CompatTemps[U1, U2](urecU1.coef, turecU1.offset, urecU2.coef, turecU2.offset)
}

case class UnitExprString[U <: UnitExpr](str: String)

object UnitExprString {
  implicit def witnessUnitExprString[U <: UnitExpr]: UnitExprString[U] =
    macro UnitMacros.unitExprString[U]
}

trait UnitExprMul[U1 <: UnitExpr, U2 <: UnitExpr] {
  type U <: UnitExpr
  def coef: Double
}

object UnitExprMul {
  implicit def witnessUnitExprMul[U1 <: UnitExpr, U2 <: UnitExpr]: UnitExprMul[U1, U2] =
    macro UnitMacros.unitExprMul[U1, U2]
}

trait UnitExprDiv[U1 <: UnitExpr, U2 <: UnitExpr] {
  type U <: UnitExpr
  def coef: Double
}

object UnitExprDiv {
  implicit def witnessUnitExprDiv[U1 <: UnitExpr, U2 <: UnitExpr]: UnitExprDiv[U1, U2] =
    macro UnitMacros.unitExprDiv[U1, U2]
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
  
  def urecVal(unitT: Type): (String, Double) = {
    val urt = appliedType(urecType, List(unitT))
    val ur = c.inferImplicitValue(urt, silent = false)
    evalTree[(String, Double)](q"(${ur}.name, ${ur}.coef)")
  }

  def turecVal(unitT: Type): Double = {
    val urt = appliedType(turecType, List(unitT))
    val ur = c.inferImplicitValue(urt, silent = false)
    evalTree[Double](q"${ur}.offset")
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
    def unapply(tpe: Type): Option[(String, Double, Type)] = {
      val du = superClass(tpe, duType)
      if (du.isEmpty) None else {
        val (name, coef) = urecVal(tpe)
        Option(name, coef, du.get.typeArgs(0))
      }
    }
  }

  object Prefix {
    def unapply(tpe: Type): Option[(String, Double, Type)] = {
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

  def canonical(typeU: Type): (Double, Map[TypeKey, Int]) = {
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
        (math.pow(bcoef, exp), mapPow(bmap, exp))
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
      val cq = q"${coef1 / coef2}"
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

    val cq = q"${coef1 * coef2}"

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

    val cq = q"${coef1 / coef2}"

    q"""
      new _root_.com.manyangled.coulomb.UnitExprDiv[$tpeU1, $tpeU2] {
        type U = $mt
        def coef = $cq
      }
    """
  }
}
