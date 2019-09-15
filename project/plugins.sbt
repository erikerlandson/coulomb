resolvers += Resolver.url(
  "bintray-sbt-plugin-releases",
    url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
        Resolver.ivyStylePatterns)

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")

addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.1")

//addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.2.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "1.3.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.2")

// I'm disabling these for now, since I'm not using them yet and they aren't all synced
// up with scala 2.12

//addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")

// scoverage and coveralls deps are at old versions to avoid a bug in the current versions
// update these when this fix is released:  https://github.com/scoverage/sbt-coveralls/issues/73
//addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.4")

//addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0")
