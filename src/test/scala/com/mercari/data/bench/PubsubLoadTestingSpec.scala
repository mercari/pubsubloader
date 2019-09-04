package com.mercari.data.bench

import com.spotify.scio.io.CustomIO
import com.spotify.scio.testing.PipelineSpec
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage
import org.scalatest.Matchers

import scala.util.Try

class PubsubLoadTestingSpec extends PipelineSpec with Matchers {

  it should "work" in {
    JobTest[ProtobufTimestampLoadTesting.type]
      .args(
        "--output=projects/mercari-dataplatform-jp-testing/topics/test",
        "--msgPerSecond=100",
        "--kinds=100"
      )
      .input(CustomIO("input"), 1L to 100L)
      .output(CustomIO[PubsubMessage]("output")) { messages =>
        messages shouldNot beEmpty

        val isSuccesses = messages
          .map(_.getPayload)
          .map(p => Try(com.google.protobuf.Timestamp.parseFrom(p)))
          .map(_.isSuccess)

        isSuccesses shouldNot containValue(false)
      }
      .run()
  }

}
