package coulomb.define

import scala.language.implicitConversions

import coulomb.rational.Rational

/** Methods and values common to all unit and temperature definitions */
abstract class NamedUnit:
    /** the full name of a unit, e.g. "meter" */
    val name: String
    /** the abbreviation of a unit, e.g. "m" for "meter" */
    val abbv: String

abstract class BaseUnit[U] extends NamedUnit:
    import coulomb.infra.*
    override def toString = s"BaseUnit($name, $abbv)"

abstract class DerivedUnit[U, D] extends NamedUnit:
    import coulomb.infra.*
    val coef: Rational
    override def toString = s"DerivedUnit($coef, $name, $abbv)"

// Not necessary, but allows meta-programming to be smarter with coefficients
abstract class DerivedUnit1[U, D] extends DerivedUnit[U, D]:
    val coef: Rational = Rational.const1

// prefix units are derived units of 1 ('unitless')
abstract class PrefixUnit[U] extends DerivedUnit[U, 1]
