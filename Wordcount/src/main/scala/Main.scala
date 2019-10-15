import java.io.{BufferedWriter, File, FileWriter}

import scala.io.Source

object Main {
  def main(args : Array[String]): Unit = {

    // Read file into RDD
    val filename = "/home/hadoop/Downloads/samples.txt"
    val filename2 = "/home/hadoop/Downloads/pride_and_prejudice.txt"

    val output = "/home/hadoop//Documents/TechField Traning/Week2/homework/output1.txt"
    val output2 = "/home/hadoop//Documents/TechField Traning/Week2/homework/output2.txt"

    val lines = Source.fromFile(filename2).getLines.mkString.split("\\s+")
    val result = Map.map(lines)

    writeFile(output2,result)
    result.foreach(println)  //You gatta trim it




  }

  def writeFile(filename: String, lines: List[(String, Int)] ): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))

    println("Write File")
    val writer = new BufferedWriter(new FileWriter(filename))
    for (line <- lines) {
      //println(line.toString())
      writer.write(line.toString+"              \n")
    }
    bw.close()

    println("End")
  }

}
