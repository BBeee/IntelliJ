name := "Cassandra"

version := "0.1"

scalaVersion := "2.11.8"


libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.3.2"

val sparkVersion = "2.2.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0",
  "org.apache.spark" %% "spark-sql" % sparkVersion
)