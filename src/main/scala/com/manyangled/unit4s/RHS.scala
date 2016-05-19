/* THIS FILE WAS MACHINE GENERATED, DO NOT EDIT */
package com.manyangled.unit4s

import scala.language.implicitConversions

import com.manyangled.church.{ Integer, IntegerValue }
import Integer.{ _0, _1 }
import Unit.factor


class RHSv0a0sc0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3])

object RHSv0a0sc0sM {
  implicit def rhs$v0a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue0)(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3]): RHSv0a0sc0sM[U1, U2, U3, P1, P2, P3] = {
    val v = uv.value
    new RHSv0a0sc0sM[U1, U2, U3, P1, P2, P3](v)
  }
}

class RHSv0a0sc0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3])

object RHSv0a0sc0sD {
  implicit def rhs$v0a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer](uv: UnitValue0)(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3]): RHSv0a0sc0sD[U1, U2, U3, P1, P2, P3] = {
    val v = uv.value
    new RHSv0a0sc0sD[U1, U2, U3, P1, P2, P3](v)
  }
}

class RHSv1a0sc0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv1a0sc0sM {
  implicit def rhs$v1a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU): RHSv1a0sc0sM[U1, U2, U3, P1, P2, P3, Ua, Pa] = {
    val v = uv.value
    new RHSv1a0sc0sM[U1, U2, U3, P1, P2, P3, Ua, Pa](v)
  }
}

class RHSv1a0sc0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv1a0sc0sD {
  implicit def rhs$v1a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU): RHSv1a0sc0sD[U1, U2, U3, P1, P2, P3, Ua, Pa] = {
    val v = uv.value
    new RHSv1a0sc0sD[U1, U2, U3, P1, P2, P3, Ua, Pa](v)
  }
}

class RHSv1a1s0c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1])

object RHSv1a1s0c0sM {
  implicit def rhs$v1a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU1: Ua#RU =:= U1#RU,
    kqpPa: Pa =!= P1#Neg): RHSv1a1s0c0sM[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s0c0sM[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s0c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1])

object RHSv1a1s0c0sD {
  implicit def rhs$v1a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU1: Ua#RU =:= U1#RU,
    kqpPa: Pa =!= P1): RHSv1a1s0c0sD[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s0c0sD[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s0c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1])

object RHSv1a1s0c1s0M {
  implicit def rhs$v1a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU1: Ua#RU =:= U1#RU,
    cqpPa: Pa =:= P1#Neg): RHSv1a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s0c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1])

object RHSv1a1s0c1s0D {
  implicit def rhs$v1a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU1: Ua#RU =:= U1#RU,
    cqpPa: Pa =:= P1): RHSv1a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s1c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2])

object RHSv1a1s1c0sM {
  implicit def rhs$v1a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU2: Ua#RU =:= U2#RU,
    kqpPa: Pa =!= P2#Neg): RHSv1a1s1c0sM[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s1c0sM[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s1c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2])

object RHSv1a1s1c0sD {
  implicit def rhs$v1a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU2: Ua#RU =:= U2#RU,
    kqpPa: Pa =!= P2): RHSv1a1s1c0sD[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s1c0sD[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s1c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2])

object RHSv1a1s1c1s1M {
  implicit def rhs$v1a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU2: Ua#RU =:= U2#RU,
    cqpPa: Pa =:= P2#Neg): RHSv1a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s1c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2])

object RHSv1a1s1c1s1D {
  implicit def rhs$v1a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU2: Ua#RU =:= U2#RU,
    cqpPa: Pa =:= P2): RHSv1a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s2c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3])

