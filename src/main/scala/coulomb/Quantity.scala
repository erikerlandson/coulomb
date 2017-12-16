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

package coulomb

import scala.language.implicitConversions

import scala.reflect.runtime.universe._

import spire.math._
import spire.syntax._
import shapeless._
import shapeless.syntax.singleton._
import singleton.ops._

import infra._

import test._

trait UnitDefinition {
  def name: String
  def abbv: String
}

class BaseUnit[U](val name: String, val abbv: String) extends UnitDefinition {
  override def toString = s"BaseUnit($name, $abbv)"
}
object BaseUnit {
  def apply[U](name: String = "", abbv: String = "")(implicit ut: TypeTag[U]): BaseUnit[U] = {
    val n = if (name != "") name else ut.tpe.typeSymbol.name.toString.toLowerCase
    val a = if (abbv != "") abbv else n.take(1)
    new BaseUnit[U](n, a)
  }
}

class DerivedUnit[U, D](val coef: Rational, val name: String, val abbv: String) extends UnitDefinition {
  override def toString = s"DerivedUnit($coef, $name, $abbv)"
}
object DerivedUnit {
  def apply[U, D](coef: Rational = Rational(1), name: String = "", abbv: String = "")(implicit ut: TypeTag[U]): DerivedUnit[U, D] = {
    require(coef > 0, "Unit coefficients must be strictly > 0")
    val n = if (name != "") name else ut.tpe.typeSymbol.name.toString.toLowerCase
    val a = if (abbv != "") abbv else n.take(1)
    new DerivedUnit[U, D](coef, n, a)
  }
}

object PrefixUnit {
  def apply[U](coef: Rational = Rational(1), name: String = "", abbv: String = "")(implicit ut: TypeTag[U]): DerivedUnit[U, Unitless] =
    DerivedUnit[U, Unitless](coef, name, abbv)
}

trait Unitless
trait %*[L, R]
trait %/[L, R]
trait %^[B, E]

trait CanonicalSig[U] {
  type Out
  def coef: Rational
}

object CanonicalSig {
  type Aux[U, O] = CanonicalSig[U] { type Out = O }

  implicit def witnessUnitlessCM: Aux[Unitless, HNil] = {
    new CanonicalSig[Unitless] {
      type Out = HNil
      val coef = Rational(1)
    }
  }

  implicit def witnessBaseUnitCM[U](implicit buU: BaseUnit[U]): Aux[U, (U, XInt1) :: HNil] = {
    new CanonicalSig[U] {
      type Out = (U, XInt1) :: HNil
      val coef = Rational(1)
    }
  }

  implicit def witnessDerivedUnitCM[U, D, DC](implicit du: DerivedUnit[U, D], dm: Aux[D, DC]): Aux[U, DC] = {
    new CanonicalSig[U] {
      type Out = DC
      val coef = du.coef * dm.coef
    }
  }

  implicit def witnessMulCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] = {
    new CanonicalSig[%*[L, R]] {
      type Out = OC
      val coef = l.coef * r.coef
    }
  }

  implicit def witnessDivCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] = {
    new CanonicalSig[%/[L, R]] {
      type Out = OC
      val coef = l.coef / r.coef
    }
  }

  implicit def witnessPowCM[B, BC, E, OC](implicit b: Aux[B, BC], u: ApplySigPow.Aux[E, BC, OC], e: singleton.ops.Id[E]): Aux[%^[B, E], OC] = {
    new CanonicalSig[%^[B, E]] {
      type Out = OC
      val coef = b.coef.pow(e.value.asInstanceOf[Int])
    }
  }
}

trait StandardSig[U] {
  type Out
}
object StandardSig {
  type Aux[U, O] = StandardSig[U] { type Out = O }

  implicit def witnessUnitlessCM: Aux[Unitless, HNil] =
    new StandardSig[Unitless] { type Out = HNil }

