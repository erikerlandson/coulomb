name := "unit4s"

organization := "com.manyangled"

version := "0.0.1"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.1.0"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % Test

licenses += ("Apache-2.0", url("http://opensource.org/licenses/Apache-2.0"))

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

scalacOptions in (Compile, doc) ++= Seq("-doc-root-content", baseDirectory.value+"/root-doc.txt")

fork := true