object RHSv1a1s2c0sM {
  implicit def rhs$v1a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU3: Ua#RU =:= U3#RU,
    kqpPa: Pa =!= P3#Neg): RHSv1a1s2c0sM[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s2c0sM[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s2c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3])

object RHSv1a1s2c0sD {
  implicit def rhs$v1a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU3: Ua#RU =:= U3#RU,
    kqpPa: Pa =!= P3): RHSv1a1s2c0sD[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s2c0sD[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s2c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3])

object RHSv1a1s2c1s2M {
  implicit def rhs$v1a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3#Neg): RHSv1a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv1a1s2c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3])

object RHSv1a1s2c1s2D {
  implicit def rhs$v1a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer](uv: UnitValue1[Ua, Pa])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua],
    ivPa: IntegerValue[Pa],
    eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3): RHSv1a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pa] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv1a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pa](v)
  }
}

class RHSv2a0sc0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv2a0sc0sM {
  implicit def rhs$v2a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU): RHSv2a0sc0sM[U1, U2, U3, P1, P2, P3, Ua, Ub, Pa, Pb] = {
    val v = uv.value
    new RHSv2a0sc0sM[U1, U2, U3, P1, P2, P3, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv2a0sc0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv2a0sc0sD {
  implicit def rhs$v2a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU): RHSv2a0sc0sD[U1, U2, U3, P1, P2, P3, Ua, Ub, Pa, Pb] = {
    val v = uv.value
    new RHSv2a0sc0sD[U1, U2, U3, P1, P2, P3, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv2a1s0c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s0c0sM {
  implicit def rhs$v2a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P1#Neg): RHSv2a1s0c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P1#Neg): RHSv2a1s0c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s0c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s0c0sD {
  implicit def rhs$v2a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P1): RHSv2a1s0c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P1): RHSv2a1s0c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s0c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s0c1s0M {
  implicit def rhs$v2a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg): RHSv2a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg): RHSv2a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s0c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s0c1s0D {
  implicit def rhs$v2a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1): RHSv2a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1): RHSv2a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s1c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s1c0sM {
  implicit def rhs$v2a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P2#Neg): RHSv2a1s1c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P2#Neg): RHSv2a1s1c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s1c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s1c0sD {
  implicit def rhs$v2a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P2): RHSv2a1s1c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P2): RHSv2a1s1c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s1c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s1c1s1M {
  implicit def rhs$v2a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg): RHSv2a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg): RHSv2a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s1c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s1c1s1D {
  implicit def rhs$v2a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2): RHSv2a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2): RHSv2a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s2c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s2c0sM {
  implicit def rhs$v2a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P3#Neg): RHSv2a1s2c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P3#Neg): RHSv2a1s2c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s2c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s2c0sD {
  implicit def rhs$v2a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P3): RHSv2a1s2c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P3): RHSv2a1s2c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s2c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s2c1s2M {
  implicit def rhs$v2a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P3#Neg): RHSv2a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P3#Neg): RHSv2a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a1s2c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv2a1s2c1s2D {
  implicit def rhs$v2a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P3): RHSv2a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pa, Ub, Pb](v)
  }

  implicit def rhs$v2a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P3): RHSv2a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pb, Ua, Pa](v)
  }
}

