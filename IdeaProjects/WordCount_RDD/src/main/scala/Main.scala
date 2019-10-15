import org.apache.spark._

object Main {
  def main(args : Array[String]): Unit = {

    //Spark setting up
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val sc = new SparkContext(conf)

    //Change log level to WARN from INFO
    sc.setLogLevel("WARN")


    // Read file into RDD
    val filename = "/home/hadoop/Downloads/samples.txt"
    val lines = sc.textFile(filename)

    val counts = Map.mapper(lines)

    counts.saveAsTextFile( "/home/hadoop/Downloads/wordcountoutput3")


  }

}
