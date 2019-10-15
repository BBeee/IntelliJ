name := "WordCount"

version := "0.1"

scalaVersion := "2.11.8"


val sparkVersion = "2.2.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0",
  "org.apache.spark" %% "spark-sql" % sparkVersion
)