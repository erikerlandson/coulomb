resolvers ++= Resolver.sonatypeOssRepos("snapshots")

val sbtTypelevelVersion = "0.4.19"

addSbtPlugin("org.typelevel" % "sbt-typelevel" % sbtTypelevelVersion)
addSbtPlugin("org.typelevel" % "sbt-typelevel-site" % sbtTypelevelVersion)

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.13.0")

addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.4.11")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.2.0")

addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.4.4")

// snapshot to pick up bug fix
// https://github.com/scalameta/mdoc/pull/664
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.3.7")
