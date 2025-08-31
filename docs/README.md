@:image(assets/coulomb-splash-800x400.png) {
    intrinsicWidth = 800
    intrinsicHeight = 400
    alt = coulomb splash
}

# Introduction to coulomb

## Quick Start

### documentation
API
[documentation](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/index.html)
for `coulomb` is hosted at javadoc.io

### packages
Include `coulomb` with your Scala project:
```scala
libraryDependencies += "com.manyangled" %% "coulomb-core" % "@VERSION@"
libraryDependencies += "com.manyangled" %% "coulomb-units" % "@VERSION@"
```

### import statements
Import `coulomb` definitions:
```scala mdoc
// fundamental coulomb types and methods
import coulomb.*
import coulomb.syntax.*

// algebraic typeclass definitions
import algebra.instances.all.given

// unit definitions
import coulomb.units.si.{*, given}
```

### examples
Use `coulomb` to do typelevel unit analysis in Scala!
```scala mdoc:nest
// acceleration
val a = 9.8.withUnit[Meter / (Second ^ 2)]

// time or duration
val t = 10.0.withUnit[Second]

// velocity
val v = a * t

// position
val x = a * (t.pow[2]) / 2
```

Improper unit analysis is a compile-time failure.
```scala mdoc:nest:fail
val time = 1.withUnit[Second]
val dist = 1.withUnit[Meter]

// Incompatible unit operations are a compile-time type error
val fail = time + dist
```

## Packages

`coulomb` publishes the following packages

| name | description |
| ---: | :--- |
| [coulomb-core] | Provides core `coulomb` logic. Defines policies for `Int`, `Long`, `Float`, `Double`. |
| [coulomb-units] | Defines common units, including SI, MKSA, Accepted, time, temperature, and US traditional |
| [coulomb-refined] | Unit analysis with typelevel [refined](https://github.com/fthomas/refined#refined-simple-refinement-types-for-scala) awareness |
| [coulomb-pureconfig] | Configuration I/O with @:api(coulomb.Quantity$) values |
| [coulomb-runtime] | Runtime units and quantities |
| [coulomb-parser] | Parsing of expressions into runtime units |

## Resources

The following resources expand the concepts behind `coulomb` and typelevel unit analysis:

- [API documentation for `coulomb`](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/index.html)
- [github discussions](https://github.com/erikerlandson/coulomb/discussions)
- [Your Data Type is a Unit](http://erikerlandson.github.io/blog/2020/04/26/your-data-type-is-a-unit/)
- [Why Your Data Schema Should Include Units](https://www.youtube.com/watch?v=qrQmB2KFKE8)
- [Algorithmic Unit Analysis](http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/)
- [A Unit Analysis of Linear Regression](http://erikerlandson.github.io/blog/2020/05/06/unit-analysis-for-linear-regression/)

## Code of Conduct

The `coulomb` project supports the
[Scala Code of Conduct](https://typelevel.org/code-of-conduct.html).
All contributors are expected to respect this code.
Any violations of this code of conduct should be reported to
[the author](https://github.com/erikerlandson/).

### gen-ai based contributions

Contributions to `coulomb` using generative AI will be considered.
However: low quality "vibe-coded" contributions will be treated the same as any low quality code,
and looked upon with disfavor.

The `coulomb` project does not currently intend to support gen-ai related configurations,
such as `.cursorrules`, `CLAUDE.md`, etc.

## Versions and Compatibility

### `coulomb` and Scala 3

Versions of `coulomb` beginning with `0.6` require Scala 3,
and are **not** compatible with Scala 2.

### `coulomb` and API scaladoc

API documentation for all `coulomb` versions can be viewed at
[javadoc.io](https://www.javadoc.io/doc/com.manyangled).

Versions 0.6 and later are organized
[here](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/index.html),
where you can select a release version in drop-down menu at the top of the page.

### `coulomb` 0.9.x

`coulomb` `0.9` is the result of a substantial redesign of `coulomb`'s type system,
for the purpose of simplifying the project's code and its implicit type dependencies.

Some `#include` statements will need to be changed relative to previous versions.
A few operations that previously could be done implicitly now can only be done via explicit method calls;
**most notably, value type conversions no longer happen implicitly.**
Unit type conversions still happen implicitly for value types that can represent fractional values
(for example, Double, Float, BigDecimal).

`coulomb` `0.9` makes more extensive use of Scala 3's advanced inline code expansion and its
metaprogramming capabilities, to improve the efficiency of the code generated under the hood.
Its simplified implicit type dependencies make its code easier to understand and easier to 
extend and integrate.

### `coulomb` 0.6.x to 0.8.x

`coulomb` versions `0.6` through `0.8` require Scala 3.

You can view the `coulomb` microsite documentation for these versions locally:

```bash
$ git clone https://github.com/erikerlandson/coulomb.git
$ cd coulomb
$ checkout vX.Y.Z
$ sbt docs/tlSitePreview
$ # view in browser at: http://localhost:4242
```

### `coulomb` versions <= 0.5.x

**The `0.5.x` series is no longer actively maintained.**
The `scala2` branch of `coulomb` on github will continue to hold coulomb <= `0.5.x`

`coulomb` versions <= `0.5.x` require Scala 2.13.

You can browse the legacy scala doc for the `*_2.13` packages
[here](https://www.javadoc.io/doc/com.manyangled).

The legacy tutorial documentation will continue to be viewable
[here](https://github.com/erikerlandson/coulomb/blob/scala2/README.md).