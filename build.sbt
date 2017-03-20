def commonSettings = Seq(
  organization := "com.manyangled",
  version := "0.2.0",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.11.8", "2.12.1"),
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.typelevel" %% "spire-macros" % "0.14.0",
    "org.typelevel" %% "spire" % "0.14.0",
    "org.scalatest" %% "scalatest" % "3.0.1" % Test
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt"))

site.settings

site.includeScaladoc()

//Re-enable if/when we want to support gh-pages w/ jekyll
//site.jekyllSupport(),

ghpages.settings

git.remoteRepo := "git@github.com:erikerlandson/coulomb.git"

lazy val coulomb = (project in file("."))
  .aggregate(unitexpr, macros)
  .dependsOn(unitexpr, macros)
  .settings(name := "coulomb")
  .settings(commonSettings :_*)

lazy val macros = (project in file("macros"))
  .dependsOn(unitexpr)
  .settings(name := "coulomb-macros")
  .settings(commonSettings :_*)

lazy val unitexpr = (project in file("unitexpr"))
  .settings(name := "coulomb-unitexpr")
  .settings(commonSettings :_*)