  implicit def witnessBaseUnitCM[U](implicit buU: BaseUnit[U]): Aux[U, (U, XInt1) :: HNil] =
    new StandardSig[U] { type Out = (U, XInt1) :: HNil }

  implicit def witnessDerivedUnitCM[U](implicit du: DerivedUnit[U, _]): Aux[U, (U, XInt1) :: HNil] =
    new StandardSig[U] { type Out = (U, XInt1) :: HNil }

  implicit def witnessMulCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigMul.Aux[LC, RC, OC]): Aux[%*[L, R], OC] =
    new StandardSig[%*[L, R]] { type Out = OC }

  implicit def witnessDivCM[L, LC, R, RC, OC](implicit l: Aux[L, LC], r: Aux[R, RC], u: UnifySigDiv.Aux[RC, LC, OC]): Aux[%/[L, R], OC] =
    new StandardSig[%/[L, R]] { type Out = OC }

  implicit def witnessPowCM[B, BC, E, OC](implicit b: Aux[B, BC], u: ApplySigPow.Aux[E, BC, OC], e: singleton.ops.Id[E]): Aux[%^[B, E], OC] =
    new StandardSig[%^[B, E]] { type Out = OC }
}

// assuming inputs (U, P) are already from some canonical sig; so a type U will never pre-exist in M
trait InsertSortedUnitSig[U, P, M] {
  type Out
}
object InsertSortedUnitSig {
  type Aux[U, P, M, O] = InsertSortedUnitSig[U, P, M] { type Out = O }

  implicit def evidence0[U, P]: Aux[U, P, HNil, (U, P) :: HNil] =
    new InsertSortedUnitSig[U, P, HNil] { type Out = (U, P) :: HNil }

  implicit def evidence1[U, P, U0, P0, MT <: HList](implicit lte: XIntGT.Aux[P, P0, False]): Aux[U, P, (U0, P0) :: MT, (U, P) :: (U0, P0) :: MT] =
    new InsertSortedUnitSig[U, P, (U0, P0) :: MT] { type Out = (U, P) :: (U0, P0) :: MT }

  implicit def evidence2[U, P, U0, P0, MT <: HList, O <: HList](implicit gt: XIntGT.Aux[P, P0, True], rc: Aux[U, P, MT, O]): Aux[U, P, (U0, P0) :: MT, (U0, P0) :: O] =
    new InsertSortedUnitSig[U, P, (U0, P0) :: MT] { type Out = (U0, P0) :: O }

}

trait SortUnitSig[M, N, D] {
  type OutN
  type OutD
}

object SortUnitSig {
  type Aux[M, N, D, ON, OD] = SortUnitSig[M, N, D] { type OutN = ON; type OutD = OD }

  implicit def evidence0[N, D]: Aux[HNil, N, D, N, D] =
    new SortUnitSig[HNil, N, D] { type OutN = N; type OutD = D }

  implicit def evidence1[U, P, MT <: HList, N, D, NO, NF, DF](implicit pos: XIntGT.Aux[P, XInt0, True], ins: InsertSortedUnitSig.Aux[U, P, N, NO], rc: Aux[MT, NO, D, NF, DF]): Aux[(U, P) :: MT, N, D, NF, DF] =
    new SortUnitSig[(U, P) :: MT, N, D] { type OutN = NF; type OutD = DF }

  implicit def evidence2[U, P, MT <: HList, N, D, NP, DO, NF, DF](implicit neg: XIntLT.Aux[P, XInt0, True], n: XIntNeg.Aux[P, NP], ins: InsertSortedUnitSig.Aux[U, NP, D, DO], rc: Aux[MT, N, DO, NF, DF]): Aux[(U, P) :: MT, N, D, NF, DF] =
    new SortUnitSig[(U, P) :: MT, N, D] { type OutN = NF; type OutD = DF }
}

