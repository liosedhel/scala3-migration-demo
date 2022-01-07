package org.virtuslab.migration.demo.utils

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._

import scala.collection.mutable.Stack

trait BaseSuite extends AnyFlatSpec with should.Matchers {

  "A base suite" should "has some test" in {
    1 shouldBe 1
    Utils.printHelloWorld() shouldBe 0
  }

}
