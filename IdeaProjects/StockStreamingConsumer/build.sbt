
import sbt.Keys._

name := "StockStreamingConsumer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.4"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.4"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.4"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.3.1"


// https://mvnrepository.com/artifact/org.apache.httpcomponents/fluent-hc
libraryDependencies += "org.apache.httpcomponents" % "fluent-hc" % "4.5.5"
// https://mvnrepository.com/artifact/io.spray/spray-json
libraryDependencies += "io.spray" %% "spray-json" % "1.3.4"
// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

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

