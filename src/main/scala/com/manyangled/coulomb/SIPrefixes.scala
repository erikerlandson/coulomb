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

  @UnitDecl("deca", 10)
  trait Deca extends PrefixUnit

  @UnitDecl("hecto", 100)
  trait Hecto extends PrefixUnit

  @UnitDecl("kilo", 1000)
  trait Kilo extends PrefixUnit

  @UnitDecl("mega", Rational(10).pow(6))
  trait Mega extends PrefixUnit

  @UnitDecl("giga", Rational(10).pow(9))
  trait Giga extends PrefixUnit

  @UnitDecl("tera", Rational(10).pow(12))
  trait Tera extends PrefixUnit

  @UnitDecl("peta", Rational(10).pow(15))
  trait Peta extends PrefixUnit

  @UnitDecl("exa", Rational(10).pow(18))
  trait Exa extends PrefixUnit

  @UnitDecl("zetta", Rational(10).pow(21))
  trait Zetta extends PrefixUnit

  @UnitDecl("yotta", Rational(10).pow(24))
  trait Yotta extends PrefixUnit

  @UnitDecl("deci", Rational(10).pow(-1))
  trait Deci extends PrefixUnit

  @UnitDecl("centi", Rational(10).pow(-2))
  trait Centi extends PrefixUnit

  @UnitDecl("milli", Rational(10).pow(-3))
  trait Milli extends PrefixUnit

  @UnitDecl("micro", Rational(10).pow(-6))
  trait Micro extends PrefixUnit

  @UnitDecl("nano", Rational(10).pow(-9))
  trait Nano extends PrefixUnit

  @UnitDecl("pico", Rational(10).pow(-12))
  trait Pico extends PrefixUnit

  @UnitDecl("femto", Rational(10).pow(-15))
  trait Femto extends PrefixUnit

  @UnitDecl("atto", Rational(10).pow(-18))
  trait Atto extends PrefixUnit

  @UnitDecl("zepto", Rational(10).pow(-21))
  trait Zepto extends PrefixUnit

  @UnitDecl("yocto", Rational(10).pow(-24))
  trait Yocto extends PrefixUnit
}
