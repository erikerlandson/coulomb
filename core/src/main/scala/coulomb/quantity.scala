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

package coulomb

import cats.kernel.{Eq, Hash, Order}
import coulomb.ops.*
import coulomb.rational.Rational
import coulomb.conversion.{ValueConversion, UnitConversion}
import coulomb.conversion.{TruncatingValueConversion, TruncatingUnitConversion}

/**
 * Represents the product of two unit expressions
 * @tparam L the left-hand unit subexpression
 * @tparam R the right-hand unit subexpression
 * {{{
 * type AcreFoot = (Acre * Foot)
 * }}}
 */
final type *[L, R]

/**
 * Represents unit division
 * @tparam L the left-hand unit subexpression (numerator)
 * @tparam R the right-hand unit subexpression (denominator)
 * {{{
 * type MPS = (Meter / Second)
 * }}}
 */
final type /[L, R]

/**
 * Represents raising unit expression B to rational power E
 * @tparam B a base unit expression
 * @tparam E a rational exponent
 * {{{
 * type V = (Meter ^ 3)
 * type H = (Second ^ -1)
 * type R = (Meter ^ (1 / 2))
 * }}}
 */
final type ^[B, E]

@deprecated("Unitless should be replaced by integer literal type '1'")
final type Unitless = 1

/**
 * obtain a string representation of a unit type, using unit abbreviation forms
 * @tparam U the unit type
 * @return the unit in string form
 * {{{
 * showUnit[Meter / Second] // => "m/s"
 * }}}
 */
inline def showUnit[U]: String = ${ coulomb.infra.show.show[U] }

/**
 * obtain a string representation of a unit type, using full unit names
 * @tparam U the unit type
 * @return the unit in string form
 * {{{
 * showUnitFull[Meter / Second] // => "meter/second"
 * }}}
 */
inline def showUnitFull[U]: String = ${ coulomb.infra.show.showFull[U] }

/**
 * Obtain the coefficient of conversion from U1 -> U2
 * If U1 and U2 are not convertible, causes a compilation failure.
 */
inline def coefficient[U1, U2]: Rational = ${ coulomb.infra.meta.coefficient[U1, U2] }

export qopaque.Quantity
export qopaque.withUnit

/**
 * Defines the opaque type Quantity[V, U] and associated lift and extract functions
 */
object qopaque:
    /**
     * Represents a value with an associated unit type
     * @tparam V the raw value type
     * @tparam U the unit type
     */
    opaque type Quantity[V, U] = V

    /**
     * Defines Quantity constructors that lift values into unit Quantities
     */
    object Quantity extends QuantityLowPriority0:
        /**
         * Lift a raw value of type V into a unit quantity
         * @tparam U the desired unit type
         * @return a Quantity with given value and unit type
         * {{{
         * val distance = Quantity[Meter](1.0)
         * }}}
         */
        def apply[U](using a: Applier[U]) = a

        /**
         * A shim class for Quantity companion object constructors
         */
        class Applier[U]:
            def apply[@specialized(Int, Long, Float, Double) V](v: V): Quantity[V, U] = v
        object Applier:
            given ctx_Applier[U]: Applier[U] = new Applier[U]

        inline given [V, U](using ord: Order[V]): Order[Quantity[V, U]] = ord

    private[qopaque] sealed class QuantityLowPriority0 extends QuantityLowPriority1:
        inline given [V, U](using hash: Hash[V]): Hash[Quantity[V, U]] = hash

    private[qopaque] sealed class QuantityLowPriority1:
        inline given [V, U](using eq: Eq[V]): Eq[Quantity[V, U]] = eq

    extension[@specialized(Int, Long, Float, Double) V](v: V)
        /**
         * Lift a raw value into a unit quantity
         * @tparam U the desired unit type
         * @return a Quantity with given value and unit type
         * {{{
         * val distance = (1.0).withUnit[Meter]
         * }}}
         */
        def withUnit[U]: Quantity[V, U] = v

    extension[@specialized(Int, Long, Float, Double) V, U](ql: Quantity[V, U])
        /**
         * extract the raw value of a unit quantity
         * @return the underlying value, stripped of its unit information
         * {{{
         * val q = (1.5).withUnit[Meter]
         * q.value // => 1.5
         * }}}
         */
        def value: V = ql

    extension[VL, UL](ql: Quantity[VL, UL])
        inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"
        inline def showFull: String = s"${ql.value.toString} ${showUnitFull[UL]}"

        inline def toValue[V](using conv: ValueConversion[VL, V]): Quantity[V, UL] =
            conv(ql.value).withUnit[UL]
        inline def toUnit[U](using conv: UnitConversion[VL, UL, U]): Quantity[VL, U] =
            conv(ql.value).withUnit[U]

        inline def tToValue[V](using conv: TruncatingValueConversion[VL, V]): Quantity[V, UL] =
            conv(ql.value).withUnit[UL]
        inline def tToUnit[U](using conv: TruncatingUnitConversion[VL, UL, U]): Quantity[VL, U] =
            conv(ql.value).withUnit[U]

        inline def unary_-(using neg: Neg[VL, UL]): Quantity[VL, UL] =
            neg(ql)

        transparent inline def +[VR, UR](qr: Quantity[VR, UR])(using add: Add[VL, UL, VR, UR]): Quantity[add.VO, add.UO] =
            add.eval(ql, qr)

        transparent inline def -[VR, UR](qr: Quantity[VR, UR])(using sub: Sub[VL, UL, VR, UR]): Quantity[sub.VO, sub.UO] =
            sub.eval(ql, qr)

        transparent inline def *[VR, UR](qr: Quantity[VR, UR])(using mul: Mul[VL, UL, VR, UR]): Quantity[mul.VO, mul.UO] =
            mul.eval(ql, qr)

        transparent inline def /[VR, UR](qr: Quantity[VR, UR])(using div: Div[VL, UL, VR, UR]): Quantity[div.VO, div.UO] =
            div.eval(ql, qr)

        transparent inline def tquot[VR, UR](qr: Quantity[VR, UR])(using tq: TQuot[VL, UL, VR, UR]): Quantity[tq.VO, tq.UO] =
            tq.eval(ql, qr)

        transparent inline def pow[P](using pow: Pow[VL, UL, P]): Quantity[pow.VO, pow.UO] =
            pow.eval(ql)

        transparent inline def tpow[P](using tp: TPow[VL, UL, P]): Quantity[tp.VO, tp.UO] =
            tp.eval(ql)

        inline def ===[VR, UR](qr: Quantity[VR, UR])(using ord: Ord[VL, UL, VR, UR]): Boolean =
            ord(ql, qr) == 0

        inline def =!=[VR, UR](qr: Quantity[VR, UR])(using ord: Ord[VL, UL, VR, UR]): Boolean =
            ord(ql, qr) != 0

        inline def <[VR, UR](qr: Quantity[VR, UR])(using ord: Ord[VL, UL, VR, UR]): Boolean =
            ord(ql, qr) < 0

        inline def <=[VR, UR](qr: Quantity[VR, UR])(using ord: Ord[VL, UL, VR, UR]): Boolean =
            ord(ql, qr) <= 0

        inline def >[VR, UR](qr: Quantity[VR, UR])(using ord: Ord[VL, UL, VR, UR]): Boolean =
            ord(ql, qr) > 0

        inline def >=[VR, UR](qr: Quantity[VR, UR])(using ord: Ord[VL, UL, VR, UR]): Boolean =
            ord(ql, qr) >= 0
    end extension
end qopaque
