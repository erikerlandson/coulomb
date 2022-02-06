package coulomb.units.si

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

final type Ampere
given BaseUnit[Ampere] with
    val name = "ampere"
    val abbv = "A"

final type Mole
given BaseUnit[Mole] with
    val name = "mole"
    val abbv = "mol"

final type Candela
given BaseUnit[Candela] with
    val name = "candela"
    val abbv = "cd"

final type Kelvin
given BaseUnit[Kelvin] with
    val name = "Kelvin"
    val abbv = "K"