trait SigToUnit[S] {
  type Out
}
object SigToUnit {
  type Aux[S, U] = SigToUnit[S] { type Out = U }
  implicit def evidence0: Aux[HNil, Unitless] = new SigToUnit[HNil] { type Out = Unitless }

  implicit def evidence1[U, ST <: HList, UC](implicit cont: Aux[ST, UC]): Aux[(U, XInt0) :: ST, UC] =
    new SigToUnit[(U, XInt0) :: ST] { type Out = UC }

  implicit def evidence2[U, ST <: HList, UC](implicit cont: Aux[ST, UC], nu: UC =:!= Unitless): Aux[(U, XInt1) :: ST, %*[U, UC]] =
    new SigToUnit[(U, XInt1) :: ST] { type Out = %*[U, UC] }

  implicit def evidence3[U, P, ST <: HList, UC](implicit ne01: XIntNon01.Aux[P, True], cont: Aux[ST, UC], nu: UC =:!= Unitless): Aux[(U, P) :: ST, %*[%^[U, P], UC]] =
    new SigToUnit[(U, P) :: ST] { type Out = %*[%^[U, P], UC] }

  implicit def evidence4[U, ST <: HList](implicit cont: Aux[ST, Unitless]): Aux[(U, XInt1) :: ST, U] =
    new SigToUnit[(U, XInt1) :: ST] { type Out = U }

  implicit def evidence5[U, P, ST <: HList](implicit ne01: XIntNon01.Aux[P, True], cont: Aux[ST, Unitless]): Aux[(U, P) :: ST, %^[U, P]] =
    new SigToUnit[(U, P) :: ST] { type Out = %^[U, P] }
}

trait MulResultType[LU, RU] {
  type Out
}
object MulResultType {
  type Aux[LU, RU, O] = MulResultType[LU, RU] { type Out = O }

  implicit def result[LU, RU, OL, OR, OU, RT](implicit
    cnL: StandardSig.Aux[LU, OL],
    cnR: StandardSig.Aux[RU, OR],
    mu: UnifySigMul.Aux[OL, OR, OU],
    rt: SigToUnit.Aux[OU, RT]): Aux[LU, RU, RT] =
    new MulResultType[LU, RU] { type Out = RT }
}

trait DivResultType[LU, RU] {
  type Out
}
object DivResultType {
  type Aux[LU, RU, O] = DivResultType[LU, RU] { type Out = O }

  implicit def result[LU, RU, OL, OR, OU, RT](implicit
      cnL: StandardSig.Aux[LU, OL],
      cnR: StandardSig.Aux[RU, OR],
      mu: UnifySigDiv.Aux[OR, OL, OU],
      rt: SigToUnit.Aux[OU, RT]): Aux[LU, RU, RT] =
    new DivResultType[LU, RU] { type Out = RT }
}

trait PowResultType[U, P] {
  type Out
}
object PowResultType {
  type Aux[U, P, O] = PowResultType[U, P] { type Out = O }
  implicit def evidence[U, P, SS, AP, RT](implicit
      ss: StandardSig.Aux[U, SS],
      ap: ApplySigPow.Aux[P, SS, AP],
      rt: SigToUnit.Aux[AP, RT]): Aux[U, P, RT] =
    new PowResultType[U, P] { type Out = RT }
}

class ConvertableUnits[U1, U2](val coef: Rational)

object ConvertableUnits {
  implicit def witnessCU[U1, U2, C1, C2](implicit u1: CanonicalSig.Aux[U1, C1], u2: CanonicalSig.Aux[U2, C2], eq: SetEqual.Aux[C1, C2, True]): ConvertableUnits[U1, U2] =
    new ConvertableUnits[U1, U2](u1.coef / u2.coef)
}

