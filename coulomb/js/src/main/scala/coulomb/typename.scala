package coulomb

import scala.language.experimental.macros

import scala.reflect.macros.whitebox

import scala.reflect.macros.blackbox.Context

trait UnitTypeName[T] {
  def name: String // typeName
  def typeString: String
  def =:=[U](t: UnitTypeName[U]): Boolean
}

object UnitTypeName {
  @inline
  def apply[T](implicit ev: UnitTypeName[T]): UnitTypeName[T] = ev

  /**
    * Build an instance of `UnitTypeName` for the type `A`
    */
  def unitTypeNameImpl[A: c.WeakTypeTag](
    c: Context
  ): c.Expr[UnitTypeName[A]] = {
    import c.universe._
    val aType = weakTypeOf[A]
    val tpe: TypeName = aType.typeSymbol.asType.name
    val tparam: List[Symbol] = aType.typeSymbol.asType.typeParams

    val tpeName = tparam match {
      // for simple types just get the name
      case Nil => tpe.toString
      // This should recursively extract the name of A if it contains other types, like `List[Int]` but it needs more wore
      case x => s"${tpe.toString}[${x.map(_.asType.name).mkString(",")}]"
    }

    // Make the instance on quasi quotes
    // Some notes
    // tpeName containes the name of the type A
    // =:= definition could be a bit weak but it is only used for tests
    val src = q"""
      new _root_.coulomb.UnitTypeName[$aType] {
        def name: String = $tpeName
        def typeString: String = name
        def =:=[U](t: UnitTypeName[U]): Boolean = name == t.name
      }
    """

    c.Expr[UnitTypeName[A]](src)
  }

  // Implicit builder for `UnitTypeName` instances
  implicit def unitTypeName[T]: UnitTypeName[T] = macro unitTypeNameImpl[T]
}
