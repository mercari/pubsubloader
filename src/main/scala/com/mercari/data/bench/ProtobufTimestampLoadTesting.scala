package com.mercari.data.bench

import com.mercari.data.condition.protobuf.TimestampCondition
import com.mercari.data.loader.pubsub.PubsubLoadTesting
import com.spotify.ratatool.scalacheck.protoBufOf
import org.scalacheck.Gen

/**
 * A PubsubLoadTesting example with random generated protobuf timestamp messages
 *
 */
object ProtobufTimestampLoadTesting extends PubsubLoadTesting {

  override def testData(kinds: Int): Seq[(Map[String, String], Array[Byte])] = {
    val tsGen = protoBufOf[com.google.protobuf.Timestamp]
      .suchThat { f =>
        TimestampCondition.isValid(f)
      }
      .retryUntil(_ => true)

    val payloads = Gen.listOfN(kinds, tsGen).sample.get

    payloads.map(p => (Map.empty[String, String], p.toByteArray))
  }

}
