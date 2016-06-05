organization  := "com.search.movies"

version       := "0.1"

scalaVersion  := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "io.spray"            %%  "spray-json"    % "1.3.2",
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    
    "com.sksamuel.elastic4s" %% "elastic4s-streams" % "2.2.1",
    "com.sksamuel.elastic4s" %% "elastic4s-jackson" % "2.2.1",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "ch.qos.logback" %  "logback-classic" % "1.1.6"
  )
}

Revolver.settings
