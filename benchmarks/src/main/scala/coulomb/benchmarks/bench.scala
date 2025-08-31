package coulomb.benchmarks

import scala.language.implicitConversions

import coulomb.*
import coulomb.syntax.*
import coulomb.units.si.{*, given}
import coulomb.units.us.{*, given}
import coulomb.units.accepted.{*, given}

object algebras:
    import algebra.ring.*
    // not optimized with inlining
    given DoubleIsField: Field[Double] with
        def zero: Double = 0.0
        def one: Double = 1.0
        def plus(x: Double, y: Double): Double = x + y
        def negate(x: Double): Double = -x
        override def minus(x: Double, y: Double): Double = x - y
        def times(x: Double, y: Double): Double = x * y
        def div(x: Double, y: Double): Double = x / y

object algebrasopt:
    import algebra.ring.*
    // inlining typeclass methods can be leveraged by inline code
    // however so far, only for these "static" typeclass objects.
    // For cases where the typeclass has to be constructed per invocation,
    // it seems to be impossible for scala to make "full" use of the inlining,
    // so I am not going to try to in-line methods for typeclasses that
    // must be non-static functions of their types, for example UnitConversion
    given DoubleIsField: Field[Double] with
        inline def zero: Double = 0.0
        inline def one: Double = 1.0
        inline def plus(x: Double, y: Double): Double = x + y
        inline def negate(x: Double): Double = -x
        override inline def minus(x: Double, y: Double): Double = x - y
        inline def times(x: Double, y: Double): Double = x * y
        inline def div(x: Double, y: Double): Double = x / y

object bmtime:
    import java.time.Instant
    import algebras.given

    def now(): Quantity[Double, Second] =
        //val s = Instant.now().toEpochMilli.toDouble
        val s = System.currentTimeMillis().toDouble
        (s / 1000d).withUnit[Second]

    def elapsed(t0: Quantity[Double, Second]): Quantity[Double, Second] =
        now() - t0

    def thruput[T](warmup: Quantity[Double, Second], measure: Quantity[Double, Second])(expr: => T): Quantity[Double, 1 / Second] =
        val t0 = now()
        while
            elapsed(t0) < warmup
        do
            expr: Unit
        val t1 = now()
        var n = 0
        while
            elapsed(t1) < measure
        do
            expr: Unit
            n += 1
        n.toDouble.withUnit[1] / elapsed(t1)

object bmdata:
    val data = Vector.fill(100000) { math.random().withUnit[Meter] }

object sumOpt:
    import algebrasopt.given
    def sum1U(x: Quantity[Double, Meter], y: Quantity[Double, Meter]): Quantity[Double, Meter] = x + y
    def sum2U(x: Quantity[Double, Yard], y: Quantity[Double, Meter]): Quantity[Double, Yard] = x + y

object sumReg:
    import algebras.given
    def sum1U(x: Quantity[Double, Meter], y: Quantity[Double, Meter]): Quantity[Double, Meter] = x + y
    def sum2U(x: Quantity[Double, Yard], y: Quantity[Double, Meter]): Quantity[Double, Yard] = x + y
 
def bench[T](name: String)(expr: => T): Unit =
    println(s"$name:")
    expr: Unit

def pctImprovement[U](v1: Quantity[Double, U], v0: Quantity[Double, U]): Quantity[Double, Percent] =
    import algebras.given
    ((v1 - v0) / v0).toUnit[Percent]