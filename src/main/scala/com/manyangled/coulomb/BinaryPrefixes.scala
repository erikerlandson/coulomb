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
