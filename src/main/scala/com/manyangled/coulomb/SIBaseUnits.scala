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

// TODO remove this eventually
object DemoUnits {
  import ChurchInt._
  import SIBaseUnits._

  trait Foot extends DerivedUnit[Meter]
  object Foot extends UnitCompanion[Foot]("foot", 0.3048)

  trait Yard extends DerivedUnit[Meter]
  object Yard extends UnitCompanion[Yard]("yard", 0.9144)

  trait Minute extends DerivedUnit[Second]
  object Minute extends UnitCompanion[Minute]("minute", 60.0)

  trait Pound extends DerivedUnit[Kilogram]
  object Pound extends UnitCompanion[Pound]("pound", 0.453592)

  trait Liter extends DerivedUnit[Meter <^> _3]
  object Liter extends UnitCompanion[Liter]("liter", 0.001)

  trait EarthGravity extends DerivedUnit[Meter </> (Second <^> _2)]
  object EarthGravity extends UnitCompanion[EarthGravity]("g", 9.807)

  trait Celsius extends DerivedTemperature
  object Celsius extends TempUnitCompanion[Celsius]("celsius", 1.0, 273.15)

  trait Fahrenheit extends DerivedTemperature
  object Fahrenheit extends TempUnitCompanion[Fahrenheit]("fahrenheit", 5.0 / 9.0, 459.67)
}