trait Converter[N1, U1, N2, U2] {
  def apply(v: N1): N2
}
trait ConverterDefaultPriority {
  // This should be specialized for efficiency, however this rule would give an accurate conversion for any type
  implicit def witness[N1, U1, N2, U2](implicit cu: ConvertableUnits[U1, U2], cfN1: Numeric[N1], ctN2: Numeric[N2]): Converter[N1, U1, N2, U2] =
    new Converter[N1, U1, N2, U2] {
      def apply(v: N1): N2 = ctN2.fromType[Rational](cfN1.toType[Rational](v) * cu.coef)
    }
}
object Converter extends ConverterDefaultPriority {
  implicit def witnessDouble[U1, U2](implicit cu: ConvertableUnits[U1, U2]): Converter[Double, U1, Double, U2] = {
    val coef = cu.coef.toDouble
    new Converter[Double, U1, Double, U2] {
      def apply(v: Double): Double = v * coef
    }
  }
}

trait UnitStringAST
object UnitStringAST {
  case object Uni extends UnitStringAST
  case class Def(d: UnitDefinition) extends UnitStringAST
  case class Pre(p: UnitDefinition) extends UnitStringAST
  case class Mul(l: UnitStringAST, r: UnitStringAST) extends UnitStringAST
  case class Div(n: UnitStringAST, d: UnitStringAST) extends UnitStringAST
  case class Pow(b: UnitStringAST, e: Int) extends UnitStringAST
}

trait HasUnitStringAST[U] {
  def ast: UnitStringAST
  override def toString = ast.toString
}
object HasUnitStringAST {
  import UnitStringAST._

  implicit def evidence0: HasUnitStringAST[Unitless] =
    new HasUnitStringAST[Unitless] { val ast = Uni }

  implicit def evidence1[P](implicit d: DerivedUnit[P, Unitless]): HasUnitStringAST[P] =
    new HasUnitStringAST[P] { val ast = Pre(d) }

  implicit def evidence2[U, D](implicit d: DerivedUnit[U, D], nu: D =:!= Unitless): HasUnitStringAST[U] =
    new HasUnitStringAST[U] { val ast = Def(d) }

  implicit def evidence3[U](implicit d: BaseUnit[U]): HasUnitStringAST[U] =
    new HasUnitStringAST[U] { val ast = Def(d) }

  implicit def evidence4[L, R](implicit l: HasUnitStringAST[L], r: HasUnitStringAST[R]): HasUnitStringAST[%*[L, R]] =
    new HasUnitStringAST[%*[L, R]] { val ast = Mul(l.ast, r.ast) }

  implicit def evidence5[N, D](implicit n: HasUnitStringAST[N], d: HasUnitStringAST[D]): HasUnitStringAST[%/[N, D]] =
    new HasUnitStringAST[%/[N, D]] { val ast = Div(n.ast, d.ast) }

  implicit def evidence6[B, E](implicit b: HasUnitStringAST[B], e: XIntValue[E]): HasUnitStringAST[%^[B, E]] =
    new HasUnitStringAST[%^[B, E]] { val ast = Pow(b.ast, e.value) }
}

trait UnitString[U] {
  def full: String
  def abbv: String
}
object UnitString {
  import UnitStringAST._

  implicit def evidence[U](implicit uast: HasUnitStringAST[U]): UnitString[U] = {
    val fs = render(uast.ast, (d: UnitDefinition) => d.name)
    val as = render(uast.ast, (d: UnitDefinition) => d.abbv)
    new UnitString[U] {
      val full = fs
      val abbv = as
    }
  }

  def render(ast: UnitStringAST, f: UnitDefinition => String): String = ast match {
    case FlatMul(t) => termStrings(t, f).mkString(" ")
    case Div(Uni, d) => s"1/${paren(d, f)}"
    case Div(n, Uni) => render(n, f)
    case Div(n, d) => s"${paren(n, f)}/${paren(d, f)}"
    case Pow(b, e) => {
      val es = if (e < 0) s"($e)" else s"$e"
      s"${paren(b, f)}^$es"
    }
    case Uni => "unitless"
    case Def(d) => f(d)
    case Pre(d) => f(d)
    case _ => "!!!"
  }

