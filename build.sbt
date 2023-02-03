// If you make changes to this build configuration, then also run:
// sbt githubWorkflowGenerate
// and check in the updates to github workflow yamls

// base version for assessing MIMA
ThisBuild / tlBaseVersion := "0.7"

// publish settings
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

ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")
ThisBuild / crossScalaVersions := Seq("3.2.2")

// run tests sequentially for easier failure debugging
Test / parallelExecution := false

def commonSettings = Seq(
    libraryDependencies += "org.scalameta" %%% "munit" % "1.0.0-M7" % Test
)

lazy val root = tlCrossRootProject
    .aggregate(core, units, runtime, spire, refined, testkit, unidocs)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("core"))
    .settings(name := "coulomb-core")
    .settings(commonSettings: _*)
    .settings(libraryDependencies += "org.typelevel" %%% "algebra" % "2.9.0")
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
    .settings(commonSettings: _*)
    .settings(
        tlVersionIntroduced := Map("3" -> "0.7.4"),
        libraryDependencies += "org.scala-lang" %% "scala3-staging" % scalaVersion.value
    )

lazy val spire = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("spire"))
    .settings(name := "coulomb-spire")
    .dependsOn(core % "compile->compile;test->test", units % Test)
    .settings(commonSettings: _*)
    .settings(libraryDependencies += "org.typelevel" %%% "spire" % "0.18.0")

lazy val refined = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("refined"))
    .settings(name := "coulomb-refined")
    .dependsOn(core % "compile->compile;test->test", units % Test)
    .settings(commonSettings: _*)
    .settings(
        libraryDependencies += "eu.timepit" %%% "refined" % "0.10.2",
        tlVersionIntroduced := Map("3" -> "0.7.2")
    )

lazy val testkit = crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("testkit"))
    .settings(
        name := "coulomb-testkit",
        libraryDependencies ++= Seq(
            "org.scalacheck" %%% "scalacheck" % "1.17.0",
            "org.scalameta" %%% "munit-scalacheck" % "1.0.0-M7" % Test,
            "org.typelevel" %%% "algebra-laws" % "2.9.0" % Test,
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
        spire.jvm,
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
lazy val docs = project
    .in(file("site"))
    .dependsOn(core.jvm, units.jvm, spire.jvm, refined.jvm)
    .enablePlugins(TypelevelSitePlugin)

// https://github.com/sbt/sbt-jmh
// sbt "benchmarks/Jmh/run .*Benchmark"
lazy val benchmarks = project
    .in(file("benchmarks"))
    .dependsOn(core.jvm % "compile->compile;compile->test")
    .settings(name := "coulomb-benchmarks")
    .enablePlugins(NoPublishPlugin, JmhPlugin)

// can enable this to add benchmarks to CI
// ThisBuild / githubWorkflowBuild += WorkflowStep.Sbt(List("benchmarks/Jmh/run .*Benchmark"))
