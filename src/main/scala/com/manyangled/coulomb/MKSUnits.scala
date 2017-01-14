package com.manyangled.coulomb

object MKSUnits {
  import ChurchInt._
  import SIBaseUnits.{
    Meter => _, Kilogram => _, Second => _, Kelvin => _, Ampere => _, _
  }

  // These are declared as synonyms of SI base units
  // It is important to declare synonyms of base units as derived, that is never
  // re-declare a base unit that already exists.
  // As long as synonyms are properly declared to be exactly equivalent to "originals"
  // it is safe to import them together, because it will not matter which version the compiler
  // sees, and which gets shadowed

  trait Meter extends DerivedUnit[SIBaseUnits.Meter]
  object Meter extends UnitCompanion[Meter]("meter")

  trait Kilogram extends DerivedUnit[SIBaseUnits.Kilogram]
  object Kilogram extends UnitCompanion[Kilogram]("kilogram")

  trait Second extends DerivedUnit[SIBaseUnits.Second]
  object Second extends UnitCompanion[Second]("second")

  trait Ampere extends DerivedUnit[SIBaseUnits.Ampere]
  object Ampere extends UnitCompanion[Ampere]("ampere")

  trait Kelvin extends DerivedTemperature
  object Kelvin extends TempUnitCompanion[Kelvin]("kelvin", 1.0, 0.0)

  // These are derived from base units

  trait Radian extends DerivedUnit[Unitless]
  object Radian extends UnitCompanion[Radian]("radian")

  trait Hertz extends DerivedUnit[Second <^> _neg1]
  object Hertz extends UnitCompanion[Hertz]("hertz")

  trait Newton extends DerivedUnit[Kilogram <*> Meter </> (Second <^> _2)]
  object Newton extends UnitCompanion[Newton]("newton")

  trait Joule extends DerivedUnit[Newton <*> Meter]
  object Joule extends UnitCompanion[Joule]("joule")

  trait Watt extends DerivedUnit[Joule </> Second]
  object Watt extends UnitCompanion[Watt]("watt")

  trait Pascal extends DerivedUnit[Newton </> (Meter <^> _2)]
  object Pascal extends UnitCompanion[Pascal]("pascal")

  trait Coulomb extends DerivedUnit[Ampere <*> Second]
  object Coulomb extends UnitCompanion[Coulomb]("coulomb")

  trait Volt extends DerivedUnit[Watt </> Ampere]
  object Volt extends UnitCompanion[Volt]("volt")

  trait Ohm extends DerivedUnit[Volt </> Ampere]
  object Ohm extends UnitCompanion[Ohm]("ohm")

  trait Farad extends DerivedUnit[Coulomb </> Volt]
  object Farad extends UnitCompanion[Farad]("farad")

  trait Weber extends DerivedUnit[Joule </> Ampere]
  object Weber extends UnitCompanion[Weber]("weber")

  trait Tesla extends DerivedUnit[Weber </> (Meter <^> _2)]
  object Tesla extends UnitCompanion[Tesla]("tesla")

  trait Henry extends DerivedUnit[Weber </> Ampere]
  object Henry extends UnitCompanion[Henry]("henry")
}
