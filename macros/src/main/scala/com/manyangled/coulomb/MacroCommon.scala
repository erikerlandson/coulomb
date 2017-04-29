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

private [coulomb] class MacroCommon(val c: whitebox.Context) {
  import c.universe._

  import infraChurchInt._

  val zeroType = typeOf[ZeroChurchInt]
  val incType = typeOf[IncChurchInt[ZeroChurchInt]].typeConstructor
  val decType = typeOf[DecChurchInt[ZeroChurchInt]].typeConstructor

  def abort(msg: String) = c.abort(c.enclosingPosition, msg)

  def warn(msg: String) = c.warning(c.enclosingPosition, msg)

  def typeName(tpe: Type): String = tpe.typeSymbol.fullName

  def evalTree[T](tree: Tree) = c.eval(c.Expr[T](c.untypecheck(tree.duplicate)))

  def superClass(tpe: Type, sup: Type): Option[Type] = {
    val supSym = sup.typeSymbol
    val bc = tpe.baseClasses.drop(1)
    if (bc.count { bSym => bSym == supSym } < 1) None else Some(tpe.baseType(supSym))
  }

  // translates types like 'this.U' into the "actual" type
  def fixType(tpe: Type) = tpe.baseType(tpe.baseClasses.head)

  def weakType1[T1: WeakTypeTag]: Type = fixType(weakTypeOf[T1])

  def weakType2[T1: WeakTypeTag, T2: WeakTypeTag]: (Type, Type) =
    (fixType(weakTypeOf[T1]), fixType(weakTypeOf[T2]))

  def weakType3[T1: WeakTypeTag, T2: WeakTypeTag, T3: WeakTypeTag]: (Type, Type, Type) =
    (fixType(weakTypeOf[T1]), fixType(weakTypeOf[T2]), fixType(weakTypeOf[T3]))

  def churchType(i: Int): Type = {
    i match {
      case v if (v > 0) => appliedType(incType, List(churchType(v - 1)))
      case v if (v < 0) => appliedType(decType, List(churchType(v + 1)))
      case _ => zeroType
    }
  }
}  
