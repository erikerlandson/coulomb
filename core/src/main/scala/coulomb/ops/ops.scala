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

package coulomb.ops

import scala.annotation.implicitNotFound

import coulomb.*

@implicitNotFound("Negation not defined in scope for Quantity[${V}, ${U}]")
abstract class Neg[V, U] extends (Quantity[V, U] => Quantity[V, U])

@implicitNotFound("Addition not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Add[VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VO, UO]

@implicitNotFound("Subtraction not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Sub[VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VO, UO]

@implicitNotFound("Multiplication not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Mul[VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VO, UO]

@implicitNotFound("Division not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Div[VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VO, UO]

@implicitNotFound("Truncating Division not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class TQuot[VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (Quantity[VL, UL], Quantity[VR, UR]) => Quantity[VO, UO]

@implicitNotFound("Power not defined in scope for Quantity[${V}, ${U}] ^ ${P}")
abstract class Pow[V, U, P]:
    type VO
    type UO
    val eval: Quantity[V, U] => Quantity[VO, UO]

@implicitNotFound("Truncating Power not defined in scope for Quantity[${V}, ${U}] ^ ${P}")
abstract class TPow[V, U, P]:
    type VO
    type UO
    val eval: Quantity[V, U] => Quantity[VO, UO]

@implicitNotFound("Ordering not defined in scope for Quantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class Ord[VL, UL, VR, UR] extends ((Quantity[VL, UL], Quantity[VR, UR]) => Int)

@implicitNotFound("Subtraction not defined in scope for DeltaQuantity[${VL}, ${UL}] and DeltaQuantity[${VR}, ${UR}]")
abstract class DeltaSub[B, VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (DeltaQuantity[VL, UL, B], DeltaQuantity[VR, UR, B]) => Quantity[VO, UO]

@implicitNotFound("Subtraction not defined in scope for DeltaQuantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class DeltaSubQ[B, VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (DeltaQuantity[VL, UL, B], Quantity[VR, UR]) => DeltaQuantity[VO, UO, B]

@implicitNotFound("Addition not defined in scope for DeltaQuantity[${VL}, ${UL}] and Quantity[${VR}, ${UR}]")
abstract class DeltaAddQ[B, VL, UL, VR, UR]:
    type VO
    type UO
    val eval: (DeltaQuantity[VL, UL, B], Quantity[VR, UR]) => DeltaQuantity[VO, UO, B]

@implicitNotFound("Ordering not defined in scope for DeltaQuantity[${VL}, ${UL}] and DeltaQuantity[${VR}, ${UR}]")
abstract class DeltaOrd[B, VL, UL, VR, UR] extends ((DeltaQuantity[VL, UL, B], DeltaQuantity[VR, UR, B]) => Int)

@implicitNotFound("Unable to simplify unit type ${U}")
abstract class SimplifiedUnit[U]:
    type UO

object SimplifiedUnit:
    import scala.quoted.*

    transparent inline given ctx_SimplifiedUnit[U]: SimplifiedUnit[U] =
        ${ simplifiedUnit[U] }

    class NC[U, UOp] extends SimplifiedUnit[U]:
        type UO = UOp

    private def simplifiedUnit[U](using Quotes, Type[U]): Expr[SimplifiedUnit[U]] =
        import quotes.reflect.*
        coulomb.infra.meta.simplify(TypeRepr.of[U]).asType match
            case '[uo] => '{ new NC[U, uo] }

/** Resolve the operator output type for left and right argument types */
@implicitNotFound("No output type resolution in scope for argument value types ${VL} and ${VR}")
abstract class ValueResolution[VL, VR]:
    type VO

object ValueResolution:
    transparent inline given ctx_VR_XpX[V]: ValueResolution[V, V] = new NC[V, V, V]
    transparent inline given ctx_VR_LpR[VL, VR](using ValuePromotion[VL, VR]): ValueResolution[VL, VR] = new NC[VL, VR, VR]
    transparent inline given ctx_VR_RpL[VL, VR](using ValuePromotion[VR, VL]): ValueResolution[VL, VR] = new NC[VL, VR, VL]
    class NC[VL, VR, VOp] extends ValueResolution[VL, VR]:
        type VO = VOp

final class ValuePromotion[VF, VT]

object ValuePromotion:
    import scala.quoted.*
    import scala.language.implicitConversions

    import coulomb.infra.meta.typestr

    final type &:[H, T]
    final type TNil
 
    transparent inline given ctx_VP_Path[VF, VT]: ValuePromotion[VF, VT] = ${ vpPath[VF, VT] }

    private type VppSet[T] = scala.collection.mutable.HashSet[T]
    private val VppSet = scala.collection.mutable.HashSet

    private def vpPath[VF, VT](using Quotes, Type[VF], Type[VT]): Expr[ValuePromotion[VF, VT]] =
        import quotes.reflect.*
        // Dealiasing is important because I am working with string type names.
        // Ability to hash, == or < directly on TypeRepr objects might allow me to 
        // use Set[TypeRepr] but not sure if it is possible. 
        val (vf, vt) = (TypeRepr.of[VF].dealias, TypeRepr.of[VT].dealias)
        if (pathexists(vf.typeSymbol.fullName,
                       vt.typeSymbol.fullName,
                       getvpp))
            '{ new ValuePromotion[VF, VT] }
        else
            report.error(s"no promotion from ${typestr(vf)} => ${typestr(vt)}")
            '{ new ValuePromotion[VF, VT] }

    private def getvpp(using Quotes): VppSet[(String, String)] =
        import quotes.reflect.*
        Implicits.search(TypeRepr.of[ValuePromotionPolicy].appliedTo(List(TypeBounds.empty))) match
            case iss: ImplicitSearchSuccess =>
                val AppliedType(_, List(vppt)) = iss.tree.tpe.baseType(TypeRepr.of[ValuePromotionPolicy].typeSymbol)
                vpp2str(vppt)
            case _ =>
                report.error("no ValuePromotionPolicy was found in scope")
                VppSet.empty[(String, String)]

    private def vpp2str(using Quotes)(vpp: quotes.reflect.TypeRepr): VppSet[(String, String)] =
        import quotes.reflect.*
        vpp match
            case t if (t =:= TypeRepr.of[TNil]) => VppSet.empty[(String, String)]
            case AppliedType(v, List(AppliedType(t2, List(vf, vt)), tail)) if ((v =:= TypeRepr.of[&:]) && (t2 =:= TypeRepr.of[Tuple2])) =>
                val vppset = vpp2str(tail)
                vppset.add((vf.dealias.typeSymbol.fullName, vt.dealias.typeSymbol.fullName))
                vppset
            case _ =>
                report.error(s"type ${typestr(vpp)} is not a valid value promotion policy")
                VppSet.empty[(String, String)]

    private def pathexists(vf: String, vt: String, edges: VppSet[(String, String)]): Boolean =
        val reachable = VppSet(vf)
        var haspath = false
        var done = false
        // print(s"\n\nvf= $vf   vt= $vt\n")
        while (!done) do
            val prevsize = reachable.size
            val next = reachable.flatMap(r => edges.filter { (f, _) => f == r }.map { (_, t) => t })
            reachable.addAll(next)
            // print(s"reachable= $reachable\n")
            if (reachable.contains(vt))
                haspath = true
                done = true
            if (reachable.size == prevsize)
                done = true
        haspath

final class ValuePromotionPolicy[Pairs]
object ValuePromotionPolicy:
    def apply[P](): ValuePromotionPolicy[P] = new ValuePromotionPolicy[P]
