// If you make changes to this build configuration, then also run:
// sbt githubWorkflowGenerate
// and check in the updates to github workflow yamls

// this line to kick off pull request from branch 'simplify-coulomb'
// base version for assessing MIMA
ThisBuild / tlBaseVersion := "0.9"

// publish settings
// artifacts now publish to s01.oss.sonatype.org, per:
// https://github.com/erikerlandson/coulomb/issues/500
//
// The sonatype publishing depends on 3 github action secrets,
// which are explained here:
// https://typelevel.org/sbt-typelevel/secrets.html
//
// On linux, you need to give a '-w 0' flag for base64:
// gpg --list-secret-keys
// gpg --armor --export-secret-keys $KEY_ID | base64 -w 0
//
// As of June 2024, you also need to use the "api token"
// for sonatype, not the legacy user name and password,
// instructions here:
// https://typelevel.org/sbt-typelevel/secrets.html#sonatype-credentials
ThisBuild / developers += tlGitHubDev("erikerlandson", "Erik Erlandson")
ThisBuild / organization := "com.manyangled"
ThisBuild / organizationName := "Erik Erlandson"
ThisBuild / licenses := List(
    "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt")
)
ThisBuild / startYear := Some(2022)

// ci settings
ThisBuild / tlCiReleaseBranches := Seq("scala3")
ThisBuild / tlSitePublishBranch := Some("scala3")
// use jdk 17 in ci builds
ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("17"))

ThisBuild / resolvers += Resolver.sonatypeCentralSnapshots

// be sure to use LTS version for improved client compatibility
// currently that is 3.3.x
// https://www.scala-lang.org/download/all.html
ThisBuild / crossScalaVersions := Seq("3.9.0")

// run tests sequentially for easier failure debugging
Test / parallelExecution := false

// throwing '-java-output-version 8' is crashing the compiler
ThisBuild / tlJdkRelease := None

// At least for now, I'd like any -W warnings to not crash the build
ThisBuild / tlFatalWarnings := false

def commonSettings = Seq(
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0" % Test,
    // newer versions of sbt-typelevel throw new warning flags
    // which my code is failing - filter them out until I can fix the code
    Compile / scalacOptions ~= (_.filterNot { x => x.startsWith("-W") })
)

lazy val root = tlCrossRootProject
    .aggregate(
        core,
        units,
        runtime,
        parser,
        pureconfig,
        refined,
        testkit,
        unidocs
    )

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("core"))
    .settings(name := "coulomb-core")
    .settings(commonSettings: _*)
    .settings(libraryDependencies += "org.typelevel" %%% "algebra" % "2.10.0")
    .settings(libraryDependencies += "org.typelevel" %%% "spire" % "0.18.0")
    .platformsSettings(JSPlatform, NativePlatform)(
        Test / unmanagedSources / excludeFilter := HiddenFileFilter || "*serde.scala"
    )

lazy val units = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("units"))
    .settings(name := "coulomb-units")
    .dependsOn(core % "compile->compile;test->test")
    .settings(commonSettings: _*)
    .platformsSettings(JSPlatform, NativePlatform)(
        libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.5.0" % Test
    )

// see also: https://github.com/lampepfl/dotty/issues/7647
lazy val runtime = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("runtime"))
    .settings(name := "coulomb-runtime")
    .dependsOn(
        core % "compile->compile;test->test",
        units % Test
    )
    .settings(
        tlVersionIntroduced := Map("3" -> "0.8.0")
    )
    .settings(commonSettings: _*)
    .settings(
        // staging compiler is only supported on JVM
        // but is also used to satisfy builds on JS and Native
        libraryDependencies += "org.scala-lang" %% "scala3-staging" % scalaVersion.value
    )
    .platformsSettings(JSPlatform, NativePlatform)(
        // any unit tests using staging must be excluded from JS and Native
        Test / unmanagedSources / excludeFilter := HiddenFileFilter || "*stagingquantity.scala"
    )

