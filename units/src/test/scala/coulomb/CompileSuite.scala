package coulomb

import coulomb.*, coulomb.conversion.standard.all.given, coulomb.ops.standard.given, coulomb.ops.resolution.standard.given, algebra.instances.all.given, coulomb.ops.algebra.all.given, coulomb.units.mks.{*,given}, coulomb.rational.Rational, coulomb.units.mksa.{*,given}, coulomb.units.si.{*,given}, coulomb.units.temperature.{*, given}, coulomb.units.time.{*, given}
import coulomb.testing.CoulombSuite

class CompileSuite extends CoulombSuite:
    test("compiler bug?") {
        val res0 = 600.withEpochTime[Second]
        val res1 = res0 - 100.withEpochTime[Second]
        res0.show
    }
