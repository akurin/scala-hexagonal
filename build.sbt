name := "hexagonal"
organization := "akurin"
version := "1.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-feature", "-deprecation")
parallelExecution := false

libraryDependencies ++= {
  val akkaVersion = "2.4.6"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
    "com.softwaremill.macwire" %% "macros" % "2.2.3" % "provided",
    "org.scalactic" %% "scalactic" % "2.2.6",
    "com.typesafe.akka" %% "akka-http-testkit" % akkaVersion,
    "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  )
}

enablePlugins(JavaAppPackaging)