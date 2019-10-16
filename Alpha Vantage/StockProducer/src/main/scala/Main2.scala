
//import org.testcontainers.containers.KafkaContainer
import spray.json.DefaultJsonProtocol

object Main2
  extends App
    with DefaultJsonProtocol {

//  implicit val actorSystem = ActorSystem("alpakka-samples")
//
//  import actorSystem.dispatcher
//
//  implicit val mat: Materializer = ActorMaterializer()
//
//  val httpRequest = HttpRequest(uri = "https://www.nasdaq.com/screening/companies-by-name.aspx?exchange=NASDAQ&render=download")
//    .withHeaders(Accept(MediaRanges.`text/*`))
//  def extractEntityData(response: HttpResponse): Source[ByteString, _] =
//    response match {
//      case HttpResponse(OK, _, entity, _) => entity.dataBytes
//      case notOkResponse =>
//        Source.failed(new RuntimeException(s"illegal response $notOkResponse"))
//    }
//  def cleanseCsvData(csvData: Map[String, ByteString]): Map[String, String] =
//    csvData
//      .filterNot { case (key, _) => key.isEmpty }
//      .mapValues(_.utf8String)
//  def toJson(map: Map[String, String])(
//    implicit jsWriter: JsonWriter[Map[String, String]]): JsValue = jsWriter.write(map)
//
//
//  private val bootstrapServers: String = "local:9092"
//  val kafkaProducerSettings = ProducerSettings(actorSystem, new StringSerializer, new StringSerializer)
//    .withBootstrapServers(bootstrapServers)
//  val future: Future[Done] =
//    Source
//      .single(httpRequest) //: HttpRequest
//      .mapAsync(1)(Http().singleRequest(_)) //: HttpResponse
//      .flatMapConcat(extractEntityData) //: ByteString
//      .via(CsvParsing.lineScanner()) //: List[ByteString]
//      .via(CsvToMap.toMap()) //: Map[String, ByteString]
//      .map(cleanseCsvData) //: Map[String, String]
//      .map(toJson) //: JsValue
//      .map(_.compactPrint) //: String (JSON formatted)
//      .map { elem =>
//        new ProducerRecord[String, String]("topic1", elem) //: Kafka ProducerRecord
//      }
//      .runWith((akka.kafka.scaladsl.Producer).plainSink(kafkaProducerSettings))
//  val cs: CoordinatedShutdown = CoordinatedShutdown(actorSystem)
//  cs.addTask(CoordinatedShutdown.PhaseServiceStop, "shut-down-client-http-pool")( () =>
//    Http().shutdownAllConnectionPools().map(_ => Done)
//  )
//  val kafkaConsumerSettings = ConsumerSettings(actorSystem, new StringDeserializer, new StringDeserializer)
//    .withBootstrapServers(bootstrapServers)
//    .withGroupId("topic1")
//    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
//  val control = Consumer
//    .atMostOnceSource(kafkaConsumerSettings, Subscriptions.topics("topic1"))
//    .map(_.value)
//    .toMat(Sink.foreach(println))(Keep.both)
//    .mapMaterializedValue(Consumer.DrainingControl.apply)
//    .run()
//  for {
//    _ <- future
//    _ <- control.drainAndShutdown()
//  } {
//    cs.run(CoordinatedShutdown.UnknownReason)
//  }
}