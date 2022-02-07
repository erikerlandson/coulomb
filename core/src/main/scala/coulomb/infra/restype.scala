package coulomb.infra

import coulomb.*

abstract class MulRU[UL, UR]:
    type RU
object MulRU:
    transparent inline given[UL, UR]: MulRU[UL, UR] = ${ meta.mulRU[UL, UR] }

abstract class DivRU[UL, UR]:
    type RU
object DivRU:
    transparent inline given[UL, UR]: DivRU[UL, UR] = ${ meta.divRU[UL, UR] }

abstract class PowRU[U, P]:
    type RU
object PowRU:
    transparent inline given[U, P]: PowRU[U, P] = ${ meta.powRU[U, P] }
