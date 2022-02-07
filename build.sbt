ThisBuild / tlBaseVersion := "0.6"

// publish settings
ThisBuild / developers += tlGitHubDev("erikerlandson", "Erik Erlandson")
ThisBuild / organization := "com.manyangled"
ThisBuild / organizationName := "Erik Erlandson"
ThisBuild / startYear := Some("2022")

// ci settings
ThisBuild / tlCiReleaseBranches := Seq("scala3")
// don't overwrite the site published from develop branch for now
ThisBuild / tlSitePublishBranch := Some("develop")

ThisBuild / crossScalaVersions := Seq("3.1.1")

lazy val root = tlCrossRootProject.aggregate(core, units)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("core"))
  .settings(
    name := "coulomb-core"
  )

lazy val units = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("units"))
  .dependsOn(core)
  .settings(
    name := "coulomb-units"
  )

lazy val docs = project.in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
