name := "Flink"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.flink" % "flink-core" % "1.7.0"
libraryDependencies += "org.apache.flink" %% "flink-scala" % "1.7.0"
libraryDependencies += "org.apache.flink" %% "flink-streaming-scala" % "1.7.0"
libraryDependencies += "org.apache.flink" %% "flink-table" % "1.7.0"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.0"