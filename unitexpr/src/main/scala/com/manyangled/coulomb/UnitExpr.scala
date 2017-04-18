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

/**
 * In `coulomb` all units are static types of a form given by the `UnitExpr` trait hierarchy
 */
trait UnitExpr

/**
 * Base units are axiomatic units. The
 * [[https://en.wikipedia.org/wiki/International_System_of_Units Standard International]]
 * (SI) unit system defines seven Base units, which `coulomb` defines in SIBaseUnits.
 * Exactly one `BaseUnit` is defined for each category of quantity (e.g. length, mass, time, etc).
 * {{{
 * import com.manyangled.coulomb._
 * import spire.math._
 * // A base unit for loop iterations
 * @UnitDecl("iteration")
 * trait Iteration extends BaseUnit
 * }}}
 */
trait BaseUnit extends UnitExpr

/**
 * Derived units are defined in terms of another [[UnitExpr]]
 * @tparam U The [[UnitExpr]] used to define the new unit
 * {{{
 * import com.manyangled.coulomb._
 * import SIBaseUnits._
 * import spire.math._
 * // A furlong is 201.168 meters
 * @UnitDecl("furlong", 201.168)
 * trait Furlong extends DerivedUnit[Meter]
 * }}}
 */
trait DerivedUnit[U <: UnitExpr] extends UnitExpr

/**
 * Prefix units represent standard unitless multipliers applied to units, e.g. SIPrefixes
 * defines units `Kilo` (10^3), `Mega` (10^6), etc.
 * {{{
 * // define a new prefix Dozen, representing multiplier of 12
 * @UnitDecl("dozen", 12)
 * trait Dozen extends PrefixUnit
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
 * val afq = 10D.withUnit[Acre %* Foot]
 * }}}
 */
sealed trait %* [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

/**
 * The unit quotient of two unit expressions
 * @tparam LUE the left-hand unit expression
 * @tparam RUE the right-hand unit expression
 * {{{
 * import SIBaseUnits._
 * // a velocity in meters per second
 * val v = 10f.withUnit[Meter %/ Second]
 * }}}
 */
sealed trait %/ [LUE <: UnitExpr, RUE <: UnitExpr] extends UnitExpr

/**
 * A power (exponent) of a unit expression
 * @tparam UE the unit expression being raised to a power
 * @tparam P the exponent (power)
 * {{{
 * import SIBaseUnits._
 * // an area in square meters
 * val area = 10.withUnit[Meter %^ _2]
 * }}}
 */
sealed trait %^ [UE <: UnitExpr, P <: ChurchInt] extends UnitExpr

/**
 * An expression representing a temperature given in some defined temperature unit.
 */
trait TemperatureExpr extends UnitExpr

/**
 * Define a base unit of temperature.  There should be exactly one such base unit, which is
 * SIBaseUnits.Kelvin.  Any other unit of temperature should be a DerivedTemperature.
 * The base temperature unit Kelvin is defined in SIBaseUnits; other uses of BaseTemperature
 * would be rare.
 */
trait BaseTemperature extends BaseUnit with TemperatureExpr

/**
 * A unitless value.  Each [[PrefixUnit]] is derived from Unitless.  Unit Expressions where all
 * units cancel out will also be of type Unitless
 * {{{
 * import USCustomaryUnits._
 * // ratio will have a type convertable to Unitless, since length units cancel
 * val ratio = (Yard(1.0) / Foot(1.0)).toUnit[Unitless]
 * }}}
 */
sealed trait Unitless extends UnitExpr
