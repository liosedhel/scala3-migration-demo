package org.virtuslab.migration.demo.web

import org.virtuslab.migration.demo.utils.Utils

case class Migration(s: String)
object WebMain {

  def main(args: Array[String]): Unit = {
    println("WebMain app")
    Utils.printHelloWorld()
    List("scala2", "scala3").map(Migration)
  }

}
