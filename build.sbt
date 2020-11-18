// preview and publish docs
// sbt clean unidoc previewSite
// sbt clean unidoc ghpagesPushSite
// unit tests
// sbt coulomb_testsJVM/test
// sbt coulomb_testsJS/test
// update version in README
// git sed 's/0\.4\.6/0.5.0/'
// review sonatype here: https://oss.sonatype.org/#stagingRepositories

Global / onChangedBuildSource := ReloadOnSourceChanges

def commonSettings = Seq(
  //---------------------------------
  // publishing configurations
  //---------------------------------
  organization := "com.manyangled",
  version := "0.5.6-SNAPSHOT",
  //isSnapshot := true,
  //publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  scalaVersion := "2.13.3",
  crossScalaVersions := Seq("2.13.3"),
  pomIncludeRepository := { _ => false },
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
  homepage := Some(url("https://github.com/erikerlandson/coulomb")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/erikerlandson/coulomb"),
      "scm:git@github.com:erikerlandson/coulomb.git"
    )
  ),
  developers := List(
    Developer(
      id    = "erikerlandson",
      name  = "Erik Erlandson",
      email = "eje@redhat.com",
      url   = url("https://erikerlandson.github.io/")
    )
  ),
  //---------------------------------
  // build configurations
  //---------------------------------
  addCompilerPlugin("org.typelevel" % "kind-projector_2.13.3" % "0.11.0"),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "spire" % "0.17.0" % Provided,
    "eu.timepit" %%% "singleton-ops" % "0.5.2" % Provided,
    "org.scalameta" %%% "munit" % "0.7.18" % Test,
    "org.typelevel" %%% "discipline-munit" % "1.0.2" % Test,
    "org.scalameta" %%% "munit-scalacheck" % "0.7.18" % Test,
  ),
  testFrameworks += new TestFramework("munit.Framework"),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt")
)

// prevents previewSite from trying to run on all packages simultaneously
def docDepSettings = Seq(
  previewSite := {}
)

lazy val coulomb = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("coulomb"))
  .settings(name := "coulomb")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_si_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-si-units"))
  .dependsOn(coulomb)
  .settings(name := "coulomb-si-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_temp_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-temp-units"))
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-temp-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_mks_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-mks-units"))
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-mks-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_accepted_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-accepted-units"))
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-accepted-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_time_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-time-units"))
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-time-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_info_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-info-units"))
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-info-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_customary_units = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-customary-units"))
  .dependsOn(coulomb, coulomb_si_units)
  .settings(name := "coulomb-customary-units")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

lazy val coulomb_physical_constants = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-physical-constants"))
  .dependsOn(coulomb, coulomb_si_units, coulomb_mks_units)
  .settings(name := "coulomb-physical-constants")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)

def coulombParserDeps = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2" % Provided
)

lazy val coulomb_parser = (project in file("coulomb-parser"))
  .dependsOn(coulomb.jvm)
  .settings(name := "coulomb-parser")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombParserDeps)

def coulombTypesafeConfigDeps = Seq(
  "com.typesafe" % "config" % "1.4.1" % Provided
)

lazy val coulomb_typesafe_config = (project in file("coulomb-typesafe-config"))
  .dependsOn(coulomb.jvm, coulomb_parser)
  .settings(name := "coulomb-typesafe-config")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombTypesafeConfigDeps)

def coulombAvroDeps = Seq(
  "org.apache.avro" % "avro" % "1.10.0" % Provided
)

lazy val coulomb_avro = (project in file("coulomb-avro"))
  .dependsOn(coulomb.jvm, coulomb_parser)
  .settings(name := "coulomb-avro")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombAvroDeps)

def coulombPureConfigDeps = Seq(
  "com.github.pureconfig" %% "pureconfig-core" % "0.14.0" % Provided,
  "com.github.pureconfig" %% "pureconfig-generic" % "0.14.0" % Provided,
)

def coulombCatsDeps = Def.setting(Seq(
  "org.typelevel" %%% "cats-core" % "2.2.0" % Provided,
  "org.typelevel" %%% "cats-testkit" % "2.2.0" % Provided,
  "org.scalacheck" %%% "scalacheck" % "1.15.1" % Provided
    // "org.typelevel" %%% "discipline-core" % "1.1.2" % Test
))

lazy val coulomb_cats = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-cats"))
  .dependsOn(coulomb)
  .settings(name := "coulomb-cats")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombCatsDeps.value)

lazy val coulomb_scalacheck = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-scalacheck"))
  .dependsOn(coulomb, coulomb_si_units, coulomb_cats)
  .settings(name := "coulomb-scalacheck")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombCatsDeps.value)

lazy val coulomb_pureconfig = (project in file("coulomb-pureconfig"))
  .dependsOn(coulomb.jvm, coulomb_parser)
  .settings(name := "coulomb-pureconfig")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombPureConfigDeps)

def coulombRefinedDeps = Def.setting(Seq(
  "eu.timepit" %%% "refined" % "0.9.18" % Provided)
)

