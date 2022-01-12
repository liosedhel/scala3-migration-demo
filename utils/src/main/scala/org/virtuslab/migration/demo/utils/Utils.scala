package org.virtuslab.migration.demo.utils

import org.virtuslab.migration.demo.ui._

object Utils {
  def printHelloWorld(): Int = {
    println("Hello World from main utils")
    values
    0
  }

  def values: Array[Animal] = {
    AnimalType.values() ++ Animal2Type.values()
  }
}
