package coulomb

import scala.reflect.runtime.universe._

trait UnitTypeName[T] {
  /** the name of type.path.Foo[Int] is 'Foo' */
  def name: String
  /** the typeString of type.path.Foo[Int] is Foo[Int] */
  def typeString: String

  override def toString(): String = typeString
  def ==(that: UnitTypeName[_]): Boolean =
    typeString == that.typeString
}

object UnitTypeName {
  @inline
  def apply[T](implicit ev: UnitTypeName[T]): UnitTypeName[T] = ev

  // Implicit builder for `UnitTypeName` instances
  implicit def unitTypeName[T](implicit ut: WeakTypeTag[T]): UnitTypeName[T] = {
    val wtt = weakTypeOf[T]
    def work(tpe: Type): String = {
      // dealias so type aliases are dereferenced consistently
      tpe.dealias match {
        case TypeRef(pre, sym, args) => {
          val ss = sym.toString
                      .stripPrefix("free ")
                      .stripPrefix("trait ")
                      .stripPrefix("class ")
                      .stripPrefix("type ")
          val as = args.map(work)
          if (args.length <= 0) ss else (ss + "[" + as.mkString(",") + "]")
        }
        // this catches literal types, e.g. Int(2)
        case t => t.toString
      }
    }
    val tn = ut.tpe.typeSymbol.name.toString()
    val ts = work(wtt)
    new UnitTypeName[T] {
      def name: String = tn
      def typeString: String = ts
    }
  }
}
