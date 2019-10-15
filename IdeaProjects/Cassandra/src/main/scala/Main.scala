import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

//import org.apache.spark.sql.SparkSession

class Main {

  def main(args: Array[String]) : Unit = {
//    val conf = new SparkConf(true)
//      .set("spark.cassandra.connection.host", "127.0.0.1")
//    val sc = new SparkContext("dse://127.0.0.1:7077", "test", conf)
//    val rdd = sc.cassandraTable("my_keyspace", "my_table")

//    val conf = new SparkConf()
//      .setMaster("local[*]")
//      .setAppName("SparkCassandra")
//      //set Cassandra host address as your local address
//      .set("spark.cassandra.connection.host", "192.168.30.154")

    val conf = new SparkConf(true)
      .set("spark.cassandra.connection.host", "127.0.0.1")
    val sc = new SparkContext(conf)
    //get table from keyspace and stored as rdd
    val rdd = sc.cassandraTable("contries", "countries")
    //collect will dump the whole rdd data to driver node (here's our machine),
    //which may crush the machine. So take first 100 (for now we use small table
    //so it's ok)
    val file_collect = rdd.collect().take(100)
    file_collect.foreach(println(_))

    //sc.stop()
//    val schema = StructType(List(
//      StructField("id", IntegerType, true),
//      StructField("name", StringType, true),
//      StructField("capital", StringType, true)
//    ))

    //Loading Cassandra Data to DataFrame
    val sqlContext= new org.apache.spark.sql.SQLContext(sc)
    val lastdf = sqlContext.read.format("org.apache.spark.sql.cassandra").options(Map("table" -> "countries", "keyspace" -> "contries")).load()

    //Save it to Cassandra
    val rdd2 = sc.parallelize(rdd.collect)
    rdd2.saveToCassandra("contries","countries",SomeColumns("id","name","capital"))


  }

}