class RHSv2a2s01c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c0sM {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P2#Neg): RHSv2a2s01c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P2#Neg): RHSv2a2s01c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c0sD {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P2): RHSv2a2s01c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P2): RHSv2a2s01c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c1s0M {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv2a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv2a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c1s0D {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    cqpPa: Pa =:= P1,
    kqpPb: Pb =!= P2): RHSv2a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    cqpPb: Pb =:= P1,
    kqpPa: Pa =!= P2): RHSv2a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c1s1M {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv2a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv2a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c1s1D {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    cqpPb: Pb =:= P2,
    kqpPa: Pa =!= P1): RHSv2a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    cqpPa: Pa =:= P2,
    kqpPb: Pb =!= P1): RHSv2a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c2s01M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c2s01M {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P2#Neg): RHSv2a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P2#Neg): RHSv2a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s01c2s01D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2])

object RHSv2a2s01c2s01D {
  implicit def rhs$v2a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P2): RHSv2a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P2): RHSv2a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c0sM {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P3#Neg): RHSv2a2s02c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P3#Neg): RHSv2a2s02c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c0sD {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P3): RHSv2a2s02c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P3): RHSv2a2s02c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c1s0M {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv2a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv2a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c1s0D {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPb: Pb =!= P3): RHSv2a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPa: Pa =!= P3): RHSv2a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c1s2M {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv2a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv2a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c1s2D {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P1): RHSv2a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P1): RHSv2a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c2s02M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c2s02M {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P3#Neg): RHSv2a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P3#Neg): RHSv2a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s02c2s02D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3])

object RHSv2a2s02c2s02D {
  implicit def rhs$v2a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P3): RHSv2a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P3): RHSv2a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c0sM {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPa: Pa =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv2a2s12c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPb: Pb =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv2a2s12c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c0sD {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPa: Pa =!= P2, kqpPb: Pb =!= P3): RHSv2a2s12c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPb: Pb =!= P2, kqpPa: Pa =!= P3): RHSv2a2s12c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c1s1M {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv2a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv2a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c1s1D {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPb: Pb =!= P3): RHSv2a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPa: Pa =!= P3): RHSv2a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c1s2M {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv2a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv2a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c1s2D {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P2): RHSv2a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P2): RHSv2a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c2s12M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c2s12M {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2#Neg, cqpPb: Pb =:= P3#Neg): RHSv2a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2#Neg, cqpPa: Pa =:= P3#Neg): RHSv2a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv2a2s12c2s12D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv2a2s12c2s12D {
  implicit def rhs$v2a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2, cqpPb: Pb =:= P3): RHSv2a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pb](v)
  }

  implicit def rhs$v2a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer](uv: UnitValue2[Ua, Pa, Ub, Pb])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2, cqpPa: Pa =:= P3): RHSv2a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv2a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pa](v)
  }
}

class RHSv3a0sc0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Ua <: Unit, Ub <: Unit, Uc <: Unit, Qa <: Integer, Qb <: Integer, Qc <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb], ivQc: IntegerValue[Qc])

object RHSv3a0sc0sM {
  implicit def rhs$v3a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU): RHSv3a0sc0sM[U1, U2, U3, P1, P2, P3, Ua, Ub, Uc, Pa, Pb, Pc] = {
    val v = uv.value
    new RHSv3a0sc0sM[U1, U2, U3, P1, P2, P3, Ua, Ub, Uc, Pa, Pb, Pc](v)
  }
}

class RHSv3a0sc0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Ua <: Unit, Ub <: Unit, Uc <: Unit, Qa <: Integer, Qb <: Integer, Qc <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb], ivQc: IntegerValue[Qc])

object RHSv3a0sc0sD {
  implicit def rhs$v3a0scp[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU): RHSv3a0sc0sD[U1, U2, U3, P1, P2, P3, Ua, Ub, Uc, Pa, Pb, Pc] = {
    val v = uv.value
    new RHSv3a0sc0sD[U1, U2, U3, P1, P2, P3, Ua, Ub, Uc, Pa, Pb, Pc](v)
  }
}

