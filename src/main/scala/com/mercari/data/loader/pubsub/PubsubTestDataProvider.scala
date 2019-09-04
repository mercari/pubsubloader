package com.mercari.data.loader.pubsub

import org.apache.beam.sdk.options.ValueProvider
import org.apache.beam.sdk.transforms.DoFn
import org.apache.beam.sdk.transforms.DoFn.{ProcessElement, Setup}

import scala.util.Random

/**
 * A test data generation DoFn ignores input and passes pregenerated random data
 *
 * @param gen
 * @param kindsProvider
 */
case class PubsubTestDataProvider(
  gen: PubsubTestDataGenerator,
  kindsProvider: ValueProvider[java.lang.Long]
) extends DoFn[AnyVal, (Map[String, String], Array[Byte])] {

  var kinds: Int = _
  // (attr source, payload), ...
  var testData: Seq[(Map[String, String], Array[Byte])] = _

  @Setup
  private[data] def setup(): Unit = {
    kinds = kindsProvider.get().toInt
    testData = gen.testData(kinds)
    testData.ensuring(_.length == kinds)
  }

  @ProcessElement
  private[data] def processElement(
    c: DoFn[AnyVal, (Map[String, String], Array[Byte])]#ProcessContext
  ): Unit = {
    val i = Random.nextInt(kinds)
    c.output(testData(i))
  }
}
