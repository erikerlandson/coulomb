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
import scala.annotation.implicitNotFound

extension[VL, UL](ql: Quantity[VL, UL])
    transparent inline def show(using sh: Show[UL]): String = s"${ql.value.toString} ${sh.value}"
    transparent inline def showFull(using sh: ShowFull[UL]): String = s"${ql.value.toString} ${sh.value}"

    transparent inline def +[VR, UR](qr: Quantity[VR, UR])(using add: Add[VL, UL, VR, UR]): Quantity[add.VO, add.UO] =
        add(ql.value, qr.value).withUnit[add.UO]

    transparent inline def to[V, U](using
        conv: coulomb.conversion.Conversion[VL, UL, V, U]): Quantity[V, U] = conv(ql.value).withUnit[U]
    transparent inline def toValue[V](using
        conv: coulomb.conversion.Conversion[VL, UL, V, UL]): Quantity[V, UL] = conv(ql.value).withUnit[UL]
    transparent inline def toUnit[U](using
        conv: coulomb.conversion.Conversion[VL, UL, VL, U]): Quantity[VL, U] = conv(ql.value).withUnit[U]

def showUnit[U](using sh: Show[U]): String = sh.value
def showUnitFull[U](using sh: ShowFull[U]): String = sh.value

@implicitNotFound("No coefficient of conversion exists for unit types (${U1}) and (${U2})")
abstract class Coefficient[U1, U2]:
    val value: coulomb.rational.Rational
    override def toString = s"Coefficient($value)"

object Coefficient:
    transparent inline given [U1, U2]: Coefficient[U1, U2] =
        ${ coulomb.infra.meta.coefficient[U1, U2] }

object test:
    def foo[U1, U2](q1: Quantity[Double, U1], q2: Quantity[Double, U2])(using s1: Show[U1], s2: Show[U2], add1: Add[Double, U1, Double, U2], s3: Show[add1.UO]): String =
        val t: Quantity[add1.VO, add1.UO] = q1 + q2
        s"${q1.show}  ${q2.show}, ${t.show}"

object si:
    import coulomb.rational.Rational
    import coulomb.define.*

    final type Meter
    given BaseUnit[Meter] with
        val name = "meter"
        val abbv = "m"

    final type Kilogram
    given BaseUnit[Kilogram] with
        val name = "kilogram"
        val abbv = "kg"

    final type Second
    given BaseUnit[Second] with
        val name = "second"
        val abbv = "s"

    final type Liter
    given DerivedUnit[Liter, Meter ^ 3] with
        val name = "liter"
        val abbv = "L"
        val coef = Rational(1, 1000)

    final type Hertz
    given DerivedUnit1[Hertz, 1 / Second] with
        val name = "Hertz"
        val abbv = "Hz"

    final type Newton
    given DerivedUnit1[Newton, Kilogram * Meter / (Second ^ 2)] with
        val name = "Newton"
        val abbv = "N"

    final type Kilo
    given PrefixUnit[Kilo] with
        val name = "kilo"
        val abbv = "k"
        val coef = Rational(1000)

    final type Yard
    given yard: DerivedUnit[Yard, Meter] with
        val name = "yard"
        val abbv = "yd"
        val coef = Rational(9144, 10000)
