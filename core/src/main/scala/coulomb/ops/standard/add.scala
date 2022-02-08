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

package coulomb.ops.standard

import coulomb.*
import coulomb.ops.*

transparent inline given addStandard1U[VL, VR, U]: Add[VL, U, VR, U] =
    ${ addmeta.addStandard1U[VL, VR, U] }

transparent inline given addStandard2U[VL, UL, VR, UR]: Add[VL, UL, VR, UR] =
    ${ addmeta.addStandard2U[VL, UL, VR, UR] }

object addmeta:
    import scala.quoted.*
    import coulomb.infra.meta.*

    def addStandard1U[VL :Type, VR :Type, U :Type](using Quotes): Expr[Add[VL, U, VR, U]] =
        import quotes.reflect.*
        // units are the same, so no coefficient is necessary
        (TypeRepr.of[VL], TypeRepr.of[VR]) match
            case (typeDouble(), typeDouble()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Double, vr: Double): Double = vl + vr
            }
            case (typeDouble(), typeFloat()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Double, vr: Float): Double = vl + vr
            }
            case (typeDouble(), typeLong()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Double, vr: Long): Double = vl + vr
            }
            case (typeDouble(), typeInt()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Double, vr: Int): Double = vl + vr
            }
            case (typeFloat(), typeDouble()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Float, vr: Double): Double = vl + vr
            }
            case (typeFloat(), typeFloat()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Float
                    type UO = U
                    def apply(vl: Float, vr: Float): Float = vl + vr
            }
            case (typeFloat(), typeLong()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Float
                    type UO = U
                    def apply(vl: Float, vr: Long): Float = vl + vr
            }
            case (typeFloat(), typeInt()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Float
                    type UO = U
                    def apply(vl: Float, vr: Int): Float = vl + vr
            }
            case (typeLong(), typeDouble()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Long, vr: Double): Double = vl + vr
            }
            case (typeLong(), typeFloat()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Float
                    type UO = U
                    def apply(vl: Long, vr: Float): Float = vl + vr
            }
            case (typeLong(), typeLong()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Long
                    type UO = U
                    def apply(vl: Long, vr: Long): Long = vl + vr
            }
            case (typeLong(), typeInt()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Long
                    type UO = U
                    def apply(vl: Long, vr: Int): Long = vl + vr
            }
            case (typeInt(), typeDouble()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Double
                    type UO = U
                    def apply(vl: Int, vr: Double): Double = vl + vr
            }
            case (typeInt(), typeFloat()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Float
                    type UO = U
                    def apply(vl: Int, vr: Float): Float = vl + vr
            }
            case (typeInt(), typeLong()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Long
                    type UO = U
                    def apply(vl: Int, vr: Long): Long = vl + vr
            }
            case (typeInt(), typeInt()) => '{
                new Add[VL, U, VR, U]:
                    type VO = Int
                    type UO = U
                    def apply(vl: Int, vr: Int): Int = vl + vr
            }
            case _ =>
                report.error(s"addition not defined for these types")
                '{ new Add[VL, U, VR, U] { type VO = Int; type UO = Nothing; def apply(vl: VL, vr: VR): VO = 0 } }

    def addStandard2U[VL :Type, UL :Type, VR :Type, UR :Type](using Quotes): Expr[Add[VL, UL, VR, UR]] =
        import quotes.reflect.*
        // units are not identical: get coefficient (or fail)
        val cf = coef(TypeRepr.of[UR], TypeRepr.of[UL]) // get coefficient from right to left
        (TypeRepr.of[VL], TypeRepr.of[VR]) match
            case (typeDouble(), typeDouble()) => '{
                new Add[VL, UL, VR, UR]:
                    type VO = Double
                    type UO = UL
                    val c = ${cf}.toDouble
                    def apply(vl: Double, vr: Double): Double = vl + (c * vr)
            }
            case _ =>
                report.error(s"addition not defined for these types")
                '{ new Add[VL, UL, VR, UR] { type VO = Int; type UO = Nothing; def apply(vl: VL, vr: VR): VO = 0 } }
