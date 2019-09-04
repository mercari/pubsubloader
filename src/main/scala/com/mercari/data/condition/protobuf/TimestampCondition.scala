package com.mercari.data.condition.protobuf

import com.google.protobuf.Timestamp

object TimestampCondition {

  // See https://github.com/protocolbuffers/protobuf/blob/master/src/google/protobuf/timestamp.proto
  private val SECONDS_LOWER_LIMIT = -62135596800L
  private val SECONDS_UPPER_LIMIT = 253402300799L
  private val NANOS_LOWER_LIMIT = 0
  private val NANOS_UPPER_LIMIT = 999999999

  /**
   * Validate the values satisfy expectation in proto
   *
   * @param ts
   * @return
   */
  def isValid(ts: Timestamp): Boolean =
    ts.getSeconds >= SECONDS_LOWER_LIMIT &&
      ts.getSeconds < SECONDS_UPPER_LIMIT &&
      ts.getNanos >= NANOS_LOWER_LIMIT &&
      ts.getNanos < NANOS_UPPER_LIMIT

}
