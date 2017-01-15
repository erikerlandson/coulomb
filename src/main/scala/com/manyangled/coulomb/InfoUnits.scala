package com.manyangled.coulomb

object InfoUnits {
  trait Byte extends BaseUnit
  object Byte extends UnitCompanion[Byte]("byte")

  trait Bit extends DerivedUnit[Byte]
  object Bit extends UnitCompanion[Bit]("bit", 0.125)

  trait Nat extends DerivedUnit[Bit]
  object Nat extends UnitCompanion[Nat]("nat", 1.4426950409)
}
