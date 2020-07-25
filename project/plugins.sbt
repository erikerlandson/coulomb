resolvers += Resolver.url(
  "bintray-sbt-plugin-releases",
    url("https://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
        Resolver.ivyStylePatterns)

// resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "jgit-repo" at "https://download.eclipse.org/jgit/maven"

addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.6")

addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.4.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.3")

// I'm disabling these for now, since I'm not using them yet and they aren't all synced
// up with scala 2.12

//addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")

// scoverage and coveralls deps are at old versions to avoid a bug in the current versions
// update these when this fix is released:  https://github.com/scoverage/sbt-coveralls/issues/73
//addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.4")

//addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject"      % "1.0.0")
addSbtPlugin("org.portable-scala" % "sbt-scala-native-crossproject" % "1.0.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"                   % "1.1.1")
