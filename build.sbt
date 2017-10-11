organization := "io.github.mrdziuban"
name := "http4s-gzip-test"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

val Http4sVersion = "0.17.0-M2"

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"     %% "http4s-circe"        % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
  "ch.qos.logback" %  "logback-classic"     % "1.2.1"
)