  def paren(ast: UnitStringAST, f: UnitDefinition => String): String = {
    val str = render(ast, f)
    if (isAtomic(ast)) str else s"($str)"
  }

  object FlatMul {
    def unapply(ast: UnitStringAST): Option[List[UnitStringAST]] = ast match {
      case Mul(l, r) => {
        val lflat = l match {
          case FlatMul(lf) => lf
          case _ => List(l)
        }
        val rflat = r match {
          case FlatMul(rf) => rf
          case _ => List(r)
        }
        Option(lflat ++ rflat)
      }
      case _ => None
    }
  }

  def termStrings(terms: List[UnitStringAST], f: UnitDefinition => String): List[String] = terms match {
    case Nil => Nil
    case Pre(p) +: Def(d) +: tail => s"${f(p)}${f(d)}" :: termStrings(tail, f)
    case term +: tail => s"${paren(term, f)}" :: termStrings(tail, f)
    case _ => List("!!!")
  }

  def isAtomic(ast: UnitStringAST): Boolean = ast match {
    case Uni => true
    case Pre(_) => true
    case Def(_) => true
    case Pow(Def(_), _) => true
    case Pow(FlatMul(Pre(_) +: Def(_) +: Nil), _) => true
    case FlatMul(Pre(_) +: Def(_) +: Nil) => true
    case _ => false
  }
}

trait UnitOps[N, U] {
  def n: Numeric[N]
  def ustr: UnitString[U]
}
object UnitOps {
  implicit def evidence[N, U](implicit nn: Numeric[N], us: UnitString[U]): UnitOps[N, U] =
    new UnitOps[N, U] {
      val n = nn
      val ustr = us
    }
}

trait UnitPowerOps[N, U, P] {
  def n: Numeric[N]
  type PowRT
}
object UnitPowerOps {
  type Aux[N, U, P, PRT] = UnitPowerOps[N, U, P] { type PowRT = PRT }
  implicit def evidence[N, U, P](implicit
      nn: Numeric[N],
      prt: PowResultType[U, P]): Aux[N, U, P, prt.Out] =
    new UnitPowerOps[N, U, P] {
      type PowRT = prt.Out
      val n = nn
    }
}

trait UnitBinaryOps[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def n2: Numeric[N2]
  def cv12: Converter[N1, U1, N2, U2]
  def cv21: Converter[N2, U2, N1, U1]
  def cn12(x: N1): N2
  def cn21(x: N2): N1
  type MulRT12
  type MulRT21
  type DivRT12
  type DivRT21
}

object UnitBinaryOps {
  type Aux[N1, U1, N2, U2, MR12, MR21, DR12, DR21] = UnitBinaryOps[N1, U1, N2, U2] {
    type MulRT12 = MR12
    type MulRT21 = MR21
    type DivRT12 = DR12
    type DivRT21 = DR21      
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      cvv12: Converter[N1, U1, N2, U2],
      cvv21: Converter[N2, U2, N1, U1],
      mrt12: MulResultType[U1, U2],
      mrt21: MulResultType[U2, U1],
      drt12: DivResultType[U1, U2],
      drt21: DivResultType[U2, U1]): Aux[N1, U1, N2, U2, mrt12.Out, mrt21.Out, drt12.Out, drt21.Out] =
    new UnitBinaryOps[N1, U1, N2, U2] {
      val n1 = nn1
      val n2 = nn2
      val cv12 = cvv12
      val cv21 = cvv21
      def cn12(x: N1): N2 = nn1.toType[N2](x)
      def cn21(x: N2): N1 = nn2.toType[N1](x)
      type MulRT12 = mrt12.Out
      type MulRT21 = mrt21.Out
      type DivRT12 = drt12.Out
      type DivRT21 = drt21.Out
    }
}

class Quantity[N, U](val value: N) extends AnyVal with Serializable {

