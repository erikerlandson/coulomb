/* THIS FILE WAS MACHINE GENERATED, DO NOT EDIT */
package com.manyangled.unit4s

import scala.language.implicitConversions

import com.manyangled.church.{ Integer, IntegerValue, NonZero }
import Integer.{ _0, _1, _neg1, _2 }
import Unit.factor


case class UnitValue0(value: Double) {
  override def toString = Unit.toString(value, Seq())

  def +(uv: UnitValue0): UnitValue0 = UnitValue0(value + uv.value)
  def -(uv: UnitValue0): UnitValue0 = UnitValue0(value - uv.value)

  def pow[Q <: Integer with NonZero](implicit
    ivQ: IntegerValue[Q]): UnitValue0 =
    UnitValue0(math.pow(value, ivQ.value))

  def pow[Q <: _0]: UnitValue0 = UnitValue0(1.0)

  def inv: UnitValue0 =
    UnitValue0(1.0 / value)

  def sq: UnitValue0 =
    UnitValue0(value * value)

  def *(v: Double): UnitValue0 = UnitValue0(value * v)


  def *(rhs: RHSv0a0sc0sM[UZ, UZ, UZ, _0, _0, _0]): UnitValue0 =
    UnitValue0(value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv1a0sc0sM[UZ, UZ, UZ, _0, _0, _0, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue1[Ua, Qa] =
    UnitValue1[Ua, Qa](value * rhs.value)

  def *[Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv2a0sc0sM[UZ, UZ, UZ, _0, _0, _0, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb]): UnitValue2[Ua, Qa, Ub, Qb] =
    UnitValue2[Ua, Qa, Ub, Qb](value * rhs.value)

  def *[Ua <: Unit, Ub <: Unit, Uc <: Unit, Qa <: Integer, Qb <: Integer, Qc <: Integer](rhs: RHSv3a0sc0sM[UZ, UZ, UZ, _0, _0, _0, Ua, Ub, Uc, Qa, Qb, Qc])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb], ivQc: IntegerValue[Qc]): UnitValue3[Ua, Qa, Ub, Qb, Uc, Qc] =
    UnitValue3[Ua, Qa, Ub, Qb, Uc, Qc](value * rhs.value)

  def /(rhs: RHSv0a0sc0sD[UZ, UZ, UZ, _0, _0, _0]): UnitValue0 =
    UnitValue0(value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv1a0sc0sD[UZ, UZ, UZ, _0, _0, _0, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue1[Ua, Qa#Neg] =
    UnitValue1[Ua, Qa#Neg](value / rhs.value)

  def /[Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv2a0sc0sD[UZ, UZ, UZ, _0, _0, _0, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa#Neg], ivQb: IntegerValue[Qb#Neg]): UnitValue2[Ua, Qa#Neg, Ub, Qb#Neg] =
    UnitValue2[Ua, Qa#Neg, Ub, Qb#Neg](value / rhs.value)

  def /[Ua <: Unit, Ub <: Unit, Uc <: Unit, Qa <: Integer, Qb <: Integer, Qc <: Integer](rhs: RHSv3a0sc0sD[UZ, UZ, UZ, _0, _0, _0, Ua, Ub, Uc, Qa, Qb, Qc])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivQa: IntegerValue[Qa#Neg], ivQb: IntegerValue[Qb#Neg], ivQc: IntegerValue[Qc#Neg]): UnitValue3[Ua, Qa#Neg, Ub, Qb#Neg, Uc, Qc#Neg] =
    UnitValue3[Ua, Qa#Neg, Ub, Qb#Neg, Uc, Qc#Neg](value / rhs.value)
}

object UnitValue0 {
  
}

case class UnitValue1[U1 <: Unit, P1 <: Integer](value: Double)(implicit
  urec1: UnitRec[U1],
  iv1: IntegerValue[P1]) {
  override def toString = Unit.toString(value, Seq((urec1.name, iv1.value)))

  def +(uv: UnitValue1[U1, P1]): UnitValue1[U1, P1] = UnitValue1[U1, P1](value + uv.value)
  def -(uv: UnitValue1[U1, P1]): UnitValue1[U1, P1] = UnitValue1[U1, P1](value - uv.value)

  def pow[Q <: Integer with NonZero](implicit
    ivQ: IntegerValue[Q],
    powP1: IntegerValue[P1#Mul[Q]]): UnitValue1[U1, P1#Mul[Q]] =
    UnitValue1[U1, P1#Mul[Q]](math.pow(value, ivQ.value))

  def pow[Q <: _0]: UnitValue0 = UnitValue0(1.0)

  def inv(implicit
    invP1: IntegerValue[P1#Neg]): UnitValue1[U1, P1#Neg] =
    UnitValue1[U1, P1#Neg](1.0 / value)

  def sq(implicit
    sqP1: IntegerValue[P1#Mul[_2]]): UnitValue1[U1, P1#Mul[_2]] =
    UnitValue1[U1, P1#Mul[_2]](value * value)

  def *(v: Double): UnitValue1[U1, P1] = UnitValue1[U1, P1](value * v)


  def *(rhs: RHSv0a0sc0sM[U1, UZ, UZ, P1, _0, _0]): UnitValue1[U1, P1] =
    UnitValue1[U1, P1](value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv1a0sc0sM[U1, UZ, UZ, P1, _0, _0, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue2[U1, P1, Ua, Qa] =
    UnitValue2[U1, P1, Ua, Qa](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv1a1s0c0sM[U1, UZ, UZ, P1, _0, _0, Q1])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue1[U1, P1#Add[Q1]] =
    UnitValue1[U1, P1#Add[Q1]](value * rhs.value)

  def *(rhs: RHSv1a1s0c1s0M[U1, UZ, UZ, P1, _0, _0, P1#Neg]): UnitValue0 =
    UnitValue0(value * rhs.value)

  def *[Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv2a0sc0sM[U1, UZ, UZ, P1, _0, _0, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb]): UnitValue3[U1, P1, Ua, Qa, Ub, Qb] =
    UnitValue3[U1, P1, Ua, Qa, Ub, Qb](value * rhs.value)

  def *[Q1 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c0sM[U1, UZ, UZ, P1, _0, _0, Q1, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa],
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue2[U1, P1#Add[Q1], Ua, Qa] =
    UnitValue2[U1, P1#Add[Q1], Ua, Qa](value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c1s0M[U1, UZ, UZ, P1, _0, _0, P1#Neg, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue1[Ua, Qa] =
    UnitValue1[Ua, Qa](value * rhs.value)

  def *[Q1 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv3a1s0c0sM[U1, UZ, UZ, P1, _0, _0, Q1, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb],
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue3[U1, P1#Add[Q1], Ua, Qa, Ub, Qb] =
    UnitValue3[U1, P1#Add[Q1], Ua, Qa, Ub, Qb](value * rhs.value)

  def *[Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv3a1s0c1s0M[U1, UZ, UZ, P1, _0, _0, P1#Neg, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb]): UnitValue2[Ua, Qa, Ub, Qb] =
    UnitValue2[Ua, Qa, Ub, Qb](value * rhs.value)

  def /(rhs: RHSv0a0sc0sD[U1, UZ, UZ, P1, _0, _0]): UnitValue1[U1, P1] =
    UnitValue1[U1, P1](value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv1a0sc0sD[U1, UZ, UZ, P1, _0, _0, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue2[U1, P1, Ua, Qa#Neg] =
    UnitValue2[U1, P1, Ua, Qa#Neg](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv1a1s0c0sD[U1, UZ, UZ, P1, _0, _0, Q1])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue1[U1, P1#Sub[Q1]] =
    UnitValue1[U1, P1#Sub[Q1]](value / rhs.value)

  def /(rhs: RHSv1a1s0c1s0D[U1, UZ, UZ, P1, _0, _0, P1]): UnitValue0 =
    UnitValue0(value / rhs.value)

  def /[Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv2a0sc0sD[U1, UZ, UZ, P1, _0, _0, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa#Neg], ivQb: IntegerValue[Qb#Neg]): UnitValue3[U1, P1, Ua, Qa#Neg, Ub, Qb#Neg] =
    UnitValue3[U1, P1, Ua, Qa#Neg, Ub, Qb#Neg](value / rhs.value)

  def /[Q1 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c0sD[U1, UZ, UZ, P1, _0, _0, Q1, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg],
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue2[U1, P1#Sub[Q1], Ua, Qa#Neg] =
    UnitValue2[U1, P1#Sub[Q1], Ua, Qa#Neg](value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c1s0D[U1, UZ, UZ, P1, _0, _0, P1, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue1[Ua, Qa#Neg] =
    UnitValue1[Ua, Qa#Neg](value / rhs.value)

  def /[Q1 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv3a1s0c0sD[U1, UZ, UZ, P1, _0, _0, Q1, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa#Neg], ivQb: IntegerValue[Qb#Neg],
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue3[U1, P1#Sub[Q1], Ua, Qa#Neg, Ub, Qb#Neg] =
    UnitValue3[U1, P1#Sub[Q1], Ua, Qa#Neg, Ub, Qb#Neg](value / rhs.value)

  def /[Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](rhs: RHSv3a1s0c1s0D[U1, UZ, UZ, P1, _0, _0, P1, Ua, Ub, Qa, Qb])(implicit
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivQa: IntegerValue[Qa#Neg], ivQb: IntegerValue[Qb#Neg]): UnitValue2[Ua, Qa#Neg, Ub, Qb#Neg] =
    UnitValue2[Ua, Qa#Neg, Ub, Qb#Neg](value / rhs.value)
}

object UnitValue1 {
  
  implicit def uvc$v1p0[Ua <: Unit, U1 <: Unit, P1 <: Integer](uv: UnitValue1[Ua, P1])(implicit
    ureca: UnitRec[Ua],
    urec1: UnitRec[U1],
    iv1: IntegerValue[P1],
    eqa1: Ua#RU =:= U1#RU): UnitValue1[U1, P1] = {
    val fp = Seq((factor[Ua, U1], iv1.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue1[U1, P1](v)
  }
}

case class UnitValue2[U1 <: Unit, P1 <: Integer, U2 <: Unit, P2 <: Integer](value: Double)(implicit
  urec1: UnitRec[U1], urec2: UnitRec[U2],
  iv1: IntegerValue[P1], iv2: IntegerValue[P2]) {
  override def toString = Unit.toString(value, Seq((urec1.name, iv1.value), (urec2.name, iv2.value)))

  def +(uv: UnitValue2[U1, P1, U2, P2]): UnitValue2[U1, P1, U2, P2] = UnitValue2[U1, P1, U2, P2](value + uv.value)
  def -(uv: UnitValue2[U1, P1, U2, P2]): UnitValue2[U1, P1, U2, P2] = UnitValue2[U1, P1, U2, P2](value - uv.value)

  def pow[Q <: Integer with NonZero](implicit
    ivQ: IntegerValue[Q],
    powP1: IntegerValue[P1#Mul[Q]], powP2: IntegerValue[P2#Mul[Q]]): UnitValue2[U1, P1#Mul[Q], U2, P2#Mul[Q]] =
    UnitValue2[U1, P1#Mul[Q], U2, P2#Mul[Q]](math.pow(value, ivQ.value))

  def pow[Q <: _0]: UnitValue0 = UnitValue0(1.0)

  def inv(implicit
    invP1: IntegerValue[P1#Neg], invP2: IntegerValue[P2#Neg]): UnitValue2[U1, P1#Neg, U2, P2#Neg] =
    UnitValue2[U1, P1#Neg, U2, P2#Neg](1.0 / value)

  def sq(implicit
    sqP1: IntegerValue[P1#Mul[_2]], sqP2: IntegerValue[P2#Mul[_2]]): UnitValue2[U1, P1#Mul[_2], U2, P2#Mul[_2]] =
    UnitValue2[U1, P1#Mul[_2], U2, P2#Mul[_2]](value * value)

  def *(v: Double): UnitValue2[U1, P1, U2, P2] = UnitValue2[U1, P1, U2, P2](value * v)


  def *(rhs: RHSv0a0sc0sM[U1, U2, UZ, P1, P2, _0]): UnitValue2[U1, P1, U2, P2] =
    UnitValue2[U1, P1, U2, P2](value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv1a0sc0sM[U1, U2, UZ, P1, P2, _0, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue3[U1, P1, U2, P2, Ua, Qa] =
    UnitValue3[U1, P1, U2, P2, Ua, Qa](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv1a1s0c0sM[U1, U2, UZ, P1, P2, _0, Q1])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue2[U1, P1#Add[Q1], U2, P2] =
    UnitValue2[U1, P1#Add[Q1], U2, P2](value * rhs.value)

  def *(rhs: RHSv1a1s0c1s0M[U1, U2, UZ, P1, P2, _0, P1#Neg]): UnitValue1[U2, P2] =
    UnitValue1[U2, P2](value * rhs.value)

  def *[Q2 <: Integer](rhs: RHSv1a1s1c0sM[U1, U2, UZ, P1, P2, _0, Q2])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue2[U1, P1, U2, P2#Add[Q2]] =
    UnitValue2[U1, P1, U2, P2#Add[Q2]](value * rhs.value)

  def *(rhs: RHSv1a1s1c1s1M[U1, U2, UZ, P1, P2, _0, P2#Neg]): UnitValue1[U1, P1] =
    UnitValue1[U1, P1](value * rhs.value)

  def *[Q1 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c0sM[U1, U2, UZ, P1, P2, _0, Q1, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa],
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue3[U1, P1#Add[Q1], U2, P2, Ua, Qa] =
    UnitValue3[U1, P1#Add[Q1], U2, P2, Ua, Qa](value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c1s0M[U1, U2, UZ, P1, P2, _0, P1#Neg, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue2[U2, P2, Ua, Qa] =
    UnitValue2[U2, P2, Ua, Qa](value * rhs.value)

  def *[Q2 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s1c0sM[U1, U2, UZ, P1, P2, _0, Q2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa],
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue3[U1, P1, U2, P2#Add[Q2], Ua, Qa] =
    UnitValue3[U1, P1, U2, P2#Add[Q2], Ua, Qa](value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s1c1s1M[U1, U2, UZ, P1, P2, _0, P2#Neg, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue2[U1, P1, Ua, Qa] =
    UnitValue2[U1, P1, Ua, Qa](value * rhs.value)

  def *[Q1 <: Integer, Q2 <: Integer](rhs: RHSv2a2s01c0sM[U1, U2, UZ, P1, P2, _0, Q1, Q2])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P2#Add[Q2]]): UnitValue2[U1, P1#Add[Q1], U2, P2#Add[Q2]] =
    UnitValue2[U1, P1#Add[Q1], U2, P2#Add[Q2]](value * rhs.value)

  def *[Q2 <: Integer](rhs: RHSv2a2s01c1s0M[U1, U2, UZ, P1, P2, _0, P1#Neg, Q2])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue1[U2, P2#Add[Q2]] =
    UnitValue1[U2, P2#Add[Q2]](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv2a2s01c1s1M[U1, U2, UZ, P1, P2, _0, Q1, P2#Neg])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue1[U1, P1#Add[Q1]] =
    UnitValue1[U1, P1#Add[Q1]](value * rhs.value)

  def *(rhs: RHSv2a2s01c2s01M[U1, U2, UZ, P1, P2, _0, P1#Neg, P2#Neg]): UnitValue0 =
    UnitValue0(value * rhs.value)

  def *[Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c0sM[U1, U2, UZ, P1, P2, _0, Q1, Q2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa],
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P2#Add[Q2]]): UnitValue3[U1, P1#Add[Q1], U2, P2#Add[Q2], Ua, Qa] =
    UnitValue3[U1, P1#Add[Q1], U2, P2#Add[Q2], Ua, Qa](value * rhs.value)

  def *[Q2 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c1s0M[U1, U2, UZ, P1, P2, _0, P1#Neg, Q2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa],
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue2[U2, P2#Add[Q2], Ua, Qa] =
    UnitValue2[U2, P2#Add[Q2], Ua, Qa](value * rhs.value)

  def *[Q1 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c1s1M[U1, U2, UZ, P1, P2, _0, Q1, P2#Neg, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa],
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue2[U1, P1#Add[Q1], Ua, Qa] =
    UnitValue2[U1, P1#Add[Q1], Ua, Qa](value * rhs.value)

  def *[Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c2s01M[U1, U2, UZ, P1, P2, _0, P1#Neg, P2#Neg, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa]): UnitValue1[Ua, Qa] =
    UnitValue1[Ua, Qa](value * rhs.value)

  def /(rhs: RHSv0a0sc0sD[U1, U2, UZ, P1, P2, _0]): UnitValue2[U1, P1, U2, P2] =
    UnitValue2[U1, P1, U2, P2](value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv1a0sc0sD[U1, U2, UZ, P1, P2, _0, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue3[U1, P1, U2, P2, Ua, Qa#Neg] =
    UnitValue3[U1, P1, U2, P2, Ua, Qa#Neg](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv1a1s0c0sD[U1, U2, UZ, P1, P2, _0, Q1])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue2[U1, P1#Sub[Q1], U2, P2] =
    UnitValue2[U1, P1#Sub[Q1], U2, P2](value / rhs.value)

  def /(rhs: RHSv1a1s0c1s0D[U1, U2, UZ, P1, P2, _0, P1]): UnitValue1[U2, P2] =
    UnitValue1[U2, P2](value / rhs.value)

  def /[Q2 <: Integer](rhs: RHSv1a1s1c0sD[U1, U2, UZ, P1, P2, _0, Q2])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue2[U1, P1, U2, P2#Sub[Q2]] =
    UnitValue2[U1, P1, U2, P2#Sub[Q2]](value / rhs.value)

  def /(rhs: RHSv1a1s1c1s1D[U1, U2, UZ, P1, P2, _0, P2]): UnitValue1[U1, P1] =
    UnitValue1[U1, P1](value / rhs.value)

  def /[Q1 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c0sD[U1, U2, UZ, P1, P2, _0, Q1, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg],
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue3[U1, P1#Sub[Q1], U2, P2, Ua, Qa#Neg] =
    UnitValue3[U1, P1#Sub[Q1], U2, P2, Ua, Qa#Neg](value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s0c1s0D[U1, U2, UZ, P1, P2, _0, P1, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue2[U2, P2, Ua, Qa#Neg] =
    UnitValue2[U2, P2, Ua, Qa#Neg](value / rhs.value)

  def /[Q2 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s1c0sD[U1, U2, UZ, P1, P2, _0, Q2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg],
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue3[U1, P1, U2, P2#Sub[Q2], Ua, Qa#Neg] =
    UnitValue3[U1, P1, U2, P2#Sub[Q2], Ua, Qa#Neg](value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv2a1s1c1s1D[U1, U2, UZ, P1, P2, _0, P2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue2[U1, P1, Ua, Qa#Neg] =
    UnitValue2[U1, P1, Ua, Qa#Neg](value / rhs.value)

  def /[Q1 <: Integer, Q2 <: Integer](rhs: RHSv2a2s01c0sD[U1, U2, UZ, P1, P2, _0, Q1, Q2])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P2#Sub[Q2]]): UnitValue2[U1, P1#Sub[Q1], U2, P2#Sub[Q2]] =
    UnitValue2[U1, P1#Sub[Q1], U2, P2#Sub[Q2]](value / rhs.value)

  def /[Q2 <: Integer](rhs: RHSv2a2s01c1s0D[U1, U2, UZ, P1, P2, _0, P1, Q2])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue1[U2, P2#Sub[Q2]] =
    UnitValue1[U2, P2#Sub[Q2]](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv2a2s01c1s1D[U1, U2, UZ, P1, P2, _0, Q1, P2])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue1[U1, P1#Sub[Q1]] =
    UnitValue1[U1, P1#Sub[Q1]](value / rhs.value)

  def /(rhs: RHSv2a2s01c2s01D[U1, U2, UZ, P1, P2, _0, P1, P2]): UnitValue0 =
    UnitValue0(value / rhs.value)

  def /[Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c0sD[U1, U2, UZ, P1, P2, _0, Q1, Q2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg],
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P2#Sub[Q2]]): UnitValue3[U1, P1#Sub[Q1], U2, P2#Sub[Q2], Ua, Qa#Neg] =
    UnitValue3[U1, P1#Sub[Q1], U2, P2#Sub[Q2], Ua, Qa#Neg](value / rhs.value)

  def /[Q2 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c1s0D[U1, U2, UZ, P1, P2, _0, P1, Q2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg],
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue2[U2, P2#Sub[Q2], Ua, Qa#Neg] =
    UnitValue2[U2, P2#Sub[Q2], Ua, Qa#Neg](value / rhs.value)

  def /[Q1 <: Integer, Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c1s1D[U1, U2, UZ, P1, P2, _0, Q1, P2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg],
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue2[U1, P1#Sub[Q1], Ua, Qa#Neg] =
    UnitValue2[U1, P1#Sub[Q1], Ua, Qa#Neg](value / rhs.value)

  def /[Ua <: Unit, Qa <: Integer](rhs: RHSv3a2s01c2s01D[U1, U2, UZ, P1, P2, _0, P1, P2, Ua, Qa])(implicit
    urecUa: UnitRec[Ua],
    ivQa: IntegerValue[Qa#Neg]): UnitValue1[Ua, Qa#Neg] =
    UnitValue1[Ua, Qa#Neg](value / rhs.value)
}

object UnitValue2 {
  
  implicit def uvc$v2p01[Ua <: Unit, Ub <: Unit, U1 <: Unit, U2 <: Unit, P1 <: Integer, P2 <: Integer](uv: UnitValue2[Ua, P1, Ub, P2])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub],
    urec1: UnitRec[U1], urec2: UnitRec[U2],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU): UnitValue2[U1, P1, U2, P2] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue2[U1, P1, U2, P2](v)
  }

  implicit def uvc$v2p10[Ua <: Unit, Ub <: Unit, U1 <: Unit, U2 <: Unit, P1 <: Integer, P2 <: Integer](uv: UnitValue2[Ub, P2, Ua, P1])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub],
    urec1: UnitRec[U1], urec2: UnitRec[U2],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU): UnitValue2[U1, P1, U2, P2] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue2[U1, P1, U2, P2](v)
  }
}

case class UnitValue3[U1 <: Unit, P1 <: Integer, U2 <: Unit, P2 <: Integer, U3 <: Unit, P3 <: Integer](value: Double)(implicit
  urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
  iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3]) {
  override def toString = Unit.toString(value, Seq((urec1.name, iv1.value), (urec2.name, iv2.value), (urec3.name, iv3.value)))

  def +(uv: UnitValue3[U1, P1, U2, P2, U3, P3]): UnitValue3[U1, P1, U2, P2, U3, P3] = UnitValue3[U1, P1, U2, P2, U3, P3](value + uv.value)
  def -(uv: UnitValue3[U1, P1, U2, P2, U3, P3]): UnitValue3[U1, P1, U2, P2, U3, P3] = UnitValue3[U1, P1, U2, P2, U3, P3](value - uv.value)

  def pow[Q <: Integer with NonZero](implicit
    ivQ: IntegerValue[Q],
    powP1: IntegerValue[P1#Mul[Q]], powP2: IntegerValue[P2#Mul[Q]], powP3: IntegerValue[P3#Mul[Q]]): UnitValue3[U1, P1#Mul[Q], U2, P2#Mul[Q], U3, P3#Mul[Q]] =
    UnitValue3[U1, P1#Mul[Q], U2, P2#Mul[Q], U3, P3#Mul[Q]](math.pow(value, ivQ.value))

  def pow[Q <: _0]: UnitValue0 = UnitValue0(1.0)

  def inv(implicit
    invP1: IntegerValue[P1#Neg], invP2: IntegerValue[P2#Neg], invP3: IntegerValue[P3#Neg]): UnitValue3[U1, P1#Neg, U2, P2#Neg, U3, P3#Neg] =
    UnitValue3[U1, P1#Neg, U2, P2#Neg, U3, P3#Neg](1.0 / value)

  def sq(implicit
    sqP1: IntegerValue[P1#Mul[_2]], sqP2: IntegerValue[P2#Mul[_2]], sqP3: IntegerValue[P3#Mul[_2]]): UnitValue3[U1, P1#Mul[_2], U2, P2#Mul[_2], U3, P3#Mul[_2]] =
    UnitValue3[U1, P1#Mul[_2], U2, P2#Mul[_2], U3, P3#Mul[_2]](value * value)

  def *(v: Double): UnitValue3[U1, P1, U2, P2, U3, P3] = UnitValue3[U1, P1, U2, P2, U3, P3](value * v)


  def *(rhs: RHSv0a0sc0sM[U1, U2, U3, P1, P2, P3]): UnitValue3[U1, P1, U2, P2, U3, P3] =
    UnitValue3[U1, P1, U2, P2, U3, P3](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv1a1s0c0sM[U1, U2, U3, P1, P2, P3, Q1])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue3[U1, P1#Add[Q1], U2, P2, U3, P3] =
    UnitValue3[U1, P1#Add[Q1], U2, P2, U3, P3](value * rhs.value)

  def *(rhs: RHSv1a1s0c1s0M[U1, U2, U3, P1, P2, P3, P1#Neg]): UnitValue2[U2, P2, U3, P3] =
    UnitValue2[U2, P2, U3, P3](value * rhs.value)

  def *[Q2 <: Integer](rhs: RHSv1a1s1c0sM[U1, U2, U3, P1, P2, P3, Q2])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue3[U1, P1, U2, P2#Add[Q2], U3, P3] =
    UnitValue3[U1, P1, U2, P2#Add[Q2], U3, P3](value * rhs.value)

  def *(rhs: RHSv1a1s1c1s1M[U1, U2, U3, P1, P2, P3, P2#Neg]): UnitValue2[U1, P1, U3, P3] =
    UnitValue2[U1, P1, U3, P3](value * rhs.value)

  def *[Q3 <: Integer](rhs: RHSv1a1s2c0sM[U1, U2, U3, P1, P2, P3, Q3])(implicit
    ivpq0: IntegerValue[P3#Add[Q3]]): UnitValue3[U1, P1, U2, P2, U3, P3#Add[Q3]] =
    UnitValue3[U1, P1, U2, P2, U3, P3#Add[Q3]](value * rhs.value)

  def *(rhs: RHSv1a1s2c1s2M[U1, U2, U3, P1, P2, P3, P3#Neg]): UnitValue2[U1, P1, U2, P2] =
    UnitValue2[U1, P1, U2, P2](value * rhs.value)

  def *[Q1 <: Integer, Q2 <: Integer](rhs: RHSv2a2s01c0sM[U1, U2, U3, P1, P2, P3, Q1, Q2])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P2#Add[Q2]]): UnitValue3[U1, P1#Add[Q1], U2, P2#Add[Q2], U3, P3] =
    UnitValue3[U1, P1#Add[Q1], U2, P2#Add[Q2], U3, P3](value * rhs.value)

  def *[Q2 <: Integer](rhs: RHSv2a2s01c1s0M[U1, U2, U3, P1, P2, P3, P1#Neg, Q2])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue2[U2, P2#Add[Q2], U3, P3] =
    UnitValue2[U2, P2#Add[Q2], U3, P3](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv2a2s01c1s1M[U1, U2, U3, P1, P2, P3, Q1, P2#Neg])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue2[U1, P1#Add[Q1], U3, P3] =
    UnitValue2[U1, P1#Add[Q1], U3, P3](value * rhs.value)

  def *(rhs: RHSv2a2s01c2s01M[U1, U2, U3, P1, P2, P3, P1#Neg, P2#Neg]): UnitValue1[U3, P3] =
    UnitValue1[U3, P3](value * rhs.value)

  def *[Q1 <: Integer, Q3 <: Integer](rhs: RHSv2a2s02c0sM[U1, U2, U3, P1, P2, P3, Q1, Q3])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P3#Add[Q3]]): UnitValue3[U1, P1#Add[Q1], U2, P2, U3, P3#Add[Q3]] =
    UnitValue3[U1, P1#Add[Q1], U2, P2, U3, P3#Add[Q3]](value * rhs.value)

  def *[Q3 <: Integer](rhs: RHSv2a2s02c1s0M[U1, U2, U3, P1, P2, P3, P1#Neg, Q3])(implicit
    ivpq0: IntegerValue[P3#Add[Q3]]): UnitValue2[U2, P2, U3, P3#Add[Q3]] =
    UnitValue2[U2, P2, U3, P3#Add[Q3]](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv2a2s02c1s2M[U1, U2, U3, P1, P2, P3, Q1, P3#Neg])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue2[U1, P1#Add[Q1], U2, P2] =
    UnitValue2[U1, P1#Add[Q1], U2, P2](value * rhs.value)

  def *(rhs: RHSv2a2s02c2s02M[U1, U2, U3, P1, P2, P3, P1#Neg, P3#Neg]): UnitValue1[U2, P2] =
    UnitValue1[U2, P2](value * rhs.value)

  def *[Q2 <: Integer, Q3 <: Integer](rhs: RHSv2a2s12c0sM[U1, U2, U3, P1, P2, P3, Q2, Q3])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]], ivpq1: IntegerValue[P3#Add[Q3]]): UnitValue3[U1, P1, U2, P2#Add[Q2], U3, P3#Add[Q3]] =
    UnitValue3[U1, P1, U2, P2#Add[Q2], U3, P3#Add[Q3]](value * rhs.value)

  def *[Q3 <: Integer](rhs: RHSv2a2s12c1s1M[U1, U2, U3, P1, P2, P3, P2#Neg, Q3])(implicit
    ivpq0: IntegerValue[P3#Add[Q3]]): UnitValue2[U1, P1, U3, P3#Add[Q3]] =
    UnitValue2[U1, P1, U3, P3#Add[Q3]](value * rhs.value)

  def *[Q2 <: Integer](rhs: RHSv2a2s12c1s2M[U1, U2, U3, P1, P2, P3, Q2, P3#Neg])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue2[U1, P1, U2, P2#Add[Q2]] =
    UnitValue2[U1, P1, U2, P2#Add[Q2]](value * rhs.value)

  def *(rhs: RHSv2a2s12c2s12M[U1, U2, U3, P1, P2, P3, P2#Neg, P3#Neg]): UnitValue1[U1, P1] =
    UnitValue1[U1, P1](value * rhs.value)

  def *[Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](rhs: RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Q1, Q2, Q3])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P2#Add[Q2]], ivpq2: IntegerValue[P3#Add[Q3]]): UnitValue3[U1, P1#Add[Q1], U2, P2#Add[Q2], U3, P3#Add[Q3]] =
    UnitValue3[U1, P1#Add[Q1], U2, P2#Add[Q2], U3, P3#Add[Q3]](value * rhs.value)

  def *[Q2 <: Integer, Q3 <: Integer](rhs: RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, P1#Neg, Q2, Q3])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]], ivpq1: IntegerValue[P3#Add[Q3]]): UnitValue2[U2, P2#Add[Q2], U3, P3#Add[Q3]] =
    UnitValue2[U2, P2#Add[Q2], U3, P3#Add[Q3]](value * rhs.value)

  def *[Q1 <: Integer, Q3 <: Integer](rhs: RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Q1, P2#Neg, Q3])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P3#Add[Q3]]): UnitValue2[U1, P1#Add[Q1], U3, P3#Add[Q3]] =
    UnitValue2[U1, P1#Add[Q1], U3, P3#Add[Q3]](value * rhs.value)

  def *[Q1 <: Integer, Q2 <: Integer](rhs: RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Q1, Q2, P3#Neg])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]], ivpq1: IntegerValue[P2#Add[Q2]]): UnitValue2[U1, P1#Add[Q1], U2, P2#Add[Q2]] =
    UnitValue2[U1, P1#Add[Q1], U2, P2#Add[Q2]](value * rhs.value)

  def *[Q3 <: Integer](rhs: RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, P1#Neg, P2#Neg, Q3])(implicit
    ivpq0: IntegerValue[P3#Add[Q3]]): UnitValue1[U3, P3#Add[Q3]] =
    UnitValue1[U3, P3#Add[Q3]](value * rhs.value)

  def *[Q2 <: Integer](rhs: RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, P1#Neg, Q2, P3#Neg])(implicit
    ivpq0: IntegerValue[P2#Add[Q2]]): UnitValue1[U2, P2#Add[Q2]] =
    UnitValue1[U2, P2#Add[Q2]](value * rhs.value)

  def *[Q1 <: Integer](rhs: RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Q1, P2#Neg, P3#Neg])(implicit
    ivpq0: IntegerValue[P1#Add[Q1]]): UnitValue1[U1, P1#Add[Q1]] =
    UnitValue1[U1, P1#Add[Q1]](value * rhs.value)

  def *(rhs: RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, P1#Neg, P2#Neg, P3#Neg]): UnitValue0 =
    UnitValue0(value * rhs.value)

  def /(rhs: RHSv0a0sc0sD[U1, U2, U3, P1, P2, P3]): UnitValue3[U1, P1, U2, P2, U3, P3] =
    UnitValue3[U1, P1, U2, P2, U3, P3](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv1a1s0c0sD[U1, U2, U3, P1, P2, P3, Q1])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue3[U1, P1#Sub[Q1], U2, P2, U3, P3] =
    UnitValue3[U1, P1#Sub[Q1], U2, P2, U3, P3](value / rhs.value)

  def /(rhs: RHSv1a1s0c1s0D[U1, U2, U3, P1, P2, P3, P1]): UnitValue2[U2, P2, U3, P3] =
    UnitValue2[U2, P2, U3, P3](value / rhs.value)

  def /[Q2 <: Integer](rhs: RHSv1a1s1c0sD[U1, U2, U3, P1, P2, P3, Q2])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue3[U1, P1, U2, P2#Sub[Q2], U3, P3] =
    UnitValue3[U1, P1, U2, P2#Sub[Q2], U3, P3](value / rhs.value)

  def /(rhs: RHSv1a1s1c1s1D[U1, U2, U3, P1, P2, P3, P2]): UnitValue2[U1, P1, U3, P3] =
    UnitValue2[U1, P1, U3, P3](value / rhs.value)

  def /[Q3 <: Integer](rhs: RHSv1a1s2c0sD[U1, U2, U3, P1, P2, P3, Q3])(implicit
    ivpq0: IntegerValue[P3#Sub[Q3]]): UnitValue3[U1, P1, U2, P2, U3, P3#Sub[Q3]] =
    UnitValue3[U1, P1, U2, P2, U3, P3#Sub[Q3]](value / rhs.value)

  def /(rhs: RHSv1a1s2c1s2D[U1, U2, U3, P1, P2, P3, P3]): UnitValue2[U1, P1, U2, P2] =
    UnitValue2[U1, P1, U2, P2](value / rhs.value)

  def /[Q1 <: Integer, Q2 <: Integer](rhs: RHSv2a2s01c0sD[U1, U2, U3, P1, P2, P3, Q1, Q2])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P2#Sub[Q2]]): UnitValue3[U1, P1#Sub[Q1], U2, P2#Sub[Q2], U3, P3] =
    UnitValue3[U1, P1#Sub[Q1], U2, P2#Sub[Q2], U3, P3](value / rhs.value)

  def /[Q2 <: Integer](rhs: RHSv2a2s01c1s0D[U1, U2, U3, P1, P2, P3, P1, Q2])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue2[U2, P2#Sub[Q2], U3, P3] =
    UnitValue2[U2, P2#Sub[Q2], U3, P3](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv2a2s01c1s1D[U1, U2, U3, P1, P2, P3, Q1, P2])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue2[U1, P1#Sub[Q1], U3, P3] =
    UnitValue2[U1, P1#Sub[Q1], U3, P3](value / rhs.value)

  def /(rhs: RHSv2a2s01c2s01D[U1, U2, U3, P1, P2, P3, P1, P2]): UnitValue1[U3, P3] =
    UnitValue1[U3, P3](value / rhs.value)

  def /[Q1 <: Integer, Q3 <: Integer](rhs: RHSv2a2s02c0sD[U1, U2, U3, P1, P2, P3, Q1, Q3])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P3#Sub[Q3]]): UnitValue3[U1, P1#Sub[Q1], U2, P2, U3, P3#Sub[Q3]] =
    UnitValue3[U1, P1#Sub[Q1], U2, P2, U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q3 <: Integer](rhs: RHSv2a2s02c1s0D[U1, U2, U3, P1, P2, P3, P1, Q3])(implicit
    ivpq0: IntegerValue[P3#Sub[Q3]]): UnitValue2[U2, P2, U3, P3#Sub[Q3]] =
    UnitValue2[U2, P2, U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv2a2s02c1s2D[U1, U2, U3, P1, P2, P3, Q1, P3])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue2[U1, P1#Sub[Q1], U2, P2] =
    UnitValue2[U1, P1#Sub[Q1], U2, P2](value / rhs.value)

  def /(rhs: RHSv2a2s02c2s02D[U1, U2, U3, P1, P2, P3, P1, P3]): UnitValue1[U2, P2] =
    UnitValue1[U2, P2](value / rhs.value)

  def /[Q2 <: Integer, Q3 <: Integer](rhs: RHSv2a2s12c0sD[U1, U2, U3, P1, P2, P3, Q2, Q3])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]], ivpq1: IntegerValue[P3#Sub[Q3]]): UnitValue3[U1, P1, U2, P2#Sub[Q2], U3, P3#Sub[Q3]] =
    UnitValue3[U1, P1, U2, P2#Sub[Q2], U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q3 <: Integer](rhs: RHSv2a2s12c1s1D[U1, U2, U3, P1, P2, P3, P2, Q3])(implicit
    ivpq0: IntegerValue[P3#Sub[Q3]]): UnitValue2[U1, P1, U3, P3#Sub[Q3]] =
    UnitValue2[U1, P1, U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q2 <: Integer](rhs: RHSv2a2s12c1s2D[U1, U2, U3, P1, P2, P3, Q2, P3])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue2[U1, P1, U2, P2#Sub[Q2]] =
    UnitValue2[U1, P1, U2, P2#Sub[Q2]](value / rhs.value)

  def /(rhs: RHSv2a2s12c2s12D[U1, U2, U3, P1, P2, P3, P2, P3]): UnitValue1[U1, P1] =
    UnitValue1[U1, P1](value / rhs.value)

  def /[Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](rhs: RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Q1, Q2, Q3])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P2#Sub[Q2]], ivpq2: IntegerValue[P3#Sub[Q3]]): UnitValue3[U1, P1#Sub[Q1], U2, P2#Sub[Q2], U3, P3#Sub[Q3]] =
    UnitValue3[U1, P1#Sub[Q1], U2, P2#Sub[Q2], U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q2 <: Integer, Q3 <: Integer](rhs: RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, P1, Q2, Q3])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]], ivpq1: IntegerValue[P3#Sub[Q3]]): UnitValue2[U2, P2#Sub[Q2], U3, P3#Sub[Q3]] =
    UnitValue2[U2, P2#Sub[Q2], U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q1 <: Integer, Q3 <: Integer](rhs: RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Q1, P2, Q3])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P3#Sub[Q3]]): UnitValue2[U1, P1#Sub[Q1], U3, P3#Sub[Q3]] =
    UnitValue2[U1, P1#Sub[Q1], U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q1 <: Integer, Q2 <: Integer](rhs: RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Q1, Q2, P3])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]], ivpq1: IntegerValue[P2#Sub[Q2]]): UnitValue2[U1, P1#Sub[Q1], U2, P2#Sub[Q2]] =
    UnitValue2[U1, P1#Sub[Q1], U2, P2#Sub[Q2]](value / rhs.value)

  def /[Q3 <: Integer](rhs: RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, P1, P2, Q3])(implicit
    ivpq0: IntegerValue[P3#Sub[Q3]]): UnitValue1[U3, P3#Sub[Q3]] =
    UnitValue1[U3, P3#Sub[Q3]](value / rhs.value)

  def /[Q2 <: Integer](rhs: RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, P1, Q2, P3])(implicit
    ivpq0: IntegerValue[P2#Sub[Q2]]): UnitValue1[U2, P2#Sub[Q2]] =
    UnitValue1[U2, P2#Sub[Q2]](value / rhs.value)

  def /[Q1 <: Integer](rhs: RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Q1, P2, P3])(implicit
    ivpq0: IntegerValue[P1#Sub[Q1]]): UnitValue1[U1, P1#Sub[Q1]] =
    UnitValue1[U1, P1#Sub[Q1]](value / rhs.value)

  def /(rhs: RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, P1, P2, P3]): UnitValue0 =
    UnitValue0(value / rhs.value)
}

object UnitValue3 {
  
  implicit def uvc$v3p012[Ua <: Unit, Ub <: Unit, Uc <: Unit, U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue3[Ua, P1, Ub, P2, Uc, P3])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub], urecc: UnitRec[Uc],
    urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU, eqc3: Uc#RU =:= U3#RU): UnitValue3[U1, P1, U2, P2, U3, P3] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value), (factor[Uc, U3], iv3.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue3[U1, P1, U2, P2, U3, P3](v)
  }

  implicit def uvc$v3p021[Ua <: Unit, Ub <: Unit, Uc <: Unit, U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue3[Ua, P1, Uc, P3, Ub, P2])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub], urecc: UnitRec[Uc],
    urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU, eqc3: Uc#RU =:= U3#RU): UnitValue3[U1, P1, U2, P2, U3, P3] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value), (factor[Uc, U3], iv3.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue3[U1, P1, U2, P2, U3, P3](v)
  }

  implicit def uvc$v3p102[Ua <: Unit, Ub <: Unit, Uc <: Unit, U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue3[Ub, P2, Ua, P1, Uc, P3])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub], urecc: UnitRec[Uc],
    urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU, eqc3: Uc#RU =:= U3#RU): UnitValue3[U1, P1, U2, P2, U3, P3] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value), (factor[Uc, U3], iv3.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue3[U1, P1, U2, P2, U3, P3](v)
  }

  implicit def uvc$v3p120[Ua <: Unit, Ub <: Unit, Uc <: Unit, U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue3[Ub, P2, Uc, P3, Ua, P1])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub], urecc: UnitRec[Uc],
    urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU, eqc3: Uc#RU =:= U3#RU): UnitValue3[U1, P1, U2, P2, U3, P3] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value), (factor[Uc, U3], iv3.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue3[U1, P1, U2, P2, U3, P3](v)
  }

  implicit def uvc$v3p201[Ua <: Unit, Ub <: Unit, Uc <: Unit, U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue3[Uc, P3, Ua, P1, Ub, P2])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub], urecc: UnitRec[Uc],
    urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU, eqc3: Uc#RU =:= U3#RU): UnitValue3[U1, P1, U2, P2, U3, P3] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value), (factor[Uc, U3], iv3.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue3[U1, P1, U2, P2, U3, P3](v)
  }

  implicit def uvc$v3p210[Ua <: Unit, Ub <: Unit, Uc <: Unit, U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue3[Uc, P3, Ub, P2, Ua, P1])(implicit
    ureca: UnitRec[Ua], urecb: UnitRec[Ub], urecc: UnitRec[Uc],
    urec1: UnitRec[U1], urec2: UnitRec[U2], urec3: UnitRec[U3],
    iv1: IntegerValue[P1], iv2: IntegerValue[P2], iv3: IntegerValue[P3],
    eqa1: Ua#RU =:= U1#RU, eqb2: Ub#RU =:= U2#RU, eqc3: Uc#RU =:= U3#RU): UnitValue3[U1, P1, U2, P2, U3, P3] = {
    val fp = Seq((factor[Ua, U1], iv1.value), (factor[Ub, U2], iv2.value), (factor[Uc, U3], iv3.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    UnitValue3[U1, P1, U2, P2, U3, P3](v)
  }
}