import sbt.Keys._

name := "StockProducer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.4"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.4"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.4"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.3.1"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"


// https://mvnrepository.com/artifact/org.apache.httpcomponents/fluent-hc
libraryDependencies += "org.apache.httpcomponents" % "fluent-hc" % "4.5.5"
// https://mvnrepository.com/artifact/io.spray/spray-json
libraryDependencies += "io.spray" %% "spray-json" % "1.3.4"
// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies +=  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.4"

libraryDependencies ++= deps
lazy val deps = {
  val akkaV = "2.5.3"
  Seq(
    "com.typesafe.akka"       %% "akka-actor"                 % akkaV,
    "com.typesafe.akka"       %% "akka-stream"                % akkaV,
    "com.typesafe.akka"       %% "akka-http"                  % "10.0.9",
    "com.typesafe.play"       %% "play-json"                  % akkaV,
    "de.heikoseeberger"       %% "akka-http-play-json"        % "1.17.0"
  )
}

libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.10"
libraryDependencies += "com.ning" % "async-http-client" % "1.9.31"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.1"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka-testkit" % "1.0-RC1"
libraryDependencies += "org.testcontainers" % "testcontainers" % "1.12.0"