// cats-parse doesn't seem to build for JS or Native
lazy val parser = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("parser"))
    .settings(name := "coulomb-parser")
    .dependsOn(
        core % "compile->compile;test->test",
        runtime,
        units % Test
    )
    .settings(
        tlVersionIntroduced := Map("3" -> "0.8.0")
    )
    .settings(commonSettings: _*)
    .settings(
        libraryDependencies += "org.typelevel" %%% "cats-parse" % "1.0.0"
    )

// pureconfig doesn't currently build for JS or Native
// https://github.com/pureconfig/pureconfig/issues/1307
lazy val pureconfig = crossProject(
    JVMPlatform /*, JSPlatform, NativePlatform */
)
    .crossType(CrossType.Pure)
    .in(file("pureconfig"))
    .settings(name := "coulomb-pureconfig")
    .dependsOn(
        core % "compile->compile;test->test",
        runtime,
        parser,
        units % Test
    )
    .settings(
        tlVersionIntroduced := Map("3" -> "0.8.0")
    )
    .settings(commonSettings: _*)
    .settings(
        libraryDependencies += "com.github.pureconfig" %%% "pureconfig-core" % "0.17.10"
    )

lazy val refined = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("refined"))
    .settings(name := "coulomb-refined")
    .dependsOn(core % "compile->compile;test->test", units % Test)
    .settings(commonSettings: _*)
    .settings(
        libraryDependencies += "eu.timepit" %%% "refined" % "0.11.1",
        tlVersionIntroduced := Map("3" -> "0.7.2")
    )

lazy val testkit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("testkit"))
    .settings(
        name := "coulomb-testkit",
        libraryDependencies ++= Seq(
            "org.scalacheck" %%% "scalacheck" % "1.17.1",
            "org.scalameta" %%% "munit-scalacheck" % "1.0.0-M11" % Test,
            "org.typelevel" %%% "algebra-laws" % "2.10.0" % Test,
            "org.typelevel" %%% "discipline-munit" % "2.0.0-M3" % Test
        )
    )
    .dependsOn(core % "compile->compile;test->test")
    .settings(commonSettings: _*)

// a target for rolling up all subproject deps: a convenient
// way to get a repl that has access to all subprojects
// sbt all/console
lazy val all = project
    .in(file("all")) // sbt will create this - it is unused
    .dependsOn(
        core.jvm,
        units.jvm,
        runtime.jvm,
        parser.jvm,
        pureconfig.jvm,
        refined.jvm
    ) // scala repl only needs JVMPlatform subproj builds
    .settings(name := "coulomb-all")
    .enablePlugins(NoPublishPlugin) // don't publish

// a published artifact aggregating API docs for viewing at javadoc.io
// build and view scaladocs locally:
// sbt unidocs/doc
// view at:  file:///your/path/to/coulomb/unidocs/target/scala-3.1.2/unidoc/index.html
// serve locally:
// python3 -m http.server -d unidocs/target/scala-3.1.2/unidoc/
lazy val unidocs = project
    .in(file("unidocs")) // sbt will create this
    .settings(name := "coulomb-docs") // the name of the artifact
    .enablePlugins(TypelevelUnidocPlugin) // enable Unidoc + publishing

