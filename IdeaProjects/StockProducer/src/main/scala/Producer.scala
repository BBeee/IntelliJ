import java.util.concurrent.LinkedBlockingQueue

import akka.actor.ActorSystem
import akka.actor.Status.Status
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}

import scala.concurrent.ExecutionContext.Implicits.global

object Producer extends App {

  override def main(args: Array[String]) {
    val queue = new LinkedBlockingQueue[Status](10)

    val topicName = "Bill"
    val keyword = "Atlanta"

    implicit val decider: Supervision.Decider = {
      case _ => Supervision.Resume
    }
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer = ActorMaterializer(ActorMaterializerSettings(system).withSupervisionStrategy(decider))

    try {
      //Main
      val API_KEY = "PMGX9ASKF5L4PW7E"
      val api = new AlphaVantageApi(API_KEY)
      //val test1 = api.minute("AAPL")
      //val test2 = test1.map((e => e.`Time Series`.series.seq))
      val fx2 = api.minute("AAPL")
      fx2.foreach { x =>
        x.`Time Series`.series.seq.foreach(println)
      }


      //
      //      //println("calling Minute")
      //      //      val fx2 = api.minute("AAPL") //List(MetaData, TimeSeries)
      //      //      fx2.foreach { x =>
      //      //        //println(s"GOT IT: ${x}")
      //      //        val temp = (x.`Time Series`.series.foreach(println))
      //      //      }
      //      //Thread.sleep(1000)
      //
      //      //Start with Kafka
      //      //kafka Properties
      //      val config = ConfigFactory.load.getConfig("akka.kafka.producer")
      //      val producerSettings =
      //        ProducerSettings(config, new StringSerializer, new StringSerializer)
      //          .withBootstrapServers("localhost:9092")
      //
      ////      val test1 = api.minute("AAPL")
      ////      val test2 = test1.map((e => e.`Time Series`.series.seq))
      ////      test2.map(_.toString().map(
      ////        value => new ProducerRecord[String, String](topicName, "Record:: " + value)
      ////      ).runWith((akka.kafka.scaladsl.Producer).plainSink(producerSettings))
      ////      )
      //
      ////          val alphavantage = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=AAPL&interval=1min&apikey=06NCM8Z6D0E5CNQ6&outputsize=full"
      ////          val source = Uri(alphavantage)
      ////
      ////          val finished = Http().singleRequest(HttpRequest(uri = source)).flatMap { response =>
      ////            response.entity.dataBytes.runWith((akka.kafka.scaladsl.Producer).plainSink(producerSettings))
      ////          }
      //
      ////          Source.repeat(finished).runFold()

      Source.repeat(fx2)
      Thread.currentThread.join()
    } catch {
      case t: Throwable =>
        t.printStackTrace()


    }
  }

}
