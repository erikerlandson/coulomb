package coulomb.units.si.prefixes

import coulomb.define.*
import coulomb.rational.Rational

final type Kilo
given PrefixUnit[Kilo] with
    val name = "kilo"
    val abbv = "k"
    val coef = Rational(1000)

final type Mega
given PrefixUnit[Mega] with
    val name = "mega"
    val abbv = "M"
    val coef = Rational(1000).pow(2)

final type Giga
given PrefixUnit[Giga] with
    val name = "giga"
    val abbv = "G"
    val coef = Rational(1000).pow(3)

final type Milli
given PrefixUnit[Milli] with
    val name = "milli"
    val abbv = "m"
    val coef = Rational(1000).pow(-1)

final type Micro
given PrefixUnit[Micro] with
    val name = "micro"
    val abbv = "μ"
    val coef = Rational(1000).pow(-2)

final type Nano
given PrefixUnit[Nano] with
    val name = "nano"
    val abbv = "n"
    val coef = Rational(1000).pow(-3)
