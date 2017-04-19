name := """hw5"""

version := "1.0-SNAPSHOT"

lazy val hw5 = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.8"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.0.0",
  "com.google.code.gson" % "gson" % "2.8.0",
  "org.apache.httpcomponents" % "httpclient" % "4.5.3"
)
