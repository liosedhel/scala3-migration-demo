package org.virtuslab.migration.demo.web

import org.virtuslab.migration.demo.utils.{BaseSuite, TestUtils}

import scala.collection.mutable.Stack

class ExampleSpecWeb extends BaseSuite {

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

  trait Animal //super type of Dog and Cat
  case class Dog() extends Animal //subtype of Animal
  case class Cat() extends Animal //subtype of Animal

  class MyInvariantBox[T] {
    def print(t: T) = println(s"Works $t")
  }

  class MyCovariantBox[+T] {
    //def print(t: T) = println(s"Works $t")
  }
  class MyContravariantBox[-T] {
    def print(t: T): Unit = println(t)
  }

  val box1: MyInvariantBox[Animal] = MyInvariantBox[Animal]()
  box1.print(new Cat())
  box1.print(new Dog())
  val box2: MyCovariantBox[Animal] = MyCovariantBox[Cat]()

  val box3 = MyContravariantBox[Animal]()

  val box4 = MyContravariantBox[Cat]()
  box4.print(new Cat())

  def animalPrint[T](box: MyContravariantBox[T], animal: T) = {
    box.print(animal)
  }

  animalPrint(MyContravariantBox[Animal](), new Cat())

  def animalPrintInv[T](box: MyInvariantBox[T], animal: T) = {
    box.print(animal)
  }

  //animalPrintInv(MyContravariantBox[Animal](), new Cat())

  def animalPrintCov[T](box: MyCovariantBox[T], animal: T) = {
    // box.print(animal)
  }

  animalPrintCov(MyCovariantBox[Cat](), new Cat(): Animal)

}
