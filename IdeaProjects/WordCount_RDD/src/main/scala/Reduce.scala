import org.apache.spark.rdd.RDD

object Reduce {

  def reducer(pairs: RDD[(String,Int)]) : RDD[(String,Int)]= {
    val counts = pairs.reduceByKey(_+_)

    return counts
  }

}
