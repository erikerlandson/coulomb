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

/** Represents the product of two unit expressions L and R */
final type *[L, R]

/** Represents the unit division L / R */
final type /[L, R]

/** Represents raising unit expression B to integer power E */
final type ^[B, E]

@deprecated("Unitless should be replaced by integer literal type '1'")
final type Unitless = 1

export quantity.Quantity as Quantity
export quantity.withUnit as withUnit

object quantity:
    opaque type Quantity[V, U] = V

    // The only two methods I need in scope of the opaque type
    // are a way to lift raw values into a Quantity
    // and a way to extract raw values from a quantity

    abstract class Applier[U]:
        def apply[V](v: V): Quantity[V, U]
    object Applier:
        given [U]: Applier[U] = new Applier[U] { def apply[V](v: V): Quantity[V, U] = v } 

    // lift
    object Quantity:
        def apply[U](using a: Applier[U]) = a
        def apply[U](v: Int): Quantity[Int, U] = v
        def apply[U](v: Long): Quantity[Long, U] = v
        def apply[U](v: Float): Quantity[Float, U] = v
        def apply[U](v: Double): Quantity[Double, U] = v
    end Quantity

    // extract
    extension[V, U](ql: Quantity[V, U])
        def value: V = ql
    extension[U](ql: Quantity[Int, U])
        def value: Int = ql
    extension[U](ql: Quantity[Long, U])
        def value: Long = ql
    extension[U](ql: Quantity[Float, U])
        def value: Float = ql
    extension[U](ql: Quantity[Double, U])
        def value: Double = ql

    extension[V](v: V)
        def withUnit[U]: Quantity[V, U] = v
    extension(v: Int)
        def withUnit[U]: Quantity[Int, U] = v
    extension(v: Long)
        def withUnit[U]: Quantity[Long, U] = v
    extension(v: Float)
        def withUnit[U]: Quantity[Float, U] = v
    extension(v: Double)
        def withUnit[U]: Quantity[Double, U] = v

end quantity

import coulomb.ops.*
import coulomb.rational.Rational
import coulomb.conversion.{ValueConversion, UnitConversion}

inline def showUnit[U]: String = ${ coulomb.infra.show.show[U] }
inline def showUnitFull[U]: String = ${ coulomb.infra.show.showFull[U] }

/**
 * Obtain the coefficient of conversion from U1 -> U2
 * If U1 and U2 are not convertible, causes a compilation failure.
 */
inline def coefficient[U1, U2]: Rational = ${ coulomb.infra.meta.coefficient[U1, U2] }

extension[VL, UL](ql: Quantity[VL, UL])
    inline def show: String = s"${ql.value.toString} ${showUnit[UL]}"
    inline def showFull: String = s"${ql.value.toString} ${showUnitFull[UL]}"

    inline def toValue[V](using conv: ValueConversion[VL, V]): Quantity[V, UL] =
        conv(ql.value).withUnit[UL]
    inline def toUnit[U](using conv: UnitConversion[VL, UL, U]): Quantity[VL, U] =
        conv(ql.value).withUnit[U]

    transparent inline def +[VR, UR](qr: Quantity[VR, UR])(using add: Add[VL, UL, VR, UR]): Quantity[add.VO, add.UO] =
        add(ql.value, qr.value).withUnit[add.UO]

    transparent inline def -[VR, UR](qr: Quantity[VR, UR])(using sub: Sub[VL, UL, VR, UR]): Quantity[sub.VO, sub.UO] =
        sub(ql.value, qr.value).withUnit[sub.UO]

    transparent inline def *[VR, UR](qr: Quantity[VR, UR])(using mul: Mul[VL, UL, VR, UR]): Quantity[mul.VO, mul.UO] =
        mul(ql.value, qr.value).withUnit[mul.UO]

    transparent inline def /[VR, UR](qr: Quantity[VR, UR])(using div: Div[VL, UL, VR, UR]): Quantity[div.VO, div.UO] =
        div(ql.value, qr.value).withUnit[div.UO]

    transparent inline def pow[P](using pow: Pow[VL, UL, P]): Quantity[pow.VO, pow.UO] =
        pow(ql.value).withUnit[pow.UO]

    transparent inline def unary_-(using neg: Neg[VL, UL]): Quantity[VL, UL] =
        neg(ql.value).withUnit[UL]
