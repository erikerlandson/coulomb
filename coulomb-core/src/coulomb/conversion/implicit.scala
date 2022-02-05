package coulomb.conversion.implicitConversion

import coulomb.*

// rules for generating scala.Conversion objects for implicit conversions
transparent inline given standardIC[VF, UF, VT, UT](using conv: coulomb.conversion.Conversion[VF, UF, VT, UT]): scala.Conversion[Quantity[VF, UF], Quantity[VT, UT]] =
    new scala.Conversion[Quantity[VF, UF], Quantity[VT, UT]]:
        def apply(q: Quantity[VF, UF]): Quantity[VT, UT] = conv(q.value).withUnit[UT]