class RHSv3a1s0c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s0c0sM {
  implicit def rhs$v3a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P1#Neg): RHSv3a1s0c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P1#Neg): RHSv3a1s0c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s0c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P1#Neg): RHSv3a1s0c0sM[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c0sM[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s0c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s0c0sD {
  implicit def rhs$v3a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P1): RHSv3a1s0c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P1): RHSv3a1s0c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s0c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P1): RHSv3a1s0c0sD[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c0sD[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s0c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s0c1s0M {
  implicit def rhs$v3a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg): RHSv3a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg): RHSv3a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s0c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg): RHSv3a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c1s0M[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s0c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s0c1s0D {
  implicit def rhs$v3a1s0c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1): RHSv3a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s0c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1): RHSv3a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s0c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1): RHSv3a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s0c1s0D[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s1c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s1c0sM {
  implicit def rhs$v3a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P2#Neg): RHSv3a1s1c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P2#Neg): RHSv3a1s1c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s1c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P2#Neg): RHSv3a1s1c0sM[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c0sM[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s1c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s1c0sD {
  implicit def rhs$v3a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P2): RHSv3a1s1c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P2): RHSv3a1s1c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s1c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P2): RHSv3a1s1c0sD[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c0sD[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s1c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s1c1s1M {
  implicit def rhs$v3a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg): RHSv3a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg): RHSv3a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s1c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg): RHSv3a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c1s1M[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s1c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s1c1s1D {
  implicit def rhs$v3a1s1c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2): RHSv3a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s1c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2): RHSv3a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s1c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2): RHSv3a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s1c1s1D[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s2c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s2c0sM {
  implicit def rhs$v3a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P3#Neg): RHSv3a1s2c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c0sM[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P3#Neg): RHSv3a1s2c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c0sM[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s2c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P3#Neg): RHSv3a1s2c0sM[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c0sM[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s2c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s2c0sD {
  implicit def rhs$v3a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P3): RHSv3a1s2c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c0sD[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P3): RHSv3a1s2c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c0sD[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s2c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P3): RHSv3a1s2c0sD[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c0sD[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s2c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s2c1s2M {
  implicit def rhs$v3a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P3#Neg): RHSv3a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P3#Neg): RHSv3a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s2c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P3#Neg): RHSv3a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c1s2M[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a1s2c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q3 <: Integer, Ua <: Unit, Ub <: Unit, Qa <: Integer, Qb <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua], urecUb: UnitRec[Ub],
  ivQa: IntegerValue[Qa], ivQb: IntegerValue[Qb])

object RHSv3a1s2c1s2D {
  implicit def rhs$v3a1s2c0p0[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P3): RHSv3a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc] = {
    val fp = Seq((factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pa, Ub, Uc, Pb, Pc](v)
  }

  implicit def rhs$v3a1s2c1p1[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P3): RHSv3a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc] = {
    val fp = Seq((factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pb, Ua, Uc, Pa, Pc](v)
  }

  implicit def rhs$v3a1s2c2p2[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU, neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P3): RHSv3a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb] = {
    val fp = Seq((factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a1s2c1s2D[U1, U2, U3, P1, P2, P3, Pc, Ua, Ub, Pa, Pb](v)
  }
}

class RHSv3a2s01c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c0sM {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P2#Neg): RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P2#Neg): RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPc: Pc =!= P2#Neg): RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P1#Neg, kqpPa: Pa =!= P2#Neg): RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPc: Pc =!= P2#Neg): RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPc: Pc =!= P1#Neg, kqpPb: Pb =!= P2#Neg): RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c0sD {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P2): RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P2): RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P1, kqpPc: Pc =!= P2): RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P1, kqpPa: Pa =!= P2): RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P1, kqpPc: Pc =!= P2): RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPc: Pc =!= P1, kqpPb: Pb =!= P2): RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c1s0M {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPc: Pc =!= P2#Neg): RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPc: Pc =!= P2#Neg): RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c1s0D {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPb: Pb =!= P2): RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPa: Pa =!= P2): RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPc: Pc =!= P2): RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1,
    kqpPa: Pa =!= P2): RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPc: Pc =!= P2): RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1,
    kqpPb: Pb =!= P2): RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c1s1M {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPc: Pc =!= P1#Neg): RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPc: Pc =!= P1#Neg): RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c1s1D {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPa: Pa =!= P1): RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPb: Pb =!= P1): RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2,
    kqpPa: Pa =!= P1): RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPc: Pc =!= P1): RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P2,
    kqpPb: Pb =!= P1): RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPc: Pc =!= P1): RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c2s01M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c2s01M {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P2#Neg): RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P2#Neg): RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPc: Pc =:= P2#Neg): RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPa: Pa =:= P2#Neg): RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPc: Pc =:= P2#Neg): RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPb: Pb =:= P2#Neg): RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s01c2s01D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s01c2s01D {
  implicit def rhs$v3a2s01c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P2): RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P2): RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s01c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1, cqpPc: Pc =:= P2): RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1, cqpPa: Pa =:= P2): RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s01c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1, cqpPc: Pc =:= P2): RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s01c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1, cqpPb: Pb =:= P2): RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s01c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c0sM {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P1#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPc: Pc =!= P1#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c0sD {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P3): RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P3): RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P1, kqpPc: Pc =!= P3): RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P1, kqpPa: Pa =!= P3): RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P1, kqpPc: Pc =!= P3): RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPc: Pc =!= P1, kqpPb: Pb =!= P3): RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c1s0M {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPc: Pc =!= P3#Neg): RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPc: Pc =!= P3#Neg): RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c1s0D {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPb: Pb =!= P3): RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPa: Pa =!= P3): RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPc: Pc =!= P3): RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1,
    kqpPa: Pa =!= P3): RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPc: Pc =!= P3): RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1,
    kqpPb: Pb =!= P3): RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c1s2M {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPc: Pc =!= P1#Neg): RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPc: Pc =!= P1#Neg): RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c1s2D {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P1): RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P1): RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P3,
    kqpPa: Pa =!= P1): RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPc: Pc =!= P1): RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P3,
    kqpPb: Pb =!= P1): RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPc: Pc =!= P1): RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c2s02M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c2s02M {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P3#Neg): RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P3#Neg): RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPc: Pc =:= P3#Neg): RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPa: Pa =:= P3#Neg): RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPc: Pc =:= P3#Neg): RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPb: Pb =:= P3#Neg): RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s02c2s02D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s02c2s02D {
  implicit def rhs$v3a2s02c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P3): RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P3): RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s02c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P1, cqpPc: Pc =:= P3): RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P1, cqpPa: Pa =:= P3): RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s02c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P1, cqpPc: Pc =:= P3): RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s02c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P1, cqpPb: Pb =:= P3): RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s02c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c0sM {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P2#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P2#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPc: Pc =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c0sD {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPa: Pa =!= P2, kqpPb: Pb =!= P3): RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    kqpPb: Pb =!= P2, kqpPa: Pa =!= P3): RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPa: Pa =!= P2, kqpPc: Pc =!= P3): RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    kqpPc: Pc =!= P2, kqpPa: Pa =!= P3): RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPb: Pb =!= P2, kqpPc: Pc =!= P3): RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    kqpPc: Pc =!= P2, kqpPb: Pb =!= P3): RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c1s1M {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPc: Pc =!= P3#Neg): RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPc: Pc =!= P3#Neg): RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c1s1D {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPb: Pb =!= P3): RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPa: Pa =!= P3): RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPc: Pc =!= P3): RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2,
    kqpPa: Pa =!= P3): RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPc: Pc =!= P3): RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P2,
    kqpPb: Pb =!= P3): RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c1s2M {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P3#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPc: Pc =!= P2#Neg): RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P3#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPc: Pc =!= P2#Neg): RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c1s2D {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P2): RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P2): RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P3,
    kqpPa: Pa =!= P2): RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPc: Pc =!= P2): RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P3,
    kqpPb: Pb =!= P2): RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPc: Pc =!= P2): RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c2s12M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c2s12M {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg, cqpPb: Pb =:= P3#Neg): RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg, cqpPa: Pa =:= P3#Neg): RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2#Neg, cqpPc: Pc =:= P3#Neg): RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg, cqpPa: Pa =:= P3#Neg): RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2#Neg, cqpPc: Pc =:= P3#Neg): RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P2#Neg, cqpPb: Pb =:= P3#Neg): RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a2s12c2s12D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q2 <: Integer, Q3 <: Integer, Ua <: Unit, Qa <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3],
  urecUa: UnitRec[Ua],
  ivQa: IntegerValue[Qa])

