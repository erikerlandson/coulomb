// xsbt clean unidoc previewSite
// xsbt clean unidoc ghpagesPushSite

def commonSettings = Seq(
  organization := "com.manyangled",
  version := "0.3.7-SNAPSHOT",
  scalaVersion := "2.13.0",
  crossScalaVersions := Seq("2.13.0"),
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.typelevel" %% "spire" % "0.17.0-M1" % Provided,
    "eu.timepit" %% "singleton-ops" % "0.4.3" % Provided,
    "org.scalatest" %% "scalatest" % "3.1.0" % Test
  ),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt"))

def docDepSettings = Seq(
  previewSite := {}
)

lazy val coulomb = (project in file("coulomb"))
  .settings(name := "coulomb")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_si_units = (project in file("coulomb-si-units"))
  .aggregate(coulomb)
  .dependsOn(coulomb)
  .settings(name := "coulomb-si-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_temp_units = (project in file("coulomb-temp-units"))
  .aggregate(coulomb, coulomb_si_units)
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-temp-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_mks_units = (project in file("coulomb-mks-units"))
  .aggregate(coulomb, coulomb_si_units)
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-mks-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_accepted_units = (project in file("coulomb-accepted-units"))
  .aggregate(coulomb, coulomb_si_units)
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-accepted-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_time_units = (project in file("coulomb-time-units"))
  .aggregate(coulomb, coulomb_si_units)
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-time-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_info_units = (project in file("coulomb-info-units"))
  .aggregate(coulomb, coulomb_si_units)
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-info-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_customary_units = (project in file("coulomb-customary-units"))
  .aggregate(coulomb, coulomb_si_units)
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-customary-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

def coulombParserDeps = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2" % Provided
)

lazy val coulomb_parser = (project in file("coulomb-parser"))
  .aggregate(coulomb)
  .dependsOn(coulomb)
  .settings(name := "coulomb-parser")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombParserDeps)

def coulombTypesafeConfigDeps = Seq(
  "com.typesafe" % "config" % "1.4.0" % Provided
)

lazy val coulomb_typesafe_config = (project in file("coulomb-typesafe-config"))
  .aggregate(coulomb, coulomb_parser)
  .dependsOn(coulomb, coulomb_parser)
  .settings(name := "coulomb-typesafe-config")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombTypesafeConfigDeps)

def coulombAvroDeps = Seq(
  "org.apache.avro" % "avro" % "1.9.2" % Provided
)

lazy val coulomb_avro = (project in file("coulomb-avro"))
  .aggregate(coulomb, coulomb_parser)
  .dependsOn(coulomb, coulomb_parser)
  .settings(name := "coulomb-avro")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombAvroDeps)

def coulombPureConfigDeps = Seq(
  "com.github.pureconfig" %% "pureconfig" % "0.12.2" % Provided
)

lazy val coulomb_pureconfig = (project in file("coulomb-pureconfig"))
  .aggregate(coulomb, coulomb_parser)
  .dependsOn(coulomb, coulomb_parser)
  .settings(name := "coulomb-pureconfig")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombPureConfigDeps)

lazy val coulomb_tests = (project in file("coulomb-tests"))
  .aggregate(coulomb, coulomb_si_units, coulomb_mks_units, coulomb_accepted_units, coulomb_time_units, coulomb_info_units, coulomb_customary_units, coulomb_temp_units, coulomb_parser, coulomb_typesafe_config, coulomb_avro, coulomb_pureconfig)
  .dependsOn(coulomb, coulomb_si_units, coulomb_mks_units, coulomb_accepted_units, coulomb_time_units, coulomb_info_units, coulomb_customary_units, coulomb_temp_units, coulomb_parser, coulomb_typesafe_config, coulomb_avro, coulomb_pureconfig)
  .settings(name := "coulomb-tests")
  .settings(commonSettings :_*)
  .settings(libraryDependencies ++= coulombAvroDeps)
  .settings(libraryDependencies ++= coulombTypesafeConfigDeps)
  .settings(libraryDependencies ++= coulombParserDeps)
  .settings(libraryDependencies ++= coulombPureConfigDeps)

lazy val coulomb_docs = (project in file("."))
  .aggregate(coulomb, coulomb_si_units, coulomb_mks_units, coulomb_accepted_units, coulomb_time_units, coulomb_info_units, coulomb_customary_units, coulomb_temp_units, coulomb_parser, coulomb_typesafe_config, coulomb_avro, coulomb_pureconfig)
  .dependsOn(coulomb, coulomb_si_units, coulomb_mks_units, coulomb_accepted_units, coulomb_time_units, coulomb_info_units, coulomb_customary_units, coulomb_temp_units, coulomb_parser, coulomb_typesafe_config, coulomb_avro, coulomb_pureconfig)
  .settings(name := "coulomb-docs")
  .settings(commonSettings :_*)

enablePlugins(ScalaUnidocPlugin, GhpagesPlugin)

siteSubdirName in ScalaUnidoc := "latest/api"

addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc)

git.remoteRepo := "git@github.com:erikerlandson/coulomb.git"
