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

package coulomb.ops.runtime

object add:
    import scala.util.NotGiven
    import scala.Conversion

    import algebra.ring.{AdditiveSemigroup, MultiplicativeSemigroup}

    import coulomb.rational.Rational
    import coulomb.{RuntimeQuantity, CoefficientRuntime}
    import coulomb.syntax.withRuntimeUnit
    import coulomb.ops.{RuntimeAdd, ValueResolution}
    import coulomb.conversion.ValueConversion
    
    transparent inline given ctx_runtime_add_1V[VL, VR](using
        eqv: VR =:= VL,
        crt: CoefficientRuntime,
        vc: ValueConversion[Rational, VL],
        alg: AdditiveSemigroup[VL],
        mul: MultiplicativeSemigroup[VL]
    ): RuntimeAdd[VL, VR] =
        new infra.RTAddNC(
            (ql: RuntimeQuantity[VL], qr: RuntimeQuantity[VR]) =>
                crt.coefficient[VL](qr.unit, ql.unit).map { coef =>
                    alg.plus(ql.value, mul.times(coef, eqv(qr.value))).withRuntimeUnit(ql.unit)
                }
        )

    object infra:
        class RTAddNC[VL, VR, VOp](
            val eval: (RuntimeQuantity[VL], RuntimeQuantity[VR]) => Either[String, RuntimeQuantity[VOp]]
        ) extends RuntimeAdd[VL, VR]:
            type VO = VOp
        
