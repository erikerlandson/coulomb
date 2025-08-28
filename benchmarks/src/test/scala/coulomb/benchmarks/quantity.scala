import coulomb.testing.CoulombSuite

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
    def elapsed(t0: Double): Double =
        java.time.Instant.now.toEpochMilli.toDouble - t0

    def thruput(warm: Double, measure: Double)(expr: => Unit): Double =
        val t0 = java.time.Instant.now.toEpochMilli.toDouble
        while
            elapsed(t0) < warm
        do
            expr
        val t1 = java.time.Instant.now.toEpochMilli.toDouble
        var n = 0
        while
            elapsed(t1) < measure
        do
            expr
            n += 1
        n.toDouble / elapsed(t1)

object bmdata:
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}
    val data = Vector.fill(1000000) { math.random().withUnit[Meter] }

object xxx1:
    import scala.language.implicitConversions
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}
    import algebrasopt.given
    def sum(x: Quantity[Double, Meter], y: Quantity[Double, Meter]): Quantity[Double, Meter] = x + y

object xxx2:
    import scala.language.implicitConversions
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}
    import algebras.given
    def sum(x: Quantity[Double, Meter], y: Quantity[Double, Meter]): Quantity[Double, Meter] = x + y
 
class BenchmarkSuite extends CoulombSuite:
    test("thruput") {
        import scala.language.implicitConversions
        import coulomb.*
        import coulomb.syntax.*
        import coulomb.testing.units.{*, given}
        val tp1 = bmtime.thruput(1000, 5000){
            bmdata.data.foldLeft(0d.withUnit[Meter])(xxx1.sum) 
        }
        println(s"xxx1: $tp1")

        val tp2 = bmtime.thruput(1000, 5000){
            bmdata.data.foldLeft(0d.withUnit[Meter])(xxx2.sum) 
        }
        println(s"xxx2: $tp2")
    }


