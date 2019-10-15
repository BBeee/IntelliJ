

object Reduce {
  def reduce(mapped: Array[(String, Int)]) : List[(String, Int)] = {
    //mapped.foreach(println)
    val reduced = mapped.groupBy(_._1).mapValues(_.size).toList
    //reduced.foreach(println)

    // t._1 to access the first element, t._2

    //val reduced = mapped.reduce((x,y) => ( x._1 , x._2 + y._2))
    //println(reduced)

    return reduced
  }
}
