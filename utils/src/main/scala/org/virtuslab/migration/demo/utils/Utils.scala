package org.virtuslab.migration.demo.utils

import org.virtuslab.migration.demo.ui.car._

import org.virtuslab.migration.demo.ui._
import scala.jdk.CollectionConverters.IterableHasAsJava
import java.util
import scala.jdk.CollectionConverters.{IterableHasAsJava, ListHasAsScala}
import java.time.LocalDate
import scala.util.Success

case class Interval[ELEM_TYPE]() {
  def from: Option[ELEM_TYPE] = None
}

class GenerateTimeRange {
  type TimeRange = Interval[LocalDate]
  def timeRange(): TimeRange = ???
}

object Utils {
  def printHelloWorld(): Int = {
    println("Hello World from main utils")
    values
    0
  }

  def values: Array[Animal] = {
    AnimalType.values() ++ Animal2Type.values()
  }

  def testMatching(car: Car) = {
    car match {
      case Mercedes(name, version) => println(name)
      case Ford(name, version)     => println(name)
    }
  }

  // def functionInterop() = {
  //   new FunctionInterop()
  //     .setExpanded(
  //       List(new Tiger(), new Tiger(), new Tiger()).asJava,
  //       new java.util.function.Function[Tiger, Boolean] {
  //         def apply(tiger: Tiger): Boolean = {
  //           true
  //         }
  //       }
  //     )
  // }

  def functionInterop2() = {
    val nodesToCollapse: util.Set[Tiger] = new util.HashSet[Tiger]()
    new FunctionInterop()
      .setExpanded(
        List(new Tiger(), new Tiger(), new Tiger()).asJava,
        (t: Tiger) => !nodesToCollapse.contains(t)
      )
  }

  //class MyClass[T <: Comparable[T]](arg: T)

  def javaInterop3() = {
    val map = new util.HashMap[String, MyClass[_]]
    if (true) {
      val myClassString = new MyClass[java.lang.String]()
      map.put("sdfsdf", myClassString)
      ()
    }
  }

  def testClassTagInArray() = {
    val opt = Some(Array.empty[Int])
    val i = opt.getOrElse(Array.empty[Int])
  }

  def testAutoUnit() = {
    Success(())
  }

  val seIt: Iterable[Int] = Iterable.apply(1, 2, 3)
  val o = Some(1)

  @inline def m() = {
    seIt
      .map { _ =>
        o.fold(Set(1)) { i =>
          if (i == 0) Set.empty else Set(0)
        }
      }
      .reduce(_ intersect _)
  }

  def produceStructure = Seq(new {
    val test: String = "test"
  })

  //produceStructure.map(_.test)

  import izumi.reflect.macrortti._
  def m(t: LightTypeTag) = t match {
    case t if t <:< LTT[String] => Option("x")
  }

  def methodWrongIndent() = {
    List().foreach { _ =>
      case class Test()
      val a = 1
    }

  }

  def addEnums(
      first: Set[AnimalType],
      second: Set[Animal2Type]
  ): Set[Animal] = {
    val an: Set[Animal] = first ++ second
    an
  }

}

object Operations {
  new Operations().publish(())
}

class Operations {
  def publish(e: Any): Unit = {}
}
