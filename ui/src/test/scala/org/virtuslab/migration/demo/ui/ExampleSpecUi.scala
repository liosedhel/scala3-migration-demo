package org.virtuslab.migration.demo.ui

import org.scalatest.flatspec.AnyFlatSpec
import org.virtuslab.migration.demo.utils.BaseSuite
import org.virtuslab.migration.demo.utils.TestUtils
import scala.collection.mutable.Stack

class ExampleSpecUi extends BaseSuite {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be(2)
    stack.pop() should be(1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new Stack[Int]
    a[NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
  }

  it should "use the TestUtils" in {
    TestUtils.printHelloWorld() shouldBe 0
  }
}
