
import akka.Done
import akka.actor._
import akka.http.scaladsl._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, MediaRanges}
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, ProducerSettings, Subscriptions}
import akka.stream._
import akka.stream.scaladsl.{Keep, Sink, Source}
import akka.util.ByteString
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import spray.json.{DefaultJsonProtocol, JsValue, JsonWriter}

import scala.concurrent.Future

object Main
  extends App
    with DefaultJsonProtocol {

  implicit val actorSystem = ActorSystem("alpakka-samples")

  import actorSystem.dispatcher

  implicit val mat: Materializer = ActorMaterializer()
  val alphavantage = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=AAPL&interval=1min&apikey=06NCM8Z6D0E5CNQ6&outputsize=full"

  val httpRequest = HttpRequest(uri = alphavantage)
    .withHeaders(Accept(MediaRanges.`text/*`))

  def extractEntityData(response: HttpResponse): Source[ByteString, _] =
    response match {
      case HttpResponse(OK, _, entity, _) => entity.dataBytes
      case notOkResponse =>
        Source.failed(new RuntimeException(s"illegal response $notOkResponse"))
    }

  def cleanseCsvData(csvData: Map[String, ByteString]): Map[String, String] =
    csvData
      .filterNot { case (key, _) => key.isEmpty }
      .mapValues(_.utf8String)

  def toJson(map: Map[String, String])(
    implicit jsWriter: JsonWriter[Map[String, String]]): JsValue = jsWriter.write(map)

  private val bootstrapServers: String = "localhost:9092"
  val kafkaProducerSettings = ProducerSettings(actorSystem, new StringSerializer, new StringSerializer)
    .withBootstrapServers(bootstrapServers)


  val future: Future[Done] =
    Source
      .single(httpRequest) //: HttpRequest
      .mapAsync(1)(Http().singleRequest(_)) //: HttpResponse
      .flatMapConcat(extractEntityData)
      .map { elem =>
        new ProducerRecord[String, String]("topic1", elem.toString()) //: Kafka ProducerRecord
      }
      .runWith((akka.kafka.scaladsl.Producer).plainSink(kafkaProducerSettings))

  val cs: CoordinatedShutdown = CoordinatedShutdown(actorSystem)
  cs.addTask(CoordinatedShutdown.PhaseServiceStop, "shut-down-client-http-pool")(() =>
    Http().shutdownAllConnectionPools().map(_ => Done)
  )

  val kafkaConsumerSettings = ConsumerSettings(actorSystem, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers(bootstrapServers)
    .withGroupId("topic1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val control = Consumer
    .atMostOnceSource(kafkaConsumerSettings, Subscriptions.topics("topic1"))
    .map(_.value)
    .toMat(Sink.foreach(println))(Keep.both)
    .mapMaterializedValue(Consumer.DrainingControl.apply)
    .run()

  for {
    _ <- future
    _ <- control.drainAndShutdown()
  } {
    cs.run(CoordinatedShutdown.UnknownReason)
  }
}