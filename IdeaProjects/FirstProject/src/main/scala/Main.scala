
import org.apache.spark._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc

object Main {
  def main(args: Array[String]): Unit = {

    var matrix :Array[Array[String]] = Airbnb.readCSV()      // when you wanna call funtion from different OBJECT
    // If you wanna call a funtion from different CLASS
    // var x: Classname = new Classname(param)
    //Airbnb.printData(matrix)
    //println(matrix.size)
    //Airbnb.q1(matrix)
    val filename = "/home/hadoop/Downloads/AB_NYC_2019.csv"

    val conf = new SparkConf().setMaster("local[2]").setAppName("my app")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val sparkSession = SparkSession.builder
      .config(conf = conf)
      .appName("spark session example")
      .getOrCreate()

    val base_df = sparkSession.read.option("header", "true").
      csv(filename)

    base_df.show()
    //base_df.select("price").show()

    //base_df.select((base_df("price").cast(IntegerType).as("price")))

    //this one temp work
    //base_df.withColumn("price", base_df("price").cast(IntegerType)).groupBy("id").max("price").show()


    val df = base_df.selectExpr(
      "host_id",
      "host_name",
      "name",
      "neighbourhood_group",
      "neighbourhood",
      "cast(price as int) price",
      "cast(calculated_host_listings_count as int) calculated_host_listings_count",
      "cast(cast(price as int) as int) availability_365")

    df.show()


    // Q1.  Find the most expensive Airbnb
    val q1 = df.orderBy(desc("price")).limit(1)
    println("Q1. Find the most expensive Airbnb ")
    q1.show()

    //println("\n")

    // Q2.  Area with more Airbib
    val q2 = df.groupBy("neighbourhood").count().sort(desc("count"))
    println("Q2. Find the Area with more Airbib ")
    q2.show()


    // Q3. Which host is the busiest?
    val q3 = df.orderBy(desc("calculated_host_listings_count")).limit(1)
    println("Q3. Which host is the busiest?")
    q3.show()

    // Q4. Find Airbnb less available
    //val q4 = df.orderBy(asc("availability_365"))     Null Comes out before non-null values
    //println("Q4. Find Airbnb less available")
    //q4.show()


    // Q4. Find Airbnb less available
    val q4 =  df.sort(df("availability_365").asc_nulls_last)
    println("Q4. Find Airbnb less available")
    q4.show()




    val file = "/home/hadoop/Desktop/output/"

    //q1.rdd.saveAsTextFile("/home/hadoop/Desktop/output/q1.txt")
    //q2.rdd.saveAsTextFile("/home/hadoop/Desktop/output/q2.txt")
    //q3.rdd.saveAsTextFile("/home/hadoop/Desktop/output/q3.txt")
    //q4.rdd.saveAsTextFile("/home/hadoop/Desktop/output/q4.txt")


  }
}