# coulomb development

## sbt-typelevel

`coulomb` uses the
[sbt-typelevel](https://typelevel.org/sbt-typelevel/)
project as its SBT build framework.
The following sections explain common `sbt` development commands that work with this framework.

## repository organization

Each `coulomb` package has a corresponding directory in the repository.
For example, the code for `coulomb-core` resides in directory `core`,
the code for `coulomb-units` resides in `units`, etc.

Site documentation resides in `docs`

## build targets

The `coulomb` project cross-builds for JVM, ScalaJS and Scala Native.
You can run standard SBT commands against one or all of these.

```sh
# run unit tests for all sub-projects and all compiler cross-builds
sbt test

# unit tests for JVM
sbt rootJVM/test

# unit tests for ScalaJS
sbt rootJS/test

# unit tests for ScalaNative
sbt rootNative/test

# unit tests for coulomb-core, JVM
sbt coreJVM/test
```

## REPL environment

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

## code formatting

`coulomb` uses `scalafmt` for formatting code, and also checking its format during CI.
It is a good idea to run `scalafmt` before you check in new code to ensure it passes CI format checks:

```sh
# run scalafmt on scala files:
sbt scalafmtAll

# run scalafmt on build.sbt
sbt scalafmtSbt

# run format checks
sbt scalafmtCheckAll

# check format of build.sbt
sbt scalafmtSbtCheck
```

## scaladoc and unidoc

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

## coulomb site documentation

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

## build.sbt

If you make changes to `build.sbt` remember to run the following:

```sh
sbt githubWorkflowGenerate
```

Make sure to check in any resulting changes to github workflow yaml.

## MiMa and binary compatibility

The `coulomb` CI workflows check all new commits for binary compatibility with the current release using
[MiMa](https://github.com/lightbend/mima).
If you are proposing a change that breaks binary compatibility with the latest release, the CI tests will fail.

There are two options to deal with this:

- Modify your contribution so that it preserves binary compatibility. This is the preferred option.
- If preserving binary compatibility is infeasible, update the value of `ThisBuild / tlBaseVersion` in `build.sbt`

