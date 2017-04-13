package com.manyangled.coulomb

import scala.language.experimental.macros
import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly

import spire.math._


@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class UnitDecl(val name: String, val coef: Rational = 1) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.unitDecl
}

@compileTimeOnly("Must enable the Scala macro paradise compiler plugin to expand static annotations")
class TempUnitDecl(name: String, coef: Rational, off: Rational) extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro UnitMacros.tempUnitDecl
}
