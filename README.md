### unit4s
Experiment with unit library with a type system

### Design goals
**Unit analysis via the type system**
I want units to be related by their exponents, and I want the exponent to also be a type parameter ([using a church numeral type system](https://github.com/erikerlandson/unit4s/blob/master/src/main/scala/com/manyangled/church/church.scala)), for example, I want it to work something like this, or similar:

``` scala
type Length = DimensionPower[Length, church.Integer._1]
type Area = DimensionPower[Length, church.Integer._2]
```

**Support easy context bounds for Dimension**
I want the user to be able (somehow) to easily express the idea that a parameter can be "any unit of Length", or "any unit of Time", etc.  Something equivalent to:

``` scala
def f[UnitValue :Length](x: UnitValue) {
  /// do something requiring a Length unit value (meter, foot, furlong, etc)
}
```

**Support easy conversion between units**
``` scala
val feet = Meter(1.0).to[Foot]
```
Did this successfully using implicits, so far.  Might be a bit inconvenient for a user to code, but not sure any user will need to.

**Support multi-unit sytems**
I want to support easily-extendable systems using multiple units, so one can do something equivalent to:
``` scala
trait MyUnitSystem extends Length :: Mass :: Time :: HNil
val u: MyUnitSystem = Meter(1.3) :: Kilo.Gram(3.4) :: Minute(4) :: HNil

// 'speed' would have type such as: Power[Meter, _1] :: Power[Kilo.Gram, _0] :: Power[Minute, _neg1] :: HNil
val speed = Meter(1.0) / Second(1.0) 

```
I have some ideas using shapeless `HList`

**Nice formatted i/o of some kind**
[squants](https://github.com/garyKeorkunian/squants) is nicely developed here (and with conversions, and multi-unit systems, although it isn't "true" open-ended unit analysis via type sytem)
