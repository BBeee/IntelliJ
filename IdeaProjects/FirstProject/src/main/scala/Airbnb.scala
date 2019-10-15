import scala.io.Source

object Airbnb {

  def readCSV() : Array[Array[String]] = {
    val filename = "/home/hadoop/Downloads/AB_NYC_2019.csv"
    val rows = 49081
    val cols = 16
    var matrix :Array[Array[String]] = Array.ofDim[String](rows, cols)

    // matrix = 49081 by 16
    //var count = 0
    for (line <- Source.fromFile(filename).getLines) {
      val col = line.split(',').map(_.trim)
      col.foreach(println)
      matrix = matrix :+ col
      //count += 1
    }

    //println(count)
    return matrix
  }

  def printData(matrix: Array[Array[String]]) : Unit = {
    for (each <- matrix) {     // cols is List
      for (x <- each)
        print(x+", ")
      println("")
    }
  }

  // Find the most expensive Airbnb
  def q1(matrix: Array[Array[String]]) : Unit = {
    var max = 0
    for ( n <- 0 to matrix.size-1) {     // cols is List
      if (matrix(n)(9) != null){
        print(matrix(n)(9))
      }

    }
  }


  def q2(matrix: Array[Array[String]]) : Unit = {
    for (each <- matrix) {     // cols is List
      for (x <- each)
        print(x+" ")
      println("")
    }
  }


  def q3(matrix: Array[Array[String]]) : Unit = {
    for (each <- matrix) {     // cols is List
      for (x <- each)
        print(x+" ")
      println("")
    }
  }

  def q4(matrix: Array[Array[String]]) : Unit = {
    for (each <- matrix) {     // cols is List
      for (x <- each)
        print(x+" ")
      println("")
    }
  }
  //val content=Source.fromFile(filename).getLines.map(_.split(";"))
  //val header=content.next
  //content.map(header.zip(_).toMap)

}