package coulomb.ops.standard

import coulomb.*
import coulomb.ops.*

transparent inline given addStandard1U[VL, VR, U]: Add[VL, U, VR, U] =
    ${ meta.addStandard1U[VL, VR, U] }

transparent inline given addStandard2U[VL, UL, VR, UR]: Add[VL, UL, VR, UR] =
    ${ meta.addStandard2U[VL, UL, VR, UR] }

object meta:
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