object RHSv3a2s12c2s12D {
  implicit def rhs$v3a2s12c01p01[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPa: Pa =:= P2, cqpPb: Pb =:= P3): RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pb, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c01p10[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUcU1: Uc#RU =!= U1#RU, neUcU2: Uc#RU =!= U2#RU, neUcU3: Uc#RU =!= U3#RU,
    cqpPb: Pb =:= P2, cqpPa: Pa =:= P3): RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pa, Uc, Pc](v)
  }

  implicit def rhs$v3a2s12c02p02[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPa: Pa =:= P2, cqpPc: Pc =:= P3): RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb] = {
    val fp = Seq((factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pc, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c02p20[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    neUbU1: Ub#RU =!= U1#RU, neUbU2: Ub#RU =!= U2#RU, neUbU3: Ub#RU =!= U3#RU,
    cqpPc: Pc =:= P2, cqpPa: Pa =:= P3): RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pa, Ub, Pb](v)
  }

  implicit def rhs$v3a2s12c12p12[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPb: Pb =:= P2, cqpPc: Pc =:= P3): RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa] = {
    val fp = Seq((factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pc, Ua, Pa](v)
  }

  implicit def rhs$v3a2s12c12p21[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    neUaU1: Ua#RU =!= U1#RU, neUaU2: Ua#RU =!= U2#RU, neUaU3: Ua#RU =!= U3#RU,
    cqpPc: Pc =:= P2, cqpPb: Pb =:= P3): RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa] = {
    val fp = Seq((factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a2s12c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pb, Ua, Pa](v)
  }
}

