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

@State(Scope.Thread)
@Fork(1)
@BenchmarkMode(Array(Mode.Throughput, Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 5, time = 5)
class QuantityBenchmark:
    import coulomb.*
    import coulomb.testing.units.{*, given}
    import algebra.instances.all.given
    import coulomb.ops.algebra.all.given
    import coulomb.ops.standard.given
    import coulomb.ops.resolution.standard.given
    import coulomb.conversion.standard.all.given

    var data: Vector[Quantity[Double, Meter]] = Vector.empty[Quantity[Double, Meter]]

    @Setup(Level.Trial)
    def prepare: Unit =
        data = Vector.fill(100000) { math.random().withUnit[Meter] }

    @Benchmark
    def add(): Quantity[Double, Meter] =
        data.foldLeft(0d.withUnit[Meter]) { (s, x) => s + x }
        
