package com.manyangled.coulomb

object BinaryPrefixes {
  trait Kibi extends PrefixUnit
  object Kibi extends UnitCompanion[Kibi]("kibi", 1024.0)

  trait Mebi extends PrefixUnit
  object Mebi extends UnitCompanion[Mebi]("mebi", math.pow(1024.0, 2))

  trait Gibi extends PrefixUnit
  object Gibi extends UnitCompanion[Gibi]("gibi", math.pow(1024.0, 3))

  trait Tebi extends PrefixUnit
  object Tebi extends UnitCompanion[Tebi]("tebi", math.pow(1024.0, 4))

  trait Pebi extends PrefixUnit
  object Pebi extends UnitCompanion[Pebi]("pebi", math.pow(1024.0, 5))

  trait Exbi extends PrefixUnit
  object Exbi extends UnitCompanion[Exbi]("exbi", math.pow(1024.0, 6))

  trait Zebi extends PrefixUnit
  object Zebi extends UnitCompanion[Zebi]("zebi", math.pow(1024.0, 7))

  trait Yobi extends PrefixUnit
  object Yobi extends UnitCompanion[Yobi]("yobi", math.pow(1024.0, 8))
}
