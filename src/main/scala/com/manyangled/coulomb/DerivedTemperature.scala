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

/*
/**
 * Define a derived unit of temperature. By definition, all derived temperatures are defined in
 * terms of [[SIBaseUnits.Kelvin]].
 * The three common temperatures Kelvin, Celsius and Fahrenheit are already defined in `coulomb`
 * and so use cases for new derived temperatures are expected to be rare.
 * {{{
 * // example defining Fahrenheit temperature unit (already defined by coulomb)
 * @TempUnitDecl("fahrenheit", 5.0 / 9.0, 459.67)
 * trait Fahrenheit extends DerivedTemperature
 * }}}
 */
trait DerivedTemperature extends DerivedUnit[SIBaseUnits.Kelvin] with TemperatureExpr
*/