  override def toString = s"Quantity($value)"

  def show(implicit uo: UnitOps[N, U]): String = s"$value ${uo.ustr.abbv}"

  def showFull(implicit uo: UnitOps[N, U]): String = s"$value ${uo.ustr.full}"

  def showUnit(implicit uo: UnitOps[N, U]): String = uo.ustr.abbv

  def showUnitFull(implicit uo: UnitOps[N, U]): String = uo.ustr.full

  def unary_-() (implicit uo: UnitOps[N, U]): Quantity[N, U] =
    new Quantity[N, U](uo.n.negate(value))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.plus(value, ubo.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def *[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, ubo.MulRT12] =
    new Quantity[N, ubo.MulRT12](ubo.n1.times(value, ubo.cn21(rhs.value)))

  def /[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N, ubo.DivRT12] =
    new Quantity[N, ubo.DivRT12](ubo.n1.div(value, ubo.cn21(rhs.value)))

  def pow[P](implicit upo: UnitPowerOps[N, U, P], p: XIntValue[P]): Quantity[N, upo.PowRT] =
    new Quantity[N, upo.PowRT](upo.n.pow(value, p.value))

  def ===[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit ubo: UnitBinaryOps[N, U, N, U2]): Quantity[N, U2] =
    new Quantity[N, U2](ubo.cv12(value))

  def toNumeric[N2](implicit ubo: UnitBinaryOps[N, U, N2, U]): Quantity[N2, U] =
    new Quantity[N2, U](ubo.cn12(value))

  def to[N2, U2](implicit ubo: UnitBinaryOps[N, U, N2, U2]): Quantity[N2, U2] =
    new Quantity[N2, U2](ubo.cv12(value))
}

object test {
trait Meter
implicit val buMeter = BaseUnit[Meter]()

trait Yard
implicit val defineUnitYard = DerivedUnit[Yard, Meter](Rational(9144, 10000), abbv = "yd")

trait Foot
implicit val defineUnitFoot = DerivedUnit[Foot, Yard](Rational(1, 3), abbv = "ft")

trait Second
implicit val buSecond = BaseUnit[Second]()

trait Minute
implicit val duMinute = DerivedUnit[Minute, Second](Rational(60), abbv="min")

trait Kilo
implicit val defineUnitKilo = PrefixUnit[Kilo](Rational(1000))

trait Mega
implicit val defineUnitMega = PrefixUnit[Mega](Rational(10).pow(6))

trait Kelvin
implicit val defineUnitKelvin = BaseUnit[Kelvin](name = "Kelvin", abbv = "K")

trait Celsius
implicit val defineUnitCelsius = DerivedTemp[Celsius](coef = 1, off = Rational(27315, 100), name = "Celsius", abbv = "C")

trait Fahrenheit
implicit val defineUnitFahrenheit = DerivedTemp[Fahrenheit](coef = Rational(5, 9), off = Rational(45967, 100), name = "Fahrenheit", abbv = "F")
}


class DerivedTemp[U](coef: Rational, val off: Rational, name: String, abbv: String) extends DerivedUnit[U, Kelvin](coef, name, abbv) {
  override def toString = s"DerivedTemp($coef, $off, $name, $abbv)"
}
object DerivedTemp {
  def apply[U](coef: Rational = Rational(1), off: Rational = Rational(0), name: String, abbv: String)(implicit
    ut: TypeTag[U]): DerivedTemp[U] = new DerivedTemp[U](coef, off, name, abbv)