lazy val coulomb_refined = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("coulomb-refined"))
  .dependsOn(coulomb)
  .settings(name := "coulomb-refined")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombRefinedDeps.value)

lazy val coulomb_pureconfig_refined = (project in file("coulomb-pureconfig-refined"))
  .dependsOn(coulomb.jvm, coulomb_parser, coulomb_pureconfig, coulomb_refined.jvm)
  .settings(name := "coulomb-pureconfig-refined")
  .settings(commonSettings :_*)
  .settings(docDepSettings :_*)
  .settings(libraryDependencies ++= coulombPureConfigDeps)
  .settings(libraryDependencies ++= coulombRefinedDeps.value)

def javaTimeJSDeps = Def.setting(Seq(
  "io.github.cquiroz" %%% "scala-java-time" % "2.0.0" % Provided)
)

lazy val coulomb_tests = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .in(file("coulomb-tests"))
  .dependsOn(coulomb, coulomb_si_units, coulomb_mks_units, coulomb_accepted_units, coulomb_time_units, coulomb_info_units, coulomb_customary_units, coulomb_temp_units, coulomb_refined, coulomb_cats, coulomb_scalacheck, coulomb_physical_constants)
  .settings(name := "coulomb-tests")
  .settings(commonSettings :_*)
  .settings(publish := {})
  .settings(publishLocal := {})
  .settings(libraryDependencies ++= coulombParserDeps)
  .settings(libraryDependencies ++= coulombTypesafeConfigDeps)
  .settings(libraryDependencies ++= coulombAvroDeps)
  .settings(libraryDependencies ++= coulombRefinedDeps.value)
  .settings(libraryDependencies ++= coulombPureConfigDeps)
  .settings(libraryDependencies ++= coulombCatsDeps.value)
  .jvmConfigure(_.dependsOn(coulomb_avro))
  .jvmConfigure(_.dependsOn(coulomb_typesafe_config))
  .jvmConfigure(_.dependsOn(coulomb_parser))
  .jvmConfigure(_.dependsOn(coulomb_pureconfig))
  .jvmConfigure(_.dependsOn(coulomb_pureconfig_refined))
  .jsSettings(
    libraryDependencies ++= javaTimeJSDeps.value,
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )

// I define this mostly to support 'publish' and 'unidoc'
lazy val coulomb_root = (project in file("."))
  .dependsOn(
    coulomb.jvm, coulomb_si_units.jvm, coulomb_mks_units.jvm, coulomb_accepted_units.jvm, coulomb_time_units.jvm, coulomb_info_units.jvm, coulomb_customary_units.jvm, coulomb_temp_units.jvm, coulomb_refined.jvm, coulomb_cats.jvm, coulomb_scalacheck.jvm,
    coulomb.js, coulomb_si_units.js, coulomb_mks_units.js, coulomb_accepted_units.js, coulomb_time_units.js, coulomb_info_units.js, coulomb_customary_units.js, coulomb_temp_units.js, coulomb_refined.js, coulomb_cats.js, coulomb_scalacheck.js,
    coulomb_parser, coulomb_typesafe_config, coulomb_avro, coulomb_pureconfig, coulomb_pureconfig_refined, coulomb_physical_constants.jvm, coulomb_physical_constants.js
  )
  .aggregate(
    coulomb.jvm, coulomb_si_units.jvm, coulomb_mks_units.jvm, coulomb_accepted_units.jvm, coulomb_time_units.jvm, coulomb_info_units.jvm, coulomb_customary_units.jvm, coulomb_temp_units.jvm, coulomb_refined.jvm, coulomb_cats.jvm, coulomb_scalacheck.jvm,
    coulomb.js, coulomb_si_units.js, coulomb_mks_units.js, coulomb_accepted_units.js, coulomb_time_units.js, coulomb_info_units.js, coulomb_customary_units.js, coulomb_temp_units.js, coulomb_refined.js, coulomb_cats.js, coulomb_scalacheck.js,
    coulomb_parser, coulomb_typesafe_config, coulomb_avro, coulomb_pureconfig, coulomb_pureconfig_refined, coulomb_physical_constants.jvm, coulomb_physical_constants.js
  )
  .settings(
    // unidoc needs to be told explicitly to ignore JS projects
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(coulomb.js, coulomb_si_units.js, coulomb_mks_units.js, coulomb_accepted_units.js, coulomb_time_units.js, coulomb_info_units.js, coulomb_customary_units.js, coulomb_temp_units.js, coulomb_refined.js, coulomb_cats.js, coulomb_scalacheck.js, coulomb_physical_constants.js))
  .settings(name := "coulomb-root")
  .settings(commonSettings :_*)
  .settings(publish := {})
  .settings(publishLocal := {})

enablePlugins(ScalaUnidocPlugin, GhpagesPlugin)

siteSubdirName in ScalaUnidoc := "latest/api"

addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc)

git.remoteRepo := "git@github.com:erikerlandson/coulomb.git"
