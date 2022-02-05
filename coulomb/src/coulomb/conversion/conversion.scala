package coulomb.conversion

// a coulomb Conversion converts raw quantity values directly,
// using knowledge about corresponding unit types UF and UT
abstract class Conversion[VF, UF, VT, UT] extends (VF => VT)
