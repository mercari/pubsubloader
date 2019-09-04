package com.mercari.data.loader.pubsub

import java.lang.{Long => JLong}

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions
import org.apache.beam.sdk.options.ValueProvider

trait PubsubLoadTestingPipelineOptions extends DataflowPipelineOptions {
  def getOutput: ValueProvider[String]
  def setOutput(value: ValueProvider[String]): Unit

  def getMsgPerSecond: ValueProvider[JLong]
  def setMsgPerSecond(value: ValueProvider[JLong]): Unit

  def getKinds: ValueProvider[JLong]
  def setKinds(value: ValueProvider[JLong]): Unit
}
