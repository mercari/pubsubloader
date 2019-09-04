package com.mercari.data.loader.pubsub

trait PubsubTestDataGenerator extends Serializable {
  def testData(kinds: Int): Seq[(Map[String, String], Array[Byte])]
}
