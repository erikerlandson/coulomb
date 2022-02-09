// If you make changes to this build configuration, then also run:
// sbt githubWorkflowGenerate
// and check in the updates to github workflow yamls

// base version for assessing MIMA
ThisBuild / tlBaseVersion := "0.6"

// publish settings
ThisBuild / developers += tlGitHubDev("erikerlandson", "Erik Erlandson")
ThisBuild / organization := "com.manyangled"
ThisBuild / organizationName := "Erik Erlandson"
ThisBuild / licenses := List("Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0"))
ThisBuild / startYear := Some(2022)

// ci settings
ThisBuild / tlCiReleaseBranches := Seq("scala3")
// don't overwrite the site published from develop branch for now
// to enable, set this to whatever main branch is to enable (e.g. develop, main, etc)
ThisBuild / tlSitePublishBranch := Some("no-such-branch")

ThisBuild / crossScalaVersions := Seq("3.1.1")

lazy val root = tlCrossRootProject
  .aggregate(core, units)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(name := "coulomb-core")

lazy val units = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("units"))
  .dependsOn(core)
  .settings(name := "coulomb-units")

// a target for rolling up all subproject deps: a convenient
// way to get a repl that has access to all subprojects
// sbt all/console
lazy val all = project
  .in(file("all")) // sbt will create this - it is unused
  .dependsOn(core.jvm, units.jvm) // scala repl only needs JVMPlatform subproj builds
  .settings(name := "coulomb-all")
  .settings(publish := {}, publishLocal := {}) // don't publish

lazy val docs = project.in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
