### coulomb
A statically typed unit analysis library for Scala

`coulomb` is still under construction, but I hope to have a consumable release soon.  Feel free to play with it in the mean time! I'll keep the head of `develop` branch unbroken.

#### Why name it `coulomb` ?
`coulomb` is a library for "static units", and 'coulomb' is the "unit of static" (aka charge).

### examples

```scala
scala> import com.manyangled.coulomb._; import ChurchInt._; import fundamental._; import prefix._; import derived._; import Unit._
import com.manyangled.coulomb._
import ChurchInt._
import fundamental._
import prefix._
import derived._
import Unit._

scala> 1.withUnit[EarthGravity].as[Foot </> (Second <^> _2)]
res0: com.manyangled.coulomb.Unit[com.manyangled.coulomb.</>[com.manyangled.coulomb.derived.Foot,com.manyangled.coulomb.<^>[com.manyangled.coulomb.fundamental.Second,com.manyangled.coulomb.ChurchInt._2]]] = 32.175196850393704 foot / (second ^ 2)

scala> 1.withUnit[Liter].as[Meter <^> _3]
res1: com.manyangled.coulomb.Unit[com.manyangled.coulomb.<^>[com.manyangled.coulomb.fundamental.Meter,com.manyangled.coulomb.ChurchInt._3]] = 0.001 meter ^ 3

scala> 1.withUnit[Foot] + 1.withUnit[Yard]
res2: com.manyangled.coulomb.Unit[com.manyangled.coulomb.derived.Foot] = 4.0 foot

scala> 1.withUnit[Foot] + 1.withUnit[Meter]
res3: com.manyangled.coulomb.Unit[com.manyangled.coulomb.derived.Foot] = 4.2808398950131235 foot

scala> 1.withUnit[Foot <^> _2] + 1.withUnit[Liter </> Meter]
res4: com.manyangled.coulomb.Unit[com.manyangled.coulomb.<^>[com.manyangled.coulomb.derived.Foot,com.manyangled.coulomb.ChurchInt._2]] = 1.0107639104167097 foot ^ 2

scala> 1.withUnit[Foot <^> _2] * 1.withUnit[Liter </> Meter]
res5: com.manyangled.coulomb.Unit[this.U] = 9.290304E-5 meter ^ 4

scala> 1.withUnit[Foot <^> _2] / 1.withUnit[Liter </> Meter]
res6: com.manyangled.coulomb.Unit[this.U] = 92.90304 unitless

```
