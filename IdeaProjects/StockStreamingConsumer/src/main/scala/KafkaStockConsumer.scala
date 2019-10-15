import java.util.Properties

import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
//import akka.actor.ActorSystem
import akka.actor.ActorSystem
import org.apache.http.client.fluent.Request
import org.apache.kafka.clients.producer.KafkaProducer

import scala.util.Try


object KafkaStockConsumer extends App {

   override def main(args: Array[String]): Unit = {

    val decider: Supervision.Decider = {
     case _ => Supervision.Resume
    }
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system).withSupervisionStrategy(decider))


    //Alphavantage API connecting using http request
    val baseUrl = "https://www.alphavantage.co/query"
    val symbol = "AAPL"

    val key = "06NCM8Z6D0E5CNQ6"
    val requestTypeURL = "TIME_SERIES_INTRADAY"
    val interval="1min"
    val outputsize="full"

    val result = Try(
      Request
        .Get(s"$baseUrl?function=$requestTypeURL&symbol=$symbol&interval=$interval&apikey=$key&outputsize=$outputsize")
        .execute()
        .returnContent()
        .asString())

//    try{
//     val result =
//     Request
//       .Get(s"$baseUrl?function=$requestTypeURL&symbol=$symbol&interval=$interval&apikey=$key&outputsize=$outputsize")
//       .execute()
//       .returnContent()
//       .asString()
//
//     result.foreach(println)
//    }

    Thread.currentThread.join()

    val topicName = "Good"
    val keyword = "Good"


    //Start with Kafka
    //kafka Properties
    val properties = new Properties()
    properties.put("metadata.broker.list","localhost:9092")
    properties.put("bootstrap.servers","localhost:9092")
    properties.put("ack","all")
    properties.put("retries","0")
    properties.put("batch.size","16384")
    properties.put("linger.ms","1")
    properties.put("buffer.memory","33554432")
    properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](properties)

    var count: Int = 0



  }

}