name := "unit4s"

lazy val commonSettings = Seq(
  organization := "com.manyangled",
  version := "0.0.1",
  scalaVersion := "2.11.6",
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % Test,
    "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3",
    "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"
  ),
  licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0")),
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
  scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt"),
  fork := true
)

seq(commonSettings: _*)

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.1.0"
)

lazy val unit4s = project in file(".")

lazy val codegen = project
  .settings(commonSettings :_*)
  .settings(
    initialCommands in console := """
      |import com.manyangled.codegen._
    """.stripMargin
  )
