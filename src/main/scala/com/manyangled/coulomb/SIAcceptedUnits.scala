package com.manyangled.coulomb

object SIAcceptedUnits {
  import ChurchInt._
  import SIBaseUnits._
  import MKSUnits.{ Radian, Pascal }

  trait Celsius extends DerivedTemperature
  object Celsius extends TempUnitCompanion[Celsius]("celsius", 1.0, 273.15)

  trait Minute extends DerivedUnit[Second]
  object Minute extends UnitCompanion[Minute]("minute", 60.0)

  trait Hour extends DerivedUnit[Second]
  object Hour extends UnitCompanion[Hour]("hour", 3600.0)

  trait Day extends DerivedUnit[Second]
  object Day extends UnitCompanion[Day]("day", 86400.0)

  trait Degree extends DerivedUnit[Radian]
  object Degree extends UnitCompanion[Degree]("degree", math.Pi / 180.0)

  trait ArcMinute extends DerivedUnit[Radian]
  object ArcMinute extends UnitCompanion[ArcMinute]("arcminute", math.Pi / 10800.0)

  trait ArcSecond extends DerivedUnit[Radian]
  object ArcSecond extends UnitCompanion[ArcSecond]("arcsecond", math.Pi / 648000.0)

  trait Hectare extends DerivedUnit[Meter <^> _2]
  object Hectare extends UnitCompanion[Hectare]("hectare", 10000.0)

  trait Liter extends DerivedUnit[Meter <^> _3]
  object Liter extends UnitCompanion[Liter]("liter", 0.001)

  trait Milliliter extends DerivedUnit[Liter]
  object Milliliter extends UnitCompanion[Milliliter]("milliliter", 0.001)

  trait Tonne extends DerivedUnit[Kilogram]
  object Tonne extends UnitCompanion[Tonne]("tonne", 1000.0)

  trait Millibar extends DerivedUnit[Pascal]
  object Millibar extends UnitCompanion[Millibar]("millibar", 100.0)

  trait Kilometer extends DerivedUnit[Meter]
  object Kilometer extends UnitCompanion[Kilometer]("kilometer", 1000.0)

  trait Millimeter extends DerivedUnit[Meter]
  object Millimeter extends UnitCompanion[Millimeter]("millimeter", 0.001)

  trait Gram extends DerivedUnit[Kilogram]
  object Gram extends UnitCompanion[Gram]("gram", 0.001)
}