// https://typelevel.org/sbt-typelevel/site.html
// sbt docs/tlSitePreview
// http://localhost:4242
import laika.ast.VirtualPath
import laika.config.{LinkConfig, ApiLinks, SourceLinks, TargetDefinition}
lazy val docs = project
    .in(file("site"))
    .dependsOn(
        core.jvm,
        units.jvm,
        runtime.jvm,
        parser.jvm,
        pureconfig.jvm,
        refined.jvm
    )
    .enablePlugins(TypelevelSitePlugin)
    .settings(
        // turn off the new -W warnings in mdoc scala compilations
        // at least until I can get a better handle on how to work with them
        Compile / scalacOptions ~= (_.filterNot { x => x.startsWith("-W") })
    )
    .settings(
        laikaConfig := LaikaConfig.defaults
            .withConfigValue(
                LinkConfig.empty
                    .addApiLinks(
                        ApiLinks(
                            "https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/"
                        ).withPackagePrefix("coulomb")
                    )
                    .addApiLinks(
                        ApiLinks(
                            "https://scala-lang.org/api/3.x/"
                        ).withPackagePrefix("scala")
                    )
                    .addApiLinks(
                        ApiLinks(
                            "https://javadoc.io/doc/org.typelevel/algebra_3/latest/"
                        ).withPackagePrefix("algebra")
                    )
                    .addApiLinks(
                        ApiLinks(
                            "https://javadoc.io/doc/org.typelevel/cats-core_3/latest/"
                        ).withPackagePrefix("cats")
                    )
                    .addApiLinks(
                        ApiLinks(
                            "https://javadoc.io/doc/org.typelevel/spire_3/latest/"
                        ).withPackagePrefix("spire")
                    )
                    .addApiLinks(
                        ApiLinks(
                            "https://javadoc.io/doc/com.github.pureconfig/pureconfig-core_3/latest/"
                        ).withPackagePrefix("pureconfig")
                    )
                    .addApiLinks(
                        // the refined api link isn't super helpful because @:api(...)
                        // doesn't work for type defs or methods, which is most of
                        // what refined provides
                        ApiLinks(
                            // refined is not publishing scaladoc for scala 3 yet
                            "https://javadoc.io/doc/eu.timepit/refined_2.13/latest/"
                        ).withPackagePrefix("eu.timepit.refined")
                    )
                    .addTargets(
                        // Target names need to be all lowercase.
                        // Note, this does not align with Laika docs.
                        // In future laika releases the names will be case insensitive, see:
                        // https://github.com/typelevel/Laika/pull/541
                        TargetDefinition.external(
                            // intended usage: [Quantity][quantitytypedef]
                            // Links to type defs do not work properly with laika '@:api(...)' constructs
                            // which is going to make a lot of coulomb references harder to do.
                            "quantitytypedef",
                            "https://www.javadoc.io/doc/com.manyangled/coulomb-docs_3/latest/coulomb.html#Quantity[V,U]=V"
                        ),
                        TargetDefinition.external(
                            "refinedapidocs",
                            "https://javadoc.io/doc/eu.timepit/refined_2.13/latest/eu/timepit/refined/index.html"
                        ),
                        TargetDefinition.internal(
                            "coulomb-introduction",
                            VirtualPath.parse("README.md")
                        ),
                        TargetDefinition.internal(
                            "coulomb-core",
                            VirtualPath.parse("coulomb-core.md")
                        ),
                        TargetDefinition.internal(
                            "coulomb-units",
                            VirtualPath.parse("coulomb-units.md")
                        ),
                        TargetDefinition.internal(
                            "coulomb-refined",
                            VirtualPath.parse("coulomb-refined.md")
                        ),
                        TargetDefinition.internal(
                            "coulomb-runtime",
                            VirtualPath.parse("coulomb-runtime.md")
                        ),
                        TargetDefinition.internal(
                            "coulomb-parser",
                            VirtualPath.parse("coulomb-parser.md")
                        ),
                        TargetDefinition.internal(
                            "coulomb-pureconfig",
                            VirtualPath.parse("coulomb-pureconfig.md")
                        )
                    )
            )
    )

// sbt benchmarksJVM/run, etc
lazy val benchmarks = crossProject(JVMPlatform, NativePlatform, JSPlatform)
    .crossType(CrossType.Pure)
    .in(file("benchmarks"))
    .settings(name := "coulomb-benchmarks")
    .dependsOn(core, units)
    .enablePlugins(NoPublishPlugin)
    .disablePlugins(MimaPlugin)
    .settings(commonSettings: _*)
    .platformsSettings(JSPlatform)(
        scalaJSUseMainModuleInitializer := true
    )
