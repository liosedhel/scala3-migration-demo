package org.virtuslab.migration.demo.utils

final case class TooLongFileError(s: String, i: Int) extends ErrorCode {
  override val message: String = "MyMessage"
  override val code: String = "400"
}
