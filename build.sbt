def commonSettings = Seq(
  organization := "com.manyangled",
  version := "0.2.0",
  scalaVersion := "2.12.3",
  //scalaOrganization := "org.typelevel",
  //scalaVersion := "2.12.4-bin-typelevel-4",
  crossScalaVersions := Seq("2.12.4"),
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    //"org.scala-lang" % "scala-reflect" % scalaVersion.value,
    //"org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "eu.timepit" %% "singleton-ops" % "0.2.1",
    //"com.chuusai" %% "shapeless" % "2.3.2",
    "org.typelevel" %% "spire-macros" % "0.14.0",
    "org.typelevel" %% "spire" % "0.14.0",
    "org.scalatest" %% "scalatest" % "3.0.1" % Test
  ),
  //addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt"))

lazy val coulomb = (project in file("."))
  .aggregate(unitexpr, macros)
  .dependsOn(unitexpr, macros)
  .settings(name := "coulomb")
  .settings(commonSettings :_*)

lazy val macros = (project in file("macros"))
  .dependsOn(unitexpr)
  .settings(name := "coulomb-macros",
            test := {},
            testOnly := {})
  .settings(commonSettings :_*)

lazy val unitexpr = (project in file("unitexpr"))
  .settings(name := "coulomb-unitexpr",
            test := {},
            testOnly := {})
  .settings(commonSettings :_*)

lazy val examples = (project in file("examples"))
  .dependsOn(coulomb)
  .settings(name := "coulomb-examples")
  .settings(commonSettings :_*)
  .settings(libraryDependencies += "com.typesafe" % "config" % "1.3.1")

enablePlugins(ScalaUnidocPlugin, GhpagesPlugin)

siteSubdirName in ScalaUnidoc := "latest/api"

addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc)

git.remoteRepo := "git@github.com:erikerlandson/coulomb.git"
