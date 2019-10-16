import java.util.Properties
import java.util.concurrent.LinkedBlockingQueue
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

object Producer {

  def main(args: Array[String]): Unit = {

    // Value of Keys to Twitter
    val queue = new LinkedBlockingQueue[Status](1000)
    val consumerKey = "5A6tyMfkSddTsRCiuYYmLEbo4"
    val secretConsumerKey = "mHJplVNVlB4IN7UfgloufdIibjKOOSOonsrGWFim0ATiIbRrQE"
    val accessToken = "1171272646467686402-9ewA8HG06r4WTV2CyJrrUcmqH7o8Tt"
    val accessTokenSecret = "YvDT6O0mP105FER6b9E7Jc8YHkNksq2iZeEqnv6rc9YRH"

    val topicName = "Good"
    val keyword = "Good"

    val confBuild = new ConfigurationBuilder()

    //Configuration to access to Twitter
    confBuild.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(secretConsumerKey)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    val stream = new TwitterStreamFactory(confBuild.build()).getInstance()

    val listener = new StatusListener {
      override def onStatus(status: Status): Unit = {
        queue.offer(status)
      }
      override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {
        println("Got a status deletion notice id:"
          + statusDeletionNotice.getStatusId)
      }//end of onDeletionNotice override
      override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {
        println("Got track limitation notice:"
          + numberOfLimitedStatuses)
      }//end of onTrackLimitationNotice override
      override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {
        println("Got scrub_geo event userId:"
          + userId
          + "upToStatusId:"
          + upToStatusId)
      }//end of onScrubGeo override
      override def onStallWarning(warning: StallWarning): Unit = {
        println("Got stall warning:"
          + warning)
      }// end of onStallWarning override
      override def onException(ex: Exception): Unit = {
        ex.printStackTrace()
      }// end of onException override
    } // End of listener

    stream.addListener(listener)

    val query = new FilterQuery(keyword)
    stream.filter(query)

    //Start with Kafka
    //kafka Properties
    val properties = new Properties()
    properties.put("metadata.broker.list","localhost:9092")
    properties.put("bootstrap.servers","localhost:9092")
    properties.put("ack","all")
    properties.put("retries","0")
    properties.put("batch.size","16384")
    properties.put("linger.ms","1")
    properties.put("buffer.memory","33554432")
    properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](properties)

    var count: Int = 0

    while(true){
      val status = queue.poll()
      if(status == null){
        //No new tweets -- wait
        //println("No new tweets -- wait")
      } else{
        //println("there are tweets")
        //there are tweets
        //Reading all the hashtags to my reading account
        for(hashtagEntity <- status.getHashtagEntities){
          println("Tweet: " + status + "\nHashtag" + hashtagEntity.getText)
          producer.send(new ProducerRecord[String, String](
            topicName,
            (count += 1).toString,
            "\n Tweet : " + status.getText +
              "\n ID : "+ status.getId +
              "\n Location : " + status.getGeoLocation +
              "\n TimeStamp :" + status.getCreatedAt +
              "\n Contributors :" + status.getContributors +
              "\n Place of Tweet : " + status.getPlace +
              "\n Hashtag : " + status.getHashtagEntities +
              "\n URL : " + status.getURLEntities))
        }
      }
    }
  }
}



