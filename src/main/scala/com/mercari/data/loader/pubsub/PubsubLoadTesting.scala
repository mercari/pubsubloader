package com.mercari.data.loader.pubsub

import com.spotify.scio.ScioContext
import org.apache.beam.sdk.io.GenerateSequence
import org.apache.beam.sdk.io.gcp.pubsub.{PubsubIO, PubsubMessage}
import org.apache.beam.sdk.transforms.ParDo
import org.joda.time.Duration

import scala.collection.JavaConverters._

/**
 * A Pub/Sub load generator with given data
 *
 */
trait PubsubLoadTesting extends PubsubTestDataGenerator {

  def main(cmdlineArgs: Array[String]): Unit = {
    val (opts, _) =
      ScioContext.parseArguments[PubsubLoadTestingPipelineOptions](cmdlineArgs)

    val kinds = opts.getKinds
    val msgPerMinute = opts.getMsgPerSecond

    val sc = ScioContext(opts)

    sc.customInput(
        "input",
        GenerateSequence
          .from(0)
          .withRate(1, Duration.standardSeconds(1L))
      )
      .flatMap(_ => 0 until msgPerMinute.get().toInt) // Enable to specify how many loads when runtime
      .applyTransform(ParDo.of(PubsubTestDataProvider(this, kinds)))
      .map(pair => new PubsubMessage(pair._2, pair._1.asJava))
      .saveAsCustomOutput("output", PubsubIO.writeMessages().to(opts.getOutput))

    sc.close()
  }

}
