import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Main {
  def main(args: Array[String]): Unit = {

    //We need batch processing configuration
    val conf = new SparkConf().setAppName("Twitter-Cassandra").setMaster("local[*]")
      .set("spark.cassandra.connection.host", "localhost") // for cassandra connection

    val ssc = new StreamingContext(conf, Seconds(5))
    val sc = ssc.sparkContext
    sc.setLogLevel("ERROR")

    val topics = List("Cassandra").toSet // You have two topics; "israel" and "manny"

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

    // Push this line to Cassandra
    // Cassandra Configuration
    val c = CassandraConnector(sc.getConf)

    val keyspace = "twitter"
    val table = "tweet"
    val createTableQuery = "CREATE TABLE IF NOT EXISTS " + keyspace + "." + table +
      "(uuid uuid," +
      "value TEXT, " +
      "PRIMARY KEY (uuid))"
    c.withSessionDo(session => session.execute(createTableQuery))


    //var id = 4

    val tmp = line.map(record => record.value().toString)
    val _line = tmp.flatMap(_.split(",")) // This _line is Dstream
    _line.foreachRDD(e => {
      println("-----------------------------------------")
      //val temp = e.collect()
      if (e.collect().size == 0)
        println("NOTHING YET")
      else {
        //You need to auto_increment for ID, and -> uuid
        //Filter the tweet; replace ' with space or whatever
        //it causes error
        //Try to create Table if not exist   ( Bill useing session.excute )

        println("size = "+e.collect().size)
        val str = e.collect()(0).replaceAll("\"|\'", "")
        val query = "INSERT INTO "+ keyspace + "." + table +"(uuid,value) VALUES ("+"now()"+",'"+str+"')"
        println(query)
        c.withSessionDo(session => session.execute(query))
        //id += 1
      }

    })

    ssc.start()
    ssc.awaitTermination

  }
}
