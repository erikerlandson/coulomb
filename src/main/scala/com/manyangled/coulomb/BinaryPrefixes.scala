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
  import spire.math.Rational

  @unitDecl("kibi", 1024)
  trait Kibi extends PrefixUnit

  @unitDecl("mebi", Rational(1024).pow(2))
  trait Mebi extends PrefixUnit

  @unitDecl("gibi", Rational(1024).pow(3))
  trait Gibi extends PrefixUnit

  @unitDecl("tebi", Rational(1024).pow(4))
  trait Tebi extends PrefixUnit

  @unitDecl("pebi", Rational(1024).pow(5))
  trait Pebi extends PrefixUnit

  @unitDecl("exbi", Rational(1024).pow(6))
  trait Exbi extends PrefixUnit

  @unitDecl("zebi", Rational(1024).pow(7))
  trait Zebi extends PrefixUnit

  @unitDecl("yobi", Rational(1024).pow(8))
  trait Yobi extends PrefixUnit
}
