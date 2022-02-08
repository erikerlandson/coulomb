/*
 * Copyright 2022 Erik Erlandson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package coulomb.conversion.standard

import coulomb.conversion.Conversion

import coulomb.*

transparent inline given convStandard1U[VF, VT, U]: Conversion[VF, U, VT, U] =
    ${ meta.convStandard1U[VF, VT, U] }

transparent inline given convStandard2U[VF, UF, VT, UT]: Conversion[VF, UF, VT, UT] =
    ${ meta.convStandard2U[VF, UF, VT, UT] }

object meta:
    import scala.quoted.*
    import coulomb.infra.meta.*

    def convStandard1U[VF :Type, VT :Type, U :Type](using Quotes):
            Expr[Conversion[VF, U, VT, U]] =
        import quotes.reflect.*
        (TypeRepr.of[VF], TypeRepr.of[VT]) match
            case (typeDouble(), typeDouble()) =>
                '{ new Conversion[VF, U, VT, U] { def apply(v: Double): Double = v } }
            case _ =>
                report.error(s"undefined Conversion pattern in convStandard1U")
                '{ new Conversion[VF, U, VT, U] { def apply(v: VF): VT = ??? } }

    def convStandard2U[VF :Type, UF :Type, VT :Type, UT :Type](using Quotes):
            Expr[Conversion[VF, UF, VT, UT]] =
        import quotes.reflect.*
        val (uF, uT) = (TypeRepr.of[UF], TypeRepr.of[UT])
        // If the case of identical units was not handled by convStandard1U, something has gone wrong
        require(!(uF =:= uT))
        val cf = coef(uF, uT)
        (TypeRepr.of[VF], TypeRepr.of[VT]) match
            case (typeDouble(), typeDouble()) => '{
                new Conversion[VF, UF, VT, UT]:
                    val c = ${cf}.toDouble
                    def apply(v: Double): Double = v * c
            }
            case _ =>
                report.error(s"undefined Conversion pattern in convStandard2U")
                '{ new Conversion[VF, UF, VT, UT] { def apply(v: VF): VT = ??? } }
