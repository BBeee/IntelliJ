object Map {
  def map(lines: Array[String]) : List[(String, Int)] = {
    //lines.foreach(println)
    //val mapped = lines.map(s => (s,1))
    val mapped = lines.map(s => (s,1))

    return Reduce.reduce(mapped)



  }
}
