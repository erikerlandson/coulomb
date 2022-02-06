import mill._
import scalalib._
import scalajslib._
import publish._
import mill.api.PathRef

// any project-wide values we would like to define
object project {
    def name: String = "coulomb"
    def version: String = "0.10.0-M1"
    // Scala 3 treats maven publishing suffixes quite differently, and I am
    // unsure if cross-compile list below will ever have more than one 3.x.x element.
    // TODO: understand scala's evolving policy on forward-compatible compilations
    // as it relates to choosing a scala-3 compiler version, forward-compatibility flags, etc
    def crossVersions: Seq[String] = List("3.1.1")
    // place-holder for when we get to JS builds
    def crossVersionsJS: Seq[(String, String)] = List(("3.1.1", "9.9.9"))
}

object `coulomb-core` extends Module {
    object jvm extends Cross[JvmModule](project.crossVersions:_*)

    class JvmModule(val crossScalaVersion: String) extends CoulombModule {
        def scalaPlatform = "jvm"
    }

    // eventually we will add JS builds, like the following
/*
    object js extends Cross[JsModule](project.crossVersionsJS:_*)

    class JsModule(val crossScalaVersion: String, crossScalaVersionJS: String) extends CoulombModule with ScalaJSModule {
        def scalaPlatform = "js"
        def scalaJSVersion = crossScalaVersionJS
    }
*/
}

object `coulomb-units` extends Module {
    object jvm extends Cross[JvmModule](project.crossVersions:_*)

    class JvmModule(val crossScalaVersion: String) extends CoulombModule {
        def scalaPlatform = "jvm"
        def moduleDeps = List(`coulomb-core`.jvm())
    }
}

// a convenience for bringing up a repl console with all subprojects compiled
// is there a better way to do this?
// usage:
// mill -i coulomb.console
object coulomb extends ScalaModule {
    val v = project.crossVersions.head
    def scalaVersion = v
    def moduleDeps = List(
        `coulomb-core`.jvm(v),
        `coulomb-units`.jvm(v)
    )
}

// may inherit from some other traits later, e.g. PublishModule
// https://github.com/erikerlandson/mill-testbed/blob/main/build.sc
trait CoulombModule extends CrossScalaModule {
    // scala compile platform, such as "jvm" or "js" or "native"
    def scalaPlatform: String

    // mill's default source path behavior is to look under proj/jvm/src, proj/js/src, etc,
    // which I'd like to change so that all platform builds use 'proj/src' and
    // any platform specific code is in proj/src-jvm, proj/src-js, etc
    def sources = T.sources(
        millSourcePath / os.up / "src",
        millSourcePath / os.up / s"src-${scalaPlatform}"
    )
}