  // A slight hack that is used by TempConverter to simplify its rules
  implicit def evidencek2k: DerivedTemp[Kelvin] = DerivedTemp[Kelvin](name = "Kelvin", abbv = "K")
}

trait TempConverter[N1, U1, N2, U2] {
  def apply(v: N1): N2
}
trait TempConverterDefaultPriority {
  // this default rule should work well everywhere but may be overridden for efficiency
  implicit def evidence[N1, U1, N2, U2](implicit
      t1: DerivedTemp[U1], t2: DerivedTemp[U2],
      n1: Numeric[N1], n2: Numeric[N2]): TempConverter[N1, U1, N2, U2] = {
    val coef = t1.coef / t2.coef
    new TempConverter[N1, U1, N2, U2] {
      def apply(v: N1): N2 = {
        n2.fromType[Rational](((n1.toType[Rational](v) + t1.off) * coef) - t2.off)
      }
    }
  }
}
object TempConverter extends TempConverterDefaultPriority {
  // override the default temp-converter generation here for specific cases
}

trait TempOps[N, U] {
  def n: Numeric[N]
  def ustr: UnitString[U]
}
object TempOps {
  implicit def evidence[N, U](implicit
      t2k: DerivedTemp[U],
      nn: Numeric[N],
      us: UnitString[U]): TempOps[N, U] =
    new TempOps[N, U] {
      val n = nn
      val ustr = us
    }
}

trait TempBinaryOps[N1, U1, N2, U2] {
  def n1: Numeric[N1]
  def n2: Numeric[N2]
  def cv12: TempConverter[N1, U1, N2, U2]
  def cv21: TempConverter[N2, U2, N1, U1]
  def cn12(x: N1): N2
  def cn21(x: N2): N1
}

object TempBinaryOps {
  type Aux[N1, U1, N2, U2] = TempBinaryOps[N1, U1, N2, U2] {
  }
  implicit def evidence[N1, U1, N2, U2](implicit
      nn1: Numeric[N1],
      nn2: Numeric[N2],
      cvv12: TempConverter[N1, U1, N2, U2],
      cvv21: TempConverter[N2, U2, N1, U1]): Aux[N1, U1, N2, U2] =
    new TempBinaryOps[N1, U1, N2, U2] {
      val n1 = nn1
      val n2 = nn2
      val cv12 = cvv12
      val cv21 = cvv21
      def cn12(x: N1): N2 = nn1.toType[N2](x)
      def cn21(x: N2): N1 = nn2.toType[N1](x)
    }
}

class Temperature[N, U] private[coulomb] (val value: N) extends AnyVal with Serializable {
  override def toString = s"Temperature($value)"

  def show(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.abbv}"

  def showFull(implicit uo: TempOps[N, U]): String = s"$value ${uo.ustr.full}"

  def showUnit(implicit uo: TempOps[N, U]): String = uo.ustr.abbv

  def showUnitFull(implicit uo: TempOps[N, U]): String = uo.ustr.full

  def -[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Quantity[N, U] =
    new Quantity[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def +[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ubo.n1.plus(value, ubo.cv21(rhs.value)))

  def -[N2, U2](rhs: Quantity[N2, U2])(implicit ubo: UnitBinaryOps[N, U, N2, U2]): Temperature[N, U] =
    new Temperature[N, U](ubo.n1.minus(value, ubo.cv21(rhs.value)))

  def ===[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) == 0

  def =!=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) != 0

  def <[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) < 0

  def <=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) <= 0

  def >[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) > 0

  def >=[N2, U2](rhs: Temperature[N2, U2])(implicit ubo: TempBinaryOps[N, U, N2, U2]): Boolean =
    ubo.n1.compare(value, ubo.cv21(rhs.value)) >= 0

  def toUnit[U2](implicit ubo: TempBinaryOps[N, U, N, U2]): Temperature[N, U2] =
    new Temperature[N, U2](ubo.cv12(value))

  def toNumeric[N2](implicit ubo: TempBinaryOps[N, U, N2, U]): Temperature[N2, U] =
    new Temperature[N2, U](ubo.cn12(value))

  def to[N2, U2](implicit ubo: TempBinaryOps[N, U, N2, U2]): Temperature[N2, U2] =
    new Temperature[N2, U2](ubo.cv12(value))
}
