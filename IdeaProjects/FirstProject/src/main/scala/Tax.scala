object Tax {
  def tax(): Unit = {
    val income = readLine("Income : ").toInt
    val kid = readLine("Kids : ").toInt

    if(income >= 100000) {
      println("Tax : "+ "%.2f".format(income*0.35))
      println("Tax Return : "+"%.2f".format(income*0.07))
    } else if( 30000 <= income && income <= 50000) {
      println("Tax : "+"%.2f".format(income*0.25))
      println("Tax Return : "+ "%.2f".format(income*0.10))
    } else {
      println("Tax : 0")
      println("Tax Return : "+"%.2f".format(income*0.13))
    }


    if(kid == 0){}
    else if(kid >0 && kid <= 3 )
      println("Activity : $2,500")
    else
      println("Activity : $7,500 AND TV!")
  }
}
