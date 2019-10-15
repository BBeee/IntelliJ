object Tax2 {
  def tax2(): Unit = {
    val income = readLine("Income : $").toDouble
    val kid = readLine("Kids : ").toDouble

    var tax : Double = 0
    var ref : Double = 0
    var total : Double = 0

    if(income >= 100000){
      tax = income*0.45
      ref = income*0.05
    }
    else if(50000 <= income && income < 100000) {
      tax = income*0.35
      ref = income*0.07
    } else if( 30000 <= income && income < 50000) {
      tax = income*0.25
      ref = income*0.10
    } else {
      tax = income*0
      ref = income*0.13
    }

    total = tax - ref

    println("Income     : $"+income+"\n")
    println("Tax        : $"+ "%.2f".format(tax))
    println("Tax Return : $"+"%.2f".format(ref))
    println("-----------------------------------")
    println("Total Tax  : $"+ "%.2f".format(total))


    if(kid == 0){}
    else if(kid >0 && kid <= 3 )
      println(" + Activity : $"+2500*kid)
    else
      println(" + Activity : $"+7500*kid+" AND a TV!")
  }
}
