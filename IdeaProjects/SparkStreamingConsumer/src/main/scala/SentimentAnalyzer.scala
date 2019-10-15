import java.util.Properties

import Sentiment.Sentiment
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations

import scala.collection.convert.wrapAll._


class SentimentAnalyzer(){
    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props) //with Serializable

  def analyze(input: List[String] ): Unit = {
    var neg = 0
    var pos = 0
    var neu = 0
    //println(input)

    val score = mainSentiment2(input.mkString(""))

//    val score = input
//      .map( each => (mainSentiment2(each), 1)).groupBy(_._1).mapValues(_.size).toList

    print(score)


    //.maxBy( item => item._2)


//      .reduce( x => ._2.max(Int)
//      .reduceByKey(_+_)
//    score.foreach(println)


//    input.foreach( each => {
//      println(each)
//      val output = mainSentiment(each)
//      if (output == Sentiment.NEGATIVE) neg += 1
//      else if (output == Sentiment.POSITIVE) pos += 1
//      else neu += 1
//    })
//    val temp : List[Int] = List(neg,pos,neu)
//    val output = temp.max
//    println("RESULT : "+output+"\n")
//    println("<Current Score>")
//    println("POSITIVE = "+pos)
//    println("NEGATIVE = "+neg)
//    println("NEUTRAL  = "+neu)
//    //mainSentiment(input)
  }

  def mainSentiment2(input: String): Sentiment = {
    if(input.isEmpty) Sentiment.NEUTRAL
    else  extractSentiment(input)
  }

  def sentiment(input: String): List[(String, Sentiment)] = Option(input) match {
    case Some(text) if text.nonEmpty => extractSentiments(text)
    case None => throw new IllegalArgumentException("input can't be null or empty")
  }

  private def extractSentiment(text: String): Sentiment = {
    if(extractSentiments(text).isEmpty){
      Sentiment.NEUTRAL
    }
    else{    val (_, sentiment) = extractSentiments(text)
      .maxBy { case (sentence, _) => sentence.length }
      sentiment
    }

  }

  def extractSentiments(text: String): List[(String, Sentiment)] = {
    val annotation: Annotation = pipeline.process(text)
    val sentences = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
    sentences
      .map(sentence => (sentence, sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])))
      .map { case (sentence, tree) => (sentence.toString, Sentiment.toSentiment(RNNCoreAnnotations.getPredictedClass(tree))) }
      .toList
  }

}
