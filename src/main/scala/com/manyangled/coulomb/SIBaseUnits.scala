package com.manyangled.coulomb

object SIBaseUnits {
  trait Meter extends BaseUnit
  object Meter extends UnitCompanion[Meter]("meter")

  trait Second extends BaseUnit
  object Second extends UnitCompanion[Second]("second")

  trait Kilogram extends BaseUnit
  object Kilogram extends UnitCompanion[Kilogram]("kilogram")

  trait Ampere extends BaseUnit
  object Ampere extends UnitCompanion[Ampere]("ampere")

  trait Mole extends BaseUnit
  object Mole extends UnitCompanion[Mole]("mole")

  trait Candela extends BaseUnit
  object Candela extends UnitCompanion[Candela]("candela")

  trait Kelvin extends BaseTemperature
  object Kelvin extends TempUnitCompanion[Kelvin]("kelvin", 1.0, 0.0)
}
