name := """hw4"""

version := "1.0-SNAPSHOT"

lazy val hw4 = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.194",
  "org.slf4j" % "slf4j-api" % "1.7.24",
  "com.google.guava" % "guava" % "21.0",
  "org.slf4j" % "slf4j-simple" % "1.7.24",
  "com.google.code.gson" % "gson" % "2.8.0",
  "org.apache.httpcomponents" % "httpclient" % "4.5.3"
)
