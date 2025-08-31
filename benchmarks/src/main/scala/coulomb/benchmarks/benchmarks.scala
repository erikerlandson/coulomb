import scala.language.implicitConversions

import coulomb.*
import coulomb.syntax.*
import coulomb.units.si.{*, given}
import coulomb.units.us.{*, given}
import coulomb.units.accepted.{*, given}

import coulomb.benchmarks.*

object Main:
    def main(args: Array[String]): Unit =
        bench("add-1U") {
            val tpreg = bmtime.thruput(5d.withUnit[Second], 10d.withUnit[Second]) {
                import algebras.given
                val x = 1d.withUnit[Meter] + 2d.withUnit[Meter] + 3d.withUnit[Meter] + 4d.withUnit[Meter]
            }
            println(s"sumReg: ${tpreg.show}")

            val tpopt = bmtime.thruput(5d.withUnit[Second], 10d.withUnit[Second]) {
                import algebrasopt.given
                val x = 1d.withUnit[Meter] + 2d.withUnit[Meter] + 3d.withUnit[Meter] + 4d.withUnit[Meter]
            }
            println(s"sumOpt: ${tpopt.show}")
            println(s"% improvement: ${pctImprovement(tpopt, tpreg).show}")
        }

        bench("sum-1U") {
            val tpreg = bmtime.thruput(5d.withUnit[Second], 10d.withUnit[Second]) {
                bmdata.data.foldLeft(0d.withUnit[Meter])(sumReg.sum1U) 
            }
            println(s"sumReg: ${tpreg.show}")

            val tpopt = bmtime.thruput(5d.withUnit[Second], 10d.withUnit[Second]) {
                bmdata.data.foldLeft(0d.withUnit[Meter])(sumOpt.sum1U) 
            }
            println(s"sumOpt: ${tpopt.show}")
            println(s"% improvement: ${pctImprovement(tpopt, tpreg).show}")
        }

        bench("sum-2U") {
            val tpreg = bmtime.thruput(5d.withUnit[Second], 10d.withUnit[Second]) {
                bmdata.data.foldLeft(0d.withUnit[Yard])(sumReg.sum2U) 
            }
            println(s"sumReg: ${tpreg.show}")

            val tpopt = bmtime.thruput(5d.withUnit[Second], 10d.withUnit[Second]) {
                bmdata.data.foldLeft(0d.withUnit[Yard])(sumOpt.sum2U) 
            }
            println(s"sumOpt: ${tpopt.show}")
            println(s"% improvement: ${pctImprovement(tpopt, tpreg).show}")
        }