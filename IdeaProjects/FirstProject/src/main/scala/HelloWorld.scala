object HelloWorld {

  def helloworld(): Unit =  {

    //variable
    var x = "variable"  //String object
    println(x.reverse)    //String can be reversed, but Integer cannot.
    println(x)


    //value
    val y = 10              //y.reverse does not work
    val y2 = y.toString.reverse      // you can change to string and reverse!
    println(y)
    println(y2)

    val ly = Array(1,2,3,4)   //ly.reverse   can be

    //Palindrom
    var input = readLine("Input : ")
    println(input == (input.reverse))

    //Palindrom If
    input = readLine("Input : ")
    if(input == input.reverse)
      println("True!")
    else
      println("False!")
    //  Putting all in one line will work too;
    if(input == input.reverse) println("True!")  else println("False!")


    // For loop
    for( count <- 0 to 10) {
      print(count)
    }

    println()

    var temp = 30
    for( count <- 0 to temp) {
      print(count)
    }

  }

}
