package coulomb

import scala.reflect.runtime.universe._

trait UnitTypeName[T] {
  def name: String // fully qualified name
  def typeString: String
  override def toString(): String = typeString
}

object UnitTypeName {
  @inline
  def apply[T](implicit ev: UnitTypeName[T]): UnitTypeName[T] = ev

  // Implicit builder for `UnitTypeName` instances
  implicit def unitTypeName[T](implicit ut: WeakTypeTag[T]): UnitTypeName[T] = {
    val wtt = weakTypeOf[T]
    new UnitTypeName[T] {
      def work(t: Type): String = {
        t match { case TypeRef(pre, sym, args) =>
          val ss = sym.toString
                      .stripPrefix("free ")
                      .stripPrefix("trait ")
                      .stripPrefix("class ")
                      .stripPrefix("type ")
          val as = args.map(work)
          if (args.length <= 0) ss else (ss + "[" + as.mkString(",") + "]")
        }
      }
      def name: String = ut.tpe.typeSymbol.name.toString()
      def typeString: String = work(wtt)
    }
  }
}
