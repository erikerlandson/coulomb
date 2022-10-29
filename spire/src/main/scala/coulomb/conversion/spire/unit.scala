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

package coulomb.conversion.spire

object unit:
    import coulomb.conversion.*
    import coulomb.conversion.spire.coefficients.*
    import _root_.spire.math.*

    export coulomb.conversion.standard.unit.given

    inline given ctx_UC_SpireRational[UF, UT]
        : UnitConversion[Rational, UF, UT] =
        new infra.RationalUC[UF, UT](coefficientRational[UF, UT])

    inline given ctx_UC_BigDecimal[UF, UT]: UnitConversion[BigDecimal, UF, UT] =
        new infra.BigDecimalUC[UF, UT](coefficientBigDecimal[UF, UT])

    inline given ctx_UC_Algebraic[UF, UT]: UnitConversion[Algebraic, UF, UT] =
        new infra.AlgebraicUC[UF, UT](Algebraic(coefficientRational[UF, UT]))

    inline given ctx_UC_Real[UF, UT]: UnitConversion[Real, UF, UT] =
        new infra.RealUC[UF, UT](Real(coefficientRational[UF, UT]))

    inline given ctx_TUC_BigInt[UF, UT]
        : TruncatingUnitConversion[BigInt, UF, UT] =
        new infra.BigIntTUC[UF, UT](coefficientRational[UF, UT])

    inline given ctx_DUC_SpireRational[B, UF, UT]
        : DeltaUnitConversion[Rational, B, UF, UT] =
        new infra.RationalDUC[B, UF, UT](
            coefficientRational[UF, UT],
            deltaOffsetRational[UF, B],
            deltaOffsetRational[UT, B]
        )

    inline given ctx_DUC_BigDecimal[B, UF, UT]
        : DeltaUnitConversion[BigDecimal, B, UF, UT] =
        new infra.BigDecimalDUC[B, UF, UT](
            coefficientBigDecimal[UF, UT],
            deltaOffsetBigDecimal[UF, B],
            deltaOffsetBigDecimal[UT, B]
        )

    inline given ctx_DUC_Algebraic[B, UF, UT]
        : DeltaUnitConversion[Algebraic, B, UF, UT] =
        new infra.AlgebraicDUC[B, UF, UT](
            Algebraic(coefficientRational[UF, UT]),
            Algebraic(deltaOffsetRational[UF, B]),
            Algebraic(deltaOffsetRational[UT, B])
        )

    inline given ctx_DUC_Real[B, UF, UT]: DeltaUnitConversion[Real, B, UF, UT] =
        new infra.RealDUC[B, UF, UT](
            Real(coefficientRational[UF, UT]),
            Real(deltaOffsetRational[UF, B]),
            Real(deltaOffsetRational[UT, B])
        )

    inline given ctx_TDUC_BigInt[B, UF, UT]
        : TruncatingDeltaUnitConversion[BigInt, B, UF, UT] =
        new infra.BigIntTDUC[B, UF, UT](
            coefficientRational[UF, UT],
            deltaOffsetRational[UF, B],
            deltaOffsetRational[UT, B]
        )

    object infra:
        class RationalUC[UF, UT](c: Rational)
            extends UnitConversion[Rational, UF, UT]:
            def apply(v: Rational): Rational = c * v

        class BigDecimalUC[UF, UT](c: BigDecimal)
            extends UnitConversion[BigDecimal, UF, UT]:
            def apply(v: BigDecimal): BigDecimal = c * v

        class AlgebraicUC[UF, UT](c: Algebraic)
            extends UnitConversion[Algebraic, UF, UT]:
            def apply(v: Algebraic): Algebraic = c * v

        class RealUC[UF, UT](c: Real) extends UnitConversion[Real, UF, UT]:
            def apply(v: Real): Real = c * v

        class BigIntTUC[UF, UT](c: Rational)
            extends TruncatingUnitConversion[BigInt, UF, UT]:
            def apply(v: BigInt): BigInt = (c * v).toBigInt

        class RationalDUC[B, UF, UT](c: Rational, df: Rational, dt: Rational)
            extends DeltaUnitConversion[Rational, B, UF, UT]:
            def apply(v: Rational): Rational = ((v + df) * c) - dt

        class BigDecimalDUC[B, UF, UT](
            c: BigDecimal,
            df: BigDecimal,
            dt: BigDecimal
        ) extends DeltaUnitConversion[BigDecimal, B, UF, UT]:
            def apply(v: BigDecimal): BigDecimal = ((v + df) * c) - dt

        class AlgebraicDUC[B, UF, UT](
            c: Algebraic,
            df: Algebraic,
            dt: Algebraic
        ) extends DeltaUnitConversion[Algebraic, B, UF, UT]:
            def apply(v: Algebraic): Algebraic = ((v + df) * c) - dt

        class RealDUC[B, UF, UT](c: Real, df: Real, dt: Real)
            extends DeltaUnitConversion[Real, B, UF, UT]:
            def apply(v: Real): Real = ((v + df) * c) - dt

        class BigIntTDUC[B, UF, UT](c: Rational, df: Rational, dt: Rational)
            extends TruncatingDeltaUnitConversion[BigInt, B, UF, UT]:
            def apply(v: BigInt): BigInt = (((v + df) * c) - dt).toBigInt