class RHSv3a3s012c0sM[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c0sM {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P2#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPa: Pa =!= P1#Neg, kqpPc: Pc =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P2#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPb: Pb =!= P1#Neg, kqpPc: Pc =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPc: Pc =!= P1#Neg, kqpPa: Pa =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPc: Pc =!= P1#Neg, kqpPb: Pb =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sM[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c0sD[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c0sD {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P2, kqpPc: Pc =!= P3): RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPa: Pa =!= P1, kqpPc: Pc =!= P2, kqpPb: Pb =!= P3): RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P2, kqpPc: Pc =!= P3): RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPb: Pb =!= P1, kqpPc: Pc =!= P2, kqpPa: Pa =!= P3): RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    kqpPc: Pc =!= P1, kqpPa: Pa =!= P2, kqpPb: Pb =!= P3): RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    kqpPc: Pc =!= P1, kqpPb: Pb =!= P2, kqpPa: Pa =!= P3): RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c0sD[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c1s0M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c1s0M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPb: Pb =!= P2#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg,
    kqpPc: Pc =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPa: Pa =!= P2#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg,
    kqpPc: Pc =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg,
    kqpPa: Pa =!= P2#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg,
    kqpPb: Pb =!= P2#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c1s0D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c1s0D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPb: Pb =!= P2, kqpPc: Pc =!= P3): RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1,
    kqpPc: Pc =!= P2, kqpPb: Pb =!= P3): RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPa: Pa =!= P2, kqpPc: Pc =!= P3): RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1,
    kqpPc: Pc =!= P2, kqpPa: Pa =!= P3): RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1,
    kqpPa: Pa =!= P2, kqpPb: Pb =!= P3): RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1,
    kqpPb: Pb =!= P2, kqpPa: Pa =!= P3): RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s0D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c1s1M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c1s1M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPa: Pa =!= P1#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P2#Neg,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPb: Pb =!= P1#Neg, kqpPc: Pc =!= P3#Neg): RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P2#Neg,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2#Neg,
    kqpPc: Pc =!= P1#Neg, kqpPb: Pb =!= P3#Neg): RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2#Neg,
    kqpPc: Pc =!= P1#Neg, kqpPa: Pa =!= P3#Neg): RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c1s1D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c1s1D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPa: Pa =!= P1, kqpPc: Pc =!= P3): RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P2,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P3): RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPb: Pb =!= P1, kqpPc: Pc =!= P3): RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P2,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P3): RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2,
    kqpPc: Pc =!= P1, kqpPb: Pb =!= P3): RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2,
    kqpPc: Pc =!= P1, kqpPa: Pa =!= P3): RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s1D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c1s2M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c1s2M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPc: Pc =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg, kqpPb: Pb =!= P2#Neg): RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg, kqpPc: Pc =!= P2#Neg): RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPc: Pc =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg, kqpPa: Pa =!= P2#Neg): RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg, kqpPc: Pc =!= P2#Neg): RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3#Neg,
    kqpPc: Pc =!= P1#Neg, kqpPa: Pa =!= P2#Neg): RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3#Neg,
    kqpPc: Pc =!= P1#Neg, kqpPb: Pb =!= P2#Neg): RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c1s2D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c1s2D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPc: Pc =:= P3,
    kqpPa: Pa =!= P1, kqpPb: Pb =!= P2): RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P1, kqpPc: Pc =!= P2): RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPc: Pc =:= P3,
    kqpPb: Pb =!= P1, kqpPa: Pa =!= P2): RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P1, kqpPc: Pc =!= P2): RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPb: Pb =:= P3,
    kqpPc: Pc =!= P1, kqpPa: Pa =!= P2): RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPa: Pa =:= P3,
    kqpPc: Pc =!= P1, kqpPb: Pb =!= P2): RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c1s2D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c2s01M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c2s01M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P2#Neg,
    kqpPc: Pc =!= P3#Neg): RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPc: Pc =:= P2#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P2#Neg,
    kqpPc: Pc =!= P3#Neg): RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPc: Pc =:= P2#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPa: Pa =:= P2#Neg,
    kqpPb: Pb =!= P3#Neg): RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPb: Pb =:= P2#Neg,
    kqpPa: Pa =!= P3#Neg): RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c2s01D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c2s01D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P2,
    kqpPc: Pc =!= P3): RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPc: Pc =:= P2,
    kqpPb: Pb =!= P3): RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P2,
    kqpPc: Pc =!= P3): RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPc: Pc =:= P2,
    kqpPa: Pa =!= P3): RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1, cqpPa: Pa =:= P2,
    kqpPb: Pb =!= P3): RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1, cqpPb: Pb =:= P2,
    kqpPa: Pa =!= P3): RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s01D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c2s02M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c2s02M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPc: Pc =:= P3#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P3#Neg,
    kqpPc: Pc =!= P2#Neg): RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPc: Pc =:= P3#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P3#Neg,
    kqpPc: Pc =!= P2#Neg): RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P2#Neg): RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P2#Neg): RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c2s02D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c2s02D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPc: Pc =:= P3,
    kqpPb: Pb =!= P2): RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P3,
    kqpPc: Pc =!= P2): RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPc: Pc =:= P3,
    kqpPa: Pa =!= P2): RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P3,
    kqpPc: Pc =!= P2): RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1, cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P2): RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1, cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P2): RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s02D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c2s12M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c2s12M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P2#Neg, cqpPc: Pc =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P2#Neg, cqpPb: Pb =:= P3#Neg,
    kqpPa: Pa =!= P1#Neg): RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P2#Neg, cqpPc: Pc =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P2#Neg, cqpPa: Pa =:= P3#Neg,
    kqpPb: Pb =!= P1#Neg): RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2#Neg, cqpPb: Pb =:= P3#Neg,
    kqpPc: Pc =!= P1#Neg): RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2#Neg, cqpPa: Pa =:= P3#Neg,
    kqpPc: Pc =!= P1#Neg): RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c2s12D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c2s12D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P2, cqpPc: Pc =:= P3,
    kqpPa: Pa =!= P1): RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P2, cqpPb: Pb =:= P3,
    kqpPa: Pa =!= P1): RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P2, cqpPc: Pc =:= P3,
    kqpPb: Pb =!= P1): RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P2, cqpPa: Pa =:= P3,
    kqpPb: Pb =!= P1): RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P2, cqpPb: Pb =:= P3,
    kqpPc: Pc =!= P1): RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P2, cqpPa: Pa =:= P3,
    kqpPc: Pc =!= P1): RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c2s12D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c3s012M[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c3s012M {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPb: Pb =:= P2#Neg, cqpPc: Pc =:= P3#Neg): RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1#Neg, cqpPc: Pc =:= P2#Neg, cqpPb: Pb =:= P3#Neg): RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPa: Pa =:= P2#Neg, cqpPc: Pc =:= P3#Neg): RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1#Neg, cqpPc: Pc =:= P2#Neg, cqpPa: Pa =:= P3#Neg): RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPa: Pa =:= P2#Neg, cqpPb: Pb =:= P3#Neg): RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1#Neg, cqpPb: Pb =:= P2#Neg, cqpPa: Pa =:= P3#Neg): RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012M[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}

class RHSv3a3s012c3s012D[U1 <: Unit, U2 <: Unit, U3 <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Q1 <: Integer, Q2 <: Integer, Q3 <: Integer](val value: Double)(implicit
  urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
  ivQ1: IntegerValue[Q1], ivQ2: IntegerValue[Q2], ivQ3: IntegerValue[Q3])

object RHSv3a3s012c3s012D {
  implicit def rhs$v3a3s012c012p012[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPb: Pb =:= P2, cqpPc: Pc =:= P3): RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Ub, U2], ivPb.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pa, Pb, Pc](v)
  }

  implicit def rhs$v3a3s012c012p021[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUaU1: Ua#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPa: Pa =:= P1, cqpPc: Pc =:= P2, cqpPb: Pb =:= P3): RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb] = {
    val fp = Seq((factor[Ua, U1], ivPa.value), (factor[Uc, U2], ivPc.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pa, Pc, Pb](v)
  }

  implicit def rhs$v3a3s012c012p102[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUcU3: Uc#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPa: Pa =:= P2, cqpPc: Pc =:= P3): RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Ua, U2], ivPa.value), (factor[Uc, U3], ivPc.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pb, Pa, Pc](v)
  }

  implicit def rhs$v3a3s012c012p120[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUbU1: Ub#RU =:= U1#RU, eqUcU2: Uc#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPb: Pb =:= P1, cqpPc: Pc =:= P2, cqpPa: Pa =:= P3): RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa] = {
    val fp = Seq((factor[Ub, U1], ivPb.value), (factor[Uc, U2], ivPc.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pb, Pc, Pa](v)
  }

  implicit def rhs$v3a3s012c012p201[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUaU2: Ua#RU =:= U2#RU, eqUbU3: Ub#RU =:= U3#RU,
    cqpPc: Pc =:= P1, cqpPa: Pa =:= P2, cqpPb: Pb =:= P3): RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ua, U2], ivPa.value), (factor[Ub, U3], ivPb.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pc, Pa, Pb](v)
  }

  implicit def rhs$v3a3s012c012p210[U1 <: Unit, U2 <: Unit, U3 <: Unit, Ua <: Unit, Ub <: Unit, Uc <: Unit, P1 <: Integer, P2 <: Integer, P3 <: Integer, Pa <: Integer, Pb <: Integer, Pc <: Integer](uv: UnitValue3[Ua, Pa, Ub, Pb, Uc, Pc])(implicit
    urecU1: UnitRec[U1], urecU2: UnitRec[U2], urecU3: UnitRec[U3],
    urecUa: UnitRec[Ua], urecUb: UnitRec[Ub], urecUc: UnitRec[Uc],
    ivPa: IntegerValue[Pa], ivPb: IntegerValue[Pb], ivPc: IntegerValue[Pc],
    eqUcU1: Uc#RU =:= U1#RU, eqUbU2: Ub#RU =:= U2#RU, eqUaU3: Ua#RU =:= U3#RU,
    cqpPc: Pc =:= P1, cqpPb: Pb =:= P2, cqpPa: Pa =:= P3): RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa] = {
    val fp = Seq((factor[Uc, U1], ivPc.value), (factor[Ub, U2], ivPb.value), (factor[Ua, U3], ivPa.value))
    val v = fp.foldLeft(uv.value) { case (v, (f, p)) => v * math.pow(f, p) }
    new RHSv3a3s012c3s012D[U1, U2, U3, P1, P2, P3, Pc, Pb, Pa](v)
  }
}
