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

import scala.language.experimental.macros
import scala.reflect.macros.whitebox

private [coulomb] class ChurchIntMacros(c0: whitebox.Context) extends MacroCommon(c0) {
  import c.universe._

  object IsZero {
    def unapply(tpe: Type): Boolean = (tpe =:= zeroType)
  }

  object IsInc {
    def unapply(tpe: Type): Option[Type] =
      if (tpe.typeConstructor =:= incType) Option(tpe.typeArgs(0)) else None
  }

  object IsDec {
    def unapply(tpe: Type): Option[Type] =
      if (tpe.typeConstructor =:= decType) Option(tpe.typeArgs(0)) else None
  }

  def computeValue(typeN: Type): Int = {
    typeN.dealias match {
      case IsZero() => 0
      case IsInc(ta) => computeValue(ta) + 1
      case IsDec(ta) => computeValue(ta) - 1
      case _ => {
        abort(s"UNIMPLEMENTED: type ${typeName(typeN)}")
        0
      }
    }
  }

  def churchIntValue[N: WeakTypeTag]: Tree = {
    val tpeN = weakTypeOf[N].dealias

    val n = computeValue(tpeN)
    val nq = q"$n"
    q"""
      _root_.com.manyangled.coulomb.ChurchIntValue[$tpeN]($nq)
    """
  }
}
