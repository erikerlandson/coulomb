resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"

addSbtPlugin("io.crashbox" % "sbt-gpg" % "0.2.1")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.15")

addSbtPlugin("com.github.sbt" % "sbt-unidoc" % "0.5.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.4.1")
addSbtPlugin("com.github.sbt" % "sbt-ghpages" % "0.7.0")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.2.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.2.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.13.0")
