### unit4s
Experiment with unit library with a type system

```scala
scala> import com.manyangled.unit4s._; import com.manyangled.church.Integer._; import fundamental._; import prefix._; import derived._; import Unit._
import com.manyangled.unit4s._
import com.manyangled.church.Integer._
import fundamental._
import prefix._
import derived._
import Unit._

scala> 1.withUnit[EarthGravity].as[Foot </> (Second <^> _2)]
res0: com.manyangled.unit4s.Unit[com.manyangled.unit4s.</>[com.manyangled.unit4s.derived.Foot,com.manyangled.unit4s.<^>[com.manyangled.unit4s.fundamental.Second,com.manyangled.church.Integer._2]]] = 32.175196850393704 foot / (second ^ 2)

scala> 1.withUnit[Liter].as[Meter <^> _3]
res1: com.manyangled.unit4s.Unit[com.manyangled.unit4s.<^>[com.manyangled.unit4s.fundamental.Meter,com.manyangled.church.Integer._3]] = 0.001 meter ^ 3

scala> 1.withUnit[Foot] + 1.withUnit[Yard]
res2: com.manyangled.unit4s.Unit[com.manyangled.unit4s.derived.Foot] = 4.0 foot

scala> 1.withUnit[Foot] + 1.withUnit[Meter]
res3: com.manyangled.unit4s.Unit[com.manyangled.unit4s.derived.Foot] = 4.2808398950131235 foot

scala> 1.withUnit[Foot <^> _2] + 1.withUnit[Liter </> Meter]
res4: com.manyangled.unit4s.Unit[com.manyangled.unit4s.<^>[com.manyangled.unit4s.derived.Foot,com.manyangled.church.Integer._2]] = 1.0107639104167097 foot ^ 2

scala> 1.withUnit[Foot <^> _2] * 1.withUnit[Liter </> Meter]
res5: com.manyangled.unit4s.Unit[this.U] = 9.290304E-5 meter ^ 4

scala> 1.withUnit[Foot <^> _2] / 1.withUnit[Liter </> Meter]
res6: com.manyangled.unit4s.Unit[this.U] = 92.90304 unitless

```
