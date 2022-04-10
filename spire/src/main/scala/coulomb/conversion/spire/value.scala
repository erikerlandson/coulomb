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

object value:
    import scala.util.NotGiven

    import spire.math.{ Rational => SpireRational, * }

    import coulomb.conversion.*
    import coulomb.rational.{ Rational => CoulombRational }

    given ctx_spire_VC_XF[VF, VT](using
        vtf: Fractional[VT],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
            ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    given ctx_spire_VC_II[VF, VT](using
        vti: NotGiven[Fractional[VT]],
        vfi: NotGiven[Fractional[VF]],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
            ): ValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    given ctx_spire_TVC_FI[VF, VT](using
        vti: NotGiven[Fractional[VT]],
        vff: Fractional[VF],
        cf: ConvertableFrom[VF],
        ct: ConvertableTo[VT]
            ): TruncatingValueConversion[VF, VT] =
        (v: VF) => ct.fromType(v)

    given ctx_ConvertableToCoulombRational: ConvertableTo[CoulombRational] with {
        import java.math.MathContext
        val bigint1 = BigInt(java.math.BigInteger.ONE)
        def fromByte(a: Byte): CoulombRational = CoulombRational(BigInt(a), bigint1)
        def fromShort(a: Short): CoulombRational = CoulombRational(BigInt(a), bigint1) 
        def fromInt(a: Int): CoulombRational = CoulombRational(BigInt(a), bigint1)
        def fromLong(a: Long): CoulombRational = CoulombRational(BigInt(a), bigint1)
        def fromFloat(a: Float): CoulombRational = fromRational(SpireRational(a))
        def fromDouble(a: Double): CoulombRational = fromRational(SpireRational(a))
        def fromBigInt(a: BigInt): CoulombRational = CoulombRational(a, bigint1)
        def fromBigDecimal(a: BigDecimal): CoulombRational = fromRational(SpireRational(a))
        def fromRational(a: SpireRational): CoulombRational =
            CoulombRational(a.numerator.toBigInt, a.denominator.toBigInt)
        def fromAlgebraic(a: Algebraic): CoulombRational =
            fromRational(a.toRational.getOrElse(SpireRational(a.toBigDecimal(MathContext.DECIMAL64))))
        def fromReal(a: Real): CoulombRational = fromRational(a.toRational)
        def fromType[B: ConvertableFrom](b: B): CoulombRational =
            fromRational(ConvertableFrom[B].toRational(b))
    }

    given ctx_ConvertableFromCoulombRational: ConvertableFrom[CoulombRational] with {
        import java.math.MathContext
        def toByte(a: CoulombRational): Byte = toBigInt(a).toByte
        def toShort(a: CoulombRational): Short = toBigInt(a).toShort
        def toInt(a: CoulombRational): Int = toBigInt(a).toInt
        def toLong(a: CoulombRational): Long = toBigInt(a).toLong
        def toFloat(a: CoulombRational): Float = toBigDecimal(a).toFloat
        def toDouble(a: CoulombRational): Double = toBigDecimal(a).toDouble
        def toBigInt(a: CoulombRational): BigInt = a.n / a.d
        def toBigDecimal(a: CoulombRational): BigDecimal = toRational(a).toBigDecimal(MathContext.DECIMAL64)
        def toRational(a: CoulombRational): SpireRational = SpireRational(a.n, a.d)
        def toAlgebraic(a: CoulombRational): Algebraic = Algebraic(toRational(a))
        def toReal(a: CoulombRational): Real = Real(toRational(a))
        def toNumber(a: CoulombRational): Number = Number(toRational(a))

        def toType[B: ConvertableTo](a: CoulombRational): B = ConvertableTo[B].fromRational(toRational(a))
        def toString(a: CoulombRational): String = a.toString 
    }

