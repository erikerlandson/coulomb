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

import coulomb.testing.CoulombSuite

class CoefficientSuite extends CoulombSuite:
    import coulomb.*
    import coulomb.testing.units.{*, given}
    import coulomb.rational.Rational

    test("identical units") {
        assert(summon[Coefficient[Meter, Meter]].value eq Rational.const1)
        assert(summon[Coefficient[Liter, Liter]].value eq Rational.const1)
        assert(summon[Coefficient[Kilogram * Meter / (Second ^ 2),
                                  Kilogram * Meter / (Second ^ 2)]].value eq Rational.const1)
    }

    test("convertible units") {
        assert(summon[Coefficient[Meter, Yard]].value == Rational(10000, 9144))
        assert(summon[Coefficient[Meter ^ 3, Liter]].value == 1000)
        assert(summon[Coefficient[Kilogram * Meter / (Second ^ 2),
                                  Pound * Yard / (Minute ^ 2)]].value == Rational(50000000000000L,5760623099L))
        assert(summon[Coefficient[Meter ^ 3, 1000 * Liter]].value == 1)
        assert(summon[Coefficient[Meter, Kilo * Yard]].value == Rational(10, 9144))
    }

    test("non-convertible units") {
        assertCE("summon[Coefficient[Meter, Second]]")
        assertCE("summon[Coefficient[Meter ^ 2, Liter]]")
        assertCE("summon[Coefficient[Kilogram * Meter / (Second ^ 2), Pound * Yard / (Minute ^ 3)]]")
    }

    test("units with embedded coefficients") {
        assert(summon[Coefficient[10 * Meter, Meter]].value == 10)
        assert(summon[Coefficient[Meter, (1 / 3) * Meter]].value == 3)
        assert(summon[Coefficient[(10 ^ 6) * Meter, Meter]].value == 1000000)
        assert(summon[Coefficient[Meter,  1.5 * Meter]].value == Rational(2, 3))
        assert(summon[Coefficient[Meter,  1.25f * Meter]].value == Rational(4, 5))
        assert(summon[Coefficient[((1 / 3L) * (10L ^ 100)) * Meter, Meter]].value == Rational(1, 3) * Rational(10).pow(100))
    }
