/*
Copyright 2017 Erik Erlandson

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.manyangled.coulomb

object SIPrefixes {
  trait Deca extends PrefixUnit
  object Deca extends UnitCompanion[Deca]("deca", 1e+1)

  trait Hecto extends PrefixUnit
  object Hecto extends UnitCompanion[Hecto]("hecto", 1e+2)

  trait Kilo extends PrefixUnit
  object Kilo extends UnitCompanion[Kilo]("kilo", 1e+3)

  trait Mega extends PrefixUnit
  object Mega extends UnitCompanion[Mega]("mega", 1e+6)

  trait Giga extends PrefixUnit
  object Giga extends UnitCompanion[Giga]("giga", 1e+9)

  trait Tera extends PrefixUnit
  object Tera extends UnitCompanion[Tera]("tera", 1e+12)

  trait Peta extends PrefixUnit
  object Peta extends UnitCompanion[Peta]("peta", 1e+15)

  trait Exa extends PrefixUnit
  object Exa extends UnitCompanion[Exa]("exa", 1e+18)

  trait Zetta extends PrefixUnit
  object Zetta extends UnitCompanion[Zetta]("zetta", 1e+21)

  trait Yotta extends PrefixUnit
  object Yotta extends UnitCompanion[Yotta]("yotta", 1e+24)

  trait Deci extends PrefixUnit
  object Deci extends UnitCompanion[Deci]("deci", 1e-1)

  trait Centi extends PrefixUnit
  object Centi extends UnitCompanion[Centi]("centi", 1e-2)

  trait Milli extends PrefixUnit
  object Milli extends UnitCompanion[Milli]("milli", 1e-3)

  trait Micro extends PrefixUnit
  object Micro extends UnitCompanion[Micro]("micro", 1e-6)

  trait Nano extends PrefixUnit
  object Nano extends UnitCompanion[Nano]("nano", 1e-9)

  trait Pico extends PrefixUnit
  object Pico extends UnitCompanion[Pico]("pico", 1e-12)

  trait Femto extends PrefixUnit
  object Femto extends UnitCompanion[Femto]("femto", 1e-15)

  trait Atto extends PrefixUnit
  object Atto extends UnitCompanion[Atto]("atto", 1e-18)

  trait Zepto extends PrefixUnit
  object Zepto extends UnitCompanion[Zepto]("zepto", 1e-21)

  trait Yocto extends PrefixUnit
  object Yocto extends UnitCompanion[Yocto]("yocto", 1e-24)
}
