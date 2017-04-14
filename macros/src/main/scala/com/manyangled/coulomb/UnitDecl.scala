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

import scala.language.experimental.macros
import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly

import spire.math._

/**
 * Annotate a unit trait with name, conversion coefficient, etc
 * @param name The name of the unit
 * @param coef The conversion coefficient of the unit. Defaults to 1.
 * @note Coefficient may be provided as any numeric type that is implicitly convertable
 * to spire.math.Rational
 * {{{
 * import com.manyangled.coulomb._
 * import SIBaseUnits._
 * import spire.math._
 * // A furlong is 201.168 meters
 * @UnitDecl("furlong", 201.168)
 * trait Furlong extends DerivedUnit[Meter]
 * }}}
 */
@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class UnitDecl(val name: String, val coef: Rational = 1) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.unitDecl
}

/**
 * Annotate a temperature unit trait with name, conversion coefficient, etc
 * @param name The name of the unit
 * @param coef The conversion coefficient of the temperature unit, w.r.t. Kelvin
 * @param off The offset of the temperature unit, w.r.t Kelvin
 * @note Coefficient & offset may be provided as any numeric type that is implicitly convertable
 * to spire.math.Rational
 * {{{
 * import com.manyangled.coulomb._
 * import spire.math._
 * // K = (F + 459.67) * 5/9
 * @TempUnitDecl("fahrenheit", 5.0 / 9.0, 459.67)
 * trait Fahrenheit extends DerivedTemperature
 * }}}
 */
@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class TempUnitDecl(name: String, coef: Rational, off: Rational) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.tempUnitDecl
}
