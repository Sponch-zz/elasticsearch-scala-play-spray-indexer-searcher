name := """indexer-movie-script"""

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "repo.codahale.com" at "http://repo.codahale.com"
resolvers += "repo.maven.com" at "http://repo1.maven.org/maven2/"



// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "org.mockito" % "mockito-all" % "1.8.4" % "test"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
libraryDependencies += "com.sksamuel.elastic4s" %% "elastic4s-streams" % "2.2.1"
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.7"
libraryDependencies += "com.googlecode.json-simple" % "json-simple" % "1.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.0"
libraryDependencies += "com.lexicalscope.jewelcli" % "jewelcli" % "0.8.9"
libraryDependencies += "biz.neumann" % "nice-uuid_2.11" % "1.1"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
libraryDependencies += "ch.qos.logback" %  "logback-classic" % "1.1.6"


// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

 


exportJars := true

fork in run := true