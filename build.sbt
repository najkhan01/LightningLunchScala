name := "LightningLunch"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("com.najkhan.lightlunch")

val http4sVersion = "1.0.0-M2"
val circeVersion = "0.14.0"

libraryDependencies ++= Seq(
  // https://mvnrepository.com/artifact/org.http4s/http4s-blaze-server
  "org.postgresql" % "postgresql" % "42.2.19",
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "3.0.4",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.14",
  "com.typesafe.akka" %% "akka-stream" % "2.6.14",
  "com.typesafe.akka" %% "akka-http" % "10.2.6",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.6",
  "com.zaxxer" % "HikariCP" % "3.4.5"
)
