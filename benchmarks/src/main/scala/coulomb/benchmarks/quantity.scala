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

package coulomb.benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.*

object algebras:
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

@State(Scope.Thread)
@Fork(1)
@BenchmarkMode(Array(Mode.Throughput))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 10, time = 2)
class QuantityBenchmark:
    import scala.language.implicitConversions
    import coulomb.*
    import coulomb.syntax.*
    import coulomb.testing.units.{*, given}
    import algebra.instances.all.given

    var data: Vector[Quantity[Double, Meter]] =
        Vector.empty[Quantity[Double, Meter]]

    @Setup(Level.Trial)
    def prepare: Unit =
        data = Vector.fill(100000) { math.random().withUnit[Meter] }

    @Benchmark
    def add1V1U(): Quantity[Double, Meter] =
        data.foldLeft(0d.withUnit[Meter]) { (s, x) => s + x }

    @Benchmark
    def add1V1U_opt(): Quantity[Double, Meter] =
        import coulomb.benchmarks.algebras.given
        data.foldLeft(0d.withUnit[Meter]) { (s, x) => s + x }

    @Benchmark
    def add1V2U(): Quantity[Double, Kilo * Meter] =
        data.foldLeft(0d.withUnit[Kilo * Meter]) { (s, x) => s + x }

    @Benchmark
    def add1V2U_opt(): Quantity[Double, Kilo * Meter] =
        import coulomb.benchmarks.algebras.given
        data.foldLeft(0d.withUnit[Kilo * Meter]) { (s, x) => s + x }
