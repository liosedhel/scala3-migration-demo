package org.virtuslab.migration.demo.ui.car

sealed trait Car {
  def name: String
}

case class Mercedes(override val name: String, version: Int) extends Car
case class Ford[T <: Comparable[_]](override val name: String, version: T) extends Car
