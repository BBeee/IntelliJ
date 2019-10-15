import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Main {
  def main(args: Array[String]): Unit = {
    println("TEST")


    val conf = new SparkConf().setAppName("Twitter-Cassandra").setMaster("local[*]")
      .set("es.index.auto.create", "true") // for Elasticsearch connection
      .set("spark.cassandra.connection.host", "localhost") // for Cassandra connection

    val ssc = new StreamingContext(conf, Seconds(5))
    val sc = ssc.sparkContext
    sc.setLogLevel("ERROR")

    // Cassandra Configuration
    val c = CassandraConnector(sc.getConf)

    val keyspace = "twitter"
    val table = "tweet"

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    val lastdf = sqlContext.read.format("org.apache.spark.sql.cassandra").options(Map("table" -> table, "keyspace" -> keyspace)).load()
    lastdf.write.format("org.elasticsearch.spark.sql").mode("Overwrite").save("index/tweet")


    ssc.start()
    ssc.awaitTermination()
  }

}
