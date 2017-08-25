sbtPlugin := true

name := "avro-generator"

organization := "local"

version := "0.0.1"

scalaVersion := "2.11.7"

//offline := true

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