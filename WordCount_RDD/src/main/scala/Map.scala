import org.apache.spark.rdd.RDD


object Map {

  def mapper(lines: RDD[String]): RDD[(String,Int)]= {
    //val idsStr = lines.map(line => line.split(" "))
    //val pairs = idsStr.map(s => (s, 1)).foreach(println)

    val pairs = lines.flatMap(line => line.split(" "))map(word => (word, 1))
    //print(pairs)

    return Reduce.reducer(pairs)
    //pairs.saveAsTextFile("/home/hadoop/Downloads/output")
    //print(pairs)

    //print(pairs)

    //foreach (x => println (x._1 + "-->" + x._2))
  }

  //def p(rdd: org.apache.spark.rdd.RDD[_]) = rdd.foreach(println) // Option 1


}
