import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils}
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaConsumer {
  def main(args: Array[String]): Unit = {

    //We need batch processing configuration
    val conf = new SparkConf().setAppName("Twitter-Sentiment").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val sc = ssc.sparkContext
    sc.setLogLevel("ERROR")

    val topics = List("Good").toSet // You have two topics; "israel" and "manny"

    val kafkaParams = Map( //Parameters to connect to Kafka
      //"bootstrap.servers" -> "172.168.21.243:9092", //This's Al's IP.
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "spark-streaming-something",
      "auto.offset.reset" -> "earliest"
    )

    val line = KafkaUtils.createDirectStream[String, String](
      //Parameter for Streaming Context
      ssc,
      PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )

    val tmp = line.map(record => record.value().toString)
    val _line = tmp.flatMap(_.split(",")) // This _line is Dstream

    //how to filter?
    //val fuc=_line.filter( bw => bw.contains("fuck"))

    val sentiment = new SentimentAnalyzer()
    var total = Array(0,0,0)
    _line.foreachRDD((x => {
      var neg = 0
      var pos = 0
      var neu = 0
      for (item <- x.collect()) {
        val output = (sentiment.mainSentiment2(item))
        if (output == Sentiment.NEGATIVE) neg += 1
        else if (output == Sentiment.POSITIVE) pos += 1
        else neu += 1
      }

      // Below commented code Performance BAD
      //val str = x.collect().mkString(" ")
      //println("-------------------------------------------")
      //println(str)
      //val output = sentiment.mainSentiment2(str)

      val temp = Map("NEUTRAL" -> neu, "POSITIVE" -> pos, "NEGATIVE" -> neg)
      val output = temp.maxBy { case (key, value) => value }._1
      println("Tweet--------------------------------------")
      x.foreach(println)
      println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
      println("RESULT : " + output + "\n")
      println("Current Tweet Score")
      println("NEUTRAL  = " + neu)
      println("POSITIVE = " + pos)
      println("NEGATIVE = " + neg)
      println("Total Score---------------------------------")
      if (output == "NEGATIVE") total(2) += 1
      else if (output == "POSITIVE") total(1) += 1
      else total(0) += 1
      println("NEUTRAL : " + total(0) + "     POSITIVE: " + total(1) + "     NEGATIVE: " + total(2))
      println("--------------------------------------------\n\n\n")


    }))

    ssc.start()
    ssc.awaitTermination

  }
}
