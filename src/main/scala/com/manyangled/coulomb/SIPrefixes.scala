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
  import spire.math.Rational

  @unitDecl("deca", 10)
  trait Deca extends PrefixUnit

  @unitDecl("hecto", 100)
  trait Hecto extends PrefixUnit

  @unitDecl("kilo", 1000)
  trait Kilo extends PrefixUnit

  @unitDecl("mega", Rational(10).pow(6))
  trait Mega extends PrefixUnit

  @unitDecl("giga", Rational(10).pow(9))
  trait Giga extends PrefixUnit

  @unitDecl("tera", Rational(10).pow(12))
  trait Tera extends PrefixUnit

  @unitDecl("peta", Rational(10).pow(15))
  trait Peta extends PrefixUnit

  @unitDecl("exa", Rational(10).pow(18))
  trait Exa extends PrefixUnit

  @unitDecl("zetta", Rational(10).pow(21))
  trait Zetta extends PrefixUnit

  @unitDecl("yotta", Rational(10).pow(24))
  trait Yotta extends PrefixUnit

  @unitDecl("deci", Rational(10).pow(-1))
  trait Deci extends PrefixUnit

  @unitDecl("centi", Rational(10).pow(-2))
  trait Centi extends PrefixUnit

  @unitDecl("milli", Rational(10).pow(-3))
  trait Milli extends PrefixUnit

  @unitDecl("micro", Rational(10).pow(-6))
  trait Micro extends PrefixUnit

  @unitDecl("nano", Rational(10).pow(-9))
  trait Nano extends PrefixUnit

  @unitDecl("pico", Rational(10).pow(-12))
  trait Pico extends PrefixUnit

  @unitDecl("femto", Rational(10).pow(-15))
  trait Femto extends PrefixUnit

  @unitDecl("atto", Rational(10).pow(-18))
  trait Atto extends PrefixUnit

  @unitDecl("zepto", Rational(10).pow(-21))
  trait Zepto extends PrefixUnit

  @unitDecl("yocto", Rational(10).pow(-24))
  trait Yocto extends PrefixUnit
}
