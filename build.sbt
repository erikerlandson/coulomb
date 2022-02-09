ThisBuild / tlBaseVersion := "0.6"

// publish settings
ThisBuild / developers += tlGitHubDev("erikerlandson", "Erik Erlandson")
ThisBuild / organization := "com.manyangled"
ThisBuild / organizationName := "Erik Erlandson"
ThisBuild / startYear := Some(2022)

// ci settings
ThisBuild / tlCiReleaseBranches := Seq("scala3")
// don't overwrite the site published from develop branch for now
// set this to whatever main branch is to enable (e.g. develop, main, etc)
ThisBuild / tlSitePublishBranch := Some("branch-to-publish-from")

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
  .enablePlugins(NoPublishPlugin) // don't publish

lazy val docs = project.in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
