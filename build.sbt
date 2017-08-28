sbtPlugin := true

name := "avro-generator"

organization := "local"

version := "0.0.1"

// sbt 0.13.x series uses Scala 2.10.x when it loads up, so sbt 0.13.x itself must be compiled against Scala 2.10,
// and so do all sbt plugins for 0.13.x.
scalaVersion := "2.10.4"

offline := true

//If having problems try disable this
//publishMavenStyle := false

// junit
libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12",
  "com.novocode" % "junit-interface" % "0.10" % "test"
)

// avro tool
libraryDependencies ++= Seq(
  "org.apache.avro" % "avro" % "1.7.7",
  "org.apache.avro" % "avro-tools" % "1.7.7"
)

libraryDependencies ++= Seq(
  "com.googlecode.json-simple" % "json-simple" % "1.1",
  "com.cedarsoftware" % "json-io" % "4.10.0"
)