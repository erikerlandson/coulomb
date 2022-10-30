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

// algebraic definitions
import algebra.instances.all.given
import coulomb.ops.algebra.all.given

// unit and value type policies for operations
import coulomb.policy.standard.given
import scala.language.implicitConversions

// unit definitions
import coulomb.units.si.{*, given}
```

### examples
Use `coulomb` to do typelevel unit analysis in Scala!
```scala mdoc:nest
// acceleration
val a = 9.8.withUnit[Meter / (Second ^ 2)]

// time or duration
val t = 10.withUnit[Second]

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
| `coulomb-core` | Provides core `coulomb` logic. Defines policies for `Int`, `Long`, `Float`, `Double`. |
| `coulomb-units` | Defines common units, including SI, MKSA, Accepted, time, temperature, and US traditional |
| `coulomb-spire` | Defines policies for working with Spire and Scala numeric types |


## Resources

The following resources expand the concepts behind `coulomb` and typelevel unit analysis:

- [API documentation for `coulomb`](https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/index.html)
- [github discussions](https://github.com/erikerlandson/coulomb/discussions)
- [Your Data Type is a Unit](http://erikerlandson.github.io/blog/2020/04/26/your-data-type-is-a-unit/)
- [Why Your Data Schema Should Include Units](https://www.youtube.com/watch?v=qrQmB2KFKE8)
- [Algorithmic Unit Analysis](http://erikerlandson.github.io/blog/2019/05/03/algorithmic-unit-analysis/)
- [A Unit Analysis of Linear Regression](http://erikerlandson.github.io/blog/2020/05/06/unit-analysis-for-linear-regression/)

## Development

### sbt-typelevel

`coulomb` uses the
[sbt-typelevel](https://typelevel.org/sbt-typelevel/)
project as its SBT build framework.
The following sections explain common `sbt` development commands that work with this framework.

### testing REPL environment

You can start up a Scala REPL that has all `coulomb` libraries provisioned,
using the command `sbt all/console`

```sh
$ sbt all/console
[info] welcome to sbt 1.7.2 (Red Hat, Inc. Java 17.0.4.1)
...
Welcome to Scala 3.2.0 (17.0.4.1, Java OpenJDK 64-Bit Server VM).
Type in expressions for evaluation. Or try :help.

scala> import coulomb.*
     | import coulomb.syntax.*
     |
     | import algebra.instances.all.given
     | import coulomb.ops.algebra.spire.all.given
     |
     | import coulomb.policy.spire.standard.given
     | import coulomb.units.si.*
     | import coulomb.units.si.given

scala> val x = 1d.withUnit[Meter]
val x: coulomb.Quantity[Double, coulomb.units.si.Meter] = 1.0

scala>
```

### scala code formatting

`coulomb` uses `scalafmt` for formatting code, and also checking its format during CI.
It is a good idea to run `scalafmt` before you check in new code to ensure it passes CI format checks:

```sh
# run scalafmt on scala files:
sbt scalafmtAll

# run scalafmt on build.sbt
sbt scalafmtSbt
```

### scaladoc and unidoc

All `scaladoc` is built and published to `javadoc.io` via CI,
however you can build and view them locally:

```sh
# generate scaladocs
sbt unidocs/doc

# serve docs via http locally:
python3 -m http.server -d unidocs/target/scala-3.1.2/unidoc/
```

You can also view directly at:
`file:///your/path/to/coulomb/unidocs/target/scala-3.1.2/unidoc/index.html`

### editing coulomb site documentation

The `coulomb` site documentation resides in the `docs` subproject,
and uses the
[sbt-typelevel-site](https://typelevel.org/sbt-typelevel/site.html)
framework, which is based on `mdoc` and `laika`

If you make edits or contributions to the site documentation,
you can preview with this command:

```sh
sbt docs/tlSitePreview

# then view at http://localhost:4242
```

### editing build.sbt

If you make changes to `build.sbt` remember to run the following:

```sh
sbt githubWorkflowGenerate
```

Make sure to check in any resulting changes to github workflow yaml.

## Previous versions

Versions of `coulomb` beginning with 0.6 require Scala 3,
and are not compatible with Scala 2.13.

`coulomb` versions <= 0.5.x require Scala 2.13.
The 0.5.x series will continue to be supported on a maintenance basis only.

You can browse the legacy scala doc for the `*_2.13` packages
[here](https://www.javadoc.io/doc/com.manyangled).

The legacy tutorial documentation will continue to be viewable
[here](https://github.com/erikerlandson/coulomb/blob/scala2/README.md).

The `scala2` branch of `coulomb` on github will continue to hold coulomb <= 0.5.x

## Code of Conduct

The `coulomb` project supports the
[Scala Code of Conduct](https://typelevel.org/code-of-conduct.html).
All contributors are expected to respect this code.
Any violations of this code of conduct should be reported to
[the author](https://github.com/erikerlandson/).

