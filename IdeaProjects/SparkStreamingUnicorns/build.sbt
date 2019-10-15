name := "SparkStreamingUnicorns"

version := "0.1"

scalaVersion := "2.11.8"


libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.6"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.6.0"



//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.3.4"
//libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.4" % "provided"
//provide means "Spark is provided in this local system"

//libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.4"
